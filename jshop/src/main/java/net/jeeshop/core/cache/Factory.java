package net.jeeshop.core.cache;

import java.lang.reflect.Constructor;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * singleton Factory  
 * @author giles
 */
public class Factory {
	// 定义一个Map用于保存第一次创建对象时的实例
    private static final Map<String, Object> singletonContainer = Maps.newHashMap();
 
    private Factory(){}
    
	
	/**
	 * 单例工厂创建
	 * @param c Class
	 * @return Class 单例对象
	 */
    @SuppressWarnings("unchecked")
	public synchronized static <T> T create(Class<T> c) {
        String className = c.getName();
        if (!singletonContainer.containsKey(className)) {
			try {
				// 获得无参构造
				 Constructor<?> constructor = c.getDeclaredConstructor();
				// 设置无参构造是可访问的 ;避免个别Class中无参构造私有化，无法newInstance
	            constructor.setAccessible(true);
	            // 产生一个实例对象。
	            singletonContainer.put(className, constructor.newInstance());
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        return (T) singletonContainer.get(className);
    }

}
