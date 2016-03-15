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

import java.util.UUID;

import fal.smartHomeLib.ControllerBoxCore.Comm.CommAction;

public class SHMDCommActionSPS implements CommAction {

	private double[] priceSignal;
	private int lastSPSUpdateTimeStamp;
	/**
	 * @return the lastSPSUpdateTimeStamp
	 */
	public int getLastSPSUpdateTimeStamp() {
		return lastSPSUpdateTimeStamp;
	}
	/**
	 * @param lastSPSUpdateTimeStamp the lastSPSUpdateTimeStamp to set
	 */
	public void setLastSPSUpdateTimeStamp(int lastSPSUpdateTimeStamp) {
		this.lastSPSUpdateTimeStamp = lastSPSUpdateTimeStamp;
	}
	private UUID deviceID;
	@Override
	public UUID getCausedByDeviceID() {
		return this.deviceID;
	}
	@Override
	public void setCausedByDeviceID(UUID deviceID) {
		this.deviceID = deviceID;
	}
	public double[] getPriceSignal() {
		return priceSignal;
	}
	public void setPriceSignal(double[] priceSignal) {
		this.priceSignal = priceSignal;
	}
	

}
