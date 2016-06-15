package net.jeeshop.services.manage.systemDict;

import java.util.List;

import net.jeeshop.core.Services;
import net.jeeshop.services.manage.systemDict.bean.SystemDict;

public interface SystemDictService extends Services<SystemDict>{

	// 根据父id查询大类类别
	public List<SystemDict> selectByPid(SystemDict e);

	// 更新子类数据
	public void updateSub(SystemDict e);

	// 添加子类数据
	public void addSub(SystemDict e);

	// 加载所有分类数据，分别存入缓存
	public void loadData();

}
