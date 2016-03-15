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


package fal.smartHomeLib.HAL;

import java.util.ArrayList;

import fal.smartHomeLib.CBGlobal.IRealTimeObserver;
import fal.smartHomeLib.ControllerBoxCore.Exception.ControllerBoxException;
import fal.smartHomeLib.HAL.Exception.HALException;

/**
 * 
 *class for accessing the realtime clock in the lab scenario and for accessing the ticks in the simulation...
 *time base is Unix-time ! ! ! 
 *@author florian.allerding@kit.edu
 *@category smart-home ControllerBox HAL
 */

public class HALrealTimeDriver extends Thread {
	
	private boolean isSimulation;
	private long timeTick;
	private long unixTimeAtStart;
	private long timeIncrement;
	
	//list of the registered components for time change announcement
	private ArrayList<HALrealTimeComponentRegister> registedComponents;
	
	/**
	 * you can restister a component on the realtimedriver. So with the given frequency a component 
	 * will be announced that the given timeperiod is over. You can also unregister a component (method: unregisterComponent(IRealTimeObserver iRealTimeObserver) )  
	 * @param iRealTimeObserver
	 * @param refreshFrequency
	 */
	public void registerComponent(IRealTimeObserver iRealTimeObserver, long refreshFrequency){
		
		
		if(!isComponentRegistered(iRealTimeObserver)){
			HALrealTimeComponentRegister _reg = new HALrealTimeComponentRegister();
			_reg.refreshPeriod = refreshFrequency;
			_reg.iRealTimeObserver = iRealTimeObserver;
			_reg.currentPeriodTick = 0;
			registedComponents.add(_reg);
		}
	}
	
	private boolean isComponentRegistered(IRealTimeObserver iRealTimeObserver){
		
		for (HALrealTimeComponentRegister _component:registedComponents){
			if (_component.iRealTimeObserver == iRealTimeObserver){
				return true;
			}
		}
		
		return false;
		
	}
	
	public void unregisterComponent(IRealTimeObserver iRealTimeObserver){
		HALrealTimeComponentRegister removeCandidate = null;
		for (HALrealTimeComponentRegister _component:registedComponents){
			if (_component.iRealTimeObserver == iRealTimeObserver){
				removeCandidate = _component;
			}
		}
		if (removeCandidate != null) {
			registedComponents.remove(removeCandidate);
		}
		
	}
	
	private void handleRegisterdComponents() throws HALException{
		
		//for(HALrealTimeComponentRegister _reg:this.registedComponents){
		for (int i = 0; i <this.registedComponents.size(); i++){
			HALrealTimeComponentRegister _reg = this.registedComponents.get(i);
			if(_reg.currentPeriodTick >= _reg.refreshPeriod ){
				//when the period is over announce the appliance
				try {
					_reg.iRealTimeObserver.onNextTimePeriode();
				} catch (ControllerBoxException ex) {
					throw new HALException(ex);
				}
				//reset period
				_reg.currentPeriodTick = 0;
			}
			else{
				//increment currentPeriodTick
				_reg.currentPeriodTick = _reg.currentPeriodTick +timeIncrement;
			}
		}
		
		
	}
	
	//do some voodoo
	
	public HALrealTimeDriver(){
		//overwrite start-time for the old simulation environment
		this(true, 1, 0);
	}
	
	public HALrealTimeDriver(boolean simulation, long timeIncrement, long forcedStartTime){
		this(simulation, timeIncrement);
		this.unixTimeAtStart = forcedStartTime;
	}
	
	public HALrealTimeDriver(boolean simulation, long timeIncrement){
		this.isSimulation = simulation;
		if (!isSimulation) {
			new Thread(this).start();
			unixTimeAtStart = System.currentTimeMillis() / 1000L;
		}
		
		registedComponents = new ArrayList<HALrealTimeComponentRegister>();
		//set the time increment
		this.timeIncrement = timeIncrement;
	}
	
	public long getUnixTime(){
		
		long unixTime;
		if(!isSimulation)
		{
			unixTime = System.currentTimeMillis() / 1000L;	
		}
		else
		{
			unixTime = (long)(this.timeTick+unixTimeAtStart);
		}
		
		return unixTime;
	}
	
	/**
	 * get the configured time increment of the time base. (In the real-smart-home scenario it's usually 1)
	 * @return
	 */
	public long getTimeIncrement() {
		return timeIncrement;
	}

	public long getUnixTimeAtStart() {
		return unixTimeAtStart;
	}

	public boolean isSimulation() {
		return isSimulation;
	}
	//time since the cb is running
	public long runningSince(){
		return this.timeTick;
	}

	public void setSimulation(boolean isSimulation) {
		this.isSimulation = isSimulation;
	}

	//for simulation update the time from the simulation engine
	public void updateTimer(int simulationTick) throws HALException{
		this.timeTick = simulationTick;
		this.handleRegisterdComponents();
	}
	
	
	/**
	 * You should N E V E R invoke this method by yourself ! ! ! !
	 * only the simulation engine should do this!
	 */
	public void resetTimer(){
		unixTimeAtStart = System.currentTimeMillis() / 1000L;
		timeTick = 0;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
				++timeTick;
				try {
					this.handleRegisterdComponents();
				} catch (HALException e) {
					
					e.printStackTrace();
				}
			}
			catch (InterruptedException ex){
				//TODO
			}
		}
	}
	
}
