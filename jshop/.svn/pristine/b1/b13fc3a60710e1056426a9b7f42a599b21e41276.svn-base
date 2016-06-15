package net.jeeshop.services.front.accountAdapter.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.front.accountAdapter.bean.AccountAdapter;
import net.jeeshop.services.front.accountAdapter.dao.AccountAdapterDao;
@Repository("accountAdapterDaoFront")
public class AccountAdapterDaoImpl implements AccountAdapterDao{
	@Resource
	private BaseDao dao;
	public void setDao(BaseDao dao) {
		this.dao = dao;
	}
	@Override
	public int insert(AccountAdapter e) {
		// TODO Auto-generated method stub
		return dao.insert("front.accountAdapter.insert", e);
	}

	@Override
	public int delete(AccountAdapter e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(AccountAdapter e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AccountAdapter selectOne(AccountAdapter e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PagerModel selectPageList(AccountAdapter e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AccountAdapter> selectList(AccountAdapter e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteById(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AccountAdapter selectById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
