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


package fal.smartHomeLib.SimulationCore;

import java.util.ArrayList;
import java.util.UUID;

import fal.smartHomeLib.CBTypes.CBParameterCollection;
import fal.smartHomeLib.CBTypes.Simulation.Screenplay.SubjectAction;
import fal.smartHomeLib.HAL.HALCommDriver;
import fal.smartHomeLib.HAL.HALrealTimeDriver;
import fal.smartHomeLib.HAL.Exchange.HALExchange;
import fal.smartHomeLib.SimulationCore.Exception.SimulationSubjectException;

public abstract class SimulationComm extends HALCommDriver implements ISimulationSubject {

	private ArrayList<SubjectAction> actions;
	private SimulationEngine simulationEngine;
	private final HALrealTimeDriver timeBase;
	private long currentTimeTick;
	@Override
	public void flushActions() {
		actions.clear();
		
	}

	@Override
	public ArrayList<SubjectAction> getActions() {
		return actions;
	}

	@Override
	public void setAction(SubjectAction action) {
		actions.add(action);
		
	}

	@Override
	public ISimulationSubject getAppendingSubject(UUID SubjectID) {
		ISimulationSubject _simSubject = null;
		
		//ask the simulationengine...
		_simSubject = this.simulationEngine.getSimulationSubjectByID(SubjectID);
		
		return _simSubject;
	}

	@Override
	public void setassignedSimulationEngine(SimulationEngine simulationEngine) {
		this.simulationEngine = simulationEngine;
		
	}

	@Override
	public void triggerSubject() {
		currentTimeTick = timeBase.getUnixTime();
		
		//invoke for announcement...
		onNextTimeTick();
		
		//if we have something to do...
		//...check if you have to perfom an action for this timetick
		
		if (!actions.isEmpty()){
			if(actions.get(0).getTick() == currentTimeTick){
				performNextAction(actions.get(0));
				actions.remove(0);
			}
		}
		
	}



	public SimulationComm(HALrealTimeDriver timerDriver, UUID deviceID,
			CBParameterCollection driverConfig)
			throws SimulationSubjectException {
		super(timerDriver, deviceID, driverConfig);
		actions = new ArrayList<SubjectAction>();
		timeBase = timerDriver;
		currentTimeTick = timeBase.getUnixTime();
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
	public final UUID getDeviceID() {
		// TODO Auto-generated method stub
		return super.getDeviceID();
	}

	@Override
	public void updateDataFromCommManager(HALExchange exchangeObject) {
		// TODO Auto-generated method stub
		
	}


}
