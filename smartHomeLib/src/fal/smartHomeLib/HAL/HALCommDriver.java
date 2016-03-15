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
import fal.smartHomeLib.CBTypes.Configuration.System.CommDeviceTypes;
import fal.smartHomeLib.ControllerBoxCore.ControllerBoxStatus;
import fal.smartHomeLib.ControllerBoxCore.Comm.CommManager;
import fal.smartHomeLib.ControllerBoxCore.Exception.ControllerBoxException;
import fal.smartHomeLib.HAL.Exchange.HALExchange;

public abstract class HALCommDriver extends CBGlobal implements HALdataSubject, HALdataObject, IRealTimeObserver, ControllerBoxLifeCycle {

	private final HALrealTimeDriver timerDriver;
	private final UUID deviceID;
	private HALdataObject assignedCommManager;
	/**
	 * @return the assignedCommManager
	 */
	public HALdataObject getAssignedCommManager() {
		return assignedCommManager;
	}

	private CBParameterCollection driverConfig;
	private CommDeviceTypes commDeviceType;
	private ControllerBoxStatus controllerBoxStatus;
	@Override
	public final void registerUnit(HALdataObject monitorObject) {
		this.assignedCommManager = monitorObject;
		
	}

	@Override
	public final void removeUnit(HALdataObject monitorObject) {
		this.assignedCommManager = monitorObject;
		
	}

	/**
	 * @return the driverConfig
	 */
	public CBParameterCollection getDriverConfig() {
		return driverConfig;
	}

	/**
	 * @param driverConfig the driverConfig to set
	 */
	public void setDriverConfig(CBParameterCollection driverConfig) {
		this.driverConfig = driverConfig;
	}

	/**
	 * @return the timerDriver
	 */
	public HALrealTimeDriver getTimerDriver() {
		return timerDriver;
	}

	/* notify the commManager for changes
	 * @see fal.HAL.HALdataSubject#updateUnit(fal.HAL.Exchange.HALExchange)
	 */
	@Override
	public final void updateUnit(HALExchange halexchange) {
		this.assignedCommManager.updateData(halexchange);
		
	}

	public HALCommDriver(HALrealTimeDriver timerDriver, UUID deviceID, CBParameterCollection driverConfig) {
		
		this.timerDriver = timerDriver;
		this.deviceID = deviceID;
		this.driverConfig = driverConfig;
	}

	public CommDeviceTypes getCommDeviceType() {
		return commDeviceType;
	}

	public void setCommDeviceType(CommDeviceTypes commDeviceType) {
		this.commDeviceType = commDeviceType;
	}

	@Override
	public final void updateData(HALExchange exchangeObject) {
	 updateDataFromCommManager(exchangeObject);
	}
	
	public abstract void updateDataFromCommManager(HALExchange exchangeObject);
	
	public final HALExchange pollDataFromGlobalController(){
	
		CommManager _commMan = (CommManager) this.assignedCommManager;
		return _commMan.pollGlobalController(this.deviceID);
		
	}

	public UUID getDeviceID() {
		return deviceID;
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
	 * @return the controllerBoxStatus
	 */
	public ControllerBoxStatus getControllerBoxStatus() {
		return controllerBoxStatus;
	}

	/**
	 * @param controllerBoxStatus the controllerBoxStatus to set
	 */
	protected void setControllerBoxStatus(ControllerBoxStatus controllerBoxStatus) {
		this.controllerBoxStatus = controllerBoxStatus;
	}
	
	





}
