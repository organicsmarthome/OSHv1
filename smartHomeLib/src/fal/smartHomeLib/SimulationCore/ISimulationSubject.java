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

import fal.smartHomeLib.CBTypes.Simulation.Screenplay.SubjectAction;

/**
 *@author florian.allerding@kit.edu
 *@category smart-home ControllerBox Simulation
 *
 */
public interface ISimulationSubject {
	
	/**
	 * is invoked by the simulation Engine on every time tick to synchronize the subjects
	 */
	public void triggerSubject();
	
	/**
	 * delete all actions from the list 
	 */
	public void flushActions();
	
	/**
	 * Sets an action for this simulation subject
	 * @param actions
	 */
	public void setAction(SubjectAction action);
	
	/**
	 * gets all actions for a subject
	 * @return
	 */
	public ArrayList<SubjectAction> getActions();
	
	/**
	 * is invoked on every (new) time tick to announce the subject
	 */
	public void onNextTimeTick();
	
	/**
	 * @param nextAction
	 * is invoked when the subject has to do the action "nextAction"
	 */
	public void performNextAction(SubjectAction nextAction);
	
	public void setassignedSimulationEngine(SimulationEngine simulationEngine);

	public UUID getDeviceID();
	
	public ISimulationSubject getAppendingSubject(UUID SubjectID);

}
