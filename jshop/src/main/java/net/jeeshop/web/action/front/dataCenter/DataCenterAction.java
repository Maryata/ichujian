package net.jeeshop.web.action.front.dataCenter;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.core.front.SystemManager;
import net.jeeshop.core.util.StrUtils;
import net.jeeshop.services.front.account.bean.Account;
import net.jeeshop.services.front.dataCenter.DataCenterService;
import net.jeeshop.services.front.dataCenter.bean.DataApply;
import net.jeeshop.services.front.dataCenter.bean.DataCenter;
import net.jeeshop.services.front.dataCenter.bean.DataFile;
import net.jeeshop.web.action.BaseController;
import net.jeeshop.web.util.LoginUserHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("frontDataCenterActionController")
@RequestMapping("dataCenter")
public class DataCenterAction extends BaseController<DataCenter> {

	@Autowired
	private DataCenterService dataCenterService;
	
	@Override
	public DataCenterService getService() {
		return dataCenterService;
	}
	
	/**
	 * 查询资料中心数据
	 * @return
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,@ModelAttribute("e")DataCenter e){
		if (LoginUserHolder.getLoginAccount() == null) {
			//return AccountAction.toLoginRedirect;
		}
		logger.debug("dicId:{}",e.getDicId());
		//设置查询默认查询第一个栏目数据
		Map<String,String> dicMap = SystemManager.getInstance().getSystemDicts("dataCenter_dicId");
		if(StrUtils.isEmpty(e.getDicId()) && StrUtils.isNotEmpty(dicMap)){
			Set<?> set = dicMap.entrySet();
			for(Iterator<?> iter = set.iterator(); iter.hasNext();){
				@SuppressWarnings("rawtypes")
				Map.Entry entry = (Map.Entry)iter.next();
				e.setDicId((String)entry.getKey());
				break;
			}
		}
		request.setAttribute("dicMap", dicMap);
		
		if("video".equals(e.getDicId())){//视频不分页
			e.setPageSize(1000);
		}else{
			e.setPageSize(9);
		}
		try {
			//super.selectList(request, e);
			
			int offset = 0;//分页偏移量
	        if (request.getParameter("pager.offset") != null) {
	            offset = Integer.parseInt(request.getParameter("pager.offset"));
	        }
	        if (offset < 0)
	            offset = 0;
	        e.setOffset(offset);
	        PagerModel pager = this.getService().selectPageList(e);
	        if (pager == null) {
	            pager = new PagerModel();
	        }
	        // 计算总页数
	        pager.setPagerSize((pager.getTotal() + pager.getPageSize() - 1) / pager.getPageSize());
	        pager.setPagerUrl(request.getContextPath()+"/dataCenter/list?dicId="+e.getDicId());
	        request.setAttribute("pager", pager);
			if("video".equals(e.getDicId())){
				DataFile df = new DataFile();
				df.setDicId("video");
				List<DataFile> files = this.getService().selectDataFiles(df);
				request.setAttribute("files", files);
			}
		} catch (Exception e1) {
			logger.error("list faild", e1);
		}
		request.setAttribute("e", e);
		return "account/dataCenters";//
	}
	
	@RequestMapping("{id}")
	public String dataCenter(ModelMap model, @PathVariable("id")String id){
		if (LoginUserHolder.getLoginAccount() == null) {
			//return AccountAction.toLoginRedirect;
		}
		DataCenter e = new DataCenter();
		e.setId(id);
		e = this.getService().selectOne(e);
		if(StrUtils.isNotEmpty(e)){
			DataFile df = new DataFile();
			df.setcId(id);
			List<DataFile> files = this.getService().selectDataFiles(df);
			model.addAttribute("files", files);
		}
		model.addAttribute("e", e);
		return "account/dataDetail";
	}
	
	/**
	 * 申请资料
	 * @return
	 */
	@RequestMapping("applyData")
	@ResponseBody
	public String applyData(ModelMap model, DataApply e) {
		Account acc = LoginUserHolder.getLoginAccount();
		if (acc == null) {
			return this.returnMsg("-1", "请登录");
		}
		e.setAccountId(acc.getId());
		this.getService().insertDataApply(e);
		e.clear();
		return this.returnMsg("1", "申请资料成功");
	}

}
