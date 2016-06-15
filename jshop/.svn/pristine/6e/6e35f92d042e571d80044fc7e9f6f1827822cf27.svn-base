package net.jeeshop.web.util;

import org.slf4j.LoggerFactory;

import net.jeeshop.core.system.bean.User;
import net.jeeshop.core.util.StrUtils;
import net.jeeshop.web.action.manage.system.UserAction;

public class DataUtil {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserAction.class);
	
	/**
	 * 设置查询区间;
	 * @param user
	 */
	public static void setUserScopeFilter(User user) {
		StringBuilder sqlString = new StringBuilder();
		//--设置数据; 上级部门是root 1 级别部门;可查询全部数据
		if(user.getUsername().equals("superadmin") ||user.getUsername().equals("admin") || "1".equals(user.getDeptPId())){
			// all;
		}else{
			sqlString.append(" and t.account in ( ");//a.id
			sqlString.append(" select a.account from t_account a where a.saleId in( ");
			sqlString.append(" select ud.uid from t_userdept  ud where  FIND_IN_SET ( ud.did, getDeptChildIds("+user.getDeptPId()+")) ");
			sqlString.append(" ) or a.saleId='"+user.getId()+"' ");
			sqlString.append(" ) ");
		}
		logger.info("set login user scoper: {}",sqlString);
		user.getSqlMap().put("dsf", sqlString.toString());
	}
	

	/**
	 * 数据过滤; dsf
	 * @param user 
	 * @return
	 */
	public static String dataScopeFilter(User user) {
		StringBuilder sqlString = new StringBuilder();
		if(StrUtils.isNotEmpty(user.getSqlMap().get("dsf"))){
			sqlString.append(user.getSqlMap().get("dsf"));
		}
		return sqlString.toString();
	}

}
