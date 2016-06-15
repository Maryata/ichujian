package net.jeeshop.services.manage.system.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.core.system.bean.Dept;
import net.jeeshop.core.system.bean.User;
import net.jeeshop.core.util.StrUtils;
import net.jeeshop.services.manage.system.UserInteface;
import net.jeeshop.services.manage.system.user.dao.UserDao;

import org.springframework.stereotype.Service;


/**
 * 用户业务逻辑实现类
 * 
 * @author huangf
 * 
 */
@Service
public class UserService implements UserInteface {
    @Resource
	private BaseDao dao;

	public void setDao(BaseDao dao) {
		this.dao = dao;
	}
	
	@Resource(name="userDao")
	private UserDao userDao;
	
	public User login(User user) {
//		user.setStatus(User.user_status_y);
		return (User) dao.selectOne("user.selectOne", user);
	}

	public List selectList(User user) {
		if (user == null)
			return dao.selectList("user.selectList");
		return dao.selectList("user.selectList", user);
	}

	public int insert(User user) {
		return dao.insert("user.insert", user);
	}

	/**
	 * 批量删除用户
	 * 
	 * @param ids
	 */
	public int deletes(String[] ids) {
		User user = new User();
		for (int i = 0; i < ids.length; i++) {
			user.setId(ids[i]);
			delete(user);
		}
		return 0;
	}

	public int delete(User e) {
		return dao.delete("user.delete", e);
	}

	public int update(User e) {
		return dao.update("user.update", e);
	}

	@SuppressWarnings("unchecked")
	public PagerModel selectPageList(User e) {
		 PagerModel pm = dao.selectPageList("user.selectPageList", "user.selectPageCount", e);
		 List<Map<String, Object>> list = pm.getList();
		 if(null!=list && list.size()>0){
			for (Map<String, Object> map : list) {
				Object obj = map.get("id");
				if(StrUtils.isNotEmpty(obj)){
					String uid = obj.toString();
					Dept dept = userDao.getUserDept(uid);
					map.put("dept", dept);
				}
			}
		 }
		 return pm;
	}

	@Override
	public User selectOne(User e) {
		return (User) dao.selectOne("user.selectOne", e);
	}

	@Override
	public User selectById(String id) {
		User user = new User();
		user.setId(id);
		return selectOne(user);
	}
	
	/**
	 * 根据条件查询数量
	 * @param user
	 * @return
	 */
	public int selectCount(User user) {
		if(user==null){
			throw new NullPointerException();
		}
		
		return (Integer) dao.selectOne("user.selectCount",user);
	}

	public User selectOneByCondition(User user) {
		if(user==null){
			throw new NullPointerException();
		}
		return (User) dao.selectOne("user.selectOneByCondition", user);
	}
	
	/**
	 * 查询当前用户所属的部门id
	 * @param e 用户id
	 * @return 
	 */
	public Integer getDept(User e) {
		return userDao.getDept(e);
	}

	/**
	 * 修改当前用户所属部门
	 * @param uid 用户id
	 * @param did 部门id
	 */
	public void updateDept(String uid, String did) {
		userDao.updateDept(uid,did);
	}

	/**
	 * 添加当前用户所属部门
	 * @param uid 用户id
	 * @param did 部门id
	 */
	public void insertDept(String uid, String did) {
		userDao.insertDept(uid,did);
	}
    /**
     * 查询当前销售人员的工号是否存在
     * @param saleCode 工号
     * @return user.selectOne
     */
	public int CheckSaleCode(String saleCode) {
		User e=new User();
		e.setUsercode(saleCode);
		return userDao.CheckSaleCode(e);
			}
    
	public Map<String, Object> SelectSaleMessage(User user) {
		return userDao.SelectSaleMessage(user);
	}
    /**
     * 查询所有人员的信息
     * @return
     */
	public List<User> selectAll() {
		return userDao.SelectAll();
	}
    /**
     * 查询所有人员的 电话
     * @param id
     * @return 
     */
	public List<Map<String,Object>> selectIds(String[] id) {
		return userDao.SelectIds(id);
		
	}
    /**
     * 查询所有的销售助理
     * @return
     */
	public List<Map> selectXszlPhone() {
		// TODO Auto-generated method stub
		return userDao.selectXszlPhone() ;
	}

}
