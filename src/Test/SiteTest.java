package Test;

import java.util.ArrayList;
import java.util.Collection;

import edu.metrostate.ics372groupproject1.scientificDataCollectionApp.Item;
import edu.metrostate.ics372groupproject1.scientificDataCollectionApp.Site;
import junit.framework.TestCase;

public class SiteTest extends TestCase{

	public void testGetSiteID() {
		Site newSite = new Site("mySite");
		String expected = "siteId";
		assertNotNull("Null ID", newSite.getSiteID());
		assertEquals("Wrong site id", newSite.getSiteID(), expected);
	}

	public void testSetSiteID() {
		Site newSite = new Site();
		newSite.setSiteID("siteId");
		String expected = "siteId";
		assertNotNull("Null ID", newSite.getSiteID());
		assertEquals("Wrong site id", newSite.getSiteID(), expected);
	}

	public void testGetItems() {
		Site newSite = new Site("12345");
		Item item1 = new Item("12345", "Temp", "Fahrenheit", "12547g", 25, 0);
		Item item2 = new Item("12345", "Temp", "Fahrenheit", "12547g", 26, 2);
		newSite.setRecording(true);
		newSite.addItem(item1);
		newSite.addItem(item2);
		assertTrue(newSite.getItems().contains(item1) && newSite.getItems().contains(item2));
	}

	public void testSetRecording() {
		Site newSite = new Site("mySite");
		newSite.setRecording(true);
		assertTrue(newSite.isRecording());
		newSite.setRecording(false);
		assertFalse(newSite.isRecording());
	}

	public void testSetItems() {
		Site newSite = new Site("12345");
		ArrayList<Item> items = new ArrayList<Item>();
		Item item1 = new Item("12345", "Temp", "Fahrenheit", "12547g", 25, 0);
		Item item2 = new Item("12345", "Temp", "Fahrenheit", "12547g", 26, 2);
		newSite.setRecording(true);
		items.add(item1);
		items.add(item2);
		newSite.setItems(items);
		assertTrue(newSite.getItems().contains(item1) && newSite.getItems().contains(item2));
	}

	public void testAddItem() {
		Site newSite = new Site("12345");
		Item item1 = new Item("12345", "Temp", "Fahrenheit", "12547g", 25, 0);
		Item item2 = new Item("12345", "Temp", "Fahrenheit", "12547g", 26, 2);
		newSite.setRecording(true);
		newSite.addItem(item1);
		newSite.addItem(item2);
		assertTrue(newSite.getItems().contains(item1) && newSite.getItems().contains(item2));
	}

	public void testIsRecording() {
		Site newSite = new Site("mySite");
		newSite.setRecording(true);
		assertTrue(newSite.isRecording());
		newSite.setRecording(false);
		assertFalse(newSite.isRecording());
	}

	public void testEquals() {
		Site newSite = new Site("mySite");
		Site newSite2 = new Site("mySite");
		assertTrue(newSite.equals(newSite2));
		Site newSite3 = new Site("differentSite");
		assertFalse(newSite.equals(newSite3));
	}
	
	public void testToString() {
		// To be implemented
	}
}
