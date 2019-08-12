package com.gwg.orm.util;


import org.apache.commons.lang3.StringUtils;

public class StringUtil {
	
	public static boolean isEmpty(Object ...args){
		if(args == null){
			return true;
		}
		for(Object obj : args){
			if(obj == null || obj.equals("")){
				return true;
			}
		}
		return false;
	}


}