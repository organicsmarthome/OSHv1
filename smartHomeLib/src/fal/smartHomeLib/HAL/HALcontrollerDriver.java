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


package fal.smartHomeLib.HAL;

import fal.smartHomeLib.HAL.Exchange.HALControllerExchange;
import fal.smartHomeLib.HAL.Exchange.HALExchange;


/**
 * 
 * concrete driver part for between the local controller and the IDevice to change the IDevice's state
 *@author florian.allerding@kit.edu
 *@category smart-home ControllerBox HAL
 */

public class HALcontrollerDriver implements HALdataObject {

	private HALdriver assignedHALDriver;
	private HALControllerExchange controllerDataObject;
	
	
	public HALcontrollerDriver(HALdriver assignedHALDriver){
		this.assignedHALDriver = assignedHALDriver;
	}
	
	/**
	 * register the containerclass to which this element belongs...
	 * @param assignedHALDriver
	 */
	protected void registerMainDriver(HALdriver assignedHALDriver){
		this.assignedHALDriver = assignedHALDriver;
	}
	
	public void updateData(HALExchange controllerObject) {
		// TODO Auto-generated method stub
		
		if (controllerObject instanceof HALControllerExchange) {
			this.controllerDataObject = (HALControllerExchange) controllerObject;
		}
		changeDeviceState();
	
	}
	
	private void changeDeviceState(){
		this.assignedHALDriver.onControllerRequest(this.controllerDataObject);
	}


}
