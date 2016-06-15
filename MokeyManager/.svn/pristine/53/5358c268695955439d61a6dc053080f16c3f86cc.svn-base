package com.org.mokey.analyse.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressBase;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

/**
 * Created by vpc on 2016/4/5.
 */
public class ExcelUtil {
    /**
     * 默认只设置居中
     * @param wb Workbook 对象
     * @return
     */
    public CellStyle getDefaultCellStyle(Workbook wb) {
        // 定义单元格对齐样式
        CellStyle cellStyle = wb.createCellStyle();
        // 居中对齐
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

        return cellStyle;
    }

    /**
     * 设置默认前景色
     * @param cellStyle
     */
    public void setDefaultForegroundColor(CellStyle cellStyle) {
        // 设置前景色
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
    }

    /**
     * 设置默认边框
     * @param cellStyle
     */
    public void setDefaultBorder(CellStyle cellStyle) {
        // 设置边框
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);
    }

    /**
     * 设置合并单元格样式
     * @param sheet
     * @param cellStyle
     * @param cellRangeAddressBase
     */
    public void setRegionStyle(Sheet sheet, CellStyle cellStyle, CellRangeAddressBase cellRangeAddressBase) {
        int firstRow = cellRangeAddressBase.getFirstRow();
        int lastRow = cellRangeAddressBase.getLastRow();
        int firstColumn = cellRangeAddressBase.getFirstColumn();
        int lastColumn = cellRangeAddressBase.getLastColumn();

        for(int i = firstRow; i <= lastRow; ++i) {
            Row row = sheet.getRow(i);
            if(row == null) {
                row = sheet.createRow(i);
            }
            for(int j = firstColumn; j <= lastColumn; ++j) {
                Cell cell = row.getCell(j);
                if(cell == null) {
                    cell = row.createCell(j);
                }
                cell.setCellStyle(cellStyle);
            }
        }
    }
}
