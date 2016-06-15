package com.sys.ekey.game.service.impl;

import com.sys.ekey.game.service.EKGameBaseService;
import com.sys.ekey.game.service.EKGameGiftService;
import com.sys.util.StrUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@SuppressWarnings("deprecation")
public class EKGameGiftServiceImpl extends EKGameBaseService implements EKGameGiftService {

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> giftsIndex(long uid) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		final String TYPE = "3"; //1：精品 2：H5精品 3：礼包
		
		List<Map<String,Object>> _l = getSqlMapClientTemplate().queryForList( "ek_gameGift.index", TYPE );

		if(null != _l && _l.size() >= 1) {
			Map<String, Object> parameterObject = new HashMap<String, Object>();
			Map<String,Object> _m = null;
			List<Map<String,Object>> categories = new ArrayList<Map<String,Object>>();
			for ( int i = 0; i < _l.size(); ++i ) { // 这个不会多，不然查询不适合放在循环内
				_m = _l.get( i );
				Object number = _m.get( "C_NUMBER" );
				Object cid = _m.get( "C_CID" );

				List<Map<String,Object>> l_category = _queryForList( "ek_gameGift.findCategoryById", cid );
				if(l_category.size() >= 1) {
					Map<String, Object> m_category = l_category.get( 0 );
					Map<String, Object> result_child_map = new HashMap<String, Object>();

					parameterObject = new HashMap<String, Object>();
					parameterObject.put( "startingIndex", 1 );
					parameterObject.put( "maxRows", number );
					parameterObject.put( "cid",  cid);

					result_child_map.put( "C_NAME", m_category.get( "C_NAME" ) );
					result_child_map.put( "C_CID", m_category.get( "C_ID" ) );
					List<Map<String,Object>> _list = _queryForList( "ek_gameGift.giftsDetail", parameterObject ) ;
					this.userAction( _list, String.valueOf( uid ) );

					result_child_map.put( "gifts",  _list);

					categories.add( result_child_map );
				}
			}

			resultMap.put( "categories", categories );
		} else {
			resultMap.put( "categories", new ArrayList<Map<String,Object>>() );
		}

