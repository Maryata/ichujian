package com.org.mokey.analyse.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.org.mokey.analyse.dao.AKeyControlDao;
import com.org.mokey.analyse.entiy.AKeyControlBean;
import com.org.mokey.analyse.entiy.KeyUsageStatBean;
import com.org.mokey.analyse.service.AKeyControlService;
import com.org.mokey.common.util.ApDateTime;
import com.org.mokey.common.util.number.NumberUtil;

public class AKeyControlServiceImpl implements AKeyControlService {

	protected  Logger log = (Logger.getLogger(getClass()));
	private AKeyControlDao aKeyControlDao;
	
	public AKeyControlDao getaKeyControlDao() {
		return aKeyControlDao;
	}
	public void setaKeyControlDao(AKeyControlDao aKeyControlDao) {
		this.aKeyControlDao = aKeyControlDao;
	}
	
	@Override
	public Map<String, Object> getUseKeyList(Map<String,String> keyMaps,String startdate, String enddate) {
		Map<String,Object> ret = new HashMap<String,Object>();
		List<String> nowDays = ApDateTime.getMonthBetween(startdate, enddate);
		//query data temp;
		/** 查询指定时间段每种操作类型（0一键启动 1一键切换 2一键常用）的数量 */
		List<AKeyControlBean> keyDataTemp = aKeyControlDao.getUseKeyMonthList(nowDays.get(0),nowDays.get(nowDays.size()-1));
		
		Set<String> keySet = keyMaps.keySet(); //
		/** 所有类型   typeList=[0,1,2]*/
		List<String> typeList = new ArrayList<String>(keySet);
		AKeyControlBean item = null;
		int totalCount = 0;
		/** 所有月的总计数量   totalMaps={0=一键操作对象,1=一键操作对象,2=一键操作对象} */
		Map<String,AKeyControlBean> totalMaps = new HashMap<String,AKeyControlBean>();
		/** 1.1 向totalMaps中存{类型=对象}  设置对象属性：type、typeName */
		for(String type : typeList){
			item = new AKeyControlBean();
			item.setType(type);
			item.setTypeName(keyMaps.get(type));
			totalMaps.put(type, item);
		}
		/** 每个月每种类型的数量   keyDatas={日期={keyTemps},日期={keyTemps}} */
		Map<String,Object> keyDatas = new HashMap<String,Object>();
		for(String day : nowDays){
			//init
			/** keyTemps={0=一键操作对象,1=一键操作对象,2=一键操作对象} */
			Map<String,AKeyControlBean> keyTemps = new HashMap<String,AKeyControlBean>();
			/** 2.1 向keyTemps中存{类型=对象}  设置对象属性：type、typeName */
			for(String type : typeList){
				item = new AKeyControlBean();
				item.setType(type);
				item.setTypeName(keyMaps.get(type));
				keyTemps.put(type, item);
			}
			int dayCount = 0;
			/** 2.2 设置对象属性：startCn */
			for(AKeyControlBean keyBean : keyDataTemp ){
				if(!keyBean.getDay().equals(day)){
					continue;
				}
				item = keyTemps.get(keyBean.getType());
				if(item==null){
					continue;
				}
				item.setStartCn(keyBean.getStartCn());
				dayCount += keyBean.getStartCn();
			}
			//set rate;
			for(String type : typeList){
				/** 2.3 设置对象属性：startRate */
				item = keyTemps.get(type);
				item.setStartRate(NumberUtil.divRateToStr(item.getStartCn(), dayCount) );
				//log.debug(item.getStartCn() + "  - "+ type);
				//--set total;
				/** 1.2 设置totalMaps中的对象属性：startCn */
				AKeyControlBean item1 = totalMaps.get(type);
				item1.setStartCn(item1.getStartCn() + item.getStartCn() );
			}
			totalCount += dayCount;
			
			keyDatas.put(day, keyTemps);
		}
		
		for(String type : typeList){
			/** 1.3 设置totalMaps中的对象属性：startRate */
			item = totalMaps.get(type);
			item.setStartRate(NumberUtil.divRateToStr(item.getStartCn(), totalCount) );
		}
		
		ret.put("keyDatas", keyDatas);
		ret.put("totalMaps", totalMaps);
		ret.put("nowDays", nowDays);
		ret.put("typeList", typeList);
		
		return ret;
	}

