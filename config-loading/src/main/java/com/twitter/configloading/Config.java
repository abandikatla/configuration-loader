package com.twitter.configloading;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Config {
	
	private Properties properties;
	
	private Config(String resource, String[] overrides){
		//load the data into properties
		properties = Properties.load(resource, overrides);
	}
	
	/**
	 * @param key 
	 * @return - the value of key from the properties
	 */
	public String get(String key){
		
		if(key != null){
			if(key.contains(".")){ //key is group.variable
				int splitPosition = key.indexOf(".");
				String group = key.substring(0, splitPosition);
				String variable = key.substring(splitPosition+1, key.length());
				return properties.get(group, variable);
				
			} else {//key is group
				return properties.get(key);
			}
		}
		return null;
	}
	
	/**
	 * @param resource  - The path of the config file
	 * @param overrides - Array of overrides
	 * @return the Config object with properties loaded from resource.
	 */
	
	public static Config load(String resource, String[] overrides){
		Config config = new Config(resource, overrides);
		return config;
		
	}
	
	public static void main(String[] args){
		String[] o = new String[]{"test", "override"};
		Config config = Config.load("/home/quikr/test2", o);
		System.out.println(config.get("testgroup.setting2"));
	}
	
}
