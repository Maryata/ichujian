package net.jeeshop.services.manage.system;

import java.util.List;

import net.jeeshop.core.Services;
import net.jeeshop.core.system.bean.Dept;
import net.jeeshop.core.system.bean.TreeItem;

public interface DeptService extends Services<Dept>{

	// 查询所有部门信息
	public List<Dept> loadRoot(Dept e);

	// 根据id删除部门
	public String deleteById(Dept e);

	// 获取所有部门及每个部门下的所有用户
	public List<TreeItem> getDeptTree();

}
