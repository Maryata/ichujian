package com.sys.action;

import com.sys.service.ExchangeService;
import com.sys.util.HttpUtil;
import com.sys.util.StrUtils;
import com.sys.vo.EKMallOrder;
import com.sys.vo.OrderVo;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单审核通过之后，向ERP发送订单信息
 * @author Maryn
 *
 */
@Component("erpInfo")
public class ErpInfo {

	public static final Logger log = LoggerFactory.getLogger(ErpInfo.class);

	@Autowired
	private ExchangeService exchangeService;
	
	public static final String SELLER_MEMO_ORDER = "e键免费换膜";
	/** 必填项 */
	// 添加接口
	public static final String METHOD_ADD = "gy.erp.trade.add";
//	public static final String METHOD_stock_get = "gy.erp.stock.get";//库存查询

//	private static final String ERP_TEST_URL = "http://demo.guanyierp.com";
	private static final String ERP_ORDER_URL = "http://v2.api.guanyierp.com/rest/erp_open";
	private static final String ERP_ORDER_APPKEY = "152669";
	private static final String ERP_ORDER_SECRET = "2cfb7066953b4514ae4aa1c73472fa4b";
	private static final String ERP_ORDER_SESSIONKEY = "f389191864714d2ab889d2dbaa2f9236";

	// 店铺code
	public static final String SHOP_CODE = "010";
	// 仓库code
	public static final String WAREHOUSE_CODE_ZDN = "ZDN";// 上海指电智能科技有限公司(电商)

	// 物流
	public static final String EXPRESS_CODE_ZTO = "ZTO";// 中通
	public static final String EXPRESS_CODE_YTO = "YTO";// 圆通
	public static final String EXPRESS_CODE_OTHER = "OTHER";// 其他

	/** 发票类型 */
	public static final int INVOICE_NOR = 1;// 普通发票(NORMAL)
	public static final int INVOICE_VAT = 2;// 增值发票(VALUE ADDED TAXES)
	
	/** 支付类型 */
	public static final String PAY_TYPE_CODE_ZHIFUBAO = "zhifubao";// 支付宝
	
	public static boolean isNotSend = false;

