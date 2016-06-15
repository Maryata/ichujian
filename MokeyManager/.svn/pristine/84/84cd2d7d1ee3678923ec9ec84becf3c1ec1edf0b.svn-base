package com.org.mokey.basedata.baseinfo.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import com.org.mokey.basedata.baseinfo.service.ActiveCodeService;
import com.org.mokey.common.AbstractAction;
import com.org.mokey.common.util.str.UnicodeReader;
import com.org.mokey.util.JSONUtil;

/**
 * 基础信息-激活码信息
 * 
 * @author giles
 * 
 */
public class ActiveCodeAction extends AbstractAction {

	private ActiveCodeService activeCodeService;

	/** 输出内容 */
	private String out; 

	/**
	 * 详细列表查询
	 * 
	 * @return
	 */
	public String getActiveList() {
		log.debug("getActiveList init");
		Map<String, Object> retMap = new HashMap<String, Object>();

		String isActive = getParameter("isActive");
		String code = getParameter("code");
		String supplier = getParameter("supplier");
		String isRemark = getParameter("isRemark");
		String isSample =getParameter("isSample");
		int start = getParameter2Int("start", 0);
		int limit = getParameter2Int("limit", 10);

		try {
			retMap = activeCodeService.getActiveListMap(isActive, code,supplier,isSample, isRemark, start, limit);
			retMap.put("status", "Y");
		} catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "getActiveList failed");
			log.error("getActiveList failed,", e);
		}
		out = JSONObject.fromObject(retMap).toString();
		log.debug("getActiveList out");
		return SUCCESS;
	}

	// 导入文件添加到数据表中
	// YYYYMMDDhms
	public String upload() {
		log.debug("init upload");
		Map<String, Object> retMap = new HashMap<String, Object>();
		boolean isPass = true;
		int i = 1;

		try {
			MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) getRequest();
			File[] files = multiWrapper.getFiles("c_reportFile");
			List<String> list = new ArrayList<String>();
			try {
                //InputStreamReader read = new InputStreamReader( new FileInputStream(files[0]),"UTF-8");//考虑到编码格式
				//BufferedReader bufferedReader = new BufferedReader(read);
				FileInputStream in = new FileInputStream(files[0]);  
				BufferedReader br = new BufferedReader(new UnicodeReader(in, Charset.defaultCharset().name()));
				String line = null;
				try {
					while ((line = br.readLine()) != null) {
						//log.debug(line);
						if (line.length() != 21) {
							// msg="第"+i+"行数据的的长度不正确！";
							retMap.put("status", "第" + i + "行数据的的长度不正确！");
							isPass = false;
							//break;
						} else {
							list.add(line);
							isPass = true;
						}
						i++;
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			if (isPass) {
				Map<String,Object> saveMap = new HashMap<String,Object>();
				String map = getParameter("saveParam"); 
				Map<String, Object> Map = (Map) JSONUtil.JSONString2Bean(map,Map.class);

				boolean isTrue = true;
				
				List<String> _l_exists = activeCodeService.list( list );
				
				
				if(_l_exists.size() >= 1)
					list.removeAll( _l_exists );

				if (isTrue) {
					List<Map<String,Object>> saveList = new ArrayList<Map<String,Object>>();
					for (int A = 0; A < list.size(); A++) {
						
						saveMap.put("c_issample", Map.get("C_ISSAMPLE"));
						saveMap.put("c_batch", Map.get("C_BATCH"));
						saveMap.put("c_code", list.get(A));
						//saveMap.put("c_id", null);
						saveMap.put("c_isvalid", 1);
						Date date = new Date();
						saveMap.put("c_sysdate", date);
						
						saveList.add( saveMap );
						
						saveMap = new HashMap<String, Object>();
					}
					
					activeCodeService.batch( saveList );
					retMap.put("success", true);
				}
//=============================================================================================	
//				List<String> _l_exists = new ArrayList<String>();
//				//循环验证所有的数据是否有重复，一旦有一个就跳出循环
//				for (int A = 0; A < list.size(); A++) {
//					String _code = list.get(A);
//					int count = activeCodeService.findActive(_code);
//					if (count> 0) {
//						//retMap.put("status", "第" + (A + 1) + "行数据为重复数据！");
//						//isTrue = false;
//						//break;
//						_l_exists.add( _code );
//					}
//				}
//				
//				if(_l_exists.size() >= 1)
//					list.removeAll( _l_exists );
//
//				if (isTrue) {
//					for (int A = 0; A < list.size(); A++) {
//
//						saveMap.put("c_issample", Map.get("C_ISSAMPLE"));
//						saveMap.put("c_batch", Map.get("C_BATCH"));
//						saveMap.put("c_code", list.get(A));
//						saveMap.put("c_id", null);
//						saveMap.put("c_isvalid", 1);
//						Date date = new Date();
//						saveMap.put("c_sysdate", date);
//						activeCodeService.saveActiveRemark(saveMap);
//					}
//					//retMap.put("status", "Y");
//					retMap.put("success", true);
//				}
//==================================================================================================				
				
			}
			retMap.put("info", "upload failed");
		} catch (Exception e) {
			log.error("upload failed,", e);

		} finally {
			try {
				writeToResponse(JSONObject.fromObject(retMap).toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		log.debug("out upload");
		return NONE;
	}

	@SuppressWarnings("unchecked")
	public String saveActiveRemark() {
		log.debug("init saveActiveRemark");
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			String saveParam = getParameter("saveParam");
			Map<String, Object> saveMap = (Map) JSONUtil.JSONString2Bean(
					saveParam, Map.class);
			String cId = activeCodeService.saveActiveRemark(saveMap);

			retMap.put("status", "Y");
			retMap.put("c_id", cId);
			retMap.put("success", true);
		} catch (Exception e) {
			retMap.put("status", "N");
			retMap.put("info", "saveActiveRemark failed");
			log.error("saveActiveRemark failed,", e);
		} finally {
			try {
				writeToResponse(JSONObject.fromObject(retMap).toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		log.debug("out saveActiveRemark");
		return NONE;

	}

	public ActiveCodeService getActiveCodeService() {
		return activeCodeService;
	}

	public void setActiveCodeService(ActiveCodeService activeCodeService) {
		this.activeCodeService = activeCodeService;
	}

	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}
}
