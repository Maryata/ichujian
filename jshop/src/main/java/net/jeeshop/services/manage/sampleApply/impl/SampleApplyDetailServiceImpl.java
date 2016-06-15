package net.jeeshop.services.manage.sampleApply.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.front.product.bean.Product;
import net.jeeshop.services.manage.dataCenter.bean.DataFile;
import net.jeeshop.services.manage.product.ProductService;
import net.jeeshop.services.manage.sampleApply.SampleApplyDetailService;
import net.jeeshop.services.manage.sampleApply.bean.SampleApply;
import net.jeeshop.services.manage.sampleApply.bean.SampleApplyDetail;
import net.jeeshop.services.manage.sampleApply.dao.SampleApplyDetailDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("sampleApplyDetailServiceManage")
public class SampleApplyDetailServiceImpl extends ServersManager<SampleApplyDetail,SampleApplyDetailDao> implements SampleApplyDetailService{
	@Resource(name="sampleApplyDetailDaoManage")
	/*@Autowired
	private ProductService productService;*/
	@Override
	public void setDao(SampleApplyDetailDao sampleApplyDetailDao) {
		// TODO Auto-generated method stub
		this.dao=sampleApplyDetailDao;
	}

	@Override
	public void inserDetail(SampleApply sa, SampleApplyDetail smd) {
 		 
 		 
	}
  
	@Override
	public List<SampleApplyDetail> checkMessage(SampleApplyDetail smd) {
		// TODO Auto-generated method stub
		return dao.checkMessage(smd);
	}

	@Override
	public List<SampleApplyDetail> checkMoble(SampleApplyDetail smd) {
		// TODO Auto-generated method stub
		return dao.checkMoble(smd);
	}

	@Override
	public int getSampleApplyDetailService(SampleApplyDetail smd) {
		// TODO Auto-generated method stub
		return dao.getSampleApplyDetailService(smd);
	}

}
