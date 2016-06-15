package net.jeeshop.services.manage.system;

import net.jeeshop.core.Services;
import net.jeeshop.core.system.bean.User;

public interface UserInteface extends Services<User> {
	/**
	 * @param e
	 * @return
	 */
	public User login(User e);
	
	/**
	 * 根据用户id查询所属部门id
	 * @param e
	 * @return
	 */
	public Integer getDept(User e);
	
	/**
	 * 修改当前用户所属部门
	 * @param uid 用户id
	 * @param did 部门id
	 */
	public void updateDept(String uid, String did);
	
	/**
	 * 添加用户所属部门
	 * @param uid 用户id
	 * @param did 部门id
	 */
	public void insertDept(String uid, String did);
}
