package net.jeeshop.services.front.orderSerialNum.dao;

import net.jeeshop.core.DaoManager;
import net.jeeshop.services.front.orderSerialNum.bean.OrderSerialNum;

public interface OrderSerialNumDao extends DaoManager<OrderSerialNum> {

	// 通过会员工号获取订单序列号
	public String getSerialNum(OrderSerialNum osn);
}
