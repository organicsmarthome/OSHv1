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


package fal.shmd.globalunits.Controller;

import java.util.HashMap;
import java.util.UUID;

import fal.smartHomeLib.ControllerBoxCore.Comm.CommAction;

public class SHMDCommActioDof implements CommAction {

	UUID actionCaller;
	private HashMap<UUID, Integer> deviceDegreeOfFreedom;
	
	public SHMDCommActioDof() {
		this.deviceDegreeOfFreedom = new HashMap<UUID, Integer>();
	}

	@Override
	public UUID getCausedByDeviceID() {
		// TODO Auto-generated method stub
		return this.actionCaller;
	}

	@Override
	public void setCausedByDeviceID(UUID deviceID) {
		this.actionCaller = deviceID;
		
	}
	
	public long getDegreeOfFreedomByDeviceId(UUID deviceId){
		return  this.deviceDegreeOfFreedom.get(deviceId).longValue();
	}
	
	public void addDeviceDof(UUID deviceId, int deviceDof) {
		this.deviceDegreeOfFreedom.put(deviceId, Integer.valueOf(deviceDof));
	}

	/**
	 * @param deviceDegreeOfFreedom the deviceDegreeOfFreedom to set
	 */
	public void setDeviceDegreeOfFreedom(
			HashMap<UUID, Integer> deviceDegreeOfFreedom) {
		this.deviceDegreeOfFreedom = deviceDegreeOfFreedom;
	}

	/**
	 * @return the deviceDegreeOfFreedom
	 */
	public HashMap<UUID, Integer> getDeviceDegreeOfFreedom() {
		return deviceDegreeOfFreedom;
	}

}
