package net.jeeshop.services.front.make_time.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.front.make_time.bean.MakeTime;
import net.jeeshop.services.front.make_time.dao.MakeTimeDao;
@Repository("makeTimeDaoFront")
public class MakeTimeDaoService implements MakeTimeDao{
	@Resource
	private BaseDao dao;

	public void setDao(BaseDao dao) {
		this.dao = dao;
	}

	@Override
	public int insert(MakeTime e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(MakeTime e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(MakeTime e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public MakeTime selectOne(MakeTime e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PagerModel selectPageList(MakeTime e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MakeTime> selectList(MakeTime e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteById(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public MakeTime selectById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MakeTime SelectDays(MakeTime makeTime) {
		// TODO Auto-generated method stub
		return (MakeTime) dao.selectOne("front.make_time.selectDays", makeTime);
	}

}
