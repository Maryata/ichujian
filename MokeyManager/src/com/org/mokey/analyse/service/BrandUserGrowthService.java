package com.org.mokey.analyse.service;

import java.util.List;
import java.util.Map;

public interface BrandUserGrowthService {

	Map<String,Object> getUserGrowthList(String time_s, String time_e,String brand);

	Map<String, Object> getUserMonthGrowthList(String startdate, String enddate,String brand);

	List getBrand();
}
