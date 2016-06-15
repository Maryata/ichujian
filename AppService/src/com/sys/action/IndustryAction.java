package com.sys.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
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
import com.sys.util.StrUtils;

public class IndustryAction extends AbstractAction {
	
	private IndustryService industryService;

	/**输出内容*/
	private String out;
	
    public String Brand(){    //品牌列表
    	Map<String,Object> retMap = new HashMap<String,Object>();
    	log.info("into Brand");   	
    	try{
				List list=industryService.getBrand();
	    		retMap.put("status", "Y");
				retMap.put("brand", list);
    	} catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("Brand failed",e);
	    }
    	out = JSONObject.fromObject(retMap).toString();
    	return "success";
    }
	
	
	
    public String IndustryType(){  
    	Map<String,Object> retMap = new HashMap<String,Object>();
    	String cityid=getParameter("cityid");
    	log.info("--cityid--"+cityid);
    	log.info("into IndustryType");   	
    	try{
    		if(StrUtils.isEmpty(cityid)){
				retMap.put("status", "N");
				retMap.put("info", "1001");
			}else{
				int industrycount=industryService.getIndustry(cityid);
				if(industrycount<1){
					cityid="0";
				}
				List list=industryService.getIndustrType(cityid);
	    		retMap.put("status", "Y");
				retMap.put("list", list);
			}
    	} catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("IndustryType failed",e);
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

	public IndustryService getIndustryService() {
		return industryService;
	}

	public void setIndustryService(IndustryService industryService) {
		this.industryService = industryService;
	}

}
