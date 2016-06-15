package com.org.mokey.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class FullCharConverter {

	public static void main(String[] args) throws UnsupportedEncodingException {

		// 全角转半角

		String QJstr = "hello!！ 全角转换，ＤＡＯ ５３２３２　";

		String result = full2HalfChange(QJstr);

		System.out.println(QJstr);

		System.out.println(result);

		System.out.println("------------------------------------");

		// 半角转全角

		String str = "java 汽车 召回 2345";

		System.out.println(str);

		System.out.println(half2Fullchange(str));

	}

	// 过滤特殊字符
	public static String StringFilter(String str) throws PatternSyntaxException {
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	// 全角转半角的 转换函数

	public static final String full2HalfChange(String QJstr)
			throws UnsupportedEncodingException {

		StringBuffer outStrBuf = new StringBuffer("");

		String Tstr = "";

		byte[] b = null;

		for (int i = 0; i < QJstr.length(); i++) {

			Tstr = QJstr.substring(i, i + 1);

			// 全角空格转换成半角空格

			if (Tstr.equals("　")) {

				outStrBuf.append(" ");

				continue;

			}

			b = Tstr.getBytes("unicode");

			// 得到 unicode 字节数据

			if (b[2] == -1) {

				// 表示全角？

				b[3] = (byte) (b[3] + 32);

				b[2] = 0;

				outStrBuf.append(new String(b, "unicode"));

			} else {

				outStrBuf.append(Tstr);

			}

		} // end for.

		return outStrBuf.toString();

	}

	// 半角转全角

	public static final String half2Fullchange(String QJstr)
			throws UnsupportedEncodingException {

		StringBuffer outStrBuf = new StringBuffer("");

		String Tstr = "";

		byte[] b = null;

		for (int i = 0; i < QJstr.length(); i++) {

			Tstr = QJstr.substring(i, i + 1);

			if (Tstr.equals(" ")) {

				// 半角空格

				outStrBuf.append(Tstr);

				continue;

			}

			b = Tstr.getBytes("unicode");

			if (b[2] == 0) {

				// 半角?

				b[3] = (byte) (b[3] - 32);

				b[2] = -1;

				outStrBuf.append(new String(b, "unicode"));

			} else {

				outStrBuf.append(Tstr);

			}

		}

		return outStrBuf.toString();

	}

}
