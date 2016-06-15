package com.sys.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class AC {

	/**
	 * @param args
	 */
	
//	public static String activecode(){
//		File flie=new File("D:\\activecode.txt");
//		BufferedReader reader=new BufferedReader();
//		return "";
//	}
	
	 public static void readTxtFile(String filePath){
	        try {
	                String encoding="GBK";
	                File file=new File(filePath);
	                if(file.isFile() && file.exists()){ //判断文件是否存在
	                    InputStreamReader read = new InputStreamReader(
	                    new FileInputStream(file),encoding);//考虑到编码格式
	                    BufferedReader bufferedReader = new BufferedReader(read);
	                    String lineTxt = null;
	                    while((lineTxt = bufferedReader.readLine()) != null){
	                        System.out.println(lineTxt);
	                    }
	                    read.close();
	        }else{
	            System.out.println("找不到指定的文件");
	        }
	        } catch (Exception e) {
	            System.out.println("读取文件内容出错");
	            e.printStackTrace();
	        }
	     
	    }
	     
	    public static void main(String argv[]){
	        String filePath = "c.txt";
	        readTxtFile(filePath);
	    }


}
