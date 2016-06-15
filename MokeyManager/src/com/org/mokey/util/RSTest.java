package com.org.mokey.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RSTest {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		RSTest op = new RSTest();
		// op.uploadFile();
		// op.getFile();--ok
		// op.deleteFile(); -- ok
		// op.dynamicPic();//--ok
		// op.getFileByURL();--ok
		// op.truncateResource();
		for (int i = 0; i < 1; i++) {
			new Thread(new TestThread1()).start();
		}
	}
}

class TestThread1 implements Runnable {
	//private static final Logger log = Logger.getLogger(TestThread1.class);
	private int i = 0;

	public void run() {
		this.truncateResource();
		/*while (i <= 100) {
			getPackage();
			upload();
			getFile();
			if(i % 50 ==1){
				//truncateResource();
			}
			i++;
		}*/
	}

	public void upload() {
		try {
			Map params = new HashMap();
			params.put("user_id", "04FADB81-4BC0-4D1F-BDE8-51578309F725");
			params.put("packagename","互动");
			params.put("groupid","2");
			params.put("title","测试");
			params.put("content","");
			params.put("index",0);
			params.put("rtype","1");
			params.put("width",300);
			params.put("height",600);
			params.put("weight","555");
			String BOUNDARY = "----SMFEtUYQG6r5B920"; // 定义数据分隔线
			URL url = new URL("http://localhost:80/admin/file/uploadFile.action");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			conn.setRequestProperty("Charsert", "UTF-8");
			conn.setRequestProperty("Content-Type",
					"multipart/form-data; boundary=" + BOUNDARY);
			
			
			
			OutputStream out = new DataOutputStream(conn.getOutputStream());
            ////1.先写文字形式的post流 
            //头 
            String boundary = BOUNDARY; 
            //中 
            StringBuffer resSB = new StringBuffer("\r\n"); 
            //尾 
            String endBoundary = "\r\n--" + boundary + "--\r\n"; 
            //strParams 1:key 2:value 
            Iterator<String> sit = params.keySet().iterator();
            while (sit.hasNext()) {
    			String key = sit.next();
    			resSB.append("Content-Disposition: form-data; name=").append(key).append("\r\n").append("\r\n").append(params.get(key)).append("\r\n").append("--").append(boundary).append("\r\n"); 
    			
    		}
            String boundaryMessage = resSB.toString(); 
             
            //写出流 
            out.write( ("--"+boundary+boundaryMessage).getBytes("utf-8") ); 
			
			
			
			byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();// 定义最后数据分隔线
	
			String fname ="E:\\图片\\QQ图片20130818215237.jpg";
			fname ="D:\\baiduyundownload\\QQ截图20131222180036.gif";
			File file = new File(fname);
			StringBuilder sb = new StringBuilder();
			sb.append("--");
			sb.append(BOUNDARY);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data;name=\"uploadfile\";filename=\"" + file.getName() + "\"\r\n");
			sb.append("Content-Type:application/octet-stream\r\n\r\n");
			byte[] data = sb.toString().getBytes();
			out.write(data);
			
			DataInputStream in = new DataInputStream(new FileInputStream(
					file));
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			out.write("\r\n".getBytes()); // 多个文件时，二个文件之间加入这个
			in.close();
			out.write(end_data);
			out.flush();
			out.close();
			// 定义BufferedReader输入流来读取URL的响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (Exception e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
		}
	}


	public void getPackage() {
		String url = "http://localhost:80/admin/package/getPackage.action?user_id=04FADB81-4BC0-4D1F-BDE8-51578309F725&dtype=1";
		Map map = new HashMap();
		// map.put("fileid", "b1429f00-a573-479b-b477-8524411e71ef");
		// String temp =
		// HttpRequestProxy.doPost("http://192.168.0.99/AgentPortal/autoHandler",
		// map, "GBK");
		String temp = HttpRequestProxy.doGet(url, map, "UTF-8");
		System.out.println("返回的消息是:" + temp);
	}

	public void dynamicPic() {
		String url = "http://localhost:80/admin/file/dynamicPic.action";
		Map map = new HashMap();
		map.put("fileid", "89e41d4c-cf21-4835-ae59-d3fed53589eb");
		map.put("width", "510");
		map.put("height", "380");
		// String temp =
		// HttpRequestProxy.doPost("http://192.168.0.99/AgentPortal/autoHandler",
		// map, "GBK");
		String temp = HttpRequestProxy.doPost(url, map, "UTF-8");
		System.out.println("返回的消息是:" + temp);
	}

	public void getFile() {
		String url = "http://localhost:80/admin/file/getFile.action";
		Map map = new HashMap();
		map.put("user_id", "04FADB81-4BC0-4D1F-BDE8-51578309F725");
		map.put("packagename", "");
		map.put("keyword", "");
		map.put("rtype", "");
		// String temp =
		// HttpRequestProxy.doPost("http://192.168.0.99/AgentPortal/autoHandler",
		// map, "GBK");
		String temp = HttpRequestProxy.doPost(url, map, "UTF-8");
		System.out.println("返回的消息是:" + temp);
	}

	public void deleteFile() {
		String url = "http://localhost:80/admin/file/deleteFile.action";
		Map map = new HashMap();
		map.put("user_id", "0");
		map.put("fileids", "4ee77f6a-060d-4f2d-9dc7-744c6c73a8ce");
		// String temp =
		// HttpRequestProxy.doPost("http://192.168.0.99/AgentPortal/autoHandler",
		// map, "GBK");
		String temp = HttpRequestProxy.doPost(url, map, "UTF-8");
		System.out.println("返回的消息是:" + temp);
	}

	public void getFileByURL() {
		String url = "http://localhost:80/admin/file/getFileByURL.action";
		Map map = new HashMap();
		map.put("fileID", "03d09d0c-08fd-46e3-84a6-1dd032f2d614");
		// String temp =
		// HttpRequestProxy.doPost("http://192.168.0.99/AgentPortal/autoHandler",
		// map, "GBK");
		String temp = HttpRequestProxy.doPost(url, map, "UTF-8");
		System.out.println("返回的消息是:" + temp);
	}

	public void truncateResource() {
		String url = "http://localhost:80/admin/file/truncateResource.action";
		Map map = new HashMap();
		map.put("userid", "94A03B33-F1AA-44DB-BC3C-E99423230A0E");
		map.put("isAdmin", "Y");
		// String temp =
		// HttpRequestProxy.doPost("http://192.168.0.99/AgentPortal/autoHandler",
		// map, "GBK");
		String temp = HttpRequestProxy.doPost(url, map, "UTF-8");
		System.out.println("返回的消息是:" + temp);
	}

}
