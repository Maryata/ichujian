package com.org.mokey.basedata.sysinfo.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vpc on 2016/3/9.
 */
public class Singleton {
    private static Singleton instance;
    private Singleton (){}
    public static Singleton getInstance() {
    if (instance == null) {
        instance = new Singleton();
         }
     return instance;
       }


    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    private Map<String,String> map = new HashMap<String,String>();

    private Map<String,String> memberInfoMap = new HashMap<String,String>();//未注册飞语用户

    private Map<String,String> Maps = new HashMap<String,String>();//飞语用户异常

    public Map<String, String> getMemberInfoMap() {
        return memberInfoMap;
    }

    public void setMemberInfoMap(Map<String, String> memberInfoMap) {
        this.memberInfoMap = memberInfoMap;
    }




    public void put(String key, String value){
        memberInfoMap.put(key, value);
    }

    public Object get(String key){
        return Maps.get(key);
    }

    public void puts(String key, String value){
        Maps.put(key, value);
    }

    public Object gets(String key){
        return memberInfoMap.get(key);
    }

    public Map<String, String> getMaps() {
        return Maps;
    }

    public void setMaps(Map<String, String> maps) {
        Maps = maps;
    }
}
