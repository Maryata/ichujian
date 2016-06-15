package net.jeeshop.services.manage.discntSolutn.impl;

import java.util.List;

import javax.annotation.Resource;

import net.jeeshop.core.ServersManager;
import net.jeeshop.core.util.StrUtils;
import net.jeeshop.services.manage.discntSolutn.DiscntSolutnService;
import net.jeeshop.services.manage.discntSolutn.bean.DiscntDetail;
import net.jeeshop.services.manage.discntSolutn.bean.DiscntSolutn;
import net.jeeshop.services.manage.discntSolutn.dao.DiscntSolutnDao;

import org.springframework.stereotype.Service;

@Service
public class DiscntSolutnServiceImpl extends ServersManager<DiscntSolutn, DiscntSolutnDao> implements DiscntSolutnService {

	@Resource(name = "discntSolutnDao")
    @Override
    public void setDao(DiscntSolutnDao dao) {
        this.dao = dao;
    }

	@Override
	// 保存方案详情
	public void inserDetail(DiscntSolutn ds, DiscntDetail dd) {
		// 获取方案id
		String sid = ds.getId();
		// 获取方案详情数据
		String[] minVals = dd.getMinVals();
		String[] maxVals = dd.getMaxVals();
		String[] rates = dd.getRates();
		if (null != minVals && null != maxVals && null != rates
				&& minVals.length > 0 && maxVals.length > 0 && rates.length > 0) {
			// 3个数组的长度相同，遍历任意数组
			for (int i = 0; i < rates.length; i++) {
				DiscntDetail detail = new DiscntDetail();
				detail.setSid(sid);
				detail.setMinVal(minVals[i]);
				detail.setMaxVal(maxVals[i]);
				detail.setRate(rates[i]);
				dao.insertDetail(detail);
			}
		}
	}

	@Override
	// 根据折扣方案id查询方案详情
	public List<DiscntDetail> selectDiscntDetail(DiscntDetail ds) {
		return dao.selectDiscntDetail(ds);
	}

	@Override
	// 更新方案详情
	public void updateDetail(DiscntSolutn ds, DiscntDetail dd) {
		// 获取方案详情数据
		String[] ids = dd.getIds();
		String[] minVals = dd.getMinVals();
		String[] maxVals = dd.getMaxVals();
		String[] rates = dd.getRates();
		if (null != minVals && null != maxVals && null != rates
				&& minVals.length > 0 && maxVals.length > 0 && rates.length > 0) {
			// 后3个数组的长度相同，遍历任意数组
			for (int i = 0; i < rates.length; i++) {
				DiscntDetail detail = new DiscntDetail();
				// 设置最小值、最大值、折扣点
				detail.setMinVal(minVals[i]);
				detail.setMaxVal(maxVals[i]);
				detail.setRate(rates[i]);
				// 获取id
				String id = ids[i];
				if("-1".equals(id)){
					// 如果id值为-1，执行添加
					detail.setSid(ds.getId());
					dao.insertDetail(detail);
				}else{
					// 如果不为-1，执行更新
					detail.setId(ids[i]);
					dao.updateDetail(detail);
				}
			}
		}
	}

	@Override
	// 根据id删除方案详情
	public int delDetailById(DiscntDetail dd) {
		return dao.delDetailById(dd);
	}

	@Override
	public String selectAccountRate(DiscntDetail e) {
		DiscntDetail dd = dao.selectAccountRate(e);
		if(StrUtils.isNotEmpty(dd) && StrUtils.isNotEmpty(dd.getRate()) && !"0".equals(dd.getRate())){
			return dd.getRate();
		}
		return "1";
	}

	

}
