package edu.metrostate.ics372groupproject1.scientificDataCollectionApp;

import java.io.File;

/**
 * This class save the study record created by a user on exit of the program 
 * and restore the record on program start if the previous record was save
 * by user.
 */
public class ProgramState {
	private static final String STATE_FILE = System.getProperty("user.dir")+"/src/STATE_FILE";
	private Record studyRecord;
	
	public ProgramState(Record record) {
		studyRecord = record;
	}
	
	public static void loadState() {
		File loadFile = new File(STATE_FILE);
		
	}
	
	public static void storeState() {
		File storeFile = new File(STATE_FILE);
		
	}

}
