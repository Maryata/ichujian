package net.jeeshop.services.manage.account.bean;import java.io.Serializable;import net.jeeshop.core.dao.QueryModel;import net.jeeshop.core.dao.page.PagerModel;/** * 会员 *  * @author Administrator *  */public class Account extends net.jeeshop.services.common.Account implements Serializable {	private static final long serialVersionUID = 1L;		private String rankName;//等级名称	private String accountTypeName;//信任账号登陆类型名称	private String username;	private String discntSolutnName;//折扣方案名称		private String discounts;//阿智折扣权限	private String otherDiscount;//非阿智折扣权限	private String name;//管理员名称	public void clear() {		super.clear();		rankName = null;		accountTypeName = null;		username=null;		discntSolutnName=null;		discounts=null;		otherDiscount=null;		name=null;	}	public String getRankName() {		return rankName;	}	public void setRankName(String rankName) {		this.rankName = rankName;	}	public String getAccountTypeName() {		return accountTypeName;	}	public void setAccountTypeName(String accountTypeName) {		this.accountTypeName = accountTypeName;	}	public String getUsername() {		return username;	}	public void setUsername(String username) {		this.username = username;	}	public String getDiscntSolutnName() {		return discntSolutnName;	}	public void setDiscntSolutnName(String discntSolutnName) {		this.discntSolutnName = discntSolutnName;	}	public String getDiscounts() {		return discounts;	}	public void setDiscounts(String discounts) {		this.discounts = discounts;	}	public String getOtherDiscount() {		return otherDiscount;	}	public void setOtherDiscount(String otherDiscount) {		this.otherDiscount = otherDiscount;	}	public String getName() {		return name;	}	public void setName(String name) {		this.name = name;	}}