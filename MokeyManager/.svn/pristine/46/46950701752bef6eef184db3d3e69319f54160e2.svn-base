package com.org.mokey.analyse.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.org.mokey.analyse.dao.IndustryBrandDao;
import com.org.mokey.analyse.service.IndustryBrandService;
import com.org.mokey.util.StrUtils;

public class IndustryBrandServiceImpl implements IndustryBrandService{
	
	protected  Logger log = (Logger.getLogger(getClass()));
	
	private IndustryBrandDao industryBrandDao;

	public IndustryBrandDao getIndustryBrandDao() {
		return industryBrandDao;
	}

	public void setIndustryBrandDao(IndustryBrandDao industryBrandDao) {
		this.industryBrandDao = industryBrandDao;
	}

	@Override
	// 获取行业品牌占比情况
	public List<Map<String,Object>> getIndustryBrandList() {
		try {
			// 查询所有行业的品牌总数
			Integer totalBrands = this.getTotalBrands();
			// 查询每个行业的品牌数
			List<Map<String,Object>> brandOfIndustry = industryBrandDao.brandOfIndustry();
			if(null!=brandOfIndustry && brandOfIndustry.size()>0){
				for (Map<String, Object> map : brandOfIndustry) {
					// 获取当前行业的品牌数
					BigDecimal count =   (BigDecimal) (StrUtils.isEmpty(map.get("CNT")) ? 0 : map.get("CNT"));
					// 计算占总品牌数的比例
					map.put("RATE", getPercent(count.intValue(),totalBrands));
				}
			}
			return brandOfIndustry;
		} catch (Exception e) {
			log.error("IndustryBrandServiceImpl.getIndustryBrandList failed, e : " + e);
		}
		return null;
	}

	// 获取百分比
	private String getPercent(Integer count, Integer totalBrands) {
		DecimalFormat df = new DecimalFormat("0.0%");    //##.0%   百分比格式，后面不足1位的用0补齐  
	    Double db = (count * 1.0) / totalBrands;// 转成double类型再计算
		return df.format(db);
	}

	@Override
	// 获取所有行业品牌总数
	public Integer getTotalBrands() {
		return industryBrandDao.getTotalBrands();
	}
	
	
	
	
	
	
	
	
	
	
}
