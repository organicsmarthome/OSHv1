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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;




import fal.smartHomeLib.CBTypes.Data.Observer.ObservedAction;
import fal.smartHomeLib.CBTypes.Data.Observer.ObservedApplianceAction;
import fal.smartHomeLib.CBTypes.Data.Observer.ObservedSmartPlugAction;

import fal.smartHomeLib.ControllerBoxCore.Data.ModelOfObservationExchange;
import fal.smartHomeLib.ControllerBoxCore.Data.ObserverExchange;
import fal.smartHomeLib.ControllerBoxCore.OC.GlobalObserver;
import fal.smartHomeLib.HAL.HALrealTimeDriver;

public class SHMDObserver extends GlobalObserver {

	private HashMap<UUID, ObservedApplianceAction> applianceCurrentStateList;
	private HashMap<UUID, ObservedSmartPlugAction> smartPlugCurrentStateList;

	public SHMDObserver(HALrealTimeDriver systemTimer) {
		super(systemTimer);

		applianceCurrentStateList = new HashMap<UUID, ObservedApplianceAction>();
		smartPlugCurrentStateList = new HashMap<UUID, ObservedSmartPlugAction>();
	}

	@Override
	public void onDeviceUpdate(UUID deviceID) {
		
		//get the state from the local observer
		ObservedAction observerdAction = this.getLocalObserver(deviceID).pollObserver().getLastAction();
		
		UUID deviceId = UUID.fromString(observerdAction.getDeviceID());
		

		//handle the appliances
		if(observerdAction instanceof ObservedApplianceAction){
			applianceCurrentStateList.put( deviceID, (ObservedApplianceAction) observerdAction);
			
		}
		//handle the smartPlugs
		if(observerdAction instanceof ObservedSmartPlugAction){
			smartPlugCurrentStateList.put(deviceId, (ObservedSmartPlugAction) observerdAction);
			
		}
	}

	@Override
	public void onSystemIsUp() {
		
	}

	@Override
	protected void onLocalUnitPost(ObserverExchange observerExchange) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSystemShutdown() {

	}

	@Override
	public void onNextTimePeriode() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ModelOfObservationExchange getObservedModelData() {

		ObservationDataObject _data = new ObservationDataObject();
		ArrayList<ObservedApplianceAction> _actionList = new ArrayList<ObservedApplianceAction>();
		_actionList.addAll(this.applianceCurrentStateList.values());
		_data.setObservedApplianceAction(_actionList);
		
		return _data;
	}	

}