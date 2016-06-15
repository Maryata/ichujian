package com.org.mokey.basedata.sysinfo.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.org.mokey.basedata.baseinfo.common.HtmlUtil;
import com.org.mokey.basedata.sysinfo.dao.GameActDao;
import com.org.mokey.basedata.sysinfo.service.GameActService;
import com.org.mokey.common.util.ApDateTime;

/**
 * 游戏帮：游戏活动
 */
public class GameActServiceImpl implements GameActService {
	
	private static final Logger LOGGER = Logger.getLogger(GameActServiceImpl.class);

	private GameActDao gameActDao;
	
	public GameActDao getGameActDao() {
		return gameActDao;
	}

	public void setGameActDao(GameActDao GameActDao) {
		this.gameActDao = GameActDao;
	}

	@Override
	// 查询活动列表
	public List<Map<String, Object>> gameActList(String gid, String name, int page, String gName) {
		return gameActDao.gameActList(gid, name, page, gName);
	}

	@Override
	// 获取活动总数
	public Integer getTotal(String gid, String name, String gName) {
		return gameActDao.getTotal(gid, name, gName);
	}

	@Override
	// 根据id删除游戏活动
	public void deleteGameAct(String id) {
		gameActDao.deleteGameAct(id);
	}

	@Override
	// 根据id查询活动
	public List<Map<String, Object>> queryActById(String editId) {
		return gameActDao.queryActById(editId);
	}

	@Override
	public Map<String, Object> listUser(int start, int limit, int aid,int flag) {
		return gameActDao.listUser(start, limit, aid,flag);
	}

	@Override
	public void updateUser(Map<String, Object> map) {
		try {
			gameActDao.updateUser(map);
		} catch (Exception e) {
			LOGGER.error("发放活动积分错误",e);
		}
	}

	@Override
	// 保存活动
	public void saveGameAct(String id, String gid, String order,
			String name, String depict, String logourl,
			String sDate, String eDate, String reward) {
		Date sdate = null;
		Date edate = null;
		try {
			sdate = ApDateTime.getDate(sDate, "yyyy-MM-dd HH:mm:ss");
			edate = ApDateTime.getDate(eDate, "yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			LOGGER.error("Parsing Date in GameGiftServiceImpl.saveGameGift failed, e : {}", e);
		}
		
		// 封装请求参数
		List<Object> args = new ArrayList<Object>();
		args.add(id);
		args.add(gid);
		args.add(name);
		args.add(depict);
//		Date date = new Date();
//		args.add(date);
		args.add("1");
		args.add(order);
		
		// 查询浏览人数
//		Integer num = gameActDao.scanedNum(id,gid);
		// 封装html文件需要的参数
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("c_id", id);
		map.put("c_name", name);
		map.put("c_sDate", sdate);
		map.put("c_eDate", edate);
//		map.put("c_date", date);
//		map.put("c_num", num);
		map.put("c_depict", depict);
		// 生成HTML
		HtmlUtil htmlUtil = new HtmlUtil();
		String shareurl = htmlUtil.createGameHtmlV2("gameActivityShare", map);
		args.add(shareurl);
		
		args.add(logourl);
		args.add(sdate);
		args.add(edate);
		args.add(reward);
		
		// 查询id是否存在
		boolean isExists = gameActDao.existId(id);
		
		if(!isExists){// id不存在，保存
			gameActDao.saveGameAct(args);
		}else{// id存在，更新
			args.remove(0);
			args.add(args.size(), id);
			gameActDao.updateGameAct(args);
		}
	}

}
