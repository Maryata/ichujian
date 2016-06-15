package com.org.mokey.basedata.sysinfo.service.impl;

import com.org.mokey.basedata.sysinfo.dao.EKGameCueDao;
import com.org.mokey.basedata.sysinfo.dao.GameCueDao;
import com.org.mokey.basedata.sysinfo.service.EKGameCueService;
import com.org.mokey.basedata.sysinfo.service.GameCueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EKGameCueServiceImpl implements EKGameCueService {

    @Autowired
    private EKGameCueDao eKGameCueDao;

    @Override
    // 询游戏提示语
    public List<Map<String, Object>> gameCue() {
        return eKGameCueDao.gameCue();
    }

    @Override
    // 添加提示语
    public void addCue(String addTitle) {
        eKGameCueDao.addCue(addTitle);
    }

    @Override
    // 修改提示语
    public void modifyCue(String cid, String title, String islive) {
        eKGameCueDao.modifyCue(cid, title, islive);
    }

    @Override
    // 使当前提示内容生效/无效
    public void isvalid(String cid, String islive) {
        eKGameCueDao.isvalid(cid, islive);
    }

    @Override
    // 删除提示内容
    public void deleteCol(String cid) {
        eKGameCueDao.deleteCol(cid);
    }

}
