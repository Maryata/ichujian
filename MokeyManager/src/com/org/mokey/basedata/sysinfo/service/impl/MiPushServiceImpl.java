package com.org.mokey.basedata.sysinfo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.org.mokey.basedata.sysinfo.service.MiPushService;
import com.org.mokey.common.util.number.NumberUtil;
import com.org.mokey.util.JSONUtil;
import com.org.mokey.util.StrUtils;
import com.xiaomi.xmpush.server.Constants;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Message.Builder;
import com.xiaomi.xmpush.server.Sender.BROADCAST_TOPIC_OP;
import com.xiaomi.xmpush.server.Result;
import com.xiaomi.xmpush.server.Sender;

public class MiPushServiceImpl implements MiPushService {
	
	/**包名*/
	private static String MY_PACKAGE_NAME = "com.net.mokeyandroid";
	/**AppID*/
	//private static String APP_ID = "2882303761517283910";
	/**AppKey*/
	//private static String APP_KEY = "5321728331910";
	/**SECRET_KEY*/
	private static String APP_SECRET_KEY = "wp6kUYV3UyvvfApAxyZUCg==";
	
	protected  Logger log = (Logger.getLogger(getClass()));

	@Override
	public Result sendMessage(String messagePayload,String title, String description
			,int passThrough,int notifyType,int retries, Map<String, Object> messageMap) throws Exception {
	     //Constants.useOfficial();
		String scope = messageMap.get("C_PUSH_SCOPE")+"";
		String topicStr = (String) messageMap.get("C_PUSH_TOPIC");
		String notify_effect = messageMap.get("C_TURN_TYPE")+"";
		String intent_uri = (String) messageMap.get("C_APP_ACTIVE");
		String web_url = (String) messageMap.get("C_TURN_URL");
		String turn_data = (String) messageMap.get("C_TURN_DATA");
		
	     Sender sender = new Sender(APP_SECRET_KEY);
	     
	     Builder builder = new Message.Builder()
			     .title(title)
		         .description(description).payload(messagePayload)
		         .restrictedPackageName(MY_PACKAGE_NAME)
		         .passThrough(passThrough)//设置消息是否通过透传的方式送给app，1表示透传消息，0表示通知栏消息
		         .notifyType(notifyType)// 
		         .notifyId(NumberUtil.roundInt(2));
	     //--------------------- 设置扩展信息;
	     if(Constants.NOTIFY_LAUNCHER_ACTIVITY.equals(notify_effect)){//1: 打开当前app对应的Launcher Activity
	    	 builder.extra(Constants.EXTRA_PARAM_NOTIFY_EFFECT, Constants.NOTIFY_LAUNCHER_ACTIVITY);
	     }
	     else if(Constants.NOTIFY_ACTIVITY.equals(notify_effect)){//2: 打开当前app内的任意一个Activity
	    	 builder.extra(Constants.EXTRA_PARAM_NOTIFY_EFFECT, Constants.NOTIFY_ACTIVITY);
	    	 builder.extra(Constants.EXTRA_PARAM_INTENT_URI, intent_uri);
	     }
	     else if(Constants.NOTIFY_WEB.equals(notify_effect)){//3: 打开网页
	    	 builder.extra(Constants.EXTRA_PARAM_NOTIFY_EFFECT, Constants.NOTIFY_WEB);
	    	 builder.extra(Constants.EXTRA_PARAM_WEB_URI, web_url);
	     }
	    if(StrUtils.isNotEmpty(turn_data)){
			@SuppressWarnings("unchecked")
			Map<String,String>[] trans = (Map[]) JSONUtil.JSONString2ObjectArray(turn_data, Map.class);
	    	for(Map<String,String> m: trans){
	    		builder.extra(m.get("key").toString(), m.get("value").toString());
	    	}
	    }
	     
	     Message message = builder.build();
	    /* Message message = new Message.Builder()
	                .title(title)
	                .description(description).payload(messagePayload)
	                .restrictedPackageName(MY_PACKAGE_NAME)
	                .passThrough(passThrough)//设置消息是否通过透传的方式送给app，1表示透传消息，0表示通知栏消息
	                .notifyType(notifyType)// 
	                .notifyId(NumberUtil.roundInt(2))
	                .build();*/
	     log.debug("push msg: "+message.toString());
	     Result r = null;
	     if("0".equals(scope)){//全部;
	    	 r = sender.broadcastAll(message, retries);
	     }else{
	    	 if(StrUtils.isNotEmpty(topicStr)){
	    		 String [] topicArr = topicStr.split(",");
	    		 //多个topic;拆分为每5个topic一组调用;因api最多
	    		 int subSize = 5;
	    		 int count = topicArr.length % subSize == 0 ? topicArr.length / subSize : topicArr.length / subSize + 1;
	    		 
	    		 List<String> topics = new ArrayList<String>();
	    		 for (int i = 0; i < count; i++) {
	    			 int index = i * subSize;
	    			 
	    			 topics.clear();
	    			 int j = 0;
	    			 while (j < subSize && index < topicArr.length) {
	    				 topics.add(topicArr[index++]);
	    				 j++;
	    			 }
	    			 //向多个topic广播消息，支持topic间的交集、并集或差集（如果只有一个topic请用单topic版本）。
	    			 if(topics.size()==1){
	    				 r = sender.broadcast(message, topics.get(0), retries);
	    			 }else{
	    				 r = sender.multiTopicBroadcast(message, topics,  BROADCAST_TOPIC_OP.UNION, retries);
	    			 }
	    		 }
	    	 }
	     }
	     log.debug("push result: "+JSONObject.fromObject(r).toString());
	     return r;
	}
	
	public static void main(String[] args) throws Exception{
		//MiPushServiceImpl mipush = new MiPushServiceImpl();
		//Result r = mipush.sendMessage("message", "测试主题", "测试描述", 0, 1, 1);
	}

}
