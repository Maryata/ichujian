package com.sys.ekey.task.action;

import com.sys.commons.AbstractAction;
import com.sys.ekey.task.service.EKTaskService;
import com.sys.util.StrUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 趣游戏--任务
 *
 * @author Maryn
 */
@Component("eKTaskAction")
public class EKTaskAction extends AbstractAction {

    private static final long serialVersionUID = 4316715744965131066L;

    @Autowired
    private EKTaskService eKTaskService;

    // 任务奖励
    /*public String reward() {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		String id = this.getParameter("id");// 任务id
		String uid = this.getParameter("uid");// 用户id
		log.info("into EKTaskAction.reward");
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
					// 如果不存在此种第三方账号，那么绑定第三方可以加分
					//if (exist == 0) {
						int i = eKTaskService.haveCompleted(id, uid);
						if (i == 0) {
							eKTaskService.reward(id, uid, "1");
						}
					//}

				} else if ("3".equals(id)) {// 签到
					eKTaskService.reward(id, uid, "2");
				} else {// 每日随机任务
					eKTaskService.reward(id, uid, "3");
				}
				reqMap.put("status", "Y");
			}
		} catch (Exception e) {
			reqMap.put("status", "N");
			reqMap.put("info", "1003," + e.getMessage());
			log.error("EKTaskAction.reward failed,e : " + e);
		}
		try {
			writeToResponse(JSONObject.fromObject(reqMap).toString());
		} catch (Exception e) {
			log.error("回写联系客服数据标识出错！",e);
		}

		return NONE;
	}*/

    // 是否显示“签到”
    public String signRecord() {
        Map<String, Object> reqMap = new HashMap<>();
        String uid = this.getParameter("uid");// 用户id
        log.info("into EKTaskAction.showSign");
        log.info(uid + " = uid");
        try {
            if (StrUtils.isEmpty(uid)) {
                reqMap.put("status", "N");
                reqMap.put("info", "1001");
            } else {
                // 查询用户当天的签到记录
                List<Map<String, Object>> signRecord = eKTaskService.signRecord(uid);
                if (StrUtils.isNotEmpty(signRecord)) {
                    reqMap.put("signRecord", "1");
                } else {
                    reqMap.put("signRecord", "0");
                }
                reqMap.put("status", "Y");
            }
        } catch (Exception e) {
            reqMap.put("status", "N");
            reqMap.put("info", "1003," + e.getMessage());
            log.error("EKTaskAction.showSign failed,e : " + e);
        }
        try {
            writeToResponse(JSONObject.fromObject(reqMap).toString());
        } catch (Exception e) {
            log.error("回写联系客服数据标识出错！", e);
        }

        return NONE;
    }

    /**
     * 随机任务
     * @return
     */
	/*public String randomTask() {
		Map<String,Object> resultMap = new HashMap<String,Object>();

		String uid = getParameter("uid");

		if(StringUtils.isEmpty(uid)) {
			resultMap.put(IGameConst.STATUS, IGameConst.NO);
			resultMap.put(IGameConst.INFO, IGameConst._1001);
		} else {

			try {
				List<Map<String, Object>> tasks = eKTaskService.getRandomTask(uid);
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
	}*/

    // 每日签到
	/*public String signIn() {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		String uid = this.getParameter("uid");// 用户id
		String signRecord = this.getParameter("signRecord");// 当天是否签到
		log.info("into EKTaskAction.signIn");
		log.info("uid = " + uid + ", signRecord = " + signRecord);
		try {
			if (StrUtils.isEmpty(uid) || StrUtils.isEmpty(signRecord)) {
				reqMap.put("status", "N");
				reqMap.put("info", "1001");
			} else {
				// 查询用户当天的签到记录
				reqMap = eKTaskService.signIn(uid, signRecord);
				reqMap.put("status", "Y");
			}
		} catch (Exception e) {
			reqMap.put("status", "N");
			reqMap.put("info", "1003, " + e.getMessage());
			log.error("EKTaskAction.signIn failed,e : " + e);
		}
		try {
			writeToResponse(JSONObject.fromObject(reqMap).toString());
		} catch (Exception e) {
			log.error("回写联系客服数据标识出错！",e);
		}

		return NONE;
	}*/

    /**
     * 签到
     *
     * @return
     */
    public String sign() {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        String uid = this.getParameter("uid");// 用户id
        int state = eKTaskService.sign(uid);
        try {
            reqMap.put("state", state);
            reqMap.put("status","Y");
            writeToResponse(JSONObject.fromObject(reqMap).toString());
        } catch (Exception e) {
            reqMap.put("status","Y");
            log.error("签到错误！",e);
        }
        return NONE;
    }

}
