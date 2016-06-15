package com.sys.util.encrypt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.EnumerationUtils;

public class ParameterEncryptor {
	
	//秘钥头
	private static String secretKeyBegin = "cjcc";
	//秘钥尾
	private static String secretKeyEnd = "qjkjxxjsyxgs";

	/**
	 * 请求参数 (去除：sign) 加密
	 * @param request
	 * @return string 加密后的值
	 */
	@SuppressWarnings("unchecked")
	public static String encrypt(HttpServletRequest request) {
		Enumeration<String> en = request.getParameterNames();
		List<String> list = EnumerationUtils.toList(en);
		Collections.sort(list);
		
		StringBuilder sb = new StringBuilder(secretKeyBegin);
		for (String str : list) {
			if(!"sign".equals(str) ) {//sign ;signature !"sign".equals(str) && !"userhead".equals(str)
				//过滤掉空值参数
				if(null==request.getParameter(str)){
					continue;
				}
				sb.append(str).append(request.getParameter(str));
			}
		}
		sb.append(secretKeyEnd);
		return MD5.md5Code(sb.toString());
	}

	/**
	 * 请求参数 (去除：sign) 加密
	 * @param map
	 * @return string 加密后的值
	 * @throws Exception 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String encrypt(Map map) throws Exception {
		List<String> list = new ArrayList<String>(map.keySet());
		Collections.sort(list);
		StringBuilder sb = new StringBuilder(secretKeyBegin);
		Object v = null;
		for (String str : list) {
			//if("sign".equals(str) == false) {//sign ;signature
			//过滤掉空值参数
			v = map.get(str);
			if(null==v){
				continue;
			}
			//参数base64加密
			//v = Base64.encode(String.valueOf(v));
			sb.append(str).append(v);
			//map.put(str, v);
			//}
		}
		sb.append(secretKeyEnd);
		//添加sign参数
		String sign = MD5.md5Code(sb.toString());
		//map.put("sign", sign);
		return sign;
	}
	
	public static void main(String[] args) throws Exception {
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("version", 3);
		m.put("sourec", "ichujian");
		encrypt(m);
		System.out.println(m);
	}
	
}
