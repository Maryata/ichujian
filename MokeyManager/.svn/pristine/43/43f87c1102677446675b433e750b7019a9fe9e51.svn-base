package com.org.mokey.basedata.baseinfo.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.basedata.baseinfo.service.ActionBaseActivityInfoService;
import com.org.mokey.common.util.ApDateTime;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;

public class ActionBaseActivityInfoServiceImpl implements
		ActionBaseActivityInfoService {
	private JdbcTemplate jdbcTemplate;
	public static final String KEY_1 = "7d9fbeb43e975cd1e9477a7e5d5e192a";

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String saveData(InputStream is) throws IOException {
		StringBuffer  sb=new StringBuffer();
		XSSFWorkbook hssfWorkbook = new XSSFWorkbook(is);
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if (hssfSheet == null) {
				continue;
			}
			// 循环行Row
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) { 
				XSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow != null) {
					XSSFCell city = hssfRow.getCell(0);// 城市
					XSSFCell industy = hssfRow.getCell(1);// 服装
					XSSFCell nature = hssfRow.getCell(2);// 性别
					XSSFCell brand = hssfRow.getCell(3);// 广告
					XSSFCell seller = hssfRow.getCell(4);// 商家 16 ==ID
					XSSFCell displaier = hssfRow.getCell(5);// 发布者 9
					XSSFCell title = hssfRow.getCell(6);// 标题 //2
					XSSFCell content = hssfRow.getCell(7);// 内容 3
					XSSFCell address = hssfRow.getCell(8);// 地址 4
					// 5.6 根据地址解析
					XSSFCell joinUrl = hssfRow.getCell(9);// 参加URL 15 ---
					XSSFCell beginTime = hssfRow.getCell(10);// 开始时间 7
					XSSFCell endTime = hssfRow.getCell(11);// 结束时间 8
					XSSFCell activityUrl = hssfRow.getCell(12);// 活动URL 17
					XSSFCell lineOnOrOff = hssfRow.getCell(13);// 线上线下 18
					XSSFCell originUrl = hssfRow.getCell(14);// 来源URL 20 ---
					XSSFCell sponsor = hssfRow.getCell(15);// 19 主办方
					String citySql = "select T.C_ID from T_ACTIVITY_CITY_INFO t where  c_level =2 and c_cname like ? ";
					Object cityID = JdbcTemplateUtils.getMap(
							jdbcTemplate, citySql, "%" + city + "%")
							.get("C_ID");

					String industySql = "select T.C_ID from T_ACTIVITY_INDUSTRY_TYPE t where t.c_name like ?";
					Object industyID = JdbcTemplateUtils.getMap(
							jdbcTemplate, industySql, "%" + industy + "%").get(
							"C_ID");
					//	    	                   
					//	    	                   
					String natureSql = "select T.C_ID from T_ACTIVITY_PROPERTY_TYPE t where t.c_name like  ?";
					Object natureID = JdbcTemplateUtils.getMap(
							jdbcTemplate, natureSql, "%" + nature + "%").get(
							"C_ID");
					//	    	               
					String brandSql = "select T.C_ID  from T_ACTIVITY_BRAND_INFO t where t.c_Cname like  ?";
					Object brandID = JdbcTemplateUtils.getMap(
							jdbcTemplate, brandSql, "%" + brand + "%").get(
							"C_ID");

					//	    	                   
					String sellerSql = "select t.c_id from T_ACTIVITY_BUSINESS_INFO t where  t.c_name like ?";
					Object sellerID = JdbcTemplateUtils.getMap(
							jdbcTemplate, sellerSql, "%" + seller + "%").get(
							"C_ID");
					 
					
					if(StrUtils.isNotEmpty(cityID)&&StrUtils.isNotEmpty(industyID)&&StrUtils.isNotEmpty(brandID)&&StrUtils.isNotEmpty(natureID)&&StrUtils.isNotEmpty(sellerID)){
						Map item = new HashMap();
						String id = JdbcTemplateUtils.getSeqId(jdbcTemplate,
								"SEQ_ACTIVITY_BASE_INFO");
						Map<String, String> json = getGeocoderLatitude(address
								.toString());
						item.put("C_ID", id);
						item.put("C_TITLE", title.toString());
						item.put("C_CONTENT", content.toString());
						item.put("C_ADDRESS", address.toString());

						if (json != null) {
							item.put("C_LONGITUDE", json.get("lng"));
							item.put("C_LATITUDE", json.get("lat"));
						} else {
							item.put("C_LONGITUDE", "");
							item.put("C_LATITUDE", "");
						}
						item.put("C_PUBLISHETYPE", 0);
						SimpleDateFormat dt = new SimpleDateFormat(
								"yyyy/mm/dd HH:mm:ss");
						try {
							item.put("C_SDATE", dt.parse(beginTime.toString()));
							item.put("C_EDATE", dt.parse(endTime.toString()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
						item.put("C_PUBLISHER", displaier.toString());
						item.put("C_CITYID", cityID.toString());
						item.put("C_INDUSTRYID", industyID.toString());
						item.put("C_BRANDID", brandID.toString());
						item.put("C_PROPERTYID", natureID.toString());
						item.put("C_BUSINESSID", sellerID.toString());
						item.put("C_JOINURL", joinUrl.toString());
						item.put("C_IMAGEURL", activityUrl.toString());
						String number = lineOnOrOff.toString();
						if (number.equals("0.0") || number.equals("0")) {
							item.put("C_TYPE", 0);
						} else {
							item.put("C_TYPE", 1);
						}
						item.put("C_SPONSOR", sponsor.toString());
						item.put("C_SOURCESURL", originUrl.toString());
						JdbcTemplateUtils.saveDataByMap(jdbcTemplate, item,
						"T_ACTIVITY_BASE_INFO");
					}else{
						sb.append("第"+rowNum+"行,");
						if(StrUtils.isEmpty(cityID)){sb.append(" 城市,");}
						if(StrUtils.isEmpty(industyID)){sb.append("行业,");}
						if(StrUtils.isEmpty(brandID)){sb.append("品牌,");}
						if(StrUtils.isEmpty(natureID)){sb.append("性质,");}
						if(StrUtils.isEmpty(sellerID)){sb.append("商家,");}
					}
				}
			}
		}
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getIndusties() {
		Map map = new HashMap();
		map.put("list",this.jdbcTemplate.queryForList("select c_name ,C_ID from T_ACTIVITY_INDUSTRY_TYPE where C_ISLIVE=1 ORDER BY C_ORDER  "));
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getBrands() {
		Map map = new HashMap();
		map.put("list",this.jdbcTemplate.queryForList("SELECT C_CNAME,c_id FROM T_ACTIVITY_BRAND_INFO where c_id!=0 and C_ISLIVE=1 ORDER BY C_CNAME "));
		return map;
	}

	/**
	 * 返回输入地址的经纬度坐标 key lng(经度),lat(纬度)
	 */
	public static Map<String, String> getGeocoderLatitude(String address) {
		BufferedReader in = null;
		try {
			// 将地址转换成utf-8的16进制
			address = URLEncoder.encode(address, "UTF-8");
			// 如果有代理，要设置代理，没代理可注释
			// System.setProperty("http.proxyHost","192.168.1.188");
			// System.setProperty("http.proxyPort","3128");
			URL tirc = new URL("http://api.map.baidu.com/geocoder?address="
					+ address + "&output=json&key=" + KEY_1);

			in = new BufferedReader(new InputStreamReader(tirc.openStream(),
					"UTF-8"));
			String res;
			StringBuilder sb = new StringBuilder("");
			while ((res = in.readLine()) != null) {
				sb.append(res.trim());
			}
			String str = sb.toString();
			Map<String, String> map = null;
			if (StrUtils.isNotEmpty(str)) {
				int lngStart = str.indexOf("lng\":");
				int lngEnd = str.indexOf(",\"lat");
				int latEnd = str.indexOf("},\"precise");
				if (lngStart > 0 && lngEnd > 0 && latEnd > 0) {
					String lng = str.substring(lngStart + 5, lngEnd);
					String lat = str.substring(lngEnd + 7, latEnd);
					map = new HashMap<String, String>();
					map.put("lng", lng);
					map.put("lat", lat);
					return map;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@SuppressWarnings( { "unchecked", "deprecation" })
	@Override
	public Map getBaseActivityInfo(int start, int limit, String sTimeS,
			String sTimeE, String eTimeS, String eTimeE, String city,
			String industy, String property, String brand, String title, String islive) {
		List args = new ArrayList(); 
		StringBuffer countSql = new StringBuffer(
				"SELECT COUNT(C_ID) FROM T_ACTIVITY_BASE_INFO T WHERE 1=1");
		
		/** 2015-6-11 修改：增加查询条件c_islive（审核状态） begin */
		if (StrUtils.isNotEmpty(islive)) {
			countSql.append(" and t.c_islive=?");
			args.add(islive);
		}
		/** 2015-6-11 修改：增加查询条件c_islive（审核状态） end */
		
		if (StrUtils.isNotEmpty(city)) {
			countSql.append(" and t.c_cityid=?");
			args.add(city);
		}
		if (StrUtils.isNotEmpty(industy)) {
			countSql.append(" and t.c_industryid=?");
			args.add(industy);
		}
		if (StrUtils.isNotEmpty(property)) {
			countSql.append(" and T.C_PROPERTYID=?");
			args.add(property);
		}
		if (StrUtils.isNotEmpty(brand)) {
			countSql.append(" and T.C_BRANDID=?");
			args.add(brand);
		}
		if (StrUtils.isNotEmpty(title)) {
			countSql.append(" and t.c_title like ?");
			args.add("%" + title.trim() + "%");
		}
		if (StrUtils.isNotEmpty(sTimeS)) {
			countSql.append(" and t.c_sdate>=to_date(?,'yyyy-mm-dd') ");
			args.add(sTimeS);
		}
		if (StrUtils.isNotEmpty(sTimeE)) {
			countSql.append(" and t.c_sdate<to_date(?,'yyyy-mm-dd')+1 ");
			args.add(sTimeE);
		}

		if (StrUtils.isNotEmpty(eTimeS)) {
			countSql.append(" and t.c_edate>=to_date(?,'yyyy-mm-dd') ");
			args.add(sTimeS);
		}
		if (StrUtils.isNotEmpty(eTimeE)) {
			countSql.append(" and t.c_edate<to_date(?,'yyyy-mm-dd')+1 ");
			args.add(sTimeE);
		}

		String rePlaceSql = "  t.c_id,t.c_title,t.c_address,t.c_sdate,t.c_edate,t.C_ACTIONDATE,t.c_industryid,t.C_IMAGEURL,t.C_WEBVIEWURL,t.C_DETAILURL,t.C_JOINURL,t.C_PUBLISHER,t.C_ISLIVE,t.C_REMARK,t.C_ADDTIME,t.C_EDITPERSON,t.C_AUDITPERSON "
				+ ",(select c_cname from T_ACTIVITY_CITY_INFO t1 where t1.c_id =t.c_cityid) as C_CITY"
				//+ ",(select c_name from T_ACTIVITY_INDUSTRY_TYPE where c_id=t.c_industryid) as C_INDUSTY"
				+ ",(SELECT C_CNAME FROM T_ACTIVITY_BRAND_INFO WHERE C_ID=T.C_BRANDID) AS C_BRAND"
				+ ",(SELECT C_NAME FROM T_ACTIVITY_PROPERTY_TYPE WHERE C_ID=T.C_PROPERTYID) AS C_PROPERTY"
				+ ",(SELECT C_NAME  FROM  T_ACTIVITY_BUSINESS_INFO WHERE C_ID=T.C_BUSINESSID) AS C_BUSINESS ";
		int count = this.jdbcTemplate.queryForInt(countSql.toString(), args
				.toArray());
		String sql1 = DaoUtil.addfy_oracle(countSql, " c_id desc ", start, limit, args)
				.toString().replace("COUNT(C_ID)", rePlaceSql);

		Map map = new HashMap();
		map.put("list", this.jdbcTemplate.queryForList(sql1, args.toArray()));
		map.put("count", count);
		return map;
	}

	@Override
	public void deleteByID(String id) {
		String sql = "delete from T_ACTIVITY_BASE_INFO where  C_id=? ";
		this.jdbcTemplate.update(sql, id);
	}
	
	@Override
	public Map getActDetail(String id){
		String sql = "select * from T_ACTIVITY_BASE_INFO where c_id=?";
		return JdbcTemplateUtils.getMap(jdbcTemplate, sql, id);
	}
	
	@Override
	public String getNextActID() {
		return Integer.valueOf(JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_ACTIVITY_BASE_INFO"))+"";
	}

	@Override
	public String saveActBaseIfo(Map<String, Object> saveMap) {
		String seqId = null;
		if(StrUtils.isNotEmpty(saveMap.get("isAdd"))){
			saveMap.remove("isAdd");
			JdbcTemplateUtils.saveDataByMap(jdbcTemplate, saveMap, "T_ACTIVITY_BASE_INFO");
		}else{
			seqId = String.valueOf(saveMap.get("C_ID"));
			Map<String, Object> whereMap = new HashMap<String, Object>();
			whereMap.put("C_ID", saveMap.get("C_ID"));
			saveMap.remove("C_ID");
			JdbcTemplateUtils.updateDataByMap(jdbcTemplate, saveMap, whereMap, "T_ACTIVITY_BASE_INFO");
		}
		return seqId;
	}

	@Override
	public Map<String, Object> getActBrandInfo(String brandId) {
		String sql = "select t.C_ID,t.C_CNAME as C_BRANDNAME,t.C_LOGOURL as C_BRANDLOGO,t.C_INDUSTRYID,T1.C_NAME as C_INDUSTRYNAME from T_ACTIVITY_BRAND_INFO t left join T_ACTIVITY_INDUSTRY_TYPE t1 on t.C_INDUSTRYID=T1.C_ID where t.C_ID=?";
		return JdbcTemplateUtils.getMap(jdbcTemplate, sql, brandId);
	}
}
