package com.org.mokey.common;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 项目各个层都使用的方法
 * @author adam.sun
 *
 */
public class SysSpringContextProvider {
	
	private static final Logger log = Logger.getLogger(SysSpringContextProvider.class);
	/**
	 * 保存都有Spring 上下文
	 */
	public static WebApplicationContext SpringApplicationContext;
	
	
	/**
	 * 取得spring中定义的bean
	 * 你可以在任何地方调用这个代码
	 * 你必须在项目中配置了spring
	 * @see  com.grandsys.commons.SysConstant
	 * @see  com.grandsys.commons.GetClass
	 * @param springBeanName
	 * @return
	 */
	public static Object getSpringBean(String springBeanName) {
		//log.debug(" into getSpringBean!,beanName:"+springBeanName);
		// check empty;
		if (StringUtils.isEmpty(springBeanName)) {
			log.debug(" the input springBeanName is empty! ");
			return null;
		}
		WebApplicationContext wac = SpringApplicationContext;
		if (wac != null) {
			//log.debug(" return by  SysConstant.SpringApplicationContext ! ");
			return wac.getBean(springBeanName);
		} else {
			//log.debug(" the  SysConstant.SpringApplicationContext is empty! ");
			//log.debug(" return by GetClass.getClass() ! ");
			return GetClass.getClass(springBeanName);
		}
	}
	
	/**
	 * 设置系统常量中的spring的上下文
	 */
	public static void setSpringApplicationContext(WebApplicationContext context){
		if(SpringApplicationContext==null && context!= null){
			SpringApplicationContext = context;			
		}
	}
	
	/**
	 * 取得系统常量中的spring的上下文
	 */
	public static WebApplicationContext getSpringApplicationContext(){
		return SpringApplicationContext;		
	}

	/** 
	 * 根据Listener中的 ServletContext 设置spring context
	 * @param servletContext
	 */
	public static void setSpringApplicationContext(ServletContext servletContext) {		
		if(SpringApplicationContext ==null){
			WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);	
			SpringApplicationContext =wac;
		}
	}
	
}
