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


package fal.shmd.main;

import fal.smartHomeLib.ControllerBoxCore.ControllerBoxManager;
import fal.smartHomeLib.ControllerBoxCore.Exception.ControllerBoxManagerException;
import fal.smartHomeLib.SimulationCore.SimulationEngine;
import fal.smartHomeLib.SimulationCore.Exception.SimulationEngineException;

public class runKITSmartHomeSimulation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//then initialize the controllerbox
		ControllerBoxManager controllerBoxManager = new ControllerBoxManager();
		
		try {
			controllerBoxManager.initControllerBox("configfiles/system/halconfig.xml", "configfiles/system/controllerBoxConfig.xml");
		} catch (ControllerBoxManagerException e) {
			e.printStackTrace();
			return;
		}
		
		//now everything should be up...
		
		//in case of simulation
		//load simulation
		System.out.println("sim started");
		
		fal.smartHomeLib.SimulationCore.SimulationEngine myEngine = null;
		try {
			myEngine = new SimulationEngine(controllerBoxManager.getSimulationSubjects());
		} catch (SimulationEngineException e) {
			e.printStackTrace();
			return;
		}
		
		//assign time base
		myEngine.assignTimerDriver(controllerBoxManager.getHalManager().getRealTimeDriver());
		
		try {
			//controllerBoxManager.changeLogLevel("INFO");
			controllerBoxManager.changeLogLevel("DEBUG");
		} catch (ControllerBoxManagerException e) {
			e.printStackTrace();
			return;
		}
		
		try {
			myEngine.loadSingleScreenplay("configfiles/simulation/screenplay.xml");
		} catch (SimulationEngineException e) {
			e.printStackTrace();
			return;
		}
		
		long simTime = System.currentTimeMillis();
	    try {
			//myEngine.runSimulation(5079965);
			myEngine.runSimulation(86400);
			//myEngine.runSimulation(507996);
			//myEngine.runSimulation(3600);
		} catch (SimulationEngineException e1) {
			e1.printStackTrace();
			return;
		}

		
		System.out.println("sim stopped");
		
		simTime = System.currentTimeMillis() - simTime;
		
		System.out.println("Simulation duration: " +simTime/1000 + " sec");
		
		//shutdown the controllerBox
		try {
			controllerBoxManager.doSystemShutdown();
		} catch (ControllerBoxManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
