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
package org.erc.pftps;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;


/**
 * The Class Preferences.
 */
public class Preferences {
	
	/** The properties. */
	private Properties properties;
	
	/** The Constant userPath. */
	public static final String userPath = System.getProperty("user.home"); 
	
	/**
	 * Instantiates a new preferences.
	 */
	public Preferences() {
		properties = new Properties();
		try {
			Path path = Paths.get(userPath, ".erc.sftp.server");
			if(path.toFile().exists() ){
				properties.load(Files.newInputStream(path));	
			}
		} catch (IOException e) {
			/* Ignored */
		}
	}

	/**
	 * Sets the.
	 *
	 * @param <T> the generic type
	 * @param key the key
	 * @param value the value
	 */
	public <T> void set(String key,T value){
		properties.setProperty(key, value.toString());
	}
	
	/**
	 * Gets the string.
	 *
	 * @param key the key
	 * @param defaultValue the default value
	 * @return the string
	 */
	public String getString(String key,String defaultValue){
		return properties.getProperty(key, defaultValue);
	}

    public void save() {
		Path path = Paths.get(userPath, ".erc.sftp.server");
		OutputStream os;
		try {
			os = Files.newOutputStream(path);
			properties.store(os,"");
			os.flush();
			os.close();			
		} catch (IOException e) {
                    System.out.println("EXCEPTION "+e.getMessage());
			/* Ignored */
		}
    }
    
}
