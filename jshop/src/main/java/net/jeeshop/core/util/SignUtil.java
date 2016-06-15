package net.jeeshop.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.slf4j.LoggerFactory;

import com.chinapay.secss.SecssUtil;

public class SignUtil {
	
//	private static String verifyFilePathKey = "verify.file";
//	private static String certFilePath = "sign.filePath";
//	private static String signFile = "sign.file";
//	private static String signPwd = "sign.file.password";
//	private static String signCertType ="sign.cert.type";
//	private static String signInvalidFields="sign.invalid.fields";
//	private static String signatureField = "signature.field";
	
	//private static SecssUtil secssUtil = null;
	/*static{
		 * 初始化security.properties属性文件
		 * 
		secssUtil = new SecssUtil();
		InputStream is = PathUtil.class.getResourceAsStream("/security.properties");
		Properties p = new Properties();
		try {
			p.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		p.setProperty("", "");
		secssUtil.init(p);
	}*/
	
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(SignUtil.class);
	
	public static SecssUtil init(Object MerId){
		/*
		 * 初始化security.properties属性文件
		 * 
		 */
		String rootPath = StreamUtil.getRootPath()+"WEB-INF/classes/payconfig/";
		SecssUtil secssUtil = new SecssUtil();
		InputStream is = PathUtil.class.getResourceAsStream("/security.properties");
		Properties p = new Properties();
		try {
			p.load(is);
			p.setProperty("verify.file",rootPath+p.getProperty("verify.file"));
			p.setProperty("sign.file", rootPath+p.getProperty("sign.file_S")+MerId+p.getProperty("sign.file_E"));
			log.info("SecssUtil.init------:"+p.getProperty("sign.file"));
		} catch (IOException e) {
			log.error("SecssUtil.init set sign.file failed.!!!!!=========",e);
		}
		
		secssUtil.init(p);
		return secssUtil;
	}
	
	public static String sign(Map signMap){
		//secssUtil = new SecssUtil();
		//secssUtil.init();
		SecssUtil secssUtil = init(signMap.get("MerId"));
		secssUtil.sign(signMap);
		
		log.info("sign ErrCode:{} , ErrMsg:{}",secssUtil.getErrCode(),secssUtil.getErrMsg());
		
		return secssUtil.getSign();
	}
	
	public static boolean verify(Map map){
		SecssUtil secssUtil = init(map.get("MerId"));
		secssUtil.verify(map);
		if("00".equals(secssUtil.getErrCode()))
			return true;
		return false;
	}
	
	public static String decryptData(String encData,String MerId){
		log.info("decryptData.encData :"+encData);
		SecssUtil secssUtil = init(MerId);
		secssUtil.decryptData(encData);
		return secssUtil.getSign();
	}
	
	/**
	 * 构造HTTP POST交易表单的方法示例
	 * @param action 表单提交地址
	 * @param hiddens 以MAP形式存储的表单键值
	 * @return 构造好的HTTP POST交易表单
	 */
	public static String createHtml(String action, Map<String, String> hiddens) {
		StringBuffer sf = new StringBuffer();
		sf.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/></head><body>");
		sf.append("<form id = \"pay_form\" action=\"" + action
				+ "\" method=\"post\">");
		if (null != hiddens && 0 != hiddens.size()) {
			Set<Entry<String, String>> set = hiddens.entrySet();
			Iterator<Entry<String, String>> it = set.iterator();
			while (it.hasNext()) {
				Entry<String, String> ey = it.next();
				String key = ey.getKey();
				String value = ey.getValue();
				sf.append("<input type=\"hidden\" name=\"" + key + "\" id=\""
						+ key + "\" value=\"" + value + "\"/><br>");
			}
		}
		sf.append("</form>");
		sf.append("</body>");
		sf.append("<script type=\"text/javascript\">");
		sf.append("document.all.pay_form.submit();");
		sf.append("</script>");
		sf.append("</html>");
		return sf.toString();
	}
}
