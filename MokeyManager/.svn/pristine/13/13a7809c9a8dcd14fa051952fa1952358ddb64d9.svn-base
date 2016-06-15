package com.org.mokey.basedata.sysinfo.util;

import com.google.common.collect.Maps;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created by vpc on 2016/4/11.
 */
public class Global {
    public static Logger log = LoggerFactory.getLogger(Global.class);

    /**
     * 当前对象实例
     */
    private static Global global = new Global();

    /**
     * 保存全局属性值
     */
    private static Map<String, String> map = Maps.newHashMap();

    /**
     * 属性文件加载对象
     */
    private static PropertiesLoader loader = new PropertiesLoader("system.properties");


    /**
     * 获取配置
     *
     */
    public static String getConfig(String key) {
        String value = map.get(key);
        if (value == null){
            value = loader.getProperty(key);
            map.put(key, value != null ? value : StringUtils.EMPTY);
        }
        return value;
    }

    public static void main(String[] args){
        System.out.println(getConfig("appId"));
    }


}
