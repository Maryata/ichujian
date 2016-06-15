package net.jeeshop.services.manage.sms.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.manage.sms.SmsValidateService;
import net.jeeshop.services.manage.sms.bean.SmsValidate;
import net.jeeshop.services.manage.sms.dao.SmsValidateDao;

@Service("smsValidateServiceManage")
public class SmsValidateServiceImpl extends ServersManager<SmsValidate, SmsValidateDao> implements SmsValidateService {

	@Resource(name = "smsValidateDaoManage")
	@Override
	public void setDao(SmsValidateDao dao) {
		this.dao = dao;
	}

}
