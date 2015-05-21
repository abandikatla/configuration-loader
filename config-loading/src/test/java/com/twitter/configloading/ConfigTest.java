package com.twitter.configloading;

import org.junit.Test;

public class ConfigTest {
	
	@Test
	public void load(){
		String filePath = "test.conf";
		String[] o = new String[]{"stage", "prod"};
		Config config = Config.load(filePath, o);
		assert config != null : "Failed";
	}
	
	@Test
	public void get(){
		String filePath = "test.conf";
		String[] o = new String[]{"stage", "prod"};
		Config config = Config.load(filePath, o);
		assert config != null : "Failed";
		assert config.get("group1.path").equals("/srv/uploads") : "Failed";
	}
	
	@Test
	public void getArray(){
		String filePath = "test.conf";
		String[] o = new String[]{"stage", "prod"};
		Config config = Config.load(filePath, o);
		assert config != null : "Failed";
		assert config.get("group1.users") != null : "Failed";
	}
	
	@Test
	public void loadNoOverrides(){
		String filePath = "test.conf";
		Config config = Config.load(filePath, null);
		assert config != null : "Failed";
		assert config.get("group1.users") == null : "Failed";
		assert config.get("group1.path").equals("/srv/uploads") : "Failed";
	}

}
