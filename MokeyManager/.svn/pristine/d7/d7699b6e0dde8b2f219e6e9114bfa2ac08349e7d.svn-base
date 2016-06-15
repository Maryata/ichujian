/**
 * 
 */
package com.org.mokey.demo.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.demo.service.PageService;
import com.org.mokey.demo.util.Base64Util;
import com.org.mokey.demo.vo.Pagination;

/**
 * @author vpc
 */
public class PageDemoAction extends AbstractAction {
	private static final long serialVersionUID = -6562890378500516301L;

	@Autowired
	private PageService pageService;

	public String main() {
		String name = getParameter( "name" );
		String code = getParameter( "code" );

		String sPage = getParameter( "page" );
		int page = 1;
		int rows = 2;
		if ( null != sPage && sPage.matches( "\\d+" ) ) {
			page = Integer.parseInt( sPage );
		} else {
			log.info( sPage );
		}

		log.info( "name : " + name + " == code : " + code + " == page : "
				+ page );

		log.info( pageService );
		String sql = "SELECT * FROM T_SYS_USER";
		Pagination pg = pageService.page( sql, page, rows );
		getRequest().setAttribute( "pg", pg );

		return ActionSupport.SUCCESS;
	}

	public String exp() {
		String list = getParameter( "list" );
		String completeImageData = getParameter( "img" );
		System.out.println( list );
		System.out.println( completeImageData );

		String imageDataString = completeImageData.substring( completeImageData
				.indexOf( "," ) + 1 );

		FileOutputStream fileOut = null;
		BufferedImage bufferImg = null;
		InputStream in = null;
		// 先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
		try {
			in = new ByteArrayInputStream(
					Base64Util.decodeImage( imageDataString ) );
			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
			bufferImg = ImageIO.read( in );
			ImageIO.write( bufferImg, "png", byteArrayOut );

			Workbook wb = new XSSFWorkbook();
			Sheet sheet = wb.createSheet( "test picture" );
			
			Header header = sheet.getHeader();
		    header.setCenter("Center Header");
		    header.setLeft("Left Header");
		    header.setRight(HSSFHeader.font("Stencil-Normal", "Italic") +
		    				HSSFHeader.fontSize((short) 16) + "Right w/ Stencil-Normal Italic font and size 16");
			
			Row row = sheet.createRow( 1 );
		    Cell cell = row.createCell((short) 1);
		    cell.setCellValue("This is a test of merging");

		    sheet.addMergedRegion(new CellRangeAddress(
		            1, //first row (0-based)
		            1, //last row  (0-based)
		            1, //first column (0-based)
		            2  //last column  (0-based)
		    ));
		    
		    row = sheet.createRow( 2 );
			row.createCell( 0 ).setCellValue( "张三" );
			row.createCell( 1 ).setCellValue( "18" );
			
			row = sheet.createRow( 3 );
			row.createCell( 0 ).setCellValue( "李四" );
			row.createCell( 1 ).setCellValue( "20" );
			
			// 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
			Drawing patriarch = sheet.createDrawingPatriarch();
			// anchor主要用于设置图片的属性
			ClientAnchor anchor = new XSSFClientAnchor( 0, 0, 800, 800,
					(short) 0, 8, (short) 9, 22 );
			anchor.setAnchorType( 3 );
			// 插入图片
			patriarch
					.createPicture( anchor, wb.addPicture(
							byteArrayOut.toByteArray(),
							XSSFWorkbook.PICTURE_TYPE_PNG ) );
			File file = new File(PageDemoAction.class
					.getClassLoader().getResource( "data/analytics" ).getFile()
					+ "/1.xlsx");
			fileOut = new FileOutputStream( file );
			// 写入excel文件
			wb.write( fileOut );

			
			InputStream inStream = new FileInputStream( file );

			HttpServletResponse response = getResponse();
			response.reset();
			response.setContentType( "application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" );
			//response.setContentType( "application/force-download" );
			//response.setContentType( "application/x-msdownload" );
			response.setContentLength((int) file.length());
			//response.setHeader("Content-Transfer-Encoding", "binary");
			
			String headerKey = "Content-Disposition";
			String headerValue = String.format( "attachment; filename=\"%s\"",
					System.currentTimeMillis() + ".xlsx" );
			response.setHeader( headerKey, headerValue );

			OutputStream outStream = response.getOutputStream();

			byte[] buffer = new byte[4096];
			int bytesRead = -1;

			while ( (bytesRead = inStream.read( buffer )) != -1 ) {
				outStream.write( buffer, 0, bytesRead );
			}

			inStream.close();
			outStream.flush();
			outStream.close();

			System.out.println( "----Excle文件已生成------" );
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			if ( fileOut != null ) {
				try {
					fileOut.close();
				} catch ( IOException e ) {
					e.printStackTrace();
				}
			}
		}

		return NONE;
	}
}
