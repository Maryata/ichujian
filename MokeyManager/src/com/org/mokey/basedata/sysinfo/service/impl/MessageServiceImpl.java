package com.org.mokey.basedata.sysinfo.service.impl;

import com.org.mokey.basedata.sysinfo.dao.MallProductDao;
import com.org.mokey.basedata.sysinfo.dao.MessageDao;
import com.org.mokey.basedata.sysinfo.service.MessageService;

import java.util.Date;
import java.util.Map;

public class MessageServiceImpl implements MessageService {

	private MessageDao messageDao;

	@Override
	public void delete(String id) {
		if( id == null || id.isEmpty() ) return;

		messageDao.delete(id);
	}

	@Override
	public Map<String, Object> listByUid (String sUid, int start, int limit) {
		return messageDao.listByUid( Long.parseLong(sUid), start, limit );
	}

	@Override
	public Map<String, Object> list(String name, int start, int limit) {
		return messageDao.list( name, start, limit );
	}

	@Override
	public void save(Map<String, Object> map) {
		// 消息类型，0：客服，1：客户
		map.put("C_TYPE",0);
		map.put("C_DATE",new Date());

		messageDao.save(map);
	}

	public MessageDao getMessageDao () {
		return messageDao;
	}

	public void setMessageDao (MessageDao messageDao) {
		this.messageDao = messageDao;
	}
}
