package com.org.mokey.analyse.action;

import java.util.List;
import java.util.Map;

import com.org.mokey.analyse.service.IndustryBrandService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.highcharts.HighchartsUtil;
import com.org.mokey.util.StrUtils;

/**
 * 行业品牌数量及占比情况
 */
@SuppressWarnings("serial")
public class IndustryBrandAction extends AbstractAction {

	private IndustryBrandService industryBrandService;

	public IndustryBrandService getIndustryBrandService() {
		return industryBrandService;
	}

	public void setIndustryBrandService(IndustryBrandService industryBrandService) {
		this.industryBrandService = industryBrandService;
	} 
	
	// 获取行业品牌占比情况
	public String getIndustryBrandList(){
		log.info("into IndustryBrandAction.getIndustryBrandList");
		try{
			// 获取行业品牌占比
			List<Map<String,Object>> list = industryBrandService.getIndustryBrandList();
			// 存入值栈
			getRequest().setAttribute("industryBrandList", list);
			String chartData = "";
			if(null!=list && list.size()>0){
				String[] industries = new String[list.size()];// 行业名称数组
				Long[] counts = new Long[list.size()];// 各行业品牌数量数组
				for (int i = 0; i < list.size(); i++) {
					Object industry = list.get(i).get("C_NAME");
					Object count = list.get(i).get("CNT");
					industries[i] = StrUtils.isEmpty(industry) ? " " : industry.toString();
					counts[i] =  StrUtils.isEmpty(count) ? 0 : Long.valueOf(count.toString());
				}
				chartData = HighchartsUtil.getPie3d("各行业品牌占比情况", industries, counts);
			}
			getRequest().setAttribute("chartData", chartData);
		}catch(Exception e){
			log.error("IndustryBrandAction.getIndustryBrandList failed,",e);
		}
		return "success";
	}

}
