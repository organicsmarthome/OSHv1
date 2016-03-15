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


package fal.shmd.localunits.Observer;

import fal.shmd.HAL.HALExchange.SmartMeterObserverExchange;
import fal.smartHomeLib.ControllerBoxCore.Data.ModelOfObservationExchange;
import fal.smartHomeLib.ControllerBoxCore.Data.ObserverExchange;
import fal.smartHomeLib.ControllerBoxCore.OC.LocalObserver;
import fal.smartHomeLib.HAL.HALrealTimeDriver;
import fal.smartHomeLib.HAL.Exchange.HALobserverExchangeObject;

public class SmartMeterObserver extends LocalObserver {

	public SmartMeterObserver(HALrealTimeDriver systemTimer) {
		super(systemTimer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onDeviceStateUpdate() {
		SmartMeterObserverExchange exchangeObject = (SmartMeterObserverExchange) this.getObserverDataObject();
		
		//System.out.println("Total activ power: " + exchangeObject.getActivePower() + " at timeTick: "+this.systemTimer.getUnixTime());
	}

	@Override
	public ObserverExchange pollObserver() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelOfObservationExchange getObservedModelData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onSystemIsUp() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNextTimePeriode() {
		// TODO Auto-generated method stub

	}

}
