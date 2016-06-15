package net.jeeshop.services.manage.systemDict.bean;

import java.io.Serializable;

import net.jeeshop.core.dao.QueryModel;

@SuppressWarnings("serial")
public class SystemDict extends QueryModel implements Serializable {
	
	private String id;
	private String pid;
	private String ddlKey;
	private String ddlVal;
	private int sort=0;
	private String extVal;
	
	public void clear(){
		super.clear();
		this.id = null;
		this.pid = null;
		this.ddlKey = null;
		this.ddlVal = null;
		sort= 0;
		extVal = null;
	}


	
	private String[] subDdlKey;// 子类名称
	
	private String[] subDdlVal;// 子类值
	private String[] subExtVal;// 子类扩展
	
	private String[] ids;//
	private String[] sorts;//
	
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

	public String getDdlKey() {
		return ddlKey;
	}

	public void setDdlKey(String ddlKey) {
		this.ddlKey = ddlKey;
	}

	public String getDdlVal() {
		return ddlVal;
	}

	public void setDdlVal(String ddlVal) {
		this.ddlVal = ddlVal;
	}

	public String[] getSubDdlKey() {
		return subDdlKey;
	}

	public void setSubDdlKey(String[] subDdlKey) {
		this.subDdlKey = subDdlKey;
	}

	public String[] getSubDdlVal() {
		return subDdlVal;
	}

	public void setSubDdlVal(String[] subDdlVal) {
		this.subDdlVal = subDdlVal;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String[] getSorts() {
		return sorts;
	}

	public void setSorts(String[] sorts) {
		this.sorts = sorts;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getExtVal() {
		return extVal;
	}

	public void setExtVal(String extVal) {
		this.extVal = extVal;
	}

	public String[] getSubExtVal() {
		return subExtVal;
	}

	public void setSubExtVal(String[] subExtVal) {
		this.subExtVal = subExtVal;
	}
	
	
}
