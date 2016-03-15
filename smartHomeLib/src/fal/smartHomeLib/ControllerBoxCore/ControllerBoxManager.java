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


package fal.smartHomeLib.ControllerBoxCore;



import java.util.ArrayList;
import java.util.UUID;

import fal.smartHomeLib.CBGlobal.CBGlobal;
import fal.smartHomeLib.CBGlobal.ControllerBoxLifeCycle;
import fal.smartHomeLib.CBGlobal.LifeCycleStates;
import fal.smartHomeLib.CBTypes.XMLSerialization;
import fal.smartHomeLib.CBTypes.Configuration.ControllerBox.ControllerBoxConfiguration;
import fal.smartHomeLib.CBTypes.Configuration.System.HALConfiguration;
import fal.smartHomeLib.ControllerBoxCore.Comm.CommManager;
import fal.smartHomeLib.ControllerBoxCore.Exception.ControllerBoxException;
import fal.smartHomeLib.ControllerBoxCore.Exception.ControllerBoxManagerException;
import fal.smartHomeLib.ControllerBoxCore.OC.GlobalController;
import fal.smartHomeLib.ControllerBoxCore.OC.GlobalOCUnit;
import fal.smartHomeLib.ControllerBoxCore.OC.GlobalObserver;
import fal.smartHomeLib.ControllerBoxCore.OC.LocalController;
import fal.smartHomeLib.ControllerBoxCore.OC.LocalOCUnit;
import fal.smartHomeLib.ControllerBoxCore.OC.LocalObserver;
import fal.smartHomeLib.HAL.HALCommDriver;
import fal.smartHomeLib.HAL.HALManager;
import fal.smartHomeLib.HAL.HALdriver;
import fal.smartHomeLib.HAL.HALrealTimeDriver;
import fal.smartHomeLib.SimulationCore.ISimulationSubject;





/**
 * for initialization an management of the controllerbox
 *@author florian.allerding@kit.edu
 *@category smart-home ControllerBox
 *
 */
public class ControllerBoxManager extends CBGlobal  {
	
	private ArrayList<HALCommDriver> commDrivers;
	private CommManager commManager;
	private GlobalOCUnit controllerBox;
	private UUID controllerBoxID;
	private ArrayList<HALdriver> deviceDrivers;
	private HALManager halManager;
	private ControllerBoxLifeCycleManager lifeCycleManager;
	private ArrayList<LocalOCUnit> localOCUnits;
	private ControllerBoxStatus controllerBoxStatus = null;
	
	/**
	 * standard ctor 
	 */
	public ControllerBoxManager() {
		
		//create a new Logger with default LogLevel: error
			
		this.globalLogger = new CBGlobalLogger();
	}
	
	/**
	 * Changes the current loglevel to a given level like "WARN", "ERROR" etc.
	 * @param LogLevel
	 */
	public void changeLogLevel(String logLevel) throws ControllerBoxManagerException{
		
			this.globalLogger.setLogLevel(logLevel);

	}
	
	/**
	 * creates local o/c-unit based on the driver information. Only for devices witch are at least 
	 * "Observable" such an instance will be created
	 * @param drivers
	 * @return
	 */
	private ArrayList<LocalOCUnit> createLocalUnits(ArrayList<HALdriver> drivers) throws ControllerBoxManagerException{
		
		ArrayList<LocalOCUnit> _localOCUnits = new ArrayList<LocalOCUnit>();
		
		this.globalLogger.printSystemMessage("...creating local units");
		
		for( HALdriver _driver: drivers)
		{
			
			//is this device able to be observer or controlled?
			//...then build an o/c-unit
			//otherwise do nothing ;-)
			LocalObserver localobserver = null;
			if (_driver.isObservable()) {
			
				//getting the class for the local oc unit
				
				
				
				try {
					localobserver = (LocalObserver) 
									_driver.getRequiredLocalObserverClass()
									.getConstructor(HALrealTimeDriver.class)
									.newInstance(this.halManager.getRealTimeDriver());
				} catch (Exception ex) {
					
					throw new ControllerBoxManagerException(ex);
				}
				
				//set the global status object
				localobserver.setControllerBoxStatus(controllerBoxStatus);
				//assign the logger
				localobserver.setGlobalLogger(this.globalLogger);
				
			}
			LocalController localcontroller = null;
			if (_driver.isControllable()){	
			
				
				try {
					localcontroller = 	(LocalController) 
										_driver.getRequiredLocalControllerClass()
										.getConstructor(HALrealTimeDriver.class)
										.newInstance(this.halManager.getRealTimeDriver());
				} catch (Exception ex) {
					
					throw new ControllerBoxManagerException(ex);
				} 
				
				//set the global status object
				localcontroller.setControllerBoxStatus(controllerBoxStatus);
				//assign the logger
				localcontroller.setGlobalLogger(this.globalLogger);
			}
				//init localunit and refer the realtime module 
				
				LocalOCUnit _localunit = new LocalOCUnit(this.halManager.getRealTimeDriver(),_driver.getDeviceID(),
														localcontroller, localobserver);
				//assign the type of the device an it's classification
				_localunit.setDeviceType(_driver.getDeviceType());
				_localunit.setDeviceClassification(_driver.getDeviceClassification());
			
				//register the local observer at the specific HAL-driver
				_driver.getHalobserverDriver().registerUnit(_localunit.localObserver);
				//do the same with the local controller
				//Before check if the controller is available (it's not available when the device is not controllable)
				if (_localunit.localController != null){
					_localunit.localController.registerUnit(_driver.getHalcontrollerDriver());
				}
				//add the new unit
				_localOCUnits.add(_localunit);
			
		}
		
		return _localOCUnits;
	}
	
