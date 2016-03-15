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

import fal.smartHomeLib.CBTypes.Configuration.System.DeviceClassification;
import fal.smartHomeLib.CBTypes.Configuration.System.DeviceTypes;
import fal.smartHomeLib.HAL.HALrealTimeDriver;


/**
 *
 * container class for the local observer and controller
 *@author florian.allerding@kit.edu
 *@category smart-home ControllerBox
 */
public class LocalOCUnit extends OCUnit {

	public LocalObserver localObserver;
	public LocalController localController;
	private final  UUID deviceID;
	private DeviceTypes deviceType;
	private DeviceClassification deviceClassification;
	
	
//	public LocalOCUnit(HALrealTimeDriver systemTimer, UUID deviceID){
//		
//		//create local controller/observer  and assign to the OCUnit 
//		this.localController = new LocalController(systemTimer);
//		this.localObserver = new LocalObserver(systemTimer);
//		this.localObserver.assignLocalOCUnit(this);
//		this.localController.assignLocalOCUnit(this);
//		this.systemTimer = systemTimer;
//		this.deviceID = deviceID;
//	}
	
	public void setDeviceClassification(DeviceClassification deviceClassification) {
		this.deviceClassification = deviceClassification;
	}


	public DeviceClassification getDeviceClassification() {
		return deviceClassification;
	}


	public void setDeviceType(DeviceTypes deviceType) {
		this.deviceType = deviceType;
	}


	public DeviceTypes getDeviceType() {
		return deviceType;
	}


	public LocalOCUnit(HALrealTimeDriver systemTimer, UUID deviceID, LocalController localController,
			LocalObserver localObserver){
		super(systemTimer);
		//create local controller/observer  and assign to the OCUnit 
		this.localController = localController;
		this.localObserver = localObserver;
		if (localObserver!=null){
			this.localObserver.assignLocalOCUnit(this);
		}
		if (localController!=null){
			this.localController.assignLocalOCUnit(this);
		}
		this.deviceID = deviceID;	
	}

	public UUID getDeviceID() {
		return deviceID;
	}








	
}
