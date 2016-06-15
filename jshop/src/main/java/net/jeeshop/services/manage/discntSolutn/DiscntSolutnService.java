package net.jeeshop.services.manage.discntSolutn;

import java.util.List;

import net.jeeshop.core.Services;
import net.jeeshop.services.manage.discntSolutn.bean.DiscntDetail;
import net.jeeshop.services.manage.discntSolutn.bean.DiscntSolutn;

public interface DiscntSolutnService extends Services<DiscntSolutn>{

	/**
	 * 保存方案详情
	 * @param ds 方案
	 * @param dd 方案详情
	 */
	public void inserDetail(DiscntSolutn ds, DiscntDetail dd);

	/**
	 * 根据折扣方案id查询方案详情
	 * @param dd 折扣方案
	 * @return 方案详情
	 */
	public List<DiscntDetail> selectDiscntDetail(DiscntDetail dd);

	/**
	 * 更新方案详情
	 * @param ds 
	 * @param dd
	 */
	public void updateDetail(DiscntSolutn ds, DiscntDetail dd);

	/**
	 * 根据id删除方案详情
	 * @param dd 方案详情id
	 * @return
	 */
	public int delDetailById(DiscntDetail dd);
	
	/**
	 * 查询用户当前订单对应的折扣率
	 * @param e
	 * @return
	 */
	public String selectAccountRate(DiscntDetail e);

}
