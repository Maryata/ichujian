package com.org.mokey.analyse.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.org.mokey.analyse.service.HoldTypeInfoService;
import com.org.mokey.analyse.entiy.HoldTypeInfoBean;
import com.org.mokey.common.util.ApDateTime;

public class HoldTypeServiceImpl  extends SqlMapClientDaoSupport implements HoldTypeInfoService{
	
	/** Logger available to subclasses */
	private Logger log = (Logger.getLogger(HoldTypeServiceImpl.class));
	private JdbcTemplate jdbcTemplate;	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	@Override
	public List HoldTypeList(String startyear ,String startmonth ,String endyear,String endmonth){
		// TODO Auto-generated method stub
		List result = new ArrayList();
		List indexresult = new ArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Calendar cal = Calendar.getInstance();
        Calendar calE = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(startyear + "-" + startmonth));
			calE.setTime(sdf.parse(endyear + "-" + endmonth));
			calE.add(Calendar.MONTH, 1);
			
			//时间范围区间
			List<String> nowDays = ApDateTime.getMonthBetween(startyear + "-" + startmonth, endyear + "-" + endmonth);
			
			String date1 = sdf.format(cal.getTime());
			String date2 = sdf.format(calE.getTime());
				
//			Map  maplist=new HashMap();
//			maplist.put("date1", date1);
//			maplist.put("date2", date2);
//			
//			indexresult = this.getSqlMapClientTemplate().queryForList("analytics.HoldList",maplist);  //查询
			
			String sqlString="select holdinfo.count,info.c_name,info.c_id from ( SELECT count(t.c_holdtype) as count,t.c_holdtype FROM T_SET_HOLD_HIS t where  t.c_key=1 and t.c_newest=1 and t.c_actiondate>=TO_DATE(?,'YYYY-MM') and t.c_actiondate<TO_DATE(?,'YYYY-MM') group by t.c_holdtype order by count(t.c_holdtype) desc ) holdinfo ,t_base_holdtype_info info where holdinfo.c_holdtype(+)=info.c_id and info.c_islive=1";
            List agrsList=new ArrayList();
            agrsList.add(date1);
            agrsList.add(date2);
            /** 查询“一键长按设置”中每种设置类型的数量 */
            indexresult=jdbcTemplate.queryForList(sqlString, agrsList.toArray());
			
			
			if (indexresult != null) {
				for (int i = 0; i < indexresult.size(); i++) {
					Map map = (Map) indexresult.get(i);
					HoldTypeInfoBean bean = new HoldTypeInfoBean();
					java.math.BigDecimal countstring =(java.math.BigDecimal)map.get("COUNT");
//					bean.setCount(countstring==null?"0":countstring.toString());
					String appnamestring =(String)map.get("C_NAME");
					String holdtype =(String)map.get("C_ID");   //长按类型
					bean.setCount(countstring==null?"0":countstring.toString());
					bean.setAppname(appnamestring.toString());
//					cal.setTime(sdf.parse(startyear + "-" + startmonth));
//					calE.setTime(sdf.parse(endyear + "-" + endmonth));
//					calE.add(Calendar.MONTH, 1);
					
					List indexList = new ArrayList();
					//while (!cal.equals(calE)) {
//						String date11 = sdf.format(cal.getTime());
//						cal.add(Calendar.MONTH, 1);
//						String date12 = sdf.format(cal.getTime());
						
//						Map  maplistinfo=new HashMap();
//						maplistinfo.put("holdtype", holdtype);
//						maplistinfo.put("date11", date11);
//						maplistinfo.put("date12", date12);
//						List indexresult1 = this.getSqlMapClientTemplate().queryForList("analytics.HoldListInfo",maplistinfo);   //查询
						
						HoldTypeInfoBean indexbean = null;
							
						String sql="select holdinfo.count,info.c_name,holdinfo.actiondate from ( SELECT count(t.c_holdtype) as count,t.c_holdtype,to_char(t.c_actiondate,'YYYY-MM') as actiondate FROM T_SET_HOLD_HIS t " +
								" where  t.c_key=1 and t.c_newest=1 and t.c_holdtype=? and t.c_actiondate>=TO_DATE(?,'YYYY-MM') and t.c_actiondate<TO_DATE(?,'YYYY-MM')" +
								" group by t.c_holdtype,to_char(t.c_actiondate,'YYYY-MM') order by count(t.c_holdtype) desc)  holdinfo , t_base_holdtype_info info where holdinfo.c_holdtype=info.c_id";
			            List agrs=new ArrayList();
			            agrs.add(holdtype);
			            agrs.add(date1);
			            agrs.add(date2);
			            /** 查询当前操作类型的每个月的数量 */
			            List indexresult1=jdbcTemplate.queryForList(sql, agrs.toArray());
						for(String day : nowDays){
							boolean ishas=false;
							for (int j = 0; j < indexresult1.size(); j++) {
								Map indexMap=(Map) indexresult1.get(j);
								if(indexMap.get("ACTIONDATE").equals(day)){
									indexbean = new HoldTypeInfoBean();
									ishas=true;
									String appnamestring1 =(String)indexMap.get("C_NAME");
									java.math.BigDecimal countstring1 =(java.math.BigDecimal)indexMap.get("COUNT");
									indexbean.setCount(countstring1==null?"0":countstring1.toString());
									indexbean.setAppname(appnamestring1);
									indexList.add(indexbean);
									break;
								}
							}
							if(!ishas){
								indexbean = new HoldTypeInfoBean();
								indexbean.setCount("0");
								indexbean.setAppname("");
								indexList.add(indexbean);
							}
						}
					//}
					bean.setListBean(indexList);
					result.add(bean);
				}
			}

			/*
			 * while(!cal.equals(calE)) { }
			 */
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(d1.getTime() - d2.getTime());
		return result;
	}
	

}
