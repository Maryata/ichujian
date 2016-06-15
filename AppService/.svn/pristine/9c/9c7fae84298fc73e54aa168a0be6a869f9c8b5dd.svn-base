package com.sys.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.junit.runner.Request;

import net.sf.json.JSONObject;

import com.sys.commons.AbstractAction;
import com.sys.service.AppService;
import com.sys.service.IndustryService;
import com.sys.service.RecommendService;
import com.sys.util.JSONUtil;
import com.sys.util.StrUtils;

public class RecommendAction extends AbstractAction {
	
	private RecommendService recommendService;

	/**输出内容*/
	private String out;
	  
    public String RecommendMain() {  //首页轮播广告
    	Map<String,Object> retMap = new HashMap<String,Object>();
    	String cityid=getParameter("cityid");
    	log.info("--cityid--"+cityid);
    	log.info("into RecommendMain");   	
    	try{
    		if(StrUtils.isEmpty(cityid)){
				retMap.put("status", "N");
				retMap.put("info", "1001");
			}else{
			List adlist=new ArrayList(); 
//			int industrycount=recommendService.getMainIndustry(cityid);
			int advertcount=recommendService.getMainAdvert(cityid);
			String citys=cityid;
//			if(industrycount<1){
//				cityid="0";
//			}
//    		List industry=recommendService.getRecommendMainIndustry(cityid);
    		
    		if(advertcount<1){
    			cityid="0";
    		}else {
    			cityid=citys;
			}
    		List advert=recommendService.getRecommendMainAdvert(cityid);
    		if(advert.size()>0){
        			Map map=(Map) advert.get(0);
        			String picurl=map.get("C_PICURL").toString();
        			String activityid=map.get("C_ACTIVITYID").toString();
        			String[] pic=picurl.split(",");
        			String[] act=activityid.split(",");
        			
        			for (int i = 0; i < act.length; i++) {
        				Map idMap =new HashMap();
						idMap.put("id", act[i]);
						idMap.put("pic", pic[i]);
						adlist.add(idMap);
					}
    		}else {
    			adlist=advert;
			}
    		
    		retMap.put("status", "Y");
//			retMap.put("industry", industry);
			retMap.put("advert", adlist);
			}
    	} catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("RecommendMain failed",e);
	    }
    	out = JSONObject.fromObject(retMap).toString();
    	return "success";
	}
    
    
    public String RecommendMainMin() {  //首页小广告
    	Map<String,Object> retMap = new HashMap<String,Object>();
    	String cityid=getParameter("cityid");
    	log.info("--cityid--"+cityid);
    	log.info("into RecommendMainMin");   	
    	try{
    		if(StrUtils.isEmpty(cityid)){
				retMap.put("status", "N");
				retMap.put("info", "1001");
			}else{
			List adlist=new ArrayList(); 
			int advertcount=recommendService.getMainAdvertMin(cityid);
			String citys=cityid;
    		if(advertcount<1){
    			cityid="0";
    		}else {
    			cityid=citys;
			}
    		List advert=recommendService.getRecommendMainAdvertMin(cityid);
    		if(advert.size()>0){
        			Map map=(Map) advert.get(0);
        			String picurl=map.get("C_PICURL").toString();
        			String activityid=map.get("C_ACTIVITYID").toString();
        			String[] pic=picurl.split(",");
        			String[] act=activityid.split(",");
        			for (int i = 0; i < act.length; i++) {
        				Map idMap =new HashMap();
						idMap.put("id", act[i]);
						idMap.put("pic", pic[i]);
						adlist.add(idMap);
					}
    		}else {
    			adlist=advert;
			}
    		retMap.put("status", "Y");
			retMap.put("advert", adlist);
			}
    	} catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("RecommendMainMin failed",e);
	    }
    	out = JSONObject.fromObject(retMap).toString();
    	return "success";
	}
    
    
    public String RecommendMainPage() {  //首页推荐精选活动
    	Map<String,Object> retMap = new HashMap<String,Object>();
    	String cityid=getParameter("cityid");
    	String pageindex=getParameter("pageindex");  //分页
    	log.info("--cityid--"+cityid+"--pageindex--"+pageindex);
    	log.info("into RecommendMainPage");   	
    	try{
    		if(StrUtils.isEmpty(cityid)||StrUtils.isEmpty(pageindex)){
				retMap.put("status", "N");
				retMap.put("info", "1001");
			}else{
				int recommendcount=recommendService.getRecommend(cityid, "2");   //2代表推荐的精选活动  先前版本有 热门 推荐等区分
				if(recommendcount<1){
					cityid="0";
				}
	    		List<Map> titleList=recommendService.getRecommendMainPageTitle(cityid,pageindex);   //首页小标题
	    		
	    		List<Map> recommendsList = new ArrayList<Map>();
	    		for(Map item : titleList){
	    			Map m=null;
	    			String typeid = item.get("C_TYPEID").toString();
	    			List list = recommendService.getRecommendMainPage(typeid);
	    			boolean isHas = false;
	    			
	    			for(Map recommend : recommendsList){
	    				if(item.get("C_TYPEID").equals(recommend.get("c_id"))){
	    					m = recommend;
	    					List oldList = (List) recommend.get("list");
	    					oldList.addAll(list);
	    					m.put("list", oldList);
	    					isHas = true;
	    					break;
	    				}
	    			}
	    			if(isHas ){
	    				continue;
	    			}
	    			m=new HashMap();
	    			m.put("c_name", item.get("C_NAME"));
	    			m.put("c_id", item.get("C_ID"));
	    			m.put("list", list);
	    			
	    			recommendsList.add(m);
	    		}
	    		
				retMap.put("recommend", recommendsList);
				retMap.put("status", "Y");
			}
    	} catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("RecommendMainPage failed",e);
	    }
    	out = JSONObject.fromObject(retMap).toString();
    	return "success";
	}
    
   //--------------------------------分割线----------------------------------------------  
    public String RecommendFollow(){    //注册后推荐关注
    	Map<String,Object> retMap = new HashMap<String,Object>();
    	String cityname=getParameter("cityname");
    	log.info("--cityname--"+cityname);
    	log.info("into RecommendFollow");   	
    	try{
    		if(StrUtils.isEmpty(cityname)){
				retMap.put("status", "N");
				retMap.put("info", "1001");
			}else{
				List list=new ArrayList();
				int i=recommendService.getRecommendCity(cityname);
				if(i==1){
					list=recommendService.getRecommendFollow(cityname);
				}else {
				    list=recommendService.getRecommendFollowNull();
				}
    		
    		retMap.put("status", "Y");
			retMap.put("list", list);
			}
    	} catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("RecommendFollow failed",e);
	    }
    	out = JSONObject.fromObject(retMap).toString();
    	return "success";
    }
    
    
    public String RecommendMainHot() {  //首页热门
    	Map<String,Object> retMap = new HashMap<String,Object>();
    	String cityid=getParameter("cityid");
    	String pageindex=getParameter("pageindex");  //分页
    	log.info("--cityid--"+cityid+"--pageindex--"+pageindex);
    	log.info("into RecommendMainHot");   	
    	try{
    		if(StrUtils.isEmpty(cityid)||StrUtils.isEmpty(pageindex)){
				retMap.put("status", "N");
				retMap.put("info", "1001");
			}else{
				int recommend=recommendService.getRecommend(cityid, "1");
				if(recommend<1){
					cityid="0";
				}
    		List hot=recommendService.getRecommendMainHot(cityid,pageindex);
    		retMap.put("status", "Y");
			retMap.put("hot", hot);
			}
    	} catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("RecommendMainHot failed",e);
	    }
    	out = JSONObject.fromObject(retMap).toString();
    	return "success";
	}
    

    
    public String GetCityList() {  //城市列表
    	Map<String,Object> retMap = new HashMap<String,Object>();
    	log.info("into GetCityList");   	
    	try{
    		List hotcity=recommendService.getHotCityList();
    		List city=recommendService.getCityList();
    		retMap.put("status", "Y");
			retMap.put("city", city);
			retMap.put("hotcity", hotcity);
    	} catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("GetCityList failed",e);
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


	public RecommendService getRecommendService() {
		return recommendService;
	}


	public void setRecommendService(RecommendService recommendService) {
		this.recommendService = recommendService;
	}



}
