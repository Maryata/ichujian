package com.sys.service;

import java.util.List;

public interface IndustryService {

	public List getIndustrType(String cityid);
	
	public List getBrand();
	
	public int getIndustry(String cityid);
}
