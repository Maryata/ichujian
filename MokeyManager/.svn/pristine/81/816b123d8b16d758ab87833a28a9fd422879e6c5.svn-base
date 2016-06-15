package com.org.mokey.common;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.ValueStack;
import com.org.mokey.system.entiy.TSysUser;

/**
 * 定义每个action的抽象类，里面定义了模组的功能类型和常用方法
 * 
 *  add Request and response methods here.
 * @author adam.sun
 *
 */
public class AbstractAction extends ActionSupport {
	/** Logger available to subclasses */
	protected  Logger log = (Logger.getLogger(getClass()));
	
	protected String SUCCESS = "input";
	protected String CHART_DATA = "chartData";
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 6655372714902221271L;
	/**session存放当前登录系统的用户bean*/
	public static final String AP_SYS_SESSION_LOGON_USER = "AP_SYS_SESSION_LOGON_USER";

	/**用户登陆后权限主菜单 */
	public static final String AP_SYS_SESSION_MENU_ROOT = "AP_SYS_SESSION_MENU_ROOT";
	/**用户权限左侧二级菜单 */
	public static final String AP_SYS_SESSION_MENU_MODEL = "AP_SYS_SESSION_MENU_MODEL";
	/**用户登陆后权限菜单 */
	public static final String AP_SYS_SESSION_MENU = "AP_SYS_SESSION_MENU";
	
