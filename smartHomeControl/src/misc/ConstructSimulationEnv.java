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


package misc;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.JAXBException;

import fal.shmd.Optimization.price.PriceSet;
import fal.shmd.Optimization.price.PriceSet.Prices;
import fal.smartHomeLib.CBTypes.XMLSerialization;
import fal.smartHomeLib.CBTypes.Configuration.ControllerBox.ControllerBoxConfiguration;
import fal.smartHomeLib.CBTypes.Configuration.System.AssignedCommDevice;
import fal.smartHomeLib.CBTypes.Configuration.System.AssignedDevice;
import fal.smartHomeLib.CBTypes.Configuration.System.AssignedLocalOCUnit;
import fal.smartHomeLib.CBTypes.Configuration.System.CommDeviceTypes;
import fal.smartHomeLib.CBTypes.Configuration.System.ConfigurationParameter;
import fal.smartHomeLib.CBTypes.Configuration.System.DeviceClassification;
import fal.smartHomeLib.CBTypes.Configuration.System.DeviceTypes;
import fal.smartHomeLib.CBTypes.Configuration.System.HALConfiguration;
import fal.smartHomeLib.CBTypes.Simulation.Screenplay.ActionParameter;
import fal.smartHomeLib.CBTypes.Simulation.Screenplay.ActionParameters;
import fal.smartHomeLib.CBTypes.Simulation.Screenplay.ActionType;
import fal.smartHomeLib.CBTypes.Simulation.Screenplay.PerformAction;
import fal.smartHomeLib.CBTypes.Simulation.Screenplay.Screenplay;
import fal.smartHomeLib.CBTypes.Simulation.Screenplay.SubjectAction;
import fal.smartHomeLib.smartHomeMisc.CSVImporter;

public class ConstructSimulationEnv {

