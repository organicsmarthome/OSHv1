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


//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.01.24 at 08:41:52 AM MEZ 
//


package fal.smartHomeLib.CBTypes.Configuration.System;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="assignedDevices" type="{http://www.example.org/HALConfiguration}AssignedDevice" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="assignedCommDevices" type="{http://www.example.org/HALConfiguration}AssignedCommDevice" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="usingHALdispatcher" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="HALdispatcherClassName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "assignedDevices",
    "assignedCommDevices",
    "usingHALdispatcher",
    "haLdispatcherClassName"
})
@XmlRootElement(name = "HALConfiguration")
public class HALConfiguration {

    protected List<AssignedDevice> assignedDevices;
    protected List<AssignedCommDevice> assignedCommDevices;
    protected Boolean usingHALdispatcher;
    @XmlElement(name = "HALdispatcherClassName")
    protected String haLdispatcherClassName;

    /**
     * Gets the value of the assignedDevices property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the assignedDevices property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAssignedDevices().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AssignedDevice }
     * 
     * 
     */
    public List<AssignedDevice> getAssignedDevices() {
        if (assignedDevices == null) {
            assignedDevices = new ArrayList<AssignedDevice>();
        }
        return this.assignedDevices;
    }

    /**
     * Gets the value of the assignedCommDevices property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the assignedCommDevices property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAssignedCommDevices().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AssignedCommDevice }
     * 
     * 
     */
    public List<AssignedCommDevice> getAssignedCommDevices() {
        if (assignedCommDevices == null) {
            assignedCommDevices = new ArrayList<AssignedCommDevice>();
        }
        return this.assignedCommDevices;
    }

    /**
     * Gets the value of the usingHALdispatcher property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUsingHALdispatcher() {
        return usingHALdispatcher;
    }

    /**
     * Sets the value of the usingHALdispatcher property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUsingHALdispatcher(Boolean value) {
        this.usingHALdispatcher = value;
    }

    /**
     * Gets the value of the haLdispatcherClassName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHALdispatcherClassName() {
        return haLdispatcherClassName;
    }

    /**
     * Sets the value of the haLdispatcherClassName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHALdispatcherClassName(String value) {
        this.haLdispatcherClassName = value;
    }

}