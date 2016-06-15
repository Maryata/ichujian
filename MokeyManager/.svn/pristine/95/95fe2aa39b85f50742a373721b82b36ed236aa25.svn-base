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
import com.org.mokey.analyse.service.NewsAppUseInfoService;
import com.org.mokey.analyse.entiy.AppUseBean;
import com.org.mokey.analyse.entiy.NewsAppInfoBean;
import com.org.mokey.common.util.ApDateTime;

public class NewsAppUseInfoServiceImpl  extends SqlMapClientDaoSupport implements NewsAppUseInfoService{
	
	/** Logger available to subclasses */
	private Logger log = (Logger.getLogger(NewsAppUseInfoServiceImpl.class));
	private JdbcTemplate jdbcTemplate;	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	@Override
	public List queryNewsAppUseInfo(String startyear ,String startmonth ,String endyear,String endmonth,String topnumber){
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
			//String startdate = nowDays.get(0);
			//String enddate = nowDays.get(nowDays.size()-1);
			
			String date1 = sdf.format(cal.getTime());
			String date2 = sdf.format(calE.getTime());
				
//			Map  maplist=new HashMap();
//			maplist.put("date1", date1);
//			maplist.put("date2", date2);
//			maplist.put("topnumber", topnumber);
//			
//			indexresult = this.getSqlMapClientTemplate().queryForList("analytics.NewsList",maplist);  //查询
			
			String sqlString="select * from ( SELECT count(t.c_app_name) as count,t.c_app_name FROM T_ACTION_USEAPP t where t.c_key=2 and t.c_type=0 and t.c_actiondate>=TO_DATE(?,'YYYY-MM') and t.c_actiondate<TO_DATE(?,'YYYY-MM') group by t.c_app_name order by count(t.c_app_name) desc) where ROWNUM<=?";
            List agrsList=new ArrayList();
            agrsList.add(date1);
            agrsList.add(date2);
            agrsList.add(topnumber);
            indexresult=jdbcTemplate.queryForList(sqlString, agrsList.toArray());
			
			
			if (indexresult != null) {
				for (int i = 0; i < indexresult.size(); i++) {
					Map map = (Map) indexresult.get(i);
					NewsAppInfoBean bean = new NewsAppInfoBean();
					java.math.BigDecimal countstring =(java.math.BigDecimal)map.get("COUNT");
					bean.setCount(countstring.toString());
					String appnamestring =(String)map.get("C_APP_NAME");
					bean.setCount(countstring.toString());
					bean.setAppname(appnamestring.toString());
					cal.setTime(sdf.parse(startyear + "-" + startmonth));
					calE.setTime(sdf.parse(endyear + "-" + endmonth));
					calE.add(Calendar.MONTH, 1);
					
					List indexList = new ArrayList();
					//while (!cal.equals(calE)) {
						//String date11 = sdf.format(cal.getTime());
						//cal.add(Calendar.MONTH, 1);
						//String date12 = sdf.format(cal.getTime());
						
//						Map  maplistinfo=new HashMap();
//						maplistinfo.put("appname", appnamestring);
//						maplistinfo.put("date11", date11);
//						maplistinfo.put("date12", date12);
//						List indexresult1 = this.getSqlMapClientTemplate().queryForList("analytics.NewsListInfo",maplistinfo);   //查询
						
						NewsAppInfoBean indexbean = new NewsAppInfoBean();
						
						String sql="SELECT count(t.c_app_name) as count,t.c_app_name,TO_CHAR(t.c_actiondate,'YYYY-MM') as actiondate FROM T_ACTION_USEAPP t where t.c_key=2 " +
								" and t.c_type=0 and t.c_app_name=? and t.c_actiondate>=TO_DATE(?,'YYYY-MM') and " +
								" t.c_actiondate<TO_DATE(?,'YYYY-MM') group by t.c_app_name,TO_CHAR(t.c_actiondate,'YYYY-MM') order by count(t.c_app_name) desc";
						List agrs=new ArrayList();
			            agrs.add(appnamestring);
			            agrs.add(date1);
			            agrs.add(date2);
			            List indexresult1=jdbcTemplate.queryForList(sql, agrs.toArray());
						
						
			            for(String day :nowDays){
			            	boolean isHas = false;
		            		for (int j2 = 0; j2 < indexresult1.size(); j2++) {
								Map indexMap=(Map) indexresult1.get(j2);
                                if(indexMap.get("ACTIONDATE").toString().equals(day)){
                                	indexbean = new NewsAppInfoBean();
                                	isHas = true;
                                	//log.info("appnamestring:"+appnamestring+" ,day:"+day +",cn: "+ indexMap.get("COUNT"));
									String appnamestring1 =(String)indexMap.get("C_APP_NAME");
									java.math.BigDecimal countstring1 =(java.math.BigDecimal)indexMap.get("COUNT");
									indexbean.setCount(countstring1.toString());
									indexbean.setAppname(appnamestring1);
									indexList.add(indexbean);
									break;
		            			}
                                //log.info(indexbean.getCount()+"--------------------------");
							}
		            		if(!isHas){
		            			indexbean = new NewsAppInfoBean();
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
