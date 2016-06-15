package com.org.mokey.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.apache.log4j.Logger;
public class Ipconfig {
	
	private static final Logger log = Logger.getLogger(Ipconfig.class);

    public static void main(String[] arguments) throws Exception{
        System.out.println("MAC ......... "+getLocalMACAddress());
        System.out.println("ip ......... "+getRealIp());
    }
    
    public static String getLocalMACAddress(){
    	InetAddress ia;
		try {
			ia = InetAddress.getLocalHost();//获取本地IP对象
			return getMACAddress(ia);
		} catch (Exception e) {
			log.error("get getLocalMACAddress failed",e);
			return null;
		}
    }
    
    //获取MAC地址的方法
    private static String getMACAddress(InetAddress ia)throws Exception{
        //获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
        
        //下面代码是把mac地址拼装成String
        StringBuffer sb = new StringBuffer();
        
        for(int i=0;i<mac.length;i++){
            if(i!=0){
                sb.append("-");
            }
            //mac[i] & 0xFF 是为了把byte转化为正整数
            String s = Integer.toHexString(mac[i] & 0xFF);
            sb.append(s.length()==1?0+s:s);
        }
        
        //把字符串所有小写字母改为大写成为正规的mac地址并返回
        return sb.toString().toUpperCase();
    }

	public static String getRealIp() throws SocketException {
		String localip = null;// 本地IP，如果没有配置外网IP则返回它
		String netip = null;// 外网IP
		Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
		InetAddress ip = null;
		boolean finded = false;// 是否找到外网IP
		while (netInterfaces.hasMoreElements() && !finded) {
			NetworkInterface ni = netInterfaces.nextElement();
			Enumeration<InetAddress> address = ni.getInetAddresses();
			while (address.hasMoreElements()) {
				ip = address.nextElement();
				if (!ip.isSiteLocalAddress() 
						&& !ip.isLoopbackAddress() 
						&& ip.getHostAddress().indexOf(":") == -1) {// 外网IP
					netip = ip.getHostAddress();
					finded = true;
					log.debug("netip:"+netip);
					break;
				} else if (ip.isSiteLocalAddress() 
						&& !ip.isLoopbackAddress() 
						&& ip.getHostAddress().indexOf(":") == -1) {// 内网IP
					localip = ip.getHostAddress();
					log.debug("localip:"+localip);
					finded = true;
					break;
				}
			}
		}
	
		if (netip != null && !"".equals(netip)) {
			return netip;
		} else {
			return localip;
		}
	}
    
    
}