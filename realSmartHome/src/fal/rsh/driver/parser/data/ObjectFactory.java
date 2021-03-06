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

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the fal.rsh.driver.parser.data package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: fal.rsh.driver.parser.data
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link WagoPowerIdMapping }
     * 
     */
    public WagoPowerIdMapping createWagoPowerIdMapping() {
        return new WagoPowerIdMapping();
    }

    /**
     * Create an instance of {@link ControllerIdMapping }
     * 
     */
    public ControllerIdMapping createControllerIdMapping() {
        return new ControllerIdMapping();
    }

    /**
     * Create an instance of {@link WagoSwitchIdMapping }
     * 
     */
    public WagoSwitchIdMapping createWagoSwitchIdMapping() {
        return new WagoSwitchIdMapping();
    }

    /**
     * Create an instance of {@link MieleIdMapping }
     * 
     */
    public MieleIdMapping createMieleIdMapping() {
        return new MieleIdMapping();
    }

}
