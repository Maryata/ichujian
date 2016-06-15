package com.sys.easybuy.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.sys.commons.AbstractAction;
import com.sys.easybuy.service.EasyBuyService;

public class EasyBuyAction extends AbstractAction{

	private static final long serialVersionUID = 1L;

	private EasyBuyService easyBuyService;
	
	private String out;

	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}

	public EasyBuyService getEasyBuyService() {
		return easyBuyService;
	}

	public void setEasyBuyService(EasyBuyService easyBuyService) {
		this.easyBuyService = easyBuyService;
	}
	
	
	// 广告位数据
	public String advertInfo() {
		String username = this.getParameter("username");// 激活码6-11位
		Map<String, Object> reqMap = new HashMap<String, Object>();
		log.info( "into EasyBuyAction.advertInfo" );
		log.info( "username = " + username );
		try {
			List<Map<String, Object>> list = easyBuyService.advertInfo(username);
			reqMap.put( "status", "Y" );
			reqMap.put( "advert", list );
		} catch ( Exception e ) {
			reqMap.put( "status", "N" );
			reqMap.put( "info", "1003," + e.getMessage() );
			log.error( "EasyBuyAction.advertInfo failed,e : " + e );
		}
		out = JSONObject.fromObject( reqMap ).toString();
		return "success";
	}
	
	// 分类列表
	public String category(){
		String username = this.getParameter("username");// 激活码6-11位
		Map<String, Object> reqMap = new HashMap<String, Object>();
		log.info( "into EasyBuyAction.category" );
		log.info( "username = " + username );
		try {
			List<Map<String, Object>> list = easyBuyService.category(username);
			reqMap.put( "status", "Y" );
			reqMap.put( "category", list );
		} catch ( Exception e ) {
			reqMap.put( "status", "N" );
			reqMap.put( "info", "1003," + e.getMessage() );
			log.error( "EasyBuyAction.category failed,e : " + e );
		}
		out = JSONObject.fromObject( reqMap ).toString();
		return "success";
	}
	
	// 可维护分类详情（首页专题详情）
	public String customableCategory(){
		String username = this.getParameter("username");// 激活码6-11位
		Map<String, Object> reqMap = new HashMap<String, Object>();
		log.info( "into EasyBuyAction.customableCategory" );
		log.info( "username = " + username );
		try {
			List<Map<String, Object>> list = easyBuyService.customableCategory(username);
			reqMap.put( "status", "Y" );
			reqMap.put( "category", list );
		} catch ( Exception e ) {
			reqMap.put( "status", "N" );
			reqMap.put( "info", "1003," + e.getMessage() );
			log.error( "EasyBuyAction.customableCategory failed,e : " + e );
		}
		out = JSONObject.fromObject( reqMap ).toString();
		return "success";
	}
	
	
	// "附近门店"
	public String storesNearby(){
		String username = this.getParameter("username");// 激活码6-11位
		Map<String, Object> reqMap = new HashMap<String, Object>();
		log.info( "into EasyBuyAction.storesNearby" );
		log.info( "username = " + username );
		try {
			List<Map<String, Object>> list = easyBuyService.storesNearby(username);
			reqMap.put( "status", "Y" );
			reqMap.put( "storesNearby", list );
		} catch ( Exception e ) {
			reqMap.put( "status", "N" );
			reqMap.put( "info", "1003," + e.getMessage() );
			log.error( "EasyBuyAction.storesNearby failed,e : " + e );
		}
		out = JSONObject.fromObject( reqMap ).toString();
		return "success";
	}
	
	// 分类详情
	public String categoryDetail(){
		String username = this.getParameter("username");// 激活码6-11位
		String cid = this.getParameter("cid");// 分类id
		String page = this.getParameter("page");// 分页
		String flag = this.getParameter("flag");// 是否可维护分类
		Map<String, Object> reqMap = new HashMap<String, Object>();
		log.info( "into EasyBuyAction.categoryDetail" );
		log.info( "username = " + username + ", cid = " + cid + ", page = " + page + ", flag = " + flag);
		try {
			List<Map<String, Object>> list = easyBuyService.categoryDetail(username, cid, page, flag);
//			list = JSONUtil.clobToString(list);
			reqMap.put( "status", "Y" );
			reqMap.put( "categoryDetail", list );
		} catch ( Exception e ) {
			reqMap.put( "status", "N" );
			reqMap.put( "info", "1003," + e.getMessage() );
			log.error( "EasyBuyAction.categoryDetail failed,e : " + e );
		}
		out = JSONObject.fromObject( reqMap ).toString();
		return "success";
	}
	
	// 产品详情
	public String productDetail(){
		String id = this.getParameter("id");// 产品id
		Map<String, Object> reqMap = new HashMap<String, Object>();
		log.info( "into EasyBuyAction.productDetail" );
		log.info( "id = " + id);
		try {
			List<Map<String, Object>> list = easyBuyService.productDetail(id);
//			list = JSONUtil.clobToString(list);
			reqMap.put( "status", "Y" );
			reqMap.put( "categoryDetail", list );
		} catch ( Exception e ) {
			reqMap.put( "status", "N" );
			reqMap.put( "info", "1003," + e.getMessage() );
			log.error( "EasyBuyAction.productDetail failed,e : " + e );
		}
		out = JSONObject.fromObject( reqMap ).toString();
		return "success";
	}
	
	// 简明产品搜索
	public String simpleSearch(){
		String username = this.getParameter("username");// 激活码6-11位
		String content = this.getParameter("content");// 产品id
		Map<String, Object> reqMap = new HashMap<String, Object>();
		log.info( "into EasyBuyAction.simpleSearch" );
		log.info( "username = " + username + ", content = " + content);
		try {
			List<Map<String, Object>> list = easyBuyService.simpleSearch(username, content);
			reqMap.put( "status", "Y" );
			reqMap.put( "categoryDetail", list );
		} catch ( Exception e ) {
			reqMap.put( "status", "N" );
			reqMap.put( "info", "1003," + e.getMessage() );
			log.error( "EasyBuyAction.simpleSearch failed,e : " + e );
		}
		out = JSONObject.fromObject( reqMap ).toString();
		return "success";
	}
	
	// 产品搜索
	public String search(){
		String username = this.getParameter("username");// 激活码6-11位
		String content = this.getParameter("content");// 产品id
		Map<String, Object> reqMap = new HashMap<String, Object>();
		log.info( "into EasyBuyAction.search" );
		log.info( "username = " + username + ", content = " + content);
		try {
			List<Map<String, Object>> list = easyBuyService.search(username, content);
//			list = JSONUtil.clobToString(list);
			reqMap.put( "status", "Y" );
			reqMap.put( "categoryDetail", list );
		} catch ( Exception e ) {
			reqMap.put( "status", "N" );
			reqMap.put( "info", "1003," + e.getMessage() );
			log.error( "EasyBuyAction.search failed,e : " + e );
		}
		out = JSONObject.fromObject( reqMap ).toString();
		return "success";
	}
	
	// "猜你喜欢"
	public String guessULike(){
		String pid = this.getParameter("pid");// 激活码6-11位
		Map<String, Object> reqMap = new HashMap<String, Object>();
		log.info( "into EasyBuyAction.guessULike" );
		log.info( "pid = " + pid );
		try {
			List<Map<String, Object>> list = easyBuyService.guessULike(pid);
//			list = JSONUtil.clobToString(list);
			reqMap.put( "status", "Y" );
			reqMap.put( "guessULike", list );
		} catch ( Exception e ) {
			reqMap.put( "status", "N" );
			reqMap.put( "info", "1003," + e.getMessage() );
			log.error( "EasyBuyAction.guessULike failed,e : " + e );
		}
		out = JSONObject.fromObject( reqMap ).toString();
		return "success";
	}
}
