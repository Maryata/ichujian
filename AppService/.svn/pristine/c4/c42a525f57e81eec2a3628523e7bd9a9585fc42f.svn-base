package com.sys.util;

import java.io.IOException;

/**
 * Runtime.getRuntime().exec("regedit");//打开注册表编辑器  
Runtime.getRuntime().exec("calc");//打开计算器  
Runtime.getRuntime().exec("shutdown -r -f -t 10");//指定10秒后重启，并且强制结束其他进程  
Runtime.getRuntime().exec("shutdown -s");//关机  
Runtime.getRuntime().exec("shutdown -l");//注销  
 * @author Administrator
 *
 */
public class shutDown {
	
	public static void main(String[] args) {
		try {
			//shutDown.shutDownHaveTimeWithMess("60", "xitong ceshi ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void shutDownAtTime(String time) {
		Runtime run = Runtime.getRuntime();
		try {
			run.exec("at " + time + " Shutdown -s");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void shutDownHaveTime(String time) {
		Runtime run = Runtime.getRuntime();
		try {
			run.exec("Shutdown.exe -s -t " + time);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void shutDownAtNow() {
		Runtime run = Runtime.getRuntime();
		try {
			run.exec("Shutdown.exe -s");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void reboot() {
		Runtime run = Runtime.getRuntime();
		try {
			run.exec("Shutdown.exe -r");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void reboot(String time) {
		Runtime run = Runtime.getRuntime();
		try {
			run.exec("shutdown -r -f -t "+time);//指定10秒后重启，并且强制结束其他进程  
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void shutDownHaveTimeWithMess(String time, String mes) {
		Runtime run = Runtime.getRuntime();
		try {
			run.exec("shutdown -s -t " + time + " -c \"" + mes + "\"");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void noShutDown() {
		Runtime run = Runtime.getRuntime();
		try {
			run.exec("shutdown -a");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
