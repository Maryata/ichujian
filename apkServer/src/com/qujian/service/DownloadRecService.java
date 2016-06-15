package com.qujian.service;

/**
 * Created by Maryn on 2016/1/4.
 */
public interface DownloadRecService {

    void recode(String cilentIp, String licenseCn);

    /**
     * 记录下载页面代开行为
     *
     * @param cilentIp  打开人的IP
     * @param licenseCn 供应商代码
     */
    void openRec(String cilentIp, String licenseCn);
}
