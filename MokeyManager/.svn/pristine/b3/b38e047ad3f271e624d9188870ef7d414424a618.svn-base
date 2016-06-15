package com.org.mokey.formdesign.dao.impl;

import com.org.mokey.formdesign.dao.SqlStatmentFormat;

public class SqlStatmentFormatOracle implements SqlStatmentFormat {

	@Override
	public String PagedResult(String sql, int pagesize, int currentrow) {
	// 如果包含order by , order by 字段需要包含在Select中
		StringBuffer formatSql= new StringBuffer();
		
		String distinctStr = "";
		
		String loweredString = sql.toLowerCase();
		String sqlPartString = sql.trim();
		if (loweredString.trim().startsWith("select")) {
			int index = 6;		
			if (loweredString.startsWith("select distinct")) {
				distinctStr = "DISTINCT ";
				index = 15;
			}
			sqlPartString = sqlPartString.substring(index);
		}
		
		
		int lastrow = currentrow + pagesize + 1;
		String orderby = getOrderByPart(sql);
		if (orderby == null || orderby.length() == 0) {
			orderby = "order by CURRENT_TIMESTAMP";
		}
		formatSql.append(" Select * from ( " );
		formatSql.append(" Select ROW_NUMBER() OVER (" + orderby + ") AS  __row_number__ ,* from ( " );
		formatSql.append(" Select ");
		formatSql.append(distinctStr);
		formatSql.append(" TOP 100 PERCENT ");
		formatSql.append(sqlPartString);
		formatSql.append(" ) a ) b where ");
		formatSql.append(" __row_number__  >  "  + currentrow );
		formatSql.append(" and __row_number__  < " + lastrow ) ;
		
		return formatSql.toString();
	}

	@Override
	public String CountResult(String sql) {
		// 如果包含order by , order by 字段需要包含在Select中
		StringBuffer formatSql= new StringBuffer();
		
		String distinctStr = "";
		
		String loweredString = sql.toLowerCase();
		String sqlPartString = sql.trim();
		if (loweredString.trim().startsWith("select")) {
			int index = 6;		
			if (loweredString.startsWith("select distinct")) {
				distinctStr = "DISTINCT ";
				index = 15;
			}
			sqlPartString = sqlPartString.substring(index);
		}
		
		
		
		String orderby = getOrderByPart(sql);
		if (orderby == null || orderby.length() == 0) {
			orderby = "order by CURRENT_TIMESTAMP";
		}
		
		formatSql.append(" Select COUNT(*) from  " );
		formatSql.append(" ( Select ");
		formatSql.append(distinctStr);
		formatSql.append(" TOP 100 PERCENT ");
		formatSql.append(sqlPartString);
		formatSql.append(" ) a ");		
		
		
		return formatSql.toString();
	}
	
	private String getOrderByPart(String sql) {
		String loweredString = sql.toLowerCase();
		int orderByIndex = loweredString.indexOf("order by");
		if (orderByIndex != -1) {
			// if we find a new "order by" then we need to ignore
			// the previous one since it was probably used for a subquery
			return sql.substring(orderByIndex);
		} else {
			return "";
		}
	}

}
