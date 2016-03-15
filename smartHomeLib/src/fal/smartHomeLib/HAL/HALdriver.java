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

import java.util.UUID;

import fal.smartHomeLib.CBGlobal.CBGlobal;
import fal.smartHomeLib.CBGlobal.ControllerBoxLifeCycle;
import fal.smartHomeLib.CBGlobal.IRealTimeObserver;
import fal.smartHomeLib.CBTypes.CBParameterCollection;
import fal.smartHomeLib.CBTypes.Configuration.System.DeviceClassification;
import fal.smartHomeLib.CBTypes.Configuration.System.DeviceTypes;
import fal.smartHomeLib.ControllerBoxCore.ControllerBoxStatus;
import fal.smartHomeLib.ControllerBoxCore.Exception.ControllerBoxException;
import fal.smartHomeLib.ControllerBoxCore.OC.LocalController;
import fal.smartHomeLib.ControllerBoxCore.OC.LocalObserver;
import fal.smartHomeLib.HAL.Exchange.HALControllerExchange;
import fal.smartHomeLib.HAL.Exchange.HALExchange;
import fal.smartHomeLib.HAL.Exchange.HALObserverExchange;



/**
 * 
 *superclass for the driver between the controllerbox and the IDevice
 *each IDevice driver has to extend it!
 *
 *@author florian.allerding@kit.edu
 *@category smart-home ControllerBox HAL
 */
public abstract class HALdriver extends CBGlobal implements IRealTimeObserver, ControllerBoxLifeCycle {

	private boolean controllable;
	private DeviceClassification deviceClassification;
	private final UUID deviceID;
	private DeviceTypes deviceType;
	public CBParameterCollection driverConfig;
	protected  HALcontrollerDriver halcontrollerDriver;

	private HALDispatcher halDispatcher;
	protected  HALobserverDriver halobserverDriver;
	private boolean isIntelligent;
	private boolean observable;
	private Class<LocalController> requiredLocalControllerClass;
	private Class<LocalObserver> requiredLocalObserverClass;
	private ControllerBoxStatus controllerBoxStatus;

	protected  HALrealTimeDriver timerDriver;

	public HALdriver(HALrealTimeDriver timerDriver, UUID deviceID, CBParameterCollection driverConfig ) {
		this.timerDriver = timerDriver;
		halobserverDriver = new HALobserverDriver(this);
		halcontrollerDriver = new HALcontrollerDriver(this);
		//TODO: load UUID from file?!
		this.deviceID = deviceID;
		this.driverConfig = driverConfig;
	}

	public HALdriver(UUID deviceID, HALrealTimeDriver timerDriver,HALobserverDriver halobserverDriver,HALcontrollerDriver halcontrollerDriver, CBParameterCollection driverConfig) {
		this.timerDriver = timerDriver;
		this.halcontrollerDriver = halcontrollerDriver;
		this.halobserverDriver = halobserverDriver;
		//register the container object
		this.halcontrollerDriver.registerMainDriver(this);
		this.halobserverDriver.registerMainDriver(this);
		//TODO: load UUID from file?!
		this.deviceID = deviceID;
		this.driverConfig = driverConfig;
	}
	protected void assignDispatcher(HALDispatcher halDispatcher){
		this.halDispatcher = halDispatcher;
	}
	public DeviceClassification getDeviceClassification() {
		return deviceClassification;
	}
	
	public UUID getDeviceID() {
		return deviceID;
	}

	public DeviceTypes getDeviceType() {
		return deviceType;
	}

	public HALcontrollerDriver getHalcontrollerDriver() {
		return halcontrollerDriver;
	}

	public HALobserverDriver getHalobserverDriver() {
		return halobserverDriver;
	}


	public Class<LocalController> getRequiredLocalControllerClass() {
		return requiredLocalControllerClass;
	}
	
	public Class<LocalObserver> getRequiredLocalObserverClass() {
		return requiredLocalObserverClass;
	}
	
	public boolean isControllable() {
		return controllable;
	}
	
	public boolean isIntelligent() {
		return isIntelligent;
	}

	public boolean isObservable() {
		return observable;
	}



	/**
	 * Simplify the notification to the local observer
	 * @param observerExchange
	 */
	public void notifyObserver(HALObserverExchange observerExchange){
		
		this.halobserverDriver.updateUnit((HALExchange) observerExchange);
	}
	/**
	 * invoked from the local controller when the device has to something
	 * @param controllerRequest contains what the device has to do...
	 */
	protected abstract void onControllerRequest(HALControllerExchange controllerRequest);
	

	protected void setControllable(boolean controllable) {
		this.controllable = controllable;
	}


	protected void setDeviceClassification(DeviceClassification deviceClassification) {
		this.deviceClassification = deviceClassification;
	}


	protected void setDeviceType(DeviceTypes deviceType) {
		this.deviceType = deviceType;
	}


	public void setIntelligent(boolean isIntelligent) {
		this.isIntelligent = isIntelligent;
	}
	
	protected void setObservable(boolean observable) {
		this.observable = observable;
	}
	
	protected void setRequiredLocalControllerClass(
			Class<LocalController> requiredLocalControllerClass) {
		this.requiredLocalControllerClass = requiredLocalControllerClass;
	}
	
	protected void setRequiredLocalObserverClass(
			Class<LocalObserver> requiredLocalObserverClass) {
		this.requiredLocalObserverClass = requiredLocalObserverClass;
	}

	@Override
	public void onNextTimePeriode() throws ControllerBoxException {
		//...in case of use please override
		
	}

	@Override
	public void onSystemError() throws ControllerBoxException {
		//...in case of use please override
		
	}

	@Override
	public void onSystemHalt() throws ControllerBoxException {
		//...in case of use please override
		
	}

	@Override
	public void onSystemRunning() throws ControllerBoxException {
		//...in case of use please override
		
	}

	@Override
	public void onSystemIsUp() throws ControllerBoxException {
		//...in case of use please override
		
	}

	@Override
	public void onSystemResume() throws ControllerBoxException {
		//...in case of use please override
		
	}

	@Override
	public void onSystemShutdown() throws ControllerBoxException {
		//...in case of use please override
		
	}

	/**
	 * @param controllerBoxStatus the controllerBoxStatus to set
	 */
	protected void setControllerBoxStatus(ControllerBoxStatus controllerBoxStatus) {
		this.controllerBoxStatus = controllerBoxStatus;
	}

	/**
	 * @return the controllerBoxStatus
	 */
	public ControllerBoxStatus getControllerBoxStatus() {
		return controllerBoxStatus;
	}

	/**
	 * @return the halDispatcher
	 */
	public HALDispatcher getHalDispatcher() {
		return halDispatcher;
	}
	
}
