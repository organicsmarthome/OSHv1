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


package fal.rsh.driver.interaction;

import java.util.UUID;

import fal.smartHomeLib.CBTypes.CBParameterCollection;
import fal.smartHomeLib.ControllerBoxCore.Exception.ControllerBoxException;
import fal.smartHomeLib.HAL.HALCommDriver;
import fal.smartHomeLib.HAL.HALrealTimeDriver;
import fal.smartHomeLib.HAL.Exchange.HALExchange;
import fal.smartHomeLib.HAL.Exchange.Comm.PriceSignalExchange;

public class DummyPriceSignalProvider extends HALCommDriver {

	@Override
	public void onSystemIsUp() throws ControllerBoxException {
		super.onSystemIsUp();
		processPriceSignal();
	}

	private double[] priceInMinutes;
	private static int minutesPerDay = 1440;
	
	public DummyPriceSignalProvider(HALrealTimeDriver timerDriver, UUID deviceID,
			CBParameterCollection driverConfig) {
		super(timerDriver, deviceID, driverConfig);
		priceInMinutes = new double[minutesPerDay];
	}

	@Override
	public void updateDataFromCommManager(HALExchange exchangeObject) {
		// TODO Auto-generated method stub

	}
	
	private void processPriceSignal(){
		//get the next price Signal
		for (int i = 0; i < priceInMinutes.length; i++){
			priceInMinutes[i] = 20;
		}
		
		//now comunicate the price to the global observer
		
		PriceSignalExchange priceSignalExchange = new PriceSignalExchange();
		
		priceSignalExchange.setDeviceID(this.getDeviceID());
		priceSignalExchange.setPriceSignalInMinutes(priceInMinutes);
		this.updateUnit(priceSignalExchange);
	}

}
