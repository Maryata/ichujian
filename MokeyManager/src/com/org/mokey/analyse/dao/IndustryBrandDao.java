package com.org.mokey.analyse.dao;

import java.util.List;
import java.util.Map;

public interface IndustryBrandDao {

	/**
	 * 查询所有行业的品牌总数
	 * @author Maryn
	 * @return 所有行业的品牌总数
	 */
	public Integer getTotalBrands();

	/**
	 * 查询每个行业的品牌数
	 * @author Maryn
	 * @return
	 */
	public List<Map<String, Object>> brandOfIndustry();

}
