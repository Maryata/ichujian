package net.jeeshop.services.manage.sampleApply.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.manage.sampleApply.bean.SampleApply;
import net.jeeshop.services.manage.sampleApply.dao.SampleApplyDao;
@Repository("sampleApplyDaoManage")
public class SampleApplyDaoImpl implements SampleApplyDao{
	@Resource
	private BaseDao dao;
	@Override
	public int insert(SampleApply e) {
		// TODO Auto-generated method stub
		return dao.insert("manage.sampleApply.insert", e);
	}

	@Override
	public int delete(SampleApply e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(SampleApply e) {//manage.sampleApply.toupdate
		return dao.update("manage.sampleApply.toupdate", e);
	}

	@Override
	public SampleApply selectOne(SampleApply e) {
		return (SampleApply) dao.selectOne("manage.sampleApply.selectOne", e);
	}

	@Override
	public PagerModel selectPageList(SampleApply e) {
		// TODO Auto-generated method stub
		return dao.selectPageList("manage.sampleApply.selectPageList",
				"manage.sampleApply.selectPageCount", e);
	}

	@Override
	public List<SampleApply> selectList(SampleApply e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteById(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SampleApply selectById(String id) {
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
	public int judgeSampleNumber(SampleApply sa) {
		// TODO Auto-generated method stub
		return dao.getCount("manage.sampleApply.judgeSampleNumber", sa);
	}

	@Override
	public int selectCount(SampleApply sa) {
		// TODO Auto-generated method stub
		return dao.getCount("manage.sampleApply.selectCount", sa);
	}

}
