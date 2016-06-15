package net.jeeshop.core.system.bean;

import java.util.ArrayList;
import java.util.List;


/**
 * 页面上显示的菜单项，每一个MenuItem对应一个节点
 */
public class TreeItem {
	private String id;// 0：根节点，否则是子节点
	private String pid;// 菜单项的父亲节点
	private String target = "rightFrame";// 打开的目标
	private String name;// 菜单名称
	private boolean open = false;// 是否展开
	private boolean checked;// true:勾选
	private List<TreeItem> children;// 子节点
	private String icon;// 节点图标
	private String saleCode;//销售代码
	private String type = "d";// d：部门，u：用户
	
	private boolean nocheck = true;// tree结构是否显示checkbox，true：不显示，false：显示。
	
	public boolean getNocheck() {
		return nocheck;
	}

	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isRootMenu() {
		return "0".equals(pid);
	}

	public void addClild(TreeItem item) {
		if(children == null){
			children = new ArrayList<TreeItem>();
		}
		children.add(item);
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<TreeItem> getChildren() {
		return children;
	}

	public void setChildren(List<TreeItem> children) {
		this.children = children;
	}

	public TreeItem(String name, List<TreeItem> children) {
		super();
		this.name = name;
		this.children = children;
	}

	public TreeItem() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	@Override
	public String toString() {
		return "name:"+name;
	}

	public String getSaleCode() {
		return saleCode;
	}

	public void setSaleCode(String saleCode) {
		this.saleCode = saleCode;
	}
}
