package com.org.mokey.analyse.action;

import com.org.mokey.analyse.entiy.CallingBill;
import com.org.mokey.analyse.service.FreeCallService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.ApDateTime;
import com.org.mokey.common.util.highcharts.HighchartsUtil;
import com.org.mokey.demo.util.ServletDownloadUtil;
import com.org.mokey.report.action.StatisticsAction;
import com.org.mokey.util.StrUtils;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Maryn on 2016/6/7.
 */
@Controller
public class FreeCallAction extends AbstractAction {

    @Autowired
    private FreeCallService freeCallService;

    @Autowired
    private StatisticsAction statisticsAction;

    public String getCallingBill() {
        String sDate = getParameter("sDate");// 起始时间
        String eDate = getParameter("eDate");// 截止时间
        // 默认时间是当前时间往前推6天
        if (StrUtils.isEmpty(sDate)) {
            sDate = ApDateTime.dateAdd("d", -6, new java.util.Date(), ApDateTime.DATE_TIME_YMD);
        }
        if (StrUtils.isEmpty(eDate)) {
            eDate = ApDateTime.getNowDateTime(ApDateTime.DATE_TIME_YMD);
        }

//        sDate = "2016-06-05";
//        eDate = "2016-06-07";
        /** 查询数据 */
        List<CallingBill> bills = freeCallService.getCallingBill(sDate, eDate);
        getRequest().getSession().setAttribute("sDate", sDate);
        getRequest().getSession().setAttribute("eDate", eDate);
        getRequest().getSession().setAttribute("bills", bills);


        /** 生成图标JSON字符串 */
        List<String> nowDays = ApDateTime.getDayBetween(sDate, eDate);
        int max = nowDays.size();

        String[] names = new String[]{"e键免费通话", "飞语免费通话"};

        String[] xtitle = new String[max];
        String[][] data = new String[2][max];
        int flag = 0;
        List<CallingBill> subBills = bills.subList(0, bills.size() - 1);
        for (CallingBill bill : subBills) {
            xtitle[flag] = bill.getDate();
            data[0][flag] = StrUtils.emptyOrString(bill.getEkCallingTime());
            data[1][flag] = StrUtils.emptyOrString(bill.getFyCallingTime());
            flag++;
        }
        //查询数据
        String chartData = null;
        try {
            chartData = HighchartsUtil.getLine(sDate + "到" + eDate + "免费通话账单比对", xtitle, names, data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getRequest().setAttribute("chartData", chartData);

        return "toList";
    }

    /**
     * 跳转到飞语通话详情页面
     *
     * @return
     */
    public String toCallDetailList() {
        String sDate = "";// 开始时间
        String eDate = "";// 结束时间
        if (StrUtils.isEmpty(sDate)) {
            sDate = ApDateTime.dateAdd("d", -6, new java.util.Date(), ApDateTime.DATE_TIME_YMD);
        }
        if (StrUtils.isEmpty(eDate)) {
            eDate = ApDateTime.getNowDateTime(ApDateTime.DATE_TIME_YMD);
        }
        getRequest().setAttribute("sDate", sDate);
        getRequest().setAttribute("eDate", eDate);
        return "toCallDetailList";
    }

    /***
     * 查询飞羽通话详情
     *
     * @return
     */
    public String selectCallDetailList() {
        String sDate = getParameter("sDate");// 开始时间
        String eDate = getParameter("eDate");// 结束时间
        String phone = getParameter("phone");//手机号
        String fyid  = getParameter("fyid");
        Map<String, Object> retmap = new HashMap<String, Object>();//其他数据
        log.info("into FreeCallAction.selectCallDetailList");
        try {
            List<Map<String, Object>> list = freeCallService.selectCallDetailList(sDate, eDate, phone,fyid);
            /***存放对象****/
            List<List<Object>> callDetialBillList = new ArrayList<>();
            for (Map map : list) {
                List<Object> rowList = new ArrayList<>();
                rowList.add(StrUtils.emptyOrString(map.get("DAY")));
                rowList.add(map.get("C_FYACCID"));
                rowList.add(StrUtils.emptyOrString(map.get("C_GLOBALMOBILEPHONE")));
                rowList.add(StrUtils.emptyOrString(map.get("EKSTIME")));
                rowList.add(StrUtils.emptyOrString(map.get("EKETIME")));
                rowList.add(StrUtils.emptyOrString(map.get("EKTIME")));
                callDetialBillList.add(rowList);
            }
            retmap.put("list", list);
            getRequest().getSession().setAttribute("callDetialBillList", callDetialBillList);
            getRequest().getSession().setAttribute("sDate", sDate);
            getRequest().getSession().setAttribute("eDate", eDate);
            getRequest().getSession().setAttribute("phone", phone);
            this.writeJSONToResponse(retmap);
        } catch (Exception e) {
            log.error("FreeCallAction.selectCallDetailList failed, e : " + e);
        }

        return NONE;
    }

    /***
     * 消费记录导出
     *
     * @return
     */
    public String exportCallDetailList() {
        List<List<Object>> list =new ArrayList<>();
        Object obj=getRequest().getSession().getAttribute("callDetialBillList");
        if (obj instanceof ArrayList<?>){
            list =(ArrayList<List<Object>>)obj;
        }
        String sDate =StrUtils.emptyOrString(getRequest().getSession().getAttribute("sDate"));
        String eDate =StrUtils.emptyOrString(getRequest().getSession().getAttribute("eDate"));
        String phone =StrUtils.emptyOrString(getRequest().getSession().getAttribute("phone"));
        String contentType = "application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        String headerKey = "Content-Disposition";
        List<List<Object>> o = list;
        String title = "";
        if (StrUtils.isEmpty(phone)) {
            title = sDate + "至" + eDate+"飞语用户通话详情";
        } else {
            title =phone+"飞语用户"+ sDate + "至" + eDate+"通话记录详情";
        }
        String[] headerArr = new String[]{"日期", "飞语账号","手机号","开始时间","结束时间","时长(分)"};
        String headerValue = null;
        try {
            headerValue = String.format("attachment; filename=\"%s\"",
                    new String(title.getBytes(Charset.forName("UTF-8")), "ISO8859-1") + ".xlsx");
        } catch (UnsupportedEncodingException e) {
            log.error("文件名编码错误", e);
        }
        if (o == null) {// 理论上不应该为空
            return NONE;
        }
        Workbook wb = new XSSFWorkbook();//创建HSSFWorkbook对象(excel的文档对象)
        Sheet sheet = wb.createSheet(title);//建立新的sheet对象（excel的表单）
        Header header = sheet.getHeader();// 设置头信息
        header.setCenter("Center Header");
        header.setRight(HSSFHeader.font("Stencil-Normal", "Italic") +
                HSSFHeader.fontSize((short) 16) + "Right w/ Stencil-Normal Italic font and size 16");
        Row row = sheet.createRow(0);// 创建首行
        Cell cell = row.createCell(0);// 创建首列
        CellRangeAddress region = new CellRangeAddress(
                0, //first row (0-based)
                0, //last row  (0-based)
                0, //first column (0-based)
                5  //last column  (0-based)
        );
        sheet.addMergedRegion(region);
        XSSFCellStyle titleStyle = statisticsAction.getCellStyle(wb, false, true, Font.COLOR_NORMAL, (short) 15, "华文楷体", true, null);
        cell.setCellStyle(titleStyle);
        cell.setCellValue(title);

        Row row_1 = sheet.createRow(1);
        for (int i = 0; i < o.get(0).size(); ++i) {
            Cell cell_1 = row_1.createCell(i);
            cell_1.setCellValue(headerArr[i].toString());

        }
        for (int j = 0; j < o.size(); ++j) {
            //创建多少行
            Row row_j = sheet.createRow(j + 2);
            for (int i = 0; i < o.get(j).size(); ++i) {
                Cell cell_j = row_j.createCell(i);
                cell_j.setCellValue(o.get(j).get(i).toString());
            }
        }
        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream(); // 创建字节数组输出流
            wb.write(out);// 将workbook写入输出流
            byte[] buf = out.toByteArray();// 将输出流转成字节数组
            in = new ByteArrayInputStream(buf);// 读入输入流
            ServletDownloadUtil.doDownload(getResponse(), in, contentType, headerKey, headerValue); // 下载
        } catch (Exception e) {
            e.printStackTrace();
        }
        return NONE;
    }

    public String exp() {
        String title = getParameter("title");
        String svg = getParameter("svg");
        List<CallingBill> bills = (List<CallingBill>) getRequest().getSession().getAttribute("bills");
        String[] head = new String[]{"日 期", "e 键", "飞 语", "差 值"};

        Workbook wb = new XSSFWorkbook();
        // 创建sheet页
        String sheetName = "通话账单";
        Sheet sheet = wb.createSheet(sheetName);

        // 设置列宽
        for (int i = 0; i < 5; i++) {
            sheet.setColumnWidth((short) i, (short) ((80 * 2.5) / ((double) 1 / 20)));
        }

        Header header = sheet.getHeader();
        header.setCenter("Center Header");
        header.setLeft("Left Header");
        header.setRight(HSSFHeader.font("Stencil-Normal", "Italic") +
                HSSFHeader.fontSize((short) 16) + "Right w/ Stencil-Normal Italic font and size 16");

        /***** 标题  *****/
        Row row_0 = sheet.createRow(0);// 创建首行
        Cell cell = row_0.createCell((short) (1));// 创建首列
        cell.setCellValue(title);

        XSSFCellStyle titleStyle = statisticsAction.getCellStyle(wb, false, true, Font.COLOR_RED, (short) 14, "华文楷体", false, null);
        cell.setCellStyle(titleStyle);

        CellRangeAddress region = new CellRangeAddress(0, 1, 1, 4);
        sheet.addMergedRegion(region);

        for (int i = -1; i < bills.size(); i++) {
            if (i == -1) {// 表头
                Row row_i = sheet.createRow(i + 3);
                for (int j = 0; j < head.length; j++) {
                    Cell cell_j = row_i.createCell(j + 1);
                    cell_j.setCellValue(head[j]);
                    XSSFCellStyle headStyle = statisticsAction.getCellStyle(wb, true,
                            false, Font.BOLDWEIGHT_BOLD, (short) 13, "华文楷体", false,
                            IndexedColors.LIGHT_TURQUOISE.getIndex());
                    cell_j.setCellStyle(headStyle);
                    region = new CellRangeAddress(i + 3, i + 3, j + 1, j + 1);
                    sheet.addMergedRegion(region);
                    RegionUtil.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM, region, sheet, wb);
                    RegionUtil.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM, region, sheet, wb);
                    RegionUtil.setBorderRight(XSSFCellStyle.BORDER_MEDIUM, region, sheet, wb);
                    RegionUtil.setBorderTop(XSSFCellStyle.BORDER_MEDIUM, region, sheet, wb);
                }
            } else {// 内容
                Row row_i = sheet.createRow(i + 3);
                CallingBill bill = bills.get(i);
                for (int j = 0; j < head.length; j++) {
                    Cell cell_j = row_i.createCell(j + 1);
                    switch (j) {
                        case 0:
                            cell_j.setCellValue(bill.getDate());
                            break;
                        case 1:
                            cell_j.setCellValue(bill.getEkCallingTime());
                            break;
                        case 2:
                            cell_j.setCellValue(bill.getFyCallingTime());
                            break;
                        case 3:
                            cell_j.setCellValue(bill.getDifference());
                            break;
                    }
                    region = new CellRangeAddress(i + 3, i + 3, j + 1, j + 1);
                    sheet.addMergedRegion(region);
                    RegionUtil.setBorderBottom(XSSFCellStyle.BORDER_THIN, region, sheet, wb);
                    RegionUtil.setBorderLeft(XSSFCellStyle.BORDER_THIN, region, sheet, wb);
                    RegionUtil.setBorderRight(XSSFCellStyle.BORDER_THIN, region, sheet, wb);
                    RegionUtil.setBorderTop(XSSFCellStyle.BORDER_THIN, region, sheet, wb);
                }
            }
        }
        // 画图
        wb = statisticsAction.drawingPatriarch(wb, svg, 0, 0, 700, 700, (short) 6, 2, (short) 14, 15, sheetName);
        // IOS写出Excel
        String contentType = "application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", URLEncoder.encode(title) + ".xlsx");
        ServletDownloadUtil.write(wb, contentType, headerKey, headerValue, getResponse());
        return NONE;
    }
}
