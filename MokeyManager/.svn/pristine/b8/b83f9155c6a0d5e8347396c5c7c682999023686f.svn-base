package com.org.mokey.basedata.sysinfo.service;

import java.util.List;
import java.util.Map;

/**
 * Created by Maryn on 2016/1/13.
 */
public interface ExchangeService {
    /**
     * 查询所有未完成的申请记录
     * @return
     * @param page 分页页码
     * @param pname 产品名称
     * @param state 审核状态
     */
    Map<String,Object> uncompletedExchange(int page, String pname, String state);

    /**
     * 审核通过
     * @param id 申请兑换id
     * @param uid 用户id
     * @param pid 产品id
     * @param cost 产品花费积分
     * @param userName 审核人
     */
    void complete(String id, String uid, String pid, String cost, String userName);
}
