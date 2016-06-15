package com.org.mokey.basedata.sysinfo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.org.mokey.basedata.baseinfo.common.HtmlUtil;
import com.org.mokey.basedata.sysinfo.dao.H5GameCollectionDao;
import com.org.mokey.basedata.sysinfo.service.H5GameCollectionService;
import com.org.mokey.task.ScheduledTasks;
import com.org.mokey.util.StrUtils;

/**
 * H5游戏合集
 * @author Maryn
 *
 */
public class H5GameCollectionServiceImpl implements H5GameCollectionService {
	
	private H5GameCollectionDao h5GameCollectionDao;

	public H5GameCollectionDao getH5GameCollectionDao() {
		return h5GameCollectionDao;
	}

	public void setH5GameCollectionDao(H5GameCollectionDao h5GameCollectionDao) {
		this.h5GameCollectionDao = h5GameCollectionDao;
	}

	@Override
	// 获取H5游戏合集列表
	public List<Map<String, Object>> h5GameCol() {
		return h5GameCollectionDao.h5GameCol();
	}

	@Override
	// 添加游戏合集
	public void h5AddCol(String name) {
		h5GameCollectionDao.h5AddCol(name);
	}

	@Override
	// 删除合集
	public void h5DeleteCol(String cid) {
		// 删除合集游戏中间表数据
		h5GameCollectionDao.h5DeleteColGame(cid);
		// 删除合集
		h5GameCollectionDao.h5DeleteCol(cid);
	}

	@Override
	// 使合集不可用/可用
	public void h5IsValid(String cid, String islive) {
		h5GameCollectionDao.h5IsValid(cid, islive);
	}

	@Override
	// 修改合集名称
	public void h5ModifyCol(String cid, String cname, String islive) {
		h5GameCollectionDao.h5ModifyCol(cid, cname, islive);
	}

	@Override
	// 查询游戏总数
	public Integer getTotal(String cid) {
		return h5GameCollectionDao.getTotal(cid);
	}

	@Override
	// 查询不属于当前合集的游戏
	public List<Map<String, Object>> getGameList(int page, String cid) {
		return h5GameCollectionDao.getGameList(page, cid);
	}

	@Override
	// 根据合集id查询合集对应的游戏页数
	public Integer getTotalCol(String cid) {
		return h5GameCollectionDao.getTotalCol(cid);
	}

	@Override
	// 根据合集id查询游戏
	public List<Map<String, Object>> getGamePageByColId(String cid, int page) {
		return h5GameCollectionDao.getGamePageByColId(cid, page);
	}

	@Override
	// 条件查询游戏总数
	public Integer getTotalCondition(String cid, String name) {
		return h5GameCollectionDao.getTotalCondition(cid, name);
	}

	@Override
	// 条件查询游戏APP
	public List<Map<String, Object>> queryGameCondition(String cid, String name, int page) {
		return h5GameCollectionDao.queryGameCondition(cid, name, page);
	}

	@Override
	// 对指定id的游戏进行合集分类
	public void handleCol(String cid, String[] gids, String[] removeGids,
			String[] orders) {
		// 替换所有“n”
		if(null!=orders && orders.length>0){
			for (int i = 0; i < orders.length; i++) {
				if("n".equals(orders[i])){
					orders[i] = "";
				}
			}
		}
		// 添加游戏
		if(null!=gids && gids.length>0){
			for (int i = 0; i < gids.length; i++) {
				// 查询游戏是否已经存在于当前合集中
				Integer count = h5GameCollectionDao.isExist(cid,gids[i]);
				if(StrUtils.isNotEmpty(count)){
					if(count==0){
						// 如果不存在，则添加到当前合集
						h5GameCollectionDao.handleCol(cid,gids[i],orders[i]);
					}else{
						// 如果存在，则更新顺序
						h5GameCollectionDao.updateOrder(cid,gids[i],orders[i]);
					}
				}
			}
		}
		// 移出游戏
		if(null!=removeGids && removeGids.length>0){
			for (String gid : removeGids) {
				if(StrUtils.isEmpty(gid)){
					continue;
				}
				h5GameCollectionDao.removeGame(cid,gid);
			}
		}
		// 生成html
		ScheduledTasks st = new ScheduledTasks();
		st.generator();
	}

	@Override
	// 根据合集id查询合集游戏（用于生成html）
	public List<Map<String, Object>> getGamePageByColIdHtml(String cid, int page) {
		List<Map<String,Object>> list = h5GameCollectionDao.getGamePageByColIdHtml(cid, page);
		if(StrUtils.isNotEmpty(list)){
			for (Map<String, Object> map : list) {
				String gid = map.get("C_ID").toString();// 游戏id
				// 查询指定H5游戏被启动的次数
				Integer cnt = h5GameCollectionDao.startingCountH5(gid);
				map.put("startCnts", (cnt==null)?"0":String.valueOf(cnt));
			}
		}
		return list;
	}
	
	
	@Override
	// 生成H5合集html
	public void generateColHtml(String cid) {
		Map<String, Object> htmlMap = new HashMap<String, Object>();
		// 查询合集数据（只查第一页数据）
		List<Map<String, Object>> list = this.getGamePageByColIdHtml(cid, 1);
		if(StrUtils.isNotEmpty(list)){
			htmlMap.put("status", "Y");
			htmlMap.put("games", list);
			// 生成html
			HtmlUtil htmlUtil = new HtmlUtil();
			htmlUtil._fillH5Collection(JSONObject.fromObject(htmlMap), Integer.valueOf(cid));
		}
	}

	@Override
	// 条件查询合集中游戏总数
	public Integer getColTotalCondition(String cid, String name) {
		return h5GameCollectionDao.getColTotalCondition(cid, name);
	}

	@Override
	// 条件查询合集中游戏
	public List<Map<String, Object>> queryColGameCondition(String cid,
			String name, int page) {
		return h5GameCollectionDao.queryColGameCondition(cid, name, page);
	}
}
