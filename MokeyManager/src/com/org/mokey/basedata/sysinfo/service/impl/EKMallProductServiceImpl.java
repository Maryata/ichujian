package com.org.mokey.basedata.sysinfo.service.impl;

import com.org.mokey.basedata.sysinfo.dao.EKMallProductDao;
import com.org.mokey.basedata.sysinfo.service.EKMallProductService;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 商品详情
 * Created by vpc on 2016/4/25.
 */
@Service
public class EKMallProductServiceImpl implements EKMallProductService {
    @Autowired
    private EKMallProductDao eKMallProductDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询列表信息
     *
     * @param page
     * @param title
     * @param type
     * @return
     */
    @Override
    public Map<String, Object> list(int page, String title, String type) {
        return eKMallProductDao.list(page, title, type);
    }

    /**
     * 添加商品信息
     *
     * @param id
     * @param
     * @param logo
     * @param
     * @param
     */
    @Override
    public void addProduct(String id, String title, String subTitle, String function, String anAbstract, String cost, String total, String type, String carriage, String logo, String amount, String islive, String detail, String illustr, String modifier) {
        eKMallProductDao.addProduct(id, title, subTitle, function, anAbstract, cost, total, type, carriage, logo, amount, islive, detail, illustr, modifier);
    }

    /**
     * 删除商品信息
     *
     * @param id
     */
    @Override
    public void toDel(String id) {
        eKMallProductDao.toDel(id);
    }

    /**
     * 更新商品信息
     *
     * @param id
     * @param title
     * @param logo
     * @param subTitle
     * @param function
     */
    @Override
    public void updateProduct(String id, String title, String subTitle, String function, String anAbstract, String cost, String total, String type, String carriage, String logo, String amount, String islive, String detail, String illustr, String modifier) {
        eKMallProductDao.updateProduct(id, title, subTitle, function, anAbstract, cost, total, type, carriage, logo, amount, islive, detail, illustr, modifier);
    }

    /**
     * 查询商品详情
     *
     * @param id
     * @return
     */
    @Override
    public List<Map<String, Object>> selectOne(String id) {
        return eKMallProductDao.selectOne(id);
    }

    /**
     * 查询商品吗列表
     *
     * @param page
     * @param type
     * @return
     */
    @Override
    public Map<String, Object> getCodeList(int page, String pid, String type) {
        return eKMallProductDao.getCodeList(page, pid, type);
    }

    /**
     * 上传商品码
     *
     * @param list
     * @param id
     */
    @Override
    public void upload(List<String> list, String id) {
        List<Object[]> codes = new ArrayList<Object[]>();
        if (StrUtils.isNotEmpty(list)) {
            for (String code : list) {
                Object[] row = new Object[5];
                String newId = JdbcTemplateUtils.getSeqId(jdbcTemplate, "SEQ_EK_MALL_PRO_CODE");
                row[0] = newId;// 主键
                row[1] = id;// 礼包id
                row[2] = code;// 礼包码
                row[3] = "1";// 是否有效
                row[4] = "0";// 状态 0：未领取  1：已领取
                codes.add(row);
            }
        }

        eKMallProductDao.upload(codes);
    }
}
