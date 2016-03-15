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

import java.io.File;
import java.io.IOException;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;


/**
 * central static logger class for the controllerbox. will be initialized at the controllerboxManager's ctor
 * 
 *@author florian.allerding@kit.edu
 *@category smart-home ControllerBox
 *
 */
public class CBLoggerCore {

	/**
	 * For creating a log-massage use this logger like:
	 * root_logger.warn("message");
	 */
	public static Logger root_logger = null;
	
	/**
	 * initialize a root Logger; for logging always use the root_logger !!!
	 * @param logFileName
	 * @param logLevel
	 */
	public static void initRootLogger(String logFileName, String logLevel) {
		initRootLogger(logFileName, logLevel, false);
	}
	
	/**
	 * initialize a root Logger; for logging always use the root_logger !!!
	 * @param logFileName
	 * @param logLevel
	 * @param createSingleLogfile
	 */
	public static void initRootLogger(String logFileName, String logLevel, boolean createSingleLogfile) {	
		
		logFileName = logFileName +".log";
		if(!createSingleLogfile){
			// delete any existing log files
			deleteLogDirectory(new File("logs"));
			
			createLogFile(logFileName);
		
		}
		else {
			createLogFile(logFileName);
		}
		FileAppender fileAppender = null;
		
		try {
			fileAppender = new FileAppender(new PatternLayout(),"logs/" + logFileName);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (root_logger == null)
			root_logger = Logger.getRootLogger();

//		Appender appender = null;
		
		root_logger.addAppender(fileAppender);
		
		try {
			root_logger.setLevel(Level.toLevel(logLevel));


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * sets a specific loglevel like "warn", "info", ...
	 * @param logLevel
	 */
	public static void setLogLevel(String logLevel){

		root_logger.setLevel(Level.toLevel(logLevel));
	}
	private static void createLogFile(String fileName) {
		File logfile = null;
		
		logfile = new File("logs");
		logfile.mkdir();
		
		logfile = new File ("logs/" + fileName);
		
		try {
//			System.out.println("file name: " + logfile.getAbsolutePath());
			if (!logfile.exists()) {
				logfile.createNewFile();
			}
		} catch (IOException e) {
			//e.printStackTrace();
		}
		
	}
	
	static public boolean deleteLogDirectory(File path) {		
		if (path.exists()) {
			File[] files = path.listFiles();
			boolean deleted = false;
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteLogDirectory(files[i]);
				} else {
					 deleted = files[i].delete();
				}
			}
		}
		return (path.delete());
	}
	

}
