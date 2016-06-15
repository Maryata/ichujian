package com.sys.game.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.sys.util.StrUtils;

@SuppressWarnings("deprecation")
public class GameBaseService extends SqlMapClientDaoSupport {

	@SuppressWarnings("unchecked")
	protected List<Map<String, Object>> _queryForList(String statementName, Object parameterObject) {
		List<Map<String, Object>> list = getSqlMapClientTemplate().queryForList( statementName, parameterObject );

		if ( null == list || list.size() == 0 )
			return new ArrayList<Map<String,Object>>();
		
		return list;
	}
	
	// 添加用户对游戏的操作行为
	@SuppressWarnings("unchecked")
	protected void userAction(List<Map<String, Object>> list,String uid){
		if(StrUtils.isNotEmpty(list)){
			for (Map<String, Object> map : list) {
				// 获取礼包id
				String gid = (map.get("ID")==null)?"":map.get("ID").toString();
				String remain = "0";// 剩余
				String got = "0";// 已领取
				map.put("REMAIN", remain);
				map.put("GOT", got);
				// 查询礼包码数量
				List<Map<String, Object>> cnt = this.getSqlMapClientTemplate().queryForList("gameGift.giftCodesCnt",gid);
				if(StrUtils.isNotEmpty(cnt)){
					Map<String, Object> cntMap = cnt.get(0);
					remain = cntMap.get("REMAIN").toString();
					got = cntMap.get("GOT").toString();
					map.put("REMAIN", remain);
					map.put("GOT", got);
				}
				if(Integer.valueOf(remain)>0){
					// ACTION：用户可以对礼包的操作行为--1领取，2淘号，3查看
					// 如果剩余礼包数大于0，则可以领取
					map.put("ACTION", "1");
				}else{
					map.put("ACTION", "2");
				}
				if(StrUtils.isNotEmpty(gid)){
					Map<String,Object> paramMap = new HashMap<String,Object>();
					paramMap.put("UID", uid);
					paramMap.put("GID", gid);
					// 查询当前用户是否领取过当前礼包的礼包码
					List<Map<String, Object>> codeList =
							this.getSqlMapClientTemplate().queryForList("gameGift.hasGotGame", paramMap);
					if(StrUtils.isNotEmpty(codeList) && codeList.size()>0){
						map.put("ACTION", "3");
						map.put("CODE", StrUtils.emptyOrString(codeList.get(0).get("C_CODE")));
					} else {// 如果没有领取过礼包码，查询通过淘号方式获取的礼包码
						List<Map<String, Object>> drawCodeList =
								this.getSqlMapClientTemplate().queryForList("gameGift.usersGiftCode", paramMap);
						if (StrUtils.isNotEmpty(drawCodeList) && drawCodeList.size()>0) {
							map.put("CODE", StrUtils.emptyOrString(drawCodeList.get(0).get("C_CODE")));
						} else {
							map.put("CODE", "");
						}
					}
				}
				// 非空处理
				map.put("METHOD", map.get("METHOD")==null?"":map.get("METHOD").toString());
			}
		}
	}
	// 添加用户对游戏的操作行为--重载方法，用于“礼包详情”接口调用（分类列表显示“淘号”，礼包详情显示“查看”）
	@SuppressWarnings("unchecked")
	protected void userActionForGiftDetail(List<Map<String, Object>> list,String uid){
		if(StrUtils.isNotEmpty(list)){
			for (Map<String, Object> map : list) {
				// 获取礼包id
				String gid = (map.get("ID")==null)?"":map.get("ID").toString();
				String remain = "0";// 剩余
				String got = "0";// 已领取
				map.put("REMAIN", remain);
				map.put("GOT", got);
				// 查询礼包码数量
				List<Map<String, Object>> cnt = this.getSqlMapClientTemplate().queryForList("gameGift.giftCodesCnt",gid);
				if(StrUtils.isNotEmpty(cnt)){
					Map<String, Object> cntMap = cnt.get(0);
					remain = cntMap.get("REMAIN").toString();
					got = cntMap.get("GOT").toString();
					map.put("REMAIN", remain);
					map.put("GOT", got);
				}
				if(Integer.valueOf(remain)>0){
					// ACTION：用户可以对礼包的操作行为--1领取，2淘号，3查看
					// 如果剩余礼包数大于0，则可以领取
					map.put("ACTION", "1");
				}else{
					map.put("ACTION", "2");
				}
				if(StrUtils.isNotEmpty(gid)){
					Map<String,Object> paramMap = new HashMap<String,Object>();
					paramMap.put("UID", uid);
					paramMap.put("GID", gid);
					// 查询当前用户是否领取过当前礼包的礼包码
					List<Map<String, Object>> codeList =
							this.getSqlMapClientTemplate().queryForList("gameGift.hasGotGame", paramMap);
					if(StrUtils.isNotEmpty(codeList) && codeList.size()>0){
						map.put("ACTION", "3");
						map.put("CODE", StrUtils.emptyOrString(codeList.get(0).get("C_CODE")));
					} else {// 如果没有领取过礼包码，查询通过淘号方式获取的礼包码
						List<Map<String, Object>> drawCodeList =
								this.getSqlMapClientTemplate().queryForList("gameGift.usersGiftCode", paramMap);
						if (StrUtils.isNotEmpty(drawCodeList) && drawCodeList.size()>0) {
							map.put("CODE", StrUtils.emptyOrString(drawCodeList.get(0).get("C_CODE")));
							map.put("ACTION", "3");
						} else {
							map.put("CODE", "");
						}
					}
				}
				// 非空处理
				map.put("METHOD", map.get("METHOD")==null?"":map.get("METHOD").toString());
			}
		}
	}

