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


package fal.smartHomeLib.CBTypes;

import java.util.HashMap;
import java.util.List;

import fal.smartHomeLib.CBTypes.Configuration.System.ConfigurationParameter;


/**
 * internal representation for name-value-pairs used by the controllerbox
 * 
 *@author florian.allerding@kit.edu
 *@category smart-home ControllerBox
 *
 */
public class CBParameterCollection {
	
	private HashMap<String, String> parameterCollection;
	
	public CBParameterCollection() {
		this.parameterCollection = new HashMap<String, String>();
	}
	
	public String getParameter(String name){
		
		String _retStr = "";
		
		_retStr = parameterCollection.get(name);
		
		return _retStr;
	}
	
	public void setParameter(String name, String value){
		
		parameterCollection.put(name, value);
	}
	
	public void loadCollection(List<ConfigurationParameter> configParam){

		for(int i = 0; i < configParam.size(); i++){
			parameterCollection.put(configParam.get(i).getParameterName(), configParam.get(i).getParameterValue());
		}
		
	}

}