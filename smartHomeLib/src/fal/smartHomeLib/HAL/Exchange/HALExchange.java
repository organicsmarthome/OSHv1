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


/**
 * @author fal
 * @param abstract superclass for data exchange object between the controllerbox and the device driver
 * 
 */
package fal.smartHomeLib.HAL.Exchange;

import java.util.UUID;

/**
 * abstract class for the data object between the HAL and the controllerbox layer
 *@author florian.allerding@kit.edu
 *@category smart-home ControllerBox HAL
 *
 */
public interface HALExchange {
	
	public UUID getDeviceID();

	public void setDeviceID(UUID deviceID);
    
}
