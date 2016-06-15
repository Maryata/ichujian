package net.jeeshop.services.front.alipay.dao.impl;

import java.util.List;

import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.front.alipay.bean.AliPay;
import net.jeeshop.services.front.alipay.dao.AliPayDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("AliPayDao")
public class AliPayDaoImpl implements AliPayDao {

	@Autowired
	private BaseDao dao;
	
	@Override
	public void logResponse(AliPay aliPay) {
		dao.insert("alipay.logResponse", aliPay);
	}

	@Override
	public int insert(AliPay e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(AliPay e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(AliPay e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AliPay selectOne(AliPay e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PagerModel selectPageList(AliPay e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AliPay> selectList(AliPay e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteById(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AliPay selectById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int selectExistsLog(AliPay aliPay){
		return dao.getCount("alipay.selectExistsLog", aliPay);
	}
}
