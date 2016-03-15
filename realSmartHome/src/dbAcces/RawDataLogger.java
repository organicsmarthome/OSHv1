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


package dbAcces;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import fal.rsh.driver.data.MieleAppliance;
import fal.rsh.driver.data.MieleApplianceDetails;
import fal.rsh.driver.data.WagoPowerData;
import fal.rsh.driver.data.WagoSwitchData;

public class RawDataLogger {
	
	private String dbHost = null;
	private String dbPort = null;
	private String dbName = null;
	private String dbUser = null;
	private String dbPasswd = null;
	private UUID dbcycleID;
	
	MysqlConnect mysqlConnect = null;
	
	private HashMap<Integer,Double> lastPowerValues;
	
	private void initDBConnection() throws SQLException{
		mysqlConnect = new MysqlConnect();
		
		mysqlConnect.connect(dbHost, dbPort, dbName, dbUser, dbPasswd);
		
		mysqlConnect.getConnection().setAutoCommit(false);
	}
	
	public RawDataLogger(){
		
		this("localhost","3306","organicsmarthome","dbUser","dbPasswd");
		
	}
	
	public RawDataLogger(String dbHost,String dbPort,String dbName,String dbUser,String dbPasswd){
		this.dbHost = dbHost;
		this.dbPort = dbPort;
		this.dbName = dbName;
		this.dbUser = dbUser;
		this.dbPasswd = dbPasswd;
		
		try {
			initDBConnection();
			}
			catch (Exception ex) {
				ex.printStackTrace();
				return;
			}
			
		lastPowerValues = new HashMap<Integer, Double>();
		
	}
	
	private void sqlReconnect() throws SQLException{
		boolean connState = false;
	
		connState = mysqlConnect.getConnection().isClosed();
	
		if (!connState){
			
			mysqlConnect = new MysqlConnect();
			
			mysqlConnect.connect(dbHost, dbPort, dbName, dbUser, dbPasswd);
			
			mysqlConnect.getConnection().setAutoCommit(false);
		}
	}
	
	public synchronized void writeToDB(List<WagoPowerData> wagoPowerDatas, List<MieleAppliance> mieleAppliances, List<WagoSwitchData> wagoSwitchDatas, List<WagoPowerData> wizData) throws SQLException,Exception{
		dbcycleID = UUID.randomUUID();
		writecurrentEnergyMnmtPanelData(wagoPowerDatas, mieleAppliances, wagoSwitchDatas);
		writeRawPowerData(wagoPowerDatas);
		writeLastPowerValues(wagoPowerDatas);
		writeRawWizData(wizData);
		writeCurrentWizData(wizData);
		
	}
	private int calculateMeterID(int controllerId,WagoPowerData powerData){
		int idValue = 0;
		idValue = controllerId*1000 +  powerData.getMeterId()*10 + powerData.getMeterPortId();
		return idValue;
	}
	
	private boolean powerDataHasChanged(WagoPowerData powerData){
		boolean dataChanged = false;
		double oldData = 0.0;
		double newData = 0.0;
		//inital:
		if (!lastPowerValues.isEmpty()) {
			oldData = lastPowerValues.get(Integer.valueOf(calculateMeterID(1, powerData)));
			newData = powerData.getPower();
			
			if (Math.abs(oldData-newData) > 5) {
				dataChanged = true;
			}
			
			return dataChanged;
		}
		else {
			return true;
		}
		
	}
		
	
	private void writeLastPowerValues(List<WagoPowerData> wagoPowerDatas){
		lastPowerValues.clear();
		for (WagoPowerData powerData: wagoPowerDatas) {
			lastPowerValues.put(Integer.valueOf(calculateMeterID(1, powerData)), Double.valueOf(powerData.getPower()));
		}
	}
	private void writeRawPowerData(List<WagoPowerData> wagoPowerDatas) throws SQLException{
		Statement statement = null;
		String tabelle = null;
		String query = null;
		
		try {
				//create statement
				statement = mysqlConnect.getConnection().createStatement();
				
				for (WagoPowerData powerData: wagoPowerDatas) {
					
					if (powerDataHasChanged(powerData)) {
						tabelle = "powerdetails";
						query = "INSERT INTO `" + tabelle + "`" +
								"(`timestamp`, `cycle`, `controller`, `meter`, `port`, `voltage`,`current`,`power`,`energy`) " +
								"VALUES (" +
								"'"+ (System.currentTimeMillis()/1000L) + "', " +
								"'"+ dbcycleID + "', " +
								"'" + 1 + "', " +
								"'" + powerData.getMeterId() + "', " +
								"'" + powerData.getMeterPortId() + "', " +
								"'" + powerData.getVoltage() + "', " +
								"'" + powerData.getCurrent() + "', " +
								"'" + powerData.getPower() + "', " +
								"'" + powerData.getEnergy()  + "'" +
								")";
						statement.execute(query);
					}
				}
			
				//commit connection
				mysqlConnect.getConnection().commit();
				if (statement != null) {
					statement.close();
				}
			
			}
			catch (SQLException sqex) {
				if (statement != null) {
					statement.close();
				}
				throw new SQLException(sqex);
			}
	}
	
