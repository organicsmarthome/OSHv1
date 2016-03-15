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


package fal.shmd.OfflineSimulation.Subjects;

import java.util.List;
import java.util.UUID;


import fal.shmd.HAL.HALExchange.SmartPlugObserverExchange;
import fal.smartHomeLib.CBTypes.CBParameterCollection;
import fal.smartHomeLib.CBTypes.Simulation.Screenplay.ActionParameter;
import fal.smartHomeLib.CBTypes.Simulation.Screenplay.ActionParameters;
import fal.smartHomeLib.CBTypes.Simulation.Screenplay.PerformAction;
import fal.smartHomeLib.CBTypes.Simulation.Screenplay.SubjectAction;
import fal.smartHomeLib.HAL.HALrealTimeDriver;
import fal.smartHomeLib.HAL.Exchange.HALControllerExchange;
import fal.smartHomeLib.SimulationCore.SimulationDevice;
import fal.smartHomeLib.SimulationCore.Exception.SimulationSubjectException;

public class VirtualSmartPlug extends SimulationDevice {

	private int currentPower = 0;
	private int energy = 0;
	private int switchState = 0;
	private boolean currentState = false;
	
	public VirtualSmartPlug(HALrealTimeDriver timerDriver, UUID deviceID,
			CBParameterCollection driverConfig)
			throws SimulationSubjectException {
		super(timerDriver, deviceID, driverConfig);
		//if it's not controllable the switchstate is default "true"; that mean: the plug is always on
		if (!this.isControllable()){
			currentState = true;
		}
	}

	@Override
	public void onNextTimeTick() {
		increaseEnergy();
		processDataSubmission();
	}
	
	private void increaseEnergy(){
		energy = (int) (energy + currentPower*this.timerDriver.getTimeIncrement());
	}

	@Override
	public void performNextAction(SubjectAction nextAction) {
		if(nextAction.isNextState() && currentState){
			currentPower = Integer.valueOf(nextAction.getPerformAction().get(0).getActionParameterCollection().get(0).getParameter().get(0).getValue());
		}
		else {
			currentPower = 0;
		}
		
		
		
	}

	@Override
	protected void onControllerRequest(HALControllerExchange controllerRequest) {
		///future use for controllable smart-plugs...

	}
	
	private void processDataSubmission(){
		SmartPlugObserverExchange observerExchange = new SmartPlugObserverExchange();
		observerExchange.setDeviceID(this.getDeviceID());
		observerExchange.setActivePower(currentPower);
		observerExchange.setCurrent(currentPower/230);
		observerExchange.setEnergy(energy);
		observerExchange.setSwitchState(switchState);
		
		
		this.notifyObserver(observerExchange);
	}

}
