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
import java.util.HashMap;
import java.util.UUID;


import fal.smartHomeLib.CBTypes.XMLSerialization;
import fal.smartHomeLib.CBTypes.Simulation.Screenplay.Screenplay;
import fal.smartHomeLib.HAL.HALdriver;
import fal.smartHomeLib.HAL.HALrealTimeDriver;
import fal.smartHomeLib.HAL.Exception.HALException;
import fal.smartHomeLib.SimulationCore.Exception.SimulationEngineException;
import fal.smartHomeLib.SimulationCore.SimulationSubjects.VirtualGenericAppliance;



/**
 * Simulation engine for the smart-home-lab
 *@author florian.allerding@kit.edu
 *@category smart-home ControllerBox Simulation
 * 
 */
public class SimulationEngine {
	private ArrayList<ISimulationSubject> simulationSubjects;
	private HashMap<UUID, ISimulationSubject> simulationMap;
	private HALrealTimeDriver timerdriver;
	private int currentSimulationTick;
	
	
	/**
	 * get a simulationSubject by his (device) ID.
	 * This can be called from another subject to get an appending subject
	 * @param subjectID
	 * @return
	 */
	protected ISimulationSubject getSimulationSubjectByID(UUID subjectID){
		
		ISimulationSubject _simSubj = null;
		
		_simSubj = simulationMap.get(subjectID);
		
		return _simSubj;
		
	}
	
	
	/**
	 * ctor with a given array of devices to simulate...yes everything is a device!
	 * @param deviceList
	 */
	public SimulationEngine(ArrayList<ISimulationSubject> deviceList) throws SimulationEngineException {
		
		this.simulationSubjects = new ArrayList<ISimulationSubject>();
		
		this.simulationMap = new HashMap<UUID, ISimulationSubject>();
		
		//cast to simulation subject
		try {
		for(ISimulationSubject _simSubj:deviceList){
						
				//assign the simulation engine
				_simSubj.setassignedSimulationEngine(this);
			
				this.simulationSubjects.add(_simSubj);
				//do the same for the hashmap (better direct Acces)
				this.simulationMap.put(_simSubj.getDeviceID(),_simSubj);			
			
		}	
		}
		catch (Exception ex){
			throw new SimulationEngineException(ex);
			
		}	

	}

	public void assignTimerDriver(HALrealTimeDriver timerDriver){
		this.timerdriver = timerDriver;
	}

	/**
	 * load the actions for the devices from a screenplay-object for a timespan
	 * @param currentDay
	 */
	public void loadSingleScreenplay(Screenplay currentDay){
		
		for(ISimulationSubject _simSubj:simulationSubjects){
			
			for(int i = 0; i < currentDay.getSIMActions().size(); i++){

				//Search for an action for a specific device
				if (currentDay.getSIMActions().get(i).getDeviceID().compareTo(_simSubj.getDeviceID().toString()) == 0)
				{
//					SubjectAction _deviceAction = new SubjectAction();
//					_deviceAction.setTimeTick(currentDay.getSIMActions().get(i).getTick());
//					_deviceAction.setNextState(currentDay.getSIMActions().get(i).isNextState());
         			_simSubj.setAction(currentDay.getSIMActions().get(i));
					

				}
			}
		}
		
		
	}

	/**
	 * @param load the actions for the devices for a timespan or a cycle from a file
	 */
	public void loadSingleScreenplay(String screenPlaySource) throws SimulationEngineException{
		
		Screenplay currentDay;

		try{
		
			currentDay = (Screenplay)XMLSerialization.file2Unmarshal(screenPlaySource, Screenplay.class);
		
		}
		catch (Exception ex){
			//CBLogger.root_logger.error("Error occured while loading screenplayfile: " +ex.getMessage());
			currentDay = null;
			throw new SimulationEngineException(ex);
			
		}
		
		this.loadSingleScreenplay(currentDay);
		
	}
	
