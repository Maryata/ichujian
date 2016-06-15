package com.sys.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

public class SFTP {

	private static Logger log = Logger.getLogger(SFTP.class);
	
	/**
	 * 连接sftp服务器
	 * 
	 * @param host
	 *            主机
	 * @param port
	 *            端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 */
	public ChannelSftp connect(String host, int port, String username,
			String password) {
		ChannelSftp sftp = null;
		try {
			log.debug("init SFTP.connect : host:["+host+"],port:["+port+"],username:["+username+"],password:["+password+"]");
			JSch jsch = new JSch();
			jsch.getSession(username, host, port);
			Session sshSession = jsch.getSession(username, host, port);
			log.debug("Session created.");
			sshSession.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			log.debug("Session connected.");
			log.debug("Opening Channel.");
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			log.debug("Connected to " + host + ".");
		} catch (Exception e) {
			log.error("SFTP.connect failed,",e);
		}
		log.debug("out SFTP.connect");
		return sftp;
	}
	
	public void closeConnect(ChannelSftp sftp) {
		if (sftp != null) {
			sftp.disconnect();
		}
	}
	
	   /**  
     * 递归创建远程服务器目录  
     * @param remote 远程服务器文件绝对路径  
     * @param ftpClient FTPClient对象  
     * @return boolean true表示目录创建成功 false表示创建失败
     * @throws IOException  
     */  
    public boolean  CreateDirectroy(String remote,FTPClient ftpClient) throws IOException{   
    	
    	  String directory = remote.substring(0,remote.lastIndexOf("/")+1);   
    	 
    	log.debug("directory"+directory);
        if(!directory.equalsIgnoreCase("/")&&!ftpClient.changeWorkingDirectory(new String(directory))){   
            //如果远程目录不存在，则递归创建远程服务器目录   
            int start=0;   
            int end = 0;   
            if(directory.startsWith("/")){   
                start = 1;   
            }else{   
                start = 0;   
            }   
            end = directory.indexOf("/",start);   
            while(true){   
                String subDirectory = new String(remote.substring(start,end));   
                if(!ftpClient.changeWorkingDirectory(subDirectory)){   
                    if(ftpClient.makeDirectory(subDirectory)){   
                        ftpClient.changeWorkingDirectory(subDirectory);  
                    }else {   
                        System.out.println("创建目录失败");   
                       return false;  
                    }   
                }   
                   
                start = end + 1;   
                end = directory.indexOf("/",start);   
                   
                //检查所有目录是否创建完毕   
                if(end <= start){   
                    break;   
                }   
            }   
        }   
        return true;   
    } 
	
	public void cdDirectory(String directory, ChannelSftp sftp, boolean isRoot){
		//sftp.cd(directory);
		String newDc = "";
		try {
			if(directory.equalsIgnoreCase("/")){
				sftp.cd(directory);
				return;
			}			
			if(directory.endsWith("/")){
				directory = directory.substring(0,directory.length()-1);   
			}
			newDc = directory;
			if(newDc.startsWith("/")){
				newDc = directory.substring(1,directory.length()); 
			}
			//sftp.ls(directory);
			sftp.cd(directory);
			return ;
		} catch (SftpException e) {
			try {
				if(newDc.indexOf("/")>-1){
					String d1=""; String d2 = "";
					if(isRoot){
						isRoot = false;
						d1 = "/"+newDc.substring(0,newDc.indexOf("/")+1);
						d2 = newDc.substring(newDc.indexOf("/")+1,newDc.length());
					}else{
						d1 = newDc.substring(0,newDc.indexOf("/")+1);
						d2 = newDc.substring(newDc.indexOf("/")+1,newDc.length());
					}
					//log.debug("newDc:"+newDc +" , d1:"+d1 +" , d2:"+d2);
					try{
						sftp.ls(d1);
					}catch(Exception e1){
						sftp.mkdir(d1);
						sftp.cd(d1);
					}
					cdDirectory(d2,sftp,isRoot);
				}else{//不存在二级目录;创建后切换
					sftp.mkdir(directory);
					sftp.cd(directory);
				}
			} catch (SftpException e1) {
				e1.printStackTrace();
			}
		}
		/*
		 Vector<LsEntry> vector = null;* 
		 Vector content = sftp.ls(directory);
		 Iterator<LsEntry> it = vector.iterator();
		while (it.hasNext()) {
			LsEntry lsEntry = it.next();
			 SftpATTRS t = lsEntry.getAttrs();
			 boolean isdir = t.isDir();//判断是否是文件夹
			System.out.println(lsEntry.getFilename()+" _ "+ " , id:"+isdir );
		}*/
		//sftp.mkdir(directory);
	}
	

