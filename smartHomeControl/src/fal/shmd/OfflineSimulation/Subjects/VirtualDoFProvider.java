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


package fal.shmd.OfflineSimulation.Subjects;

import java.util.List;
import java.util.UUID;

import fal.shmd.HAL.HALExchange.Comm.DOFStateUserInteraction;
import fal.smartHomeLib.CBTypes.CBParameterCollection;
import fal.smartHomeLib.CBTypes.Simulation.Screenplay.ActionParameter;
import fal.smartHomeLib.CBTypes.Simulation.Screenplay.SubjectAction;
import fal.smartHomeLib.HAL.HALrealTimeDriver;
import fal.smartHomeLib.SimulationCore.SimulationComm;
import fal.smartHomeLib.SimulationCore.Exception.SimulationSubjectException;


public class VirtualDoFProvider extends SimulationComm {

	
	
	public VirtualDoFProvider(HALrealTimeDriver timerDriver, UUID deviceID,
			CBParameterCollection driverConfig)
			throws SimulationSubjectException {
		super(timerDriver, deviceID, driverConfig);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see fal.SimulationCore.SimulationComm#performNextAction(fal.CBTypes.Simulation.Screenplay.SubjectAction)
	 */
	@Override
	public void performNextAction(SubjectAction nextAction) {
		
		List<ActionParameter> dofParam = nextAction.getPerformAction().get(0).getActionParameterCollection().get(0).getParameter();
		
		DOFStateUserInteraction userInteractionExchange  = new DOFStateUserInteraction();
		
		for (ActionParameter parameter: dofParam){
			userInteractionExchange.addDeviceDof(UUID.fromString(parameter.getName()), Integer.parseInt(parameter.getValue()));
		}
		
		this.updateUnit(userInteractionExchange);
		
		
	}
	
	

}
