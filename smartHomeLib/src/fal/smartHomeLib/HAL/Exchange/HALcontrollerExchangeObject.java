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


package fal.smartHomeLib.HAL.Exchange;


import fal.smartHomeLib.HAL.HALdeviceState;

/**
 * 
 *the concrete implementation of the data exchange class for the HAL 
 *here: the controller side
 *there are two possibilities for interaction:
 *The simple way using the props like "setrun" or "setstop"
 *or for more detailed commands using the HALdeviceState
 *@author florian.allerding@kit.edu
 *@category smart-home ControllerBox HAL
 *@deprecated please use your own Exchange object (derived from HALExchange)
 */
public class HALcontrollerExchangeObject extends HALObserverExchange {
	
	private HALdeviceState nextDeviceState;
	private boolean run;
	private boolean stop;
	private boolean pause;

	/**
	 * simply way to tell the device, that it has to make a pause
	 * that mean that the device stops it's operation in it's actual state
	 * @return
	 */
	public boolean isPause() {
		return pause;
	}

	/**
	 * simply way to tell the device, that it has to make a pause
	 * that mean that the device stops it's operation in it's actual state
	 * @param pause
	 */
	public void setPause(boolean pause) {
		this.pause = pause;
	}

	/**
	 * simply way to tell the device, that it has to run
	 * @return
	 */
	public boolean isRun() {
		return run;
	}

	/**
	 * simply way to tell the device, that it has to run
	 * @param run
	 */
	public void setRun(boolean run) {
		this.run = run;
	}

	/**
	 * simply way to tell the device, that it has to stop
	 * @return
	 */
	public boolean isStop() {
		return stop;
	}

	/**
	 * simply way to tell the device, that it has to stop
	 * @param stop
	 */
	public void setStop(boolean stop) {
		this.stop = stop;
	}

	/**
	 * for more details commands please use the HALdeviceState by creating a special sub-class
	 * @return
	 */
	protected HALdeviceState getNextDeviceState() {
		return nextDeviceState;
	}

	/**
	 * for more details commands please use the HALdeviceState by creating a special sub-class
	 * @param nextDeviceState
	 */
	protected void setNextDeviceState(HALdeviceState nextDeviceState) {
		this.nextDeviceState = nextDeviceState;
	}



	

}
