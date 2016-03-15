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


package fal.shmd.globalunits.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import fal.shmd.HAL.HALExchange.PowerProfile;
import fal.shmd.globalunits.Observer.ObservationDataObject;
import fal.shmd.localunits.Observer.LocalMieleApplianceObserver;
import fal.smartHomeLib.CBTypes.Data.Observer.ObservedApplianceAction;
import fal.smartHomeLib.ControllerBoxCore.Comm.CommAction;
import fal.smartHomeLib.ControllerBoxCore.Data.ControllerExchange;
import fal.smartHomeLib.ControllerBoxCore.Exception.ControllerBoxException;
import fal.smartHomeLib.ControllerBoxCore.OC.GlobalController;
import fal.smartHomeLib.ControllerBoxCore.OC.LocalObserver;
import fal.smartHomeLib.HAL.HALrealTimeDriver;

public class SHMDController extends GlobalController{

	
	private ArrayList<ApplianceSchedule> applianceSchedules;
	/**
	 * @return the applianceSchedules
	 */
	public ArrayList<ApplianceSchedule> getApplianceSchedules() {
		return applianceSchedules;
	}


	private double[] currentPriceSignal = null;
	private int lastSPSUpdateTimeStamp = 0;
	private HashMap<UUID, Integer> deviceDegreeOfFreedom; 
	private long lastSchedule = -1; 
	
	public SHMDController(HALrealTimeDriver systemTimer) {
		super(systemTimer);
		this.deviceDegreeOfFreedom = new HashMap<UUID, Integer>();
	}

	/**
	 * 
	 * @param deviceId
	 * @return true if the appliance is already scheduled
	 */
	private boolean actionScheduled(UUID deviceId) {
		
		for (ApplianceSchedule applianceSchedule: applianceSchedules){
			if (applianceSchedule.deviceID.compareTo(deviceId) == 0){
				return true;
			}
		}
		
		return false;
		
	}

	/**
	 * delete an already scheduled appliance, normally you schould do this when you discover, that the appliance 
	 * is not longer able to run (it's perhaps not longer "programmed")
	 * @param applianceId
	 */
	private void deleteScheduledApplianceById(UUID applianceId){
		
		ApplianceSchedule _deleteItem = null;
		for (ApplianceSchedule applianceSchedule: applianceSchedules){
			if (applianceSchedule.deviceID.compareTo(applianceId) == 0) {
				_deleteItem = applianceSchedule;
			}
		}		
		if (_deleteItem  != null){
			applianceSchedules.remove(_deleteItem);
		}	
	}
	
	/**
	 * get the current profile of the appliance
	 * @param deviceID the id of the appliance
	 * @return the profile of the appliance
	 */
	private List<PowerProfile> getCurrentApplianceProfile(UUID deviceID){
		
		LocalObserver localObserver = this.getGlobalObserver().getLocalObserver(deviceID);
		//Dirty-Trick-Harry ?!?
		if (localObserver instanceof LocalMieleApplianceObserver){
			LocalMieleApplianceObserver applianceObserver = (LocalMieleApplianceObserver) localObserver;
		
			return applianceObserver.getCurrentApllianceProfile();
		}
		
		return null;
	}
	
	/**
	 * go thrue the list of appliance action and see if there is a new action and perhaps someting is to do with these new actions...
	 */
	private void handleApplianceActions(){
		ObservationDataObject _data = (ObservationDataObject) this.getGlobalObserver().getObservedModelData();
		ArrayList<ObservedApplianceAction> _devActions = _data.getObservedApplianceAction();
		
		for (ObservedApplianceAction action: _devActions){

			if (action.getTimeStamp() >= lastSchedule) {
				//check if it's programmed => then schedule it
				if ( action.isProgrammed()){
					UUID _devId = UUID.fromString(action.getDeviceID() );
					if (!actionScheduled(_devId)){
						scheduleApplianceById(_devId);
					}
				}
				//check if it's aborted or the user set the appliance manually running 
				//=> then delete it from the schedule list			
				if (action.isStopRunning() || action.isStartRunning()) {
					UUID _devId = UUID.fromString(action.getDeviceID() );
					if (actionScheduled(_devId)){
						deleteScheduledApplianceById(_devId);
						globalLogger.logDebug("deleting scheduled Appliance: " +_devId.toString());
					}
				}
			}
		}
		
		lastSchedule = this.systemTimer.getUnixTime();	
	}

	
	@Override
	public void onCommAction(CommAction commAction) {
		//price signal
		if (commAction instanceof SHMDCommActionSPS){
			this.currentPriceSignal = ((SHMDCommActionSPS) commAction).getPriceSignal();
			this.lastSPSUpdateTimeStamp = ((SHMDCommActionSPS) commAction).getLastSPSUpdateTimeStamp();
		}
		//appliance dof
		if (commAction instanceof SHMDCommActioDof){
			
			this.deviceDegreeOfFreedom = ((SHMDCommActioDof) commAction).getDeviceDegreeOfFreedom();
		}
	}
	
	@Override
	public void onNextTimePeriode() {
		handleApplianceActions();
		setApplianceCommand();	
	}
	
	@Override
	public void onSystemIsUp() throws ControllerBoxException {
		this.systemTimer.registerComponent(this, 1);
		applianceSchedules = new ArrayList<ApplianceSchedule>();
		
	}
	
