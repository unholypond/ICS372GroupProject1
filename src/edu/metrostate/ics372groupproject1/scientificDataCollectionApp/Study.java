package edu.metrostate.ics372groupproject1.scientificDataCollectionApp;

import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
import java.util.Iterator;
//import java.util.Map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Study {
	
	@SerializedName("study_name")
	@Expose
	private String studyName;
	
	@SerializedName("study_id")
	@Expose
	private String studyID;
	
	@SerializedName("sites")
	@Expose
	private ArrayList<Site> listOfSites = new ArrayList<>();
	//constructors
	public Study() {
		this.studyID = "";
		this.studyName = "";
	}
  
	public Study(String studyID, String studyName) {
		this.studyID = studyID;
		this.studyName = studyName;		
	}
	
	public String getStudyName() {
		return studyName;
	}

	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}

	public String getStudyID() {
		return studyID;
	}

	public void setStudyID(String studyId) {
		this.studyID = studyId;
	}
	
	//adding sites to a study, takes a site as parameter
	public boolean addSite(Site site) {
		if(!listOfSites.contains(site)) {
			listOfSites.add(site);
		}
		return listOfSites.contains(site);
	}
	
	//set all site to start collecting
	public void startAllSiteCollection() {
		for(Site s : listOfSites) {
			s.setRecording(true);
		}
	}
	
	//end all sites collection
	public void endAllSiteCollection() {
		for(Site s: listOfSites) {
			s.setRecording(false);
		}
	}
		
	/**
	 * @param
	 * setSiteForReading method takes a Readings object and sets up a list
	 * of sites associated with each reading object under a study
	 * @return
	 * void
	 */
	public void setSiteForReading(Readings reading) {
		Iterator<Item> itemIterator = reading.getReadings().iterator();
		//Iterate over readings to create site and associate the items to it
		while (itemIterator.hasNext()) {
			Item currentItem = itemIterator.next();
			if (currentItem.getSiteID() != null) {
				Site currentSite = new Site(currentItem.getSiteID());
				if (!listOfSites.contains(currentSite)) {
					//the site is not yet in study so add empty site to study
					listOfSites.add(currentSite);
				} 
			}
		}
	}
	
	/**
	 * @param
	 * This method takes an ID string as parameter and return 
	 * a site from study that match the input string ID
	 * @return
	 * a Site is return to the caller
	 */
	public Site getSiteByID(String siteId) {
		Site temp = null;
		for(Site s : listOfSites) {
			if(s.getSiteID() != null) {
				if(s.getSiteID().equals(siteId)) {
					temp = s;
					break;
				}
			}
		}
		return temp;
	}
	
	//get the list of sites in study
	public ArrayList<Site> getAllSite() {
		return listOfSites;
	}
	
	@Override
	//equality of study implementation
	public boolean equals(Object o) {
		Study nstudy = null;
		if(o instanceof Study) {
			nstudy = (Study)o;
		}
		return this.studyID.equals(nstudy.getStudyID()) && this.studyName.equals(nstudy.getStudyName());
	}
	
	@Override
	//Override the inherited toString method from Object class
	public String toString() {
		String text = "";
		for(Site s : listOfSites) {
			text += s.toString() + "\n";
		}
		return "\nStudy_ID: " + this.studyID +"\nStudy_Name: "+ this.studyName +"\n" + text;
	}
	
	//remove empty site from study
	public void removeEmptySite() {
		ArrayList<Site> rSite = new ArrayList<Site>();
		for (Site s : listOfSites) {
			if (s.isEmpty()) {
				rSite.add(s);
			} 
		}
		for(Site s : rSite) {
			listOfSites.remove(s);
		}
	}
}	

