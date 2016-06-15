package com.org.mokey.analyse.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.org.mokey.analyse.dao.UserGrowthDao;
import com.org.mokey.analyse.entiy.UserGrowthBean;
import com.org.mokey.analyse.service.UserGrowthService;
import com.org.mokey.common.util.ApDateTime;
import com.org.mokey.common.util.number.NumberUtil;

public class UserGrowthServiceImpl implements UserGrowthService {

	protected  Logger log = (Logger.getLogger(getClass()));
	
	private UserGrowthDao userGrowthDao;

	@SuppressWarnings({ "rawtypes" })
	public Map getUserGrowthList(String time_s, String time_e,String supplierCode) {
		Map<String,Object> ret = new HashMap<String,Object>();
		List<String> nowDays = ApDateTime.getDayBetween(time_s, time_e);
		List<String> lastDays = new ArrayList<String>();
		
		Map<String,UserGrowthBean> nowList = new HashMap<String,UserGrowthBean>();
		Map<String,UserGrowthBean> lastList = new HashMap<String,UserGrowthBean>();
		//初始化所有列表
		UserGrowthBean item = null;
		for(String day : nowDays){
			String lastDay = ApDateTime.dateAdd("y", -1, day, ApDateTime.DATE_TIME_YMD);
			item = new UserGrowthBean();
			item.setDay(day);
			item.setLasyDay(lastDay);
			nowList.put(day, item);
			//-----
			lastDays.add(lastDay);
			item = new UserGrowthBean();
			item.setDay(lastDay);
			lastList.put(lastDay, item);
		}
		
		this.getUserGrowthData( nowDays, nowList,supplierCode);
		this.getUserGrowthData( lastDays, lastList,supplierCode);
		
		ret.put("nowDays", nowDays);
		ret.put("nowList", nowList);
		ret.put("lastList", lastList);
		
		return ret;
	}
	
	private Map getUserGrowthData(List<String> nowDays,Map<String,UserGrowthBean> dayList,String supplierCode){
		String time_s = nowDays.get(0) ;
		String time_e = nowDays.get(nowDays.size()-1);
		
		UserGrowthBean item = null;
		//库存总数
		int activeCodeCount = userGrowthDao.getActiveCodeCount(time_e,supplierCode);
		//log.debug("activeCodeCount:"+activeCodeCount);
		// 指定时间段内每天的启动数量
		List<Map> startList =  (List<Map>) userGrowthDao.getStartDayList(time_s, time_e,supplierCode);
		/** 1. 每天的数据*/
		for(int i=0;i<startList.size();i++){
			item = dayList.get(startList.get(i).get("DAY"));
			/** 1.1 设置启动数量 */
			item.setStartCn(Integer.valueOf((startList.get(i).get("COUNT")+"")));
		}
		//active code count at before betwen times;
		// 指定时间段之前激活的数量
		int beforeActCount = userGrowthDao.getActiveCountAtBeforeTime(ApDateTime.dateAdd("d", 1, time_s, ApDateTime.DATE_TIME_YMD),ApDateTime.DATE_TIME_YMD,supplierCode );
		// 指定时间段内每天激活数量
		List<Map> activeList = (List<Map>) userGrowthDao.getActiveDayList(time_s,time_e,supplierCode);
		for(int i=0;i<activeList.size();i++){
			item = dayList.get(activeList.get(i).get("DAY"));
			/** 1.2 设置激活数量*/
			item.setActiveCn(Integer.valueOf((activeList.get(i).get("COUNT")+"")));
			/** 1.3 设置激活率 */
			item.setActiveRate(NumberUtil.divRateToStr(item.getActiveCn(), item.getStartCn()));
		}
		/** 2.合计 */
		//set ku cun shu
		int preActCount = beforeActCount;// 指定时间段之前的激活数量
		
		String totalKey = "合计";
		UserGrowthBean total = new UserGrowthBean();
		/** 2.1 设置日期 */
		total.setDay(totalKey);
		/** 2.2 设置去年同期 */
		total.setLasyDay(totalKey);
		
		for(String day : nowDays){
			item = dayList.get(day);
			/** 指定时间段内每天的激活数量累加到preActCount上 */
			preActCount += item.getActiveCn();
			
			if(activeCodeCount>= preActCount){
				/** 1.4 设置每天的剩余库存 */
				item.setInventoryCn(activeCodeCount - preActCount);
			}
			/** 2.3 设置启动数量 */
			total.setStartCn(total.getStartCn()+item.getStartCn());
			/** 2.4 设置激活数量 */
			total.setActiveCn(total.getActiveCn()+item.getActiveCn());
		}
		/** 2.5 设置库存数量 */
		total.setInventoryCn(item.getInventoryCn());
		/** 2.6 设置激活率 */
		total.setActiveRate(NumberUtil.divRateToStr(total.getActiveCn(), total.getStartCn()));
		// 将合计对应的对象存入集合
		dayList.put(totalKey, total);
		nowDays.add(totalKey);
		
		return dayList;
	}
	
