/**
 * 
 */
package com.org.mokey.demo.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author vpc
 */
public class ExcelUtil {

	private static ExcelUtil excelUtil = init();

	private ExcelUtil() {
	}

	private static final ExcelUtil init() {
		return new ExcelUtil();
	}

	public static ExcelUtil newInstance() {
		return excelUtil;
	}

	public synchronized void exportExcelToFile(String tName, List<Object> tHeader,
			List<List<Object>> tValue, Map<String, Short> tHeaderStyle,
			Map<String, Short> tValueStyle, String filePath) throws Exception {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			// 单个表单赋值和样式
			this.setSheet(workbook, tName, tHeader, tValue, tHeaderStyle, tValueStyle );
			// 导出excel文件
			this.export( workbook, filePath );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	public synchronized InputStream exportExcelToStream(String tName, List<Object> tHeader,
			List<List<Object>> tValue, Map<String, Short> tHeaderStyle,
			Map<String, Short> tValueStyle) throws Exception {
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 单个表单赋值和样式
		this.setSheet(workbook, tName, tHeader, tValue, tHeaderStyle, tValueStyle );
		// 导出excel文件
		return export( workbook );
	}

	public synchronized void exportExcelToFile(List<String> tName, List<List<Object>> tHeader,
			List<List<List<Object>>> tValue,
			List<Map<String, Short>> tHeaderStyle,
			List<Map<String, Short>> tValueStyle, String filePath)
			throws Exception {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			for ( int i = 0; i < tName.size(); i++ ) {
				this.setSheet(workbook, tName.get( i ), tHeader.get( i ),
						tValue.get( i ), tHeaderStyle.get( i ),
						tValueStyle.get( i ) ); // 单个表单赋值和样式
			}
			// 导出excel文件
			this.export( workbook, filePath );
		} catch ( Exception e ) {
		}
	}

	public synchronized InputStream exportExcelToStream(List<String> tName,
			List<List<Object>> tHeader, List<List<List<Object>>> tValue,
			List<Map<String, Short>> tHeaderStyle,
			List<Map<String, Short>> tValueStyle) throws Exception {
		XSSFWorkbook workbook = new XSSFWorkbook();
		// for循环完成文档各个表单的赋值和样式
		for ( int i = 0; i < tName.size(); i++ ) {
			this.setSheet( workbook, tName.get( i ), tHeader.get( i ), tValue.get( i ),
					tHeaderStyle.get( i ), tValueStyle.get( i ) ); // 单个表单赋值和样式
		}
		return export( workbook );
	}

	private void setSheet(XSSFWorkbook workbook,String tName, List<Object> tHeader,
			List<List<Object>> tValue, Map<String, Short> tHeaderStyle,
			Map<String, Short> tValueStyle) throws Exception {
		try {
			// 创建表单并设置其表名
			XSSFSheet sheet = workbook.createSheet( tName );
			// 创建表单行
			XSSFRow tRow = sheet.createRow( 0 );
			// 赋值和样式(此时,为表头行)
			tRow = this.setTRow(workbook,sheet, tRow, tHeader, tHeaderStyle );
			// for循环完成表单数据的赋值和样式(除表头)
			for ( int i = 0; i < tValue.size(); i++ ) {
				tRow = sheet.createRow( i + 1 ); // 获取表单行
				tRow = this.setTRow(workbook, sheet, tRow, tValue.get( i ), tValueStyle ); // 设置当前行的数据和样式
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private XSSFRow setTRow(XSSFWorkbook workbook,XSSFSheet sheet,XSSFRow row, List<Object> tRow,
			Map<String, Short> tHeaderStyle) throws Exception {
		try {
			// 获取单元格样式
			XSSFCellStyle cellStyle = this.setCellStyle(workbook, tHeaderStyle );
			// 声明单元格
			XSSFCell cell = null;
			// for循环完成该表单某行各个列赋值和样式
			for ( int i = 0; i < tRow.size(); i++ ) {
				cell = row.createCell( i ); // 获取每列单元格
				cell.setCellStyle( cellStyle ); // 设置样式
				sheet.autoSizeColumn( (short) i ); // 设置单元格自适应
				Object obj = tRow.get( i ); // 获取当前列的值
				// 判断对象所属类型, 并强转
				if ( obj instanceof Integer ) { // 当数字时
					cell.setCellValue( (Integer) obj );
				} else if ( obj instanceof String ) // 当为字符串时
				{
					cell.setCellValue( (String) obj );
				} else if ( obj instanceof Boolean ) // 当为布尔时
				{
					cell.setCellValue( (Boolean) obj );
				} else if ( obj instanceof Date ) // 当为时间时
				{
					cell.setCellValue( (Date) obj );
				} else if ( obj instanceof Calendar ) // 当为时间时
				{
					cell.setCellValue( (Calendar) obj );
				} else if ( obj instanceof Double ) // 当为小数时
				{
					cell.setCellValue( (Double) obj );
				}
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return row; // 返回
	}

	private XSSFCellStyle setCellStyle(XSSFWorkbook workbook,Map<String, Short> fontStyle)
			throws Exception {
		// 声明单元格样式
		XSSFCellStyle cellStyle = null;
		try {
			// 创建字体
			XSSFFont font = workbook.createFont();
			
			if(fontStyle == null) {
				font.setBoldweight( XSSFFont.BOLDWEIGHT_BOLD );
				font.setColor( XSSFFont.COLOR_NORMAL );
			} else {
				font.setColor( fontStyle.get( "color" ) );
				font.setBoldweight( fontStyle.get( "weight" ) );
			}
			
			cellStyle = workbook.createCellStyle();
			// 添加字体样式
			cellStyle.setFont( font );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return cellStyle; // 返回
	}

	private void export(XSSFWorkbook workbook, String filePath)
			throws Exception {
		OutputStream out = null;
		try {
			// 根据指定xls文件创建文件字符流
			out = new FileOutputStream( filePath );
			// 将文档写入指定文件
			workbook.write( out );
		} finally {
			try {
				out.close();
			} catch ( IOException e ) {
				e.printStackTrace();
			}
		}
	}

	private InputStream export(XSSFWorkbook workbook) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			try {
				workbook.write( baos );
			} catch ( IOException e ) {
				e.printStackTrace();
			}
			byte[] ba = baos.toByteArray();
			ByteArrayInputStream bais = new ByteArrayInputStream( ba );
			return bais;
		} finally {
			// 关闭流, 释放资源
			baos.close();
		}
	}
}
