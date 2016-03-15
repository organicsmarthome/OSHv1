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

import java.sql.*;

public class MysqlConnect {

	private static Connection mysql_conn;

	public static void connect(String host_ip, String port, String dbname, String user, String password) throws SQLException {
		
		System.out.println("* establishing SQL connection..."); 
	    
		String host_url = "jdbc:mysql://" + host_ip + ":" + port + "/" + dbname;
		
		mysql_conn = DriverManager.getConnection(host_url, user, password);
		System.out.println("* ...SQL connection OK"); 
	}
	

	public static Connection getConnection() {
		return mysql_conn;
	}

	public static boolean close() {
		try {
			mysql_conn.close();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
}
