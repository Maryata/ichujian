package com.org.mokey.analyse.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/3/9.
 */
public class Singletons {
    private static Singletons instance;
    private Singletons(){}
    public static Singletons getInstance() {
    if (instance == null) {
        instance = new Singletons();
         }
     return instance;
       }

    private Map<String,Integer> map = new HashMap<String,Integer>();

    public void put(String key, Integer value){
        map.put(key, value);
    }

        public Integer get(String key){
        return map.get(key);
    }

    public Map<String, Integer> getMap() {
        return map;
    }

    public void setMap(Map<String, Integer> map) {
        this.map = map;
    }

    private Map<String,List<List<Object>>> baseMap = new HashMap<String,List<List<Object>>>();

    public Map<String, List<List<Object>>> getBaseMap() {
        return baseMap;
    }

    public void setBaseMap(Map<String, List<List<Object>>> baseMap) {
        this.baseMap = baseMap;
    }

    public Map<String, List<Object>> getTotalMap() {
        return totalMap;
    }

    public void setTotalMap(Map<String, List<Object>> totalMap) {
        this.totalMap = totalMap;
    }

    private Map<String,List<Object>> totalMap = new HashMap<String,List<Object>>();
    public void put0(String key, List<List<Object>> value){
        baseMap.put(key, value);
    }

    public List<List<Object>> get0(String key){
        return baseMap.get(key);
    }
    public void put1(String key, List<Object> value){
        totalMap.put(key, value);
    }

    public List<Object> get1(String key){
        return totalMap.get(key);
    }
}
