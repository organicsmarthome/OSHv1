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

import java.util.UUID;

import fal.smartHomeLib.CBTypes.CBParameterCollection;
import fal.smartHomeLib.CBTypes.Data.ApplianceState;
import fal.smartHomeLib.CBTypes.Simulation.Screenplay.SubjectAction;
import fal.smartHomeLib.ControllerBoxCore.Exception.ControllerBoxException;
import fal.smartHomeLib.HAL.HALrealTimeDriver;
import fal.smartHomeLib.HAL.Exchange.HALControllerExchange;
import fal.smartHomeLib.HAL.Exchange.HALobserverExchangeObject;
import fal.smartHomeLib.SimulationCore.Exception.SimulationSubjectException;

public class VirtualIntelligentAppliance extends VirtualGenericAppliance {

	private ApplianceState applianceState;
	private String applianceDetails;
	
	
	public VirtualIntelligentAppliance(HALrealTimeDriver timerDriver,
			UUID deviceID, CBParameterCollection driverConfig) throws SimulationSubjectException {
		super(timerDriver, deviceID, driverConfig);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onInit() {
		activePower = 0;
		reactivePower = 0;
		progamDuration = deviceProfile.getProfileTicks().size();
		systemState = false;
		isIntelligent = deviceProfile.isIntelligent();
		hasProfile = deviceProfile.isHasProfile();
		progTick = 0;
		applianceDetails = "";
		applianceState = ApplianceState.OFF;

	}

	@Override
	protected void onProcessingTimeTick() {
		//notify the controllerbox

		HALobserverExchangeObject observerObj = new HALobserverExchangeObject();
		observerObj.setActivePower(activePower);
		observerObj.setReactivePower(reactivePower);
		observerObj.setRunning(systemState);
		observerObj.setDeviceID(getDeviceID());
		observerObj.setApplianceState(ApplianceState.valueOf(applianceState.toString()));
		observerObj.setApplianceDetails(applianceDetails);
		this.notifyObserver(observerObj);

	}

	@Override
	protected void onControllerRequest(HALControllerExchange controllerRequest) {
		//in this version an appliance will try to run whenever this Method is invoked
		systemState = true;
		applianceState = ApplianceState.RUNNING;

	}
	
	@Override
	public void performNextAction(SubjectAction nextAction){
		//if the next state is true the i-appliance is filled up an is now able to run
		if (nextAction.isNextState()) {
			applianceState = ApplianceState.PROGRAMMED;
			
		}
		else
		{
			applianceState = ApplianceState.OFF;
			systemState = false;
		}
	}
	

	@Override
	protected void onActiveTimeTick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onProgramEnd() {
		applianceState = ApplianceState.OFF;
		
	}

	@Override
	public void onNextTimePeriode() throws ControllerBoxException {
		// TODO Auto-generated method stub
		
	}

}
