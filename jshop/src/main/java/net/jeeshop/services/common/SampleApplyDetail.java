package net.jeeshop.services.common;

import java.io.Serializable;

import net.jeeshop.core.dao.page.PagerModel;

public class SampleApplyDetail extends PagerModel implements Serializable {
	private static final long serialVersionUID = 1L;
    private String id;
    private String productName;//产品名称
    private String productCode;//产品code
    private String productId;//产品id
    private String brandName;//产品名称
    private String modelName;//手机品牌名称
    private String plineName;//指电品牌名称
    private String sId;//
	private String creatTime;//添加时间
	private String productNum;//产品数量
	private String number;
	private String productPrice;//产品单价
	private String plineCode;
	private String[] productNums;
	private String[] productNames;
    private String[] productCodes;
    private String[] productIds;
    private String[] brandNames;
    private String[] modelNames;
    private String[] plineNames;
    private String[] plineCodes;
    public void clear() {
		super.clear();
		id=null;
		productName=null;
		productId=null;
		brandName=null;
		modelName=null;
		plineName=null;
		sId=null;
		creatTime=null;
		productCode=null;
		productName=null;
		productNum=null;
		number=null;
		productIds=null;
		brandNames=null;
		modelNames=null;
		plineNames=null;
		productCodes=null;
		productNums=null;
		productPrice=null;
		plineCode=null;
    }
	public String[] getProductNums() {
		return productNums;
	}
	public void setProductNums(String[] productNums) {
		this.productNums = productNums;
	}
    public String getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String[] getProductNames() {
		return productNames;
	}
	public void setProductNames(String[] productNames) {
		this.productNames = productNames;
	}
	public String[] getProductCodes() {
		return productCodes;
	}
	public void setProductCodes(String[] productCodes) {
		this.productCodes = productCodes;
	}
	public String[] getProductIds() {
		return productIds;
	}
	public void setProductIds(String[] productIds) {
		this.productIds = productIds;
	}
	public String[] getBrandNames() {
		return brandNames;
	}
	public void setBrandNames(String[] brandNames) {
		this.brandNames = brandNames;
	}
	public String[] getModelNames() {
		return modelNames;
	}
	public void setModelNames(String[] modelNames) {
		this.modelNames = modelNames;
	}
	public String[] getPlineNames() {
		return plineNames;
	}
	public void setPlineNames(String[] plineNames) {
		this.plineNames = plineNames;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getPlineName() {
		return plineName;
	}
	public void setPlineName(String plineName) {
		this.plineName = plineName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getsId() {
		return sId;
	}
	public void setsId(String sId) {
		this.sId = sId;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductNum() {
		return productNum;
	}
	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}
	public String getPlineCode() {
		return plineCode;
	}
	public void setPlineCode(String plineCode) {
		this.plineCode = plineCode;
	}
	public String[] getPlineCodes() {
		return plineCodes;
	}
	public void setPlineCodes(String[] plineCodes) {
		this.plineCodes = plineCodes;
	}
}
