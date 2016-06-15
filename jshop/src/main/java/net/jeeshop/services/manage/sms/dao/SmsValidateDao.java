package net.jeeshop.services.manage.sms.dao;

import net.jeeshop.core.DaoManager;
import net.jeeshop.services.manage.sms.bean.SmsValidate;

public interface SmsValidateDao extends DaoManager<SmsValidate> {
	
	public int getCount(SmsValidate e);

}
