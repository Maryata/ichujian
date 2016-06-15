package com.sys.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sys.util.ApDateTime;
import com.sys.util.StrUtils;

public class UserServiceImpl extends SqlMapClientDaoSupport implements UserService {
	
	/** Logger available to subclasses */
	private Logger log = (Logger.getLogger(UserServiceImpl.class));

	@Override
	public String External(String regid,String nickname,String logintype,String sex,String age,String headimage) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("regid", regid);
		map.put("nickname", nickname);
		map.put("logintype", logintype);
		map.put("sex", sex);
		map.put("age", age);
		map.put("headimage", headimage);
		map.put("actiondate", new Date());
		String uid=(String)getSqlMapClientTemplate().insert("user.External",map);
		return uid;
	}

	@Override
	public void Feedback(String content, Date actiondate) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("content", content);
		map.put("actiondate", actiondate);
		getSqlMapClientTemplate().insert("soft_sql1.Feedback",map);
	}
	
	@Override
	public void Feedback(String content,Date actiondate,String imei,String type) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("content", content);
		map.put("actiondate", actiondate);
		map.put("imei", imei);
		map.put("type", type);
		getSqlMapClientTemplate().insert("soft_sql1.Feedback1",map);
	}

	@Override
	public List Login(String phonenum, String password) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("phonenum", phonenum);
		map.put("password", password);
		List list=getSqlMapClientTemplate().queryForList("user.Login",map);
		return list;
	}
	
	@Override
	public void BindPhone(String regid,String logintype,String phonenum,Date actiondate){
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("regid", regid);
		map.put("logintype", logintype);
		map.put("phonenum", phonenum);
		map.put("actiondate", actiondate);
		getSqlMapClientTemplate().update("user.BindPhone",map);
	}

	@SuppressWarnings("deprecation")
	@Override
	// 第三方注册账号绑定手机后，生成logintype为0的数据
	public void chujianUser(String uid, String phonenum, String logintype, String pwd, String nickname) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UID", uid);
		map.put("LOGINTYPE", logintype);
		map.put("PWD", pwd);
		map.put("PHONENUM", phonenum);
		map.put("NICKNAME", nickname);
		map.put("DATE", new Date());
		this.getSqlMapClientTemplate().update("user.chujianUser", map);
	}
	
	@Override
	public void Modifypd(String regid, String oldpassword,
			String newpassword, Date actiondate) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("regid", regid);
		map.put("oldpassword", oldpassword);
		map.put("newpassword", newpassword);
		map.put("actiondate", actiondate);
		getSqlMapClientTemplate().update("user.Modifypd",map);
	}

	@Override
	public String Reg(String phonenum,String password,Date actiondate) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("phonenum", phonenum);
		map.put("password", password);
		map.put("actiondate", actiondate);
		String i=(String) getSqlMapClientTemplate().insert("user.Reg",map);
		return i;
	}

	@Override
	public void Product_User(String userid, String activecode, Date actiondate) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("userid", userid);
		map.put("activecode", activecode);
		map.put("actiondate", actiondate);
		getSqlMapClientTemplate().insert("soft_sql.Product_User",map);
	}

	@Override
	public void Device_User(String userid, String imei, Date actiondate) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("userid", userid);
		map.put("imei", imei);
		map.put("actiondate", actiondate);
		getSqlMapClientTemplate().insert("soft_sql.Device_User",map);
	}

	@Override
	public List FindPhone(String phonenum) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("phonenum", phonenum);
		List list=getSqlMapClientTemplate().queryForList("user.FindPhone",map);
		return list;
	}

	@Override
	public List Findpd(String regid,String oldpassword) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("regid", regid);
		map.put("oldpassword", oldpassword);
		List list=getSqlMapClientTemplate().queryForList("user.Findpd",map);
		return list;
	}

	@Override
	public List GetMemberList(String regid,String imei) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("regid", regid);
		map.put("imei", imei);
		List list=getSqlMapClientTemplate().queryForList("user.GetMemberList",map);
		return list;
	}

	@Override
	public void InsertImei(String regid, String imei,String uid) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("uid", uid);
		map.put("regid", regid);
		map.put("imei", imei);
		map.put("actiondate", new Date());
		getSqlMapClientTemplate().insert("user.InsertImei", map);
	}

	@Override
	public void UpdateNewImei(String regid, String imei) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("regid", regid);
		map.put("imei", imei);
		getSqlMapClientTemplate().update("user.UpdateNewImei", map);
	}

	@Override
	public void UpdateOtherImei(String regid, String imei) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("regid", regid);
		map.put("imei", imei);
		getSqlMapClientTemplate().update("user.UpdateOtherImei", map);
	}

	@Override
	public List GetExternalList(String regid, String logintype) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("regid", regid);
		map.put("logintype", logintype);
		List list=getSqlMapClientTemplate().queryForList("user.GetExternalList",map);
		return list;
	}

	@Override
	public List FindExternalImei(String regid, String imei) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("regid", regid);
		map.put("imei", imei);
		List list=getSqlMapClientTemplate().queryForList("user.FindExternalImei",map);
		return list;
	}

	@Override
	public void UpdateExternal(String regid, String nickname, String logintype,
			String sex, String age, String headimage) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("regid", regid);
		map.put("nickname", nickname);
		map.put("logintype", logintype);
		map.put("sex", sex);
		map.put("age", age);
		map.put("headimage", headimage);
		getSqlMapClientTemplate().update("user.UpdateExternal",map);
	}

	@Override
	public void InsertExternalImei(String regid,String imei,String uid) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("uid", uid);
		map.put("regid", regid);
		map.put("imei", imei);
		map.put("actiondate", new Date());
		getSqlMapClientTemplate().insert("user.InsertExternalImei",map);
	}

	@Override
	public void UpdateExternalNewImei(String regid, String imei) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("regid", regid);
		map.put("imei", imei);
		getSqlMapClientTemplate().insert("user.UpdateExternalNewImei",map);
	}

	@Override
	public void UpdateExternalOtherImei(String regid, String imei) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("regid", regid);
		map.put("imei", imei);
		getSqlMapClientTemplate().insert("user.UpdateExternalOtherImei",map);
	}

	@Override
	public void EditUserInfo(String regid, String nickname, String sex) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("regid", regid);
		map.put("nickname", nickname);
		map.put("sex", sex);
		map.put("actiondate", new Date());
		getSqlMapClientTemplate().update("user.EditUserInfo",map);
	}

	@Override
	public void RegImei(String regid, String imei, String validatecode,
			Date actiondate,String uid) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("regid", regid);
		map.put("imei", imei);
		map.put("validatecode", validatecode);
		map.put("actiondate", actiondate);
		map.put("uid", uid);
		getSqlMapClientTemplate().insert("user.RegImei",map);
	}

	@Override
	public void ForGetpd(String phonenum, String newpassword, Date actiondate) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("phonenum", phonenum);
		map.put("newpassword", newpassword);
		map.put("actiondate", actiondate);
		getSqlMapClientTemplate().insert("user.ForGetpd",map);
	}

	
	@Override
	public String GetIntegral(String regid) {
		// TODO Auto-generated method stub
		int integral=0;
		Map map=new HashMap();
		map.put("regid", regid);
		List list=getSqlMapClientTemplate().queryForList("user.GetIntegral",map);
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				Map map2=(Map) list.get(i);
				int number=Integer.parseInt(map2.get("C_INTEGRAL").toString());
				integral+=number;
			}
		}
		return integral+"";
	}
	
	@Override
	public String GetPrivilege(String regid) {
		// TODO Auto-generated method stub
		int privilege=0;
		Map map=new HashMap();
		map.put("regid", regid);
		List list=getSqlMapClientTemplate().queryForList("user.GetPrivilege",map);
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				Map map2=(Map) list.get(i);
				int number=Integer.parseInt(map2.get("C_NUMBER").toString());
				privilege+=number;
			}
		}
		return privilege+"";
	}
	
	@Override
	public List GetActivity(String regid) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("regid", regid);
		List list=getSqlMapClientTemplate().queryForList("user.GetActivity",map);
		if(list.size()<1){
			for (int i = 0; i < 3; i++) {
	            Map mapi=new HashMap();
	            mapi.put("C_TYPE", i+1+"");
	            mapi.put("C_COUNT", "0");
	            list.add(mapi);
			}
		}else {
				for (int j = 0; j < 3; j++) {
					boolean ishas=false;
					for (int i = 0; i < list.size(); i++) {
						Map map2=(Map) list.get(i);
						String value=map2.get("C_TYPE").toString();
						if(value.equals(j+1+"")){
							ishas=true;
							break;
						}
					}
					if(!ishas){
						Map mapi=new HashMap();
			            mapi.put("C_TYPE", j+1+"");
			            mapi.put("C_COUNT", "0");
			            list.add(mapi);
					}
				}
		}
		return list;
	}

	@Override
	public String GetFollow(String regid) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("regid", regid);
		int number=(Integer)getSqlMapClientTemplate().queryForObject("user.GetFollow",map);
		return number+"";
	}

	@Override
	public List GetUserInfo(String regid, String logintype) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("regid", regid);
		map.put("logintype", logintype);
		List list=getSqlMapClientTemplate().queryForList("user.GetUserInfo",map);
		return list;
	}

	@Override
	public List UserFollowBrand(String uid,String pageindex) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("uid", uid);
		int page=Integer.parseInt(pageindex);
		page=(page-1)*20;
		map.put("page", page);
		List list=getSqlMapClientTemplate().queryForList("user.UserFollowBrand",map);
		return list;
	}

	@Override
	public List UserFollowBrandActivity(String regid,String pageindex) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("regid", regid);
		int page=Integer.parseInt(pageindex);
		page=(page-1)*20;
		map.put("page", page);
		List list=getSqlMapClientTemplate().queryForList("user.UserFollowBrandActivity",map);
		return list;
	}

	@Override
	public List GetValidateList(String phonenum, String type,String date) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("phonenum", phonenum);
		map.put("type", type);
		map.put("date", date);
		List list=getSqlMapClientTemplate().queryForList("user.getValidateList",map);
		return list;
	}

	@Override
	public void InsertValidate(String imei,String phonenum, String type, Date date,
			String code) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("imei", imei);
		map.put("phonenum", phonenum);
		map.put("type", type);
		map.put("date", date);
		map.put("code", code);
		getSqlMapClientTemplate().insert("user.InsertValidate",map);
	}

	@Override
	public void UpdateValidate(String phonenum, String type) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("phonenum", phonenum);
		map.put("type", type);
		getSqlMapClientTemplate().update("user.UpdateValidate",map);
	}

	@Override
	public List GetValidate(String phonenum, String type) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("phonenum", phonenum);
		map.put("type", type);
		List list=getSqlMapClientTemplate().queryForList("user.GetValidate",map);
		return list;
	}

	@Override
	public void UpdateHead(String regid, String headurl) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("regid", regid);
		map.put("headurl", headurl);
		getSqlMapClientTemplate().update("user.UpdateHead",map);
	}

	@Override
	public int UserFollowBrandNum(String regid) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("regid", regid);
		int i=(Integer) getSqlMapClientTemplate().queryForObject("user.UserFollowBrandNum",map);
		return i;
	}

	@Override
	public List UserCollectingActivity(String regid, String pageindex,String type) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("regid", regid);
		map.put("type", type);
		int page=Integer.parseInt(pageindex);
		page=(page-1)*20;
		map.put("page", page);
		List list=getSqlMapClientTemplate().queryForList("user.UserCollectingActivity",map);
		return list;
	}

	@Override
	public List UserMainFollowBrand(String uid, String pageindex) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("uid", uid);
		int page=Integer.parseInt(pageindex);
		page=(page-1)*20;
		map.put("page", page);
		List list=getSqlMapClientTemplate().queryForList("user.UserMainFollowBrand",map);
		return list;
	}

	@Override
	public int UserCollectingActivityNum(String type, String regid) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("regid", regid);
		map.put("type", type);
		int i=(Integer) getSqlMapClientTemplate().queryForObject("user.UserCollectingActivityNum",map);
		return i;
	}

	@Override
	public void BindExternal(String uid, String regid, String nickname,
			String logintype, String sex, String age, String headimage) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("uid", uid);
		map.put("regid", regid);
		map.put("nickname", nickname);
		map.put("logintype", logintype);
		map.put("sex", sex);
		map.put("age", age);
		map.put("headimage", headimage);
		map.put("actiondate", new Date());
		getSqlMapClientTemplate().insert("user.BindExternal",map);
	}

	@Override
	public List GetUserList(String uid, String logintype) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("uid", uid);
		map.put("logintype", logintype);
		List list=getSqlMapClientTemplate().queryForList("user.GetUserList",map);
		return list;
	}

	@Override
	public List GetExternalListbystate(String regid, String logintype) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("regid", regid);
		map.put("logintype", logintype);
		List list=getSqlMapClientTemplate().queryForList("user.GetExternalListbystate",map);
		return list;
	}

	@Override
	public List GetUserBindList(String uid) {
		List retList =new ArrayList();
		Map map=new HashMap();
		map.put("uid", uid);
		List<Map> list=getSqlMapClientTemplate().queryForList("user.GetUserBindList",map);

		Map retMap =new HashMap();
		retList.add(retMap);
		retMap.put("chujian", "0");
		retMap.put("qq", "0");
		retMap.put("weixin", "0");
		retMap.put("weibo", "0");
		if(list.size()>0){
			for (Map m : list) {
                String value=m.get("C_LOGINTYPE").toString();
                if("0".equals(value)){
                	retMap.put("chujian", "1");
                }else if("1".equals(value)){
                	retMap.put("qq", "1");
                	if("0".equals(m.get("C_STATE"))){
                		retMap.put("unBindable_qq", "1");
    				}else{
    					retMap.put("unBindable_qq", "0");
    				}
                }else if("2".equals(value)){
                	retMap.put("weixin", "1");
                	if("0".equals(m.get("C_STATE"))){
    					retMap.put("unBindable_weixin", "1");
    				}else{
    					retMap.put("unBindable_weixin", "0");
    				}
                }else if("3".equals(value)){
                	retMap.put("weibo", "1");
                	if("0".equals(m.get("C_STATE"))){
    					retMap.put("unBindable_weibo", "1");
    				}else{
    					retMap.put("unBindable_weibo", "0");
    				}
                }
			}
		}
		return retList;
	}

	@Override
	public void BindExternal(String uid, String regid, String nickname,
			String logintype, String sex, String age, String headimage,
			String phonenum) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("uid", uid);
		map.put("regid", regid);
		map.put("nickname", nickname);
		map.put("logintype", logintype);
		map.put("sex", sex);
		map.put("age", age);
		map.put("headimage", headimage);
		map.put("actiondate", new Date());
		if(null==phonenum){
			getSqlMapClientTemplate().insert("user.BindExternal",map);
		}else{
			map.put("phonenum", phonenum);
			getSqlMapClientTemplate().insert("user.BindExternal2",map);
		}
	}
	
	@Override
	// 解绑第三方账号
	public void unBindExternal(String uid, String logintype) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UID", uid);
		map.put("LOGINTYPE", logintype);
		this.getSqlMapClientTemplate().update("user.unBindExternal", map);
	}

	@Override
	public List GetUserList(String uid) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		map.put("uid", uid);
		List list=getSqlMapClientTemplate().queryForList("user.GetUserList2",map);
		return list;
	}

	@Override
	// 查询当前第三方账号是否是第一次登录的账号
	public String isFirstInfo(String uid, String logintype) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UID", uid);
		map.put("LOGINTYPE", logintype);
		return (String) this.getSqlMapClientTemplate().queryForObject("user.isFirstInfo", map);
	}
	
	@Override
	// 查询是否存在logintype为0的信息
	public int existChujianUser(String uid, String logintype) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UID", uid);
		map.put("LOGINTYPE", logintype);
		return (Integer) this.getSqlMapClientTemplate().queryForObject("user.existChujianUser", map);
	}

	@Override
	// 生成默认昵称
	public void defaultNickname(String nickname, String regid) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("NICKNAME", nickname);
		map.put("REGID", regid);
		this.getSqlMapClientTemplate().update("user.defaultNickname", map);
	}

	@Override
	public List<Map<String, Object>> isUsableCode(String phonenum,
			String validatecode, String s_date, String type) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("DATE", s_date);
		map.put("VALICODE", validatecode);
		map.put("TYPE", type);
		map.put("PHONENUM", phonenum);
		return this.getSqlMapClientTemplate().queryForList("user.isUsableCode", map);
	}

	@Override
	public List<Map<String, Object>> findByPhone(String phonenum) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("PHONENUM", phonenum);
		return this.getSqlMapClientTemplate().queryForList("user.findByPhone", map);
	}

	@Override
	public void bindPhone(String uid, String logintype, String phonenum, String pwd) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UID", uid);
		map.put("LOGINTYPE", logintype);
		map.put("PHONENUM", phonenum);
		map.put("PWD", pwd);
		map.put("ACTIONDATE", new Date());
		this.getSqlMapClientTemplate().update("user.bindPhone", map);
	}

	@Override
	public void updatePhonenum(String uid, String phonenum) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UID", uid);
		map.put("PHONENUM", phonenum);
		map.put("DATE", new Date());
		this.getSqlMapClientTemplate().update("user.updatePhonenum", map);
	}
	
}
