package com.org.mokey.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * # WMI 获取服务器信息
 * @author Administrator
 *
 */
public class SystemVbUtil {
	/** log available to subclasses */
	private static final Logger log = Logger.getLogger(SystemVbUtil.class);
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args){
		log.debug("init");
		long stime = System.currentTimeMillis();
		SystemVbUtil st = new SystemVbUtil();
		
		List<String> netList = st.getNetworkAdapter();
		log.debug(netList);
		/*for(String ns : netList){
			log.debug("name:"+ns);
			String net = st.getPNetworkAdapter(ns);
			log.debug("st:"+net);
		}*/
		
		String net = st.getPNetworkAdapter(null);
		log.debug("st:"+net);

		long etime = System.currentTimeMillis();
        System.out.println("time:"+ (etime - stime));
	}
	
	public static List<String> getNetworkAdapter()
	  {
		List<String> result = new ArrayList<String>();;
	    try {
	      String line;
	      File file = File.createTempFile("tmp_NA", ".vbs");
	      file.deleteOnExit();
	      FileWriter fw = new FileWriter(file);
	      
	      String vbs = "On Error Resume Next " +
	      		"\r\n\r\n" +
	      		"strComputer = \".\"  " +
	      		"\r\n" +
	      		"Set objWMIService = GetObject(\"winmgmts:\" _ " +
	      		"\r\n" +
	      		"    & \"{impersonationLevel=impersonate}!\\\\\" & strComputer & \"\\root\\cimv2\") " +
	      		"\r\n" +
	      		"Set colItems = objWMIService.ExecQuery(\"Select * from Win32_NetworkAdapterConfiguration \")  " +// Where IPEnabled='TRUE'
	      		"\r\n " +
	      		"For Each objItem in colItems" +
	      		"\r\n" +
	      		"     Wscript.Echo objItem.Description  " +
	      		"\r\n" +
	      		"     'exit for  ' do the first cpu only! " +
	      		"\r\n" +
	      		"Next	";

	      fw.write(vbs);
	      fw.close();
	      Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
	      BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream(),"GBK"));

	      while ((line = input.readLine()) != null){
	        result.add(line);
	      }
	      input.close();
	      file.delete();
	    } catch (Exception e) {
	      e.fillInStackTrace();
	    }
	    
	    return result;
	  }
	
	public static String getPNetworkAdapter(String name)
	  {
	    String result = "";
	    try {
	      String line;
	      File file = File.createTempFile("tmp_NA", ".vbs");
	      file.deleteOnExit();
	      FileWriter fw = new FileWriter(file);
	      
	      String vbs = "On Error Resume Next " +
	      		"\r\n\r\n" +
	      		"strComputer = \".\"  " +
	      		"\r\n" +
	      		"Set objWMIService = GetObject(\"winmgmts:\" _ " +
	      		"\r\n" +
	      		"    & \"{impersonationLevel=impersonate}!\\\\\" & strComputer & \"\\root\\cimv2\") " +
	      		"\r\n" +
	      		"Set colItems = objWMIService.ExecQuery(\"Select * from Win32_PerfRawData_Tcpip_NetworkInterface\")  " +// Where Name = '"+name +"'
	      		"\r\n " +
	      		"For Each objItem in colItems" +
	      		"\r\n     Wscript.Echo objItem.Name  " +
	      		"\r\n     Wscript.Echo objItem.BytesReceivedPersec  " +
	      		"\r\n     Wscript.Echo objItem.BytesSentPersec  " +
	      		"\r\n     Wscript.Echo objItem.BytesTotalPersec  " +
	      		"\r\n" +
	      		"     'exit for  ' do the first cpu only! " +
	      		"\r\n" +
	      		"Next	";

	      fw.write(vbs);
	      fw.close();
	      Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
	      BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream(),"GBK"));

	      while ((line = input.readLine()) != null){
	    	System.out.println("-- "+line);
	        result = result + line + ";";
	      }

	      input.close();
	      file.delete();
	    } catch (Exception e) {
	      e.fillInStackTrace();
	    }
	    if ((result.trim().length() < 1) || (result == null))
	      result = "";

	    return result.trim();
	  }
	
	 public static String getCPUSerial()
	  {
	    String result = "";
	    try {
	      String line;
	      File file = File.createTempFile("tmp", ".vbs");
	      file.deleteOnExit();
	      FileWriter fw = new FileWriter(file);

	      String vbs = "On Error Resume Next \r\n\r\nstrComputer = \".\"  \r\nSet objWMIService = GetObject(\"winmgmts:\" _ \r\n    & \"{impersonationLevel=impersonate}!\\\\\" & strComputer & \"\\root\\cimv2\") \r\nSet colItems = objWMIService.ExecQuery(\"Select * from Win32_Processor\")  \r\n For Each objItem in colItems\r\n     Wscript.Echo objItem.ProcessorId  \r\n     'exit for  ' do the first cpu only! \r\nNext                    ";

	      fw.write(vbs);
	      fw.close();
	      Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
	      BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

	      while ((line = input.readLine()) != null)
	        result = result + line + ";";

	      input.close();
	      file.delete();
	    } catch (Exception e) {
	      e.fillInStackTrace();
	    }
	    if ((result.trim().length() < 1) || (result == null))
	      result = "No CUP ID";

	    return result.trim();
	  }
	
	
}
