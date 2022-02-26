package org.erc.pftps.services;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * The Class FTPServerTest.
 */
public class FTPServerTest {

	private static final String ADMIN = "admin";

	/**
	 * Mount test.
	 */
	@Test
	public void mountTest(){
		FTPServer server = new FTPServer();
		server.setPort(19000);
		server.setUser(ADMIN, ADMIN.toCharArray(), "/tmp");
		server.start();
		
		//TODO check port 
		server.stop();
		
		// Second stop must be ignored
		server.stop();
		
		assertTrue("All ok",true);
	}
	
	/**
	 * Sets the user cases test.
	 */
	@Test
	public void setUserCasesTest(){
		FTPServer server = new FTPServer();
		
		// Valid
		server.setUser(ADMIN, ADMIN.toCharArray(), "/tmp");
		
		// Valid (no password)
		server.setUser(ADMIN, null, "/tmp");
		assertTrue("All ok",true);
		
	}
}
