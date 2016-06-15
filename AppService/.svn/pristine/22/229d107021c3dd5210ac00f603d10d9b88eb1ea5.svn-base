package com.sys.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import sun.misc.BASE64Encoder;

import com.sys.commons.AbstractAction;
import com.sys.util.encrypt.Base64;

public class T extends AbstractAction{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			//System.out.println(encodeBase64File("D:\\101.png"));
			String string=Base64.encryptBASE64(Base64.getBytes("E:\\head1.png"));
			
			InputStream userhead=null;
			//InputStream userhead2=null;
		    userhead=getParameter3(string.replaceAll(" ",""));
		    //userhead2=getParameter3(string.replaceAll(" ",""));
		    InputStream userhead2 = StreamUtil.copyStream(userhead);
		    
		    StreamUtil.fileSave(userhead,"E:\\11.png");
		    StreamUtil.fileSave(userhead2,"E:\\12.png");
		    //StreamUtil.fileSave(userhead,"E:\\13.png");
			//String fileurl = FileServices.saveHeadFile(userhead, "73.png" , "USER_HEAD");
			//System.out.println(fileurl+"================");
			//Base64File.string2File(string, "E:\\head.png");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    public static String Ins(){
        try {
            String encoding="utf-8";
            File file=new File("D:\\5000x2.txt");
    		//Date actiondate1=new Date();
    	    Date ss= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2014-10-16 17:18:88");
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = "";
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
    	return "success";
    }
    
    public static String encodeBase64File(String path) throws Exception {
        File  file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int)file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return new BASE64Encoder().encode(buffer);
    }

}
