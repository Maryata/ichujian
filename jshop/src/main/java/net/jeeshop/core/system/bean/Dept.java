package net.jeeshop.core.system.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.jeeshop.core.dao.page.PagerModel;

/**
 * 部门
 *
 */
@SuppressWarnings("serial")
public class Dept extends PagerModel implements Serializable {
	
	private String id;
	private String pid;
	private String name;// 部门名称
	private String code;// 部门编号
	private Integer orderNum;// 排序
	private String type;// 类型，预留字段
	
	private List<Dept> children = new ArrayList<Dept>();// 子部门
	
	public static final String dept_status_y = "y";//启用
	public static final String dept_status_n = "n";//禁用
	
	public void clear() {
		this.id = null;
		this.pid = null;
		this.name = null;
		this.code = null;
		this.orderNum = null;
		this.type = null;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public List<Dept> getChildren() {
		return children;
	}

	public void setChildren(List<Dept> children) {
		this.children = children;
	}
	
	private List<User> users = new ArrayList<User>();

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
