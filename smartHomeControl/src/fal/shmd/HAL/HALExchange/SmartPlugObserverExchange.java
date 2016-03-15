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

import fal.smartHomeLib.HAL.Exchange.HALObserverExchange;

public class SmartPlugObserverExchange extends HALObserverExchange {

	private int activePower;
	private double current;
	private UUID deviceId;
	private int energy;
	private int reactivePower;
	private int switchState;
	private int voltage;
	private boolean switchable;
	public boolean isSwitchable() {
		return switchable;
	}
	public void setSwitchable(boolean switchable) {
		this.switchable = switchable;
	}
	public int getActivePower() {
		return activePower;
	}
	public double getCurrent() {
		return current;
	}
	public UUID getDeviceId() {
		return deviceId;
	}
	public int getEnergy() {
		return energy;
	}
	public int getReactivePower() {
		return reactivePower;
	}
	public int getSwitchState() {
		return switchState;
	}
	public int getVoltage() {
		return voltage;
	}
	public void setActivePower(int activePower) {
		this.activePower = activePower;
	}
	public void setCurrent(double current) {
		this.current = current;
	}
	public void setDeviceId(UUID deviceId) {
		this.deviceId = deviceId;
	}
	public void setEnergy(int energy) {
		this.energy = energy;
	}
	public void setReactivePower(int reactivePower) {
		this.reactivePower = reactivePower;
	}
	public void setSwitchState(int switchState) {
		this.switchState = switchState;
	}
	public void setVoltage(int voltage) {
		this.voltage = voltage;
	}

}
