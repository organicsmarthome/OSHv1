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


import fal.smartHomeLib.ControllerBoxCore.Data.ObserverExchange;
import fal.smartHomeLib.HAL.HALdataObject;
import fal.smartHomeLib.HAL.HALrealTimeDriver;
import fal.smartHomeLib.HAL.Exchange.HALExchange;
import fal.smartHomeLib.HAL.Exchange.HALObserverExchange;


/**
 * 
 *superclass for the local observer at the controllerbox
 * 
 * @author florian.allerding@kit.edu
 *@category smart-home ControllerBox
 */
public abstract class LocalObserver extends Observer implements HALdataObject {
	private LocalOCUnit assignedOCUnit;
	private GlobalObserver globalobserver;
	// current representation of the state from the observed device
	private HALObserverExchange observerDataObject;
	


	public LocalObserver(HALrealTimeDriver systemTimer) {
		super(systemTimer);
	}

	protected void assignLocalOCUnit(LocalOCUnit localOCUnit) {
		this.assignedOCUnit = localOCUnit;
		// this.deviceID = this.assignedOCUnit.getDeviceID();
	}

	public LocalOCUnit getAssignedOCUnit() {
		return assignedOCUnit;
	}
	
	/**
	 * returns the local controllerUnit according to this observer
	 * @return
	 */
	public LocalController getLocalController() {
		return this.getAssignedOCUnit().localController;
	}

	public UUID getDeviceID() {

		return this.assignedOCUnit.getDeviceID();
	}
	

	/**
	 * get the current representation of the state from the observed device
	 * 
	 * @return
	 */
	public HALExchange getObserverDataObject() {
		return observerDataObject;
	}

	public HALrealTimeDriver getSystemTimer() {
		return systemTimer;
	}

	/**
	 * is invoked everytime when the state of the device changes
	 */
	public abstract void onDeviceStateUpdate();

	protected void registerGlobalObserver(GlobalObserver observer) {
		this.globalobserver = observer;
	}

	@Override
	public final void updateData(HALExchange exchangeObject) {
		// cast to the observer object
		this.observerDataObject = (HALObserverExchange) exchangeObject;
		this.onDeviceStateUpdate();

	}

	/**
	 * tells the global observer that something has changed
	 */
	public final void updateGlobalObserver() {
		this.globalobserver.updateDeviceData(getDeviceID());
	}
	
	/**
	 * is invoked when the global Observer poll the local unit
	 * now it's on you to decide, what you want to do...
	 * @return
	 */
	public abstract ObserverExchange pollObserver();
		


}
