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

import fal.shmd.HAL.HALExchange.SmartPlugObserverExchange;
import fal.smartHomeLib.CBTypes.Data.Observer.ObservedSmartPlugAction;
import fal.smartHomeLib.ControllerBoxCore.Data.ModelOfObservationExchange;
import fal.smartHomeLib.ControllerBoxCore.Data.ObserverExchange;
import fal.smartHomeLib.ControllerBoxCore.OC.LocalObserver;
import fal.smartHomeLib.HAL.HALrealTimeDriver;

public class SmartPlugObserver extends LocalObserver {

	private int powerThreshold = 400;
	private boolean connectedApplianceRunning = false;
	private int lastPowerValue = 0;
	private int powerChangeThreshold = 100;
	private ObserverExchange observerExchange;
	public SmartPlugObserver(HALrealTimeDriver systemTimer) {
		super(systemTimer);
		this.observerExchange = new ObserverExchange();
	}

	@Override
	public void onDeviceStateUpdate() {
		SmartPlugObserverExchange plugObserverExchange = (SmartPlugObserverExchange) getObserverDataObject();
		
		if(!connectedApplianceRunning){
			//look if the connected device to the plugs do something
			if(plugObserverExchange.getActivePower() > powerThreshold){
				connectedApplianceRunning = true;
				lastPowerValue = plugObserverExchange.getActivePower();
				//notify global observer => device start running
				ObservedSmartPlugAction appAction = new ObservedSmartPlugAction();
				appAction.setDeviceID(this.getDeviceID().toString());
				appAction.setTimeStamp(getSystemTimer().getUnixTime());
				appAction.setActionOccurrd(true);
				appAction.setStartRunning(true);
				appAction.setCurrentPowerValue(plugObserverExchange.getActivePower());
				this.observerExchange.setLastAction(appAction);
				this.updateGlobalObserver();
			}
		}
		else{
			if(plugObserverExchange.getActivePower() <= powerThreshold){
				connectedApplianceRunning = false;
				//notify global observer => device stop running
				ObservedSmartPlugAction appAction = new ObservedSmartPlugAction();
				appAction.setDeviceID(this.getDeviceID().toString());
				appAction.setTimeStamp(getSystemTimer().getUnixTime());
				appAction.setActionOccurrd(true);
				appAction.setStopRunning(true);
				this.observerExchange.setLastAction(appAction);
				this.updateGlobalObserver();
				
			}
			else {
				if(Math.abs(plugObserverExchange.getActivePower()-lastPowerValue) > powerChangeThreshold){
					//notify global observer => device power changed!
					ObservedSmartPlugAction appAction = new ObservedSmartPlugAction();
					appAction.setDeviceID(this.getDeviceID().toString());
					appAction.setTimeStamp(getSystemTimer().getUnixTime());
					appAction.setActionOccurrd(true);
					appAction.setChangedPowerValue(true);
					appAction.setCurrentPowerValue(plugObserverExchange.getActivePower());
					this.observerExchange.setLastAction(appAction);
					this.updateGlobalObserver();
				}
			}
		}
		
	}

	@Override
	public ObserverExchange pollObserver() {
		// TODO Auto-generated method stub
		return this.observerExchange;
	}

	@Override
	public ModelOfObservationExchange getObservedModelData() {
		// TODO Auto-generated method stub
		return null;
	}

}
