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


package fal.smartHomeLib.HAL.Exchange;



import fal.smartHomeLib.CBTypes.Data.ApplianceState;
import fal.smartHomeLib.HAL.HALdeviceState;

/**
 *
 *the concrete implementation of the data exchange class for the HAL
 * here: the observer side
 * it's fitted with some properties where the author found, that it's necessary for each driver ;-)
 * when you find something special you can use the HALdeviceState or create a subclass extending this class
 *@author florian.allerding@kit.edu
 *@category smart-home ControllerBox HAL
 *@deprecated please use your own Exchange object (derived from HALExchange)
 */
public class HALobserverExchangeObject extends HALObserverExchange {
	

	private String applianceDetails;
	private ApplianceState applianceState;
	private HALdeviceState deviceState;
	private boolean isRunning;
	private int reactivePower;
	private int activePower;
	private boolean isActive;
	private boolean isOn;

	
	/**
	 * mean that the device is able to run, for ex. that it's filled up with laundry
	 * @return
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * @return the reactive power for electrical devices
	 */
	public int getActivePower() {
		return activePower;
	}

	public String getApplianceDetails() {
		return applianceDetails;
	}

	public ApplianceState getApplianceState() {
		return applianceState;
	}

	/**
	 * the detail state of a device depending of the device type
	 * it returns the abstract super class you should cast to a specific subclass you desire
	 * @return 
	 *  
	 */
	public HALdeviceState getDeviceState() {
		return deviceState;
	}

	public int getReactivePower() {
		return reactivePower;
	}
	
	/**
	 * mean that the device is operating like washing the dishes
	 * @return
	 */
	public boolean isRunning() {
		return isRunning;
	}
	public void setActivePower(int activePower) {
		this.activePower = activePower;
	}
	public void setApplianceDetails(String applianceDetails) {
		this.applianceDetails = applianceDetails;
	}
	public void setApplianceState(ApplianceState applianceState) {
		this.applianceState = applianceState;
	}
	/**
	 * the detail state of a device depending of the device type
	 * when you have a special device you should create a subclass first...
	 * @return 
	 *  
	 */
	public void setDeviceState(HALdeviceState deviceState) {
		this.deviceState = deviceState;
	}

	public void setReactivePower(int reactivePower) {
		this.reactivePower = reactivePower;
	}
	/**
	 * mean that the device is operating like washing the dishes
	 * @return
	 */
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	/**
	 * mean that the device is able to run, for ex. that it's filled up with laundry
	 * @return
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * mean that the device is powered-up (f.ex. when you switch on the power switch of you washing machine 
	 * or you plug-in you car on the charging-station)
	 * @param isOn
	 */
	public void setOn(boolean isOn) {
		this.isOn = isOn;
	}

	/**
	 * mean that the device is powered-up (f.ex. when you switch on the power switch of you washing machine 
	 * or you plug-in you car on the charging-station)
	 * @return
	 */
	public boolean isOn() {
		return isOn;
	}



}
