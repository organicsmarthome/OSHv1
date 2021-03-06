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
// Generated on: 2010.02.17 at 03:50:28 PM MEZ 
//


package fal.smartHomeLib.CBTypes.Simulation.VirtualDevicesData;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProfileTick complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProfileTick">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="activePower" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="reactivePower" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="deviceStateName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="parameters" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="parameterName" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                   &lt;element name="parameterValue" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProfileTick", propOrder = {
    "activePower",
    "reactivePower",
    "deviceStateName",
    "parameters"
})
public class ProfileTick {

    protected int activePower;
    protected int reactivePower;
    @XmlElement(required = true)
    protected String deviceStateName;
    protected List<ProfileTick.Parameters> parameters;

    /**
     * Gets the value of the activePower property.
     * 
     */
    public int getActivePower() {
        return activePower;
    }

    /**
     * Sets the value of the activePower property.
     * 
     */
    public void setActivePower(int value) {
        this.activePower = value;
    }

    /**
     * Gets the value of the reactivePower property.
     * 
     */
    public int getReactivePower() {
        return reactivePower;
    }

    /**
     * Sets the value of the reactivePower property.
     * 
     */
    public void setReactivePower(int value) {
        this.reactivePower = value;
    }

    /**
     * Gets the value of the deviceStateName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeviceStateName() {
        return deviceStateName;
    }

    /**
     * Sets the value of the deviceStateName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeviceStateName(String value) {
        this.deviceStateName = value;
    }

    /**
     * Gets the value of the parameters property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parameters property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParameters().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProfileTick.Parameters }
     * 
     * 
     */
    public List<ProfileTick.Parameters> getParameters() {
        if (parameters == null) {
            parameters = new ArrayList<ProfileTick.Parameters>();
        }
        return this.parameters;
    }


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
     *         &lt;element name="parameterName" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *         &lt;element name="parameterValue" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
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
        "parameterName",
        "parameterValue"
    })
    public static class Parameters {

        @XmlElement(required = true)
        protected Object parameterName;
        @XmlElement(required = true)
        protected Object parameterValue;

        /**
         * Gets the value of the parameterName property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getParameterName() {
            return parameterName;
        }

        /**
         * Sets the value of the parameterName property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setParameterName(Object value) {
            this.parameterName = value;
        }

        /**
         * Gets the value of the parameterValue property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getParameterValue() {
            return parameterValue;
        }

        /**
         * Sets the value of the parameterValue property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setParameterValue(Object value) {
            this.parameterValue = value;
        }

    }

}
