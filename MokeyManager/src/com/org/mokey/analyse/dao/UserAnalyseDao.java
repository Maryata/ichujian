package com.org.mokey.analyse.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by Maryn on 2016/1/18.
 */
public interface UserAnalyseDao {

    /**
     * 当前用户可见的供应商
     *
     * @param userId 用户id
     * @param sup    查询的供应商
     * @return
     */
    List<Map<String, Object>> visibleSupplier(String userId, String sup);

    /**
     * 查询品牌下的供应商
     *
     * @param userId 用户id
     * @param sup    供应商代码
     * @param brand  品牌id
     * @return
     */
    List<Map<String, Object>> visibleSupplierByBrand(String userId, String sup, String brand);

    /**
     * 活跃用户数据
     *
     * @param suppliers 供应商
     * @param sNow      开始时间
     * @param eNow      结束时间
     * @param timeType  统计维度 ： 1:日、2:周、3:月，注意，周和日返回的都是按日分组的数据，需要手动计算周数据
     * @return
     */
    List<Map<String, Object>> activeUserData(
            List<String> suppliers, String sNow, String eNow, String timeType);

    /**
     * 一段时间内的下载用户数据
     *
     * @param suppliers 供应商代码集合
     * @param sNow      开始时间，格式：yyyy-MM-dd
     * @param eNow      结束时间，格式：yyyy-MM-dd
     * @param timeType  统计维度 ： 1:日、2:周、3:月，注意，周和日返回的都是按日分组的数据，需要手动计算周数据
     * @return
     */
    List<Map<String, Object>> downloadUserData(
            List<String> suppliers, String sNow, String eNow, String timeType);

    /**
     * 一段时间内的激活用户数据
     *
     * @param suppliers 供应商代码集合
     * @param sNow      开始时间，格式：yyyy-MM-dd
     * @param eNow      结束时间，格式：yyyy-MM-dd
     * @param timeType  统计维度 ： 1:日、2:周、3:月，注意，周和日返回的都是按日分组的数据，需要手动计算周数据
     * @return
     */
    List<Map<String, Object>> activationUserData(
            List<String> suppliers, String sNow, String eNow, String timeType);


    /**
     * 一段时间内的注册用户数据
     *
     * @param suppliers 供应商代码集合
     * @param sNow      开始时间，格式：yyyy-MM-dd
     * @param eNow      结束时间，格式：yyyy-MM-dd
     * @param timeType  统计维度 ： 1:日、2:周、3:月，注意，周和日返回的都是按日分组的数据，需要手动计算周数据
     * @return
     */
    List<Map<String, Object>> registerUserData(
            List<String> suppliers, String sNow, String eNow, String timeType);

    /**
     * 查询每天启动过的用户（第一天除外）
     *
     * @param suppliers 供应商
     * @param date      日期
     * @return
     */
    List<Map<String, Object>> stayingData(List<String> suppliers, String date);

    /**
     * 根据指定月的第一天和最后一天查询当月所有数据
     *
     * @param sup   供应商
     * @param sDate 月初
     * @param eDate 月末
     * @return
     */
    List<Map<String, Object>> allStayingData(List<String> sup,
                                             String sDate, String eDate);

    /**
     * 将查询到的用户启动触键APP的数据存入临时表
     *
     * @param allData  查询到的数据
     * @param timeFlag 当前时间
     */
    void intoTempTable(List<Map<String, Object>> allData, String timeFlag);

    /**
     * 查询临时表，获取每个用户每个月的启动天数
     *
     * @param timeFlag
     * @return
     */
    List<Map<String, Object>> getTempData(String timeFlag);

    List<Map<String, Object>> getCompleteData(String sup, String sDate, String eDate, List<String> ids);

    /**
     * 获取所有供应商
     *
     * @return
     */
    List<Map<String, Object>> getAllSuppliers();

    /**
     * 批量保存供应商一天的数据
     *
     * @param args
     */
    void saveDataOfYesterday(List<Object[]> args);

    /**
     * 综合数据分析 ---------单个供应商查询
     *
     * @param sDate
     * @param eDate
     * @param sup
     * @return
     */
    List<Map<String, Object>> selectAnalyseDateList(String sDate, String eDate, List<String> sup);

    /**
     * 综合数据分析 ---------全部供应商查询
     *
     * @param sDate
     * @param eDate
     * @param sup
     * @return
     */
    List<Map<String, Object>> selectAllAnalyseDateList(String sDate, String eDate, List<String> sup);

    /**
     * 查询用户可见的所有品牌
     *
     * @param userId
     * @return
     */
    List<Map<String, Object>> getBrands(String userId);

    /**
     * 查询指定品牌的所有渠道
     *
     * @param brand  品牌id
     * @param userId
     * @return
     */
    List<Map<String, Object>> getSuppliersByBrand(String brand, String userId);

    /**
     *注册和激活用户总数
     *
     * @param sup
     * @return
     */
    List<Map<String,Object>> selectReg_ActCount(List<String> sup);

    /**
     * 使用或启动 用户列表
     * @param sup
     * @param sDate
     * @param eDate
     * @return
     */
    List<Map<String,Object>> selectUserList(List<String> sup, String sDate, String eDate);

    /**
     * 注册用户列表
     * @param sup
     * @return
     */
    List<Map<String,Object>> selectRegList(List<String> sup);
}