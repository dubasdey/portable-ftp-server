package org.erc.pftps;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * The Class PreferencesTest.
 */
public class PreferencesTest {

	/**
	 * Save store test.
	 */
	@Test
	public void saveStoreTest(){
		Preferences preferences = new Preferences();
		preferences.set("TEST-KEY", "test");
		String key = preferences.getString("TEST-KEY", "error");
		assertEquals("test",key);
	}
}
