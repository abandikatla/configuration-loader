package com.twitter.configloading;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.twitter.configloading.util.ObjectUtil;

public class Properties {
	
	private Map<String, Map<String, Object>> properties;
	
	private Properties(){
		properties = new HashMap<String, Map<String,Object>>();
	}
	
	public static Properties load(String resource,String[] overrides){
		Properties prop = new Properties();
		StringBuilder overridesRegexString = new StringBuilder();
		boolean isOverridden = false;
		if(overrides != null && overrides.length > 0){
			isOverridden = true;
		}
		
		Pattern overridePattern = Pattern.compile("^\\s*([^=<>\\s]+)\\s*<\\s*([^<>=\\s]+)\\s*>\\s*=\\s*([^=<>]*)\\s*$");
		Pattern validOverridesPattern = null;
		Pattern groupPattern = Pattern.compile("^\\s*\\[\\s*([^\\s\\[\\]=]+)\\s*\\]\\s*$");
		Pattern settingPattern = Pattern.compile("^\\s*([^=<>\\s]+)\\s*=\\s*([^=<>]*)\\s*$");
		
		Matcher overrideMatcher = overridePattern.matcher("key<test>=val");
		Matcher validOverridesMatcher = null;
		Matcher settingMatcher = settingPattern.matcher("key=val");
		Matcher groupMatcher =  groupPattern.matcher("[init]");
		
		if(isOverridden){
			for(int i=0; i<overrides.length; i++){
				overridesRegexString.append(overrides[i]);
				if(i < overrides.length-1){
					overridesRegexString.append("|");
				}
			}
			validOverridesPattern = Pattern.compile(overridesRegexString.toString());
			validOverridesMatcher = validOverridesPattern.matcher("test");
		}
		
		BufferedReader reader  = null;
		
		try {
			reader = new BufferedReader(new FileReader(resource));
			String line = null;
			String group= null;
			while((line = reader.readLine()) != null){
				if (line.indexOf("=") != -1) { 
					if(group != null){
						overrideMatcher.reset(line);
						if(overrideMatcher.matches()){
							if(isOverridden){
								String setting = overrideMatcher.group(1);
								String override = overrideMatcher.group(2);
								String value = overrideMatcher.group(3);
								validOverridesMatcher.reset(override);
								if(validOverridesMatcher.matches()){
									prop.put(group, setting, value);
								}
							}
						} else { //default setting
							settingMatcher.reset(line);
							if(settingMatcher.matches()){
								String setting = settingMatcher.group(1);
								String value = settingMatcher.group(2);
								prop.put(group, setting, value);
							} else {
								System.err.println(line + " Malformed input file 1");
								System.exit(1);
							}
						}
					} else {
						System.err.println("Malformed input file 2");
						System.exit(1);
					}
				} else if(line.indexOf("[") != -1){ //group
					groupMatcher.reset(line);
					if(groupMatcher.matches()){
						group = groupMatcher.group(1);
						prop.put(group, null);
					} else {
						System.err.println("Malformed input file 3");
						System.exit(1);
					}
				} 
			}
		} catch (FileNotFoundException e) {
			System.err.println("Cannot open file " + resource + ", Cause : " + e.getCause());
		} catch (IOException e) {
			System.err.println("Unable to read from " + resource + ", Cause : " + e.getCause());
		} finally{
			try {
				reader.close();
			} catch (IOException e) {
				System.err.println("Unable to close the reader, Cause " + e.getCause());
			}
		}
		return prop;
	}
	
	public String get(String group, String setting){
		if(properties.containsKey(group)
				&& properties.get(group) != null
				&& properties.get(group).get(setting) != null){
			return ObjectUtil.toString(properties.get(group).get(setting));
		}
		return null;
	}

	public String get(String group) {
		if (!properties.containsKey(group))
			return null;
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\n");
		if (properties.get(group) != null) {
			for (Iterator<String> iterator = properties.get(group).keySet()
					.iterator(); iterator.hasNext();) {
				String setting = iterator.next();
				sb.append(setting).append(" == ");
				sb.append(ObjectUtil.toString(properties.get(group).get(setting)));
				if (iterator.hasNext()) {
					sb.append(",");
				}
				sb.append("\n");
			}
		}
		sb.append("}");
		return sb.toString();
	}
	
	private void put(String group, String setting, String value){
		Map<String,Object> settingsMap = properties.get(group);
		if(settingsMap == null){
			settingsMap =  new HashMap<String, Object>();
		}
		value = value.trim();
		settingsMap.put(setting, ObjectUtil.getObjectFrom(value));
		properties.put(group, settingsMap);
	}
	
	private void put(String group, Map<String,Object> val) {
		this.properties.put(group, val);
	}
	
}
