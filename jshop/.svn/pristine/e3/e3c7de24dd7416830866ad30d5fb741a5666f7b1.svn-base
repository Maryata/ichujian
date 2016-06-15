package net.jeeshop.web.action.manage.customer;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.jeeshop.core.ManageContainer;
import net.jeeshop.core.Services;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.core.system.bean.User;
import net.jeeshop.core.util.StrUtils;
import net.jeeshop.services.manage.customer.CustomerService;
import net.jeeshop.services.manage.customer.bean.Customer;
import net.jeeshop.services.manage.discntSolutn.DiscntSolutnService;
import net.jeeshop.services.manage.discntSolutn.bean.DiscntSolutn;
import net.jeeshop.services.manage.product.ProductService;
import net.jeeshop.services.manage.product.bean.Product;
import net.jeeshop.services.manage.system.DeptService;
import net.jeeshop.web.action.BaseController;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("/manage/customer/")
public class CustomerAction extends BaseController<Customer>{
	private static final Logger logger = LoggerFactory.getLogger(CustomerAction.class);
	@Autowired
	private CustomerService customerService;
	@Autowired
    private DeptService deptService;
    
    @Autowired
    private DiscntSolutnService dsService; 
    
    @Autowired
    private ProductService productService;
	private static final String page_toList = "/manage/customer/customerList";
    private static final String page_toEdit = "/manage/customer/customerEdit";
    private CustomerAction(){
        super.page_toList = page_toList;
        super.page_toAdd = page_toEdit;
        super.page_toEdit = page_toEdit;
    }
	@Override
	public Services<Customer> getService() {
		return customerService;
	}

	@Override
	protected void selectListAfter(PagerModel pager) {
		pager.setPagerUrl("selectList");
	}
	 
	 @RequestMapping("toCustomerAdd")
	 public String toCustomerAdd(Customer e, ModelMap model,HttpSession session) throws Exception {
		 User u = (User) session.getAttribute(ManageContainer.manage_session_user_info);
		 e.setNickname(e.getNickname());
		 e.setProductsName(u.getNickname());
		customerService.insert(e);
		return "redirect:selectList";
	 }
	 @RequestMapping("toCustomerEdit")
		public String toEdit(Customer e, ModelMap model) throws Exception {
	        e = getService().selectOne(e);
			//getKeyValue(e);
	        model.addAttribute("e", e);
	        // 获取折扣方案
	        List<DiscntSolutn> dsList = dsService.selectList(new DiscntSolutn());
	        // 获取激活码
	        List<Product> actCodes = productService.selectActiveCodeList();
	        model.addAttribute("dsList", dsList);
	        model.addAttribute("actCodes", actCodes);
	        return page_toEdit;
		}
	 @RequestMapping("toCusAdd")
	 public String toCusAdd(Customer e, ModelMap model,HttpSession session){
		e.clear();
		model.addAttribute("e", e);
		return page_toAdd;
		 
		 
	 }
	 @RequestMapping("toCusEdit")
	 public String toCusEdit(Customer e, ModelMap model,HttpSession session) throws Exception {
		 User u = (User) session.getAttribute(ManageContainer.manage_session_user_info);
		 e.setProductsName(u.getNickname());
		 customerService.update(e);
		 return "redirect:selectList";
	 }
	 
	    @RequestMapping("unique")
		@ResponseBody
		public String unique(Customer e,HttpServletRequest request) throws IOException{
	    	String validateId=null;
	    	if(StrUtils.isNotEmpty(e.getId())){
				validateId = e.getId();
			}
	    	if(StrUtils.isNotEmpty(e.getAccount())){
	    		e.setValidateId(validateId);
	    		if(customerService.selectCount(e)>0){
	    			return ("{\"error\":\"会员账号已经存在!\"}");
	    		}else{
	    			return ("{\"ok\":\"会员账号可以使用!\"}");
	    		}
	    	}
	    	if(StrUtils.isNotEmpty(e.getEmail())){
	    		e.setValidateId(validateId);
	    		if(customerService.selectCount(e)>0){
	    			return ("{\"error\":\"邮箱已经存在!\"}");
	    		}else{
	    			return ("{\"ok\":\"邮箱可以使用!\"}");
	    		}
	    		
	    	}
	    	
			return "OK";
	 }
	 /*查询所有的用户*/   
    @RequestMapping(value = "selectCustomer", method = RequestMethod.POST)
	@ResponseBody
	public <customerService> String selectCustomer(HttpServletRequest request,Customer e){
		String detail=request.getParameter("detail");
		e.setDetail(detail);
		
		int page = 1;//分页偏移量
        if (request.getParameter("page") != null) {
        	page = Integer.parseInt(request.getParameter("page"));
        }
        e.setPageSize(5);
        e.setOffset( (page-1)*e.getPageSize() );
        PagerModel pager = getService().selectPageList(e);
        if (pager == null) {
            pager = new PagerModel();
        }
        // 计算总页数
        pager.setPagerSize((pager.getTotal() + e.getPageSize() - 1)/ e.getPageSize());
		//List<Customer> customerList=customerService.selectAllCustomer(e);
		JSONObject jsons = new JSONObject(); 
		jsons.put("customerList", pager.getList());
		jsons.put("pagerSize", pager.getPagerSize());
		return jsons.toString();
	}
}
