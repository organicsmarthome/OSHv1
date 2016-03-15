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


package fal.smartHomeLib.ControllerBoxCore.OC;

import fal.smartHomeLib.CBGlobal.ControllerBoxLifeCycle;
import fal.smartHomeLib.CBGlobal.IRealTimeObserver;
import fal.smartHomeLib.ControllerBoxCore.CBGeneral;
import fal.smartHomeLib.ControllerBoxCore.Exception.ControllerBoxException;
import fal.smartHomeLib.HAL.HALrealTimeDriver;

/**
 * abstract superclass for all controllers
 *@author florian.allerding@kit.edu
 *@category smart-home ControllerBox
 * 
 */
public abstract class Controller extends CBGeneral implements IRealTimeObserver, ControllerBoxLifeCycle {

	
	public Controller(HALrealTimeDriver systemTimer) {
		super(systemTimer);
		
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

	@Override
	public void onNextTimePeriode() throws ControllerBoxException {
		//...in case of use please override
		
	}
	



}
