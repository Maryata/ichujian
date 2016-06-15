package com.org.mokey.basedata.sysinfo.service.impl;

import java.util.List;
import java.util.Map;

import com.org.mokey.basedata.sysinfo.dao.McrAppIndexDao;
import com.org.mokey.basedata.sysinfo.service.McrAppIndexService;
import com.org.mokey.util.StrUtils;

/**
 * 微用帮：首页排版
 */
public class McrAppIndexServiceImpl implements McrAppIndexService{
	
	private McrAppIndexDao mcrAppIndexDao;

	public void setMcrAppIndexDao(McrAppIndexDao mcrAppIndexDao) {
		this.mcrAppIndexDao = mcrAppIndexDao;
	}

	public McrAppIndexDao getMcrAppIndexDao() {
		return mcrAppIndexDao;
	}

	@Override
	// 查询当前首页显示的合集
	public List<Map<String,Object>> currCol() {
		return mcrAppIndexDao.currCol();
	}

	@Override
	// 查询首页显示的分类
	public Map<String, Object> indexColList(String name, int start, int limit) {
		return mcrAppIndexDao.indexColList(name, start, limit);
	}

	@Override
	// 首页排版
	public void handle(String col, String colCnt, String id, String removeId, String order,
			String number) {
		String[] ids = id.split(",");
		String[] removeIds = removeId.split(",");
		String[] numbers = number.split(",");
		String[] orders = order.split(",");
		// 替换所有的“n”为空
		if(StrUtils.isNotEmpty(numbers)){
			for (int i = 0; i < numbers.length; i++) {
				if("n".equals(numbers[i])){
					numbers[i] = "";
				}
			}
		}
		for (int i = 0; i < orders.length; i++) {
			if("n".equals(orders[i])){
				orders[i] = "";
			}
		}
		// 查询合集是否已经存在
		Integer colCount = mcrAppIndexDao.isExist("0",col);// 0表示合集
		if(colCount>0){
			// 更新合集
			mcrAppIndexDao.update("0",col,colCnt,"0");
		}else{
			// 新增合集
			mcrAppIndexDao.add("0",col,colCnt,"0");
		}
		// 添加分类
		if(null!=ids && ids.length>0){
			for (int i = 0; i < ids.length; i++) {
				// 查询分类是否已经存在
				int cateCount = mcrAppIndexDao.isExist("1",ids[i]);// 1表示分类
				if(cateCount==0){
					// 如果不存在，新增分类
					mcrAppIndexDao.add("1",ids[i],numbers[i],orders[i]);
				}else{
					// 如果存在，更新分类
					mcrAppIndexDao.update("1",ids[i],numbers[i],orders[i]);
				}
			}
		}
		// 移除分类
		if(null!=removeIds && removeIds.length>0){
			for (String cid : removeIds) {
				if(StrUtils.isEmpty(cid)){
					continue;
				}
				mcrAppIndexDao.remove("1",cid);
			}
		}
	}

	@Override
	// 分页查询首页中未显示的分类
	public Map<String, Object> cateList(int page, String name) {
		return mcrAppIndexDao.cateList(page, name);
	}

}
