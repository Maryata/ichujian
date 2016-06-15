package net.jeeshop.services.front.unionpay.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.front.unionpay.bean.UnionPay;
import net.jeeshop.services.front.unionpay.dao.UnionPayDao;

@Repository
public class UnionPayDaoImpl implements UnionPayDao {

	@Resource
	private BaseDao dao;
	
	@Override
	public int insert(UnionPay e) {
		return dao.insert("front.unionPay.insert", e);
	}

	@Override
	public int delete(UnionPay e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(UnionPay e) {
		return dao.update("front.unionPay.update", e);
	}

	@Override
	public UnionPay selectOne(UnionPay e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PagerModel selectPageList(UnionPay e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UnionPay> selectList(UnionPay e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteById(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public UnionPay selectById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	// 根据订单id查询支付信息
	public UnionPay selectByOrderId(String orderid) {
		return (UnionPay) dao.selectOne("front.unionPay.selectByOrderId", orderid);
	}

}
