package net.jeeshop.services.manage.dataApply.bean;

import java.io.Serializable;
/**
 * 会员资料下载
 * @author wn
 *
 */
@SuppressWarnings("serial")
public class DataApply extends net.jeeshop.services.common.DataApply implements
		Serializable {

	private String startDate;
	private String endDate;
	private String companyName;//会员公司名称
	private String title;//资料名称
	private String fname;//文件名称
	public void clear() {
		super.clear();
		startDate=null;
		endDate=null;
		companyName=null;
		title=null;
		fname=null;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}

}
