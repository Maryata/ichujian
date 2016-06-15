package com.qujian.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FileUtil {
	
	public static void checkDir(String paths){
		File file = new File(paths);    
        if(!file.exists()){    
        	file.mkdirs();    
        }
	}
	
	private static boolean dateCompare(long date){
		//timeTransStr(date);
		Date nowDate = new Date();
		//10s
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        cal.add(Calendar.SECOND, -8);
        //timeTransStr(cal.getTimeInMillis());
        if(date > cal.getTimeInMillis()){
        	//System.out.println("10s 内");
        	return false;
        }
        
        //10天内
        cal = Calendar.getInstance();
        cal.setTime(nowDate);
        cal.add(Calendar.DAY_OF_MONTH, -10);
        
        //timeTransStr(cal.getTimeInMillis());
        if(date < cal.getTimeInMillis()){
        	//System.out.println("10t 外");
        	return false;
        }
        return false;
    }
	
	@SuppressWarnings("unused")
	private static void timeTransStr(long time){
		Date dat=new Date(time);  
        GregorianCalendar gc = new GregorianCalendar();   
        gc.setTime(dat);  
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
        String sb=format.format(gc.getTime());  
        System.out.println(sb);
	}
	
	public static boolean isHasFile(String filePath){
		File file = new File(filePath);
		if(file.exists() && file.isFile()){
			if( dateCompare(file.lastModified())){
				return true;
			}
		}
		return false;
	}

	public static String toString(InputStream input, String encoding)
			throws IOException {
		StringWriter sw = new StringWriter();
		copy(input, sw, encoding);
		return sw.toString();
	}
	
	public static void copy(InputStream input, Writer output, String encoding)
			throws IOException {
		if (encoding == null) {
			copy(input, output);
		} else {
			InputStreamReader in = new InputStreamReader(input, encoding);
			copy(in, output);
		}
	}
	
	public static void copy(InputStream input, Writer output)
			throws IOException {
		InputStreamReader in = new InputStreamReader(input);
		copy(in, output);
	}
	
	public static int copy(Reader input, Writer output) throws IOException {
		long count = copyLarge(input, output);
		if (count > 2147483647L)
			return -1;
		else
			return (int) count;
	}
	
	public static long copyLarge(Reader input, Writer output)
			throws IOException {
		char buffer[] = new char[4096];
		long count = 0L;
		for (int n = 0; -1 != (n = input.read(buffer));) {
			output.write(buffer, 0, n);
			count += n;
		}

		return count;
	}

}
