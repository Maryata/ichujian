package com.org.mokey.system.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.org.mokey.common.util.ApDateTime;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.system.dao.SysUserDao;
import com.org.mokey.system.entiy.TSysUser;
import com.org.mokey.util.StrUtils;

public class SysUserDaoImpl implements SysUserDao {

	protected Logger log = (Logger.getLogger(getClass()));
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public TSysUser userLogin(String username, String password) {
		String sql = "select * from T_SYS_USER where lower(C_USERCODE)=lower(?) and C_PASSWORD=? ";
		Object [] args = new Object[]{username,password};
		//log.debug("sql:"+DaoUtil.converseQesmarkSQL(sql.toString(), args));
		List<TSysUser> list = jdbcTemplate.query(sql.toString(),args ,new SysUserRowMapper());//RowMapper将每一行封装成自定义的类
		TSysUser sysUser = null;
		if(StrUtils.isNotEmpty(list)){
			sysUser = list.get(0);
		}
		return sysUser;
	}
		/*
	 * @author vpc
	 *
	 */
	private class SysUserRowMapper implements RowMapper<TSysUser> {  
	    public TSysUser mapRow(ResultSet rs, int index) throws SQLException {  
	    	TSysUser bean = new TSysUser();  	  
	    	bean.setUserId(rs.getString("C_USERID"));
	    	bean.setUserCode(rs.getString("C_USERCODE"));
	    	bean.setUserName(rs.getString("C_USERNAME"));
	    	bean.setPassword(rs.getString("C_PASSWORD"));
	    	bean.setEmail(rs.getString("C_EMAIL"));
	    	bean.setUserMobile(rs.getString("C_USER_MOBILE"));
	    	bean.setSex(rs.getString("C_SEX"));
	    	bean.setBirthday(rs.getString("C_BIRTHDAY"));
	    	bean.setPassword(rs.getString("C_POST"));
	    	bean.setHomeTel(rs.getString("C_HOME_TEL"));
	    	bean.setHomeAddress(rs.getString("C_HOME_ADDRESS"));
	    	bean.setUserStatus(rs.getString("C_USER_STATUS"));
	    	bean.setModifyTime(rs.getString("C_MODIFY_TIME"));
	    	bean.setValidity(rs.getString("C_VALIDITY"));
	        return bean;  
	    }  
	}

	@Override
	public boolean updatePass(String userId, String newPassword) {
		String sql = "update T_SYS_USER set C_PASSWORD=? where C_USERID = ? ";
		Object [] args = new Object[]{newPassword,userId};
		int cn = jdbcTemplate.update(sql, args);
		return cn>0 ? true : false ;
	}

	@Override
	public Map<String, Object> getUserIfoListMap(String c_usercode,
			String c_username, int start, int limit) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		StringBuffer sql = new StringBuffer("select count(*) from T_SYS_USER where 1=1 ");
		List<Object> args = new ArrayList<Object>();
		
		if(StrUtils.isNotEmpty(c_usercode)){
			sql.append(" and c_usercode like ? ");
			args.add("%"+c_usercode+"%");
		}
		if(StrUtils.isNotEmpty(c_username)){
			sql.append(" and c_username like ? ");
			args.add("%"+c_username+"%");
		}
		
