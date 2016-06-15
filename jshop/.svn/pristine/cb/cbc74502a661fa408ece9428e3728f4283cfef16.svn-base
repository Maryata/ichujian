package net.jeeshop.services.front.ordermain.bean;

import java.io.Serializable;
import java.util.List;


public class Ordermain extends net.jeeshop.services.common.Ordermain implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private double total0;
	private List<String> queryOrderIDs;// 查询订单的ID集合

	public void clear() {
		super.clear();
		total0 = 0;
		if(queryOrderIDs!=null){
			queryOrderIDs.clear();
			queryOrderIDs = null;
		}
	}
	
	public Ordermain() {
		super();
	}

	public Ordermain(int orderid) {
		super(orderid);
	}
	
	
	public double getTotal0() {
		return total0;
	}

	public void setTotal0(double total0) {
		this.total0 = total0;
	}

	public List<String> getQueryOrderIDs() {
		return queryOrderIDs;
	}

	public void setQueryOrderIDs(List<String> queryOrderIDs) {
		this.queryOrderIDs = queryOrderIDs;
	}
}
