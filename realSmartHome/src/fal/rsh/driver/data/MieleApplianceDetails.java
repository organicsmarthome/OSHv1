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


public class MieleApplianceDetails {
	private String value;
	private String name;
	
	public MieleApplianceDetails(String value, String name){
		this.name= name;
		this.value= value;
	}
	public String getValue() {
		return value;
	}
	public String getName() {
		return name;
	}
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n" + " Name: " + getName());
		sb.append(" Value: " + getValue());
		return sb.toString();
	}
	
}


