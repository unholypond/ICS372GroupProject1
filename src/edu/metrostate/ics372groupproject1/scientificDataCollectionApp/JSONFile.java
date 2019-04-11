package edu.metrostate.ics372groupproject1.scientificDataCollectionApp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

/**
 * 
 * JSONFile is a singleton class the handle JSON file
 * It reads, write JSON file into java object
 *
 */
public class JSONFile {

	//Class members 
	private static File outputFile = null;
	private static FileReader reader = null;
	
	/**
	 * @param
	 * ReadJSON method takes a File as a parameter, reads the content into Java Objects
	 * and return list of sites objects
	 */	
	public static Readings readJSON(File input) throws Exception{
		//Instantiates a BufferReader object that takes the input file as an argument 
		reader = new FileReader(input);
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
	
	/**
	 * @param
	 * return a record that encapsulates the previous study
	 */
	public static Record loadState(String fileName) throws Exception {
		final File FILE = new File(System.getProperty("user.dir")+"/src/"+fileName+".json");
		//Instantiates a BufferReader object that takes the input file as an argument 
		reader = new FileReader(FILE);
		Record myRecord = Record.getInstance();
		boolean hasState = false;
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(FILE));
		//check if state file is empty
		if (br.readLine() != null) {
			hasState = true;
		}
		if(hasState) {
			Gson myGson = new GsonBuilder().setLenient().create(); //instance of GSON 
			Study[] study = myGson.fromJson(reader, Study[].class);
			for(int i = 0; i < study.length; i++) {
				myRecord.addStudy(study[i]);
			}
		}
		reader.close();
		return myRecord;
	}
	
	/*
	 * WriteToFile method takes as a parameters a list of sites
	 * and a file name. It write the sites in the list to a file on the disk
	 */
	public static void writeToFile(Record studyRecord, String outputFileName) throws Exception{
		//path and construct of the output file
		outputFile = new File(System.getProperty("user.dir")+"/src/"+ outputFileName + ".json");
		//Instantiate a PrintWriter object
		FileWriter writer = new FileWriter(outputFile);
		//Write JSON object in pretty format
		Gson myGson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
		//new instance of JSON Object that will contains iterations of study
		JsonArray jObject = new JsonArray();
		Iterator<Study> it = studyRecord.iterator();
		while(it.hasNext()) {
			jObject.add(myGson.toJsonTree(it.next()));
		}
		
		String jsonString = myGson.toJson(jObject);
		writer.write( jsonString);
		writer.close();
	}
}
