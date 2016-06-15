package net.jeeshop.services.front.ordermain.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.front.ordermain.bean.Ordermain;
import net.jeeshop.services.front.ordermain.dao.OrdermainDao;

@Repository
public class OrdermainDaoImpl implements OrdermainDao {

	@Resource
	private BaseDao dao;

	public void setDao(BaseDao dao) {
		this.dao = dao;
	}
	
	@Override
	public int insert(Ordermain e) {
		return dao.insert("front.ordermain.insert", e);
	}

	@Override
	public int delete(Ordermain e) {
		return dao.delete("front.ordermain.delete", e);
	}

	@Override
	public int update(Ordermain e) {
		return dao.update("front.ordermain.update", e);
	}

	@Override
	public Ordermain selectOne(Ordermain e) {
		return (Ordermain) dao.selectOne("front.ordermain.selectOne", e);
	}

	@Override
	public PagerModel selectPageList(Ordermain e) {
		return dao.selectPageList("front.ordermain.selectPageList",
				"front.ordermain.selectPageCount", e);
	}

	@Override
	public List<Ordermain> selectList(Ordermain e) {
		return dao.selectList("front.ordermain.selectList", e);
	}

	@Override
	public int deleteById(int id) {
		return dao.delete("front.ordermain.deleteById", id);
	}

	@Override
	public Ordermain selectById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteByOrderId(String id) {
		return dao.delete("front.ordermain.deleteByOrderId", id);
	}

}
