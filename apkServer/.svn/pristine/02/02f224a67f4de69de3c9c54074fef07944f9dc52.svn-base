/**
 * 
 */
package com.qujian.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qujian.dao.ISupplierDao;
import com.qujian.po.Supplier;
import com.qujian.service.ISupplierService;

/**
 * @author vpc
 *
 */
@Service("supplierService")
@Transactional
public class SupplierServiceImpl implements ISupplierService {
	@Resource
	private ISupplierDao supplierDao;
	
	@Override
	public Supplier find(String code) {
		
		List<Supplier> suppliers = supplierDao.query( "SELECT C_SUPPLIER_CODE, C_COMPANY,C_CONTACTS,C_EMAIL,C_ID,C_LEVE,C_LOCATION,C_LOGO_URI,C_MODITY_TIME,C_PHONE,C_SYSDATE,C_TYPE,C_URL,C_SUPPLIER_NAME,C_COLOR,C_ABOUT_LOGO_URI,C_MAIN_COMMON_SLOGAN,C_MAIN_MAIN_LOGO_URI,C_ABOUT_INFO,C_MAIN_MAIN_BUY,C_SHOPPING_URI,C_COMMON_COPYRIGHT,C_HELPANDFEEDBACK,C_WEBSITE FROM T_BASE_SUPPLIER WHERE C_SUPPLIER_CODE = ? AND C_ISLIVE='1'", new Object[]{code} );
		if(null != suppliers && suppliers.size() >= 1) 
			return suppliers.get( 0 );
		
		return null;
	}

	@Override
	public void saveActionDownload(String licenseCn, String code) {
		supplierDao.saveActionDownload(licenseCn,code);
	}
}
