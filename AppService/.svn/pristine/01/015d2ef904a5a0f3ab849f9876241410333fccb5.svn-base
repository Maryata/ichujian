package com.sys.ekey.tbk.service.impl;

import com.sys.ekey.tbk.service.TbkService;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/8/15.
 */
@SuppressWarnings("deprecation")
@Service
public class TbkServiceImpl extends SqlMapClientDaoSupport implements TbkService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    private static Logger LOGGER = Logger.getLogger(TbkServiceImpl.class);

    /**
     * 广告位
     *
     * @param type
     * @return
     */
    @Override
    public List<Map<String, Object>> advert(String type) {
        List<Map<String, Object>> list = new ArrayList();
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("type", type);
        try {
            list = this.getSqlMapClientTemplate().queryForList("ek_tbk.advert", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("TbkServiceImpl.advert failed, e : " + e.getMessage());
        }
        return list;
    }

    /**
     * 分类列表
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> cateList() {
        List<Map<String, Object>> list = new ArrayList();
        try {
            list = this.getSqlMapClientTemplate().queryForList("ek_tbk.cateList");
        } catch (DataAccessException e) {
            LOGGER.error("TbkServiceImpl.cateList failed, e : " + e.getMessage());
        }
        return list;
    }

    /**
     * 首页主题列表
     *
     * @param day
     * @return
     */
    @Override
    public List<Map<String, Object>> theme(String day) {
        List<Map<String, Object>> list = new ArrayList();
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("date", day);
        try {
            list = this.getSqlMapClientTemplate().queryForList("ek_tbk.themeList", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("TbkServiceImpl.theme failed, e : " + e.getMessage());
        }
        return list;
    }

    /**
     * 首页商品列表
     *
     * @param day
     * @return
     */
    @Override
    public List<Map<String, Object>> product(String day) {
        List<Map<String, Object>> list = new ArrayList();
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("date", day);
        try {
            list = this.getSqlMapClientTemplate().queryForList("ek_tbk.productList", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("TbkServiceImpl._product failed, e : " + e.getMessage());
        }
        return list;
    }

    /**
     * 主题商品详情
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> themeProductDetail(String aid) {
        List<Map<String, Object>> list = new ArrayList();
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("aid", aid);
        try {
            list = this.getSqlMapClientTemplate().queryForList("ek_tbk.themeProductDetail", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("TbkServiceImpl.themeProductDetail failed, e : " + e.getMessage());
        }
        return list;
    }

    /**
     * 商品列表
     *
     * @param aid
     * @return
     */
    @Override
    public List<Map<String, Object>> productList(String aid) {
        List<Map<String, Object>> list = new ArrayList();
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("aid", aid);
        try {
            list = this.getSqlMapClientTemplate().queryForList("ek_tbk.products", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("TbkServiceImpl.productList failed, e : " + e.getMessage());
        }
        return list;
    }

    /**
     * 添加主题收藏
     *
     * @param aid
     * @param uid
     */
    @Override
    public void collect(String aid, String uid) {
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("aid", aid);
        paramMap.put("uid", uid);
        try {
            this.getSqlMapClientTemplate().insert("ek_tbk.collect", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("TbkServiceImpl.collect failed, e : " + e.getMessage());
        }
    }

    /**
     * 查询是否收藏牧歌主题
     *
     * @param aid
     * @param uid
     * @return
     */
    @Override
    public List<Map<String, Object>> collectList(String aid, String uid) {
        List<Map<String, Object>> list = new ArrayList();
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("aid", aid);
        paramMap.put("uid", uid);
        try {
            list = this.getSqlMapClientTemplate().queryForList("ek_tbk.collectList", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("TbkServiceImpl.collectList failed, e : " + e.getMessage());
        }
        return list;
    }

    /**
     * 收藏列表查询
     *
     * @param uid
     * @return
     */
    @Override
    public List<Map<String, Object>> collects(String uid) {
        List<Map<String, Object>> list = new ArrayList();
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("uid", uid);
        try {
            list = this.getSqlMapClientTemplate().queryForList("ek_tbk.collects", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("TbkServiceImpl.collects failed, e : " + e.getMessage());
        }
        return list;
    }

    /**
     * 主题详情
     *
     * @param aid
     * @return
     */
    @Override
    public List<Map<String, Object>> themeDetail(String aid) {
        List<Map<String, Object>> list = new ArrayList();
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("aid", aid);
        try {
            list = this.getSqlMapClientTemplate().queryForList("ek_tbk.themeDetail", paramMap);
        } catch (DataAccessException e) {
            LOGGER.error("TbkServiceImpl.themeDetail failed, e : " + e.getMessage());
        }
        return list;
    }
}
