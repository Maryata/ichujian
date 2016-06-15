package com.org.mokey.basedata.sysinfo.entiy;

import com.org.mokey.basedata.sysinfo.util.Global;
import com.org.mokey.common.util.ApDateTime;
import com.org.mokey.util.MD5;

import java.io.Serializable;

/**
 * Created by vpc on 2016/4/11.
 */
public class FreeCall implements Serializable {


    public String freecall_select = "http://api.feiyucloud.com/api/getAccount";//查询账户
    public String freecall_disable = "http://api.feiyucloud.com/api/disableAccount";//禁用账户
    public String freecall_enble = "http://api.feiyucloud.com/api/enableAccount";//启用账户
    public String freecall_update = "http://api.feiyucloud.com/api/modifyAccountDisplayNumber";//修改飞语云账户绑定手机号
    public String freecall_onlineStatus = "http://api.feiyucloud.com/api/onlineStatus";//查询飞语云账户的在线状态
    public String freecall_fetchCallHistory = "http://api.feiyucloud.com/api/fetchCallHistory";//查询通话记录
    public String freecall_addAccount="http://api.feiyucloud.com/api/addAccount";


    private String appId;//飞语云通讯给每个APP的分配的唯一APPID
    private String appAccountId;//在应用服务器端用户的唯一名称，同一个应用必须要保证唯一
    private String globalMobilePhone;//待绑定手机号码，拨打出去可以显示用户的本机号码，要带国别码； 例如：86+13888888888；如果账户要调用双向回拨接口，必须绑定手机号;
    private String district;//号码的国际区号（中国就是86）
    private Long ti;//自1970年1月1日0时起的毫秒数（UTC/GMT+08:00）
    private String ver;//当前接口的版本号：2.1.0
    private String au;//MD5（appId+ appToken+ti）
    private String infoType;//查询信息类型：1）飞语云账户号码；2）APP账户号码；3）手机号码
    private String info;//infoType对应的查询信息
    private String fyAccountId;//飞语云账户
    private String fyAccountIds;//需要查询的飞语云账户ID。多个用英文逗号分隔，一次最多查询10个。
    /**
     * 获取 au
     *
     * @param time
     * @return
     */
    public static String getAuValue(Long time) {//MD5（appId+ appToken+ti）
        String appid = Global.getConfig("appId");
        String appToken = Global.getConfig("appToken");
        return MD5.md5Code(appid + appToken + time);
    }

    /**
     * 获取 appid
     *
     * @return
     */
    public static String GetAppId() {
        return Global.getConfig("appId");
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static Long GetTime() {
        return ApDateTime.getNowDateTime();
    }

    /**
     * 获取 ver
     *
     * @return
     */
    public static String GetVer() {
        return Global.getConfig("ver");
    }

    public static void main(String[] args) {
        //System.out.println(getAuValue());
    }
}
