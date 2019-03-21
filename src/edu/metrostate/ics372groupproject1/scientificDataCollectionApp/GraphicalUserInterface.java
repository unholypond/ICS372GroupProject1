package edu.metrostate.ics372groupproject1.scientificDataCollectionApp;

import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GraphicalUserInterface {
	private JFrame frame;
	private JTabbedPane tabPanel;
	private Record record;
	private final int OPTION_TYPE = JOptionPane.YES_NO_OPTION;
	private final int MESSAGE_TYPE = JOptionPane.QUESTION_MESSAGE;
	private static final String TITLE = "Save on Exit";
	private static final String MESSAGE = "Would you like to save your study?"; //choice to save state or not
	private static final int YES = 0;
	private static final int NO = 1; 
	
	//Create the application (the constructor).
	public GraphicalUserInterface() {
		//check if stateFile exit, load it
//		if(StateFile != null) {
//			List<Study> records = new Record();
//			record = 
//		}
		//call to instance method in the constructor
		initialize();
	}

	/** 
	 * @param
	 * Initialize the contents of the frame, and a tab panel that contains
	 * the two panel import panel and create panel.
	 */
	private void initialize() {
		record = new Record();   //list of all studies
		frame = new JFrame("Scientific Data Recorder");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				//Ask the user where they want to save the state or not
				int option = JOptionPane.showConfirmDialog(frame, MESSAGE, TITLE, OPTION_TYPE, MESSAGE_TYPE);
				if(option == YES) {
					//Save the state 
					System.exit(YES);
				}else if(option == NO) {
					//exit program without saving state
					System.exit(NO);;
				}else {
					//save the state if other circumstance force the program to exit
					
				}
			}
		});
		
		tabPanel = new JTabbedPane();
		tabPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		//main panel that takes care of input files
		ImportReadingPanel importReadingPanel = new ImportReadingPanel(frame, record, tabPanel);
		//create new study and readings panel
		CreateReadingPanel createReadingPanel = new CreateReadingPanel(frame, record, tabPanel);
		tabPanel.addTab("Import", importReadingPanel);
		tabPanel.setToolTipTextAt(0, "Import study from a file system");
		tabPanel.addTab("Create", createReadingPanel);
		tabPanel.setToolTipTextAt(1, "Create a reading for a site");
		frame.getContentPane().add(tabPanel);
		frame.setLocationByPlatform(true);
	}
	
	//Launch the application.
		public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						GraphicalUserInterface window = new GraphicalUserInterface();
						window.frame.setVisible(true);
						window.frame.setPreferredSize(new Dimension(625, 760));
						window.frame.pack();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
}
