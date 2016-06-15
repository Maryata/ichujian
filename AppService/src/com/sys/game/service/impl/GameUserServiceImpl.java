package com.sys.game.service.impl;

import com.sys.game.service.GameBaseService;
import com.sys.game.service.GameUserService;
import com.sys.util.StrUtils;
import com.sys.util.file.FileServices;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

@SuppressWarnings("deprecation")
@Service
public class GameUserServiceImpl extends GameBaseService implements GameUserService {
	
	private Logger LOGGER = Logger.getLogger(GameUserServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getBindingInfo(String uid) {
		List<Map<String,Object>> bindingInfo =new ArrayList<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UID", uid);
		// 查询绑定信息
		List<Map<String,Object>> list = this.getSqlMapClientTemplate().queryForList("gameUser.getBindingInfo", map);
		
		Map<String,Object> reqMap = new HashMap<String,Object>();
		// 存入"登陆类型"，0表示没有绑定/注册
		reqMap.put("chujian", "0");
		reqMap.put("qq", "0");
		reqMap.put("weixin", "0");
		reqMap.put("weibo", "0");
		bindingInfo.add(reqMap);
		if(null!=list && list.size()>0){
			for (Map<String,Object> m : list) {
				// 获取登陆状态
				String type = (null==m.get("C_LOGINTYPE")) ? "" : m.get("C_LOGINTYPE").toString();
				// 修改当前遍历到的登陆状态为1
				if("0".equals(type)){
					reqMap.put("chujian", "1");
                }else if("1".equals(type)){
                	reqMap.put("qq", "1");
                	if("0".equals(m.get("C_STATE"))){
    					reqMap.put("unBindable_qq", "1");
    				}else{
    					reqMap.put("unBindable_qq", "0");
    				}
                }else if("2".equals(type)){
                	reqMap.put("weixin", "1");
                	if("0".equals(m.get("C_STATE"))){
    					reqMap.put("unBindable_weixin", "1");
    				}else{
    					reqMap.put("unBindable_weixin", "0");
    				}
                }else if("3".equals(type)){
                	reqMap.put("weibo", "1");
                	if("0".equals(m.get("C_STATE"))){
    					reqMap.put("unBindable_weibo", "1");
    				}else{
    					reqMap.put("unBindable_weibo", "0");
    				}
                }
			}
		}
		return bindingInfo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getUserInfo(String uid) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("uid", uid);
		return this.getSqlMapClientTemplate().queryForList("gameUser.getUserInfo", map);
	}

	@Override
	public void updateHead(String uid, String fileurl) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UID", uid);
		map.put("HEADIMAGE", fileurl);
		map.put("ACTIONDATE", new Date());
		this.getSqlMapClientTemplate().update("gameUser.updateHead", map);
	}

	@Override
	public void setNewPwd(String phonenum, String pwd) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("PHONENUM", phonenum);
		map.put("PWD", pwd);
		map.put("ACTIONDATE", new Date());
		this.getSqlMapClientTemplate().update("gameUser.setNewPwd", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findByPhone(String phonenum) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("PHONENUM", phonenum);
		return this.getSqlMapClientTemplate().queryForList("gameUser.findByPhone", map);
	}

	@Override
	public void invalidatedOther(String phonenum, String type) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("PHONENUM", phonenum);
		map.put("TYPE", type);
		this.getSqlMapClientTemplate().update("gameUser.invalidatedOther", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> isUsableCode(String phonenum,
			String valicode, String s_date, String type) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("DATE", s_date);
		map.put("VALICODE", valicode);
		map.put("TYPE", type);
		map.put("PHONENUM", phonenum);
		return this.getSqlMapClientTemplate().queryForList("gameUser.isUsableCode", map);
	}

	@Override
	public void insertValicode(String imei, String phonenum, String type,
			Date date, String code) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("IMEI", imei);
		map.put("DATE", date);
		map.put("CODE", code);
		map.put("TYPE", type);
		map.put("PHONENUM", phonenum);
		this.getSqlMapClientTemplate().insert("gameUser.insertValicode", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getValiCode(String phonenum, String type,
			String date) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("PHONENUM", phonenum);
		map.put("TYPE", type);
		map.put("DATE", date);
		return this.getSqlMapClientTemplate().queryForList("gameUser.getValiCode", map);
	}

