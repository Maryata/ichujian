/**
 * 
 */
package com.org.mokey.demo.dao;

import com.org.mokey.demo.vo.Pagination;

/**
 * @author vpc
 *
 */
public interface PageDAO {
	Pagination page(String sql, int page, int rows, Object... args);
}
