package com.org.mokey.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.org.mokey.util.StreamUtil;

/**
 * ��ȡSpring�����µ�ʵ����
 * @author ����.L
 *
 */
public class GetClass {
	public static Object getClass(String className){
		//String[] contexts = new String[1];
		//contexts[0] = "/WebRoot/WEB-INF/classes/applicationContext.xml";
/*		contexts[1] = "/WebRoot/WEB-INF/applicationContext-lxb.xml";
		contexts[2] = "/WebRoot/WEB-INF/applicationContext-hb.xml";
		contexts[3] = "/WebRoot/WEB-INF/applicationContext-wq.xml";
		contexts[4] = "/WebRoot/WEB-INF/applicationContext-zls.xml";*/
		//ApplicationContext context = new FileSystemXmlApplicationContext(contexts);
		
		String[] contexts = new String[2];
		// "file:"+StreamUtil.getRootPath() +
		contexts[0] ="/WEB-INF/conf/spring/applicationContext.xml";
		ApplicationContext factory=new ClassPathXmlApplicationContext(contexts);
		return factory.getBean(className);
	}
	
	public static void main(String[] args) {
		System.out.println("--");
		System.out.println(" result ="+GetClass.getClass("readModuleCfgDAO"));
		System.out.println("===");
	}
}
