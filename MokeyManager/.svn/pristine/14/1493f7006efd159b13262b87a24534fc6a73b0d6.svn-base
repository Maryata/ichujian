package com.org.mokey.analyse.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.org.mokey.analyse.dao.BrandfUserGrowthDao;
import com.org.mokey.analyse.entiy.BrandUserGrowthBean;
import com.org.mokey.analyse.service.BrandUserGrowthService;
import com.org.mokey.common.util.ApDateTime;
import com.org.mokey.common.util.number.NumberUtil;

public class BrandUserGrowthServiceImpl implements BrandUserGrowthService {

	protected  Logger log = (Logger.getLogger(getClass()));
	
	private BrandfUserGrowthDao brandUserGrowthDao;

	@SuppressWarnings({ "rawtypes" })
	public Map getUserGrowthList(String time_s, String time_e,String brand) {
		Map<String,Object> ret = new HashMap<String,Object>();
		List<String> nowDays = ApDateTime.getDayBetween(time_s, time_e);
		List<String> lastDays = new ArrayList<String>();
		
		Map<String,BrandUserGrowthBean> nowList = new HashMap<String,BrandUserGrowthBean>();
		Map<String,BrandUserGrowthBean> lastList = new HashMap<String,BrandUserGrowthBean>();
		//初始化所有列表
		BrandUserGrowthBean item = null;
		for(String day : nowDays){
			String lastDay = ApDateTime.dateAdd("y", -1, day, ApDateTime.DATE_TIME_YMD);
			
			//循环品牌;
			
			item = new BrandUserGrowthBean();
			item.setDay(day);
			item.setLasyDay(lastDay);
			nowList.put(day, item);
			//-----
			lastDays.add(lastDay);
			item = new BrandUserGrowthBean();
			item.setDay(lastDay);
			lastList.put(lastDay, item);
		}
		
		this.getUserGrowthData( nowDays, nowList,brand);
		this.getUserGrowthData( lastDays, lastList,brand);
		
		ret.put("nowDays", nowDays);
		ret.put("nowList", nowList);
		ret.put("lastList", lastList);
		
		return ret;
	}
	
	private Map getUserGrowthData(List<String> nowDays,Map<String,BrandUserGrowthBean> dayList,String brand){
		String time_s = nowDays.get(0) ;
		String time_e = nowDays.get(nowDays.size()-1);
		
		BrandUserGrowthBean item = null;
		//库存总数
		//int activeCodeCount = brandUserGrowthDao.getActiveCodeCount(time_s, time_e);//------
		
		List<Map> startList =  brandUserGrowthDao.getStartDayList(time_s, time_e,brand);  //启动
		for(int i=0;i<startList.size();i++){
			item = dayList.get(startList.get(i).get("DAY"));
			item.setStartCn(Integer.valueOf((startList.get(i).get("COUNT")+"")));
		}
		
		int beforeActCount = brandUserGrowthDao.getActiveCountAtBeforeTime(ApDateTime.dateAdd("d", 1, time_e, ApDateTime.DATE_TIME_YMD),ApDateTime.DATE_TIME_YMD );
		
		List<Map> activeList = brandUserGrowthDao.getActiveDayList(time_s,time_e,brand);  //激活

		for(int i=0;i<activeList.size();i++){
			item = dayList.get(activeList.get(i).get("DAY"));
			item.setActiveCn(Integer.valueOf((activeList.get(i).get("COUNT")+"")));
			
			item.setActiveRate(NumberUtil.divRateToStr(item.getActiveCn(), item.getStartCn()));
		}
	
		int preActCount = beforeActCount;
		
		String totalKey = "合计";
		BrandUserGrowthBean total = new BrandUserGrowthBean();
		total.setDay(totalKey);
		total.setLasyDay(totalKey);
		for(String day : nowDays){
			item = dayList.get(day);
			//--
			//preActCount += item.getActiveCn();
			
//			if(activeCodeCount>= preActCount){
//				item.setInventoryCn(activeCodeCount - preActCount);
//			}
			total.setStartCn(total.getStartCn()+item.getStartCn());
			total.setActiveCn(total.getActiveCn()+item.getActiveCn());
		}
		//total.setInventoryCn(item.getInventoryCn());
		//total.setActiveRate(NumberUtil.divRateToStr(total.getActiveCn(), total.getStartCn()));
		dayList.put(totalKey, total);
		
		nowDays.add(totalKey);
		
		return dayList;
	}
	


