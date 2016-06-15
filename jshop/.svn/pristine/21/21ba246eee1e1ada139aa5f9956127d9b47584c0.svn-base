package net.jeeshop.core.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author giles.wu
 * @version 1.0 通用排序
 */
public class SortList<E> {
	public void Sort(List<E> list, final String method, final String sort) {
		Collections.sort(list, new Comparator<E>() {
			public int compare(Object a, Object b) {
				int ret = 0;
				try {
					Method m1 = a.getClass().getMethod(method, null);
					Method m2 = b.getClass().getMethod(method, null);
					if (sort != null && "desc".equals(sort))// 倒序
						ret = String.valueOf(m2.invoke(b, null)).compareTo(String.valueOf(m1.invoke(a, null)));
					else// 正序
						ret = String.valueOf(m1.invoke(b, null)).compareTo(String.valueOf(m2.invoke(a, null)));
				} catch (NoSuchMethodException ne) {
					System.out.println(ne);
				} catch (IllegalAccessException ie) {
					System.out.println(ie);
				} catch (InvocationTargetException it) {
					System.out.println(it);
				}
				return ret;
			}
		});
	}
	
}
