package net.jeeshop.services.common;

import java.io.Serializable;

import net.jeeshop.core.dao.QueryModel;

/*顾客资料*/
public class Customer extends QueryModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String account; //账号
	private String nickname; //昵称
	private String password; //密码
	private String accountType; //账号类型;
	private String sex;
	private String birthday;
	private String province;
	private String city;
	
	
	
	private String registerType;//注册类型
	private String mingpianImage;//名片图片
	private String mendianImage;//门店图片
	private String weixin;//微信
	private String saleCode;//销售人员工号
	private String brandType;//销售品牌
	@Deprecated
	private String cardType;
	@Deprecated
	private String cardNO;
	@Deprecated
	private int grade;
	private String amount;
	private String tel; //电话
	
	
	private String freeze;//冻结状态
	private String lastLoginTime;
	private String lastLoginIp;
	private String lastLoginArea;
	private String diffAreaLogin;
	private String regeistDate;
	private String freezeStartdate;
	private String freezeEnddate;
	private int addressID;
	private String openId;
	private String accessToken;
	private String alipayUseId;//支付宝快捷登陆的用户ID
	private String sinaWeiboID;//新浪微博用户登陆返回的ID
	private String rank;//会员等级
	private int score;//会员积分
	
	//TODO 删除系统用户前须校验、线下关系转移
	private String saleId;//销售id(用户表Id)
	private String saleDeptId;//销售所在的部门 冗余字段:修改用户所在的部门时需同步修改
	
	private String discntSolutnId;//折扣方案ID
	
	//-----------基础信息
	private String companyName;//公司名称
	private String corporate;//公司法人 -m-
	private String trueName; //联系人姓名
	private String phone;//联系人电话
	private String jobTitle;//联系人职位
	private String email;//联系人邮箱
	private String emailIsActive;
	private String bossName;//总经理姓名 -m-
	private String bossPhone;//总经理电话 -m-
	private String address;//公司地址
	private String postcode; //邮编
	
	//-----------公司资质
	private String busLicenseScan;//公司营业执照扫描件
	private String taxCertificateScan;//公司税务登记证扫描件
	private String companyBank;//开户行名称
	private String bankAccountName;//开户人名称
	private String bankNo;//开户帐号
	private String taxNo;//税号
	
	//-----------经营信息
	private String nature;//公司性质
	private String ltd;//经营性质 xx
	private String ltdName;//经营性质 xx
	private String products;//经销产品 xx
	private String productsName;//经销产品 xx
	
	private String annualSales;//年销售额
	private String purchaseType;//采购分类 xx -m-
	private String paymentType;//结算方式 -m-
	
	private String status;//资料审核状态
	
	private String activeCode;// 激活码
	private String accountCode;//会员编号
	private String accountBrand;//会员品牌
	private String syncState;//同步的状态 0未同步  1同步
	public static final String account_status_init = "init";//信息初始化 -- 待销售联系客户健全资料
	public static final String account_status_sales = "sales";//销售补充资料 -- 提交总监审核
	public static final String account_status_salesm = "salesm";//销售总监审核  -- 提交财务审核
	public static final String account_status_finance = "finance";//财务审核完成
	