	/**
	 * will shutdown the controllerbox and announce the components, that the system is going down...
	 */
	public void doSystemShutdown() throws ControllerBoxManagerException{
		//the local units
		try {
			this.lifeCycleManager.switchToLifeCycleState(LifeCycleStates.ON_SYSTEM_SHUTDOWN);
		}
		catch (Exception ex) {
			throw new ControllerBoxManagerException(ex);
		}
		this.globalLogger.printSystemMessage("ControllerBox is going down...");
	}
	
	/**
	 * get the unique ID for the controllerbox or the household (one controllerbox per household...))
	 * @return
	 */
	public UUID getControllerBoxID() {
		return controllerBoxID;
	}
	
	/**
	 * get the assigned drivers to the controllerbox
	 * @return
	 */
	public ArrayList<HALdriver> getDeviceDrivers() {
		return deviceDrivers;
	}

	/**
	 * get the HALManager of the cb 
	 * @return
	 */
	public HALManager getHalManager() {
		return halManager;
	}
	
	/**
	 * get all members of the lifecyle-process. Used by the lifecycle-manager
	 * @return
	 */
	protected ArrayList<ControllerBoxLifeCycle> getLifeCycleMembers(){
		
		ArrayList<ControllerBoxLifeCycle> boxLifeCycleMembers = new ArrayList<ControllerBoxLifeCycle>();
		
		for (HALdriver haldriver: this.deviceDrivers){
			boxLifeCycleMembers.add((ControllerBoxLifeCycle) haldriver);
		}
		
		for (HALCommDriver halCommDriver: this.commDrivers){
			boxLifeCycleMembers.add((ControllerBoxLifeCycle) halCommDriver);
		}
		
		for (LocalOCUnit localOCUnit: localOCUnits){
			
			if (localOCUnit.localObserver != null){
				boxLifeCycleMembers.add((ControllerBoxLifeCycle) localOCUnit.localObserver);
			}
			if (localOCUnit.localController != null){
				boxLifeCycleMembers.add((ControllerBoxLifeCycle) localOCUnit.localController);
			}
		}
		
		boxLifeCycleMembers.add((ControllerBoxLifeCycle) halManager);
		if (commManager != null){
			boxLifeCycleMembers.add((ControllerBoxLifeCycle) commManager);
		}
		boxLifeCycleMembers.add((ControllerBoxLifeCycle) controllerBox.getObserver());
		boxLifeCycleMembers.add((ControllerBoxLifeCycle) controllerBox.getController());
		
		return boxLifeCycleMembers;
	}

