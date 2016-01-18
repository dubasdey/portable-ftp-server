/*
    This file is part of PortableFtpServer.

    PortableFtpServer is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Foobar is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with PortableFtpServer.  If not, see <http://www.gnu.org/licenses/>.
 */
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
