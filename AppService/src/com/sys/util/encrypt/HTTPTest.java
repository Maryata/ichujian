package com.sys.util.encrypt;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.auth.CredentialsProvider;
import org.apache.commons.httpclient.methods.PostMethod;

public class HTTPTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
      HttpClient httpClient=new HttpClient();
      //httpClient.getParams().setAuthenticationPreemptive(value)
      //PostMethod post=new PostMethod("http://192.168.8.221/AppService/app/update.action?version=1");
      PostMethod post=new PostMethod("http://192.168.8.221/AppService2/app/update.action?version=MQ==&sign=50CF53DCD7D8385ABE56213BA75BE869");
     // Map map=new HashMap();
     // map.put(key, value);
      //post.setRequestHeader("Authorization", "ichujian");
      //post.setRequestHeader("password", "Ichujian@321");    
      
      try {
		int status=httpClient.executeMethod(post);
		System.out.println(status);
	} catch (HttpException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

}
