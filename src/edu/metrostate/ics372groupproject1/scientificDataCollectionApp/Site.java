package edu.metrostate.ics372groupproject1.scientificDataCollectionApp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Site {
	
//	@SerializedName("site_ID")
//	@Expose
	private String siteID;
	private boolean recording;
	private Set<String> readingIDs = new HashSet<String>();
	//Creating collection class to store objects read from JSON file.
	@SerializedName("site_readings")
	@Expose
	private ArrayList<Item> items = new ArrayList<Item>();

	public Site() {
		recording = false;
	}
	public Site (String id) {
		this();
		siteID = id;
	}
	

	public String getSiteID() {
		return siteID;
	}

	public void setSiteID(String siteID) {
		this.siteID = siteID;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setRecording(boolean bool) {
		this.recording = bool;
	}
	
	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}
	
	/**
	 * @param
	 * addItem: takes a single Item as a parameter, adds it to 
	 * the instance of site's list of items
	 * @return
	 * return true if the new item is added to site
	 */
	public boolean addItem(Item i) {
		if(!readingIDs.contains(i.getReadingID()) && recording) {
			this.items.add(i);
			this.readingIDs.add(i.getReadingID());
		}
		return this.items.contains(i);
	}
	
	/**
	 * @param
	 * addReadings: takes a Readings as a parameter, add readings to 
	 * the instance of site list of items
	 * @return
	 * return true if the new item is added to site
	 */
	public boolean addReadings(Readings readings) {
		boolean result = false;
		if(this.recording) {
			for(Item item : readings.getReadings()) {
				if(item.getSiteID() != null && item.getSiteID().equals(this.siteID)) {
					this.items.add(item);
					result = this.items.contains(item);
				}
			}
		}
		return result;
	}
	
	/**
	 * @param
	 * return the status of a site either start collecting
	 * or end collecting
	 * @return
	 * return true if start collecting and false if collection ended
	 */
	public boolean isRecording() {
		return this.recording;
	}
	
	/**
	 * @param
	 * compare two sites for equality
	 * @return
	 * return true if equal and false other wise
	 */
	public boolean equals(Object o) {
		if(o instanceof Site) {
			Site site = (Site)o;
			//true if both siteID field are equal
			return this.siteID.equals(site.getSiteID());
		}else {
			return false;
		}
	}
	
	//to string method 
	public String toString() {
		String text = "";
		for(Item i : items) {
			text += i.toString() + "\n";
		}
		return text;
	}

}