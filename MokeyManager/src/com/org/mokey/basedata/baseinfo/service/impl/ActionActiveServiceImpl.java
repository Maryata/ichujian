package com.org.mokey.basedata.baseinfo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.basedata.baseinfo.service.ActionActiveService;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.util.StrUtils;

public class ActionActiveServiceImpl implements ActionActiveService {
	
	
	protected  Logger log = (Logger.getLogger(getClass()));
	
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, Object> getActiveListMap(String time_s, String time_e,
			int start, int limit,String imei,String code) {
		Map<String, Object> ret = new HashMap<String, Object>(); 
		
		StringBuffer sql = new StringBuffer("select count(act.C_ID) from T_ACTION_ACTIVE act left join T_BASE_DEVICE dev on act.C_IMEI=dev.C_IMEI  where 1=1 ");
		List<Object> args = new ArrayList<Object>();
		
		
		if(StrUtils.isNotEmpty(code)){
			sql.append(" and  substr(act.C_ACTIVECODE,6,2)=? ");   
			args.add(code);
		}
		if(StrUtils.isNotEmpty(time_s)){
			sql.append(" and act.C_SYSDATE >= to_date(?,'yyyy-mm-dd') ");
			args.add(time_s);
		}
		if(StrUtils.isNotEmpty(time_e)){
			sql.append(" and act.C_SYSDATE < to_date(?,'yyyy-mm-dd')+1 ");
			args.add(time_e);
		}
		if(StrUtils.isNotEmpty(imei)){
			sql.append(" and act.C_IMEI like ? ");
			args.add("%"+imei+"%");
		}
		log.info("--------------"+sql);
		int count = jdbcTemplate.queryForInt(sql.toString(),args.toArray());
		//,sta.C_SYSDATE as START_DATE 启动时间;  left join T_ACTION_START sta on act.C_IMEI=sta.C_IMEI 
		String sql1 = DaoUtil.addfy_oracle(sql, " act.C_SYSDATE desc ", start, limit, args).toString().replace("count(act.C_ID)", "act.C_ID, act.C_IMEI, act.C_SYSDATE, act.C_ACTIVECODE,act.C_ACTIONCOUNT,act.C_ACTIONDATE ,dev.C_BRAND ");
		List list = jdbcTemplate.queryForList(sql1,args.toArray());
		
		ret.put("count", count);
		ret.put("list", list);
		
		return ret;
	}
	
