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


package fal.shmd.localunits.Controller;

import javax.swing.SpringLayout.Constraints;

import fal.shmd.HAL.HALExchange.ApplianceCommand;
import fal.shmd.HAL.HALExchange.IntelligentApplianceControllerExchange;
import fal.shmd.HAL.HALExchange.IntelligentApplianceObserverExchange;
import fal.smartHomeLib.CBTypes.Data.ApplianceState;
import fal.smartHomeLib.ControllerBoxCore.Data.ControllerExchange;
import fal.smartHomeLib.ControllerBoxCore.Exception.ControllerBoxException;
import fal.smartHomeLib.ControllerBoxCore.OC.LocalController;
import fal.smartHomeLib.HAL.HALrealTimeDriver;


public class LocalMieleApplianceController extends LocalController {
	
	private boolean pendingRecomandation;
	private int pendingRecomandationCounter; 
	private final int maxRetryNumber = 5;
	
	public LocalMieleApplianceController(HALrealTimeDriver systemTimer) {
		super(systemTimer);
		this.pendingRecomandation = false;
		this.pendingRecomandationCounter = 0;
		
	}


	/**
	 * tell "Erna" that she has to work...
	 */
	private void callDevice(){
		IntelligentApplianceControllerExchange halControllerExchangeObject = new IntelligentApplianceControllerExchange();
	    halControllerExchangeObject.setApplianceCommand(ApplianceCommand.START);
	    this.pendingRecomandation = true;
		this.updateUnit(halControllerExchangeObject);
		++this.pendingRecomandationCounter;
	}

	@Override
	public void recommandUnit(ControllerExchange controllerExchange) {

		IntelligentApplianceObserverExchange halObserverExchangeObject = (IntelligentApplianceObserverExchange) this.getLocalObserver().getObserverDataObject();
		fal.shmd.HAL.HALExchange.ApplianceState _state = halObserverExchangeObject.getApplianceState();
		//first ask the local observer if you can do so...
		//test if the appliance is able to run
		if(_state == fal.shmd.HAL.HALExchange.ApplianceState.PROGRAMMED){
			//if it is so...call the appliance...
			this.pendingRecomandationCounter = 0;
			this.callDevice();
			//check after 30 sec if the appliance is faithfully;-)
			this.systemTimer.registerComponent(this, 30);
		}
		
	}
	
	private void processPendingCommand(){
		IntelligentApplianceObserverExchange halObserverExchangeObject = (IntelligentApplianceObserverExchange) this.getLocalObserver().getObserverDataObject();
		fal.shmd.HAL.HALExchange.ApplianceState _state = halObserverExchangeObject.getApplianceState();

		//first check if there is a pending request
		if (pendingRecomandation){
			if((_state == fal.shmd.HAL.HALExchange.ApplianceState.PROGRAMMED) && (this.pendingRecomandationCounter <= maxRetryNumber)){
				//the appliance is already programmed and is still not running GRRRR!
				//...so call it again;
				this.callDevice();
			}
			
			else  { //give it up or the pending request was successfully
				
				//first check if the appliance is finally not faithful...
				if (this.pendingRecomandationCounter > maxRetryNumber) {
					//O.K. this ist not good...we log an error...
					this.globalLogger.logError("Pening-Request-Error: the Appliance finally doesn't react on the requst!!!");
				}
				this.pendingRecomandation = false;
				this.pendingRecomandationCounter = 0;
				this.systemTimer.unregisterComponent(this);
				this.globalLogger.logDebug("Pending Miele-appliance recommandation succssesfully closed!");
			}
		}
		
	}

	@Override
	public void onNextTimePeriode() throws ControllerBoxException {
		this.processPendingCommand();
	}

	
}
