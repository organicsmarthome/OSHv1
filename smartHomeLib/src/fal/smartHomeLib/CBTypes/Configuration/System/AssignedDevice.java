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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AssignedDevice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AssignedDevice">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="deviceID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="deviceType" type="{http://www.example.org/DevTypes}DeviceTypes"/>
 *         &lt;element name="deviceClassification" type="{http://www.example.org/DevTypes}DeviceClassification"/>
 *         &lt;element name="deviceDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="driverClassName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="driverParameters" type="{http://www.example.org/ConfigurationParameter}ConfigurationParameter" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="controllable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="observable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="assignedLocalOCUnit" type="{http://www.example.org/HALConfiguration}assignedLocalOCUnit"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AssignedDevice", propOrder = {
    "deviceID",
    "deviceType",
    "deviceClassification",
    "deviceDescription",
    "driverClassName",
    "driverParameters",
    "controllable",
    "observable",
    "assignedLocalOCUnit"
})
public class AssignedDevice {

    @XmlElement(required = true)
    protected String deviceID;
    @XmlElement(required = true)
    protected DeviceTypes deviceType;
    @XmlElement(required = true, defaultValue = "N/A")
    protected DeviceClassification deviceClassification;
    @XmlElement(required = true)
    protected String deviceDescription;
    @XmlElement(required = true)
    protected String driverClassName;
    protected List<ConfigurationParameter> driverParameters;
    protected boolean controllable;
    protected boolean observable;
    @XmlElement(required = true)
    protected AssignedLocalOCUnit assignedLocalOCUnit;

    /**
     * Gets the value of the deviceID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeviceID() {
        return deviceID;
    }

    /**
     * Sets the value of the deviceID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeviceID(String value) {
        this.deviceID = value;
    }

    /**
     * Gets the value of the deviceType property.
     * 
     * @return
     *     possible object is
     *     {@link DeviceTypes }
     *     
     */
    public DeviceTypes getDeviceType() {
        return deviceType;
    }

    /**
     * Sets the value of the deviceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeviceTypes }
     *     
     */
    public void setDeviceType(DeviceTypes value) {
        this.deviceType = value;
    }

    /**
     * Gets the value of the deviceClassification property.
     * 
     * @return
     *     possible object is
     *     {@link DeviceClassification }
     *     
     */
    public DeviceClassification getDeviceClassification() {
        return deviceClassification;
    }

    /**
     * Sets the value of the deviceClassification property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeviceClassification }
     *     
     */
    public void setDeviceClassification(DeviceClassification value) {
        this.deviceClassification = value;
    }

    /**
     * Gets the value of the deviceDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeviceDescription() {
        return deviceDescription;
    }

    /**
     * Sets the value of the deviceDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeviceDescription(String value) {
        this.deviceDescription = value;
    }

    /**
     * Gets the value of the driverClassName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDriverClassName() {
        return driverClassName;
    }

    /**
     * Sets the value of the driverClassName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDriverClassName(String value) {
        this.driverClassName = value;
    }

    /**
     * Gets the value of the driverParameters property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the driverParameters property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDriverParameters().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConfigurationParameter }
     * 
     * 
     */
    public List<ConfigurationParameter> getDriverParameters() {
        if (driverParameters == null) {
            driverParameters = new ArrayList<ConfigurationParameter>();
        }
        return this.driverParameters;
    }

    /**
     * Gets the value of the controllable property.
     * 
     */
    public boolean isControllable() {
        return controllable;
    }

    /**
     * Sets the value of the controllable property.
     * 
     */
    public void setControllable(boolean value) {
        this.controllable = value;
    }

    /**
     * Gets the value of the observable property.
     * 
     */
    public boolean isObservable() {
        return observable;
    }

    /**
     * Sets the value of the observable property.
     * 
     */
    public void setObservable(boolean value) {
        this.observable = value;
    }

    /**
     * Gets the value of the assignedLocalOCUnit property.
     * 
     * @return
     *     possible object is
     *     {@link AssignedLocalOCUnit }
     *     
     */
    public AssignedLocalOCUnit getAssignedLocalOCUnit() {
        return assignedLocalOCUnit;
    }

    /**
     * Sets the value of the assignedLocalOCUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link AssignedLocalOCUnit }
     *     
     */
    public void setAssignedLocalOCUnit(AssignedLocalOCUnit value) {
        this.assignedLocalOCUnit = value;
    }

}
