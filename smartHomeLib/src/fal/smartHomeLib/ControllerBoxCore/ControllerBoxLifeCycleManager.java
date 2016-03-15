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

import fal.smartHomeLib.CBGlobal.ControllerBoxLifeCycle;
import fal.smartHomeLib.CBGlobal.LifeCycleStates;
import fal.smartHomeLib.ControllerBoxCore.Exception.ControllerBoxException;
import fal.smartHomeLib.HAL.HALManager;

public class ControllerBoxLifeCycleManager {

	private ControllerBoxManager controllerBoxManager;
	private HALManager halManager;
	private LifeCycleStates currentState;
	private CBGlobalLogger globalLogger;
	
	public ControllerBoxLifeCycleManager(ControllerBoxManager controllerBoxManager, HALManager halManager ) {
		this.controllerBoxManager = controllerBoxManager;
		this.halManager = halManager;
		this.globalLogger = controllerBoxManager.getGlobalLogger();
		this.currentState = LifeCycleStates.ON_SYSTEM_INIT;
		
	}

	public void switchToLifeCycleState(LifeCycleStates nextState) throws ControllerBoxException{
		this.currentState = nextState;
		switch (nextState)
		{
			case ON_SYSTEM_RUNNING:
			{
				for(ControllerBoxLifeCycle boxLifeCycle: this.controllerBoxManager.getLifeCycleMembers()){
					boxLifeCycle.onSystemRunning();
				}
				this.globalLogger.printSystemMessage("...switching to SYSTEM_RUNNING");
				break;
			
			}
			case ON_SYSTEM_IS_UP:
			{
				for(ControllerBoxLifeCycle boxLifeCycle: this.controllerBoxManager.getLifeCycleMembers()){
					boxLifeCycle.onSystemIsUp();
				}
				this.globalLogger.printSystemMessage("...switching to SYSTEM_IS_UP");
				break;
			}
			case ON_SYSTEM_SHUTDOWN:
			{
				for(ControllerBoxLifeCycle boxLifeCycle: this.controllerBoxManager.getLifeCycleMembers()){
					boxLifeCycle.onSystemShutdown();
				}
				this.globalLogger.printSystemMessage("...switching to SYSTEM_SHUTDOWN");
				break;
			}
			case ON_SYSTEM_HALT:
			{
				for(ControllerBoxLifeCycle boxLifeCycle: this.controllerBoxManager.getLifeCycleMembers()){
					boxLifeCycle.onSystemHalt();
				}
				this.globalLogger.printSystemMessage("...switching to SYSTEM_HALT");
				break;
			}
			case ON_SYSTEM_RESUME:
			{
				for(ControllerBoxLifeCycle boxLifeCycle: this.controllerBoxManager.getLifeCycleMembers()){
					boxLifeCycle.onSystemResume();
				}
				this.globalLogger.printSystemMessage("...switching to SYSTEM_RESUME");
				break;
			}
			case ON_SYSTEM_ERROR:
			{
				for(ControllerBoxLifeCycle boxLifeCycle: this.controllerBoxManager.getLifeCycleMembers()){
					boxLifeCycle.onSystemError();
				}
				this.globalLogger.printSystemMessage("...switching to SYSTEM_ERROR");
				break;
			}
		}
			
	}

	/**
	 * @return the currentState
	 */
	protected LifeCycleStates getCurrentState() {
		return currentState;
	}
}
