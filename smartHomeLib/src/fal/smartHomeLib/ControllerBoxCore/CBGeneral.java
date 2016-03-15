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



import java.util.UUID;

import fal.smartHomeLib.CBGlobal.CBGlobal;
import fal.smartHomeLib.HAL.HALrealTimeDriver;

/**
 * Global superclass for all o/c-components at the controllerboxCore
 *@author florian.allerding@kit.edu
 *@category smart-home ControllerBox
 */
public abstract class CBGeneral extends CBGlobal {
	
	protected final HALrealTimeDriver systemTimer;
	private ControllerBoxStatus controllerBoxStatus;
	
	public CBGeneral(HALrealTimeDriver systemTimer){
		this.systemTimer = systemTimer;
	}

	public long getCurrentTimeFromTimeBase(){
		return this.systemTimer.getUnixTime();
	}
	
	public long getUnixTimeAtStartFromTimeBase(){
		
		return systemTimer.getUnixTimeAtStart();
	}
	
	public long getrunningSinceFromTimeBase() {
		return systemTimer.runningSince();
	}

	public ControllerBoxStatus getControllerBoxStatus() {
		return controllerBoxStatus;
	}

	public void setControllerBoxStatus(ControllerBoxStatus controllerBoxStatus) {
		this.controllerBoxStatus = controllerBoxStatus;
	}
	
	/**
	 * is invoked when a ConcurrentActionHandler, which is called from this Object, has done his job... 
	 * @param actionHandler
	 */
	public void onConcurrentActionPerformed(ConcurrentActionHandler actionHandler){
		
	}


	
}
