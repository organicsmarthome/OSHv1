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

import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.NoRouteToHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import dbAcces.RawDataLogger;
import dbAcces.MysqlConnect;
import fal.rsh.driver.data.WagoPowerData;
import fal.rsh.driver.data.WagoSwitchData;
import fal.rsh.driver.parser.Wago.WagoPowerParser;
import fal.rsh.driver.parser.Wago.WagoSwitchParser;
import fal.rsh.driver.parser.data.WagoSwitchIdMapping;
import fal.rsh.driver.parser.mieleGateway.MieleGatewayParser;

/**
 * @author florian.allerding@kit.edu
 * @category smart-home  Driver-Parser
 * @deprecated only for the first "living-phase" of the smart-Home
 */
public class SmartHomeDataParser extends Thread{

	private HashMap<Integer,UUID> applianceIdMapping;
	private HashMap<Integer, UUID> mesurementIdMapping;
	
	private MieleGatewayParser selectParser = null;
	private WagoPowerParser powerParser = null;
	private WagoSwitchParser switchParser = null;
	private String urlToWagoSystem = "http://192.168.1.51/xmlwago/ramdisk/values.xml";
	private int WagoControllerId = 1; 
	private RawDataLogger dbWriter;
	public SmartHomeDataParser(){
		this.initApplianceIds();
		this.initPowermesurementIds();
		try {
			initParser();
		}
		catch (Exception ex) {
			ex.getStackTrace();
			return;
		}
	}
		
	private void initParser() throws Exception{
			
		selectParser = new MieleGatewayParser("http://192.168.1.20/homebus");
		powerParser = new WagoPowerParser(urlToWagoSystem, WagoControllerId);
		switchParser = new WagoSwitchParser("http://192.168.1.52/xmlwago/ramdisk/values.xml", 2, initSwitchIdMappings());

		dbWriter = new RawDataLogger();
		
	}
	
	private HashMap<Integer, UUID> initSwitchIdMappings(){
		
		HashMap<Integer, UUID> switchIdMapping = new HashMap<Integer, UUID>();
		//Licht schlafen1
		switchIdMapping.put(2015, UUID.fromString("962a7c0e-6022-48ab-98d4-4cf0d2a3ab2e"));
		//Licht schlafen2
		switchIdMapping.put(2016, UUID.fromString("1b2cf8e6-b4b8-4810-8880-db845783f697"));
		//Licht Wozi_vorn
		switchIdMapping.put(2017, UUID.fromString("5d7e113c-4193-4c52-8444-3886d2a76080"));
		//Licht Wozi_mitte
		switchIdMapping.put(2018, UUID.fromString("ea26e5ab-c18e-4869-a8be-89c4746dda67"));
		//Licht Kueche
		switchIdMapping.put(2019, UUID.fromString("01d097f8-8704-416e-bf22-db42f189f0df"));
		//Licht Bad
		switchIdMapping.put(2020, UUID.fromString("1e66542c-dd85-4902-a190-385edb36df13"));
		
		return switchIdMapping;
		
	}

	private void initApplianceIds(){
		applianceIdMapping = new HashMap<Integer, UUID>();
		//Kochfeld
		applianceIdMapping.put(-1609550966, UUID.fromString("e2ef0d13-61b3-4188-b32a-1570dcbab4d1"));
		//Kaffeevollautomat
		applianceIdMapping.put(-1609551312, UUID.fromString("de61f462-cda2-4941-8402-f93a1f1b3e57"));
		//Geschirrspüler
		applianceIdMapping.put(-1609555510, UUID.fromString("ab9519db-7a14-4e43-ac3a-ade723802194"));
		//Backofen
		applianceIdMapping.put(-1609555623, UUID.fromString("cef732b1-04ba-49e1-8189-818468a0d98e"));
		//Wäschetrockner
		applianceIdMapping.put(-1609555628, UUID.fromString("1468cc8a-dfdc-418a-8df8-96ba8c146156"));
		//Waschautomat
		applianceIdMapping.put(-1609555631, UUID.fromString("e7b3f13d-fdf6-4663-848a-222303d734b8"));
	}
	
