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

public class constructVirtualKITsmartHome {

	public static void main(String[] args) {
		
		Screenplay myScreenplay = new Screenplay();
				
				//some variables
				String iDeviceScreeplayDirectory = "sampleFiles/idevice_screenplays_KITsmartHome/";
		
				//list of devices
				ArrayList<String> deviceProfiles = new ArrayList<String>();
				
				//int simulationmaxDuration = 5079965;
				int simulationmaxDuration = 86400;
				
				int choosenPriceCurve = 0;
				
				UUID commDeviceIdSPS = UUID.fromString("0909624f-c281-4713-8bbf-a8eaf3f8e7d6");
				UUID commDeviceIdDoF = UUID.fromString("32c8b193-6c86-4abd-be5a-2e49fee11535");
								
				deviceProfiles.add("configfiles/driver/heatPlateProfile.xml");
				deviceProfiles.add("configfiles/driver/coffeeSystemProfile.xml");
				deviceProfiles.add("configfiles/driver/dishwasherProfile.xml");
				deviceProfiles.add("configfiles/driver/stoveProfile.xml");
				deviceProfiles.add("configfiles/driver/dryerProfile.xml");
				deviceProfiles.add("configfiles/driver/wamaProfile.xml");		
				
				DeviceTypes[] deviceNames = {DeviceTypes.HEATPLATE,
											 DeviceTypes.COFFEESYSTEM,
											 DeviceTypes.DISHWASCHER,
											 DeviceTypes.STOVE,
											 DeviceTypes.DRYER,
											 DeviceTypes.WASHINGMACHINE};
				
				UUID[] deviceIds = {UUID.fromString("e2ef0d13-61b3-4188-b32a-1570dcbab4d1"), 
									UUID.fromString("de61f462-cda2-4941-8402-f93a1f1b3e57"),
									UUID.fromString("ab9519db-7a14-4e43-ac3a-ade723802194"),
									UUID.fromString("cef732b1-04ba-49e1-8189-818468a0d98e"),
									UUID.fromString("1468cc8a-dfdc-418a-8df8-96ba8c146156"),
									UUID.fromString("e7b3f13d-fdf6-4663-848a-222303d734b8")};
				
				String[] locObserverClass =   {	"fal.shmd.localunits.Observer.LocalMieleApplianceObserver",
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
												"fal.shmd.localunits.Controller.LocalMieleApplianceController"};
				
				String[] driverClass = 	      {	"fal.shmd.OfflineSimulation.Subjects.VirtualMieleAppliance",
					     						"fal.shmd.OfflineSimulation.Subjects.VirtualMieleAppliance",
										     	"fal.shmd.OfflineSimulation.Subjects.VirtualMieleAppliance",
										     	"fal.shmd.OfflineSimulation.Subjects.VirtualMieleAppliance",
										     	"fal.shmd.OfflineSimulation.Subjects.VirtualMieleAppliance",
										     	"fal.shmd.OfflineSimulation.Subjects.VirtualMieleAppliance"};
				
				boolean[] deviceControllable = {false,
												false,
												true,
												false,
												true,
												true};		
				
				String[] applianceDoF = 	  {	"0",
												"0",
												"7200",
												"0",
												"7200",
												"7200"};
											   
			   //smartplugs
				UUID[] smartPlugIds = {		UUID.fromString("3c37cd7d-a47c-4acb-9a54-95071f77f878"),
											UUID.fromString("e213060d-cbcc-4d33-a478-b351619e91a7"),
											UUID.fromString("92284802-d4cf-4728-882e-4f0dab8ca3ae"),
											UUID.fromString("92d24a9a-5712-4abb-9a4c-5bf47c492f94"),
											UUID.fromString("dd4e4477-00e6-4fc7-a61b-47fcb2514a08"),
											UUID.fromString("7d1ee29b-2bb7-480f-9134-f5a5dc716e50"),
											UUID.fromString("ec27d88e-378c-4fb0-8749-b174902a2e28"),
											UUID.fromString("ef3414d5-0d1e-48cb-801b-35c4ed368d8b"),
											UUID.fromString("cf7b57fe-0816-4848-bc34-94fd7c15a77f"),
											UUID.fromString("b5c37152-e722-455a-9fe0-f852df49a15b"),
											UUID.fromString("279fbbdc-18ea-4416-a5b9-840f115ff667"),
											UUID.fromString("0f5bcd90-f0c6-4819-9fbb-613b42684632"),
											UUID.fromString("e1f1cc68-80a8-4012-933a-429fc1fe234d"),
											UUID.fromString("035eff28-e958-4659-99e4-cea4b03940b6"),
											UUID.fromString("6eafedd2-a09d-45db-b76a-628dee65352b"),
											UUID.fromString("4cc4dd39-c0f9-4a45-a922-9d143d6cf96a"),
											UUID.fromString("680bcdab-470e-4e15-b7b1-5e10771e013f"),
											UUID.fromString("a3e13e25-7a34-48e8-8dbf-99abfc9586f4"),
											UUID.fromString("8fc7f2f3-e2b8-4b2a-af96-616ade6d7fa1"),
											UUID.fromString("a65b3e08-94ea-4265-bc08-92820aa54bbc"),
											UUID.fromString("8a873e5d-c8ff-4cae-8da6-1c61b13dc8f0"),
											UUID.fromString("427d46a5-33d2-419a-b6dd-1fb2459ebfd1"),
											UUID.fromString("c823215c-3978-4897-b3b4-d76ca003c739")};
				
			   //and now build the halconfig
			   
			   HALConfiguration halconfig = new HALConfiguration();
			   
			   //create the appliances
			   for (int i = 0; i < deviceIds.length ; i++) {
			   
				   halconfig.getAssignedDevices().add(createDevice(deviceIds[i], deviceNames[i], 
						driverClass[i], deviceProfiles.get(i), locControllerClass[i], locObserverClass[i], deviceControllable[i]));
			   }
			   
			   //create the smartplugs
			   for (int i = 0; i < smartPlugIds.length; i++) {
				   halconfig.getAssignedDevices().add(createSmartPlug(smartPlugIds[i]));
			   }
			   		   
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
			   
			   //the deviceDispatcher
			   halconfig.setUsingHALdispatcher(false);

			   
			   try{
				   XMLSerialization.marshal2File("configfiles/system/halconfig.xml", halconfig);
			   }
			   catch (Exception e) {
				e.printStackTrace();
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
				XMLSerialization.marshal2File("configfiles/system/controllerBoxConfig.xml", cbconfig);

			   	} catch (Exception e) {
			   		e.printStackTrace();
			   	}
			   	
//so let's look about the screenplays
			   	
			   	//first the appliances
			   	
				for (int i = 1; i <= deviceIds.length; i++)
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
				
//so now add the smart plugs
						   
//load the price signals and add them to the commActions
			   	
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
					   }
				   }

