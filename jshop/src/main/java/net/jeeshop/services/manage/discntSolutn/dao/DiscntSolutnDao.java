package net.jeeshop.services.manage.discntSolutn.dao;

import java.util.List;

import net.jeeshop.core.DaoManager;
import net.jeeshop.services.manage.discntSolutn.bean.DiscntDetail;
import net.jeeshop.services.manage.discntSolutn.bean.DiscntSolutn;

public interface DiscntSolutnDao extends DaoManager<DiscntSolutn>{
	
	// 保存方案详情
	public void insertDetail(DiscntDetail detail);

	// 根据折扣方案id查询方案详情
	public List<DiscntDetail> selectDiscntDetail(DiscntDetail dd);

	// 更新方案详情
	public void updateDetail(DiscntDetail detail);

	// 根据id删除方案详情
	public int delDetailById(DiscntDetail dd);
	
	/**
	 * 查询用户当前订单对应的折扣率
	 * @param e
	 * @return
	 */
	public DiscntDetail selectAccountRate(DiscntDetail e);
	
	

}
