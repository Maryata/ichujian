package net.jeeshop.services.front.discntDetail.bean;

import java.io.Serializable;

import net.jeeshop.core.dao.QueryModel;

/**
 * 方案详情
 * @author Maryn
 *
 */
@SuppressWarnings("serial")
public class DiscntDetail extends QueryModel implements Serializable {

	private String id;// 方案详情id
	private String sid;// 方案id
	private String minVal;// 最低交易额度
	private String maxVal;// 最高交易额度
	private String rate;// 折扣点
	
	public void clear(){
		super.clear();
		this.id = null;
		this.sid = null;
		this.minVal = null;
		this.maxVal = null;
		this.rate = null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getMinVal() {
		return minVal;
	}

	public void setMinVal(String minVal) {
		this.minVal = minVal;
	}

	public String getMaxVal() {
		return maxVal;
	}

	public void setMaxVal(String maxVal) {
		this.maxVal = maxVal;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}
	
	/** 数组格式,用于接收多个同名参数 */
	private String[] ids;// 主键
	private String[] minVals;// 最低交易额度
	private String[] maxVals;// 最高交易额度
	private String[] rates;// 折扣点

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String[] getMinVals() {
		return minVals;
	}

	public void setMinVals(String[] minVals) {
		this.minVals = minVals;
	}

	public String[] getMaxVals() {
		return maxVals;
	}

	public void setMaxVals(String[] maxVals) {
		this.maxVals = maxVals;
	}

	public String[] getRates() {
		return rates;
	}

	public void setRates(String[] rates) {
		this.rates = rates;
	}
	
}
