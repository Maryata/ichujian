package com.sys.ekey.game.service.impl;

import com.sys.ekey.game.service.EKGameBaseService;
import com.sys.ekey.game.service.EKGameInfoService;
import com.sys.game.service.GameBaseService;
import com.sys.game.service.GameInfoService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EKGameInfoServiceImpl extends EKGameBaseService implements
		EKGameInfoService {

	@Override
	public Map<String, Object> getInformationById(long id, String uid,
			String source) {
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		parameterObject.put( "id", id );
		parameterObject.put( "uid", uid );
		parameterObject.put( "source", source );
		
		List<Map<String, Object>> list = _queryForList( "ek_gameInfo.getInformationById", parameterObject );
		
		if ( null == list || list.size() <= 0 )
			return new HashMap<String, Object>();

		return list.get( 0 );
	}

	@Override
	public List<Map<String, Object>> listInformation(long gid, int page,
			int rows) {
		int start = rows * (page - 1) + 1;
		int maxRows = start + rows - 1;
		
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		
		parameterObject.put( "gid", gid );
		parameterObject.put( "startingIndex", start );
		parameterObject.put( "maxRows", maxRows );
		
		return _queryForList( "ek_gameInfo.listInformation", parameterObject );
	}

}
