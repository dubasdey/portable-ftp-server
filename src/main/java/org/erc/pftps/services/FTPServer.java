package org.erc.pftps.services;

import org.apache.ftpserver.ConnectionConfigFactory;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;

public class FTPServer {

	private int port;
	
	private InMemoryUserManager userManager;
	
	public FTPServer(){
		port = 21;
		userManager = new InMemoryUserManager();
	}
	
	public void setUser(String login,String password,String home){
		BaseUser user = new BaseUser();
	    user.setName(login);
	    user.setPassword(password);
	    user.setHomeDirectory(home);
	    user.setEnabled(true);
	    userManager.setUser(user);
	}
	
	public void start(){
		
		ConnectionConfigFactory configFactory = new ConnectionConfigFactory();
		configFactory.setAnonymousLoginEnabled(false);
		configFactory.setMaxLoginFailures(5);
		configFactory.setMaxThreads(20);
		configFactory.setMaxLogins(10);
		configFactory.setMaxAnonymousLogins(10);


		ListenerFactory factory = new ListenerFactory();	
		factory.setPort(port);
		
		FtpServerFactory serverFactory = new FtpServerFactory();
		serverFactory.addListener("default", factory.createListener());
		serverFactory.setUserManager(userManager);
		serverFactory.setConnectionConfig(configFactory.createConnectionConfig());
		
	    FtpServer server = serverFactory.createServer();
	    try{
	        server.start();
	    } catch (FtpException ex){
	        //TODO
	    }
		
	}
}
