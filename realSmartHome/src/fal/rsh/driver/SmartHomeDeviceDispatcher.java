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


package fal.rsh.driver;


import java.sql.SQLException;

import java.util.UUID;

import dbAcces.RawDataLogger;


import fal.rsh.driver.data.MieleAppliance;
import fal.rsh.driver.data.WagoPowerData;
import fal.rsh.driver.data.WagoSwitchData;

import fal.rsh.driver.parser.SmartHomeParserThread;

import fal.smartHomeLib.ControllerBoxCore.CBGlobalLogger;
import fal.smartHomeLib.HAL.HALDispatcher;


public class SmartHomeDeviceDispatcher extends HALDispatcher {

	private SmartHomeParserThread smartHomeParserThread;
	private RawDataLogger rawDataLogger;
	
	public SmartHomeDeviceDispatcher(CBGlobalLogger logger) {
		super(logger);
		Thread parser = new SmartHomeParserThread(logger);
		this.smartHomeParserThread = (SmartHomeParserThread) parser;
		this.smartHomeParserThread.setSmartHomeDeviceDispatcher(this);
		
		//init raw data logger
		rawDataLogger = new RawDataLogger();
		
		parser.start();
	}
	
	/**
	 * is invoked every time when new data from the parsers are available
	 */
	public void onNewDataReceived(){

		
		Runnable updateDataBase = new Runnable() {
			
			@Override
			public void run() {
				try {
					rawDataLogger.writeToDB(smartHomeParserThread.getWagoPowerDatasList(), 
											smartHomeParserThread.getMieleAppliancesList(), 
											smartHomeParserThread.getWagoSwitchDataList(),
											smartHomeParserThread.getWagoWIZDataList());
				} catch (Exception e) {
					globalLogger.logError("raw-write to db failed", e);
				}
				
			}
		};
		
		new Thread(updateDataBase).start();
	}
	
	public MieleAppliance getMieleApplianceById(UUID deviceId){
		
		for (MieleAppliance mieleAppliance: this.smartHomeParserThread.getMieleAppliancesList()) {

			if (mieleAppliance.getApplianceId().compareTo(deviceId) == 0) {
				return mieleAppliance;
			}
		}
		
		return null;
	}
	
	public WagoPowerData getWagoPowerDataById(UUID deviceId){	
		
		for (WagoPowerData wagoPowerData: this.smartHomeParserThread.getWagoPowerDatasList()) {
			if (wagoPowerData.getAssociatedApplianceId() != null){
				if (wagoPowerData.getAssociatedApplianceId().compareTo(deviceId) == 0) {
					return wagoPowerData;
				}
			}
		}
		
		return null;
	}
	
	public WagoSwitchData getWagoSwitchDataById(UUID deviceId){
		
		for (WagoSwitchData switchData: this.smartHomeParserThread.getWagoSwitchDataList()){
			if (switchData.getUuid() == deviceId) {
				return switchData;
			}
		}
		return null;
	}

	

	

	


}
