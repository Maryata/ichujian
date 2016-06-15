package com.org.mokey.basedata.sysinfo.service.impl;

import java.util.Map;

import com.org.mokey.basedata.sysinfo.dao.H5GameBoutiqueIndexDao;
import com.org.mokey.basedata.sysinfo.service.H5GameBoutiqueIndexService;
import com.org.mokey.util.StrUtils;

/**
 * 游戏帮：H5精品游戏首页排版
 */
public class H5GameBoutiqueIndexServiceImpl implements H5GameBoutiqueIndexService{
	
	private H5GameBoutiqueIndexDao h5GameBoutiqueIndexDao;

	public void setH5GameBoutiqueIndexDao(H5GameBoutiqueIndexDao h5GameBoutiqueIndexDao) {
		this.h5GameBoutiqueIndexDao = h5GameBoutiqueIndexDao;
	}

	public H5GameBoutiqueIndexDao getH5GameBoutiqueIndexDao() {
		return h5GameBoutiqueIndexDao;
	}

	@Override
	// 查询首页显示的分类
	public Map<String, Object> indexCateList(String name, int start, int limit) {
		return h5GameBoutiqueIndexDao.indexCateList(name, start, limit);
	}

	@Override
	// 首页排版
	public void handle(String id, String removeId, String order, String number) {
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
		// 添加分类
		if(null!=ids && ids.length>0){
			for (int i = 0; i < ids.length; i++) {
				// 查询分类是否已经存在
				int cateCount = h5GameBoutiqueIndexDao.isExist(ids[i]);// 1表示分类
				if(cateCount==0){
					// 如果不存在，新增分类
					h5GameBoutiqueIndexDao.add(ids[i],numbers[i],orders[i]);
				}else{
					// 如果存在，更新分类
					h5GameBoutiqueIndexDao.update(ids[i],numbers[i],orders[i]);
				}
			}
		}
		// 移除分类
		if(null!=removeIds && removeIds.length>0){
			for (String cid : removeIds) {
				if(StrUtils.isEmpty(cid)){
					continue;
				}
				h5GameBoutiqueIndexDao.remove(cid);
			}
		}
	}

	@Override
	// 分页查询首页中未显示的分类
	public Map<String, Object> cateList(int page, String name) {
		return h5GameBoutiqueIndexDao.cateList(page, name);
	}

}
