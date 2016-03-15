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
import java.util.UUID;


import fal.shmd.HAL.HALExchange.Comm.DOFStateUserInteraction;
import fal.shmd.HAL.HALExchange.Comm.ScheduledApplianceUIexchange;
import fal.smartHomeLib.ControllerBoxCore.Comm.CommManager;
import fal.smartHomeLib.HAL.HALrealTimeDriver;
import fal.smartHomeLib.HAL.Exchange.HALExchange;
import fal.smartHomeLib.HAL.Exchange.Comm.PriceSignalExchange;


public class SHMDCommManager extends CommManager {

	private ArrayList<ApplianceSchedule> newApplianceSchedules;
	public SHMDCommManager(HALrealTimeDriver systemTimer) {
		super(systemTimer);
		newApplianceSchedules = new ArrayList<ApplianceSchedule>();
	}

	@Override
	public void onSystemIsUp() {
		this.systemTimer.registerComponent(this, 3600);

	}

	@Override
	public void onNextTimePeriode() {
		// TODO Auto-generated method stub

	}
	
	
	
	@Override
	public HALExchange pollGlobalController(UUID commDeviceID) {
		
		ScheduledApplianceUIexchange applianceUIexchange = new ScheduledApplianceUIexchange();
		applianceUIexchange.setCurrentApplianceSchedules(new ArrayList<ApplianceSchedule>());
		if (this.getGlobalController() instanceof SHMDController) { 
			for (int i = 0; i < ((SHMDController)this.getGlobalController()).getApplianceSchedules().size(); i++) {
				applianceUIexchange.getCurrentApplianceSchedules().add(((SHMDController)this.getGlobalController()).getApplianceSchedules().get(i));
			}
			//applianceUIexchange.getCurrentApplianceSchedules().addAll(((SHMDController)this.getGlobalController()).getApplianceSchedules());
		
		}
		return applianceUIexchange;
	}
	



	@Override
	public void receiveDataFromCommDevice(HALExchange halexchange) {
		
		if (halexchange instanceof PriceSignalExchange){
			PriceSignalExchange signalExchange = (PriceSignalExchange) halexchange;
			
			SHMDCommActionSPS commAction = new SHMDCommActionSPS();
			commAction.setPriceSignal(signalExchange.getPriceSignalInMinutes());
			commAction.setLastSPSUpdateTimeStamp((int)systemTimer.getUnixTime());
			this.notifyGlobalController(commAction);
			globalLogger.logDebug("Getting new price-Signal at: " +this.systemTimer.getUnixTime());
		}
		if (halexchange instanceof DOFStateUserInteraction) {
			SHMDCommActioDof shmdCommActioDof = new SHMDCommActioDof();
			shmdCommActioDof.setDeviceDegreeOfFreedom(((DOFStateUserInteraction)halexchange).getDeviceDegreeOfFreedom());
			this.notifyGlobalController(shmdCommActioDof);
			//globalLogger.logDebug("Getting new appliance-dof-Signal at: " +this.systemTimer.getUnixTime());
		}
		
	}

}
