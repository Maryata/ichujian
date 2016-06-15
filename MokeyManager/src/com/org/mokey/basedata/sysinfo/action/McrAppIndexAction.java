package com.org.mokey.basedata.sysinfo.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.org.mokey.basedata.sysinfo.service.McrAppColService;
import com.org.mokey.basedata.sysinfo.service.McrAppIndexService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.util.StrUtils;

/**
 * 微用帮：首页排版
 */
@SuppressWarnings("serial")
public class McrAppIndexAction extends AbstractAction{
	
	// 获取合集列表
	public String mcrAppIndex(){
		// 获取cid
		log.info("into McrAppIndexAction.mcrAppIndex");
		try{
			// 获取所有合集
			List<Map<String,Object>> list = mcrAppColService.mcrAppColList();
			// 查询当前首页显示的合集
			List<Map<String,Object>> col = mcrAppIndexService.currCol();
			String colId = "";
			String colCnt = "";
			if(StrUtils.isNotEmpty(col)){
				Map<String, Object> map = col.get(0);
				colId = (map.get("CID")==null)?"":map.get("CID").toString();
				colCnt = (map.get("CNT")==null)?"":map.get("CNT").toString();
			}
			// 存入值栈
			getRequest().setAttribute("cols", list);
			getRequest().setAttribute("colId", colId);
			getRequest().setAttribute("colCnt", colCnt);
		}catch(Exception e){
			log.error("McrAppIndexAction.mcrAppIndex failed,",e);
		}
		return "mcrAppIndex";
	}
	
	// 分页查询首页中未显示的分类
	public String cateList(){
		// 获取page
		int page = getParameter2Int("page", 1);
		// 应用名称
		String name = getParameter("name");
		Map<String, Object> retmap = new HashMap<String, Object>();
		log.info("into McrAppCateAction.cateList");
		log.info("page=" + page);
		try{
			retmap = mcrAppIndexService.cateList(page,name);// 分页查询首页中未显示的分类
			retmap.put("status", "Y");
			// 回写查询结果
			this.writeJSONToResponse(retmap);
		}catch(Exception e){
			retmap.put("status", "N");
			log.error("McrAppCateAction.cateList failed, e : " + e);
		}
		return NONE;
	}
	
	// 查询首页显示的分类
	public String indexColList(){
		// 获取page
		int page = getParameter2Int("page", 1);
		// 应用名称
		String name = getParameter("name");
		Map<String, Object> retmap = new HashMap<String, Object>();
		log.info("into McrAppCateAction.indexColList");
		log.info("page=" + page);
		try{
			retmap = mcrAppIndexService.indexColList(name, (page-1)*5, 5);
			retmap.put( "status", "Y" );
			// 回写查询结果
			this.writeJSONToResponse(retmap);
		}catch(Exception e){
			retmap.put( "status", "N" );
			log.error("McrAppCateAction.indexColList failed, e : " + e);
		}
		return NONE;
	}
	
	// 首页排版
	public String handle(){
		// 获取请求参数
		String col = getParameter("col");// 合集id
		String colCnt = getParameter("colCnt");// 合集中应用的数量
		String id = getParameter("id");// 分类id
		String removeId = getParameter("removeId");// 移除出的分类id
		String order = getParameter("order");// 分类的排序
		String number = getParameter("number");// 分类中的应用的数量
		log.info("into McrAppCateAction.handle");
		log.info("col=" + col + ", colCnt=" + colCnt + ", id=" + id + "," +
				" removeId=" + removeId + ", order=" + order + ", number=" + number);
		try{
			mcrAppIndexService.handle(col,colCnt,id,removeId,order,number);
		}catch(Exception e){
			log.error("McrAppCateAction.handle failed, e : " + e);
		}
		return NONE;
	}
	
	private McrAppIndexService mcrAppIndexService;
	
	private McrAppColService mcrAppColService;

	public McrAppIndexService getMcrAppIndexService() {
		return mcrAppIndexService;
	}

	public void setMcrAppIndexService(McrAppIndexService mcrAppIndexService) {
		this.mcrAppIndexService = mcrAppIndexService;
	}
	
	public McrAppColService getMcrAppColService() {
		return mcrAppColService;
	}

	public void setMcrAppColService(McrAppColService mcrAppColService) {
		this.mcrAppColService = mcrAppColService;
	}
	
}
