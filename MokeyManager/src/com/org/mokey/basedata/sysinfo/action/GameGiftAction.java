package com.org.mokey.basedata.sysinfo.action;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.basedata.sysinfo.service.AppInfoService;
import com.org.mokey.basedata.sysinfo.service.GameGiftCateService;
import com.org.mokey.basedata.sysinfo.service.GameGiftService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.common.util.str.UnicodeReader;
import com.org.mokey.demo.util.ServletDownloadUtil;
import com.org.mokey.util.ExcelCellStyle;
import com.org.mokey.util.StrUtils;

/**
 * 游戏帮：游戏礼包
 */
@SuppressWarnings("serial")
public class GameGiftAction extends AbstractAction{
	
	// 游戏礼包
	private GameGiftService gameGiftService;
	
	// 游戏
	private AppInfoService appInfoService;
	
	// 游戏礼包分类
	private GameGiftCateService gameGiftCateService;

	public GameGiftCateService getGameGiftCateService() {
		return gameGiftCateService;
	}

	public void setGameGiftCateService(GameGiftCateService gameGiftCateService) {
		this.gameGiftCateService = gameGiftCateService;
	}

	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public GameGiftService getGameGiftService() {
		return gameGiftService;
	}

	public void setGameGiftService(GameGiftService gameGiftService) {
		this.gameGiftService = gameGiftService;
	}
	
	public AppInfoService getAppInfoService() {
		return appInfoService;
	}

	public void setAppInfoService(AppInfoService appInfoService) {
		this.appInfoService = appInfoService;
	}

	// 跳转到礼包列表页面
	public String toGiftList(){
		log.info("into GameGiftAction.toActList");
		try {
			// 查询所有游戏
			@SuppressWarnings("unchecked")
			List<Map<String,Object>> games = appInfoService.getGameAppInfoList();
			getRequest().setAttribute("games", games);
		} catch (Exception e) {
			log.error("GameGiftAction.toActList failed, e : " + e);
		}
		return "gameGiftList";
	}
	
	// 查询礼包列表
	public String gameGiftList(){
		String gid = getParameter( "gid" );// 游戏id
		String name = getParameter( "name" );// 礼包名
		String gName = getParameter( "gName" );// 游戏名
		String sPage = getParameter("page");// 获取page
		log.info("into GameGiftAction.GameGiftList");
		log.info("gid = " + gid + ", name = " + name + ", page = " + sPage);
		try {
			int page = 1;// 默认第一页
			if(null != sPage && sPage.matches( "\\d+" )) {
				page = Integer.parseInt( sPage );
			} else {
				log.info( sPage );
			}
			List<Map<String,Object>> list = gameGiftService.gameGiftList(gid, name, page, gName);
			super.writeJSONToResponse(list);
		} catch (Exception e) {
			log.error("GameGiftAction.GameGiftList failed, e : " + e);
		}
		return NONE;
	}
	
	// 获取礼包总数
	public String getTotal(){
		String gid = getParameter("gid");// 游戏id
		String gName = getParameter("gName");// 游戏名
		String name = getParameter("name");// 礼包名
		log.info("into GameGiftAction.getTotalCol");
		log.info("gid = " + gid + ", name = " + name);
		try {
			// 总条数
			Integer total = gameGiftService.getTotal(gid, name, gName);
			// 计算总页数
			Integer rows = 10;// 每页10条
			Integer totalPage = (total-1)/rows + 1;
			// 回写查询结果
			super.writeJSONToResponse(totalPage);
		} catch (Exception e) {
			log.error("GameGiftAction.getTotal failed,",e);
		}
		return NONE;
	}
	
	// 根据礼包id删除游戏礼包
	public String deleteGameGift(){
		String id = getParameter("id");// 礼包id
		log.info("into GameGiftAction.GameGiftAction");
		log.info("id = " + id);
		try {
			// 删除
			gameGiftService.deleteGameGift(id);
		} catch (Exception e) {
			log.error("GameGiftAction.getTotal failed,",e);
		}
		return NONE;
	}
	
