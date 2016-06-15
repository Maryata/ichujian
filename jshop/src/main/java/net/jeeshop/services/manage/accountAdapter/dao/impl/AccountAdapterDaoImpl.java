package net.jeeshop.services.manage.accountAdapter.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.manage.accountAdapter.bean.AccountAdapter;
import net.jeeshop.services.manage.accountAdapter.dao.AccountAdapterDao;
/**
 * 适配清单dao实现层
 * @author wn
 *
 */
@Repository("accountAdapterDaoManage")
public class AccountAdapterDaoImpl implements AccountAdapterDao{
	 @Resource
	private BaseDao dao;
	@Override
	public int insert(AccountAdapter e) {
		return dao.insert("manage.accountAdapter.insert", e);
	}

	@Override
	public int delete(AccountAdapter e) {
		return dao.delete("manage.accountAdapter.delete", e);
	}

	@Override
	public int update(AccountAdapter e) {
		return dao.update("manage.accountAdapter.update", e);
	}

	@Override
	public AccountAdapter selectOne(AccountAdapter e) {
		return (AccountAdapter) dao.selectOne("manage.accountAdapter.selectOne", e);
	}

	@Override
	public PagerModel selectPageList(AccountAdapter e) {
		return dao.selectPageList("manage.accountAdapter.selectPageList",
				"manage.accountAdapter.selectPageCount", e);
	}

	@Override
	public List selectList(AccountAdapter e) {
		return dao.selectList("manage.accountAdapter.selectList", e);
	}

	@Override
	public int deleteById(int id) {
		return dao.delete("manage.accountAdapter.deleteById", id);
	}

	@Override
	public AccountAdapter selectById(String id) {
		return (AccountAdapter) dao.selectOne("manage.accountAdapter.selectById",id);
	}
	
	public int deletes(String[] ids) {
		AccountAdapter e = new AccountAdapter();
		for (int i = 0; i < ids.length; i++) {
			e.setId(ids[i]);
			delete(e);
		}
		return 0;
	}


	public void setDao(BaseDao dao) {
		this.dao = dao;
	}

}
