package com.sys.easybuy.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.sys.easybuy.service.EasyBuyService;
import com.sys.util.StrUtils;

@SuppressWarnings("deprecation")
public class EasyBuyServiceImpl extends SqlMapClientDaoSupport implements EasyBuyService {
	
	private static final Logger LOG = Logger.getLogger(EasyBuyServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	// 广告位信息
	public List<Map<String, Object>> advertInfo(String user) {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("UCODE", user);
			return this.getSqlMapClientTemplate().queryForList("easyBuy.advertInfo",map);
		} catch (DataAccessException e) {
			LOG.error("EasyBuyServiceImpl.advertInfo failed, e : ",e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	// 分类列表
	public List<Map<String, Object>> category(String user) {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("UCODE", user);
			return this.getSqlMapClientTemplate().queryForList("easyBuy.category",map);
		} catch (DataAccessException e) {
			LOG.error("EasyBuyServiceImpl.category failed, e : ",e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	// 可维护分类详情（首页专题详情）
	public List<Map<String, Object>> customableCategory(String user) {
		try {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			String cateNum = "";// 前N个分类
			String proNum = "";// 前N个产品
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("UCODE", user);
			// 默认取前2个分类
			map.put("CATENUM", StrUtils.isEmpty(cateNum)?"2":cateNum);
			// 查询前2个分类的id
			List<Map<String, Object>> categorys = this.getSqlMapClientTemplate().queryForList("easyBuy.customableCate",map);
			
			// 查询每个分类下的前4个产品
			for (Map<String, Object> category : categorys) {
				Map<String, Object> reqMap = new HashMap<String, Object>();
				String cname = category.get("C_NAME").toString();
				String cid = category.get("C_ID").toString();
				map.clear();
				map.put("CID", cid);
				// 默认取前4个产品
				map.put("PRONUM", StrUtils.isEmpty(proNum)?"4":proNum);
				List<Map<String, Object>> products = this.getSqlMapClientTemplate().queryForList("easyBuy.customableCatePro",map);
				reqMap.put("cid", cid);
				reqMap.put("cname", cname);
				reqMap.put("products", products);
				list.add(reqMap);
			}
			return list;
			
			
		} catch (DataAccessException e) {
			LOG.error("EasyBuyServiceImpl.customableCategory failed, e : ",e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	// "附近门店"
	public List<Map<String, Object>> storesNearby(String user) {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("UCODE", user);
			return this.getSqlMapClientTemplate().queryForList("easyBuy.storesNearby",map);
		} catch (DataAccessException e) {
			LOG.error("EasyBuyServiceImpl.storesNearby failed, e : ",e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	// 分类详情
	public List<Map<String, Object>> categoryDetail(String user, String cid, String page, String flag) {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("UCODE", user);
			map.put("CID", cid);
			map.put("PAGE", page);
			if ("0".equals(flag)){
				return this.getSqlMapClientTemplate().queryForList("easyBuy.categoryDetail",map);
			}else if ("1".equals(flag)){
				return this.getSqlMapClientTemplate().queryForList("easyBuy.topicDetail",map);
			}
		} catch (DataAccessException e) {
			LOG.error("EasyBuyServiceImpl.categoryDetail failed, e : ",e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	// 产品详情
	public List<Map<String, Object>> productDetail(String id) {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ID", id);
			return this.getSqlMapClientTemplate().queryForList("easyBuy.productDetail",map);
		} catch (DataAccessException e) {
			LOG.error("EasyBuyServiceImpl.productDetail failed, e : ",e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	// 简明产品搜索
	public List<Map<String, Object>> simpleSearch(String user, String content) {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("UCODE", user);
			map.put("CONTENT", "%" + content + "%");
			return this.getSqlMapClientTemplate().queryForList("easyBuy.simpleSearch",map);
		} catch (DataAccessException e) {
			LOG.error("EasyBuyServiceImpl.simpleSearch failed, e : ",e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	// 产品搜索
	public List<Map<String, Object>> search(String user, String content) {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("UCODE", user);
			map.put("CONTENT", "%" + content + "%");
			return this.getSqlMapClientTemplate().queryForList("easyBuy.search",map);
		} catch (DataAccessException e) {
			LOG.error("EasyBuyServiceImpl.search failed, e : ",e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	// "猜你喜欢"
	public List<Map<String, Object>> guessULike(String pid) {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("PID", pid);
			// 产品所在分类的id
//			String cid = (String) this.getSqlMapClientTemplate().queryForObject("easyBuy.cidOfPid", map);
//			map.put("CID", cid);
			// 所在分类下的产品总数
			/*Integer count = (Integer) this.getSqlMapClientTemplate().queryForObject("easyBuy.totalInCate", map);
			List<Integer> list = new ArrayList<Integer>();
			Random ran = new Random();
			int rownum = 1;
			while (list.size() < 4) {
				Integer ranInt = ran.nextInt(count);
				if(!list.contains(ranInt)){
					list.add(ranInt);
					map.put("RANDOMROWNUM"+rownum, ranInt);
					rownum += 1;
				}
			}*/
			// 随机取四个产品
			return this.getSqlMapClientTemplate().queryForList("easyBuy.guessULike",map);
		} catch (DataAccessException e) {
			LOG.error("EasyBuyServiceImpl.search failed, e : ",e);
		}
		return null;
	}

}
