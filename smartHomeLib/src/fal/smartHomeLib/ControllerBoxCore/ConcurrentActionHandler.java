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

import java.util.UUID;


/**
 * abstract superclass for cuncurrent actions. In case of the "real" smart home these action in "executeCommand()" of this class
 * will be executed in a concurrent way. In case of simulation it will be serial. This class can be called an initialized from every class
 * based on "GBGeneral". That mean from every observer or controller.
 * This is usefull for every command which needs a several time to be executed...like optimizer and so on...
 * @author florian
 *
 */
public abstract class ConcurrentActionHandler extends Thread {
	private boolean threadEnabled;
	private CBGeneral invokedFormObj;
	
	/**
	 * @param invokedFormObj
	 */
	public ConcurrentActionHandler(CBGeneral invokedFormObj) {
		this.invokedFormObj = invokedFormObj;
		this.threadEnabled = !invokedFormObj.getControllerBoxStatus().isSimulation();
		if (threadEnabled) {
			new Thread(this);
		}	
	}
	
	public boolean isThreadEnabled() {
		return threadEnabled;
	}

	@Override
	public final void run() {
		this.executeCommand();
	}
	
	public final void startCommand(){
		if (threadEnabled) {
			this.start();
		}
		else{
			this.executeCommand();
		}
	}
	
	/**
	 * Here you can enter the code for the execution in a concurrent environment
	 */
	public abstract void executeCommand();
	
	/**
	 * announce the caller (CBGeneral, invokedFormObj) that the command has been done
	 */
	public final void sendActionPerformed(){
		this.invokedFormObj.onConcurrentActionPerformed(this);
	}
	
}
