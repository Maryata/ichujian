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
import com.org.mokey.analyse.service.SetAppInfoService;
import com.org.mokey.analyse.entiy.SetAppInfoBean;
import com.org.mokey.common.util.ApDateTime;

public class SetAppInfoServiceImpl  extends SqlMapClientDaoSupport implements SetAppInfoService{
	
	/** Logger available to subclasses */
	private Logger log = (Logger.getLogger(SetAppInfoServiceImpl.class));
	private JdbcTemplate jdbcTemplate;	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	@Override
	public List setList(String startyear ,String startmonth ,String endyear,String endmonth,String topnumber){
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
			
			String sqlString="select * from ( SELECT count(t.c_app_name) as count,t.c_app_name FROM t_Set_Click_His t where t.c_newest=1 and t.c_key=1 and t.c_actiondate>=TO_DATE(?,'YYYY-MM') and t.c_actiondate<TO_DATE(?,'YYYY-MM') group by t.c_app_name order by count(t.c_app_name) desc) where ROWNUM<=?";
            List agrsList=new ArrayList();
            agrsList.add(date1);
            agrsList.add(date2);
            agrsList.add(topnumber);
            indexresult=jdbcTemplate.queryForList(sqlString, agrsList.toArray());
			
			
			if (indexresult != null) {
				for (int i = 0; i < indexresult.size(); i++) {
					Map map = (Map) indexresult.get(i);
					SetAppInfoBean bean = new SetAppInfoBean();
					java.math.BigDecimal countstring =(java.math.BigDecimal)map.get("COUNT");
					bean.setCount(countstring.toString());
					String appnamestring =(String)map.get("C_APP_NAME");
					bean.setCount(countstring.toString());
					bean.setAppname(appnamestring.toString());
					cal.setTime(sdf.parse(startyear + "-" + startmonth));
					calE.setTime(sdf.parse(endyear + "-" + endmonth));
					calE.add(Calendar.MONTH, 1);
					
					List indexList = new ArrayList();
						SetAppInfoBean indexbean = new SetAppInfoBean();
						
						String sql="SELECT count(t.c_app_name) as count,t.c_app_name ,TO_CHAR(t.c_actiondate,'YYYY-MM') as actiondate FROM t_Set_Click_His t " +
								" where t.c_newest=1 and t.c_key=1 and t.c_app_name=? and t.c_actiondate>=TO_DATE(?,'YYYY-MM') and t.c_actiondate<TO_DATE(?,'YYYY-MM')" +
								" group by t.c_app_name,TO_CHAR(t.c_actiondate,'YYYY-MM')";
			            List agrs=new ArrayList();
			            agrs.add(appnamestring);
			            agrs.add(date1);
			            agrs.add(date2);
			            List indexresult1=jdbcTemplate.queryForList(sql, agrs.toArray());				
						for(String day:nowDays){
							boolean ishas=false;
							for (int j = 0; j < indexresult1.size(); j++) {
								Map indexMap=(Map) indexresult1.get(j);
								if(indexMap.get("ACTIONDATE").equals(day)){
									indexbean = new SetAppInfoBean();
									ishas=true;
									String appnamestring1 =(String)indexMap.get("C_APP_NAME");
									java.math.BigDecimal countstring1 =(java.math.BigDecimal)indexMap.get("COUNT");
									indexbean.setCount(countstring1.toString());
									indexbean.setAppname(appnamestring1);
									indexList.add(indexbean);
									break;
								}
							}
							if(!ishas){
								indexbean = new SetAppInfoBean();
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
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public int GetTotle(String startyear, String startmonth, String endyear,
			String endmonth) {
		// TODO Auto-generated method stub
		int totle=0;
		String sql="SELECT count(t.c_imei) FROM t_Set_Click_His t where t.c_newest=1 and t.c_key=1" +
				" and t.c_actiondate>=TO_DATE(?,'YYYY-MM') and t.c_actiondate<TO_DATE(?,'YYYY-MM') ";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Calendar cal = Calendar.getInstance();
        Calendar calE = Calendar.getInstance();
        
		try {
			cal.setTime(sdf.parse(startyear + "-" + startmonth));
			calE.setTime(sdf.parse(endyear + "-" + endmonth));
			calE.add(Calendar.MONTH, 1);
			String date1 = sdf.format(cal.getTime());
			String date2 = sdf.format(calE.getTime());
            List agrsList=new ArrayList();
            agrsList.add(date1);
            agrsList.add(date2);
            totle=jdbcTemplate.queryForInt(sql, agrsList.toArray());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return totle;
	}
	@Override
	public Map getManthTotle(String startyear, String startmonth,
			String endyear, String endmonth) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		List list = new ArrayList();
		String sql="SELECT count(TO_CHAR(t.c_actiondate,'YYYY-MM')) count,TO_CHAR(t.c_actiondate,'YYYY-MM') as actiondate FROM t_Set_Click_His t " +
				" where t.c_newest=1 and t.c_key=1 and t.c_actiondate>=TO_DATE(?,'YYYY-MM') and t.c_actiondate<TO_DATE(?,'YYYY-MM') " +
				" group by TO_CHAR(t.c_actiondate,'YYYY-MM')";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Calendar cal = Calendar.getInstance();
		Calendar calE = Calendar.getInstance();

		List<String> nowDays = ApDateTime.getMonthBetween(startyear + "-" + startmonth, endyear + "-" + endmonth);
		
		try {
			cal.setTime(sdf.parse(startyear + "-" + startmonth));
			calE.setTime(sdf.parse(endyear + "-" + endmonth));
			calE.add(Calendar.MONTH, 1);
			String date1 = sdf.format(cal.getTime());
			String date2 = sdf.format(calE.getTime());
		    List agrsList=new ArrayList();
		    agrsList.add(date1);
		    agrsList.add(date2);
		    list=jdbcTemplate.queryForList(sql, agrsList.toArray());
		    for(int j=0;j<nowDays.size();j++){
		    	boolean ishas=false;
		    	for(int i=0;i<list.size();i++){
		    		Map map2=(Map) list.get(i);
		    		if(map2.get("ACTIONDATE").toString().equals(nowDays.get(j).toString())){
			    		map.put(j+1, map2.get("COUNT").toString());
			    		ishas=true;
			    		break;
			    	}
		    	}
		    	if(!ishas){
		    		map.put(j+1, "0");
		    	}
		    }
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return map;
	}
}
