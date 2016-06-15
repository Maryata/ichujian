package com.sys.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sys.commons.AbstractAction;
import com.sys.service.SetService;
import com.sys.util.StrUtils;

public class SetAction extends AbstractAction {
	
	private SetService setService;
	
	/**输出内容*/
	private String out;
	
    public String Click(){  //点击
    	Map<String,Object> retMap = new HashMap<String,Object>();
    	String imei=getParameter("imei");
    	String clicktype=getParameter("clicktype");
    	String name_package=getParameter("name_package");         //name_packages
    	String key=getParameter("key");
    	String choosetype=getParameter("choosetype");   //大类
    	log.info("into Click");

    	try{
		    //String name_packages=new String(name_package.getBytes("ISO-8859-1"),"UTF-8");
	    	log.info("--imei--:"+imei+"--clicktype--"+clicktype+"--name_package--"+name_package+"--key--"+key+"--choosetype--"+choosetype);
	    	if(StrUtils.isEmpty(clicktype)||StrUtils.isEmpty(key)||StrUtils.isEmpty(imei)||StrUtils.isEmpty(name_package)||StrUtils.isEmpty(choosetype)){
				retMap.put("status", "N");
				retMap.put("info", "1001");
	    	}else{
	    		Date actiondate1=new Date();    
				List list=setService.FindClick(imei,clicktype,key,choosetype);  //查询是否有记录    
	    
        		if(list.size()>0){    //有记录
        			//设置表
        		    setService.UpdateClick(imei, clicktype, name_package, actiondate1,key,choosetype);  //修改当前最新	//name_packages暂无用
        		    setService.UpdateOtherClick(imei, clicktype,key,choosetype);  //修改其他不是最新       //更改
        		    //历史表
        		    setService.UpdateClickHis(imei, clicktype, key,choosetype);   //修改此类型所有不是最新
        		    String appname="";
        		    String apppackage="";
        		    if(name_package.equals("0")){
        		    	 setService.ClickHis(imei, clicktype, key, apppackage, appname, actiondate1,choosetype);
        		    }else {
        		    	JSONArray array=JSONArray.fromObject(name_package);
             		    for(int i=0;i<array.size();i++){                  //循环插入最新
             		    	JSONObject json=(JSONObject) array.get(i);
             		    	appname=json.getString("appName");
             		    	apppackage=json.getString("appPackage");
                 		    setService.ClickHis(imei, clicktype, key, apppackage, appname, actiondate1,choosetype);
             		    }
					}
        		}else{               //无记录
        			//设置表
        		    setService.UpdateOtherClick(imei, clicktype,key,choosetype);  //修改其他不是最新      //更改
            		setService.Click(imei, clicktype, name_package, actiondate1,key,choosetype);  //插入记录    //name_packages无用

        		    //历史表
        		    setService.UpdateClickHis(imei, clicktype, key,choosetype);   //修改此类型所有不是最新
        		    String appname="";
        		    String apppackage="";
        		    if(name_package.equals("0")){
        		    	 setService.ClickHis(imei, clicktype, key, apppackage, appname, actiondate1,choosetype);
        		    }else {
        		    	JSONArray array=JSONArray.fromObject(name_package);
             		    for(int i=0;i<array.size();i++){                  //循环插入最新
             		    	JSONObject json=(JSONObject) array.get(i);
             		    	appname=json.getString("appName");
             		    	apppackage=json.getString("appPackage");
                 		    setService.ClickHis(imei, clicktype, key, apppackage, appname, actiondate1,choosetype);
             		    }
					}
        		}	
	    	}  	
			retMap.put("status", "Y");
	    } catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("SetAction.Click",e);
	    }
    	out = JSONObject.fromObject(retMap).toString();
    	return "success";
    }
	
    public String Hold(){  //长按
    	Map<String,Object> retMap = new HashMap<String,Object>();
    	String imei=getParameter("imei");
    	String holdtype=getParameter("holdtype");
    	String key=getParameter("key");   
    	String name_package=getParameter("name_package");
    	String choosetype=getParameter("choosetype");  //大类
    	log.info("into Hold");
    	log.info("--imei--"+imei+"--holdtype--"+holdtype+"--key--"+key+"--choosetype--"+choosetype+"--name_package--"+name_package);
    	try{
	    	if(StrUtils.isEmpty(holdtype)||StrUtils.isEmpty(imei)||StrUtils.isEmpty(key)||StrUtils.isEmpty(name_package)||StrUtils.isEmpty(choosetype)){
				retMap.put("status", "N");
				retMap.put("info", "1001");
	    	}else{
	    		Date actiondate1=new Date(); 		
				List list=setService.FindHold(imei,holdtype,key,choosetype);                 //更改
	    		if(list.size()>0){   //有设置记录
	    			//长按
	    			setService.UpdateHold(imei, holdtype, key,choosetype);     //更改 当前为最新
	    			setService.UpdateOtherHold(imei, holdtype, key,choosetype);   //修改其他不是最新
	    			//历史
	    			setService.UpdateHoldHis(imei, holdtype, key,choosetype);   //更改其他不是最新
	    			String appname="";
	    			String apppackage="";
	    			if(name_package.equals("0")){
	    				setService.HoldHis(imei, holdtype, key,actiondate1,choosetype,appname,apppackage);
	    			}else {
	    				JSONArray array=JSONArray.fromObject(name_package);
	        		    for(int i=0;i<array.size();i++){                  //循环插入最新
	        		    	JSONObject json=(JSONObject) array.get(i);
	        		    	appname=json.getString("appName");
	        		    	apppackage=json.getString("appPackage");
	        		    	setService.HoldHis(imei, holdtype, key,actiondate1,choosetype,appname,apppackage);
	        		    }
					}
	    			retMap.put("status", "Y");
	    		}else{    //无设置记录
	    			//长按
	    			setService.UpdateOtherHold(imei, holdtype, key,choosetype);   //修改其他不是最新
	        		setService.Hold(imei, holdtype, actiondate1,key,choosetype);       //新增   
	        		//历史
	        		setService.UpdateHoldHis(imei, holdtype, key,choosetype);   //更改其他不是最新
	        		String appname="";
	    			String apppackage="";
	    			if(name_package.equals("0")){
	    				setService.HoldHis(imei, holdtype, key,actiondate1,choosetype,appname,apppackage);
	    			}else {
	    				JSONArray array=JSONArray.fromObject(name_package);
	        		    for(int i=0;i<array.size();i++){                  //循环插入最新
	        		    	JSONObject json=(JSONObject) array.get(i);
	        		    	appname=json.getString("appName");
	        		    	apppackage=json.getString("appPackage");
	        		    	setService.HoldHis(imei, holdtype, key,actiondate1,choosetype,appname,apppackage);
	        		    }
					}
	    			retMap.put("status", "Y");
	    		}		
	    	}  	
	    } catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("SetAction.Hold failed,",e);
	    }
    	out = JSONObject.fromObject(retMap).toString();
    	return "success";
    }
    
    
    
    public String Sos(){  //SOS  暂时无用
    	Map<String,Object> retMap = new HashMap<String,Object>();
    	String userid=getParameter("userid");
    	String imei=getParameter("imei");
    	String sostype=getParameter("sostype");
    	String behavior=getParameter("behavior");
    	String soscontent=getParameter("soscontent");
    	String objective=getParameter("objective");  //求救对象
    	String actiondate=getParameter("actiondate");
    	log.info("进入SOS");
    	log.info("--userid--"+userid+"--imei--"+imei+"--sostype--"+sostype+"--behavior--"+behavior+"--soscontent--"+soscontent+"--objective--"+objective+"--actiondate--"+actiondate);
    	try{
	    	if(StrUtils.isEmpty(userid)||StrUtils.isEmpty(sostype)||StrUtils.isEmpty(imei)||StrUtils.isEmpty(behavior)||StrUtils.isEmpty(soscontent)||StrUtils.isEmpty(objective)||StrUtils.isEmpty(actiondate)){
				retMap.put("status", "N");
				retMap.put("info", "1001");
	    	}else{
	    		//Date actiondate1=sdf.parse(actiondate);
	    		Date actiondate1=new Date();
	    		setService.Sos(userid,imei, sostype, behavior, soscontent, objective, actiondate1);
				retMap.put("status", "Y");
	    	}  	
	    } catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("SetAction.Sos failed,",e);
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

	public SetService getSetService() {
		return setService;
	}

	public void setSetService(SetService setService) {
		this.setService = setService;
	}

}
