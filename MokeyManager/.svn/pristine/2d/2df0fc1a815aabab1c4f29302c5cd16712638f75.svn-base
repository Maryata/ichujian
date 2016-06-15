package com.org.mokey.analyse.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.org.mokey.analyse.entiy.BrandUserGrowthBean;
import com.org.mokey.analyse.service.BrandUserGrowthService;
import com.org.mokey.analyse.service.UserGrowthService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.ApDateTime;
import com.org.mokey.common.util.highcharts.HighchartsUtil;
import com.org.mokey.common.util.zingchart.ChartUtils;
import com.org.mokey.util.StrUtils;

/**
 * 运营分析-日用户增长情况
 * @author lenovo
 *
 */
@SuppressWarnings("serial")
public class BrandUserGrowthAction extends AbstractAction {

	private BrandUserGrowthService brandUserGrowthService;
	
	public String getUserGrowthList() {
		HttpServletRequest request = getRequest();
		String startdate = getParameter("startdate");
		String enddate = getParameter("enddate");
		String brand = getParameter("brand");
		if(brand==null||"".equals(brand)){
			brand="0";
		}
		
		if(StrUtils.isEmpty(startdate)){
			startdate = ApDateTime.dateAdd("d", -7, new java.util.Date(), ApDateTime.DATE_TIME_YMD);
		}
		if(StrUtils.isEmpty(enddate)){
			enddate = ApDateTime.getNowDateTime(ApDateTime.DATE_TIME_YMD);
		}
		
		request.setAttribute("startdate", startdate);
		request.setAttribute("enddate", enddate);
		try{
			List brandList=brandUserGrowthService.getBrand();  //品牌数据
			String[] xtitle = {};
			String[][] data = {};
			
			List dataList=new ArrayList();
			Map<String,Object> userMap=new HashMap<String, Object>();
			
			String [] xtitle1 = {};
			if(brand.equals("0")){
				xtitle=new String[brandList.size()];
				List<String> nowDays = ApDateTime.getDayBetween(startdate, enddate);
				data=new String[brandList.size()][nowDays.size()];
				xtitle1= new String[nowDays.size()];
				for (int i = 0; i < brandList.size(); i++) {
					Map mapbrand=(Map) brandList.get(i);
					userMap=new HashMap<String, Object>();
					userMap = brandUserGrowthService.getUserGrowthList(startdate, enddate,mapbrand.get("C_ID").toString());	
					dataList.add(userMap);
					xtitle[i] = mapbrand.get("C_NAME").toString();
					
					Map<String,BrandUserGrowthBean> nowList = (Map<String, BrandUserGrowthBean>) userMap.get("nowList");
					BrandUserGrowthBean nowBean=null;

						int flag = 0;
						for(String nowDay:nowDays){
							nowBean = nowList.get(nowDay);
							if("合计".equals(nowDay)){
								continue;
							}
							xtitle1[flag]=nowDay;
							data[i][flag] = nowBean.getActiveCn()+"";
							flag ++;	
						}
						
				}
			}else{
				xtitle=new String[1];
				for(int i=0;i<brandList.size();i++){
					Map mapbrand=(Map) brandList.get(i);
					if(mapbrand.get("C_ID").toString().equals(brand)){
						xtitle[0] = mapbrand.get("C_NAME").toString();	
					}
				}
				userMap = brandUserGrowthService.getUserGrowthList(startdate, enddate,brand);	
				dataList.add(userMap);	
				
				List<String> nowDays = (List<String>) userMap.get("nowDays");
				Map<String,BrandUserGrowthBean> nowList = (Map<String, BrandUserGrowthBean>) userMap.get("nowList");
				BrandUserGrowthBean nowBean=null;
				data=new String[1][nowDays.size()-1];
				xtitle1=new String[nowDays.size()-1];
				int flag = 0;
				for(String nowDay:nowDays){
					nowBean = nowList.get(nowDay);
					if("合计".equals(nowDay)){
						continue;
					}
					xtitle1[flag]=nowDay;
					data[0][flag] = nowBean.getActiveCn()+"";
					flag ++;
				}
			}
			Map map=new HashMap();
			map.put("C_ID", "0");
			map.put("C_NAME", "全部");
			brandList.add(0,map);
			
			
			//转换chart数据;
			List<String> nowDays = (List<String>) userMap.get("nowDays");
			//Map<String,BrandUserGrowthBean> nowList = (Map<String, BrandUserGrowthBean>) userMap.get("nowList");
			//int max = nowDays.size();
			
//			String[] xtitle = new String[2] ;
//	    	xtitle[0]="安装情况";
//	    	xtitle[1]="激活情况";
	    	
			//String[][] data = new String[2][max-1];
//			BrandUserGrowthBean nowBean=null;
//			int flag = 0;
//			for(String nowDay:nowDays){
//				nowBean = nowList.get(nowDay);
//				if("合计".equals(nowDay)){
//					continue;
//				}
//				data[0][flag] = nowBean.getStartCn()+"";
//				data[1][flag] = nowBean.getActiveCn()+"";
//				flag ++;
//			}
	    	//查询数据
			//Long starttime = ApDateTime.getTime(nowDays.get(0), ApDateTime.DATE_TIME_YMD);
	    	//String chartData=ChartUtils.getDateTimeData(nowDays.get(0)+"到"+nowDays.get(nowDays.size()-2)+"日用户增长情况",xtitle,starttime,ChartUtils.TIME_DAY, data);
			String chartData = HighchartsUtil.getLine(nowDays.get(0)+"到"+nowDays.get(nowDays.size()-2)+"日用户增长情况", xtitle1, xtitle, data);
	    	request.setAttribute("chartData", chartData);
	    	
	    	request.setAttribute("brand", brand);
	    	request.setAttribute("brandList", brandList);
			request.setAttribute("nowDays", userMap.get("nowDays"));
			request.setAttribute("nowList", userMap.get("nowList"));
			request.setAttribute("lastList", userMap.get("lastList"));
			request.setAttribute("dataList", dataList);
		}catch(Exception e){
			log.error("getUserGrowthList failed,",e);
		}
		return "success";
	}
	
