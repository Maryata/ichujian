package com.sys.util;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

/**
 * 执行命令
 * @author giles
 *
 */
public class ProcessClass {
	
	
	/**
	 * 同步使只能有一个进程执行该方法
	 * @param commendlist
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static synchronized Process exec(List<String> commend){
		StringBuffer strBuf = new StringBuffer();
		for(Iterator iter =commend.iterator();iter.hasNext(); ){
			String Strtemp = (String)iter.next();
			strBuf.append(Strtemp);
			strBuf.append(" ");
		}
		try {
			return Runtime.getRuntime().exec(strBuf.toString());
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static synchronized void execBuilder(List<String> commend){
		InputStream in = null;
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commend);
			builder.redirectErrorStream(true);
			Process process = builder.start();
			in = process.getInputStream();
			int bufSize = 1024*10;
			byte[] re = new byte[bufSize];
			while (in.read(re)==-1 ) {
				in.close();
			}
			//process.destroy();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(in!=null){
				try{
					in.close();
					System.gc();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}

}
