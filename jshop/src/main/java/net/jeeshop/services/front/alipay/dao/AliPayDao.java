package net.jeeshop.services.front.alipay.dao;

import net.jeeshop.core.DaoManager;
import net.jeeshop.services.front.alipay.bean.AliPay;



public interface AliPayDao extends DaoManager<AliPay> {

	// 记录响应信息
	public void logResponse(AliPay aliPay);

	public int selectExistsLog(AliPay aliPay);

}