	// 跳转到礼包添加页面
	@SuppressWarnings("unchecked")
	public String toGameGiftAdd(){
		log.info("into GameGiftAction.toGameGiftAdd");
		try {
			// 查询所有游戏
			List<Map<String,Object>> games = appInfoService.getGameAppInfoList();
			getRequest().setAttribute("games", games);
			// 查询所有游戏礼包分类
			List<Map<String, Object>> cate = gameGiftCateService.getGiftCateList();
			getRequest().setAttribute("cate", cate);
			// 主键id
			String newId = JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_GAME_ACTIVITY_INFO");
			getRequest().setAttribute("newId", newId);
			// 添加/编辑的标识
			getRequest().setAttribute("addFlag", 1);
		} catch (Exception e) {
			log.error("GameGiftAction.toGameGiftAdd failed, e : " + e);
		}
		return "toGameGiftAdd";
	}
	// 跳转到礼包编辑页面
	@SuppressWarnings("unchecked")
	public String toGameGiftEdit(){
		String editId = getParameter("editId");
		log.info("into GameGiftAction.toGameGiftEdit");
		log.info("editId = " + editId);
		try {
			// 查询所有游戏
			List<Map<String,Object>> games = appInfoService.getGameAppInfoList();
			getRequest().setAttribute("games", games);
			// 查询所有游戏礼包分类
			List<Map<String, Object>> cate = gameGiftCateService.getGiftCateList();
			getRequest().setAttribute("cate", cate);
			// 根据id查询礼包
			List<Map<String, Object>> list = gameGiftService.queryGiftById(editId);
			String editGid = "";
			String editOrder = "";
			String editCount = "";
			String editName = "";
			String editDepict = "";
			String editMehtod = "";
			String editSDate = "";
			String editEDate = "";
			String editCate = "";
			if(StrUtils.isNotEmpty(list)){
				editGid = (list.get(0).get("GID"))==null?"":list.get(0).get("GID").toString();
				editOrder = (list.get(0).get("CORDER"))==null?"":list.get(0).get("CORDER").toString();
				editCount = (list.get(0).get("CCOUNT"))==null?"":list.get(0).get("CCOUNT").toString();
				editName = (list.get(0).get("NAME"))==null?"":list.get(0).get("NAME").toString();
				editDepict = (list.get(0).get("DEPICT"))==null?"":list.get(0).get("DEPICT").toString();
				editMehtod = (list.get(0).get("CMETHOD"))==null?"":list.get(0).get("CMETHOD").toString();
				editSDate = (list.get(0).get("SDATE"))==null?"":list.get(0).get("SDATE").toString();
				editEDate = (list.get(0).get("EDATE"))==null?"":list.get(0).get("EDATE").toString();
				editCate = (list.get(0).get("CATE"))==null?"":list.get(0).get("CATE").toString();
			}
			getRequest().setAttribute("newId", editId);
			getRequest().setAttribute("editGid", editGid);
			getRequest().setAttribute("editOrder", editOrder);
			getRequest().setAttribute("editCount", editCount);
			getRequest().setAttribute("editName", editName);
			getRequest().setAttribute("editDepict", editDepict);
			getRequest().setAttribute("editMehtod", editMehtod);
			getRequest().setAttribute("editSDate", editSDate);
			getRequest().setAttribute("editEDate", editEDate);
			getRequest().setAttribute("editCate", editCate);
		} catch (Exception e) {
			log.error("GameGiftAction.toGameGiftEdit failed, e : " + e);
		}
		return "toGameGiftAdd";
	}
	
	// 保存礼包
	public String saveGameGift(){
		String id = getParameter("id");// 礼包id
		String gid = getParameter("gid");// 游戏id
		String name = getParameter("name");// 礼包名称
		String depict = getParameter("depict");// 礼包内容
		String sdate = getParameter("sdate");// 礼包开始时间
		String edate = getParameter("edate");// 礼包结束时间
		String method = getParameter("method");// 礼包使用方法
		String count = getParameter("count");// 礼包码数量
		String order = getParameter("order");// 游戏id
		String cate = getParameter("cate");// 游戏礼包分类
		log.info("into GameGiftAction.saveGameGift");
		log.info("id = " + id + ", gid = " + gid + ", name = " + name + ", cate = " + cate + 
				", depict = " + depict + ", sdate = " + sdate + ", edate = " + edate +
				", method = " + method +  ", count = " + count + ", order = " + order);
		try {
			gameGiftService.saveGameGift(id,gid,name,depict,sdate,edate,method,count,order,cate);
		} catch (Exception e) {
			log.error("GameGiftAction.saveGameGift failed, e : " + e);
		}
		return "saveGameGift";
	}
	
