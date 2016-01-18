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
