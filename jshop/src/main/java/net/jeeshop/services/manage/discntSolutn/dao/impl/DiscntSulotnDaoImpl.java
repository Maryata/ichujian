package net.jeeshop.services.manage.discntSolutn.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.manage.discntSolutn.bean.DiscntDetail;
import net.jeeshop.services.manage.discntSolutn.bean.DiscntSolutn;
import net.jeeshop.services.manage.discntSolutn.dao.DiscntSolutnDao;

@Repository("discntSolutnDao")
public class DiscntSulotnDaoImpl implements DiscntSolutnDao{

	@Autowired
	private BaseDao dao;
	
	@Override
	public int insert(DiscntSolutn e) {
		return dao.insert("manage.discntSolutn.insert", e);
	}

	@Override
	public int delete(DiscntSolutn e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(DiscntSolutn e) {
		return dao.update("manage.discntSolutn.update",e);
	}

	@Override
	public DiscntSolutn selectOne(DiscntSolutn e) {
		return (DiscntSolutn) dao.selectOne("manage.discntSolutn.selectOne", e);
	}

	@Override
	public PagerModel selectPageList(DiscntSolutn e) {
		return dao.selectPageList("manage.discntSolutn.selectPageList", "manage.discntSolutn.selectPageCount", e);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DiscntSolutn> selectList(DiscntSolutn e) {
		return dao.selectList("manage.discntSolutn.selectList",e);
	}

	@Override
	public int deleteById(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DiscntSolutn selectById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	// 保存方案详情
	public void insertDetail(DiscntDetail detail) {
		dao.insert("manage.discntSolutn.insertDetail", detail);
	}

	@SuppressWarnings("unchecked")
	@Override
	// 根据折扣方案id查询方案详情
	public List<DiscntDetail> selectDiscntDetail(DiscntDetail dd) {
		return dao.selectList("manage.discntSolutn.selectDiscntDetail", dd);
	}

	@Override
	// 更新方案详情
	public void updateDetail(DiscntDetail detail) {
		dao.update("manage.discntSolutn.updateDetail",detail);
	}

	@Override
	// 根据id删除方案详情
	public int delDetailById(DiscntDetail dd) {
		return dao.delete("manage.discntSolutn.delDetailById", dd);
	}

	@Override
	public DiscntDetail selectAccountRate(DiscntDetail e) {
		return (DiscntDetail) dao.selectOne("manage.discntSolutn.selectAccountRate", e);
	}

}
