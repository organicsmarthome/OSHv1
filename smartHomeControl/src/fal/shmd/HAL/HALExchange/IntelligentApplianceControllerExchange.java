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


package fal.shmd.HAL.HALExchange;

import java.util.UUID;

import fal.smartHomeLib.HAL.Exchange.HALControllerExchange;

public class IntelligentApplianceControllerExchange extends
		HALControllerExchange {

	private ApplianceCommand applianceCommand;
	public ApplianceCommand getApplianceCommand() {
		return applianceCommand;
	}

	public void setApplianceCommand(ApplianceCommand applianceCommand) {
		this.applianceCommand = applianceCommand;
	}

	public IntelligentApplianceControllerExchange() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public UUID getDeviceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDeviceID(UUID deviceID) {
		// TODO Auto-generated method stub

	}


	
	

}
