package com.org.mokey.util;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;

import org.apache.log4j.Logger;
import org.jinterop.dcom.common.IJIAuthInfo;
import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.common.JISystem;
import org.jinterop.dcom.core.IJIComObject;
import org.jinterop.dcom.core.JIArray;
import org.jinterop.dcom.core.JIComServer;
import org.jinterop.dcom.core.JIProgId;
import org.jinterop.dcom.core.JISession;
import org.jinterop.dcom.core.JIString;
import org.jinterop.dcom.core.JIVariant;
import org.jinterop.dcom.impls.JIObjectFactory;
import org.jinterop.dcom.impls.automation.IJIDispatch;
import org.jinterop.dcom.impls.automation.IJIEnumVariant;

/**
 * Created with IntelliJ IDEA.
 * User: noah
 * Date: 8/16/12
 * Time: 8:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class WmiService {

    private JIComServer m_ComStub = null;
    private IJIComObject m_ComObject = null;
    private IJIDispatch m_Dispatch = null;
    private String m_Address = null;
    private JISession m_Session = null;
    private IJIDispatch m_WbemServices = null;

    private static final String WMI_CLSID = "76A6415B-CB41-11d1-8B02-00600806D9B6";
    private static final String WMI_PROGID = "WbemScripting.SWbemLocator";

    private Logger logger = Logger.getLogger(WmiService.class);


    public WmiService(String address) {
        JISystem.setAutoRegisteration(true);
        JISystem.getLogger().setLevel(Level.OFF);
        m_Address = address;
    }

    public void query(String strQuery) {

        System.out.println("query:" + strQuery);

        JIVariant results[] = new JIVariant[0];
        try {
            results = m_WbemServices.callMethodA("ExecQuery", new Object[]{new JIString(strQuery), JIVariant.OPTIONAL_PARAM(), JIVariant.OPTIONAL_PARAM(), JIVariant.OPTIONAL_PARAM()});
            IJIDispatch wOSd = (IJIDispatch) JIObjectFactory.narrowObject((results[0]).getObjectAsComObject());

            int count = wOSd.get("Count").getObjectAsInt();

            IJIComObject enumComObject = wOSd.get("_NewEnum").getObjectAsComObject();
            IJIEnumVariant enumVariant = (IJIEnumVariant) JIObjectFactory.narrowObject(enumComObject.queryInterface(IJIEnumVariant.IID));

            IJIDispatch wbemObject_dispatch = null;

            for (int c = 0; c < count; c++) {

                Object[] values = enumVariant.next(1);
                JIArray array = (JIArray) values[0];
                Object[] arrayObj = (Object[]) array.getArrayInstance();
                for (int j = 0; j < arrayObj.length; j++) {
                    wbemObject_dispatch = (IJIDispatch) JIObjectFactory.narrowObject(((JIVariant) arrayObj[j]).getObjectAsComObject());
                }
                
                JIVariant [] jiv = wbemObject_dispatch.callMethodA("GetObjectText_", new Object[]{1});

                String str = (wbemObject_dispatch.callMethodA("GetObjectText_", new Object[]{1}))[0].getObjectAsString2();
                System.out.println("------------------(" + c + "):");
                System.out.println(str);
                System.out.println();
            }


        } catch (JIException e) {
            e.printStackTrace();
        }
    }

    public void connect(final String domain, final String username, final String password) {
        try {

            m_Session = JISession.createSession(domain, username, password);
            m_Session.useSessionSecurity(true);
            m_Session.setGlobalSocketTimeout(5000);

            m_ComStub = new JIComServer(JIProgId.valueOf(WMI_PROGID), m_Address, m_Session);

            IJIComObject unknown = m_ComStub.createInstance();
            m_ComObject = unknown.queryInterface(WMI_CLSID);

            m_Dispatch = (IJIDispatch) JIObjectFactory.narrowObject(m_ComObject.queryInterface(IJIDispatch.IID));
            JIVariant results[] = m_Dispatch.callMethodA(
                    "ConnectServer",
                    new Object[]{
                            new JIString(m_Address),
                            JIVariant.OPTIONAL_PARAM(),
                            JIVariant.OPTIONAL_PARAM(),
                            JIVariant.OPTIONAL_PARAM(),
                            JIVariant.OPTIONAL_PARAM(),
                            JIVariant.OPTIONAL_PARAM(),
                            0,
                            JIVariant.OPTIONAL_PARAM()
                    }
            );

            m_WbemServices = (IJIDispatch) JIObjectFactory.narrowObject((results[0]).getObjectAsComObject());

        } catch (JIException e) {
            e.printStackTrace();
            if (m_Session != null) {
                try {
                    JISession.destroySession(m_Session);
                } catch (JIException e1) {
                    logger.error(e.getMessage(), e);
                }
            }
        } catch (UnknownHostException e) {
            if (m_Session != null) {
                try {
                    JISession.destroySession(m_Session);
                } catch (JIException e1) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    public void disconnect() {
        try {
            JISession.destroySession(m_Session);
        } catch (JIException e) {
            logger.error(e.getMessage(), e);
        }
    }


    public static void main3(String[] args) {

        WmiService wmiService = new WmiService("192.168.0.193");

        //域（一般为空字符），用户名，密码
        wmiService.connect("", "administrator", "giles");

        //系统信息
        //wmiService.query("SELECT * FROM Win32_ComputerSystem");

        //CPU信息
        //wmiService.query("SELECT * FROM Win32_PerfFormattedData_PerfOS_Processor WHERE Name != '_Total'");

        //内存信息
        //wmiService.query("SELECT * FROM Win32_PerfFormattedData_PerfOS_Memory");

        //磁盘信息
        //wmiService.query("SELECT * FROM Win32_PerfRawData_PerfDisk_PhysicalDisk Where Name != '_Total'");
        
        
        wmiService.query("SELECT * FROM Win32_PerfRawData_Tcpip_NetworkInterface");
        
        //wmiService.query("SELECT * FROM Win32_NetworkAdapterConfiguration");

        wmiService.disconnect();
    }
    
	public static void main(String[] args) {
		String address="192.168.1.25";  
		address="127.0.0.1";  
		JISession session=null;
		try{
			JISystem.getLogger().setLevel(Level.OFF);
			JISystem.setInBuiltLogHandler(false);
			JISystem.setAutoRegisteration(true);

			/** 1.连接到 WMI 服务 * */  
			session = JISession.createSession("","administrator","giles"); 
			
			//session = JISession.createSession();
			session.useSessionSecurity(true);
			session.setGlobalSocketTimeout(5000); // session超时时间
			
		    JIComServer server = null;
		    
		    //server = new JIComServer(JIProgId.valueOf("WbemScripting.SWbemLocator"), session);
		    
		    server = new JIComServer(JIProgId.valueOf("WbemScripting.SWbemLocator"),  address, session);  
		    IJIDispatch dispatch = (IJIDispatch) JIObjectFactory.narrowObject(server.createInstance()  
		            .queryInterface(IJIDispatch.IID));  
		    // params数组对应的 WMI对象方法参数，JIVariant.OPTIONAL_PARAM()代表可选参数
		    Object[] params = new Object[] {  
		            new JIString(address),  
		            new JIString("ROOT\\CIMV2"),  
		            JIVariant.OPTIONAL_PARAM(),  
		            JIVariant.OPTIONAL_PARAM(),  
		            JIVariant.OPTIONAL_PARAM(),  
		            JIVariant.OPTIONAL_PARAM(),  
		            new Integer(0), JIVariant.OPTIONAL_PARAM() };  
		    JIVariant[] results = dispatch.callMethodA("ConnectServer", params);  
		    IJIDispatch wbemServices = (IJIDispatch) JIObjectFactory  
		                    .narrowObject(results[0].getObjectAsComObject());  
		      
		    /** 2.检索 WMI 托管资源的实例* */  
		    JIVariant[] results2= wbemServices.callMethodA("InstancesOf", new Object[] {  
		            new JIString("Win32_Processor"), new Integer(0), JIVariant.OPTIONAL_PARAM()  
		     });  
		       IJIDispatch wbemObjectSet_dispatch = (IJIDispatch)JIObjectFactory.narrowObject(results2[0].getObjectAsComObject());  
		         
		       /** 3.显示 WMI 托管资源的属性,得到_NewEnum属性迭代元素* */  
		       JIVariant variant_enu = wbemObjectSet_dispatch.get("_NewEnum");  
		    IJIComObject enumComObject = variant_enu.getObjectAsComObject();  
		    IJIEnumVariant enumVariant = (IJIEnumVariant) JIObjectFactory  
		            .narrowObject(enumComObject  
		                    .queryInterface(IJIEnumVariant.IID));  
		    Object[] elements = enumVariant.next(1);  
		    JIArray aJIArray = (JIArray) elements[0];  
		    JIVariant[] array = (JIVariant[]) aJIArray.getArrayInstance();  
		      
		    for (JIVariant variant : array) {  
		        IJIDispatch wbemObjectDispatch = (IJIDispatch) JIObjectFactory  
		                .narrowObject(variant.getObjectAsComObject());  
		        JIVariant[] v = wbemObjectDispatch.callMethodA(  
		                "GetObjectText_", new Object[] { 1 });  
		        System.out.println(v[0].getObjectAsString().getString());  
		    }  
		         
		} catch (UnknownHostException e) {  
		    e.printStackTrace();  
		} catch (JIException e) {  
		    e.printStackTrace();  
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{  
		    try {  
		        JISession.destroySession(session);  
		    } catch (JIException e) {  
		        e.printStackTrace();  
		    }  
		}  
	}
}