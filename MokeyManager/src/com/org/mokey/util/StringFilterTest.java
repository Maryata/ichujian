package com.org.mokey.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class StringFilterTest {
	
	// 过滤特殊字符
	public String StringFilter(String str){
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	@Test
	public void testStringFilter(){
		String str = "~！@#￥%……&*（））——+{}：”《》？(长城)*?%%*(*.中国}34{45[]12.fd'*&999下面是中文的字符￥……{}【】。，；’“‘”？";
		System.out.println(str);
		System.out.println(StringFilter(str));
	}
}
