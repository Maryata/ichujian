/**
 * 
 */
package com.sys.weixin.action;

import com.opensymphony.xwork2.ActionSupport;
import com.sys.commons.AbstractAction;
import com.sys.util.HttpRequestProxy;
import com.sys.util.encrypt.ParameterEncryptor;
import net.sf.json.JSONObject;
import org.hsqldb.lib.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

/**
 * @author vpc
 *
 */
public class H5Action extends AbstractAction {
	private static final long serialVersionUID = -8764044450049190417L;
	private final String CHARSET = "UTF-8";
	private final String SIGNKEY = "sign";
	private final String IP = "http://192.168.8.221";
	
	/**
	 * 校验手机号是否可用
	 */
	private final String VALIDATEPHONE = "/AppService/game/validatePhone.action";
	/**
	 * 发送验证码
	 */
	private final String GETVALICODE = "/AppService/game/getvalicode.action";
	/**
	 * 验证码是否有效
	 */
	private final String ISVALIDCODE = "/AppService/game/isvalidcode.action";
	
	/**
	 * 登录
	 */
	private final String LOGIN = "/AppService/game/login.action";
	
	/**
	 * 忘记密码-设置新密码
	 */
	private final String SETPWDOFFGT = "/AppService/game/setpwdoffgt.action";
	/**
	 * 礼包列表
	 */
	//private final String GAMEGIFTLIST = "/AppService/game/gameGiftList.action";
	
	/**
	 * 获取某个分类下的礼包
	 */
	private final String GIFTSCATEGORYDETAIL = "/AppService/game/giftsCategoryDetail.action";
	
	/**
	 * 获取礼包激活码
	 */
	private final String GETGIFT = "/AppService/game/getGift.action";
	/**
	 * 获取礼包详情
	 */
	private final String GIFTDETAIL = "/AppService/game/giftDetail.action";
	
	/**
	 * 触键注册
	 */
	private final String REGISTER = "/AppService/game/register.action";
	
	private final String IMEI = "493002407599521"; // 默认IMEI
	
	@SuppressWarnings("rawtypes")
	public String validatePhone() {
		String result = null;
		Map parameters = getRequest().getParameterMap();
		
		parameters = getParameterMap(parameters);
		
		result = HttpRequestProxy.doPost( IP + VALIDATEPHONE, parameters, CHARSET );
		
		try {
			writeToResponse( result );
		} catch ( IOException e ) {
			log.error( e.getMessage() );
		}
		return NONE;
	}
	
	public String sendCode() {
		getValicode();
		
		return ActionSupport.SUCCESS;
	}
	
