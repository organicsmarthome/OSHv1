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


/**
 * @author florian.allerding@kit.edu
 * @category smart-home  Driver-Parser
 */
public class MieleHomeBusParser {

	Document dom;
	String name;
	String value;
		
	public void update(MieleAppliance device) throws Exception {
	
		parseXmlFile(device);		
		parseDocument(device);
		
	}
	
	private void parseXmlFile(MieleAppliance device) throws Exception {
		
		DocumentBuilderFactory dbf  = DocumentBuilderFactory.newInstance();
		java.io.InputStream in = null;
		try {
			DocumentBuilder db = null;
			//instance of document builder
			db = dbf.newDocumentBuilder();
			
			URL xmlUrl = new URL(device.getHomebusURL());
			URLConnection urlConn = xmlUrl.openConnection();
			urlConn.setConnectTimeout(15000);
			urlConn.setReadTimeout(15000);
			in = urlConn.getInputStream();
			dom = db.parse(in);
			in.close();
      
		}catch(Exception ex) {
			if (in != null) in.close();
			throw new Exception(ex);
		}
	}

	private void parseDocument(MieleAppliance device){
		//get the root elememt
		Element docEle = dom.getDocumentElement();
				
		// get NodeList consisting of the nodes
		NodeList devicesNodeList = docEle.getChildNodes();
		
		
		for(int i=0; i<devicesNodeList.getLength(); i++ ){
			Node deviceNode = devicesNodeList.item(i);
			NodeList deviceNodeItems = deviceNode.getChildNodes();
			String UidString=deviceNodeItems.item(1).getTextContent();
						
			// if the current device is the selected one
			if(Integer.parseInt(UidString)== device.getUid()){
				device.setClasse(Integer.parseInt(deviceNodeItems.item(0).getTextContent()));
				device.setType(deviceNodeItems.item(2).getTextContent());
				device.setStateID(Integer.parseInt(deviceNodeItems.item(4).getTextContent()));
									
				// set DetailURL, set DetailName
				Node actionsNode = deviceNodeItems.item(8);
				NodeList actionNodeList = actionsNode.getChildNodes();
				Node actionNode=actionNodeList.item(1);
				NamedNodeMap actionAttributes = actionNode.getAttributes();
				if(actionAttributes.item(1).getNodeValue()!=null)
					device.setDetailName(actionAttributes.item(1).getNodeValue());
				if(actionAttributes.item(0).getNodeValue()!=null)
					device.setDetailURL(actionAttributes.item(0).getNodeValue());
					
			} 
		} 
	} 
} 
