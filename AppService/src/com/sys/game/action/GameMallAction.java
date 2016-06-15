package com.sys.game.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sys.game.service.GameMallService;
import com.sys.util.JSONUtil;

/**
 * 趣游戏--商城
 * 
 * @author Maryn
 * 
 */
@Component
public class GameMallAction extends GameBaseAction {

	private static final long serialVersionUID = 1225297600827445094L;

	@Autowired
	private GameMallService gameMallService;

	// 商城产品
	public String mallProduct() {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		String pid = this.getParameter("pid");// 产品id
		log.info("into GameMallAction.mallProduct");
		log.info("pid = " + pid);
		try {
			// 根据产品id查询产品详情
			List<Map<String, Object>> list = gameMallService.mallProduct(pid);
			reqMap.put("list", JSONUtil.clobToString(list));
			reqMap.put("status", "Y");
		} catch (Exception e) {
			reqMap.put("status", "N");
			reqMap.put("info", "1003," + e.getMessage());
			log.error("GameMallAction.mallProduct failed,e : ", e);
		}
		out = JSONObject.fromObject(reqMap).toString();
		return "success";
	}
	
	// 申请商品兑换
	public String exchange() {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		String pid = this.getParameter("pid");// 产品id
		String uid = this.getParameter("uid");// 用户id
		String contact = this.getParameter("contact");// 用户联系方式
		String type = this.getParameter("type");// 联系方式类型：1 QQ, 2 手机号
		log.info("into GameMallAction.exchange");
		log.info("pid = " + pid + ", uid = " + uid + ", contact = " + contact + ", type = " + type);
		try {
			// 申请商品兑换
			gameMallService.exchange(pid, uid, contact, type);
			reqMap.put("status", "Y");
		} catch (Exception e) {
			reqMap.put("status", "N");
			reqMap.put("info", "1003," + e.getMessage());
			log.error("GameMallAction.exchange failed,e : ", e);
		}
		out = JSONObject.fromObject(reqMap).toString();
		return "success";
	}
	
	// 商品兑换记录
	public String exchangeRecord() {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		String uid = this.getParameter("uid");// 用户id
		String page = this.getParameter("page");// 页码
		String pSize = this.getParameter("pSize");// 每页数量
		log.info("into GameMallAction.exchangeRecord");
		log.info("uid = " + uid + ", page = " + page + ", pSize = " + pSize);
		try {
			// 商品兑换记录
			List<Map<String, Object>> list = gameMallService.exchangeRecord(uid, page, pSize);
			reqMap.put("record", list);
			reqMap.put("status", "Y");
		} catch (Exception e) {
			reqMap.put("status", "N");
			reqMap.put("info", "1003," + e.getMessage());
			log.error("GameMallAction.exchangeRecord failed,e : ", e);
		}
		out = JSONObject.fromObject(reqMap).toString();
		return "success";
	}
}
