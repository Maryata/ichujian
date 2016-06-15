package com.org.mokey.basedata.sysinfo.service.impl;

import com.org.mokey.basedata.sysinfo.dao.EKRewardDao;
import com.org.mokey.basedata.sysinfo.service.EKRewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 签到奖励
 * Created by vpc on 2016/4/20.
 */
@Service
public class EKRewardServiceImpl implements EKRewardService {
    @Autowired
    private EKRewardDao eKRewardDao;

    /**
     * 加载列表页面
     *
     * @param page
     * @param type
     * @return
     */
    @Override
    public Map<String, Object> list(int page, String type) {
        return eKRewardDao.list(page, type);
    }

    /**
     * 修改
     *
     * @param args
     */
    @Override
    public void updateReward(List<Object> args) {
         eKRewardDao.updateReward(args);
    }
}
