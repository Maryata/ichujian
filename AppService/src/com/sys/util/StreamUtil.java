package com.sys.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Description: 此类用于...
 * @author SunGuoGang(2010-3-30)
 * @version 1.0.0
 */
public class StreamUtil {

	/**
	 * @author SunGuoGang(2007-4-12)
	 * @param args
	 */
	public static void main1(String[] args) {
		try {
			System.out.println(readFile("c:/aa.html"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * 判断路径中相关文件夹是否存在，不存在则创建相关文件夹
	 * @param path
	 * @author Darcy
	 * 创建时间 Dec 24, 2009 10:23:45 AM
	 */
	private static void makeDir(String path){
		File file = new File(path);
		if(file.exists()){
			file = null;
			return;
		}else{
			for(int i=path.indexOf("/");i!=-1;i=path.indexOf("/", i+1)){//如果字串最后为“/”结尾，此循环已足够
				String pathSub = path.substring(0,i);
				File fileSub = new File(pathSub);
				if(!fileSub.exists()){
					System.out.println(fileSub.mkdir());
				}
			}
			if(!file.exists()){//如果字串最后不为“/”结尾，此判断保证可创建最后一级文件夹
				file.mkdir();
			}
		}
	}
	public static void main(String[] args) {
		//makeDir("d:/adbc/test/asdf/");
		//System.out.println(new File("d:asdf/test/").mkdir());
		//String str = "asdf/asdf";
		//System.out.println(str.indexOf("g"));
	}

	/**
	 * 读取文件内容
	 * @param inputPath 输入文件全路径
	 * @return 字符串
	 * @throws IOException
	 */
	public static String readFile(String inputPath) throws IOException {
		if (inputPath == null || inputPath.length() == 0) {
			return null;
		}
		inputPath = inputPath.replaceAll("%20", " ");// 处理空格
		StringBuffer returnSb = new StringBuffer();
		BufferedReader inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(
				inputPath), "UTF-8")); // 读取文档
		String temStr = null;
		do {
			temStr = inputStream.readLine();
			if (temStr != null)
				returnSb.append(temStr);
		} while (temStr != null);
		//System.out.println(returnSb.toString());

		return returnSb.toString();

	}

	// */ 存储文件
	public static synchronized void writeHtml(String filePath, String info) {
		PrintWriter pw = null;
		try {
			filePath = filePath.replaceAll("%20", " ");// 处理空格
			//System.out.println(filePath);
			File writeFile = new File(filePath);
			writeFile.mkdirs();
			boolean isExit = writeFile.exists();
			if (isExit != true) {
				writeFile.createNewFile();
			} else {
				writeFile.delete();
				writeFile.createNewFile();
			}
			pw = new PrintWriter(writeFile, "UTF-8");
			pw.println(info);
			// pw.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			pw.close();
		}
	}

	public static synchronized void delHtmlFile(String filePath) {
		try {

			filePath = filePath.replaceAll("%20", " ");// 处理空格
			//System.out.println(filePath);
			File writeFile = new File(filePath);
			boolean isExit = writeFile.exists();
			if (isExit == true) {
				writeFile.delete();
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public static String InputStream2String(InputStream iss) throws IOException {
		if (iss == null)
			return "";
		StringBuffer content = new StringBuffer("");
		BufferedReader reader = new BufferedReader(new InputStreamReader(iss));
		String inLine = reader.readLine();
		while (inLine != null) {
			content.append(inLine);
			content.append("\n");
			inLine = reader.readLine();
		}
		return content.toString();
	}

	/**
	 * 取得系统的根路径
	 */
	public static String getRootPath() {
		String systemRootPath = convertURLToPath(StreamUtil.class.getResource("StreamUtil.class")
				.toString());
		//System.out.println("systemRootPath:"+systemRootPath);
		// 本软件系统的路径
		systemRootPath = convert2RootPath(systemRootPath);
		//System.out.println("222systemRootPath:"+systemRootPath);
		//用新的字符编码生成字符串
		try {
			systemRootPath = java.net.URLDecoder.decode(systemRootPath, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//System.out.println("333systemRootPath:"+systemRootPath);
		systemRootPath = systemRootPath.replaceAll("%20", " ");
		String jarTest=systemRootPath.substring(0, 4);
		 if(jarTest.equals("le:/"))
		 {
			 systemRootPath=systemRootPath.replace(jarTest, "");
		 }
		//System.out.println("getRootPath=="+systemRootPath);
		return systemRootPath;
	}

	private static String convertURLToPath(String url) {
		String strReturn = "";
		boolean isWindows = System.getProperty("os.name").startsWith("Windows");
		if (url != null && url.startsWith("file:") && isWindows == false) {
			strReturn = url.toString().substring("file:".length()); // /home/resen/..
		} else {
			strReturn = url.toString().substring("file:/".length()); // d:/resin/...
		}
		return strReturn;
	}

	private static String convert2RootPath(String classpath) {
		String strReturn = "";
		int index = classpath.indexOf("/WEB-INF/");
		if (classpath != null && index > -1) {
			strReturn = classpath.trim().substring(0, index + 1);
		}
		return strReturn;
	}


	/**
	 * 判断文件是否存在
	 * @param filePathAndName 文件的命名(包括路径和文件名)
	 * @return 存在返回true,不存在返回false
	 */
	public static boolean fileIsExists(String filePathAndName){
		try{
			File file=new File(filePathAndName);
			if(file.exists())
				return true;
			else 
				return false;
		}catch(Exception e){
			throw new RuntimeException(e.getMessage());
		}
		
	}
	
	public static InputStream copyStream(InputStream is){
		try{
			ByteArrayOutputStream os=new ByteArrayOutputStream();
            byte[] buffer = new byte[2048]; 
            int length=0; 
            // 读取myfile文件输出到toFile文件中 
            while ((length = is.read(buffer)) > 0) { 
                os.write(buffer, 0, length); 
            } 
            // 关闭输入流 
            //is.close(); 
            // 关闭输出流 
            InputStream retIs  = new ByteArrayInputStream(os.toByteArray());
            os.close();
            return retIs;
		}catch(Exception e){
			throw new RuntimeException(e.getMessage());
		}
		
	}
	
	public static boolean fileSave(InputStream is,String outPath){
		try{
            //长传到服务器目录 
            File newfile = new File(outPath);
            OutputStream os = new FileOutputStream(newfile); 
            byte[] buffer = new byte[2048]; 
            int length=0; 
            // 读取myfile文件输出到toFile文件中 
            while ((length = is.read(buffer)) > 0) { 
                os.write(buffer, 0, length); 
            } 
            // 关闭输入流 
            is.close(); 
            // 关闭输出流 
            os.close();
		}catch(Exception e){
			throw new RuntimeException(e.getMessage());
		}
		return false;
	}
	
	public static boolean fileSave(File infile,String outPath){
		InputStream is = null;
		 OutputStream os =null;
		try{
			is = new FileInputStream(infile); 
            //长传到服务器目录 
            File newfile = new File(outPath);
            os = new FileOutputStream(newfile); 
            byte[] buffer = new byte[2048]; 
            int length=0; 
            // 读取myfile文件输出到toFile文件中 
            while ((length = is.read(buffer)) > 0) { 
                os.write(buffer, 0, length); 
            } 
           /* // 关闭输入流 
            is.close(); 
            // 关闭输出流 
           os.close();*/ 
		}catch(Exception e){
			//throw new RuntimeException(e.getMessage());
			e.printStackTrace();
		}finally{
			try{
				if(is!=null){ is.close();}
			}catch(Exception e){}
			try{
				if(os!=null){ os.close();}
			}catch(Exception e){}
		}
		return false;
	}
	
	
	
}
