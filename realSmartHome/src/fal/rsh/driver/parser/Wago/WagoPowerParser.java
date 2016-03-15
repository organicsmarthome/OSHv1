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

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import fal.rsh.driver.data.WagoPowerData;



public class WagoPowerParser  {
	
	private String urlToWagoSystem = null;
	private int WagoControllerId;
	private ArrayList<WagoPowerData> parsedWagoPowerData;
	/**
	 * retun the previously parsed data by "ParsePowerData()"
	 * @return the parsedWagoPowerData
	 */
	public ArrayList<WagoPowerData> getParsedWagoPowerData() {
		return parsedWagoPowerData;
	}


	private Document getXmlDocument(String urlToWagoBus) throws Exception {
	
		Document dom = null;
		// get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		java.io.InputStream in =null;
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
			//Element el = (Element) nl.item(0); // ersetze durch pId
			//parseDevice(el, device);
			for (int i = 0; i < elemNodeList.getLength(); i++  ){
				Element elem = (Element) elemNodeList.item(i);
				elemList.add(elem);
			}
			
		}
		
		return elemList;
		
	}
	
	//ctor
	public WagoPowerParser(String urlToWagoSystem, int WagoControllerId){
		this.urlToWagoSystem = urlToWagoSystem;
		this.WagoControllerId = WagoControllerId;
	}
	
	/**
	 * will parse the data first and the parsed data can be get by invoking "getParsedWagoPowerData()"
	 * @throws Exception
	 */
	public void ParsePowerData() throws Exception{
		this.parsedWagoPowerData = this.getPowerData();
	}
	
	public ArrayList<WagoPowerData> getPowerData() throws Exception{
		ArrayList<WagoPowerData> wagoPowerDatas = new ArrayList<WagoPowerData>();
		
		Document xmlDoc = this.getXmlDocument(urlToWagoSystem);
		
		ArrayList<Element> elemList =  null;
		//parse the document
		
		elemList = getXmlElementSubList(xmlDoc, "inputdevice");
		int t2 = elemList.size();
		for (int i = 0; i < elemList.size(); i++ ){
	
			NodeList singleMeterXml = elemList.get(i).getElementsByTagName("input");
			for (int j = 0; j < singleMeterXml.getLength(); j++) {
				
				
				WagoPowerData tmpPwrData = parsePowerPort((Element)singleMeterXml.item(j));
				tmpPwrData.setMeterId(i);
				tmpPwrData.setWagoControllerId(this.WagoControllerId);
				wagoPowerDatas.add(tmpPwrData);
			}
			
		}
		
		return wagoPowerDatas;
	}
	
	private WagoPowerData parsePowerPort(Element powerElement){
		WagoPowerData powerData = new WagoPowerData();
		
		 
		powerData.setPower(Double.parseDouble(((Element) powerElement.getElementsByTagName("power").item(0)).getAttribute("value"))*4.0);
		powerData.setCurrent(Double.parseDouble(((Element) powerElement.getElementsByTagName("current").item(0)).getAttribute("value"))*4.0);
		powerData.setVoltage(Double.parseDouble(((Element) powerElement.getElementsByTagName("voltage").item(0)).getAttribute("value")));
		powerData.setEnergy(Double.parseDouble(((Element) powerElement.getElementsByTagName("energy").item(0)).getAttribute("value"))*4.0);
		powerData.setMeterPortId(Integer.parseInt(powerElement.getAttribute("port")));
		powerData.setTimeStamp(System.currentTimeMillis()/1000L);
		return powerData;
	}
	



}
