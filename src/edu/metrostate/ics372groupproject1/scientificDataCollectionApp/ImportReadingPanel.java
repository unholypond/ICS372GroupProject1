package edu.metrostate.ics372groupproject1.scientificDataCollectionApp;

import javax.swing.JPanel;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.border.BevelBorder;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
//import javax.swing.JTabbedPane;
import javax.swing.border.EtchedBorder;

@SuppressWarnings("serial")
public class ImportReadingPanel extends JPanel {
	
	protected static final int YES = 0;
	protected static final int NO = 1;
	private File importedFile = null;
	private JSONFile jsonFile;
	private XMLFile xmlFile;
	private Readings readings;
	private String fileName, siteID, StudyName, StudyID;
	private Site selectedSite;
	private Study importedStudy = null; //will reference the current study at hand
	private Record records; // global list of studies collected
	// Swing components
	private JFrame frame;
	private JTabbedPane tabpanel;
	private JFileChooser chooser;
	private JLabel studyNameLabel, studyIDLabel, header, fileNameLabel, siteId, statusLabel;
	private JTextField siteIDField, studyNameField, studyIDField;
	private JTextArea mainDisplay;
	private JButton UploadButton, readButton, startButton, addButton, EndButton, viewButton, exportButton;
	private JScrollPane scrollPane;
	
	public ImportReadingPanel(JFrame frame, Record studyRecord, JTabbedPane cp) {
		//initialize all the widgets
		this.frame = frame;
		jsonFile = new JSONFile();
		xmlFile = new XMLFile();
		records = studyRecord;
		tabpanel = cp;
		initialize();
	}
		
