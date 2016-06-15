/**
 * 
 */
package com.qujian.service;

import com.qujian.po.Supplier;

/**
 * @author vpc
 *
 */
public interface ISupplierService {
    Supplier find(String code);

	/**
	 * @Description: TODO
	 * @param licenseCn
	 * @param code
	 */
	void saveActionDownload(String licenseCn, String code);
}
