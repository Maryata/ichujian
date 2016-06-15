package net.jeeshop.core.util;

import java.util.Random;

public class RandNumber {

	public static String getRandNumber(int count){
		String chars = "0123456789"; 
        String result = "";
        for (int i = 0; i < count; i++) 
        { 
            int rand = (int) (Math.random() * 10); 
            result = result+chars.charAt(rand);
        } 
        return result;
	}
	
	public static String getFixLenthString(int strLength) {
	         
	        Random rm = new Random();
	         
	        // 获得随机数
	        double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);
	 
	        // 将获得的获得随机数转化为字符串
	        String fixLenthString = String.valueOf(pross);
	 
	        // 返回固定的长度的随机数
	        return fixLenthString.substring(1, strLength + 1);
	    }
	
	public static void main(String args[]){
		for (int i = 0; i < 100; i++) {
			System.out.println(getFixLenthString(12));	
		}
		
	}
	
	
}