	@Override
	public Map<String, Object> getKeyUsageStatList(String startdate, String enddate) {
		Map<String,Object> ret = new HashMap<String,Object>();
		/** {合计,年-月,年-月...} */
		List<String> nowDays = ApDateTime.getMonthBetween(startdate, enddate);
		//query data temp;
		/** 1.查询指定时间段内每个键（1/2/3/4）每种操作类型（0单击/1长按）的数量 */
		List<KeyUsageStatBean> tempData = aKeyControlDao.getKeyUsageStatMonthList(nowDays.get(0),nowDays.get(nowDays.size()-1));
		
		Map<String,Long> usageDatas = new HashMap<String,Long>();// {日期键位类型=数量,'合计'键位类型=数量}
		Map<String,Long> sumDatas = new HashMap<String,Long>();// {日期类型=数量,'合计'类型=数量}
		Map<String,Long> csumDatas = new HashMap<String,Long>();
		Map<String,Long> totalDatas = new HashMap<String,Long>();// 每个键的合计总数{键=数量}
		
		//按键集合
		String [] keys = new String []{"1","2","3","4"};
		String hj = "合计";
		String key =""; 
		
		long tatalCn = 0;
		for(KeyUsageStatBean bean : tempData){
			/** 累加计算合计数量 */
			tatalCn += bean.getStartCn();
			//--
			/** 键值对形式存放 {日期键位类型=数量}{2015-0410=3957} */
			usageDatas.put(bean.getDay()+""+bean.getKey()+bean.getType(), Long.valueOf(bean.getStartCn()));
			//计算合计
			key = bean.getDay()+"0";
			if("0".equals(bean.getType())){
				/** 键值对形式存放 {日期类型=数量}{2015-040=3957} */
				sumDatas.put(key, (sumDatas.get(key)==null ? 0 : sumDatas.get(key))+bean.getStartCn());
			}
			key = bean.getDay()+"1";
			if("1".equals(bean.getType())){
				/** {2015-041=3957} */
				sumDatas.put(key, (sumDatas.get(key)==null ? 0 : sumDatas.get(key))+bean.getStartCn());
			}
			//计算合计hj
			key = hj+"0";
			if("0".equals(bean.getType())){
				/** {合计0=3957} */
				sumDatas.put(key, (sumDatas.get(key)==null ? 0 : sumDatas.get(key))+bean.getStartCn());
			}
			key = hj+"1";
			if("1".equals(bean.getType())){
				/** {合计1=3957} */
				sumDatas.put(key, (sumDatas.get(key)==null ? 0 : sumDatas.get(key))+bean.getStartCn());
			}
			
			//计算总计
			for(int j=0;j<keys.length;j++){
				if(keys[j].equals(bean.getKey())){
					/** 每个键的数量 {键=数量}{1=3957} */
					totalDatas.put(keys[j], (totalDatas.get(keys[j])==null ? 0 : totalDatas.get(keys[j]))+bean.getStartCn());
					
					key = hj+keys[j]+"0";
					if("0".equals(bean.getType())){
						/** {'合计'键位类型=数量}{合计10=xxxx} */
						usageDatas.put(key, (usageDatas.get(key)==null ? 0 : usageDatas.get(key))+bean.getStartCn());
					}
					key = hj+keys[j]+"1";
					if("1".equals(bean.getType())){
						/** {合计11=xxxx} */
						usageDatas.put(key, (usageDatas.get(key)==null ? 0 : usageDatas.get(key))+bean.getStartCn());
					}
				}
			}
		}
		
		nowDays.add(hj);
		ret.put("nowDays", nowDays);
		ret.put("usageDatas", usageDatas);
		ret.put("sumDatas", sumDatas);
		ret.put("totalDatas", totalDatas);
		ret.put("tatalCn", tatalCn);
		//用户总数
		long DeviceCount = aKeyControlDao.getDeviceCount();
		ret.put("DeviceCount", DeviceCount);
		return ret;
	}
	
	

}
