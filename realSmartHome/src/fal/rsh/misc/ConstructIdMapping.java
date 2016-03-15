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


package fal.rsh.misc;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.UUID;

import javax.xml.bind.JAXBException;

import fal.rsh.driver.parser.data.ControllerIdMapping;
import fal.rsh.driver.parser.data.MieleIdMapping;
import fal.rsh.driver.parser.data.WagoPowerIdMapping;
import fal.rsh.driver.parser.data.WagoSwitchIdMapping;
import fal.smartHomeLib.CBTypes.XMLSerialization;

public class ConstructIdMapping {

	private static WagoPowerIdMapping setPowerIdMapping(int wagoId, String smartHomeUUID){
		WagoPowerIdMapping powerIdMapping = new WagoPowerIdMapping();
		powerIdMapping.setWagoMeterId(wagoId);
		powerIdMapping.setSmartHomeDeviceUUID(smartHomeUUID);
		return powerIdMapping;
	}
	
	private static MieleIdMapping setMieleIdMapping(int mieleId,String smartHomeUUID) {
		MieleIdMapping idMapping = new MieleIdMapping();
		idMapping.setMieleId(mieleId);
		idMapping.setSmartHomeDeviceUUID(smartHomeUUID);
		return idMapping;
	}
	
	private static WagoSwitchIdMapping setSwitchIdMapping(int wagoId, String smartHomeUUID){
		WagoSwitchIdMapping switchIdMapping = new WagoSwitchIdMapping();
		switchIdMapping.setWagoswitchId(wagoId);
		switchIdMapping.setSmartHomeDeviceUUID(smartHomeUUID);
		return switchIdMapping;
	}

