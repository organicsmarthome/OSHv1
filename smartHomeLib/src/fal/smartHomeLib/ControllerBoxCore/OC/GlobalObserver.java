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

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import fal.smartHomeLib.ControllerBoxCore.Data.ObserverExchange;
import fal.smartHomeLib.HAL.HALrealTimeDriver;


/**
 * represents the observer of the global o/c-unit
 *@author florian.allerding@kit.edu
 *@category smart-home ControllerBox
 *
 */
public abstract class GlobalObserver extends Observer{
	
	private GlobalOCUnit assignedOCUnit;
	
	public GlobalObserver(HALrealTimeDriver systemTimer){
		super(systemTimer);
	}
	
	/**
	 * assign the controllerbox o/c-unit
	 * @param assignedOCUnit
	 */
	protected void assignControllerBox(GlobalOCUnit assignedOCUnit){
		this.assignedOCUnit = assignedOCUnit;
	}

	/**
	 * gets a local observer based on it's id
	 * @param deviceID
	 * @return
	 */
	public LocalObserver getLocalObserver(UUID deviceID){
		
		LocalObserver _localObserver = null;
		_localObserver = assignedOCUnit.getLocalUnits().get(deviceID).localObserver;
		return _localObserver;	
	}	
	
	/**
	 * Returns all assigned local Observer
	 * @return
	 */
	public ArrayList<LocalObserver> getAllLocalObservers(){
		
		ArrayList<LocalObserver> _localObserver = new ArrayList<LocalObserver>();
		Collection<LocalOCUnit> _ocCollection = assignedOCUnit.getLocalUnits().values();
		ArrayList<LocalOCUnit>  _localOCUnits = new ArrayList<LocalOCUnit>();
		_localOCUnits.addAll(_ocCollection);
		
		for (int i = 0; i < _localOCUnits.size(); i++){
			_localObserver.add(_localOCUnits.get(i).localObserver);
		}
		
		return _localObserver;
	}
	
	/**
	 * returns the list of all ids form the assignd devices/local units
	 * @return
	 */
	public ArrayList<UUID> getAssigndDeviceIDs(){
		ArrayList<UUID> _devIDs = new ArrayList<UUID>();
		
		for (int i = 0; i < _devIDs.size(); i++){
			_devIDs.add((UUID) assignedOCUnit.getLocalUnits().keySet().toArray()[i]);
		}
		return _devIDs;
	}
	
	public void updateDeviceData(UUID deviceID) {
		
		this.onDeviceUpdate(deviceID);
	}
	
	public void updateDeviceData(ObserverExchange observerExchange){
		
	}
	
	/**
	 * is invoked every time when a local unit post a new exchange object
	 * @param observerExchange
	 */
	protected abstract void onLocalUnitPost(ObserverExchange observerExchange);
	
	/**
	 * You can also poll a local unit. It'll return it's state using a ObserverExchang object
	 * @param deviceId
	 * @return
	 */
	protected ObserverExchange pollLocalUnit(UUID deviceId){
		
		return null;
	}
	
	/**
	 * notify about the changed state of a local oc unit with the id 
	 * @param deviceID
	 */
	public abstract void onDeviceUpdate(UUID deviceID);
	
	

	

}
