package com.weixinpay.util;

import com.unionpay.acp.sdk.HttpClient;
import com.unionpay.acp.sdk.LogUtil;
import com.unionpay.acp.sdk.SDKUtil;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.SAXReader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vpc on 2016/4/15.
 */
public class WeixinService {
    public static Document post(
            Document document, String reqUrl, String encoding) {

        Document rspData = null;
        LogUtil.writeLog("请求微信地址:" + reqUrl);
        //发送后台请求数据
        HttpClient hc = new HttpClient(reqUrl, 30000, 30000);
        try {
            int status = hc.send(document, encoding);
            if (200 == status) {
                String resultString = hc.getResult();
                if (null != resultString && !"".equals(resultString)) {
                    rspData = DocumentHelper.parseText(resultString);
                }
            } else {
                LogUtil.writeLog("返回http状态码["+status+"]，请检查请求参数或者请求地址是否正确");
            }
        } catch (Exception e) {
            LogUtil.writeErrorLog("发送数据失败!",e);
        }

        return rspData;
    }
}
