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


package fal.rsh.main;

import fal.smartHomeLib.ControllerBoxCore.ControllerBoxManager;
import fal.smartHomeLib.ControllerBoxCore.Exception.ControllerBoxManagerException;
import fal.smartHomeLib.SimulationCore.SimulationEngine;
import fal.smartHomeLib.SimulationCore.Exception.SimulationEngineException;

public class ControllerBoxLoader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//initialize the controllerbox
		ControllerBoxManager controllerBoxManager = new ControllerBoxManager();
		
		try {
			controllerBoxManager.changeLogLevel("DEBUG");
		} catch (ControllerBoxManagerException e) {
			controllerBoxManager.getGlobalLogger().logError("ERROR: changing loglevel",e);
			return;
		}
		
		try {
			controllerBoxManager.initControllerBox("configfiles/system/halconfig.xml", "configfiles/system/controllerboxconfig.xml");
		} catch (ControllerBoxManagerException e) {
			controllerBoxManager.getGlobalLogger().logError("ERROR: initializing ControllerBox", e);
			return;
		}
		
		
		
		//now everything should be up...
		
		controllerBoxManager.getGlobalLogger().printSystemMessage("Controllerbox is up...now starting system");
		
		try {
			controllerBoxManager.startSystem();
		} catch (ControllerBoxManagerException e) {
			controllerBoxManager.getGlobalLogger().logError("ERROR: switching runlevel to *running*", e);
		}
		

		




	}

}
