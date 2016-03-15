/*
 ************************************************************************************************* 
 * OrganicSmartHome [Version 1.0] is a framework for energy management in intelligent buildings
 * Copyright (C) 2011  Florian Allerding (florian.allerding@kit.edu)
 * 
 * 
 * This file is part of the OrganicSmartHome.
 * 
 * OrganicSmartHome is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 * 
 * OrganicSmartHome is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with OrganicSmartHome.  
 * 
 * If not, see <http://www.gnu.org/licenses/>.
 * 
 *************************************************************************************************
 */


package fal.rsh.driver.miele;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.xml.bind.JAXBException;
import fal.rsh.driver.SmartHomeDeviceDispatcher;
import fal.rsh.driver.data.MieleAppliance;
import fal.rsh.driver.data.MieleApplianceDetails;
import fal.rsh.driver.data.WagoPowerData;
import fal.shmd.HAL.HALExchange.ApplianceCommand;
import fal.shmd.HAL.HALExchange.ApplianceState;
import fal.shmd.HAL.HALExchange.IntelligentApplianceControllerExchange;
import fal.shmd.HAL.HALExchange.IntelligentApplianceObserverExchange;
import fal.shmd.HAL.HALExchange.PowerProfile;
import fal.smartHomeLib.CBTypes.CBParameterCollection;
import fal.smartHomeLib.CBTypes.XMLSerialization;
import fal.smartHomeLib.CBTypes.Simulation.VirtualDevicesData.DeviceProfile;
import fal.smartHomeLib.CBTypes.Simulation.VirtualDevicesData.ProfileTick;
import fal.smartHomeLib.ControllerBoxCore.Exception.ControllerBoxException;
import fal.smartHomeLib.HAL.HALDispatcher;
import fal.smartHomeLib.HAL.HALcontrollerDriver;
import fal.smartHomeLib.HAL.HALdriver;
import fal.smartHomeLib.HAL.HALobserverDriver;
import fal.smartHomeLib.HAL.HALrealTimeDriver;
import fal.smartHomeLib.HAL.Exception.HALDriverException;
import fal.smartHomeLib.HAL.Exchange.HALControllerExchange;

/**
 * @author florian.allerding@kit.edu
 * @category smart-home  Driver-Parser
 */
public class GenericMieleDriver extends HALdriver {

	private String appliancePhase = "";
	//driverData
	private int appliancePower;
	private String applianceProgram = "";
	private int applianceRemainingTime = 0;
	private ApplianceState applianceState;
	private String applianceStep = "";
	private List<PowerProfile> currentPowerProfile;
	private DeviceProfile deviceProfile;
	private MieleAppliance mieleAppliance;
	private WagoPowerData wagoPowerData;
	private boolean applianceStartEnabled;
	private boolean applianceStopEnabled;
	private String applianceStartURL = "";
	private String applianceStopURL = "";
	
	public GenericMieleDriver(HALrealTimeDriver timerDriver, UUID deviceID,
			CBParameterCollection driverConfig) {
		super(timerDriver, deviceID, driverConfig);
		
	}

	public GenericMieleDriver(UUID deviceID, HALrealTimeDriver timerDriver,
			HALobserverDriver halobserverDriver,
			HALcontrollerDriver halcontrollerDriver,
			CBParameterCollection driverConfig) {
		super(deviceID, timerDriver, halobserverDriver, halcontrollerDriver,
				driverConfig);
		
	}

	private int calculateProgramTime (String mieleTime){
		int TimeInSeconds = 0;
		String _tmpTime = mieleTime.substring(0, mieleTime.length()-1);
		String[] _tmpTimeHourMin = _tmpTime.split(":");
		TimeInSeconds = Integer.valueOf(_tmpTimeHourMin[0]) * 3600 + Integer.valueOf(_tmpTimeHourMin[1]) * 60;
		
		return TimeInSeconds;
	}
	
