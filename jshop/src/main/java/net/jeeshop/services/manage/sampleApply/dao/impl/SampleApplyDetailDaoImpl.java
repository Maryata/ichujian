package net.jeeshop.services.manage.sampleApply.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.manage.sampleApply.bean.SampleApplyDetail;
import net.jeeshop.services.manage.sampleApply.dao.SampleApplyDetailDao;
@Repository("sampleApplyDetailDaoManage")
public class SampleApplyDetailDaoImpl implements SampleApplyDetailDao{
	@Resource
	private BaseDao dao;
	@Override
	public int insert(SampleApplyDetail e) {
		// TODO Auto-generated method stub
		return dao.insert("manage.sampleApply.insertDetail", e);
	}

	@Override
	public int delete(SampleApplyDetail e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(SampleApplyDetail e) {
		// TODO Auto-generated method stub
		return dao.update("manage.sampleApply.update", e);
	}

	@Override
	public SampleApplyDetail selectOne(SampleApplyDetail e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PagerModel selectPageList(SampleApplyDetail e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SampleApplyDetail> selectList(SampleApplyDetail e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteById(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SampleApplyDetail selectById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public BaseDao getDao() {
		return dao;
	}

	public void setDao(BaseDao dao) {
		this.dao = dao;
	}

	@Override
	public List<SampleApplyDetail> checkMessage(SampleApplyDetail smd) {
		// TODO Auto-generated method stub
		return dao.selectList("manage.sampleApply.checkMessage", smd);
	}

	@Override  //Map<string,Object>
	public List<SampleApplyDetail> checkMoble(SampleApplyDetail smd) {
		// TODO Auto-generated method stub
		return  dao.selectList("manage.sampleApply.checkMoble", smd);
				//(Map<String, Object>) dao.selectOne("manage.sampleApply.checkMoble", smd);
	}

	@Override
	public int getSampleApplyDetailService(SampleApplyDetail smd) {
		// TODO Auto-generated method stub
		return dao.getCount("manage.sampleApply.selectNumber", smd);
	}



}
