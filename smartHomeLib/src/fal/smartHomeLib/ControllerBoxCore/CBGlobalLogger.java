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




/**
 * Global logger for the controllerbox. Please use this logger for all controllerbox logging issues
 * @author florian.allerding@kit.edu
 *
 */
public class CBGlobalLogger {
	

	private boolean createSingleLogfile = false;

	private boolean logSystemMessages = true;

	private boolean messageCallerTrace = true;

	private boolean systemMessagesEnable = true;

	public CBGlobalLogger(){
		
		if (!createSingleLogfile){
			String timeStamp = String.valueOf(System.currentTimeMillis()/1000L);
			CBLoggerCore.initRootLogger("controllerBoxLog_" +timeStamp , "ERROR", true);
		}
		else {
			CBLoggerCore.initRootLogger("controllerBoxLog", "ERROR");
		}
	}

	private long currentTime(){
		return System.currentTimeMillis()/1000L;
	}

	/**
	 * create a single file for logging and override it every restart
	 * disable this variable and a logfile with timestamp will be created every restart
	 * @return the createSingleLogfile
	 */
	public boolean isCreateSingleLogfile() {
		return createSingleLogfile;
	}

	/**
	 * log system messages in the logfile
	 * @return the logSystemMessages
	 */
	public boolean isLogSystemMessages() {
		return logSystemMessages;
	}

	/**
	 * report the caller class of the log-message while logging 
	 * @return the messageCallerTrace
	 */
	public boolean isMessageCallerTrace() {
		return messageCallerTrace;
	}
	/**
	 * print system messages on the console
	 * @return the systemMessagesEnable
	 */
	public boolean isSystemMessagesEnable() {
		return systemMessagesEnable;
	}
	public void logDebug(Object message) {
		
		if (messageCallerTrace) {
			String[] callerClassNameSpace = Thread.currentThread().getStackTrace()[2].getClassName().split("\\.");
			String callerClassName = callerClassNameSpace[callerClassNameSpace.length-1];
			CBLoggerCore.root_logger.debug("["+currentTime()+ "] DEBUG: " + callerClassName + " : " + message);
		}
		else {
			CBLoggerCore.root_logger.debug(message);
		}
	}
	public void logDebug(Object message, Throwable throwable) {
		if (messageCallerTrace) {
			String[] callerClassNameSpace = Thread.currentThread().getStackTrace()[2].getClassName().split("\\.");
			String callerClassName = callerClassNameSpace[callerClassNameSpace.length-1];
			CBLoggerCore.root_logger.debug("["+currentTime()+ "] DEBUG: " + callerClassName + " : " + message, throwable);
		}
		else {
			CBLoggerCore.root_logger.debug(message, throwable);
		}
	}
	public void logError(Object message){
		if (messageCallerTrace) {
			String[] callerClassNameSpace = Thread.currentThread().getStackTrace()[2].getClassName().split("\\.");
			String callerClassName = callerClassNameSpace[callerClassNameSpace.length-1];
			CBLoggerCore.root_logger.error("["+currentTime()+ "] ERROR: " + callerClassName + " : " + message);
		}
		else {
			CBLoggerCore.root_logger.error(message);
		}
	}
	
	public void logError(Object message, Throwable throwable){
		if (messageCallerTrace) {
			String[] callerClassNameSpace = Thread.currentThread().getStackTrace()[2].getClassName().split("\\.");
			String callerClassName = callerClassNameSpace[callerClassNameSpace.length-1];
			CBLoggerCore.root_logger.error("["+currentTime()+ "] ERROR: " + callerClassName + " : " + message, throwable);
		}
		else {
			CBLoggerCore.root_logger.error(message, throwable);
		}
	}
	
	public void logInfo(Object message){
		
		if (messageCallerTrace) {
			String[] callerClassNameSpace = Thread.currentThread().getStackTrace()[2].getClassName().split("\\.");
			String callerClassName = callerClassNameSpace[callerClassNameSpace.length-1];
			CBLoggerCore.root_logger.info("["+currentTime()+ "] INFO: " + callerClassName + " : " + message);
		}
		else {
			CBLoggerCore.root_logger.info(message);
		}
	}

	public void logInfo(Object message, Throwable throwable){
		if (messageCallerTrace) {
			String[] callerClassNameSpace = Thread.currentThread().getStackTrace()[2].getClassName().split("\\.");
			String callerClassName = callerClassNameSpace[callerClassNameSpace.length-1];
			CBLoggerCore.root_logger.info("["+currentTime()+ "] INFO: " + callerClassName + " : " + message, throwable);
		}
		else {
			CBLoggerCore.root_logger.info(message, throwable);
		}
	}
	
	public void logWarning(Object message){
		if (messageCallerTrace) {
			String[] callerClassNameSpace = Thread.currentThread().getStackTrace()[2].getClassName().split("\\.");
			String callerClassName = callerClassNameSpace[callerClassNameSpace.length-1];
			CBLoggerCore.root_logger.warn("["+currentTime()+ "] WARN: " + callerClassName + " : " + message);
		}
		else {
			CBLoggerCore.root_logger.warn(message);
		}
	}
	
	public void logWarning(Object message, Throwable throwable){
		if (messageCallerTrace) {
			String[] callerClassNameSpace = Thread.currentThread().getStackTrace()[2].getClassName().split("\\.");
			String callerClassName = callerClassNameSpace[callerClassNameSpace.length-1];
			CBLoggerCore.root_logger.warn("["+currentTime()+ "] WARN: " + callerClassName + " : " + message, throwable);
		}
		else {
			CBLoggerCore.root_logger.warn(message, throwable);
		}
	}
	
	/**
	 * System messages can be printed at the console to show the current state of the contollerbox
	 * @param message
	 */
	public void printSystemMessage(Object message){
	
		if (systemMessagesEnable){
			if (messageCallerTrace) {
				String[] callerClassNameSpace = Thread.currentThread().getStackTrace()[2].getClassName().split("\\.");
				String callerClassName = callerClassNameSpace[callerClassNameSpace.length-1];
				System.out.println("[ControllerBox] " + callerClassName + " : " + message);
			}
			else {
				System.out.println("[ControllerBox] "+message);
			}
		}
		if (logSystemMessages) {
			this.logInfo("[ControllerBox] : " + message);	
		}
	}
	
	/**
	 * create a single file for logging and override it every restart
	 * disable this variable and a logfile with timestamp will be created every restart
	 * @param createSingleLogfile the createSingleLogfile to set
	 */
	public void setCreateSingleLogfile(boolean createSingleLogfile) {
		this.createSingleLogfile = createSingleLogfile;
	}
	
	public void setLogLevel(String logLevel) {
		CBLoggerCore.setLogLevel(logLevel);
	}
	
	/**
	 * log system messages in the logfile
	 * @param logSystemMessages the logSystemMessages to set
	 */
	public void setLogSystemMessages(boolean logSystemMessages) {
		this.logSystemMessages = logSystemMessages;
	}
	
	/**
	 *  report the caller class of the log-message while logging 
	 * @param messageCallerTrace the messageCallerTrace to set
	 */
	public void setMessageCallerTrace(boolean messageCallerTrace) {
		this.messageCallerTrace = messageCallerTrace;
	}
	
	/**
	 * print system messages on the console
	 * @param systemMessagesEnable the systemMessagesEnable to set
	 */
	public void setSystemMessagesEnable(boolean systemMessagesEnable) {
		this.systemMessagesEnable = systemMessagesEnable;
	}


}
