package com.org.mokey.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.log4j.Logger;

import com.org.mokey.system.entiy.common.MenuTree;

/**
 * JSON本身已经有很多方法,例如
 *<br>  jsonStr ="{intCount:30,strDesc:"DESC",objUser:{...},arrUsers:[{},{}]}"
 *<br>	JSONObject jsonObject =JSONObject.fromObject(jsonStr);
 *<br>	jsonObject.getInt("intCount");
 *<br>	jsonObject.getString("strDesc");
 *<br>	jsonObject.getJSONObject("objUser");
 *<br>	jsonObject.getJSONArray("arrUsers");
 *
 * @author adam.sun 
 */
public class JSONUtil {
	
	  private static final Logger log = Logger.getLogger(JSONUtil.class);
	/**
	 * 把JSONArray形式的数组转为javabean对象数组
	 * @param reqString 输入的字符串形式[{},{},{}]
	 * @param clazz javabeean的类名
	 * @return Object 需要转变为自己的类型
	 */
	public static Object JSONString2ObjectArray(String reqString,Class<?> clazz) {
		//String 到 JSONObject
		JSONArray ja =JSONArray.fromObject(reqString);
		//JSONObject 到 Bean
		return JSONArray.toArray(ja, clazz);
	}

	/**
	 * 把JSONObject形式的数组转为javabean对象
	 * @param reqString 输入的字符串形式{}
	 * @param clazz javabeean的类名
	 * @return Object 需要转变为自己的类型
	 */
	public static Object JSONString2Bean(String reqString,Class<?> clazz) {
		//String 到 JSONObject
		JSONObject ja =JSONObject.fromObject(reqString);
		//JSONObject 到 Bean
		return JSONObject.toBean(ja, clazz);
	}
	
	 /**
	  *从requeets里取得变量参数
	 * @param reader requet.getReader();
	 * @throws IOException 
	 */
	public static JSONObject getRequestJSONObject(BufferedReader reader) throws IOException {
		StringBuffer jb = new StringBuffer();
		String line = null;
		while ((line = reader.readLine()) != null) {
			jb.append(line);
		}
		return  JSONObject.fromObject(jb.toString());
	}
	
	/**
	 * 把对象中的部分属性剔出掉，比如关联的一个对象 eg:
	 *  <br>Object yourObject=.......;
	 *  <br>String[] kickProperties = {"userName","userCode"};     //不需要转换的字段   
        <br>JsonConfig config = JSONUtil.kickProperty(kickProperties, TSysUser.class);
		<br>String string = JSONObject.fromObject(yourObject,config).toString();
		<br>OR
		<br>List string = JSONArray.fromObject(yourObject,config).toString();
		
	 * @param kickProperties 需要剔出的属性列表
	 * @param Class clazz javaBean 的类名
	 * @return JsonConfig
	 */
	public static JsonConfig kickProperty(final String[] kickProperties,Class<?> clazz) {
		if(kickProperties== null){
			return null;
		}
		JsonConfig jsonConfig = new JsonConfig();		
		jsonConfig.setRootClass( clazz );
		jsonConfig.setJsonPropertyFilter(new PropertyFilter(){
		public boolean apply( Object source, String name, Object value )
		{
			for (int i = 0; i < kickProperties.length; i++) {
				if( kickProperties[i].equalsIgnoreCase( name )  ){
					//log.debug(" Your kill property is"+name);
			        return true;
			      }			      
			}
		  return false;
		   }
		});
		return jsonConfig;
	}
	
