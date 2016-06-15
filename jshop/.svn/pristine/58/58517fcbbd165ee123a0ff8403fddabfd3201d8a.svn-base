package net.jeeshop.services.manage.system.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.jeeshop.core.system.bean.Dept;
import net.jeeshop.core.system.bean.User;
import net.jeeshop.core.system.bean.UserDept;
import net.jeeshop.services.manage.system.user.dao.UserDao;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoImpl extends SqlSessionDaoSupport implements UserDao{

	private static final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);
	
	public SqlSession openSession() {
		try {
			SqlSession session = getSqlSession();
			return session;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	// 根据用户id查询所属部门id
	public Integer getDept(User e) {
		try {
			List<Object> list = openSession().selectList("user.getDept",e);
			if(null!=list && list.size()>0){
				return (Integer) list.get(0);
			}
		} catch (Exception e1) {
			log.error("UserDaoImpl.getDept failed, e : " + e1);
		}
		return null;
	}

	@Override
	// 修改当前用户所属部门
	public void updateDept(String uid, String did) {
		try {
			UserDept ud = new UserDept();
			ud.setUid(uid);
			ud.setDid(did);
			// 查询当前用户是否有部门
			List<UserDept> list = openSession().selectList("user.selectDept", did);
			// 如果存在，就更新
			if(null!=list && list.size()>0){
				openSession().update("user.updateDept", ud);
			}else{
				// 不存在，就保存
				openSession().insert("user.insertDept", ud);
			}
		} catch (Exception e) {
			log.error("UserDaoImpl.updateDept failed, e : " + e);
		}
	}

	@Override
	// 添加当前用户所属部门
	public void insertDept(String uid, String did) {
		try {
			UserDept ud = new UserDept();
			ud.setUid(uid);
			ud.setDid(did);
			openSession().update("user.insertDept", ud);
		} catch (Exception e) {
			log.error("UserDaoImpl.insertDept failed, e : " + e);
		}
	}

	@Override
	// 根据用户id获取所属部门
	public Dept getUserDept(String uid) {
		try {
			List<Object> list = openSession().selectList("user.getUserDept", uid);
			if(null!=list && list.size()>0){
				return (Dept) list.get(0);
			}
		} catch (Exception e) {
			log.error("UserDaoImpl.getUserDept failed, e : " + e);
		}
		return null;
	}

	@Override
	// 根据部门id删除用户部门中间表数据
	public int deleteUserDept(String did) {
		try {
			return openSession().delete("user.deleteUserDept", did);
		} catch (Exception e) {
			log.error("UserDaoImpl.deleteUserDept failed, e : " + e);
		}
		return 0;
	}

	@Override
	// 根据部门id查询部门下的所有用户
	public List<User> getUserByDeptId(String id) {
		try {
			return openSession().selectList("user.getUserByDeptId", id);
		} catch (Exception e) {
			log.error("UserDaoImpl.getUserByDeptId failed, e : " + e);
		}
		return null;
	}

	@Override
	//查询是否存在销售人员的id
	public int CheckSaleCode(User e) {
		try {
		return (Integer)openSession().selectOne("user.CheckSaleCode", e);
		} catch (Exception e1) {
			log.error("UserDaoImpl.CheckSaleCode failed, e1 : " + e1);
		}
		return 0;
	}

	@Override
	public Map<String,Object> SelectSaleMessage(User e) {
		if(e==null){
			return openSession().selectOne("user.SelectSaleMessage");
		}
		try {
			return openSession().selectOne("user.SelectSaleMessage", e);
			} catch (Exception e1) {
				log.error("UserDaoImpl.SelectSaleMessage failed, e1 : " + e1);
			}
			return null;
	}
    /**
     * 查询所有人的信息
     */
	@Override
	public List<User> SelectAll() {
        try{
        	return openSession().selectList("user.SelectInfoAll",new  User());
        }catch(Exception e){
        	log.error("UserDaoImpl.SelectAll failed, e : " , e);
        }
		return null;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> SelectIds(String[] ids) {
		 try{
			 Map param = new HashMap();
			 param.put("ids", ids);
			 
        	return openSession().selectList("user.getUsersPhone",param);
        }catch(Exception e){
        	log.error("UserDaoImpl.SelectIds failed, e : " , e);
        }
		return null;
	}

	@Override
	public List<Map> selectXszlPhone() {
		  try{
			  User u = new User();
			  u.setRole_name("销售助理");
	        	return openSession().selectList("user.selectXszlPhone",u);
	        }catch(Exception e){
	        	log.error("UserDaoImpl.selectXszlPhone failed, e : " , e);
	        }
			return null;
	}


}
