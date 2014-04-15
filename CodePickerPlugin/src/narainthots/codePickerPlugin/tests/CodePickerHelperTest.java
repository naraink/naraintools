package narainthots.codePickerPlugin.tests;

import static org.junit.Assert.*;
import narainthots.codePickerPlugin.CodePickerHelper;

import org.junit.Test;

public class CodePickerHelperTest {

	@Test
	public void testGetSuggestions() {
		CodePickerHelper cph = new CodePickerHelper();
		try {
			System.out.println(cph.getSuggestions("fibonacci"));
			System.out.println(cph.getTitles());
			System.out.println(cph.getLinks());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//fibonacci
		}
		
	}

}
