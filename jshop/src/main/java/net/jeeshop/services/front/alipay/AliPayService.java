package net.jeeshop.services.front.alipay;

import net.jeeshop.core.Services;
import net.jeeshop.services.front.alipay.bean.AliPay;


public interface AliPayService extends Services<AliPay>{

	public void logResponse(AliPay aliPay);

	public int selectExistsLog(AliPay aliPay);

}
