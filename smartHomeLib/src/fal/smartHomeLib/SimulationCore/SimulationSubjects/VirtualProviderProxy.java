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


package fal.smartHomeLib.SimulationCore.SimulationSubjects;

import java.util.ArrayList;
import java.util.UUID;

import fal.smartHomeLib.CBTypes.CBParameterCollection;
import fal.smartHomeLib.CBTypes.Simulation.Screenplay.SubjectAction;
import fal.smartHomeLib.ControllerBoxCore.Exception.ControllerBoxException;
import fal.smartHomeLib.HAL.HALrealTimeDriver;
import fal.smartHomeLib.HAL.Exchange.HALExchange;
import fal.smartHomeLib.SimulationCore.SimulationComm;
import fal.smartHomeLib.SimulationCore.SimulationEngine;
import fal.smartHomeLib.SimulationCore.Exception.SimulationSubjectException;



/**
 * 
 * Generates the virtual proxy for receiving external signals like pricing signals 
 * 
 *@author florian.allerding@kit.edu
 *@category smart-home ControllerBox Simulation
 * 
 *
 */
public class VirtualProviderProxy extends SimulationComm {

	public VirtualProviderProxy(HALrealTimeDriver timerDriver, UUID deviceID,
			CBParameterCollection driverConfig)
			throws SimulationSubjectException {
		super(timerDriver, deviceID, driverConfig);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onNextTimeTick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void performNextAction(SubjectAction nextAction) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void flushActions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<SubjectAction> getActions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAction(SubjectAction action) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setassignedSimulationEngine(SimulationEngine simulationEngine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void triggerSubject() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateDataFromCommManager(HALExchange exchangeObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNextTimePeriode() throws ControllerBoxException {
		// TODO Auto-generated method stub
		
	}














}
