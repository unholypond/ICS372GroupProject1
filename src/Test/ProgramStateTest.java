package Test;



import edu.metrostate.ics372groupproject1.scientificDataCollectionApp.ProgramState;
import junit.framework.TestCase;

public class ProgramStateTest extends TestCase{

	public void testgetExtensionFromAFile() {
		String fname = "myfile.json";
		String extension = fname.substring(fname.lastIndexOf("."));
		assertEquals(".json", extension);
		fname = "myFile.xml";
		extension = fname.substring(fname.lastIndexOf("."));
		assertEquals(".xml", extension);
	}
	
	public void testCreateStudy() {

	}


	public void testCreateSite() throws Exception {
		ProgramState ps = new ProgramState("/Test/outputStateFile");
		ps.createNewStudy("1", "testStudy");
		assertTrue(ps.createSite("12345"));
		
		assertTrue(ps.createSite("12513"));
		
		assertEquals(2, ps.getCurrentProgramStudy().getAllSite().size());
	}

	
	public void testReadFile() {
		
	}

}
