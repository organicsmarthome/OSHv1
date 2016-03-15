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


package fal.shmd.OfflineSimulation.Subjects;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fal.shmd.HAL.HALExchange.ApplianceCommand;
import fal.shmd.HAL.HALExchange.ApplianceState;
import fal.shmd.HAL.HALExchange.IntelligentApplianceControllerExchange;
import fal.shmd.HAL.HALExchange.IntelligentApplianceObserverExchange;
import fal.shmd.HAL.HALExchange.PowerProfile;
import fal.smartHomeLib.CBTypes.CBParameterCollection;
import fal.smartHomeLib.CBTypes.Simulation.Screenplay.SubjectAction;
import fal.smartHomeLib.CBTypes.Simulation.VirtualDevicesData.ProfileTick;
import fal.smartHomeLib.ControllerBoxCore.Exception.ControllerBoxException;
import fal.smartHomeLib.HAL.HALrealTimeDriver;
import fal.smartHomeLib.HAL.Exchange.HALControllerExchange;
import fal.smartHomeLib.SimulationCore.Exception.SimulationSubjectException;
import fal.smartHomeLib.SimulationCore.SimulationSubjects.VirtualGenericAppliance;


public class VirtualMieleAppliance extends VirtualGenericAppliance {

	private ApplianceState applianceState;
	private String applianceDetails;
	private List<PowerProfile> currentPowerProfile;
	
	
	public VirtualMieleAppliance(HALrealTimeDriver timerDriver,
			UUID deviceID, CBParameterCollection driverConfig) throws SimulationSubjectException {
		super(timerDriver, deviceID, driverConfig);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onInit() {
		activePower = 0;
		reactivePower = 0;
		progamDuration = deviceProfile.getProfileTicks().size();
		systemState = false;
		isIntelligent = deviceProfile.isIntelligent();
		hasProfile = deviceProfile.isHasProfile();
		progTick = 0;
		applianceDetails = "";
		applianceState = ApplianceState.OFF;

	}

	@Override
	protected void onProcessingTimeTick() {
		//notify the controllerbox

		IntelligentApplianceObserverExchange observerObj = new IntelligentApplianceObserverExchange();
		
		switch (applianceState) {
		case PROGRAMMED: {
			observerObj.setDeviceID(this.getDeviceID());
			observerObj.setApplianceState(applianceState);
			observerObj.setCurrentActivePower(0);
			observerObj.setCurrentReactivePower(0);
			observerObj.setDeviceID(getDeviceID());
			observerObj.setExpectedProgramDuration(currentPowerProfile.size());
			observerObj.setExpectedPowerProfile(currentPowerProfile);
		}
			
			break;

		case RUNNING: {
			
			observerObj.setDeviceID(this.getDeviceID());
			observerObj.setApplianceState(applianceState);
			observerObj.setCurrentActivePower(activePower);
			observerObj.setCurrentReactivePower(0);
			observerObj.setDeviceID(getDeviceID());
			observerObj.setExpectedProgramDuration(currentPowerProfile.size());
			observerObj.setProgramTimeLeft(currentPowerProfile.size()-this.progTick);
			
			break;
		}
			
		default: {
			observerObj.setDeviceID(this.getDeviceID());
			observerObj.setApplianceState(applianceState);
			observerObj.setCurrentActivePower(activePower);
			observerObj.setCurrentReactivePower(0);
			observerObj.setDeviceID(getDeviceID());
			observerObj.setProgramTimeLeft(0);
			
		}
			break;
		}


		this.notifyObserver(observerObj);

	}

	@Override
	protected void onControllerRequest(HALControllerExchange controllerRequest) {
		IntelligentApplianceControllerExchange controllerExchange = (IntelligentApplianceControllerExchange) controllerRequest;
		
		if (controllerExchange.getApplianceCommand() == ApplianceCommand.START){
			systemState = true;
			applianceState = ApplianceState.RUNNING;
		}
		
		if (controllerExchange.getApplianceCommand() == ApplianceCommand.STOP){
			systemState = false;
			applianceState = ApplianceState.OFF;
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
	@Override
	public void performNextAction(SubjectAction nextAction){
		//if the next state is true the i-appliance is filled up an is now able to run
		if (nextAction.isNextState()) {
			if (this.isControllable()) {
				applianceState = ApplianceState.PROGRAMMED;
			}
			else {
				applianceState = ApplianceState.RUNNING;
				systemState = true;
			}
			this.currentPowerProfile = generatePowerProfile();
			
		}
		else
		{
			applianceState = ApplianceState.OFF;
			systemState = false;
		}
	}
	

	@Override
	protected void onActiveTimeTick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onProgramEnd() {
		applianceState = ApplianceState.OFF;
		
	}


}
