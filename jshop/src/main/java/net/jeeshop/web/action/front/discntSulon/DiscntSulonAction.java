package net.jeeshop.web.action.front.discntSulon;

import java.util.ArrayList;
import java.util.List;

import net.jeeshop.core.Services;
import net.jeeshop.services.front.discntDetail.DiscunSulonService;
import net.jeeshop.services.front.discntDetail.bean.DiscntDetail;
import net.jeeshop.web.action.BaseController;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller("frontDiscntSulonActionController")
@RequestMapping("/dis")
public class DiscntSulonAction extends BaseController<DiscntDetail>{
	// 前台
	@Autowired
	private DiscunSulonService discunSulonService;
	
	public DiscunSulonService getDiscunSulonService() {
		return discunSulonService;
	}

	public void setDiscunSulonService(DiscunSulonService discunSulonService) {
		this.discunSulonService = discunSulonService;
	}

	@Override
	public Services<DiscntDetail> getService() {
		// TODO Auto-generated method stub
		return null;
	}

	@RequestMapping(value = "selectDis", method = RequestMethod.POST)
	@ResponseBody
	public <discunSulonService> String selectDis(){
		int data=10;
		List<DiscntDetail> List=discunSulonService.selectAllMessage();
		List<DiscntDetail> disList=new ArrayList<DiscntDetail>();
		for(int i=0;i<List.size();i++){
			DiscntDetail discntDetail=new DiscntDetail();
			DiscntDetail d=List.get(i);
			discntDetail.setMaxVal(d.getMaxVal());
			discntDetail.setMinVal(d.getMinVal());
			discntDetail.setRate(String.valueOf(Double.valueOf(d.getRate())*data));
			disList.add(discntDetail);
		}
		JSONArray jsons = new JSONArray(); 
		for(int i=0;i<disList.size();i++){
			JSONObject jsonObject = new JSONObject(); 
			jsonObject.put("dis", disList.get(i));
			jsons.add(jsonObject); 
		}
		return jsons.toString();
	}
}