	private static Map<String, Object> addInit(String warehouse_code){
		Map<String, Object> initInfo = new HashMap<String,Object>();
		initInfo.put("appkey", ERP_ORDER_APPKEY);
		initInfo.put("sessionkey", ERP_ORDER_SESSIONKEY);
		initInfo.put("cod", false);
		initInfo.put("shop_code", ErpInfo.SHOP_CODE);
		initInfo.put("warehouse_code", warehouse_code);

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
	public String addOrder(OrderVo order){
    	String platform_code = order.getSerialId();
    	log.info(">>>>>>>>>>>>>>>>>> into ErpInfo.addOrder() platform_code [{}]...",platform_code);
    	
    	// 初始化参数
    	Map<String, Object> info = addInit(ErpInfo.WAREHOUSE_CODE_ZDN);
    	info.put("method", ErpInfo.METHOD_ADD);
		info.put("vip_code",order.getTelNum());
		info.put("express_code", order.getExpressCode());// 物流公司code



		info.put("receiver_address", order.getAddress());
		info.put("receiver_name", order.getConsignee());// 收货人姓名
		info.put("receiver_mobile", order.getTelNum());// 收货人手机
		info.put("deal_datetime", order.getCreateDate());// 拍单时间
		info.put("platform_code", platform_code);// 平台单号
		info.put("seller_memo", SELLER_MEMO_ORDER);// 卖家备注

		// 商品
		List<Map<String, Object>> details = new ArrayList<Map<String,Object>>();


		Map<String, Object> product = new HashMap<>();
		product.put("qty",1);// 商品数量
		product.put("price", 9);// 商品单价
		product.put("item_code", order.getpCode());// 商品代码
		details.add(product);

		info.put("details", details);
		
		// 支付信息
		List<Map<String, Object>> payments = new ArrayList<Map<String,Object>>();
		Map<String, Object> payment = new HashMap<String, Object>();
		payment.put("payment", 9);// 支付金额
		payment.put("pay_type_code", ErpInfo.PAY_TYPE_CODE_ZHIFUBAO);// 支付类型码
		payments.add(payment);
		info.put("payments", payments);
		
		// 发票
		List<Map<String, Object>> invoices = new ArrayList<Map<String,Object>>();
		Map<String, Object> invoice = new HashMap<String, Object>();
		invoice.put("invoice_type", ErpInfo.INVOICE_VAT);
		invoices.add(invoice);
		info.put("invoices", invoices);
		
		String json = JSONObject.fromObject(info).toString();
		String sign = sign(json, ERP_ORDER_SECRET);// 签名
		
		info.put("sign", sign);
		json = JSONObject.fromObject(info).toString();
		String result = null;
		try {
			log.info(">>>>>>>>>>>>>>>>>> add order to ERP platform_code:[{}] ，content:[{}]  ",platform_code.substring(1, platform_code.length()),json);
			if(!isNotSend){
				result = HttpUtil.doPostJson(ERP_ORDER_URL, json);
//				result = HttpUtil.doPostJson(ERP_TEST_URL, json);
				log.info(">>>>>>>>>>>>>>>>>> add order to ERP platform_code:[{}] ，result:[{}]  ",platform_code.substring(1, platform_code.length()),result);
				JSONObject jsonResult = JSONObject.fromObject(result);
				if("true".equals(StrUtils.emptyOrString(jsonResult.get("success")))){
					log.info(">>>>>>>>>>>>>>>>>> add order to ERP successfully platform_code:[{}]!!!",platform_code.substring(1, platform_code.length()));
					exchangeService.toErp(platform_code, "1");
				}else{
					log.info(">>>>>>>>>>>>>>>>>> add order to ERP failed platform_code:[{}] !!!",platform_code.substring(1, platform_code.length()));
				}
			}else{
    			log.info("temp model, not send to erp");
    		}
		} catch (Exception e) {
			log.error(">>>>>>>>>>>>>>>>>> add order to ERP fialed platform_code:["+platform_code.substring(1, platform_code.length())+"]  faild...",e);
		}
    	return result;
    }

    /**
     * 商城订单推送
     * @param order
     * @return
     */
	public String addMallOrder(EKMallOrder order){
    	String platform_code = order.getTrade_no();
    	log.info(">>>>>>>>>>>>>>>>>> into ErpInfo.addOrder() platform_code [{}]...",platform_code);

    	// 初始化参数
    	Map<String, Object> info = addInit(ErpInfo.WAREHOUSE_CODE_ZDN);
    	info.put("method", ErpInfo.METHOD_ADD);
		info.put("vip_code",order.getRecv_phone());
		info.put("express_code", order.getExpress_code());// 物流公司code



		info.put("receiver_address", order.getRecv_addr());
		info.put("receiver_name", order.getRecv_name());// 收货人姓名
		info.put("receiver_mobile", order.getRecv_phone());// 收货人手机
		info.put("deal_datetime", order.getCtime());// 拍单时间
		info.put("platform_code", platform_code);// 平台单号
		info.put("seller_memo", EKMallOrder.SELLER_MEMO);// 卖家备注

		// 商品
		List<Map<String, Object>> details = new ArrayList<>();


		Map<String, Object> product = new HashMap<>();
		product.put("qty", 1);// 商品数量
		product.put("price", 9);// 商品单价
		product.put("item_code", order.getPcode());// 商品代码
		details.add(product);

		info.put("details", details);

		// 支付信息
		List<Map<String, Object>> payments = new ArrayList<>();
		Map<String, Object> payment = new HashMap<>();
		payment.put("payment", 9);// 支付金额
		payment.put("pay_type_code", ErpInfo.PAY_TYPE_CODE_ZHIFUBAO);// 支付类型码
		payments.add(payment);
		info.put("payments", payments);

		// 发票
		List<Map<String, Object>> invoices = new ArrayList<>();
		Map<String, Object> invoice = new HashMap<>();
		invoice.put("invoice_type", ErpInfo.INVOICE_VAT);
		invoices.add(invoice);
		info.put("invoices", invoices);

		String json = JSONObject.fromObject(info).toString();
		String sign = sign(json, ERP_ORDER_SECRET);// 签名

		info.put("sign", sign);
		json = JSONObject.fromObject(info).toString();
		String result = null;
		try {
			log.info(">>>>>>>>>>>>>>>>>> add order to ERP platform_code:[{}] ，content:[{}]  ",platform_code.substring(1, platform_code.length()),json);
			if(!isNotSend){
				result = HttpUtil.doPostJson(ERP_ORDER_URL, json);
//				result = HttpUtil.doPostJson(ERP_TEST_URL, json);
				log.info(">>>>>>>>>>>>>>>>>> add order to ERP platform_code:[{}] ，result:[{}]  ",platform_code.substring(1, platform_code.length()),result);
				JSONObject jsonResult = JSONObject.fromObject(result);
				if("true".equals(StrUtils.emptyOrString(jsonResult.get("success")))){
					log.info(">>>>>>>>>>>>>zd>>>>> add order to ERP successfully platform_code:[{}]!!!",platform_code.substring(1, platform_code.length()));

					if (StrUtils.isNotEmpty(exchangeService)) {
						exchangeService.toErp(platform_code, "2");
					}
				}else{
					log.info(">>>>>>>>>>>>>>>>>> add order to ERP failed platform_code:[{}] !!!",platform_code.substring(1, platform_code.length()));
				}
			}else{
    			log.info("temp model, not send to erp");
    		}
		} catch (Exception e) {
			log.error(">>>>>>>>>>>>>>>>>> add order to ERP fialed platform_code:["+platform_code.substring(1, platform_code.length())+"]  faild...",e);
		}
    	return result;
    }

    public static void main (String[] args) {
    	try {

    		Map<String, Object> info = ErpInfo.addInit(ErpInfo.WAREHOUSE_CODE_ZDN);
			info.put("method", ErpInfo.METHOD_ADD);
			info.put("express_code","ZTO");
//			info.put("receiver_province", "上海市");
//			info.put("receiver_city", "上海市");
//			info.put("receiver_district", "浦东新区");
			info.put("receiver_address", "四川省德阳市中江县兴隆镇五里坝12村1组");
			info.put("receiver_name", "向乾文");
			info.put("vip_code","15228972264");
			info.put("receiver_mobile", "15228972264");
			info.put("deal_datetime", "2016-06-23 17:19:11");
			info.put("platform_code", "E"+"0623171909-2835");
			info.put("seller_memo", SELLER_MEMO_ORDER);
    		
    		// 商品
    		List<Map<String, Object>> details = new ArrayList<Map<String,Object>>();
    		Map<String, Object> detail = new HashMap<String, Object>();
    		detail.put("qty", 1);
    		detail.put("price", 9);
    		detail.put("item_code", "C200A1M0126");
    		details.add(detail);
    		info.put("details", details);
    		
    		// 支付信息
    		List<Map<String, Object>> payments = new ArrayList<Map<String,Object>>();
    		Map<String, Object> payment = new HashMap<String, Object>();
    		payment.put("payment", 9);
    		payment.put("pay_type_code", ErpInfo.PAY_TYPE_CODE_ZHIFUBAO);
    		payments.add(payment);
    		info.put("payments", payments);
    		
    		// 发票
    		List<Map<String, Object>> invoices = new ArrayList<Map<String,Object>>();
    		Map<String, Object> invoice = new HashMap<String, Object>();
    		invoice.put("invoice_type", ErpInfo.INVOICE_NOR);
    		invoices.add(invoice);
    		info.put("invoices", invoices);
    		
    		String json = JSONObject.fromObject(info).toString();
    		String sign = ErpInfo.sign(json, ERP_ORDER_SECRET);// 获取签名
    		
    		info.put("sign", sign);
    		json = JSONObject.fromObject(info).toString();
			String result = null;
			try {
				log.info(">>>>>>>>>>>>>>>>>> add order to ERP platform_code:[{}] ，content:[{}]  ","1",json);
				if(!isNotSend){
					result = HttpUtil.doPostJson(ERP_ORDER_URL, json);
					log.info(">>>>>>>>>>>>>>>>>> add order to ERP platform_code:[{}] ，result:[{}]  ","1",result);
					JSONObject jsonResult = JSONObject.fromObject(result);
					if("true".equals(StrUtils.emptyOrString(jsonResult.get("success")))){
						log.info(">>>>>>>>>>>>>>>>>> add order to ERP successfully platform_code:[{}]!!!","1");
					}else{
						log.info(">>>>>>>>>>>>>>>>>> add order to ERP failed platform_code:[{}] !!!","1");
					}
				}else{
					log.info("temp model, not send to erp");
				}
			} catch (Exception e) {
				log.error(">>>>>>>>>>>>>>>>>> add order to ERP fialed platform_code:["+"1"+"]  failed...",e);
			}

		} catch (Exception ex) {
    		
    	} finally {
	    		
    	}
	}
}
