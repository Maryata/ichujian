package com.org.mokey.basedata.sysinfo.service;

import java.util.List;
import java.util.Map;

public interface EKGameCueService {

	/**
	 * 查询游戏提示语
	 * @return
	 */
	public List<Map<String, Object>> gameCue();

	/**
	 * 添加提示语
	 * @param addTitle 提示语
	 */
	public void addCue(String addTitle);

	/**
	 * 修改提示语
	 * @param cid 提示id
	 * @param title 提示内容
	 * @param islive 是否有效
	 */
	public void modifyCue(String cid, String title, String islive);
	
	/**
	 * 使当前提示内容生效/无效
	 * @param cid 提示id
	 * @param islive 提示有效性
	 */
	public void isvalid(String cid, String islive);

	/**
	 * 删除提示内容
	 * @param cid 提示id
	 */
	public void deleteCol(String cid);

}
