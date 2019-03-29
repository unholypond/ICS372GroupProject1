package Test;

import java.util.ArrayList;

import edu.metrostate.ics372groupproject1.scientificDataCollectionApp.Item;
import edu.metrostate.ics372groupproject1.scientificDataCollectionApp.Readings;
import junit.framework.TestCase;


public class ReadingsTest extends TestCase{
	
	public void testReadings() {
		Readings read = new Readings();
		assertNotNull("No object created", read);
	}

	public void testGetReadings() throws Exception{
		Readings read = new Readings();
		Item item1 = new Item();
		Item item2 = new Item("12345", "Temp", "", "12547g", 25, 0);
		ArrayList<Item> list = new ArrayList<Item>();
		list.add(item1);
		list.add(item2);
		read.setReadings(list);
		try {
			assertEquals("The reading was not included", list, read.getReadings());
		}
		catch(NullPointerException e) {
			assertNull("Empty list", list);
		}
	}

	public void testToString() {
		Readings read = new Readings();
		Item item1 = new Item("12345", "Temp", "", "12547g", 25, 0);
		ArrayList<Item> list = new ArrayList<Item>();
		list.add(item1);
		read.setReadings(list);
		String expected = "{\n" + 
				"Site_id = 12345\n" + 
				"reading_type = Temp\n" + 
				"reading_id = 12547g\n" + 
				"reading_value = 25.0\n" + 
				"reading_unit = \n" + 
				"reading_date = 0\n\r" + 
				"}\n\n";
		assertEquals("Wrong string output", expected, read.toString());
	}

}
