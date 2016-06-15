package net.jeeshop.services.manage.dataApply.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.manage.dataApply.DataApplyService;
import net.jeeshop.services.manage.dataApply.bean.DataApply;
import net.jeeshop.services.manage.dataApply.dao.DataApplyDao;
@Service("dataApplyServiceManage")
public class DataApplyServiceImpl extends ServersManager<DataApply, DataApplyDao> implements DataApplyService{
    @Resource(name="dataApplyDaoManage")
	@Override
	public void setDao(DataApplyDao dataApplyDao) {
		// TODO Auto-generated method stub
		this.dao=dataApplyDao;
	}

}
