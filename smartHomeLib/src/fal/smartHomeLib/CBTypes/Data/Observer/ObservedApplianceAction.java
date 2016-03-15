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
// Generated on: 2011.01.14 at 04:39:54 PM MEZ 
//


package fal.smartHomeLib.CBTypes.Data.Observer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ObservedApplianceAction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ObservedApplianceAction">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.example.org/ObserverDataStorage}ObservedAction">
 *       &lt;sequence>
 *         &lt;element name="startRunning" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="stopRunning" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Programmed" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ObservedApplianceAction", propOrder = {
    "startRunning",
    "stopRunning",
    "programmed"
})
public class ObservedApplianceAction
    extends ObservedAction
{

    protected boolean startRunning;
    protected boolean stopRunning;
    @XmlElement(name = "Programmed")
    protected boolean programmed;

    /**
     * Gets the value of the startRunning property.
     * 
     */
    public boolean isStartRunning() {
        return startRunning;
    }

    /**
     * Sets the value of the startRunning property.
     * 
     */
    public void setStartRunning(boolean value) {
        this.startRunning = value;
    }

    /**
     * Gets the value of the stopRunning property.
     * 
     */
    public boolean isStopRunning() {
        return stopRunning;
    }

    /**
     * Sets the value of the stopRunning property.
     * 
     */
    public void setStopRunning(boolean value) {
        this.stopRunning = value;
    }

    /**
     * Gets the value of the programmed property.
     * 
     */
    public boolean isProgrammed() {
        return programmed;
    }

    /**
     * Sets the value of the programmed property.
     * 
     */
    public void setProgrammed(boolean value) {
        this.programmed = value;
    }

}
