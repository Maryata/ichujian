package net.jeeshop.core.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 常见的辅助类
 * 
 * @author 
 * @since 2009-07-15
 */
public class StrUtils {

	/**
	 * 判断对象是否Empty(null或元素为0)<br>
	 * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
	 * 
	 * @param pObj
	 *            待检查对象
	 * @return boolean 返回的布尔值
	 */
	public static boolean isEmpty(Object pObj) {
		if (pObj == null)
			return true;
		if (pObj == "")
			return true;
		if (pObj instanceof String) {
			if (((String) pObj).length() == 0) {
				return true;
			}
		} else if (pObj instanceof Collection) {
			if (((Collection<?>) pObj).size() == 0) {
				return true;
			}
		} else if (pObj instanceof Map) {
			if (((Map<?,?>) pObj).size() == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断对象是否为NotEmpty(!null或元素>0)<br>
	 * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
	 * 
	 * @param pObj
	 *            待检查对象
	 * @return boolean 返回的布尔值
	 */
	public static boolean isNotEmpty(Object pObj) {
		if (pObj == null)
			return false;
		if (pObj == "")
			return false;
		if (pObj instanceof String) {
			if (((String) pObj).trim().length() == 0) {
				return false;
			}
		} else if (pObj instanceof Collection) {
			if (((Collection<?>) pObj).size() == 0) {
				return false;
			}
		} else if (pObj instanceof Map) {
			if (((Map<?,?>) pObj).size() == 0) {
				return false;
			}
		}
		return true;
	}
	
	/** 
     * 生成以中心点为中心的四方形经纬度 
     *  
     * @param lat 纬度 
     * @param lon 精度 
     * @param raidus 半径（以米为单位） 
     * @return 
     */  
    public static double[] getAround(double lat, double lon, int raidus) {  
  
        Double latitude = lat;  
        Double longitude = lon;  
  
        Double degree = (24901 * 1609) / 360.0;  
        double raidusMile = raidus;  
  
        Double dpmLat = 1 / degree;  
        Double radiusLat = dpmLat * raidusMile;  
        Double minLat = latitude - radiusLat;  
        Double maxLat = latitude + radiusLat;  
  
        Double mpdLng = degree * Math.cos(latitude * (Math.PI / 180));  
        Double dpmLng = 1 / mpdLng;               
        Double radiusLng = dpmLng * raidusMile;   
        Double minLng = longitude - radiusLng;    
        Double maxLng = longitude + radiusLng;    
        return new double[] { minLat, minLng, maxLat, maxLng };  
    }

	
	/**
	 * 方法名称:transStringToMap
	 * 传入参数:mapString 形如 username'chenziwen^password'1234
	 * 返回值:Map
	*/
	public static Map<String,Object> transStringToMap(String mapString,String spExt){
	  Map<String,Object> map = new HashMap<String,Object>();
	  java.util.StringTokenizer items;
	  for(StringTokenizer entrys = new StringTokenizer(mapString, spExt);entrys.hasMoreTokens(); 
	    map.put(items.nextToken(), items.hasMoreTokens() ? ((Object) (items.nextToken())) : null))
	      items = new StringTokenizer(entrys.nextToken(), "=");
	  return map;
	}

	
	/**
	 * 方法名称:transMapToString
	 * 传入参数:map
	 * 返回值:String 形如 username'chenziwen^password'1234
	*/
	public static String transMapToString(Map<?,?> map){
	  java.util.Map.Entry<?,?> entry;
	  StringBuffer sb = new StringBuffer();
	  for(Iterator<?> iterator = map.entrySet().iterator(); iterator.hasNext();)
	  {
	    entry = (java.util.Map.Entry<?,?>)iterator.next();
	      sb.append(entry.getKey().toString()).append( "'" ).append(null==entry.getValue()?"":
	      entry.getValue().toString()).append (iterator.hasNext() ? "^" : "");
	  }
	  return sb.toString();
	}

	/**
	 * 为空则返回空字符串，否则toString
	 * @param obj
	 * @return
	 */
	public static String emptyOrString(Object obj){
		return obj==null ? "" : obj.toString();
	}
	

}