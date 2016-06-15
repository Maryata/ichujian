package com.sys.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cyberoller.sms.zt.MessagePostSender;



public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		/* 读取配置文件信息(地址、用户名、密码、产品id)固定信息 */
//		String host = Config.getInstance().getValue("sms.zt.sender.host");
//		String username = Config.getInstance().getValue("sms.zt.sender.username");
//		String password = Config.getInstance().getValue("sms.zt.sender.password");
//		String productid = Config.getInstance().getValue("sms.zt.sender.productid");
		
		String host = "http://www.ztsms.cn:8800/sendSms.do";
		String username = "yangyi";
		String password = "cyberSH2015";
		String productid = "887361";
		
//		String host = FileUtil.getSmspath("sms.zt.sender.host");
//		String username = FileUtil.getSmspath("sms.zt.sender.username");
//		String password = FileUtil.getSmspath("sms.zt.sender.password");
//		String productid = FileUtil.getSmspath("sms.zt.sender.productid");
		
		//String codeString="123456";
		
		String codeString=RandNumber.getRandNumber(6);
		System.out.println(codeString);
		com.cyberoller.sms.MessageSender messageSender = new MessagePostSender(host, username, password, productid);
		String message = "亲爱的触键用户：手机验证码为"+codeString+"，请提交完成验证。如非本人操作，请忽略本短信。【触键】";
		try {
			messageSender.send("15021573917", message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		try {
//			//messageSender.send("15026891760", message);
//			List list=new ArrayList();
//			Map map=new HashMap();
//			map.put("C_CONTENT", 1);
//			map.put("2", 2);
//			map.put("3", 3);
//			map.put("4", 4);
//			list.add(map);
//			
//			List ss=jsonListToList(list);
//			System.out.println(ss.toString());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

	}
	
	public static List jsonListToList(List list) 
    {
        List returnsList = new ArrayList();
        for (int i = 0; i < list.size(); i++)
        {
            Map map = (Map) list.get(i);
            String aString=map.get("C_CONTENT").toString();
            map.put("C_CONTENT", aString);
            returnsList.add(map);
        }
        return returnsList;
    }

}