		return resultMap;
	}

	@Override
	public List<Map<String, Object>> giftsCategoryDetail(long uid, int categoryId,
			int page, int rows) {
		int start = rows * (page - 1) + 1;
		int maxRows = start + rows - 1;

		Map<String, Object> parameterObject = new HashMap<String, Object>();

		parameterObject.put( "cid", categoryId );
		parameterObject.put( "startingIndex", start );
		parameterObject.put( "maxRows", maxRows );

		List<Map<String,Object>> _list = _queryForList( "ek_gameGift.giftsDetail", parameterObject ) ;
		this.userAction( _list, String.valueOf( uid ) );
		return _list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> gameGiftList(String uid, String pageIndex) {
		// 查询所有游戏礼包
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("PAGE", pageIndex);
		List<Map<String, Object>> list = this.getSqlMapClientTemplate().queryForList("ek_gameGift.gameGiftList", paramMap);
		this.userAction(list, uid);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	// 获取单个游戏礼包
	public List<Map<String, Object>> gameGift(String uid, String gid) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("GID", gid);
		// 查询当前用户是否领取过当前游戏
		List<Map<String, Object>> list = this.getSqlMapClientTemplate().queryForList("ek_gameGift.gameGift", paramMap);
		this.userAction(list, uid);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	// 获取当前用户的指定礼包的礼包码
	public List<Map<String, Object>> usersGiftCode(String uid, String gid) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("UID", uid);
		paramMap.put("GID", gid);
//		return (String) this.getSqlMapClientTemplate().queryForObject("gameGift.usersGiftCode", paramMap);
		return this.getSqlMapClientTemplate().queryForList("ek_gameGift.hasGotGame", paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	// 获取一条礼包码
	public List<Map<String, Object>> getGiftCode(String gid) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("GID", gid);
		return this.getSqlMapClientTemplate().queryForList("ek_gameGift.getGiftCode", paramMap);
	}

	@Override
	// 更新礼包码
	public void updateGiftCode(String cid) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("CID", cid);
		this.getSqlMapClientTemplate().update("ek_gameGift.updateGiftCode", paramMap);
	}

	@Override
	// 添加用户对礼包的操作行为
	public void addUserActionOfGift(String uid, String gid, String type, String code) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("UID", uid);
		paramMap.put("GID", gid);
		paramMap.put("TYPE", type);
		paramMap.put("CODE", code);
		this.getSqlMapClientTemplate().update("ek_gameGift.updateUserActionOfGift", paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	// 查询礼包详情
	public List<Map<String, Object>> giftDetail(String uid, String gid) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("GID", gid);
		List<Map<String, Object>> list = this.getSqlMapClientTemplate().queryForList("ek_gameGift.giftDetail", paramMap);
		this.userActionForGiftDetail(list, uid);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	// 礼包淘号
	public List<Map<String, Object>> drawGiftCode(String gid) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("GID", gid);
		paramMap.put("DATE", new Date());
		return this.getSqlMapClientTemplate().queryForList("ek_gameGift.drawGiftCode", paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	// 获取当前用户的礼包
	public List<Map<String, Object>> usersGifts(String uid) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("UID", uid);
		return this.getSqlMapClientTemplate().queryForList("ek_gameGift.usersGifts", paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	// 礼包推荐
	public List<Map<String, Object>> recommendGift(String uid) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("UID", uid);
		// 查询用户下载过的游戏的礼包
		List<Map<String, Object>> downloadedGameGifts = this.getSqlMapClientTemplate()
				.queryForList("ek_gameGift.downloadedGameGifts", paramMap);
		// 查询用户的礼包
		List<Map<String, Object>> usersGifts = this.getSqlMapClientTemplate()
				.queryForList("ek_gameGift.usersGifts", paramMap);
		// 查询后台维护的推荐礼包
		List<Map<String, Object>> recommendGifts = this.getSqlMapClientTemplate()
				.queryForList("ek_gameGift.recommendGifts");
		Set<String> removeId = new HashSet<String>();
		if (StrUtils.isNotEmpty(downloadedGameGifts)) {
			for (Map<String, Object> map : downloadedGameGifts) {
				// 下载过的游戏的礼包id
				String gid = StrUtils.emptyOrString(map.get("ID"));
				for (Map<String, Object> map2 : usersGifts) {
					// 用户的礼包的id
					String gid2 = StrUtils.emptyOrString(map2.get("C_ID"));
					if(StrUtils.isNotEmpty(gid) && StrUtils.isNotEmpty(gid2) && gid.equals(gid2)){
						// 如果用户下载的游戏对应的礼包已经存在于“我的礼包”，则不重复推荐
						removeId.add(gid);
					}
				}
				for (Map<String, Object> map3 : recommendGifts) {
					// 后台维护的推荐礼包的id
					String gid3 = StrUtils.emptyOrString(map3.get("ID"));
					if(StrUtils.isNotEmpty(gid) && StrUtils.isNotEmpty(gid3) && gid.equals(gid3)){
						// 如果后台维护的推荐礼包已经存在于“我的礼包”，则不重复推荐
						removeId.add(gid);
					}
				}
			}
		}
		int length = downloadedGameGifts.size();
		for (int i = 0; i < length; i++) {
			for (Map<String, Object> map : downloadedGameGifts) {
				// 下载过的游戏的礼包id
				String gid = StrUtils.emptyOrString(map.get("ID"));
				if(removeId.contains(gid)){
					downloadedGameGifts.remove(map);
					break;
				}
			}
		}
		recommendGifts.addAll(downloadedGameGifts);
		this.userAction(recommendGifts, uid);
		return recommendGifts;
	}

}
