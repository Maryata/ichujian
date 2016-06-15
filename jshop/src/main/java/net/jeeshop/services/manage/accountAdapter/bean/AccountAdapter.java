package net.jeeshop.services.manage.accountAdapter.bean;

import java.io.Serializable;
/**
 * 适配清单
 * @author wn
 *
 */
@SuppressWarnings("serial")
public class AccountAdapter extends net.jeeshop.services.common.AccountAdapter implements Serializable{
	private String companyName;//会员公司名称
	public void clear() {
		super.clear();
		companyName = null;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
