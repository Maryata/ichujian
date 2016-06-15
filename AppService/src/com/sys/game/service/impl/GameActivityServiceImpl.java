package com.sys.game.service.impl;

import com.sys.game.service.GameActivityService;
import com.sys.game.service.GameBaseService;
import com.sys.util.StrUtils;

import com.sys.util.file.FileServices;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GameActivityServiceImpl extends GameBaseService implements GameActivityService {

	@Override
	public List<Map<String, Object>> listActivity(long gid, int page, int rows) {
		int start = rows * (page - 1) + 1;
		int maxRows = start + rows - 1;
		
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		
		parameterObject.put( "gid", gid );
		parameterObject.put( "startingIndex", start );
		parameterObject.put( "maxRows", maxRows );
		
		return _queryForList( "gameActivity.listActivity", parameterObject );
	}

	@Override
	public Map<String, Object> getActivityById(long id, String uid,
			String source) {
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		parameterObject.put( "id", id );
		parameterObject.put( "uid", uid );
		parameterObject.put( "source", source );
		
		List<Map<String, Object>> list = _queryForList( "gameActivity.getActivityById", parameterObject );
		
		if ( null == list || list.size() <= 0 ) {
			return new HashMap<String, Object>();
		}

		return list.get( 0 );
	}

	@Override
	public List<Map<String, Object>> listActivity(int pageNumber, int pageSize) {
		int start = pageSize * (pageNumber - 1) + 1;
		int maxRows = start + pageSize - 1;

		Map<String, Object> parameterObject = new HashMap<String, Object>();

		parameterObject.put( "startingIndex", start );
		parameterObject.put( "maxRows", maxRows );

		return _queryForList( "gameActivity.eventsList", parameterObject );
	}

	@Override
	public Map<String, Object> getReply(long id, String uid) {
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		parameterObject.put( "aid", id );
		parameterObject.put( "uid", uid );

		List<Map<String, Object>> list = _queryForList( "gameActivity.getReply", parameterObject );

		if ( null == list || list.size() <= 0 ) {
			return new HashMap<String, Object>();
		}

		return list.get( 0 );
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<Map<String, Object>> attendedAct(String uid, String page, String pSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("UID", uid);
		paramMap.put("PAGE", page);
		paramMap.put("PSIZE", StrUtils.isEmpty(pSize)?"20":pSize);
		return this.getSqlMapClientTemplate().queryForList("gameActivity.attendedAct", paramMap);
	}

	@Override
	public void campusActivities (String id, String uid, String aid, String comment, String[] imgArr) {
        Map<String,Object> parameterObject = new HashMap<String,Object>();
        StringBuilder stringBuilder = new StringBuilder();
        if (imgArr != null) {
            for (int i = 0; i < imgArr.length; ++i) {
                String dest = "game/" + uid + "/activity/" + aid + "/" + System.currentTimeMillis() + ".png";

                String s = new String(Base64.decodeBase64(imgArr[i].getBytes())).replaceAll(" ","").replaceAll("%3D","=");

                String path = FileServices.saveFile(new ByteArrayInputStream(Base64.decodeBase64(s)), dest);
                if(stringBuilder.length() == 0) {
                    stringBuilder.append(path);
                } else {
                    stringBuilder.append(";").append(path);
                }
            }
        }

        parameterObject.put("aid", Long.parseLong(aid));
        parameterObject.put("uid",Long.parseLong(uid));
        parameterObject.put("comment",comment);
        parameterObject.put("img",stringBuilder.toString());

        if(StringUtils.isEmpty(id)) {
            getSqlMapClientTemplate().insert("gameActivity.campusActivities",parameterObject);
        } else {
            parameterObject.put("id", Long.valueOf(id));

            getSqlMapClientTemplate().update("gameActivity.updateMemberActivityById", parameterObject);
        }
	}
}
