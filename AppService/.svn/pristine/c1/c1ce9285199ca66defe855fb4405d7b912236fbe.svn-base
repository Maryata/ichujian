package com.sys.ekey.index.service.impl;

import com.sys.ekey.index.service.IndexService;
import com.sys.ekey.task.service.EKTaskService;
import com.sys.util.StrUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Maryn on 2016/3/1.
 */
@Service
@Transactional
public class IndexServiceImpl extends SqlMapClientDaoSupport implements IndexService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private EKTaskService ekTaskService;

    private static Logger LOGGER = Logger.getLogger(IndexServiceImpl.class);

    public static final Integer INDEXAPPCOUNT = 23;// 首页显示APP数量
    public static final Integer EASYBUYURL = 6;// 供应商的“周边数码url”的长度
    public static final String APPTYPE_EASYBUY = "2";// APP类型-周边数码
    public static final String APPTYPE_EXCHANGE = "5";// APP类型-免费换膜
    public static final String EDITABLE = "1";// 模板APP-可删除
    public static final String UNEDITABLE = "0";// 模板APP-不可删除

    // 是否显示“免费换膜”和“周边数码”
    private boolean[] showInternal(String supCode) {
        boolean exchangeFlag = true;
        boolean easyBuyFlag = true;
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("SUPCODE", supCode);
        // 查询当前供应商是否显示“免费换膜”和“周边数码”
        List<Map<String, Object>> supInfo = this.getSqlMapClientTemplate().queryForList("ek_index.showInternal", paramMap);
        if (StrUtils.isNotEmpty(supInfo)) {
            Map<String, Object> map = supInfo.get(0);
            String showExchange = StrUtils.emptyOrString(map.get("C_ISEXCHANGE"));
            String url = StrUtils.emptyOrString(map.get("C_URL"));
            if ("0".equals(showExchange)) {// 不显示“免费换膜”
                exchangeFlag = false;
            }
            if (StrUtils.isNotEmpty(url) && url.length() != EASYBUYURL) {// 不显示“周边数码”
                easyBuyFlag = false;
            }
        }
        return new boolean[]{exchangeFlag, easyBuyFlag};
    }

    @Override
    // 用户首页APP布局接口
    public List<Map<String, Object>> indexAppLayout(String uid, String supCode) {
        try {
            Map<String, Object> paramMap = new HashMap();
            List<Map<String, Object>> modelTemp = new ArrayList();
            List<Map<String, Object>> usersTemp = new ArrayList();

            /** 2016-04-13 增加“没有激活码”的处理 begin */
            if (StrUtils.isEmpty(supCode)) {
                paramMap.put("UID", StrUtils.isEmpty(uid) ? "-1" : uid);
                List<Map<String, Object>> modelApp = this.getSqlMapClientTemplate().queryForList("ek_index.modelApp", paramMap);
                return addSupApp(modelApp, null);
            }
            /** 2016-04-13 增加“没有激活码”的处理 end */

            // 查询当前供应商的定制APP
            paramMap.put("SUPCODE", supCode);
            List<Map<String, Object>> supplierApp = this.getSqlMapClientTemplate().queryForList("ek_index.supplierApp", paramMap);
            if (StrUtils.isNotEmpty(supplierApp)) {
                for (Map<String, Object> map : supplierApp) {
                    map.put("C_EDITABLE", UNEDITABLE);
                }
            }
            paramMap.clear();
            paramMap.put("EDITABLE", UNEDITABLE);
            paramMap.put("UID", StrUtils.isEmpty(uid) ? "-1" : uid);

            boolean[] showInternal = showInternal(supCode);// 根据供应商代码查询是否显示“免费换膜”和“周边数码”
            boolean showExchange = showInternal[0];// “免费换膜”
            boolean showEasyBuy = showInternal[1];// “周边数码”

            if (StrUtils.isEmpty(uid)) {// 无用户id
                // 查询所有模板APP
                List<Map<String, Object>> modelApp = this.getSqlMapClientTemplate().queryForList("ek_index.modelApp", paramMap);

                if (!showExchange || !showEasyBuy) {// “免费换膜”和“周边数码”有一个需要隐藏，就移除
                    removeExchange(modelApp, modelTemp, null, showExchange, showEasyBuy);
                    return addSupApp(modelTemp, supplierApp);
                    //return modelTemp;
                } else {// 否则直接将模板APP返回到前台
                    return addSupApp(modelApp, supplierApp);
                    //return modelApp;
                }
            } else {// 有用户id
                List<String> modelAid = new ArrayList();

                // 查询模板中不可删除的应用
                List<Map<String, Object>> modelApp = this.getSqlMapClientTemplate().queryForList("ek_index.modelApp", paramMap);
                // 查询用户的应用
                List<Map<String, Object>> usersApp = this.getSqlMapClientTemplate().queryForList("ek_index.usersApp", paramMap);

                removeExchange(modelApp, modelTemp, modelAid, showExchange, showEasyBuy);

                // 遍历两个集合，去除用户app中在模板app中出现的app
                if (StrUtils.isNotEmpty(usersApp)) {
                    for (int j = 0; j < usersApp.size(); j++) {
                        Map<String, Object> usersMap = usersApp.get(j);
                        usersMap.put("C_EDITABLE", EDITABLE);
                        String uAid = usersMap.get("C_ID").toString();
                        String type = usersMap.get("C_TYPE").toString();
                        // 如果是“免费换膜”，停止本次循环，进入下一次循环。
                        if (APPTYPE_EXCHANGE.equals(type) && !showExchange) {
                            continue;
                        }
                        // 如果是“周边数码”，停止本次循环，进入下一次循环。
                        if (APPTYPE_EASYBUY.equals(type) && !showEasyBuy) {
                            continue;
                        }
                        // 不存在于模板并且不存在于临时app集合的，存放临时app集合
                        if (!modelAid.contains(uAid) && !usersTemp.contains(usersMap)) {
                            usersTemp.add(usersMap);
                        }
                    }
                }

                modelTemp.addAll(supplierApp);// 将定制APP放到不可删除的APP和可删除的APP之间
                modelTemp.addAll(usersTemp);// 两个集合拼接
//                return modelTemp.subList(0, modelTemp.size() > INDEXAPPCOUNT ? INDEXAPPCOUNT : modelTemp.size());// 取前23个
                return modelTemp;
            }
        } catch (DataAccessException e) {
            LOGGER.error("IndexServiceImpl.indexAppLayout failed, e : " + e.getMessage());
        }
        return null;
    }

    /**
     * 遍历模板应用，将除“免费换膜”和“周边数码”以外的其他应用存入临时集合
     *
     * @param modelApp     所有模板APP（uid为空时）或不可删除的模板APP（uid不为空）
     * @param modelTemp    存放除“免费换膜”以外的其他应用
     * @param modelAid     存放模板app的id，用于去除用户APP中重复出现的APP
     * @param showExchange 是否显示“免费换膜”
     * @param showEasyBuy  是否显示“周边数码”
     */
    private void removeExchange(List<Map<String, Object>> modelApp,
                                List<Map<String, Object>> modelTemp,
                                List<String> modelAid, boolean showExchange, boolean showEasyBuy) {
        for (int i = 0; i < modelApp.size(); i++) {
            Map<String, Object> modelMap = modelApp.get(i);
            String mAid = modelMap.get("C_ID").toString();
            if (null != modelAid) {
                // 记录模板app的id
                modelAid.add(mAid);
            }
            String type = modelMap.get("C_TYPE").toString();
            // 如果是“免费换膜”，停止本次循环，进入下一次循环。
            if (APPTYPE_EXCHANGE.equals(type) && !showExchange) {
                continue;
            }
            // 如果是“周边数码”，停止本次循环，进入下一次循环。
            if (APPTYPE_EASYBUY.equals(type) && !showEasyBuy) {
                continue;
            }
            // 模板app存入模板临时集合
            modelTemp.add(modelMap);
        }
    }

    /**
     * 将定制APP放到不可删除的APP和可删除的APP之间
     *
     * @param modelTemp   所有模板APP（包括不可删除和可删除的APP）
     * @param supplierApp 定制APP
     * @return
     */
    private List<Map<String, Object>> addSupApp(
            List<Map<String, Object>> modelTemp, List<Map<String, Object>> supplierApp) {
        List<Map<String, Object>> uneditableTemp = new ArrayList();
        List<Map<String, Object>> editableTemp = new ArrayList();
        if (modelTemp.size() > 0) {
            for (Map<String, Object> map : modelTemp) {
                String editable = StrUtils.emptyOrString(map.get("C_EDITABLE"));
                if (UNEDITABLE.equals(editable)) {
                    uneditableTemp.add(map);
                } else {
                    editableTemp.add(map);
                }
            }
        }
        if (StrUtils.isNotEmpty(supplierApp)) {
            uneditableTemp.addAll(supplierApp);
        }
        uneditableTemp.addAll(editableTemp);
//        return uneditableTemp.subList(0, uneditableTemp.size() > INDEXAPPCOUNT ? INDEXAPPCOUNT : uneditableTemp.size());
        return uneditableTemp;
    }

    @Override
    // 首页广告
    public List<Map<String, Object>> advertInfo() {
        List<Map<String, Object>> list = new ArrayList();
        try {
            list = this.getSqlMapClientTemplate().queryForList("ek_index.advertInfo");
        } catch (DataAccessException e) {
            LOGGER.error("IndexServiceImpl.advertInfo failed, e : " + e.getMessage());
        }
        return list;
    }

    @Override
    // 用户点击APP历史纪录
    public List<Map<String, Object>> appUsingHis(String uid, String imei) {
        List<Map<String, Object>> list = new ArrayList();
        Map<String, Object> paramMap = new HashMap();
        if (StrUtils.isNotEmpty(uid)) {
            paramMap.put("UID", uid);
        } else {
            paramMap.put("IMEI", imei);
        }
        try {
            list = this.getSqlMapClientTemplate().queryForList("ek_index.appUsingHis", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("IndexServiceImpl.appUsingHis failed, e : " + e.getMessage());
        }
        return list;
    }

    @Override
    // 记录用户点击APP的行为
    public void appUsingRecord(String aid, String uid, String imei) {
        try {
            Map<String, Object> paramMap = new HashMap();
            paramMap.put("AID", aid);
            //paramMap.put("DATE", new Date());
            paramMap.put("UID", StrUtils.emptyOrString(uid));
            paramMap.put("IMEI", StrUtils.emptyOrString(imei));
            this.getSqlMapClientTemplate().update("ek_index.appUsingRecord", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("IndexServiceImpl.appUsingRecord failed, e : " + e.getMessage());
        }
    }

    @Override
    // 查询所有按键的点击次数
    public Map<String, Integer> countOfEachKeys(String imei, String date) {
        Map<String, Integer> reqMap = new HashMap<String, Integer>();
        for (int i = 1; i <= 4; i++) {
            reqMap.put(String.valueOf(i), 0);
        }
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("IMEI", imei);
        paramMap.put("DATE", date);
        try {
            List<Map<String, Object>> list = this.getSqlMapClientTemplate().queryForList("ek_index.countOfEachKeys", paramMap);
            Integer total = 0;
            if (StrUtils.isNotEmpty(list)) {
                for (Map<String, Object> map : list) {
                    String key = StrUtils.emptyOrString(map.get("C_KEY"));
                    Integer cnt = StrUtils.zeroOrInt(map.get("CNT"));
                    reqMap.put(key, cnt);
                    // 累加，计算总数量
                    total += cnt;
                }
            }
            reqMap.put("-1", total);// -1表示总数量
        } catch (DataAccessException e) {
            LOGGER.error("IndexServiceImpl.countOfEachKeys failed, e : " + e.getMessage());
        }
        return reqMap;
    }

    @Override
    public List<Map<String, Object>> appByKey(String key, String supCode) {
        List<Map<String, Object>> result = null;

        // 默认取一号键应用
        if (StringUtils.isEmpty(key)) {
            key = "1";
        }
        if (StringUtils.isEmpty(supCode)) {
            supCode = "-1";
        }

        // 如果不存在此供应商代码，则是非定制供应商
        if (!_isExists(supCode)) {
            supCode = "-1";
        }

        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("key", key);
        parameterMap.put("supCode", supCode);

        result = getSqlMapClientTemplate().queryForList("ek_index.appByKey", parameterMap);

        if (result == null) {
            result = new ArrayList<Map<String, Object>>();
        }

        return result;
    }

    private boolean _isExists(String supCode) {
        final String sql = "select nvl(count(1),0) from t_ek_index_key_app where c_supCode=?";

        int count = jdbcTemplate.queryForObject(sql, new Object[]{supCode}, Integer.class);

        return count >= 1;
    }

    @Override
    public List<Map<String, Object>> keyInfo(String key, String supCode) {
        List<Map<String, Object>> result = null;

        Map<String, Object> parameterMap = new HashMap<String, Object>();

        // 如果不存在此供应商代码，则是非定制供应商
        if (!_isExists(supCode)) {
            //supCode = "-1";
            return new ArrayList<Map<String, Object>>();
        }

        parameterMap.put("key", key);
        parameterMap.put("supCode", supCode);

        result = getSqlMapClientTemplate().queryForList("ek_index.keyInfo", parameterMap);

        if (result == null) {
            result = new ArrayList<Map<String, Object>>();
        }

        return result;
    }

    @Override
    // 查询现有的所有首页应用总数，如果总数于参数不相等则返回所有应用，否则返回0
    public List<Map<String, Object>> getAllAppOrNot(String cnt) {
        Integer count = (Integer) getSqlMapClientTemplate().queryForObject("ek_index.getCountOfAllApp", Integer.class);

        if (count.compareTo(Integer.valueOf(cnt)) != 0) {
            return getSqlMapClientTemplate().queryForList("ek_index.getAllApp");
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public boolean customHis(String uid, String aid, String op) {
        Map<String, Object> parameterMap = new HashMap<String, Object>();

        Date date = new Date();
        parameterMap.put("uid", uid);
        parameterMap.put("aid", aid);
        parameterMap.put("v_date", date);
        parameterMap.put("op", op);

        if (aid.indexOf(',') != -1) {
            final String sql = "insert into t_ek_index_user_custom_his (c_id, c_uid, c_aid, c_op, c_date) values (seq_ek_index_u_custom_his.nextval, ?, ?, ?, ?)";
            final String addSql = "insert into t_ek_index_user_app (c_id, c_uid, c_aid, c_order, c_date) values (seq_ek_index_app_info.nextval,?,?,(select nvl(max(c_order),0) from t_ek_index_user_app where c_uid = ?) + 1,?)";
            String[] aidArr = aid.split(",");
            List<Object[]> batchArgs = new ArrayList<Object[]>();

            for (int i = 0; i < aidArr.length; ++i) {
                Object[] args = new Object[4];

                args[0] = uid;
                args[1] = aidArr[i];
                args[2] = op;
                args[3] = date;

                batchArgs.add(args);

                if ("1".equals(op)) {
                    Object[] addArgs = new Object[4];
                    addArgs[0] = uid;
                    addArgs[1] = aidArr[i];
                    addArgs[2] = uid;
                    addArgs[3] = date;
                    // 这边由于顺序原因这样写，因为后一条记录的顺序依赖前一条数据
                    // 另：可以先查出数据库当前用户的最后一条记录，在程序中计算排序，然后批量
                    // 更新，效果比这个好，后续调整
                    try {
                        jdbcTemplate.update(addSql, addArgs);
                    } catch (Exception e) {
                        logger.error("批量添加定制应用出错", e);

                        return false;
                    }

                    // 添加某个指定应用到首页
                    // 任务id， 用户id，类型3
                    // 根据任务id查询指定应用是什么
                    //...
                    //应用id 去查应用表
                    //
//                    ekTaskService.reward(uid,"3","18",aid);
                }
            }

            try {
                jdbcTemplate.batchUpdate(sql, batchArgs);
            } catch (Exception e) {
                logger.error("批量更新定制记录出错", e);
                return false;
            }
            if ("0".equals(op)) {
                parameterMap.put("ids", Arrays.asList(aid.split(",")));
                try {
                    getSqlMapClientTemplate().delete("ek_index.appDel", parameterMap);
                } catch (Exception e) {
                    logger.error("批量删除用户应用出错", e);
                    return false;
                }
            }
        } else {
            try {
                getSqlMapClientTemplate().insert("ek_index.customHis", parameterMap);
            } catch (Exception e) {
                logger.error("记录定制信息出错", e);
                return false;
            }

            // 减应用
            if ("0".equals(op)) {
                parameterMap.put("ids", Arrays.asList(aid.split(",")));
                try {
                    getSqlMapClientTemplate().delete("ek_index.appDel", parameterMap);
                } catch (Exception e) {
                    logger.error("删除用户应用出错", e);
                    return false;
                }
            } else { // 1 加应用
                try {
                    getSqlMapClientTemplate().insert("ek_index.appAdd", parameterMap);


//                    ekTaskService.reward(uid,"3","18",aid);

                } catch (Exception e) {
                    logger.error("添加应用出错", e);
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public List<Map<String, Object>> more(String uid, String supCode) {
        List<Map<String, Object>> result = null;
        boolean[] flags;
        if (StrUtils.isNotEmpty(supCode)) {
            flags = new boolean[]{true, true};
        } else {
            // 是否显示免费换膜
            flags = showInternal(supCode);
        }
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("showExchange", flags[0]);
        parameterMap.put("easyBuy", flags[1]);

        // 用户未注册 根据系统初始模版数据返回结果

        if (StringUtils.isEmpty(uid)) {
            result = getSqlMapClientTemplate().queryForList("ek_index.more", parameterMap);
        } else { // 根据用户定制返回结果
            parameterMap.put("uid", uid);
            result = getSqlMapClientTemplate().queryForList("ek_index.more0", parameterMap);
        }

        if (result == null) {
            result = new ArrayList<Map<String, Object>>();
        }

        return result;
    }
}
