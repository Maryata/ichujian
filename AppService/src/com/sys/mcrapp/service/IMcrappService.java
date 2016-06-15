/**
 * 
 */
package com.sys.mcrapp.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author vpc
 *
 */
public interface IMcrappService {
	/**
	 * 持久化用户行为
	 * @Description: TODO
	 * @param imei IMEI 必须
	 * @param aid 应用ID 必须
	 * @param content 搜索内容 默认空
	 * @param type 操作类型 默认：0， 0：收藏，1：打开，2：取消收藏，3：关闭，4：搜索
	 * @param date 操作时间 默认：系统当前时间
	 * @return
	 */
	Integer persistentUserBehavior(String imei,String aid,String content,String type,Date date);
	/**
	 * 分类列表
	 * @Description: TODO
	 * @param page 当前页，默认：1
	 * @param rows 每页显示数量，默认：20
	 * @return
	 */
	List<Map<String,Object>> listCategory(int page, int rows);
	/**
	 * 首页定制
	 * @Description: TODO
	 * @return
	 */
	Map<String,Object> index();
	
	/**
	 * 分类下的应用列表
	 * @Description: TODO
	 * @param cid 分类ID
	 * @param page 当前页，默认：1
	 * @param rows 每页显示数量，默认：20
	 * @return
	 */
	List<Map<String,Object>> categoryDetail(String cid,int page,int rows);
	
	/**
	 * 合集下的应用列表
	 * @Description: TODO
	 * @param cid 合集ID
	 * @param page 当前页，默认：1
	 * @param rows 每页显示数量，默认：20
	 * @return
	 */
	List<Map<String,Object>> collectionDetail(String cid,int page,int rows);
	
	/**
	 * 搜索
	 * @Description: TODO
	 * @param imei 发起搜索的设备IMEI
	 * @param content 搜索内容
	 * @param date 搜索时间
	 * @return
	 */
	List<Map<String,Object>> search(String imei,String content,Date date);
}
