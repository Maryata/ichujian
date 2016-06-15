/**
 * 
 */
package com.org.mokey.demo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.org.mokey.demo.dao.PageDAO;
import com.org.mokey.demo.service.PageService;
import com.org.mokey.demo.vo.Pagination;

/**
 * @author vpc
 *
 */
@Service("pageService")
@Transactional
public class PageServiceImpl implements PageService {
	@Resource
	private PageDAO pageDAO;
	
	/* (non-Javadoc)
	 * @see com.org.mokey.demo.service.PageService#page(java.lang.String, int, int, java.lang.Object[])
	 */
	@Override
	public Pagination page(String sql, int page, int rows, Object... args) {
		return pageDAO.page( sql, page, rows, args );
	}

}
