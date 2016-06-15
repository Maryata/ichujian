package net.jeeshop.services.manage.systemDict.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.core.front.SystemManager;
import net.jeeshop.core.util.StrUtils;
import net.jeeshop.services.manage.systemDict.SystemDictService;
import net.jeeshop.services.manage.systemDict.bean.SystemDict;
import net.jeeshop.services.manage.systemDict.dao.SystemDictDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemDictServiceImpl implements SystemDictService {

	private static final Logger logger = LoggerFactory.getLogger(SystemDictServiceImpl.class);
	
	@Autowired
	private SystemDictDao systemDictDao;
	
	@Autowired
	private SystemManager systemManager;

	@Override
	public int insert(SystemDict e) {
		return systemDictDao.insert(e);
	}

	@Override
	// 根据id删除字典分类数据
	public int delete(SystemDict e) {
		return systemDictDao.delete(e);
	}

	@Override
	public int deletes(String[] ids) {
		return 0;
	}

	@Override
	public int update(SystemDict e) {
		return systemDictDao.update(e);
	}

	@Override
	public SystemDict selectOne(SystemDict e) {
		return systemDictDao.selectOne(e);
	}

	@Override
	public SystemDict selectById(String id) {
		return null;
	}

	@Override
	public PagerModel selectPageList(SystemDict e) {
		return systemDictDao.selectPageList(e);
	}

	@Override
	public List<SystemDict> selectList(SystemDict e) {
		return systemDictDao.selectList(e);
	}

	@Override
	// 根据父id查询类别
	public List<SystemDict> selectByPid(SystemDict e) {
		return systemDictDao.selectByPid(e);
	}

	@Override
	// 更新子类数据
	public void updateSub(SystemDict e) {
		try {
			// 获取父类id
			String pid = e.getId();
			// 获取分类名称和值的数组
			String[] subDdlKeys = e.getSubDdlKey();
			String[] subDdlVals = e.getSubDdlVal();
			String[] subExtVals = e.getSubExtVal();
			
			String[] ids = e.getIds();
			String[] sorts = e.getSorts();
			if(null!=subDdlKeys && subDdlKeys.length>0 && subDdlVals!=null && subDdlVals.length>0){
				for (int i = 0; i < subDdlKeys.length; i++) {
					SystemDict dict = new SystemDict();
					dict.setPid(pid);
					dict.setDdlKey(subDdlKeys[i]);
					dict.setDdlVal(subDdlVals[i]);
					dict.setExtVal(subExtVals[i]);
					dict.setSort(StrUtils.isNotEmpty(sorts[i]) ? Integer.valueOf(sorts[i]) : 1);
					String id = ids[i];
					if("-1".equals(id)){
						// 如果id为-1，则添加
						systemDictDao.insert(dict);
					}else{
						// 否则更新
						dict.setId(id);
						systemDictDao.update(dict);
					}
				}
			}
		} catch (Exception e1) {
			logger.error("SystemDictServiceImpl.updateSub failed, e : " + e1);
		}
	}

	@Override
	// 添加子类数据
	public void addSub(SystemDict e) {
		String pid = e.getId();
		// 获取分类名称和值的数组
		String[] subDdlKeys = e.getSubDdlKey();
		String[] subDdlVals = e.getSubDdlVal();
		String[] sorts = e.getSorts();
		String[] subExtVals = e.getSubExtVal();
		if(null!=subDdlKeys && subDdlKeys.length>0 && subDdlVals!=null && subDdlVals.length>0){
			for (int i = 0; i < subDdlKeys.length; i++) {
				SystemDict dict = new SystemDict();
				dict.setPid(pid);
				dict.setDdlKey(subDdlKeys[i]);
				dict.setDdlVal(subDdlVals[i]);
				if(StrUtils.isNotEmpty(subExtVals) && subExtVals.length== subDdlKeys.length)
					dict.setExtVal(subExtVals[i]);
				dict.setSort(StrUtils.isNotEmpty(sorts[i]) ? Integer.valueOf(sorts[i]) : 1);
				systemDictDao.insert(dict);
			}
		}
	}

	// 加载所有分类信息
	public void loadData() {
		SystemDict sd = new SystemDict();
		// 查询所有大类
		List<SystemDict> data = systemDictDao.selectList(sd);
		if(StrUtils.isNotEmpty(data)){
			for (SystemDict dict : data) {
				// 以大类的id作为键
				sd.setPid(dict.getId());
				// 根据父id查询子类
				List<SystemDict> sub = systemDictDao.selectByPid(sd);
				if(StrUtils.isNotEmpty(sub)){//有序存储
					Map<String,String> map = new LinkedHashMap<String,String>();
					for (SystemDict subDict : sub) {
						if(!map.containsKey(subDict.getDdlKey()))//过滤重复key;
						map.put(subDict.getDdlKey(), subDict.getDdlVal());
					}
					//logger.debug(" data key:{} data:{}",dict.getDdlKey(),map);
					// 存入缓存，缓存的Key是ddlKey，值是子类Map
					systemManager.setSystemDict(dict.getDdlKey(), map);
					
					//特殊缓存处理;扩展;
					if("account_purchaseType".equals(dict.getDdlKey())){
						Map<String,String> extmap = new LinkedHashMap<String,String>();
						for (SystemDict subDict : sub) {
							if(!extmap.containsKey(subDict.getDdlKey()))//过滤重复key;
								extmap.put(subDict.getDdlKey(), subDict.getExtVal());
						}
						systemManager.setSystemDict_ext(dict.getDdlKey(), extmap);
					}
				}
			}
		}
	}
	
}
