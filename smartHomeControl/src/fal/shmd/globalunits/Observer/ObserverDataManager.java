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


package fal.shmd.globalunits.Observer;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import javax.xml.bind.JAXBException;

import fal.smartHomeLib.CBTypes.XMLSerialization;
import fal.smartHomeLib.CBTypes.Configuration.System.DeviceClassification;
import fal.smartHomeLib.CBTypes.Data.Observer.IAppliance;
import fal.smartHomeLib.CBTypes.Data.Observer.IDeviceGeneric;
import fal.smartHomeLib.CBTypes.Data.Observer.ObservedAction;
import fal.smartHomeLib.CBTypes.Data.Observer.ObservedApplianceAction;
import fal.smartHomeLib.CBTypes.Data.Observer.ObserverDateStorage;
import fal.smartHomeLib.CBTypes.Data.Observer.Season;
import fal.smartHomeLib.CBTypes.Data.Observer.Seasons;
import fal.smartHomeLib.CBTypes.Data.Observer.TimeSlot;
import fal.smartHomeLib.CBTypes.Data.Observer.Weekday;
import fal.smartHomeLib.CBTypes.Data.Observer.Weekdays;
import fal.smartHomeLib.ControllerBoxCore.ObserverModel;
import fal.smartHomeLib.ControllerBoxCore.OC.LocalObserver;
import fal.smartHomeLib.HAL.HALrealTimeDriver;
import fal.smartHomeLib.smartHomeMisc.TimeConversion;

/**
 * class for managing and storage the observed data from the
 *         localObserver or directly from the devices
 *@author florian.allerding@kit.edu
 *@category smart-home ControllerBox        
 */
public class ObserverDataManager extends ObserverModel {

	public ObserverDataManager(HALrealTimeDriver systemTimer) {
		super(systemTimer);
		this.observerDataStorage = new ObserverDateStorage();
	}

	private Seasons currentSeasonType;
	public Seasons getCurrentSeasonType() {
		return currentSeasonType;
	}

	public void setCurrentSeasonType(Seasons currentSeasonType) {
		this.currentSeasonType = currentSeasonType;
	}

	private ObserverDateStorage observerDataStorage;


	public void buildDataStorageForLocalUnits(
			ArrayList<LocalObserver> localObservers, int numberOfInitalTimeSlots) {

		// init seasons

		Season _summerSeason = new Season();
		_summerSeason.setSeasonType(Seasons.SUMMER);
		Season _winterSeason = new Season();
		_winterSeason.setSeasonType(Seasons.WINTER);
		Season _middleSeason = new Season();
		_middleSeason.setSeasonType(Seasons.MIDDLESEASON);

		observerDataStorage.getSesons().add(_summerSeason);
		observerDataStorage.getSesons().add(_winterSeason);
		observerDataStorage.getSesons().add(_middleSeason);

		for (Season _season : observerDataStorage.getSesons()) {
		
			for (LocalObserver _locObserv : localObservers) {

			// manage appliances
			if (_locObserv.getAssignedOCUnit().getDeviceClassification() == DeviceClassification.APPLIANCE) {
					IAppliance _appliance = new IAppliance();
					_appliance.getWeekdays().addAll(
							constructSubWeekDayTree(numberOfInitalTimeSlots));
					_appliance.setDeviceID(_locObserv.getAssignedOCUnit()
							.getDeviceID().toString());
					_season.getAssignedAplliances().add(_appliance);
				}

			}

			// TODO implementation for other possible devices

		}
	}

	private Season getSpecificSeason(Seasons seasonType){
		for(Season _season: observerDataStorage.getSesons()) {
			if(_season.getSeasonType() == seasonType){
				return _season;
			}
		}
		
		return null;
	}
	
	private List<TimeSlot> buildTimeSlots(int numberOfInitalTimeSlots) {
		ArrayList<TimeSlot> _timeSlots = new ArrayList<TimeSlot>();

		long secondsOfADay = 86400;
		long secondsOfATimeslot = secondsOfADay / numberOfInitalTimeSlots;
		long startTime = 0;

		for (int i = 0; i < numberOfInitalTimeSlots; i++) {
			TimeSlot _timeSlot = new TimeSlot();
			_timeSlot.setTimeFrom(startTime);
			_timeSlot.setTimeTo(startTime + secondsOfATimeslot - 1);
			startTime = startTime + secondsOfATimeslot;
			_timeSlots.add(_timeSlot);
		}

		return _timeSlots;
	}

	private List<Weekday> constructSubWeekDayTree() {

		return this.constructSubWeekDayTree(4);
	}

