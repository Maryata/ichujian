package com.org.mokey.system.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.common.util.ApDateTime;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.system.dao.SysRoleDao;
import com.org.mokey.util.StrUtils;

public class SysRoleDaoImpl implements SysRoleDao {
	protected Logger log = (Logger.getLogger(getClass()));
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	@Override
	public Map<String, Object> getRoleIfoListMap(String rolecode,
			String rolename, int start, int limit) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		StringBuffer sql = new StringBuffer("select count(*) from T_SYS_ROLE where 1=1 ");
		List<Object> args = new ArrayList<Object>();
		
		if(StrUtils.isNotEmpty(rolecode)){
			sql.append(" and C_ROLE_CODE like ? ");
			args.add("%"+rolecode+"%");
		}
		if(StrUtils.isNotEmpty(rolename)){
			sql.append(" and C_ROLE_NAME like ? ");
			args.add("%"+rolename+"%");
		}
		
		int count = jdbcTemplate.queryForObject(sql.toString(),args.toArray(),Integer.class);
		String sql1 = DaoUtil.addfy_oracle(sql, " C_ROLE_NAME,C_SYSDATE ", start, limit, args).toString().replace("count(*)", "*");
		//log.debug("sql:"+DaoUtil.converseQesmarkSQL(sql1, args));
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1,args.toArray());
		
		ret.put("count", count);
		ret.put("list", list);
		
		return ret;
	}

	@Override
	public Map<String, Object> checkRoleInfo(String checkType, String value,
			String id) {
		List<Object> args = new ArrayList<Object>();
		Map<String, Object> ret = new HashMap<String, Object>();
		String sql = "select count(*) from T_SYS_ROLE where 1=1 and "+checkType+" = ? ";
		args.add(value);
		
		if(StrUtils.isNotEmpty(id)){
			sql +=" and C_ID<>? ";
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
	public String saveRoleIfo(Map<String, Object> saveMap) {
		String roleId = null;
		/*String user_role = null; //角色
		if(saveMap.containsKey("USER_ROLE")){
			user_role = (String) saveMap.get("USER_ROLE");
			saveMap.remove("USER_ROLE");
		}*/
		String nowTime = ApDateTime.getNowDateTime(ApDateTime.DATE_TIME_Sec);
		if(StrUtils.isEmpty(saveMap.get("C_ID"))){
			//seqId =  JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_APP_INFO");
			//log.debug("seqId:"+seqId);
			roleId = java.util.UUID.randomUUID().toString();
			saveMap.put("C_ID", roleId);
			//saveMap.put("#SEQ#c_id", "SEQ_APP_INFO.nextval");
			
			saveMap.put("C_MODIFY_TIME", nowTime);//new java.util.Date()
			//saveMap.put("C_ISLIVE", "1");
			
			JdbcTemplateUtils.saveDataByMap(jdbcTemplate, saveMap, "T_SYS_ROLE");
		}else{
			roleId = (String) saveMap.get("C_ID");
			Map<String,String> whereMap = new HashMap<String,String>();
			whereMap.put("C_ID", roleId);
			saveMap.remove("C_ID");
			
			JdbcTemplateUtils.updateDataByMap(jdbcTemplate, saveMap, whereMap, "T_SYS_ROLE");
		}
		return roleId;
	}

	@Override
	public void deleteRoleIfo(String c_id) {
		//删除角色相关表;
		String sql = "delete from T_SYS_ROLE_FUNCTION where C_ROLE_ID=? ";
		jdbcTemplate.update(sql, c_id);
		sql = " delete from T_SYS_USER_ROLE where C_ROLE_ID=?";
		jdbcTemplate.update(sql, c_id);
		sql = " delete from T_SYS_ROLE where C_ID=?";
		jdbcTemplate.update(sql, c_id);
	}

	public List getRoleFunctionById(String roleId) {
		String sql = "SELECT C_FUNCTION_ID FROM T_SYS_ROLE_FUNCTION where C_ROLE_ID =?";
		return jdbcTemplate.queryForList(sql,roleId);
	}
	
	public List getFunctionByPid(String fid){
		String sql = "select C_ID,C_PID,C_NAME,C_IS_MENU,C_LEVE,C_URL,C_ORDER from T_SYS_FUNCTION where C_PID=? order by C_ORDER asc ";
		return jdbcTemplate.queryForList(sql,fid);
	}

	@Override
	public void saveRoleFunct(String funId, String roleId) {
		String sql = "select count(*) from T_SYS_ROLE_FUNCTION where C_ROLE_ID=? and C_FUNCTION_ID=?";
		Object [] args = new Object[]{roleId,funId};
		int cn = jdbcTemplate.queryForObject(sql, args, Integer.class);
		if(cn>0){//修改
			//sql = "update T_SYS_ROLE_FUNCTION set C_MODIFY_TIME=? where C_ROLE_ID=? and C_FUNCTION_ID=? ";
		}else{//新增
			sql = " insert into T_SYS_ROLE_FUNCTION(C_ID,C_ROLE_ID,C_FUNCTION_ID) values(?,?,?) ";
			args = new Object[]{UUID.randomUUID().toString(),roleId,funId};
			jdbcTemplate.update(sql, args);
		}
	}

	@Override
	public void deleteRoleFunct(String funId, String roleId) {
		String sql = "delete from T_SYS_ROLE_FUNCTION where C_ROLE_ID=? and C_FUNCTION_ID=? ";
		jdbcTemplate.update(sql,roleId,funId);
	}

	

}
