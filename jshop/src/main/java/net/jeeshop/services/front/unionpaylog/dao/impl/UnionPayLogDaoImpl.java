package net.jeeshop.services.front.unionpaylog.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.services.front.unionpaylog.bean.UnionPayLog;
import net.jeeshop.services.front.unionpaylog.dao.UnionPayLogDao;

@Repository
public class UnionPayLogDaoImpl implements UnionPayLogDao {

	@Autowired
	private BaseDao dao;
	@Override
	// 记录回调信息
	public void logResponse(UnionPayLog unionPayLog) {
		dao.insert("unionPayLog.logResponse", unionPayLog);
	}
	
	@Override
	// 根据回 <调方法名、订单序列号、银联查询号、回调状态> 查询日志是否已经记录
	public int isExists(UnionPayLog unionPayLog) {
		return dao.getCount("unionPayLog.isExists", unionPayLog);
	}

}
