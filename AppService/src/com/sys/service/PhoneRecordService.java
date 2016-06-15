package com.sys.service;

public interface PhoneRecordService {

	/**
	 * 记录手机号
	 * @author Maryn
	 * @param imei 手机唯一标识
	 * @param uEmail 用户邮箱
	 */
	public void recordPhone(String imei, String uEmail);

	/**
	 * 记录手机imei
	 * @param imei imei
	 */
	public void recordImei(String imei);

}
