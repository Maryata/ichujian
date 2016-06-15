package com.org.mokey.basedata.baseinfo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.basedata.baseinfo.service.ActionSendDemoQueryService;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.util.StrUtils;

public class ActionSendDemoQueryServiceImpl implements
		ActionSendDemoQueryService {

	protected JdbcTemplate jdbcTemplate;
	private  static boolean flag=false;
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.org.mokey.basedata.baseinfo.service.impl.ActionSendDemoQueryService
	 * #queryForSupplier()
	 */
	@SuppressWarnings( { "deprecation", "unchecked" })
	public Map<String, Object> queryForSupplier(String timeS, String timeE,
			String supp, String isSendDemo, int start, int limit) {
		Map<String, Object> ret = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer(
				"select DISTINCT ta.c_supplier_code,ta.C_COMPANY,ta.c_supplier_name  from   T_BASE_SUPPLIER  ta left join T_BASE_ACTIVE_CODE  tb on TA.C_SUPPLIER_CODE=SUBSTR(tb.C_CODE,6,2)  WHERE 1=1");
		// 在这边加条件
		if (StrUtils.isNotEmpty(supp)) {
			sql.append(" and ta.c_supplier_code=");
			sql.append(supp);

		}

		String countSql = sql.toString().replace(
				"DISTINCT ta.c_supplier_code,ta.C_COMPANY,ta.c_supplier_name",
				"count(distinct ta.c_id)");
 
		int count = getJdbcTemplate().queryForInt(countSql); // 获取总记录数

		// 这边要做的分页就是 针对于前三个条件的 唯一有关的就是 上面的代理商 这个条件 其他是否送样，是否根据日期都没有关系

		if (start > 0 || limit > 0) {
			sql.insert(0, "SELECT * FROM ( SELECT temp.* ,ROWNUM NUM  FROM (");

			sql.append(") temp ");
			if (limit > 0) {
				if (start < 0) {
					start = 0;
				}
				sql.append(" WHERE ROWNUM <= ");
				sql.append(start + limit);
				sql.append(")");

			}
			if (start > 0) {
				sql.append(" where num >  ");
				sql.append(start);
			}
		}


		List rows = getJdbcTemplate().queryForList(sql.toString());

		Iterator it = rows.iterator();
		List<Map> list = new ArrayList<Map>();
		while (it.hasNext()) {
			Map result = (Map) it.next();

			result.put("supplier", result.get("c_company"));
			result.put("brand", result.get("c_supplier_name"));
			result.put("code", result.get("C_SUPPLIER_CODE"));
			StringBuffer activeOK = new StringBuffer(
					"select  count(*)  from T_BASE_ACTIVE_CODE where c_isvalid=1 and substr(C_CODE,6,2)='"
							+ result.get("C_SUPPLIER_CODE") + "'"); // 这里查询出的
			// 是所有数量，不管激活未激活的数目
			StringBuffer activeNO = new StringBuffer(
					"select  count(*)  from T_BASE_ACTIVE_CODE where c_isvalid =0 and substr(C_CODE,6,2)='"
							+ result.get("C_SUPPLIER_CODE") + "'");
			StringBuffer sum = new StringBuffer(
					"select  count(*)  from T_BASE_ACTIVE_CODE where substr(C_CODE,6,2)='"
							+ result.get("C_SUPPLIER_CODE") + "'");
			
			//外面传进来 是否是送样  全部是三 ，如果是三的话  就默认没有这个 约束条件
			if(StrUtils.isNotEmpty(isSendDemo)){
			if(!isSendDemo.equals("3")){
			if (StrUtils.isNotEmpty(isSendDemo)) { 

				// 0 代表的是样本 1代表的不是样本 ==
				activeOK.append(" and c_issample=");
				activeOK.append(new Integer(isSendDemo) - 1); 

				activeNO.append(" and c_issample=");
				activeNO.append(new Integer(isSendDemo) - 1);

				sum.append(" and c_issample=");
				sum.append(new Integer(isSendDemo) - 1);
			}
			}
			}
			
			if (flag&&StrUtils.isNotEmpty(timeS)) { //第一次进来就 不让他根据时间查询
				
				activeNO.append(" and c_sysdate >= to_date('" + timeS
						+ "','yyyy-mm-dd') ");
				
				sum.append(" and c_sysdate >= to_date('" + timeS
						+ "','yyyy-mm-dd') ");
				if (StrUtils.isNotEmpty(timeE)) {
					activeNO.append(" and c_sysdate < to_date('" + timeE
							+ "','yyyy-mm-dd')+1 ");
					sum.append(" and c_sysdate < to_date('" + timeE
							+ "','yyyy-mm-dd')+1 ");
				}
				
			}
			
			flag=true;
			
			
			int number = getJdbcTemplate()
					.queryForInt(activeOK.toString());
			result.put("left", number); // 这里是未激活的数量    ---这里是和时间不挂钩的

			int number2 = getJdbcTemplate() //这里是已经激活的数量  XX
					.queryForInt(activeNO.toString());
			result.put("active", number2);
			
			int sum1 = getJdbcTemplate().queryForInt(sum.toString());
			result.put("sum", sum1);     //这里是总数量  和时间有关的啊  XX

			
			list.add(result);
		}


		ret.put("count", count); //这里是进行分页
		ret.put("list", list);
		return ret;

		// 这里所需要的就是 返回一个MAP集合 一个总个数，一个 LIST集合 LIST集合里面放的是MAP MAP 里面有K-V 通过界面上面的K
		// 就可以获得V

	}
	
	@SuppressWarnings("unchecked")
	public List queryForOneMessage(String imei, String clickType){
		List  args=new ArrayList();
		args.add(imei);
		args.add(clickType);
		
		String sql="select a1.c_clicktype,a1.c_app_name,a1.c_key from (select t.c_clicktype,t.c_app_name,t.c_key from T_SET_CLICK_HIS t,t_set_click s where t.c_imei=? and t.c_newest=1 and t.c_key=1 group by t.c_clicktype,t.c_app_name,t.c_key) a1 where a1.c_clicktype=?";
	
		
		return  jdbcTemplate.queryForList(sql,args.toArray());
	}
	
	
	
	public Map<String, Object> getActiveListMap(String time_s, String time_e,
			int start, int limit,String imei,String code) {
		Map<String, Object> ret = new HashMap<String, Object>(); 
		
		StringBuffer sql = new StringBuffer("select count(act.C_ID) from T_ACTION_ACTIVE act left join T_BASE_DEVICE dev on act.C_IMEI=dev.C_IMEI  where 1=1 ");
		List<Object> args = new ArrayList<Object>();
		
		
		if(StrUtils.isNotEmpty(code)){
			sql.append(" and  substr(act.C_ACTIVECODE,6,2)=? ");  
			args.add(code);
		}
//		if(StrUtils.isNotEmpty(time_s)){
//			sql.append(" and act.C_SYSDATE >= to_date(?,'yyyy-mm-dd') ");
//			args.add(time_s);
//		}
//		if(StrUtils.isNotEmpty(time_e)){
//			sql.append(" and act.C_SYSDATE < to_date(?,'yyyy-mm-dd')+1 ");
//			args.add(time_e);
//		}
		if(StrUtils.isNotEmpty(imei)){
			sql.append(" and act.C_IMEI like ? ");
			args.add("%"+imei+"%");
		}
		int count = jdbcTemplate.queryForInt(sql.toString(),args.toArray());
		//,sta.C_SYSDATE as START_DATE 启动时间;  left join T_ACTION_START sta on act.C_IMEI=sta.C_IMEI 
		String sql1 = DaoUtil.addfy_oracle(sql, " act.C_SYSDATE desc ", start, limit, args).toString().replace("count(act.C_ID)", "act.C_ID, act.C_IMEI, act.C_SYSDATE, act.C_ACTIVECODE,act.C_ACTIONCOUNT,act.C_ACTIONDATE ,dev.C_BRAND ");
		List list = jdbcTemplate.queryForList(sql1,args.toArray());
		
		ret.put("count", count);
		ret.put("list", list);
		return ret;
	}
	

}
