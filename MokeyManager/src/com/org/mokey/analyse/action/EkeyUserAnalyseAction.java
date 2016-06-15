package com.org.mokey.analyse.action;

import com.opensymphony.xwork2.ActionSupport;
import com.org.mokey.analyse.service.EkeyUserAnalyseService;
import com.org.mokey.analyse.service.UserAnalyseService;
import com.org.mokey.analyse.vo.EkeyComplexBean;
import com.org.mokey.common.AbstractAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by Maryn on 2016/4/5.
 */
@Controller("ekeyUserAnalyseAction")
public class EkeyUserAnalyseAction extends AbstractAction {

    @Autowired
    private EkeyUserAnalyseService ekeyUserAnalyseService;

    @Autowired
    private UserAnalyseService userAnalyseService;

    public String analyse() throws Exception {
        // 1 日 2 周 3 月
        String type = getParameter("timeType");
        if(StringUtils.isEmpty(type)) {
            return ERROR;
        }

        Map<String,Object> result;
        String sup = getParameter(ParameterKey.sup);// 供应商
        String userId = getSessionLoginUser().getUserId();// 当前用户id
//        String userId = "1b877d3f-09b3-4011-b9f8-837868251578";// 当前用户id
        String year = getParameter(ParameterKey.year);// 年份
        String sDate = getParameter(ParameterKey.sDate);// 开始时间
        String eDate = getParameter(ParameterKey.eDate);// 结束时间
        String timeType = getParameter(ParameterKey.timeType);// 时间类型：1:日、2:周、3:月
        log.info("into analyse...");
        log.info("sup = " + sup + ", year = " + year + ", sDate = "
                + sDate + ", eDate = " + eDate + ", userId = " + userId
                + ", timeType = " + timeType);

        // 设置可见的供应商
        getRequest().setAttribute("suppliers",userAnalyseService.visibleSupplier(userId,sup));

        result = ekeyUserAnalyseService.userAnalyse(userId,sup,sDate,eDate,timeType,year);
        List<EkeyComplexBean> ekeyComplexBeanList = (List<EkeyComplexBean>) result.get("eKey");

        getRequest().setAttribute(ParameterKey.year,year);
        getRequest().setAttribute(ParameterKey.sDate,sDate);
        getRequest().setAttribute(ParameterKey.eDate,eDate);
        getRequest().setAttribute(ParameterKey.timeType,timeType);

        getRequest().getSession().setAttribute("eKeyData",ekeyComplexBeanList);

        return ActionSupport.SUCCESS;
    }

    private interface ParameterKey {
        String year = "year";
        String sDate = "sDate";
        String eDate = "eDate";
        String timeType = "timeType";
        String dataType = "dataType";
        String sup = "sup";
        String SVG = "svg";
    }
}
