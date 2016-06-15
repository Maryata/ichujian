package net.jeeshop.services.manage.dataCenter.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.manage.dataCenter.bean.DataCenter;
import net.jeeshop.services.manage.dataCenter.bean.DataFile;
import net.jeeshop.services.manage.dataCenter.dao.DataCenterDao;
import net.jeeshop.services.manage.discntSolutn.bean.DiscntSolutn;

@Repository("dataCenterDaoManage")
public class DataCenterDaoImpl implements DataCenterDao {

	@Resource
	private BaseDao dao;

	public void setDao(BaseDao dao) {
		this.dao = dao;
	}

	@Override
	public int insert(DataCenter e) {
		return dao.insert("manage.dataCenter.insert", e);
	}

	@Override
	public int delete(DataCenter e) {
		return 0;
	}

	@Override
	public int update(DataCenter e) {
		return dao.update("manage.dataCenter.update", e);
	}

	@Override
	public DataCenter selectOne(DataCenter e) {
		return (DataCenter) dao.selectOne("manage.dataCenter.selectOne", e);
	}

	@Override
	public PagerModel selectPageList(DataCenter e) {
		return dao.selectPageList("manage.dataCenter.selectPageList",
				"manage.dataCenter.selectPageCount", e);//
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

	// 添加资源详情
	@Override
	public void insertDetail(DataFile dataFile) {
		// TODO Auto-generated method stub
		dao.insert("manage.dataCenter.insertDetail", dataFile);
	}

	// 根据id删除资源详情
	@Override
	public int delDetailById(DataFile dd) {
		// TODO Auto-generated method stub
		return dao.delete("manage.dataCenter.delDetailById", dd);
	}

	// 根据id查询资源详情
	@SuppressWarnings("unchecked")
	@Override
	public List<DataFile> selectDataDetail(DataFile dd) {
		// TODO Auto-generated method stub
		return dao.selectList("manage.dataCenter.selectDataDetail", dd);
	}

	@Override
	public void updateDetail(DataFile dd) {
		dao.update("manage.dataCenter.updateDetail", dd);

	}

	@Override
	public void deleteDataCenter(String id) {
		// TODO Auto-generated method stub
		dao.delete("manage.dataCenter.deleteId", id);
	}

	@Override
	public void deleteDataFile(String cId) {
		// TODO Auto-generated method stub
		dao.delete("manage.dataCenter.deletecId", cId);
	}

}
