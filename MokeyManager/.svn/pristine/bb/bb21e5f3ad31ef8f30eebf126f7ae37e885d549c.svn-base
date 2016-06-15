package com.org.mokey.common.util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;


import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * 
 * 处理DAO的工具类
 * 
 * @author adam.sun
 * 
 */
public class DaoUtil {
	private static final Logger log = Logger.getLogger(DaoUtil.class);
	/**
	 * 把输入的字符串数组拼接为in 语句内容
	 * 
	 * @param strArray
	 *            字符串数组
	 * @param throwEmpty
	 *            对数组内的空null是否抱出异常
	 * @return 形如 ('ab','bc','cd')
	 */
	public static String buildInCase(String[] strArray, boolean throwEmpty) {
		if (strArray == null || strArray.length <= 0) {
			throw new IllegalArgumentException("The input args is empty");
		}
		String inCase = "(";
		for (int i = 0; i < strArray.length; i++) {
			if (strArray[i] == null || strArray[i].length() <= 0) {
				if (throwEmpty) { // 抛出异常
					throw new IllegalArgumentException(
							"The input args is empty");
				} else {
					continue; // 继续
				}
			}
			if (i == strArray.length - 1) { // 最后一行
				inCase += "'" + strArray[i] + "')";
			} else {
				inCase += "'" + strArray[i] + "',";
			}
		}
		if(inCase.length()>1){
			// 到这里一定有值， 把 ('a','b','c', 转换为 ('a','b','c')
			inCase = inCase.substring(0, inCase.length() - 1) + " )";
		}else{
			inCase +=")";
		}
		if("()".equals(inCase)){
			inCase ="('xxx')";
		}
		return inCase;
	}
	
	/**
	 * 把输入的字符串数组拼接为带?值的in 语句内容
	 * 
	 * @param strArray
	 *            字符串数组
	 * @param throwEmpty
	 *            对数组内的空null是否抱出异常
	 * @return 形如 ('ab','bc','cd')
	 */
	public static String buildInCaseAsQ(String[] strArray, boolean throwEmpty) {
		if (strArray == null || strArray.length <= 0) {
			throw new IllegalArgumentException("The input args is empty");
		}
		String inCase = "(";
		for (int i = 0; i < strArray.length; i++) {
			if (strArray[i] == null || strArray[i].length() <= 0) {
				if (throwEmpty) { // 抛出异常
					throw new IllegalArgumentException(
							"The input args is empty");
				} else {
					continue; // 继续
				}
			}
			if (i == strArray.length - 1) { // 最后一行
				inCase += "?)";
			} else {
				inCase += "?,";
			}
		}
		if(inCase.length()>1){
			// 到这里一定有值， 把 ('a','b','c', 转换为 ('a','b','c')
			inCase = inCase.substring(0, inCase.length() - 1) + " )";
		}else{
			inCase =inCase+")";
		}
		
		if("()".equals(inCase)){
			inCase ="(?)";
		}
		return inCase;
	}

	/**
	 * 把输入的字符串数组拼接为in 语句内容
	 * 
	 * @param strArray
	 *            一个容器
	 * @param throwEmpty
	 *            对数组内的空null是否抱出异常
	 * @return 形如 ('ab','bc','cd')
	 */
	@SuppressWarnings("unchecked")
	public static String buildInCase(Collection strArray, boolean throwEmpty) {
		if (strArray == null || strArray.size() <= 0) {
			throw new IllegalArgumentException("The input args is empty");
		}
		String inCase = "(";
		for (Iterator iterator = strArray.iterator(); iterator.hasNext();) {
			String index = (String) iterator.next();
			if (index == null || index.length() <= 0) {
				if (throwEmpty) { // 抛出异常
					throw new IllegalArgumentException(
							"The input args is empty");
				} else {
					continue; // 继续
				}
			}
			inCase += "'" + index + "',";

		}
		if(inCase.length()>1){
			// 到这里一定有值， 把 ('a','b','c', 转换为 ('a','b','c')
			inCase = inCase.substring(0, inCase.length() - 1) + " )";
		}else{
			inCase +=")";
		}
		if("()".equals(inCase)){
			inCase ="('xxx')";
		}
		return inCase;
	}
	
	
	/**
	 * 把输入的字符串数组拼接为in 语句内容
	 * 
	 * @param strArray
	 *            一个容器
	 * @param throwEmpty
	 *            对数组内的空null是否抱出异常
	 * @return 形如 ('ab','bc','cd')
	 */
	@SuppressWarnings("unchecked")
	public static String buildInCaseAsQ(Collection strArray, boolean throwEmpty) {
		if (strArray == null || strArray.size() <= 0) {
			throw new IllegalArgumentException("The input args is empty");
		}
		String inCase = "(";
		
		for (Iterator iterator = strArray.iterator(); iterator.hasNext();) {
			String index = (String) iterator.next();
			if (index == null || index.length() <= 0) {
				if (throwEmpty) { // 抛出异常
					throw new IllegalArgumentException(
							"The input args is empty");
				} else {
					continue; // 继续
				}
			}
			inCase += "?,";

		}
		
		if(inCase.length()>1){
			// 到这里一定有值， 把 ('a','b','c', 转换为 ('a','b','c')
			inCase = inCase.substring(0, inCase.length() - 1) + " )";
		}else{
			inCase +=")";
		}
		
		if("()".equals(inCase)){
			inCase ="(?)";
		}
		return inCase;
	}



