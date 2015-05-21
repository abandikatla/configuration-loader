package com.twitter.configloading.util;

import org.junit.Test;

public class ObjectUtilTest {

	@Test
	public void getObjectFromInteger(){
		String str = "123";
		assert ObjectUtil.getObjectFrom(str).getClass() == Long.class : "Failed";
		assert ObjectUtil.getObjectFrom(str).equals(new Long(123)) : "Failed";
	}
	
	@Test
	public void arrayToString(){
		Integer[] arr = new Integer[] { 1, 2 };
		assert ObjectUtil.toString(arr).equals("[1, 2]") : "Failed";
	}
}