	public UserGrowthDao getUserGrowthDao() {
		return userGrowthDao;
	}
	public void setUserGrowthDao(UserGrowthDao userGrowthDao) {
		this.userGrowthDao = userGrowthDao;
	}

	@Override
	public Map<String, Object> getUserMonthGrowthList(String startdate,
			String enddate,String supplierCode) {
		Map<String,Object> ret = new HashMap<String,Object>();
		List<String> nowDays = ApDateTime.getMonthBetween(startdate, enddate);
		List<String> lastDays = new ArrayList<String>();
		
		Map<String,UserGrowthBean> nowList = new HashMap<String,UserGrowthBean>();
		Map<String,UserGrowthBean> lastList = new HashMap<String,UserGrowthBean>();
		//初始化所有列表
		UserGrowthBean item = null;
		for(String day : nowDays){
			String lastDay = ApDateTime.dateAdd("y", -1, day, ApDateTime.DATE_TIME_YM);
			item = new UserGrowthBean();
			item.setDay(day);
			item.setLasyDay(lastDay);
			nowList.put(day, item);
			//-----
			lastDays.add(lastDay);
			item = new UserGrowthBean();
			item.setDay(lastDay);
			lastList.put(lastDay, item);
		}
		
		this.getUserMonthGrowthData( nowDays, nowList,supplierCode);
		this.getUserMonthGrowthData( lastDays, lastList,supplierCode);
		
		ret.put("nowDays", nowDays);
		ret.put("nowList", nowList);
		ret.put("lastList", lastList);
		
		return ret;
	}
	
	private Map getUserMonthGrowthData(List<String> nowDays,Map<String,UserGrowthBean> dayList,String supplierCode){
		String time_s = nowDays.get(0) ;
		String time_e = nowDays.get(nowDays.size()-1);
		
		UserGrowthBean item = null;
		//库存总数
		int activeCodeCount = userGrowthDao.getActiveCodeMonthCount(time_e, supplierCode);
		//log.debug("activeCodeCount:"+activeCodeCount);
		List<Map> startList =  (List<Map>) userGrowthDao.getStartMonthList(time_s, time_e,supplierCode);
		for(int i=0;i<startList.size();i++){
			item = dayList.get(startList.get(i).get("DAY"));
			item.setStartCn(Integer.valueOf((startList.get(i).get("COUNT")+"")));
		}
		//active code count at before betwen times;
		int beforeActCount = userGrowthDao.getActiveCountAtBeforeTime(ApDateTime.dateAdd("d", 1, time_s, ApDateTime.DATE_TIME_YM),ApDateTime.DATE_TIME_YM,supplierCode );
		//
		List<Map> activeList = (List<Map>) userGrowthDao.getActiveMonthList(time_s,time_e,supplierCode);

		for(int i=0;i<activeList.size();i++){
			item = dayList.get(activeList.get(i).get("DAY"));
			item.setActiveCn(Integer.valueOf((activeList.get(i).get("COUNT")+"")));
			
			item.setActiveRate(NumberUtil.divRateToStr(item.getActiveCn(), item.getStartCn()));
		}
		//set ku cun shu
		int preActCount = beforeActCount;
		
		String totalKey = "合计";
		UserGrowthBean total = new UserGrowthBean();
		total.setDay(totalKey);
		total.setLasyDay(totalKey);
		for(String day : nowDays){
			item = dayList.get(day);
			//--
			preActCount += item.getActiveCn();
			
			if(activeCodeCount>= preActCount){
				item.setInventoryCn(activeCodeCount - preActCount);
			}
			total.setStartCn(total.getStartCn()+item.getStartCn());
			total.setActiveCn(total.getActiveCn()+item.getActiveCn());
		}
		total.setInventoryCn(item.getInventoryCn());
		total.setActiveRate(NumberUtil.divRateToStr(total.getActiveCn(), total.getStartCn()));
		dayList.put(totalKey, total);
		
		nowDays.add(totalKey);
		
		return dayList;
	}

}