		int count = jdbcTemplate.queryForObject(sql.toString(),args.toArray(),Integer.class);
		String sql1 = DaoUtil.addfy_oracle(sql, " c_username,C_SYSDATE ", start, limit, args).toString().replace("count(*)", "*");
		//log.debug("sql:"+DaoUtil.converseQesmarkSQL(sql1, args));
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1,args.toArray());
		
		ret.put("count", count);
		ret.put("list", list);
		
		return ret;
	}

	@Override
	public Map<String, Object> checkUserInfo(String checkType, String value,
			String id) {
		List<Object> args = new ArrayList<Object>();
		Map<String, Object> ret = new HashMap<String, Object>();
		String sql = "select count(*) from T_SYS_USER where 1=1 and "+checkType+" = ? ";
		args.add(value);
		
		if(StrUtils.isNotEmpty(id)){
			sql +=" and C_USERID<>? ";
			args.add(id);
		}
		log.debug("sql:"+DaoUtil.converseQesmarkSQL(sql, args));
		int count = jdbcTemplate.queryForObject(sql, args.toArray(),Integer.class);
		if(count>0){
			ret.put("isExits", 1);
		}else{
			ret.put("isExits", 0);
		}
		return ret;
	}

	@Override
	public List getSuppliers() {
		String sql = "SELECT C_ID,C_SUPPLIER_CODE,C_COMPANY as C_SUPPLIER_NAME FROM T_BASE_SUPPLIER order by C_SUPPLIER_NAME ";
		List list = jdbcTemplate.queryForList(sql);
		return list;
	}

	@Override
	public List getUserRoles() {
		String sql = "SELECT C_ID,C_ROLE_CODE,C_ROLE_NAME FROM T_SYS_ROLE order by C_ROLE_NAME ";
		List list = jdbcTemplate.queryForList(sql);
		return list;
	}

	@Override
	public String saveUserIfo(Map<String, Object> saveMap) {
		String userId = null;
		String user_role = null; //角色
		if(saveMap.containsKey("USER_ROLE")){
			user_role = (String) saveMap.get("USER_ROLE");
			saveMap.remove("USER_ROLE");
		}
		String nowTime = ApDateTime.getNowDateTime(ApDateTime.DATE_TIME_Sec);
		if(StrUtils.isEmpty(saveMap.get("C_USERID"))){
			//seqId =  JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_APP_INFO");
			//log.debug("seqId:"+seqId);
			userId = java.util.UUID.randomUUID().toString();
			saveMap.put("C_USERID", userId);
			//saveMap.put("#SEQ#c_id", "SEQ_APP_INFO.nextval");
			
			saveMap.put("C_MODIFY_TIME", nowTime);//new java.util.Date()
			//saveMap.put("C_ISLIVE", "1");
			
			JdbcTemplateUtils.saveDataByMap(jdbcTemplate, saveMap, "T_SYS_USER");
		}else{
			userId = (String) saveMap.get("C_USERID");
			Map whereMap = new HashMap();
			whereMap.put("C_USERID", saveMap.get("C_USERID"));
			saveMap.remove("C_USERID");
			
			JdbcTemplateUtils.updateDataByMap(jdbcTemplate, saveMap, whereMap, "T_SYS_USER");
		}
		
		if(StrUtils.isNotEmpty(user_role)){
			String [] user_roleArr = user_role.split(",");
			if(user_role.length()>0){
				//保存用户角色;
				String sql = "delete from T_SYS_USER_ROLE where C_USERID=?";
				jdbcTemplate.update(sql,userId);
				
				//保存角色;
				String roleSql = "INSERT INTO T_SYS_USER_ROLE(C_ID, C_USERID, C_ROLE_ID, C_MODIFY_TIME) values(?,?,?,?) ";
				
				List<Object[]> userRoles = new ArrayList<Object[]>();
				Object[] roleTemp = new Object[4];
				roleTemp[0] = "#newId";
				roleTemp[1] = userId;
				roleTemp[3] = nowTime;
				for(int i=0;i<user_roleArr.length;i++ ){
					Object data [] = roleTemp.clone();
					data[2] = user_roleArr[i];
					userRoles.add(data);
				}
				JdbcTemplateUtils.batchUpdateListArrsData(jdbcTemplate, userRoles, roleSql);
			}
		}
		return userId;
	}

	@Override
	public void deleteUserIfo(String c_id) {
		//update T_SYS_USER set C_USER_STATUS=0 where C_USERID=?
		String sql = "delete from T_SYS_USER_ROLE where C_USERID=? ";
		jdbcTemplate.update(sql, c_id);
		sql = " delete from T_SYS_USER where C_USERID=?";
		jdbcTemplate.update(sql, c_id);
	}

	@Override
	public Map<String, Object> getUserIfoById(String userId) {
		return jdbcTemplate.queryForMap("select * from T_SYS_USER where C_USERID=?",userId);
	}

	@Override
	public List getUserRoleById(String userId) {
		return jdbcTemplate.queryForList("select C_ID, C_USERID, C_ROLE_ID from T_SYS_USER_ROLE where C_USERID=?",userId);
	}

	@Override
	public List getUserFunctions(String userId) {
		StringBuffer sql = new StringBuffer("select f.C_ID,f.C_PID,f.C_NAME,f.C_IS_MENU,f.C_LEVE,f.C_URL,f.C_ORDER from T_SYS_FUNCTION f ");
		sql.append(" where exists (")
		.append(" select 1 from T_SYS_ROLE_FUNCTION rf where rf.C_FUNCTION_ID=f.C_ID")
		.append(" and rf.C_ROLE_ID in ( select C_ROLE_ID from T_SYS_USER_ROLE where C_USERID=? )")
		.append(" ) order by f.C_ORDER ");
		return jdbcTemplate.queryForList(sql.toString(),userId );//new Object[]{userId}
	}

}
