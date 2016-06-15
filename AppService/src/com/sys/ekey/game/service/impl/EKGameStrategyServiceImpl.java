package com.sys.ekey.game.service.impl;

import com.sys.ekey.game.service.EKGameBaseService;
import com.sys.ekey.game.service.EKGameStrategyService;
import com.sys.game.service.GameBaseService;
import com.sys.game.service.GameStrategyService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EKGameStrategyServiceImpl extends EKGameBaseService implements
		EKGameStrategyService {

	@Override
	public List<Map<String, Object>> raiders(long gid, int page, int rows) {
		int start = rows * (page - 1) + 1;
		int maxRows = start + rows - 1;
		
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		
		parameterObject.put( "gid", gid );
		parameterObject.put( "startingIndex", start );
		parameterObject.put( "maxRows", maxRows );
		
		return _queryForList( "ek_gameStrategy.raiders", parameterObject );
	}

	@Override
	public Map<String, Object> getRaidersById(long id, String uid, String source) {
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		parameterObject.put( "id", id );
		parameterObject.put( "uid", uid );
		parameterObject.put( "source", source );
		
		List<Map<String, Object>> list = _queryForList( "ek_gameStrategy.getRaidersById", parameterObject );
		
		if ( null == list || list.size() <= 0 )
			return new HashMap<String, Object>();

		return list.get( 0 );
	}

	@Override
	public List<Map<String, Object>> search(int uid, String imei, String content, String type, String gid) {
		content = content == null ? "默认搜索" : content;
		//操作类型  0：下载    1：卸载  2：启动   3：查看 4：退出 5：发弹幕 6：搜索
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		parameterObject.put("uid", uid);
		parameterObject.put("imei", imei);
		parameterObject.put("content", content);
		parameterObject.put("type", "6");
		parameterObject.put("source", "1"); // 操作来源：0： app游戏  1：攻略  2：活动  3：资讯  4：h5游戏
		parameterObject.put("date", new Date());

		getSqlMapClientTemplate().insert("ek_gameApp.searchBehavior", parameterObject);

		// 现在查询是不分类型的，查询详细信息
		if ("0".equals(type)) {
			return _queryForList("ek_gameStrategy.search", gid);
		}

		return _queryForList("ek_gameStrategy.search", gid);
	}

    @Override
    public List<Map<String, Object>> list(int categoryId,
                                                         int page, int rows) {
        int start = rows * (page - 1) + 1;
        int maxRows = start + rows - 1;

        Map<String, Object> parameterObject = new HashMap<String, Object>();

        parameterObject.put( "cid", categoryId );
        parameterObject.put( "startingIndex", start );
        parameterObject.put( "maxRows", maxRows );

        List<Map<String,Object>> _list = _queryForList( "ek_gameStrategy.listByCategory", parameterObject ) ;

        return _list;
    }
}
