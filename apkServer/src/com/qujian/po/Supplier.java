/**
 * 
 */
package com.qujian.po;

import java.io.Serializable;
import java.util.Date;

/**
 * @author vpc
 * 
 */
public class Supplier implements Serializable {
	private static final long serialVersionUID = 5997058687162192026L;
	private String id; // C_ID 
	private String company; // C_COMPANY 公司名称
	private Date sysdate; // C_SYSDATE 系统时间
	private Date modityTime;// C_MODITY_TIME 修改时间
	private String phone;// C_PHONE 电话
	private String contacts;// C_CONTACTS 联系人
	private String email; // C_EMAIL email
	private String location; // C_LOCATION 地址
	private String leve;// C_LEVE 级别
	private String url; // C_URL url 下载地址
	private String type; // C_TYPE 打开类型 1浏览器or 0webview
	private String name; // C_SUPPLIER_NAME 供应商名称
	private String code; // C_SUPPLIER_CODE 供应商代码
	private String logouri; // logo 地址
	
	private String color; // C_COLOR 导航栏颜色
	private String aboutLogouri; // C_ABOUT_LOGO_URI 欢迎页面LOGO地址
	private String slogan; // C_MAIN_COMMON_SLOGAN 欢迎页面slogan
	private String mainLogouri; // C_MAIN_MAIN_LOGO_URI 导航栏LOGO
	private String aboutInfo; //C_ABOUT_INFO 关于信息（例如：关于触键）
	private String buy; // C_MAIN_MAIN_BUY 一键购膜名称
	private String shoppingUri; // C_SHOPPING_URI 一键购膜图片URI
	private String copyright; // C_COMMON_COPYRIGHT 版权信息
	private String helpAndfeedback; //C_HELPANDFEEDBACK 帮助和反馈
	private String webSite; // 站点地址
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public Date getSysdate() {
		return sysdate;
	}
	public void setSysdate(Date sysdate) {
		this.sysdate = sysdate;
	}
	public Date getModityTime() {
		return modityTime;
	}
	public void setModityTime(Date modityTime) {
		this.modityTime = modityTime;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLeve() {
		return leve;
	}
	public void setLeve(String leve) {
		this.leve = leve;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLogouri() {
		return logouri;
	}
	public void setLogouri(String logouri) {
		this.logouri = logouri;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getAboutLogouri() {
		return aboutLogouri;
	}
	public void setAboutLogouri(String aboutLogouri) {
		this.aboutLogouri = aboutLogouri;
	}
	public String getSlogan() {
		return slogan;
	}
	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}
	public String getMainLogouri() {
		return mainLogouri;
	}
	public void setMainLogouri(String mainLogouri) {
		this.mainLogouri = mainLogouri;
	}
	public String getAboutInfo() {
		return aboutInfo;
	}
	public void setAboutInfo(String aboutInfo) {
		this.aboutInfo = aboutInfo;
	}
	public String getBuy() {
		return buy;
	}
	public void setBuy(String buy) {
		this.buy = buy;
	}
	public String getShoppingUri() {
		return shoppingUri;
	}
	public void setShoppingUri(String shoppingUri) {
		this.shoppingUri = shoppingUri;
	}
	public String getCopyright() {
		return copyright;
	}
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	public String getHelpAndfeedback() {
		return helpAndfeedback;
	}
	public void setHelpAndfeedback(String helpAndfeedback) {
		this.helpAndfeedback = helpAndfeedback;
	}
	public String getWebSite() {
		return webSite;
	}
	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}
	
}