	private List<Weekday> constructSubWeekDayTree(int numberOfInitalTimeSlots) {

		ArrayList<Weekday> _weekday = new ArrayList<Weekday>();

		Weekday _tmpMo = new Weekday();
		_tmpMo.setDay(Weekdays.MO);
		_tmpMo.getApplianceActionsSlot().addAll(
				buildTimeSlots(numberOfInitalTimeSlots));
		Weekday _tmpTu = new Weekday();
		_tmpTu.setDay(Weekdays.TU);
		_tmpTu.getApplianceActionsSlot().addAll(
				buildTimeSlots(numberOfInitalTimeSlots));
		Weekday _tmpWe = new Weekday();
		_tmpWe.setDay(Weekdays.WE);
		_tmpWe.getApplianceActionsSlot().addAll(
				buildTimeSlots(numberOfInitalTimeSlots));
		Weekday _tmpTh = new Weekday();
		_tmpTh.setDay(Weekdays.TH);
		_tmpTh.getApplianceActionsSlot().addAll(
				buildTimeSlots(numberOfInitalTimeSlots));
		Weekday _tmpFr = new Weekday();
		_tmpFr.setDay(Weekdays.FR);
		_tmpFr.getApplianceActionsSlot().addAll(
				buildTimeSlots(numberOfInitalTimeSlots));
		Weekday _tmpSa = new Weekday();
		_tmpSa.setDay(Weekdays.SA);
		_tmpSa.getApplianceActionsSlot().addAll(
				buildTimeSlots(numberOfInitalTimeSlots));
		Weekday _tmpSu = new Weekday();
		_tmpSu.setDay(Weekdays.SU);
		_tmpSu.getApplianceActionsSlot().addAll(
				buildTimeSlots(numberOfInitalTimeSlots));

		_weekday.add(_tmpMo);
		_weekday.add(_tmpTu);
		_weekday.add(_tmpWe);
		_weekday.add(_tmpTh);
		_weekday.add(_tmpFr);
		_weekday.add(_tmpSa);
		_weekday.add(_tmpSu);

		return _weekday;
	}


	private IAppliance getDeviceSubTree(UUID applianceId, Season season) {

		for (IAppliance _appliance : season.getAssignedAplliances()) {
			if (UUID.fromString(_appliance.getDeviceID()).compareTo(applianceId) == 0) {
				return _appliance;
			} else {

			}
		}
		return null;
	}

	public Season getSeasonSubTree(Seasons seasonType) {

		for (Season _season : observerDataStorage.getSesons()) {

			if (_season.getSeasonType() == seasonType)
				return _season;
		}
		return null;
	}

	public TimeSlot getTimeSlot(Weekday weekday, int timeSlot) {

		return weekday.getApplianceActionsSlot().get(timeSlot);
	}

	private Weekday getWeekdaySubtree(Weekdays weekdayType,
			IDeviceGeneric device) {

		for (Weekday _weekday : device.getWeekdays()) {
			if (_weekday.getDay() == weekdayType) {
				return _weekday;
			}
		}
		return null;
	}


	
	private Season getcurrentSeason(){
		
		for(Season _season:this.observerDataStorage.getSesons()){
			if (_season.getSeasonType() == this.currentSeasonType) return _season;
		}
		return null;
	}

	public void storeNewAction(ObservedAction observedAction) {

		// handle action based on it's type
		if (observedAction instanceof ObservedApplianceAction) {

			// get the subtree:
			IAppliance _tmpApp = getDeviceSubTree(UUID
					.fromString(observedAction.getDeviceID()),
					this.getcurrentSeason());

			// calculating the time from 0h of the current day
			long _timeFromMidnight = TimeConversion
					.calculateTimeSpanFromMidnight(observedAction
							.getTimeStamp());

			Weekday _weekday = getWeekdaySubtree(TimeConversion
					.convertUnixTime2Weekdays(observedAction.getTimeStamp()),
					_tmpApp);
			
			Weekdays mytest = TimeConversion.convertUnixTime2Weekdays(observedAction.getTimeStamp());

			for (TimeSlot _timeSlot : _weekday.getApplianceActionsSlot()) {

				if ((_timeSlot.getTimeFrom() < _timeFromMidnight)
						&& _timeSlot.getTimeTo() > _timeFromMidnight) {
					_timeSlot.getActions().add(observedAction);
				}

			}

		}

		// TODO implementation for other possible devices
	}
	
	public IAppliance getApplianceObservedActionTree(UUID applianceId, Seasons specificSeason){
		
		return getDeviceSubTree(applianceId, getSpecificSeason(specificSeason));
		
	}
	
	public void persistObservedData(String fileName) throws FileNotFoundException, JAXBException {
		XMLSerialization.marshal2File(fileName, this.observerDataStorage);
	}

	@Override
	public void onNextTimePeriode() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initializeDataStorage(Collection<LocalObserver> localObservers) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<ObservedAction> predict(long timestamp) {
		// TODO Auto-generated method stub
		return null;
	}

}