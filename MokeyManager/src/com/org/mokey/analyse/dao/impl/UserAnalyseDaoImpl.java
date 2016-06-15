package com.org.mokey.analyse.dao.impl;

import com.org.mokey.analyse.dao.UserAnalyseDao;
import com.org.mokey.common.util.ApDateTime;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Maryn on 2016/1/18.
 */
@Repository
public class UserAnalyseDaoImpl implements UserAnalyseDao {

    private static final Log LOGGER = LogFactory.getLog(UserAnalyseDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Map<String, Object>> getBrands(String userId) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {

            String sql = "SELECT B.C_ID, B.C_CODE, B.C_NAME FROM T_SYS_DATA_PERMISSION T," +
                    " T_BASE_SUPPLIER S, T_BASE_BRAND B, T_BASE_SUPPLIER_BRAND SB" +
                    " WHERE T.C_SID = S.C_ID AND S.C_ID = SB.C_SUP_ID" +
                    " AND B.C_ID = SB.C_BRA_ID AND T.C_UID = ?" +
                    " GROUP BY B.C_ID, B.C_CODE, B.C_NAME ORDER BY B.C_NAME";
            list = jdbcTemplate.queryForList(sql, new Object[]{userId});
        } catch (DataAccessException e) {
            LOGGER.error("UserAnalyseOfDayDaoImpl.getBrands failed ! e :", e);
        }
        return list;
    }

