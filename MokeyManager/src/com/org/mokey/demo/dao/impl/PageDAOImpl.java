/**
 * 
 */
package com.org.mokey.demo.dao.impl;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.org.mokey.demo.dao.PageDAO;
import com.org.mokey.demo.vo.Pagination;

/**
 * @author vpc
 *
 */
@Repository("pageDAO")
public class PageDAOImpl implements PageDAO {
	@Resource
	private JdbcTemplate jdbcTemplate;

	/* (non-Javadoc)
	 * @see com.org.mokey.demo.dao.PageDAO#page(java.lang.String, int, int, java.lang.Object[])
	 */
	@Override
	public Pagination page(String sql, int page, int rows, Object... args) {
		return new Pagination( sql, page, rows, jdbcTemplate );
	}
}
