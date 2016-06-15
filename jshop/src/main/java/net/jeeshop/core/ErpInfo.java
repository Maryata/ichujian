package net.jeeshop.core;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.jeeshop.core.front.SystemManager;
import net.jeeshop.core.util.ApDateTime;
import net.jeeshop.core.util.HttpUtil;
import net.jeeshop.core.util.StrUtils;
import net.jeeshop.services.manage.order.bean.Order;
import net.jeeshop.services.manage.orderdetail.bean.Orderdetail;
import net.jeeshop.services.manage.ordership.bean.Ordership;
import net.jeeshop.services.manage.sampleApply.bean.SampleApply;
import net.jeeshop.services.manage.sampleApply.bean.SampleApplyDetail;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 订单审核通过之后，向ERP发送订单信息
 * @author Maryn
 *
 */
public class ErpInfo {

	public static final Logger log = LoggerFactory.getLogger(ErpInfo.class);
	
	public static final String SELLER_MEMO_ORDER = "指电销售订单";
	public static final String SELLER_MEMO_SAMPLE = "指电样品申请";
	/** 必填项 */
	// 添加接口
	public static final String METHOD_ADD = "gy.erp.trade.add";
	public static final String METHOD_stock_get = "gy.erp.stock.get";//库存查询
	
	// 店铺code
	public static final String SHOP_CODE = "004";
	// 仓库code
	public static final String WAREHOUSE_CODE_ZDL = "ZDL";// 上海指电智能科技有限公司
	public static final String WAREHOUSE_CODE_SHGS = "SHGS";// 上海公司--【样品仓】
	
	/** 发票类型 */
	public static final int INVOICE_NOR = 1;// 普通发票(NORMAL)
	public static final int INVOICE_VAT = 2;// 增值发票(VALUE ADDED TAXES)
	
	/** 支付类型 */
	public static final String PAY_TYPE_CODE_COD = "cod";// 到付
	public static final String PAY_TYPE_CODE_WANGYIN = "wangyin";// 网银
	public static final String PAY_TYPE_CODE_ZHIFUBAO = "zhifubao";// 支付宝
	public static final String PAY_TYPE_CODE_CAIFUTONG = "caifutong";// 财富通
	
	public static boolean isNotSend = false;
	
	@SuppressWarnings("static-access")
	private static Map<String, Object> addInit(String warehouse_code){
		Map<String, Object> initInfo = new HashMap<String,Object>();
		initInfo.put("appkey", SystemManager.getInstance().getProperty("erp.order.appkey"));
		initInfo.put("sessionkey", SystemManager.getInstance().getProperty("erp.order.sessionkey"));
		initInfo.put("cod", true);
		initInfo.put("shop_code", ErpInfo.SHOP_CODE);
		initInfo.put("warehouse_code", warehouse_code);
		
		String isTempStr = SystemManager.getInstance().getProperty("erp.order.isNotSend");
		if("Y".equals(isTempStr)){//Y  订单信息不推送到ERP
			isNotSend = true;
		}		
		return initInfo;
	}
	
	public static String sign(String json,String secret){
        StringBuilder enValue = new StringBuilder();
        //前后加上secret
        enValue.append(secret);
        enValue.append(json);
        enValue.append(secret);
        // 使用MD5加密(32位大写)
        byte[] bytes = encryptMD5(enValue.toString());
        return byte2hex(bytes);
    }

