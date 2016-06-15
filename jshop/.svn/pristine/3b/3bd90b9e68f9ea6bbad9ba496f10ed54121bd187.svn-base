package net.jeeshop.services.front.unionpaylog.impl;

import java.util.List;

import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.front.unionpaylog.UnionPayLogService;
import net.jeeshop.services.front.unionpaylog.bean.UnionPayLog;
import net.jeeshop.services.front.unionpaylog.dao.UnionPayLogDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnionPayLogServiceImpl implements UnionPayLogService {

	@Autowired
	private UnionPayLogDao unionPayLogDao;
	
	@Override
	public int insert(UnionPayLog e) {
		return 0;
	}

	@Override
	public int delete(UnionPayLog e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deletes(String[] ids) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(UnionPayLog e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public UnionPayLog selectOne(UnionPayLog e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UnionPayLog selectById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PagerModel selectPageList(UnionPayLog e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UnionPayLog> selectList(UnionPayLog e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	// 记录回调信息
	public void logResponse(UnionPayLog unionPayLog) {
		unionPayLogDao.logResponse(unionPayLog);
	}

	@Override
	// 根据回 <调方法名、订单序列号、银联查询号、回调状态> 查询日志是否已经记录
	public int isExists(UnionPayLog unionPayLog) {
		return unionPayLogDao.isExists(unionPayLog);
	}

}