	private List<Double> calculateProfileInMinutes(List<PowerProfile> powerProfileInSec){
		List<Double> profileInMinutes = new ArrayList<Double>();
		int count = 0;
		double avgPwr = 0;
		for (int i = 0; i < powerProfileInSec.size(); i++) {	
			if (count < 60) {
				avgPwr += powerProfileInSec.get(i).activePower;
				++ count;
			}
			else {
				profileInMinutes.add(avgPwr/60);
				avgPwr = 0;
				count = 0;
			}
		}
		
		return profileInMinutes;
	}
	
	private double[] getCurrentSPSRange(int currentUnixTime,int lastSPSUpdateTimeStamp, double[] priceSignal){
		double[] currentRange = new double[1440];
		
		int offset = (currentUnixTime - lastSPSUpdateTimeStamp)/60; 
		try {
			for (int i = 0; i < currentRange.length; i++){
				currentRange[i] = priceSignal[offset + i];
			}
		}
		catch (Exception ex){
			globalLogger.logError("Price Signal too short! Continue anyway with the given range...");
		}
		
		return currentRange;
	}
	
	private int optimalStartTimeFromNowOn(int LastSPSUpdate ,int startTimeInSec, int dofInSec, List<PowerProfile> powerProfileInSec, double[] priceSignalInMiuntes){
		
		double[] priceInMiuntes = getCurrentSPSRange(startTimeInSec, LastSPSUpdate, priceSignalInMiuntes);
		int dofInMin = dofInSec/60;
		double minCost = 0;
		int optStartTimeDiff = 0;
		List<Double> powerProfileInMin = calculateProfileInMinutes(powerProfileInSec);
		
		//calculate the cost if the appliance is running now and set this as minimum cost
		for(int i = 0; i < powerProfileInMin.size(); i ++){
			minCost = minCost + powerProfileInMin.get(i) * priceInMiuntes[(i)];
		}
		
		double cost;
		
		//the temporal degree of freedom of 
		//an appliance is the time where its workitem has to be done
		int timeForOpti = dofInMin - powerProfileInMin.size();
		//if the tDof is to small start immediately
		if (timeForOpti < 0) timeForOpti = 0;
		
		//slide the workitem of the appliance along it's dof
		for(int i = 1; i <= timeForOpti; i ++){
			cost = 0;
			for(int j = 0; j < powerProfileInMin.size(); j ++){
				cost = cost + powerProfileInMin.get(j) * priceInMiuntes[(i + j)];
			}
			if(minCost > cost){
				minCost = cost;
				optStartTimeDiff =  i;
			}
		}
		
		return startTimeInSec+(optStartTimeDiff*60);
	}
	
	/**
	 * reschedule a single appliance by it's id
	 * @param applianceId
	 */
	private void scheduleApplianceById(UUID applianceId){
		int startTime = 0;	
		try {
			
			//test it the dof is out of range
			if (deviceDegreeOfFreedom.get(applianceId) > 72001) throw new Exception("Dof out of range");
			if (getCurrentApplianceProfile(applianceId).isEmpty()) throw new Exception("Power profile is empty!");
			//startTime = ApplianceReschedule.optimalStartTimeFromNowOn(lastSPSUpdateTimeStamp ,(int) this.systemTimer.getUnixTime(), deviceDegreeOfFreedom.get(applianceId), getCurrentApplianceProfile(applianceId), currentPriceSignal);	
			startTime = this.optimalStartTimeFromNowOn(lastSPSUpdateTimeStamp ,(int) this.systemTimer.getUnixTime(), deviceDegreeOfFreedom.get(applianceId), getCurrentApplianceProfile(applianceId), currentPriceSignal);	
			
			//to avoid some concurrency problems every device is running 60 sec later...						
			startTime = startTime + 60;
			ApplianceSchedule applianceSchedule = new ApplianceSchedule();
			applianceSchedule.deviceID = applianceId;
			applianceSchedule.StartTime =  startTime;
		    applianceSchedules.add(applianceSchedule);
			globalLogger.logDebug("Appliance " + applianceSchedule.deviceID + " scheduled to: " + applianceSchedule.StartTime + " at current time: " + this.systemTimer.getUnixTime());
		}
		catch (Exception ex) {
			globalLogger.logError("Appliance rescheduling failed!",ex);
		}
	}
	

	/**
	 * see in the list of scheduled appliance and decide, which appliance has to start now...
	 */
	private void setApplianceCommand(){
		
		ArrayList<ApplianceSchedule> _deleteList = new ArrayList<ApplianceSchedule>();
		for (ApplianceSchedule applianceSchedule: applianceSchedules){
			if (applianceSchedule.StartTime <= systemTimer.getUnixTime()){
				//time to wait is over...
				//now tell the device that it has to run...
				ControllerExchange _cntEx = new ControllerExchange();
				this.getLocalController(applianceSchedule.deviceID).recommandUnit(_cntEx);
				
				//add to delete list
				_deleteList.add(applianceSchedule);
			}
		}	
		//now deleting the modified items:
		for (ApplianceSchedule _itemToDelete: _deleteList){
			applianceSchedules.remove(_itemToDelete);
		}
	}
	
	

}
