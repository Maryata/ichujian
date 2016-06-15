package com.org.mokey.common;

import java.util.HashMap;
import java.util.Map;

public class SysConstant {
	
	public enum Signal {
		/**代理商*/
		supplier("select C_SUPPLIER_CODE as ID,C_SUPPLIER_NAME as NAME from T_BASE_SUPPLIER order by C_SUPPLIER_CODE"),
		/**品牌ID*/
		brand("select C_ID as ID,C_NAME as NAME from T_BASE_PHONE_BRAND where C_ISLIVE=1 order by C_ORDER") ;
		
		private String sql;
		private Signal(String sql){
			this.sql=sql;
		}
		public String getSql(){
			return this.sql;
		}
	}

	private SysConstant() {
		super();
	}
	/**权限root根目录 */
	public static final String FUNCTION_ROOT = "0";
	
	/**
	 * 系统中表示超级管理员角色的ID
	 */
	public static final String AP_SYS_ROLE_ID = "R00001";

	/**
	 * 系统中表示超级管理员的ID
	 */
	public static final String AP_SYS_ADMIN_ID = "U00001";

	/**
	 * 系统的状态，表示肯定 Y
	 */
	public static final String AP_SYS_STATUS_TRUE = "Y";

	/**
	 * 系统的状态，表示否定 N
	 */
	public static final String AP_SYS_STATUS_FALSE = "N";
	/**
	 * 表示删除 D
	 */
	public static final String AP_SYS_STATUS_DELETE = "D";
	/**
	 * 表示正常 N
	 */
	public static final String AP_SYS_STATUS_NOMAL = "N";

	/**
	 * 保存都有的系统的全局的环境变量
	 */
	public static Map<String, Object> ApplatformContext = new HashMap<String, Object>();

	/**
	 * 标识是否进行缓存查询
	 */
	public static String SYS_ISCACHE_DEAL = "Y";

	/**
	 * 取得系统常量，这个常量是系统级的，在as的runtime内都有效
	 * 
	 * @param strKey
	 * @return Object
	 */
	public static Object getSysConstant(String strKey) {
		if (strKey != null) {
			return ApplatformContext.get(strKey);
		} else {
			return null;
		}

	}

	/**
	 * 设置系统常量，这个常量是系统级的，在as的runtime内都有效
	 * 
	 * @param strKey
	 *            环境变量类型
	 * 
	 * @param ojbValue
	 *            该类型下的一组变量
	 */
	public static void setSysConstant(String strKey, Object ojbValue) {
		if (strKey != null && ojbValue != null) {
			ApplatformContext.put(strKey, ojbValue);
		}
	}

	/**
	 * 设置系统常量，把某个变量从系统删除
	 * 
	 * @param strKey
	 *            关键字
	 * @param ojbValue
	 */
	public static void removevSysConstant(String strKey) {
		if (strKey != null) {
			ApplatformContext.remove(strKey);
		}
	}

	public static SysConstant createSysConstant() {
		return new SysConstant();
	}

	public static Map<String, Object> getApplatformContext() {
		return ApplatformContext;
	}

	public static void setApplatformContext(Map<String, Object> applatformContext) {
		ApplatformContext = applatformContext;
	}

}