	/**
	 * DB2的分页方法
	 * 
	 * @param sb
	 * @param start
	 * @param limit
	 * @return
	 */
	public static StringBuffer addfy_db2(StringBuffer sb, String order,
			String ascOrdesc, int start, int limit) {
		StringBuffer orderby = new StringBuffer();
		if (order != null) {
			orderby.append("ORDER BY ").append(order).append(" ").append(
					ascOrdesc);
		}
		if (start > 0 || limit > 0) {
			sb.insert(0, "SELECT * FROM (SELECT TEMP.*,ROWNUMBER() OVER("
					+ orderby.toString() + ") AS ROWNUM FROM (");
			sb.append(") AS TEMP) AS TABLEINSTA WHERE TABLEINSTA.ROWNUM ");
			boolean ff = false;
			if (start > 0) {
				sb.append(" > " + start);
				ff = true;
			}
			if (limit > 0) {
				if (ff) {
					sb.append(" and TABLEINSTA.ROWNUM ");
				}
				sb.append(" <= " + (start + limit));
			}
		}
		return sb;
	}

	/**
	 * DB2的分页方法
	 * 
	 * @param sb
	 * @param start
	 * @param limit
	 * @return
	 */
	public static StringBuffer addfy_db2(StringBuffer sb, String order,
			String ascOrdesc, int start, int limit, List args) {
		StringBuffer orderby = new StringBuffer();
		if (order != null) {
			orderby.append("ORDER BY ").append(order).append(" ").append(
					ascOrdesc);
		}
		if (start > 0 || limit > 0) {
			sb.insert(0, "SELECT * FROM (SELECT TEMP.*,ROWNUMBER() OVER("
					+ orderby.toString() + ") AS ROWNUM FROM (");
			sb.append(") AS TEMP) AS TABLEINSTA WHERE TABLEINSTA.ROWNUM ");
			boolean ff = false;
			if (start > 0) {
				sb.append(" > ?");
				args.add(start + "");
				ff = true;
			}
			if (limit > 0) {
				if (ff) {
					sb.append(" and TABLEINSTA.ROWNUM ");
				}
				sb.append(" <= ?");
				args.add(start + limit + "");
			}
		}
		return sb;
	}

	/**
	 * oracle的分页
	 * 
	 * @param sb
	 * @param start
	 * @param limit
	 * @param order
	 * @param ascOrdesc
	 * @return
	 */
	public static StringBuffer addfy_oracle(StringBuffer sb, String order,int start, int limit, List args) {
		StringBuffer orderby = new StringBuffer();
		if (order != null) {
			orderby.append(" ORDER BY ").append(order);
		}
		if (start > 0 || limit > 0) {
			sb.insert(0, "SELECT * FROM ( SELECT temp.* ,ROWNUM NUM  FROM (");
			sb.append(orderby);
			sb.append(") temp ");
			if (limit > 0) {
				if (start < 0) {
					start = 0;
				}
				sb.append(" WHERE ROWNUM <= ? )");
				args.add(start + limit);
			}
			if (start > 0) {
				sb.append(" where num > ? ");
				args.add(start);
			}
		}
		//log.debug("sb::" + sb.toString());
		return sb;
	}

	/**
	 * oracle的分页
	 * 
	 * @param sb
	 * @param start
	 * @param limit
	 * @return
	 */
	public static StringBuffer addfy_oracle(StringBuffer sb, int start,
			int limit) {
		if (start > 0 || limit > 0) {
			sb.insert(0, "SELECT * FROM ( SELECT temp.* ,ROWNUM num  FROM (");
			sb.append(") temp ");
			if (limit > 0) {
				if (start < 0) {
					start = 0;
				}
				sb.append(" WHERE ROWNUM <= " + (start + limit) + " )");
			}
			if (start > 0) {
				sb.append(" where num > " + start);
			}
		}
		return sb;
	}

