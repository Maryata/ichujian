package com.org.mokey.common.util.number;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @function
 * @author <a href="mailto:zylgang@sina.com">liugang</a>
 * @create 2011-12-8
 * @version 1.0
 * @logs 2011-12-8
 */
public class NumberUtil {
	
	/**
	 * 随机小数
	 * @param size
	 * @return
	 */
	public static int roundInt(int size){
		int[] array = {1,2,3,4,5,6,7,8,9,0};
		Random rand = new Random();
		for (int i = 10; i > 1; i--) {
			int index = rand.nextInt(i);
			int tmp = array[index];
			array[index] = array[i - 1];
			array[i - 1] = tmp;
		}
		int result = 0;
		//String max="1";
		for(int i = 0; i < size; i++){
			result = result * 10 + array[i];
			//max += "0";
		}
		return result;
	}

	// 生成不重复随机数1
	/**
	 * 根据给定的最小数字和最大数字，以及随机数的个数，产生指定的不重复的数组
	 * 
	 * @param begin
	 *            最小数字（包含该数）
	 * @param end
	 *            最大数字（不包含该数）
	 * @param size
	 *            指定产生随机数的个数
	 */
	public int[] generateRandomNumber(int begin, int end, int size) {
		// 加入逻辑判断，确保begin<end并且size不能大于该表示范围
		if (begin >= end || (end - begin) < size) {
			return null;
		}
		// 种子你可以随意生成，但不能重复
		int[] seed = new int[end - begin];

		for (int i = begin; i < end; i++) {
			seed[i - begin] = i;
		}
		int[] ranArr = new int[size];
		Random ran = new Random();
		// 数量你可以自己定义。
		for (int i = 0; i < size; i++) {
			// 得到一个位置
			int j = ran.nextInt(seed.length - i);
			// 得到那个位置的数值
			ranArr[i] = seed[j];
			// 将最后一个未用的数字放到这里
			seed[j] = seed[seed.length - 1 - i];
		}
		return ranArr;
	}

	// 生成不重复随机数2
	/**
	 * 根据给定的最小数字和最大数字，以及随机数的个数，产生指定的不重复的数组
	 * 
	 * @param begin
	 *            最小数字（包含该数）
	 * @param end
	 *            最大数字（不包含该数）
	 * @param size
	 *            指定产生随机数的个数
	 */
	public Integer[] generateBySet(int begin, int end, int size) {
		// 加入逻辑判断，确保begin<end并且size不能大于该表示范围
		if (begin >= end || (end - begin) < size) {
			return null;
		}

		Random ran = new Random();
		Set<Integer> set = new HashSet<Integer>();
		while (set.size() < size) {
			set.add(begin + ran.nextInt(end - begin));
		}

		Integer[] ranArr = new Integer[size];
		ranArr = set.toArray(new Integer[size]);
		// ranArr = (Integer[]) set.toArray();

		return ranArr;
	}

	/**
	 * 判断String是否是整数
	 */
	public boolean isInteger(String s) {
		if ((s != null) && (s != ""))
			return s.matches("^[0-9]*$");
		else
			return false;
	}

	/**
	 * 判断字符串是否是浮点数
	 */
	public boolean isDouble(String value) {
		try {
			Double.parseDouble(value);
			if (value.contains("."))
				return true;
			return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 判断字符串是否是数字
	 */
	public boolean isNumber(String value) {
		return isInteger(value) || isDouble(value);
	}

	// 排序方法
	public static void sort(int[] array) {// 小到大的排序
		int temp = 0;
		for (int i = 0; i < array.length; i++) {
			for (int j = i; j < array.length; j++) {
				if (array[i] > array[j]) {
					temp = array[i];
					array[i] = array[j];
					array[j] = temp;
				}
			}
		}
	}

	/**
	 * 是否是质数
	 */
	public static boolean isPrimes(int n) {
		for (int i = 2; i <= Math.sqrt(n); i++) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 阶乘
	 * 
	 * @param n
	 * @return
	 */
	public static int factorial(int n) {
		if (n == 1) {
			return 1;
		}
		return n * factorial(n - 1);
	}

	/**
	 * 平方根算法
	 * 
	 * @param x
	 * @return
	 */
	public static long sqrt(long x) {
		long y = 0;
		long b = (~Long.MAX_VALUE) >>> 1;
		while (b > 0) {
			if (x >= y + b) {
				x -= y + b;
				y >>= 1;
				y += b;
			} else {
				y >>= 1;
			}
			b >>= 2;
		}
		return y;
	}

	private int math_subnode(int selectNum, int minNum) {
		if (selectNum == minNum) {
			return 1;
		} else {
			return selectNum * math_subnode(selectNum - 1, minNum);
		}
	}

	private int math_node(int selectNum) {
		if (selectNum == 0) {
			return 1;
		} else {
			return selectNum * math_node(selectNum - 1);
		}
	}

	/**
	 * 可以用于计算双色球、大乐透注数的方法 selectNum：选中了的小球个数 minNum：至少要选中多少个小球
	 * 比如大乐透35选5可以这样调用processMultiple(7,5); 就是数学中的：C75=7*6/2*1
	 */
	public int processMultiple(int selectNum, int minNum) {
		int result;
		result = math_subnode(selectNum, minNum)
				/ math_node(selectNum - minNum);
		return result;
	}

	// 求m和n的最大公约数
	public static int gongyue(int m, int n) {
		while (m % n != 0) {
			int temp = m % n;
			m = n;
			n = temp;
		}
		return n;
	}

	/**
	 * 求两数的最小公倍数
	 */
	public static int gongbei(int m, int n) {
		return m * n / gongyue(m, n);
	}

	/**
	 * 递归求两数的最大公约数
	 */
	public static int divisor(int m, int n) {
		if (m % n == 0) {
			return n;
		} else {
			return divisor(n, m % n);
		}
	}

	/**
	 * 计算2个数的%比；保留2位小数
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static String divRateToStr(int v1, int v2) {
		double v = 0D;
		if (v1 != 0 && v2 != 0) {
			v = v1 * 1.0 / v2 * 100.0;
		}
		return String.format("%.2f", v);//"%"
	}
	
	/**
	 * 相除保留小数
	 * @param v1
	 * @param v2
	 * @param len
	 * @return
	 */
	public static String divToStr(int v1, int v2,int len) {
		if (v1 != 0 && v2 != 0) {
			if(v1%v2>0){
				double v = v1 * 1.0 / v2 * 1.0;
				BigDecimal bg = new BigDecimal(v);
		        v = bg.setScale(len, BigDecimal.ROUND_HALF_UP).doubleValue();
		        return String.valueOf(v);
			}else{
				return String.valueOf(v1/v2);
			}
		}
		return "0";
	}
	
	/**
	 * 计算2个数的%比；保留2位小数
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static String divRateToStr(Long v1, Long v2) {
		double v = 0D;
		if (v1 != 0 && v2 != 0) {
			v = v1 * 1.0 / v2 * 100.0;
		}
		return String.format("%.2f", v);//"%"
	}
	
	public static String rate(double v,int len) {
		if (v != 0) {
			BigDecimal bg = new BigDecimal(v);
	        v = bg.setScale(len, BigDecimal.ROUND_HALF_UP).doubleValue();
	        return String.valueOf(v);
		}
		return "0";
	}

	public static void main(String[] args) {
		for(int i=0;i<100;i++)
		System.out.println( roundInt(6));
	}

}
