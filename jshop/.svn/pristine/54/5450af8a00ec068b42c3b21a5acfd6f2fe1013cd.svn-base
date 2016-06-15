package net.jeeshop.services.manage.system.dept.dao.impl;

import java.util.List;

import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.core.system.bean.Dept;
import net.jeeshop.services.manage.system.dept.dao.DeptDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("deptDaoManage")
public class DeptDaoImpl implements DeptDao{

	@Autowired
	private BaseDao dao;
	
	@Override
	public int insert(Dept e) {
		return dao.insert("dept.insert", e);
	}

	@Override
	public int delete(Dept e) {
		return dao.delete("dept.deleteById", e);
	}

	@Override
	public int update(Dept e) {
		return dao.update("dept.update", e);
	}

	@Override
	public Dept selectOne(Dept e) {
		return (Dept) dao.selectOne("dept.selectOne", e);
	}

	@Override
	public PagerModel selectPageList(Dept e) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Dept> selectList(Dept e) {
		return dao.selectList("dept.selectList",e);
	}

	@Override
	public int deleteById(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Dept selectById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	// 根据上级部门id删除部门
	public int deleteByPid(Dept dept) {
		return dao.delete("dept.deleteByPid", dept);
	}

}