	/**
	 * oracle的分页(含？对应的参数LIST)
	 * 
	 * @param sb
	 * @param start
	 * @param limit
	 * @return
	 */
	public static StringBuffer addfy_oracle(StringBuffer sb, int start,
			int limit, List args) {
		if (start > 0 || limit > 0) {
			sb.insert(0, "SELECT * FROM ( SELECT temp.* ,ROWNUM num  FROM (");
			sb.append(") temp ");
			if (limit > 0) {
				if (start < 0) {
					start = 0;
				}
				sb.append(" WHERE ROWNUM <= ?)");
				args.add(start + limit + "");
			}
			if (start > 0) {
				sb.append(" where num > ? ");
				args.add(start + "");
			}
		}
		return sb;
	}

    public static StringBuilder addfy_oracle(StringBuilder sb, int start,
                                            int limit, List args) {
        if (start > 0 || limit > 0) {
            sb.insert(0, "SELECT * FROM ( SELECT temp.* ,ROWNUM num  FROM (");
            sb.append(") temp ");
            if (limit > 0) {
                if (start < 0) {
                    start = 0;
                }
                sb.append(" ) WHERE num <= ?");
                args.add(start + limit + "");
            }
            if (start > 0) {
                sb.append(" and num >= ? ");
                args.add(start + "");
            }
        }
        return sb;
    }

	/**
	 * SQL server的分页方法
	 * 
	 * @param sb
	 * @param start
	 * @param limit
	 * @param order
	 *            必需有 用哪个字段排序
	 * @param ascOrdesc
	 *            必需有 升序还是降序
	 * @return
	 */
	public static StringBuffer addfy_SQLserver(StringBuffer sb, String order,
			String ascOrdesc, int start, int limit, List args) {
		if (order == null) {
			return sb;
		}
		StringBuffer orderby1 = new StringBuffer();
		StringBuffer orderby2 = new StringBuffer();
		orderby1.append(" ORDER BY ").append(order).append(" ").append(
				ascOrdesc);
		if ("asc".equalsIgnoreCase(ascOrdesc)) {
			orderby2.append(" ORDER BY ").append(order).append(" Desc");
		} else {
			orderby2.append(" ORDER BY ").append(order).append(" asc");
		}
		if (start >= 0 && limit > 0) {
			sb.insert(0, "select TOP " + limit
					+ " * FROM (SELECT *,ROW_NUMBER() Over("
					+ orderby1.toString() + ") as rowNum FROM (");
			sb.append(") as temp) as moduleTable where rowNum > ? ");
			// 添加第一个参数
			args.add(start);
		}
		return sb;
	}

	/**
	 * SQL server的分页方法
	 * 
	 * @param sb
	 * @param start
	 * @param limit
	 * @param order
	 *            必需有 用哪个字段排序
	 * @param ascOrdesc
	 *            必需有 升序还是降序
	 * @return
	 */
	public static StringBuffer addfy_SQLserver(StringBuffer sb, String order,
			String ascOrdesc, int start, int limit) {
		if (order == null) {
			return sb;
		}

		StringBuffer orderby1 = new StringBuffer();
		StringBuffer orderby2 = new StringBuffer();
		orderby1.append(" ORDER BY ").append(order).append(" ").append(
				ascOrdesc);
		if ("asc".equalsIgnoreCase(ascOrdesc)) {
			orderby2.append(" ORDER BY ").append(order).append(" Desc");
		} else {
			orderby2.append(" ORDER BY ").append(order).append(" asc");
		}
		if (start >= 0 && limit > 0) {
			sb.insert(0, "select TOP " + limit
					+ " * FROM (SELECT *,ROW_NUMBER() Over("
					+ orderby1.toString() + ") as rowNum FROM (");
			sb
					.append(") as temp) as moduleTable where rowNum > " + start
							+ " ");
		}
		return sb;
	}


	/**
	 * 根据?值列表将带问号的SQL语句转换成可直接执行的sql语句
	 * 
	 * @param str
	 * @param args
	 * @return
	 */
	public static String converseQesmarkSQL(String str, Object[] args) {
		String result = str;
		if (result != null) {
			for (int i = 0; i < args.length; i++) {
				Object str1 = args[i];
				result = result.replaceFirst("\\?",Matcher.quoteReplacement("'" + str1 + "'"));//参数有特殊字符时会报异常
			}
		}
		return result;
	}
	/**
	 * 根据?值列表将带问号的SQL语句转换成可直接执行的sql语句
	 * 
	 * @param str
	 * @param args
	 * @return
	 */
	public static String converseQesmarkSQL(String str, List args) {
		String result = str;
		if (result != null) {
			for (int i = 0; i < args.size(); i++) {
				Object str1 = args.get(i);
				result = result.replaceFirst("\\?",Matcher.quoteReplacement("'" + str1 + "'"));
			}
		}
		return result;
	}
	
}



