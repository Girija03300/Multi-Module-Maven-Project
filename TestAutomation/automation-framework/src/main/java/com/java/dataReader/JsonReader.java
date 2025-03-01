package com.java.dataReader;


import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.java.base.SetUp;
import com.java.utils.Utilities;


public class JsonReader extends Utilities{
	
	public static String getDataFromJson(String object , String data) throws IOException, ParseException
	{	
		JSONParser parser  = new JSONParser();
		FileReader jsonFile = new FileReader(SetUp.BaseDirectory+getconfigPropertyValue("jsonFilePath"));
		JSONObject jsonObject = (JSONObject) parser.parse(jsonFile);
		JSONObject getPageObject = (JSONObject)jsonObject.get(object);
		String value= getPageObject.get(data).toString();
		return value;
	}
}
