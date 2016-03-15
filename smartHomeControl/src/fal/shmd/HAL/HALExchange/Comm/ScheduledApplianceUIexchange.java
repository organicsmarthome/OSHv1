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


package fal.shmd.HAL.HALExchange.Comm;

import java.util.ArrayList;
import java.util.UUID;

import fal.shmd.globalunits.Controller.ApplianceSchedule;
import fal.smartHomeLib.HAL.Exchange.Comm.HALCommExchange;


public class ScheduledApplianceUIexchange extends HALCommExchange {
	private ArrayList<ApplianceSchedule> currentApplianceSchedules;

	/**
	 * @return the currentApplianceSchedules
	 */
	public ArrayList<ApplianceSchedule> getCurrentApplianceSchedules() {
		return currentApplianceSchedules;
	}

	/**
	 * @param currentApplianceSchedules the currentApplianceSchedules to set
	 */
	public void setCurrentApplianceSchedules(
			ArrayList<ApplianceSchedule> currentApplianceSchedules) {
		this.currentApplianceSchedules = currentApplianceSchedules;
	} 
}
