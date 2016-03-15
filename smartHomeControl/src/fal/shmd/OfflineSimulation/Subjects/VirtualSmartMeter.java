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

import java.util.UUID;

import fal.shmd.HAL.HALExchange.SmartMeterObserverExchange;
import fal.smartHomeLib.CBTypes.CBParameterCollection;
import fal.smartHomeLib.CBTypes.Simulation.Screenplay.SubjectAction;
import fal.smartHomeLib.ControllerBoxCore.Exception.ControllerBoxException;
import fal.smartHomeLib.HAL.HALrealTimeDriver;
import fal.smartHomeLib.HAL.Exchange.HALControllerExchange;
import fal.smartHomeLib.HAL.Exchange.HALobserverExchangeObject;
import fal.smartHomeLib.SimulationCore.SimulationDevice;
import fal.smartHomeLib.SimulationCore.SimulationSmartMeter;
import fal.smartHomeLib.SimulationCore.Exception.SimulationSubjectException;


/**
 * represent the simulation agent of a smart meter
 * 
 *@author florian.allerding@kit.edu
 *@category smart-home ControllerBox Simulation
 *
 */
public class VirtualSmartMeter extends SimulationSmartMeter {
	private int currentActivePower;
	private int currentReactivePower;
	private int totalEnergieConsumption;
	private int internalCounter;
	private int tickResolution;
	private int RMSactivPower;
	private int RMSreactivePower;

	
	
	public VirtualSmartMeter(HALrealTimeDriver timerDriver, UUID deviceID,
			CBParameterCollection driverConfig) throws SimulationSubjectException {
		super(timerDriver, deviceID, driverConfig);
		tickResolution = Integer.parseInt(driverConfig.getParameter("resolution"));
		this.internalCounter = 0;
	}

	public void updateActivePower(int activePower){
		
		this.currentActivePower = activePower;
	}
	
	public void updateReactivePower(int reactivePower){
		
		this.currentReactivePower = reactivePower;
	}
	
	@Override
	public void onNextTimeTick() {
		
		++internalCounter;
		if(internalCounter == tickResolution){
		
			SmartMeterObserverExchange observerObj = new SmartMeterObserverExchange();
			RMSactivPower = RMSactivPower/tickResolution;
			RMSreactivePower = RMSreactivePower/tickResolution;
			observerObj.setActivePower(RMSactivPower);
			observerObj.setReactivePower(RMSreactivePower);
			observerObj.setDeviceID(getDeviceID());
			this.notifyObserver(observerObj);
			internalCounter = 0;
			//reset the values...
			RMSactivPower = 0;
			RMSreactivePower = 0;
		}
		else
		{
			RMSactivPower = RMSactivPower + currentActivePower;
			RMSreactivePower = RMSreactivePower + currentReactivePower;
		}
		
	}

	@Override
	public void performNextAction(SubjectAction nextAction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onControllerRequest(HALControllerExchange controllerRequest) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNextTimePeriode() throws ControllerBoxException {
		// TODO Auto-generated method stub
		
	}

}
