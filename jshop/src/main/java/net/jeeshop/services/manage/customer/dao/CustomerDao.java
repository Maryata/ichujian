package net.jeeshop.services.manage.customer.dao;

import java.util.List;

import net.jeeshop.core.DaoManager;
import net.jeeshop.services.manage.customer.bean.Customer;

public interface CustomerDao extends DaoManager<Customer>{

	int selectCount(Customer e);

	List<Customer> selectAllCustomer(Customer e);


}
