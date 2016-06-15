package net.jeeshop.web.action.manage.sampleApply;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.jeeshop.core.ErpInfo;
import net.jeeshop.core.ManageContainer;
import net.jeeshop.core.Services;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.core.system.bean.User;
import net.jeeshop.core.util.StrUtils;
import net.jeeshop.services.front.catalog.bean.Catalog;
import net.jeeshop.services.front.orderSerialNum.OrderSerialNumService;
import net.jeeshop.services.front.orderSerialNum.bean.OrderSerialNum;
import net.jeeshop.services.manage.orderdetail.bean.Orderdetail;
import net.jeeshop.services.manage.product.ProductService;
import net.jeeshop.services.manage.product.bean.Product;
import net.jeeshop.services.manage.sampleApply.SampleApplyDetailService;
import net.jeeshop.services.manage.sampleApply.SampleApplyService;
import net.jeeshop.services.manage.sampleApply.bean.SampleApply;
import net.jeeshop.services.manage.sampleApply.bean.SampleApplyDetail;
import net.jeeshop.web.action.BaseController;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/manage/sampleApply/")
public class SampleApplyAction extends BaseController<SampleApply>{
	private static final String page_toList = "/manage/sampleApply/sampleApplyList";
	private static final String page_toEdit = "/manage/sampleApply/sampleApplyEdit";
	private static final Logger logger = LoggerFactory.getLogger(SampleApplyAction.class);
	@Autowired
	private SampleApplyService sampleApplyService;
	@Autowired
	private SampleApplyDetailService sampleApplyDetailService;
	@Autowired
	private ProductService productService;
	@Autowired
	private OrderSerialNumService orderSerialNumService;
	
	 private SampleApplyAction(){
        super.page_toList = page_toList;
        super.page_toAdd = page_toEdit;
        //super.page_toEdit = page_toEdit;
    }
	public Services<SampleApply> getService() {
		return sampleApplyService;
	}
	
