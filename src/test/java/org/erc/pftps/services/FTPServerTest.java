package org.erc.pftps.services;

import org.junit.Test;

/**
 * The Class FTPServerTest.
 */
public class FTPServerTest {

	/**
	 * Mount test.
	 */
	@Test
	public void mountTest(){
		FTPServer server = new FTPServer();
		server.setPort(19000);
		server.setUser("admin", "admin".toCharArray(), "/tmp");
		server.start();
		
		//TODO check port 
		server.stop();
		
		// Second stop must be ignored
		server.stop();
	}
	
	/**
	 * Sets the user cases test.
	 */
	@Test
	public void setUserCasesTest(){
		FTPServer server = new FTPServer();
		
		// Valid
		server.setUser("admin", "admin".toCharArray(), "/tmp");
		
		// Valid (no password)
		server.setUser("admin", null, "/tmp");
		
		
	}
}
