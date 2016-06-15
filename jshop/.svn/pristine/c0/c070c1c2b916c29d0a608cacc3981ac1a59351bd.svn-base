package net.jeeshop.services.manage.systemDict.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.manage.systemDict.bean.SystemDict;
import net.jeeshop.services.manage.systemDict.dao.SystemDictDao;

@Repository
public class SystemDictDaoImpl implements SystemDictDao {

	@Autowired
	private BaseDao dao;
	
	@Override
	public int insert(SystemDict e) {
		return dao.insert("systemDict.insert", e);
	}

	@Override
	// 根据id删除字典分类数据
	public int delete(SystemDict e) {
		return dao.delete("systemDict.deleteById", e);
	}

	@Override
	public int update(SystemDict e) {
		return dao.update("systemDict.update", e);
	}

	@Override
	public SystemDict selectOne(SystemDict e) {
		return (SystemDict) dao.selectOne("systemDict.selectOne", e);
	}

	@Override
	public PagerModel selectPageList(SystemDict e) {
		return dao.selectPageList("systemDict.selectPageList", "systemDict.selectList", e);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SystemDict> selectList(SystemDict e) {
		return dao.selectList("systemDict.selectList", e);
	}

	@Override
	public int deleteById(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SystemDict selectById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	// 根据父id查询类别
	public List<SystemDict> selectByPid(SystemDict e) {
		return dao.selectList("systemDict.selectByPid", e);
	}

}
