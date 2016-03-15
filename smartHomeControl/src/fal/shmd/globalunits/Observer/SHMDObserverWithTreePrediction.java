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
	import java.util.HashMap;
	import java.util.UUID;

	import javax.xml.bind.JAXBException;

	import fal.smartHomeLib.CBTypes.Data.ApplianceState;
	import fal.smartHomeLib.CBTypes.Data.Observer.ObservedAction;
	import fal.smartHomeLib.CBTypes.Data.Observer.ObservedApplianceAction;
	import fal.smartHomeLib.CBTypes.Data.Observer.ObservedSmartPlugAction;
	import fal.smartHomeLib.CBTypes.Data.Observer.Seasons;
	import fal.smartHomeLib.ControllerBoxCore.Data.ModelOfObservationExchange;
	import fal.smartHomeLib.ControllerBoxCore.Data.ObserverExchange;
	import fal.smartHomeLib.ControllerBoxCore.OC.GlobalObserver;
	import fal.smartHomeLib.HAL.HALrealTimeDriver;

	public class SHMDObserverWithTreePrediction extends GlobalObserver {

		//
		private ObserverDataManager observerDataManager;
		private HashMap<UUID, ObservedApplianceAction> applianceCurrentStateList;
		private HashMap<UUID, ObservedSmartPlugAction> smartPlugCurrentStateList;

		public SHMDObserverWithTreePrediction(HALrealTimeDriver systemTimer) {
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
				handleActions(observerdAction);
			}
			//handle the smartPlugs
			if(observerdAction instanceof ObservedSmartPlugAction){
				smartPlugCurrentStateList.put(deviceId, (ObservedSmartPlugAction) observerdAction);
				handleActions(observerdAction);
			}
		}
		
		private void handleActions(ObservedAction observedAction){
			
			//if(observedApplianceAction.isActionOccurrd() && observedApplianceAction.isProgrammed()){
			if(observedAction.isActionOccurrd()){	
				observerDataManager.storeNewAction(observedAction);
			}
		}

		@Override
		public void onSystemIsUp() {
			observerDataManager = new ObserverDataManager(this.systemTimer);
			observerDataManager.buildDataStorageForLocalUnits(getAllLocalObservers(), 24);
			observerDataManager.setCurrentSeasonType(Seasons.SUMMER);
			
		}

		@Override
		protected void onLocalUnitPost(ObserverExchange observerExchange) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSystemShutdown() {
			//write the observed appliance tree after the simulation
			try {
				this.observerDataManager.persistObservedData("logs/observedData.xml");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JAXBException e) {
		
}

		}

		@Override
		public ModelOfObservationExchange getObservedModelData() {
			// TODO Auto-generated method stub
			return null;
		}
}
		