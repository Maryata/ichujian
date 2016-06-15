package com.sys.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
public class NetWork {
	public String recevied;
	public String sent;
	public boolean isSuc;
	public void NetWorkFlux(String cmdline) {
		try {
			String line;
			Process p = Runtime.getRuntime().exec(cmdline);
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream(),"GBK"));
			int c = 0;
			while ((line = input.readLine()) != null ) {
				//System.out.println(line+"-==-");
				if(c > 3)
				{
					String s [] = line.replaceAll("字节", "").trim().replace(" ", "@").split(" ");
					//System.out.println(s.length);
					for(String str : s)
					{
						//System.out.println("--"+str);
						recevied = str.substring(0, str.indexOf("@"));
						sent = str.substring(str.lastIndexOf("@") + 1, str.length());
					}
					/**System.out.println(recevied + " " + sent);
					System.out.println("接受流量:" + Long.parseLong(recevied) / 1024 / 1024 + "MB");
					System.out.println("发送流量:" + Long.parseLong(sent) / 1024 / 1024 + "MB");
					**/
					//recevied = Long.parseLong(recevied) / 1024  + " KB";
					//sent = Long.parseLong(sent) / 1024  + " KB";
					break;
				}
				c ++;
			}
			input.close();
			isSuc = true;
		} catch (Exception e) {
			e.printStackTrace();
			isSuc = false;
		}
	}
	
    public static void main(String[] arguments) throws Exception{
    	NetWork netWork = new NetWork();
    	for(int i=0;i<1;i++){
    		netWork.NetWorkFlux("netstat -e");
            System.out.println("send--"+netWork.sent+"\trecevied--"+netWork.recevied);
            Thread.sleep(100);
    	}
    }
}
