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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import fal.smartHomeLib.ControllerBoxCore.CBGlobalLogger;


public class PriceSignalThread extends Thread {
	private static Connection priceSQLconnection;
	private CBGlobalLogger globalLogger;
	private PriceSignalProvider priceSignalProvider;
	private double[] priceInMinutes;
	public PriceSignalThread(CBGlobalLogger globalLogger, PriceSignalProvider priceSignalProvider) {
		super();
		this.globalLogger = globalLogger;
		this.priceSignalProvider = priceSignalProvider;
		priceInMinutes = new double[2160];
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		
		try {
			getPriceSignalData();
			priceSignalProvider.processPriceSignal(priceInMinutes);
			sleep(3600000);
			while(true) {
				
				try {
					getPriceSignalData();
					priceSignalProvider.processPriceSignal(priceInMinutes);
					sleep(3600000);
					
				} catch (InterruptedException e) {		
					globalLogger.logError(e);
				}			
			}
		} catch (Exception e) {
			globalLogger.logError(e);
		}
	}
	
	public void setUpSQLConnection(String dbHost, String dbPort, String dbName, String dbUser, String dbPasswd) throws SQLException {
		
		globalLogger.logDebug("* establishing SQL connection for priceSignal driver..."); 
	    
		//set db adress DB
		String host_url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
		
		priceSQLconnection = DriverManager.getConnection(host_url, dbUser, dbPasswd);
		
		globalLogger.logDebug("* ...SQL connection for priceSignal driver OK"); 
		
	}

	/**
	 * @return the priceSQLconnection
	 */
	public Connection getPriceSQLconnection() {
		return priceSQLconnection;
	}	

	private void getPriceSignalData(){
		Statement statement = null;
		String query = null;
		ResultSet resultSet = null;
		
		try {
			statement = this.getPriceSQLconnection().createStatement();

			query = "SELECT * FROM sps";
			statement.execute(query);
			
			resultSet = statement.getResultSet();
			
			int i = 0;
			while (resultSet.next()) {
				
				//UUID appId = UUID.fromString(resultSet.getString("ApplianceID"));
				Integer timeStamp = Integer.valueOf(resultSet.getInt("timestamp"));
				
				int startTimeForSPS = (int) (System.currentTimeMillis()/1000L);
				//get the sps for the next 36h!
				if ((timeStamp >= startTimeForSPS) && (i < 2160)){
					
						priceInMinutes[i] = Double.parseDouble(Integer.toString(resultSet.getInt("price")));
						i++;
					}	
				}
		}
		catch (Exception ex) {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {

				}
			}
			globalLogger.logError("getting DofData failed!", ex);
		}
	}
}
