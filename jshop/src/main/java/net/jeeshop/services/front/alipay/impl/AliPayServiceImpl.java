package net.jeeshop.services.front.alipay.impl;

import java.util.List;

import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.front.alipay.AliPayService;
import net.jeeshop.services.front.alipay.bean.AliPay;
import net.jeeshop.services.front.alipay.dao.AliPayDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("AliPayService")
public class AliPayServiceImpl implements AliPayService{

	@Autowired
	private AliPayDao aliPayDao;
	
	@Override
	// 记录响应信息
	public void logResponse(AliPay aliPay) {
		aliPayDao.logResponse(aliPay);
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
	public int deletes(String[] ids) {
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
	public AliPay selectById(String id) {
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
	public int selectExistsLog(AliPay aliPay){
		return aliPayDao.selectExistsLog(aliPay);
	}
}