    private static byte[] encryptMD5(String data) {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(data.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }
    
    /**
     * 将订单信息存入ERP
     * @param order
     * @return
     */
    @SuppressWarnings("static-access")
	public static String addOrder(Order order, String saller){
    	String platform_code = order.getSerialId();
    	log.info(">>>>>>>>>>>>>>>>>> into ErpInfo.addOrder() platform_code [{}]...",platform_code);
    	
    	// 初始化参数
    	Map<String, Object> info = addInit(ErpInfo.WAREHOUSE_CODE_ZDL);
    	info.put("method", ErpInfo.METHOD_ADD);
    	
    	Ordership os = order.getOrdership();
    	
    	info.put("platform_code", order.getSerialId());// 平台单号
    	info.put("express_code", order.getExpressCode());// 物流公司code
    	info.put("receiver_name", order.getOrdership().getShipname());// 收货人姓名
    	info.put("vip_code", order.getAccount());// 会员id
		info.put("receiver_mobile", os.getPhone());// 收货人手机
		info.put("receiver_province", os.getProvince());// 收货人省份
		info.put("receiver_city", os.getCity());// 收货人城市
		info.put("receiver_district", os.getArea());// 收货人区
		info.put("receiver_address", (StrUtils.isNotEmpty(os.getShiparea()) ? os.getShiparea() : "") + (StrUtils.isNotEmpty(os.getShipaddress()) ? os.getShipaddress() : "")); 
		info.put("deal_datetime", order.getCreatedate());// 拍单时间
		info.put("plan_delivery_date", order.getExpectSignDate());// 预计发货时间
		info.put("business_man_code", saller);// 业务员code
		info.put("buyer_memo",StrUtils.emptyOrString(order.getOtherRequirement()));// 客户留言
		info.put("seller_memo", SELLER_MEMO_ORDER);// 卖家备注
		
		// 商品
		List<Map<String, Object>> details = new ArrayList<Map<String,Object>>();
		List<Orderdetail> orderdetails = order.getOrderdetail();
		if(StrUtils.isNotEmpty(orderdetails)){
			for (Orderdetail orderdetail : orderdetails) {
				Map<String, Object> product = new HashMap<String, Object>();
				product.put("qty", orderdetail.getNumber());// 商品数量
				product.put("price", orderdetail.getPrice());// 商品单价
				product.put("item_code", orderdetail.getPcode());// 商品代码
				details.add(product);
			}
		}
		info.put("details", details);
		
		// 支付信息
		List<Map<String, Object>> payments = new ArrayList<Map<String,Object>>();
		Map<String, Object> payment = new HashMap<String, Object>();
		payment.put("payment", order.getAmount());// 支付金额
		payment.put("pay_type_code", ErpInfo.PAY_TYPE_CODE_COD);// 支付类型码
		payments.add(payment);
		info.put("payments", payments);
		
		// 发票
		List<Map<String, Object>> invoices = new ArrayList<Map<String,Object>>();
		Map<String, Object> invoice = new HashMap<String, Object>();
		int orderType = order.getOrderType();
		if(orderType==1){
			invoice.put("invoice_type", ErpInfo.INVOICE_VAT);
		}else{
			invoice.put("invoice_type", ErpInfo.INVOICE_NOR);
		}
		invoices.add(invoice);
		info.put("invoices", invoices);
		
		String json = JSONObject.fromObject(info).toString();
		String sign = sign(json, SystemManager.getInstance().getProperty("erp.order.secret"));// 获取签名
		
		info.put("sign", sign);
		json = JSONObject.fromObject(info).toString();
		String result = null;
		try {
			log.info(">>>>>>>>>>>>>>>>>> add order to ERP platform_code:[{}] ，content:[{}]  ",platform_code,json);
			if(!isNotSend){
				result = HttpUtil.doPostJson(SystemManager.getInstance().getProperty("erp.order.url"), json);
				log.info(">>>>>>>>>>>>>>>>>> add order to ERP platform_code:[{}] ，result:[{}]  ",platform_code,result);
				JSONObject jsonResult = JSONObject.fromObject(result);
				if("true".equals(StrUtils.emptyOrString(jsonResult.get("success")))){
					log.info(">>>>>>>>>>>>>>>>>> add order to ERP successfully platform_code:[{}]!!!",platform_code);
				}else{
					log.info(">>>>>>>>>>>>>>>>>> add order to ERP fialed platform_code:[{}] !!!",platform_code);
				}
			}else{
    			log.info("temp model, not send to erp");
    		}
		} catch (Exception e) {
			log.error(">>>>>>>>>>>>>>>>>> add order to ERP fialed platform_code:["+platform_code+"]  faild...",e);
		}		
    	return result;
    }
    
    /**
     * 样品推送：推动到样品仓
     * @param sa
     */
	public static void addSample(SampleApply sa){
    	String  platform_code = sa.getSampleNo();
    	log.info(">>>>>>>>>>>>>>>>>> into ErpInfo.testAddSample()  platform_code [{}]...",platform_code);
    	
    	// 初始化参数
    	Map<String, Object> info = addInit(ErpInfo.WAREHOUSE_CODE_SHGS);
    	
    	info.put("method", ErpInfo.METHOD_ADD);
    	
    	info.put("platform_code", sa.getSampleNo());// 平台单号
    	info.put("express_code", sa.getExpressCode());// 物流公司code
    	info.put("receiver_name", StrUtils.isEmpty(sa.getContact())?"":sa.getContact());// 收货人姓名
    	info.put("vip_code", sa.getCustomerId());// 会员id
    	info.put("receiver_mobile", sa.getPhone());// 收货人手机
    	info.put("receiver_address", StrUtils.isEmpty(sa.getAddress())?"":sa.getAddress());
    	info.put("deal_datetime", ApDateTime.getNowDateTime(ApDateTime.DATE_TIME_Sec));// 拍单时间
    	info.put("business_man_code", sa.getCreateUserId());// 业务员code
    	info.put("seller_memo", SELLER_MEMO_SAMPLE);// 卖家备注
    	
    	// 商品
    	List<Map<String, Object>> details = new ArrayList<Map<String,Object>>();
    	List<SampleApplyDetail> saList = sa.getSaList();
		if(StrUtils.isNotEmpty(saList)){
			for (SampleApplyDetail sad : saList) {
				Map<String, Object> product = new HashMap<String, Object>();
				product.put("qty", sad.getProductNum());// 商品数量
		    	product.put("price", sad.getProductPrice());// 商品单价
		    	product.put("item_code", sad.getProductCode());// 商品代码
				details.add(product);
			}
		}
    	
    	info.put("details", details);
    	
    	// 支付信息
    	List<Map<String, Object>> payments = new ArrayList<Map<String,Object>>();
    	Map<String, Object> payment = new HashMap<String, Object>();
    	payment.put("payment", sa.getAmount());// 支付金额
    	payment.put("pay_type_code", ErpInfo.PAY_TYPE_CODE_COD);// 支付类型码
    	payments.add(payment);
    	info.put("payments", payments);
    	
    	// 发票
    	List<Map<String, Object>> invoices = new ArrayList<Map<String,Object>>();
    	Map<String, Object> invoice = new HashMap<String, Object>();
    	invoice.put("invoice_type", ErpInfo.INVOICE_NOR);
    	invoices.add(invoice);
    	info.put("invoices", invoices);
    	
    	String json = JSONObject.fromObject(info).toString();
    	String sign = sign(json, SystemManager.getInstance().getProperty("erp.order.secret"));// 获取签名
    	
    	info.put("sign", sign);
    	json = JSONObject.fromObject(info).toString();
    	String result = null;
    	try {
    		log.info(">>>>>>>>>>>>>>>>>> add sample to ERP platform_code:[{}] ，content:[{}]  ",platform_code,json);
    		if(!isNotSend){
	    		result = HttpUtil.doPostJson(SystemManager.getInstance().getProperty("erp.order.url"), json);
	    		log.info(">>>>>>>>>>>>>>>>>> add sample to ERP platform_code:[{}] ，result:[{}]  ",platform_code,result);
	    		JSONObject jsonResult = JSONObject.fromObject(result);
	    		if("true".equals(StrUtils.emptyOrString(jsonResult.get("success")))){
	    			log.info(">>>>>>>>>>>>>>>>>> add sample to ERP successfully platform_code[{}] !!!",platform_code);
	    		}else{
	    			log.info(">>>>>>>>>>>>>>>>>> add sample to ERP fialed platform_code[{}] !!!",platform_code);
	    		}
    		}else{
    			log.info("temp model, not send to erp");
    		}
    	} catch (Exception e) {
    		log.error("---- add sample to ERP faild platform_code["+platform_code+"]",e);
    	}
    }
	
	/**
	 * 订单库存
	 * @param barcode
	 * @return
	 */
	public static double get_stock(String barcode){
		return get_stock(barcode,ErpInfo.WAREHOUSE_CODE_ZDL);
	}
	
	/**
	 * 样品仓库存
	 * @param barcode
	 * @return
	 */
	public static double get_sample_stock(String barcode){
		return get_stock(barcode,ErpInfo.WAREHOUSE_CODE_SHGS);
	}
    

	/**
	 * 库存查询
	 * @param barcode 产品代码
	 * @param warehouse 仓库代码
	 * @return
	 */
    private static double get_stock(String barcode,String warehouse){
    	log.info(">>> ErpInfo.get_stock() into");
    	
    	// 初始化参数
    	Map<String, Object> info = addInit(warehouse);
    	info.put("method", "gy.erp.new.stock.get");
    	
    	info.put("barcode", barcode);//  E200A1M0021
    	info.put("page_no", "1");//
    	info.put("page_size", "100");//
    	
		
		String json = JSONObject.fromObject(info).toString();
		String sign = sign(json, SystemManager.getInstance().getProperty("erp.order.secret"));// 获取签名
		
		info.put("sign", sign);
		json = JSONObject.fromObject(info).toString();
		String result = null;
		double salable_qty = 0;
		try {
			result = HttpUtil.doPostJson(SystemManager.getInstance().getProperty("erp.order.url"), json);
			JSONObject jsonResult = JSONObject.fromObject(result);
			log.info("result:{}",result);
			if("true".equals(StrUtils.emptyOrString(jsonResult.get("success")))){
				JSONArray stocks = jsonResult.getJSONArray("stocks");
				for(int i=0 ;i<stocks.size();i++){
					JSONObject item = stocks.getJSONObject(i);
					if(ErpInfo.WAREHOUSE_CODE_ZDL.equals(item.getString("warehouse_code"))){
						salable_qty += item.getDouble("salable_qty");
					}					
				}
				log.info("salable_qty :{}",salable_qty);
			}else{
			}
			
			if(isNotSend){
				salable_qty = 100; // test
			}
		} catch (Exception e) {
			log.info(">>> ErpInfo.get_stock() faild",e);
		}
		log.info(">>> ErpInfo.get_stock() end");
		return salable_qty;
    }
    
    
    
    @Test
    public void testStockGet1() {
    	try {
    		/*String order = "{\n" +
    				"    \"appkey\": \"126225\",\n" +
    				"    \"sessionkey\": \"d6d373609de246fb9d63c2b5290601bb\",\n" +
    				"    \"method\": \"gy.erp.trade.add\",\n" +
    				"    \"cod\": true,\n" +
    				"    \"shop_code\": \"ichujian123\",\n" +
    				"    \"express_code\": \"SFGJ\",\n" +
    				"    \"warehouse_code\": \"1001\",\n" +
    				"    \"vip_code\": \"123\",\n" +
					"    \"receiver_name\": \"张三\",\n" +
					"    \"receiver_mobile\": \"13812344321\",\n" +
					"    \"receiver_province\": \"上海\",\n" +
					"    \"receiver_city\": \"上海\",\n" +
					"    \"receiver_district\": \"长宁区\",\n" +
					"    \"receiver_address\": \"凯旋路613号\",\n" +
					"    \"deal_datetime\": \"2015-09-22 15:00:01\",\n" +
					"    \"payment\": 15.2,\n" +
					"    \"details\": [\n" +
					"		{" +		
					"    	\"qty\": 1,\n" +
					"    	\"price\": 15.2,\n" +
					"    	\"item_code\": \"B100A1M0007\"\n" +
					"    	}\n" +
                    "	],\n" +
                    "    \"payments\": [\n" +
                    "		{" +		
                    "    	\"payment\": 15.2,\n" +
                    "    	\"pay_type_code\": \"order01234\"\n" +
                    "    	}\n" +
                    "	],\n" +
                    "    \"invoices\": [\n" +
                    "		{" +		
                    "    	\"invoice_type\": 1\n" +
                    "    	}\n" +
                    "	]\n" +
                    "}";*/
//    		JSONObject jsonObj = JSONObject.fromObject(order);
//    		String sign = this.sign(jsonObj.toString(), "4d97588127414cf6816994854c958a5d");
    		/*order = "{\n" +
    				"    \"appkey\": \"126225\",\n" +
    				"    \"sessionkey\": \"d6d373609de246fb9d63c2b5290601bb\",\n" +
    				"    \"method\": \"gy.erp.trade.add\",\n" +
    				"    \"cod\": true,\n" +
    				"    \"shop_code\": \"ichujian123\",\n" +
    				"    \"express_code\": \"SFGJ\",\n" +
    				"    \"warehouse_code\": \"1001\",\n" +
    				"    \"vip_code\": \"123\",\n" +
					"    \"receiver_name\": \"张三\",\n" +
					"    \"receiver_mobile\": \"13812344321\",\n" +
					"    \"receiver_province\": \"上海\",\n" +
					"    \"receiver_city\": \"上海\",\n" +
					"    \"receiver_district\": \"长宁区\",\n" +
					"    \"receiver_address\": \"凯旋路613号\",\n" +
					"    \"deal_datetime\": \"2015-09-22 15:00:01\",\n" +
					"    \"payment\": 15.2,\n" +
					"    \"details\": [\n" +
					"		{" +		
					"    	\"qty\": 1,\n" +
					"    	\"price\": 15.2,\n" +
					"    	\"item_code\": \"B100A1M0007\"\n" +
					"    	}\n" +
                    "	],\n" +
                    "    \"payments\": [\n" +
                    "		{" +		
                    "    	\"payment\": 15.2,\n" +
                    "    	\"pay_type_code\": \"order01234\"\n" +
                    "    	}\n" +
                    "	],\n" +
                    "    \"invoices\": [\n" +
                    "		{" +		
                    "    	\"invoice_type\": 1\n" +
                    "    	}\n" +
                    "	],\n" +
                    "    \"sign\": \"" + sign + "\"\n" +
                    "}";*/
    		Map<String, Object> info = ErpInfo.addInit(ErpInfo.WAREHOUSE_CODE_ZDL);
    		
    		info.put("platform_code", "10001");
    		info.put("receiver_name", "张三");
    		info.put("receiver_mobile", "13811112222");
    		info.put("receiver_province", "上海");
    		info.put("receiver_city", "上海");
    		info.put("receiver_district", "长宁区");
    		info.put("receiver_address", "凯旋路613号");
    		info.put("deal_datetime", "2015-09-23 10:32:01");
    		
    		// 商品
    		List<Map<String, Object>> details = new ArrayList<Map<String,Object>>();
    		Map<String, Object> detail = new HashMap<String, Object>();
    		detail.put("qty", 1);
    		detail.put("price", 22);
    		detail.put("item_code", "B100A1M0007");
    		details.add(detail);
    		info.put("details", details);
    		
    		// 支付信息
    		List<Map<String, Object>> payments = new ArrayList<Map<String,Object>>();
    		Map<String, Object> payment = new HashMap<String, Object>();
    		payment.put("payment", 22);
    		payment.put("pay_type_code", 22);
    		payments.add(payment);
    		info.put("payments", payments);
    		
    		// 发票
    		List<Map<String, Object>> invoices = new ArrayList<Map<String,Object>>();
    		Map<String, Object> invoice = new HashMap<String, Object>();
    		invoice.put("invoice_type", ErpInfo.INVOICE_NOR);
    		invoices.add(invoice);
    		info.put("invoices", invoices);
    		
    		String json = JSONObject.fromObject(info).toString();
    		String sign = ErpInfo.sign(json, SystemManager.getInstance().getProperty("erp.order.secret"));// 获取签名
    		
    		info.put("sign", sign);
    		json = JSONObject.fromObject(info).toString();    		
    		
			
    	} catch (Exception ex) {
    		
    	} finally {
	    		
    	}
	}
}