	@Override
	public void updatePhonenum(String uid, String phonenum) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UID", uid);
		map.put("PHONENUM", phonenum);
		map.put("DATE", new Date());
		this.getSqlMapClientTemplate().update("gameUser.updatePhonenum", map);
	}

	@Override
	public void chujianUser(String uid, String logintype, String phonenum,
			String pwd, String nickname) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UID", uid);
		map.put("LOGINTYPE", logintype);
		map.put("PHONENUM", phonenum);
		map.put("PWD", pwd);
		map.put("NICKNAME", nickname);
		map.put("DATE", new Date());
		this.getSqlMapClientTemplate().update("gameUser.chujianUser", map);
	}

	@Override
	public void bindPhone(String uid, String logintype, String phonenum,
			String pwd) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UID", uid);
		map.put("LOGINTYPE", logintype);
		map.put("PHONENUM", phonenum);
		map.put("PWD", pwd);
		map.put("ACTIONDATE", new Date());
		this.getSqlMapClientTemplate().update("gameUser.bindPhone", map);
	}

	@Override
	public void editUserInfo(String uid, String nickname, String sex) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UID", uid);
		map.put("NICKNAME", nickname);
		map.put("SEX", sex);
		map.put("ACTIONDATE", new Date());
		this.getSqlMapClientTemplate().update("gameUser.editUserInfo", map);
	}

	@Override
	public void modifyPwd(String uid, String oldpwd, String newpwd) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UID", uid);
		map.put("OPWD", oldpwd);
		map.put("NPWD", newpwd);
		map.put("ACTIONDATE", new Date());
		this.getSqlMapClientTemplate().update("gameUser.modifyPwd", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findUserByUidNPwd(String uid, String pwd) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UID", uid);
		map.put("PWD", pwd);
		return this.getSqlMapClientTemplate().queryForList("gameUser.findUserByUidNPwd", map);
	}

	@Override
	public void updateIsNotLogin(String regid, String imei) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("REGID", regid);
		map.put("IMEI", imei);
		this.getSqlMapClientTemplate().update("gameUser.updateIsNotLogin", map);
	}

	@Override
	public void insertImei(String uid, String regid, String imei) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UID", uid);
		map.put("REGID", regid);
		map.put("IMEI", imei);
		// 查询是否是第一次登录
		List<Map<String,Object>> list = this.isFirstLogin(map);
		if(null!=list && list.size()>0){
			map.put("ISFIRSTLOGIN", "0");
		}else{
			map.put("ISFIRSTLOGIN", "1");
		}
		map.put("ACTIONDATE", new Date());
		this.getSqlMapClientTemplate().insert("gameUser.insertImei", map);
	}

	// 查询是否是第一次登录
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> isFirstLogin(Map<String,Object> map){
		return this.getSqlMapClientTemplate().queryForList("gameUser.isFirstLogin", map);
	}

	@Override
	public void updateIsLogin(String regid, String imei) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("REGID", regid);
		map.put("IMEI", imei);
		this.getSqlMapClientTemplate().update("gameUser.updateIsLogin", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getMemberImei(String regid, String imei) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("REGID", regid);
		map.put("IMEI", imei);
		return this.getSqlMapClientTemplate().queryForList("gameUser.getMemberImei", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> login(String phonenum, String pwd) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("PHONENUM", phonenum);
		map.put("PWD", pwd);
		return this.getSqlMapClientTemplate().queryForList("gameUser.login", map);
	}

	@Override
	public void regImei(String regid, String imei, String valicode, Date date,
			String uid) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("REGID", regid);
		map.put("IMEI", imei);
		map.put("VALICODE", valicode);
		map.put("DATE", new Date());
		map.put("UID", uid);
		this.getSqlMapClientTemplate().update("gameUser.regImei", map);
	}

	@Override
	public void defaultNickname(String nickname, String regid) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("NICKNAME", nickname);
		map.put("REGID", regid);
		this.getSqlMapClientTemplate().update("gameUser.defaultNickname", map);
	}

	@Override
	public String getUid(String regid, String type) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("REGID", regid);
		map.put("TYPE", type);
		return (String) this.getSqlMapClientTemplate().queryForObject("gameUser.getUid", map);
	}

	@Override
	public String register(String phonenum, String pwd) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("PHONENUM", phonenum);
		map.put("PWD", pwd);
		map.put("DATE", new Date());
		return (String) this.getSqlMapClientTemplate().insert("gameUser.register", map);
	}

	@Override
	public void unBindExternal(String uid, String logintype) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UID", uid);
		map.put("LOGINTYPE", logintype);
		this.getSqlMapClientTemplate().update("gameUser.unBindExternal", map);
	}

	@Override
	public String isFirstInfo(String uid, String logintype) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UID", uid);
		map.put("LOGINTYPE", logintype);
		return (String) this.getSqlMapClientTemplate().queryForObject("gameUser.isFirstInfo", map);
	}

	@Override
	public void bindExternal(String uid, String regid, String nickname,
			String logintype, String phonenum, String sex, String age,
			String headimage) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UID", uid);
		map.put("REGID", regid);
		map.put("NICKNAME", nickname);
		map.put("LOGINTYPE", logintype);
		map.put("PHONENUM", phonenum);
		map.put("SEX", sex);
		map.put("AGE", age);
		map.put("HEADIMAGE", headimage);
		map.put("ACTIONDATE", new Date());
		if(StrUtils.isEmpty(map.get("PHONENUM"))){
			this.getSqlMapClientTemplate().insert("gameUser.bindExternal", map);
		}else{
			this.getSqlMapClientTemplate().insert("gameUser.bindExternalWithPhone", map);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> isRegistered(String uid, String loginType) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UID", uid);
		map.put("LOGINTYPE", loginType);
		return this.getSqlMapClientTemplate().queryForList("gameUser.isRegistered", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> isBinding(String regid, String loginType) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("REGID", regid);
		map.put("LOGINTYPE", loginType);
		return this.getSqlMapClientTemplate().queryForList("gameUser.isBinding", map);
	}

	@Override
	public void insertExternalNImei(String uid, String regid, String imei) {
		this.insertImei(uid, regid, imei);
	}

	@Override
	public String insertExternal(String regid, String nickname,
			String logintype, String headimage, String sex, String age) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("REGID", regid);
		map.put("NICKNAME", nickname);
		map.put("LOGINTYPE", logintype);
		map.put("HEADIMAGE", headimage);
		map.put("SEX", sex);
		map.put("AGE", age);
		map.put("ACTIONDATE", new Date());
		return (String) this.getSqlMapClientTemplate().insert("gameUser.insertExternal", map);
	}

	@Override
	public Integer findInfoByRegidNImei(String regid, String imei) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("REGID", regid);
		map.put("IMEI", imei);
		return (Integer) this.getSqlMapClientTemplate().queryForObject("gameUser.findInfoByRegidNImei", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findExternal(String regid, String logintype) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("LOGINTYPE", logintype);
		map.put("REGID", regid);
		return this.getSqlMapClientTemplate().queryForList("gameUser.getExternal", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	// 获取游戏提示语
	public List<Map<String, Object>> gameCue() {
		return this.getSqlMapClientTemplate().queryForList("gameUser.gameCue");
	}

	@SuppressWarnings("unchecked")
	@Override
	// 获取用户id
	public List<Map<String, Object>> userHeadImg(String uid, String loginType) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UID", uid);
		return this.getSqlMapClientTemplate().queryForList("gameUser.userHeadImg", map);
	}

	@Override
	public String registerCj(String phonenum, String pwd) {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("PHONENUM", phonenum);
			map.put("PWD", pwd);
			map.put("DATE", new Date());
			return (String) this.getSqlMapClientTemplate().insert("gameUser.registerCj", map);
		} catch (Exception e) {
			LOGGER.error("GameUserServiceImpl.registerCj failed, e : ", e);
		}
		return null;
	}

	@Override
	public String getUidCj(String regid, String type) {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("REGID", regid);
			map.put("TYPE", type);
			return (String) this.getSqlMapClientTemplate().queryForObject("gameUser.getUidCj", map);
		} catch (Exception e) {
			LOGGER.error("GameUserServiceImpl.getUidCj failed, e : ", e);
		}
		return null;
	}

	@Override
	public void defaultNicknameCj(String nickname, String regid) {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("NICKNAME", nickname);
			map.put("REGID", regid);
			this.getSqlMapClientTemplate().update("gameUser.defaultNicknameCj", map);
		} catch (Exception e) {
			LOGGER.error("GameUserServiceImpl.defaultNicknameCj failed, e : ", e);
		}
	}

	@Override
	public void regImeiCj(String regid, String imei, String valicode, String uid) {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("REGID", regid);
			map.put("IMEI", imei);
			map.put("VALICODE", valicode);
			map.put("DATE", new Date());
			map.put("UID", uid);
			this.getSqlMapClientTemplate().update("gameUser.regImeiCj", map);
		} catch (Exception e) {
			LOGGER.error("GameUserServiceImpl.regImeiCj failed, e : ", e);
		}
	}

    @Override
    public Map<String, Object> score(int uid, int pageNumber, int pageSize) {
        Map<String,Object> result = new HashMap<String,Object>();
        int start = pageSize * (pageNumber - 1) + 1;
        int maxRows = start + pageSize - 1;

        Map<String, Object> parameterObject = new HashMap<String, Object>();

        parameterObject.put("uid", uid);
        parameterObject.put( "startingIndex", start );
        parameterObject.put( "maxRows", maxRows );

        Object total = getTotalScore(uid);
        List<Map<String,Object>> rows = _queryForList("gameUser.listScore",parameterObject);

        result.put("total",total);
        result.put("rows", rows);

        return result;
    }

	@Override
	public Object getTotalScore( int uid ) {
		return getSqlMapClientTemplate().queryForObject("gameUser.totalScore", uid);
	}

	@Override
	public Integer getTotalGift(String uid) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject("gameUser.getTotalGift", uid);
	}

	@Override
	public Integer getTotalMess(String uid) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject("gameUser.getTotalMess", uid);
	}

    @Override
    public List<Map<String, Object>> getMessage(int i, int pageNumber, int pageSize) {
        int start = pageSize * (pageNumber - 1) + 1;
        int maxRows = start + pageSize - 1;

        Map<String, Object> parameterObject = new HashMap<String, Object>();

        parameterObject.put("uid", i);
        parameterObject.put( "startingIndex", start );
        parameterObject.put( "maxRows", maxRows );

        return _queryForList("gameUser.getMessage",parameterObject);
    }

	@Override
	public void contactServiceRoute (String sUid, String message, String[] imgArr) throws IOException {
		Map<String,Object> parameterObject = new HashMap<String,Object>();
		StringBuilder stringBuilder = new StringBuilder();
		if(imgArr != null) {
			for (int i = 0; i < imgArr.length; ++i) {
				String dest = "game/" + sUid + "/" + System.currentTimeMillis() + ".png";

				String s = new String(Base64.decodeBase64(imgArr[i].getBytes())).replaceAll(" ","").replaceAll("%3D","=");

				String path = FileServices.saveFile(new ByteArrayInputStream(Base64.decodeBase64(s)), dest);
				if(stringBuilder.length() == 0) {
					stringBuilder.append(path);
				} else {
					stringBuilder.append(";").append(path);
				}
			}
		}

		parameterObject.put("uid",Long.parseLong(sUid));
		parameterObject.put("message",message);
		parameterObject.put("img",stringBuilder.toString());

		getSqlMapClientTemplate().insert("gameUser.contactServiceRoute",parameterObject);
	}

	@SuppressWarnings("unchecked")
	@Override
	// 查询state=1的用户信息
	public List<Map<String, Object>> getRegisterInfo(String uid) {
		return getSqlMapClientTemplate().queryForList("gameUser.getRegisterInfo", uid);
	}

	@SuppressWarnings("unchecked")
	@Override
	// 查询用户是否做过一次性任务（上传头像、修改昵称、修改性别、修改手机号、绑定第三方）
	public List<Map<String, Object>> haveCompleted(String uid) {
		return getSqlMapClientTemplate().queryForList("gameUser.haveCompleted", uid);
	}

	@Override
	public Map<String, Object> showGetScore(String uid) {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		int i = 0;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("UID", uid);
		i = (Integer) getSqlMapClientTemplate().queryForObject("gameUser.upLoadHeadImg", param);
		reqMap.put("upLoadHeadImg", i>0?"0":"1");
		i = (Integer) getSqlMapClientTemplate().queryForObject("gameUser.changeNickname", param);
		reqMap.put("changeNickname", i>0?"0":"1");
		i = (Integer) getSqlMapClientTemplate().queryForObject("gameUser.boundPhone", param);
		reqMap.put("boundPhone", i>0?"0":"1");
		i = (Integer) getSqlMapClientTemplate().queryForObject("gameUser.bindQq", param);
		reqMap.put("bindQq", i>0?"0":"1");
		i = (Integer) getSqlMapClientTemplate().queryForObject("gameUser.bindWeChat", param);
		reqMap.put("bindWeChat", i>0?"0":"1");
		i = (Integer) getSqlMapClientTemplate().queryForObject("gameUser.bindMcroBlog", param);
		reqMap.put("bindMcroBlog", i>0?"0":"1");
		return reqMap;
	}

}
