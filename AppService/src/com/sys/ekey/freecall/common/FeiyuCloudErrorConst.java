package com.sys.ekey.freecall.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 飞语云常量-错误代码
 * <p/>
 * Created by Maryn on 2016/4/11.
 */
public class FeiyuCloudErrorConst {

    public static Map<String, String> errorCodeMap;

    static {
        errorCodeMap = new HashMap<>();
        errorCodeMap.put(Server.AUTH_FAIL_NULL_ACCID, "授权失败，参数fyAccountId为空");
        errorCodeMap.put(Server.AUTH_FAIL_NULL_APPID, "授权失败，参数appId为空");
        errorCodeMap.put(Server.AUTH_FAIL_NULL_AU, "授权失败，参数au为空");
        errorCodeMap.put(Server.AUTH_FAIL_TIMESTAMP, "授权失败，时间戳出错");
        errorCodeMap.put(Server.AUTH_FAIL_ENCRYPT, "授权失败，加密出错");
        errorCodeMap.put(Server.AUTH_FAIL_UNEXISTSAPP, "授权失败，此应用不存在");
        errorCodeMap.put(Server.AUTH_FAIL_NULL_APP_ACCID, "授权失败，参数appAccountId为空");
        errorCodeMap.put(Server.AUTH_FAIL_IPBAN, "授权失败，ip禁止");
        errorCodeMap.put(Server.AUTH_FAIL_ASCRIPTION, "授权失败，此app不属于此sp账户");
        errorCodeMap.put(Server.ACCOUNT_EXISTING, "此账户在此应用下已经创建过");
        errorCodeMap.put(Server.ACCOUNT_EXISTING_PHONENUM, "此手机号码已经被其他账户绑定");
        errorCodeMap.put(Server.ACCOUNT_UNEXISTING, "此应用下对应的账户不存在");
        errorCodeMap.put(Server.FEIYU_ACCOUNT_UNEXISTING, "此应用下对应的飞语账户不存在");
        errorCodeMap.put(Server.ACCOUNT_BAN, "此账户已经禁用");
        errorCodeMap.put(Server.ACCOUNT_ENABLE, "此账户已经被启用");
        errorCodeMap.put(Server.CALL_OVER_MAXONLINE, "超过请求话机在线最大数量");
        errorCodeMap.put(Server.CALL_CALLEE, "被叫不正确");
        errorCodeMap.put(Server.FEIYUCLOUD_UNBOUNDED_PHONE, "此飞语号没有绑定手机号");
        errorCodeMap.put(Server.CALL_CALLER_86BEGINNING, "主叫必须是以86开头中国手机号码或者座机");
        errorCodeMap.put(Server.CALL_CALLEE_SPECIALNUM, "被叫不能是特定服务号");
        errorCodeMap.put(Server.CALL_CALLER_CALLEE_SAME, "主叫和被叫不能相同");
        errorCodeMap.put(Server.CALL_ONCEMORE_CALL_IN10SEC, "同一个主叫和被叫10秒内不能连续发起两次呼叫");
        errorCodeMap.put(Server.CALL_MAXTIME, "通话最大分钟数必须大于0");
        errorCodeMap.put(Server.CALL_REC, "没有找对对应的通话请求记录");
        errorCodeMap.put(Server.CALL_CALLEE_UNEXISTING, "被叫不存在");
        errorCodeMap.put(Server.CALL_UNCONFIG_AUTHURI, "没有配置呼叫授权地址");
        errorCodeMap.put(Server.CALL_INTERNAL, "呼叫内部错误");
        errorCodeMap.put(Server.HTTP_REQUEST, "网络请求失败");
        errorCodeMap.put(Server.HTTP_REQUEST_APPSERVER, "网络请求app服务器失败");
        errorCodeMap.put(Server.HTTP_PARSE_RESAULT, "解析推送话单返回结果失败");
        errorCodeMap.put(Server.HTTP_REQUEST_EXCHANGE, "请求交换接口出错");

        errorCodeMap.put(Server.ANDROID_INNER_ERROR, "内部错误");
        errorCodeMap.put(Server.ANDROID_NULL_CONTEXT, "context为空");
        errorCodeMap.put(Server.ANDROID_NULL_APPID, "飞语云应用ID为空");
        errorCodeMap.put(Server.ANDROID_NULL_TOKEN, "飞语云应用Token为空");
        errorCodeMap.put(Server.ANDROID_NULL_ACCID, "飞语云账户ID为空");
        errorCodeMap.put(Server.ANDROID_NULL_PWD, "飞语云账户密码为空");
        errorCodeMap.put(Server.ANDROID_NULL_CHANNELID, "渠道号为空");
        errorCodeMap.put(Server.ANDROID_CONNECT_INTERRUPT, "飞语云连接已断开");
        errorCodeMap.put(Server.ANDROID_UNCONNECT_INTERNET_ERROR, "飞语云连接失败，网络异常");
        errorCodeMap.put(Server.ANDROID_INTERNET_REQUEST_FAILED, "网络请求失败");
        errorCodeMap.put(Server.ANDROID_INTERNET_ERROR, "网络异常");
        errorCodeMap.put(Server.ANDROID_CALL_FAILED, "呼叫失败");
        errorCodeMap.put(Server.ANDROID_NULL_CALLEE, "被叫号码为空");
        errorCodeMap.put(Server.ANDROID_SHOW_PHONE_ERROR, "显号类型错误");
        errorCodeMap.put(Server.ANDROID_CALL_FAILED_UNCONNECT, "呼叫失败，未连接到飞语云");
        errorCodeMap.put(Server.ANDROID_PHONE_NUT_FOUND, "找不到当前电话");
        errorCodeMap.put(Server.ANDROID_IS_CALLING, "呼叫失败，正在通话中");

    }
    /**
     * 应用服务器错误代码表
     */
    public class Server {
        /**
         * 授权失败，参数fyAccountId为空
         */
        public static final String AUTH_FAIL_NULL_ACCID = "101001";
        /**
         * 授权失败，参数appId为空
         */
        public static final String AUTH_FAIL_NULL_APPID = "101002";
        /**
         * 授权失败，参数au为空
         */
        public static final String AUTH_FAIL_NULL_AU = "101003";
        /**
         * 授权失败，时间戳出错
         */
        public static final String AUTH_FAIL_TIMESTAMP = "101004";
        /**
         * 授权失败，加密出错
         */
        public static final String AUTH_FAIL_ENCRYPT = "101005";
        /**
         * 授权失败，此应用不存在
         */
        public static final String AUTH_FAIL_UNEXISTSAPP = "101006";
        /**
         * 授权失败，参数appAccountId为空
         */
        public static final String AUTH_FAIL_NULL_APP_ACCID = "102001";
        /**
         * 授权失败，ip禁止
         */
        public static final String AUTH_FAIL_IPBAN = "102002";
        /**
         * 授权失败，此app不属于此sp账户
         */
        public static final String AUTH_FAIL_ASCRIPTION = "102003";
        /**
         * 此账户在此应用下已经创建过
         */
        public static final String ACCOUNT_EXISTING = "103001";
        /**
         * 此手机号码已经被其他账户绑定
         */
        public static final String ACCOUNT_EXISTING_PHONENUM = "103002";
        /**
         * 此应用下对应的账户不存在
         */
        public static final String ACCOUNT_UNEXISTING = "103003";
        /**
         * 此应用下对应的飞语账户不存在
         */
        public static final String FEIYU_ACCOUNT_UNEXISTING = "103004";
        /**
         * 此账户已经禁用
         */
        public static final String ACCOUNT_BAN = "103005";
        /**
         * 此账户已经被启用
         */
        public static final String ACCOUNT_ENABLE = "103006";
        /**
         * 超过请求话机在线最大数量
         */
        public static final String CALL_OVER_MAXONLINE = "103007";
        /**
         * 被叫不正确
         */
        public static final String CALL_CALLEE = "104001";
        /**
         * 此飞语号没有绑定手机号
         */
        public static final String FEIYUCLOUD_UNBOUNDED_PHONE = "104002";
        /**
         * 主叫必须是以86开头中国手机号码或者座机
         */
        public static final String CALL_CALLER_86BEGINNING = "104003";
        /**
         * 被叫不能是特定服务号
         */
        public static final String CALL_CALLEE_SPECIALNUM = "104004";
        /**
         * 主叫和被叫不能相同
         */
        public static final String CALL_CALLER_CALLEE_SAME = "104005";
        /**
         * 同一个主叫和被叫10秒内不能连续发起两次呼叫
         */
        public static final String CALL_ONCEMORE_CALL_IN10SEC = "104006";
        /**
         * 通话最大分钟数必须大于0
         */
        public static final String CALL_MAXTIME = "104007";
        /**
         * 没有找对对应的通话请求记录
         */
        public static final String CALL_REC = "104008";
        /**
         * 被叫不存在
         */
        public static final String CALL_CALLEE_UNEXISTING = "104009";
        /**
         * 没有配置呼叫授权地址
         */
        public static final String CALL_UNCONFIG_AUTHURI = "104010";
        /**
         * 呼叫内部错误
         */
        public static final String CALL_INTERNAL = "104011";
        /**
         * 网络请求失败
         */
        public static final String HTTP_REQUEST = "105001";
        /**
         * 网络请求app服务器失败
         */
        public static final String HTTP_REQUEST_APPSERVER = "105002";
        /**
         * 解析推送话单返回结果失败
         */
        public static final String HTTP_PARSE_RESAULT = "105003";
        /**
         * 请求交换接口出错
         */
        public static final String HTTP_REQUEST_EXCHANGE = "106001";
        /**
         * 内部错误
         */
        public static final String ANDROID_INNER_ERROR = "300000";
        /**
         * context为空
         */
        public static final String ANDROID_NULL_CONTEXT = "301000";
        /**
         * 飞语云应用ID为空
         */
        public static final String ANDROID_NULL_APPID = "301001";
        /**
         * 飞语云应用Token为空
         */
        public static final String ANDROID_NULL_TOKEN = "301002";
        /**
         * 飞语云账户ID为空
         */
        public static final String ANDROID_NULL_ACCID = "301003";
        /**
         * 飞语云账户密码为空
         */
        public static final String ANDROID_NULL_PWD = "301004";
        /**
         * 渠道号为空
         */
        public static final String ANDROID_NULL_CHANNELID = "301005";
        /**
         * 飞语云连接已断开
         */
        public static final String ANDROID_CONNECT_INTERRUPT = "301100";
        /**
         * 飞语云连接失败，网络异常
         */
        public static final String ANDROID_UNCONNECT_INTERNET_ERROR = "301101";
        /**
         * 网络请求失败
         */
        public static final String ANDROID_INTERNET_REQUEST_FAILED = "302000";
        /**
         * 网络异常
         */
        public static final String ANDROID_INTERNET_ERROR = "302001";
        /**
         * 呼叫失败
         */
        public static final String ANDROID_CALL_FAILED = "302101";
        /**
         * 被叫号码为空
         */
        public static final String ANDROID_NULL_CALLEE = "302102";
        /**
         * 显号类型错误
         */
        public static final String ANDROID_SHOW_PHONE_ERROR = "302103";
        /**
         * 呼叫失败，未连接到飞语云
         */
        public static final String ANDROID_CALL_FAILED_UNCONNECT = "302104";
        /**
         * 找不到当前电话
         */
        public static final String ANDROID_PHONE_NUT_FOUND = "302105";
        /**
         * 呼叫失败，正在通话中
         */
        public static final String ANDROID_IS_CALLING = "302106";

    }

    /**
     * Android错误代码表
     */
    class App {
//        300000	内部错误
//        301000	context为空
//        301001	飞语云应用ID为空
//        301002	飞语云应用Token为空
//        301003	飞语云账户ID为空
//        301004	飞语云账户密码为空
//        301005	渠道号为空
//        301100	飞语云连接已断开
//        301101	飞语云连接失败，网络异常
//        302000	网络请求失败
//        302001	网络异常
//        302101	呼叫失败
//        302102	被叫号码为空
//        302103	显号类型错误
//        302104	呼叫失败，未连接到飞语云
//        302105	找不到当前电话
//        302106	呼叫失败，正在通话中
//        302201	被叫不在线
//        302202	被叫拒接
//        302203	被叫正忙
//        302204	呼叫超时
//        302205	呼叫间隔过短
//        302206	呼叫受限
//        302207	呼叫服务暂不可用
//        302208	呼叫终止
//        302209	呼叫失败，被叫未找到
//        302210	开发者账户余额不足
    }
}
