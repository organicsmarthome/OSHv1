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

import java.util.List;
import java.util.UUID;

import fal.shmd.HAL.HALExchange.Comm.DOFStateUserInteraction;
import fal.smartHomeLib.CBTypes.CBParameterCollection;
import fal.smartHomeLib.CBTypes.Simulation.Screenplay.ActionParameter;
import fal.smartHomeLib.ControllerBoxCore.Exception.ControllerBoxException;
import fal.smartHomeLib.HAL.HALCommDriver;
import fal.smartHomeLib.HAL.HALrealTimeDriver;
import fal.smartHomeLib.HAL.Exchange.HALExchange;

public class DummyUserInteractionProvider extends HALCommDriver {

	public DummyUserInteractionProvider(HALrealTimeDriver timerDriver,
			UUID deviceID, CBParameterCollection driverConfig) {
		super(timerDriver, deviceID, driverConfig);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void updateDataFromCommManager(HALExchange exchangeObject) {
		// TODO Auto-generated method stub

	}
	
	
	
	@Override
	public void onSystemIsUp() throws ControllerBoxException {
		// TODO Auto-generated method stub
		super.onSystemIsUp();
		processDofInformation();
	}

	private void processDofInformation(){
		
		DOFStateUserInteraction userInteractionExchange  = new DOFStateUserInteraction();	
		
		UUID[] deviceIds = {UUID.fromString("e2ef0d13-61b3-4188-b32a-1570dcbab4d1"), 
				UUID.fromString("de61f462-cda2-4941-8402-f93a1f1b3e57"),
				UUID.fromString("ab9519db-7a14-4e43-ac3a-ade723802194"),
				UUID.fromString("cef732b1-04ba-49e1-8189-818468a0d98e"),
				UUID.fromString("1468cc8a-dfdc-418a-8df8-96ba8c146156"),
				UUID.fromString("e7b3f13d-fdf6-4663-848a-222303d734b8")};
		
		int[] applianceDoF = 	  
			{	0,
				0,
				7200,
				0,
				7200,
				7200};	
		
		for (int i = 0; i < deviceIds.length; i++){
			userInteractionExchange.addDeviceDof(deviceIds[i], applianceDoF[i]);
		}
		
		//userInteractionExchange.addDeviceDof(UUID.fromString(parameter.getName()), Integer.parseInt(parameter.getValue()));
		
		this.updateUnit(userInteractionExchange);
	}

}