	@Override
	public List GetDeviceByImei(String imei) {
		// TODO Auto-generated method stub
		String sql="select * from T_BASE_DEVICE where c_imei=?";
		List list=jdbcTemplate.queryForList(sql, new Object[]{imei});
		return list;
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public List GetDeviceInfoByImei(String imei,String key) {
		// TODO Auto-generated method stub
		    List list=new ArrayList();
			String sql="select a1.c_clicktype,a1.c_app_name,a1.c_key from " +
					" (select t.c_clicktype,t.c_app_name,t.c_key from T_SET_CLICK_HIS t,t_set_click s where t.c_imei=? and t.c_newest=1 and t.c_key=? " +
					" group by t.c_clicktype,t.c_app_name,t.c_key) a1 " +
					" where a1.c_clicktype=(select t.c_clicktype from T_SET_CLICK t where t.c_imei=? and t.c_key=? and t.c_newest=1)";
			List argsList=new ArrayList();
			argsList.add(imei);
			argsList.add(key);
			argsList.add(imei);
			argsList.add(key);
			list=jdbcTemplate.queryForList(sql, argsList.toArray());
		return list;
	}

	@Override
	public List GetOnekeyhold(String imei) {
		// TODO Auto-generated method stub
		String sql="select h.c_name from T_SET_Hold_HIS t,t_base_holdtype_info h " +
				" where t.c_imei=? and t.c_newest=1 and t.c_key=1 and t.c_holdtype=h.c_id";
        List list=jdbcTemplate.queryForList(sql, new Object[]{imei});		
		return list;
	}

	@Override
	public List GetTwokeyInss(String imei) {
		// TODO Auto-generated method stub
		String sql="select t.c_app_name from T_ACTION_INSTALLED_APPINFO t where t.c_imei=? and (t.c_type=0 or t.c_type=1)  order by t.c_type";
		List list=jdbcTemplate.queryForList(sql, new Object[]{imei});
		return list;
	}

	@Override
	public List GetThreekeyInss(String imei) {
		// TODO Auto-generated method stub
		String sql="select t.c_app_name from T_ACTION_INSTALLED_APPINFO t where t.c_imei=? and t.c_type=2";
		List list=jdbcTemplate.queryForList(sql, new Object[]{imei});
		return list;
	}

	@Override
	public List GetClickcount(String imei) {
		// TODO Auto-generated method stub
//		String sql ="select u.c_key,u.c_type,count(u.c_type) as count from t_action_usekey u where u.c_imei=? group by u.c_key,u.c_type";
//		List list = jdbcTemplate.queryForList(sql, new Object[]{imei});
//		String sql1 = "select count(*) from( " +
//		"SELECT TRUNC(u.c_actiondate) FROM t_action_usekey u " +
//		"where u.c_imei=? group by TRUNC(u.c_actiondate))";
//		int sum=jdbcTemplate.queryForInt(sql1, new Object[]{imei});
		String sql ="with US as( select u.c_key,u.c_type,count(u.c_type) as countss from t_action_usekey u where u.c_imei=? " +
				" group by u.c_key,u.c_type),UC as ( select count(*) as counts from(SELECT TRUNC(u.c_actiondate) FROM t_action_usekey u " +
				" where u.c_imei=? group by TRUNC(u.c_actiondate))) select us.c_key,us.c_type,us.countss,round(us.countss/uc.counts,2) UAVG " +
				" from us,uc order by us.c_key,us.c_type";
		List list = jdbcTemplate.queryForList(sql, new Object[]{imei,imei});
		return list;
	}

	@Override
	public String GetRemark(String imei) {
		// TODO Auto-generated method stub
		String remark="";
		String sql = "select c.c_remark from t_action_active t,t_base_active_code c where t.c_activecode=c.c_code and t.c_imei=?";
		List list=jdbcTemplate.queryForList(sql,new Object[]{imei});
		if(list.size()>0){
			Map remarks=(Map) list.get(0);
			if(remarks.get("C_REMARK")==null||"".equals(remarks.get("C_REMARK"))){
				remark="";
			}else {
				remark=remarks.get("C_REMARK").toString();
			}
		}
		return remark;
	}

	@Override
	public String GetUseKeyTotle(String imei) {
		// TODO Auto-generated method stub
//		String sql = "select count(*) from t_action_usekey u where u.c_imei=?";
//		int count=jdbcTemplate.queryForInt(sql, new Object[]{imei});
//		String sql1 = "select count(*) from( " +
//				"SELECT TRUNC(u.c_actiondate) FROM t_action_usekey u " +
//				"where u.c_imei=? group by TRUNC(u.c_actiondate))";
//		int sum=jdbcTemplate.queryForInt(sql1, new Object[]{imei});
		String totle="";
		String sql = "with US as ( select count(*) as count1 from t_action_usekey u where u.c_imei=?)," +
				" UC as ( select count(*) as count2 from(SELECT TRUNC(u.c_actiondate) FROM t_action_usekey u where u.c_imei=?" +
				" group by TRUNC(u.c_actiondate))) select round(us.count1/decode(uc.count2,0,1,uc.count2),2) as totle from us,uc";
		List list=jdbcTemplate.queryForList(sql,new Object[]{imei,imei});
		if(list.size()>0){
			Map map=(Map) list.get(0);
			totle= map.get("TOTLE").toString();
		}
		return totle;
	}

}
