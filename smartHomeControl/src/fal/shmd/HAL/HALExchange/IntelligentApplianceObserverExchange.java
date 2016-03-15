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


package fal.shmd.HAL.HALExchange;

import java.util.List;
import java.util.UUID;

import fal.smartHomeLib.HAL.Exchange.HALObserverExchange;

public class IntelligentApplianceObserverExchange extends
		HALObserverExchange {

    private List<PowerProfile> expectedPowerProfile;
    private ApplianceState applianceState;
    private int currentActivePower;
    private int currentReactivePower;
    private int expectedProgramDuration;
	private int programTimeLeft;

	
	public List<PowerProfile> getExpectedPowerProfile() {
		return expectedPowerProfile;
	}

	public void setExpectedPowerProfile(List<PowerProfile> expectedPowerProfile) {
		this.expectedPowerProfile = expectedPowerProfile;
	}

	public ApplianceState getApplianceState() {
		return applianceState;
	}

	public void setApplianceState(ApplianceState applianceState) {
		this.applianceState = applianceState;
	}

	public int getCurrentActivePower() {
		return currentActivePower;
	}

	public void setCurrentActivePower(int currentActivePower) {
		this.currentActivePower = currentActivePower;
	}

	public int getCurrentReactivePower() {
		return currentReactivePower;
	}

	public void setCurrentReactivePower(int currentReactivePower) {
		this.currentReactivePower = currentReactivePower;
	}

	public int getExpectedProgramDuration() {
		return expectedProgramDuration;
	}

	public void setExpectedProgramDuration(int expectedProgramDuration) {
		this.expectedProgramDuration = expectedProgramDuration;
	}

	public int getProgramTimeLeft() {
		return programTimeLeft;
	}

	public void setProgramTimeLeft(int programTimeLeft) {
		this.programTimeLeft = programTimeLeft;
	}

	public IntelligentApplianceObserverExchange() {
		// TODO Auto-generated constructor stub
	}
	

}
