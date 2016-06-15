package net.jeeshop.services.front.unionpaylog.dao;

import net.jeeshop.services.front.unionpaylog.bean.UnionPayLog;

public interface UnionPayLogDao {

	// 记录回调信息
	public void logResponse(UnionPayLog unionPayLog);

	// 根据回 <调方法名、订单序列号、银联查询号、回调状态> 查询日志是否已经记录
	public int isExists(UnionPayLog unionPayLog);

}
