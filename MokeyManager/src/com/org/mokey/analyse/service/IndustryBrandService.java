package com.org.mokey.analyse.service;

import java.util.List;
import java.util.Map;

public interface IndustryBrandService {
	
	/**
	 * 获取行业品牌数量及占比情况
	 * @author Maryn
	 * @return 
	 */
	public List<Map<String,Object>> getIndustryBrandList();

	/**
	 * 获取所有行业品牌总数
	 * @return
	 */
	public Integer getTotalBrands();

}
