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


package fal.rsh.driver.parser;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.JAXBException;

import fal.rsh.driver.SmartHomeDeviceDispatcher;
import fal.rsh.driver.data.MieleAppliance;
import fal.rsh.driver.data.WagoPowerData;
import fal.rsh.driver.data.WagoSwitchData;
import fal.rsh.driver.parser.Wago.WagoPowerParser;
import fal.rsh.driver.parser.Wago.WagoSwitchParser;
import fal.rsh.driver.parser.data.ControllerIdMapping;
import fal.rsh.driver.parser.data.MieleIdMapping;
import fal.rsh.driver.parser.data.WagoPowerIdMapping;
import fal.rsh.driver.parser.data.WagoSwitchIdMapping;
import fal.rsh.driver.parser.mieleGateway.MieleGatewayParser;
import fal.smartHomeLib.CBTypes.XMLSerialization;
import fal.smartHomeLib.ControllerBoxCore.CBGlobalLogger;
import fal.smartHomeLib.HAL.Exception.HALException;

/**
 * @author florian.allerding@kit.edu
 * @category smart-home  Driver-Parser
 */
public class SmartHomeParserThread extends Thread{
	private HashMap<Integer,UUID> applianceIdMapping;
	private ControllerIdMapping controllerIdMapping;
	protected CBGlobalLogger globalLogger;
	private HashMap<Integer, UUID> mesurementIdMapping;
	private List<MieleAppliance> mieleAppliancesList;
	private WagoPowerParser powerParser = null;
	private MieleGatewayParser selectParser = null; 
	private SmartHomeDeviceDispatcher smartHomeDeviceDispatcher;
	private HashMap<Integer, UUID> switchIdMapping;
	private WagoSwitchParser switchParser = null;
	private String urlToIdMappingFile = "configfiles/system/controllerIdMapping.xml";
	private String urlToWagoSystem = "http://192.168.1.51/xmlwago/ramdisk/values.xml";
	private int WagoControllerId = 1;
	private List<WagoPowerData> wagoPowerDatasList;
	private List<WagoSwitchData> wagoSwitchDataList;
	private int imAliveCounter = 0;

	private List<WagoPowerData> wagoWIZDataList;
	private int wizControllerId = 3;
	private WagoPowerParser wizParser = null;
	public SmartHomeParserThread(CBGlobalLogger globalLogger){
		this.globalLogger = globalLogger;
		try {
			controllerIdMapping = (ControllerIdMapping) XMLSerialization.file2Unmarshal(urlToIdMappingFile, ControllerIdMapping.class);
		} catch (FileNotFoundException e) {
			globalLogger.logError("ERROR loading controllerIdMapping", e);
		} catch (JAXBException e) {
			globalLogger.logError("ERROR loading controllerIdMapping", e);
		}
		this.initApplianceIds();
		this.initPowermesurementIds();
		this.initSwitchIdMappings();
		try {
			initParsers();
		}
		catch (Exception ex) {
			ex.getStackTrace();
			return;
		}
	}
	

	/**
	 * @return the mieleAppliancesList
	 */
	public synchronized List<MieleAppliance> getMieleAppliancesList() {
		return mieleAppliancesList;
	}

	private WagoPowerData getPowerDatabyPort(ArrayList<WagoPowerData> wagoPowerDatas, int meterId, int portId){
		
		for(WagoPowerData powerData: wagoPowerDatas){
			if ((powerData.getMeterId() == meterId) && (powerData.getMeterPortId()== portId)) {
				return powerData;
			}
		}
		
		return null;
	}

	public SmartHomeDeviceDispatcher getSmartHomeDeviceDispatcher() {
		return smartHomeDeviceDispatcher;
	}
	
	/**
	 * @return the wagoPowerDatasList
	 */
	public synchronized List<WagoPowerData> getWagoPowerDatasList() {
		return wagoPowerDatasList;
	}
	
	/**
	 * @return the wagoSwitchDataList
	 */
	public synchronized List<WagoSwitchData> getWagoSwitchDataList() {
		return wagoSwitchDataList;
	}
	
	/**
	 * @return the wagoWIZDataList
	 */
	public synchronized List<WagoPowerData> getWagoWIZDataList() {
		return wagoWIZDataList;
	}
	
	/**
	 * for identifier mapping and assigning the power data from the measurement-units...and do some other voodoo...
	 * @param powerData
	 * @return
	 */
	private ArrayList<WagoPowerData> handleWagoPowerData(ArrayList<WagoPowerData> powerData){
		 ArrayList<WagoPowerData> wagoPowerData = powerData;	 	
		 for(int i = 0; i < wagoPowerData.size(); i ++){
			 int currentId;

			currentId = 1000 +  wagoPowerData.get(i).getMeterId()*10 + wagoPowerData.get(i).getMeterPortId();
			 if (mesurementIdMapping.containsKey(currentId)){
				 wagoPowerData.get(i).setAssociatedApplianceId(mesurementIdMapping.get(currentId));
			 }
			 
			 //and it gets much dirtier...handle mutiple phase devices
			 //for the heatplates:
			 if (currentId == 1080) {
				 //add the values for each phase
				 double power = 0.0;
				 double power0 = 0.0;
				 double power1 = 0.0;
				 double power2 = 0.0;
				 
				 power0 = wagoPowerData.get(i).getPower();
				 power1 = getPowerDatabyPort(wagoPowerData, 8, 1).getPower();
				 power2 = getPowerDatabyPort(wagoPowerData, 8, 2).getPower();
				 
				 //add the three values of the phases
				 power = power0+power1+power2;
				 wagoPowerData.get(i).setPower(power);
			 }
			 
		 }
 
		 return wagoPowerData;
	 }
	
