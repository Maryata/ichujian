package com.sys.util.encrypt;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//import com.sys.util.Base64;

public class IchujianEncrypt {

	private static String key="ichujian";
	
	long timeDate= new Date().getTime();
	
	public static Map SetMap() {
		Map maps =new HashMap();
		maps.put("user", "张三");
		maps.put("password", "123456");
		maps.put("age", "100");
		maps.put("sex", "1");
		return maps;
	}
	
	public static List setList(Map<String, List> maps){
		List list=new ArrayList();
		for(Map.Entry<String, List> m:maps.entrySet()){
			list.add(m);
		}
		//Collections.sort(list);
		for (int i = 0; i < list.size(); i++) {
			Map mmMap=(Map) list.get(i);
			System.out.println(mmMap);
		}
		return list;
	}
	
	public static void main(String[] args){
		String codeString=key+new Date().getTime();
		try {
			/*String ss= Base64.encode(codeString);
			String sss= Base64.decode(ss);
			System.out.println(codeString);
			System.out.println(ss);
			System.out.println(sss);*/
			
			
			Map map=SetMap();
			setList(map);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
