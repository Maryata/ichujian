package net.jeeshop.core.system.bean;

import java.io.Serializable;
import java.util.Map;

import net.jeeshop.core.dao.page.PagerModel;


/**
 * 用户
 * 
 * @author huangf
 * 
 */
public class User extends PagerModel implements Serializable {
	private String id;
	private String username;
	private String password;
	private String createtime;
	private String updatetime;
	private String createAccount;
	private String updateAccount;
	private String status;
	private String rid;
	private String email;
    private String usercode;//工号
    private String brandType;//销售品牌
	private String newpassword;
	private String newpassword2;
	private String nickname;
	private String phone;
	private String deptId;//部门Id
	private String deptPId;//部门PId
	private String deptName;//部门名称
    private String[] ids;//id数组
	private String role_dbPrivilege;
	private Map<String, String> dbPrivilegeMap;// 用户数据库权限
    private String role_name;//角色
	public static final String user_status_y = "y";// 启用
	public static final String user_status_n = "n";// 禁用
    private String discount;//阿智折扣权限
    private String otherDiscount;//非阿智折扣权限
	public void clear() {
		this.id = null;
		this.status = null;
		this.createtime = null;
		this.updatetime = null;
		this.createAccount = null;
		this.updateAccount = null;
		this.rid = null;
		this.username = null;
		this.password = null;
		newpassword = null;
		newpassword2 = null;
		nickname = null;
		email = null;
		phone=null;
		usercode=null;
		brandType=null;
		role_dbPrivilege = null;
		role_name=null;
		deptId = null;
		deptPId = null;
		deptName = null;
		discount=null;
		otherDiscount=null;
		ids=null;
		if (dbPrivilegeMap != null) {
			dbPrivilegeMap.clear();
			dbPrivilegeMap = null;
		}
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	public String getNewpassword2() {
		return newpassword2;
	}

	public void setNewpassword2(String newpassword2) {
		this.newpassword2 = newpassword2;
	}

	public Map<String, String> getDbPrivilegeMap() {
		return dbPrivilegeMap;
	}

	public void setDbPrivilegeMap(Map<String, String> dbPrivilegeMap) {
		this.dbPrivilegeMap = dbPrivilegeMap;
	}

	public String getRole_dbPrivilege() {
		return role_dbPrivilege;
	}

	public void setRole_dbPrivilege(String role_dbPrivilege) {
		this.role_dbPrivilege = role_dbPrivilege;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getCreateAccount() {
		return createAccount;
	}

	public void setCreateAccount(String createAccount) {
		this.createAccount = createAccount;
	}

	public String getUpdateAccount() {
		return updateAccount;
	}

	public void setUpdateAccount(String updateAccount) {
		this.updateAccount = updateAccount;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptPId() {
		return deptPId;
	}

	public void setDeptPId(String deptPId) {
		this.deptPId = deptPId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}


	private Dept dept;// 用户所属部门

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public String getBrandType() {
		return brandType;
	}

	public void setBrandType(String brandType) {
		this.brandType = brandType;
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getOtherDiscount() {
		return otherDiscount;
	}

	public void setOtherDiscount(String otherDiscount) {
		this.otherDiscount = otherDiscount;
	}

}
