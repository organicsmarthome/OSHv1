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


package fal.smartHomeLib.ControllerBoxCore;

import java.util.Collection;

import fal.smartHomeLib.CBGlobal.IRealTimeObserver;
import fal.smartHomeLib.CBTypes.Data.Observer.ObservedAction;
import fal.smartHomeLib.ControllerBoxCore.Exception.ControllerBoxException;
import fal.smartHomeLib.ControllerBoxCore.OC.LocalController;
import fal.smartHomeLib.ControllerBoxCore.OC.LocalObserver;
import fal.smartHomeLib.HAL.HALrealTimeDriver;

/**
 * abstract class for data model to handle the observed actions at the global controller
 *@author florian.allerding@kit.edu
 *@category smart-home ControllerBox
 *
 */
public abstract class ObserverModel implements IRealTimeObserver {

	private HALrealTimeDriver systemTimer;

	public ObserverModel(HALrealTimeDriver systemTimer) {
		super();
		this.systemTimer = systemTimer;
	}

	/**
	 * @return the systemTimer
	 */
	public HALrealTimeDriver getSystemTimer() {
		return systemTimer;
	}

	/* (non-Javadoc)
	 * @see fal.smartHomeLib.CBGlobal.IRealTimeObserver#onNextTimePeriode()
	 */
	@Override
	public void onNextTimePeriode() throws ControllerBoxException {
			
	}
	
	public abstract void storeNewAction(ObservedAction observedAction);
	
	
	public abstract void initializeDataStorage(Collection<LocalObserver> localObservers);
	
	public abstract Collection<ObservedAction> predict(long timestamp);
	
	

}
