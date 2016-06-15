package com.sys.game.action;

import com.sys.game.service.GameTaskService;
import com.sys.game.util.IGameConst;
import com.sys.util.StrUtils;

import edu.emory.mathcs.backport.java.util.Arrays;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 趣游戏--任务
 * 
 * @author Maryn
 * 
 */
@Component
public class GameTaskAction extends GameBaseAction {

	private static final long serialVersionUID = 4316715744965131066L;

	@Autowired
	private GameTaskService gameTaskService;

	// 任务奖励
	public String reward() {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		String id = this.getParameter("id");// 任务id
		String uid = this.getParameter("uid");// 用户id
		log.info("into GameTaskAction.reward");
		log.info("id = " + id + ", uid = " + uid);
		try {
			if (StrUtils.isEmpty(id)) {
				reqMap.put("status", "N");
				reqMap.put("info", "1001");
			} else {
				// 查询用户是否已经做过一次性任务（上传头像、修改昵称、修改性别、修改手机号、绑定第三方）
				String[] arr = {"1", "14", "15", "16", "17", "18", "19"};
				List<String> list = Arrays.asList(arr);
				if (list.contains(id)) {// 一次性任务
					// 查询当前用户是否绑定过第三方账号或手机
					/*int exist = 0;
					if ("15".equals(id)) {// QQ
						exist = gameTaskService.haveBound(uid, "1");
					} else if ("16".equals(id)) {// 微信
						exist = gameTaskService.haveBound(uid, "2");
					} else if ("17".equals(id)) {// 微博
						exist = gameTaskService.haveBound(uid, "3");
					} else if ("19".equals(id)) {// 手机号
						exist = gameTaskService.haveBound(uid, "0");
					}*/
					// 如果不存在此种第三方账号，那么绑定第三方可以加分
					//if (exist == 0) {
						int i = gameTaskService.haveCompleted(id, uid);
						if (i == 0) {
							gameTaskService.reward(id, uid, "1");
						}
					//}

				} else if ("3".equals(id)) {// 签到
					gameTaskService.reward(id, uid, "2");
				} else {// 每日随机任务
					gameTaskService.reward(id, uid, "3");
				}
				reqMap.put("status", "Y");
			}
		} catch (Exception e) {
			reqMap.put("status", "N");
			reqMap.put("info", "1003," + e.getMessage());
			log.error("GameTaskAction.reward failed,e : " + e);
		}
		out = JSONObject.fromObject(reqMap).toString();
		return "success";
	}
	
	// 是否显示“签到”
	public String signRecord() {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		String uid = this.getParameter("uid");// 用户id
		log.info("into GameTaskAction.showSign");
		log.info(uid + " = uid");
		try {
			if (StrUtils.isEmpty(uid)) {
				reqMap.put("status", "N");
				reqMap.put("info", "1001");
			} else {
				// 查询用户当天的签到记录
				List<Map<String, Object>> signRecord = gameTaskService.signRecord(uid);
				if(StrUtils.isNotEmpty(signRecord)){
					reqMap.put("signRecord", "1");
				}else{
					reqMap.put("signRecord", "0");
				}
				reqMap.put("status", "Y");
			}
		} catch (Exception e) {
			reqMap.put("status", "N");
			reqMap.put("info", "1003," + e.getMessage());
			log.error("GameTaskAction.showSign failed,e : " + e);
		}
		out = JSONObject.fromObject(reqMap).toString();
		return "success";
	}

	/**
	 * 随机任务
	 * @return
	 */
	public String randomTask() {
		Map<String,Object> resultMap = new HashMap<String,Object>();

		String uid = getParameter("uid");

		if(StringUtils.isEmpty(uid)) {
			resultMap.put(IGameConst.STATUS, IGameConst.NO);
			resultMap.put(IGameConst.INFO, IGameConst._1001);
		} else {

			try {
				List<Map<String, Object>> tasks = gameTaskService.getRandomTask(uid);
				resultMap.put(IGameConst.STATUS, IGameConst.YES);
				resultMap.put(IGameConst.INFO, IGameConst._1002);
				resultMap.put("tasks", tasks);
			} catch (Exception e) {
				resultMap.put(IGameConst.STATUS, IGameConst.NO);
				resultMap.put(IGameConst.INFO, IGameConst._1003);
				log.error(e);
			}
		}

		try {
			writeToResponse(JSONObject.fromObject(resultMap).toString());
		} catch (IOException e) {
			log.error(e);
		}

        return NONE;
	}
	
	// 每日签到
	public String signIn() {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		String uid = this.getParameter("uid");// 用户id
		String signRecord = this.getParameter("signRecord");// 当天是否签到
		log.info("into GameTaskAction.signIn");
		log.info("uid = " + uid + ", signRecord = " + signRecord);
		try {
			if (StrUtils.isEmpty(uid) || StrUtils.isEmpty(signRecord)) {
				reqMap.put("status", "N");
				reqMap.put("info", "1001");
			} else {
				// 查询用户当天的签到记录
				reqMap = gameTaskService.signIn(uid, signRecord);
				reqMap.put("status", "Y");
			}
		} catch (Exception e) {
			reqMap.put("status", "N");
			reqMap.put("info", "1003, " + e.getMessage());
			log.error("GameTaskAction.signIn failed,e : " + e);
		}
		out = JSONObject.fromObject(reqMap).toString();
		return "success";
	}
}
