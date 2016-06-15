package net.jeeshop.services.manage.commision.impl;


import javax.annotation.Resource;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.manage.commision.CommisionService;
import net.jeeshop.services.manage.commision.bean.Commision;
import net.jeeshop.services.manage.commision.dao.CommisionDao;

import org.springframework.stereotype.Service;

@Service("commisionServiceManage")
public class CommisionServiceImpl extends ServersManager<Commision, CommisionDao> implements CommisionService{
	@Resource(name = "commisionDaoManage")
	@Override
	public void setDao(CommisionDao commisionDao) {
		// TODO Auto-generated method stub
		this.dao=commisionDao;
	}



}