	/**
	 * Gets all simulation-enabled device-driver and comm-driver.
	 * Necessary for the simulation-engine to get the connection between the controllerbox and the simualtion-layer
	 * @return
	 */
	public ArrayList<ISimulationSubject> getSimulationSubjects(){
		
		ArrayList<ISimulationSubject> simulationSubjects = new ArrayList<ISimulationSubject>();
		
		//add the device driver:
		for(HALdriver haldriver: this.deviceDrivers){
			if (haldriver instanceof ISimulationSubject){
				simulationSubjects.add( (ISimulationSubject) haldriver);
			}
		}
		for (HALCommDriver commDriver: commDrivers){
			if (commDriver instanceof ISimulationSubject){
				simulationSubjects.add((ISimulationSubject) commDriver);
			}
		}
		
		return simulationSubjects;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initCommManager(ControllerBoxConfiguration controllerBoxConfig, GlobalController globalController) throws ControllerBoxManagerException{

		this.globalLogger.printSystemMessage("...setting up Comm-manager");
		//only assign a comm-manager if there are some comm-devices!
		if (!commDrivers.isEmpty()) {
			//assign Manager to hal layer driver
			commManager = null;
			//...creating a commManager
			Class commManagerClass;
			try {
				commManagerClass = Class.forName(controllerBoxConfig.getCommManagerClass());
				commManager = (CommManager)commManagerClass.getConstructor(HALrealTimeDriver.class).newInstance(halManager.getRealTimeDriver());
				globalController.setCommManager(commManager);
				commManager.setGlobalController(globalController);
				commManager.initCommManager();
			}
			catch (Exception ex) {
				throw new ControllerBoxManagerException(ex);
			}
			//set the global status object
			commManager.setControllerBoxStatus(controllerBoxStatus);
			//assign the logger
			commManager.setGlobalLogger(this.globalLogger);
			
			//register the commDevices...
			for(HALCommDriver commDriver: this.commDrivers){
				commDriver.registerUnit(commManager);
				commManager.registerUnit(commDriver);
			}
			
			//register the commanger @the global controller
		}
		
	
		
	}
	
	
	/**
	 * initialize the ControllerBox based on the given configuration objects 
	 * @param halConfig
	 * @param controllerBoxConfig
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void initControllerBox(HALConfiguration halConfig, ControllerBoxConfiguration controllerBoxConfig) throws ControllerBoxManagerException{
		
		
		//init global status object:
		this.controllerBoxStatus = new ControllerBoxStatus();
		
		this.globalLogger.printSystemMessage("...initializing ControllerBox");
		
		boolean runInSimulation;
		
		//set some status variables...
		this.controllerBoxStatus.setSimulation(controllerBoxConfig.isSimulation());

		//create a hal manager first
		this.halManager = new HALManager();
		//assign the logger
		this.halManager.setGlobalLogger(this.globalLogger);
		this.globalLogger.printSystemMessage("...setting up lifecyle-management");
		//init life-cycle-manager
		this.lifeCycleManager = new ControllerBoxLifeCycleManager(this, halManager);
		
		//assign the global status to the lifecycle-manager...
		this.controllerBoxStatus.setLifeCycleManager(lifeCycleManager);
		
		//init the hal
		try {
			this.globalLogger.printSystemMessage("...starting HAL-layer");
			halManager.loadConfiguration(halConfig, this.controllerBoxStatus, controllerBoxConfig.isSimulation());
		}
		catch (Exception ex) {
			throw new ControllerBoxManagerException(ex); 
		}
		//then start the controllerbox layer
		deviceDrivers = halManager.getConnectedDevices();
		commDrivers = halManager.getConnectedCommDriver();
		//create local OC-Unit connected with the specific HAL-driver
		localOCUnits = createLocalUnits(deviceDrivers);
		
		//load global o/c-unit
		controllerBoxConfig.getGlobalControllerClass();
		GlobalObserver globalObserver = null;
		GlobalController globalController = null;
		this.globalLogger.printSystemMessage("...creating global O/C-units");
		Class globalObserverClass = null;
		Class globalControllerClass = null;
		try {
			globalObserverClass = Class.forName(controllerBoxConfig.getGlobalObserverClass());
		} catch (Exception ex) {
			
			throw new ControllerBoxManagerException(ex);
		}
		
		try {
			globalControllerClass = Class.forName(controllerBoxConfig.getGlobalControllerClass());
		} catch (Exception ex) {
			
			throw new ControllerBoxManagerException(ex);
		}
		
		try {
			globalObserver = (GlobalObserver) 	globalObserverClass
												.getConstructor(HALrealTimeDriver.class)
												.newInstance(halManager.getRealTimeDriver());
		} catch (Exception ex) {
			throw new ControllerBoxManagerException(ex);
		} 
		
		try {
			globalController = (GlobalController)globalControllerClass
												.getConstructor(HALrealTimeDriver.class)
												.newInstance(halManager.getRealTimeDriver());
		} catch (Exception ex) {
			throw new ControllerBoxManagerException(ex);
		} 
		
		//set the global status object
		globalObserver.setControllerBoxStatus(controllerBoxStatus);
		globalController.setControllerBoxStatus(controllerBoxStatus);
		//assign the logger
		globalObserver.setGlobalLogger(this.globalLogger);
		globalController.setGlobalLogger(this.globalLogger);
		//init commManager
		initCommManager(controllerBoxConfig, globalController);
		
		//create global ControllerBox
		this.controllerBox = new GlobalOCUnit(halManager.getRealTimeDriver(), globalObserver, globalController);
		
		registerLocalUnits();
		
		//notify the units, that the system is up
		
		try {
			this.lifeCycleManager.switchToLifeCycleState(LifeCycleStates.ON_SYSTEM_IS_UP);
			this.globalLogger.printSystemMessage("...ControllerBox is up!");
		}
		catch (Exception ex) {
			throw new ControllerBoxManagerException(ex);
		}
		
		
	
	}

	/**
	 * initialize the ControllerBox based on the given configuration files 
	 * @param halConfig
	 * @param controllerBoxConfig
	 */
	public void initControllerBox(String halConfigFile, String controllerBoxConfigFile) throws ControllerBoxManagerException{
		
		//load from files:
		HALConfiguration halconfig = null;
		try {
			halconfig = (HALConfiguration)XMLSerialization.file2Unmarshal(halConfigFile, HALConfiguration.class);
		} catch (Exception ex) {
			this.globalLogger.logError("can't load HAL-configuration",ex);
		}
		ControllerBoxConfiguration cbConfig = null;
		try {
			 cbConfig = (ControllerBoxConfiguration)XMLSerialization.file2Unmarshal(controllerBoxConfigFile, ControllerBoxConfiguration.class);
		} catch (Exception ex) {
			this.globalLogger.logError("can't load ControllerBox-configuration",ex);
		} 
		
		this.initControllerBox(halconfig,cbConfig);
		
	}
	
	/**
	 * register local o/c-units at the controllerbox (global o/c-unit)
	 */
	private void registerLocalUnits(){
		
		
		for(LocalOCUnit _localunit: localOCUnits){
			controllerBox.registerLocalUnit(_localunit);
		}
	}

	
	/**
	 * set the unique ID for the controllerbox or the household (one controllerbox per household...))
	 * @param controllerBoxID
	 */
	public void setControllerBoxID(UUID controllerBoxID) {
		this.controllerBoxID = controllerBoxID;
	}
	
	/**
	 * Switch to life-cycle-state "error"
	 */
	public void setSystemError() throws ControllerBoxManagerException{
		try {
			this.lifeCycleManager.switchToLifeCycleState(LifeCycleStates.ON_SYSTEM_ERROR);
		} catch (ControllerBoxException ex) {
			throw new ControllerBoxManagerException(ex);
		}
	}
	
	/**
	 * Switch to life-cycle-state "halt"
	 */
	public void setSystemHalt() throws ControllerBoxManagerException{
		try {
			this.lifeCycleManager.switchToLifeCycleState(LifeCycleStates.ON_SYSTEM_HALT);
		} catch (ControllerBoxException ex) {
			throw new ControllerBoxManagerException(ex);
		}
	}
	
	
	/**
	 * Switch to life-cycle-state "resume"
	 */
	public void setSystemResume() throws ControllerBoxManagerException{
		try {
			this.lifeCycleManager.switchToLifeCycleState(LifeCycleStates.ON_SYSTEM_RESUME);
		} catch (ControllerBoxException ex) {
			throw new ControllerBoxManagerException(ex);
		}
	}
	
	/**
	 * Switch to life-cycle-state "running"
	 */
	public void setSystemRunning() throws ControllerBoxManagerException{
		try {
			this.lifeCycleManager.switchToLifeCycleState(LifeCycleStates.ON_SYSTEM_RUNNING);
		} catch (ControllerBoxException ex) {
			throw new ControllerBoxManagerException(ex);
		}
	}
	
	/**
	 * start the controllerbox in the "real smart-home mode"
	 */
	public void startSystem() throws ControllerBoxManagerException{
		
		Thread cbSystemThread = this.halManager.getRealTimeDriver();
		cbSystemThread.start();
		try {
			this.lifeCycleManager.switchToLifeCycleState(LifeCycleStates.ON_SYSTEM_RUNNING);
		} catch (ControllerBoxException ex) {
			throw new ControllerBoxManagerException(ex);
		}
		this.globalLogger.printSystemMessage("...System started");
		
	}
	

}