	/**
	 * 把对象中的部分属性剔出掉，比如关联的一个对象(仅仅去掉指定类的属性) eg:
	 *  <br>Object yourObject=.......;
	 *  <br>String[] kickProperties = {"userName","userCode"};     //不需要转换的字段   
        <br>JsonConfig config = JSONUtil.kickProperty(kickProperties, TSysUser.class.getName());
		<br>String string = JSONObject.fromObject(yourObject,config).toString();
		<br>OR
		<br>List string = JSONArray.fromObject(yourObject,config).toString();
		
	 * @param kickProperties 需要剔出的属性列表
	 * @param Class clazz javaBean 的类名
	 * @return JsonConfig
	 */
	public static JsonConfig kickProperty(final String[] kickProperties,final String className) {
		if(kickProperties== null){
			return null;
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setJsonPropertyFilter(new PropertyFilter(){
		public boolean apply( Object source, String name, Object value )
		{
			if(!source.getClass().getName().equals(className)){
				return false; //不是本类的全部放行
			}
			for (int i = 0; i < kickProperties.length; i++) {
				
				if( kickProperties[i].equalsIgnoreCase( name )  ){
					//log.debug(" Your kill property is"+name);
			        return true;
			      }			      
			}
			  return false;
		   }
		});
		return jsonConfig;
	}

	
	/**
	 * 仅仅保留对象中的给定字段，比如关联的一个对象(仅仅去掉指定类的属性) eg:
	 *  <br>Object yourObject=.......;
	 *  <br>String[] kickProperties = {"userName","userCode"};     //不需要转换的字段   
        <br>JsonConfig config = JSONUtil.holdProperty(kickProperties, TSysUser.class.getName());
		<br>String string = JSONObject.fromObject(yourObject,config).toString();
		<br>OR
		<br>List string = JSONArray.fromObject(yourObject,config).toString();
		
	 * @param holdProperties 需要剔出的属性列表
	 * @param className  javaBean 的类名
	 * @return JsonConfig
	 */
	public static JsonConfig holdProperty(final String[] holdProperties,final String className) {
		if(holdProperties== null){
			return null;
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setJsonPropertyFilter(new PropertyFilter(){
		public boolean apply( Object source, String name, Object value )
		{			
			//log.debug(source.getClass().getName()+" ==== "+className+"==="+name);
			if(!source.getClass().getName().equals(className)){
				return false; //不是本类的全部放行
			}
			for (int i = 0; i < holdProperties.length; i++) {
				//log.debug( holdProperties[i]+" equals "+name+"");
				if( holdProperties[i].equalsIgnoreCase( name )  ){
					//log.debug(" Your hold property is "+name);
			        return false;
			      }			      
			}
			return true;
		   }
		});
		return jsonConfig;
	}
	
	/**
	 * 仅仅保留对象中的给定字段，比如关联的一个对象(仅仅去掉指定类的属性) eg:
	 *  <br>Object yourObject=.......;
	 *  <br>String[] kickProperties = {"userName","userCode"};     //不需要转换的字段   
        <br>JsonConfig config = JSONUtil.holdProperty(kickProperties, TSysUser.class.getName());
		<br>String string = JSONObject.fromObject(yourObject,config).toString();
		<br>OR
		<br>List string = JSONArray.fromObject(yourObject,config).toString();
		
	 * @param holdProperties 需要剔出的属性列表
	 * @param className  javaBean 的类名
	 * @return JsonConfig
	 */
	public static JsonConfig holdProperty(final String[] holdProperties) {
		if(holdProperties== null){
			return null;
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setJsonPropertyFilter(new PropertyFilter(){
		public boolean apply( Object source, String name, Object value )
		{			
			//log.debug("===================================="+name);
				for (int i = 0; i < holdProperties.length; i++) {
					
					//log.debug( holdProperties[i]+" equals "+name+"");
					if( holdProperties[i].equalsIgnoreCase( name )  ){
						//log.debug(" Your hold property is "+name);
				        return false;
				      }			      
				}
			return true;
		   }
		});
		return jsonConfig;
	}
	

	/* @param kickProperties 需要剔出的属性列表
	 * @param Class clazz javaBean 的类名
	 * @return JsonConfig
	 */
	public static JsonConfig kickPropertyByMode(final String[] kickProperties,Class<?> clazz) {
		
		if(kickProperties== null){
			return null;
		}
		
		JsonConfig jsonConfig = new JsonConfig();		
		jsonConfig.setRootClass( clazz );
		jsonConfig.setJsonPropertyFilter(new PropertyFilter(){
		public boolean apply( Object source, String name, Object value )
		{
			//System.out.println("converse"+source.getClass().getName());
			/*MenuTree menuTree ;
			if(source instanceof MenuTree){
				menuTree= (MenuTree) source;
			}*/
			for (int i = 0; i < kickProperties.length; i++) {
			if(!"checked".equals(kickProperties[i]))
				if( kickProperties[i].equalsIgnoreCase( name )){
					//log.debug(" Your kill property is"+name+ "& menuMode is ");
			        return true;
			      }			      
			}
			  
			  return false;
		   }
		});
		return jsonConfig;
	}
	
	
	/* @param kickProperties 需要剔出的属性列表
	 * @param Class clazz javaBean 的类名
	 * @return JsonConfig
	 */
	public static JsonConfig kickPropertyByMode(final String[] kickProperties,final String className ) {
		
		if(kickProperties== null){
			return null;
		}
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setJsonPropertyFilter(new PropertyFilter(){
		public boolean apply( Object source, String name, Object value )
		{			
				// filter the checked
			if (source.getClass().getName().equals(className)) {
				MenuTree menuTree = (MenuTree) source;
				if (!menuTree.isNeedCheck()&& "checked".equals(name)) {
					return true;
				}
			}
			
			for (int i = 0; i < kickProperties.length; i++) {
		
				if( kickProperties[i].equalsIgnoreCase( name ) && !"checked".equals(kickProperties[i])){
					log.debug(" Your kill property is"+name+ "& menuMode is ");
			        return true;
			      }	
			}
			  return false;
		   }
		});

		return jsonConfig;
		
	}
	
    /***
     * json字符串转java List
     * @param rsContent
     * @return
     * @throws Exception
     */
	public static List<Map<String, String>> jsonStringToList(String rsContent) 
    {
        JSONArray arry = JSONArray.fromObject(rsContent);
        //System.out.println("json字符串内容如下");
        //System.out.println(arry);
        List<Map<String, String>> rsList = new ArrayList<Map<String, String>>();
        for (int i = 0; i < arry.size(); i++)
        {
            JSONObject jsonObject = arry.getJSONObject(i);
            Map<String, String> map = new HashMap<String, String>();
            for (Iterator<?> iter = jsonObject.keys(); iter.hasNext();)
            {
                String key = (String) iter.next();
                String value = jsonObject.get(key).toString();
                map.put(key, value);
            }
            rsList.add(map);
        }
        return rsList;
    }

}
