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


package fal.smartHomeLib.smartHomeMisc;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;


import fal.smartHomeLib.CBTypes.Data.Observer.Weekdays;

public class TimeConversion {

	public static Weekdays convertUnixTime2Weekdays(long unixTime) {
		Calendar _calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"),
				Locale.GERMANY);
		_calendar.setTimeInMillis(unixTime * 1000);
		int day = _calendar.get(Calendar.DAY_OF_WEEK);
		return Weekdays.values()[day - 1];
	}

	public static long calculateTimeSpanFromMidnight(long unixTime) {

		Calendar _calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"),
				Locale.GERMANY);
		_calendar.setTimeInMillis(unixTime * 1000);
		long hours = _calendar.get(Calendar.HOUR_OF_DAY);
		long minutes = _calendar.get(Calendar.MINUTE);
		long seconds = _calendar.get(Calendar.SECOND);
		return seconds + minutes * 60 + hours * 60 * 60;
	}

}
