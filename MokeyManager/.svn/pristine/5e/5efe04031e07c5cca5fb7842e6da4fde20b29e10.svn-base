package com.org.mokey.analyse.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.org.mokey.analyse.dao.UserGrowthDao;
import com.org.mokey.analyse.dao.UserUseInfoDao;
import com.org.mokey.analyse.entiy.UserGrowthBean;
import com.org.mokey.analyse.service.UserUseInfoService;
import com.org.mokey.common.util.ApDateTime;
import com.org.mokey.common.util.number.NumberUtil;

public class UserUseInfoServiceImpl implements UserUseInfoService {
	protected  Logger log = (Logger.getLogger(getClass()));
	private UserUseInfoDao userUseInfoDao;
	private UserGrowthDao userGrowthDao;
	
	public UserUseInfoDao getUserUseInfoDao() {
		return userUseInfoDao;
	}
	public void setUserUseInfoDao(UserUseInfoDao userUseInfoDao) {
		this.userUseInfoDao = userUseInfoDao;
	}
	
	public UserGrowthDao getUserGrowthDao() {
		return userGrowthDao;
	}
	public void setUserGrowthDao(UserGrowthDao userGrowthDao) {
		this.userGrowthDao = userGrowthDao;
	}
	
	@Override
	public Map<String, Object> getUserUseByDay(String startdate, String enddate) {
		Map<String,Object> ret = new HashMap<String,Object>();
		
		List<String> nowDays = ApDateTime.getDayBetween(startdate, enddate);
		startdate = nowDays.get(0);
		enddate = nowDays.get(nowDays.size()-1);
		
		
		List<Map> listTemp = userUseInfoDao.getUserUseByDayList(startdate,enddate);
		
		Map<String,UserGrowthBean> nowList = new HashMap<String,UserGrowthBean>();
		UserGrowthBean item = null;
		for(String day : nowDays){
			item = new UserGrowthBean();
			item.setDay(day);
			nowList.put(day, item);
		}
		
		//active code count at before betwen times;
		int beforeActCount = userGrowthDao.getActiveCountAtBeforeTime(startdate,ApDateTime.DATE_TIME_YMD, null);
		//
		List<Map> activeList = (List<Map>) userGrowthDao.getActiveDayList(startdate,enddate,null);
		for(Map m : listTemp){
			item = nowList.get(m.get("DAY"));
			item.setStartCn(Integer.valueOf(m.get("COUNT")+""));
		}
		for(Map m : activeList){
			item = nowList.get(m.get("DAY"));
			item.setActiveCn(Integer.valueOf(m.get("COUNT")+""));
		}
		
		long userCn = 0;
		double actCount = 0;
		int useCount = 0;
		for(String day : nowDays){
			item = nowList.get(day);
			
			useCount += item.getStartCn();
			beforeActCount += item.getActiveCn();
			//log.debug(day+" - "+item.getActiveCn()+" - "+beforeActCount);
			userCn += beforeActCount;
			item.setActiveCn(beforeActCount);
			item.setActiveRate( NumberUtil.divToStr(item.getStartCn() , beforeActCount , 1));
			
			actCount += Double.valueOf(item.getActiveRate());
		}
		
		UserGrowthBean total = new UserGrowthBean();
		total.setStartCn(useCount/nowDays.size());
		total.setActiveCn((int) (userCn/nowDays.size()));
		total.setActiveRate( NumberUtil.rate(actCount/nowDays.size(),1) );
		//NumberUtil.divToStr((total.getStartCn()/beforeActCount),nowDays.size(),1)
		//NumberUtil.divToStr(total.getStartCn() , beforeActCount , 1)
		
		nowList.put("平均", total);
		nowDays.add("平均");
		
		ret.put("nowDays", nowDays);
		ret.put("nowList", nowList);
		return ret;
	}
	@Override
	public Map<String, Object> getUserUseByMonth(String startdate,
			String enddate) {
		Map<String,Object> ret = new HashMap<String,Object>();
		
		List<String> nowDays = ApDateTime.getMonthBetween(startdate, enddate);
		startdate = nowDays.get(0);
		enddate = nowDays.get(nowDays.size()-1);
		
		
		List<Map> listTemp = userUseInfoDao.getUserUseByMonthList(startdate,enddate);
		
		Map<String,UserGrowthBean> nowList = new HashMap<String,UserGrowthBean>();
		UserGrowthBean item = null;
		for(String day : nowDays){
			item = new UserGrowthBean();
			item.setDay(day);
			nowList.put(day, item);
		}
		
		//active code count at before betwen times;
		int beforeActCount = userGrowthDao.getActiveCountAtBeforeTime(startdate , ApDateTime.DATE_TIME_YM,null);
		//
		List<Map> activeList = (List<Map>) userGrowthDao.getActiveMonthList(startdate,enddate,null);
		for(Map m : listTemp){
			item = nowList.get(m.get("DAY"));
			item.setStartCn(Integer.valueOf(m.get("COUNT")+""));
		}
		for(Map m : activeList){
			item = nowList.get(m.get("DAY"));
			item.setActiveCn(Integer.valueOf(m.get("COUNT")+""));
		}
		
		long userCn = 0;
		double actCount = 0;
		int useCount = 0;
		for(String day : nowDays){
			item = nowList.get(day);
			
			useCount += item.getStartCn();
			beforeActCount += item.getActiveCn();
			//log.debug(day+" - "+item.getActiveCn()+" - "+beforeActCount);
			userCn += beforeActCount;
			item.setActiveCn(beforeActCount);
			item.setActiveRate( NumberUtil.divToStr(item.getStartCn() , beforeActCount , 1));
			
			actCount += Double.valueOf(item.getActiveRate());
		}
		
		UserGrowthBean total = new UserGrowthBean();
		total.setStartCn(useCount/nowDays.size());
		total.setActiveCn((int) (userCn/nowDays.size()));
		total.setActiveRate( NumberUtil.rate(actCount/nowDays.size(),1) );
		//NumberUtil.divToStr((total.getStartCn()/beforeActCount),nowDays.size(),1)
		//NumberUtil.divToStr(total.getStartCn() , beforeActCount , 1)
		
		nowList.put("平均", total);
		nowDays.add("平均");
		
		ret.put("nowDays", nowDays);
		ret.put("nowList", nowList);
		return ret;
	}
	
	

}
