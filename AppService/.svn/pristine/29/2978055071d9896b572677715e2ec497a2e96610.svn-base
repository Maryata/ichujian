package com.sys.ekey.shop.common;

import com.sys.util.ApDateTime;
import com.unionpay.acp.sdk.LogUtil;

/**
 * Created by vpc on 2016/4/21.
 */
public class OrderUtil {

    public static String outTradeNo(){
        String out_trade_no = "";
        Long currentTimeMillis = System.currentTimeMillis();
        String currentTimeMillisStr = String.valueOf(currentTimeMillis);
        try {
            out_trade_no = ApDateTime.getDateTime(ApDateTime.DATE_TIME_YMD_NOSYMBOL,currentTimeMillis);
        } catch (Exception e) {
            LogUtil.writeLog("时间获取异常");
        }
        out_trade_no += (int)((Math.random()*9+1)*100000);
        out_trade_no += currentTimeMillisStr.substring(currentTimeMillisStr.length() - 8);
        return out_trade_no;
    }


    public static void main(String args[]) {
        System.out.println(outTradeNo());
    }

}