	/**
	 * You can load more than one day at the same time
	 * @param screenplays: An Arraylist of the one-day-screenplay
	 * @param ticksPerDay: The duration of a day
	 * @param resetTimerAfterEachDay: you want to do this?
	 */
	public void runMultipleDaySimulation(ArrayList<Screenplay> screenplays, int ticksPerDay, boolean resetTimerAfterEachDay) throws SimulationEngineException{
		
		for(Screenplay _screenplay:screenplays){
			
			this.loadSingleScreenplay(_screenplay);
			
			runSimulation(ticksPerDay);
			
			if (resetTimerAfterEachDay){
				this.resetSimulationTimer();
			}
			
		}
		
	}
	
	/**
	 * simulate the next timeTick an will increment the realtime driver 
	 * @param currentTick
	 * @throws SimulationEngineException
	 */
	private void simulateNextTimeTick(int currentTick) throws SimulationEngineException {
		
		//update realtimedriver
		try {
			this.timerdriver.updateTimer(currentTick);
		} catch (HALException ex) {
			throw new SimulationEngineException(ex);
		}
		
		int totalActivePower = 0;
		int totalReactivePower = 0;
		SimulationSmartMeter smartMeter = null;
		
		for(ISimulationSubject _simSubject:simulationSubjects){
			
			//general
			_simSubject.triggerSubject();
			
			//looking for some special devices
			//for virtual appliances
			if(_simSubject instanceof SimulationDevice){
				SimulationDevice _vdevice = (SimulationDevice)_simSubject;
				
				totalActivePower = _vdevice.getActivePower() + totalActivePower;
				totalReactivePower = _vdevice.getReactivePower() + totalReactivePower;
			}

			//for smart-meters
			if (_simSubject instanceof SimulationSmartMeter){
				smartMeter = (SimulationSmartMeter)_simSubject;
			}
			
		
		}
		
		//update the smart meter
		if (smartMeter != null){
			smartMeter.updateActivePower(totalActivePower);
			smartMeter.updateReactivePower(totalReactivePower);
		}
	}
	

	/**
	 * start the simulation based on an external clock
	 * @throws SimulationEngineException 
	 */
	public void runSimulationByExternalClock(int startTime) throws SimulationEngineException {
		
		//update realtimedriver
		try {
			this.timerdriver.updateTimer(startTime);
		} catch (HALException ex) {
			throw new SimulationEngineException(ex);
		}
		
	}
	
	/**
	 * Trigger the simulation by an external clock to simulate the next step
	 * @throws SimulationEngineException
	 */
	public void triggerEngine() throws SimulationEngineException{
		
		//next tick
		++this.currentSimulationTick;

		//simulate it
		simulateNextTimeTick(currentSimulationTick);
	}
	

	/**
	 * start the simulation with a given numberOfTicks based on the internal clock
	 * @param numberOfTicks
	 * @throws SimulationEngineException
	 */
	public void runSimulation(int numberOfTicks) throws SimulationEngineException{
		//provisionally logging
		String messages = "";
		
		for (int currentTick = 0; currentTick < numberOfTicks; currentTick++ )
		{
			simulateNextTimeTick(currentTick);
		}
	
	}

	/**
	 * Reset the simulation timer to zero. Necessary when you want to run several simulations
	 * beginning at the same sart time '0'
	 * @throws SimulationEngineException 
	 */
	public void resetSimulationTimer() throws SimulationEngineException {
		try {
			this.timerdriver.updateTimer(0);
		} catch (HALException ex) {
			throw new SimulationEngineException(ex);
		}
	}
	
	/**
	 * You can set a specific start time for the simulation.
	 * Normally you don't need that
	 * @param startTimeTick
	 * @throws SimulationEngineException 
	 */
	public void setSimulationTimerTo(int startTimeTick) throws SimulationEngineException{
		try {
			this.timerdriver.updateTimer(startTimeTick);
		} catch (HALException ex) {
			throw new SimulationEngineException(ex);
		}
	}

}
