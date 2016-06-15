package net.jeeshop.services.front.unionpay.bean;

import java.io.Serializable;

import net.jeeshop.core.dao.page.PagerModel;
/**
 * 银联支付
 * @author Maryn
 *
 */
public class UnionPay extends PagerModel implements Serializable{

	private static final long serialVersionUID = 3783343253854569328L;
	public static final String UNIONPAY_PAYSTATUS_Y = "y";//支付成功
	public static final String UNIONPAY_PAYSTATUS_N = "n";//未支付成功
	
	private String id;// 主键id
	private String orderid;// 订单id
	private String orderSerialId;// 订单序列号id
	private String paystatus;// 支付状态
	private double payamount;// 订单金额
	private String createtime;// 创建时间
	private String confirmdate;// 订单确认时间
	private String confirmuser;// 确认人
	private String remark;// 备注
	private String tradeNo;// 银联流水号

	public void clear() {
		super.clear();
		id = null;
		orderid = null;
		paystatus = null;
		payamount = 0;
		createtime = null;
		confirmdate = null;
		confirmuser = null;
		remark = null;
		tradeNo = null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderid() {
		return orderid;
	}
	
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getOrderSerialId() {
		return orderSerialId;
	}

	public void setOrderSerialId(String orderSerialId) {
		this.orderSerialId = orderSerialId;
	}

	public String getPaystatus() {
		return paystatus;
	}

	public void setPaystatus(String paystatus) {
		this.paystatus = paystatus;
	}

	public double getPayamount() {
		return payamount;
	}

	public void setPayamount(double payamount) {
		this.payamount = payamount;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getConfirmdate() {
		return confirmdate;
	}

	public void setConfirmdate(String confirmdate) {
		this.confirmdate = confirmdate;
	}

	public String getConfirmuser() {
		return confirmuser;
	}

	public void setConfirmuser(String confirmuser) {
		this.confirmuser = confirmuser;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	
}
