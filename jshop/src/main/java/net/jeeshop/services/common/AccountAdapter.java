package net.jeeshop.services.common;

import java.io.Serializable;

import net.jeeshop.core.dao.QueryModel;

/**
 * 适配清单
 * 
 * @author wn
 * 
 */
@SuppressWarnings("serial")
public class AccountAdapter extends QueryModel implements Serializable {
	private String id;
	private String accountId;// 会员Id
	private String brand;// 品牌
	private String models;// 型号
	private int buyNumber;// 采购量

	public void clear() {
		super.clear();
		id = null;
		accountId = null;
		brand = null;
		models = null;
		buyNumber = 0;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModels() {
		return models;
	}

	public void setModels(String models) {
		this.models = models;
	}

	public int getBuyNumber() {
		return buyNumber;
	}

	public void setBuyNumber(int buyNumber) {
		this.buyNumber = buyNumber;
	}

}