	public BrandfUserGrowthDao getBrandUserGrowthDao() {
		return brandUserGrowthDao;
	}

	public void setBrandUserGrowthDao(BrandfUserGrowthDao brandUserGrowthDao) {
		this.brandUserGrowthDao = brandUserGrowthDao;
	}

	@Override
	public Map<String, Object> getUserMonthGrowthList(String startdate,
			String enddate,String brand) {
		Map<String,Object> ret = new HashMap<String,Object>();
		List<String> nowDays = ApDateTime.getMonthBetween(startdate, enddate);
		List<String> lastDays = new ArrayList<String>();
		
		Map<String,BrandUserGrowthBean> nowList = new HashMap<String,BrandUserGrowthBean>();
		Map<String,BrandUserGrowthBean> lastList = new HashMap<String,BrandUserGrowthBean>();
		//初始化所有列表
		BrandUserGrowthBean item = null;
		for(String day : nowDays){
			String lastDay = ApDateTime.dateAdd("y", -1, day, ApDateTime.DATE_TIME_YM);
			item = new BrandUserGrowthBean();
			item.setDay(day);
			item.setLasyDay(lastDay);
			nowList.put(day, item);
			//-----
			lastDays.add(lastDay);
			item = new BrandUserGrowthBean();
			item.setDay(lastDay);
			lastList.put(lastDay, item);
		}
		
		this.getUserMonthGrowthData( nowDays, nowList,brand);
		this.getUserMonthGrowthData( lastDays, lastList,brand);
		
		ret.put("nowDays", nowDays);
		ret.put("nowList", nowList);
		ret.put("lastList", lastList);
		
		return ret;
	}
	
	private Map getUserMonthGrowthData(List<String> nowDays,Map<String,BrandUserGrowthBean> dayList,String brand){
		String time_s = nowDays.get(0) ;
		String time_e = nowDays.get(nowDays.size()-1);
		
		BrandUserGrowthBean item = null;
		//库存总数
		//int activeCodeCount = brandUserGrowthDao.getActiveCodeMonthCount(time_s, time_e);
		//log.debug("activeCodeCount:"+activeCodeCount);
		List<Map> startList =  brandUserGrowthDao.getStartMonthList(time_s, time_e,brand);
		for(int i=0;i<startList.size();i++){
			item = dayList.get(startList.get(i).get("DAY"));
			item.setStartCn(Integer.valueOf((startList.get(i).get("COUNT")+"")));
		}
		//active code count at before betwen times;
		int beforeActCount = brandUserGrowthDao.getActiveCountAtBeforeTime(ApDateTime.dateAdd("d", 1, time_e, ApDateTime.DATE_TIME_YM),ApDateTime.DATE_TIME_YM );
		//
		List<Map> activeList = brandUserGrowthDao.getActiveMonthList(time_s,time_e,brand);

		for(int i=0;i<activeList.size();i++){
			item = dayList.get(activeList.get(i).get("DAY"));
			item.setActiveCn(Integer.valueOf((activeList.get(i).get("COUNT")+"")));
			
			item.setActiveRate(NumberUtil.divRateToStr(item.getActiveCn(), item.getStartCn()));
		}
		//set ku cun shu
		int preActCount = beforeActCount;
		
		String totalKey = "合计";
		BrandUserGrowthBean total = new BrandUserGrowthBean();
		total.setDay(totalKey);
		total.setLasyDay(totalKey);
		for(String day : nowDays){
			item = dayList.get(day);
			//--
//			preActCount += item.getActiveCn();
//			
//			if(activeCodeCount>= preActCount){
//				item.setInventoryCn(activeCodeCount - preActCount);
//			}
			total.setStartCn(total.getStartCn()+item.getStartCn());
			total.setActiveCn(total.getActiveCn()+item.getActiveCn());
		}
		//total.setInventoryCn(item.getInventoryCn());
		//total.setActiveRate(NumberUtil.divRateToStr(total.getActiveCn(), total.getStartCn()));
		dayList.put(totalKey, total);
		
		nowDays.add(totalKey);
		
		return dayList;
	}

	@Override
	public List getBrand() {
		// TODO Auto-generated method stub
		List list=brandUserGrowthDao.getBrand();
		return list;
	}

}
