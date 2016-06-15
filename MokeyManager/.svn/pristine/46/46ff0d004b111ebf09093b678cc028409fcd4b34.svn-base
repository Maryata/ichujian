package com.org.mokey.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
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
 * # WMI 获取服务器信息
#
WMI_address =192.168.1.25
# if domain is not null,set ''
WMI_domain =
#set wmi system login user
WMI_username =administrator
#set wmi system login userPass
WMI_password =giles
 * @author Administrator
 *
 */
public class SystemUtil {
	
	/** log available to subclasses */
	private static final Logger log = Logger.getLogger(SystemUtil.class);
    private static final String WMI_CLSID = "76A6415B-CB41-11d1-8B02-00600806D9B6";
    private static final String WMI_PROGID = "WbemScripting.SWbemLocator";
	//-------------------------------
	private String _address;
	private String _domain;
	private String _username;
	private String _password;
	private String _deskPath;
	//-------------------------------
	
	private JIComServer m_ComStub = null;
    private IJIComObject m_ComObject = null;
    private IJIDispatch m_Dispatch = null;
    private JISession m_Session = null;
    private IJIDispatch m_WbemServices = null;
    
    private String errorMsg;
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args){
		log.debug("init");
		long stime = System.currentTimeMillis();
		//系统运行时长
		//getSystemRunTime();
		//系统启动时间
		//getSystemStartUpTime();
		//getThisDiskUse();
		SystemUtil st = new SystemUtil();
		
		st.getThisDiskUse();
		/*try {
			st.connect();
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(st.getErrorMsg());
		}
		
		Map netWorkData = st.getNetWork();
		log.debug(""+netWorkData);
		//	List<Map> networkAdapterS = st.getWMI("Select * from Win32_NetworkAdapterConfiguration Where IPEnabled='TRUE'",new String []{"Description","Caption","DHCPServer","DNSHostName","IPAddress"} );
		
		st.disconnect();
		long etime = System.currentTimeMillis();
        System.out.println("time:"+ (etime - stime));
*/	}
	
	public Map<String,Long> getNetWork(){
		Map<String,Long> ret = new HashMap<String,Long>();
		try {
			boolean isHas = false;
			List<Map> networkAdapterS;
			networkAdapterS = getWMI("Select * from Win32_NetworkAdapterConfiguration Where IPEnabled='TRUE'",new String []{"Description","Caption","DHCPServer","DNSHostName","IPAddress"} );
			if( networkAdapterS!=null ){
				//
				List<Map> netPerfRawDatas = getWMI("Select * from Win32_PerfRawData_Tcpip_NetworkInterface",new String []{"Name", "BytesSentPersec", "BytesTotalPersec", "BytesReceivedPersec", "PacketsReceivedErrors", "PacketsReceivedDiscarded"} );
				Long bytesSentPersec = 0L;
				Long bytesReceivedPersec = 0L;
				Long bytesTotalPersec = 0L;
			
				for(Map adapter : networkAdapterS){
					for(Map perData : netPerfRawDatas){
						//处理数据
						if(adapter.get("Description").equals(perData.get("Name"))){
							//log.debug("---:"+perData);
							bytesSentPersec += Long.valueOf(perData.get("BytesSentPersec").toString()) ;
							bytesReceivedPersec += Long.valueOf(perData.get("BytesReceivedPersec").toString()) ;
							//bytesTotalPersec += Long.valueOf(perData.get("BytesTotalPersec").toString()) ;
							//ret = perData;
							isHas = true;
							break;
						}
					}
				}
				ret.put("sendRate", bytesSentPersec);
				ret.put("receiveRate", bytesReceivedPersec);
				//ret.put("totalPersec", bytesTotalPersec);
			}
			if(!isHas){
				ret.put("22", 0L);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return ret;
	}
	
	public SystemUtil() {
		init ();
	}
	
	/**
	 * wmi 初始化参数信息
	 */
	private void init (){
		try {
			ResourceBundle loadMedium = ResourceBundle.getBundle("SysParams");
			//_address = loadMedium.getString("WMI_address");
			_address = Ipconfig.getRealIp();
			_domain = loadMedium.getString("WMI_domain");
			_username = loadMedium.getString("WMI_username");
			_password = loadMedium.getString("WMI_password");
			
			_deskPath = loadMedium.getString("_desk_path");
		}catch(Exception e){
			log.debug("SystemUtil.init failed ",e);
		}
	}
	
	public List getWMI(String query,String [] retParam) throws Exception {
       // try {
        	List ret = new ArrayList();
        	 
        	JIVariant results[] = new JIVariant[0];
        	if(m_WbemServices==null){
        		log.debug("m_WbemServices is null, so ret connects");
        		this.connect();
        	}
            results = m_WbemServices.callMethodA("ExecQuery", new Object[]{new JIString(query), JIVariant.OPTIONAL_PARAM(), JIVariant.OPTIONAL_PARAM(), JIVariant.OPTIONAL_PARAM()});
            /*results = m_WbemServices.callMethodA("InstancesOf", new Object[] {    
                    new JIString(query), new Integer(0), JIVariant.OPTIONAL_PARAM()    
            });*/
            IJIDispatch wOSd = (IJIDispatch) JIObjectFactory.narrowObject((results[0]).getObjectAsComObject());

            int count = wOSd.get("Count").getObjectAsInt();

            IJIComObject enumComObject = wOSd.get("_NewEnum").getObjectAsComObject();
            IJIEnumVariant enumVariant = (IJIEnumVariant) JIObjectFactory.narrowObject(enumComObject.queryInterface(IJIEnumVariant.IID));

            IJIDispatch wbemObject_dispatch = null;

            for (int c = 0; c < count; c++) {
            	Map retMap = new HashMap();
                Object[] values = enumVariant.next(1);
                JIArray array = (JIArray) values[0];
               //log.debug("array:"+array);
                Object[] arrayObj = (Object[]) array.getArrayInstance();
                for(Object obj : arrayObj){
                	wbemObject_dispatch = (IJIDispatch) JIObjectFactory.narrowObject(((JIVariant) obj).getObjectAsComObject());
                }
                String str = (wbemObject_dispatch.callMethodA("GetObjectText_", new Object[]{1}))[0].getObjectAsString().getString();
                str = str.substring(str.indexOf("{")+1, str.lastIndexOf("}"));
                //log.debug("str:"+str);
                Map dataMap = StrUtils.transStringToMap(str, "	");
                //log.debug("dataMap:"+dataMap);
                Iterator<String> it = dataMap.keySet().iterator();
        		while (it.hasNext()) {
        			String key = it.next();
        			if(dataMap.get(key)==null){
    					continue;
    				}
        			for(String retKey : retParam){
        				if(retKey.equals(key.trim() )  ){
        					String value = dataMap.get(key)+"";
                			if(value.lastIndexOf("\";")>-1){
                				value = value.substring(value.indexOf("\"")+1,value.lastIndexOf("\""));
                			}
        					retMap.put(retKey,value );
        				}
        			}
        		}
        		/* String strArr[] = str.split("	");
                //System.out.println("------------------(" + c + "):");
               //System.out.println(str);
                for(String item : strArr){
                	if(item==null || item.trim().length()<1 || item.lastIndexOf(";")==-1){
                		continue;
                	}
                	item = item.substring(0,item.lastIndexOf(";"));
                	//System.out.println("---["+item+"] ");
            		String keyVal [] = item.split("=");
            		for(String retKey : retParam){
            			if(retKey.equals(keyVal[0].trim() )  ){
            				//log.debug(retKey+" --" + keyVal[1]);
            				retMap.put(retKey, keyVal[1]);
            			}
            		}
                }*/
                ret.add(retMap);
            }
            //log.debug("ret:"+ret);
            return ret;
        /*} catch (JIException e) {
        	log.error("query failed",e);
        	return null;
        }*/
    }
	

    public void connect() throws Exception {
        try {
        	this.setErrorMsg(null);
        	JISystem.setAutoRegisteration(true);
            JISystem.getLogger().setLevel(Level.OFF);
            log.debug("_address:["+_address+"],_domain:["+_domain+"],_username:["+_username+"],_password:["+_password+"]");
             
            m_Session = JISession.createSession(_domain, _username, _password);
            m_Session.useSessionSecurity(true);
            m_Session.setGlobalSocketTimeout(5000);

            m_ComStub = new JIComServer(JIProgId.valueOf(WMI_PROGID), _address, m_Session);

            IJIComObject unknown = m_ComStub.createInstance();
            m_ComObject = unknown.queryInterface(WMI_CLSID);

            m_Dispatch = (IJIDispatch) JIObjectFactory.narrowObject(m_ComObject.queryInterface(IJIDispatch.IID));
            JIVariant results[] = m_Dispatch.callMethodA("ConnectServer",
                    new Object[]{
                            new JIString(_address),
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
           log.error("connect failed",e);
           this.setErrorMsg(e.getMessage());
           if (m_Session != null) {
               try {
                   JISession.destroySession(m_Session);
                   m_WbemServices=null;
               } catch (JIException e1) {
                   log.error(e.getMessage(), e);
               }
           }
           //Remote Registry
           if(e.getMessage().equals("Message not found for errorCode: 0xC0000034") ){
        	   this.setErrorMsg("访问WMI服务器的[Remote Registry]服务未启用");
        	   log.debug(this.getErrorMsg());
        	   throw new Exception(this.getErrorMsg());
           }else if(e.getMessage().equals("The RPC server is unavailable. Please check if the COM server is up and running and that route to the COM Server is accessible (A simple \"Ping\" to the Server machine would do). Also please confirm if the Windows Firewall is not blocking DCOM access. [0x800706BA]") ){
        	   this.setErrorMsg("RPC服务器不可用。解决办法：关闭防火墙  或者  开启防火墙入站规则：TCP 协议;本地RPC动态端口;远程特定135端口。");
        	   log.debug(this.getErrorMsg());
        	   throw new Exception(this.getErrorMsg());
           }else if(e.getMessage().equals("The attempted logon is invalid. This is either due to a bad username or authentication information. [0xC000006D]") ){
        	   this.setErrorMsg("WMI服务器 登录失败：未知的用户名或密码错误");
        	   log.debug(this.getErrorMsg());
        	   throw new Exception(this.getErrorMsg());
           }else if(e.getMessage().equals("Message not found for errorCode: 0xC0000001")){
        	   this.setErrorMsg("配置的IP地址无法访问，尝试设置本机IP");
        	   _address = Ipconfig.getRealIp();
        	   log.debug(this.getErrorMsg());
        	   throw new Exception(this.getErrorMsg());
           }
        } catch (UnknownHostException e) {
        	log.error("connect failed",e);
        	this.setErrorMsg(e.getMessage());
            if (m_Session != null) {
                try {
                    JISession.destroySession(m_Session);
                } catch (JIException e1) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    public void disconnect() {
        try {
            JISession.destroySession(m_Session);
            m_WbemServices=null;
        } catch (JIException e) {
            log.error(e.getMessage(), e);
        }
    }
	
    
    
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**
	 * 获取操作系统运行时长
	 * @return
	 * eg : 0天19时22分
	 */
	public String getSystemRunTime() {
		try {
			//获取系统启动时间
			Process process = Runtime.getRuntime().exec("net statistics workstation");
	        String startUpTime = "";
	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(),"gbk"));
	        int i = 0;
	        String timeWith = "";
	        while ((timeWith = bufferedReader.readLine()) != null) {
	            if (i == 3) {
	                startUpTime = timeWith;
	            }
	            i++;
	        }
	        process.waitFor();
	        startUpTime = startUpTime.substring(8, startUpTime.length());
	        //log.debug("startUpTime:"+ startUpTime );
	        //-----------获取时间-------------
	        if(startUpTime!=null&& startUpTime.length()>0){
	        	String [] startUpTimeArr = startUpTime.split(" ");
	        	String startUpD = startUpTimeArr[0];
	        	String startUpT = startUpTimeArr[1];
	        	String [] startUpDArr = startUpD.split("/");
	        	String [] startUpTArr = startUpT.split(":");
	        	
	        	java.util.Calendar c = java.util.Calendar.getInstance();
		        c.set(java.util.Calendar.YEAR, new Integer(startUpDArr[0]).intValue());
				c.set(java.util.Calendar.MONTH, new Integer(startUpDArr[1]).intValue() - 1);
				c.set(java.util.Calendar.DATE, new Integer(startUpDArr[2]).intValue());
				
				c.set(java.util.Calendar.HOUR_OF_DAY, new Integer(startUpTArr[0]).intValue());
				c.set(java.util.Calendar.MINUTE, new Integer(startUpTArr[0]).intValue());
				c.set(java.util.Calendar.SECOND, new Integer(startUpTArr[0]).intValue());
				
				long useTime = c.getTime().getTime();
				useTime =  (System.currentTimeMillis()-useTime)/1000;
				String rt = "";
				long h = 0;
				long m = 0;
				long temp = useTime%3600;
				if(useTime>3600){
					h= useTime/3600;
					if(temp!=0){
						if(temp>60){
							m = temp/60;
						}
					}
		    	}else{
			        m = useTime/60;
			    }
				if(h>=24){
					rt = (h/24)+"天  "+(h%24 )+"小时";
				}else{
					rt = h+"小时";
				}
				rt+= " "+m+"分";
				
				startUpTime = rt;
				//log.debug("use time:"+ startUpTime );
	        }
	        //------------------------
	        return startUpTime;
		} catch (Exception e) {
			log.error("getSystemRunTime failed ",e);
			return null;
		}
	}
	
	/**
	 * 获取系统启动时间
	 * @return
	 * eg : 2013/12/6 21:25:14
	 */
	public static String getSystemStartUpTime() {
		try {
			//获取系统启动时间
			Process process = Runtime.getRuntime().exec("net statistics workstation");
	        String startUpTime = "";
	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(),"gbk"));
	        int i = 0;
	        String timeWith = "";
	        while ((timeWith = bufferedReader.readLine()) != null) {
	            if (i == 3) {
	                startUpTime = timeWith;
	            }
	            i++;
	        }
	        process.waitFor();
	        startUpTime = startUpTime.substring(8, startUpTime.length());
	        log.debug("startUpTime:"+ startUpTime );
	        return startUpTime;
		} catch (Exception e) {
			log.error("getSystemStartUpTime failed ",e);
			return null;
		}
	}
	
	/**
	 * 
	 */
	public Map<String,Long> getThisDiskUse() {
		try {
			Map<String,Long> ret = new HashMap<String,Long>();
			
			//String systemRootPath = StreamUtil.getRootPath();
			//String diskName = systemRootPath.substring(0, 2);
			String diskName = _deskPath;
			//log.debug(diskName);
	        // 获取磁盘分区列表
        	File file = new File(diskName);
        	if(file.exists()){
	        	/*String key1 = file.getPath();//盘符
	        	String key2 = file.getFreeSpace() / 1024 / 1024 / 1024 + "G";//空间未使用
	        	String key3 = (file.getTotalSpace()-file.getFreeSpace()) / 1024 / 1024 / 1024 + "G";//已经使用
	        	String key4 = file.getTotalSpace() / 1024 / 1024 / 1024 + "G";//总容量
	        	Double compare = (Double) (1 - file.getFreeSpace() * 1.0 / file.getTotalSpace()) * 100;
	        	String key5 =  compare.intValue() + "%";
	        	//log.debug(key1);log.debug(key2);log.debug(key3);log.debug(key4);log.debug(key5);
	        	 */	        	
        		Long free = file.getFreeSpace();
	        	Long totle = file.getTotalSpace();
	        	ret.put("used", (totle - free));
	        	ret.put("free", free);
        	}
        	//log.debug(ret);
        	return ret;
		} catch (Exception e) {
			log.error("获取盘符信息错误",e);
			return null;
		}
	}
	
	/**
	 * 获取硬盘信息
	 * @return
	 */
	public static String[][] getdiskInfo(){
		String[][] disk = null;
		try {
			File[] roots = File.listRoots();
			disk = new String[roots.length][];
	        // 获取磁盘分区列表
	        for (int i=0;i<roots.length;i++) {
	        	File file = roots[i];
	        	if(file.exists()){
		        	disk[i] = new String[5];
		        	disk[i][0] = file.getPath();//盘符
		        	disk[i][1] = file.getFreeSpace() / 1024 / 1024 / 1024 + "G";//空间未使用
		        	disk[i][2] = (file.getTotalSpace()-file.getFreeSpace()) / 1024 / 1024 / 1024 + "G";//已经使用
		        	disk[i][3] = file.getTotalSpace() / 1024 / 1024 / 1024 + "G";//总容量
		        	Double compare = (Double) (1 - file.getFreeSpace() * 1.0 / file.getTotalSpace()) * 100;
		        	disk[i][4] =  compare.intValue() + "%";
	        	}
	        }
		} catch (Exception e) {
			log.error("获取盘符信息错误",e);
		}
        return disk;
	}

}
