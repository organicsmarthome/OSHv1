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

import java.sql.SQLException;
import java.util.UUID;

import fal.smartHomeLib.CBTypes.CBParameterCollection;
import fal.smartHomeLib.ControllerBoxCore.Exception.ControllerBoxException;
import fal.smartHomeLib.HAL.HALCommDriver;
import fal.smartHomeLib.HAL.HALrealTimeDriver;
import fal.smartHomeLib.HAL.Exchange.HALExchange;
import fal.smartHomeLib.HAL.Exchange.Comm.PriceSignalExchange;

public class PriceSignalProvider extends HALCommDriver {


	private PriceSignalThread priceSignalThread;
	public PriceSignalProvider(HALrealTimeDriver timerDriver, UUID deviceID,
			CBParameterCollection driverConfig) {
		super(timerDriver, deviceID, driverConfig);
		
	}
	
	@Override
	public void onSystemIsUp() throws ControllerBoxException {
		super.onSystemIsUp();
		//processPriceSignal();
		priceSignalThread = new PriceSignalThread(globalLogger, this);
		try {
			priceSignalThread.setUpSQLConnection("localhost","3306","organicsmarthome","osh","osh");
		} catch (SQLException e) {
			globalLogger.logError(e);
		}
		priceSignalThread.start();	
	}

	public void processPriceSignal(double[] priceInMinutes){

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
