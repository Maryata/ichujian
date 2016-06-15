package net.jeeshop.services.common;

import java.io.Serializable;

import net.jeeshop.core.dao.QueryModel;

public class MakeTime extends QueryModel implements Serializable{
	private static final long serialVersionUID = 1L;
   private String id;//id
public int getS_count() {
	return s_count;
}
public void setS_count(int s_count) {
	this.s_count = s_count;
}
public int getT_count() {
	return t_count;
}
public void setT_count(int t_count) {
	this.t_count = t_count;
}
public int getDays() {
	return days;
}
public void setDays(int days) {
	this.days = days;
}
private int s_count;//单件商品最大数
   private int t_count;//总数量
   private int days;//发货需要的时间
   private String type;//类型  0：裸膜        1：精装以及简装出货时间
public void clear() {
	super.clear();
	id=null;
	s_count=0;
	t_count=0;
	days=0;
	type=null;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}

}
