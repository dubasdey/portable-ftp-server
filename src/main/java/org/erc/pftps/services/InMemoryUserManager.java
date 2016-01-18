package org.erc.pftps.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.ftpserver.ftplet.Authentication;
import org.apache.ftpserver.ftplet.AuthenticationFailedException;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.User;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.usermanager.UsernamePasswordAuthentication;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.ConcurrentLoginPermission;
import org.apache.ftpserver.usermanager.impl.WritePermission;

public class InMemoryUserManager implements UserManager {

	private BaseUser user;
		
	public void setUser(BaseUser _user){
		user = _user;
		if(user.getAuthorities() == null || user.getAuthorities().isEmpty()){
			List<Authority> authorities = new ArrayList<Authority>();
			authorities.add(new WritePermission());
			authorities.add(new ConcurrentLoginPermission(10, 10));
			user.setAuthorities(authorities);
		}
	}
	
	@Override
	public User authenticate(Authentication auth) throws AuthenticationFailedException {
		if(auth!=null && auth instanceof UsernamePasswordAuthentication){
			UsernamePasswordAuthentication userAuth = (UsernamePasswordAuthentication) auth;
			if(user.getName().equals(userAuth.getUsername()) && user.getPassword().equals(userAuth.getPassword())){
				return user;
			}
		}
		return null;
	}

	@Override
	public void delete(String login) throws FtpException {}

	@Override
	public boolean doesExist(String login) throws FtpException {
		return (user.getName().equals(login));
	}

	@Override
	public String getAdminName() throws FtpException {
		return user.getName();
	}

	@Override
	public String[] getAllUserNames() throws FtpException {
		return new String[]{user.getName()};
	}

	@Override
	public User getUserByName(String login) throws FtpException {
		return user;
	}

	@Override
	public boolean isAdmin(String login) throws FtpException {
		return (user.getName().equals(login));
	}

	@Override
	public void save(User login) throws FtpException {}

}
