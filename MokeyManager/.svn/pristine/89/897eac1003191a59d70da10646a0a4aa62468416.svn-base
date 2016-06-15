package com.org.mokey.common.util.excel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.org.mokey.demo.util.SvgPngConverter;
import com.org.mokey.util.StrUtils;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.commons.logging.Log;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * ��ȡ��ݵ���ز���
 * @author
 *
 */
public class ExcelUtil {
    public final static String getStringValue(HSSFCell cell){
        if (cell == null)
            return null;
        if (cell.getCellType() != HSSFCell.CELL_TYPE_STRING){
            throw new IllegalArgumentException("cell must STRING type");
        }
        
        return cell.getStringCellValue();
    }
    
    public final static Integer getIntValue(HSSFCell cell){
        if (cell == null)
            return null;
        if (cell.getCellType() != HSSFCell.CELL_TYPE_NUMERIC){
            throw new IllegalArgumentException("cell must NUMBER type");
        }
        return new Integer((int)Math.round(cell.getNumericCellValue()));
    }
    
    public final static Float getFloatValue(HSSFCell cell){
        if (cell == null)
            return null;
        if (cell.getCellType() != HSSFCell.CELL_TYPE_NUMERIC){
            throw new IllegalArgumentException("cell must NUMBER type");
        }
        return new Float((float)cell.getNumericCellValue());
    }
    
    public final static Date getDateValue(HSSFCell cell){
        if (cell == null){
            return null;
        }
        
        if (cell.getCellType() != HSSFCell.CELL_TYPE_NUMERIC){
            throw new IllegalArgumentException("cell must NUMBER type");
        }
        
        return cell.getDateCellValue();
    }
    
    /**
     * ��ȡ����е������ַ��÷��������ڳ����ε����֣�Excel������������ֻ�����Ҫ�ַ�
     * ���Ҫ��ȡ���֣���ʹ����ȷ����Ӧ������
     * @param cell
     * @return
     */
    public static String getNumberStringValue(HSSFCell cell){
        if (cell == null){
            return null;
        }
        
        String value = null;
        if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING){
            value = cell.getStringCellValue();
        }
        else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
            value = String.valueOf((long)cell.getNumericCellValue());
        }
        
        return value;
    }
    
    public static String getString(HSSFCell cell){
        String value = "";
        if (cell == null){
            return "";
        }

        switch (cell.getCellType()){
            case HSSFCell.CELL_TYPE_STRING:
                value = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                value = new Double(cell.getNumericCellValue()).toString();
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                value = "";
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                value = String.valueOf(cell.getBooleanCellValue());
                break;                
            case HSSFCell.CELL_TYPE_ERROR:
                value = String.valueOf(cell.getErrorCellValue());
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                value = "formula";
                break;
            default : break;
        }
        return value;
    }
    
    public static void setCellValue(Object value,String colType, HSSFCell cell) {
        if("number".equals(colType.trim())){
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            System.out.println("value:" + value);
            if(value != null) {
                if(value.toString().contains(".")){
                    cell.setCellValue(Double.parseDouble(value.toString()));  //double����
                }else{
                    if(value == null ||"".equals(value.toString().trim())
                    		||"null".equals(value.toString().trim())
                    		|| "0".equals(value.toString().trim())){
                        System.out.println("===========");
                        cell.setCellValue("");
                    }else{
                        cell.setCellValue(Integer.parseInt(value.toString()));   //int����
                    }
                }
            }else{
                cell.setCellValue("");
            }
        }else{
            cell.setCellType(Cell.CELL_TYPE_STRING);
            if("date".equals(colType.trim())){              //��������
                try {
                    if(value != null) {
                        cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").parse(value.toString()));
                    }
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }else{
                if(value != null) {
                    cell.setCellValue(new HSSFRichTextString(value.toString()));//�ַ�����
                }
            }
        }
        //���õ�Ԫ��Ŀ��
       
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
    public static Workbook drawingPatriarch(Workbook wb, String svg, int dx1, int dy1,
                                      int dx2, int dy2, int col1, int row1, int col2, int row2, String sheetName) throws IOException, TranscoderException {
        if(StrUtils.isEmpty(svg)){
            return wb;
        }else{
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            SvgPngConverter.convertToPng(svg, outputStream);

            // 获取sheet页
            Sheet sheet = wb.getSheet(sheetName);
            // 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
            Drawing patriarch = sheet.createDrawingPatriarch();
            // anchor主要用于设置图片的属性
            ClientAnchor anchor = new XSSFClientAnchor(dx1, dy1, dx2, dy2, (short) col1, row1, (short) col2, row2);
            anchor.setAnchorType( 3 );
            // 插入图片
            patriarch.createPicture( anchor,
                    wb.addPicture(outputStream.toByteArray(), XSSFWorkbook.PICTURE_TYPE_PNG ) );
            return wb;
        }
    }
}