	private void initialize() {
		//local study variable for the current study
		fileNameLabel = new JLabel();
		mainDisplay = new JTextArea();
		fileNameLabel.setForeground(new Color(0, 0, 128));
		fileNameLabel.setBounds(148, 99, 206, 21);
		setFont(new Font("Tahoma", Font.PLAIN, 12));
		setBorder(new TitledBorder ( new EtchedBorder (), "Import Reading"));
		setLayout(null);
		
		header = new JLabel("Enter study name and ID or upload from a file!");
		header.setBounds(148, 21, 314, 15);
		header.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		add(header);
		
		//this Text area will contain the site reading 
		mainDisplay.setEditable(false);
		mainDisplay.setLineWrap(true);
		mainDisplay.setBorder(new TitledBorder ( new EtchedBorder (), "Display Reading"));
		scrollPane = new JScrollPane(mainDisplay);
		scrollPane.setBounds(42, 300, 513, 341);
		scrollPane.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		add(scrollPane);
		
		//Label and Field for a Study Name input
		studyNameLabel = new JLabel("Study Name: ");
		studyNameLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		studyNameLabel.setBounds(40, 56, 83, 17);
		add(studyNameLabel);
		
		studyNameField = new JTextField();
		studyNameField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Takes study name from the user
				StudyName = studyNameField.getText();
				studyNameField.transferFocus();
			}
		});
		studyNameField.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		studyNameField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		studyNameField.setBounds(146, 57, 235, 20);
		studyNameField.setColumns(10);
		add(studyNameField);
		
		//Label and Field for a Study ID input
		studyIDLabel = new JLabel("Study ID: ");
		studyIDLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		studyIDLabel.setBounds(416, 60, 63, 17);
		add(studyIDLabel);
		
		studyIDField = new JTextField();
		studyIDField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Takes study ID from the user
				StudyID = studyIDField.getText();
				studyIDField.transferFocus();
			}
		});
		studyIDField.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		studyIDField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		studyIDField.setBounds(487, 57, 68, 20);
		studyIDField.setColumns(10);
		add(studyIDField);
		
		//Choose the file to be read
		UploadButton = new JButton("Upload File");
		UploadButton.setBounds(40, 99, 83, 21);
		UploadButton.setToolTipText("Navigate to the JSON file.");
		UploadButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		UploadButton.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		UploadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					studyNameField.setText("");
					studyIDField.setText("");
					//reset imported study instance to null
					importedStudy = null;
					importedFile = chooseFile();
					fileNameLabel.setText(getFileName());
					studyNameField.requestFocus();
				}
				catch(Exception e) {
					JOptionPane.showMessageDialog(frame, e.getStackTrace());
				}
			}
		});
		add(UploadButton);
		
		//This functional button will call the ReadJson() or readXMLFile with the input file
		readButton = new JButton("Read File");
		readButton.setBounds(40, 143, 83, 21);
		readButton.setToolTipText("Read the selected JSON file.");
		readButton.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		readButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		readButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				//Call to the method to read the JSON
				if(importedFile != null) {
					try {
						if(isJSON(fileName)) {
							//parse JSON
							readings = jsonFile.readJSON(importedFile);
							if (StudyName != null && StudyID != null && !StudyID.equals("")) {
								importedStudy = new Study(StudyID, StudyName);
								/**
								 * Check if record contains imported study
								 * if so return it 
								 */
								if (records.contains(importedStudy)) {
									importedStudy = getStudyFromRecord(importedStudy);
								}
								//Add empty sites to study
								importedStudy.setSiteForReading(readings);
								//Display the content of the input JSON
								mainDisplay.setText(readings.toString());
								//clear the label for input file
								fileNameLabel.setText("");
								readButton.transferFocus();
							}else {
								String message = "Please provide study ID and name to continue!";
								JOptionPane.showMessageDialog(frame, message);
								//return the cursor to study name field component
								studyNameField.requestFocus();
							}
						}else {
							//parse XML file
							readings = xmlFile.readXMLFile(importedFile);
							//set the study from the imported file
							importedStudy = xmlFile.getStudy();
							if (records.contains(importedStudy)) {
								importedStudy = getStudyFromRecord(importedStudy);
							}
							//Add empty sites to study
							importedStudy.setSiteForReading(readings);
							if (importedStudy != null) {
								//set the study name and ID from the file
								studyNameField.setText(importedStudy.getStudyName());
								studyIDField.setText(importedStudy.getStudyID());
							}
							
							//Display the content of the XML file
							mainDisplay.setText(readings.toString());
							//clear the label for input file
							fileNameLabel.setText("");
							readButton.transferFocus();
						}
					}catch(Exception e) {
						String title = "Error";
						int messageType = JOptionPane.ERROR_MESSAGE;
						e.printStackTrace();
						JOptionPane.showMessageDialog(frame, e.getCause(), title, messageType);
					}
				}
				else {
					JOptionPane.showMessageDialog(frame, "Please choose a File to be read!");
				}
			}
		});
		add(readButton);
				
		fileNameLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		add(fileNameLabel);
		
		//Label to indicate where to enter the site id
		siteId = new JLabel("Site ID:");
		siteId.setBounds(40, 189, 53, 15);
		siteId.setFont(new Font("Tahoma", Font.BOLD, 12));
		add(siteId);
				
		//Text field that takes the site ID of selected site
		siteIDField = new JTextField();
		siteIDField.setBounds(152, 187, 86, 20);
		siteIDField.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		siteIDField.setColumns(10);
		siteIDField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (importedStudy != null) {
					siteID = siteIDField.getText();
					//reference the selected site from the study's list of sites
					if(importedStudy.getSiteByID(siteID) == null) {
						JOptionPane.showMessageDialog(frame, "No site with this ID found!");
					}else {
						selectedSite = importedStudy.getSiteByID(siteID);
					}
					
					/**
					 * Add the study to the records if it is not already
					 * in record
					 */
					if (!records.contains(importedStudy)) {
						records.add(importedStudy);
					}
					//mainDisplay the siteID to the user
					siteIDField.setText("");
					statusLabel.setText("Site ID: " + selectedSite.getSiteID());
				}else {
					JOptionPane.showMessageDialog(frame, "Please select a study first");
				}
				siteIDField.setText("");
			}
		});
		add(siteIDField);
				
		//this functional button allow a site collection to start saving
		startButton = new JButton("Start ");
		startButton.setBounds(397, 225, 68, 21);
		startButton.setToolTipText("Start site collection.");
		startButton.setBackground(UIManager.getColor("Button.background"));
		startButton.setForeground(Color.BLACK);
		startButton.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		startButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(importedStudy.getAllSite().contains(selectedSite)) {
					selectedSite.setRecording(true);
					//Show the selected site and status in the text field
					String info = "Site: "+ selectedSite.getSiteID() +" is now Collecting.";
					statusLabel.setText(info);
				}
				else {
					JOptionPane.showMessageDialog(frame, "Please Enter a site ID to start collecting!");
				}
			}
		});
		add(startButton);
		
		//Label to display whether the site is collecting or end collection
		statusLabel = new JLabel();
		statusLabel.setForeground(new Color(0, 0, 128));
		statusLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		statusLabel.setBounds(42, 225, 272, 21);
		add(statusLabel);
		
		//This button toggle the site boolean recording to true or false		
		EndButton = new JButton("End ");
		EndButton.setBounds(492, 225, 63, 21);
		EndButton.setToolTipText("End site collection");
		EndButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		EndButton.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		EndButton.setBackground(UIManager.getColor("Button.background"));
		EndButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(importedStudy.getAllSite().contains(selectedSite)) {
					//This is where the call to the method to stop saving will go
					selectedSite.setRecording(false);
					//Show the selected site and status in the text field
					String info = "Site: "+siteID +" is no longer Collecting.";
					statusLabel.setText(info);
				}
				else {
					JOptionPane.showMessageDialog(frame, "Please Enter a site ID to stop collecting from!");
				}
			}
		});
		add(EndButton);
				
		//Add collection to a specified site
		addButton = new JButton("Add ");
		addButton.setBounds(42, 257, 68, 21);
		addButton.setToolTipText("Add Items to Site.");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (siteID != null) {
					final String MESSAGE = "Reading added successfuly!\nAdd another reading?";
					final int TYPE = JOptionPane.YES_NO_OPTION;
					if (importedStudy.getAllSite().contains(selectedSite)) {
						//Add readings to matching site in study
						selectedSite.addReadings(readings);
						final int result = JOptionPane.showConfirmDialog(frame, MESSAGE, "Success", TYPE);
						if (result == YES) {
							siteID = null;
							siteIDField.requestFocus();
							
						}else if(result == NO) {
							siteID = null;
						}
					} else {
						JOptionPane.showMessageDialog(frame, "Please enter a site to add collection to!");
					} 
				}else {
					int type = JOptionPane.ERROR_MESSAGE;
					String message = "No Site ID provided!";
					JOptionPane.showMessageDialog(frame, message, "Error", type);
					siteIDField.requestFocus();
				}
			}
		});
		addButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		addButton.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		add(addButton);

				
		//View the readings in study
		viewButton = new JButton("View ");
		viewButton.setBounds(152, 257, 68, 21);
		viewButton.setToolTipText("Show Items for a Site.");
		viewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (selectedSite != null && !importedStudy.getAllSite().isEmpty()) {
					//mainDisplay a selected site's reading
					mainDisplay.setText(importedStudy.getAllSite().toString());
				}
				else {
					JOptionPane.showMessageDialog(frame, "No site selected for display!");
				}
			}
		});
		viewButton.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		viewButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		add(viewButton);
		
		//button to create a reading
		JButton writeReading = new JButton("Create ");
		writeReading.setToolTipText("create a new reading");
		writeReading.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabpanel.setSelectedIndex(1);
			}
		});
		writeReading.setFont(new Font("Tahoma", Font.BOLD, 12));
		writeReading.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		writeReading.setBounds(264, 257, 68, 21);
		add(writeReading);
		
		//this functional button will export the site collection in a JSON format
		exportButton = new JButton("Export JSON");
		exportButton.setBounds(473, 652, 83, 21);
		exportButton.setToolTipText("Export your site(s) to a JSON file.");
		exportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				//This is where the call to the method to write the JSON to a file will go
				try {
					String outputFileName = JOptionPane.showInputDialog(frame,"Name your Export File...");
					if(outputFileName.equals("")) {
						outputFileName = "RecordOfStudy";
					}
					jsonFile.writeToFile(records, outputFileName);
					
					//successful export Message is mainDisplayed on the screen
					String message = String.format("%s has been written successfully! \n", outputFileName);
					JOptionPane.showMessageDialog(frame, message);
				}catch(Exception e) {
					JOptionPane.showMessageDialog(frame, "Export Cancelled!");
				}
			}
		});
		exportButton.setAutoscrolls(true);
		exportButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		exportButton.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, Color.DARK_GRAY, null));
		add(exportButton);
		
	}
			
	/**
	 * @return
	 * Returns the chosen file
	 */	
	private File chooseFile() throws IOException {
		//Specify the current directory for the file chooser()
        File currentDir = new File(System.getProperty("user.dir")+"/src");
        chooser = new JFileChooser(currentDir);
        
        //filter on files with .text extension
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".JSON & .XML files", "json", "xml");
        this.chooser.setFileFilter(filter);
        
        //open the file chooser dialog box

        int status = chooser.showOpenDialog(frame);
        if(status == JFileChooser.APPROVE_OPTION) {
            //Construct the output file name
            fileName =  chooser.getSelectedFile().getName();
        }
        return chooser.getSelectedFile();
	}
	
	/**
	 * @param filename
	 * @return
	 * return a file Extension
	 */
	private boolean isJSON(String filename) {
		String extension = fileName.substring(fileName.lastIndexOf("."));
		if(extension.equals(".json")) {
			return true;
		}else {
			return false;
		}
	}
		
	//method to get the input file name
	public String getFileName() {
		return fileName;
	}
	
	/**
	 * @return
	 * return the current study
	 */
	public Study getImportedStudy() {
		return importedStudy;
	}
	
	/**
	 * @param Study as an input parameter
	 * @return
	 * a study from the record
	 */
	public Study getStudyFromRecord(Study s) {
		int index = records.indexOf(s);
		return records.get(index);
	}
	//extract site ID from a importedStudy
	public String[] extractSiteID(Study st) {
		int size = st.getAllSite().size();
		String[] list = new String[size];
		for(int i = 0; i < size; i++) {
			list[i] = st.getAllSite().get(i).getSiteID();
		}
		return list;
	}
}
