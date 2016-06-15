package net.jeeshop.services.front.account.bean;import java.io.Serializable;import java.util.Map;import net.jeeshop.core.front.SystemManager;import net.jeeshop.core.util.StrUtils;/** * 会员 *  * @author jqsl2012@163.com *  */public class Account extends net.jeeshop.services.common.Account implements Serializable {	private static final long serialVersionUID = 1L;	// 修改密码	private String password2;	private String newPassword;	private String newPassword2;   	private String vcode;// 注册时候输入的验证码	private String mVcode;// 短信验证码	private String rate;	public static final String account_freeze_y = "y";//已冻结	public static final String account_freeze_n = "n";//未冻结	public static final String account_emailIsActive_y = "y";//邮箱已激活	public static final String account_emailIsActive_n = "n";//邮箱未激活		private LoginTypeEnum loginType;//登陆方式		private String newEmail;//新邮箱，修改绑定的邮箱//	private String orderid;//用户订单号，订单完毕后更新此订单的积分到用户头上	private int addScore;//订单完毕后，此用户增加的积分数，此积分数最后会被增加到用户的积分账户上		private String validateId;//验证		private String login;//登录用;		private Map<String,String> brandMap;//用户产品线map	private String brands;//用户所属品牌的产品线code	private Map<String,Map<String,String>> accPriceMap;		public Map<String, Map<String, String>> getAccPriceMap() {		return accPriceMap;	}	public void setAccPriceMap(Map<String, Map<String, String>> accPriceMap) {		this.accPriceMap = accPriceMap;	}	public void clear() {		super.clear();		newEmail = null;		password2 = null;		newPassword = null;		newPassword2 = null;		loginType = null;//		orderid = null;		addScore = 0;		validateId = null;		mVcode = null;		rate=null;	}	public String getPassword2() {		return password2;	}	public void setPassword2(String password2) {		this.password2 = password2;	}	public String getNewPassword() {		return newPassword;	}	public void setNewPassword(String newPassword) {		this.newPassword = newPassword;	}	public String getNewPassword2() {		return newPassword2;	}	public void setNewPassword2(String newPassword2) {		this.newPassword2 = newPassword2;	}	public String getVcode() {		return vcode;	}	public void setVcode(String vcode) {		this.vcode = vcode;	}	public LoginTypeEnum getLoginType() {		return loginType;	}	public void setLoginType(LoginTypeEnum loginType) {		this.loginType = loginType;	}	public String getNewEmail() {		return newEmail;	}	public void setNewEmail(String newEmail) {		this.newEmail = newEmail;	}	public int getAddScore() {		return addScore;	}	public void setAddScore(int addScore) {		this.addScore = addScore;	}	public String getValidateId() {		return validateId;	}	public void setValidateId(String validateId) {		this.validateId = validateId;	}		public String getmVcode() {		return mVcode;	}	public void setmVcode(String mVcode) {		this.mVcode = mVcode;	}	public Map getBrandMap() {		return brandMap;	}	public void setBrandMap(Map brandMap) {		this.brandMap = brandMap;	}	public String getBrands() {		return brands;	}	public void setBrands(String brands) {		this.brands = brands;	}	/**	 * 获取  结款方式	 * @return	 */	public String getPaymentTypeName(){		String k = this.getPaymentType();		k = SystemManager.getInstance().getSystemDict("account_paymentType", k);		if(null==k){			k="";		}		return k;	}		/**	 * 检查账号信息是否完整	 * 用于下单确认判断;	 * @return boolean	 */	public boolean checkUser(){		if("0".equals(this.getAccType())){			if(StrUtils.isEmpty(this.getTrueName())){					return false;				}		}else if("1".equals(this.getAccType())){			if(StrUtils.isEmpty(this.getTrueName()) ||StrUtils.isEmpty(this.getPhone())					||StrUtils.isEmpty(this.getNature()) ||(StrUtils.isEmpty(this.getLtd()) && StrUtils.isEmpty(this.getLtdName()) ) )				{					return false;				}		}		return true;	}		/**检查资料是否审核通过*/	public boolean checkAduitUser(){		if(StrUtils.isEmpty(this.getCompanyName()) ||StrUtils.isEmpty(this.getCorporate()) ||StrUtils.isEmpty(this.getBossName())			|| StrUtils.isEmpty(this.getBossPhone())			|| StrUtils.isEmpty(this.getDiscntSolutnId())			|| !Account.account_status_finance.equals(this.getStatus())//		){			return false;		}		return true;	}	public String getLogin() {		return login;	}	public void setLogin(String login) {		this.login = login;	}	public String getRate() {		return rate;	}	public void setRate(String rate) {		this.rate = rate;	}			}