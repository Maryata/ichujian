package net.jeeshop.services.front.dataCenter.dao;

import java.util.List;

import net.jeeshop.core.DaoManager;
import net.jeeshop.services.front.dataCenter.bean.DataApply;
import net.jeeshop.services.front.dataCenter.bean.DataCenter;
import net.jeeshop.services.front.dataCenter.bean.DataFile;

public interface DataCenterDao extends DaoManager<DataCenter> {
	
	/**
	 * 查询资料中心组对用的文件列表
	 * @param df
	 * @return
	 */
	public List<DataFile> selectDataFiles(DataFile df);
	
	/**
	 * 提交申请
	 * @param e
	 * @return
	 */
	public int insertDataApply(DataApply e);

}