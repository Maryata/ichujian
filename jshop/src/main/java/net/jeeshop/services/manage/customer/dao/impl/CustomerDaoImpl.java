package net.jeeshop.services.manage.customer.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.manage.customer.bean.Customer;
import net.jeeshop.services.manage.customer.dao.CustomerDao;
@Repository("customerDaoManage")
public class CustomerDaoImpl implements CustomerDao{
   @Resource
   private BaseDao dao;

	@Override
	public int insert(Customer e) {
		// TODO Auto-generated method stub
		return dao.insert("manage.customer.insert", e);
	}

	@Override
	public int delete(Customer e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Customer e) {
		// TODO Auto-generated method stub
		return dao.update("manage.customer.update", e);
	}

	@Override
	public Customer selectOne(Customer e) {
		// TODO Auto-generated method stub
		return (Customer) dao.selectOne("manage.customer.selectOne",e);
	}

	@Override
	public PagerModel selectPageList(Customer e) {
		return dao.selectPageList("manage.customer.selectPageList",
				"manage.customer.selectPageCount", e);
	}

	@Override
	public List<Customer> selectList(Customer e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteById(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Customer selectById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public BaseDao getDao() {
		return dao;
	}

	public void setDao(BaseDao dao) {
		this.dao = dao;
	}

	@Override
	public int selectCount(Customer e) {
		return dao.getCount("manage.customer.selectCount", e);
	}

	@Override
	public List<Customer> selectAllCustomer(Customer e) {
		return dao.selectList("manage.customer.selectAllCustomer",e);
	}

}
