package net.jeeshop.core.sms;

import net.jeeshop.core.front.SystemManager;
import net.jeeshop.services.manage.sms.bean.Sms;

import org.slf4j.LoggerFactory;

import com.cyberoller.sms.MessageSender;
import com.cyberoller.sms.zt.MessagePostSender;

/**
 * 发送短信
 * @author giles
 *
 */
public class SMSUtil {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SMSUtil.class);
	/**
	 * 发送短信
	 * @param sms
	 * @throws Exception
	 */
	public static void sendSMS(Sms sms) throws Exception{
		logger.info("phone {} send msg-s : content:[{}]",sms.getPhone(),sms.getContent());
		String isSend = SystemManager.getInstance().getProperty("sms.zt.sender.isSend");
		
		String host = SystemManager.getInstance().getProperty("sms.zt.sender.host");
		String username = SystemManager.getInstance().getProperty("sms.zt.sender.username");
		String password = SystemManager.getInstance().getProperty("sms.zt.sender.password");
		String productid = SystemManager.getInstance().getProperty("sms.zt.sender.productid");
		//String sendnumber=SystemManager.getInstance().getProperty("sms.zt.sender.sendnumber");
				
		MessageSender messageSender = new MessagePostSender(host, username, password, productid);
		if("N".equals(isSend)){
		}else{
			messageSender.send(sms.getPhone(), sms.getContent()); //发送验证码
		}
		logger.info("phone {} send msg-e",sms.getPhone());
		
	}
	

}
