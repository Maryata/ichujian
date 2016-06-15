package com.org.mokey.report.action;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;

import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.ApDateTime;
import com.org.mokey.common.util.highcharts.HighchartsUtil;
import com.org.mokey.demo.util.ServletDownloadUtil;
import com.org.mokey.demo.util.SvgPngConverter;
import com.org.mokey.report.service.StatisticsService;
import com.org.mokey.util.StrUtils;

/**
 * 后台数据统计指标分析
 */
@SuppressWarnings("serial")
public class StatisticsAction extends AbstractAction {
	
	private StatisticsService statisticsService;
	
	private String out;

	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}

	public StatisticsService getStatisticsService() {
		return statisticsService;
	}

	public void setStatisticsService(StatisticsService statisticsService) {
		this.statisticsService = statisticsService;
	}
	
	// 品牌下载、激活、库存信息
	public String brandUser() {
		if(log.isDebugEnabled()) {
			log.debug( "into StatisticsAction.brandUser" );
		}
		Map<String,Object> retMap = new HashMap<String, Object>();
		
		try {
			List<Map<String,Object>> list = statisticsService.brandUser();
			
			retMap.put( "status", "Y" );
			retMap.put( "list", list );
		} catch(Exception e) {
			log.error( e.getMessage() );
		}
		
		try {
			writeToResponse( JSONObject.fromObject( retMap ).toString() );
		} catch ( IOException e ) {
			log.error( e.getMessage() );
		}
		
		return NONE;
	}
	
	//一段时间内用户增长变化
	public String userGrowth() {
		if(log.isDebugEnabled()) {
			log.debug( "into StatisticsAction.userGrowth" );
		}
		Map<String,Object> retMap = new HashMap<String, Object>();
		String sDate = getParameter( "sDate" ),eDate = getParameter( "eDate" );
		
		final String PATTERN = "yyyy-MM-dd";
		
		try {
			List<Map<String,Object>> list = statisticsService.userGrowth( ApDateTime.getDate( sDate, PATTERN ), ApDateTime.getDate( eDate, PATTERN ) );
			
			retMap.put( "status", "Y" );
			retMap.put( "list", list );
		} catch(Exception e) {
			log.error( e.getMessage() );
		}
		
		try {
			writeToResponse( JSONObject.fromObject( retMap ).toString() );
		} catch ( IOException e ) {
			log.error( e.getMessage() );
		}
		return NONE;
	}
	
	//一段时间内品牌激活情况
	public String brandUserGrowth() {
		if(log.isDebugEnabled()) {
			log.debug( "into StatisticsAction.brandUserGrowth" );
		}
		Map<String,Object> retMap = new HashMap<String, Object>();
		String sDate = getParameter( "sDate" ),eDate = getParameter( "eDate" );
		
		final String PATTERN = "yyyy-MM-dd";
		
		try {
			List<Map<String,Object>> list = statisticsService.brandUserGrowth( ApDateTime.getDate( sDate, PATTERN ), ApDateTime.getDate( eDate, PATTERN ) );
			
			retMap.put( "status", "Y" );
			retMap.put( "list", list );
			retMap.put( "categories", ApDateTime.getDayBetween( sDate, eDate ) );
		} catch(Exception e) {
			log.error( e.getMessage() );
		}
		
		try {
			writeToResponse( JSONObject.fromObject( retMap ).toString() );
		} catch ( IOException e ) {
			log.error( e.getMessage() );
		}
		
		return NONE;
	}
	
	
	// 按键设置
	@SuppressWarnings("unchecked")
	public String keySet(){
		Map<String,Object> retMap = new HashMap<String,Object>();
		String sDate = getParameter("sDate");
		String eDate = getParameter("eDate");
		log.info("into StatisticsAction.keySet");
		log.info("sDate = " + sDate + ", eDate = " + eDate);
		if(StrUtils.isEmpty(sDate)){
			sDate = ApDateTime.getFirstDayOfCurrMonth();// 本月第一天
		}
		if(StrUtils.isEmpty(eDate)){
			eDate = ApDateTime.getNowDateTime("yyyy-MM-dd");
		}
		try {
			// 查询数据
			retMap = statisticsService.keySet(sDate, eDate);
			// 获取每个键位的数据
			List<Map<String, Object>> first = (List<Map<String, Object>>) retMap.get("first");
			List<Map<String, Object>> second = (List<Map<String, Object>>) retMap.get("second");
			List<Map<String, Object>> third = (List<Map<String, Object>>) retMap.get("third");
			List<Map<String, Object>> fourth = (List<Map<String, Object>>) retMap.get("fourth");
			// 饼状图JSON
			String graph_1 = this.keySetGraph("一号键设置情况", first);
			String graph_2 = this.keySetGraph("二号键设置情况", second);
			String graph_3 = this.keySetGraph("三号键设置情况", third);
			String graph_4 = this.keySetGraph("四号键设置情况", fourth);
			retMap.put("graph_1", graph_1);
			retMap.put("graph_2", graph_2);
			retMap.put("graph_3", graph_3);
			retMap.put("graph_4", graph_4);
			retMap.put("sDate", sDate);
			retMap.put("eDate", eDate);
			// 回写查询结果
			this.writeJSONToResponse(retMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE; 
	}
	
	// 按键设置饼状图JSON
	private String keySetGraph(String title, List<Map<String, Object>> list) throws JSONException {
		String chartData = "";
		if(StrUtils.isNotEmpty(list)){
			String[] appName = new String[list.size()];// 应用名称
			Long[] appCnt = new Long[list.size()];// 各应用的数量
			for (int i=0;i<list.size();i++) {
				String name = StrUtils.isEmpty(list.get(i).get("C_APP_NAME")) ? " " : list.get(i).get("C_APP_NAME").toString() ;
				Long count = StrUtils.isEmpty(list.get(i).get("CNT")) ? 0 : Long.valueOf(list.get(i).get("CNT").toString());
				appName[i] = "<b>" + name + "</b>：" + count + "个";
				appCnt[i] =  count;
			}
			chartData += HighchartsUtil.getPie3d(title, appName, appCnt);
		}
		return chartData;
	}

	/**
	 *  2015-8-24  将T_SET_CLICK_HIS表中key为3的数据的c_app_name字段修改为“游戏帮”
	 *  该接口返回修改之前的数据的id，方便恢复数据 
	 */
	public String updatedIds(){
		List<Map<String, Object>> list= statisticsService.updatedIds();
		StringBuffer sb = new StringBuffer("(");
		for (Map<String, Object> map : list) {
			String id = map.get("C_ID").toString();
			sb.append(id + ",");
		}
		String ids = sb.toString();
		ids = ids.substring(0, ids.length()-1);
		ids = ids + ")";
		out = JSONObject.fromObject(ids).toString();
		return "success";
	}
	
	// 导出Excel
	public String exp(){
		Workbook wb = new XSSFWorkbook();
		
		// 按键设置
		wb = this.keySetExp(wb);
		// 安装激活
		wb = this.installNActive(wb);
		
		
		ByteArrayInputStream in = null;
		ByteArrayOutputStream out = null;
		// 生成Excel
		try {
			// 创建字节数组输出流
			out = new ByteArrayOutputStream();
			// 将workbook写入输出流
			wb.write(out);
			// 将输出流转成字节数组
			byte[] buf = out.toByteArray();
			// 读入输入流
			in = new ByteArrayInputStream(buf);
			String contentType = "application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
			String headerKey = "Content-Disposition";
			String headerValue = String.format( "attachment; filename=\"%s\"",
					System.currentTimeMillis() + ".xlsx" );
			// 下载
			ServletDownloadUtil.doDownload(getResponse(), in, contentType, headerKey, headerValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}

	// "安装激活"sheet页
	private Workbook installNActive(Workbook wb) {
		final String sheetName = "下载激活";
		// 各品牌下载激活数据
		String brandUserData = getParameter( "dt0" );
		// 一段时间内用户增长数据
		String userGrowthData = getParameter( "dt1" );
		
		// 一段时间内用户增长折线图SVG xml
		String userGrowthSVG = getParameter( "svg0" );
		// 一段时间那日品牌用户增长SVG xml
		String brandUserGrowthSVG = getParameter( "svg1" );
		
		Sheet sheet = wb.createSheet( sheetName );
		// 定义单元格对齐样式
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 居中对齐
		
		Row row = sheet.createRow(0);// 创建首行
		Cell cell = row.createCell((short)(0));// 创建首列
		cell.setCellValue("下载激活数据分析");
		cell.setCellStyle(cellStyle);// 居中对齐
		sheet.addMergedRegion(new CellRangeAddress(
	            0, //first row (0-based)
	            0, //last row  (0-based)
	            0, //first column (0-based)
	            3  //last column  (0-based)
	    ));
		
		JSONArray jsonArray = JSONArray.fromObject( brandUserData );
		int len1 = jsonArray.size();
		for(int i = 0; i < len1; ++i) {
			row = sheet.createRow( i + 1 );
			
			JSONObject json = jsonArray.getJSONObject( i );
			JSONArray _arr = json.getJSONArray( "r" );
			
			for(int j = 0; j < _arr.size(); ++j) {
				Cell c = row.createCell( j );
				c.setCellValue( _arr.getString( j ) );
			}
		}
		
		jsonArray = JSONArray.fromObject( userGrowthData );
		int len2 = jsonArray.size();
		for(int i = 0; i < len2; ++i) {
			row = sheet.createRow( len1 + i + 3 );
			
			JSONObject json = jsonArray.getJSONObject( i );
			JSONArray _arr = json.getJSONArray( "r" );
			
			for(int j = 0; j < _arr.size(); ++j) {
				Cell c = row.createCell( j );
				c.setCellValue( _arr.getString( j ) );
			}
		}
		
		int rows = len1 + len2;
		
		drawingPatriarch(wb, userGrowthSVG, 0, 0, 800, 800, (short) 0, rows + 5, (short) 11, rows + 25, sheetName);
		drawingPatriarch(wb, brandUserGrowthSVG, 0, 0, 800, 800, (short) 0, rows + 27, (short) 11, rows + 47, sheetName);
		
		return wb;
	}

	// "按键设置"sheet页
	private Workbook keySetExp(Workbook wb) {
		// 获取请求参数
		String firstRow = getRequest().getParameter("firstRow");
		String secondRow = getRequest().getParameter("secondRow");
		String thirdRow = getRequest().getParameter("thirdRow");
		String fourthRow = getRequest().getParameter("fourthRow");
		String firstKeySetSVG = getRequest().getParameter("firstKeySetSVG");
		String secondKeySetSVG = getRequest().getParameter("secondKeySetSVG");
		String thirdKeySetSVG = getRequest().getParameter("thirdKeySetSVG");
		String fourthKeySetSVG = getRequest().getParameter("fourthKeySetSVG");
		
		// 创建sheet页
		Sheet sheet = wb.createSheet("按键设置");
		
		// 设置列宽
		for (int i = 1; i <= 20; i++) {
			sheet.setColumnWidth((short) i, (short) ( ( 50 * 2.5 ) / ( (double) 1 / 20 ) ) );
		}
		
		// 设置头信息
		Header header = sheet.getHeader();
		header.setCenter("Center Header");
		header.setLeft("Left Header");
		header.setRight(HSSFHeader.font("Stencil-Normal", "Italic") +
 				HSSFHeader.fontSize((short) 16) + "Right w/ Stencil-Normal Italic font and size 16");
		
		/***** 标题  *****/
		XSSFCellStyle titleStyle = this.getCellStyle(wb, false, true,
				Font.COLOR_RED, (short) 20, "华文楷体", true, null);
		Row row = sheet.createRow(0);// 创建首行
		Cell cell = row.createCell((short)(0));// 创建首列
		cell.setCellValue("按 键 设 置 情 况 分 析");
		
		cell.setCellStyle(titleStyle);
		
		CellRangeAddress region = new CellRangeAddress(
	            0, //first row (0-based)
	            0, //last row  (0-based)
	            0, //first column (0-based)
	            20  //last column  (0-based)
	    );  
        sheet.addMergedRegion(region);  
        RegionUtil.setBorderBottom(XSSFCellStyle.BORDER_DOUBLE,region, sheet, wb);  
		/***** 表头  *****/
		XSSFCellStyle headStyle = this.getCellStyle(wb, true, 
					false, Font.BOLDWEIGHT_BOLD, (short) 13, "宋体", false,
					IndexedColors.LIGHT_TURQUOISE.getIndex());
		// 第1行
		row = sheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue("键位");
		cell.setCellStyle(headStyle);
		for (int i = 1,j = 1; i <= 19; i+=2,j++) {
			cell = row.createCell(i);
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue(Double.parseDouble(j+""));
			// 合并单元格
			region = new CellRangeAddress(1,1,i,i+1);
			sheet.addMergedRegion(region);
			// 边框
	        RegionUtil.setBorderBottom(XSSFCellStyle.BORDER_DOUBLE,region, sheet, wb);   
	        RegionUtil.setBorderLeft(XSSFCellStyle.BORDER_DOUBLE,region, sheet, wb);   
	        RegionUtil.setBorderRight(XSSFCellStyle.BORDER_DOUBLE,region, sheet, wb);   
	        RegionUtil.setBorderTop(XSSFCellStyle.BORDER_DOUBLE,region, sheet, wb);
			cell.setCellStyle(headStyle);
		}
		
		// 第1行
		row = sheet.createRow(2);
		
		row.createCell(0);
		region = new CellRangeAddress(1,2,0,0);
		sheet.addMergedRegion(region);
		RegionUtil.setBorderLeft(XSSFCellStyle.BORDER_DOUBLE,region, sheet, wb);
		RegionUtil.setBorderBottom(XSSFCellStyle.BORDER_DOUBLE,region, sheet, wb); 
		
		for (int i = 1; i <= 20; i++) {
			if(i%2 != 0){
				cell = row.createCell(i);
				cell.setCellValue("应用名称");
			}else{
				cell = row.createCell(i);
				cell.setCellValue("数量");
			}
			cell.setCellStyle(headStyle);
			headStyle.setBorderTop(XSSFCellStyle.BORDER_DOUBLE);			//上边框
			headStyle.setBorderRight(XSSFCellStyle.BORDER_DOUBLE);			//右边框	
			headStyle.setBorderBottom(XSSFCellStyle.BORDER_DOUBLE); 		//下边框
			headStyle.setBorderLeft(XSSFCellStyle.BORDER_DOUBLE);			//左边框
		}
		/***** 表格数据  *****/
		XSSFCellStyle tableStyle = this.getCellStyle(wb, false,
				false, Font.COLOR_NORMAL, (short) 11, "宋体", false,
				null);
		// 把每一行的数据分别存入一个List
		List<String> row_1 = this.getRowData(firstRow);
		List<String> row_2 = this.getRowData(secondRow);
		List<String> row_3 = this.getRowData(thirdRow);
		List<String> row_4 = this.getRowData(fourthRow);
		// 将数据存入sheet
		for (int i = 0; i < 4; i++) {
			// 创建第2、3、4、5行
			row = sheet.createRow(i+3);
			for (int j = 0; j < 21; j++) {
				if(j==0){// 第一次内循环设置键位
					cell = row.createCell(0);
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					String key = null;
					if(i==0){
						key = "1";
					}else if(i==1) {
						key = "2";
					}else if(i==2) {
						key = "3";
					}else if(i==3) {
						key = "4";
					}
					cell.setCellValue(Double.parseDouble(key));
				}else{// 其余循环设置数据
					String v = "";
					cell = row.createCell(j);
					try {
						if(i==0){
							v = row_1.get(j-1);
						}else if(i==1) {
							v = row_2.get(j-1);
						}else if(i==2) {
							v = row_3.get(j-1);
						}else if(i==3) {
							v = row_4.get(j-1);
						}
						if(j%2==0){
							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							cell.setCellValue(Double.parseDouble(v));
						}else{
							cell.setCellValue(v);
						}
					} catch (Exception e) {
					}
				}
				cell.setCellStyle(tableStyle);
			}
		}
		/***** 饼状图数据  *****/
		// 分别写到workbook中
		// 1号键
		wb = drawingPatriarch(wb, firstKeySetSVG, 0, 0, 700, 700, (short) 1, 8, (short) 8, 30, "按键设置" );
		// 2号键
		wb = drawingPatriarch(wb, secondKeySetSVG, 0, 0, 700, 700, (short) 9, 8, (short) 16, 30, "按键设置"  );
		// 3号键
		wb = drawingPatriarch(wb, thirdKeySetSVG, 0, 0, 700, 700, (short) 1, 32, (short) 8, 55, "按键设置"  );
		// 4号键
		wb = drawingPatriarch(wb, fourthKeySetSVG, 0, 0, 700, 700, (short) 9, 32, (short) 16, 55, "按键设置"  );
		
		return wb;
	}
	
	/**
	 *  获取单元格样式
	 * @param wb Workbook
	 * @param bold 字体是否加粗
	 * @param italic 是否斜体
	 * @param fontColor 字体颜色
	 * @param fontHeight 字体大小（字号）
	 * @param fontStyle 字体风格（如：宋体）
	 * @param underline 是否加下划线
	 * @param bgColor 单元格背景颜色
	 * @return
	 */
	public XSSFCellStyle getCellStyle(Workbook wb, boolean bold, boolean italic, short fontColor,
			short fontHeight, String fontStyle, boolean underline, Short bgColor){
		//============================   
        //       设置单元格的字体   
        //============================   
        // 创建单元格样式对象   
        XSSFCellStyle cellStyle = (XSSFCellStyle) wb.createCellStyle();   
        // 创建字体对象   
        Font ztFont = wb.createFont();   
        ztFont.setItalic(italic);                    		// 设置字体为斜体字   
        ztFont.setColor(fontColor);           				// 设置字体
        ztFont.setFontHeightInPoints(fontHeight);    		// 设置字体大小
        ztFont.setFontName(fontStyle);             			// 将“华文行楷”字体应用到当前单元格上
        if (bold) {
        	ztFont.setBoldweight(Font.BOLDWEIGHT_BOLD);		// 字体加粗
		}
        if(underline){
        	ztFont.setUnderline(Font.U_DOUBLE);     // 添加（Font.U_SINGLE单条下划线/Font.U_DOUBLE双条下划线）   
        }
//      ztFont.setStrikeout(true);                  		// 是否添加删除线   
        cellStyle.setFont(ztFont);                    		// 将字体应用到样式上面
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 水平居中
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); // 垂直居中
//    	cellStyle.setWrapText(true);						//设置自动换行
    	//==============================
    	// 背景色
    	//==============================
    	if(null!=bgColor){
    		cellStyle.setFillForegroundColor(bgColor);
    		cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
    	}
    	
        return cellStyle;
	}
	
	// “按键设置”表格数据--将字符串数据封装到List中
	private List<String> getRowData(String row){
		String[] keyValArr = row.split(",");
		List<String> list = new ArrayList<String>(20);
		for (int i = 0; i < 10; i++) {
			int len = keyValArr.length;
			if(i<len){
				String[] keyVal = keyValArr[i].split(":");
				if(StrUtils.isNotEmpty(keyVal) && keyVal.length == 2){
					list.add(keyVal[0]);
					list.add(keyVal[1]);
				}
			}
		}
		return list;
	}
	
	/**
	 *  SVG to PNG
	 * (0,   0,   800, 800, (short) 0, 8,   (short) 9, 22)
	 * (dx1, dy1, dx2, dy2,      col1, row1,     col2, row2)
	 * @param wb Workbook对象
	 * @param svg SVG字符串
	 * @param dx1  起始锚点<行>偏移量
	 * @param dy1  起始锚点<列>偏移量
	 * @param dx2  结束锚点<行>偏移量
	 * @param dy2  结束锚点<列>偏移量
	 * @param col1 起始锚点<列> 
	 * @param row1  起始锚点<行>
	 * @param col2  结束锚点<列> 
	 * @param row2  结束锚点<行>
	 * @return
	 */
	public Workbook drawingPatriarch(Workbook wb, String svg, int dx1, int dy1,
			int dx2, int dy2, int col1, int row1, int col2, int row2,String sheetName){
		if(StrUtils.isEmpty(svg)){
			return wb;
		}else{
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			try {
				SvgPngConverter.convertToPng(svg, outputStream);
			} catch (TranscoderException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 获取sheet页
			Sheet sheet = wb.getSheet(sheetName);
			// 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
			Drawing patriarch = sheet.createDrawingPatriarch();
			// anchor主要用于设置图片的属性
			ClientAnchor anchor = new XSSFClientAnchor(dx1, dy1, dx2, dy2, (short) col1, row1, (short) col2, row2);
			anchor.setAnchorType( 3 );
			// 插入图片
			patriarch.createPicture( anchor, 
					wb.addPicture(outputStream.toByteArray(),XSSFWorkbook.PICTURE_TYPE_PNG ) );
			return wb;
		}
	}
}
