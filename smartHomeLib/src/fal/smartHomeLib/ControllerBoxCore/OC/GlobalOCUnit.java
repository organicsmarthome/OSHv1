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


import java.util.HashMap;
import java.util.UUID;

import fal.smartHomeLib.HAL.HALrealTimeDriver;


/**
 *
 *containerclass for the virtual controllerbox-unit it represents the central controlling unit
 *@author florian.allerding@kit.edu
 *@category smart-home ControllerBox
 */
public class GlobalOCUnit extends OCUnit {

	private GlobalObserver observer;
	private GlobalController controller;
	//private ArrayList<LocalOCUnit> localUnits;
	private HashMap<UUID, LocalOCUnit> localUnits;
	

	public GlobalOCUnit(HALrealTimeDriver systemTimer, GlobalObserver globalObserver, GlobalController globalController){
		super(systemTimer);
		this.localUnits =  new HashMap<UUID, LocalOCUnit>();
		this.observer = globalObserver;
		this.controller = globalController;
		this.observer.assignControllerBox(this);
		this.controller.assignControllerBox(this);
		
	}
	public GlobalObserver getObserver() {
		return observer;
	}

	public GlobalController getController() {
		return controller;
	}
	
	public void registerLocalUnit(LocalOCUnit localunit){
		
		localUnits.put(localunit.getDeviceID(), localunit);
		
		//assign globalObserver
		localunit.localObserver.registerGlobalObserver(this.observer);
		
		//TODO
		//add to the List
	}
	protected HashMap<UUID, LocalOCUnit> getLocalUnits() {
		return localUnits;
	}



	
}