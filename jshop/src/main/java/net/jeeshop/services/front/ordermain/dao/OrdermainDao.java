package net.jeeshop.services.front.ordermain.dao;

import net.jeeshop.core.DaoManager;
import net.jeeshop.services.front.ordermain.bean.Ordermain;

public interface OrdermainDao extends DaoManager<Ordermain> {

	int deleteByOrderId(String id);

}
