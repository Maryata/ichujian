/**
 * 
 */
package com.org.mokey.system.entiy.common;

import java.util.Comparator;

/**
 * 仅仅存放页面上菜单列表
 * 
 * @author adam.sun
 * 
 */
public class TreeNode implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7742219145860439246L;

	/** 页面显示的菜单名称 */
	private String text; // 可以作为业务上的名称

	/** 本节点的标识 */
	private String id; // 可以作为业务上的主键

	/** 显示的样式 */
	private String pcls;

	/** 显示顺序 */
	private Integer order; // 可以作为业务上的顺序

	/** 页面的URL */
	private String purl;

	/** 功能是否可以关闭 * */
	private String isCanClose;

	/** 显示位置 ESWNC */
	public String location;
	/** 是否处于激活状态 */
	public boolean isActivation;

	/**
	 * 显示图片(如有,用于替换iconCls)
	 */
	private String picture;

	/**
	 * 业务id
	 */
	private String infoId;
	/**
	 * 标题显示样式
	 */
	private String titleShowType;
	
	/**
	 * 链接参数
	 */
	private String params;

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n id is:");
		sb.append(id);
		sb.append("\n text is:");
		sb.append(text);
		sb.append("\n pcls is:");
		sb.append(pcls);
		sb.append("\n purl is:");
		sb.append(purl);
		sb.append("\n location is:");
		sb.append(location);
		sb.append("\n isActivation is:");
		sb.append(isActivation);

		return sb.toString();
	}

	public String getPurl() {
		return purl;
	}

	public void setPurl(String url) {
		purl = url;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;

	}

	public static final class Comparat implements Comparator<Object> {
		public int compare(Object o1, Object o2) {
			TreeNode treeOne = (TreeNode) o1;
			TreeNode TreeTwo = (TreeNode) o2;
			if (treeOne == null || TreeTwo == null) {
				return 1;
			}
			return treeOne.getOrder().compareTo(TreeTwo.getOrder());
		}
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isActivation() {
		return isActivation;
	}

	public void setActivation(boolean isActivation) {
		this.isActivation = isActivation;
	}

	public String getPcls() {
		return pcls;
	}

	public void setPcls(String pcls) {
		this.pcls = pcls;
	}

	public String getIsCanClose() {
		return isCanClose;
	}

	public void setIsCanClose(String isCanClose) {
		this.isCanClose = isCanClose;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getTitleShowType() {
		return titleShowType;
	}

	public void setTitleShowType(String titleShowType) {
		this.titleShowType = titleShowType;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

}
