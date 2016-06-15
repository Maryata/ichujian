package com.org.mokey.basedata.sysinfo.service;

import java.util.List;
import java.util.Map;

/**
 *	游戏帮：游戏合集
 */
public interface GameCollectionService {

	/**
	 * 获取游戏合集
	 * @author Maryn
	 * @param cid 合集id
	 * @return 游戏合集
	 */
	public List<Map<String, Object>> gameCol(String cid);

	/**
	 * 添加游戏合集
	 * @author Maryn
	 * @param name 合集名称
	 * @param type 类型
	 */
	public void addCol(String name, String type);
	
	/**
	 * 删除游戏合集
	 * @author Maryn
	 * @param cid 合集id
	 */
	public void deleteCol(String cid);
	
	/**
	 * 修改合集名称
	 * @author Maryn
	 * @param cid 合集id
	 * @param cname 合集名称
	 * @param cname 是否有效
	 */
	public void modifyCol(String cid, String cname, String islive);

	/**
	 * 使合集失效
	 * @author Maryn
	 * @param cid 合集id
	 * @param islive 是否有效
	 */
	public void isvalid(String cid, String islive);
	
	/**
	 * 查询不属于当前合集的游戏
	 * @author Maryn
	 * @param cid 合集id
	 * @param type 
	 * @return 所有游戏
	 */
	public List<Map<String, Object>> getGameList(int page, String cid, String type);

	/**
	 * 对指定id的游戏进行合集分类
	 * @author Maryn
	 * @param cid 合集id
	 * @param gids 游戏id集合
	 * @param removeGids 要移除出当前集合的游戏id
	 * @param orders 排序
	 */
	public void handleCol(String cid, String[] gids, String[] removeGids, String[] orders);
	
	/**
	 * 查询不属于当前合集的游戏总条数
	 * @author Maryn
	 * @param cid 合集id
	 * @param type 类型
	 * @return 总数
	 */
	public Integer getTotal(String cid, String type);

	/**
	 * 条件查询游戏APP
	 * @author Maryn
	 * @param cid 合集id
	 * @param name 游戏名称
	 * @param type 
	 * @param minSize 游戏大小范围：最小
	 * @param maxSize 游戏大小范围：最大
	 * @param page 页码
	 * @return 符合条件的游戏
	 */
	public List<Map<String, Object>> queryGameCondition(String cid,
			String name, String type, String minSize, String maxSize, int page);

	/**
	 * 条件查询游戏总数
	 * @author Maryn
	 * @param cid 合集id
	 * @param name 游戏名称
	 * @param type 
	 * @param minSize 最小游戏大小
	 * @param maxSize 最大游戏大小
	 * @return 总数
	 */
	public Integer getTotalCondition(String cid, String name,
			String type, String minSize, String maxSize);

	/**
	 * 根据合集id查询游戏
	 * @author Maryn
	 * @param cid 合集id
	 * @param page 分页页码
	 * @param type 
	 * @return 合集对应的游戏
	 */
	public List<Map<String, Object>> getGamePageByColId(String cid, int page, String type);

	/**
	 * 根据合集id查询合集对应的游戏页数
	 * @author Maryn
	 * @param cid 合集id
	 * @param type 
	 * @return 合集对应的游戏页数
	 */
	public Integer getTotalCol(String cid, String type);

	/**
	 * 条件查询合集中游戏总数
	 * @param cid 合集id
	 * @param name 游戏名
	 * @param type 
	 * @return
	 */
	public Integer getColTotalCondition(String cid, String name, String type);

	/**
	 * 条件查询合集中游戏
	 * @param cid 合集id
	 * @param name 游戏名
	 * @param page 分页
	 * @param type 
	 * @return
	 */
	public List<Map<String, Object>> queryColGameCondition(String cid,
			String name, int page, String type);

}
