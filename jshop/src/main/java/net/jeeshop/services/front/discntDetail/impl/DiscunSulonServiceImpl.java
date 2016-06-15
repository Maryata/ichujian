package net.jeeshop.services.front.discntDetail.impl;

import java.util.List;

import javax.annotation.Resource;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.front.discntDetail.DiscunSulonService;
import net.jeeshop.services.front.discntDetail.bean.DiscntDetail;
import net.jeeshop.services.front.discntDetail.dao.DiscunSolunDao;

import org.springframework.stereotype.Service;

@Service("discunSulonServiceFront")
public class DiscunSulonServiceImpl extends ServersManager<DiscntDetail ,DiscunSolunDao> implements DiscunSulonService{
	@Resource(name = "discunSolinDaoFront")
	@Override
	public void setDao(DiscunSolunDao dao) {
		 this.dao = dao;
	}
	@Override
	public List<DiscntDetail> selectAllMessage() {
		// TODO Auto-generated method stub
		return dao.selectAllMessage();
	}

	

    
}
