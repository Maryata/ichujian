package com.org.mokey.basedata.sysinfo.dao;

import java.util.List;
import java.util.Map;

/**
 * 游戏帮：游戏礼包
 */
public interface EKGameGiftDao {

	// 查询礼包列表
	public List<Map<String, Object>> gameGiftList(String gid, String name, int page, String gName);

	// 获取礼包总数
	public Integer getTotal(String gid, String name, String gName);
	
	// 根据礼包id删除礼包码
	public void deleteGameGiftCode(String id);

	// 根据礼包id删除游戏礼包
	public void deleteGameGift(String id);

	// 保存游戏礼包
	public void saveGameGift(List<Object> args);

	// 更新游戏礼包
	public void updateGameGift(List<Object> args);

	// 根据id查询礼包
	public List<Map<String, Object>> queryGiftById(String editId);

	// 浏览游戏礼包的人数--newId：礼包id，gid：游戏id
	public Integer scanedNum(String newId, String gid);

	// 查询id是否存在
	public boolean existId(String id);

	// 保存上传文件中的礼包码
	public void upload(List<Object[]> list);

	// 根据礼包id查询相关礼包码
	public Map<String, Object> getCodesListByGid(int page, String gid, String state, String sDate, String eDate);

	// 删除礼包码
	public void delCode(String gid, String id);

	// 批量删除礼包码
	public void batchDel(List<Object[]> list);

	// 按“领取状态”查询所有指定礼包的礼包码
	public List<Map<String, Object>> getCodesByGidNState(String gid,
														 String state);

}
