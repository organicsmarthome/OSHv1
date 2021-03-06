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


package fal.rsh.driver.interaction;


import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;



import fal.shmd.HAL.HALExchange.Comm.DOFStateUserInteraction;
import fal.shmd.HAL.HALExchange.Comm.ScheduledApplianceUIexchange;
import fal.shmd.globalunits.Controller.ApplianceSchedule;
import fal.shmd.globalunits.Controller.SHMDCommManager;
import fal.smartHomeLib.CBTypes.CBParameterCollection;

import fal.smartHomeLib.ControllerBoxCore.Exception.ControllerBoxException;
import fal.smartHomeLib.HAL.HALCommDriver;
import fal.smartHomeLib.HAL.HALrealTimeDriver;
import fal.smartHomeLib.HAL.Exchange.HALExchange;

public class UserInteractionProvider extends HALCommDriver {

	private DofDBThread dbThread;
	
	public UserInteractionProvider(HALrealTimeDriver timerDriver,
			UUID deviceID, CBParameterCollection driverConfig) {
		super(timerDriver, deviceID, driverConfig);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void updateDataFromCommManager(HALExchange exchangeObject) {
		// TODO Auto-generated method stub

	}
	
	
	
	@Override
	public void onSystemIsUp() throws ControllerBoxException {
		// TODO Auto-generated method stub
		super.onSystemIsUp();
		//processDummyDofInformation();
		dbThread = new DofDBThread(this.globalLogger, this);
		
			try {
				dbThread.setUpSQLConnection("localhost","3306","organicsmarthome","osh","osh");
			} catch (SQLException e) {
				globalLogger.logError(e);
			}
		dbThread.start();	
		
	}
	
	public ArrayList<ApplianceSchedule> triggerCommManager(){
		ScheduledApplianceUIexchange applianceUIexchange = (ScheduledApplianceUIexchange) ((SHMDCommManager)this.getAssignedCommManager()).pollGlobalController(getDeviceID());
		return applianceUIexchange.getCurrentApplianceSchedules();
		
	}
	
	
	public void processDofInformation(HashMap<UUID, Integer> applianceDof){
		
		DOFStateUserInteraction userInteractionExchange  = new DOFStateUserInteraction();	
			
			userInteractionExchange.getDeviceDegreeOfFreedom().putAll(applianceDof);
		
		//userInteractionExchange.addDeviceDof(UUID.fromString(parameter.getName()), Integer.parseInt(parameter.getValue()));
		
		this.updateUnit(userInteractionExchange);
	}

}
