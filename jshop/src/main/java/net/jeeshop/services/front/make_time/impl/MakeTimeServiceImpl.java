package net.jeeshop.services.front.make_time.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.front.make_time.MakeTimeService;
import net.jeeshop.services.front.make_time.bean.MakeTime;
import net.jeeshop.services.front.make_time.dao.MakeTimeDao;
@Service("makeTimeServiceFront")
public class MakeTimeServiceImpl extends ServersManager<MakeTime , MakeTimeDao> implements MakeTimeService{
	@Resource(name = "makeTimeDaoFront")
	@Override
	public void setDao(MakeTimeDao makeTimeDao) {
		// TODO Auto-generated method stub
		this.dao=makeTimeDao;
	}

	@Override
	public MakeTime SelectDays(MakeTime makeTime) {
		return dao.SelectDays(makeTime);
		// TODO Auto-generated method stub
		
	}

}
