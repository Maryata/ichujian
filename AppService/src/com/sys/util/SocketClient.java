package com.sys.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

 
public class SocketClient {
	
	private Logger log = Logger.getLogger(SocketClient.class);
	
	private String socketServer="127.0.0.1";
	
	private int socketPort=8080;
	
	/**
     * 连接超时
     */
    private int connectTimeOut = 70000;
    
    public static void main(String[] args) {
		 SocketClient op = new SocketClient();
		 Map data = new HashMap();
		 data.put("resource_package", "0000000");
		 String out = op.sendToMsgServer(data, "3", "09", "02");
		 System.out.println("--"+out);
	 }
    
	
	/**
	 * 发送到消息服务器	
	 * @param data
	 * @param type
	 * @param sendId
	 * @param receiveId
	 * @return
	 */
	public String sendToMsgServer(Map data ,String type , String sendId ,String receiveId){
		String ret = null;
		String dataStr="";
		//String d = JSONObject.fromObject(data).toString();
		sendId="8888";
		receiveId="0";
		//dataStr = d;
		//log.debug("sendToMsgServer,socketServer:"+socketServer+",socketPort:"+socketPort+",data:"+dataStr);
		dataStr = ""+sendId+receiveId;
		//log.debug("sendId:"+"--"+ByteUtil.toHexString(sendId));
		//log.debug("receiveId:"+Integer.valueOf(receiveId,16));
		//ret = this.send(socketServer, socketPort, dataStr, "UTF-8");
		return ret;
	}
	
	public String send(String host,int port,String dataStr,String recvEncoding){
		 Socket socket = null;
		 try {
			  byte[] type = ByteUtil.hexStringToBytes(ByteUtil.toHexString("3"));
		         byte[] sendId = ByteUtil.hexStringToBytes(ByteUtil.toHexString("8888"));
		         byte[] receiveId = ByteUtil.hexStringToBytes(ByteUtil.toHexString("0"));
		         
		         List<Integer> list = new ArrayList<Integer>();
		         for(byte b : type){
		 			list.add((int)b);
		 		 }
		         for(byte b : sendId){
		 			list.add((int)b);
		 		 }
		         for(byte b : receiveId){
		 			list.add((int)b);
		 		 }
		         int length = list.size();
		         byte[] lengthB = ByteUtil.hexStringToBytes(ByteUtil.toHexString(""+length));
		         List<Integer> dlist = new ArrayList<Integer>();
		         for(byte b : lengthB){
		        	 dlist.add((int)b);
		 		 }
		         dlist.addAll(list);
		         log.debug("dlist:"+dlist);
		         
		        int [] dataInt = new int[dlist.size()];
		 		int i=0;
		 		for (Integer d : list) {
		 			dataInt[i] = d;
		 			i++;
		 		}
		 		
		 		//----------------------------
			 //socket =new Socket();
			 //SocketAddress socketAddress = new InetSocketAddress(host,port);
			 //socket.connect(socketAddress, connectTimeOut);
	         // 60s超时
	 		socket =new Socket(host,port);
	        socket.setSoTimeout(connectTimeOut);
	
	         /** 发送客户端准备传输的信息 */
	         // 由Socket对象得到输出流，并构造PrintWriter对象
	         PrintWriter printWriter =new PrintWriter(socket.getOutputStream(),true);
	         // 将输入读入的字符串输出到Server
	         //printWriter.println(dataStr);
	         printWriter.println(dataInt);
	         
	        // printWriter.print(s)
	         // 刷新输出流，使Server马上收到该字符串
	         printWriter.flush();
	
	         /** 用于获取服务端传输来的信息 */
	         // 由Socket对象得到输入流，并构造相应的BufferedReader对象
	         BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(socket.getInputStream(),recvEncoding));
	         // 输入读入一字符串
	         String tempLine = bufferedReader.readLine();
	         StringBuffer temp = new StringBuffer();
	         while (tempLine != null){
	        	 temp.append(tempLine); //temp.append(crlf);
	        	 tempLine = bufferedReader.readLine();
	         }
	         /** 关闭Socket*/
	         printWriter.close();
	         bufferedReader.close();
	         return temp.toString();
		 }catch(Exception e){
			 log.error("send failed,",e);
		 }finally{
			 if(socket!=null &&socket.isClosed()){
				 try {
					socket.close();
				} catch (IOException e) {
					log.error("socket.close failed,",e);
				}
			 }
		 }
		return null;
	}
	
	private List<Integer> getStr2int(String value,int len,boolean isLeft){
		List<Integer> b = new ArrayList<Integer>();
		if(isLeft){
			value = getStrRepalceLeft(value, len, "00");
		}else{
			value = getStrRepalceRight(value, len, "00");
		}
		log.debug(" v-----"+value);
		//b = DataConverter.str2Bcd(value);
		
		String v = "";
		int l = 0;
		for (int i = 0; i < getStr2TypeLen(value); i++) {
			v = value.substring(i*2,(i*2)+2);
			l = Integer.valueOf(v,16);
			b.add(l);
		}
		return b;
	}
	
	
	/**
	 * 将字符串转为2位合并为1位的算法，设置左补00
	 * @param value
	 * @param len 
	 * @param replaceStr 设置左补字符串
	 * @return
	 */
	private String getStrRepalceLeft(String value,int len,String replaceStr){
		if(value.length()%2 != 0){
			value = "0" + value;
		}
		int l = len - getStr2TypeLen(value);
		String rStr = "";
		for (int i = 0; i < l; i++) {
			rStr += replaceStr;
		}
		return rStr + value;
	}
	/**
	 * 将字符串转为2位合并为1位的算法，设置右补00
	 * @param value
	 * @param len 
	 * @param replaceStr 设置右补字符串
	 * @return
	 */
	private String getStrRepalceRight(String value,int len,String replaceStr){
		if(value.length()%2 != 0){
			value +=  "0";
		}
		int l = len - getStr2TypeLen(value);
		String rStr = "";
		for (int i = 0; i < l; i++) {
			rStr += replaceStr;
		}
		return value + rStr;
	}
	
	/**
	 * 获取2位合1位的字符长度
	 * @param value
	 * @return
	 */
	private int getStr2TypeLen(String value){
		if(value.length()%2 != 0){
			return value.length()/2+1;
		}else{
			return value.length()/2;
		}
	}
	
	
	
	
    public static void main1(String[] args) {
        try {
            /** 创建Socket*/
            // 创建一个流套接字并将其连接到指定 IP 地址的指定端口号(本处是本机)
            Socket socket =new Socket("127.0.0.1",2013);
            // 60s超时
            socket.setSoTimeout(60000);
 
            /** 发送客户端准备传输的信息 */
            // 由Socket对象得到输出流，并构造PrintWriter对象
            PrintWriter printWriter =new PrintWriter(socket.getOutputStream(),true);
            // 将输入读入的字符串输出到Server
            BufferedReader sysBuff =new BufferedReader(new InputStreamReader(System.in));
            printWriter.println(sysBuff.readLine());
            // 刷新输出流，使Server马上收到该字符串
            printWriter.flush();
 
            /** 用于获取服务端传输来的信息 */
            // 由Socket对象得到输入流，并构造相应的BufferedReader对象
            BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // 输入读入一字符串
            String result = bufferedReader.readLine();
            System.out.println("Server say : " + result);
 
            /** 关闭Socket*/
            printWriter.close();
            bufferedReader.close();
            socket.close();
        }catch (Exception e) {
            System.out.println("Exception:" + e);
        }
    }
}
