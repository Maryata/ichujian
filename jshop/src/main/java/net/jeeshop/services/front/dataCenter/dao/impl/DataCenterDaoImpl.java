package net.jeeshop.services.front.dataCenter.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.front.dataCenter.bean.DataApply;
import net.jeeshop.services.front.dataCenter.bean.DataCenter;
import net.jeeshop.services.front.dataCenter.bean.DataFile;
import net.jeeshop.services.front.dataCenter.dao.DataCenterDao;

import org.springframework.stereotype.Repository;

@Repository("dataCenterDaoFront")
public class DataCenterDaoImpl implements DataCenterDao {

    @Resource
	private BaseDao dao;

	public void setDao(BaseDao dao) {
		this.dao = dao;
	}
	
	@Override
	public int insert(DataCenter e) {
		return 0;
	}

	@Override
	public int delete(DataCenter e) {
		return 0;
	}

	@Override
	public int update(DataCenter e) {
		return 0;
	}

	@Override
	public DataCenter selectOne(DataCenter e) {
		return (DataCenter) dao.selectOne("front.dataCenter.selectOne", e);
	}

	@Override
	public PagerModel selectPageList(DataCenter e) {
		return dao.selectPageList("front.dataCenter.selectPageList", "front.dataCenter.selectPageCount", e);
	}

	@Override
	public List<DataCenter> selectList(DataCenter e) {
		return null;
	}

	@Override
	public int deleteById(int id) {
		return 0;
	}

	@Override
	public DataCenter selectById(String id) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DataFile> selectDataFiles(DataFile df) {
		return dao.selectList("front.dataCenter.selectDataFiles", df);
	}

	@Override
	public int insertDataApply(DataApply e) {
		return dao.insert("front.dataCenter.insertDataApply", e);
	}

}
