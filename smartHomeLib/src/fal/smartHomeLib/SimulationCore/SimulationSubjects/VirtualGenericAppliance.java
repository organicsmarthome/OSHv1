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
import fal.smartHomeLib.CBTypes.XMLSerialization;
import fal.smartHomeLib.CBTypes.Simulation.Screenplay.SubjectAction;
import fal.smartHomeLib.CBTypes.Simulation.VirtualDevicesData.DeviceProfile;
import fal.smartHomeLib.ControllerBoxCore.CBLoggerCore;
import fal.smartHomeLib.HAL.HALrealTimeDriver;
import fal.smartHomeLib.SimulationCore.SimulationDevice;
import fal.smartHomeLib.SimulationCore.Exception.SimulationSubjectException;

public abstract class VirtualGenericAppliance extends SimulationDevice {
	
	protected int activePower;
	protected DeviceProfile deviceProfile;
	protected boolean hasProfile;
	protected boolean isIntelligent;
	protected int progamDuration;
	protected int progTick;
	protected int reactivePower;
	protected boolean systemState;
	
	public VirtualGenericAppliance(HALrealTimeDriver timerDriver, UUID deviceID,
			CBParameterCollection driverConfig) throws SimulationSubjectException {
		super(timerDriver, deviceID, driverConfig);
		String profileSourceName = driverConfig.getParameter("profileSource");
		//load profile
		try {
			deviceProfile = (DeviceProfile)XMLSerialization.file2Unmarshal(profileSourceName, DeviceProfile.class);
		}
		catch (Exception ex){
			CBLoggerCore.root_logger.error("An error ouccurd while loading the device profile: " +ex.getMessage());
		}
		
		onInit();	

	}
	

	
	public int getActivePower() {
		return activePower;
	}
	
	public int getProgamDuration() {
		return progamDuration;
	}
	public int getReactivePower() {
		return reactivePower;
	}
	
	
	public boolean getSystemState() {
		return systemState;
	}
	
	public boolean isIntelligent() {
		return isIntelligent;
	}
	
	

	public boolean isProfile() {
		return hasProfile;
	}
	
	protected abstract void onInit();
	
	@Override
	public void onNextTimeTick() {
		
		this.onProcessingTimeTick();
		if (systemState){
					
					//next tick
					progTick = progTick + 1;
					
					if (progamDuration > progTick)
					{
						activePower = deviceProfile.getProfileTicks().get(progTick).getActivePower();
						reactivePower = deviceProfile.getProfileTicks().get(progTick).getReactivePower();
						this.onActiveTimeTick();
		
					}
					else // turn off the device
					{
						systemState = false;
						activePower = 0;
						reactivePower = 0;
						progTick = 0;
						this.onProgramEnd();
					}
					
					
		}
	
	}
	
	/**
	 * is invoked when the program stopps at the end of a work-item
	 */
	protected abstract void onProgramEnd();
	
	/**
	 * is invoked while processing a time tick
	 */
	protected abstract void onProcessingTimeTick();
	
	/**
	 * is invoked while processing a time tick  AND the appliance is running 
	 */
	protected abstract void onActiveTimeTick();
		
	@Override
	public void performNextAction(SubjectAction nextAction) {
		
		//check if the appliance is running 
		systemState = nextAction.isNextState();
		
	}
	
	public void sethasProfile(boolean profile) {
		hasProfile = profile;
	}
	
	public void setIntelligent(boolean isIntelligent) {
		this.isIntelligent = isIntelligent;
	}
	
	public void setSystemState(boolean systemState) {
		this.systemState = systemState;
	}

}
