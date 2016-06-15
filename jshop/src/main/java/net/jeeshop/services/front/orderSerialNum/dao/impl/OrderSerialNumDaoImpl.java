package net.jeeshop.services.front.orderSerialNum.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.front.orderSerialNum.bean.OrderSerialNum;
import net.jeeshop.services.front.orderSerialNum.dao.OrderSerialNumDao;

/**
 * 订单-用户-日期-顺序号
 * @author Maryn
 *
 */
@Repository("OrderSerialNumDao")
public class OrderSerialNumDaoImpl implements OrderSerialNumDao {

	@Resource
	private BaseDao dao;
	
	public void setDao(BaseDao dao) {
		this.dao = dao;
	}
	
	@Override
	public int insert(OrderSerialNum e) {
		return dao.insert("orderSerialNum.insert", e);
	}

	@Override
	public int delete(OrderSerialNum e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(OrderSerialNum e) {
		return dao.update("orderSerialNum.update", e);
	}

	@Override
	public OrderSerialNum selectOne(OrderSerialNum e) {
		return (OrderSerialNum) dao.selectOne("orderSerialNum.selectOne", e);
	}

	@Override
	public PagerModel selectPageList(OrderSerialNum e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderSerialNum> selectList(OrderSerialNum e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteById(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public OrderSerialNum selectById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	// 通过会员工号获取订单序列号
	public String getSerialNum(OrderSerialNum osn) {
		return (String) dao.selectOne("orderSerialNum.getSerialNum", osn);
	}

}
