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

	/**
	 * 截取用户userId后4位
	 */
	public static String subStrLast(String param, int subCount){

		int length = StringUtils.length(param);
		if(length > subCount){
			return StringUtils.substring(param, length -4);
		}else if(length < subCount){
			return String.format("%0"+subCount+"d", Integer.valueOf(param));
		}
		return null;

	}

	public static void main(String[] args) {

		System.out.println(subStrLast("123", 8));
	}

}
