/**
 * 
 */
package com.org.mokey.demo.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author vpc
 *
 */
public class ServletDownloadUtil {
	private static final Log LOGGER = LogFactory.getLog(ServletDownloadUtil.class);
	private static final String CONTENTTYPE = "application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	private static final String HEADERKEY = "Content-Disposition";
	private static final String HEADERVALUE = "attachment; filename=\"%s\"";
	
	public static void doDownload(HttpServletResponse response,File file) throws IOException {
		doDownload( response, file, CONTENTTYPE,HEADERKEY,String.format( HEADERVALUE, System.currentTimeMillis() + ".xlsx" ) );
	}
	public static void doDownload(HttpServletResponse response,File file,String contentType) throws IOException {
		doDownload( response, file, contentType,HEADERKEY, String.format( HEADERVALUE, System.currentTimeMillis() + ".xlsx" ));
	}
	
	public static void doDownload(HttpServletResponse response,File file,String contentType,String headerKey,String headerValue) throws IOException {
		InputStream in = new FileInputStream( file );
		
		doDownload( response,in,contentType,headerKey,headerValue );
	}
	public static void doDownload(HttpServletResponse response,InputStream in,String contentType,String headerKey,String headerValue) throws IOException {
		
		response.reset();
		response.setContentType( contentType );
		//response.setContentLength((int) file.length());
		response.setHeader( headerKey, headerValue );
		//response.setContentType( "application/force-download" );
		//response.setContentType( "application/x-msdownload" );
		//response.setHeader("Content-Transfer-Encoding", "binary");
		
		OutputStream out = response.getOutputStream();
		
		byte[] buffer = new byte[4096];
		int bytesRead = -1;
		
		while ( (bytesRead = in.read( buffer )) != -1 ) {
			out.write( buffer, 0, bytesRead );
		}
		in.close();
		out.flush();
		out.close();

	}

	public static void write(Workbook wb, String contentType, String headerKey, String headerValue, HttpServletResponse response) {
		ByteArrayInputStream in = null;
		ByteArrayOutputStream out = null;
		// 生成Excel
		try {
			// 创建字节数组输出流
			out = new ByteArrayOutputStream();
			// 将workbook写入输出流
			wb.write(out);
			// 将输出流转成字节数组
			byte[] buf = out.toByteArray();
			// 读入输入流
			in = new ByteArrayInputStream(buf);
			// 下载
			doDownload(response, in, contentType, headerKey, headerValue);
		} catch (Exception e) {
			LOGGER.error("导出数据出错", e);
		}
	}
}
