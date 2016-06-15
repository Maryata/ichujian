package net.jeeshop.web.action.front.product;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.jeeshop.core.util.JsonMapper;
import net.jeeshop.core.util.StrUtils;
import net.jeeshop.core.util.StringUtil;
import net.jeeshop.services.front.account.bean.Account;
import net.jeeshop.services.front.product.bean.Product;
import net.jeeshop.web.util.LoginUserHolder;

public class ProductUtil {
	
	/**
	 * 处理登陆用户价格
	 * @param p
	 */
	public static void setPrice(Product p){
		Account acc = LoginUserHolder.getLoginAccount();
		if(StrUtils.isNotEmpty(acc)){
			if("1".equals(acc.getIsSysAccount())){//系统代客下单账号
				return;
			}
			//预处理用户价格;
			getAccPrices(acc,true);
			setPrice(p,acc.getAccPriceMap());
		}
	}

	/**
	 * 预处理价格
	 * @param acc
	 * @param isSet
	 * @return
	 */
	public static Map<String,Map<String,String>> getAccPrices(Account acc,boolean isSet){
		Map<String,Map<String,String>> accPrices = new HashMap<String,Map<String,String>>();
		if(StrUtils.isNotEmpty(acc)){
			if(StrUtils.isEmpty(acc.getAccPriceMap())){
				if(StrUtils.isNotEmpty(acc.getAccPrice()) ){
					List<Map> accPriceInfo = (List<Map>) JsonMapper.fromJsonString(acc.getAccPrice(), List.class);
					for(Map m : accPriceInfo){
						accPrices.put((String) m.get("pline"), m);
					}
					if(isSet){
						acc.setAccPriceMap(accPrices);
						LoginUserHolder.setLoginAccount(acc);
					}
				}
			}
		}
		return accPrices;
	}
	
	/**
	 * 
	 * @param p
	 * @param accPrices
	 */
	public static void setPrice(Product p,Map<String,Map<String,String>> accPrices){
		if(StrUtils.isNotEmpty(accPrices)){
			if(accPrices.containsKey(p.getPline())){//
				//price_a:M0120 price_p:M9001 price_pl:M9002
				String pcode = p.getPcode().substring(6,11);
				String price = null;
				if("M9001".equals(pcode)){
					price = accPrices.get(p.getPline()).get("price_p");
				}else if("M9002".equals(pcode)){
					price = accPrices.get(p.getPline()).get("price_pl");
				}else{
					price = accPrices.get(p.getPline()).get("price_a");
				}
				DecimalFormat df = new DecimalFormat("0.00");
				if(price!=null && StringUtil.isNotEmpty(price)){
					p.setPrice(df.format(Double.valueOf(price)));
					p.setNowPrice(p.getPrice());
				}
			}			
		}
	}
	
	

}