	/**
	 * dirty little helper...
	 * @param powerData
	 * @return
	 */
	private boolean isValueWorthrtoBeWrittenInDataBase(WagoPowerData powerData){
		
		int meterId = powerData.getMeterId();
		int portId = powerData.getMeterPortId();
		
		//log sum
		if (meterId == 0){
			return true;
		}
		//log el heating
		if (meterId == 2) {
			return true;
		}
		//log pv
		if (meterId == 3) {
			return true;
		}
		return false;
	}
	
	private void writeCurrentWizData(List<WagoPowerData> wizDatas) throws Exception{
		Statement statement = null;
		String tabelle = "wiz_actual";
		String query = null;
		
		try {
			//create statement
			statement = mysqlConnect.getConnection().createStatement();
			
			//delete all the junk
			query = "DELETE FROM `" + tabelle + "`";
			statement.execute(query);
			
			//write it baby!
			for (WagoPowerData powerData: wizDatas) {
				
					query = "INSERT INTO `" + tabelle + "`" +
							"(`timestamp`, `cycle`, `controller`, `meter`, `port`, `voltage`,`current`,`power`,`energy`) " +
							"VALUES (" +
							"'"+ (System.currentTimeMillis()/1000L) + "', " +
							"'"+ dbcycleID + "', " +
							"'" + 3 + "', " +
							"'" + powerData.getMeterId() + "', " +
							"'" + powerData.getMeterPortId() + "', " +
							"'" + powerData.getVoltage() + "', " +
							"'" + (powerData.getCurrent()/4) + "', " +
							"'" + (powerData.getPower()/4) + "', " +
							"'" + (powerData.getEnergy()/4)  + "'" +
							")";
					statement.execute(query);
				
			}
		
			//commit connection
			mysqlConnect.getConnection().commit();
			if (statement != null) {
				statement.close();
			}
		
		}
		catch (Exception sqex) {
			if (statement != null) {
				statement.close();
			}
			throw new Exception(sqex);
		}
	}

