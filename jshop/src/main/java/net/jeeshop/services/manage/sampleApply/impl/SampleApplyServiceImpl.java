package net.jeeshop.services.manage.sampleApply.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.manage.sampleApply.SampleApplyService;
import net.jeeshop.services.manage.sampleApply.bean.SampleApply;
import net.jeeshop.services.manage.sampleApply.dao.SampleApplyDao;
@Service("sampleApplyServiceManage")
public class SampleApplyServiceImpl extends ServersManager<SampleApply,SampleApplyDao> implements SampleApplyService{
	@Resource(name="sampleApplyDaoManage")
	@Override
	public void setDao(SampleApplyDao sampleApplyDao) {
		// TODO Auto-generated method stub
		this.dao=sampleApplyDao;
	}

	@Override
	public int judgeSampleNumber(SampleApply sa) {
		// TODO Auto-generated method stub
		int number=dao.judgeSampleNumber(sa);
		return number;
	}

	@Override
	public int selectCount(SampleApply sa) {
		// TODO Auto-generated method stub
		return dao.selectCount(sa);
	}

}
