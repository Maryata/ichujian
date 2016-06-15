package com.org.mokey.basedata.sysinfo.service;

import java.util.List;
import java.util.Map;

/**
 * H5游戏合集
 * @author Maryn
 *
 */
public interface H5GameCollectionService {

	/**
	 * 获取游戏合集列表
	 * @return
	 */
	public List<Map<String, Object>> h5GameCol();

	/**
	 * 添加游戏合集
	 * @param name
	 */
	public void h5AddCol(String name);

	/**
	 * 删除合集
	 * @param cid 合集id
	 */
	public void h5DeleteCol(String cid);

	/**
	 * 使合集不可用/可用
	 * @param cid 合集id
	 * @param islive 0：使生效，1：使无效
	 */
	public void h5IsValid(String cid, String islive);

	/**
	 * 修改合集名称
	 * @author Maryn
	 * @param cid 合集id
	 * @param cname 合集名称
	 * @param islive 是否有效
	 */
	public void h5ModifyCol(String cid, String cname, String islive);

	/**
	 * 查询不属于当前合集的游戏总条数
	 * @author Maryn
	 * @param cid 合集id
	 * @return 总数
	 */
	public Integer getTotal(String cid);

	/**
	 * 查询不属于当前合集的游戏
	 * @author Maryn
	 * @param cid 合集id
	 * @return 所有游戏
	 */
	public List<Map<String, Object>> getGameList(int page, String cid);

	/**
	 * 根据合集id查询合集对应的游戏页数
	 * @author Maryn
	 * @param cid 合集id
	 * @return 合集对应的游戏页数
	 */
	public Integer getTotalCol(String cid);

	/**
	 * 根据合集id查询游戏
	 * @author Maryn
	 * @param cid 合集id
	 * @param page 分页页码
	 * @return 合集对应的游戏
	 */
	public List<Map<String, Object>> getGamePageByColId(String cid, int page);

	/**
	 * 条件查询游戏总数
	 * @author Maryn
	 * @param cid 合集id
	 * @param name 游戏名称
	 * @return 总数
	 */
	public Integer getTotalCondition(String cid, String name);

	/**
	 * 条件查询游戏APP
	 * @author Maryn
	 * @param cid 合集id
	 * @param name 游戏名称
	 * @param page 页码
	 * @return 符合条件的游戏
	 */
	public List<Map<String, Object>> queryGameCondition(String cid, String name, int page);

	/**
	 * 对指定id的游戏进行合集分类
	 * @author Maryn
	 * @param cid 合集id
	 * @param gids 游戏id集合
	 * @param removeGids 要移除出当前集合的游戏id
	 * @param orders 排序
	 */
	public void handleCol(String cid, String[] gids, String[] removeGids,
			String[] orders);

	/**
	 * 根据合集id查询合集游戏（用于生成html）
	 * @param cid 合集id
	 * @param page 分页
	 * @return
	 */
	public List<Map<String, Object>> getGamePageByColIdHtml(String cid, int page);

	/**
	 * 生成H5合集html
	 * @param cid 合集id
	 */
	public void generateColHtml(String cid);

	/**
	 * 条件查询合集中游戏总数
	 * @param cid 合集id
	 * @param name 游戏名
	 * @return
	 */
	public Integer getColTotalCondition(String cid, String name);
	
	/**
	 * 条件查询合集中游戏
	 * @param cid 合集id
	 * @param name 游戏名
	 * @param page 分页
	 * @return
	 */
	public List<Map<String, Object>> queryColGameCondition(String cid,
			String name, int page);
}
