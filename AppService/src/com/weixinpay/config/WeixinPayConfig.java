package com.weixinpay.config;

/**
 * Created by vpc on 2016/4/14.
 */
public class WeixinPayConfig {
    //初始化
    //微信开发平台应用id
    public static final String APP_ID = "wx30710e246a7d6e1e";

    //应用对应的凭证
    public static final String APP_SECRET = "ce6a67e3585edec699d93667661ea972";

    public static final String KEY = "ICHUJIAN1329812701CF122139C296F1";

    // 统一下单URL
    public static final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    // 通知地址
    public static final String NOTIFY_URL = "http://www.51ekey.com/AppService/servlet/weixinPayNotify";

    // 免费换膜通知地址
    public static final String NOTIFY_URL_FREE = "http://www.51ekey.com/AppService/servlet/membraneNotifyWeChat";

    //public static final String NOTIFY_URL_FREE = "http://www.51ekey.com/AppService/servlet/membraneNotifyWeChat";

    //public static final String NOTIFY_URL_FREE = "https://180.166.220.210/AppService/servlet/membraneNotifyWeChat";
    // 商城通知地址
    public static final String NOTIFY_URL_SHOP = "http://www.51ekey.com/AppService/servlet/shopNotifyWeChat";

    // public static final String NOTIFY_URL_SHOP = "https://180.166.220.210/AppService/servlet/shopNotifyWeChat";
    // 终端IP
    public static final String SPBILL_CREATE_IP = "120.26.50.114";

    // 商户号
    public static final String MCH_ID = "1329812701";

    // 默认编码
    public static final String CHARSET = "UTF-8";

    // 交易类型
    public static final String TRADE_TYPE = "APP";
    public static final String TRADE_TYPE_H5 = "JSAPI";

    public static final String PACKAGE = "Sign=WXPay";


    //-------------------------------------- H5返利


    //微信开发平台应用id - H5返利
    public static final String APP_ID_H5 = "wx51d347df2a345dd3";

    //  - H5返利 key
    public static final String KEY_H5 = "ICHUJIAN1375800602CF122139C296F1";

    //应用对应的凭证
    public static final String APP_SECRET_H5 = "d38613655c11160cfa167bc062f8aef6";

    // 商户号
    public static final String MCH_ID_H5 = "1375800602";

    // 通知地址
    public static final String NOTIFY_URL_H5 = "servlet/h5WXNotify";

    // 获取用户OpenId
    public static final String URL_GET_OPEN_ID = "https://api.weixin.qq.com/sns/oauth2/access_token";

    // 获取access_token(此token是公众号token,不是页面授权token)
    public static final String URL_GET_TOKEN = "https://api.weixin.qq.com/cgi-bin/token";

    // 获取ticket
    public static final String URL_GET_TICKET = "http://api.weixin.qq.com/cgi-bin/ticket/getticket";

}