//	// 修改密码
//	private String password2;
//	private String newPassword;
//	private String newPassword2;
//
//	private String vcode;// 注册时候输入的验证码
//	
//	public static final String account_freeze_y = "y";//已冻结
//	public static final String account_freeze_n = "n";//未冻结
//	public static final String account_emailIsActive_y = "y";//邮箱已激活
//	public static final String account_emailIsActive_n = "n";//邮箱未激活
//	
//	private LoginTypeEnum loginType;//登陆方式
//	
//	private String newEmail;//新邮箱，修改绑定的邮箱
	
	/**
	 * 根据会员积分获取会员等级
	 * @return
	 */
	public String getRankCode() {
		if (this.score < 500) {
			return "R1";
		} else if (this.score >= 999) {
			return "R2";
		} else if (this.score >= 1000 && this.score <= 1999) {
			return "R3";
		} else if (this.score >= 2000 && this.score <= 4000) {
			return "R4";
		} else {
			return "R5";
		}
	}
	public void clear() {
		super.clear();
		id = null;
		registerType=null;
		mingpianImage=null;
		mendianImage=null;
		weixin=null;
		account = null;
		nickname = null;
		password = null;
		accountType = null;
		trueName = null;
		sex = null;
		birthday = null;
		province = null;
		city = null;
		address = null;
		postcode = null;
		cardType = null;
		cardNO = null;
		grade = 0;
		amount = null;
		tel = null;
		email = null;
		emailIsActive = null;
		freeze = null;
//		vcode = null;
		lastLoginTime = null;
		lastLoginIp = null;
		lastLoginArea = null;
		diffAreaLogin = null;
		regeistDate = null;
		addressID = 0;
		freezeStartdate = null;
		freezeEnddate = null;
		openId = null;
		accessToken = null;
		saleCode=null;
		brandType=null;
//		newEmail = null;
//		password2 = null;
//		newPassword = null;
//		newPassword2 = null;
//		loginType = null;
		
		alipayUseId = null;
		sinaWeiboID = null;
		rank = null;
		score = 0;
		
		phone = null;
		companyName = null;
		companyBank = null;
		bankAccountName = null;
		bankNo = null;
		taxNo = null;
		paymentType = null;
		corporate =null;
		jobTitle =null;
		bossName =null;
		bossPhone =null;
		busLicenseScan =null;
		taxCertificateScan =null;
		
		nature = null;
		ltd =null; ltdName =null;
		products =null; productsName =null;
		annualSales =null; purchaseType =null;
		status = null;
		accountCode=null;
		accountBrand=null;
		activeCode = null;
		syncState=null;
	}
	public String getActiveCode() {
		return activeCode;
	}
	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNO() {
		return cardNO;
	}

	public void setCardNO(String cardNO) {
		this.cardNO = cardNO;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFreeze() {
		return freeze;
	}

	public void setFreeze(String freeze) {
		this.freeze = freeze;
	}


	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getRegeistDate() {
		return regeistDate;
	}

	public void setRegeistDate(String regeistDate) {
		this.regeistDate = regeistDate;
	}
	
	public int getAddressID() {
		return addressID;
	}

	public void setAddressID(int addressID) {
		this.addressID = addressID;
	}

	public String getFreezeStartdate() {
		return freezeStartdate;
	}

	public void setFreezeStartdate(String freezeStartdate) {
		this.freezeStartdate = freezeStartdate;
	}

	public String getFreezeEnddate() {
		return freezeEnddate;
	}

	public void setFreezeEnddate(String freezeEnddate) {
		this.freezeEnddate = freezeEnddate;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAlipayUseId() {
		return alipayUseId;
	}

	public void setAlipayUseId(String alipayUseId) {
		this.alipayUseId = alipayUseId;
	}
	
	public String getSinaWeiboID() {
		return sinaWeiboID;
	}

	public void setSinaWeiboID(String sinaWeiboID) {
		this.sinaWeiboID = sinaWeiboID;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getEmailIsActive() {
		return emailIsActive;
	}

	public void setEmailIsActive(String emailIsActive) {
		this.emailIsActive = emailIsActive;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getLastLoginArea() {
		return lastLoginArea;
	}

	public void setLastLoginArea(String lastLoginArea) {
		this.lastLoginArea = lastLoginArea;
	}

	public String getDiffAreaLogin() {
		return diffAreaLogin;
	}

	public void setDiffAreaLogin(String diffAreaLogin) {
		this.diffAreaLogin = diffAreaLogin;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyBank() {
		return companyBank;
	}

	public void setCompanyBank(String companyBank) {
		this.companyBank = companyBank;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getTaxNo() {
		return taxNo;
	}

	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getSaleId() {
		return saleId;
	}

	public void setSaleId(String saleId) {
		this.saleId = saleId;
	}

	public String getSaleDeptId() {
		return saleDeptId;
	}

	public void setSaleDeptId(String saleDeptId) {
		this.saleDeptId = saleDeptId;
	}

	public String getCorporate() {
		return corporate;
	}

	public void setCorporate(String corporate) {
		this.corporate = corporate;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getBossName() {
		return bossName;
	}

	public void setBossName(String bossName) {
		this.bossName = bossName;
	}

	public String getBossPhone() {
		return bossPhone;
	}

	public void setBossPhone(String bossPhone) {
		this.bossPhone = bossPhone;
	}

	public String getBusLicenseScan() {
		return busLicenseScan;
	}

	public void setBusLicenseScan(String busLicenseScan) {
		this.busLicenseScan = busLicenseScan;
	}

	public String getTaxCertificateScan() {
		return taxCertificateScan;
	}

	public void setTaxCertificateScan(String taxCertificateScan) {
		this.taxCertificateScan = taxCertificateScan;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public String getLtd() {
		return ltd;
	}

	public void setLtd(String ltd) {
		this.ltd = ltd;
	}

	public String getLtdName() {
		return ltdName;
	}

	public void setLtdName(String ltdName) {
		this.ltdName = ltdName;
	}

	public String getProducts() {
		return products;
	}

	public void setProducts(String products) {
		this.products = products;
	}

	public String getProductsName() {
		return productsName;
	}

	public void setProductsName(String productsName) {
		this.productsName = productsName;
	}

	public String getAnnualSales() {
		return annualSales;
	}

	public void setAnnualSales(String annualSales) {
		this.annualSales = annualSales;
	}

	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDiscntSolutnId() {
		return discntSolutnId;
	}

	public void setDiscntSolutnId(String discntSolutnId) {
		this.discntSolutnId = discntSolutnId;
	}

	public String getBrandType() {
		return brandType;
	}

	public void setBrandType(String brandType) {
		this.brandType = brandType;
	}

	public String getSaleCode() {
		return saleCode;
	}

	public void setSaleCode(String saleCode) {
		this.saleCode = saleCode;
	}

	public String getWeixin() {
		return weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	public String getMendianImage() {
		return mendianImage;
	}

	public void setMendianImage(String mendianImage) {
		this.mendianImage = mendianImage;
	}

	public String getMingpianImage() {
		return mingpianImage;
	}

	public void setMingpianImage(String mingpianImage) {
		this.mingpianImage = mingpianImage;
	}

	public String getRegisterType() {
		return registerType;
	}

	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public String getAccountBrand() {
		return accountBrand;
	}
	public void setAccountBrand(String accountBrand) {
		this.accountBrand = accountBrand;
	}
	public String getSyncState() {
		return syncState;
	}
	public void setSyncState(String syncState) {
		this.syncState = syncState;
	}
}
