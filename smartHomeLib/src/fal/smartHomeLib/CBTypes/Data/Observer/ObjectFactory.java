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

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the fal.smartHomeLib.CBTypes.Data.Observer package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: fal.smartHomeLib.CBTypes.Data.Observer
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ObservedAction }
     * 
     */
    public ObservedAction createObservedAction() {
        return new ObservedAction();
    }

    /**
     * Create an instance of {@link IAppliance }
     * 
     */
    public IAppliance createIAppliance() {
        return new IAppliance();
    }

    /**
     * Create an instance of {@link IDeviceGeneric }
     * 
     */
    public IDeviceGeneric createIDeviceGeneric() {
        return new IDeviceGeneric();
    }

    /**
     * Create an instance of {@link Season }
     * 
     */
    public Season createSeason() {
        return new Season();
    }

    /**
     * Create an instance of {@link ObservedSmartPlugAction }
     * 
     */
    public ObservedSmartPlugAction createObservedSmartPlugAction() {
        return new ObservedSmartPlugAction();
    }

    /**
     * Create an instance of {@link ObservedApplianceAction }
     * 
     */
    public ObservedApplianceAction createObservedApplianceAction() {
        return new ObservedApplianceAction();
    }

    /**
     * Create an instance of {@link Weekday }
     * 
     */
    public Weekday createWeekday() {
        return new Weekday();
    }

    /**
     * Create an instance of {@link ObserverDateStorage }
     * 
     */
    public ObserverDateStorage createObserverDateStorage() {
        return new ObserverDateStorage();
    }

    /**
     * Create an instance of {@link TimeSlot }
     * 
     */
    public TimeSlot createTimeSlot() {
        return new TimeSlot();
    }

}