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


package fal.rsh.driver.data;

import java.util.LinkedList;
import java.util.UUID;


public class MieleAppliance{


	private long timestamp;
	private long cycleID;
	private String homebusURL;
	private int classe;
	private int uid;
	private String type;
	private String name;
	private int stateID;
	private String addName;
	private int roomID;
	private String roomLevel;
	private String roomName;
	private String detailURL;
	private String detailName;
	
	private UUID applianceId;
			
	private LinkedList<MieleApplianceDetails> addInfoList; 
	private LinkedList<MieleApplianceDetails> addActionList; 

	// PowerURL
	private String powerURL;
	// Power-Integer
	private double power;
	private int pId;
	
	public double getPower() {
		return power;
	}
	
	public void setPower(double power) {
		this.power = power;
	}
	
	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public int getpId() {
		return pId;
	}

	public void setpId(int pId) {
		this.pId = pId;
	}

	public String getDetailName() {
		return detailName;
	}

	public void setDetailName(String detailName) {
		this.detailName = detailName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getClasse() {
		return classe;
	}

	public void setClasse(int classe) {
		this.classe = classe;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public int getStateID() {
		return stateID;
	}

	public void setStateID(int stateID) {
		this.stateID = stateID;
	}

	public String getAddName() {
		return addName;
	}

	public void setAddName(String addName) {
		this.addName = addName;
	}

	public int getRoomID() {
		return roomID;
	}

	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}

	public String getRoomName() {
		return roomName;
	}
	
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	
	public String getRoomLevel() {
		return roomLevel;
	}

	public void setRoomLevel(String roomLevel) {
		this.roomLevel = roomLevel;
	}

	public String getDetailURL() {
		return detailURL;
	}

	public void setDetailURL(String detailURL) {
		this.detailURL = detailURL;
	}

	public LinkedList<MieleApplianceDetails> getAddInfoList() {
		return addInfoList;
	}

	public void setAddInfoList(LinkedList<MieleApplianceDetails> addInfoList) {
		this.addInfoList = addInfoList;
	}

	public String getPowerURL() {
		return powerURL;
	}

	public void setPowerURL(String powerURL) {
		this.powerURL = powerURL;
	}

	public long getCycleID() {
		return cycleID;
	}

	public void setCycleID(long cycleID) {
		this.cycleID = cycleID;
	}
	
	public String getHomebusURL() {
		return homebusURL;
	}

	public void setHomebusURL(String homebusURL) {
		this.homebusURL = homebusURL;
	}
	
	public LinkedList<MieleApplianceDetails> getAddActionList() {
		return addActionList;
	}

	public void setAddActionList(LinkedList<MieleApplianceDetails> addActionList) {
		this.addActionList = addActionList;
	}

	/**
	 * @return the applianceId
	 */
	public UUID getApplianceId() {
		return applianceId;
	}

	/**
	 * @param applianceId the applianceId to set
	 */
	public void setApplianceId(UUID applianceId) {
		this.applianceId = applianceId;
	}
}

