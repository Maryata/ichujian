package com.org.mokey.basedata.sysinfo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.org.mokey.basedata.baseinfo.common.HtmlUtil;
import com.org.mokey.basedata.sysinfo.dao.GameInfoDao;
import com.org.mokey.basedata.sysinfo.service.GameInfoService;

/**
 * 游戏帮：游戏资讯
 */
public class GameInfoServiceImpl implements GameInfoService {

	private GameInfoDao gameInfoDao;
	
	public GameInfoDao getGameInfoDao() {
		return gameInfoDao;
	}

	public void setGameInfoDao(GameInfoDao gameInfoDao) {
		this.gameInfoDao = gameInfoDao;
	}

	@Override
	// 查询资讯列表
	public List<Map<String, Object>> gameInfoList(String gid, String name, int page, String gName) {
		return gameInfoDao.gameInfoList(gid, name, page, gName);
	}

	@Override
	// 获取资讯总数
	public Integer getTotal(String gid, String name, String gName) {
		return gameInfoDao.getTotal(gid, name, gName);
	}

	@Override
	// 根据id删除游戏资讯
	public void deleteGameInfo(String id) {
		gameInfoDao.deleteGameInfo(id);
	}

	@Override
	// 根据id查询资讯
	public List<Map<String, Object>> queryInfoById(String editId) {
		return gameInfoDao.queryInfoById(editId);
	}
	
	@Override
	// 保存资讯
	public void saveGameInfo(String id, String gid, String order, String name, String depict, String logourl) {
		// 封装请求参数
		List<Object> args = new ArrayList<Object>();
		args.add(id);
		args.add(gid);
		args.add(name);
		args.add(depict);
		Date date = new Date();
		args.add(date);
		args.add("1");
		args.add(order);
		
		// 查询浏览人数
//		Integer num = gameInfoDao.scanedNum(id,gid);
		// 封装html文件需要的参数
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("c_id", id);
		map.put("c_name", name);
		map.put("c_date", date);
//		map.put("c_num", num);
		map.put("c_depict", depict);
		// 生成HTML
		HtmlUtil htmlUtil = new HtmlUtil();
		String shareurl = htmlUtil.createGameHtmlV2("gameInformationShare",map);
		args.add(shareurl);
		
		args.add(logourl);
		
		// 查询id是否存在
		boolean isExists = gameInfoDao.existId(id);
		
		if(!isExists){// id不存在，保存
			gameInfoDao.saveGameInfo(args);
		}else{// id存在，更新
			args.remove(0);
			args.add(args.size(), id);
			gameInfoDao.updateGameInfo(args);
		}
	}

}
