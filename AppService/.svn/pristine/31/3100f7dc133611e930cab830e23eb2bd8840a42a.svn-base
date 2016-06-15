package com.sys.action;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.cyberoller.sms.zt.MessagePostSender;
import com.sys.commons.AbstractAction;
import com.sys.service.UserService;
import com.sys.util.ApDateTime;
import com.sys.util.FileUtil;
import com.sys.util.MD5;
import com.sys.util.RandNumber;
import com.sys.util.StrUtils;
import com.sys.util.file.FileServices;

public class UserAction extends AbstractAction {
	
	private UserService userService;
	
	/**输出内容*/
	private String out;
	
    public String Login(){  //普通登录
    	Map<String,Object> retMap = new HashMap<String,Object>();
    	String phonenum=getParameter("phonenum");
    	String imei=getParameter("imei");
    	String password=getParameter("password");
    	log.info("into Login");
    	log.info("--phonenum--"+phonenum+"--imei--"+imei+"--password--"+password);
    	try{
    	if(StrUtils.isEmpty(phonenum)||StrUtils.isEmpty(password)||StrUtils.isEmpty(imei)){
			retMap.put("status", "N");
			retMap.put("info", "1001");
    	}else{
    		/** 2015-8-11 修改 ： 密码进行MD5加密 */
    		List list=userService.Login(phonenum, MD5.md5Code(password));
    		if(list.size()>0){
    			retMap.put("status", "Y");
    			Map m=(Map) list.get(0);
    			m.put("C_PASSWORD", password);
    			retMap.put("userinfo", list);
    			List list2=userService.GetMemberList(m.get("C_REGID").toString(), imei);
    			if(list2.size()>0){
    				userService.UpdateNewImei(m.get("C_REGID").toString(), imei);   //当前为最新
    				userService.UpdateOtherImei(m.get("C_REGID").toString(), imei); //修改其他不是最新
    			}else {
    				userService.InsertImei(m.get("C_REGID").toString(), imei,m.get("C_ID").toString());
    				userService.UpdateOtherImei(m.get("C_REGID").toString(), imei); //修改其他不是最新
				}
    		}else{
    			retMap.put("status", "N");
    			retMap.put("info", "1008");
    		}
    	}  	
	    } catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("UserAction.Login failed,",e);
	    }
    	out = JSONObject.fromObject(retMap).toString();
    	return "success";
    }
    
    
    public String External(){  //第三方登录
    	Map<String,Object> retMap = new HashMap<String,Object>();
    	String regid=getParameter("regid");
    	String nickname=getParameter("nickname");
    	String logintype=getParameter("logintype"); //第三方的名字
    	String imei=getParameter("imei");
    	String age=getParameter("age");
    	String sex=getParameter("sex");
    	String headimage=getParameter("headimage");
        log.info("into External");
        log.info("--regid--"+regid+"--nickname--"+nickname+"--logintype--"+logintype+"--imei--"+imei+"--age--"+age+"--sex--"+sex+"--headimage--"+headimage);
    	try{
    	if(StrUtils.isEmpty(regid)||StrUtils.isEmpty(nickname)||StrUtils.isEmpty(logintype)||StrUtils.isEmpty(imei)||StrUtils.isEmpty(headimage)){
			retMap.put("status", "N");
			retMap.put("info", "1001");
    	}else{
    		String sexs=("".equals(sex)||sex==null)?"":sex;
    		String ages=("".equals(age)||age==null)?"":age;
    		List list = userService.GetExternalList(regid, logintype);
    		String uid="";
    		if(list.size()>0){
        		Map map2=(Map) list.get(0);
        		uid=map2.get("C_ID").toString();
        		
    			retMap.put("isfirstlogin", "N");
    			//userService.UpdateExternal(regid, nickname, logintype, sexs, ages, headimage);
    			List imeilist=userService.FindExternalImei(regid, imei);
    			if(imeilist.size()>0){
    				userService.UpdateExternalNewImei(regid, imei);   //当前最新
    				userService.UpdateExternalOtherImei(regid, imei);  //其他不是最新
    			}else {
    				userService.InsertExternalImei(regid, imei,uid);    //新增
    				userService.UpdateExternalOtherImei(regid, imei);  //其他不是最新
				}
    		}else {
    			retMap.put("isfirstlogin", "Y");
    			uid=userService.External(regid, nickname, logintype, sexs, ages, headimage);  //新增
    			userService.InsertExternalImei(regid, imei,uid);
			}
			retMap.put("status", "Y");
			retMap.put("uid", uid);
    	}  	
	    } catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("UserAction.External failed,",e);
	    }
    	out = JSONObject.fromObject(retMap).toString();
    	return "success";
    }
    
    public String Userinfo(){        //用户信息
    	Map<String,Object> retMap = new HashMap<String,Object>();
    	String uid=getParameter("uid");
    	log.info("into Userinfo");
    	log.info("--uid--"+uid);
    	try{
    	if(StrUtils.isEmpty(uid)){
			retMap.put("status", "N");
			retMap.put("info", "1001");
    	}else{
    		List<Map> list=userService.GetUserList(uid);
	    	if(list.size()>0){
	    		for(Map map:list){
	    			if("".equals(map.get("C_NICKNAME"))||null==map.get("C_NICKNAME")){
		    				map.put("C_NICKNAME", "");
	    			}
	    		}
	    	}
    		List bindList=userService.GetUserBindList(uid);
    		
    		retMap.put("status", "Y");
    		retMap.put("userinfo", list);
    		retMap.put("bindList", bindList);
    		
    	}  	
	    } catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("UserAction.Userinfo failed,",e);
	    }
    	out = JSONObject.fromObject(retMap).toString();
    	return "success";
    }
    
    
    public String Modifypd(){  //修改密码
    	Map<String,Object> retMap = new HashMap<String,Object>();
    	String uid=getParameter("uid");
    	String oldpassword=getParameter("oldpassword");
    	String newpassword=getParameter("newpassword");
    	log.info("into Modifypd");
    	log.info("--uid--"+uid+"--oldpassword--"+oldpassword+"--newpassword--"+newpassword);
    	try{
    	if(StrUtils.isEmpty(uid)||StrUtils.isEmpty(oldpassword)||StrUtils.isEmpty(newpassword)){
			retMap.put("status", "N");
			retMap.put("info", "1001");
    	}else{
    		/** 2015-8-11 修改 ： 密码进行MD5加密 */
    		oldpassword = MD5.md5Code(oldpassword);
    		newpassword = MD5.md5Code(newpassword);
    		List list=userService.Findpd(uid,oldpassword);
            if(list.size()>0){
        		Date actiondate1=new Date();
        		userService.Modifypd(uid, oldpassword, newpassword, actiondate1);
    			retMap.put("status", "Y");
            }else{
    			retMap.put("status", "N");
    			retMap.put("info", "1009");
            } 		
    	}  	
	    } catch (Exception e) {
	    	retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("UserAction.Modifypd failed,",e);
	    }
    	out = JSONObject.fromObject(retMap).toString();
    	return "success";
    }
    
    
    public String EditUserInfo(){            //编辑用户信息
    	Map<String,Object> retMap = new HashMap<String,Object>();
    	String uid=getParameter("uid");
    	String nickname=getParameter("nickname");
    	String sex=getParameter("sex");
    	log.info("into EditUserInfo");
    	log.info("--uid--"+uid+"--nickname--"+nickname+"--sex--"+sex);
    	try{
    	if(StrUtils.isEmpty(uid)||StrUtils.isEmpty(nickname)||StrUtils.isEmpty(sex)){
			retMap.put("status", "N");
			retMap.put("info", "1001");
    	}else{
        		userService.EditUserInfo(uid,nickname,sex);
    			retMap.put("status", "Y");
    	}  	
	    } catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("UserAction.EditUserInfo failed,",e);
	    }
    	out = JSONObject.fromObject(retMap).toString();
    	return "success";
    }
    
    
    public String BindPhone(){  //绑定手机
    	Map<String,Object> retMap = new HashMap<String,Object>();
    	String uid=getParameter("uid");
    	String logintype=getParameter("logintype");
    	String type=getParameter("type");
    	String phonenum=getParameter("phonenum");
    	String validatecode=getParameter("validatecode");
    	String pwd=getParameter("pwd");
    	log.info("into BindPhone");
    	log.info("--uid--"+uid+"--logintype--"+logintype+"--phonenum--"+phonenum+"--validatecode--"+validatecode);
    	try{
    	if(StrUtils.isEmpty(uid)||StrUtils.isEmpty(logintype)||StrUtils.isEmpty(phonenum)||StrUtils.isEmpty(validatecode)){
			retMap.put("status", "N");
			retMap.put("info", "1001");
	    	out = JSONObject.fromObject(retMap).toString();
	    	return "success";
    	}
    		List reglist=userService.FindPhone(phonenum);     //注册过的手机无法绑定
    		if(reglist.size()>0){
    			retMap.put("status", "N");
    			retMap.put("info", "1010");
    			out = JSONObject.fromObject(retMap).toString();
    			return "success";
    		}
    		List list=userService.GetValidate(phonenum, type);    //没有验证码
    		if(list.size()<1){
    			retMap.put("status", "N");
    			retMap.put("info", "1011");
    	    	out = JSONObject.fromObject(retMap).toString();
    	    	return "success";
    		}else{
				/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String sdate=ApDateTime.dateAdd("mm", -30,  new java.util.Date(), ApDateTime.DATE_TIME_Sec);
				Map map=(Map) list.get(0);
    			if(!validatecode.equals(map.get("C_CODE").toString())||sdf.parse(map.get("C_DATE").toString()).before(sdf.parse(sdate))){  //验证码过期
        			retMap.put("status", "N");
        			retMap.put("info", "1011");
        	    	out = JSONObject.fromObject(retMap).toString();
        	    	return "success";
        		}else{
        			Date actiondate1=new Date();
            		userService.BindPhone(uid, logintype, phonenum, actiondate1);
            		
            		
            		// 生成logintype=0的信息（类似触键注册信息）
            		userService.chujianUser(uid,"0",pwd);
            		
        			retMap.put("status", "Y");	
        		}*/
    			// 获取30min之前的时间
				String s_date = ApDateTime.dateAdd("mm", -30,new java.util.Date(), ApDateTime.DATE_TIME_Sec);
				// 校验验证码是否可用
				List<Map<String, Object>> list2 = userService.isUsableCode(phonenum, validatecode, s_date, type);
				if (list2.isEmpty() && list2.size() < 1) {
					retMap.put("status", "N");
					retMap.put("info", "1011");
					out = JSONObject.fromObject(retMap).toString();
					return "success";
				}
				if(StrUtils.isNotEmpty(pwd)){// 密码不为空，绑定手机
					/** 2015-8-11 修改 ： 密码进行MD5加密 */
					pwd = MD5.md5Code(pwd);
					// 第三方绑定手机号
					userService.bindPhone(uid, logintype, phonenum, pwd);
					// 生成logintype=0的信息（类似触键注册信息）
					userService.chujianUser(uid,phonenum,"0",pwd,"ICJ"+uid);
				}else{// 密码为空，修改手机
					userService.updatePhonenum(uid,phonenum);
				}
				retMap.put("status", "Y");
			} 	
	    } catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("UserAction.BindPhone failed,",e);
	    }
    	out = JSONObject.fromObject(retMap).toString();
    	return "success";
    }
    
    public String BindExternal(){  //绑定第三方
    	Map<String,Object> retMap = new HashMap<String,Object>();
    	String uid=getParameter("uid");
    	String regid=getParameter("regid");
    	String logintype=getParameter("logintype");
    	String imei=getParameter("imei");
    	String nickname=getParameter("nickname");
    	String age=getParameter("age");
    	String sex=getParameter("sex");
    	String headimage=getParameter("headimage");
    	
    	
    	log.info("into BindExternal");
    	log.info("--regid--"+regid+"--logintype--"+logintype+"--uid--"+uid+"--imei--"+imei);
    	try{
    	if(StrUtils.isEmpty(regid)||StrUtils.isEmpty(logintype)||StrUtils.isEmpty(imei)||StrUtils.isEmpty(uid)){
			retMap.put("status", "N");
			retMap.put("info", "1001");
	    	out = JSONObject.fromObject(retMap).toString();
	    	return "success";
    	}
    	List list=userService.GetExternalListbystate(regid,logintype);   //查询第三方账号是否绑定或者注册过
    	if(list.size()>0){
    		retMap.put("info", "1017");	
    	}else {
    		List userList=userService.GetUserList(uid, "0");       //是否已经注册过
    		if(userList.size()>0){
    			Map map=(Map)userList.get(0);
    			String phonenum=map.get("C_PHONENUM").toString();
    			userService.BindExternal(uid, regid, nickname, logintype, sex, age, headimage,phonenum);	
    		}else {
    			userService.BindExternal(uid, regid, nickname, logintype, sex, age, headimage);	
			}    		
    		retMap.put("info", "1016");
		}
    	retMap.put("status", "Y");
	    } catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("UserAction.BindExternal failed,",e);
	    }
    	out = JSONObject.fromObject(retMap).toString();
    	return "success";
    }
    
    // 解绑第三方账号
 	public String unBindExternal(){
 		Map<String, Object> reqMap = new HashMap<String, Object>();
 		// 获取请求参数
 		String uid = this.getParameter("uid");
 		String logintype = this.getParameter("logintype");
 		log.info("into UserAction.unBindExternal");
 		log.info("uid = " + uid + " ,logintype = " + logintype);
 		try {
 			if (StrUtils.isEmpty(uid) || StrUtils.isEmpty(logintype)) {
 				reqMap.put("status", "N");
 				reqMap.put("info", "1001");
 			} else {
 				// 查询当前第三方账号是否是第一次登录的账号
 				String state = userService.isFirstInfo(uid,logintype);
				if("1".equals(state)){
					reqMap.put("status", "N");
					reqMap.put("info", "1018");
				}else{
					// 解除绑定
					userService.unBindExternal(uid,logintype);
					reqMap.put("status", "Y");
					reqMap.put("info", "1016");
				}
 			}
 		} catch (Exception e) {
 			reqMap.put("status", "N");
 			reqMap.put("info", "1003, " + e.getMessage());
 			log.error("UserAction.unBindExternal failed,e : " + e);
 		}
 		out = JSONObject.fromObject(reqMap).toString();
 		return "success";
 	}
    
    public String Reg(){  //注册
    	Map<String,Object> retMap = new HashMap<String,Object>();
    	String phonenum=getParameter("phonenum");
    	String password=getParameter("password");
    	String validatecode=getParameter("validatecode");
    	String imei=getParameter("imei");
    	String type=getParameter("type");
    	log.info("into Reg");
    	log.info("--phonenum--"+phonenum+"--password--"+password+"--validatecode--"+validatecode+"--imei--"+imei+"--type--"+type);
    	try{
    	if(StrUtils.isEmpty(phonenum)||StrUtils.isEmpty(password)||StrUtils.isEmpty(validatecode)||StrUtils.isEmpty(imei)||StrUtils.isEmpty(type)){
			retMap.put("status", "N");
			retMap.put("info", "1001");
			out = JSONObject.fromObject(retMap).toString();
			return "success";
    	}
//    		List list=userService.FindPhone(phonenum);
//    		if(list.size()>0){
//    			retMap.put("status", "N");
//    			retMap.put("info", "1010");
//    		}else{
			List list1=userService.GetValidate(phonenum, type);  //查询验证码
			if(list1.size()<1){
				retMap.put("status", "N");
    			retMap.put("info", "1011");
    			out = JSONObject.fromObject(retMap).toString();
    			return "success";
			}else {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String sdate=ApDateTime.dateAdd("mm", -30,  new java.util.Date(), ApDateTime.DATE_TIME_Sec);
				Map map=(Map) list1.get(0);
    			if(!validatecode.equals(map.get("C_CODE").toString())||sdf.parse(map.get("C_DATE").toString()).before(sdf.parse(sdate))){
        			retMap.put("status", "N");
        			retMap.put("info", "1011");
        			out = JSONObject.fromObject(retMap).toString();
        			return "success";
    			}
        		Date actiondate1=new Date();
        		String uid="";
        		/** 2015-8-11 修改 ： 保存密码之前进行MD5加密 */
        		String regid=userService.Reg(phonenum,MD5.md5Code(password),actiondate1);//注册
        		List listid=userService.GetExternalList("ICJ"+regid, "0");
        		if(listid.size()>0){
        			Map map2=(Map) listid.get(0);
            		uid=map2.get("C_ID").toString();
            		userService.RegImei("ICJ"+regid, imei, validatecode, actiondate1,uid); //注册IMEI	
            		// 生成默认昵称
            		userService.defaultNickname("ICJ"+uid,"ICJ"+regid);
        		}
    			retMap.put("status", "Y");
    			retMap.put("regid", "ICJ"+regid);
    			retMap.put("uid", uid);
    			retMap.put("info", "1012");	
			}
//    		}	
	    } catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("UserAction.Reg failed,",e);
	    }
    	out = JSONObject.fromObject(retMap).toString();
    	return "success";
    }
    
    // 校验手机号是否可用
  	public String validatePhone(){
  		Map<String, Object> reqMap = new HashMap<String, Object>();
  		// 获取请求参数
  		String phoneNum = this.getParameter("phoneNum");
  		log.info("into UserAction.validatePhone");
  		log.info("phoneNum = " + phoneNum);
  		try {
  			if (StrUtils.isEmpty(phoneNum)) {
 				reqMap.put("status", "N");
 				reqMap.put("info", "1001");
 			} else {
 	 			// 校验
 				List<Map<String,Object>> list = userService.findByPhone(phoneNum);
 	 			if(null!=list && list.size()>0){
 	 				reqMap.put("info", "1010");// 不可用
 	 				reqMap.put("status", "N");
 	 			}else{
 	 				reqMap.put("status", "Y");
 	 			}
 			}
  		} catch (Exception e) {
  			reqMap.put("status", "N");
  			reqMap.put("info", "1003," + e.getMessage());
  			log.error("UserAction.validatePhone failed,e : " + e);
  		}
  		out = JSONObject.fromObject(reqMap).toString();
  		return "success";
  	}
    
    public String GetValidateCode(){  //获取验证码
    	Map<String,Object> retMap = new HashMap<String,Object>();
    	String imei=getParameter("imei");
    	String phonenum=getParameter("phonenum");
    	String type=getParameter("type");
    	log.info("into getValidateCode");
    	log.info("--imei--"+imei+"--phonenum--"+phonenum+"--type--"+type);
    	try {
			if(StrUtils.isEmpty(imei)||StrUtils.isEmpty(phonenum)||StrUtils.isEmpty(imei)){
				retMap.put("status", "N");
				retMap.put("info", "1001");
			}else{
				if(type.equals("2")){                               //修改密码   没有注册 则不发送验证码
					List list=userService.FindPhone(phonenum);
					if(list.size()<1){
						retMap.put("status", "N");
            			retMap.put("info", "1013");
            			out = JSONObject.fromObject(retMap).toString();
    			    	return "success";
					}
				}
				if(type.equals("1")){                         //注册   手机号是否已经注册过
					List list=userService.FindPhone(phonenum);
					if(list.size()>0){
						retMap.put("status", "N");
            			retMap.put("info", "1010");
            			out = JSONObject.fromObject(retMap).toString();
    			    	return "success";
					}
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				List list=userService.GetValidateList(phonenum, type, sdf.format(new Date()));
				if(list.size()>=5){
					retMap.put("status", "N");
					retMap.put("info", "1015");
					out = JSONObject.fromObject(retMap).toString();
			    	return "success";
				}else {
					String host = FileUtil.getSmspath("sms.zt.sender.host");
					String username = FileUtil.getSmspath("sms.zt.sender.username");
					String password = FileUtil.getSmspath("sms.zt.sender.password");
					String productid = FileUtil.getSmspath("sms.zt.sender.productid");
					String sendnumber=FileUtil.getSmspath("sms.zt.sender.sendnumber");
					
					String code=RandNumber.getRandNumber(6);
					com.cyberoller.sms.MessageSender messageSender = new MessagePostSender(host, username, password, productid);
					String message = "亲爱的触键用户：手机验证码为"+code+"，请提交完成验证。如非本人操作，请忽略本短信。【触键】";
					messageSender.send(phonenum, message); //发送验证码
					userService.UpdateValidate(phonenum, type); //修改其他验证码状态
					userService.InsertValidate(imei,phonenum, type, new Date(), code);
	    			retMap.put("status", "Y");
	    			retMap.put("sendnumber", sendnumber);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("UserAction.getValidateCode failed,",e);
		}
		out = JSONObject.fromObject(retMap).toString();
    	return "success";
    }
    
    public String Feedback(){  //意见反馈
    	Map<String,Object> retMap = new HashMap<String,Object>();
    	String content=getParameter("content");
    	String imei=getParameter("imei");      //新增
    	String type=getParameter("type");      //新增
    	log.info("into Feedback");

    	try {
		    //String contents=new String(content.getBytes("ISO-8859-1"),"UTF-8");
	    	log.info("--content--"+content+"--imei--"+imei+"--type--"+type);
    	if(StrUtils.isEmpty(content)){
			retMap.put("status", "N");
			retMap.put("info", "1001");
    	}else{
    		Date actiondate1=new Date();
			userService.Feedback(content, actiondate1, imei, type);	
			retMap.put("status", "Y");
			retMap.put("info", "1007");
    	}
    	} catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("UserAction.Feedback failed,",e);
		}
//    	out = JSONObject.fromObject(retMap).toString();
//    	log.info("return data");
//    	return "success";
    	try {
			writeToResponse(JSONObject.fromObject(retMap).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return NONE;
    }	
    
    public String ForGetpd(){  //忘记密码-判断验证码
    	Map<String,Object> retMap = new HashMap<String,Object>();
    	String phonenum=getParameter("phonenum");
    	String validatecode=getParameter("validatecode");
    	String type=getParameter("type");
    	String imei=getParameter("imei");
    	log.info("into ForGetpd");
    	log.info("--phonenum--"+phonenum+"--validatecode--"+validatecode+"--type-"+type+"--imei--"+imei);
    	try{
    	if(StrUtils.isEmpty(validatecode)||StrUtils.isEmpty(phonenum)||StrUtils.isEmpty(type)||StrUtils.isEmpty(imei)){
			retMap.put("status", "N");
			retMap.put("info", "1001");
	    	out = JSONObject.fromObject(retMap).toString();
	    	return "success";
    	}
    		List list1=userService.GetValidate(phonenum, type);
    		if(list1.size()<1){
    			retMap.put("status", "N");
    			retMap.put("info", "1011");
    	    	out = JSONObject.fromObject(retMap).toString();
    	    	return "success";
    		}else {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String sdate=ApDateTime.dateAdd("mm", -30,  new java.util.Date(), ApDateTime.DATE_TIME_Sec);
				Map map=(Map) list1.get(0);
    			if(!validatecode.equals(map.get("C_CODE").toString())||sdf.parse(map.get("C_DATE").toString()).before(sdf.parse(sdate))){
        			retMap.put("status", "N");
        			retMap.put("info", "1011");
        	    	out = JSONObject.fromObject(retMap).toString();
        	    	return "success";
        		}else {
            		List list=userService.FindPhone(phonenum);
                    if(list.size()>0){
            			retMap.put("status", "Y");
                    }else{
            			retMap.put("status", "N");
            			retMap.put("info", "1013");
                    } 
				}
    			userService.UpdateValidate(phonenum, type);      //修改验证码状态为0
			}		 	
	    } catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("UserAction.ForGetpd failed,",e);
	    }
    	out = JSONObject.fromObject(retMap).toString();
    	return "success";
    }
    
    
    public String ForGetpdUpdate(){  //忘记密码-修改密码
    	Map<String,Object> retMap = new HashMap<String,Object>();
    	String phonenum=getParameter("phonenum");
    	String newpassword=getParameter("newpassword");
    	log.info("into ForGetpd");
    	log.info("--phonenum--"+phonenum+"--newpassword--"+newpassword);
    	try{
    	if(StrUtils.isEmpty(phonenum)||StrUtils.isEmpty(newpassword)){
			retMap.put("status", "N");
			retMap.put("info", "1001");
	    	out = JSONObject.fromObject(retMap).toString();
	    	return "success";
    	}
            		List list=userService.FindPhone(phonenum);
                    if(list.size()>0){
                		Date actiondate1=new Date();
                		/** 2015-8-11 修改 ： 密码进行MD5加密 */
                		newpassword = MD5.md5Code(newpassword);
                		userService.ForGetpd(phonenum,newpassword, actiondate1);  //修改密码
            			retMap.put("status", "Y");
            			retMap.put("info", "1014");
                    }else{
            			retMap.put("status", "Y");
            			retMap.put("info", "1013");
                    } 
	    } catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("UserAction.ForGetpd failed,",e);
	    }
    	out = JSONObject.fromObject(retMap).toString();
    	return "success";
    }
    
    public String UserCenter(){    //用户中心
    	Map<String, Object> retMap=new HashMap<String, Object>();
    	try{
	    	String uid=getParameter("uid");
	    	String logintype=getParameter("logintype");
	    	log.info("into ActiveityInfo");
	    	log.info("--uid--"+uid+"--logintype--"+logintype);
	    	if(StrUtils.isEmpty(uid)||StrUtils.isEmpty(logintype)){
	    		retMap.put("status", "N");
	    		retMap.put("info", "1001");
	    		out=JSONObject.fromObject(retMap).toString();
	    		return "success";
	    	}
	    	List<Map> listinfo=userService.GetUserInfo(uid, logintype);
	    	if(listinfo.size()>0){
	    		for(Map map:listinfo){
	    			if("".equals(map.get("C_NICKNAME"))||null==map.get("C_NICKNAME")){
		    				map.put("C_NICKNAME", "");
	    			}
	    		}
	    	}
	    	retMap.put("status", "Y");
	    	retMap.put("userinfo", listinfo);
	    	//String integral=userService.GetIntegral(uid);
	    	//retMap.put("integral", integral);
	    	//String privilege=userService.GetPrivilege(uid);
	    	//retMap.put("privilege", privilege);
	    	List list=userService.GetActivity(uid);
	    	retMap.put("activity", list);
	    	String follow=userService.GetFollow(uid);
	    	retMap.put("follow", follow);
    	}catch(Exception e){
    		retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("UserAction.UserCenter failed,",e);
    	}
    	out=JSONObject.fromObject(retMap).toString();
    	return "success";
    }
    
    public String UserCollectingActivity() {      //用户参加 收藏的活动
    	Map<String, Object> retMap=new HashMap<String, Object>();
    	String uid=getParameter("uid");
    	String type=getParameter("type");
    	String pageindex=getParameter("pageindex");
    	log.info("into UserCollectingActivity");
    	log.info("--uid--"+uid+"--pageindex--"+pageindex);
    	if(StrUtils.isEmpty(uid)||StrUtils.isEmpty(pageindex)){
    		retMap.put("status", "N");
    		retMap.put("info", "1001");
    		out=JSONObject.fromObject(retMap).toString();
    		return "success";
    	}
    	try {
    		List activity=userService.UserCollectingActivity(uid,pageindex,type);
    		int i=userService.UserCollectingActivityNum(type,uid);
    		retMap.put("status", "Y");
    		retMap.put("activity", activity);
    		retMap.put("count", i+"");
		} catch (Exception e) {
			// TODO: handle exception
    		retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("UserAction.UserCollectingActivity failed,",e);
		}
    	out=JSONObject.fromObject(retMap).toString();
    	return "success";
	}
    
    public String UserFollowActivity() { //用户关注的活动（首页）
    	Map<String, Object> retMap=new HashMap<String, Object>();
    	String uid=getParameter("uid");
    	String pageindex=getParameter("pageindex");
    	log.info("into UserFollowActivity");
    	log.info("--uid--"+uid+"--pageindex--"+pageindex);
    	if(StrUtils.isEmpty(uid)||StrUtils.isEmpty(pageindex)){
    		retMap.put("status", "N");
    		retMap.put("info", "1001");
    		out=JSONObject.fromObject(retMap).toString();
    		return "success";
    	}
    	try {
    		List activity=userService.UserFollowBrandActivity(uid,pageindex);
    		retMap.put("status", "Y");
    		retMap.put("activity", activity);
		} catch (Exception e) {
			// TODO: handle exception
    		retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("UserAction.UserFollowActivity failed,",e);
		}
    	out=JSONObject.fromObject(retMap).toString();
    	return "success";
	}
    
    public String UserMainFollowBrand() { //用户关注的品牌（首页）
    	Map<String, Object> retMap=new HashMap<String, Object>();
    	String uid=getParameter("uid");
    	String pageindex=getParameter("pageindex");
    	log.info("into UserMainFollowBrand");
    	log.info("--uid--"+uid+"--pageindex--"+pageindex);
    	if(StrUtils.isEmpty(uid)||StrUtils.isEmpty(pageindex)){
    		retMap.put("status", "N");
    		retMap.put("info", "1001");
    		out=JSONObject.fromObject(retMap).toString();
    		return "success";
    	}
    	try {
    		List brand=userService.UserMainFollowBrand(uid,pageindex);
    		retMap.put("status", "Y");
    		retMap.put("brand", brand);
		} catch (Exception e) {
			// TODO: handle exception
    		retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("UserAction.UserMainFollowBrand failed,",e);
		}
    	out=JSONObject.fromObject(retMap).toString();
    	return "success";
	}
    
    public String UserFollowBrand() { //用户关注的品牌（我的活动帮）
    	Map<String, Object> retMap=new HashMap<String, Object>();
    	String uid=getParameter("uid");
    	String pageindex=getParameter("pageindex");
    	log.info("into UserFollowBrand");
    	log.info("--uid--"+uid+"--pageindex--"+pageindex);
    	if(StrUtils.isEmpty(uid)||StrUtils.isEmpty(pageindex)){
    		retMap.put("status", "N");
    		retMap.put("info", "1001");
    		out=JSONObject.fromObject(retMap).toString();
    		return "success";
    	}
    	try {
    		List brand=userService.UserFollowBrand(uid,pageindex);
    		int i=userService.UserFollowBrandNum(uid);
    		retMap.put("status", "Y");
    		retMap.put("brand", brand);
    		retMap.put("count", i+"");
		} catch (Exception e) {
			// TODO: handle exception
    		retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("UserAction.UserFollowBrand failed,",e);
		}
    	out=JSONObject.fromObject(retMap).toString();
    	return "success";
	}
    
    /**
     * 用户上传头像
     * @return
     */
    public String UserHead(){
    	Map<String, Object> retMap=new HashMap<String, Object>();
    	String uid=getParameter("uid");
    	InputStream userhead=getParameter3("userhead");
    	//InputStream userhead1=getParameter3("userhead");
    	
    	log.info("into UserHead");
    	log.info("--uid--"+uid);
    	if(StrUtils.isEmpty(uid)){
    		retMap.put("status", "N");
    		retMap.put("info", "1001");
    		out=JSONObject.fromObject(retMap).toString();
    		return "success";
    	}
    	try {
//    		MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper)getRequest();
//    		File[] files = multiWrapper.getFiles("userhead");
//    		String[] filname=multiWrapper.getFileNames("userhead");
    		String fileurl="";
			//for(int i=0;i<files.length;i++){
				//int index=filname[i].indexOf(".");
				//String fileNameString = uid + filname[i].substring(index);
//    			String timestamp = "_" + System.currentTimeMillis();
				fileurl = FileServices.saveHeadFile(userhead, "activity/image/userHead/" + uid + ".png");
			//}
			userService.UpdateHead(uid, fileurl);
			retMap.put("status", "Y");
			retMap.put("userhead", fileurl);
		} catch (Exception e) {
			// TODO: handle exception
			retMap.put("status", "N");
			retMap.put("info", "1003"+e.getMessage());
			log.error("UserAction.UserHead failed,",e);
		}
		out=JSONObject.fromObject(retMap).toString();
    	return "success";
    }
    

	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
