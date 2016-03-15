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

import java.util.UUID;

public class WagoPowerData {
private double voltage;
private double current;
private double power;
private double energy;
private int wagoControllerId;
private int meterId;
private int meterPortId;
private UUID associatedApplianceId;
private long timeStamp;

/**
 * @return the voltage
 */
public double getVoltage() {
	return voltage;
}
/**
 * @param voltage the voltage to set
 */
public void setVoltage(double voltage) {
	this.voltage = voltage;
}
/**
 * @return the current
 */
public double getCurrent() {
	return current;
}
/**
 * @param current the current to set
 */
public void setCurrent(double current) {
	this.current = current;
}
/**
 * @return the power
 */
public double getPower() {
	return power;
}
/**
 * @param power the power to set
 */
public void setPower(double power) {
	this.power = power;
}
/**
 * @return the energy
 */
public double getEnergy() {
	return energy;
}
/**
 * @param energy the energy to set
 */
public void setEnergy(double energy) {
	this.energy = energy;
}
/**
 * @return the wagoControllerId
 */
public int getWagoControllerId() {
	return wagoControllerId;
}
/**
 * @param wagoControllerId the wagoControllerId to set
 */
public void setWagoControllerId(int wagoControllerId) {
	this.wagoControllerId = wagoControllerId;
}
/**
 * @return the meterId
 */
public int getMeterId() {
	return meterId;
}
/**
 * @param meterId the meterId to set
 */
public void setMeterId(int meterId) {
	this.meterId = meterId;
}
/**
 * @return the meterPortId
 */
public int getMeterPortId() {
	return meterPortId;
}
/**
 * @param meterPortId the meterPortId to set
 */
public void setMeterPortId(int meterPortId) {
	this.meterPortId = meterPortId;
}
/**
 * @return the uuid
 */
public UUID getAssociatedApplianceId() {
	return associatedApplianceId;
}
/**
 * @param uuid the uuid to set
 */
public void setAssociatedApplianceId(UUID uuid) {
	this.associatedApplianceId = uuid;
}
/**
 * @return the timeStamp
 */
public long getTimeStamp() {
	return timeStamp;
}
/**
 * @param timeStamp the timeStamp to set
 */
public void setTimeStamp(long timeStamp) {
	this.timeStamp = timeStamp;
}
}
