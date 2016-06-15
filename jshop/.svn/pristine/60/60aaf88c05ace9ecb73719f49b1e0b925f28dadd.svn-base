package net.jeeshop.services.manage.dataCenter.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.jeeshop.core.ServersManager;
import net.jeeshop.core.system.bean.User;
import net.jeeshop.services.manage.dataCenter.DataCenterService;
import net.jeeshop.services.manage.dataCenter.bean.DataCenter;
import net.jeeshop.services.manage.dataCenter.bean.DataFile;
import net.jeeshop.services.manage.dataCenter.dao.DataCenterDao;
import net.jeeshop.services.manage.discntSolutn.bean.DiscntDetail;

@Service("dataCenterServiceManage")
public class DataCenterServiceImpl extends
		ServersManager<DataCenter, DataCenterDao> implements DataCenterService {

	@Resource(name = "dataCenterDaoManage")
	@Override
	public void setDao(DataCenterDao dataCenterDao) {
		this.dao = dataCenterDao;
	}

	/**
	 * 保存资源详情
	 */
	@Override
	public void inserDetail(DataCenter ds, DataFile dd, User u) {

		// 获取资源id
		String cid = ds.getId();
		// 获取资源详情数据
		String[] fnames = dd.getFnames();
		String[] furls = dd.getFurls();
		String[] fbigurls = dd.getFbigurls();
		String[] playurls=dd.getPlayurls();
		// String[] fsize=dd.getFsizes();
		if (null != fnames && null != furls && null != fbigurls &&null !=playurls
				&& fnames.length > 0 && furls.length > 0 && fbigurls.length > 0 && playurls.length>0) {
			// 3个数组的长度相同，遍历任意数组
			for (int i = 0; i < fbigurls.length; i++) {
				DataFile dataFile = new DataFile();
				dataFile.setcId(cid);
				dataFile.setFurl(furls[i]);
				dataFile.setFname(fnames[i]);
				dataFile.setFbigurl(fbigurls[i]);
				// dataFile.setFbigurl(fsize[i]);
				dataFile.setUserId(u.getId());
				dataFile.setPlayurl(playurls[i]);
				dao.insertDetail(dataFile);
			}
		}

	}

	@Override
	public int delDetailById(DataFile dd) {
		// TODO Auto-generated method stub
		return dao.delDetailById(dd);
	}

	/**
	 * 根据id查询资源详情
	 */
	@Override
	public List<DataFile> selectDataDetail(DataFile dd) {
		// TODO Auto-generated method stub
		return dao.selectDataDetail(dd);
	}

	/**
	 * 更新资源中心和资源详情
	 */
	@Override
	public void updateDetail(DataCenter ds, DataFile dd, User u) {
		// TODO Auto-generated method stub
		// 获取资源详情数据

		String[] ids = dd.getIds();
		String[] fnames = dd.getFnames();
		String[] furls = dd.getFurls();
		String[] fbigurls = dd.getFbigurls();
		String[] playurls=dd.getPlayurls();
		// String[] fsize=dd.getFsizes();
		if (null != fnames && null != furls && null != fbigurls &&null !=playurls
				&& fnames.length > 0 && furls.length > 0 && fbigurls.length > 0  && playurls.length>0) {
			// 3个数组的长度相同，遍历任意数组
			for (int i = 0; i < fbigurls.length; i++) {
				DataFile dataFile = new DataFile();
				dataFile.setFurl(furls[i]);
				dataFile.setFname(fnames[i]);
				dataFile.setFbigurl(fbigurls[i]);
				dataFile.setPlayurl(playurls[i]);
				dataFile.setUserId(u.getId());
				// dataFile.setFbigurl(fsize[i]);
				// dao.insertDetail(dataFile);
				String id = ids[i];
				if ("-1".equals(id)) {
					// 如果id值为-1，执行添加
					dataFile.setcId(ds.getId());
					dao.insertDetail(dataFile);
				} else {
					// 如果不为-1，执行更新
					dataFile.setId(ids[i]);
					dao.updateDetail(dataFile);
				}
			}
		}

	}
    /**
     * 删除指定id的资源文件以及其下的资源详情
     */
	@Override
	public boolean deleteId(String id) {
		//删除datacenter中的id这条数据
		dao.deleteDataCenter(id);
		//删除datafile 中cid为id的所有数据
		dao.deleteDataFile(id);
		return true;
	}

}
