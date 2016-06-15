package com.org.mokey.basedata.sysinfo.dao;

import java.util.List;
import java.util.Map;

public interface EKGameCueDao {

	// 询游戏提示语
	public List<Map<String, Object>> gameCue();

	// 添加提示语
	public void addCue(String addTitle);

	// 修改提示语
	public void modifyCue(String cid, String title, String islive);

	// 使当前提示内容生效/无效
	public void isvalid(String cid, String islive);

	// 删除提示内容
	public void deleteCol(String cid);

}
