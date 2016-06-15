package net.jeeshop.services.manage.customer.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.front.account.bean.Account;
import net.jeeshop.services.manage.customer.CustomerService;
import net.jeeshop.services.manage.customer.bean.Customer;
import net.jeeshop.services.manage.customer.dao.CustomerDao;
@Service("customerServiceManage")
public class CustomerServiceImpl extends ServersManager<Customer, CustomerDao> implements CustomerService{
	@Resource(name="customerDaoManage")
	@Override
	public void setDao(CustomerDao customerDao) {
		this.dao=customerDao;
	}
	public int selectCount(Customer e) {
		return dao.selectCount(e);
	}
	@Override
	public List<Customer> selectAllCustomer(Customer e) {
		return dao.selectAllCustomer(e);
	}

}
