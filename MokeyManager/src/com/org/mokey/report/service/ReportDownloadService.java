package com.org.mokey.report.service;

import java.util.List;
import java.util.Map;

public interface ReportDownloadService {
        Map<String,Object> getReportDownload(String time_s,String time_e,int start,int limit);
        void deleteReportDownload(String id);
        void saveReport(Map<String, Object> map);
}
