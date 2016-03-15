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


package fal.rsh.driver.parser.mieleGateway;



import java.io.InputStream;

import java.net.MalformedURLException;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import fal.rsh.driver.data.MieleAppliance;

/**
 * @author florian.allerding@kit.edu
 * @category smart-home  Driver-Parser
 */
public class MieleGatewayParser {
	public List<MieleAppliance> myDevices;
	private Document dom;
	private URL xmlUrl;


	public MieleGatewayParser(String url) throws MalformedURLException{
		myDevices = new ArrayList<MieleAppliance>(); 
		xmlUrl= new URL(url);
	}
	

	public void runUpdate() throws Exception{
		myDevices = new ArrayList<MieleAppliance>();
		parseXmlFile();
		if(dom!=null){
		parseDocument();
		}
		
	}
	
	private void parseXmlFile() throws Exception{
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		InputStream in = null;
		try {
			
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			//open Inputstream
			URLConnection urlConn = xmlUrl.openConnection();
			urlConn.setConnectTimeout(15000);
			urlConn.setReadTimeout(15000);
			in = urlConn.getInputStream();
			// parse Input
			dom = db.parse(in);
			//close Input
			in.close();
          
		}catch(Exception ex) {
			if (in != null) in.close();
			throw new Exception(ex);
		}
	}


	private void parseDocument() throws Exception{
		//get the root element

		Element docEle = dom.getDocumentElement();
			
		
		//get a nodelist of <Device> elements
		NodeList nl = docEle.getElementsByTagName("device");
		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) {
				
				//get the Device element
				Element el = (Element)nl.item(i);
				
				//get the Device object
				MieleAppliance e = getDevice(el);
				
				//set pID
				e.setpId((i+1));
			
				
				//add Device objects to list
				myDevices.add(e);
			}
		}
	}


	private MieleAppliance getDevice(Element empEl) throws Exception {
				
		MieleAppliance device= new MieleAppliance();
		device.setName(getTextValue(empEl,"name"));
		device.setClasse (getIntValue(empEl,"class"));
		device.setUid (getIntValue(empEl,"UID"));
		device.setType (getTextValue(empEl, "type"));
        device.setStateID (getIntValue(empEl, "state"));
        device.setAddName(getTextValue  (empEl, "additionalName"));
        // set the content of the room tag
        device.setRoomName(getTextValue  (empEl, "room"));
        // get the attributes of room tag
        parseRoom(empEl, device);
        //get detailName and detailURL
        parseActions(empEl,device);
        
        
        MieleAddInfoParser addInfo = new MieleAddInfoParser();
        try {
			addInfo.update(device);
		} catch (Exception e) {
			throw new Exception(e);
		}
    
		return device;
	}

	public void parseRoom(Element empe, MieleAppliance device){
    	//get a nodelist of <room> elements
    	NodeList nl= empe.getElementsByTagName("room");
    	if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			
			if (el!=null) {

				device.setRoomLevel(el.getAttribute("level"));				

				try {
					int tmp = Integer.parseInt(el.getAttribute("id"));	
					device.setRoomID(tmp);
					
				} catch (Exception e) {
					//e.printStackTrace();
				}


				
			}
		}
  

    }

    	public void parseActions(Element empe, MieleAppliance device)
    	{
    		//get a nodelist of <actions> elements
        	NodeList n1= empe.getElementsByTagName("actions");
        	NodeList n2;
        	if(n1 != null && n1.getLength() > 0)
        	{
    			Element el = (Element)n1.item(0);
    			//get a nodelist of <action> elements
    			n2=el.getElementsByTagName("action");
    			if(n2!=null && n2.getLength() > 0)
    			{
    				Element elx=(Element)n2.item(0);
    				if (elx!=null) 
    				{
    	
    					device.setDetailName(elx.getAttribute("name"));
    					device.setDetailURL(elx.getAttribute("URL"));
    					
    						
    				}
    			}
    		}
        	
        }

	private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		//get a nodelist of <tagname> elements
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			Node tmpNode = el.getFirstChild();
			if (tmpNode!=null) textVal = tmpNode.getNodeValue();
		}
		

		return textVal;
	}

	
	private int getIntValue(Element ele, String tagName) {
		return Integer.parseInt(getTextValue(ele,tagName));
	}
	
}
