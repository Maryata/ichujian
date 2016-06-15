package com.org.mokey.basedata.baseinfo.action;

import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Result;
import com.xiaomi.xmpush.server.Sender;

public class MiPushTest {
	private Message buildMessage() throws Exception {
	     String PACKAGENAME = "com.xiaomi.mipushdemo";
	     String messagePayload = "This is a message";
	     String title = "notification title";
	     String description = "notification description";
	     Message message = new Message.Builder()
	             .title(title)
	             .description(description).payload(messagePayload)
	             .restrictedPackageName(PACKAGENAME)
	             .passThrough(1)  //消息使用透传方式
	             .notifyType(1)     // 使用默认提示音提示
	             .build();
	     return message;
	}
	private void sendBroadcast() throws Exception {
	     //Constants.useOfficial();
	     Sender sender = new Sender("PH471HIMUsBrjwXHUSQQKw==");
	     String messagePayload = "This is a message";
	     String title = "notification title";
	     String description = "notification description";
	     String topic = "topic1";
	     Message message = new Message.Builder()
	                .title(title)
	                .description(description).payload(messagePayload)
	                .restrictedPackageName("com.net.mokey")
	                .notifyType(2)     // 使用默认提示音提示
	                .build();
	     Result r = sender.broadcast(message, topic, 0); //根据topic，发送消息到指定一组设备上，不重试。
	     r.getErrorCode();
	}
	public static void main(String[] args) throws Exception{
		MiPushTest mipush = new MiPushTest();
		mipush.sendBroadcast();
	}
}
