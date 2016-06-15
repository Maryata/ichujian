package com.qujian.service.impl;

import com.qujian.dao.DownloadRecDao;
import com.qujian.service.DownloadRecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Maryn on 2016/1/4.
 */
@Service
public class DownloadRecServiceImpl implements DownloadRecService {

    @Autowired
    private DownloadRecDao downloadRecDao;

    @Override
    public void recode(String cilentIp, String licenseCn) {
        downloadRecDao.recode(cilentIp, licenseCn);
    }

    @Override
    public void openRec(String cilentIp, String licenseCn) {
        downloadRecDao.openRec(cilentIp, licenseCn);
    }
}