	private List<PowerProfile> shrinkPowerProfile(List<PowerProfile> powerProfile, int programDuration){
		List<PowerProfile> _tmpList = new ArrayList<PowerProfile>();
		
		//if it's greater => shrink it!
		if (powerProfile.size() >= programDuration) {
			for( int i = 0; i < programDuration; i++){
				_tmpList.add(powerProfile.get(i));
			}
		}
		else {
			_tmpList.addAll(powerProfile);
			//expand it
			for (int i = 0; i < (programDuration-powerProfile.size()); i++){
				_tmpList.add(powerProfile.get(powerProfile.size()-1));
			}
		}
		
		return _tmpList;
	}
	
	private void callTheLocalObserver(){
		IntelligentApplianceObserverExchange mieleObserverExchange = new IntelligentApplianceObserverExchange();
		
		
		switch (applianceState) {
		case PROGRAMMED: {
			mieleObserverExchange.setDeviceID(this.getDeviceID());
			mieleObserverExchange.setApplianceState(applianceState);
			mieleObserverExchange.setCurrentActivePower(0);
			mieleObserverExchange.setCurrentReactivePower(0);
			mieleObserverExchange.setDeviceID(getDeviceID());
			mieleObserverExchange.setExpectedProgramDuration(applianceRemainingTime);
			mieleObserverExchange.setExpectedPowerProfile(shrinkPowerProfile(currentPowerProfile, applianceRemainingTime));
		}
			
			break;

		default: {
			mieleObserverExchange.setDeviceID(this.getDeviceID());
			mieleObserverExchange.setApplianceState(applianceState);
			mieleObserverExchange.setCurrentActivePower(this.appliancePower);
			mieleObserverExchange.setCurrentReactivePower(0);
			mieleObserverExchange.setDeviceID(getDeviceID());
			mieleObserverExchange.setProgramTimeLeft(this.applianceRemainingTime);
			
		}
			break;
		}


		this.notifyObserver(mieleObserverExchange);
		
		
		
	}
	protected void collectDeviceData() throws HALDriverException {
		
		//get appliance data object
		mieleAppliance = ((SmartHomeDeviceDispatcher)this.getHalDispatcher()).getMieleApplianceById(this.getDeviceID());
		wagoPowerData = ((SmartHomeDeviceDispatcher)this.getHalDispatcher()).getWagoPowerDataById(this.getDeviceID());
		
		if(mieleAppliance == null) {
			throw new HALDriverException("Miele-Appliance-Data not available!");
		}
		if (wagoPowerData == null) {
			throw new HALDriverException("Wago-Power-Data not available!");
		}
		
	}
	
	private List<PowerProfile> generatePowerProfile(){
		
		ArrayList<PowerProfile> _pwrProfileList = new ArrayList<PowerProfile>();
		int count = 0;
		for(ProfileTick profileTick: deviceProfile.getProfileTicks()){
			PowerProfile _pwrPro = new PowerProfile();
			_pwrPro.activePower = profileTick.getActivePower();
			_pwrPro.ApplianceState = 0;
			_pwrPro.TimeTick = count;
			_pwrProfileList.add(_pwrPro);
			++count;
			
		}
		
		
		return _pwrProfileList;
	}
	
	private void loadDeviceProfiles() throws ControllerBoxException{
		String profileSourceName = driverConfig.getParameter("profileSource");
		//load profiles
			try {
				deviceProfile = (DeviceProfile)XMLSerialization.file2Unmarshal(profileSourceName, DeviceProfile.class);
			} catch (FileNotFoundException e) {
				throw new ControllerBoxException(e);
			} catch (JAXBException e) {
				throw new ControllerBoxException(e);
			}

	
	}
	
	
	@Override
	protected void onControllerRequest(HALControllerExchange controllerRequest) {
		
		try {
			IntelligentApplianceControllerExchange controllerExchange = (IntelligentApplianceControllerExchange) controllerRequest;

		String reqURLString = "";
		
		if ((controllerExchange.getApplianceCommand() == ApplianceCommand.START) && applianceStartEnabled){
			reqURLString = applianceStartURL;
		}
		
		if (controllerExchange.getApplianceCommand() == ApplianceCommand.STOP && applianceStopEnabled){
			reqURLString = applianceStopURL;
		}
		
		//process request
		
		URL mieleReqUrl = null;
		try {
			mieleReqUrl = new URL(reqURLString);
		} catch (MalformedURLException ex) {
			globalLogger.logError("Can't get miele command URL!", ex);
			throw new Exception(ex);
		}
		 
		
		try {
			globalLogger.logDebug("Sending request to Miele device");
			URLConnection mieleConn = mieleReqUrl.openConnection();
			mieleConn.getInputStream().close();
			
		} catch (IOException ex) {
			globalLogger.logError("sending request to miele appliance failed!", ex);
			throw new Exception(ex);
			
		}
		} catch (Exception reqEx) {
			globalLogger.logError("Request to Miele Gateway failed!", reqEx);
		}
		
		//mieleConn = null;	
				
	}
	
	
	@Override
	public void onNextTimePeriode() throws ControllerBoxException {
		super.onNextTimePeriode();
		try {
			collectDeviceData();
			parseMieleData();
			callTheLocalObserver();
			
		} catch (HALDriverException ex) {
			globalLogger.logError("parsing Miele data failed, waiting for the next valid data set from the miele-gateway...", ex);
		}
	}
	
