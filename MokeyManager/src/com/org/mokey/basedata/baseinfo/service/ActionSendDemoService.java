package com.org.mokey.basedata.baseinfo.service;

import java.util.List;
import java.util.Map;

public interface ActionSendDemoService {

	Map<String, Object> getActiveListMap(String time_s, String time_e,
			int start, int limit, String imei,String code);
	
	List GetDeviceByImei(String imei); 
	
	List GetDeviceInfoByImei(String imei,String key);   //查询4个键的单击设置
	
	List GetOnekeyhold(String imei);   //一键长按
	
	List GetTwokeyInss(String imei);  //二键长按  显示已经安装的新闻 阅读app
	
	List GetThreekeyInss(String imei);  //三键单击  显示已经安装的游戏
	
	String GetUseKeyTotle(String imei);   //总数
	
	String GetRemark(String imei);    //备注
	
	List GetClickcount(String imei);  //按键使用次数            
	
	Map  getKeyClickCount(String imei);
	
	Map  getKeyClickCountByTime(String start ,String end,String imei);
	
	//查看历史点击记录
	
	Map   getHitHistory(String start ,String end,String imei,int start2,int limit1,String option );

	Map getHitHistory(String imei, int start, int limit);
	
	
}
