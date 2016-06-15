package com.org.mokey.basedata.sysinfo.service.impl;

import java.util.Map;

import org.apache.log4j.Logger;

import com.org.mokey.basedata.sysinfo.dao.PushDao;
import com.org.mokey.basedata.sysinfo.service.MiPushService;
import com.org.mokey.basedata.sysinfo.service.PushService;
import com.org.mokey.util.StrUtils;

public class PushServiceImpl implements PushService {
	protected  Logger log = (Logger.getLogger(getClass()));
	private PushDao pushDao;
	private MiPushService miPushService;

	public PushDao getPushDao() {
		return pushDao;
	}
	public void setPushDao(PushDao pushDao) {
		this.pushDao = pushDao;
	}
	public MiPushService getMiPushService() {
		return miPushService;
	}
	public void setMiPushService(MiPushService miPushService) {
		this.miPushService = miPushService;
	}
	@Override
	public Map<String, Object> getPushInfoListMap(Map<String, Object> queryMap,
			int start, int limit) {
		return pushDao.getPushInfoListMap(queryMap,start,limit);
	}

	@Override
	public String savePushInfo(Map<String, Object> saveMap) {
		String msgId = pushDao.savePushInfo(saveMap);
		return msgId;
	}
	
	@Override
	public String sendMessages(Map<String, Object> messageMap) throws Exception {
		String messagePayload = "This is a message";
		String title = (String) messageMap.get("C_PUSH_TITLE");
		String description = (String) messageMap.get("C_PUSH_CONTENT");
		
		int passThrough = Integer.valueOf(messageMap.get("C_PUSH_TYPE")+"");
		Integer notifyType = 1;
		String notifyTypeStr = (String)messageMap.get("C_HINT_TYPE");
		if(notifyTypeStr.indexOf(",")>-1){
			if("1,2,4".equals(notifyTypeStr)){//全部
				notifyType = -1;
			}else{
				String [] notityArr = notifyTypeStr.split(",");
				if(notityArr.length==2){
					notifyType = Integer.valueOf(notityArr[0])|Integer.valueOf(notityArr[1]);
				}else if(notityArr.length==3){
					notifyType = Integer.valueOf(notityArr[0])|Integer.valueOf(notityArr[1])|Integer.valueOf(notityArr[2]);
				}
				/*for(int i=0;i<notityArr.length;i++){
					notifyType 
				}*/
			}
		}else{
			notifyType = Integer.valueOf(notifyTypeStr);
		}
		int retries = 0;
		if(StrUtils.isNotEmpty(messageMap.get("C_failed_COUNT"))){
			retries = Integer.valueOf(messageMap.get("C_failed_COUNT")+"");
		}
		//try {
		miPushService.sendMessage(messagePayload, title, description, passThrough, notifyType, retries
				,messageMap);
		/*} catch (Exception e) {
			log.error("sendMessages failed,", e);
		}*/
		//保存数据
		String msgId = pushDao.savePushInfo(messageMap);
		return msgId;
	}

}
