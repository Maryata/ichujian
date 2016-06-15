package com.sys.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.hsqldb.lib.StringUtil;

import com.sys.commons.AbstractAction;
import com.sys.service.IUsernotifyService;
import com.sys.util.MD5;

public class UserNotifyAction extends AbstractAction {
	private static final long serialVersionUID = 471571946220161834L;

	private IUsernotifyService usernotifyService;
	
	private String username,devicename,password,nickname,sex,sign,logintype,age,headimage;
	
	public String userNotify() {
		Map<String, String> result = new HashMap<String, String>();
		String _regid , _nickname ,
		_logintype ,_phonenum ,_sex ,_age,
		_headimage ,_password ,_imei;
		synchronized ( this ) {
			_regid = "reg_" + System.currentTimeMillis();
			_nickname = nickname;
			_logintype = logintype;
			_phonenum = username;
			_sex = sex;
			_age = age;
			_headimage = headimage;
			_password = password;
			_imei = devicename;
		}
		if(_checkSign()) {
			if(StringUtil.isEmpty( _phonenum )) {
				result.put( "code", "101" );
				result.put( "msg", "Invalid UserName" );
			} else if (!"0".equals( logintype )) {
				result.put( "code", "104" );
				result.put( "msg", "Unknown Error" );
			} else {
				Map<String,String> r = usernotifyService.usernotify( _regid, _nickname, _logintype, _phonenum, _sex, _age, _headimage, _password, _imei );
				String code = r.get("code");
				result.put( "code", code );
				if("100".equals( code )) {
					result.put( "msg", "User Notify OK" );
				} else if("101".equals( code )) {
					result.put( "msg", "Invalid UserName" );
				} else if("104".equals( code )) {
					result.put( "msg", "Unknown Error" );
				}
			}
			
		} else {
			result.put( "code", "102" );
			result.put( "msg", "Invalid Signature" );
		}
		
		getResponse().setContentType( "text/html;charset=UTF-8" );
		
		try {
			String str = JSONObject.fromObject( result ).toString();
			getResponse().getWriter().write( str );
			
			if(log.isDebugEnabled()) {
				log.debug( str );
			}
			
		} catch ( IOException e ) {
			log.error( e );
		}
		
		return NONE;
	}

	/**
	 * @Description: TODO
	 * @return
	 */
	private boolean _checkSign() {
		String _sign = "";
		StringBuffer sb = new StringBuffer();
		//username=…&devicename=…&password =…&nickname =…&sex =…&usersecret=…
		//68F5C843C81AF0BFEB8835ABC8DDCD33
		
		synchronized(this) {
			sb.append( "username=" ).append( username );
			sb.append( "&devicename=" ).append( devicename );
			sb.append( "&password=" ).append( password );
			sb.append( "&nickname=" ).append( nickname );
			sb.append( "&sex=" ).append( sex );
			sb.append( "&logintype=" ).append( "0" );
			sb.append( "&age=" ).append( age );
			sb.append( "&headimage=" ).append( headimage );
			sb.append( "&usersecret=" ).append( "68F5C843C81AF0BFEB8835ABC8DDCD33" );
		}
		
		_sign = MD5.md5Code( sb.toString() );
		
		if(log.isDebugEnabled()) {
			log.debug( "sign : " + _sign );
		}
		
		return _sign.equalsIgnoreCase( sign );
	}

	public IUsernotifyService getUsernotifyService() {
		return usernotifyService;
	}

	public void setUsernotifyService(IUsernotifyService usernotifyService) {
		this.usernotifyService = usernotifyService;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDevicename() {
		return devicename;
	}

	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getLogintype() {
		return logintype;
	}

	public void setLogintype(String logintype) {
		this.logintype = logintype;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getHeadimage() {
		return headimage;
	}

	public void setHeadimage(String headimage) {
		this.headimage = headimage;
	}
}
