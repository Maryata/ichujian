package com.sys.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.net.aso.i;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sys.util.ApDateTime;

public class ClassifiedServiceImpl extends SqlMapClientDaoSupport implements ClassifiedService {

	@Override
	public List getHotBrand(String industryid, String cityid, String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("industryid",industryid );
		map.put("cityid",cityid );
		map.put("type",type );
		
		List list=getSqlMapClientTemplate().queryForList("class.ClassifiedHotBrand",map);
		return list;
	}

	@Override
	public List getPropertyType() {
		// TODO Auto-generated method stub
		List list = getSqlMapClientTemplate().queryForList("class.ClassifiedPropertyType");
		return list;
	}

	public List getBaseInfo(String industryid, String cityid, String propertyid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("industryid",industryid );
		map.put("cityid",cityid );
		map.put("propertyid",propertyid );
		
		List list = getSqlMapClientTemplate().queryForList("class.ClassifiedBaseInfo",map);
		return list;
	}

	@Override
	public List ClassiFiedDetail(String industyid,String pageindex) {
		// TODO Auto-generated method stub
		Map map=new HashMap();
		List list=new ArrayList();
		map.put("industyid", industyid);
		int page=Integer.parseInt(pageindex);
		page=(page-1)*20;
		map.put("page", page);
		if("0".equals(industyid)){  //全部
			list=getSqlMapClientTemplate().queryForList("class.ClassiFiedDetail", map);
		}else {
			list=getSqlMapClientTemplate().queryForList("class.ClassiFiedDetailbyIndustryId", map);
		}		
		return list;
	}
 
	@Override
	public void saveSearchRecord(String uid, String type, String indexid) {
		Map  map=new HashMap();
		map.put("c_uid",uid );
		map.put("c_type",type );
		map.put("c_indexid",indexid );
		getSqlMapClientTemplate().insert("class.ClassiFiedUserSearchRecordSave",map);
	}

	@Override
	public List getBusinessArea(String cityid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cityid", cityid);
		List list = getSqlMapClientTemplate().queryForList("class.getBusinessArea", map);
		return list;
	}

	@Override
	public List getBusinessZone(String areaid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("areaid", areaid);
		List list = getSqlMapClientTemplate().queryForList("class.getBusinessArea", map);
		return list;
	}


}
