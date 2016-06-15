package com.org.mokey.basedata.sysinfo.service.impl;

import java.util.List;
import java.util.Map;

import com.org.mokey.basedata.sysinfo.dao.McrAppCateDao;
import com.org.mokey.basedata.sysinfo.service.McrAppCateService;
import com.org.mokey.util.StrUtils;

/**
 * 微用帮：应用分类
 */
public class McrAppCateServiceImpl implements McrAppCateService{
	
	private McrAppCateDao mcrAppCateDao;

	public void setMcrAppCateDao(McrAppCateDao mcrAppCateDao) {
		this.mcrAppCateDao = mcrAppCateDao;
	}

	public McrAppCateDao getMcrAppCateDao() {
		return mcrAppCateDao;
	}

	@Override
	// 新增分类
	public void mcrAppCateAdd(String id, String name, String logo,
				String order, String modifier) {
		mcrAppCateDao.mcrAppCateAdd(id, name, logo, order, modifier);
	}

	@Override
	// 删除分类
	public void mcrAppCateDel(String cid) {
		mcrAppCateDao.mcrAppCateDel(cid);
	}

	@Override
	// 更新分类
	public void update(String cid, String cname, String modifier, String logo, String order) {
		mcrAppCateDao.update(cid, cname, modifier, logo, order);
	}

	@Override
	// 查询所有应用
	public Integer getAppTotal(String cid) {
		return mcrAppCateDao.getAppTotal(cid);
	}

	@Override
	// 查询所有应用
	public List<Map<String, Object>> getAppList(int page, String cid) {
		return mcrAppCateDao.getAppList(page, cid);
	}

	@Override
	// 查询当前分类的应用总数 
	public Integer getCateTotal(String cid) {
		return mcrAppCateDao.getCateTotal(cid);
	}

	@Override
	// 查询当前分类的应用 
	public List<Map<String, Object>> getCateList(int page, String cid) {
		return mcrAppCateDao.getCateList(page, cid);
	}

	@Override
	// 条件查询应用数量
	public Integer getTotalCondition(String cid, String name, String appCate) {
		return mcrAppCateDao.getTotalCondition(cid, name, appCate);
	}

	@Override
	// 条件查询应用
	public List<Map<String, Object>> getListCondition(int page, String cid,
			String name, String appCate) {
		return mcrAppCateDao.getListCondition(page, cid, name, appCate);
	}

	@Override
	// 条件查询当前分类的应用数量
	public Integer getCateTotalCondition(String cid, String name) {
		return mcrAppCateDao.getCateTotalCondition(cid, name);
	}

	@Override
	// 条件查询当前分类的应用
	public List<Map<String, Object>> getCateListCondition(int page, String cid,
			String name) {
		return mcrAppCateDao.getCateListCondition(page, cid, name);
	}

	@Override
	// 对应用进行分类分类
	public void handleCate(String cid, String[] aids, String[] removeAids,
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
				// 查询游戏是否已经存在于当前分类中
				int count = mcrAppCateDao.isExist(cid,aids[i]);
				if(count==0){
					// 如果不存在，则添加到当前分类
					mcrAppCateDao.handleCate(cid,aids[i],orders[i]);
				}else{
					// 如果存在，则更新顺序
					mcrAppCateDao.updateOrder(cid,aids[i],orders[i]);
				}
			}
		}
		// 移出游戏
		if(null!=removeAids && removeAids.length>0){
			for (String aid : removeAids) {
				if(StrUtils.isEmpty(aid)){
					continue;
				}
				mcrAppCateDao.removeApp(cid,aid);
			}
		}
	}

	@Override
	// 查询所有分类总数
	public Integer getAllCateTotal() {
		return mcrAppCateDao.getAllCateTotal();
	}

	@Override
	// 分页查询所有分类
	public List<Map<String, Object>> getAllCateList(int page) {
		return mcrAppCateDao.getAllCateList(page);
	}

	@Override
	// 分页查询所有分类（可有查询条件）
	public List<Map<String, Object>> getAllCateList(int page, String name) {
		return mcrAppCateDao.getAllCateList(page, name);
	}

	@Override
	// 查询所有分类名称和id
	public List<Map<String, Object>> cateList() {
		return mcrAppCateDao.cateList();
	}

}
