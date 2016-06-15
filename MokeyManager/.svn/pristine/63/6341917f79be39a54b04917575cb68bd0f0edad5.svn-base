package com.org.mokey.analyse.action;

import com.org.mokey.analyse.service.EkeyKeyUsingService;
import com.org.mokey.analyse.service.UserAnalyseService;
import com.org.mokey.analyse.util.ExcelUtil;
import com.org.mokey.analyse.util.Singletons;
import com.org.mokey.analyse.vo.SupVo;
import com.org.mokey.analyse.vo.TimeVo;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.ApDateTime;
import com.org.mokey.common.util.highcharts.HighchartsUtil;
import com.org.mokey.demo.util.ServletDownloadUtil;
import com.org.mokey.report.action.StatisticsAction;
import com.org.mokey.util.StrUtils;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 用户数据分析--日
 * Created by Maryn on 2016/1/18.
 */
@Component
public class UserAnalyseAction extends AbstractAction {


    @Autowired
    private EkeyKeyUsingService ekeyKeyUsingService;
    @Autowired
    private UserAnalyseService userAnalyseService;
    @Autowired
    private StatisticsAction statisticsAction;

    /**
     * 数据分析
     *
     * @return
     */
    public String analyse() {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        String sup = getParameter("sup");// 供应商
        String userId = getSessionLoginUser().getUserId();// 当前用户id
        //String userId = "1b877d3f-09b3-4011-b9f8-837868251578";// 当前用户id
        String year = getParameter("year");// 年份
        String sDate = getParameter("sDate");// 开始时间
        String eDate = getParameter("eDate");// 结束时间
        String dataType = getParameter("dataType");// 数据类型：1:下载情况、2:激活情况、3:注册情况、4:活跃情况、5:留存
        String timeType = getParameter("timeType");// 时间类型：1:日、2:周、3:月
        log.info("into analyse...");
        log.info("sup = " + sup + ", year = " + year + ", sDate = "
                + sDate + ", eDate = " + eDate + ", userId = " + userId
                + ", dataType = " + dataType + ", timeType = " + timeType);
        try {
            if (dataType.equals("1")) {
                reqMap = userAnalyseService.downloadUser(userId, sup, sDate, eDate, timeType, year, dataType);
            }
            if (dataType.equals("2")) {
                reqMap = userAnalyseService.activationUser(userId, sup, sDate, eDate, timeType, year, dataType);
            }
            if (dataType.equals("3")) {
                reqMap = userAnalyseService.registerUser(userId, sup, sDate, eDate, timeType, year, dataType);
            }
            if (dataType.equals("4")) {
                reqMap = userAnalyseService.activeUser(userId, sup, sDate, eDate, timeType, year, dataType);
            }
            if (dataType.equals("5")) {
                reqMap = userAnalyseService.stayingUser(userId, sup, sDate, eDate, timeType, year, dataType);
            }

            getRequest().getSession().setAttribute("analyseData", reqMap);

            try {
                // HighChats线型图
                lineGraph(timeType, dataType, sDate, eDate, reqMap);
            } catch (Exception e) {
                log.error("get HighChats failed ! dataType : [" + dataType +
                        "] time : [year >>> " + StrUtils.emptyOrString(year)
                        + " sDate >>> " + sDate + " eDate >>> " + eDate + "]");
            }

            // 回写
            this.writeJSONToResponse(reqMap);
        } catch (Exception e) {
            reqMap.put("status", "N");
            log.error("UserAnalyseOfDayAction.analyse failed ! e : ", e);
        }
        return NONE;
    }

