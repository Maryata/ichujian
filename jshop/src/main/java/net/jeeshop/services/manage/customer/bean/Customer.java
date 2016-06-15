package net.jeeshop.services.manage.customer.bean;

import java.io.Serializable;

public class Customer extends net.jeeshop.services.common.Customer implements Serializable{
	private static final long serialVersionUID = 1L;
	private String rankName;//等级名称
	private String accountTypeName;//信任账号登陆类型名称
	private String username;
	private String discntSolutnName;//折扣方案名称	
	private String validateId;
	private String detail;
	public void clear() {
		super.clear();
		rankName = null;
		accountTypeName = null;
		username=null;
		discntSolutnName=null;
		validateId=null;
		detail=null;
	}

	public String getRankName() {
		return rankName;
	}

	public void setRankName(String rankName) {
		this.rankName = rankName;
	}

	public String getAccountTypeName() {
		return accountTypeName;
	}

	public void setAccountTypeName(String accountTypeName) {
		this.accountTypeName = accountTypeName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDiscntSolutnName() {
		return discntSolutnName;
	}

	public void setDiscntSolutnName(String discntSolutnName) {
		this.discntSolutnName = discntSolutnName;
	}

	public String getValidateId() {
		return validateId;
	}

	public void setValidateId(String validateId) {
		this.validateId = validateId;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
