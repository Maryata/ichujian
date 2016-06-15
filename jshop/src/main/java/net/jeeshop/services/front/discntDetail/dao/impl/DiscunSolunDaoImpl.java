package net.jeeshop.services.front.discntDetail.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.front.discntDetail.bean.DiscntDetail;
import net.jeeshop.services.front.discntDetail.dao.DiscunSolunDao;
@Repository("discunSolinDaoFront")
public class DiscunSolunDaoImpl implements DiscunSolunDao{
	  @Resource
		private BaseDao dao;

		public void setDao(BaseDao dao) {
			this.dao = dao;
		}
	@Override
	public int insert(DiscntDetail e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(DiscntDetail e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(DiscntDetail e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DiscntDetail selectOne(DiscntDetail e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PagerModel selectPageList(DiscntDetail e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DiscntDetail> selectList(DiscntDetail e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteById(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DiscntDetail selectById(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<DiscntDetail> selectAllMessage() {
		// TODO Auto-generated method stub
		return dao.selectList("front.discntDetail.selectAllMessage");
	}

}
