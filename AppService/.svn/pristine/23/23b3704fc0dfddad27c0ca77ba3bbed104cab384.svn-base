package com.sys.ekey.freecall.common;

import com.sys.util.ApDateTime;
import com.unionpay.acp.sdk.LogUtil;

/**
 * Created by Maryn on 2016/4/15.
 */
public class FeiyuOrderUtil {

    public static String outTradeNo(String fyAccountId){
        String out_trade_no = "";
        Long currentTimeMillis = System.currentTimeMillis();
        String currentTimeMillisStr = String.valueOf(currentTimeMillis);
        try {
            out_trade_no = ApDateTime.getDateTime(ApDateTime.DATE_TIME_YMD_NOSYMBOL,currentTimeMillis);
        } catch (Exception e) {
            LogUtil.writeLog("时间获取异常");
        }
        out_trade_no += fyAccountId.substring(2);
        out_trade_no += currentTimeMillisStr.substring(currentTimeMillisStr.length() - 8);
        return out_trade_no;
    }
}
