package com.twitter.configloading;

import org.junit.Test;

public class PropertiesTest {
	
	@Test
	public void load(){
		String filePath = "test.conf";
		String[] o = new String[]{"stage", "prod"};
		Properties properties = Properties.load(filePath, o);
		assert properties != null : "Failed";
	}
	
	@Test
	public void get(){
		String filePath = "test.conf";
		String[] o = new String[]{"stage", "prod"};
		Properties properties = Properties.load(filePath, o);
		assert properties.get("group1","path").equals("/srv/uploads") : "Failed";
	}
	
	@Test
	public void getGroup(){
		String filePath = "test.conf";
		String[] o = new String[]{"stage", "prod"};
		Properties properties = Properties.load(filePath, o);
		assert properties.get("group1").equals("{\nusers == [[amulya, ankita, sandeep]],\npath == /srv/uploads,\nuser_count == 3\n}") : "Failed";
	}

}
