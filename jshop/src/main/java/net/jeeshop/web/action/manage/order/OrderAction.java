package net.jeeshop.web.action.manage.order;import java.io.IOException;import java.text.DecimalFormat;import java.util.Collections;import java.util.List;import java.util.Map;import javax.servlet.http.HttpServletRequest;import net.jeeshop.core.ErpInfo;import net.jeeshop.core.KeyValueHelper;import net.jeeshop.core.ManageContainer;import net.jeeshop.core.dao.page.PagerModel;import net.jeeshop.core.exception.UpdateOrderStatusException;import net.jeeshop.core.front.SystemManager;import net.jeeshop.core.system.bean.User;import net.jeeshop.core.util.DateTimeUtil;import net.jeeshop.core.util.StrUtils;import net.jeeshop.services.front.area.bean.Area;import net.jeeshop.services.manage.account.AccountService;import net.jeeshop.services.manage.notifyTemplate.NotifyTemplateService;import net.jeeshop.services.manage.order.OrderService;import net.jeeshop.services.manage.order.bean.Order;import net.jeeshop.services.manage.orderdetail.OrderdetailService;import net.jeeshop.services.manage.orderdetail.bean.Orderdetail;import net.jeeshop.services.manage.orderlog.OrderlogService;import net.jeeshop.services.manage.orderlog.bean.Orderlog;import net.jeeshop.services.manage.ordermain.OrdermainService;import net.jeeshop.services.manage.ordermain.bean.Ordermain;import net.jeeshop.services.manage.orderpay.OrderpayService;import net.jeeshop.services.manage.orderpay.bean.Orderpay;import net.jeeshop.services.manage.ordership.OrdershipService;import net.jeeshop.services.manage.ordership.bean.Ordership;import net.jeeshop.services.manage.product.ProductService;import net.jeeshop.web.action.BaseController;import net.jeeshop.web.util.DataUtil;import net.jeeshop.web.util.LoginUserHolder;import net.jeeshop.web.util.RequestHolder;import org.apache.commons.lang.StringUtils;import org.slf4j.LoggerFactory;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Controller;import org.springframework.ui.ModelMap;import org.springframework.web.bind.annotation.RequestMapping;import org.springframework.web.bind.annotation.RequestMethod;import org.springframework.web.bind.annotation.ResponseBody;import com.alibaba.fastjson.JSON;import com.alibaba.fastjson.JSONObject;/** * 订单管理 *  * @author jqsl2012@163.com * @author dylan *  */@Controller@RequestMapping("/manage/order/")public class OrderAction extends BaseController<Order> {	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(OrderAction.class);	private static final long serialVersionUID = 1L;	@Autowired	private OrderService orderService;	@Autowired	private OrderpayService orderpayService;	@Autowired	private OrdershipService ordershipService;	@Autowired	private OrderdetailService orderdetailService;	@Autowired	private ProductService productService;	@Autowired	private OrderlogService orderlogService;	@Autowired	private OrdermainService  ordermainService;	@Autowired	private NotifyTemplateService notifyTemplateService;	@Autowired	private AccountService accountService;	public OrdermainService getOrdermainService() {		return ordermainService;	}	public void setOrdermainService(OrdermainService ordermainService) {		this.ordermainService = ordermainService;	}	private static final String page_toList = "/manage/order/orderList";	private static final String page_toAdd = "/manage/order/orderEdit";	private static final String page_toEdit = "/manage/order/orderEdit";	private static final String page_toPrint = "/manage/order/orderPrint";	private static final String page_selectOrdership = "/manage/order/updateOrdership";	private OrderAction() {		super.page_toList = page_toList;		super.page_toAdd = page_toAdd;		super.page_toEdit = page_toEdit;	}		@Override	public OrderService getService() {		return orderService;	}	@Override	protected void selectListAfter(PagerModel pager) {		super.selectListAfter(pager);		if(pager.getList()!=null){			//订单状态中文化显示。			for(int i=0;i<pager.getList().size();i++){				Order item = (Order) pager.getList().get(i);				//item.setStatusStr(KeyValueHelper.get("order_status_"+item.getStatus()));				item.setPaystatusStr(KeyValueHelper.get("order_paystatus_"+item.getPaystatus()));			}		}	}	/**	 * 退款管理、退货管理 页面必须直接显示与退款、退款状态相一致的数据	 */	@Override	protected void setParamWhenInitQuery(Order e) {		String refundStatus = RequestHolder.getRequest().getParameter("refundStatus");		String status = RequestHolder.getRequest().getParameter("status");		String paystatus = RequestHolder.getRequest().getParameter("paystatus");//		String notCancel = getRequest().getParameter("notCancel");		logger.error("refundStatus="+refundStatus+",status="+status+",paystatus="+paystatus);				if(StringUtils.isNotBlank(refundStatus)){			e.setRefundStatus(refundStatus);		}		if(StringUtils.isNotBlank(status)){			e.setStatus(status);		}		if(StringUtils.isNotBlank(paystatus)){			e.setPaystatus(paystatus);		}		//--------------TODO 设置查询范围:		User user = LoginUserHolder.getLoginUser();		e.getSqlMap().put("dsf", DataUtil.dataScopeFilter(user));			}		/**	 * 订单打印功能	 * @return	 * @throws Exception	 */	@RequestMapping("toPrint")	public String toPrint(Order e, ModelMap model) throws Exception {		if(StringUtils.isBlank(e.getId())){			throw new NullPointerException("订单ID不能为空！");		}				//加载指定的订单信息		e = orderService.selectById(e.getId());				//加载收货人地址信息		Ordership ordership = new Ordership();		ordership.setOrderid(e.getId());		ordership = ordershipService.selectOne(ordership);		/*if(ordership==null){			throw new NullPointerException("系统查询不到收货人地址信息！");		}*/		e.setOrdership(ordership);				//加载订单项列表 以及 产品信息		Orderdetail orderdetail = new Orderdetail();		orderdetail.setOrderID(Integer.valueOf(e.getId()));		List<Orderdetail> orderdetailList = orderdetailService.selectList(orderdetail);		if(orderdetailList==null){			throw new NullPointerException("查询不到订单明细信息！");		}		e.setOrderdetail(orderdetailList);		model.addAttribute("e", e);		return page_toPrint;	}		/**	 * 查看订单详细信息	 */	@Override	public String toEdit(Order e, ModelMap model) throws Exception {		if(StringUtils.isBlank(e.getId())){			throw new NullPointerException("订单ID不能为空！");		}				//加载指定的订单信息		e = orderService.selectOne(e);		if(e==null){			throw new NullPointerException("根据订单ID查询不到订单！");		}				//订单各种状态 中文化。这样做是为了考虑到以后国际化的需要		if(StringUtils.isNotBlank(e.getStatus())){			e.setStatusStr(KeyValueHelper.get("order_status_"+e.getStatus()));		}		if(StringUtils.isNotBlank(e.getRefundStatus())){			e.setRefundStatusStr(KeyValueHelper.get("order_refundStatus_"+e.getRefundStatus()));		}		if(StringUtils.isNotBlank(e.getPaystatus())){			e.setPaystatusStr(KeyValueHelper.get("order_paystatus_"+e.getPaystatus()));		}				//加载支付记录		/*Orderpay orderpay = new Orderpay();		orderpay.setOrderid(e.getId());		e.setOrderpayList(orderpayService.selectList(orderpay));		if(e.getOrderpayList()!=null){			for(int i=0;i<e.getOrderpayList().size();i++){				Orderpay orderpayInfo = e.getOrderpayList().get(i);				String paymethod = KeyValueHelper.get("orderpay_paymethod_"+orderpayInfo.getPaymethod());				orderpayInfo.setPaymethod(paymethod);			}		}*/				//加载订单配送记录		e.setOrdership(ordershipService.selectOne(new Ordership(e.getId())));		//加载主表的订单信息		Ordermain ordermain = new Ordermain();		ordermain.setOrderID(Integer.valueOf(e.getId()));		List<Ordermain> ordermainList = ordermainService.selectList(ordermain);		e.setOrdermain(ordermainList);		//加载订单项列表 以及 产品信息		Orderdetail orderdetail = new Orderdetail();		orderdetail.setOrderID(Integer.valueOf(e.getId()));		List<Orderdetail> orderdetailList = orderdetailService.selectList(orderdetail);		if(orderdetailList==null || orderdetailList.size()==0){			throw new NullPointerException("订单数据异常，订单未包含任何订单项数据！");		}		e.setOrderdetail(orderdetailList);		//检查此订单是否含赠品		for(int i=0;i<e.getOrderdetail().size();i++){			Orderdetail item = e.getOrderdetail().get(i);			if(StringUtils.isNotBlank(item.getGiftID())){				e.setHasGift(true);				break;			}		}				//加载订单支付日志记录		if(StringUtils.isNotBlank(e.getId())){			e.setOrderlogs(orderlogService.selectList(new Orderlog(e.getId())));			if(e.getOrderlogs()==null){				e.setOrderlogs(Collections.EMPTY_LIST);			}			logger.error(">>>orderlogs.size="+e.getOrderlogs().size());		}				String expectDateTemp = DateTimeUtil.dateAdd("d", 8, new java.util.Date(), "yyyy-MM-dd");		model.addAttribute("minDate",expectDateTemp);		model.addAttribute("nowDate",DateTimeUtil.dateAdd("d", 1, new java.util.Date(), "yyyy-MM-dd"));		model.addAttribute("nowDateTime",DateTimeUtil.dateAdd("d", 0, new java.util.Date(), "yyyy-MM-dd HH:mm"));		model.addAttribute("e", e);		return page_toEdit;	}		/**	 * 后台添加订单支付记录	 * @return	 * @throws Exception 	 */	@RequestMapping(value = "insertOrderpay", method = RequestMethod.POST)	public String insertOrderpay(ModelMap model, Order e) throws Exception {		logger.error(">>>addOrderpay...orderid="+e.getId());		if(StringUtils.isBlank(e.getId())){			throw new NullPointerException(ManageContainer.OrderAction_param_null);		}				checkStatus1(e);				e.getOrderpay().setOrderid(e.getId());//订单ID		e.getOrderpay().setTradeNo("tradeNoTest");		e.getOrderpay().setPaystatus(Orderpay.orderpay_paystatus_y);//假设支付成功		orderpayService.insert(e.getOrderpay());		RequestHolder.getRequest().getSession().setAttribute("optionMsg", "添加支付记录成功！");				insertOrderlog(e.getId(),"【增加支付记录】增"+e.getOrderpay().getPayamount()+"￥;");				Order oInfo = new Order();		oInfo.setId(e.getId());		oInfo.setPaystatus(Order.order_paystatus_y);//全额支付		orderService.update(oInfo);		//		toEdit2();		return "redirect:toEdit?id="+e.getId();	}		DecimalFormat df = new DecimalFormat("0.00");		/**	 * 设置订单为审核通过	 * @return	 * @throws IOException 	 */	@RequestMapping(value = "updateOrderStatus")//, method = RequestMethod.POST	public String updateOrderStatus(ModelMap model, Order e){		logger.error("updateOrderStatus id = "+e.getId()+",status="+e.getStatus());				String isConfirmStatus = RequestHolder.getRequest().getParameter("isConfirmStatus");		if(StringUtils.isBlank(e.getId()) || ( StringUtils.isBlank(e.getStatus()) && !"y".equals(isConfirmStatus) ) ){			throw new NullPointerException(ManageContainer.OrderAction_param_null);		}				Order orderInfo = orderService.selectById(e.getId());		if(orderInfo==null){			throw new UpdateOrderStatusException(ManageContainer.OrderAction_selectById_null);		}				/**		 * 订单流程控制		 */				//修改订单状态		Order order = new Order();		String msg = "";		if(orderInfo.getStatus().equals(Order.order_status_temp)){			//throw new RuntimeException(ManageContainer.OrderAction_updateOrderStatus_statusException);		}else if(orderInfo.getStatus().equals(Order.order_status_init)){			if(Order.order_status_cancel.equals(e.getStatus())){//取消订单				order.setStatus(e.getStatus());				//order.setConfirmStatus(Order.order_confirmStatus_sy2);				msg +="订单已取消";			}else if(e.getStatus().equals(Order.order_status_stock) ){				//----				order.setPhone(e.getPhone());				order.setExpectSignDate(e.getExpectSignDate());				order.setStatus(e.getStatus());				//order.setConfirmStatus(Order.order_confirmStatus_sy2);				msg +="【财务审核通过】 助理审核通过";			}else{				//throw new RuntimeException(ManageContainer.OrderAction_updateOrderStatus_statusException);			}		}else if(orderInfo.getStatus().equals(Order.order_status_stock)){			if(!e.getStatus().equals(Order.order_status_pass)){				//throw new RuntimeException(ManageContainer.OrderAction_updateOrderStatus_statusException);			}else{				order.setStatus(e.getStatus());				msg +="【仓库审核通过】 仓库审核通过";				/****************** 订单审核通过之后，向ERP系统发送订单信息  *********************/				// 销售人员				String saleCode = accountService.selectById(orderInfo.getAccountId()).getSaleCode();				// 查询物流信息				Ordership os = ordershipService.selectOne(new Ordership(e.getId()));				if(os == null){					throw new NullPointerException("订单数据异常，订单未包含物流数据！");				}				orderInfo.setOrdership(os);				//加载订单项列表 以及 产品信息				Orderdetail orderdetail = new Orderdetail();				orderdetail.setOrderID(Integer.valueOf(e.getId()));				List<Orderdetail> orderdetailList = orderdetailService.selectList(orderdetail);				if(orderdetailList==null || orderdetailList.size()==0){					throw new NullPointerException("订单数据异常，订单未包含任何订单项数据！");				}				orderInfo.setOrderdetail(orderdetailList);				// send to ERP				ErpInfo.addOrder(orderInfo, saleCode);				/****************** 订单审核通过之后，向ERP系统发送订单信息  *********************/			}		}else if(orderInfo.getStatus().equals(Order.order_status_pass)){			if(!e.getStatus().equals(Order.order_status_send)){				//throw new RuntimeException(ManageContainer.OrderAction_updateOrderStatus_statusException);			}else{				order.setSendDate(e.getSendDate());				order.setStatus(e.getStatus());				order.setExpressNo(e.getExpressNo());				order.setShipname(e.getShipname());				order.setExpressCompanyName(e.getExpressCompanyName());				order.setNumber(e.getNumber());				msg +="【已发货】";				if(StrUtils.isNotEmpty(e.getExpressCompanyName()))msg +=" 物流公司："+order.getExpressCompanyName();				msg +="  物流单号："+e.getExpressNo();			}		}else if(orderInfo.getStatus().equals(Order.order_status_send)){			if(!e.getStatus().equals(Order.order_status_file)){				//throw new RuntimeException(ManageContainer.OrderAction_updateOrderStatus_statusException);			}else{				order.setStatus(e.getStatus());				msg +="【已完成】";			}		}				if(StrUtils.isNotEmpty(msg)){			orderInfo.setStatus(order.getStatus());			orderInfo.setShipname(order.getShipname());			orderInfo.setSendDate(order.getSendDate());			orderInfo.setExpressCompanyName(order.getExpressCompanyName());			orderInfo.setExpressNo(order.getExpressNo());			order.setId(e.getId());			order.setSerialId(e.getSerialId());			order.setAccount(e.getAccount());			orderService.update(order);						//发送订单短信;			notifyTemplateService.sendOrderSms(orderInfo);			//记录日志			insertOrderlog(e.getId(),msg);		}		return "redirect:toEdit?id="+e.getId();	}	/**	 * 插入订单操作日志	 * @param orderid	订单ID	 * @param content	日志内容	 */	private void insertOrderlog(String orderid,String content) {		User user = LoginUserHolder.getLoginUser();		Orderlog orderlog = new Orderlog();		orderlog.setOrderid(orderid);//订单ID		orderlog.setAccount(user.getUsername());//操作人账号		orderlog.setAccountId(user.getId());		orderlog.setContent(content);//日志内容		orderlog.setAccountType(Orderlog.orderlog_accountType_m);		orderlogService.insert(orderlog);	}	/**	 * 设置订单为取消	 * @return	 * @throws IOException 	 *///	public String cancel() throws IOException{//		if(StringUtils.isBlank(e.getId())){//			throw new NullPointerException();//		}//		//		Order order = new Order();//		order.setStatus(Order.order_status_cancel);//		order.setId(e.getId());//		orderService.update(order);//		//		insertOrderlog(e.getId(),"【取消订单】");//		//		toEdit2();//		return null;//	}		/**	 * 设置订单为已发货	 * @return	 * @throws IOException 	 *///	public String setSend() throws IOException{//		if(StringUtils.isBlank(e.getId())){//			throw new NullPointerException();//		}//		//		Order order = new Order();//		order.setStatus(Order.order_status_send);//		order.setId(e.getId());//		orderService.update(order);//		//		insertOrderlog(e.getId(),"【已发货】");//		//		toEdit2();//		return null;//	}		/**	 * 设置订单为已签收	 * @return	 * @throws IOException 	 *///	public String setSign() throws IOException{//		if(StringUtils.isBlank(e.getId())){//			throw new NullPointerException();//		}//		//		Order order = new Order();//		order.setStatus(Order.order_status_sign);//		order.setId(e.getId());//		orderService.update(order);//		//		insertOrderlog(e.getId(),"【已签收】");//		//		toEdit2();//		return null;//	}		/**	 * 设置订单为已归档	 * @return	 * @throws IOException 	 *///	public String setFile() throws IOException{//		if(StringUtils.isBlank(e.getId())){//			throw new NullPointerException();//		}//		//		Order order = new Order();//		order.setStatus(Order.order_status_file);//		order.setId(e.getId());//		orderService.update(order);//		//		insertOrderlog(e.getId(),"【已归档】");//		//		toEdit2();//		return null;//	}		/**	 * 修改订单的各种状态	 * @return	 * @throws Exception	 *///	@Deprecated//	public String changeOrderStatus() throws Exception {//		logger.error(">>>changeOrderStatus...");////		String aaa = getRequest().getParameter("aaa");////		log.error(">>>changeOrderStatus...aaa="+this.aaa);//		return null;//	}	/**	 * 后台修改订单总金额	 * @return	 * @throws Exception	 */	@RequestMapping(value = "updatePayMonery", method = RequestMethod.POST)	public String updatePayMonery(ModelMap model, Order e) throws Exception {		checkStatus1(e);				logger.error("updatePayMonery = id = " + e.getId() + ",amount = " + e.getAmount());		User user = LoginUserHolder.getLoginUser();		orderService.updatePayMonery(e,user.getUsername());				return "redirect:toEdit?id="+e.getId();	}	/**	 * 后台编辑订单页面，添加支付记录、修改订单总金额 操作之前需要进行如下的判断，这2个按钮的操作必须是订单为未审核 且 订单未支付 才可以，否则抛出异常。	 */	private void checkStatus1(Order e) {		Order orderInfo = orderService.selectById(e.getId());		if(orderInfo==null){			throw new NullPointerException(ManageContainer.OrderAction_selectById_null);		}				/**		 * 订单流程控制		 */		if(!orderInfo.getStatus().equals(Order.order_status_init)){			throw new UpdateOrderStatusException(ManageContainer.OrderAction_updatePayMonery_update);		}				if(!orderInfo.getPaystatus().equals(Order.order_paystatus_n)){			throw new UpdateOrderStatusException("未支付的订单才支持此操作！");		}	}		/**	 * 查询订单的配送地址信息-->然后后台工作人员可以进行修改	 * @return	 */	@RequestMapping(value = "selectOrdership")	public String selectOrdership(ModelMap model, Order e){		String orderid = RequestHolder.getRequest().getParameter("orderid");		if(StringUtils.isBlank(orderid)){			throw new NullPointerException("非法请求！");		}				Ordership ordership = new Ordership();		ordership.setOrderid(orderid);		ordership = ordershipService.selectOne(ordership);		if(ordership==null){			throw new NullPointerException("根据订单ID查询不到该订单的配送信息！");		}				e.setOrdership(ordership);		//		areaList		//获取区域列表		if(StringUtils.isNotBlank(ordership.getArea())){//					address.getArea()			net.jeeshop.services.front.area.bean.Area area = SystemManager.getInstance().getAreaMap().get(ordership.getProvinceCode());			if(area!=null && area.getChildren()!=null && area.getChildren().size()>0){				for(int i=0;i<area.getChildren().size();i++){					net.jeeshop.services.front.area.bean.Area city = area.getChildren().get(i);					if(city.getCode().equals(ordership.getCityCode())){						//						logger.error("address.getCity()="+address.getCity());//						logger.error(city.toString());//						address.setAreaList(city.getChildren());						List<Area> areaList = city.getChildren();						break;					}				}			}		}				return page_selectOrdership;	}		/**	 * 修改订单配送地址信息	 * @return	 * @throws IOException 	 */	@RequestMapping(value = "updateOrdership", method = RequestMethod.POST)	public String updateOrdership(ModelMap model, Order e) throws IOException{		logger.error("updateOrdership...");		if(StringUtils.isBlank(e.getOrdership().getShipname())){			throw new NullPointerException("收货人不能为空！");		}else if(StringUtils.isBlank(e.getOrdership().getShipaddress())){			throw new NullPointerException("收货人街道地址不能为空！");		}else if(StringUtils.isBlank(e.getOrdership().getTel())){			throw new NullPointerException("收货人手机号码！");		}else if(StringUtils.isBlank(e.getOrdership().getProvince())){			throw new NullPointerException("省份人不能为空！");		}else if(StringUtils.isBlank(e.getOrdership().getCity())){			throw new NullPointerException("城市人不能为空！");		}				if(StringUtils.isBlank(e.getId())){			throw new NullPointerException(ManageContainer.OrderAction_param_null);		}				Order order = orderService.selectById(e.getId());		if(order==null){			throw new NullPointerException("查询不到订单信息!");		}				if(!order.getStatus().equals(Order.order_status_init)){			throw new RuntimeException("只有【未审核】的订单才能修改收货人配送地址信息!");		}						ordershipService.update(e.getOrdership());				return "redirect:toEdit?id="+e.getId();	}	/**	 * 根据省份编码获取城市列表	 * @return	 * @throws IOException 	 */	@RequestMapping(value = "selectCitysByProvinceCode")	@ResponseBody	public String selectCitysByProvinceCode() throws IOException{		logger.error("selectCitysByProvinceCode...");		String provinceCode = RequestHolder.getRequest().getParameter("provinceCode");		logger.error("selectCitysByProvinceCode...provinceCode="+provinceCode);		if(StringUtils.isBlank(provinceCode)){			throw new NullPointerException("provinceCode is null");		}		//		Area area = new Area();//		area.setCode(provinceCode);        Map<String, Area> areaMap = SystemManager.getInstance().getAreaMap();		if(areaMap!=null && areaMap.size()>0){			net.jeeshop.services.front.area.bean.Area areaInfo = areaMap.get(provinceCode);						logger.error("areaInfo = " + areaInfo);						if(areaInfo!=null && areaInfo.getChildren()!=null && areaInfo.getChildren().size()>0){				String jsonStr = JSON.toJSONString(areaInfo.getChildren());				logger.error("jsonStr=" + jsonStr);				return (jsonStr);			}		}				return "{}";	}	/**	 * 根据城市编码获取区域列表	 * @return	 * @throws IOException 	 */	@RequestMapping(value = "selectAreaListByCityCode")	@ResponseBody	public String selectAreaListByCityCode() throws IOException{		logger.error("selectAreaListByCityCode...");		String provinceCode = RequestHolder.getRequest().getParameter("provinceCode");		String cityCode = RequestHolder.getRequest().getParameter("cityCode");		logger.error("selectAreaListByCityCode...provinceCode="+provinceCode+",cityCode="+cityCode);		if(StringUtils.isBlank(provinceCode) || StringUtils.isBlank(cityCode)){			throw new NullPointerException("provinceCode or cityCode is null");		}        Map<String, Area> areaMap = SystemManager.getInstance().getAreaMap();		if(areaMap!=null && areaMap.size()>0){			net.jeeshop.services.front.area.bean.Area city = areaMap.get(provinceCode);						logger.error("areaInfo = " + city);						if(city!=null && city.getChildren()!=null && city.getChildren().size()>0){				for(int i=0;i<city.getChildren().size();i++){					net.jeeshop.services.front.area.bean.Area item = city.getChildren().get(i);					if(item.getCode().equals(cityCode)){						if(item.getChildren()!=null && item.getChildren().size()>0){							String jsonStr = JSON.toJSONString(item.getChildren());							logger.error("jsonStr=" + jsonStr);							return (jsonStr);						}					}				}			}		}				return "{}";	}			/**	 * 根据订单对应的库存信息	 * @return	 */	@RequestMapping(value = "get_order_stock")	@ResponseBody	public String get_order_stock(HttpServletRequest request){		logger.info("get_order_stock init");		String orderId = request.getParameter("orderId");				Orderdetail orderdetail = new Orderdetail();		orderdetail.setOrderID(Integer.valueOf(orderId));		List<Orderdetail> orderdetailList = orderdetailService.selectList(orderdetail);		if(StrUtils.isNotEmpty(orderdetailList)){			for(Orderdetail item : orderdetailList){				item.setSalable_qty(ErpInfo.get_stock(item.getPcode()));			}		}		JSONObject json = new JSONObject();		json.put("list", orderdetailList);		logger.info("get_order_stock end");		return json.toString();	}		/**	 * 根据订单对应的库存信息	 * @return	 */	@RequestMapping(value = "get_order_payInfo")	@ResponseBody	public String get_order_payInfo(HttpServletRequest request){		logger.info("get_order_payInfo init");		String orderId = request.getParameter("orderId");				List<?> payList = orderpayService.getPayList(orderId);				JSONObject json = new JSONObject();		json.put("list", payList);		logger.info("get_order_payInfo end");		return json.toString();	}	}