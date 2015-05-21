package com.twitter.configloading.util;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Arrays;

public class ObjectUtil {
	
	public static Object getObjectFrom(String value){
		int cursor = value.indexOf(",");
		if( cursor ==  -1){
			Number numberFormat = getNumeric(value);
			if(numberFormat != null){
				return numberFormat;
			 }
			return value;
		}
		return value.split("\\s*,\\s*");
	}
	
	protected static Number getNumeric(String str)
	{
	  NumberFormat formatter = NumberFormat.getInstance();
	  ParsePosition pos = new ParsePosition(0);
	  return formatter.parse(str, pos);
	}
	
	public static String toString(Object value){
		if(value.getClass().isArray()){
			return Arrays.deepToString((Object[]) value);
		}
		return value.toString();
	}
}
