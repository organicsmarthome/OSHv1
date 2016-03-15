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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


/**
  *@author florian.allerding@kit.edu
 *@category smart-home ControllerBox
 *
 */
public class CSVImporter {

	//reads a List from a single-row document
	public static ArrayList<Integer> readIntegerList(String filename)
	{
		ArrayList<Integer> outputList = new ArrayList<Integer>();
		
		try {
			BufferedReader csvReader = new BufferedReader(new FileReader(new File(filename)));
			String _tmpString;
			while ((_tmpString = csvReader.readLine()) != null)
			{
				outputList.add(Integer.parseInt(_tmpString.split(",")[0]));
			}
			
		}
		catch (Exception ex)
		{
			System.out.println("Error reading csv-file: " +ex.getMessage());
		}
		
		return outputList;
	}
	
	//reads a List from a multi-row document
	public static ArrayList<ArrayList<String>> readMultirowList(String filename)
	{
		ArrayList<String> lineList = new ArrayList<String>();
		ArrayList<ArrayList<String>> outputList = new ArrayList<ArrayList<String>>();
		
		try {
			BufferedReader csvReader = new BufferedReader(new FileReader(new File(filename)));
			String _tmpString;
			while ((_tmpString = csvReader.readLine()) != null)
			{
				String[] _tmpLine = _tmpString.split(";");
				for(String rowItem: _tmpLine)
				{
				 //add it to the line...
					lineList.add(rowItem);
				}
				outputList.add(lineList);
			}
			
		}
		catch (Exception ex)
		{
			System.out.println("Error reading csv-file: " +ex.getMessage());
		}
		
		return outputList;
	}
	
	
}