//loading actions for the Dof-Device
				   SubjectAction dofAction = new SubjectAction();
				   dofAction.setActionType(ActionType.USER_ACTION);
				   dofAction.setDeviceID(commDeviceIdDoF.toString());
				   dofAction.setTick(0);
				   
				   //dof for each device
				   PerformAction dofPerformAction = new PerformAction();
				   ActionParameters dofParameters = new ActionParameters();
				   dofParameters.setParametersName("DegreeOfFreedom");
				   for(int i = 0; i < deviceIds.length; i++){
					   ActionParameter parameter = new ActionParameter();
					   parameter.setName(deviceIds[i].toString());
					   parameter.setValue(applianceDoF[i]);
					   dofParameters.getParameter().add(parameter);
				   }
				   
				   dofPerformAction.getActionParameterCollection().add(dofParameters);
				   dofAction.getPerformAction().add(dofPerformAction);
				   
				   myScreenplay.getSIMActions().add(dofAction);


//serialize the screenplay
				   
   try{
	   XMLSerialization.marshal2File("configfiles/simulation/screenplay.xml", myScreenplay);
   }
   catch(Exception ex)
   {
	   System.out.println("Fail to serialize the screenplay");
   }
			   	
			   	System.out.println("config done");

	}
	
	public static AssignedDevice createDevice(UUID deviceId, DeviceTypes deviceType, String driverClassName, String profileSource,			
												String localControllerClass, String localObserverClass, boolean controllable){
		 AssignedDevice _assdev = new AssignedDevice();
		 _assdev.setControllable(controllable);
		 _assdev.setObservable(true);
		 _assdev.setDeviceID(deviceId.toString());
		 _assdev.setDeviceDescription("");
		 _assdev.setDeviceType(deviceType);
		 _assdev.setDeviceClassification(DeviceClassification.APPLIANCE);
		 _assdev.setDriverClassName(driverClassName);
		 ConfigurationParameter _param = new ConfigurationParameter();
		 _param.setParameterName("profileSource");
		 _param.setParameterType("String");
		 _param.setParameterValue(profileSource);
		 _assdev.getDriverParameters().add(_param);
		 AssignedLocalOCUnit _lunit = new AssignedLocalOCUnit();
		 _lunit.setLocalControllerClassName(localControllerClass);
		 _lunit.setLocalObserverClassName(localObserverClass);
		 _assdev.setAssignedLocalOCUnit(_lunit);
		 
		 return _assdev;
	}
	
	public static AssignedDevice createSmartPlug(UUID deviceId){
			AssignedDevice _assdev = new AssignedDevice();
			_assdev.setControllable(false);
			_assdev.setObservable(true);
			_assdev.setDeviceID(deviceId.toString());
			_assdev.setDeviceDescription("SmartHome wall-plugs");
			_assdev.setDeviceType(DeviceTypes.SMARTPLUG);
			_assdev.setDeviceClassification(DeviceClassification.SMARTPLUG);
			_assdev.setDriverClassName("fal.shmd.OfflineSimulation.Subjects.VirtualSmartPlug");
			AssignedLocalOCUnit _lunit = new AssignedLocalOCUnit();
			//_lunit.setLocalControllerClassName("localControllerClass");
			_lunit.setLocalObserverClassName("fal.shmd.localunits.Observer.SmartPlugObserver");
			_assdev.setAssignedLocalOCUnit(_lunit);

			return _assdev;
	}
	
	public static SubjectAction generateSubjectAction(UUID deviceId,int startTime){
		   SubjectAction myAction  = new SubjectAction();
		   myAction.setTick((long)startTime);		      
		   myAction.setDeviceID(deviceId.toString());
		   myAction.setNextState(true);
		   myAction.setActionType(ActionType.I_DEVICE_ACTION);
		   return myAction;	
	}


}
