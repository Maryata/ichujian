package com.org.mokey.basedata.sysinfo.service.impl;

import java.util.List;
import java.util.Map;

import com.org.mokey.basedata.sysinfo.dao.PhoneBrandDao;
import com.org.mokey.basedata.sysinfo.service.PhoneBrandService;
import com.org.mokey.util.StrUtils;

public class PhoneBrandServiceImpl implements PhoneBrandService {
	
	private PhoneBrandDao phoneBrandDao;

	public PhoneBrandDao getPhoneBrandDao() {
		return phoneBrandDao;
	}

	public void setPhoneBrandDao(PhoneBrandDao phoneBrandDao) {
		this.phoneBrandDao = phoneBrandDao;
	}

	@Override
	public void deleteBrandInfo(String c_id) {
		phoneBrandDao.deleteBrandInfo(c_id);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String saveBrandInfo(Map<String, Object> saveMap) {
		List<String> c_models = (List) saveMap.get("C_MODEL_CODE");
		saveMap.remove("C_MODEL_CODE");
		
		String brandId =  phoneBrandDao.saveBrandInfo(saveMap);
		//保存手机型号
		if(saveMap.containsKey("C_ID")){
			if(StrUtils.isNotEmpty(c_models)){
				for(int i=0;i<c_models.size();i++){
					phoneBrandDao.saveModel(brandId,c_models.get(i));
				}
			}
		}else{
			if(StrUtils.isNotEmpty(c_models)){
				List<String> modelList = phoneBrandDao.getModelByBrandId(brandId);
				//查看不在新列表中的;进行删除
				if(StrUtils.isNotEmpty(modelList)){
					for(String code : modelList){
						if(StrUtils.isEmpty(code)){
							continue;
						}
						boolean isHas = false;
						for(int i=0;i<c_models.size();i++){
							if(StrUtils.isEmpty(c_models.get(i))){
								continue;
							}
							if(c_models.get(i).equals(code)){
								isHas = true;
								break;
							}
						}
						if(!isHas){
							phoneBrandDao.deleteModel(brandId, code);
							//modelList.remove(code);
						}
					}
				}
				
				//保存不存在的记录
				for(int i=0;i<c_models.size();i++){
					if(StrUtils.isEmpty(c_models.get(i))){
						continue;
					}
					if(!modelList.contains(c_models.get(i))){
						phoneBrandDao.saveModel(brandId, c_models.get(i));
					}
				}
			}else{
				phoneBrandDao.deleteModel(brandId, null);
			}
		}
		return brandId;
	}

	@Override
	public Map<String, Object> getBrandListMap(String c_name, int start,
			int limit) {
		return phoneBrandDao.getBrandListMap(c_name,start,limit);
	}

}
