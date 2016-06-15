package com.sys.util.listener;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.PropertyConfigurator;

import com.sys.util.StreamUtil;

/**
 * 日志回滚文件必须要制定绝对路径比如d:/tomcate/webapp/.../logFile.log,
 * 而一般的做法是在log4.properties文件中指定绝对路径。这在每个开发人员本地存在多个各异的文件，
 * 很不利于管理和集成。所以这里仅在log4.properties文件中设置相对路径：log/rollingFile.log
 * 在web.xml中配置本servlet.这样开发人员没有必要关心log4j的配置了。
 * 
 * 运行本servlet后log4.properties文件在被修改，所以在第一次运行的时候，
 * 在本servlet运行前就加载了log4.properties文件的各个模块(比如hibernate、spring、struts、
 * servlet)只有在第二次运行起来后才能生效.
 * 
 * @author: Adam.Sun 
 * Email: adam.sun@grandsys.com 
 * Date: 2008-1-8 
 * Time: 10:39:45
 */

public class SysLog4jPluginListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent sce) {	
	}

	public void contextInitialized(ServletContextEvent sce) {
		String prefix=StreamUtil.getRootPath();
		//get Absolute file path as: D:/tomcat/webapp/products
		//String prefix = config.getServletContext().getRealPath("/");
		toPrint("Absolute path prefix is:" + prefix);
		String filePath = "";
		try {
			// load log4j config properties file eg:
			// D:/tomcat/webapp/products/WEB-INF/classes/log4j.properties
			filePath = prefix + "/WEB-INF/classes/log4j.properties";
			toPrint("config file is:" + filePath);

			//init the stream
			Properties props = new Properties();
			FileInputStream istream = new FileInputStream(filePath);
			props.load(istream);
			istream.close();
			//get log RollingFile path of Canonical path,eg: /log/rollFile.log
			String canonicalRFile = props.getProperty("log4j.appender.RollingFile.File");
//			String pisFinished = props
//					.getProperty("log4j.appender.RollingFile.Seted");
//			toPrint("Did it changed ? :" + pisFinished);
			toPrint("this cfg : "+canonicalRFile);
			//if has done it befor ,no necessary do it again.
			if (canonicalRFile.startsWith("/log")
					|| canonicalRFile.startsWith("\\log")
					|| canonicalRFile.startsWith("log")
					|| !canonicalRFile.startsWith(prefix)) {
				canonicalRFile = "/log/rollFile.log";
				//eg: D:/tomcat/webapp/products/WEB-INF/log/rollFile.log
				String logFile = prefix + canonicalRFile;
				toPrint("target file :" + logFile);
				props.setProperty("log4j.appender.RollingFile.File", logFile);
//				props.setProperty("log4j.appender.RollingFile.Seted", "true");
				PropertyConfigurator.configure(props);//save it in jv
				//save it to disk
				FileOutputStream out = new FileOutputStream(filePath);
				props.store(out, filePath);
				out.close();
				toPrint("Finish init log4j file!");
			}else{
				toPrint("No change!");
			}
			
		} catch (IOException e) {
			toPrint("Could not read configuration file [" + filePath+ "].");
			toPrint("Ignoring configuration file [" + filePath + "].");

		}
	}
	/**
	 * this class is to cofig log4j file,
	 * so we can‘t use log4j logger to print info.
	 * @param content
	 */
	private static void toPrint(String content) {
		System.out.println("Log4j: "+content);
	}

}
