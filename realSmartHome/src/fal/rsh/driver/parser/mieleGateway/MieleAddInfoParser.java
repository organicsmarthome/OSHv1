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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import fal.rsh.driver.data.MieleAppliance;
import fal.rsh.driver.data.MieleApplianceDetails;

/**
 * Parser for the miele additional infos (details) for a miele device
 * @author florian.allerding@kit.edu
 * @category smart-home  Driver-Parser
 */

public class MieleAddInfoParser {

	Element docEle;
	Document docu;
	URL xmlUrl;

	LinkedList<MieleApplianceDetails> addInfoList;


	String name;
	String value;
	MieleApplianceDetails deviceDetails;

	
	LinkedList<MieleApplianceDetails> addActionList;
	

		
	public  void update (MieleAppliance device)throws Exception {

		parseXmlFile(device);

		parseDocument(device);
		
	}
	
	private void parseXmlFile(MieleAppliance device)throws Exception{
		//init factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		InputStream in = null;
		try {
			// instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			xmlUrl = new URL(device.getDetailURL());
			URLConnection urlConn = xmlUrl.openConnection();
			urlConn.setConnectTimeout(15000);
			urlConn.setReadTimeout(15000);
			in = urlConn.getInputStream();
			docu = db.parse(in);
			docEle = docu.getDocumentElement();
			in.close();
			
			}
		
		catch(Exception ex) {
			if (in != null) in.close();
			throw new Exception();
		}
	} 

	
	private void parseDocument(MieleAppliance device)throws MalformedURLException{
		
		// get NodeList consisting of the nodes "information" and "action"
		NodeList devices = docEle.getChildNodes();
		// get the child node "information" of "device" 
		Node informationNode = devices.item(0);
		//get the nodelist "keyList" of "information" 
		NodeList keyNodeList = informationNode.getChildNodes();
		//each run a new LinkedList because the number of elements might change
		addInfoList = new LinkedList<MieleApplianceDetails>();
			//fill the addInfoList
			for(int i=0; i<keyNodeList.getLength(); i++){
				Node keyNode = keyNodeList.item(i);
				NamedNodeMap keyAttributes = keyNode.getAttributes();
				
				if(keyAttributes.item(1).getNodeValue()!=null)
				name = keyAttributes.item(1).getNodeValue();
				
				if(keyAttributes.item(0).getNodeValue()!=null)
				value = keyAttributes.item(0).getNodeValue();
				
				deviceDetails = new MieleApplianceDetails(name, value);
				addInfoList.add(deviceDetails);
			} 
			
			device.setAddInfoList(addInfoList);
				
				// get the child node "actions" of "device" 
				Node actionsNode = devices.item(1);
										
				//get the nodelist "keyList" of "information" 
				NodeList actionsNodeList = actionsNode.getChildNodes();
				//each run a new LinkedList because the number of elements might change
				addActionList = new LinkedList<MieleApplianceDetails>();
					//fill the addInfoList
				
					for(int i=0; i<actionsNodeList.getLength(); i++){
						Node actionNode = actionsNodeList.item(i);
						NamedNodeMap keyAttributes = actionNode.getAttributes();
					
						if(keyAttributes.item(0).getNodeValue()!=null)
						name = keyAttributes.item(0).getNodeValue();
					
						if(keyAttributes.item(1).getNodeValue()!=null)
						value = keyAttributes.item(1).getNodeValue();
											
						deviceDetails = new MieleApplianceDetails(name, value);
						addActionList.add(deviceDetails);
					}
					device.setAddActionList(addActionList);
	} 
}
