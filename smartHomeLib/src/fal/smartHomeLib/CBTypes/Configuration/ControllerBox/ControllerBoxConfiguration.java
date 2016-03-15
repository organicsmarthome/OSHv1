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
// Generated on: 2010.07.05 at 02:51:01 PM MESZ 
//


package fal.smartHomeLib.CBTypes.Configuration.ControllerBox;

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
 *         &lt;element name="Simulation" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="runnigVirtual" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="configfilePathes" type="{http://www.example.org/ControllerBoxConfiguration}FilePathes"/>
 *         &lt;element name="globalControllerClass" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="globalObserverClass" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="commManagerClass" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "simulation",
    "runnigVirtual",
    "configfilePathes",
    "globalControllerClass",
    "globalObserverClass",
    "commManagerClass"
})
@XmlRootElement(name = "ControllerBoxConfiguration")
public class ControllerBoxConfiguration {

    @XmlElement(name = "Simulation")
    protected boolean simulation;
    protected boolean runnigVirtual;
    @XmlElement(required = true)
    protected FilePathes configfilePathes;
    @XmlElement(required = true)
    protected String globalControllerClass;
    @XmlElement(required = true)
    protected String globalObserverClass;
    @XmlElement(required = true)
    protected String commManagerClass;

    /**
     * Gets the value of the simulation property.
     * 
     */
    public boolean isSimulation() {
        return simulation;
    }

    /**
     * Sets the value of the simulation property.
     * 
     */
    public void setSimulation(boolean value) {
        this.simulation = value;
    }

    /**
     * Gets the value of the runnigVirtual property.
     * 
     */
    public boolean isRunnigVirtual() {
        return runnigVirtual;
    }

    /**
     * Sets the value of the runnigVirtual property.
     * 
     */
    public void setRunnigVirtual(boolean value) {
        this.runnigVirtual = value;
    }

    /**
     * Gets the value of the configfilePathes property.
     * 
     * @return
     *     possible object is
     *     {@link FilePathes }
     *     
     */
    public FilePathes getConfigfilePathes() {
        return configfilePathes;
    }

    /**
     * Sets the value of the configfilePathes property.
     * 
     * @param value
     *     allowed object is
     *     {@link FilePathes }
     *     
     */
    public void setConfigfilePathes(FilePathes value) {
        this.configfilePathes = value;
    }

    /**
     * Gets the value of the globalControllerClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGlobalControllerClass() {
        return globalControllerClass;
    }

    /**
     * Sets the value of the globalControllerClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGlobalControllerClass(String value) {
        this.globalControllerClass = value;
    }

    /**
     * Gets the value of the globalObserverClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGlobalObserverClass() {
        return globalObserverClass;
    }

    /**
     * Sets the value of the globalObserverClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGlobalObserverClass(String value) {
        this.globalObserverClass = value;
    }

    /**
     * Gets the value of the commManagerClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommManagerClass() {
        return commManagerClass;
    }

    /**
     * Sets the value of the commManagerClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommManagerClass(String value) {
        this.commManagerClass = value;
    }

}