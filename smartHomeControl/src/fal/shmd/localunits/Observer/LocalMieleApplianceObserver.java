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


package fal.shmd.localunits.Observer;


import java.util.ArrayList;
import java.util.List;

import fal.shmd.HAL.HALExchange.ApplianceState;
import fal.shmd.HAL.HALExchange.IntelligentApplianceObserverExchange;
import fal.shmd.HAL.HALExchange.PowerProfile;
import fal.smartHomeLib.CBTypes.Data.Observer.ObservedApplianceAction;
import fal.smartHomeLib.ControllerBoxCore.Data.ModelOfObservationExchange;
import fal.smartHomeLib.ControllerBoxCore.Data.ObserverExchange;
import fal.smartHomeLib.ControllerBoxCore.Exception.ControllerBoxException;
import fal.smartHomeLib.ControllerBoxCore.OC.LocalObserver;
import fal.smartHomeLib.HAL.HALrealTimeDriver;

/**
 * @author florian
 */
public class LocalMieleApplianceObserver extends LocalObserver {
	private ObserverExchange observerExchange;
	private ApplianceState currentState;
	private List<PowerProfile> currentApplianceProfile;


	public List<PowerProfile> getCurrentApllianceProfile() {
		return currentApplianceProfile;
	}

	public void setCurrentApllianceProfile(
			List<PowerProfile> currentApllianceProfile) {
		this.currentApplianceProfile = currentApllianceProfile;
	}

	public LocalMieleApplianceObserver(HALrealTimeDriver systemTimer) {
		super(systemTimer);
		this.observerExchange = new ObserverExchange();
		this.currentApplianceProfile = new ArrayList<PowerProfile>();
	}

	@Override
	public void onDeviceStateUpdate() {
		IntelligentApplianceObserverExchange halObserverExchangeObject = (IntelligentApplianceObserverExchange) getObserverDataObject();

		if (halObserverExchangeObject.getApplianceState() != currentState) {
			//set the current state
			this.currentState = halObserverExchangeObject.getApplianceState();
			
			ObservedApplianceAction appAction = new ObservedApplianceAction();
			appAction.setDeviceID(this.getDeviceID().toString());
			appAction.setTimeStamp(getSystemTimer().getUnixTime());
			appAction.setActionOccurrd(true);
			
			
			switch (halObserverExchangeObject.getApplianceState()) {
			case PROGRAMMED:
			{
				appAction.setProgrammed(true);
				this.currentApplianceProfile = new ArrayList<PowerProfile>();
				this.currentApplianceProfile.addAll(halObserverExchangeObject.getExpectedPowerProfile());
				globalLogger.logDebug("Appliance " + halObserverExchangeObject.getDeviceID().toString() + " programmed at tick:" + this.systemTimer.getUnixTime());
			}		
				break;
			case OFF:
			{
				this.currentApplianceProfile.clear();
				appAction.setStopRunning(true);
				globalLogger.logDebug("Appliance " + halObserverExchangeObject.getDeviceID().toString() + " set -off- at tick:" + this.systemTimer.getUnixTime());
			}
				break;
			case ON:
			{
				this.currentApplianceProfile.clear();
				appAction.setStopRunning(true);
				globalLogger.logDebug("Appliance " + halObserverExchangeObject.getDeviceID().toString() + " set -on- at tick:" + this.systemTimer.getUnixTime());
			}
				break;
			case RUNNING:
			{
				appAction.setStartRunning(true);
				globalLogger.logDebug("Appliance " + halObserverExchangeObject.getDeviceID().toString() + " start running at tick:" + this.systemTimer.getUnixTime());
			}
				break;
			case END:
			{
				this.currentApplianceProfile.clear();
				appAction.setStopRunning(true);
				globalLogger.logDebug("Appliance " + halObserverExchangeObject.getDeviceID().toString() + " stop running at tick:" + this.systemTimer.getUnixTime());
			}
			default:
				break;
			}

			this.observerExchange.setLastAction(appAction);
			this.updateGlobalObserver();
		
			//set the current app profile
			//setPowerProfile(halObserverExchangeObject.getExpectedPowerProfile());
			

		} 
		//for testing
//		if (halObserverExchangeObject.getApplianceState() == ApplianceState.RUNNING) {
//			System.out.println("Device with active power: " + ((IntelligentApplianceObserverExchange) halObserverExchangeObject).getCurrentActivePower() + " is running at " +this.getCurrentTimeFromTimeBase());
//		}

	}
	
	private void setPowerProfile(List <PowerProfile> powerProfiles){
		if (this.currentApplianceProfile.isEmpty()){
			int timeResolutionDivisor = 60;
			int counter = 0;
			int powerAVG = 0;
			ArrayList<PowerProfile> _profileList = new ArrayList<PowerProfile>();
			for(PowerProfile powerProfile: powerProfiles){
				
				if(counter < timeResolutionDivisor){
					powerAVG += powerProfile.activePower;
					++counter;
					
				}
				else{
					PowerProfile _pwrProfile = new PowerProfile();
					_pwrProfile.activePower = powerAVG/timeResolutionDivisor;
	
				}
				
			}
			this.currentApplianceProfile.addAll(_profileList);
		}
	}

	@Override
	public ObserverExchange pollObserver() {
		return observerExchange;
	}

	@Override
	public void onSystemIsUp() throws ControllerBoxException {
		// TODO Auto-generated method stub

	}

	@Override
	public ModelOfObservationExchange getObservedModelData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onNextTimePeriode() throws ControllerBoxException {
		// TODO Auto-generated method stub
		
	}


}
