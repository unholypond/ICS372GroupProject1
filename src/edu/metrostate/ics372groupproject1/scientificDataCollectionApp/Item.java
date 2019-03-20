package edu.metrostate.ics372groupproject1.scientificDataCollectionApp;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @param
 * Item class represent the reading object of the site
 * It keeps track of the attributes relating to a particular 
 * reading at a specified site.
 *
 */

public class Item {	
	@SerializedName("site_id")
	@Expose
	private String siteID;
	
	@SerializedName("reading_type")
	@Expose
	private String readingType;
	
	@SerializedName("reading_id")
	@Expose
	private String readingID;
	
	@SerializedName("reading_value")
	@Expose
	private double readingValue;
	
	@SerializedName("reading_unit")
	@Expose
	private String unit = "";
	
	@SerializedName("reading_date")
	@Expose
	private long readingDate;
	
	
	//The constructors of the Item class
	public Item() {
		
	}

	public Item(String siteID, String readingType, String unit, String readingID, double readingValue, long readingDate) {
		this.siteID = siteID;
		this.readingType = readingType;
		this.readingID = readingID;
		this.readingValue = readingValue;
		this.unit = unit;
		this.readingDate = readingDate;	
	}

	public String getSiteID() {
		return siteID;
	}

	public void setSiteID(String siteID) {
		this.siteID = siteID;
	}

	public String getReadingType() {
		return readingType;
	}

	public void setReadingType(String readingType) {
		this.readingType = readingType;
	}

	public String getReadingID() {
		return readingID;
	}

	public void setReadingID(String readingID) {
		this.readingID = readingID;
	}

	public double getReadingValue() {
		return readingValue;
	}

	public void setReadingValue(double readingValue) {
		this.readingValue = readingValue;
	}

	public long getReadingDate() {
		return readingDate;
	}

	public void setReadingDate(long readingDate) {
		this.readingDate = readingDate;
	}
	
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	//defining equality among items
	public boolean equals(Object object) {
		Item nitem = null;
		if(object instanceof Item) {
			nitem = (Item)object;
		}
		final boolean EQUAL_SITE_ID = this.siteID.equals(nitem.getSiteID());
		final boolean EQUAL_READING_TYPE = this.readingType.equals(nitem.getReadingType());
		final boolean EQUAL_READING_ID = this.readingID.equals(nitem.getReadingID());
		final boolean EQUAL_READING_VALUE = this.readingValue == nitem.getReadingValue();
		//return true if equal and false other wise
		return EQUAL_SITE_ID && EQUAL_READING_TYPE && EQUAL_READING_ID && EQUAL_READING_VALUE;
	}
	
	//To string method
		public String toString() {
			return "{\nSite_id = " + siteID + "\nreading_type = " + readingType + 
					"\nreading_id = " + readingID + "\nreading_value = " + readingValue + 
					"\nreading_unit = "+ unit + "\nreading_date = " + readingDate +
					"\n}";
		}
}