	public String sendCodeR() {
		String result = getValicode();
		
		if(result != null) {
			try {
				writeToResponse( result );
			} catch ( IOException e ) {
				log.error( e.getMessage() );
			}
		}
		
		return NONE;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String reg() {
		String result = null;
		HttpServletRequest request = getRequest();
		Map parameters = request.getParameterMap();
		String phonenum = request.getParameter( "phonenum" );
		
		//Object pwd = request.getSession().getAttribute( String.valueOf( phonenum ) );
		Object pwd = request.getSession().getAttribute( phonenum );
		String type = request.getParameter( "type" );
		
		request.setAttribute( "type", type );
		Map m = new HashMap(parameters);
		m.put( "type", new String[]{type} ); // 注册
		m.put( "pwd", new Object[]{pwd} );
		
		parameters = getParameterMap(m);
		
		result = HttpRequestProxy.doPost( IP + REGISTER, parameters, CHARSET );
		
		try {
			writeToResponse( result );
		} catch ( IOException e ) {
			log.error( e.getMessage() );
		}
		
		return NONE;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String isvalidcode() {
		String result = null;
		HttpServletRequest request = getRequest();
		Map parameters = request.getParameterMap();
		String type = request.getParameter( "type" );
		
		Map m = new HashMap( parameters );
		m.put("type",new String[]{type}); // 注册
		
		parameters = getParameterMap( m );
		
		// 校验验证码是否有效
		result = HttpRequestProxy.doPost( IP + ISVALIDCODE, parameters, CHARSET );
		
		//result = "{\"status\":\"Y\"}";
		//result = "{\"status\":\"N\"}";
		try {
			writeToResponse( result );
		} catch ( IOException e ) {
			log.error( e.getMessage() );
		}
		
		return NONE;
	}
	
	@SuppressWarnings("rawtypes")
	public String login() {
		String result = null;
		HttpServletRequest request = getRequest();
		Map parameters = request.getParameterMap();
		
		parameters = getParameterMap( parameters );
		
		// 登录
		result = HttpRequestProxy.doPost( IP + LOGIN, parameters, CHARSET );
		
		try {
			writeToResponse( result );
		} catch ( IOException e ) {
			log.error( e.getMessage() );
		}
		
		return NONE;
	}
	
	@SuppressWarnings("rawtypes")
	public String setpwdoffgt() {
		String result = null;
		HttpServletRequest request = getRequest();
		Map parameters = request.getParameterMap();
		
		parameters = getParameterMap( parameters );
		
		// 设置密码
		result = HttpRequestProxy.doPost( IP + SETPWDOFFGT, parameters, CHARSET );
		
		try {
			writeToResponse( result );
		} catch ( IOException e ) {
			log.error( e.getMessage() );
		}
		
		return NONE;
	}
	
	private String getValicode() {
		HttpServletRequest request = getRequest();
		String result = null;
		String phone = request.getParameter( "phonenum" );
		String pwd = request.getParameter( "pwd" );
		String type = request.getParameter( "type" );
		
		request.setAttribute( "type", type );
		log.info( "pwd ======================" + pwd );
		
		Pattern p = Pattern.compile("^((13[0-9])|(14[5,7])|(15[^4,\\D])|(18[0,1,2,3,4-9])|(17[7]))\\d{8}$");
		if(phone != null && p.matcher( phone ).matches()) {
			request.setAttribute( "phoneNumber", phone );
			
			Map<Object, Object> parameters = getParameterMap(request.getParameterMap());
			
			result = HttpRequestProxy.doPost( IP + GETVALICODE, parameters, CHARSET );
			
			//result = "{\"status\":\"Y\"}";
			
			JSONObject json = JSONObject.fromObject( result );
			String stauts = json.getString( "status" );
			// 如果手机号码有效，把密码存到当前session
			if("Y".equals( stauts )) {
				getRequest().getSession().setAttribute( phone, pwd );
			}
		}
		
		System.out.println("phone ==================" + phone);
		
		return result;
	}
	
	public String index() {
		return ActionSupport.SUCCESS;
	}
	
	public String modifypwd() {
		HttpServletRequest request = getRequest();
		request.setAttribute( "phoneNumber", request.getParameter( "phoneNumber" ) );
		
		return ActionSupport.SUCCESS;
	}
	
	public String pwdoffgt() {
		return ActionSupport.SUCCESS;
	}
	
	public String register() {
		return ActionSupport.SUCCESS;
	}
	
	public String list() {
		return ActionSupport.SUCCESS;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String ajaxList() {
		String result = null;
		Map parameters = new HashMap();
		
		String uid = getRequest().getParameter( "uid" );
		if(StringUtil.isEmpty( uid )) {
			uid = "-1";
		}
		
		parameters.put( "uid", new String[]{uid} );
		parameters.put( "cid", new Long[] { -1L } );
		
		parameters = getParameterMap( parameters );
		
		// 获取礼包列表
		result = HttpRequestProxy.doPost( IP + GIFTSCATEGORYDETAIL, parameters, CHARSET );
		
		try {
			writeToResponse( result );
		} catch ( IOException e ) {
			log.error( e.getMessage() );
		}
		
		return NONE;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getGift() {
		String result = null;
		HttpServletRequest request = getRequest();
		String username = request.getParameter( "username" );
		String id = request.getParameter( "id" );
		
		if(StringUtil.isEmpty( username ) || StringUtil.isEmpty( id )) {
			result = "{\"status\":\"N\"}";
			try {
				writeToResponse( result );
			} catch ( IOException e ) {
				log.error( e.getMessage() );
			}
			
			return NONE;
		}
		
		Map parameters = new HashMap();
		
		parameters.put( "uid", new String[]{username} );
		parameters.put( "gid", new String[]{id} );
		
		parameters = getParameterMap( parameters );
		
		// 获取礼包激活码
		result = HttpRequestProxy.doPost( IP + GETGIFT, parameters, CHARSET );
		
		try {
			writeToResponse( result );
		} catch ( IOException e ) {
			log.error( e.getMessage() );
		}
		
		return NONE;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String giftDetail() {
		String result = null;
		HttpServletRequest request = getRequest();
		String username = request.getParameter( "username" );
		String id = request.getParameter( "id" );
		
		if(StringUtil.isEmpty( username ) || StringUtil.isEmpty( id )) {
			result = "{\"status\":\"N\"}";
			try {
				writeToResponse( result );
			} catch ( IOException e ) {
				log.error( e.getMessage() );
			}
			
			return NONE;
		}
		
		Map parameters = new HashMap();
		
		parameters.put( "uid", new String[]{username} );
		parameters.put( "gid", new String[]{id} );
		
		parameters = getParameterMap( parameters );
		
		// 获取礼包详情
		result = HttpRequestProxy.doPost( IP + GIFTDETAIL, parameters, CHARSET );
		
		try {
			writeToResponse( result );
		} catch ( IOException e ) {
			log.error( e.getMessage() );
		}
		
		return NONE;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
 	private Map<Object, Object> getParameterMap(Map map) {
		String sign = null;
		
		Map<Object, Object> parameters = new HashMap<Object, Object>();
		
		for ( Iterator<Entry> iterator = map.entrySet().iterator(); iterator.hasNext(); ) {
			Entry entry = iterator.next();
			try {
				Object[] o = (Object[]) entry.getValue();
				String value = o[0].toString();
				parameters.put( entry.getKey(), com.alibaba.druid.util.Base64
						.byteArrayToBase64(value.getBytes( CHARSET )) );
			} catch ( UnsupportedEncodingException e ) {
				log.error( e.getMessage() );
			} catch (Exception ex) {
				log.error( ex.getMessage() );
			}
		}
		
		try {
			parameters.put( "imei", com.alibaba.druid.util.Base64
					.byteArrayToBase64(IMEI.getBytes(CHARSET)) );
		} catch ( UnsupportedEncodingException e ) {
			log.error( e.getMessage() );
		}
		
		try {
			sign = ParameterEncryptor.encrypt( parameters );
		} catch ( Exception e1 ) {
			e1.printStackTrace();
		}
		
		parameters.put( SIGNKEY, sign );
		
		return parameters;
	}
}
