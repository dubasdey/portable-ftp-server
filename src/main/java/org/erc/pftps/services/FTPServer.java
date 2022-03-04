/*
    This file is part of PortableFtpServer.

    PortableFtpServer is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    PortableFtpServer is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with PortableFtpServer.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.erc.pftps.services;

import java.io.File;

import org.apache.ftpserver.ConnectionConfigFactory;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.ssl.SslConfigurationFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;

/**
 * The Class FTPServer.
 */
public class FTPServer {

	/** The user manager. */
	private InMemoryUserManager userManager;
	
	/** The server. */
	private FtpServer server;
	
	private ListenerFactory factory;
	/**
	 * Instantiates a new FTP server.
	 */
	public FTPServer(){
		userManager = new InMemoryUserManager();
		factory = new ListenerFactory();
		factory.setIdleTimeout(60);
	}
	
	/**
	 * Sets the port.
	 *
	 * @param port the new port
	 */
	public void setPort(int port) {
		factory.setPort(port);
	}
	
	/**
	 * Sets the user.
	 *
	 * @param login the login
	 * @param password the password
	 * @param home the home
	 */
	public void setUser(String login,char[] password,String home){
		BaseUser user = new BaseUser();
	    user.setName(login);
	    if(password !=null && password.length>0){
	    	user.setPassword(new String(password));
	    }
	    user.setHomeDirectory(home);
	    user.setEnabled(true);
	    userManager.setUser(user);
	}
	
	/**
	 * SSL Configuration
	 * @param jksPath
	 * @param password
	 */
	public void setSSL(String jksPath,String password) {
		
		File file = new File(jksPath);
		
		// define basic SSL configuration
		SslConfigurationFactory ssl = new SslConfigurationFactory();
		ssl.setKeystoreFile(file);
		ssl.setKeystorePassword(password);
		
		// set the SSL configuration for the listener
		factory.setSslConfiguration(ssl.createSslConfiguration());
		factory.setImplicitSsl(true);
	}
	/**
	 * Stop.
	 */
	public void stop(){
		if (server!=null && !server.isStopped()){
			server.stop();
			server = null;
		}
	}
	
	/**
	 * Start.
	 */
	public boolean start(){

		stop();
		
		ConnectionConfigFactory configFactory = new ConnectionConfigFactory();
		configFactory.setAnonymousLoginEnabled(false);
		configFactory.setMaxAnonymousLogins(0);
		
		configFactory.setMaxLoginFailures(5);
		configFactory.setLoginFailureDelay(30);

		configFactory.setMaxThreads(10);
		configFactory.setMaxLogins(10);
		
		FtpServerFactory serverFactory = new FtpServerFactory();
		serverFactory.addListener("default", factory.createListener());
		serverFactory.setUserManager(userManager);
		serverFactory.setConnectionConfig(configFactory.createConnectionConfig());

	    server = serverFactory.createServer();
	    
	    try{
	        server.start();
	    } catch (FtpException ex){
	    	System.err.println(ex.getMessage());
	    	return false;
	    }
	    return true;
		
	}
}