	/* (non-Javadoc)
	 * @see fal.HAL.HALdriver#onSystemIsUp()
	 */
	@Override
	public void onSystemIsUp() throws ControllerBoxException {
		super.onSystemIsUp();
		//in the moment we have only one Power Profile ;-)
		this.loadDeviceProfiles();
		this.currentPowerProfile = this.generatePowerProfile();
		this.timerDriver.registerComponent(this, 1);
		
	}

	/**
	 * get the informations from the data object given from the mieleGateway-proxy
	 */
	private void parseMieleData(){
		
		//first check if the appliance is able to start or stop
		applianceStartEnabled = false;
		applianceStopEnabled = false;
		
		for (MieleApplianceDetails applianceActions: this.mieleAppliance.getAddActionList()) {
			
			if(applianceActions.getName().equalsIgnoreCase("start")){
				applianceStartEnabled = true;
				//try this one
				if ((applianceActions.getValue() != "") || (applianceActions.getValue() != null)){
					applianceStartURL = applianceActions.getValue();
				}
			}
			if(applianceActions.getName().equalsIgnoreCase("stop")){
				applianceStopEnabled = true;
				if ((applianceActions.getValue() != "") || (applianceActions.getValue() != null)){
					applianceStopURL = applianceActions.getValue();
				}
			}
		}
		
		//now look for the current state an supplementary informations
		for(MieleApplianceDetails applianceDetails: this.mieleAppliance.getAddInfoList()){
			if (applianceDetails.getName().equals("State")){
				
				//set the default state: it's always off...
				applianceState = ApplianceState.OFF;
				
				if (applianceDetails.getValue().equalsIgnoreCase("Off")){
					applianceState = ApplianceState.OFF;
				}
				if (applianceDetails.getValue().equalsIgnoreCase("On")){
					applianceState = ApplianceState.ON;
				}
				if (applianceDetails.getValue().equalsIgnoreCase("Programmed")){
					//because of a different semantic meaning(!!!!) between the miele appliances we need 
					//here a supplementary decision base: some of the appliances are only "really" programmed,
					//if you can see the tinny little buttom to switch them on...
					if (applianceStartEnabled) {
						applianceState = ApplianceState.PROGRAMMED;
					}
					else {
						//o.K. you've choosen the program - but that's all (not really interesting for us...)
						applianceState = ApplianceState.ON;
					}
					
					
				}
				if (applianceDetails.getValue().equalsIgnoreCase("Running")){
					applianceState = ApplianceState.RUNNING;
				}
				if (applianceDetails.getValue().equalsIgnoreCase("End")){
					applianceState = ApplianceState.END;
				}
				if (applianceDetails.getValue().equalsIgnoreCase("Abort")){
					applianceState = ApplianceState.END;
				}
				if (applianceDetails.getValue().equalsIgnoreCase("Paused")){
					applianceState = ApplianceState.ON;
				}
				
			}
			if (applianceDetails.getName().equals("Phase")){
				this.appliancePhase = applianceDetails.getValue();
			}
			if (applianceDetails.getName().equals("Remaining Time")){
				this.applianceRemainingTime = calculateProgramTime(applianceDetails.getValue());
			}
			if (applianceDetails.getName().equals("Program")){
				this.applianceProgram = applianceDetails.getValue();
			}
		}
		
		
	}
	

}
