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


package fal.smartHomeLib.ControllerBoxCore.Comm;

import java.util.ArrayList;
import java.util.UUID;

import fal.smartHomeLib.CBGlobal.ControllerBoxLifeCycle;
import fal.smartHomeLib.CBGlobal.IRealTimeObserver;
import fal.smartHomeLib.ControllerBoxCore.CBGeneral;
import fal.smartHomeLib.ControllerBoxCore.Exception.ControllerBoxException;
import fal.smartHomeLib.ControllerBoxCore.OC.GlobalController;
import fal.smartHomeLib.HAL.HALdataObject;
import fal.smartHomeLib.HAL.HALdataSubject;
import fal.smartHomeLib.HAL.HALrealTimeDriver;
import fal.smartHomeLib.HAL.Exchange.HALExchange;

/**
 * @author florian
 * Containerclass for the external communication with the Energy-Provider and/or for 
 * user-interactions
 */
public abstract class CommManager extends CBGeneral implements IRealTimeObserver,HALdataObject,HALdataSubject, ControllerBoxLifeCycle  {

	private GlobalController globalController;
	private ArrayList<HALdataObject> assignedCommDriver;
	public GlobalController getGlobalController() {
		return globalController;
	}

	public void setGlobalController(GlobalController globalController) {
		this.globalController = globalController;
	}



	public CommManager(HALrealTimeDriver systemTimer) {
		super(systemTimer);
		assignedCommDriver = new ArrayList<HALdataObject>();
		// TODO Auto-generated constructor stub
	}


	
	public void initCommManager(){

	}

	//from HALdataObject
	@Override
	public final void updateData(HALExchange exchangeObject) {
		receiveDataFromCommDevice(exchangeObject);
		
	}


	//from HALdataSubject
	@Override
	public final void registerUnit(HALdataObject monitorObject) {
		this.assignedCommDriver.add(monitorObject);
		
	}
	//from HALdataSubject
	@Override
	public final void removeUnit(HALdataObject monitorObject) {
		this.assignedCommDriver.remove(monitorObject);
		
	}
	//from HALdataSubject
	@Override
	public final void updateUnit(HALExchange halexchange) {
		//dont't know what to do
		
	}
	
	public abstract void receiveDataFromCommDevice(HALExchange halexchange);

	
	/**
	 * Invoked by a comm-driver to get some data
	 * @param commDeviceID
	 * @return
	 */
	public abstract HALExchange pollGlobalController(UUID commDeviceID);
	
	/**
	 * notify the global controller when f.a. a price signal has been received
	 * @param commAction
	 */
	public final void notifyGlobalController(CommAction commAction) {
		this.globalController.onCommAction(commAction);
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

	

}
