package com.org.mokey.common.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import sun.misc.BASE64Decoder;     
import sun.misc.BASE64Encoder;     
    
/**
 *     
* @ClassName: Base64 
* @Description: (BASE64加密解密类) 
* 
* @date 2013-2-25 下午4:57:32 
*
 */
public class Base64     
{  
	
    /**
     *              
    * @Title: decoder 
    * @Description: (Base64解密方法) 
    * @param @param key
    * @param @return
    * @param @throws Exception    设定文件 
    * @return String 返回类型 
    * @throws
     */
    public static String decode(String key) throws Exception {
        return decode(key,"UTF-8");               
    }
    
    
    public static String decode(String key,String charsetName) throws Exception {
    	 return new String((new sun.misc.BASE64Decoder()).decodeBuffer(key),charsetName);
    }
                  
    /**
     *            
    * @Title: encode 
    * @Description: (Base64加密方法) 
    * @param @param key
    * @param @return
    * @param @throws Exception    设定文件 
    * @return String    返回类型 
    * @throws
     */
    public static String encode(String key) throws Exception {
        return encode(key,"UTF-8");
    }
    
    public static String encode(String key,String charsetName) throws Exception {
        return (new sun.misc.BASE64Encoder()).encodeBuffer(key.getBytes(charsetName));               
    }
    
    /**
     *              
    * @Title: decryptBASE64 
    * @Description: (Base64解密方法) 
    * @param @param key
    * @param @return
    * @param @throws Exception    设定文件 
    * @return byte[]    返回类型 
    * @throws
     */
    public static byte[] decryptBASE64(String key) throws Exception {               
        return (new BASE64Decoder()).decodeBuffer(key);               
    }               
                  
    /**
     *            
    * @Title: encryptBASE64 
    * @Description: (Base64加密方法) 
    * @param @param key
    * @param @return
    * @param @throws Exception    设定文件 
    * @return String    返回类型 
    * @throws
     */
    public static String encryptBASE64(byte[] key) throws Exception {               
        return (new BASE64Encoder()).encodeBuffer(key);               
    }
    
    
    public static byte[] getBytes(String filePath){  
        byte[] buffer = null;  
        try {  
            File file = new File(filePath);  
            FileInputStream fis = new FileInputStream(file);  
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);  
            byte[] b = new byte[1000];  
            int n;  
            while ((n = fis.read(b)) != -1) {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return buffer;  
    }
    
    public static void main(String[] args) throws Exception     
    {     
        String data = Base64.encode("test");
        System.out.println("加密前："+data);
        System.out.println("解密后："+Base64.decode(data));
    }
        
}    
