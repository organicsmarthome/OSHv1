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


package fal.smartHomeLib.ControllerBoxCore.OC;


import java.util.UUID;

import fal.smartHomeLib.ControllerBoxCore.Comm.CommAction;
import fal.smartHomeLib.ControllerBoxCore.Comm.CommManager;
import fal.smartHomeLib.ControllerBoxCore.Data.ModelOfObservationExchange;
import fal.smartHomeLib.HAL.HALrealTimeDriver;


/**
 *Superclass for the global controller unit
 *
 *@author florian.allerding@kit.edu
 *@category smart-home ControllerBox
 *
 */
public abstract class GlobalController extends Controller {

	private CommManager commManager;
	private GlobalOCUnit assignedOCUnit;
	
	
	public GlobalController(HALrealTimeDriver systemTimer){
		super(systemTimer);
	}

	
	public void setCommManager(CommManager commManager) {
		this.commManager = commManager;
	}


	public CommManager getCommManager() {
		return commManager;
	}

	protected void assignControllerBox(GlobalOCUnit assignedOCUnit){
		this.assignedOCUnit = assignedOCUnit;
	}
	
	/**
	 * get a local controller unit from a specific local o/c-unit
	 * @param deviceID
	 * @return
	 */
	public LocalController getLocalController(UUID deviceID) {
		
		LocalController _localController = null;
		
		_localController = assignedOCUnit.getLocalUnits().get(deviceID).localController;
		
		return _localController;
	}
	
	/**
	 * return the according global observer unit
	 * @return
	 */
	public GlobalObserver getGlobalObserver(){
		return this.assignedOCUnit.getObserver();
	}
	
	/**
	 * For the communication between the observer and the controller
	 * The observer can invoke this method to get some observed data.
	 * Only an interface will be communicated, so feel free to create some own classes...
	 * @return
	 */
	public ModelOfObservationExchange getDataFromGlobalObserver(){
		return this.getGlobalObserver().getObservedModelData();
	}
	
	public abstract void onCommAction(CommAction commAction);
	
	
}
