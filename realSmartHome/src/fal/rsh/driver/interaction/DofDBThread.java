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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import dbAcces.MysqlConnect;
import fal.shmd.globalunits.Controller.ApplianceSchedule;
import fal.smartHomeLib.ControllerBoxCore.CBGlobalLogger;
import fal.smartHomeLib.ControllerBoxCore.OC.GlobalController;

public class DofDBThread extends Thread {
	
private static Connection dofSQLconnection;
	
	/**
 * @return the dofSQLconnection
 */
public Connection getDofSQLconnection() {
	return dofSQLconnection;
}

	private CBGlobalLogger globalLogger;
	private HashMap<UUID, Integer> applianceDof;
	private UserInteractionProvider userInteractionProvider;
	
	public DofDBThread(CBGlobalLogger globalLogger, UserInteractionProvider userInteractionProvider) {
		super();
		this.globalLogger = globalLogger;
		applianceDof = new HashMap<UUID, Integer>();
		this.userInteractionProvider = userInteractionProvider;
	}



	public void setUpSQLConnection(String dbHost, String dbPort, String dbName, String dbUser, String dbPasswd) throws SQLException {
	
		globalLogger.logDebug("* establishing SQL connection for DoF driver..."); 
	    
		//set db adress DB
		String host_url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
		
		dofSQLconnection = DriverManager.getConnection(host_url, dbUser, dbPasswd);
		
		globalLogger.logDebug("* ...SQL connection for DoF driver OK"); 
		
	}


	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		super.run();
		
		while (true) {
			
			try {
				getDofData();
				this.userInteractionProvider.processDofInformation(applianceDof);
				this.renewApplianceScheduleTable();
				sleep(1000);
			
			} catch (Exception e) {
				globalLogger.logError("transfering user interaction data faild", e);
			}
		}
		
	}
	
	private void renewApplianceScheduleTable(){
		Statement statement = null;
		String query = null;
		try {
			//get the connection
			statement = dofSQLconnection.createStatement();
			//set the autocommit to false
			dofSQLconnection.setAutoCommit(false);
			//first delete old data
			query = "DELETE FROM `" + "appliance_schedule" + "`";
			statement.execute(query);
			
			ArrayList<ApplianceSchedule> applianceSchedule = this.userInteractionProvider.triggerCommManager();
			
			for (int i = 0; i < applianceSchedule.size(); i++){
				
				String devId = applianceSchedule.get(i).deviceID.toString();
				int startTime = (int) applianceSchedule.get(i).StartTime;

				query = "INSERT INTO `" + "appliance_schedule" + "`" +
						"(`uuid`, `scheduled_at`, `scheduled_to`) " +
						"VALUES (" +
						"'"+ devId + "', " +
						"'" + (System.currentTimeMillis()/1000L) + "', " +
						"'" + startTime + "'" +
						")";
				statement.execute(query);
			}
			//commit the query
			dofSQLconnection.commit();
			
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					
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
			globalLogger.logError("acualizing of the DOF table failed",ex);
		}
		finally {
			//accept now the auto commit again
			try {
				dofSQLconnection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}
	}
	
	private void getDofData(){
		Statement statement = null;
		String query = null;
		ResultSet resultSet = null;
		
		try {
			statement = dofSQLconnection.createStatement();
			
			query = "SELECT * FROM appliance_dof";
			statement.execute(query);
			//dofSQLconnection.commit();
			
			resultSet = statement.getResultSet();
			
			while (resultSet.next()) {
				
				UUID appId = UUID.fromString(resultSet.getString("uuid"));
				Integer appDof = Integer.valueOf(resultSet.getInt("dof"));
				String Test = appId.toString();
			
				applianceDof.put(appId, appDof);
			}
			statement.close();
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
