package net.jeeshop.services.front.unionpay.dao;

import net.jeeshop.core.DaoManager;
import net.jeeshop.services.front.unionpay.bean.UnionPay;

public interface UnionPayDao extends DaoManager<UnionPay>{

	/**
	 * 根据订单id查询支付信息
	 * @param orderId 订单id
	 * @return
	 */
	public UnionPay selectByOrderId(String orderId);

}
