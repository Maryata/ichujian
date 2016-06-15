package net.jeeshop.services.manage.ordermain.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.manage.ordermain.bean.Ordermain;
import net.jeeshop.services.manage.ordermain.dao.OrdermainDao;

import org.springframework.stereotype.Repository;

@Repository("ordermainDaoManage")
public class OrdermainDaoImpl implements OrdermainDao {

	@Resource
	private BaseDao dao;

	public void setDao(BaseDao dao) {
		this.dao = dao;
	}

	public PagerModel selectPageList(Ordermain e) {
		return dao.selectPageList("manage.ordermain.selectPageList",
				"manage.ordermain.selectPageCount", e);
	}

	public List selectList(Ordermain e) {
		return dao.selectList("manage.ordermain.selectList", e);
	}

	public Ordermain selectOne(Ordermain e) {
		return (Ordermain) dao.selectOne("manage.ordermain.selectOne", e);
	}

	public int delete(Ordermain e) {
		return dao.delete("manage.ordermain.delete", e);
	}

	public int update(Ordermain e) {
		return dao.update("manage.ordermain.update", e);
	}

	public int deletes(String[] ids) {
		Ordermain e = new Ordermain();
		for (int i = 0; i < ids.length; i++) {
			e.setId(ids[i]);
			delete(e);
		}
		return 0;
	}

	public int insert(Ordermain e) {
		return dao.insert("manage.ordermain.insert", e);
	}

	public int deleteById(int id) {
		return dao.delete("manage.ordermain.deleteById", id);
	}

	@Override
	public Ordermain selectById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
