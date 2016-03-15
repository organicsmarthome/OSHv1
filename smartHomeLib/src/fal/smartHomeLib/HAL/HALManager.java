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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.UUID;

import fal.smartHomeLib.CBGlobal.CBGlobal;
import fal.smartHomeLib.CBGlobal.ControllerBoxLifeCycle;
import fal.smartHomeLib.CBTypes.CBParameterCollection;
import fal.smartHomeLib.CBTypes.Configuration.System.AssignedCommDevice;
import fal.smartHomeLib.CBTypes.Configuration.System.AssignedDevice;
import fal.smartHomeLib.CBTypes.Configuration.System.HALConfiguration;
import fal.smartHomeLib.ControllerBoxCore.CBGlobalLogger;
import fal.smartHomeLib.ControllerBoxCore.CBLoggerCore;
import fal.smartHomeLib.ControllerBoxCore.ControllerBoxStatus;
import fal.smartHomeLib.ControllerBoxCore.Exception.ControllerBoxException;
import fal.smartHomeLib.ControllerBoxCore.Exception.ControllerBoxManagerException;
import fal.smartHomeLib.ControllerBoxCore.OC.LocalController;
import fal.smartHomeLib.ControllerBoxCore.OC.LocalObserver;
import fal.smartHomeLib.HAL.Exception.HALManagerException;

/**
 * represents the manager of the HAL-layer
 * 
 * @author florian.allerding@kit.edu
 *@category smart-home ControllerBox HAL
 * 
 */
public class HALManager extends CBGlobal implements ControllerBoxLifeCycle  {

	private ArrayList<HALdriver> connectedDevices;
	private ArrayList<HALCommDriver> connectedCommDriver;
	private HALrealTimeDriver realTimeDriver;
	private HALConfiguration halConfig;
	private HALDispatcher halDispatcher;
	private ControllerBoxStatus controllerBoxStatus;

	public HALrealTimeDriver getRealTimeDriver() {
		return realTimeDriver;
	}

	public ArrayList<HALdriver> getConnectedDevices() {
		return connectedDevices;
	}


