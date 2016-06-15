package net.jeeshop.services.manage.dataCenter.dao;

import java.util.List;

import net.jeeshop.core.DaoManager;
import net.jeeshop.services.manage.dataCenter.bean.DataCenter;
import net.jeeshop.services.manage.dataCenter.bean.DataFile;

public interface DataCenterDao extends DaoManager<DataCenter> {
	// 保存资源详情
		public void insertDetail(DataFile dataFile);
    //根据id删除资源详情
		public int delDetailById(DataFile dd);
    //根据id查询资源详情		
		public List<DataFile> selectDataDetail(DataFile dd);
	//更新资源中心和资源详情	
		public void updateDetail(DataFile dd);
	//删除资源文件中id这条数据	
		public void deleteDataCenter(String id);
	//删除资源文件中sid为id的所有数据	
		public void deleteDataFile(String id);

}
