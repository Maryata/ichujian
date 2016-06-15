package com.org.mokey.basedata.sysinfo.service;

import java.util.List;
import java.util.Map;

/**
 * 签到奖励
 * Created by vpc on 2016/4/20.
 */
public interface EKRewardService {
    /**
     * 加载 列表页面
     *
     * @param page
     * @param type
     * @return
     */
    Map<String, Object> list(int page, String type);

    /**
     * 修改
     *
     * @param args
     */
    void updateReward(List<Object> args);
}
