package net.jeeshop.services.manage.customer;

import java.util.List;

import net.jeeshop.core.Services;
import net.jeeshop.services.manage.customer.bean.Customer;

public interface CustomerService extends Services<Customer>{

	int selectCount(Customer e);


	List<Customer> selectAllCustomer(Customer e);

}
