package com.sys.ekey.game.service;

import java.util.List;
import java.util.Map;

public interface EKGameTaskService {

    /**
     * 任务奖励
     *
     * @param id   任务id
     * @param uid  用户id
     * @param type 类型：0：一次性任务，1：每日随机任务
     */
    public void reward(String id, String uid, String type);

    /**
     * 查询用户当天的签到记录
     *
     * @param uid 用户id
     * @return
     */
    public List<Map<String, Object>> signRecord(String uid);

    /**
     * 获取随机任务列表
     *
     * @return
     */
    List<Map<String, Object>> getRandomTask(String uid);

    /**
     * 每日签到
     *
     * @param uid        用户id
     * @param signRecord 是否已签到
     * @return
     */
    public Map<String, Object> signIn(String uid, String signRecord);


    /**
     * 查询用户是否已经做过一次性任务（上传头像、修改昵称、修改性别、修改手机号、绑定第三方）
     *
     * @param tid 任务id
     * @param uid 用户ID
     * @return
     */
    public int haveCompleted(String tid, String uid);

    /**
     * 查询对应的登录类型的用户是否存在
     *
     * @param uid
     * @param loginType
     * @return
     */
    int haveBound(String uid, String loginType);
}
