package com.sys.util.encrypt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;


public class Test1 {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		// 1. 获取并设置url地址，一个字符串变量，一个URL对象 
		String urlStr ="http://192.168.8.221/AppService2/app/update.action";
		URL url= new URL(urlStr);
		// 2. 创建一个密码证书，(UsernamePasswordCredentials类)
		  String username="ichujian";
		  String password="Ichujian@321";
		  UsernamePasswordCredentials upc = new UsernamePasswordCredentials(username, password);

		// 3. 设置认证范围 (AuthScore类)

		String strRealm = "Digest Authentication";
		String strHost = url.getHost();

		int iPort = url.getPort();
		AuthScope as = new AuthScope(strHost, iPort, strRealm);

		// 4. 创建认证提供者(BasicCredentials类) ，基于as和upc

		BasicCredentialsProvider bcp=new BasicCredentialsProvider();
		bcp.setCredentials(as, upc);

		// 5. 创建Http客户端(DefaultHttpClient类)

		DefaultHttpClient client=new DefaultHttpClient();

		// 6. 为此客户端设置认证提供者

		client.setCredentialsProvider(bcp);


		// 7. 创建一个get 方法(HttpGet类)，基于所访问的url地址

		HttpGet hg= new HttpGet(urlStr);


		// 8. 执行get方法并返回 response

		HttpResponse  hr = client.execute(hg);


		// 9. 从response中取回数据，使用InputStreamReader读取Response的Entity：

        String line=null;

        StringBuilder builder = new StringBuilder();


        BufferedReader reader = new BufferedReader(new InputStreamReader(hr.getEntity().getContent() ));

        while((line = reader.readLine()) != null)  builder.append(line);


       String strContent=builder.toString();
       
       System.out.println(strContent);




	}

}
