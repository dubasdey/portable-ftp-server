package org.erc.pftps;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;


/**
 * TODO cypher properties
 * 
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
		Path path = Paths.get(userPath, ".erc.sftp.server");
		OutputStream os;
		try {
			os = Files.newOutputStream(path);
			properties.store(os,"");
			os.flush();
			os.close();			
		} catch (IOException e) {
			/* Ignored */
		}
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
}
