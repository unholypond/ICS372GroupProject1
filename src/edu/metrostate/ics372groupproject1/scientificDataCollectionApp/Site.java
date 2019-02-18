package edu.metrostate.ics372groupproject1.scientificDataCollectionApp;

import java.util.ArrayList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Site {
	
	private String siteID;
	private boolean recording;

	public Site() {
		recording = false;
	}
	
	/**
	 * Creating collection class to store objects read from JSON file.
	 */
	@SerializedName("site_readings")
	@Expose
	private ArrayList<Item> items = new ArrayList<Item>();

	public String getSiteID() {
		return siteID;
	}

	public void setSiteID(String siteID) {
		this.siteID = siteID;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}
	
	public void setRecording(boolean t) {
		this.recording = t;
	}
	
	public void addItem(Item i) {
		this.items.add(i);
	}
	
	public boolean isRecording() {
		return this.recording;
	}
	
	public String toString() {
		String text = "";
		for(Item i : items) {
			text += i.toString() + "\n\n";
		}
		return text;
	}

}