	public static void main(String[] args) {
		ControllerIdMapping controllerIdMapping = new ControllerIdMapping();

		//set the power mappings
		
		//for the really important devices...
		
		//heatplate
		controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1080,"e2ef0d13-61b3-4188-b32a-1570dcbab4d1"));
		//coffeesystem
		controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1061,"de61f462-cda2-4941-8402-f93a1f1b3e57"));
		//dishwasher
		controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1032,"ab9519db-7a14-4e43-ac3a-ade723802194"));
		//stove
		controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1042,"cef732b1-04ba-49e1-8189-818468a0d98e"));
		//dryer
		controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1030,"1468cc8a-dfdc-418a-8df8-96ba8c146156"));
		//washingmachine
		controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1072,"e7b3f13d-fdf6-4663-848a-222303d734b8"));
		//deepfreezer
		controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1051,"bb4ae893-e71e-4269-ad7e-4f4b5c8ac5aa"));
		
		
		//for the universe and the rest...
		controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1010,"3c37cd7d-a47c-4acb-9a54-95071f77f878"));
		controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1011,"e213060d-cbcc-4d33-a478-b351619e91a7"));
		controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1012,"92284802-d4cf-4728-882e-4f0dab8ca3ae"));

		controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1020,"92d24a9a-5712-4abb-9a4c-5bf47c492f94"));
		controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1021,"dd4e4477-00e6-4fc7-a61b-47fcb2514a08"));
		controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1022,"7d1ee29b-2bb7-480f-9134-f5a5dc716e50"));
		
		//really important device!!!!: controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1030,""));
		controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1031,"ec27d88e-378c-4fb0-8749-b174902a2e28"));
		//really important device!!!!: controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1032,""));
	
		controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1040,"ef3414d5-0d1e-48cb-801b-35c4ed368d8b"));
		controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1041,"cf7b57fe-0816-4848-bc34-94fd7c15a77f"));
		//really important device!!!!: controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1042,""));

		controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1050,"b5c37152-e722-455a-9fe0-f852df49a15b"));
		//really important device!!!!: controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1051,""));
		controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1052,"279fbbdc-18ea-4416-a5b9-840f115ff667"));

		controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1060,"0f5bcd90-f0c6-4819-9fbb-613b42684632"));
		//really important device!!!!: controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1061,""));
		controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1062,"e1f1cc68-80a8-4012-933a-429fc1fe234d"));

		controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1070,"035eff28-e958-4659-99e4-cea4b03940b6"));
		controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1071,"6eafedd2-a09d-45db-b76a-628dee65352b"));
		//really important device!!!!: controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1072,""));

		//really important device!!!!: controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1080,""));
		controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1081,"4cc4dd39-c0f9-4a45-a922-9d143d6cf96a"));
		controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1082,"680bcdab-470e-4e15-b7b1-5e10771e013f"));
		
		controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1090,"a3e13e25-7a34-48e8-8dbf-99abfc9586f4"));
		controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1091,"8fc7f2f3-e2b8-4b2a-af96-616ade6d7fa1"));
		controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1092,"a65b3e08-94ea-4265-bc08-92820aa54bbc"));
		
		controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1100,"8a873e5d-c8ff-4cae-8da6-1c61b13dc8f0"));
		controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1111,"427d46a5-33d2-419a-b6dd-1fb2459ebfd1"));
		controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1112,"c823215c-3978-4897-b3b4-d76ca003c739"));
		
		controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1120,"efd5297e-28b1-4c2f-9ce5-5f85c3514acf"));
		controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1121,"885f992e-3883-49cf-bea8-29650452fe3c"));
		controllerIdMapping.getWagoPowerIdMapping().add(setPowerIdMapping(1122,"f96368c0-3b39-4012-8336-aa727e9e3f78"));
		
		
		//generate miele-id-mapping
		
		//heatplate
        controllerIdMapping.getMieleIdMapping().add(setMieleIdMapping(-1609550966, "e2ef0d13-61b3-4188-b32a-1570dcbab4d1"));
		//coffeesystem
        controllerIdMapping.getMieleIdMapping().add(setMieleIdMapping(-1609551312, "de61f462-cda2-4941-8402-f93a1f1b3e57"));
		//dishwasher
        controllerIdMapping.getMieleIdMapping().add(setMieleIdMapping(-1609555510, "ab9519db-7a14-4e43-ac3a-ade723802194"));
		//stove
        controllerIdMapping.getMieleIdMapping().add(setMieleIdMapping(-1609555623, "cef732b1-04ba-49e1-8189-818468a0d98e"));
		//dryer
        controllerIdMapping.getMieleIdMapping().add(setMieleIdMapping(-1609555628, "1468cc8a-dfdc-418a-8df8-96ba8c146156"));
		//washingmachine
        controllerIdMapping.getMieleIdMapping().add(setMieleIdMapping(-1609555631, "e7b3f13d-fdf6-4663-848a-222303d734b8"));


        //generate switch-id-mapping
        
		//Licht schlafen1
		controllerIdMapping.getWagoSwitchIdMapping().add(setSwitchIdMapping(2015, "962a7c0e-6022-48ab-98d4-4cf0d2a3ab2e"));
		//Licht schlafen2
		controllerIdMapping.getWagoSwitchIdMapping().add(setSwitchIdMapping(2016, "1b2cf8e6-b4b8-4810-8880-db845783f697"));
		//Licht Wozi_vorn
		controllerIdMapping.getWagoSwitchIdMapping().add(setSwitchIdMapping(2017, "5d7e113c-4193-4c52-8444-3886d2a76080"));
		//Licht Wozi_mitte
		controllerIdMapping.getWagoSwitchIdMapping().add(setSwitchIdMapping(2018, "ea26e5ab-c18e-4869-a8be-89c4746dda67"));
		//Licht Kueche
		controllerIdMapping.getWagoSwitchIdMapping().add(setSwitchIdMapping(2019, "01d097f8-8704-416e-bf22-db42f189f0df"));
		//Licht Bad
		controllerIdMapping.getWagoSwitchIdMapping().add(setSwitchIdMapping(2020, "1e66542c-dd85-4902-a190-385edb36df13"));
		
		//marshal it!
		
		try {
			XMLSerialization.marshal2File("configfiles/system/controllerIdMapping.xml", controllerIdMapping);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		

		System.out.println("ID-Mapping done");

	}

}
