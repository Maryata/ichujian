package com.sys.ekey.game.service.impl;

import com.sys.ekey.game.service.EKGameBaseService;
import com.sys.ekey.game.service.EKGameH5Service;
import com.sys.game.service.GameBaseService;
import com.sys.game.service.GameH5Service;
import com.sys.util.StrUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@SuppressWarnings("deprecation")
public class EKGameH5ServiceImpl extends EKGameBaseService implements EKGameH5Service {

	@SuppressWarnings("unchecked")
	@Override
	// H5游戏分类列表
	public List<Map<String, Object>> h5CategoryList() {
		return this.getSqlMapClientTemplate().queryForList("ek_gameH5.h5CategoryList");
	}

	@Override
	// 保存用户发弹幕的行为
	public void saveH5Barrage(String uid, String gid, String type, String source) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("UID", uid);
		paramMap.put("GID", gid);
		paramMap.put("TYPE", type);
		paramMap.put("DATE", new Date());
		paramMap.put("SOURCE", source);
		this.getSqlMapClientTemplate().update("ek_gameH5.saveH5Barrage", paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	// 最近在玩
	public List<Map<String, Object>> recentlyPlaying(String uid, String type) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("UID", uid);
		if("1".equals(type)){
			list = this.getSqlMapClientTemplate().queryForList("ek_gameH5.recentlyPlaying", paramMap);
		}
		if("2".equals(type)){
			list = this.getSqlMapClientTemplate().queryForList("ek_gameH5.allRecentlyPlaying", paramMap);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	// 根据分类id获取具体某一分类中的游戏(H5)
	public List<Map<String, Object>> h5GamesInCategory(String cid,
			String pageindex) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("CID", cid);
		paramMap.put("PAGE", Integer.parseInt(pageindex));
		// 分页获取当前分类的所有游戏信息
		List<Map<String, Object>> list = this.getSqlMapClientTemplate()
				.queryForList("ek_gameH5.h5GamesInCategory", paramMap);
		if (StrUtils.isNotEmpty(list)) {
			for (Map<String, Object> map : list) {
				String gid = map.get("C_ID").toString();// 游戏id
				// 查询指定H5游戏被启动的次数
				paramMap.clear();
				paramMap.put("GID", gid);
				Integer cnt = (Integer) this.getSqlMapClientTemplate()
						.queryForObject("ek_gameH5.startingCountH5", paramMap);
				map.put("startCnts", (cnt == null) ? "0" : String.valueOf(cnt));
			}
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> searchH5(int uid, String imei, String content,
											  Date date, String type) {
		//操作类型  0：下载    1：卸载  2：启动   3：查看 4：退出 5：发弹幕 6：搜索
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		parameterObject.put( "uid", uid );
		parameterObject.put( "imei", imei );
		parameterObject.put( "content", content );
		parameterObject.put( "type", "6" );
		parameterObject.put( "source", "4" ); // 操作来源：0： app游戏  1：攻略  2：活动  3：资讯  4：h5游戏
		parameterObject.put( "date", date );

		getSqlMapClientTemplate().insert( "ek_gameH5.searchBehavior",parameterObject );

		if("0".equals( type )) {
			return _queryForList( "ek_gameH5.searchH51", content );
		}

		return _queryForList( "ek_gameH5.searchH5", content );
	}

	@Override
	public Map<String, Object> getH5GameById (String gid) {
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		parameterObject.put( "id", gid );

		List<Map<String, Object>> list = _queryForList( "ek_gameH5.get", parameterObject );

		if ( null == list || list.size() <= 0 ) {
			return new HashMap<String, Object>();
		}

		return list.get( 0 );
	}
}
