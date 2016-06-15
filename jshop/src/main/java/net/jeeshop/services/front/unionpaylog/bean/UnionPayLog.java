package net.jeeshop.services.front.unionpaylog.bean;

import java.io.Serializable;
import java.util.Date;

import net.jeeshop.core.dao.QueryModel;

/**
 * 银联支付回调信息
 * @author Maryn
 *
 */
public class UnionPayLog extends QueryModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id; 
	private String callback;// 回调方法名 
	private String orderId;// 平台流水号（orderpay表的remark字段）
	private String queryId;// 银联查询号
	private String respCode;// 应答码
	private String respMsg;// 应答信息
	private String valideData;// 支付响应信息
	private Date logTime;// 操作时间
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCallback() {
		return callback;
	}
	public void setCallback(String callback) {
		this.callback = callback;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getQueryId() {
		return queryId;
	}
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	public String getValideData() {
		return valideData;
	}
	public void setValideData(String valideData) {
		this.valideData = valideData;
	}
	public Date getLogTime() {
		return logTime;
	}
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
}
