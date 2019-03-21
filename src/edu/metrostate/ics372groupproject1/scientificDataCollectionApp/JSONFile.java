package edu.metrostate.ics372groupproject1.scientificDataCollectionApp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * JSONFile is a singleton class the handle JSON file
 * It reads, write JSON file into java object
 *
 */
public class JSONFile {

	//Class members 
	private File outputFile;
	
	//constructor, initialize class members
	public JSONFile() {
		outputFile = null;
	}
		
	/**
	 * @param
	 * ReadJSON method takes a File as a parameter, reads the content into Java Objects
	 * and return list of sites objects
	 */	
	public Readings readJSON(File input) throws Exception{
		//Instantiates a BufferReader object that takes the input file as an argument 
		BufferedReader reader = new BufferedReader(new FileReader(input));
		Gson myGson = new Gson(); //instance of GSON 
		Readings myReadings = new Readings();
		myReadings = myGson.fromJson(reader, Readings.class);
		reader.close();
		for(Item i : myReadings.getReadings()) {
			//correction to date and unit in the imported readings
			i.validateDate();
			i.ValidateUnit();
		}
		return myReadings;
	}
	
	/*
	 * WriteToFile method takes as a parameters a list of sites
	 * and a file name. It write the sites in the list to a file on the disk
	 */
	public void writeToFile(Record studyRecord, String outputFileName) throws Exception{
		//path and construct of the output file
		outputFile = new File(System.getProperty("user.dir")+"/src/"+ outputFileName + ".json");
		//Instantiate a PrintWriter object
		PrintWriter writer = new PrintWriter(outputFile);
		//Write JSON object to the specified file on the disk
		Gson myGson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
		String jsonString = myGson.toJson(studyRecord.toArray());
		writer.write(jsonString);
		writer.close();
	}
}
