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


package fal.rsh.driver.smartHome;


import java.util.UUID;

import fal.rsh.driver.SmartHomeDeviceDispatcher;
import fal.rsh.driver.data.WagoPowerData;
import fal.rsh.driver.data.WagoSwitchData;
import fal.smartHomeLib.CBTypes.CBParameterCollection;
import fal.smartHomeLib.ControllerBoxCore.Exception.ControllerBoxException;
import fal.smartHomeLib.HAL.HALcontrollerDriver;
import fal.smartHomeLib.HAL.HALdriver;
import fal.smartHomeLib.HAL.HALobserverDriver;
import fal.smartHomeLib.HAL.HALrealTimeDriver;
import fal.smartHomeLib.HAL.Exchange.HALControllerExchange;
import fal.shmd.HAL.HALExchange.SmartPlugControllerExchange;

import fal.shmd.HAL.HALExchange.SmartPlugObserverExchange;

public class SmartPlugDriver extends HALdriver {

	private UUID WagoswitchUUID;
	private UUID WagoMeterUUID;
	
	public SmartPlugDriver(HALrealTimeDriver timerDriver, UUID deviceID,
			CBParameterCollection driverConfig) {
		super(timerDriver, deviceID, driverConfig);
		
	}

	public SmartPlugDriver(UUID deviceID, HALrealTimeDriver timerDriver,
			HALobserverDriver halobserverDriver,
			HALcontrollerDriver halcontrollerDriver,
			CBParameterCollection driverConfig) {
		super(deviceID, timerDriver, halobserverDriver, halcontrollerDriver,
				driverConfig);
		
	}	

	@Override
	protected void onControllerRequest(HALControllerExchange controllerRequest) {
		SmartPlugControllerExchange controllerExchange = (SmartPlugControllerExchange) controllerRequest;
		//future use if the plugs are controllable

	}
	
	private void processDataSubmission(){
		SmartPlugObserverExchange observerExchange = new SmartPlugObserverExchange();
		
		SmartHomeDeviceDispatcher _tmpDispatch =(SmartHomeDeviceDispatcher)this.getHalDispatcher();
		WagoPowerData wagoPowerData = _tmpDispatch.getWagoPowerDataById(getDeviceID());
		
		observerExchange.setDeviceID(this.getDeviceID());
		observerExchange.setActivePower((int)wagoPowerData.getPower());
		observerExchange.setCurrent((int)wagoPowerData.getCurrent());
		observerExchange.setEnergy((int)wagoPowerData.getEnergy());
		
		if(this.isControllable()) {
			WagoSwitchData wagoSwitchData = _tmpDispatch.getWagoSwitchDataById(getDeviceID());
			observerExchange.setSwitchState(wagoSwitchData.getState());
		}
		
		this.notifyObserver(observerExchange);
	}

	@Override
	public void onSystemIsUp() throws ControllerBoxException {
		
		this.timerDriver.registerComponent(this, 1);
		super.onSystemIsUp();
		
	}

	@Override
	public void onNextTimePeriode() throws ControllerBoxException {
		processDataSubmission();
		super.onNextTimePeriode();
		
	}
	
	
	
	
	

}