	public String getUserMonthGrowthList() {
		HttpServletRequest request = getRequest();
		String startdate = getParameter("startdate");
		String enddate = getParameter("enddate");
		String brand = getParameter("brand");
		if(brand==null||"".equals(brand)){
			brand="0";
		}
		
		if(StrUtils.isEmpty(startdate)){
			startdate = ApDateTime.getNowDateTime(ApDateTime.DATE_TIME_Y)+"-01";
		}
		if(StrUtils.isEmpty(enddate)){
			enddate = ApDateTime.getNowDateTime(ApDateTime.DATE_TIME_YM);
		}
		
		request.setAttribute("startdate", startdate);
		request.setAttribute("enddate", enddate);
		try{
			List brandList=brandUserGrowthService.getBrand();  //品牌数据
			String[] xtitle = {};
			String[][] data = {};
			String [] xtitle1 = {};
			List dataList=new ArrayList();
			Map<String,Object> userMap=new HashMap<String, Object>();
			if(brand.equals("0")){
				xtitle=new String[brandList.size()];
				List<String> nowDays = ApDateTime.getMonthBetween(startdate, enddate);
				data=new String[brandList.size()][nowDays.size()];
				xtitle1= new String[nowDays.size()];
				for (int i = 0; i < brandList.size(); i++) {
					Map mapbrand=(Map) brandList.get(i);
					userMap=new HashMap<String, Object>();
					userMap = brandUserGrowthService.getUserMonthGrowthList(startdate, enddate,mapbrand.get("C_ID").toString());	
					dataList.add(userMap);
					xtitle[i] = mapbrand.get("C_NAME").toString();
					
					Map<String,BrandUserGrowthBean> nowList = (Map<String, BrandUserGrowthBean>) userMap.get("nowList");
					BrandUserGrowthBean nowBean=null;

						int flag = 0;
						for(String nowDay:nowDays){
							nowBean = nowList.get(nowDay);
							if("合计".equals(nowDay)){
								continue;
							}
							xtitle1[flag]=nowDay;
							data[i][flag] = nowBean.getActiveCn()+"";
							flag ++;	
						}
				}	
			}else{
				xtitle=new String[1];
				for(int i=0;i<brandList.size();i++){
					Map mapbrand=(Map) brandList.get(i);
					if(mapbrand.get("C_ID").toString().equals(brand)){
						xtitle[0] = mapbrand.get("C_NAME").toString();	
					}
				}
				userMap = brandUserGrowthService.getUserMonthGrowthList(startdate, enddate,brand);	
				dataList.add(userMap);
				
				List<String> nowDays = (List<String>) userMap.get("nowDays");
				Map<String,BrandUserGrowthBean> nowList = (Map<String, BrandUserGrowthBean>) userMap.get("nowList");
				BrandUserGrowthBean nowBean=null;
				data=new String[1][nowDays.size()-1];
				xtitle1=  new String[nowDays.size()-1];
				int flag = 0;
				for(String nowDay:nowDays){
					nowBean = nowList.get(nowDay);
					if("合计".equals(nowDay)){
						continue;
					}
					xtitle[flag]=nowDay;
					data[0][flag] = nowBean.getActiveCn()+"";
					flag ++;
				}
			}

			Map map=new HashMap();
			map.put("C_ID", "0");
			map.put("C_NAME", "全部");
			brandList.add(0,map);
			
			//转换chart数据;
			List<String> nowDays = (List<String>) userMap.get("nowDays");
//			Map<String,BrandUserGrowthBean> nowList = (Map<String, BrandUserGrowthBean>) userMap.get("nowList");
//			int max = nowDays.size();
//			
//			String[] xtitle = new String[2] ;
//	    	xtitle[0]="安装情况";
//	    	xtitle[1]="激活情况";
//	    	
//			String[][] data = new String[2][max-1];
//			BrandUserGrowthBean nowBean=null;
//			int flag = 0;
//			for(String nowDay:nowDays){
//				nowBean = nowList.get(nowDay);
//				if("合计".equals(nowDay)){
//					continue;
//				}
//				data[0][flag] = nowBean.getStartCn()+"";
//				data[1][flag] = nowBean.getActiveCn()+"";
//				flag ++;
//			}
	    	//查询数据
			//Long starttime = ApDateTime.getTime(nowDays.get(0)+"-20", ApDateTime.DATE_TIME_YMD);
	    	//String chartData=ChartUtils.getDateTimeDataMonth(nowDays.get(0)+"到"+nowDays.get(nowDays.size()-2)+"月用户增长情况",xtitle,starttime,ChartUtils.TIME_MONTH, data);
			String chartData = HighchartsUtil.getLine(nowDays.get(0)+"到"+nowDays.get(nowDays.size()-2)+"日用户增长情况", xtitle1, xtitle, data);
	    	request.setAttribute("chartData", chartData);
	    	request.setAttribute("brand", brand);
	    	request.setAttribute("brandList", brandList);
			request.setAttribute("nowDays", userMap.get("nowDays"));
			request.setAttribute("nowList", userMap.get("nowList"));
			request.setAttribute("lastList", userMap.get("lastList"));
			request.setAttribute("dataList", dataList);
			//------
		}catch(Exception e){
			log.error("getUserMonthGrowthList failed,",e);
		}
		return "successMonth";
	}

	public BrandUserGrowthService getBrandUserGrowthService() {
		return brandUserGrowthService;
	}

	public void setBrandUserGrowthService(
			BrandUserGrowthService brandUserGrowthService) {
		this.brandUserGrowthService = brandUserGrowthService;
	}	
	

}
