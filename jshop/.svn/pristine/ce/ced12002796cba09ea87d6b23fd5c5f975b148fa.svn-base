package net.jeeshop.services.manage.commision.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.manage.commision.bean.Commision;
import net.jeeshop.services.manage.commision.dao.CommisionDao;

import org.springframework.stereotype.Repository;

@Repository("commisionDaoManage")
public class CommisionDaoImpl implements CommisionDao{
	@Resource
	private BaseDao dao;
	@Override
	public int insert(Commision e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Commision e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Commision e) {
		// TODO Auto-generated method stub
		return dao.update("manage.commision.update", e);
	}

	@Override
	public Commision selectOne(Commision e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PagerModel selectPageList(Commision e) {
		// TODO Auto-generated method stub
		return dao.selectPageList("manage.commision.selectPageList", "manage.commision.selectPageCount", e);
	}

	@Override
	public List<Commision> selectList(Commision e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteById(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Commision selectById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public BaseDao getDao() {
		return dao;
	}

	public void setDao(BaseDao dao) {
		this.dao = dao;
	}

}
