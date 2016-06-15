package com.org.mokey.common.util.excel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

public class ExcelOperator{

    /**
     * ��Ԫ��Ĭ�Ͽ��
     */
    public static final Integer CELL_DEFAULT_WIDTH = 4000;

    /**
     * �����ַ������ռ���
     */
    public static final Integer SINGLECHAR_DEFAULT_WIDTH = 570;

    /**
     * @throws Exception
     */
    public static ByteArrayOutputStream toExcelByte(String[] title, String[] colType, Object[][] context,
        String sheetName) throws Exception {
        if (title.length != colType.length) {
            return null;
        }
        HSSFWorkbook workbook = new HSSFWorkbook(); // �����µ�Excel������
        HSSFSheet sheet = workbook.createSheet(sheetName); // ��Excel�������н������?��Ϊȱʡ
        
        HSSFRow row = sheet.createRow(0);
        int cellSize = title.length;
        Integer[] cellWidth = new Integer[cellSize];
        // ���õڱ�ͷ��Ϣ
        for (int i = 0; i < cellSize; i++) {
            HSSFCell cell = row.createCell(i, CellStyle.ALIGN_CENTER);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cell.setCellValue(title[i].trim());
            cell.setCellStyle(setTitleCellStyle(workbook));
            // �����ֶ�������Ƶ�Ԫ����
            int number = (title[i].trim().length()) * SINGLECHAR_DEFAULT_WIDTH;
            if (number > CELL_DEFAULT_WIDTH) {
                sheet.setColumnWidth(i, number);
                cellWidth[i] = number;
            } else {
                sheet.setColumnWidth(i, CELL_DEFAULT_WIDTH);
                cellWidth[i] = CELL_DEFAULT_WIDTH;
            }

            // ������ʽ
            //            setSecondStyle(workbook, row, cell);
        }
        
        toWriterRowVertical( workbook,title, context, colType, sheet, 1);

        // д�������Ϣ
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        return bos;
    }
    /**
     * , boolean vertical �Ƿ�����
     * @throws Exception
     */
    public static void toExcel(String[] title, String[] colType, Object[][] context,
        String fileName, String sheetName) throws Exception {
        if (title.length != colType.length) {
            return;
        }
        // ���ñ���
        HSSFWorkbook workbook = new HSSFWorkbook(); // �����µ�Excel������
        HSSFSheet sheet = workbook.createSheet(sheetName); // ��Excel�������н������?��Ϊȱʡ
        
        HSSFRow row = sheet.createRow(0);
        int cellSize = title.length;
        Integer[] cellWidth = new Integer[cellSize];
        // ���õڱ�ͷ��Ϣ
        for (int i = 0; i < cellSize; i++) {
            HSSFCell cell = row.createCell(i, CellStyle.ALIGN_CENTER);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cell.setCellValue(title[i].trim());
            cell.setCellStyle(setTitleCellStyle(workbook));
            // �����ֶ�������Ƶ�Ԫ����
            int number = (title[i].trim().length()) * SINGLECHAR_DEFAULT_WIDTH;
            if (number > CELL_DEFAULT_WIDTH) {
                sheet.setColumnWidth(i, number);
                cellWidth[i] = number;
            } else {
                sheet.setColumnWidth(i, CELL_DEFAULT_WIDTH);
                cellWidth[i] = CELL_DEFAULT_WIDTH;
            }

            // ������ʽ
            //            setSecondStyle(workbook, row, cell);
        }
        
        toWriterRowVertical( workbook,title, context, colType, sheet, 1);
        FileOutputStream fileOut = new FileOutputStream(fileName);
        workbook.write(fileOut);
        fileOut.close();

    }

    private static HSSFCellStyle setTitleCellStyle(HSSFWorkbook wb) {
        /**
         * ���÷��1
         */
        HSSFCellStyle style1 = wb.createCellStyle();
        style1.setBorderBottom(HSSFCellStyle.BORDER_THIN); // ���õ��޸�ı߿�
        style1.setBottomBorderColor(HSSFColor.BLACK.index); // ���õ�Ԫ��ı߿���ɫ��
        style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style1.setLeftBorderColor(HSSFColor.BLACK.index);
        style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style1.setRightBorderColor(HSSFColor.BLACK.index);
        style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style1.setTopBorderColor(HSSFColor.BLACK.index);
        style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);// ��õ�����Pattern
        // ��Ԫ�񱳾�����ʾģʽ��
        style1.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex()); // ���õ�Ԫ�񱳾�ɫ;
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // ˮƽ���뷽ʽ
        // style1.setWrapText(true);//�ı����������ݶ����Զ�����
        // style.setFillPattern(HSSFCellStyle.//);
        // ��������Color,���ȴ���Font����,���font����,Ȼ����Ϊ�����style
        HSSFFont font = wb.createFont();
