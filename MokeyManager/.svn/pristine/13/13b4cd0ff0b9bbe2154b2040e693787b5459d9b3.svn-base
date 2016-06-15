package com.org.mokey.util;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

public class ExcelCellStyle {

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
	public static XSSFCellStyle getCellStyle(Workbook wb, boolean bold, boolean italic, short fontColor,
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
}
