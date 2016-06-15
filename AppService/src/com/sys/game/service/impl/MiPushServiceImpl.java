package com.sys.game.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.sys.game.service.MiPushService;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Result;
import com.xiaomi.xmpush.server.Sender;
import com.xiaomi.xmpush.server.Message.Builder;

@Service
public class MiPushServiceImpl implements MiPushService {

	/** 包名 */
	private static String MY_PACKAGE_NAME = "com.net.mokeyandroid";
	/** AppID */
	// private static String APP_ID = "2882303761517283910";
	/** AppKey */
	// private static String APP_KEY = "5321728331910";
	/** SECRET_KEY */
	private static String APP_SECRET_KEY = "wp6kUYV3UyvvfApAxyZUCg==";

	protected Logger log = (Logger.getLogger(getClass()));

	@Override
	public Result sendMessage(String messages, String gid) throws Exception {
		Sender sender = new Sender(APP_SECRET_KEY);
		Builder builder = new Message.Builder()
				.title(messages)
				.description(messages)
				.payload(messages)
				.restrictedPackageName(MY_PACKAGE_NAME)
				.passThrough(1)// 设置消息是否通过透传的方式送给app，1表示透传消息，0表示通知栏消息
				.notifyType(1);//
		builder.extra("type", "2");
		Message message = builder.build();
		
//		Sender sender = new Sender(APP_SECRET_KEY);
//		String topic = "ICJ" + gid;
//		Message message = new Message.Builder()
//				.title(messages)
//				.description(messages)
//				.payload(messages)
//				.restrictedPackageName(MY_PACKAGE_NAME)
//				.notifyType(1) // 使用默认提示音提示
//				.build();
		 
		Result r = sender.broadcast(message, "ICJ" + gid, 1); // 根据topic，发送消息到指定一组设备上，不重试。

		sender = new Sender("yvqg9hMuZKPll9kcmSASRA==");
		builder = new Message.Builder()
				.title(messages)
				.description(messages)
				.payload(messages)
				.restrictedPackageName("com.ichujian.games")
				.passThrough(1)// 设置消息是否通过透传的方式送给app，1表示透传消息，0表示通知栏消息
				.notifyType(1);//
		builder.extra("type", "2");
		message = builder.build();
		
		r = sender.broadcast(message, "ICJ" + gid, 1); // 根据topic，发送消息到指定一组设备上，不重试。
		// log.info("result  ======== regid : " + r );
		// if(log.isInfoEnabled()) {
		// log.info("result  ======== regid : " + r );
		// }

		return r;
	}

	public static void main(String[] args) throws Exception {
		// MiPushServiceImpl mipush = new MiPushServiceImpl();
		// Result r = mipush.sendMessage("message", "测试主题", "测试描述", 0, 1, 1);
	}

}
