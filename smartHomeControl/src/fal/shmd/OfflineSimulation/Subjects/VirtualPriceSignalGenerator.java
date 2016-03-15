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

import java.util.ArrayList;
import java.util.UUID;

import fal.smartHomeLib.CBTypes.CBParameterCollection;
import fal.smartHomeLib.CBTypes.Simulation.Screenplay.SubjectAction;
import fal.smartHomeLib.ControllerBoxCore.Exception.ControllerBoxException;
import fal.smartHomeLib.HAL.HALrealTimeDriver;
import fal.smartHomeLib.HAL.Exchange.HALExchange;
import fal.smartHomeLib.HAL.Exchange.Comm.PriceSignalExchange;
import fal.smartHomeLib.SimulationCore.SimulationComm;
import fal.smartHomeLib.SimulationCore.SimulationEngine;
import fal.smartHomeLib.SimulationCore.Exception.SimulationSubjectException;

public class VirtualPriceSignalGenerator extends SimulationComm {

	private double[] priceInMinutes;

	public VirtualPriceSignalGenerator(HALrealTimeDriver timerDriver,
			UUID deviceID, CBParameterCollection driverConfig)
			throws SimulationSubjectException {
		super(timerDriver, deviceID, driverConfig);
		priceInMinutes = new double[2160];
	}

	

	@Override
	public void onNextTimeTick() {
		

	}

	@Override
	public void performNextAction(SubjectAction nextAction) {
	
		//get the next price Signal (blow up to 36h, yes I know, it's a fake....)
		for (int i = 0; i < 1440; i++){
			priceInMinutes[i] = Double.valueOf(nextAction.getPerformAction().get(0).getActionParameterCollection().get(0).getParameter().get(i).getValue());
		}
		int count = 0; 
		for (int i = 1440; i < 2160; i++){
			priceInMinutes[i] = Double.valueOf(nextAction.getPerformAction().get(0).getActionParameterCollection().get(0).getParameter().get(count).getValue());
			++count;
		}
		
		//now comunicate the price to the global observer
		
		PriceSignalExchange priceSignalExchange = new PriceSignalExchange();
		
		priceSignalExchange.setDeviceID(this.getDeviceID());
		priceSignalExchange.setPriceSignalInMinutes(priceInMinutes);
		this.updateUnit(priceSignalExchange);
		
	}




	@Override
	public void updateDataFromCommManager(HALExchange exchangeObject) {
		// TODO Auto-generated method stub
		
	}

	
	







}
