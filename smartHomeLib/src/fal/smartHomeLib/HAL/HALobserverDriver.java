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




import fal.smartHomeLib.HAL.Exchange.HALExchange;


/**
 * 
 * concrete driver part between the IDevice and the local observer to update the IDevice current state to the local observer
 *@author florian.allerding@kit.edu
 *@category smart-home ControllerBox HAL
 */
public class HALobserverDriver implements HALdataSubject{

	private HALdataObject observerObject;
	private HALdriver assignedHALDriver;
	
	public HALobserverDriver(HALdriver assignedHALDriver){
		this.assignedHALDriver = assignedHALDriver;
	}
	
	/**
	 * register the containerclass to wich this element belongs...
	 * @param assignedHALDriver
	 */
	protected void registerMainDriver(HALdriver assignedHALDriver){
		this.assignedHALDriver = assignedHALDriver;
	}
	
	@Override
	public void registerUnit(HALdataObject observerObject) {

		this.observerObject = observerObject;
		
		
	}

	@Override
	public void removeUnit(HALdataObject observerObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUnit(HALExchange observerExchange) {

		this.observerObject.updateData(observerExchange);
		
		
	}



}