    @Override
    // 查询指定品牌的所有渠道
    public List<Map<String, Object>> getSuppliersByBrand(String brand, String userId) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            if ("0".equals(brand)) {
                return visibleSupplier(userId, "0");
            } else {
                String sql = "SELECT S.C_SUPPLIER_CODE, S.C_SUP_NAME C_SUPPLIER_NAME" +
                        " FROM T_BASE_BRAND B, T_BASE_SUPPLIER_BRAND SB, T_BASE_SUPPLIER S" +
                        " WHERE B.C_ID = SB.C_BRA_ID AND SB.C_SUP_ID = S.C_ID AND B.C_ID = ?";
                list = jdbcTemplate.queryForList(sql, new Object[]{brand});
            }
        } catch (DataAccessException e) {
            LOGGER.error("UserAnalyseOfDayDaoImpl.getSuppliersByBrand failed ! e :", e);
        }
        return list;
    }

    /**
     * 注册和激活用户总数
     * @param sup
     * @return
     */
    @Override
    public List<Map<String, Object>> selectReg_ActCount(List<String> sup) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("sup", sup);
            String sql = "SELECT COUNT(T.C_IMEI) AS ACT_COUNT FROM T_ACTION_ACTIVE T" +
                    " WHERE SUBSTR(T.C_ACTIVECODE, 6, 6) IN (:sup)";
            list = namedParameterJdbcTemplate.queryForList(sql, paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("UserAnalyseOfDayDaoImpl.selectReg_ActCount failed ! e :", e);
        }
        return list;
    }

    /**
     * 使用或启动 用户列表
     * @param sup
     * @param sDate
     * @param eDate
     * @return
     */
    @Override
    public List<Map<String, Object>> selectUserList(List<String> sup, String sDate, String eDate) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("sup", sup);
            paramMap.put("sDate", sDate);
            paramMap.put("eDate", eDate);
            String sql = "SELECT U.C_IMEI, TO_CHAR(U.C_ACTIONDATE, 'yyyy-MM-dd') DAY" +
                    " FROM T_ACTION_USEAPP U, T_ACTION_ACTIVE T WHERE U.C_IMEI = T.C_IMEI" +
                    " AND  U.C_ACTIONDATE < TO_DATE(:eDate, 'yyyy-MM-dd') + 1" +
                    " AND U.C_ACTIONDATE >= TO_DATE(:sDate, 'yyyy-MM-dd')" +
                    " AND SUBSTR(T.C_ACTIVECODE, 6, 6) IN (:sup)" +
                    " GROUP BY U.C_IMEI, TO_CHAR(U.C_ACTIONDATE, 'yyyy-MM-dd')";
            list = namedParameterJdbcTemplate.queryForList(sql, paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("UserAnalyseOfDayDaoImpl.selectUserList failed ! e :", e);
        }
        return list;
    }

    /**
     * 注册用户列表
     * @param sup
     * @return
     */
    @Override
    public List<Map<String, Object>> selectRegList(List<String> sup) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("sup", sup);
            String sql = "SELECT T.C_PHONENUM,TEMI.C_IMEI FROM T_EK_MEMBER_INFO T,T_EK_MEMBER_IMEI TEMI" +
                    " WHERE C_SUPCODE IN(:sup)" +
                    " AND T.C_ISLIVE=1 AND T.C_ID=TEMI.C_UID";
            list = namedParameterJdbcTemplate.queryForList(sql, paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("UserAnalyseOfDayDaoImpl.selectRegList failed ! e :", e);
        }
        return list;
    }


    @Override
    public List<Map<String, Object>> visibleSupplier(String userId, String sup) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {

            String sql = "SELECT S.C_SUPPLIER_CODE, S.C_SUP_NAME AS C_SUPPLIER_NAME" +
                    " FROM T_SYS_DATA_PERMISSION T, T_BASE_SUPPLIER S" +
                    " WHERE T.C_SID = S.C_ID" +
//                    " AND T.C_SCODE = S.C_SUPPLIER_CODE" +
                    " AND T.C_UID = ?";// '1b877d3f-09b3-4011-b9f8-837868251578' 测试用uid
            List<String> args = new ArrayList<String>();
            args.add(userId);
            if ("0".equals(sup)) {
                sql += " ORDER BY S.C_SUPPLIER_CODE";
            } else {
                args.add(sup);
                sql += " AND T.C_SCODE = ? ORDER BY S.C_SUPPLIER_CODE";
            }
            list = jdbcTemplate.queryForList(sql, args.toArray());
        } catch (DataAccessException e) {
            LOGGER.error("UserAnalyseOfDayDaoImpl.visibleSupplier failed ! e :", e);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> visibleSupplierByBrand(String userId, String sup, String brand) {
        return null;
    }

    @Override
    // 获取所有供应商
    public List<Map<String, Object>> getAllSuppliers() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {

            String sql = "SELECT S.C_SUPPLIER_CODE CODE FROM T_BASE_SUPPLIER S WHERE S.C_ISLIVE = 1 ORDER BY S.C_SUPPLIER_CODE";
            list = jdbcTemplate.queryForList(sql);
        } catch (DataAccessException e) {
            LOGGER.error("UserAnalyseOfDayDaoImpl.getAllSuppliers failed ! e :", e);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> activeUserData(
            List<String> suppliers, String sNow, String eNow, String timeType) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {

            String sql = "";

            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("suppliers", suppliers);
            paramMap.put("sNow", sNow);
            paramMap.put("eNow", eNow);

            String dateFormat = "'yyyy-MM-dd'";
            if ("3".equalsIgnoreCase(timeType)) {
                try {
                    //sNow += "" + ApDateTime.getFirstDayOfMonth(sNow, "yyyy-MM");
                    sNow += "-01";
                    eNow += "-" + ApDateTime.getLastDayOfMonth(eNow, "yyyy-MM");
                    paramMap.put("sNow", sNow);
                    paramMap.put("eNow", eNow);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                sql = "SELECT CODE, SUBSTR(DAY1, 1, 7) DAY, SUM(TOTAL1) TOTAL" +
                        " FROM (SELECT CODE, DAY1, COUNT(C_IMEI) TOTAL1" +
                        " FROM (SELECT SUBSTR(T.C_ACTIVECODE, 6, 6) CODE, U.C_IMEI," +
                        " TO_CHAR(U.C_ACTIONDATE, 'yyyy-MM-dd') DAY1" +
                        " FROM T_ACTION_USEAPP U, T_ACTION_ACTIVE T" +
                        " WHERE U.C_IMEI = T.C_IMEI AND" +
                        " U.C_ACTIONDATE >= TO_DATE(:sNow, 'yyyy-MM-dd')" +
                        " AND U.C_ACTIONDATE < TO_DATE(:eNow, 'yyyy-MM-dd') + 1" +
                        " AND SUBSTR(T.C_ACTIVECODE, 6, 6) IN (:suppliers)" +
                        " GROUP BY SUBSTR(T.C_ACTIVECODE, 6, 6), U.C_IMEI," +
                        " TO_CHAR(U.C_ACTIONDATE, 'yyyy-MM-dd')) GROUP BY CODE, DAY1" +
                        " ) GROUP BY CODE, SUBSTR(DAY1, 1, 7) ORDER BY CODE, DAY";
            } else {
                // 使用NamedParameterJdbcTemplate执行查询时，占位符使用":参数map的键"(:suppliers)
                sql = "SELECT CODE, DAY, COUNT(C_IMEI) TOTAL" +
                        " FROM (SELECT SUBSTR(T.C_ACTIVECODE, 6, 6) CODE," +
                        " U.C_IMEI, TO_CHAR(U.C_ACTIONDATE, " + dateFormat + ") DAY" +
                        " FROM T_ACTION_USEAPP U, T_ACTION_ACTIVE T WHERE U.C_IMEI = T.C_IMEI" +
                        " AND U.C_ACTIONDATE < TO_DATE(:eNow, " + dateFormat + ") + 1" +
                        " AND U.C_ACTIONDATE >= TO_DATE(:sNow, " + dateFormat + ")" +
                        " AND SUBSTR(T.C_ACTIVECODE, 6, 6) IN (:suppliers)" +
                        " GROUP BY SUBSTR(T.C_ACTIVECODE, 6, 6)," +
                        " U.C_IMEI, TO_CHAR(U.C_ACTIONDATE, " + dateFormat + "))" +
                        " GROUP BY CODE, DAY ORDER BY CODE, DAY";
            }

            list = namedParameterJdbcTemplate.queryForList(sql, paramMap);

        } catch (DataAccessException e) {
            LOGGER.error("UserAnalyseOfDayDaoImpl.activeUserData failed ! e :", e);
        }
        return list;
    }


    @Override
    public List<Map<String, Object>> downloadUserData(List<String> suppliers, String sNow, String eNow, String timeType) {
        StringBuilder stringBuilder = new StringBuilder("select * from (select count(*) as TOTAL");
        String dateFormat = "";
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("supCodeList", suppliers);
        parameterMap.put("sNow", sNow);
        parameterMap.put("eNow", eNow);

        // 1:日、2:周、3:月 最好定义成枚举或者常量如：DAY : 1,WEEK : 2, MONTH : 3
        if ("1".equalsIgnoreCase(timeType) || "2".equalsIgnoreCase(timeType)) {
            dateFormat = "'yyyy-MM-dd'";
        } else {
            dateFormat = "'yyyy-MM'";
        }
        stringBuilder.append(",").append("to_char(c_date,").append(dateFormat)
                .append(") as DAY, c_supCode as CODE from t_sys_download_rec where c_supcode in(:supCodeList)")
                .append(" and c_date >= to_date(:sNow,").append(dateFormat)
                .append(") and c_date <");
        if ("1".equalsIgnoreCase(timeType) || "2".equalsIgnoreCase(timeType)) {
            stringBuilder.append("to_date(:eNow,").append(dateFormat).append(") + 1 ");
        } else {
            stringBuilder.append("add_months(to_date(:eNow,").append(dateFormat).append("),1) ");
        }
        stringBuilder.append(" group by to_char(c_date,").append(dateFormat)
                .append("), c_supCode) order by CODE, DAY");

        try {
            return namedParameterJdbcTemplate.queryForList(stringBuilder.toString(), parameterMap);
        } catch (Exception e) {
            LOGGER.error(e);
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> activationUserData(List<String> suppliers, String sNow, String eNow, String timeType) {
        StringBuilder stringBuilder = new StringBuilder("select * from (select to_char(C_SYSDATE,");
        String dateFormat = "";
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("supCodeList", suppliers);
        parameterMap.put("sNow", sNow);
        parameterMap.put("eNow", eNow);

        // 1:日、2:周、3:月 最好定义成枚举或者常量如：DAY : 1,WEEK : 2, MONTH : 3
        if ("1".equalsIgnoreCase(timeType) || "2".equalsIgnoreCase(timeType)) {
            dateFormat = "'yyyy-MM-dd'";
        } else {
            dateFormat = "'yyyy-MM'";
        }

        // 效率问题暂不考虑，后期修改
        stringBuilder.append(dateFormat).append(") as DAY, count(*) as TOTAL,substr(C_ACTIVECODE,6,6) as CODE from T_ACTION_ACTIVE where C_SYSDATE >= to_date(:sNow,").append(dateFormat)
                .append(") and c_sysdate < ");
        if ("1".equalsIgnoreCase(timeType) || "2".equalsIgnoreCase(timeType)) {
            stringBuilder.append("to_date(:eNow,").append(dateFormat).append(") + 1 ");
        } else {
            stringBuilder.append("add_months(to_date(:eNow,").append(dateFormat).append("),1) ");
        }
        stringBuilder.append(" and substr(C_ACTIVECODE,6,6) in(:supCodeList)")
                .append(" group by to_char(c_sysdate,").append(dateFormat)
                .append("), substr(C_ACTIVECODE,6,6) ) order by CODE, DAY");

        try {
            return namedParameterJdbcTemplate.queryForList(stringBuilder.toString(), parameterMap);
        } catch (Exception e) {
            LOGGER.error(e);
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> registerUserData(List<String> suppliers, String sNow, String eNow, String timeType) {
//        select count(*) as count,c_supcode,to_char(c_actiondate,?) as d from T_CJ_MEMBER_INFO where c_supcode in () and c_actiondate <? and c_actiondate >= ? group by 时间，供应商代码

        StringBuilder stringBuilder = new StringBuilder("select * from (select count(*) as TOTAL,c_supCode as CODE,to_char(c_actiondate,");
        String dateFormat = "";
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("supCodeList", suppliers);
        parameterMap.put("sNow", sNow);
        parameterMap.put("eNow", eNow);

        // 1:日、2:周、3:月 最好定义成枚举或者常量如：DAY : 1,WEEK : 2, MONTH : 3
        if ("1".equalsIgnoreCase(timeType) || "2".equalsIgnoreCase(timeType)) {
            dateFormat = "'yyyy-MM-dd'";
        } else {
            dateFormat = "'yyyy-MM'";
        }

        // 效率问题暂不考虑，后期修改
        stringBuilder.append(dateFormat).append(") as DAY from T_EK_MEMBER_INFO where c_actiondate >= to_date(:sNow,").append(dateFormat)
                .append(") and c_actiondate < ");
        if ("1".equalsIgnoreCase(timeType) || "2".equalsIgnoreCase(timeType)) {
            stringBuilder.append("to_date(:eNow,").append(dateFormat).append(") + 1 ");
        } else {
            stringBuilder.append("add_months(to_date(:eNow,").append(dateFormat).append("),1) ");
        }
        stringBuilder.append("and c_supcode in(:supCodeList)")
                .append(" group by to_char(c_actiondate,").append(dateFormat)
                .append("), c_supCode) order by CODE, DAY");

        try {

            return namedParameterJdbcTemplate.queryForList(stringBuilder.toString(), parameterMap);
        } catch (Exception e) {
            LOGGER.error(e);
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> stayingData(
            List<String> suppliers, String date) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("suppliers", suppliers);
            paramMap.put("date", date);

            String sql = "SELECT SUBSTR(T.C_ACTIVECODE, 6, 6) CODE, U.C_IMEI," +
                    " TO_CHAR(U.C_ACTIONDATE, 'yyyy-MM-dd') DAY" +
                    " FROM T_ACTION_ACTIVE T, T_ACTION_USEAPP U WHERE T.C_IMEI = U.C_IMEI" +
                    " AND U.C_KEY = '0' AND SUBSTR(T.C_ACTIVECODE, 6, 6) IN (:suppliers)" +
                    " AND TO_CHAR(U.C_ACTIONDATE, 'yyyy-MM-dd') = :date" +
                    " GROUP BY SUBSTR(T.C_ACTIVECODE, 6, 6), U.C_IMEI," +
                    " TO_CHAR(U.C_ACTIONDATE, 'yyyy-MM-dd')" +
                    " ORDER BY SUBSTR(T.C_ACTIVECODE, 6, 6), TO_CHAR(U.C_ACTIONDATE, 'yyyy-MM-dd')";

            list = namedParameterJdbcTemplate.queryForList(sql, paramMap);

        } catch (DataAccessException e) {
            LOGGER.error("UserAnalyseOfDayDaoImpl.stayingData failed ! e :", e);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> allStayingData(List<String> sup,
                                                    String sDate, String eDate) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("suppliers", sup);
            paramMap.put("sDate", sDate);
            paramMap.put("eDate", eDate);

            String sql = "SELECT SUBSTR(T.C_ACTIVECODE, 6, 6) CODE, U.C_IMEI," +
                    " TO_CHAR(U.C_ACTIONDATE, 'yyyy-MM-dd') DAY" +
                    " FROM T_ACTION_ACTIVE T, T_ACTION_USEAPP U WHERE T.C_IMEI = U.C_IMEI" +
                    " AND U.C_KEY = '0' AND SUBSTR(T.C_ACTIVECODE, 6, 6) IN (:suppliers)" +
                    " AND U.C_ACTIONDATE >= TO_DATE(:sDate, 'yyyy-MM-dd')" +
                    " AND U.C_ACTIONDATE < TO_DATE(:eDate, 'yyyy-MM-dd') + 1" +
                    " GROUP BY SUBSTR(T.C_ACTIVECODE, 6, 6), U.C_IMEI," +
                    " TO_CHAR(U.C_ACTIONDATE, 'yyyy-MM-dd')" +
                    " ORDER BY SUBSTR(T.C_ACTIVECODE, 6, 6), TO_CHAR(U.C_ACTIONDATE, 'yyyy-MM-dd')";

            list = namedParameterJdbcTemplate.queryForList(sql, paramMap);

        } catch (DataAccessException e) {
            LOGGER.error("UserAnalyseOfDayDaoImpl.stayingDataAllMonth failed ! e :", e);
        }
        return list;
    }

    @Override
    public void intoTempTable(List<Map<String, Object>> allData, String timeFlag) {
        try {

            // 存入新的数据
            String sql = "INSERT INTO T_STARTAPP_TEMP (C_CODE, C_IMEI, C_DATE, C_FLAG)" +
                    " VALUES (?, ?, ?, ?)";
            List<Object[]> batchArgs = new ArrayList<Object[]>();
            for (int i = 0; i < allData.size(); i++) {
                // 将一条数据存入一个Object数组，再将这个数组存入batchArgs
                Map<String, Object> map = allData.get(i);
                batchArgs.add(new Object[]{map.get("CODE").toString(),
                        map.get("C_IMEI").toString(),
                        map.get("DAY").toString(),
                        timeFlag});
            }
            // 批量插入数据
            jdbcTemplate.batchUpdate(sql, batchArgs);

        } catch (DataAccessException e) {
            LOGGER.error("UserAnalyseOfDayDaoImpl.intoTempTable failed ! e :", e);
        }
    }

    @Override
    public List<Map<String, Object>> getTempData(String timeFlag) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            // 查询用户总数大于27的数据（只需要天数够一个整月的数据）
            String sql = "SELECT * FROM (SELECT T.C_CODE CODE, SUBSTR(T.C_DATE, 1, 7)" +
                    " MONTH, T.C_IMEI IMEI, COUNT(T.C_IMEI) TOTAL" +
                    " FROM T_STARTAPP_TEMP T WHERE T.C_FLAG = ?" +
                    " GROUP BY T.C_CODE, SUBSTR(T.C_DATE, 1, 7), T.C_IMEI" +
                    " ORDER BY T.C_CODE, SUBSTR(T.C_DATE, 1, 7), T.C_IMEI)" +
                    " WHERE TOTAL > 27";

            list = jdbcTemplate.queryForList(sql, new Object[]{timeFlag});

            try {
                // 删除本次查询的数据
                sql = "DELETE FROM T_STARTAPP_TEMP T WHERE T.C_FLAG = ?";
                jdbcTemplate.update(sql, new Object[]{timeFlag});
            } catch (Exception e) {
                LOGGER.error("delete temp data failed ! [C_FLAG : " + timeFlag + "] e :", e);
            }

        } catch (DataAccessException e) {
            LOGGER.error("UserAnalyseOfDayDaoImpl.getTempData failed ! e :", e);
        }
        return list;
    }

    /**
     * 综合数据分析 --------单个供应商查询
     *
     * @param sDate
     * @param eDate
     * @param suppliers
     * @return
     */
    @Override
    public List<Map<String, Object>> selectAnalyseDateList(String sDate, String eDate, List<String> suppliers) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("suppliers", suppliers);
            paramMap.put("sDate", sDate);
            paramMap.put("eDate", eDate);
            String sql = "SELECT C_ID,C_SUP,C_DATE,C_DOWNLOAD,C_ACTIVE,C_ACTIVE_RATE,C_REGISTER,C_REGISTER_RATE,C_START," +
                    " C_USEKEY,C_USE,C_USE_FREECALL,C_REG_D,C_REG_D_RATE,C_REG_W_END,C_REG_W_END_RATE,C_REG_W_ALL," +
                    " C_REG_W_ALL_RATE,C_REG_M_END,C_REG_M_END_RATE,C_REG_M_ALL,C_REG_M_ALL_RATE,C_ACT_D,C_ACT_D_RATE," +
                    " C_ACT_W_END,C_ACT_W_END_RATE,C_ACT_W_ALL,C_ACT_W_ALL_RATE,C_ACT_M_END,C_ACT_M_END_RATE,C_ACT_M_ALL,C_ACT_M_ALL_RATE," +
                    " C_FY_D,C_FY_D_TIME,C_FY_D_RATE,C_FY_W_END,C_FY_W_END_RATE,C_FY_W_END_TIME,C_FY_W_ALL,C_FY_W_ALL_RATE,C_FY_W_ALL_TIME," +
                    " C_FY_M_END,C_FY_M_END_RATE,C_FY_M_END_TIME,C_FY_M_ALL,C_FY_M_ALL_RATE,C_FY_M_ALL_TIME" +
                    " FROM T_EK_ANALYSE_DATA T" +
                    " WHERE TO_DATE(T.C_DATE,'yyyy-MM-dd')>=TO_DATE (:sDate , 'yyyy-MM-dd')" +
                    " AND  TO_DATE(T.C_DATE,'yyyy-MM-dd')< TO_DATE (:eDate , 'yyyy-MM-dd')+1 " +
                    " AND T .C_SUP IN (:suppliers)";
            list = namedParameterJdbcTemplate.queryForList(sql, paramMap);

        } catch (DataAccessException e) {
            LOGGER.error("UserAnalyseOfDayDaoImpl.selectAnalyseDateList failed ! e :", e);
        }
        return list;
    }

    /**
     * 综合数据分析 --------全部供应商查询
     *
     * @param sDate
     * @param eDate
     * @param suppliers
     * @return
     */
    @Override
    public List<Map<String, Object>> selectAllAnalyseDateList(String sDate, String eDate, List<String> suppliers) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("suppliers", suppliers);
            paramMap.put("sDate", sDate);
            paramMap.put("eDate", eDate);
            String sql = "SELECT C_DATE," +
                    " SUM(C_DOWNLOAD) AS C_DOWNLOAD,SUM(C_ACTIVE) AS C_ACTIVE,SUM(C_REGISTER) AS C_REGISTER,SUM(C_START) AS C_START," +
                    " SUM(C_USEKEY) AS C_USEKEY,SUM(C_USE) AS C_USE,SUM(C_USE_FREECALL) AS C_USE_FREECALL," +
                    " SUM(C_REG_D) AS C_REG_D,SUM(C_REG_W_END) AS C_REG_W_END,SUM(C_REG_W_ALL) AS C_REG_W_ALL," +
                    " SUM(C_REG_M_END) AS C_REG_M_END,SUM(C_REG_M_ALL) AS C_REG_M_ALL,SUM(C_ACT_D) AS C_ACT_D," +
                    " SUM(C_ACT_W_END) AS C_ACT_W_END,SUM(C_ACT_W_ALL) AS C_ACT_W_ALL,SUM(C_ACT_M_END) AS C_ACT_M_END," +
                    " SUM(C_ACT_M_ALL) AS C_ACT_M_ALL,SUM(C_FY_D) AS C_FY_D,SUM(C_FY_D_TIME) AS C_FY_D_TIME," +
                    " SUM(C_FY_W_END) AS C_FY_W_END,SUM(C_FY_W_END_TIME) AS C_FY_W_END_TIME,SUM(C_FY_W_ALL) AS C_FY_W_ALL," +
                    " SUM(C_FY_W_ALL_TIME) AS C_FY_W_ALL_TIME,SUM(C_FY_M_END) AS C_FY_M_END," +
                    " SUM(C_FY_M_END_TIME) AS C_FY_M_END_TIME,SUM(C_FY_M_ALL) AS C_FY_M_ALL,SUM(C_FY_M_ALL_TIME) AS C_FY_M_ALL_TIME" +
                    " FROM T_EK_ANALYSE_DATA T" +
                    " WHERE TO_DATE(T.C_DATE,'yyyy-MM-dd')>=TO_DATE (:sDate , 'yyyy-MM-dd')" +
                    " AND  TO_DATE(T.C_DATE,'yyyy-MM-dd')< TO_DATE (:eDate , 'yyyy-MM-dd')+1 " +
                    " AND T .C_SUP IN (:suppliers)" +
                    " GROUP BY C_DATE";
            list = namedParameterJdbcTemplate.queryForList(sql, paramMap);

        } catch (DataAccessException e) {
            LOGGER.error("UserAnalyseOfDayDaoImpl.selectAllAnalyseDateList failed ! e :", e);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> getCompleteData(String sup, String sDate, String eDate, List<String> ids) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            List<Object> args = new ArrayList<>();
            String sql = "SELECT T.C_DATE, T.C_DOWNLOAD, T.C_ACTIVE, T.C_ACTIVE_RATE, T.C_REGISTER," +
                    " T.C_REGISTER_RATE, T.C_START, T.C_USEKEY, T.C_USE, T.C_USE_FREECALL";
            /** 注册用户-次日留存 */
            if (ids.contains("1") && ids.contains("4")) {
                sql += ", T.C_REG_D, T.C_REG_D_RATE";
            }
            /** 注册用户-周留存人数 */
            if (ids.contains("1") && ids.contains("5")) {
                sql += ", T.C_REG_W_END, T.C_REG_W_END_RATE";
            }
            /** 注册用户-周内留存人数 */
            if (ids.contains("1") && ids.contains("6")) {
                sql += ", T.C_REG_W_ALL, T.C_REG_W_ALL_RATE";
            }
            /** 注册用户-月留存 */
            if (ids.contains("1") && ids.contains("7")) {
                sql += ", T.C_REG_M_END, T.C_REG_M_END_RATE";
            }
            /** 注册用户-月内留存 */
            if (ids.contains("1") && ids.contains("8")) {
                sql += ", T.C_REG_M_ALL, T.C_REG_M_ALL_RATE";
            }
            /** 激活用户-次日留存 */
            if (ids.contains("2") && ids.contains("4")) {
                sql += ", T.C_ACT_D, T.C_ACT_D_RATE";
            }
            /** 激活用户-周留存人数 */
            if (ids.contains("2") && ids.contains("5")) {
                sql += ", T.C_ACT_W_END, T.C_ACT_W_END_RATE";
            }
            /** 激活用户-周内留存人数 */
            if (ids.contains("2") && ids.contains("6")) {
                sql += ", T.C_ACT_W_ALL, T.C_ACT_W_ALL_RATE";
            }
            /** 激活用户-月留存 */
            if (ids.contains("2") && ids.contains("7")) {
                sql += " ,T.C_REG_D, T.C_REG_D_RATE";
            }
            /** 激活用户-月内留存 */
            if (ids.contains("2") && ids.contains("8")) {
                sql += ", T.C_REG_D, T.C_FY_D_RATE";
            }
            /** 免费通话-次日留存 */
            if (ids.contains("3") && ids.contains("4")) {
                sql += ", T.C_FY_D, T.C_FY_D_RATE, T.C_FY_D_TIME";
            }
            /** 免费通话-周留存人数 */
            if (ids.contains("3") && ids.contains("5")) {
                sql += ", T.C_FY_W_END, T.C_FY_W_END_RATE, T.C_FY_W_END_TIME";
            }
            /** 免费通话-周内留存人数 */
            if (ids.contains("3") && ids.contains("6")) {
                sql += ", T.C_FY_W_ALL, T.C_FY_W_ALL_RATE, T.C_FY_W_ALL_TIME";
            }
            /** 免费通话-月留存 */
            if (ids.contains("3") && ids.contains("7")) {
                sql += ", T.C_FY_M_END, T.C_FY_M_END_RATE, T.C_FY_M_END_TIME";
            }
            /** 免费通话-月内留存 */
            if (ids.contains("3") && ids.contains("8")) {
                sql += ", T.C_FY_M_ALL, T.C_FY_M_ALL_RATE, T.C_FY_M_ALL_TIME";
            }

            sql += " FROM T_EK_ANALYSE_DATA T WHERE 1 = 1";
            if (!"0".equals(sup)) {
                sql += " AND T.C_SUP = ? AND T.C_DATE >= TO_DATE(?, 'YYYY-MM-DD')" +
                        " AND T.C_DATE < TO_DATE(?, 'YYYY-MM-DD') + 1 ORDER BY T.C_DATE";
                args.add(sup);
            } else {
                sql += " AND T.C_SUP = ? AND T.C_DATE >= TO_DATE(?, 'YYYY-MM-DD')" +
                        " AND T.C_DATE < TO_DATE(?, 'YYYY-MM-DD') + 1 ORDER BY T.C_DATE";
            }

            list = jdbcTemplate.queryForList(sql, args.toArray());

        } catch (DataAccessException e) {
            LOGGER.error("UserAnalyseOfDayDaoImpl.getTempData failed ! e :", e);
        }
        return list;
    }

    @Override
    // 批量保存供应商一天的数据
    public void saveDataOfYesterday(List<Object[]> args) {
        try {
            String sql = "INSERT INTO T_EK_ANALYSE_DATA" +
                    " (C_ID, C_SUP, C_DATE, C_DOWNLOAD, C_ACTIVE, C_ACTIVE_RATE," +
                    " C_REGISTER, C_REGISTER_RATE, C_START, C_USEKEY, C_USE, C_USE_FREECALL," +
                    " C_REG_D, C_REG_D_RATE," +
                    " C_REG_W_END, C_REG_W_END_RATE, C_REG_W_ALL, C_REG_W_ALL_RATE," +
                    " C_REG_M_END, C_REG_M_END_RATE, C_REG_M_ALL, C_REG_M_ALL_RATE," +
                    " C_ACT_D, C_ACT_D_RATE," +
                    " C_ACT_W_END, C_ACT_W_END_RATE, C_ACT_W_ALL, C_ACT_W_ALL_RATE," +
                    " C_ACT_M_END, C_ACT_M_END_RATE, C_ACT_M_ALL, C_ACT_M_ALL_RATE," +
                    " C_FY_D, C_FY_D_RATE, C_FY_D_TIME," +
                    " C_FY_W_END, C_FY_W_END_RATE, C_FY_W_END_TIME, C_FY_W_ALL, C_FY_W_ALL_RATE, C_FY_W_ALL_TIME," +
                    " C_FY_M_END, C_FY_M_END_RATE, C_FY_M_END_TIME, C_FY_M_ALL, C_FY_M_ALL_RATE, C_FY_M_ALL_TIME)" +
                    " VALUES (SEQ_EK_ANALYSE_DATA.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
                    " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
                    " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
                    " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.batchUpdate(sql, args);
        } catch (DataAccessException e) {
            LOGGER.error("UserAnalyseOfDayDaoImpl.saveDataOfYesterday failed ! e :", e);
        }
    }
}