package org.erc.pftps.services;

import static org.junit.Assert.*;

import org.apache.ftpserver.ftplet.AuthenticationFailedException;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.User;
import org.apache.ftpserver.usermanager.UsernamePasswordAuthentication;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.junit.Test;


/**
 * The Class InMemoryUserManagerTest.
 */
public class InMemoryUserManagerTest {

	/** The Constant USER. */
	private static final String USER = "admin";
	
	/** The imum. */
	private InMemoryUserManager imum = new InMemoryUserManager();
	
	/**
	 * Instantiates a new in memory user manager test.
	 */
	public InMemoryUserManagerTest(){
		imum = new InMemoryUserManager();
		
		BaseUser user = new BaseUser();
	    user.setName(USER);
	    user.setPassword(USER);
	    user.setEnabled(true);
		imum.setUser(user);
	}
	

	/**
	 * Save ignored T est.
	 *
	 * @throws FtpException the ftp exception
	 */
	@Test
	public void saveIgnoredTEst() throws FtpException{
		BaseUser user = new BaseUser();
	    user.setName("admin2");
	    user.setPassword(USER);
	    user.setEnabled(true);
		imum.save(user);
		
		User u1 = imum.getUserByName("admin2");
		assertNotNull(u1);
		assertEquals(USER,u1.getName());
	}
	
	/**
	 * User exists test.
	 *
	 * @throws FtpException the ftp exception
	 */
	@Test
	public void userExistsTest() throws FtpException{
		// Exists
		assertTrue(imum.doesExist(USER));
	}
	
	/**
	 * Gets the user names test.
	 *
	 * @return the user names test
	 * @throws FtpException the ftp exception
	 */
	@Test
	public void getUserNamesTest() throws FtpException{
		// Exists
		String[] names = imum.getAllUserNames();
		assertNotNull(names);
		assertTrue(names.length == 1);
		assertEquals(USER,names[0]);
	}
	
	/**
	 * Gets the user by name test.
	 *
	 * @return the user by name test
	 * @throws FtpException the ftp exception
	 */
	@Test
	public void getUserByNameTest() throws FtpException{
		User user = imum.getUserByName(USER);
		assertNotNull(user);
	}
	
	
	/**
	 * Delete not work test.
	 *
	 * @throws FtpException the ftp exception
	 */
	@Test
	public void deleteNotWorkTest() throws FtpException{
		imum.delete(USER);
		User user = imum.getUserByName(USER);	
		assertNotNull(user);
	}
	
	/**
	 * User is admin test.
	 *
	 * @throws FtpException the ftp exception
	 */
	@Test
	public void userIsAdminTest() throws FtpException{
		// User is allways admin
 		assertEquals(USER,imum.getAdminName());
 		assertTrue(imum.isAdmin(USER));
	}
	
	
	/**
	 * Authenticate test.
	 *
	 * @throws FtpException the ftp exception
	 */
	@Test
	public void authenticateTest() throws FtpException{



		// Authenticate
 		User loggedUser = null;
		UsernamePasswordAuthentication auth = new UsernamePasswordAuthentication(USER,USER);
		try {
			loggedUser = imum.authenticate(auth);
			assertNotNull(loggedUser);
		} catch (AuthenticationFailedException e) {
			assertTrue(false);
		}
		
		// No authenticate
		auth = new UsernamePasswordAuthentication(USER,"empty");
		try {
			loggedUser = imum.authenticate(auth);
			assertNull(loggedUser);
		} catch (AuthenticationFailedException e) {
			assertTrue(false);
		}		

	}
}
