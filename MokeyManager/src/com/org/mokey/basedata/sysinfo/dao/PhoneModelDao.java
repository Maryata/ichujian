package com.org.mokey.basedata.sysinfo.dao;

import java.util.Map;

/**
 * Created by Maryn on 2016/4/26.
 */
public interface PhoneModelDao {

    /**
     * 分页查询
     *  @param page      页码
     * @param rows
     * @param brandName @return
     * @param islive
     */
    Map<String, Object> phoneList(int page, int rows, String brandName, String islive);

    /**
     * 新增手机品牌
     *
     * @param name     品牌名称
     * @param userName 操作人
     */
    void addBrand(String name, String userName);

    /**
     * 编辑品牌
     *
     * @param id       品牌id
     * @param name     品牌名称
     * @param userName 操作人
     */
    void editBrand(String id, String name, String userName);

    /**
     * 手机品牌上下架
     *
     * @param id       品牌id
     * @param act      动作: 0.下架，1：上架
     * @param act      类型: 1.品牌，2：型号
     * @param userName 操作人
     */
    void onShelf(String id, String act, String type, String userName);

    /**
     * 分页查询品牌下的型号
     *
     * @param page      页码
     * @param rows      每页数量
     * @param brandId   品牌id
     * @param phoneName 型号名称
     * @param islive
     * @return
     */
    Map<String, Object> modelList(Integer page, Integer rows, String brandId, String phoneName, String islive);

    /**
     * 新增指定手机品牌下的型号
     *
     * @param name     型号名称
     * @param code     型号代码
     * @param brandId  品牌id
     * @param userName 操作人
     */
    void addModel(String name, String code, String brandId, String userName);

    /**
     * 编辑型号
     *
     * @param name     型号名称
     * @param code     型号代码
     * @param brandId  品牌id
     * @param userName 操作人
     * @param id       型号id
     */
    void editModel(String name, String code, String brandId, String userName, String id);
}