    public String exp() {
        // 页面传过来的svg数组(各个供应商的图表数据和合计图表数据)
        String svg = getRequest().getParameter(ParameterKey.SVG);
        String title = getRequest().getParameter("title");
        String dataStr = getRequest().getParameter("dataStr");
        String exp_dataType = getRequest().getParameter("exp_dataType");

        ExcelUtil excelUtil = new ExcelUtil();

        Workbook wb = new XSSFWorkbook();
        // 表格数据结果
        Map<String, Object> result = null;
        // String userId = getSessionLoginUser().getUserId();
        // String userId = "1b877d3f-09b3-4011-b9f8-837868251578";// 当前用户id
        String contentType = "application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        String headerKey = "Content-Disposition";
        String headerValue = null;
        try {
            //attachment;
            headerValue = String.format("filename=\"%s\"",
                    URLEncoder.encode(title + System.currentTimeMillis() + ".xlsx", "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("文件名编码错误", e);
        }

        List<String> timeStringList = new ArrayList<>();
        List<Map<String, Object>> supVoList = null;

        // 第一个时间单位开始列
        int firstColumn = 1;
        // 默认只有一个时间单位
        int lastColumn = 2;
        // 当前行位置
        int currentRowNum = 0;

        String[] headerArr = new String[]{"本期统计", "上期同比"};

        Map<String, Map<String, Long>> totalNumMap = new LinkedHashMap<String, Map<String, Long>>();

        // sheet 页的名字
        final String sheetName = dataStr;
        Object o = getRequest().getSession().getAttribute("analyseData");

        // 理论上不应该为空
        if (o == null) {
            return NONE;
        }
        result = (Map<String, Object>) o;

        List<String> headTimeList = (List<String>) result.get("timeList");
        if ("2".equals(exp_dataType)) {
            if (StrUtils.isNotEmpty(headTimeList)) {
                for (String timeString : headTimeList) {
                    timeString = timeString.replaceAll("[<]br[>]", "");
                    timeStringList.add(timeString);
                }
            }
        } else {
            timeStringList = (List<String>) result.get("timeList");
        }
        timeStringList.remove(0);
        supVoList = (List<Map<String, Object>>) result.get("dataMap");

        int timeSize = timeStringList.size();
        int supListSize = supVoList.size();

        Sheet sheet = wb.createSheet(sheetName);
        CellStyle cellStyle = excelUtil.getDefaultCellStyle(wb);

        // 创建表头行 前面空一行 0
        Row row = sheet.createRow(currentRowNum + 1);
        currentRowNum++;
        Cell cell = row.createCell(1);
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellValue(title);
        Font font = wb.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints((short) 18);
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
        // 合并第一行第二行，第一列到第九列 下标从0开始
        CellRangeAddress cellRangeAddress = new CellRangeAddress(1, 2, 1, 9);
        excelUtil.setRegionStyle(sheet, cellStyle, cellRangeAddress);
        sheet.addMergedRegion(cellRangeAddress);
        currentRowNum++;

        //多少个时间维度换行
        int step = 4;

        for (int k = 0; k < timeSize; k += step) {
            // 空一行，创建实际数据行
            row = sheet.createRow(currentRowNum + 2);
            currentRowNum += 2;

            cell = row.createCell((short) (0));// 创建首列

            cell.setCellValue("供应商");
            cellStyle = excelUtil.getDefaultCellStyle(wb);
            excelUtil.setDefaultBorder(cellStyle);
            excelUtil.setDefaultForegroundColor(cellStyle);
            cellRangeAddress = new CellRangeAddress(
                    currentRowNum, //first row (0-based)
                    currentRowNum + 1, //last row  (0-based)
                    0, //first column (0-based)
                    0  //last column  (0-based)
            );
            excelUtil.setRegionStyle(sheet, cellStyle, cellRangeAddress);
            sheet.addMergedRegion(cellRangeAddress);
            cell.setCellStyle(cellStyle);

            Row headerRow = null;
            int len = k + 3;
            while (len >= timeSize) {
                --len;
            }
            // 表头行设置
            for (int j = k; j <= len; ++j) {
                cellStyle = excelUtil.getDefaultCellStyle(wb);
                excelUtil.setDefaultBorder(cellStyle);
                excelUtil.setDefaultForegroundColor(cellStyle);
                // 设置时间
                cell = row.createCell(((j % step) * 2) + 1);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                String timeString = timeStringList.get(j);
                Map<String, Long> sumMap = new HashMap<String, Long>();

                // 设置默认合计数量
                sumMap.put(SumKey.downloadSum, 0L);
                sumMap.put(SumKey.activationSum, 0L);

                totalNumMap.put(timeString, sumMap);

                cell.setCellValue(timeString);

                cellRangeAddress = new CellRangeAddress(
                        row.getRowNum(),
                        row.getRowNum(),
                        firstColumn,
                        lastColumn
                );
                excelUtil.setRegionStyle(sheet, cellStyle, cellRangeAddress);
                sheet.addMergedRegion(cellRangeAddress);

                cell.setCellStyle(cellStyle);

                // 下载,同比下载
                if (headerRow == null) {
                    headerRow = sheet.createRow(currentRowNum + 1);
                    currentRowNum++;
                }

                cellStyle = excelUtil.getDefaultCellStyle(wb);
                excelUtil.setDefaultBorder(cellStyle);

                // 设置下载、同比
                for (int h = firstColumn - 1; h < firstColumn + 1; h++) {
                    cell = headerRow.createCell(h + 1);
                    cell.setCellValue(headerArr[h % 2]);
                    cell.setCellStyle(cellStyle);
                }

                firstColumn = lastColumn + 1;
                lastColumn = lastColumn + 2;
            }

            // 填充供应商信息
            for (int i = 0; i < supListSize; ++i) {
                Map<String, Object> supVo = supVoList.get(i);
                row = sheet.createRow(++currentRowNum);
                cellStyle = excelUtil.getDefaultCellStyle(wb);
                excelUtil.setDefaultForegroundColor(cellStyle);
                excelUtil.setDefaultBorder(cellStyle);
                cell = row.createCell(0);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(supVo.get("sup").toString());

                List<Object> timeVoList = (List<Object>) supVo.get("data");
                // 供应商在各个时间的下载数、激活数、注册数、活跃数、留存数填充
                for (int j = k; j <= len; ++j) {
                    int t0 = 2 * j;
                    int t1 = t0 + 1;
                    String timeString = timeStringList.get(j);
                    Map<String, Long> sumMap = new HashMap<String, Long>();
                    cellStyle = excelUtil.getDefaultCellStyle(wb);
                    excelUtil.setDefaultBorder(cellStyle);

                    // 下载
                    cell = row.createCell(((j % step) * 2) + 1);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    cell.setCellStyle(cellStyle);
                    Long downloadNum = Long.parseLong(timeVoList.get(t0).toString());
                    cell.setCellValue(downloadNum);

                    // 激活
                    cell = row.createCell(((j % step) * 2) + 2);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    cell.setCellStyle(cellStyle);
                    Long activationNum = Long.valueOf(timeVoList.get(t1).toString());
                    cell.setCellValue(activationNum);

                    // 合计
                    sumMap.put(SumKey.downloadSum, totalNumMap.get(timeString).get(SumKey.downloadSum) + downloadNum);
                    sumMap.put(SumKey.activationSum, totalNumMap.get(timeString).get(SumKey.activationSum) + activationNum);


                    totalNumMap.put(timeString, sumMap);
                }
            }

            // 合计行
            row = sheet.createRow(++currentRowNum);
            cell = row.createCell(0);
            cell.setCellValue("合计");
            cellStyle = excelUtil.getDefaultCellStyle(wb);
            excelUtil.setDefaultBorder(cellStyle);
            excelUtil.setDefaultForegroundColor(cellStyle);
            cell.setCellStyle(cellStyle);

            cellStyle = excelUtil.getDefaultCellStyle(wb);
            excelUtil.setDefaultBorder(cellStyle);
            int j = k;
            for (Iterator<String> iterator = totalNumMap.keySet().iterator(); iterator.hasNext(); ++j) {
                String timeString = iterator.next();
                Map<String, Long> sumMap = totalNumMap.get(timeString);
                // 当前合计
                cell = row.createCell(((j % step) * 2) + 1);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(sumMap.get(SumKey.downloadSum));

                // 同期合计
                cell = row.createCell(((j % step) * 2) + 2);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(sumMap.get(SumKey.activationSum));
            }

            // 清空合计map，不然循环会有影响
            totalNumMap.clear();

            // 重置首列和默认最后一列位置
            firstColumn = 1;
            lastColumn = 2;
        }

        log.info("current row num : " + currentRowNum);
        try {
            //画图

            com.org.mokey.common.util.excel.ExcelUtil.drawingPatriarch(wb, svg, 0, 0, 413, 957, (short) 0, currentRowNum + 2, (short) 11, currentRowNum + 23, sheetName);

        } catch (IOException e) {
            log.error("综合报表导出异常", e);
        } catch (TranscoderException e) {
            log.error("综合报表导出异常", e);
        }

        write(wb, contentType, headerKey, headerValue);

        return NONE;
    }

    private void write(Workbook wb, String contentType, String headerKey, String headerValue) {
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
            // 下载
            ServletDownloadUtil.doDownload(getResponse(), in, contentType, headerKey, headerValue);
        } catch (Exception e) {
            log.error("导出综合统计数据出错", e);
        }
    }

    // 查询用户可见的品牌
    public String getBrands() {
        Map<String, Object> reqMap = new HashMap<>();
        String userId = getSessionLoginUser().getUserId();
        log.info("into getBrands.........");
        log.info("userId = " + userId);
        try {
            List<Map<String, Object>> brands = userAnalyseService.getBrands(userId);
            List<Map<String, Object>> suppliers = userAnalyseService.getSuppliers(userId);

            reqMap.put("brands", brands);
            reqMap.put("suppliers", suppliers);
            // 回写
            this.writeJSONToResponse(reqMap);
        } catch (Exception e) {
            reqMap.put("status", "N");
            log.error("UserAnalyseOfDayAction.getBrands failed ! e : ", e);
        }
        return NONE;
    }

    // 查询指定品牌的所有渠道
    public String getSuppliersByBrand() {
        Map<String, Object> reqMap = new HashMap<>();
        String brand = getParameter("brand");// 品牌id
        String userId = getSessionLoginUser().getUserId();
        log.info("into getSuppliersByBrand.........");
        log.info("brand = " + brand + ", userId = " + userId);
        try {
            List<Map<String, Object>> suppliers = userAnalyseService.getSuppliersByBrand(brand, userId);


            reqMap.put("suppliers", suppliers);
            // 回写
            this.writeJSONToResponse(reqMap);
        } catch (Exception e) {
            reqMap.put("status", "N");
            log.error("UserAnalyseOfDayAction.getSuppliersByBrand failed ! e : ", e);
        }
        return NONE;
    }

    // 查询用户可见的供应商
    public String getSuppliers() {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        String userId = getSessionLoginUser().getUserId();
//        String userId = "1b877d3f-09b3-4011-b9f8-837868251578";// 当前用户id
        log.info("into analyse.........");
        log.info("userId = " + userId);
        try {
            List<Map<String, Object>> suppliers = userAnalyseService.getSuppliers(userId);

            reqMap.put("suppliers", suppliers);
            // 回写
            this.writeJSONToResponse(reqMap);
        } catch (Exception e) {
            reqMap.put("status", "N");
            log.error("UserAnalyseOfDayAction.analyse failed ! e : ", e);
        }
        return NONE;
    }


    /**
     * 最新综合数据
     *
     * @return
     */

    public String AllComplexStatistics() {

        String sDate = getParameter(ParameterKey.sDate);// 开始时间
        String eDate = getParameter(ParameterKey.eDate);// 结束时间

        if (StrUtils.isEmpty(sDate)) {// 默认起始时间为今天的六天前
            sDate = ApDateTime.dateAdd("d", -7, new java.util.Date(), ApDateTime.DATE_TIME_YMD);
        }
        if (StrUtils.isEmpty(eDate)) {// 默认截止日期为今天
            eDate = ApDateTime.dateAdd("d", -1, new java.util.Date(), ApDateTime.DATE_TIME_YMD);
        }

        getRequest().setAttribute(ParameterKey.sDate, sDate);
        getRequest().setAttribute(ParameterKey.eDate, eDate);

        return "complexAll";
    }

    /**
     * 综合数据统计分析及导出
     *
     * @return
     */
    public String toAnalyseUser() {
        String sDate = getParameter(ParameterKey.sDate);// 开始时间
        String eDate = getParameter(ParameterKey.eDate);// 结束时间

        if (StrUtils.isEmpty(sDate)) {// 默认起始时间为今天的六天前
            sDate = ApDateTime.dateAdd("d", -7, new java.util.Date(), ApDateTime.DATE_TIME_YMD);
        }
        if (StrUtils.isEmpty(eDate)) {// 默认截止日期为今天
            eDate = ApDateTime.dateAdd("d", -1, new java.util.Date(), ApDateTime.DATE_TIME_YMD);
        }

        getRequest().setAttribute(ParameterKey.sDate, sDate);
        getRequest().setAttribute(ParameterKey.eDate, eDate);
        return "toAnalyseUser";
    }
    private static String getRate(int count, int total) {
        DecimalFormat df = new DecimalFormat("#.00");
        if (count == 0 || total == 0) {
            return "0.00%";
        } else {
            double result = Double.parseDouble(df.format((double) count * 100 / total));
            return result + "%";
        }
    }
    /**
     *基础数据封装
     */
    public void packagingBaseData(List<Map<String,Object>> list,List<String> idList){
        Singletons.getInstance().getBaseMap().clear();
        Singletons.getInstance().getTotalMap().clear();
        List<List<Object>> baseList =new ArrayList<>();
        int downCount =0;//下载总数
        int activeCount =0;//激活总数
        int registerCount =0;//注册总数
        int freeCallCount=0;//免费通话总数
        int allStart=0;
        int allUserKey=0;
        int allUser=0;
        int allRegUse=0;
        float allRegUseRate=0;
        int allNRegUse=0;
        float allNRegUseRate=0;

        int DayRegCount=0;//日留存总数
        float DayRegRate=0;//日注册率
        int WeekRegCount=0;
        float WeekRegRate=0;
        int N_WeekRegCount=0;
        float N_WeekRegRate=0;
        int MonthRegCount=0;
        float MonthRegRate=0;
        int N_MonthRegCount=0;
        float N_MonthRegRate=0;

        int dayActivityCount=0;//日激活留存
        float dayActivityRate=0;//日激活率
        int WeekActivityCount=0;
        float WeekActivityRate=0;
        int N_WeekActivityCount=0;
        float N_WeekActivityRate=0;
        int MonthActivityCount=0;
        float MonthActivityRate=0;
        int N_MonthActivityCount=0;
        float N_MonthActivityRate=0;


        int dayFyCount=0;//日免费通话数
        float dayFyRate=0;//日免费通话率
        int dayFyTime =0;//日免费通话时间
        int WeekFyCount=0;
        float WeekFyRate=0;
        int WeekFyTime =0;
        int N_WeekFyCount=0;
        float N_WeekFyRate=0;
        int N_WeekFyTime =0;
        int MonthFyCount=0;
        float MonthFyRate=0;
        int MonthFyTime =0;
        int  N_MonthFyCount=0;
        float  N_MonthFyRate=0;
        int  N_MonthFyTime =0;
        List<Object> totalList = new ArrayList<>();
           /*  = new ArrayList<>();*/
        /*************************基础数据封装****************************/

        int baseSize=0;
        for(Map map : list){
            baseSize++;
            List<Object> rowList =new ArrayList<>();
            Object C_DATE=map.get("C_DATE");
            rowList.add(C_DATE);
            Object C_DOWNLOAD=map.get("C_DOWNLOAD");
            rowList.add(C_DOWNLOAD);
            downCount+=Integer.parseInt(StrUtils.emptyOrString(C_DOWNLOAD));//下载总数
            Object C_ACTIVE=map.get("C_ACTIVE");
            rowList.add(C_ACTIVE);
            activeCount+=Integer.parseInt(StrUtils.emptyOrString(C_ACTIVE));//激活总数
            Object C_ACTIVE_RATE=map.get("C_ACTIVE_RATE");
            rowList.add(C_ACTIVE_RATE);
            Object C_REGISTER=map.get("C_REGISTER");
            rowList.add(C_REGISTER);
            registerCount+=Integer.parseInt(StrUtils.emptyOrString(C_REGISTER));//注册总数
            Object C_REGISTER_RATE=map.get("C_REGISTER_RATE");
            rowList.add(C_REGISTER_RATE);
            Object C_START=map.get("C_START");
            rowList.add(C_START);
            allStart+=Integer.parseInt(StrUtils.emptyOrString(C_START));
            Object C_USEKEY=map.get("C_USEKEY");
            rowList.add(C_USEKEY);
            allUserKey+=Integer.parseInt(StrUtils.emptyOrString(C_USEKEY));
            Object C_USE=map.get("C_USE");
            rowList.add(C_USE);

            allUser+=Integer.parseInt(StrUtils.emptyOrString(C_USE));
            Object C_REG_COUNT=map.get("C_REG_COUNT");
            rowList.add(C_REG_COUNT);
            allRegUse+=Integer.parseInt(StrUtils.emptyOrString(C_REG_COUNT));
            Object C_REG_COUNT_RATE=map.get("C_REG_COUNT_RATE");
            rowList.add(C_REG_COUNT_RATE);
            allRegUseRate+=Float.parseFloat(StrUtils.emptyOrString(C_REG_COUNT_RATE).replace("%",""));
            Object C_N_REG_COUNT=map.get("C_N_REG_COUNT");
            rowList.add(C_N_REG_COUNT);
            allNRegUse+=Integer.parseInt(StrUtils.emptyOrString(C_N_REG_COUNT));
            Object C_N_REG_COUNT_RATE=map.get("C_N_REG_COUNT_RATE");
            rowList.add(C_N_REG_COUNT_RATE);
            allNRegUseRate+=Float.parseFloat(StrUtils.emptyOrString(C_N_REG_COUNT_RATE).replace("%",""));
            Object C_USE_FREECALL=StrUtils.emptyOrString(map.get("C_USE_FREECALL"));
            freeCallCount+=Integer.parseInt(StrUtils.emptyOrString(C_USE_FREECALL));//免费通话总数
            if(idList.contains("1") && idList.contains("4")){
                Object C_REG_D=map.get("C_REG_D");
                rowList.add(C_REG_D);
                Object C_REG_D_RATE=map.get("C_REG_D_RATE");
                rowList.add(C_REG_D_RATE);
                DayRegCount+=Integer.parseInt(StrUtils.emptyOrString(C_REG_D));//注册日留存总数
            }
            if(idList.contains("1") && idList.contains("5")){
                Object C_REG_W_END=map.get("C_REG_W_END");
                rowList.add(C_REG_W_END);
                WeekRegCount+=Integer.parseInt(StrUtils.emptyOrString(C_REG_W_END));
                Object C_REG_W_END_RATE=map.get("C_REG_W_END_RATE");
                rowList.add(C_REG_W_END_RATE);
                WeekRegRate+=Float.parseFloat(StrUtils.emptyOrString(C_REG_W_END_RATE).replace("%",""));
            }
            if(idList.contains("1") && idList.contains("6")){
                Object C_REG_W_ALL=map.get("C_REG_W_ALL");
                rowList.add(C_REG_W_ALL);
                N_WeekRegCount+=Integer.parseInt(StrUtils.emptyOrString(C_REG_W_ALL));
                Object C_REG_W_ALL_RATE=map.get("C_REG_W_ALL_RATE");
                rowList.add(C_REG_W_ALL_RATE);
                N_WeekRegRate+=Float.parseFloat(StrUtils.emptyOrString(C_REG_W_ALL_RATE).replace("%",""));
            }

            if(idList.contains("1") && idList.contains("7")){
                Object C_REG_M_END=map.get("C_REG_M_END");
                rowList.add(C_REG_M_END);
                MonthRegCount+=Integer.parseInt(StrUtils.emptyOrString(C_REG_M_END));
                Object C_REG_M_END_RATE=map.get("C_REG_M_END_RATE");
                rowList.add(C_REG_M_END_RATE);
                MonthRegRate+=Float.parseFloat(StrUtils.emptyOrString(C_REG_M_END_RATE).replace("%",""));
            }
            if(idList.contains("1") && idList.contains("8")){
                Object C_REG_M_ALL=map.get("C_REG_M_ALL");
                rowList.add(C_REG_M_ALL);
                N_MonthRegCount+=Integer.parseInt(StrUtils.emptyOrString(C_REG_M_ALL));
                Object C_REG_M_ALL_RATE=map.get("C_REG_M_ALL_RATE");
                rowList.add(C_REG_M_ALL_RATE);
                N_MonthRegRate+=Float.parseFloat(StrUtils.emptyOrString(C_REG_M_ALL_RATE).replace("%",""));
            }
            if(idList.contains("2") && idList.contains("4")){
                Object C_ACT_D=map.get("C_ACT_D");
                rowList.add(C_ACT_D);
                Object C_ACT_D_RATE=map.get("C_ACT_D_RATE");
                rowList.add(C_ACT_D_RATE);
                dayActivityCount+=Integer.parseInt(StrUtils.emptyOrString(C_ACT_D));//激活日留存总数
            }
            if(idList.contains("2") && idList.contains("5")){
                Object C_ACT_W_END=map.get("C_ACT_W_END");
                rowList.add(C_ACT_W_END);
                N_MonthRegCount+=Integer.parseInt(StrUtils.emptyOrString(C_ACT_W_END));
                Object C_ACT_W_END_RATE=map.get("C_ACT_W_END_RATE");
                rowList.add(C_ACT_W_END_RATE);
                WeekActivityRate+=Float.parseFloat(StrUtils.emptyOrString(C_ACT_W_END_RATE).replace("%",""));
            }

            if(idList.contains("2") && idList.contains("6")){
                Object C_ACT_W_ALL=map.get("C_ACT_W_ALL");
                rowList.add(C_ACT_W_ALL);
                N_WeekActivityCount+=Integer.parseInt(StrUtils.emptyOrString(C_ACT_W_ALL));
                Object C_ACT_W_ALL_RATE=map.get("C_ACT_W_ALL_RATE");
                rowList.add(C_ACT_W_ALL_RATE);
                N_WeekActivityRate+=Float.parseFloat(StrUtils.emptyOrString(C_ACT_W_ALL_RATE).replace("%",""));
            }
            if(idList.contains("2") && idList.contains("7")){
                Object C_ACT_M_END=map.get("C_ACT_M_END");
                rowList.add(C_ACT_M_END);
                MonthActivityCount+=Integer.parseInt(StrUtils.emptyOrString(C_ACT_M_END));
                Object C_ACT_M_END_RATE=map.get("C_ACT_M_END_RATE");
                rowList.add(C_ACT_M_END_RATE);
                MonthActivityRate+=Float.parseFloat(StrUtils.emptyOrString(C_ACT_M_END_RATE).replace("%",""));
            }
            if(idList.contains("2") && idList.contains("8")){
                Object C_ACT_M_ALL=map.get("C_ACT_M_ALL");
                rowList.add(C_ACT_M_ALL);
                N_MonthActivityCount+=Integer.parseInt(StrUtils.emptyOrString(C_ACT_M_ALL));
                Object C_ACT_M_ALL_RATE=map.get("C_ACT_M_ALL_RATE");
                rowList.add(C_ACT_M_ALL_RATE);
                N_MonthActivityRate+=Float.parseFloat(StrUtils.emptyOrString(C_ACT_M_ALL_RATE).replace("%",""));
            }
            if(idList.contains("3") && idList.contains("4")){
                Object C_FY_D=map.get("C_FY_D");
                rowList.add(C_FY_D);
                Object C_FY_D_TIME=map.get("C_FY_D_TIME");
                rowList.add(C_FY_D_TIME);
                Object C_FY_D_RATE=map.get("C_FY_D_RATE");
                rowList.add(C_FY_D_RATE);
                dayFyCount+=Integer.parseInt(StrUtils.emptyOrString(C_FY_D));//免费通话日留存总数
                dayFyTime+=Integer.parseInt(StrUtils.emptyOrString(C_FY_D_TIME));//免费通话日留存总数
            }
            if(idList.contains("3") && idList.contains("5")){
                Object C_FY_W_END=map.get("C_FY_W_END");
                rowList.add(C_FY_W_END);
                WeekFyCount+=Integer.parseInt(StrUtils.emptyOrString(C_FY_W_END));
                Object C_FY_W_END_TIME=map.get("C_FY_W_END_TIME");
                rowList.add(C_FY_W_END_TIME);
                WeekFyTime+=Integer.parseInt(StrUtils.emptyOrString(C_FY_W_END_TIME));
                Object C_FY_W_END_RATE=map.get("C_FY_W_END_RATE");
                rowList.add(C_FY_W_END_RATE);
                WeekFyRate+=Float.parseFloat(StrUtils.emptyOrString(C_FY_W_END_RATE).replace("%",""));
            }
            if(idList.contains("3") && idList.contains("6")){
                Object C_FY_W_ALL=map.get("C_FY_W_ALL");
                rowList.add(C_FY_W_ALL);
                N_WeekFyCount+=Integer.parseInt(StrUtils.emptyOrString(C_FY_W_ALL));
                Object C_FY_W_ALL_TIME=map.get("C_FY_W_ALL_TIME");
                rowList.add(C_FY_W_ALL_TIME);
                N_WeekFyTime+=Integer.parseInt(StrUtils.emptyOrString(C_FY_W_ALL_TIME));
                Object C_FY_W_ALL_RATE=map.get("C_FY_W_ALL_RATE");
                rowList.add(C_FY_W_ALL_RATE);
                N_WeekFyRate+=Float.parseFloat(StrUtils.emptyOrString(C_FY_W_ALL_RATE).replace("%",""));
            }
            if(idList.contains("3") && idList.contains("7")){
                Object C_FY_M_END=map.get("C_FY_M_END");
                rowList.add(C_FY_M_END);
                MonthFyCount+=Integer.parseInt(StrUtils.emptyOrString(C_FY_M_END));
                Object C_FY_M_END_TIME=map.get("C_FY_M_END_TIME");
                rowList.add(C_FY_M_END_TIME);
                MonthFyTime+=Integer.parseInt(StrUtils.emptyOrString(C_FY_M_END_TIME));
                Object C_FY_M_END_RATE=map.get("C_FY_M_END_RATE");
                rowList.add(C_FY_M_END_RATE);
                MonthFyRate+=Float.parseFloat(StrUtils.emptyOrString(C_FY_M_END_RATE).replace("%",""));
            }
            if(idList.contains("3") && idList.contains("8")){
                Object C_FY_M_ALL=map.get("C_FY_M_ALL");
                rowList.add(C_FY_M_ALL);
                N_MonthFyCount+=Integer.parseInt(StrUtils.emptyOrString(C_FY_M_ALL));
                Object C_FY_M_ALL_TIME=map.get("C_FY_M_ALL_TIME");
                rowList.add(C_FY_M_ALL_TIME);
                N_MonthFyTime+=Integer.parseInt(StrUtils.emptyOrString(C_FY_M_ALL_TIME));
                Object C_FY_M_ALL_RATE=map.get("C_FY_M_ALL_RATE");
                rowList.add(C_FY_M_ALL_RATE);
                N_MonthFyRate+=Float.parseFloat(StrUtils.emptyOrString(C_FY_M_ALL_RATE).replace("%",""));
            }
            baseList.add(rowList);
        }

        /*****************************添加合计*********************************/
        totalList.add("合计");
        totalList.add(downCount);
        totalList.add(activeCount);
        totalList.add(getRate(activeCount,downCount));
        totalList.add(registerCount);
        totalList.add(getRate(registerCount,activeCount));
        totalList.add(allStart);
        totalList.add(allUserKey);
        totalList.add(allUser);
        java.text.DecimalFormat df=new java.text.DecimalFormat("#.00");
        totalList.add(allRegUse);
        totalList.add(df.format(allRegUseRate/baseSize)+"%");
        totalList.add(allNRegUse);
        totalList.add(df.format(allNRegUseRate/baseSize)+"%");

        if(baseSize!=0){
            //注册日留存总数
            if(idList.contains("1") && idList.contains("4")){
                totalList.add(DayRegCount);
                totalList.add(getRate(DayRegCount,registerCount));//--
            }
            //注册周留存合计
            if(idList.contains("1") && idList.contains("5")){
                totalList.add(WeekRegCount);
                totalList.add(df.format(WeekRegRate/baseSize)+"%");//--
            }
            //注册周内留存总数合计
            if(idList.contains("1") && idList.contains("6")){
                totalList.add(N_WeekRegCount);
                totalList.add(df.format(N_WeekRegRate/baseSize)+"%");//--
            }
            //注册月留存合计
            if(idList.contains("1") && idList.contains("7")){
                totalList.add(MonthRegCount);
                totalList.add(df.format(MonthRegRate/baseSize)+"%");//MonthRegRate
            }
            //注册月内留存合计
            if(idList.contains("1") && idList.contains("8")){
                totalList.add(N_MonthRegCount);
                totalList.add(df.format(N_MonthRegRate/baseSize)+"%");//N_MonthRegRate
            }

            //激活合计
            if(idList.contains("2") && idList.contains("4")){
                totalList.add(dayActivityCount);
                totalList.add(getRate(dayActivityCount,activeCount));
            }
            if(idList.contains("2") && idList.contains("5")){
                totalList.add(WeekActivityCount);
                totalList.add(df.format(WeekActivityRate/baseSize)+"%");//WeekActivityRate
            }
            if(idList.contains("2") && idList.contains("6")){
                totalList.add(N_WeekActivityCount);
                totalList.add(df.format(N_WeekActivityRate/baseSize)+"%");//N_WeekActivityRate
            }
            if(idList.contains("2") && idList.contains("7")){
                totalList.add(MonthActivityCount);
                totalList.add(df.format(MonthActivityRate/baseSize)+"%");//MonthActivityRate
            }
            if(idList.contains("2") && idList.contains("8")){
                totalList.add(N_MonthActivityCount);
                totalList.add(df.format(N_MonthActivityRate/baseSize)+"%");//N_MonthActivityRate
            }
            //免费通话合计
            if(idList.contains("3") && idList.contains("4")){
                totalList.add(dayFyCount);
                totalList.add(dayFyTime);
                totalList.add(getRate(dayFyCount,freeCallCount));
            }
            if(idList.contains("3") && idList.contains("5")){
                totalList.add(WeekFyCount);
                totalList.add(WeekFyTime);
                totalList.add(df.format(WeekFyRate/baseSize)+"%");//WeekFyRate
            }
            if(idList.contains("3") && idList.contains("6")){
                totalList.add(N_WeekFyCount);
                totalList.add(N_WeekFyTime);
                totalList.add(df.format(N_WeekFyRate/baseSize)+"%");//N_WeekFyRate
            }
            if(idList.contains("3") && idList.contains("7")){
                totalList.add(MonthFyCount);
                totalList.add(MonthFyTime);
                totalList.add(df.format(MonthFyRate/baseSize)+"%");//MonthFyRate
            }
            if(idList.contains("3") && idList.contains("8")){
                totalList.add(N_MonthFyCount);
                totalList.add(N_MonthFyTime);
                totalList.add(df.format(N_MonthFyRate/baseSize)+"%");//N_MonthFyRate
            }
        }
        Singletons.getInstance().put0("baseList",baseList);
        Singletons.getInstance().put1("totaLList",totalList);
    }
    /**
     * 综合数据统计  -----------查询
     *
     * @return
     */
    public String selectAnalyseDate() {
        String type = getParameter(ParameterKey.type);
        String brand = getParameter("brand");// 品牌
        List<Map<String, Object>> suppliers;// 可见供应商集合
        List<Map<String, Object>> list =new ArrayList<>();

        if("2".equals(type)){
            //查询
            String parameter = getParameter("parameter");
            List<String> idLists = new ArrayList<>();
            if (StrUtils.isNotEmpty(parameter)) {
                String[] params = parameter.split(",");
                idLists = Arrays.asList(params);
            }

            long begin = System.currentTimeMillis();
            String sup = getParameter(ParameterKey.sup);// 供应商
            if (StringUtils.isEmpty(sup)) {
                sup = "0";
            }
            String userId = getSessionLoginUser().getUserId();// 当前用户id
            String sDate = getParameter(ParameterKey.sDate);// 开始时间
            String eDate = getParameter(ParameterKey.eDate);// 结束时间
            String timeType = "1";// 时间类型：1:日
            try {
                getRequest().setCharacterEncoding("UTF-8");
            } catch (UnsupportedEncodingException e) {
                log.error("设置编码错误", e);
            }
            suppliers = userAnalyseService.visibleSupplier(userId, sup); //查询所有供应商
            getRequest().setAttribute("suppliers", suppliers);// 设置可见的供应商
            Map<String, Object> retmap = new HashMap<String, Object>();//其他数据
            log.info("into UserAnalyseAction.selectAnalyseDate");
                try {
                    list = userAnalyseService.selectAnalyseDate(userId, sup, sDate, eDate, brand);
                    retmap.put("list", list);
                    getRequest().getSession().setAttribute("list", list);
                    getRequest().getSession().setAttribute("sDate", sDate);
                    getRequest().getSession().setAttribute("eDate", eDate);
                    getRequest().getSession().setAttribute("suppliers", suppliers);
                    getRequest().getSession().setAttribute("parameter", parameter);
                    this.writeJSONToResponse(retmap);
                } catch (Exception e) {
                    log.error("UserAnalyseAction.selectAnalyseDate failed, e : " + e);
                }
        }
        return NONE;
    }
    /**
     * ============导出综合统计数据 .xlsx
     *
     * @return
     */
    public String exportAllComplexStatistic() {
        List<Map<String, Object>> suppliers;// 可见供应商集合
        List<Map<String, Object>> list =new ArrayList<>();
        //导出
        Object obj =getRequest().getSession().getAttribute("list");
        String sDay = getRequest().getSession().getAttribute("sDate")+"";
        String eDay = getRequest().getSession().getAttribute("eDate")+"";
        suppliers = (ArrayList<Map<String,Object>>)(getRequest().getSession().getAttribute("suppliers"));
        String parameter =StrUtils.emptyOrString(getRequest().getSession().getAttribute("parameter"));
        List<String> idList = new ArrayList<>();
        if (StrUtils.isNotEmpty(parameter)) {
            String[] params = parameter.split(",");
            idList = Arrays.asList(params);
        }
        if (obj instanceof ArrayList<?>){
            list =(ArrayList<Map<String,Object>>)obj;
        }
        packagingBaseData(list,idList);
        List<List<Object>> baseList=Singletons.getInstance().get0("baseList");
        List<Object>  totalList=Singletons.getInstance().get1("totaLList");
        /***********************************封装数据*****************************************/
        //把合计添加进去
        baseList.add(totalList);
        String contentType = "application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        String headerKey = "Content-Disposition";
        String title = "";
        if (suppliers.size() > 1) {
            title = "所有供应商后台数据分析" + sDay + "至" + eDay;
        } else {
            title = suppliers.get(0).get("C_SUPPLIER_NAME") + "后台数据分析" + sDay + "至" + eDay;
        }
        //基础数据
        List<String> baselist=new ArrayList<String>();
        //注册数据
        List<String> registerlist=new ArrayList<String>();
        //激活数据
        List<String> activationlist=new ArrayList<String>();
        //免费通话数据
        List<String> calllist=new ArrayList<String>();
        baselist.add("日期");
        baselist.add("下载数");
        baselist.add("激活数");
        baselist.add("激活率");
        baselist.add("注册数");
        baselist.add("注册率");
        baselist.add("启动数");
        baselist.add("按键使用数");
        baselist.add("合计");
        baselist.add("注册使用数");
        baselist.add("注册使用率");
        baselist.add("非注册使用数");
        baselist.add("非注册使用率");
        Object[] base = baselist.toArray();
        String headerValue = null;
        try {
            headerValue = String.format("attachment; filename=\"%s\"",
                    new String(title.getBytes(Charset.forName("UTF-8")), "ISO8859-1") + ".xlsx");
        } catch (UnsupportedEncodingException e) {
            log.error("文件名编码错误", e);
        }
        //创建HSSFWorkbook对象(excel的文档对象)
        Workbook wb = new XSSFWorkbook();
        //建立新的sheet对象（excel的表单）
        Sheet sheet = wb.createSheet(title);
        // 设置头信息
        Header header = sheet.getHeader();
        header.setCenter("Center Header");
        header.setRight(HSSFHeader.font("Stencil-Normal", "Italic") +
                HSSFHeader.fontSize((short) 16) + "Right w/ Stencil-Normal Italic font and size 16");
        Row row = sheet.createRow(0);// 创建首行
        Cell cell = row.createCell(0);// 创建首列
        XSSFCellStyle titleStyle = statisticsAction.getCellStyle(wb, false, true, Font.COLOR_NORMAL, (short) 17, "华文楷体", true, null);
        cell.setCellStyle(titleStyle);
        cell.setCellValue(title);
            Cell   cell_1;
            Row row_1 = sheet.createRow(1);
            {
                for(int i=0;i<13;i++){
                    CellRangeAddress regionDate = new CellRangeAddress(
                            1, //first row (0-based)
                            2, //last row  (0-based)
                            i, //first column (0-based)
                            i //last column  (0-based)
                    );
                    sheet.addMergedRegion(regionDate);
                }
                //循环设置基础数据
                for (int i = 0; i < base.length; ++i) {
                    cell_1 = row_1.createCell(i);
                    cell_1.setCellValue(base[i].toString());
                }
                int i=0;
                Row row_2 = sheet.createRow(2);
                int start=13;
                if(idList.contains("1")){//是否包含1
                    registerlist.add("注册");
                    int count=0;
                    if(idList.contains("4")){//是否包含1
                        count+=1;
                        registerlist.add("次日留存");
                        registerlist.add("次日留存率");
                    }
                    if(idList.contains("5")){//是否包含1
                        count+=1;
                        registerlist.add("周留存");
                        registerlist.add("周留存率");
                    }
                    if(idList.contains("6")){//是否包含1
                        count+=1;
                        registerlist.add("周内留存");
                        registerlist.add("周内留存率");
                    }
                    if(idList.contains("7")){//是否包含1
                        count+=1;
                        registerlist.add("月留存");
                        registerlist.add("月留存率");
                    }
                    if(idList.contains("8")){//是否包含1
                        count+=1;
                        registerlist.add("月内留存");
                        registerlist.add("月内留存率");
                    }
                    CellRangeAddress regions= new CellRangeAddress(
                                1, //first row (0-based)
                                1, //last row  (0-based)
                            start, //first column (0-based)
                            start+count*2-1//last column  (0-based)
                        );
                        sheet.addMergedRegion(regions);
                    Object[] registerlistbase = registerlist.toArray();
                    cell_1 = row_1.createCell(13);
                    XSSFCellStyle titleStyle1 = statisticsAction.getCellStyle(wb, false, false, Font.COLOR_NORMAL, (short) 14, "华文楷体", false, null);
                    cell_1.setCellStyle(titleStyle1);
                    cell_1.setCellValue(StrUtils.emptyOrString(registerlistbase[0]));//把注册填充到第一行
                    for (int z = 0; z < registerlistbase.length-1; ++z) {//填充注册的数据到第二行
                      Cell  cell_2 = row_2.createCell(13+z);
                      cell_2.setCellValue(StrUtils.emptyOrString(registerlistbase[z+1]));
                    }
                    start=start+count*2;
                }
                if(idList.contains("2")){//是否包含2
                    activationlist.add("激活");
                    int count=0;
                    if(idList.contains("4")){//是否包含4
                        count+=1;
                        activationlist.add("次日留存");
                        activationlist.add("次日留存率");
                    }
                    if(idList.contains("5")){//是否包含5
                        count+=1;
                        activationlist.add("周留存");
                        activationlist.add("周留存率");
                    }
                    if(idList.contains("6")){//是否包含6
                        count+=1;
                        activationlist.add("周内留存");
                        activationlist.add("周内留存率");
                    }
                    if(idList.contains("7")){//是否包含7
                        count+=1;
                        activationlist.add("月留存");
                        activationlist.add("月留存率");
                    }
                    if(idList.contains("8")){//是否包含8
                        count+=1;
                        activationlist.add("月内留存");
                        activationlist.add("月内留存率");
                    }
                    CellRangeAddress regions= new CellRangeAddress(
                            1, //first row (0-based)
                            1, //last row  (0-based)
                            start, //first column (0-based)
                            start+count*2-1//last column  (0-based)
                    );
                    sheet.addMergedRegion(regions);

                    Object [] activationlistbase = activationlist.toArray();
                        cell_1 = row_1.createCell(start);
                    XSSFCellStyle titleStyle1 = statisticsAction.getCellStyle(wb, false, false, Font.COLOR_NORMAL, (short) 14, "华文楷体", false, null);
                     cell_1.setCellStyle(titleStyle1);
                        cell_1.setCellValue(StrUtils.emptyOrString(activationlistbase[0]));
                        for (int z = 0; z < activationlistbase.length-1; ++z) {//填充激活的数据到第二行
                            Cell cell_2 = row_2.createCell(start+z);
                            cell_2.setCellValue(StrUtils.emptyOrString(activationlistbase[z+1]));

                        }
                    start=start+count*2;
                }
                if(idList.contains("3")){//是否包含3
                    calllist.add("免费通话");
                    int count=0;
                    if(idList.contains("4")){//是否包含4
                        count+=1;
                        calllist.add("次日留存");
                        calllist.add("时长（分）");
                        calllist.add("次日留存率");
                    }
                    if(idList.contains("5")){//是否包含5
                        count+=1;
                        calllist.add("周留存");
                        calllist.add("时长（分）");
                        calllist.add("周留存率");
                    }
                    if(idList.contains("6")){//是否包含6
                        count+=1;
                        calllist.add("周内留存");
                        calllist.add("时长（分）");
                        calllist.add("周内留存率");
                    }
                    if(idList.contains("7")){//是否包含7
                        count+=1;
                        calllist.add("月留存");
                        calllist.add("时长（分）");
                        calllist.add("月留存率");
                    }
                    if(idList.contains("8")){//是否包含8
                        count+=1;
                        calllist.add("月内留存");
                        calllist.add("时长（分）");
                        calllist.add("月内留存率");
                    }
                    CellRangeAddress regions= new CellRangeAddress(
                            1, //first row (0-based)
                            1, //last row  (0-based)
                            start, //first column (0-based)
                            start+count*3-1//last column  (0-based)
                    );
                       sheet.addMergedRegion(regions);
                       Object[] calllistbase   =  calllist.toArray();
                        cell_1 = row_1.createCell(start);
                       XSSFCellStyle titleStyle1 = statisticsAction.getCellStyle(wb, false, false, Font.COLOR_NORMAL, (short) 14, "华文楷体", false, null);
                       cell_1.setCellStyle(titleStyle1);
                        //把激活填充到第一行的第10列
                        cell_1.setCellValue(StrUtils.emptyOrString(calllistbase[0]));
                        for (int z = 0; z < calllistbase.length-1; ++z) {
                            cell_1 = row_2.createCell(start+z);
                            cell_1.setCellValue(StrUtils.emptyOrString(calllistbase[z+1]));
                        }
                    start=start+count*3-1;
                }
                if(start==47){
                    CellRangeAddress regions= new CellRangeAddress(
                            0, //first row (0-based)
                            0, //last row  (0-based)
                            0, //first column (0-based)
                            start//last column  (0-based)
                    );
                    sheet.addMergedRegion(regions);
                }else {
                    CellRangeAddress regions= new CellRangeAddress(
                            0, //first row (0-based)
                            0, //last row  (0-based)
                            0, //first column (0-based)
                            start-1//last column  (0-based)
                    );
                    sheet.addMergedRegion(regions);
                }

                for (int s = 0; s < baseList.size(); ++s) {
                    //创建多少行
                    Row row_j = sheet.createRow(s + 3);
                    List<Object> list_s = baseList.get(s);
                    for (int q = 0; q < list_s.size(); ++q) {
                        Cell cell_j = row_j.createCell(q);
                        Object v_q = list_s.get(q);
                        cell_j.setCellValue(StrUtils.emptyOrString(v_q));
                    }
                }
                // 生成Excel
                write(wb, contentType, headerKey, headerValue);
            }
        return NONE;
    }

    /**
     * 导出综合统计数据 .xlsx
     *
     * @return
     */
    public String exportAllComplexStatistics() {

        long begin = System.currentTimeMillis();

        String sup = getParameter(ParameterKey.sup);// 供应商
        if (StringUtils.isEmpty(sup)) {
            sup = "0";
        }
        String parameter = getSessionLoginUser().getUserId();// 参数
        String userId = getSessionLoginUser().getUserId();// 当前用户id
        String sDate = getParameter(ParameterKey.sDate);// 开始时间
        String eDate = getParameter(ParameterKey.eDate);// 结束时间
        String timeType = "1";// 时间类型：1:日
        try {
            getRequest().setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("设置编码错误", e);
        }
        // 可见供应商集合
        List<Map<String, Object>> suppliers;
        //查询所有供应商
        suppliers = userAnalyseService.visibleSupplier(userId, sup);

        // 设置可见的供应商
        getRequest().setAttribute("suppliers", suppliers);
        //其他数据
        List<List<Object>> result = userAnalyseService.allcomplexStatistics(userId, sup, sDate, eDate, timeType);
        //所有留存数据
        List<List<Object>> list = ekeyKeyUsingService.stayingUser(userId, sup, sDate, eDate);
        String contentType = "application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        String headerKey = "Content-Disposition";
        List<List<Object>> o = result;
        String title = "";
        if (suppliers.size() > 1) {
            title = "所有供应商后台数据分析" + sDate + "至" + eDate;
        } else {
            title = suppliers.get(0).get("C_SUPPLIER_NAME") + "后台数据分析" + sDate + "至" + eDate;
        }
        String[] headerArr = new String[]{"日期", "下载数",
                "激活数", "激活率", "注册数", "注册率", "免费通话数",
                "启动数", "按键使用数", "合计", "注册次日留存",
                "注册次日留存率", "激活次日留存", "激活次日留存率", "免费通话次日留存", "时长（分）", "免费通话次日留存率", "注册第7天留存", "注册第7天留存率", "激活第7天留存", "激活第7天留存率", "免费通话第7天留存率", "时长（分）", "免费通话7天内留存",
                "注册7天内留存", "注册7天内留存率", "激活7天内留存", "激活7天内留存率", "免费通话天内留存", "时长（分）", "免费通话7天内留存率", "注册第30天留存", "注册第30天留存率", "激活第30天留存", "激活第30天留存率", "免费通话第30天留存", "时长（分）", "免费通话第30天留存率",
                "注册30天内留存", "注册30天内留存率", "激活30天内留存", "激活30天内留存率", "免费通话30天内留存", "时长（分）", "免费通话30天内留存率"
        };

        String headerValue = null;
        try {
            headerValue = String.format("attachment; filename=\"%s\"",
                    new String(title.getBytes(Charset.forName("UTF-8")), "ISO8859-1") + ".xlsx");
        } catch (UnsupportedEncodingException e) {
            log.error("文件名编码错误", e);
        }
        // 理论上不应该为空
        if (o == null) {
            return NONE;
        }
        //创建HSSFWorkbook对象(excel的文档对象)
        Workbook wb = new XSSFWorkbook();
        //建立新的sheet对象（excel的表单）
        Sheet sheet = wb.createSheet(title);
        // 设置头信息

        Header header = sheet.getHeader();
        header.setCenter("Center Header");
        header.setRight(HSSFHeader.font("Stencil-Normal", "Italic") +
                HSSFHeader.fontSize((short) 16) + "Right w/ Stencil-Normal Italic font and size 16");
        Row row = sheet.createRow(0);// 创建首行
        Cell cell = row.createCell(0);// 创建首列
        CellRangeAddress region = new CellRangeAddress(
                0, //first row (0-based)
                0, //last row  (0-based)
                0, //first column (0-based)
                29  //last column  (0-based)
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

        long end = System.currentTimeMillis();
        log.info("总共用时 >>>>>>>>>>> " + (end - begin) / 1000 + " 秒");
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
            // 下载
            ServletDownloadUtil.doDownload(getResponse(), in, contentType, headerKey, headerValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getRequest().setAttribute("status", "Y");
        return NONE;
    }

    /**
     * 查询留存信息
     *
     * @return
     */
    public String selectAllComplexStatistics() {
        String parameter = getParameter("parameter");
        List<String> idList = new ArrayList<>();
        if (StrUtils.isNotEmpty(parameter)) {
            String[] params = parameter.split(",");
            idList = Arrays.asList(params);
        }
        long begin = System.currentTimeMillis();

        String sup = getParameter(ParameterKey.sup);// 供应商
        if (StringUtils.isEmpty(sup)) {
            sup = "0";
        }
        String userId = getSessionLoginUser().getUserId();// 当前用户id
        String sDate = getParameter(ParameterKey.sDate);// 开始时间
        String eDate = getParameter(ParameterKey.eDate);// 结束时间
        String timeType = "1";// 时间类型：1:日
        try {
            getRequest().setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("设置编码错误", e);
        }
        // 可见供应商集合
        List<Map<String, Object>> suppliers;
        //查询所有供应商
        suppliers = userAnalyseService.visibleSupplier(userId, sup);

        // 设置可见的供应商
        getRequest().setAttribute("suppliers", suppliers);
        //其他数据
        Map<String, Object> retmap = new HashMap<String, Object>();
        log.info("into UserAnalyseAction.selectAllComplexStatistics");
        try {
            List<Map<String, Object>> list = userAnalyseService.selectallcomplexStatistics(userId, sup, sDate, eDate, timeType, idList);

            retmap.put("list", list);
            this.writeJSONToResponse(retmap);
        } catch (Exception e) {
            log.error("UserAnalyseAction.selectAllComplexStatistics failed, e : " + e);
        }
        return NONE;
    }


    public String complexStatistics() {
        Map<String, Object> result;
        String sup = getParameter(ParameterKey.sup);// 供应商
        String userId = getSessionLoginUser().getUserId();// 当前用户id
//        String userId = "1b877d3f-09b3-4011-b9f8-837868251578";// 当前用户id
        String year = getParameter(ParameterKey.year);// 年份
        String sDate = getParameter(ParameterKey.sDate);// 开始时间
        String eDate = getParameter(ParameterKey.eDate);// 结束时间
        String timeType = getParameter(ParameterKey.timeType);// 时间类型：1:日、2:周、3:月
        log.info("into analyse...");
        log.info("sup = " + sup + ", year = " + year + ", sDate = "
                + sDate + ", eDate = " + eDate + ", userId = " + userId
                + ", timeType = " + timeType);

        // 设置可见的供应商
        getRequest().setAttribute("suppliers", userAnalyseService.visibleSupplier(userId, sup));

        result = userAnalyseService.complexStatistics(userId, sup, sDate, eDate, timeType, year);

        List<String> timeStringList = (List<String>) result.get("timeStringList");
        List<SupVo> supVoList = (List<SupVo>) result.get("supVoList");
        // 设置显示的时间集合
        getRequest().setAttribute("timeStringList", timeStringList);

        // 设置数据集合
        getRequest().setAttribute("supVoList", supVoList);

        String[] chartArr = getChartArr(timeStringList, supVoList);

        //  设置图表数组
        getRequest().setAttribute("chartArr", chartArr);

        getRequest().setAttribute(ParameterKey.year, year);
        getRequest().setAttribute(ParameterKey.sDate, sDate);
        getRequest().setAttribute(ParameterKey.eDate, eDate);
        getRequest().setAttribute(ParameterKey.timeType, timeType);

        // 数据存储在session，方便导出时使用
        getRequest().getSession().setAttribute("STATISTICS_" + userId, result);

        return "complex";
    }

    private String[] getChartArr(List<String> timeStringList, List<SupVo> supVoList) {
        LinkedList<String> charList = new LinkedList<String>();
        String[] nameArr = new String[]{"下载", "激活", "注册", "活跃", "留存"};
        int x = nameArr.length;
        int y = timeStringList.size();
        Long[][] dataArr = new Long[x][y];
        Long[][] sumDataArr = new Long[x][y];

        for (int i = 0; i < x; ++i) {
            for (int j = 0; j < y; ++j) {
                // 初始化总数数组
                sumDataArr[i][j] = 0L;
            }
        }

        for (Iterator<SupVo> iterator = supVoList.iterator(); iterator.hasNext(); ) {
            SupVo supVo = iterator.next();
            String title = supVo.getSupName();

            List<TimeVo> timeVoList = supVo.getL_timeVo();

            int j = 0;
            for (Iterator<TimeVo> iterator1 = timeVoList.iterator(); iterator1.hasNext(); ++j) {
                TimeVo timeVo = iterator1.next();
                dataArr[0][j] = timeVo.getDownloadNum();
                dataArr[1][j] = timeVo.getActivationNum();
                dataArr[2][j] = timeVo.getRegisterNum();
                dataArr[3][j] = timeVo.getActiveNum();
                dataArr[4][j] = timeVo.getStayingNum();

                // 合计数据添加
                sumDataArr[0][j] += timeVo.getDownloadNum();
                sumDataArr[1][j] += timeVo.getActivationNum();
                sumDataArr[2][j] += timeVo.getRegisterNum();
                sumDataArr[3][j] += timeVo.getActiveNum();
                sumDataArr[4][j] += timeVo.getStayingNum();
            }

            // 标题，柱子数组，横轴下标，数据
            try {
                charList.add(HighchartsUtil.getBar(title, timeStringList.toArray(new String[]{}), nameArr, dataArr));
            } catch (Exception e) {
                LOG.error("柱状图生成出错", e);
            }
        }

        try {
            charList.addFirst(HighchartsUtil.getBar("合计", timeStringList.toArray(new String[]{}), nameArr, sumDataArr));
        } catch (Exception e) {
            LOG.error("柱状图生成出错", e);
        }

        return charList.toArray(new String[]{});
    }

    /**
     * 导出综合统计数据 .xlsx
     *
     * @return
     */
    public String exportComplexStatistics() {
        // 页面传过来的svg数组(各个供应商的图表数据和合计图表数据)
        try {
            getRequest().setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("设置编码错误", e);
        }
        String[] svgArr = getRequest().getParameterValues(ParameterKey.SVG_ARR);
        String year = getRequest().getParameter(ParameterKey.year);
        String sDate = getRequest().getParameter(ParameterKey.sDate);
        String eDate = getRequest().getParameter(ParameterKey.eDate);
        String timeType = getRequest().getParameter(ParameterKey.timeType);
        String suffix;

        ExcelUtil excelUtil = new ExcelUtil();

        if ("2".equalsIgnoreCase(timeType)) {
            suffix = "周";
        } else {
            suffix = "月";
        }

        Workbook wb = new XSSFWorkbook();
        // 表格数据结果
        Map<String, Object> result = null;
        String userId = getSessionLoginUser().getUserId();
        // String userId = "1b877d3f-09b3-4011-b9f8-837868251578";// 当前用户id
        String contentType = "application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        String headerKey = "Content-Disposition";
        String title = year + "年第" + sDate + suffix + "至第" + eDate + suffix + "综合数据分析";
        String headerValue = null;
        try {
            headerValue = String.format("attachment; filename=\"%s\"",
                    new String(title.getBytes(Charset.forName("UTF-8")), "ISO8859-1") + System.currentTimeMillis() + ".xlsx");
        } catch (UnsupportedEncodingException e) {
            log.error("文件名编码错误", e);
        }

        List<String> timeStringList = null;
        List<SupVo> supVoList = null;

        // 第一个时间单位开始列
        int firstColumn = 1;
        // 默认只有一个时间单位
        int lastColumn = 5;
        // 当前行位置
        int currentRowNum = 0;

        String[] headerArr = new String[]{"下载数", "激活数", "注册数", "活跃数", "留存数"};

        Map<String, Map<String, Long>> totalNumMap = new LinkedHashMap<String, Map<String, Long>>();

        // sheet 页的名字
        final String sheetName = "报表综合数据";
        Object o = getRequest().getSession().getAttribute("STATISTICS_" + userId);

        // 理论上不应该为空
        if (o == null) {
            return NONE;
        }
        result = (Map<String, Object>) o;

        timeStringList = (List<String>) result.get("timeStringList");
        supVoList = (List<SupVo>) result.get("supVoList");

        int timeSize = timeStringList.size();
        int supListSize = supVoList.size();

        Sheet sheet = wb.createSheet(sheetName);
        CellStyle cellStyle = excelUtil.getDefaultCellStyle(wb);

        // 创建表头行 前面空一行 0
        Row row = sheet.createRow(currentRowNum + 1);
        currentRowNum++;
        Cell cell = row.createCell(1);
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellValue(title);
        Font font = wb.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints((short) 18);
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
        // 合并第一行第二行，第一列到第九列 下标从0开始
        CellRangeAddress cellRangeAddress = new CellRangeAddress(1, 2, 1, 9);
        excelUtil.setRegionStyle(sheet, cellStyle, cellRangeAddress);
        sheet.addMergedRegion(cellRangeAddress);
        currentRowNum++;

        //多少个时间维度换行
        int step = 2;

        for (int k = 0; k < timeSize; k += step) {
            // 空一行，创建实际数据化
            row = sheet.createRow(currentRowNum + 2);
            currentRowNum += 2;

            cell = row.createCell((short) (0));// 创建首列

            cell.setCellValue("供应商");
            cellStyle = excelUtil.getDefaultCellStyle(wb);
            excelUtil.setDefaultBorder(cellStyle);
            excelUtil.setDefaultForegroundColor(cellStyle);
            cellRangeAddress = new CellRangeAddress(
                    currentRowNum, //first row (0-based)
                    currentRowNum + 1, //last row  (0-based)
                    0, //first column (0-based)
                    0  //last column  (0-based)
            );
            excelUtil.setRegionStyle(sheet, cellStyle, cellRangeAddress);
            sheet.addMergedRegion(cellRangeAddress);
            cell.setCellStyle(cellStyle);

            Row headerRow = null;
            int len = k + 1;
            while (len >= timeSize) {
                --len;
            }
            // 表头行设置
            for (int j = k; j <= len; ++j) {
                cellStyle = excelUtil.getDefaultCellStyle(wb);
                excelUtil.setDefaultBorder(cellStyle);
                excelUtil.setDefaultForegroundColor(cellStyle);
                // 设置时间
                cell = row.createCell(((j % step) * 5) + 1);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                String timeString = timeStringList.get(j);
                Map<String, Long> sumMap = new HashMap<String, Long>();

                // 设置默认合计数量
                sumMap.put(SumKey.downloadSum, 0L);
                sumMap.put(SumKey.activationSum, 0L);
                sumMap.put(SumKey.registerSum, 0L);
                sumMap.put(SumKey.activeSum, 0L);
                sumMap.put(SumKey.stayingSum, 0L);
                totalNumMap.put(timeString, sumMap);

                cell.setCellValue(timeString);

                cellRangeAddress = new CellRangeAddress(
                        row.getRowNum(),
                        row.getRowNum(),
                        firstColumn,
                        lastColumn
                );
                excelUtil.setRegionStyle(sheet, cellStyle, cellRangeAddress);
                sheet.addMergedRegion(cellRangeAddress);

                cell.setCellStyle(cellStyle);

                // 下载、激活、注册、活跃、留存
                if (headerRow == null) {
                    headerRow = sheet.createRow(currentRowNum + 1);
                    currentRowNum++;
                }

                cellStyle = excelUtil.getDefaultCellStyle(wb);
                excelUtil.setDefaultBorder(cellStyle);

                // 设置下载、激活、注册、活跃、留存文本
                for (int h = firstColumn - 1; h < firstColumn + 4; h++) {
                    cell = headerRow.createCell(h + 1);
                    cell.setCellValue(headerArr[h % 5]);
                    cell.setCellStyle(cellStyle);
                }

                firstColumn = lastColumn + 1;
                lastColumn = lastColumn + 5;
            }

            // 填充供应商信息
            for (int i = 0; i < supListSize; ++i) {
                SupVo supVo = supVoList.get(i);
                row = sheet.createRow(++currentRowNum);
                cellStyle = excelUtil.getDefaultCellStyle(wb);
                excelUtil.setDefaultForegroundColor(cellStyle);
                excelUtil.setDefaultBorder(cellStyle);
                cell = row.createCell(0);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(supVo.getSupName());

                List<TimeVo> timeVoList = supVo.getL_timeVo();
                // 供应商在各个时间的下载数、激活数、注册数、活跃数、留存数填充
                for (int j = k; j <= len; ++j) {
                    TimeVo timeVo = timeVoList.get(j);
                    String timeString = timeVo.getTimeString();
                    Map<String, Long> sumMap = new HashMap<String, Long>();
                    cellStyle = excelUtil.getDefaultCellStyle(wb);
                    excelUtil.setDefaultBorder(cellStyle);

                    // 下载
                    cell = row.createCell(((j % step) * 5) + 1);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    cell.setCellStyle(cellStyle);
                    Long downloadNum = timeVo.getDownloadNum();
                    cell.setCellValue(downloadNum);

                    // 激活
                    cell = row.createCell(((j % step) * 5) + 2);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    cell.setCellStyle(cellStyle);
                    Long activationNum = timeVo.getActivationNum();
                    cell.setCellValue(activationNum);

                    // 注册
                    cell = row.createCell(((j % step) * 5) + 3);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    cell.setCellStyle(cellStyle);
                    Long registerNum = timeVo.getRegisterNum();
                    cell.setCellValue(registerNum);

                    //活跃
                    cell = row.createCell(((j % step) * 5) + 4);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    cell.setCellStyle(cellStyle);
                    Long activeNum = timeVo.getActiveNum();
                    cell.setCellValue(activeNum);

                    // 留存
                    cell = row.createCell(((j % step) * 5) + 5);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    cell.setCellStyle(cellStyle);
                    Long stayingNum = timeVo.getStayingNum();
                    cell.setCellValue(stayingNum);

                    // 合计
                    sumMap.put(SumKey.downloadSum, totalNumMap.get(timeString).get(SumKey.downloadSum) + downloadNum);
                    sumMap.put(SumKey.activationSum, totalNumMap.get(timeString).get(SumKey.activationSum) + activationNum);
                    sumMap.put(SumKey.registerSum, totalNumMap.get(timeString).get(SumKey.registerSum) + registerNum);
                    sumMap.put(SumKey.activeSum, totalNumMap.get(timeString).get(SumKey.activeSum) + activeNum);
                    sumMap.put(SumKey.stayingSum, totalNumMap.get(timeString).get(SumKey.stayingSum) + stayingNum);

                    totalNumMap.put(timeString, sumMap);
                }
            }

            // 合计行
            row = sheet.createRow(++currentRowNum);
            cell = row.createCell(0);
            cell.setCellValue("合计");
            cellStyle = excelUtil.getDefaultCellStyle(wb);
            excelUtil.setDefaultBorder(cellStyle);
            excelUtil.setDefaultForegroundColor(cellStyle);
            cell.setCellStyle(cellStyle);

            cellStyle = excelUtil.getDefaultCellStyle(wb);
            excelUtil.setDefaultBorder(cellStyle);
            int j = k;
            for (Iterator<String> iterator = totalNumMap.keySet().iterator(); iterator.hasNext(); ++j) {
                String timeString = iterator.next();
                Map<String, Long> sumMap = totalNumMap.get(timeString);
                // 下载合计
                cell = row.createCell(((j % step) * 5) + 1);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(sumMap.get(SumKey.downloadSum));

                // 激活合计
                cell = row.createCell(((j % step) * 5) + 2);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(sumMap.get(SumKey.activationSum));

                // 注册合计
                cell = row.createCell(((j % step) * 5) + 3);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(sumMap.get(SumKey.registerSum));

                //活跃合计
                cell = row.createCell(((j % step) * 5) + 4);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(sumMap.get(SumKey.activeSum));

                // 留存合计
                cell = row.createCell(((j % step) * 5) + 5);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(sumMap.get(SumKey.stayingSum));
            }

            // 清空合计map，不然循环会有影响
            totalNumMap.clear();

            // 重置首列和默认最后一列位置
            firstColumn = 1;
            lastColumn = 5;
        }

        log.info("current row num : " + currentRowNum);
        try {
            //画图
            for (int i = 0; i < svgArr.length; ++i) {
                com.org.mokey.common.util.excel.ExcelUtil.drawingPatriarch(wb, svgArr[i], 0, 0, 413, 957, (short) 0, currentRowNum + 2, (short) 11, currentRowNum + 23, sheetName);

                currentRowNum += 23;
            }
        } catch (IOException e) {
            log.error("综合报表导出异常", e);
        } catch (TranscoderException e) {
            log.error("综合报表导出异常", e);
        }

        write(wb, contentType, headerKey, headerValue);

        return NONE;
    }

    @SuppressWarnings("unchecked")
    private void lineGraph(String timeType, String dataType, String sDate,
                           String eDate, Map<String, Object> reqMap) throws JSONException {
        String title_time = "";
        String title_data = "";
        if ("1".equals(timeType)) {
            title_time = "日";
        } else if ("2".equals(timeType)) {
            title_time = "周";
        } else {
            title_time = "月";
        }
        if ("1".equals(dataType)) {
            title_data = "用户下载情况";
        } else if ("2".equals(dataType)) {
            title_data = "用户激活情况";
        } else if ("3".equals(dataType)) {
            title_data = "用户注册情况";
        } else if ("4".equals(dataType)) {
            title_data = "用户活跃情况";
        } else {
            title_data = "用户留存情况";
        }
        String chartData = "";

        if (StrUtils.isNotEmpty(reqMap)) {
            // 时间数组
            List<String> srcTimeList = (List<String>) reqMap.get("timeList");
            List<String> desTimeList = new ArrayList<String>(srcTimeList);
            desTimeList.remove(0);// 时间列表（去掉第一个元素“供应商”）
            String[] times = objArr2StringArr((Object[]) desTimeList.toArray());

            // 供应商数组
            List<Map<String, Object>> suppliers = (List<Map<String, Object>>) reqMap
                    .get("suppliers");
            List<String> sup = new ArrayList<String>();
            if (StrUtils.isNotEmpty(suppliers)) {
                for (Map<String, Object> supplier : suppliers) {
                    sup.add(supplier.get("C_SUPPLIER_NAME").toString());
                }
            }
            String[] sups = objArr2StringArr((Object[]) sup.toArray());

            // 数据数组（2维数组）
            List<Object> dataList = (List<Object>) reqMap.get("dataMap");

            //
            String[][] data = new String[sups.length][times.length];

            for (int i = 0; i < sups.length; i++) {
                String arrSupName = sups[i];
                for (int j = 0; j < times.length; j++) {
                    String cnt = "";
                    Map<String, Object> oneDataMap = (Map<String, Object>) dataList.get(i);
                    List<String> oneData = (List<String>) oneDataMap.get("data");
                    String supName = oneDataMap.get("sup").toString();
                    if (arrSupName.equals(supName)) {
                        cnt = String.valueOf(oneData.get(j * 2));
                        data[i][j] = cnt;
                    }
                }
            }

            chartData = HighchartsUtil.getLine(sDate + "到" + eDate + title_time + title_data, times, sups, data);
        }
        reqMap.put("chartData", chartData);
    }

    // 从T_EK_ANALYSE_DATA表中获取已查询到的数据
    public String getCompleteData() {
        Map<String, Object> retmap = new HashMap<>();
        String sup = getParameter("sup");// 供应商
        String sDate = getParameter("sDate");// 供应商
        String eDate = getParameter("eDate");// 供应商
        String parameter = getParameter("parameter");
        String[] params = parameter.split(",");
        List<String> ids = Arrays.asList(params);
        try {
            List<Map<String,Object>> list = userAnalyseService.getCompleteData(sup, sDate, eDate, ids);
            retmap.put("list",list);
            this.writeJSONToResponse(retmap);
        } catch (IOException e) {
            retmap.put("status", "N");
            log.error("EKGameGiftCateAction.gameGiftCateList failed, e : " + e);
        }
        return NONE;
    }

    public String test() {
        long begin = System.currentTimeMillis();
        userAnalyseService.timedTask();
        long end = System.currentTimeMillis();
        long cost = (end - begin) / 1000;
        log.info(" >>>>>>>>>>>>>>>>>>>>>>>>>>> 总计用时 >>>>>>>>>>>>>>>>>>>>>>>>>>> " + cost + " 秒 ");
        return NONE;
    }

    public String[] objArr2StringArr(Object[] objArr) {
        String[] stringArr = new String[objArr.length];
        for (int i = 0; i < objArr.length; i++) {
            stringArr[i] = objArr[i].toString();
        }
        return stringArr;
    }

    private interface SumKey {
        String downloadSum = "downloadSum";
        String activationSum = "activationSum";
        String registerSum = "registerSum";
        String activeSum = "activeSum";
        String stayingSum = "stayingSum";
    }

    private interface ParameterKey {
        String year = "year";
        String yearBef = "yearBef";
        String yearCurr = "yearCurr";
        String sDate = "sDate";
        String eDate = "eDate";
        String timeType = "timeType";
        String dataType = "dataType";
        String sup = "sup";
        String SVG_ARR = "svgArr";
        String SVG = "svg";
        String type = "type";
    }

    public static void main(String args[]) {


    }
}
