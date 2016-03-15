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


package fal.smartHomeLib.SimulationCore;

import java.util.UUID;

import fal.smartHomeLib.CBTypes.CBParameterCollection;
import fal.smartHomeLib.HAL.HALrealTimeDriver;
import fal.smartHomeLib.SimulationCore.Exception.SimulationSubjectException;

/**
 * Abstract superclass for simulated smart-meters
 * @author florian.allerding@kit.edu
 *
 */
public abstract class SimulationSmartMeter extends SimulationDevice {

	public SimulationSmartMeter(HALrealTimeDriver timerDriver, UUID deviceID,
			CBParameterCollection driverConfig)
			throws SimulationSubjectException {
		super(timerDriver, deviceID, driverConfig);
		
	}

	public abstract void updateActivePower(int totalActivePower);
	
	public abstract void updateReactivePower(int totalReactivePower);
}
