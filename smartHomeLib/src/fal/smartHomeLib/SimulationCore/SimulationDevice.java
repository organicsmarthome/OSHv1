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
import fal.smartHomeLib.HAL.HALdriver;
import fal.smartHomeLib.HAL.HALrealTimeDriver;
import fal.smartHomeLib.SimulationCore.Exception.SimulationSubjectException;


/**
 * 
 * Superclass for simulation subjects like simulated appliances or smartmeters or ....
 * This class inherits from HALdriver. This is necessary for the capability of an integration into the controllerbox's HAL 
 * 
 *@author florian.allerding@kit.edu
 *@category smart-home ControllerBox Simulation
 * 
 */
public abstract class SimulationDevice extends HALdriver implements ISimulationSubject{

	
private ArrayList<SubjectAction> actions;

	private long currentTimeTick;
	private UUID globalId;
	private SimulationEngine simulationEngine;
	private final HALrealTimeDriver timeBase;
	private int ActivePower;
	private int ReactivePower;
	private int Current;
	//ctor
	public SimulationDevice(HALrealTimeDriver timerDriver, UUID deviceID,
			CBParameterCollection driverConfig) throws SimulationSubjectException {
		super(timerDriver, deviceID, driverConfig);
		actions = new ArrayList<SubjectAction>();
		timeBase = timerDriver;
		currentTimeTick = timeBase.getUnixTime();
		this.ActivePower = 0;
		this.ReactivePower = 0;
		this.Current = 0;
	}
	


	/**
	 * get the total active power consumption (For non-electrical device always '0')
	 * @return
	 */
	public int getActivePower() {
		return ActivePower;
	}


	/**
	 * set the total active power consumption (For non-electrical device always '0')
	 * @return
	 */
	public void setActivePower(int activePower) {
		ActivePower = activePower;
	}


	/**
	 * get the total reactive power consumption (For non-electrical device always '0')
	 * @return
	 */
	public int getReactivePower() {
		return ReactivePower;
	}


	/**
	 * set the total reactive power consumption (For non-electrical device always '0')
	 * @return
	 */
	public void setReactivePower(int reactivePower) {
		ReactivePower = reactivePower;
	}


	/**
	 * get the total current (For non-electrical device always '0')
	 * @return
	 */
	public int getCurrent() {
		return Current;
	}


	/**
	 * set the total current (For non-electrical device always '0')
	 * @return
	 */
	public void setCurrent(int current) {
		Current = current;
	}



	/**
	 * delete all actions from the list 
	 */
	public void flushActions(){
		actions.clear();
	}
	
	/**
	 * gets all actions for a subject
	 * @return
	 */
	public ArrayList<SubjectAction> getActions() {
		return actions;
	}
	
	
	/**
	 * Get another subject depending on this subject perhaps to tell him to do something...
	 * @param SubjectID
	 */
	public ISimulationSubject getAppendingSubject(UUID SubjectID){
		ISimulationSubject _simSubject = null;
		
		//ask the simulationengine...
		_simSubject = this.simulationEngine.getSimulationSubjectByID(SubjectID);
		
		return _simSubject;
	}

	/**
	 * is invoked on every (new) time tick to announce the subject
	 */
	public abstract void onNextTimeTick();
		
	/**
	 * @param nextAction
	 * is invoked when the subject has to do the action "nextAction"
	 */
	public abstract void performNextAction(SubjectAction nextAction);
	
	/**
	 * Sets an action for this simulation subject
	 * @param actions
	 */
	public void setAction(SubjectAction action) {
		actions.add(action);
	}
	
	public void setassignedSimulationEngine(SimulationEngine simulationEngine){
		this.simulationEngine = simulationEngine;
	}
	
	@Override
	public final void triggerSubject() {
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

	
	
	
	
	

}
