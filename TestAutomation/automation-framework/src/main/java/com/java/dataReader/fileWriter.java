package com.java.dataReader;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.java.utils.Utilities;

public class fileWriter extends Utilities{

	FileWriter fileWriter;
	BufferedWriter writer; 
	
	
	public fileWriter(String filePath) throws IOException
	{
		fileWriter  = new FileWriter(filePath);
		writer = new BufferedWriter(fileWriter);		
		}
	public synchronized void writeData(String data) throws IOException
	{
	
		writer.write(data);
		writer.newLine();
	}
	public void closeFile() throws IOException
	{
		writer.flush();
		writer.close();
	}
}
