package edu.metrostate.ics372groupproject1.scientificDataCollectionApp;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This class maintain the states of the current program execution:
 * the studies record, the current study, sites, and readings along
 * with imported file if one exist and readings from the imported file
 * 
 */
public class ProgramState {
	private Record currentProgramRecord = null;
	private Study currentProgramStudy = null;
	private Site currentProgramSite = null;
	private Item currentProgramItem = null;
	private File currentProgramImputFile = null;
	private Readings currentProgramReadings = null;
	private boolean isRecordEmpty;
	private XMLFile xmlfile;
	private final String JSON = ".json";
	private final String XML = ".xml";
	private JFileChooser chooser;
	protected String inputFileName;
	
	/**
	 * Constructor
	 * @param stateFile
	 */
	public ProgramState(String stateFile) throws Exception{
		//return record from previous state if it exist
		currentProgramRecord = JSONFile.loadState(stateFile);
		isRecordEmpty = currentProgramRecord.isEmpty();
	}
	
	/**
	 * Only create a study if it hasn't already been created
	 * @param studyID
	 * @param StudyName
	 * @return 
	 */
	public boolean createNewStudy(String studyID, String studyName) {
		if(!isRecordEmpty) {
			currentProgramStudy = new Study(studyID, studyName);
		}else if(currentProgramRecord.getStudy(studyID, studyName) == null) {
			currentProgramStudy = new Study(studyID, studyName);
		}else {
			currentProgramStudy = currentProgramRecord.getStudy(studyID, studyName);
		}
		return currentProgramRecord.addStudy(currentProgramStudy);
	}

	/**
	 * Creates a new site only if it doesn't already exist
	 * @param siteID
	 * @return
	 */
	public boolean createSite(String siteID) {
		if(currentProgramSite == null) {
			currentProgramSite = new Site(siteID);
		}else if(currentProgramStudy.getSiteByID(siteID) == null) {
			currentProgramSite = new Site(siteID);
		}
		return currentProgramStudy.addSite(currentProgramSite);
	}
	
	/**
	 * Creates a new reading
	 * @param siteID
	 * @param readingType
	 * @param unit
	 * @param readingID
	 * @param readingValue
	 * @param readingDate
	 * @return
	 * @throws Exception 
	 */
	public void itemFactory(
			String siteID, 
			String readingType, 
			String unit, 
			String readingID, 
			Double readingValue, 
			Long readingDate) throws Exception {
		if (currentProgramSite != null) {
			currentProgramSite.setRecording(true);
			currentProgramSite.addItem(
					new Item(siteID, 
							readingType, 
							unit, 
							readingID, 
							readingValue, 
							readingDate)
					);
		}else {
			throw new Exception("Cannot add to Null Site");
		}
	}
	
	/**
	 * Takes a file of the user choice and reads the file
	 * @param readingFile
	 */
	public void readFile(File readingFile)throws Exception {
		String extension = getExtension(readingFile.getName());
		if(extension.equals(JSON)) {
			currentProgramReadings  = JSONFile.readJSON(readingFile);
			currentProgramStudy.setSiteForReading(currentProgramReadings);
		}else if(extension.equals(XML)) {
			currentProgramReadings = xmlfile.readXMLFile(readingFile);
			if (currentProgramReadings != null) {
				currentProgramStudy = xmlfile.getStudy();
				currentProgramStudy.setSiteForReading(currentProgramReadings);
			}
		}
	}
	
	/**
	 * Take a file name and return the file extension
	 * @param filename
	 * @return
	 */
	private String getExtension(String filename) {
		String extension = filename.substring(filename.lastIndexOf("."));
		return extension;
	}

	/**
	 * export the record to a file of the user's choice
	 * @param outputFileName
	 * @throws Exception
	 */
	public void exportStudies(String outputFileName) throws Exception{
		JSONFile.writeToFile(currentProgramRecord, outputFileName);
	}
	
	/**
	 * @return
	 * Returns the chosen file
	 */	
	public File chooseFile() throws IOException {
		//Specify the current directory for the file chooser()
        File currentDir = new File(System.getProperty("user.dir")+"/src");
        chooser = new JFileChooser(currentDir);
        
        //filter on files with .text extension
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".JSON & .XML files", "json", "xml");
        this.chooser.setFileFilter(filter);
        
        //open the file chooser dialog box

        int status = chooser.showOpenDialog(null);
        if(status == JFileChooser.APPROVE_OPTION) {
            //Construct the output file name
            inputFileName =  chooser.getSelectedFile().getName();
        }
        return chooser.getSelectedFile();
	}
	
	/**
	 * 
	 * @return
	 */
	public Record getCurrentProgramRecord() {
		return currentProgramRecord;
	}

	/**
	 * 
	 * @param currentProgramRecord
	 */
	public void setCurrentProgramRecord(Record currentProgramRecord) {
		this.currentProgramRecord = currentProgramRecord;
	}

	/**
	 * 
	 * @return
	 */
	public Study getCurrentProgramStudy() {
		return currentProgramStudy;
	}

	/**
	 * Check for study in record if null create new
	 * @param currentProgramStudy
	 */
	public void setCurrentProgramStudy(Study activestudy) {
			currentProgramStudy = activestudy;
	}

	/**
	 * 
	 * @return
	 */
	public Site getCurrentProgramSite() {
		return currentProgramSite;
	}

	/**
	 * 
	 * @param currentProgramSite
	 */
	public void setCurrentProgramSite(String siteID) {
		if (currentProgramStudy != null) {
			if(currentProgramStudy.getSiteByID(siteID) != null) {
				currentProgramSite = currentProgramStudy.getSiteByID(siteID);
			}else {
				currentProgramSite = null;
			}
		}
	}
	
	/**
	 * call to start collecting for current site
	 */
	public void startCollectingReadings() {
		if(currentProgramSite != null) {
			currentProgramSite.setRecording(true);
			if(currentProgramReadings != null) {
				currentProgramSite.addReadings(currentProgramReadings);
			}
		}
	}
	
	/**
	 * end current site collection
	 */
	public void endCollectingReadings() {
		if(currentProgramSite != null) {
			currentProgramSite.setRecording(false);
		}
	}

	/**
	 * 
	 * @return
	 */
	public Item getCurrentProgramItem() {
		return currentProgramItem;
	}

	/**
	 * 
	 * @param currentProgramItem
	 */
	public void setCurrentProgramItem(Item currentProgramItem) {
		this.currentProgramItem = currentProgramItem;
	}

	/**
	 * 
	 * @return
	 */
	public File getCurrentProgramImputFile() {
		return currentProgramImputFile;
	}

	/**
	 * 
	 * @param currentProgramImputFile
	 */
	public void setCurrentProgramImputFile(File currentProgramImputFile) {
		this.currentProgramImputFile = currentProgramImputFile;
	} 
	

}