	public static void main(String[] args) {
		Screenplay myScreenplay = new Screenplay();
				
				//this is a scrrenplay for two month
				
				//list of devices
				ArrayList<String> deviceProfiles = new ArrayList<String>();
				
				UUID commDeviceIdSPS = UUID.fromString("0909624f-c281-4713-8bbf-a8eaf3f8e7d6");
				UUID commDeviceIdDoF = UUID.fromString("32c8b193-6c86-4abd-be5a-2e49fee11535");
				UUID _smartdeviceId =  UUID.fromString("f2e27b40-48b5-4dde-af08-6da6946f22ba");
				
				deviceProfiles.add("sampleFiles/wamaProfile.xml");
				deviceProfiles.add("sampleFiles/deepFreezerProfile.xml");
				deviceProfiles.add("sampleFiles/toasterProfile.xml");
				deviceProfiles.add("sampleFiles/stoveProfile.xml");
				deviceProfiles.add("sampleFiles/breadmakerProfile.xml");
				deviceProfiles.add("sampleFiles/dishwasherProfile.xml");
				deviceProfiles.add("sampleFiles/dryerProfile.xml");
				
				DeviceTypes[] deviceNames = {DeviceTypes.WASHINGMACHINE,
											 DeviceTypes.DEEPFREEZER,
											 DeviceTypes.STOVE,
											 DeviceTypes.STOVE,
											 DeviceTypes.STOVE,
											 DeviceTypes.DISHWASCHER,
											 DeviceTypes.DRYER};
				
				UUID[] deviceIds = {UUID.fromString("fce5c484-8558-436d-8173-16e1f92bff14"),
									UUID.fromString("058b48e0-7356-4e4c-a016-f9e88754958c"),
									UUID.fromString("e7fd86f8-e384-4749-88e8-403230dc9201"),
									UUID.fromString("a6b3aeef-c619-4f9c-b35d-c70fe250f7af"),
									UUID.fromString("3f625017-7112-47b5-a006-badd4f97e50f"),
									UUID.fromString("94458f48-b3fc-409c-a8fa-7f3721c63cf0"),
									UUID.fromString("3820d3bc-66dc-46c8-ac58-8d913db001a6")};
				
				
				String[] locObserverClass =   {	"fal.shmd.localunits.Observer.LocalMieleApplianceObserver",
												"fal.shmd.localunits.Observer.LocalMieleApplianceObserver",
												"fal.shmd.localunits.Observer.LocalMieleApplianceObserver",
												"fal.shmd.localunits.Observer.LocalMieleApplianceObserver",
												"fal.shmd.localunits.Observer.LocalMieleApplianceObserver",
												"fal.shmd.localunits.Observer.LocalMieleApplianceObserver",
												"fal.shmd.localunits.Observer.LocalMieleApplianceObserver"};
				
				String[] locControllerClass = {	"fal.shmd.localunits.Controller.LocalMieleApplianceController",
												"fal.shmd.localunits.Controller.LocalMieleApplianceController",
												"fal.shmd.localunits.Controller.LocalMieleApplianceController",
												"fal.shmd.localunits.Controller.LocalMieleApplianceController",
												"fal.shmd.localunits.Controller.LocalMieleApplianceController",
												"fal.shmd.localunits.Controller.LocalMieleApplianceController",
												"fal.shmd.localunits.Controller.LocalMieleApplianceController"};
				
				String[] driverClass = 	      {	"fal.shmd.OfflineSimulation.Subjects.VirtualMieleAppliance",
					     						"fal.shmd.OfflineSimulation.Subjects.VirtualMieleAppliance",
										     	"fal.shmd.OfflineSimulation.Subjects.VirtualMieleAppliance",
										     	"fal.shmd.OfflineSimulation.Subjects.VirtualMieleAppliance",
										     	"fal.shmd.OfflineSimulation.Subjects.VirtualMieleAppliance",
												"fal.shmd.OfflineSimulation.Subjects.VirtualMieleAppliance",
										     	"fal.shmd.OfflineSimulation.Subjects.VirtualMieleAppliance"};
				
				String[] applianceDoF = {"7200","7200","7200","7200","7200","7200","7200"};
				
			//String iDeviceScreeplayDirectory = "sampleFiles/idevice_screenplays/";
			String iDeviceScreeplayDirectory = "sampleFiles/idevice_screenplays_oneDay/";
			
			int choosenPriceCurve = 0;
			
			//int simulationmaxDuration = 5079965;
			int simulationmaxDuration = 86400;
											   
				for (int i = 1; i<=7; i++)
				{
						
					//UUID _deviceId = UUID.randomUUID();
					//deviceIds.add(_deviceId);	
					
					//do some voodoo for the deep freezer
					if(i == 2){
						
//						for(int uTime = 0; uTime < simulationmaxDuration; uTime+=1500){
//							
//							   SubjectAction myAction  = new SubjectAction();
//							   myAction.setTick((long)uTime);		      
//							   myAction.setDeviceID(deviceIds[i-1].toString());
//							   myAction.setNextState(true);
//							   myAction.setActionType(ActionType.I_DEVICE_ACTION);
//							   myScreenplay.getSIMActions().add(myAction);			
//						}
						
						
					}
					else
					{
						//load ideviceschedule:
						ArrayList<Integer> deviceWorkItems = CSVImporter.readIntegerList(iDeviceScreeplayDirectory+"device_"+i+".csv");
					
						for(Integer startTime: deviceWorkItems){
							
							   SubjectAction myAction  = new SubjectAction();
							   myAction.setTick((long)startTime);		      
							   myAction.setDeviceID(deviceIds[i-1].toString());
							   myAction.setNextState(true);
							   myAction.setActionType(ActionType.I_DEVICE_ACTION);
							   myScreenplay.getSIMActions().add(myAction);			
						}
					}
				
				}
			   
			   
			   //creating priceSignal
			   
			   //loading price signal
			   List<double[]> pricesList;
			   pricesList = new ArrayList<double[]>();
			   PriceSet priceSet = null;
			try {
				priceSet = (PriceSet) XMLSerialization.file2Unmarshal("sampleFiles/PriceCurves/"+"prices.xml", PriceSet.class);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JAXBException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			   List<Prices> list = priceSet.getPrices();
			   
				for(int i = 0; i < list.size(); i ++){
					int minutesPerDay = 1440;
					double[] tmp = new double[minutesPerDay];
					int interval = list.get(i).getInterval();
					for(int j = 0; j < tmp.length; j ++){
						tmp[j] = list.get(i).getPrice().get(j/interval);
					}
					pricesList.add(tmp);
				}
				//we chose one...
				double[] price = pricesList.get(choosenPriceCurve);
			   
				//printout price for debug
				for (int i = 0; i < price.length; i++){
					System.out.println(i+ "; " + price[i]);
				}
			
				
				//end
				
				
			   ActionParameters actionParameters = new ActionParameters();
			   actionParameters.setParametersName("PriceSignal");
			   for (int i = 0; i < price.length; i++){
				   ActionParameter parameter = new ActionParameter();
				   parameter.setName(Integer.toString(i));
				   parameter.setValue(Double.toString(price[i]));
				   actionParameters.getParameter().add(parameter);
			   }
			   PerformAction performAction = new PerformAction();
			   performAction.getActionParameterCollection().add(actionParameters);
			   
			   for(int i = 0; i < simulationmaxDuration; i++){
				   if ( ( (i % 86400) == 0 ) || (i == 0)) {
					   SubjectAction commAction = new SubjectAction();
					   commAction.setActionType(ActionType.PROVIDER_SPS_ACTION);
					   commAction.setDeviceID(commDeviceIdSPS.toString());
					   commAction.setTick(i);
					   commAction.getPerformAction().add(performAction);
					   myScreenplay.getSIMActions().add(commAction);
					   //ActionParameters actionParameters = new ActionParameters();
					   //actionParameters.setParametersName("PriceSignal");
					   
					 //  performAction.getActionParameterCollection().add(e)
					 //  commAction.getPerformAction().add(e);
				   }
			   }
			   
			   //now define the dof and create the action...
			   
			   SubjectAction dofAction = new SubjectAction();
			   dofAction.setActionType(ActionType.USER_ACTION);
			   dofAction.setDeviceID(commDeviceIdDoF.toString());
			   dofAction.setTick(0);
			   
			   //dof for each device
			   PerformAction dofPerformAction = new PerformAction();
			   ActionParameters dofParameters = new ActionParameters();
			   dofParameters.setParametersName("DegreeOfFreedom");
			   for(int i = 0; i < 7; i++){
				   ActionParameter parameter = new ActionParameter();
				   parameter.setName(deviceIds[i].toString());
				   parameter.setValue(applianceDoF[i]);
				   dofParameters.getParameter().add(parameter);
			   }
			   
			   dofPerformAction.getActionParameterCollection().add(dofParameters);
			   dofAction.getPerformAction().add(dofPerformAction);
			   
			   myScreenplay.getSIMActions().add(dofAction);
			  
			   
			   try{
				   XMLSerialization.marshal2File("sampleFiles/screenplay.xml", myScreenplay);
			   }
			   catch(Exception ex)
			   {
				   
			   }
			   
			   //and now build the halconfig
			   
			   HALConfiguration halconfig = new HALConfiguration();
			   
			   int count = 1;
			   
			   
			   for(UUID _deviceID:deviceIds){
				 AssignedDevice _assdev = new AssignedDevice();
				 _assdev.setControllable(true);
				 _assdev.setObservable(true);
				 _assdev.setDeviceID(_deviceID.toString());
				 _assdev.setDeviceDescription("");
				 _assdev.setDeviceType(deviceNames[count-1]);
				 _assdev.setDeviceClassification(DeviceClassification.APPLIANCE);
				 _assdev.setDriverClassName(driverClass[count-1]);
				 ConfigurationParameter _param = new ConfigurationParameter();
				 _param.setParameterName("profileSource");
				 _param.setParameterType("String");
				 _param.setParameterValue(deviceProfiles.get(count-1));
				 _assdev.getDriverParameters().add(_param);
				 AssignedLocalOCUnit _lunit = new AssignedLocalOCUnit();
				 _lunit.setLocalControllerClassName(locControllerClass[count-1]);
				 _lunit.setLocalObserverClassName(locObserverClass[count-1]);
				 _assdev.setAssignedLocalOCUnit(_lunit);
				 halconfig.getAssignedDevices().add(_assdev);
				 
				 ++count;
			   }
			   
			   //now adding  a smart meter
			   AssignedDevice smartmeter = new AssignedDevice();
			   smartmeter.setControllable(false);
			   smartmeter.setObservable(true);
			   smartmeter.setDeviceID(_smartdeviceId.toString());
			   smartmeter.setDeviceType(DeviceTypes.SMARTMETER);
			   smartmeter.setDeviceClassification(DeviceClassification.APPLIANCE);
			   smartmeter.setDriverClassName("fal.shmd.OfflineSimulation.Subjects.VirtualSmartMeter");
			   ConfigurationParameter _param = new ConfigurationParameter();
			   _param.setParameterName("resolution");
			   _param.setParameterType("int");
			   _param.setParameterValue("20");
			   smartmeter.getDriverParameters().add(_param);
			   AssignedLocalOCUnit _lunit = new AssignedLocalOCUnit();
			   _lunit.setLocalObserverClassName("fal.shmd.localunits.Observer.SmartMeterObserver");
			   smartmeter.setAssignedLocalOCUnit(_lunit);
			   halconfig.getAssignedDevices().add(smartmeter);
			   
			   //adding comm device for the SPS
			   
			   AssignedCommDevice commDev = new AssignedCommDevice();

			   commDev.setDeviceID(commDeviceIdSPS.toString());
			   commDev.setDeviceType(CommDeviceTypes.ENERGYPROVIDERDRIVER);
			   commDev.setDriverClassName("fal.shmd.OfflineSimulation.Subjects.VirtualPriceSignalGenerator");

			   
			   halconfig.getAssignedCommDevices().add(commDev);
			   
			   //now adding the DoF comm device...
			   
			   AssignedCommDevice dofCommDEV = new AssignedCommDevice();
			   dofCommDEV.setDeviceID(commDeviceIdDoF.toString());
			   dofCommDEV.setDeviceType(CommDeviceTypes.USERINTERACTIONDEVICE);
			   dofCommDEV.setDriverClassName("fal.shmd.OfflineSimulation.Subjects.VirtualDoFProvider");
			   
			   halconfig.getAssignedCommDevices().add(dofCommDEV);
			   
			   try{
				   XMLSerialization.marshal2File("sampleFiles/halconfig.xml", halconfig);
			   }
			   catch (Exception e) {
				// TODO: handle exception
			   }
			   
			   //generate the ControllerBoxConfiguration
			   ControllerBoxConfiguration cbconfig = new ControllerBoxConfiguration();
			   cbconfig.setSimulation(true);
			   fal.smartHomeLib.CBTypes.Configuration.ControllerBox.FilePathes _filePath = new fal.smartHomeLib.CBTypes.Configuration.ControllerBox.FilePathes();
			   _filePath.setLogFileDirectory("logs");
			   cbconfig.setConfigfilePathes(_filePath);
			   cbconfig.setGlobalObserverClass("fal.shmd.globalunits.Observer.SHMDObserver");
			   cbconfig.setGlobalControllerClass("fal.shmd.globalunits.Controller.SHMDController");
			   cbconfig.setCommManagerClass("fal.shmd.globalunits.Controller.SHMDCommManager");
			   try {
				XMLSerialization.marshal2File("sampleFiles/controllerBoxConfig.xml", cbconfig);

			   	} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			   	
			   	System.out.println("Screenplay done");

			}


}
