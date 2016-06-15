/**
 * 
 */
package com.org.mokey.demo.vo;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
/**
 * @author vpc
 *
 */
public class Pagination implements java.io.Serializable {
	private static final long serialVersionUID = -8405359681766491279L;

	/**
	 * 每页显示的记录数
	 */
	private int numPerPage;

	/**
	 * 记录总数
	 */
	private int total;

	/**
	 * 总页数
	 */
	private int totalPages;

	/**
	 * 当前页码
	 */
	private int currentPage;

	/**
	 * 记录起始行数
	 */
	private int startIndex;

	/**
	 * 记录结束行数
	 */
	private int lastIndex;

	/**
	 * 结果集存放List
	 */
	private List<Map<String, Object>> rows;

	/**
	 * 构造函数
	 * 
	 * @param sql
	 *            sql语句
	 * @param currentPage
	 *            当前页码
	 * @param numPerPage
	 *            每页显示记录数
	 * @param jdbcTemplate
	 *            JdbcTemplate实例
	 */
	public Pagination(String sql, int currentPage, int numPerPage,
			JdbcTemplate jdbcTemplate) {
		if ( jdbcTemplate == null ) {
			throw new IllegalArgumentException(
					"jdbcTemplate is null , pls initialize ... " );
		} else if ( StringUtils.isBlank(sql) ) {
			throw new IllegalArgumentException(
					"sql is blank , pls initialize ... " );
		}
		// 设置每页显示记录数
		setNumPerPage( numPerPage );

		// 设置当前页数
		setCurrentPage( currentPage );

		// 计算总记录数SQL
		StringBuffer totalSQL = new StringBuffer( " select count(1) from ( " );
		totalSQL.append( sql );
		totalSQL.append( " ) " );

		// 总记录数
		setTotal( jdbcTemplate.queryForObject( totalSQL.toString(), Integer.class ) );

		// 计算总页数
		setTotalPages();

		// 计算起始行数
		setStartIndex();

		// 计算结束行数
		setLastIndex();

		// 拼装oracle的分页语句 （其他DB修改此处的分页关键词即可）
		StringBuffer paginationSQL = new StringBuffer( " select * from ( " );
		paginationSQL.append( " select row_limit.*,rownum rownum_ from ( " );
		paginationSQL.append( sql );
		paginationSQL.append( "　) row_limit where rownum <= " + lastIndex );
		paginationSQL.append( " ) where　rownum_ > " + startIndex );

		setRows( jdbcTemplate.queryForList( paginationSQL.toString() ) );
	}

	/**
	 * 根据总记录数和每页显示记录数 计算总页数
	 * 
	 * @see
	 */
	private void setTotalPages() {
		if ( total % numPerPage == 0 ) {
			this.totalPages = total / numPerPage;
		} else {
			this.totalPages = (total / numPerPage) + 1;
		}
	}

	/**
	 * 根据当前页和每页显示记录条数 计算记录开始行数
	 * 
	 * @see
	 */
	private void setStartIndex() {
		this.startIndex = (currentPage - 1) * numPerPage;
	}

	/**
	 * 计算记录结束行数
	 * 
	 * @see
	 */
	private void setLastIndex() {
		if ( total < numPerPage ) {
			this.lastIndex = total;
		} else if ( (total % numPerPage == 0)
				|| (total % numPerPage != 0 && currentPage < totalPages) ) {
			this.lastIndex = currentPage * numPerPage;
		} else if ( total % numPerPage != 0 && currentPage == totalPages ) {
			this.lastIndex = total;
		}
	}

	// setter and getter
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	public List<Map<String, Object>> getRows() {
		return rows;
	}

	public void setRows(List<Map<String, Object>> rows) {
		this.rows = rows;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public int getLastIndex() {
		return lastIndex;
	}

}
