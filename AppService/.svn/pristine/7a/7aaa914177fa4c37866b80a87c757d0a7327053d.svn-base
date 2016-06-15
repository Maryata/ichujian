package com.sys.ekey.game.service;

import com.xiaomi.xmpush.server.Result;

public interface EKMiPushService {
	
	/**
	 * 
	 * @param messagePayload
	 * @param title
	 * @param description
	 * @param passThrough ：设置消息是否通过透传的方式送给app，1表示透传消息，0表示通知栏消息
	 * @param notifyType ：设置通知类型 1|2|4
	 * 	<br> 设置通知类型，type的值可以是DEFAULT_ALL或者以下其他几种的OR组合：
        <br> DEFAULT_ALL = -1;
        <br> DEFAULT_SOUND  = 1;   // 使用默认提示音提示
        <br> DEFAULT_VIBRATE = 2;   // 使用默认震动提示
        <br> DEFAULT_LIGHTS = 4;    // 使用默认led灯光提示
	 * @param retries ：重试次数0-10
	 * @return
	 * @throws Exception
	 */
	public Result sendMessage(String messages, String gid)  throws Exception ;

}
