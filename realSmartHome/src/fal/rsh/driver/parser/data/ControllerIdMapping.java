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
// Generated on: 2011.01.13 at 04:00:26 PM MEZ 
//


package fal.rsh.driver.parser.data;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="wagoPowerIdMapping" type="{http://www.example.org/ControllerIdMapping}WagoPowerIdMapping" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="wagoSwitchIdMapping" type="{http://www.example.org/ControllerIdMapping}WagoSwitchIdMapping" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="mieleIdMapping" type="{http://www.example.org/ControllerIdMapping}MieleIdMapping" maxOccurs="unbounded" minOccurs="0"/>
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
    "wagoPowerIdMapping",
    "wagoSwitchIdMapping",
    "mieleIdMapping"
})
@XmlRootElement(name = "ControllerIdMapping")
public class ControllerIdMapping {

    protected List<WagoPowerIdMapping> wagoPowerIdMapping;
    protected List<WagoSwitchIdMapping> wagoSwitchIdMapping;
    protected List<MieleIdMapping> mieleIdMapping;

    /**
     * Gets the value of the wagoPowerIdMapping property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the wagoPowerIdMapping property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWagoPowerIdMapping().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WagoPowerIdMapping }
     * 
     * 
     */
    public List<WagoPowerIdMapping> getWagoPowerIdMapping() {
        if (wagoPowerIdMapping == null) {
            wagoPowerIdMapping = new ArrayList<WagoPowerIdMapping>();
        }
        return this.wagoPowerIdMapping;
    }

    /**
     * Gets the value of the wagoSwitchIdMapping property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the wagoSwitchIdMapping property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWagoSwitchIdMapping().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WagoSwitchIdMapping }
     * 
     * 
     */
    public List<WagoSwitchIdMapping> getWagoSwitchIdMapping() {
        if (wagoSwitchIdMapping == null) {
            wagoSwitchIdMapping = new ArrayList<WagoSwitchIdMapping>();
        }
        return this.wagoSwitchIdMapping;
    }

    /**
     * Gets the value of the mieleIdMapping property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mieleIdMapping property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMieleIdMapping().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MieleIdMapping }
     * 
     * 
     */
    public List<MieleIdMapping> getMieleIdMapping() {
        if (mieleIdMapping == null) {
            mieleIdMapping = new ArrayList<MieleIdMapping>();
        }
        return this.mieleIdMapping;
    }

}