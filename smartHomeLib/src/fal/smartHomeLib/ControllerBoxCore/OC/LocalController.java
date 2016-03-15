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

import fal.smartHomeLib.ControllerBoxCore.Data.ControllerExchange;
import fal.smartHomeLib.ControllerBoxCore.Data.ModelOfObservationExchange;
import fal.smartHomeLib.HAL.HALdataObject;
import fal.smartHomeLib.HAL.HALdataSubject;
import fal.smartHomeLib.HAL.HALrealTimeDriver;
import fal.smartHomeLib.HAL.Exchange.HALExchange;


/**
 *superclass for the local controller
 *@author florian.allerding@kit.edu
 *@category smart-home ControllerBox
 */
public abstract class LocalController extends Controller implements HALdataSubject {
	
	private LocalOCUnit assignedOCUnit;

	private HALdataObject monitorObject;
	private UUID deviceID;
	
	public LocalController(HALrealTimeDriver systemTimer){
		super(systemTimer);
	}

	
	/**
	 * get the local o/c-unit to which thing controller belongs...
	 * @return
	 */
	public final LocalOCUnit getAssignedOCUnit() {
		return assignedOCUnit;
	}
	
	/**
	 * returns the local observerUnit according to this controller
	 * @return
	 */
	public final LocalObserver getLocalObserver(){
		return this.getAssignedOCUnit().localObserver;
	}

	/**
	 * For the communication between the observer and the controller
	 * The observer can invoke this method to get some observed data.
	 * Only an interface will be communicated, so feel free to create some own classes...
	 * @return
	 */
	public ModelOfObservationExchange getDataFromLocalObserver(){
		return this.getLocalObserver().getObservedModelData();
	}
	
	@Override
	public void registerUnit(HALdataObject monitorObject) {
		this.monitorObject = monitorObject;
		
	}

	@Override
	public final void removeUnit(HALdataObject monitorObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public final void updateUnit(HALExchange halexchange) {
		// TODO Auto-generated method stub
		this.monitorObject.updateData(halexchange);
		
	}
	
	protected final void assignLocalOCUnit(LocalOCUnit localOCUnit){
		this.assignedOCUnit = localOCUnit;
		this.deviceID = this.assignedOCUnit.getDeviceID();
		
	}

	public UUID getDeviceID() {
		return deviceID;
	}
	
	/**
	 * is invoked when the local unit has to do something...
	 * @param controllerExchange
	 */
	public abstract void recommandUnit(ControllerExchange controllerExchange);
	
	
	




}
