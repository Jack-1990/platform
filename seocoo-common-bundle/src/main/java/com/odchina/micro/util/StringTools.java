package com.odchina.micro.util;

import java.util.Date;

public class StringTools {

	public  static boolean isEmpty(String obj){
		
		if(obj!=null&&!"".equals(obj)){
			return false;
		}
		return true;
	}

	/**
	 * 获取指定位数的随机数
	 * 
	 * @param position
	 * @return
	 */
	public static String getRandomString(int position)
	{
		// 获取Long时间
		Date date = new Date();
		String time = date.getTime()+"";
		// 除去生成时间位数 剩下 要生成的 位数
		int pos =  position - time.length();
		long pow = (long) Math.pow(10, pos);
		// 生成 指定位数的随机数
		String random = time + (long) (Math.random() * pow);
		return random;
	}
	
}
