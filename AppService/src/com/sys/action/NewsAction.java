package com.sys.action;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sys.commons.AbstractAction;
import com.sys.service.NewsService;
import com.sys.util.StrUtils;

public class NewsAction extends AbstractAction {
	
	private NewsService newsService;
	
	/**输出内容*/
	private String out;
	
    public String GoodNews(){  //精品推荐
    	Map<String,Object> retMap = new HashMap<String,Object>();
    	String type=getParameter("type");          //精品or发现
    	String category=getParameter("category");   //新闻or阅读
    	log.info("into GoodNews");
    	log.info("--type--"+type+"--category--"+category);
    	try{
        	if(StrUtils.isEmpty(type)||StrUtils.isEmpty(category)){
    			retMap.put("status", "N");
    			retMap.put("info", "1001");
    	    	out = JSONObject.fromObject(retMap).toString();
    	    	return "success";
        	}
		    List list=new ArrayList();                  
			list=newsService.GoodNews(type, category);
			    		
    		retMap.put("status", "Y");
			retMap.put("goodnews", list);
    	} catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("NewsAction.GoodNews failed,",e);
	    }
    	out = JSONObject.fromObject(retMap).toString();
    	log.info("return data");
    	return "success";
    }
	
	
    public String Installed(){  //安装的新闻or阅读          有则更新 无则新增
    	Map<String,Object> retMap = new HashMap<String,Object>();
    	String imei=getParameter("imei");         
    	String type=getParameter("type");  //新闻 or 阅读
    	String name_package=getParameter("name_package");  //名称和包名
    	log.info("into Installed");
    	try{
		    //String name_packages=new String(name_package.getBytes("ISO-8859-1"),"UTF-8");
    	    log.info("--type--"+type+"--imei--"+imei+"--name_package--"+name_package);
        	if(StrUtils.isEmpty(imei)||StrUtils.isEmpty(type)||StrUtils.isEmpty(name_package)){
    			retMap.put("status", "N");
    			retMap.put("info", "1001");
    	    	out = JSONObject.fromObject(retMap).toString();
    	    	return "success";
        	}
    		Date actiondate1=new Date();
		    JSONArray array=JSONArray.fromObject(name_package);
		    for(int i=0;i<array.size();i++){                  //循环插入最新
		    	JSONObject json=(JSONObject) array.get(i);
		    	String appname=json.getString("appName");
		    	String apppackage=json.getString("appPackage");
		    	//查询安装是否有记录
		    	List list=newsService.findList(imei, type, apppackage, appname);
		    	if(list.size()>0){
	                Map map=(Map)list.get(0);
	                String id=map.get("C_ID").toString();
		    		newsService.updateList(imei, type, apppackage, appname, actiondate1,id); //更新
		    	}else{
		    		newsService.Installed(imei,type,apppackage,appname,actiondate1);	
		    	}
		    }
			retMap.put("status", "Y");	
     	} catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("NewsAction.Installed failed,",e);
	    }
    	out = JSONObject.fromObject(retMap).toString();
    	return "success";
    }
    
    
    public String NewsDetails(){    //详情页面---暂时无用
    	Map<String,Object> retMap = new HashMap<String,Object>();
    	String id=getParameter("id");
    	log.info("into NewsDetails");
    	log.info("--id--"+id);
    	try{
    	if(StrUtils.isEmpty(id)){
			retMap.put("status", "N");
			retMap.put("info", "1001");
    	}else{
    		List list=newsService.NewsDetails(id);
    		retMap.put("status", "Y");
    		retMap.put("details", list);
    	}
	    } catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("NewsAction.NewsDetails failed,",e);
	    }   	
    	out = JSONObject.fromObject(retMap).toString();
    	return "success";
    }
    
    public String DownApp(){       //下载新闻 or 阅读 记录
    	Map<String,Object> retMap = new HashMap<String,Object>();
    	String imei=getParameter("imei");
    	String type=getParameter("type");
    	String key=getParameter("key");
    	String name_package=getParameter("name_package");
    	log.info("into DownApp");
    	try{
			//String name_packages=new String(name_package.getBytes("ISO-8859-1"),"UTF-8");
	    	log.info("--imei--"+imei+"--type--"+type+"--key--"+key+"--name_package--"+name_package);
	    	if(StrUtils.isEmpty(imei)||StrUtils.isEmpty(type)||StrUtils.isEmpty(key)||StrUtils.isEmpty(name_package)){
				retMap.put("status", "N");
				retMap.put("info", "1001");
		    	out = JSONObject.fromObject(retMap).toString();
		    	return "success";
	    	}
			Date actiondate1=new Date();
		    JSONArray array=JSONArray.fromObject(name_package);
		    for(int i=0;i<array.size();i++){                  //循环插入最新
		    	JSONObject json=(JSONObject) array.get(i);
		    	String appname=json.getString("appName");
		    	String apppackage=json.getString("appPackage");
		    	newsService.DownApp(imei, type, key, apppackage, appname, actiondate1);
		    }
			retMap.put("status", "Y");	
	    } catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("NewsAction.DownApp failed,",e);
		}   
    	out = JSONObject.fromObject(retMap).toString();
    	return "success";
    }
    
    
    
    public String UnloadApp(){       //卸载新闻 or 阅读 记录    卸载后删除安装表的记录
    	Map<String,Object> retMap = new HashMap<String,Object>();
    	String imei=getParameter("imei");
    	String type=getParameter("type");
    	String key=getParameter("key");
    	String name_package=getParameter("name_package");
    	log.info("into UnloadApp");
    	try{
    		//String name_packages=new String(name_package.getBytes("ISO-8859-1"),"UTF-8");
        	log.info("--imei--"+imei+"--type--"+type+"--key--"+key+"--name_package--"+name_package);
    	if(StrUtils.isEmpty(imei)||StrUtils.isEmpty(type)||StrUtils.isEmpty(key)||StrUtils.isEmpty(name_package)){
			retMap.put("status", "N");
			retMap.put("info", "1001");
	    	out = JSONObject.fromObject(retMap).toString();
	    	return "success";
    	}
		Date actiondate1=new Date();
	    JSONArray array=JSONArray.fromObject(name_package);
	    for(int i=0;i<array.size();i++){                  //循环插入最新
	    	JSONObject json=(JSONObject) array.get(i);
	    	String appname=json.getString("appName");
	    	String apppackage=json.getString("appPackage");
	    	//查询安装表是否有记录
	    	List list=newsService.findList(imei, type, apppackage, appname);
	    	if(list.size()>0){
                Map map=(Map)list.get(0);
                String id=map.get("C_ID").toString();
	    		newsService.deleteAppInfo(id); //删除安装表的记录
	    	}
	    	newsService.UnloadApp(imei, type, key, apppackage, appname, actiondate1);
	    }
		retMap.put("status", "Y");	
	    } catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("NewsAction.UnloadApp failed,",e);
		}   
    	out = JSONObject.fromObject(retMap).toString();
    	return "success";
    }
    
    public String Advert(){    //广告
    	Map<String,Object> retMap = new HashMap<String,Object>();
    	String type=getParameter("type");  //新闻 or 阅读
    	log.info("---type---"+type);
    	log.info("into Advert");
    	try{
        	if(StrUtils.isEmpty(type)){
    			retMap.put("status", "N");
    			retMap.put("info", "1001");
    	    	out = JSONObject.fromObject(retMap).toString();
    	    	return "success";
        	}
    		List list=newsService.Advert(type);  		
    		retMap.put("status", "Y");
			retMap.put("Advert", list);
    	} catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("NewsAction.Advert failed,",e);
	    }
    	out = JSONObject.fromObject(retMap).toString();
    	return "success";
    }
    
    public String MainAdvert(){    //首页广告
    	Map<String,Object> retMap = new HashMap<String,Object>();
    	String type=getParameter("type");  //3  首页广告
    	log.info("---type---"+type);
    	log.info("into MainAdvert");
    	try{
        	if(StrUtils.isEmpty(type)){
    			retMap.put("status", "N");
    			retMap.put("info", "1001");
    	    	out = JSONObject.fromObject(retMap).toString();
    	    	return "success";
        	}
    		List list=newsService.MainAdvert(type);  		
    		retMap.put("status", "Y");
			retMap.put("MainAdvert", list);
    	} catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("NewsAction.MainAdvert failed,",e);
	    }
    	out = JSONObject.fromObject(retMap).toString();
    	return "success";
    }
    

    
	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}

	public NewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

}
