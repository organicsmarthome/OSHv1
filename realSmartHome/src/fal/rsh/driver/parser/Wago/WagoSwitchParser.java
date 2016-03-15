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


package fal.rsh.driver.parser.Wago;


import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


import fal.rsh.driver.data.WagoPowerData;
import fal.rsh.driver.data.WagoSwitchData;



public class WagoSwitchParser {
	private String urlToWagoSystem = null;
	private HashMap<Integer,UUID> switchIdMapping;
	private int WagoControllerId;
	private ArrayList<WagoSwitchData> parsedSwitchData;
	

	
	/**
	 *  retun the previously parsed data by "ParseSwitchData()"
	 * @return the parsedSwitchData
	 */
	public ArrayList<WagoSwitchData> getParsedSwitchData() {
		return parsedSwitchData;
	}


	private Document getXmlDocument(String urlToWagoBus) throws Exception {
	
		Document dom = null;
		// get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		java.io.InputStream in = null;
		try {

			// Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			URL xmlUrl = new URL(urlToWagoBus);
			URLConnection urlConn = xmlUrl.openConnection();
			urlConn.setConnectTimeout(15000);
			urlConn.setReadTimeout(15000);
			in = urlConn.getInputStream();
			dom = db.parse(in);
			in.close();
		} catch (Exception ex) {
			if (in != null) in.close();
			throw new Exception(ex);
		} 
		return dom;
	}
	
	
	private ArrayList<Element> getXmlElementSubList(Document dom, String tagName){
		ArrayList<Element> elemList = new ArrayList<Element>();
		
		// get a nodelist of <Device> elements
		NodeList elemNodeList = dom.getElementsByTagName(tagName);
		if (elemNodeList != null) {
			// get the Device element
			for (int i = 0; i < elemNodeList.getLength(); i++  ){
				Element elem = (Element) elemNodeList.item(i);
				elemList.add(elem);
			}
			
		}
		
		return elemList;
		
	}
	
	//ctor
	public WagoSwitchParser(String urlToWagoSystem, int WagoControllerId, HashMap<Integer,UUID> switchIdMapping){
		this.switchIdMapping = switchIdMapping;
		this.urlToWagoSystem = urlToWagoSystem;
		this.WagoControllerId = WagoControllerId;
	}
	
	/**
	 * * will parse the data first and the parsed data can be get by invoking "getParsedSwitchData()"
	 * @throws Exception
	 */
	public void ParseSwitchData() throws Exception{
		parsedSwitchData = getSwitchData();
	}
	
	public ArrayList<WagoSwitchData> getSwitchData() throws Exception{
		ArrayList<WagoSwitchData> wagoSwitchDatas = new ArrayList<WagoSwitchData>();
		
		Document xmlDoc = this.getXmlDocument(urlToWagoSystem);
		
		ArrayList<Element> elemList =  null;
		//parse the document
		
		elemList = getXmlElementSubList(xmlDoc, "statefuldevice");
		
		for (int i = 0; i < elemList.size(); i++ ){
	
			WagoSwitchData switchData = new WagoSwitchData();
			int switchId = Integer.parseInt( elemList.get(i).getAttribute("id"))+(WagoControllerId*1000);
			switchData.setUuid(switchIdMapping.get(switchId));
			boolean state = Boolean.parseBoolean(((Element) elemList.get(i).getElementsByTagName("output").item(0)).getAttribute("state"));
			if (state) {
				switchData.setState(1);
			}
			else {
				switchData.setState(0);
			}
			switchData.setTimeStamp(System.currentTimeMillis()/1000L);
			wagoSwitchDatas.add(switchData);
			
		}
		
		return wagoSwitchDatas;
	}

}