	/**
	 * session can be get by  request.getSession()
	 * @return
	 */
	protected static HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}
	protected static ServletContext getServletContext() {
		return ServletActionContext.getServletContext();
		
	}
	protected static HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
	
	protected String getParameter(String name){
		String v = getRequest().getParameter(name);
		if(v!=null){
			v = v.trim();
		}
		return v;
	}
	
	protected String getParameter2(String name){
		String v = getRequest().getParameter(name);
		if(v!=null){
			v = v.trim();
		}else{
			v ="";
		}
		return v;
	}
	
	protected int getParameter2Int(String name,int defa){
		String v = getRequest().getParameter(name);
		if(v!=null){
			defa = Integer.parseInt(v);
		}
		return defa;
	}
	
	
	protected static void printToResponse(String xmlString) throws IOException{
		HttpServletResponse response ;
        //将生成的XML字符串发往客户端     
		response=getResponse();
        response.setContentType("text/xml; charset=UTF-8");      
        response.setHeader("Cache-Control", "no-cache");
		response.getWriter().print(xmlString);	
		response.getWriter().flush();      
	}

	/**
	 * @param str 
	 * @throws IOException
	 */
	public static void writeToResponse(String str) throws IOException {
		HttpServletResponse response=getResponse();
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(str);
		response.getWriter().flush();
	}
	
	public void writeJson(HttpServletResponse response, String... msg)
			throws IOException {
		write(response, "application/json", msg);
	}

	public void writeXml(HttpServletResponse response, String... msg)
		throws IOException {
		write(response, "text/xml", msg);
	}

	public void write(HttpServletResponse response, String type, String... msg)
		throws IOException {
		response.setContentType(type + ";charset=UTF-8");
	// response.setCharacterEncoding("GBK");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setHeader("Pragma", "no-cache");

		for (String m : msg) {
			response.getWriter().write(m);
		}
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 设置登录系统的用户
	 * @param loginUser
	 */
	public static void setSessionLoginUser(TSysUser loginUser) {		
		getRequest().getSession().setAttribute(AP_SYS_SESSION_LOGON_USER, loginUser);		
	}

	/**
	 * 清空session
	 * @param loginUser
	 */
	public static void invalidateLoginUser() {		
		getRequest().getSession().invalidate();		
	}
	
	/**
	 * 取得当前系统用户
	 * @param loginUser
	 */
	public static TSysUser getSessionLoginUser() {
		return (TSysUser) getRequest().getSession().getAttribute(AP_SYS_SESSION_LOGON_USER);
	}
	/**
	 * 设置session
	 * @param key
	 * @param val
	 */
	public static void setSessionKey(String key,Object val) {		
		getRequest().getSession().setAttribute(key, val);		
	}
	
	/**
	 * 获取session
	 * @param key
	 * @param val
	 */
	public static Object getSessionKey(String key) {	
		return getRequest().getSession().getAttribute(key);		
	}
	
	/**
	 * @param beanName 在Request中取得Spring配置中的Bean
	 * @param beanIFClass 此bean的接口或实例类
	 * @return Object
	 */
	public static Object getSpringBean(String beanName,@SuppressWarnings("rawtypes") Class beanIFClass){		
		ServletContext servletContext = getRequest().getSession().getServletContext();		
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);			
		return  wac.getBean(beanName,beanIFClass);
				
	}	
	public static String getRealPath(String path){
		return ServletActionContext.getServletContext().getRealPath(path);

	}
	
	public static String getRootPath() {
		if (getRequest() == null) {
			throw new RuntimeException("request is null.");
		}
		StringBuffer url = new StringBuffer();
		url.append(getRequest().getScheme() + "://");
		url.append(getRequest().getServerName());
		if (getRequest().getServerPort() != 80)
			url.append(":" + getRequest().getServerPort());
		url.append(getRequest().getContextPath());
		return url.toString();
	}
	public static String getRootPath(HttpServletRequest request ) {
		if (request == null) {
			throw new RuntimeException("request is null.");
		}
		StringBuffer url = new StringBuffer();
		url.append(request.getScheme() + "://");
		url.append(request.getServerName());
		if (request.getServerPort() != 80)
			url.append(":" + request.getServerPort());
		url.append(request.getContextPath());
		return url.toString();
	}
	
	/**
	 * 输入相对路径如： pages/index.jsp
	 * 返回绝对URL如： http://localhost:8080/applatform/pages/index.jsp
	 * @param relativePath
	 * @return
	 */
	public static String getAbsolutePath(HttpServletRequest request,String relativePath) {
			if (relativePath.startsWith("/"))
				return getRootPath(request) + relativePath;
			else
				return getRootPath(request) + "/" + relativePath;
		}

	
	/**
	   * @author round.he
	   * @description 将bean中的属性和值对应的封装到map中
	   * @param theBean
	   * @return
	   * @throws IllegalArgumentException
	   * @throws IllegalAccessException
	   * @throws InvocationTargetException
	   */
		public Map<String, Object> packagingPutinMap(Serializable theBean)
				throws IllegalArgumentException, IllegalAccessException,
				InvocationTargetException {
			log.debug("into packagingPutinMap");
			// 获取bean 对应的class
			Class theBeanClass = theBean.getClass();
	        //封装属性值的map集合
			Map<String, Object> map = new HashMap<String, Object>();

			// Field [] field=theBeanClass.getDeclaredFields();
	       //得到Bean中的所有方法
			Method[] methods = theBeanClass.getDeclaredMethods();

			for (int i = 0; i < methods.length; i++) {
				Method tmpMethod = methods[i];
				String methodName = tmpMethod.getName();
				String[] strArray = methodName.split("get");
				if (methodName.indexOf("get") == 0) {
					//获取方法返回的值
					Object obj = tmpMethod.invoke(theBean, null);
					//用属性名称作为map的key
					if(strArray[1].equals("EMail")){
						log.debug(strArray[1]+"==========="+obj);
						map.put(strArray[1], obj);
					}
					String fieldName = strArray[1].substring(0, 1).toLowerCase()
							+ strArray[1].substring(1, strArray[1].length());
					if (obj != null) {
						map.put(fieldName, obj);
					} else {
						map.put(fieldName, "");
					}

				}
			}
			return map;
		}
	
	/**
	 * @author round.he
	 * @description <li>把某个对象放到session中
	 *              //map 的封装规则：ep 当前客户对象 key: 是栏位的id ,value: 是栏位所对应的值
	 *              //除了在栏位管理新增的栏位,还有固定的栏位 固定的栏位key预定为Db字段名称
	 * @param type
	 * @param map
	 */
	public void inputSysObjectInSession(String type,Map<String,Object> map){
		@SuppressWarnings("rawtypes")
		Map oldmap=(Map)getRequest().getSession().getAttribute(type);
		if(oldmap==null){
			oldmap=new HashMap();
		}
		oldmap.putAll(map);
		getRequest().getSession().setAttribute(type, oldmap);
	}
	/**
	 * @author round.he
	 * @description <li>获取session中某个对象的某属性值
	 * @param type  
	 *           //对象类型
	 * @param key
	 *          //key 关键字
	 * @return
	 */
	public  Object getSysObjectforSession(String type,String key){
		   if(type==null||key==null){
			   return "";
		   }
		   Map<String,Object> map=(Map<String,Object>)getRequest().getSession().getAttribute(type);  
		   if(map==null){
			   return "";
		   }
		   Object obj=map.get(key);
		    if(obj==null){
		    	return "";
		    }
		   return obj;
	}
	
	/**
	 * 获取值栈
	 * @author Maryn
	 * @since 2015年5月13日14:45:16
	 * @return
	 */
	protected static ValueStack getValueStack(){
		return ActionContext.getContext().getValueStack();
	}
	
	// 回写JSON
	protected void writeJSONToResponse(Object obj) throws IOException{
		String json = JSONArray.fromObject(obj).toString();
		writeToResponse(json);
	}
}
