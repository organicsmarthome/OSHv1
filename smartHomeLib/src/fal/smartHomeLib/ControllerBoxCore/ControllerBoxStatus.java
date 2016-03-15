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

import fal.smartHomeLib.CBGlobal.LifeCycleStates;

public class ControllerBoxStatus {
	private boolean simulation;
	private ControllerBoxLifeCycleManager lifeCycleManager;

	public boolean isSimulation() {
		return simulation;
	}

	protected void setSimulation(boolean simulation) {
		this.simulation = simulation;
	}

	/**
	 * @return the currentLifeCycleState
	 */
	public LifeCycleStates getCurrentLifeCycleState() {
		return lifeCycleManager.getCurrentState();
	}

	/**
	 * @param lifeCycleManager the lifeCycleManager to set
	 */
	protected void setLifeCycleManager(
			ControllerBoxLifeCycleManager lifeCycleManager) {
		this.lifeCycleManager = lifeCycleManager;
	}


}
