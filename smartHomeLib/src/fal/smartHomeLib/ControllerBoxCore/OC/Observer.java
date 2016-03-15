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


package fal.smartHomeLib.ControllerBoxCore.OC;

import fal.smartHomeLib.CBGlobal.ControllerBoxLifeCycle;
import fal.smartHomeLib.CBGlobal.IRealTimeObserver;
import fal.smartHomeLib.ControllerBoxCore.CBGeneral;
import fal.smartHomeLib.ControllerBoxCore.Data.ModelOfObservationExchange;
import fal.smartHomeLib.ControllerBoxCore.Data.ModelOfObservationType;
import fal.smartHomeLib.ControllerBoxCore.Exception.ControllerBoxException;
import fal.smartHomeLib.HAL.HALrealTimeDriver;

/**
 * superclass for all observer
 * @author florian
 * abstract superclass for all observers
 */
public abstract class Observer extends CBGeneral implements IRealTimeObserver, ControllerBoxLifeCycle { 
	
	protected ModelOfObservationType modelOfObservationType; 
	public Observer(HALrealTimeDriver systemTimer) {
		super(systemTimer);
		
	}
	
	/**
	 * For the communication between the observer and the controller
	 * The observer can invoke this method to get some observed data.
	 * Only an interface will be communicated, so feel free to create some own classes...
	 * @return
	 */
	public abstract ModelOfObservationExchange getObservedModelData();

	public ModelOfObservationType getModelOfObservationType() {
		return modelOfObservationType;
	}

	public void setModelOfObservationType(
			ModelOfObservationType modelOfObservationType) {
		this.modelOfObservationType = modelOfObservationType;
	}

	@Override
	public void onNextTimePeriode() throws ControllerBoxException {
		//...in case of use please override
		
	}

	@Override
	public void onSystemError() throws ControllerBoxException {
		//...in case of use please override
		
	}

	@Override
	public void onSystemHalt() throws ControllerBoxException {
		//...in case of use please override
		
	}

	@Override
	public void onSystemRunning() throws ControllerBoxException {
		//...in case of use please override
		
	}

	@Override
	public void onSystemIsUp() throws ControllerBoxException {
		//...in case of use please override
		
	}

	@Override
	public void onSystemResume() throws ControllerBoxException {
		//...in case of use please override
		
	}

	@Override
	public void onSystemShutdown() throws ControllerBoxException {
		//...in case of use please override
		
	}

	


	
}