//        font.setColor(HSSFFont.SS_NONE);
//        // font.setFontHeightInPoints((short)24);
//        font.setFontName("����");
        // font.setItalic(true);
        // font.setStrikeout(true);//���������ɾ����
        //        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//        style1.setFont(font);
        return style1;
    }
    private static HSSFCellStyle setCellStyle(HSSFWorkbook wb) {
        /**
         * ���÷��1
         */
        HSSFCellStyle style1 = wb.createCellStyle();
        style1.setBorderBottom(HSSFCellStyle.BORDER_THIN); // ���õ��޸�ı߿�Ϊ����
        style1.setBottomBorderColor(HSSFColor.BLACK.index); // ���õ�Ԫ��ı߿���ɫ��
        style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style1.setLeftBorderColor(HSSFColor.BLACK.index);
        style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style1.setRightBorderColor(HSSFColor.BLACK.index);
        style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style1.setTopBorderColor(HSSFColor.BLACK.index);
        style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);// ��õ�����Pattern
        // ��Ԫ�񱳾�����ʾģʽ��
        style1.setFillForegroundColor(new HSSFColor.WHITE().getIndex()); // ���õ�Ԫ�񱳾�ɫ;
        //        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // ˮƽ���뷽ʽ
        // style1.setWrapText(true);//�ı����������ݶ����Զ�����
        // style.setFillPattern(HSSFCellStyle.//);
        // ��������Color,���ȴ���Font����,���font����,Ȼ����Ϊ�����style
//        HSSFFont font = wb.createFont();
//        font.setColor(HSSFFont.SS_NONE);
//        // font.setFontHeightInPoints((short)24);
//        font.setFontName("����");
//        // font.setItalic(true);
//        // font.setStrikeout(true);//���������ɾ����
//        //        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//        style1.setFont(font);
        return style1;
    }

    /**
     * @param context
     *            txt�ļ�����
     * @param n
     *            �ӵڼ��п�ʼ�����
     * @param sheet
     *            excel������
     * @param m
     *            �ӵڼ��п�ʼд���
     * @throws Exception
     */
    private static void toWriterRowVertical(HSSFWorkbook workbook,String[] title, Object[][] context, String[] colType,
        HSSFSheet sheet, int startRow) throws Exception {
        HSSFRow row = null;
        HSSFCell cell = null;
        for (int j = 0; j < context.length; j++) {
            row = sheet.createRow(j + startRow);
            //            row.setHeight((short) 400);
            for (int i = 0; i < title.length; i++) {
                cell = row.createCell(i);
                cell.setCellStyle(setCellStyle(workbook));
                ExcelUtil.setCellValue(context[j][i], colType[i], cell);
            }
        }
    }
    /**
     * @param context
     *            txt�ļ�����
     * @param n
     *            �ӵڼ��п�ʼ�����
     * @param sheet
     *            excel������
     * @param m
     *            �ӵڼ��п�ʼд���
     * @throws Exception
     */
    private static void toWriterRow(HSSFWorkbook workbook,final Object[][] context, String[] colType,
        Integer[] cellWidth, HSSFSheet sheet, int startRow) throws Exception {
        HSSFRow row = null;
        HSSFCell cell = null;
        for (int i = 0; i < context.length; i++) {
            row = sheet.createRow(i + startRow);
            //            row.setHeight((short) 400);
            for (int j = 0; j < context[i].length; j++) {
                cell = row.createCell(j);
                cell.setCellStyle(setCellStyle(workbook));
                ExcelUtil.setCellValue(context[i][j], colType[j], cell);
                int number = (context[i][j].toString().trim().length()) * SINGLECHAR_DEFAULT_WIDTH;
                if (number > cellWidth[i]) {
                    sheet.setColumnWidth(i, number);
                    cellWidth[i] = number;
                }
            }
        }
    }

    public static void main(String[] args) {
        String[] title = new String[] {"ʱ��", "����", "����" };
        String[] type = new String[] {"string", "number", "number" };
        Object[][] data = new Object[][] { {"2011-01-02 12:00", "12", "234" }, {"2011-01-02 12:15", "23", "454" },
            {"2011-01-02 12:30", "12", "234" }, {"2011-01-02 12:45", "23", "454" } ,
        {"2011-01-02 12:30", "12", "234" }, {"2011-01-02 12:45", "23", "454" },
        {"2011-01-02 12:30", "12", "234" }, {"2011-01-02 12:45", "23", "454" } ,
        {"2011-01-02 12:30", "12", "234" }, {"2011-01-02 12:45", "23", "454" },
        {"2011-01-02 12:30", "12", "234" }, {"2011-01-02 12:45", "23", "454" } };
        String filename = "d:\\aa.xls";
        String filenameb = "d:\\aabb.xls";
        try {
            ExcelOperator.toExcel(title, type, data, filename, "a");
//            ByteArrayOutputStream ss = ExcelOperator.toExcelByte(title,  type, data, "a", false);
//            OutputStream o = new FileOutputStream(new File(filenameb));
//            o.write(ss.toByteArray());
//            o.flush();
//            o.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
