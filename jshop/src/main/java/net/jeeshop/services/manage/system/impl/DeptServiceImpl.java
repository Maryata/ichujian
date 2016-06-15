package net.jeeshop.services.manage.system.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.jeeshop.core.ServersManager;
import net.jeeshop.core.system.bean.Dept;
import net.jeeshop.core.system.bean.TreeItem;
import net.jeeshop.core.system.bean.User;
import net.jeeshop.services.manage.system.DeptService;
import net.jeeshop.services.manage.system.dept.dao.DeptDao;
import net.jeeshop.services.manage.system.user.dao.UserDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 部门业务逻辑实现类
 * @author Maryn
 *
 */
@Service
public class DeptServiceImpl extends ServersManager<Dept, DeptDao> implements DeptService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DeptServiceImpl.class); 

    @Resource(name = "deptDaoManage")
    @Override
	public void setDao(DeptDao dao) {
    	this.dao = dao;
	}
    
    @Autowired
    private DeptDao deptDao;
    
    @Autowired
    private UserDao userDao;
    
	/**
	 * 查询所有部门信息
	 * @param e 查询条件
	 * @return
	 */
	public List<Dept> loadRoot(Dept e) {
		if(null==e){
			e = new Dept();
		}
		// 查询所有部门
		e.setPid("1");
		List<Dept> list = dao.selectList(e);
		if(null!=list && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				// 查询当前部门下的所有子部门
				Dept dept = list.get(i);
				loadChildren(dept);
			}
		}
		return list;
	}
	
	// 查询当前部门下的所有子部门
	private void loadChildren(Dept dept) {
		Dept d = new Dept();
		// 设置父id
		d.setPid(dept.getId());// 设置父id
//		d.setType(dept.getType());// 设置type
		// 以传递进来的部门的id做为父id查询所有子部门，设置子部门的集合
		dept.setChildren(dao.selectList(d));
		if(null!=dept.getChildren() && dept.getChildren().size()>0){
			// 遍历所有子部门，查询每个子部门下的二级子部门
			for (int i = 0; i < dept.getChildren().size(); i++) {
				// 查询当前部门下的所有子部门
				loadChildren(dept.getChildren().get(i));
			}
		}
	}

	@Override
	// 根据id删除部门
	public String deleteById(Dept e) {
		try {
			// 删除《用户部门中间表》数据
			userDao.deleteUserDept(e.getId());
			// 删除下级部门
			Dept dept = new Dept();
			dept.setPid(e.getId());
			// 查询是否有下级部门
			deptDao.deleteByPid(dept);
			// 删除当前部门
			dao.delete(e);
			return "1";
		} catch (Exception e1) {
			LOGGER.error("DeptServiceImpl.deleteById failed, e : " + e1);
		}
		return "0";
	}

	@Override
	// 获取所有部门及每个部门下的所有用户
	public List<TreeItem> getDeptTree() {
		Dept e = new Dept();
		e.setId("4");//查询指电目录下
		// 查询一级部门
		List<Dept> depts = deptDao.selectList(e);
		// 创建部门菜单
		List<TreeItem> root = new ArrayList<TreeItem>();
		// 将“部门”对象转换成“部门菜单”树对象
		if(null!=depts && depts.size()>0){
			for (int i = 0; i < depts.size(); i++) {
				Dept dept = depts.get(i);
				TreeItem item = new TreeItem(dept.getName(),null);
				item.setId(dept.getId());
				item.setPid(dept.getPid());
				root.add(item);
			}
		}
		// 加载子部门
		if(null!=root && root.size()>0){
			for (int i = 0; i < root.size(); i++) {
				TreeItem item = root.get(i);
				Dept d = new Dept();
				d.setPid(item.getId());
				// 将当前部门的id做为父id，查询其下的子部门
				loadChildrenByPid(item, d);
			}
		}
		return root;
	}

	/**
	 * 根据父部门id查询子部门
	 * @param item 父部门菜单
	 * @param d 子部门
	 */
	private void loadChildrenByPid(TreeItem item, Dept d) {
		// 查询子部门
		List<Dept> subDepts = deptDao.selectList(d);
		//if(null==subDepts || subDepts.size()==0){
			// 如果结果为空，说明是最底层部门。查询其下员工
			List<User> users = userDao.getUserByDeptId(item.getId());
			List<TreeItem> uItems = new ArrayList<TreeItem>();
			if(null!=users && users.size()>0){
				for (User user : users) {
					TreeItem uItem = new TreeItem();
					uItem.setId(user.getId());
					uItem.setName(user.getNickname());
					uItem.setPid(item.getId());
					uItem.setSaleCode(user.getUsercode());
					uItem.setType("U");
					uItems.add(uItem);
					
					uItem.setNocheck(false);// 用户显示复选框，部门不显示
				}
			}
			item.setChildren(uItems);
			//return;
		//}
		
		if(item.getChildren()==null){
			item.setChildren(new ArrayList<TreeItem>());
		}
		if(null!=subDepts && subDepts.size()>0){
			// 遍历子部门集合，将子部门转换成“部门菜单”，并存入“父部门菜单”
			for (int i = 0; i < subDepts.size(); i++) {
				Dept dept = subDepts.get(i);
				TreeItem additem = new TreeItem(dept.getName(),null);
				additem.setId(dept.getId());
				additem.setPid(dept.getPid());
				item.getChildren().add(additem);
			}
		}
		if(null!=item.getChildren() && item.getChildren().size()>0){
			// 遍历父部门下的所有子部门。
			for (TreeItem thisItem : item.getChildren()) {
				// 将每个子部门作为父部门，递归查询子部门下的子部门
				if("U".equals(thisItem.getType())){
					continue;
				}
				Dept dept = new Dept();
				dept.setPid(thisItem.getId());
				loadChildrenByPid(thisItem,dept);
			}
		}
	}
	
}