	private void initPowermesurementIds(){
		mesurementIdMapping = new HashMap<Integer, UUID>();
		
		//Kochfeld
		mesurementIdMapping.put(1080, UUID.fromString("e2ef0d13-61b3-4188-b32a-1570dcbab4d1"));
		//Kaffeevollautomat
		mesurementIdMapping.put(1061, UUID.fromString("de61f462-cda2-4941-8402-f93a1f1b3e57"));
		//Geschirrspüler
		mesurementIdMapping.put(1032, UUID.fromString("ab9519db-7a14-4e43-ac3a-ade723802194"));
		//Backofen
		mesurementIdMapping.put(1042, UUID.fromString("cef732b1-04ba-49e1-8189-818468a0d98e"));
		//Wäschetrockner
		mesurementIdMapping.put(1030, UUID.fromString("1468cc8a-dfdc-418a-8df8-96ba8c146156"));
		//Waschautomat
		mesurementIdMapping.put(1072, UUID.fromString("e7b3f13d-fdf6-4663-848a-222303d734b8"));
		//Tiefkuehler
		mesurementIdMapping.put(1051, UUID.fromString("bb4ae893-e71e-4269-ad7e-4f4b5c8ac5aa"));
	
	}
	
private WagoPowerData getPowerDatebyPort(ArrayList<WagoPowerData> wagoPowerDatas, int meterId, int portId){
	
	for(WagoPowerData powerData: wagoPowerDatas){
		if ((powerData.getMeterId() == meterId) && (powerData.getMeterPortId()== portId)) {
			return powerData;
		}
	}
	
	return null;
}
	
 private ArrayList<WagoPowerData> dirtyHackforSomeThingWeforgotToImplement(ArrayList<WagoPowerData> powerData){
	 ArrayList<WagoPowerData> wagoPowerData = powerData;	 	
	 for(int i = 0; i < wagoPowerData.size(); i ++){
		 int currentId;

		currentId = 1000 +  wagoPowerData.get(i).getMeterId()*10 + wagoPowerData.get(i).getMeterPortId();
		 if (mesurementIdMapping.containsKey(currentId)){
			 wagoPowerData.get(i).setAssociatedApplianceId(mesurementIdMapping.get(currentId));
		 }
		 
		 //and it gets much dirtier..
		 if (currentId == 1080) {
			 //fake the values
			 double power = 0.0;
			 double power0 = 0.0;
			 double power1 = 0.0;
			 double power2 = 0.0;
			 //get the rest
			 
			 power0 = wagoPowerData.get(i).getPower();
			 power1 = getPowerDatebyPort(wagoPowerData, 8, 1).getPower();
			 power2 = getPowerDatebyPort(wagoPowerData, 8, 2).getPower();
			 
			 power = power0+power1+power2;
			 wagoPowerData.get(i).setPower(power);
		 }
	 }
	 
	 
	 return wagoPowerData;
 }

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		
		super.run();
		int counter = 0;
		while (true) {
			
			if (counter > 300) {
				Runtime.getRuntime().gc();
				counter = 0;
			}
			++counter;
			
			try {
				//update devices
				selectParser.runUpdate();


			
				//update power
				ArrayList<WagoPowerData> powerDatas = null;
			
				powerDatas = powerParser.getPowerData();


			
				//update switches
				ArrayList<WagoSwitchData> switchDatas = null;
				switchDatas = switchParser.getSwitchData();
				
				//add uuid's for miele appliances
			
				for(int i = 0; i < selectParser.myDevices.size(); i++) {
				
					int mieleId = selectParser.myDevices.get(i).getUid();
					UUID uuid = applianceIdMapping.get(mieleId);
					selectParser.myDevices.get(i).setApplianceId(uuid);
				}
			
				//add uuid for measurement
			
				powerDatas = dirtyHackforSomeThingWeforgotToImplement(powerDatas);
			
				//write to database
			
				dbWriter.writeToDB(powerDatas, selectParser.myDevices, switchDatas, new ArrayList<WagoPowerData>());
				System.out.println("data wrote at " + Long.toString(System.currentTimeMillis()) + " => [OK]");
	
			
				//System.out.println("still alive at " + Long.toString(System.currentTimeMillis()) + " => [OK]");
			
				sleep(1000);

			
			
			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("Shit happens...try it again...");
			}
		}
		
	}
}
