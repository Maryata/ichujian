package net.jeeshop.services.manage.dataCenter;

import java.util.List;

import net.jeeshop.core.Services;
import net.jeeshop.core.system.bean.User;
import net.jeeshop.services.manage.dataCenter.bean.DataCenter;
import net.jeeshop.services.manage.dataCenter.bean.DataFile;

public interface DataCenterService extends Services<DataCenter> {
	/**
	 * 保存资源详情
	 * @param ds 资源
	 * @param dd 资源详情
	 */
	public void inserDetail(DataCenter ds, DataFile dd,User u);
	/**
	 * 根据id删除资源详情
	 * @param dd 资源详情id
	 * @return
	 */
	public int delDetailById(DataFile dd);
	/**
	 * 查询资源详情后
	 * @param dd
	 * @return list
	 */
	public List<DataFile> selectDataDetail(DataFile dd);
	/**
	 * 更新资源详情
	 * @param ds
	 * @param dd
	 */
	public void updateDetail(DataCenter ds, DataFile dd,User u);
	/**
	 * 删除指定id的资源文件
	 * @param id
	 * @return
	 */
	public boolean deleteId(String id);
}
