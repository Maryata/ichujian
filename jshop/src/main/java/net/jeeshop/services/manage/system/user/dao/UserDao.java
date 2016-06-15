package net.jeeshop.services.manage.system.user.dao;

import java.util.List;
import java.util.Map;

import net.jeeshop.core.system.bean.Dept;
import net.jeeshop.core.system.bean.User;

public interface UserDao {

	// 根据用户id查询所属部门id
	public Integer getDept(User e);
	
	// 修改当前用户所属部门
	public void updateDept(String uid, String did);

	// 添加当前用户所属部门
	public void insertDept(String uid, String did);

	// 根据用户id获取所属部门
	public Dept getUserDept(String uid);

	// 根据部门id删除用户部门中间表数据
	public int deleteUserDept(String did);

	// 根据部门id查询部门下的所有用户
	public List<User> getUserByDeptId(String id);
    //查询是否存在销售人员的id
	public int CheckSaleCode(User e);
    //根据工号查询信息
	public Map<String, Object> SelectSaleMessage(User user);
    //查询所有人的信息
	public List<User> SelectAll();
    //查询多id人员的手机号
	public List<Map<String,Object>> SelectIds(String[] id);
    //查询所有的销售助理信息
	public List<Map> selectXszlPhone();

}
