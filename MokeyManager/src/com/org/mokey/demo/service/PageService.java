/**
 * 
 */
package com.org.mokey.demo.service;

import com.org.mokey.demo.vo.Pagination;

/**
 * @author vpc
 *
 */
public interface PageService {
	Pagination page(String sql, int page, int rows, Object... args);
}
