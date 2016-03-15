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
import fal.smartHomeLib.ControllerBoxCore.Exception.ControllerBoxException;
import fal.smartHomeLib.HAL.HALrealTimeDriver;
import fal.smartHomeLib.HAL.Exchange.HALControllerExchange;
import fal.smartHomeLib.SimulationCore.Exception.SimulationSubjectException;



public class VirtualClassicAppliance  extends VirtualGenericAppliance{

	public VirtualClassicAppliance(HALrealTimeDriver timerDriver,
			UUID deviceID, CBParameterCollection driverConfig) throws SimulationSubjectException {
		super(timerDriver, deviceID, driverConfig);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onInit() {
		this.activePower = 0;
		this.reactivePower = 0;
		this.progamDuration = this.deviceProfile.getProfileTicks().size();
		this.systemState = false;
		this.isIntelligent = this.deviceProfile.isIntelligent();
		this.hasProfile = this.deviceProfile.isHasProfile();
		this.progTick = 0;
		
	}

	@Override
	protected void onProcessingTimeTick() {
		//not intelligent we'll do nothing
		
	}

	@Override
	protected void onControllerRequest(HALControllerExchange controllerRequest) {
		//not intelligent we'll not react to anything!
		
	}

	@Override
	protected void onActiveTimeTick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onProgramEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNextTimePeriode() throws ControllerBoxException {
		// TODO Auto-generated method stub
		
	}
	


}



