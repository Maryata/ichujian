package com.org.mokey.basedata.sysinfo.service;

import java.util.List;
import java.util.Map;

/**
 * 游戏帮：游戏礼包
 */
public interface EKGameGiftService {

	/**
	 *  查询礼包列表
	 * @param gid 游戏id
	 * @param name 礼包标题名
	 * @param page 分页页码
	 * @param gName 游戏名
	 * @return
	 */
	public List<Map<String, Object>> gameGiftList(String gid, String name, int page, String gName);

	/**
	 * 获取礼包总数
	 * @param gid 游戏id
	 * @param name 礼包标题名
	 * @param gName 游戏名
	 * @return
	 */
	public Integer getTotal(String gid, String name, String gName);

	/**
	 * 根据id删除游戏礼包
	 * @param id 礼包id
	 */
	public void deleteGameGift(String id);

	/**
	 * 保存礼包
	 * @param id 礼包id 
	 * @param gid 游戏id 
	 * @param name 礼包名称 
	 * @param depict 礼包内容 
	 * @param sdate 开始时间 
	 * @param edate 结束时间 
	 * @param method 使用方法 
	 * @param count 礼包数量 
	 * @param order 排序
	 * @param cate 游戏礼包分类
	 */
	public void saveGameGift(String id, String gid, String name, String depict,
							 String sdate, String edate, String method, String count,
							 String order, String cate);

	/**
	 * 根据id查询礼包
	 * @param editId 礼包id
	 * @return
	 */
	public List<Map<String, Object>> queryGiftById(String editId);

	/**
	 * 保存上传文件中的礼包码
	 * @param list 礼包码
	 * @param gid 游戏id
	 */
	public void upload(List<String> list, String gid);

	/**
	 * 根据礼包id查询相关礼包码
	 * @param page 分页
	 * @param gid 礼包id
	 * @param state 领取状态
	 * @param eDate 开始时间
	 * @param sDate 结束时间
	 * @return
	 */
	public Map<String, Object> getCodesListByGid(int page, String gid, String state, String sDate, String eDate);

	/**
	 * 删除礼包码
	 * @param gid 礼包id
	 * @param id 礼包码id
	 */
	public void delCode(String gid, String id);

	/**
	 * 批量删除礼包码
	 * @param gid 礼包id
	 * @param id 礼包码id
	 */
	public void batchDel(String gid, String id);

	/**
	 * 按“领取状态”查询所有指定礼包的礼包码
	 * @param gid 礼包id
	 * @param state 领取状态
	 * @return
	 */
	public List<Map<String, Object>> getCodesByGidNState(String gid,
														 String state);


}