	private void writeRawWizData(List<WagoPowerData> wizDatas) throws Exception{
		
		Statement statement = null;
		String tabelle = null;
		String query = null;
		
		try {
				//create statement
				statement = mysqlConnect.getConnection().createStatement();
				
				
				for (WagoPowerData powerData: wizDatas) {

					//now the dirty next dirty hack: we only want to write the sum and the PV-data to the db
					if (isValueWorthrtoBeWrittenInDataBase(powerData)) {
						query = "INSERT INTO `" + "wiz" + "`" +
								"(`timestamp`, `cycle`, `controller`, `meter`, `port`, `voltage`,`current`,`power`,`energy`) " +
								"VALUES (" +
								"'"+ (System.currentTimeMillis()/1000L) + "', " +
								"'"+ dbcycleID + "', " +
								"'" + 3 + "', " +
								"'" + powerData.getMeterId() + "', " +
								"'" + powerData.getMeterPortId() + "', " +
								"'" + powerData.getVoltage() + "', " +
								"'" + (powerData.getCurrent()/4) + "', " +
								"'" + (powerData.getPower()/4) + "', " +
								"'" + (powerData.getEnergy()/4)  + "'" +
								")";
						statement.execute(query);
					}
				}
			
				//commit connection
				mysqlConnect.getConnection().commit();
				if (statement != null) {
					statement.close();
				}
			
			}
			catch (Exception sqex) {
				if (statement != null) {
					statement.close();
				}
				throw new Exception(sqex);
			}
	}
	private void writecurrentEnergyMnmtPanelData(List<WagoPowerData> wagoPowerDatas, List<MieleAppliance> mieleAppliances, List<WagoSwitchData> wagoSwitchDatas) throws SQLException{
	
		Statement statement = null;
		String tabelle = null;
		String query = null;
		String appKey;
		String appValue;
			
		try {
		
		//do we need a reconnection???
		//this.sqlReconnect();
		
		statement = mysqlConnect.getConnection().createStatement();
		
		//first delete the table "details"; show only the current state!
		tabelle = "details";
		query = "DELETE FROM `" + tabelle + "`";
		statement.execute(query);
		
		//first delete the table "miele_actions"; show only the current actions!
		tabelle = "miele_actions";
		query = "DELETE FROM `" + tabelle + "`";
		statement.execute(query);
		
		//insert the current state of the miele appliances into the table "details"
		for (MieleAppliance mieleAppliance: mieleAppliances){
			
			for (MieleApplianceDetails applianceDetails: mieleAppliance.getAddInfoList()) {
				
				if (mieleAppliance.getApplianceId() != null){
					appKey = applianceDetails.getName();
					appValue = applianceDetails.getValue();
					tabelle = "details";
					query = "INSERT INTO `" + tabelle + "`" +
							"(`timestamp`, `cycle`, `uuid`, `key`, `value`) " +
							"VALUES (" +
							"'"+ (System.currentTimeMillis()/1000L) + "', " +
							"'" + dbcycleID + "', " +
							"'" + mieleAppliance.getApplianceId() + "', " +
							"'" + appKey + "', " +
							"'" + appValue + "'" +
							")";
					statement.execute(query);
				}
			}
			
			for (MieleApplianceDetails applianceDetails: mieleAppliance.getAddActionList()) {
				appKey = applianceDetails.getName();
				appValue = applianceDetails.getValue();
				tabelle = "miele_actions";
				query = "INSERT INTO `" + tabelle + "`" +
						"(`uuid`, `name`, `url`) " +
						"VALUES (" +
						"'"+ mieleAppliance.getApplianceId() + "', " +
						"'" + appKey + "', " +
						"'" + appValue + "'" +
						")";
				statement.execute(query);
			}		
		}
		
		//some dirty hack for the integration of the fridge and the deep-freezer /////////
		query = "INSERT INTO `" + "details" + "`" +
		"(`timestamp`, `cycle`, `uuid`, `key`, `value`) " +
		"VALUES (" +
		"'"+ (System.currentTimeMillis()/1000L) + "', " +
		"'" + dbcycleID + "', " +
		"'" + "9504ab14-2f4a-4c4b-a0ec-9b7e7fc3b1ce" + "', " +
		"'" + "State" + "', " +
		"'" + "On" + "'" +
		")";
		statement.execute(query);
		query = "INSERT INTO `" + "details" + "`" +
		"(`timestamp`, `cycle`, `uuid`, `key`, `value`) " +
		"VALUES (" +
		"'"+ (System.currentTimeMillis()/1000L) + "', " +
		"'" + dbcycleID + "', " +
		"'" + "149d0b2e-a893-4172-ae87-9f5014cc5bf5" + "', " +
		"'" + "State" + "', " +
		"'" + "On" + "'" +
		")";
		statement.execute(query);
		//end of the dirtyhacks////////////
	
		//insert now the power data (with history; they will not be delete!)
		for (WagoPowerData powerData: wagoPowerDatas){
			
			if (powerData.getAssociatedApplianceId() != null) {
				tabelle = "details";
				query = "INSERT INTO `" + tabelle + "`" +
				"(`timestamp`, `cycle`, `uuid`, `key`, `value`) " +
				"VALUES (" +
				"'"+ powerData.getTimeStamp() + "', " +
				"'" + dbcycleID + "', " +
				"'" + powerData.getAssociatedApplianceId() + "', " +
				"'power', " +
				"'" + powerData.getPower() + "'" +
				")";
				statement.execute(query);
			}
		}
		
		//delete first the switch data: show only the current state!
		tabelle = "switches";
		query = "DELETE FROM `" + tabelle + "`";
		statement.execute(query);
		
		//insert the current switch state
		for (WagoSwitchData switchData: wagoSwitchDatas){
			
			if (switchData.getUuid() != null) {
				tabelle = "switches";
				query = "INSERT INTO `" + tabelle + "`" +
				"(`uuid`, `state`, `timestamp`) " +
				"VALUES (" +
				"'"+ switchData.getUuid() + "', " +
				"'" + switchData.getState() + "'," +
				"'" + switchData.getTimeStamp() + "'" +
				")";
				statement.execute(query);
			}
		}
		
		//commit connection
		mysqlConnect.getConnection().commit();
		
		//close the statement
		if (statement != null) {
			statement.close();
		}
		
		}
		catch (SQLException sqex) {
			if (statement != null) {
				statement.close();
			}
			throw new SQLException(sqex);
		}
	
	}


}
