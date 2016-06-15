package com.org.mokey.basedata.baseinfo.service;

import java.util.List;
import java.util.Map;

public interface ActionActiveService {

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
	
}
