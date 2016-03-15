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

import javax.xml.parsers.*;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.*;
import java.io.*;

/**
 *@author florian.allerding@kit.edu
 *@category smart-home ControllerBox
 *
 */
public class XmlObjectHandler implements java.io.Serializable {


	private static final long serialVersionUID = 1L;

	public Document createDocument() {
		
		try {
		//create factory
		DocumentBuilderFactory docBuliderFact = DocumentBuilderFactory.newInstance();
		//create docbuilder
		DocumentBuilder docBuilder = docBuliderFact.newDocumentBuilder();
		
		return docBuilder.newDocument();
		
		
		}
		catch (Exception ex)
		{
			System.out.println("Error creating XML document: " +ex.getMessage());
			return null;
			
		}
		
	}
	
	public Document loadXMLDocument(String fileName) {
	
		try {
			
			//create factory
			DocumentBuilderFactory docBuliderFact = DocumentBuilderFactory.newInstance();
			
			//create docbuilder
			DocumentBuilder docBuilder = docBuliderFact.newDocumentBuilder();
			
			Document xmlDocument;
			//load file
			File xmlFile = new File(fileName);	
		
			if (xmlFile.exists())
			{			
				//load from file
				 xmlDocument = docBuilder.parse(new File(fileName));
			}
			
			else
			{
				//create new document
				 xmlDocument = docBuilder.newDocument();
				
			}
	
			return xmlDocument;		
		}
		catch (Exception ex) {
			System.out.println("Error loading XML document: " +ex.getMessage());
			return null;
		}
	}
	
	public void saveXMLDocument(Document xmlDocument, String fileName) {
		
		try {
		TransformerFactory transFact = TransformerFactory.newInstance();
		Transformer xmltrans = transFact.newTransformer();
		Source src = new DOMSource(xmlDocument);
		Result destination = new StreamResult(fileName);
		xmltrans.transform(src, destination);
		}
		catch (Exception ex) {
			System.out.println("Error saving File: " +ex.getMessage());

		}
	
	}
	
	public Node selectSingleXMLNode(Document xmlDocument, String tagName) throws Exception {
		
		Node innerNode = xmlDocument.getFirstChild();
		
		NodeList innerNodes = xmlDocument.getElementsByTagName(tagName);
		
		//only for a test...
		String match = "test";
		
		for (int i = 0; i < innerNodes.getLength(); i++) {
			
			innerNode = innerNodes.item(i);		
		
		}
		
		return innerNode;
	}
	
	public NodeList selectSubNodes (Document xmlDocument, String xPath) throws Exception {
		
		NodeList innerNodes;
		
		XPathFactory xpathFact = XPathFactory.newInstance();
		XPath xPathObj = xpathFact.newXPath();
		
		innerNodes = (NodeList) xPathObj.evaluate(xPath, xmlDocument, XPathConstants.NODESET);
		
		return innerNodes;
	}
	

	
}