	/**
	 * 上传文件
	 * 
	 * @param directory
	 *            上传的目录
	 * @param uploadFile
	 *            要上传的文件
	 * @param sftp
	 */
	public void upload(String directory, String uploadFile, ChannelSftp sftp) {
		log.debug("init SFTP.upload");
		try {
			//log.debug("SFTP.upload cd directory: "+directory);
			//this.cdDirectory(directory, sftp, true);
			//log.debug("SFTP.upload uploadFile: "+uploadFile);
			File file = new File(uploadFile);
			sftp.put(new FileInputStream(file), file.getName());
			log.debug("SFTP.upload uploadFile: "+uploadFile+" finished");
		} catch (Exception e) {
			log.error("SFTP.upload failed",e);
		}
		log.debug("out SFTP.upload");
	}

	/**
	 * 下载文件
	 * 
	 * @param directory
	 *            下载目录
	 * @param downloadFile
	 *            下载的文件
	 * @param saveFile
	 *            存在本地的路径
	 * @param sftp
	 */
	public void download(String directory, String downloadFile,
			String saveFile, ChannelSftp sftp) {
		log.debug("init SFTP.download");
		try {
			sftp.cd(directory);
			File file = new File(saveFile);
			sftp.get(downloadFile, new FileOutputStream(file));
		} catch (Exception e) {
			log.error("SFTP.download failed",e);
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param directory
	 *            要删除文件所在目录
	 * @param deleteFile
	 *            要删除的文件
	 * @param sftp
	 */
	public void delete(String directory, String deleteFile, ChannelSftp sftp) {
		try {
			sftp.cd(directory);
			sftp.rm(deleteFile);
		} catch (Exception e) {
			log.error("SFTP.delete failed",e);
		}
	}

	/**
	 * 列出目录下的文件
	 * 
	 * @param directory
	 *            要列出的目录
	 * @param sftp
	 * @return
	 * @throws SftpException
	 */
	@SuppressWarnings("rawtypes")
	public Vector listFiles(String directory, ChannelSftp sftp)
			throws SftpException {
		return sftp.ls(directory);
	}

	public static void main(String[] args) {
		SFTP sf = new SFTP();
		/*String host = "192.168.0.1";
		int port = 22;
		String username = "root";
		String password = "root";*/
		String host = "114.215.138.174";
		int port = 22;
		String username = "root";
		String password = "gost77889";
		
		String directory = "/mokey11/t1/t2";
		String uploadFile = "D:\\MM_testr.log";
		String downloadFile = "upload.txt";
		String saveFile = "D:\\tmp\\download.txt";
		String deleteFile = "delete.txt";
		ChannelSftp sftp = null;
		//sftp = sf.connect(host, port, username, password);
		sftp = sf.connect("192.168.8.221", 22, "root", "123456");
		try {
			sf.cdDirectory(directory,  sftp,  true);
			//sf.upload(directory, uploadFile, sftp);
			//sf.download(directory, downloadFile, saveFile, sftp);
			//sf.delete(directory, deleteFile, sftp);
			
			//sftp.cd(directory);
			//sftp.mkdir("ss");
			System.out.println("finished");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			sftp.quit();
			sf.closeConnect(sftp);
		}
	}
}
