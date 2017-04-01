package cn.seocoo.platform.common.util;

import java.util.Random;


public class StringUtil {

	
	public static boolean isEmpty(String str){
		if(str!=null&&!"".equals(str)){
			return false;
		}
		return true;
	}
	
	public static boolean isEmpty(Object str){
		if(str!=null&&!"".equals(str)){
			return false;
		}
		return true;
	}
	
	public static long parseLong(String s, long defaultVal) {
		try{
			return Long.valueOf(s);
		}catch(Exception e) {
			return defaultVal;
		}
	}
	
	public static long parseLong(String s) {
		return parseLong(s, 0);
	}
	
	public static double parseDouble(String s, double defaultVal) {
		try{
			return Double.valueOf(s);
		}catch(Exception e) {
			return defaultVal;
		}
	}
	
	public static double parseDouble(String s) {
		return parseDouble(s, 0);
	}
	
	public static int parseInt(String s, int defaultVal) {
		try{
			return Integer.valueOf(s);
		}catch(Exception e) {
			return defaultVal;
		}
	}
	
	public static int parseInt(String s) {
		return parseInt(s, 0);
	}
	
	public static String markupEncode(String s) {
		if(s == null) {
			return "";
		}
		
		s = s.replaceAll("<", "&lt;");
		s = s.replaceAll(">", "&gt;");
		
		return s;
	}
	
	public static String replaceEmpty(String str) {
		return str == null ? "" : str;
	}
	
	/** 
	 * 判断是否包含字符串
	 * @param source 源字符串
	 * @param str 包含的字符
	 * @return
	 */
	public static boolean contain(String source, String str) {
		String[] strs = source.split(",");
		for (int i = 0; i < strs.length; i++) {
			if (str.equals(strs[i])) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 去除mac地址冒号并变成大写
	 * @param oldStr
	 * @return
	 */
	public static String clearColons(String oldStr){
		oldStr = oldStr.replace(":", "");
		oldStr = oldStr.toUpperCase();
		return oldStr;
	}
	
	/**
	 * 将去除冒号的mac地址拼接上冒号
	 * @param oldStr
	 * @return
	 */
	public static String addColons(String oldStr){
		if (oldStr.length()<12) {
			return "mac地址不合法";
		}
		oldStr = oldStr.substring(0, 2) + ":" + oldStr.substring(2, 4) + ":" + oldStr.substring(4, 6) 
				+ ":" + oldStr.substring(6, 8) + ":" + oldStr.substring(8, 10) + ":" + oldStr.substring(10, 12);
		return oldStr;
	}
	
	/**
	 * 获取随机码
	 * @return
	 */
	public static String randomCodeByLength(int length){
		
		String s = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random r = new Random();
		String result ="";
		for (int i =0; i < length; i++ ){
			int n = r.nextInt(35);
			result += s.charAt(n);
		}
		return result;
	}
	
	
	public static boolean isLetter(char c) {   
	       int k = 0x80;   
	       return c / k == 0 ? true : false;   
	   }  
	/**  
	    * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1  
	    * @param String s 需要得到长度的字符串  
	    * @return int 得到的字符串长度  
	    */   
	   public static int length(String s) {  
	       if (s == null)  
	           return 0;  
	       char[] c = s.toCharArray();  
	       int len = 0;  
	       for (int i = 0; i < c.length; i++) {  
	           len++;  
	           if (!isLetter(c[i])) {  
	               len++;  
	           }  
	       }  
	       return len;  
	   } 
	   
	   public static String random6(){
			Random random = new Random();
			String result="";
			for(int i=0;i<6;i++){
			result+=random.nextInt(10);
			}
			return result;
		}
}
