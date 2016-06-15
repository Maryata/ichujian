/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sys.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/*
 * MD5 算法
*/
public class MD5 {
    
    // 全局数组
    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    public MD5() {
    }

    // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    // 返回形式只为数字
    private static String byteToNum(byte bByte) {
        int iRet = bByte;
        System.out.println("iRet1=" + iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        return String.valueOf(iRet);
    }

    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    public static String GetMD5Code(String strObj) {
        String resultString = null;
        try {
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }
    
    /**
	 * md5 加密:(默认UTF-8编码)
	 * 
	 * @param code
	 *            要加密的数据
	 * @return
	 */
	public static String md5Code(String code) {
		return md5Code(code, "UTF-8");
	}

	/**
	 * md5 加密:
	 * 
	 * @param code
	 *            要加密的数据
	 * @param charsetName
	 *            字符编码
	 * @return
	 */
	public static String md5Code(String code, String charsetName) {
		StringBuffer sb = new StringBuffer(32);
		if (code != null && !"".equals(code)) {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] array = md.digest(code.getBytes(charsetName));

				for (int i = 0; i < array.length; i++) {
					sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb.toString().toUpperCase();
	}

    public static void main(String[] args) {
        MD5 getMD5 = new MD5();
        System.out.println(getMD5.GetMD5Code("000000"));
    }
}