	// if you want to do 'real' things ;-)
	public void loadConfiguration(HALConfiguration halConfig, ControllerBoxStatus controllerBoxStatus, boolean isSimulation)
			throws HALManagerException {
		this.globalLogger.printSystemMessage("...loading HAL configuration");
		this.halConfig = halConfig;
		//set the mode
		this.realTimeDriver.setSimulation(isSimulation);
		this.controllerBoxStatus = controllerBoxStatus;
		this.globalLogger.printSystemMessage("...setting up HAL-dispatcher");
		halDispatcher = this.loadHALDispatcher();
		this.globalLogger.printSystemMessage("...creating HAL-driver");
		this.processDriverConfiguration();
		this.globalLogger.printSystemMessage("...creating HAL-COMM-devices");
		this.processCommDeviceConfiguration();
		this.globalLogger.printSystemMessage("...HAL-layer is up!");
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void processCommDeviceConfiguration() throws HALManagerException {
		for (int i = 0; i < this.halConfig.getAssignedCommDevices().size(); i++){
			AssignedCommDevice _device = this.halConfig.getAssignedCommDevices().get(i);
			
			// load driver parameter
			CBParameterCollection drvParams = new CBParameterCollection();
			drvParams.loadCollection(_device.getDriverParameters());
			
			// get the class of the driver an make an instance
			
			Class driverClass = null;
			try {
				driverClass = Class.forName(_device.getDriverClassName());
			} catch (ClassNotFoundException ex) {
				throw new HALManagerException(ex);
			}
			
			HALCommDriver _driver = null;
			try {
				_driver = (HALCommDriver) driverClass.getConstructor(
						HALrealTimeDriver.class, UUID.class,
						CBParameterCollection.class).newInstance(
						realTimeDriver, UUID.fromString(_device.getDeviceID()),
						drvParams);
			} catch (Exception ex) {
				throw new HALManagerException(ex);
			}
			_driver.setCommDeviceType(_device.getDeviceType());
			_driver.setControllerBoxStatus(controllerBoxStatus);
			//assign the logger
			_driver.setGlobalLogger(this.globalLogger);
		
			connectedCommDriver.add(_driver);
			
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void processDriverConfiguration() throws HALManagerException {
		
		for (int i = 0; i < this.halConfig.getAssignedDevices().size(); i++) {

			AssignedDevice _device = this.halConfig.getAssignedDevices().get(i);

			// load driver parameter
			CBParameterCollection drvParams = new CBParameterCollection();
			drvParams.loadCollection(_device.getDriverParameters());

			// get the class of the driver an make an instance
			Class driverClass = null;
			try {
				driverClass = Class.forName(_device.getDriverClassName());
			} catch (ClassNotFoundException ex) {
				throw new HALManagerException(ex);
			}

			HALdriver _driver = null;
			try {
				_driver = (HALdriver) driverClass.getConstructor(
						HALrealTimeDriver.class, UUID.class,
						CBParameterCollection.class).newInstance(
						realTimeDriver, UUID.fromString(_device.getDeviceID()),
						drvParams);
			} catch (Exception ex) {
				throw new HALManagerException(ex);
			}

			_driver.setControllable(_device.isControllable());
			_driver.setObservable(_device.isObservable());
			_driver.setDeviceType(_device.getDeviceType());
			_driver.setDeviceClassification(_device.getDeviceClassification());
			_driver.setControllerBoxStatus(controllerBoxStatus);
			//assign the logger
			_driver.setGlobalLogger(this.globalLogger);
			// add driver to the list of connected devices
			connectedDevices.add(_driver);

			// assign the dispatcher
			_driver.assignDispatcher(halDispatcher);

			// get the class to the controller and the observer and refer it for
			// the cbox-layer
			if (_device.isControllable()) {
				// ...the controller class
				String controllerClassName = _device.getAssignedLocalOCUnit()
						.getLocalControllerClassName();

				try {
					_driver
					.setRequiredLocalControllerClass((Class<LocalController>) Class
					.forName(controllerClassName));

				} catch (ClassNotFoundException ex) {
					throw new HALManagerException(ex);
				}
				
				globalLogger.printSystemMessage("Driver loaded: -" + _driver.getClass().getSimpleName()+  "-  for device: "+ _device.getDeviceID() +" ...... [OK]");
			}

			if (_device.isObservable()) {
				// ...and the observer class
				String observerClassName = _device.getAssignedLocalOCUnit()
						.getLocalObserverClassName();

				try {
					_driver
					.setRequiredLocalObserverClass((Class<LocalObserver>) Class
					.forName(observerClassName));
				} catch (ClassNotFoundException ex) {
					throw new HALManagerException(ex);
				}
			}
		}
	}

	public void startHAL() {

	}

	public void addDevice(HALdriver driver, String deviceDescription) {

	}

	public void removeDevice(HALdriver driver) {

	}

	public HALManager() {
		connectedDevices = new ArrayList<HALdriver>();
		connectedCommDriver = new ArrayList<HALCommDriver>();
		// init realtime module
		realTimeDriver = new HALrealTimeDriver();
		
		

	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private HALDispatcher loadHALDispatcher() throws HALManagerException {
		HALDispatcher halDispatcher = null;
		//check if "isUsingHALdispatcher() != null" only for backward compatibility!
		if((this.halConfig.isUsingHALdispatcher() != null) && this.halConfig.isUsingHALdispatcher()) {
			Class dispatcherClass;
			try {
				dispatcherClass = Class.forName(halConfig.getHALdispatcherClassName());
			} catch (ClassNotFoundException ex) {
				throw new HALManagerException(ex);
			}
			
			try {
				
				halDispatcher = (HALDispatcher) dispatcherClass.getConstructor(CBGlobalLogger.class).newInstance(this.globalLogger);
				
			} catch (Exception ex) {
			
				throw new HALManagerException(ex);
			}
		}
		else {
			//for backward compatibility...
			halDispatcher = new HALDispatcher(this.globalLogger);
		}
		
		return halDispatcher;
	}

	public ArrayList<HALCommDriver> getConnectedCommDriver() {
		return connectedCommDriver;
	}

	@Override
	public void onSystemError() throws ControllerBoxException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSystemHalt() throws ControllerBoxException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSystemRunning() throws ControllerBoxException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSystemIsUp() throws ControllerBoxException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSystemResume() throws ControllerBoxException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSystemShutdown() throws ControllerBoxException {
		// TODO Auto-generated method stub
		
	}



}
