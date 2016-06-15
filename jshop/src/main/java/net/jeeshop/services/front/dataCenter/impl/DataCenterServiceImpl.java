package net.jeeshop.services.front.dataCenter.impl;

import java.util.List;

import javax.annotation.Resource;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.front.dataCenter.DataCenterService;
import net.jeeshop.services.front.dataCenter.bean.DataApply;
import net.jeeshop.services.front.dataCenter.bean.DataCenter;
import net.jeeshop.services.front.dataCenter.bean.DataFile;
import net.jeeshop.services.front.dataCenter.dao.DataCenterDao;

import org.springframework.stereotype.Service;

@Service("dataCenterServiceFront")
public class DataCenterServiceImpl extends ServersManager<DataCenter, DataCenterDao> implements DataCenterService{

	@Resource(name = "dataCenterDaoFront")
	@Override
	public void setDao(DataCenterDao dao) {
        this.dao = dao;
    }

	@Override
	public List<DataFile> selectDataFiles(DataFile df) {
		return this.getDao().selectDataFiles(df);
	}

	@Override
	public int insertDataApply(DataApply e) {
		return this.getDao().insertDataApply(e);
	}

}
