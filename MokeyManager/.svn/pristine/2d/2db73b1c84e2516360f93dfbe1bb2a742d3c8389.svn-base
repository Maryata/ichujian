/**
 * 
 */  
package com.org.mokey.system.entiy.common;

import java.util.Comparator;
import java.util.Iterator;

/**
 * 仅仅存放页面上菜单列表
 * 
 * @author adam.sun
 * 
 */
public class MenuTree extends TopMenu implements java.io.Serializable {
 
	private static final long serialVersionUID = 1530098021666878546L;

	/** 显示的样式 */
	private String cls;

	/**
	 * 鼠标悬浮时显示的文字,常放描述信息
	 */

	private String qtip;
	private boolean checked;

	private String pid;
	//private String type;

	private String code;
	
	//private String typeSet;

	private boolean needCheck;

	/** 他的父节点 */
	//private MenuTree ParentTree;

	/** 是否为叶子节点 */
	private boolean leaf;

	private boolean expandable;

	/** 是否自动加载 */
	private boolean isOnLoad;

	private String orderBy;// 是否排序Better.wang

	//private String menuMode;

	private boolean draggable;

	private boolean expanded;

	//private String memberId;
	private boolean isHavaChild;

	//private boolean hasDimission;// 是否离职

	//private String nodeType;// 节点类型 判断是否是自定义链接类型

	//private String managerId;

	private boolean allowChildren = false;
	//private String remark;// 备注
	// 节点text(用于节点需要特殊显示时,如附带html代码,用本字段存储节点实际text)
	//private String name;
	
	//private String parentBusId;//业务数据的上级业务ID,如树为菜单时,则为上级菜单.   非上级节点ID, 
	/**
	 * 成员性质(给组织成员加上成员性质)   
	 */
	//private String memberProperty;
	
	/**
	 * 业务用扩展数据 by black
	 */
	//private Object expandData;

	/**
	 * 上级节点id
	 */
	//private String parentNodeId;
	
	private boolean disabled;//节点是否置灰 yoran add


	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}


	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n id is:");
		sb.append(getId());
		sb.append("\n text is:");
		sb.append(getText());

		sb.append("\n checked is:");
		sb.append(checked);

		sb.append("\n title is:");
		sb.append(getTitle());
		sb.append("\n code is:");
		sb.append(getCode());
		sb.append("\n pid is:");
		sb.append(pid);
		sb.append("\n leaf is:");
		sb.append(leaf);
		sb.append("\n cls is:");
		sb.append(cls);
		sb.append("\n location is:");
		sb.append(location);
		sb.append("\n isActivation is:");
		sb.append(isActivation);
		sb.append("\n isOnLoad is:");
		sb.append(isOnLoad);
		sb.append("\n disabled is:");
		sb.append(disabled);
		
		
		sb.append(getId());
		if (getChildren() == null || getChildren().size() == 0) {
			return sb.toString();
		}
		sb.append("\n Children is:");
		for (Iterator<MenuTree> iterator = getChildren().iterator(); iterator
				.hasNext();) {
			MenuTree name = iterator.next();
			sb.append("\n");
			sb.append(name.toString("--"));

		}

		return sb.toString();
	}

	public String toString(String fix) {
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		sb.append(fix);
		sb.append(" id is:");
		sb.append(getId());
		sb.append("\n");

		sb.append(fix);
		sb.append(" checked is:");
		sb.append(checked);
		sb.append("\n");

		sb.append(fix);
		sb.append(" text is:");
		sb.append(getText());
		sb.append("\n");
		sb.append(fix);
		sb.append(" title is:");
		sb.append(getTitle());
		sb.append("\n");
		sb.append(fix);
		sb.append(" code is:");
		sb.append(getCode());
		sb.append("\n");
		sb.append(fix);
		sb.append(" cls is:");
		sb.append(cls);
		sb.append("\n");
		sb.append(fix);
		sb.append(" pid is:");
		sb.append(pid);
		sb.append("\n");
		sb.append(fix);
		sb.append(" leaf is:");
		sb.append(leaf);
		sb.append("\n");
		sb.append(fix);
		sb.append(" purl is:");
		sb.append(getPurl());
		sb.append("\n");
		sb.append(fix);
		sb.append(" pcls is:");
		sb.append(getPcls());
		sb.append("\n");
		sb.append(fix);
		sb.append(" location is:");
		sb.append(location);
		sb.append("\n");
		sb.append(fix);
		sb.append(" isActivation is:");
		sb.append(isActivation);
		sb.append("\n");
		sb.append(fix);
		sb.append(" isOnLoad is:");
		sb.append(isOnLoad);
		
		sb.append("\n");
		sb.append(fix);

		if (getChildren() == null || getChildren().size() == 0) {
			return sb.toString();
		}
		sb.append(" Children is:");
		for (Iterator<MenuTree> iterator = getChildren().iterator(); iterator
				.hasNext();) {
			MenuTree name = iterator.next();
			sb.append(fix);
			sb.append("\n");
			sb.append(name.toString(fix + "--"));

		}

		return sb.toString();
	}

	public static final class Comparat implements Comparator<Object> {
		public int compare(Object o1, Object o2) {
			MenuTree treeOne = (MenuTree) o1;
			MenuTree TreeTwo = (MenuTree) o2;
			if (treeOne == null || TreeTwo == null) {
				return 1;
			}
			return treeOne.getOrder().compareTo(TreeTwo.getOrder());
		}
	}

	public boolean isOnLoad() {
		return isOnLoad;
	}

	public void setOnLoad(boolean isOnLoad) {
		this.isOnLoad = isOnLoad;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getQtip() {
		return qtip;
	}

	public void setQtip(String qtip) {
		this.qtip = qtip;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isExpandable() {
		return expandable;
	}

	public void setExpandable(boolean expandable) {
		this.expandable = expandable;
	}

	public boolean isDraggable() {
		return draggable;
	}

	public void setDraggable(boolean draggable) {
		this.draggable = draggable;
	}

	public boolean isNeedCheck() {
		return needCheck;
	}

	public void setNeedCheck(boolean needCheck) {
		this.needCheck = needCheck;
	}


	public boolean getIsHavaChild() {
		return isHavaChild;
	}

	public void setIsHavaChild(boolean isHavaChild) {
		this.isHavaChild = isHavaChild;
	}


	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isAllowChildren() {
		return allowChildren;
	}

	public void setAllowChildren(boolean allowChildren) {
		this.allowChildren = allowChildren;
	}


	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
}
