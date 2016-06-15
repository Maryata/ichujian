package com.org.mokey.basedata.sysinfo.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.org.mokey.basedata.sysinfo.service.TestingMachineManagementService;
import com.org.mokey.common.AbstractAction;
/**
 * 测试机管理
 * @author vpc
 *
 */
public class TestingMachineManagementAction extends AbstractAction{
	private static final long serialVersionUID = -5391158956287432117L;
	private TestingMachineManagementService testingMachineManagementService;
	
	public String ajaxTestingMachineList(){
		log.info("into ajaxTestingMachineList");
		Map<String, Object> retmap=new HashMap<String, Object>();
		String phonename=getParameter("phonename");
		String username=getParameter("username");
		int start=getParameter2Int("start",0);
		int limit=getParameter2Int("limit",10);
		try {
			retmap=testingMachineManagementService.getTestingMachineList( phonename, username, start, limit );
			retmap.put("status", "Y");
		} catch (Exception e) {
			retmap.put("status", "N");
			log.error("ajaxTestingMachineList failed",e);
		}
		try {
			getResponse().setContentType( "text/html;charset=UTF-8" );
			getResponse().getWriter().write( JSONObject.fromObject(retmap).toString() );
		} catch ( IOException e ) {
			log.error( "ajaxTestingMachineList failed", e );
		}
		
		return NONE;
	}

	public TestingMachineManagementService getTestingMachineManagementService() {
		return testingMachineManagementService;
	}

	public void setTestingMachineManagementService(
			TestingMachineManagementService testingMachineManagementService) {
		this.testingMachineManagementService = testingMachineManagementService;
	}

}