	// 上传礼包码
	public String upload(){
		String id = getParameter("id");// 礼包id
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) getRequest();
			File[] files = multiWrapper.getFiles("codeScanField");
			List<String> list = new ArrayList<String>();
			try {
				FileInputStream in = new FileInputStream(files[0]);  
				BufferedReader br = new BufferedReader(new UnicodeReader(in, Charset.defaultCharset().name()));
				String line = null;
				try {
					while ((line = br.readLine()) != null) {
						list.add(line);
					}
				} catch (IOException e) {
					e.printStackTrace();
					retMap.put("status", "I/O流读写出错！");
				} finally {
					try {
						br.close();
					} catch (IOException e) {
						log.error("GameGiftAction.upload failed, e : " + e);
						retMap.put("status", "关闭I/O流出错！");
					}
				}
			} catch (Exception e) {
				log.error("GameGiftAction.upload failed, e : " + e);
				retMap.put("status", "I/O流读写出错！");
			}
			gameGiftService.upload(list, id);
		} catch (Exception e) {
			log.error("GameGiftAction.upload failed, e : " + e);
			retMap.put("status", "上传失败！");
		} finally {
			try {
				writeToResponse(JSONObject.fromObject(retMap).toString());
			} catch (IOException e) {
				log.error("GameGiftAction.upload failed, e : " + e);
			}
		}
		return NONE;
	}
	
	// 跳转到礼包码页面
	public String toGiftCodesList(){
		// 获取去请求参数
		String gid = getParameter("editId");// 礼包id
		log.info("into GameGiftAction.toGiftCodes");
		log.info("gid = " + gid);
		try {
			getRequest().setAttribute("gid", gid);
		} catch (Exception e) {
			log.error("GameGiftAction.toGiftCodes failed,",e);
		}
		return "toGiftCodesList";
	}
	
	// 根据礼包id查询相关礼包码
	public String getCodesListByGid(){
		Map<String, Object> retmap = new HashMap<String, Object>();
		int page = getParameter2Int("page", 1);// 获取page
		String gid = getParameter("gid");// 礼包id
		String state = getParameter("state");// 礼包id
		String sDate = getParameter("sDate");// 礼包id
		String eDate = getParameter("eDate");// 礼包id
		log.info("into GameGiftAction.getCodesListByGid");
		log.info("gid = " + gid + ", state = " + state +
				", sDate = " + sDate + ", eDate = " + eDate);
		try{
			// 分页查询
			retmap = gameGiftService.getCodesListByGid(page, gid, state, sDate, eDate);
			retmap.put("status", "Y");
			// 回写查询结果
			this.writeJSONToResponse(retmap);
		}catch(Exception e){
			retmap.put("status", "N");
			log.error("GameGiftAction.getCodesListByGid failed,",e);
		}
		return NONE;
	}
	
	// 删除礼包码
	public String delCode(){
		String gid = getParameter("gid");// 礼包id
		String id = getParameter("id");// 礼包码id
		log.info("into GameGiftAction.delCode");
		log.info("gid = " + gid + ", id = " + id);
		try{
			// 分页查询
			gameGiftService.delCode(gid, id);
		}catch(Exception e){
			log.error("GameGiftAction.delCode failed,",e);
		}
		return NONE;
	}
	
	// 批量删除礼包码
	public String batchDel(){
		String gid = getParameter("gid");// 礼包id
		String id = getParameter("id");// 礼包码id
		log.info("into GameGiftAction.batchDel");
		log.info("gid = " + gid + ", id = " + id);
		try{
			// 分页查询
			gameGiftService.batchDel(gid, id);
		}catch(Exception e){
			log.error("GameGiftAction.batchDel failed,",e);
		}
		return NONE;
	}
	
	// 导出Excel
	public String exportExcel(){
		String gid = getParameter("gid");// 礼包id
		String state = getParameter("queryState");// 礼包码领取状态
		log.info("into GameGiftAction.exportExcel");
		log.info("gid = " + gid + ", state = " + state);
		try{
			// 按“领取状态”查询所有指定礼包的礼包码
			List<Map<String, Object>> list = gameGiftService.getCodesByGidNState(gid, state);
			
			/***************** 导出  *************************/
			Workbook wb = new XSSFWorkbook();
			// 创建sheet页
			Sheet sheet = wb.createSheet("礼包码");
			// 设置列宽
			for (int i = 0; i < 3; i++) {
				sheet.setColumnWidth((short) i, (short) ( ( 200 * 2.5 ) / ( (double) 1 / 20 ) ) );
			}
			XSSFCellStyle headStyle = ExcelCellStyle.getCellStyle(wb, true,
				false, Font.BOLDWEIGHT_BOLD, (short) 11, "宋体", false,
				IndexedColors.LIGHT_TURQUOISE.getIndex());
			headStyle.setBorderTop(XSSFCellStyle.BORDER_DOUBLE);			//上边框
			headStyle.setBorderRight(XSSFCellStyle.BORDER_DOUBLE);			//右边框	
			headStyle.setBorderBottom(XSSFCellStyle.BORDER_DOUBLE); 		//下边框
			headStyle.setBorderLeft(XSSFCellStyle.BORDER_DOUBLE);			//左边框
			// 设置头信息
			Header header = sheet.getHeader();
			header.setCenter("Center Header");
			header.setLeft("Left Header");
			header.setRight(HSSFHeader.font("Stencil-Normal", "Italic") +
	 				HSSFHeader.fontSize((short) 16) + "Right w/ Stencil-Normal Italic font and size 16");
			// 表头
			Row row = sheet.createRow(0);// 创建首行
			Cell cell = row.createCell((short)(0));// 首列
			cell.setCellStyle(headStyle);
			cell.setCellValue("礼  包  码");
			cell = row.createCell((short)(1));// 第二列
			cell.setCellStyle(headStyle);
			cell.setCellValue("领  取  人  昵  称");
			cell = row.createCell((short)(2));// 第三列
			cell.setCellStyle(headStyle);
			cell.setCellValue("领  取  人  ID");
			XSSFCellStyle contentStlye = ExcelCellStyle.getCellStyle(wb, false,
					false, Font.COLOR_NORMAL, (short) 11, "宋体", false, null);
			// 正文
			if(StrUtils.isNotEmpty(list)){
				for (int i = 0; i < list.size(); i++) {
					row = sheet.createRow(i+1);
					Map<String, Object> map = list.get(i);
					cell = row.createCell((short)(0));
					cell.setCellStyle(contentStlye);
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(Double.parseDouble(StrUtils.emptyOrString(map.get("C_CODE"))));
					cell = row.createCell((short)(1));
					cell.setCellStyle(contentStlye);
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue(Double.parseDouble(StrUtils.emptyOrString(map.get("USERID"))));
					cell = row.createCell((short)(2));
					cell.setCellStyle(contentStlye);
					cell.setCellValue(StrUtils.emptyOrString(map.get("UNAME")));
				}
			}
			
			// 创建字节数组输出流
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			// 将workbook写入输出流
			wb.write(out);
			// 将输出流转成字节数组
			byte[] buf = out.toByteArray();
			// 读入输入流
			ByteArrayInputStream in = new ByteArrayInputStream(buf);
			String contentType = "application/application/vnd." +
					"openxmlformats-officedocument.spreadsheetml.sheet";
			String headerKey = "Content-Disposition";
			String headerValue = String.format( "attachment; filename=\"%s\"",
					System.currentTimeMillis() + ".xlsx" );
			// 下载
			ServletDownloadUtil.doDownload(getResponse(), in, contentType, headerKey, headerValue);
			
		}catch(Exception e){
			log.error("GameGiftAction.exportExcel failed,",e);
		}
		return NONE;
	}
}