	/**
	 * @Description: TODO 处理用户评分
	 * @param param map 包含gid和用户id集合
	 * @param tempList 用户评论临时列表
	 * @param retList 用户评分列表,比临时列表多了评分信息
	 */
	protected void _processGrade(Map<String, Object> param,
			List<Map<String, Object>> tempList,
			List<Map<String, Object>> retList) {
		List<Map<String,Object>> _gradeList =  null;
		if(null != param) { // 用户没有评分
			// 当前页用户对游戏的评分列表
			_gradeList = _queryForList( "gameApp.getGrade", param );
		}
		
		if ( null != tempList && tempList.size() >= 1 ) { // 如果评论列表有数据
			for ( int i = 0; i < tempList.size(); ++i ) {
				Map<String, Object> _m = tempList.get( i );
				if(null != _gradeList) {
					Object o = _m.get( "C_UID" );
					boolean flag = false; // 标识用户是否有评分
					for ( int j = 0; j < _gradeList.size(); ++j ) {
						Map<String,Object> _gradeMap = _gradeList.get( j );
						if(o.equals( _gradeMap.get( "C_UID" ))) {
							_m.put( "C_GRADE", _gradeMap.get( "C_GRADE" ) ); // 将该用户对该游戏的评分加入集合
							_gradeList.remove( _gradeMap ); //当前map值已取，不需要放入下次循环
							// 这边需要注意的是，一个用户如果对一个游戏多次评分，多次评论，
							// 那么评论与评分的关系就是乱的，无法唯一标识了,因为评分和评论不是原子的操作，意味着就算从时间上也不能关联（或许可以用时间差，但是会很麻烦，实现上)
							
							flag = true; // 标识这个用户是有评分的
							
							break; //值已取，跳出本次循环
						}
					}
					
					if(!flag) { //如果当前用户没有评分
						_m.put( "C_GRADE", 0 ); // 默认分值0，这边没有用常量，需要改，直接找到这边改吧，比较乱
					}
					
				} else {
					_m.put( "C_GRADE", 0 );
				}
				retList.add( _m ); // 这个map已经包含用户评论所有信息，游戏评论 、用户头像和用户评分
			}
		}
	}
}