	private void initApplianceIds(){
		applianceIdMapping = new HashMap<Integer, UUID>();
		
		for(MieleIdMapping mieleMapping: controllerIdMapping.getMieleIdMapping() ){
			applianceIdMapping.put(mieleMapping.getMieleId(), UUID.fromString(mieleMapping.getSmartHomeDeviceUUID()));
		}
	}
	
	private void initParsers() throws HALException{
		
		try {
			selectParser = new MieleGatewayParser("http://192.168.1.20/homebus");
		} catch (MalformedURLException ex) {
			
			throw new HALException();
		}
		
		powerParser = new WagoPowerParser(urlToWagoSystem, WagoControllerId);
		switchParser = new WagoSwitchParser("http://192.168.1.52/xmlwago/ramdisk/values.xml", 2, switchIdMapping);
		wizParser = new WagoPowerParser("http://192.168.1.50/xmlwago/ramdisk/values.xml", wizControllerId);
		
	}
	 
		private void initPowermesurementIds(){
			
			mesurementIdMapping = new HashMap<Integer, UUID>();
				
			for(WagoPowerIdMapping idMapping: controllerIdMapping.getWagoPowerIdMapping()){
				mesurementIdMapping.put(idMapping.getWagoMeterId(), UUID.fromString(idMapping.getSmartHomeDeviceUUID()));
			}
			
		}

		private void initSwitchIdMappings(){
			
			switchIdMapping = new HashMap<Integer, UUID>();
			
			for(WagoSwitchIdMapping idMapping: controllerIdMapping.getWagoSwitchIdMapping()){
				switchIdMapping.put(idMapping.getWagoswitchId(), UUID.fromString(idMapping.getSmartHomeDeviceUUID()));
			}

		}

		@Override
		public void run() {
			
			super.run();
			while (true) {
				
				try {
					//update devices
					
					Runnable updateMieleData = new Runnable() {	
						@Override
						public void run() {
							try {
								selectParser.runUpdate();
							} catch (Exception e) {
								globalLogger.logError("parsing miele data failed",e);
							}
							
						}
					};
		
					Runnable updateWagoPowerData  = new Runnable() {
						
						@Override
						public void run() {
							
							try {
								powerParser.ParsePowerData();
							} catch (Exception e) {
								globalLogger.logError("parsing wago power data data failed",e);
							}
							
						}
					};
					
					Runnable updateWagoWIZData  = new Runnable() {
						
						@Override
						public void run() {
							
							try {
								wizParser.ParsePowerData();
							} catch (Exception e) {
								globalLogger.logError("parsing wago wiz data data failed",e);
							}
							
						}
					};
					
					Runnable updateWagoSwitchData = new Runnable() {
						
						@Override
						public void run() {
							
							try {
								switchParser.ParseSwitchData();
							} catch (Exception e) {
								globalLogger.logError("parsing wago switch data failed",e);
							}
							
						}
					};
					
					Thread t_miele = new Thread(updateMieleData);
					Thread t_wagoPw = new Thread(updateWagoPowerData);
					Thread t_wagoSw = new Thread(updateWagoSwitchData);
					Thread t_wiz = new Thread(updateWagoWIZData);
					
					//satrting the threads
					t_miele.start();
					t_wagoPw.start();
					t_wagoSw.start();
					t_wiz.start();
					
					//waiting that everybody has done his job...
					t_miele.join();
					t_wagoPw.join();
					t_wagoSw.join();
					t_wiz.join();
					
					//update power
					ArrayList<WagoPowerData> powerDatas = powerParser.getParsedWagoPowerData();
			
					//update switches
					ArrayList<WagoSwitchData> switchDatas = switchParser.getParsedSwitchData();
					
					//add uuid's for miele appliances
					for(int i = 0; i < selectParser.myDevices.size(); i++) {
					
						int mieleId = selectParser.myDevices.get(i).getUid();
						UUID uuid = applianceIdMapping.get(mieleId);
						selectParser.myDevices.get(i).setApplianceId(uuid);
					}
				
					//add uuid for measurement
					powerDatas = handleWagoPowerData(powerDatas);
				
					//renew the data list...
					this.wagoPowerDatasList = powerDatas;
					this.wagoSwitchDataList = switchDatas;
					this.mieleAppliancesList = selectParser.myDevices;
					
					//update WIZ
					
					wagoWIZDataList = wizParser.getParsedWagoPowerData();
		
					this.smartHomeDeviceDispatcher.onNewDataReceived();
					
					//I'm still alive!!! 
					if ((imAliveCounter > 1000) || (imAliveCounter == 0)) {
						globalLogger.printSystemMessage("raw data received and still alive at " + Long.toString(System.currentTimeMillis()/1000) + " => [OK]");
						imAliveCounter = 1;
						
					}
					else {
						imAliveCounter++;
					}
					sleep(1000);
				
				} catch (Exception ex) {
					this.globalLogger.logDebug("Error parsing smartHome-Data; also well known as: Shit happens...try it again... ", ex);
				}
			}
			
		}

		public void setSmartHomeDeviceDispatcher(
				SmartHomeDeviceDispatcher smartHomeDeviceDispatcher) {
			this.smartHomeDeviceDispatcher = smartHomeDeviceDispatcher;
		}

}
