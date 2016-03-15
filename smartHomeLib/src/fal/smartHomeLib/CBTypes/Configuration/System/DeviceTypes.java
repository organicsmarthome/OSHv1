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

import javax.xml.bind.annotation.XmlEnum;


/**
 * <p>Java class for DeviceTypes.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DeviceTypes">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="WASHINGMACHINE"/>
 *     &lt;enumeration value="DISHWASCHER"/>
 *     &lt;enumeration value="DRYER"/>
 *     &lt;enumeration value="REFRIGERATOR"/>
 *     &lt;enumeration value="DEEPFREEZER"/>
 *     &lt;enumeration value="COFFEESYSTEM"/>
 *     &lt;enumeration value="FANHEATER"/>
 *     &lt;enumeration value="CHPPLANT"/>
 *     &lt;enumeration value="PVSYSTEM"/>
 *     &lt;enumeration value="AIRCONDITION"/>
 *     &lt;enumeration value="STOVE"/>
 *     &lt;enumeration value="HEATPLATE"/>
 *     &lt;enumeration value="SMARTPLUG"/>
 *     &lt;enumeration value="ECAR"/>
 *     &lt;enumeration value="SMARTMETER"/>
 *     &lt;enumeration value="PROVIDERSERVICEPROXY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum DeviceTypes {

    WASHINGMACHINE,
    DISHWASCHER,
    DRYER,
    REFRIGERATOR,
    DEEPFREEZER,
    COFFEESYSTEM,
    FANHEATER,
    CHPPLANT,
    PVSYSTEM,
    AIRCONDITION,
    STOVE,
    HEATPLATE,
    SMARTPLUG,
    ECAR,
    SMARTMETER,
    PROVIDERSERVICEPROXY;

    public String value() {
        return name();
    }

    public static DeviceTypes fromValue(String v) {
        return valueOf(v);
    }

}
