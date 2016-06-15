package com.org.mokey.basedata.sysinfo.service.impl;

import java.util.List;
import java.util.Map;

import com.org.mokey.basedata.sysinfo.dao.McrAppColDao;
import com.org.mokey.basedata.sysinfo.service.McrAppColService;
import com.org.mokey.util.StrUtils;

/**
 * 微用帮：应用合集
 */
public class McrAppColServiceImpl implements McrAppColService{
	
	private McrAppColDao mcrAppColDao;

	public void setMcrAppColDao(McrAppColDao mcrAppColDao) {
		this.mcrAppColDao = mcrAppColDao;
	}

	public McrAppColDao getMcrAppColDao() {
		return mcrAppColDao;
	}

	// 获取应用合集列表
	@Override
	public List<Map<String, Object>> mcrAppColList() {
		return mcrAppColDao.mcrAppColList();
	}

	@Override
	// 新增合集
	public void mcrAppColAdd(String addName, String userName) {
		mcrAppColDao.mcrAppColAdd(addName, userName);
	}

	@Override
	// 删除合集
	public void mcrAppColDel(String cid) {
		mcrAppColDao.mcrAppColDel(cid);
	}

	@Override
	// 更新合集
	public void update(String cid, String cname, String modifier) {
		mcrAppColDao.update(cid, cname, modifier);
	}

	@Override
	// 查询所有应用
	public Integer getAppTotal(String cid) {
		return mcrAppColDao.getAppTotal(cid);
	}

	@Override
	// 查询所有应用
	public List<Map<String, Object>> getAppList(int page, String cid) {
		return mcrAppColDao.getAppList(page, cid);
	}

	@Override
	// 查询当前合集的应用总数 
	public Integer getColTotal(String cid) {
		return mcrAppColDao.getColTotal(cid);
	}

	@Override
	// 查询当前合集的应用 
	public List<Map<String, Object>> getColList(int page, String cid) {
		return mcrAppColDao.getColList(page, cid);
	}

	@Override
	// 条件查询应用数量
	public Integer getTotalCondition(String cid, String name) {
		return mcrAppColDao.getTotalCondition(cid, name);
	}

	@Override
	// 条件查询应用
	public List<Map<String, Object>> getListCondition(int page, String cid,
			String name) {
		return mcrAppColDao.getListCondition(page, cid, name);
	}

	@Override
	// 条件查询当前合集的应用数量
	public Integer getColTotalCondition(String cid, String name) {
		return mcrAppColDao.getColTotalCondition(cid, name);
	}

	@Override
	// 条件查询当前合集的应用
	public List<Map<String, Object>> getColListCondition(int page, String cid,
			String name) {
		return mcrAppColDao.getColListCondition(page, cid, name);
	}

	@Override
	// 对应用进行合集分类
	public void handleCol(String cid, String[] aids, String[] removeAids,
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
		if(null!=aids && aids.length>0){
			for (int i = 0; i < aids.length; i++) {
				// 查询游戏是否已经存在于当前合集中
				int count = mcrAppColDao.isExist(cid,aids[i]);
				if(count==0){
					// 如果不存在，则添加到当前合集
					mcrAppColDao.handleCol(cid,aids[i],orders[i]);
				}else{
					// 如果存在，则更新顺序
					mcrAppColDao.updateOrder(cid,aids[i],orders[i]);
				}
			}
		}
		// 移出应用
		if(null!=removeAids && removeAids.length>0){
			for (String aid : removeAids) {
				if(StrUtils.isEmpty(aid)){
					continue;
				}
				mcrAppColDao.removeApp(cid,aid);
			}
		}
	}

}
