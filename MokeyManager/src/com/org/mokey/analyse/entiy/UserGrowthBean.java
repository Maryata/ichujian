package com.org.mokey.analyse.entiy;

public class UserGrowthBean {
	
	private int startCn;//激动
	private int activeCn;//激活
	private int inventoryCn;//库存
	
	private String day;//日期
	private String lasyDay;//去年日期
	private String activeRate ="0.00";//act rate;
	
	public int getStartCn() {
		return startCn;
	}
	public void setStartCn(int startCn) {
		this.startCn = startCn;
	}
	public int getActiveCn() {
		return activeCn;
	}
	public void setActiveCn(int activeCn) {
		this.activeCn = activeCn;
	}
	public int getInventoryCn() {
		return inventoryCn;
	}
	public void setInventoryCn(int inventoryCn) {
		this.inventoryCn = inventoryCn;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getLasyDay() {
		return lasyDay;
	}
	public void setLasyDay(String lasyDay) {
		this.lasyDay = lasyDay;
	}
	public String getActiveRate() {
		return activeRate;
	}
	public void setActiveRate(String activeRate) {
		this.activeRate = activeRate;
	}
	
}
