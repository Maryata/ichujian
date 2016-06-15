package net.jeeshop.services.front.unionpay;

import net.jeeshop.core.Services;
import net.jeeshop.services.front.unionpay.bean.UnionPay;

public interface UnionPayService extends Services<UnionPay> {

	public UnionPay selectByOrderId(String id);

}
