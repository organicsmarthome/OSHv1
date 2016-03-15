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


package fal.smartHomeLib.smartHomeMisc;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.w3c.dom.*;

import fal.smartHomeLib.CBTypes.XMLSerialization;
import fal.smartHomeLib.CBTypes.Simulation.VirtualDevicesData.DeviceProfile;
import fal.smartHomeLib.CBTypes.Simulation.VirtualDevicesData.ProfileTick;


/**
 *@author florian.allerding@kit.edu
 *@category smart-home ControllerBox
 *
 */
public class ConvertCSVtoSIMFiles {
	public static void converToDeviceProfile(String sourceFileName, String destFileName) {
		XmlObjectHandler xmlHandler = new XmlObjectHandler();
		ArrayList<Integer> sourceList = CSVImporter.readIntegerList(sourceFileName);
		
		//load XML file (or create it)
		Document outputXML = xmlHandler.createDocument();
		
		Element rootElem = outputXML.createElement("device");
		outputXML.appendChild(rootElem);
		int counter = 0;
		for(Integer activePower: sourceList) {
			
			Element innerNode = outputXML.createElement("tick");
			innerNode.setAttribute("time",Integer.toString(counter));
			Element activePowerNode = outputXML.createElement("activePower");
			Element reactivePowerNode = outputXML.createElement("reactivePower");
			activePowerNode.setTextContent(Integer.toString(activePower));
			reactivePowerNode.setTextContent("0");
			innerNode.appendChild(activePowerNode);
			innerNode.appendChild(reactivePowerNode);
			rootElem.appendChild(innerNode);
			counter++;
		}
		//write Document
		xmlHandler.saveXMLDocument(outputXML, destFileName);
	}
	

	public static void convertScreenplayList(String sourceFileName, String destFileName){
		XmlObjectHandler xmlHandler = new XmlObjectHandler();
		ArrayList<ArrayList<String>> sourceList = CSVImporter.readMultirowList(sourceFileName);
		
		//load XML file (or create it)
		Document outputXML = xmlHandler.createDocument();
		
		Element rootElem = outputXML.createElement("screeplay");
		outputXML.appendChild(rootElem);
		int counter = 0;
		for(ArrayList<String> tickActions: sourceList) {
			
			Element innerNode = outputXML.createElement("tick");
			innerNode.setAttribute("time",Integer.toString(counter));
			
			for (String innerItem: tickActions) {
				Element itemNode = outputXML.createElement("item");
				
				itemNode.setTextContent(innerItem);
				innerNode.appendChild(itemNode);
			}
			rootElem.appendChild(innerNode);
			counter++;
		}
		//write Document
		xmlHandler.saveXMLDocument(outputXML, destFileName);
	}
	
	public static DeviceProfile convertCSV2DeviceProfile(String sourceFileName){
		DeviceProfile _deviceProfile = new DeviceProfile();
		_deviceProfile.setIntelligent(true);
		_deviceProfile.setHasProfile(true);
		
		ArrayList<Integer> _tickList = CSVImporter.readIntegerList(sourceFileName);
		
		for(Integer tick:_tickList){
			ProfileTick _proTick = new ProfileTick();
			_proTick.setActivePower(tick);
			_deviceProfile.getProfileTicks().add(_proTick);
			
		}
		
		return _deviceProfile;
	}
	
	public static void convertCSV2DeviceProfileXML(String sourceFileName, String destFileName){
		
		DeviceProfile _devDeviceProfile = convertCSV2DeviceProfile(sourceFileName);
		
		try {
			XMLSerialization.marshal2File(destFileName, _devDeviceProfile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	

}
