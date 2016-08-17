package com.sys.freecall.service;

import java.util.List;
import java.util.Map;

/**
 * Created by Maryn on 2016/7/8.
 */
public interface FreeCallService {

    /**
     * 走马灯广告
     *
     * @return
     */
    List<Map<String, Object>> marqueeBanner();

    /**
     * 轮播广告
     *
     * @return
     */
    List<Map<String, Object>> advert();

    /**
     * 套餐列表
     *
     * @return
     */
    List<Map<String, Object>> meal();

    /**
     * 充值到期时间
     *
     * @param uid
     * @return
     */
    String term(String uid);
}