	@Override
	protected void selectListAfter(PagerModel pager) {
		pager.setPagerUrl("selectList");
	}
	/*样品添加页面跳转*/
	@RequestMapping("toSampleApplyAdd")
	public String toSampleApplyAdd(){
		return page_toAdd;
	}
	 @RequestMapping(value = "findModel", method = RequestMethod.POST)
	 @ResponseBody
	 public  String selectCustomer(HttpServletRequest request){
		String mainCatalogCode=request.getParameter("code");
		Catalog mainItem= systemManager.getCatalogsCodeMap().get(mainCatalogCode);
		JSONObject jsons = new JSONObject(); 
		jsons.put("mainItem", mainItem.getChildren());
		return jsons.toString();
	 }
	 /*判断顾客已发送的样品数量*/
	 @RequestMapping(value = "judgeSampleNumber", method = RequestMethod.POST)
	 @ResponseBody
	public int judgeSampleNumber(HttpServletRequest request){
		String accountId=request.getParameter("accountId");
		SampleApply sa=new SampleApply();
		sa.setCustomerId(accountId);
		int number=0;
		number= sampleApplyService.judgeSampleNumber(sa);
		return number;
	}
	 
	 
	@RequestMapping(value = "checkApplyNum")
	@ResponseBody
	public String checkApplyNum(SampleApplyDetail smd,HttpServletRequest request){
		List<SampleApplyDetail> customerList= sampleApplyDetailService.checkMessage(smd);//已添加的品牌列表
		JSONObject retObj = new JSONObject();
		retObj.put("list", customerList);
		return retObj.toString();
	}
	 
	 
	 /*添加信息*/
	 @RequestMapping(value = "addSampleApply", method = RequestMethod.POST)
	 @ResponseBody
	 public String addSampleApply(HttpSession session,SampleApply sa,SampleApplyDetail smd,ModelMap model){
		 model= new ModelMap();
		 User u = (User) session.getAttribute(ManageContainer.manage_session_user_info);
		sa.setCreateUser(u.getUsername());
		sa.setCreateUserId(u.getId());
		
		//样品单序列号
		OrderSerialNum osn = new OrderSerialNum();
		osn.setUcode("000000");
		String serialNum = orderSerialNumService.getSerialNum(osn);
		sa.setSampleNo("Y"+serialNum);
		
		sa.setAmount("0");//总价
		sampleApplyService.insert(sa);//添加主表
		smd.setsId(sa.getId());//从表插入关联字段
		//-------------------------------------------- ----------------------
		 List<SampleApplyDetail> smdList=new ArrayList<SampleApplyDetail>();
		 String[] productIds = smd.getProductIds();
		 String[] productNames=smd.getProductNames();
	     String[] brandNames=smd.getBrandNames();
	     String[] modelNames=smd.getModelNames();
	     String[] plineNames=smd.getPlineNames();
	     String[] productNums=smd.getProductNums();
	     String[] plineCodes=smd.getPlineCodes();
	     List<SampleApplyDetail> list = new ArrayList<SampleApplyDetail>();
		 if(null !=plineCodes && null!=productNums && null !=productNums  && null!=productIds && null !=productNames    && null !=brandNames  && null !=modelNames  && null !=plineNames  &&
				plineCodes.length>0 && productIds.length>0 && productNames.length>0 &&  brandNames.length>0 && modelNames.length>0 && plineNames.length>0){
			for (int i = 0; i < productIds.length; i++) {
				SampleApplyDetail sampleApplyDetail=new SampleApplyDetail();
				sampleApplyDetail.setBrandName(brandNames[i]);
				sampleApplyDetail.setModelName(modelNames[i]);
				sampleApplyDetail.setPlineName(plineNames[i]);
				//sampleApplyDetail.setProductCode(productCodes[i]);
				sampleApplyDetail.setProductId(productIds[i]);
				sampleApplyDetail.setProductName(productNames[i]);
				sampleApplyDetail.setProductNum(productNums[i]);
				sampleApplyDetail.setProductPrice("0");
				sampleApplyDetail.setsId(smd.getsId());
				//------------------查询产品型号-------------------------
				String id=productIds[i];
				String code=plineCodes[i];
				Product e=new Product();
				e.setCatalogID(id);
				e.setPline(code);
				Product product=productService.selectPcode(e);
				sampleApplyDetail.setProductCode(product.getPcode());//查询到的pcode 放入数据库
				
				sampleApplyDetailService.insert(sampleApplyDetail);//添加从表
				
				list.add(sampleApplyDetail);
			}
			
			sa.setSaList(list);
			 
		 }
		
		//sampleApplyDetailService.inserDetail(sa, smd);
		 
		try {
			//ErpInfo.addSample(sa);
		} catch (Exception e) {
			logger.error(">>>>>>>>>>>>>>>>>>>>>>>>Add sample to ERP failed!");
		} 
		
		model.addAttribute("msg", "success");
		return JSON.toJSONString(model);
	 }
	 /*查询指定会员的样品申请信息*/
	 @RequestMapping(value = "checkMessage", method = RequestMethod.POST)
	 @ResponseBody
	 public  String checkMessage(HttpServletRequest request,SampleApplyDetail smd,SampleApply sa){ 
		 String customerId=request.getParameter("customerId");
		 String id=request.getParameter("id");
		 if(id!=""){
			 smd.setsId(id);
		 }
		 if(customerId!=""){
			sa.setCustomerId(customerId) ;
		 }
		 List<SampleApplyDetail> customerList= sampleApplyDetailService.checkMoble(smd);
		 JSONObject jsons = new JSONObject(); 
		 jsons.put("customer", customerList);
		 return jsons.toString();
	 }
	 
	 
	 /*查询订单的库存*/
	 @RequestMapping(value = "get_sampleapply_stock", method = RequestMethod.POST)
	 @ResponseBody
	 public  String get_sampleapply_stock(HttpServletRequest request,SampleApplyDetail smd){ 
		 //String customerId=request.getParameter("customerId");
		 String id=request.getParameter("id");
		 if(id!=""){
			 smd.setsId(id);
		 }
		 
		 List<SampleApplyDetail> customerList= sampleApplyDetailService.checkMoble(smd);
		 if(StrUtils.isNotEmpty(customerList)){
				for(SampleApplyDetail item : customerList){
					item.setSalable_qty(ErpInfo.get_sample_stock(item.getProductCode()));
				}
			}
		 JSONObject jsons = new JSONObject(); 
		 jsons.put("list", customerList);
		 return jsons.toString();
	 }
	 /**
	  * 推送样品订单到erp
	  * @param session
	  * @param request
	  * @param smd
	  * @param sa
	  * @return
	  */
	 @RequestMapping(value = "toEdit", method = RequestMethod.POST)
	 @ResponseBody
	 public String toEdit (HttpSession session,HttpServletRequest request,SampleApplyDetail smd,SampleApply sa){
		 String id=request.getParameter("id");
		 if(id!=""){
			 smd.setsId(id);
		 }
		 User u = (User) session.getAttribute(ManageContainer.manage_session_user_info);
			sa.setCreateUser(u.getUsername());
			sa.setCreateUserId(u.getId());
			
		 List<SampleApplyDetail> customerList= sampleApplyDetailService.checkMoble(smd);//获取用户添加的样品信息
		 if(StrUtils.isNotEmpty(customerList)){
				for(SampleApplyDetail item : customerList){
					sa.setId(item.getsId());//用户id
				}
			}
		 sa.setStatus("1");
		 SampleApply sampleApply = sampleApplyService.selectOne(sa);//用户信息
		 if(sampleApply!=null){
			 sa.setAddress(sampleApply.getAddress());
			 sa.setAmount(sampleApply.getAmount());
			 sa.setCompany(sampleApply.getCompany());
			 sa.setContact(sampleApply.getContact());
			 sa.setCreatedate(sampleApply.getCreatedate());
			 sa.setCustomerId(sampleApply.getCustomerId());
			 sa.setExpressCode(sampleApply.getExpressCode());
			 sa.setExpressName(sampleApply.getExpressName());
			 sa.setPhone(sampleApply.getPhone());
			 sa.setSampleNo(sampleApply.getSampleNo());
			 
		 }
		 int row = sampleApplyService.update(sa);
		 JSONObject json = new JSONObject();
		 if(row>0){
			 sa.setSaList(customerList);
			 try {
					ErpInfo.addSample(sa);//推送样品订单
				} catch (Exception e) {
					logger.error(">>>>>>>>>>>>>>>>>>>>>>>>Add sample to ERP failed!");
				} 
				
			 json.put("status", 1);
		 }
			 
			return json.toString();
	 }
	 
	 
	 
}
