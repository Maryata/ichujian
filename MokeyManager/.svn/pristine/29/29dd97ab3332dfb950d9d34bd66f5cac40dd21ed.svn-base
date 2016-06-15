package com.org.mokey.report.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.org.mokey.report.dao.StatisticsDao;
import com.org.mokey.report.service.StatisticsService;

/**
 * 后台数据统计指标分析
 */
public class StatisticsServiceImpl implements StatisticsService {
	private StatisticsDao statisticsDao;

	public StatisticsDao getStatisticsDao() {
		return statisticsDao;
	}

	public void setStatisticsDao(StatisticsDao statisticsDao) {
		this.statisticsDao = statisticsDao;
	}

	@Override
	// 按键设置
	public Map<String, Object> keySet(String sDate, String eDate) {
		return statisticsDao.statisticsDao(sDate, eDate);
	}

	@Override
	public List<Map<String, Object>> updatedIds() {
		return statisticsDao.updatedIds();
	}

	/* (non-Javadoc)
	 * @see com.org.mokey.report.service.StatisticsService#brandUser()
	 */
	@Override
	public List<Map<String, Object>> brandUser() {
		return statisticsDao.brandUser();
	}

	/* (non-Javadoc)
	 * @see com.org.mokey.report.service.StatisticsService#userGrowth(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> userGrowth(Date sDate, Date eDate) {
		return statisticsDao.userGrowth(sDate, eDate);
	}

	/* (non-Javadoc)
	 * @see com.org.mokey.report.service.StatisticsService#brandUserGrowth(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> brandUserGrowth(Date sDate, Date eDate) {
		return statisticsDao.brandUserGrowth(sDate, eDate);
	}
	
}
