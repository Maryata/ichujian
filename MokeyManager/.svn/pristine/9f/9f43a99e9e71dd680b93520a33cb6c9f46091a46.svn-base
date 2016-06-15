package com.org.mokey.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import org.apache.log4j.Logger;

/**
 * 汉字转换位汉语拼音，英文字符不变
 * @author xuke
 */
public class Cn2Spell {
	
	private static final Logger log = Logger.getLogger(Cn2Spell.class);
 
    /**
    * 汉字转换位汉语拼音首字母，英文字符不变
    * @param chines 汉字
    * @return 拼音
    */
    public static String converterToFirstSpell(String chines){   
    	try {
			chines = FullCharConverter.full2HalfChange(chines);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        String pinyinName = "";
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0].charAt(0);
                } catch (Exception e) {
                    log.error("error char:"+nameChar[i]);
                }
            }else{
            	pinyinName += nameChar[i];
            }
        }
        return pinyinName;
    }
 
    public static String converterToSpellTrim(String chines){
    	if(null==chines ||chines.trim().length()==0){
    		return "";
    	}
    	log.debug("converterToSpellTrim.chines:"+chines);
    	try {
    		chines = StringFilter(chines);
    		chines = FullCharConverter.full2HalfChange(chines);
		} catch (Exception e) {
			log.error("converterToSpellTrim.chines["+chines+"]", e);
		}
    	chines = chines.replaceAll("\\s*", "");
    	return converterToSpell(chines);
    }
    
 // 过滤特殊字符
 	public static String StringFilter(String str) {
 		// 只允许字母和数字
 		// String regEx = "[^a-zA-Z0-9]";
 		// 清除掉所有特殊字符
 		//String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
 		String regEx = "[`~!@#$%^&*+=|{}':;',\\[\\]<>/?~！@#￥%……&*——+|{}【】‘；：”“’。，、？《》™]";
 		Pattern p = Pattern.compile(regEx);
 		Matcher m = p.matcher(str);
 		return m.replaceAll("").trim();
 	}
    
    /**
    * 汉字转换位汉语拼音，英文字符不变
    * @param chines 汉字
    * @return 拼音
    */
    public static String converterToSpell(String chines){    
    	log.debug("converterToSpell.chines.init:"+chines);
        String pinyinName = "";
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0];
                } catch (Exception e) {
                	log.error( chines );
                    log.error("error char:"+nameChar[i]);
                }
            }else{
            	pinyinName += nameChar[i];
            }
        }
        log.debug("converterToSpell.chines.out:"+pinyinName);
        return pinyinName;
    }
    
    public static void main(String[] args) {
    	String chines="《》芭比波朗（Bobbi Brown）.jsppng？";
		//System.out.println(converterToFirstSpell(chines));
		//System.out.println(converterToSpell(chines));
		System.out.println(converterToSpellTrim(chines));
		
	}
}
