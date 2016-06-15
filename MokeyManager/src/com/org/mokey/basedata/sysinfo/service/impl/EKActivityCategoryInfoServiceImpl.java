package com.org.mokey.basedata.sysinfo.service.impl;

import com.org.mokey.basedata.sysinfo.dao.EKActivityCategoryInfoDao;
import com.org.mokey.basedata.sysinfo.service.EKActivityCategoryInfoService;
import com.org.mokey.util.StrUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by vpc on 2016/3/2.
 * e键 ：活动分类
 */
public class EKActivityCategoryInfoServiceImpl implements EKActivityCategoryInfoService {
    private EKActivityCategoryInfoDao ekActivityCategoryInfoDao;

    public EKActivityCategoryInfoDao getEkActivityCategoryInfoDao() {
        return ekActivityCategoryInfoDao;
    }

    public void setEkActivityCategoryInfoDao(EKActivityCategoryInfoDao ekActivityCategoryInfoDao) {
        this.ekActivityCategoryInfoDao = ekActivityCategoryInfoDao;
    }

    @Override
    public Map<String, Object> ekActivityCategoryInfoList(int page) {
        return ekActivityCategoryInfoDao.ekActivityCategoryInfoList(page);
    }

    @Override
    public void addInfo(String id, String name, String logo, String type, String headLineImg) {
        ekActivityCategoryInfoDao.addInfo(id, name, logo, type, headLineImg);
    }

    @Override
    public void toDel(String id) {
        ekActivityCategoryInfoDao.toDel(id);
        //删除活动头条表中当前分类
        ekActivityCategoryInfoDao.removeHeadLine(id);
        //删除首页分类表中当前分类
        ekActivityCategoryInfoDao.removeActivityIndexCate(id);
        // 删除“分类活动中间表”T_EK_ACTIVITY_CATEGORY_ACTIVIT表中当前分类的所有数据
        ekActivityCategoryInfoDao.removeActByCid(id);
    }

    @Override
    public void updateInfo(String id, String name, String logo, String type, String headLineImg) {
        ekActivityCategoryInfoDao.updateInfo(id, name, logo, type, headLineImg);
    }


    @Override

    public Map<String, Object> getAllActivityCategoryInfo(int page, String cid, String title, String ccid, String imgType) {

        return ekActivityCategoryInfoDao.getAllActivityCategoryInfo(page,cid,title,ccid,imgType);
    }

    @Override
    public List<Map<String, Object>> activityCategoryList(String type) {
        return ekActivityCategoryInfoDao.activityCategoryList(type);
    }

    @Override
    public Map<String, Object> getCurrActivityCaInfo(int page, String cid, String title) {
        return ekActivityCategoryInfoDao.getCurrActivityCaInfo(page, cid, title);
    }

    @Override
    public void handleActivity(String cid, String aid, String removeGid, String order, String tagid, String title, String subtitle) {
        String[] aids = aid.split(",");
        String[] removeGids = removeGid.split(",");
        String[] orders = order.split(",");
        String[] tagids = tagid.split(",");
        String[] titles = title.split(",");
        String[] subtitles = subtitle.split(",");
        if (StrUtils.isNotEmpty(orders)) {
            for (int i = 0; i < orders.length; i++) {
                if ("n".equals(orders[i])) {
                    orders[i] = "";
                }
            }
        }
        if (tagids.length==1 && tagids[0].equals("")) {
            tagids = new String[aids.length];
            for (int i = 0; i < tagids.length; i++) {
                tagids[i] = "";
            }
        } else {
            for (int i = 0; i < tagids.length; i++) {
                if ("n".equals(tagids[i])) {
                    tagids[i] = "";
                }
            }
        }
        if (StrUtils.isNotEmpty(titles)) {
            for (int i = 0; i < titles.length; i++) {
                if ("n".equals(titles[i])) {
                    titles[i] = "";
                }
            }
        }
        if (StrUtils.isNotEmpty(subtitles)) {
            for (int i = 0; i < subtitles.length; i++) {
                if ("n".equals(subtitles[i])) {
                    subtitles[i] = "";
                }
            }
        }
        // 添加活动到当前分类
        if (null != aids && aids.length > 0) {
            for (int i = 0; i < aids.length; i++) {
                int count = 0;
                count = ekActivityCategoryInfoDao.isExitActivity(cid, aids[i]);
                if (count == 0) {
                    ekActivityCategoryInfoDao.addIndex(cid, aids[i], orders[i], tagids[i], titles[i], subtitles[i]);
                } else {
                    ekActivityCategoryInfoDao.updateIndex(cid, aids[i], orders[i], tagids[i], titles[i], subtitles[i]);
                }
            }
        }
        // 移除出分类
        if (null != removeGids && removeGids.length > 0) {
            for (String id : removeGids) {
                if (StrUtils.isEmpty(id)) {
                    continue;
                }
                ekActivityCategoryInfoDao.removeIndex(cid, id);
            }
        }


    }

    @Override
    public Map<String, Object> ekActivityCategoryList(int page, List ids) {
        return ekActivityCategoryInfoDao.ekActivityCategoryList(page, ids);
    }

    @Override
    public Map<String, Object> ekActivityInfoList(int page, List ids) {
        return ekActivityCategoryInfoDao.ekActivityInfoList(page, ids);
    }



}
