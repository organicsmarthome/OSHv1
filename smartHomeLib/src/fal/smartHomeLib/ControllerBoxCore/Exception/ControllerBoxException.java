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


package fal.smartHomeLib.ControllerBoxCore.Exception;

import fal.smartHomeLib.ControllerBoxCore.CBLoggerCore;

/**
 * superclass for exceptions near the controllerbox
 *@author florian.allerding@kit.edu
 *@category smart-home ControllerBox
 *
 */
public class ControllerBoxException extends Exception {

	public ControllerBoxException() {
		CBLoggerCore.root_logger.error("An unknown error near the Controller occured..." + "I'm sorry, Dave. I'm afraid I can't do that. ");
	}

	public ControllerBoxException(String message) {
		super(message + " I'm sorry, Dave. I'm afraid I can't do that. ");
		CBLoggerCore.root_logger.error("An error near the Controller occured: " +message);
	}

	public ControllerBoxException(Throwable cause) {
		super(cause);
		CBLoggerCore.root_logger.error("An error near the Controller occured: " +cause.getStackTrace());
	}

	public ControllerBoxException(String message, Throwable cause) {
		super(message +" I'm sorry, Dave. I'm afraid I can't do that. ", cause);
		CBLoggerCore.root_logger.error("An error near the Controller occured: " +cause.getStackTrace());
	}

}
