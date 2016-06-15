package com.org.mokey.basedata.baseinfo.action;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class A {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 try {
		String content = "9876543";
	     String path = "d:/test";
	     
	     MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
	     
	     Map hints = new HashMap();
	     hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
	     BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400,hints);
	     File file1 = new File(path,content+".jpg");
	    
			MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
