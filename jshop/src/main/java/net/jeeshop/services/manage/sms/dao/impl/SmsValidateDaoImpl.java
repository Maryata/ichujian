package net.jeeshop.services.manage.sms.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.manage.sms.bean.SmsValidate;
import net.jeeshop.services.manage.sms.dao.SmsValidateDao;

import org.springframework.stereotype.Repository;

@Repository("smsValidateDaoManage")
public class SmsValidateDaoImpl implements SmsValidateDao {

	@Resource
	private BaseDao dao;

	@Override
	public int insert(SmsValidate e) {
		return dao.insert("manage.smsValidate.insert", e);
	}

	@Override
	public int delete(SmsValidate e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(SmsValidate e) {
		return dao.update("manage.smsValidate.update", e);
	}

	@Override
	public SmsValidate selectOne(SmsValidate e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PagerModel selectPageList(SmsValidate e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SmsValidate> selectList(SmsValidate e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteById(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SmsValidate selectById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCount(SmsValidate e) {
		return Integer.valueOf(dao.selectOne("manage.smsValidate.getCount", e).toString());
	}
	

}
