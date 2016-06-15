package com.org.mokey.common.util.file;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SFTPChannel {
    Session session = null;
    ChannelSftp channel = null;

    private static final Logger log = Logger.getLogger(SFTPChannel.class.getName());

    public ChannelSftp getChannel(Map<String, String> sftpDetails, int timeout) throws JSchException {

        String ftpHost = sftpDetails.get(SFTPConstants.SFTP_REQ_HOST);
        String port = sftpDetails.get(SFTPConstants.SFTP_REQ_PORT);
        String ftpUserName = sftpDetails.get(SFTPConstants.SFTP_REQ_USERNAME);
        String ftpPassword = sftpDetails.get(SFTPConstants.SFTP_REQ_PASSWORD);

        int ftpPort = SFTPConstants.SFTP_DEFAULT_PORT;
        if (port != null && !port.equals("")) {
            ftpPort = Integer.valueOf(port);
        }

        JSch jsch = new JSch(); // 创建JSch对象
        session = jsch.getSession(ftpUserName, ftpHost, ftpPort); // 根据用户名，主机ip，端口获取一个Session对象
        log.debug("Session created.");
        if (ftpPassword != null) {
            session.setPassword(ftpPassword); // 设置密码
        }
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config); // 为Session对象设置properties
        session.setTimeout(timeout); // 设置timeout时间
        session.connect(); // 通过Session建立链接
        log.debug("Session connected.");

        log.debug("Opening Channel.");
        channel = (ChannelSftp) session.openChannel("sftp"); // 打开SFTP通道
        channel.connect(); // 建立SFTP通道的连接
        log.debug("Connected successfully to ftpHost = " + ftpHost + ",as ftpUserName = " + ftpUserName);
        return channel;//(ChannelSftp) 
    }
    
    /** 
     * 创建指定文件夹 
     * 
     * @param dirName 
     *            dirName 
     */ 
    public void mkDir(String dirName) 
    { 
        String[] dirs = dirName.split("/"); 
        try 
        { 
            String now = channel.pwd(); 
            for (int i = 0; i < dirs.length; i++) 
            { 
                boolean dirExists = openDir(dirs[i]); 
                if (!dirExists) 
                { 
                	channel.mkdir(dirs[i]); 
                    channel.cd(dirs[i]); 

                } 

            } 
            channel.cd(now); 
        } 
        catch (SftpException e) 
        { 
            log.error("mkDir Exception : " , e); 
        } 
    }
    
    /** 
     * 打开指定目录 
     * 
     * @param directory 
     *            directory 
     * @return 是否打开目录 
     */ 
    public boolean openDir(String directory) 
    { 
        try 
        { 
        	channel.cd(directory); 
            return true; 
        } 
        catch (SftpException e) 
        { 
            log.error("openDir Exception : " , e); 
            return false; 
        } 
    }
    
    /** 
     * 创建指定文件夹 
     * 
     * @param directory 
     *            路径+文件夹名 
     * @return 成功与否 
     */ 
    public boolean openAndMakeDir(String directory) 
    { 
        try 
        { 
            String now = channel.pwd();
            log.debug("now:"+now);
            log.debug("directory:"+directory);
            if (now.equals(directory)) 
            { 
                return true; 
            } 
            else 
            { 
                try 
                { 
                	channel.cd(directory); 
                    return true; 
                } 
                catch (SftpException e) 
                { 
                	//log.error("",e);
                    if (directory.startsWith(now)) 
                    { 
                        directory = directory.replaceFirst(now, ""); 
                    } 
                    String[] dirList = directory.split("/"); 
                    dirList = (String[]) ArrayUtils.removeElement(dirList, ""); 
                    for (String dir : dirList) 
                    { 
                        try 
                        { 
                        	channel.cd(dir); 
                        } 
                        catch (SftpException e1) 
                        { 
                        	channel.mkdir(dir); 
                        	channel.cd(dir); 
                        	//log.error("e1-----------",e1);
                        } 
                    } 
                    return true; 
                } 
            } 
        } 
        catch (SftpException e) 
        { 
            log.error("openDir Exception : " + directory, e); 
            return false; 
        } 
    } 

    public void closeChannel() throws Exception {
        if (channel != null) {
            channel.disconnect();
        }
        if (session != null) {
            session.disconnect();
        }
    }

    //创建ftp主目录
    //http://xinyoulinglei.iteye.com/blog/1750069
//    public static void createCd(Map sftpDetails){
//    	SFTPChannel channel = new SFTPChannel();
//        try {
//        	ChannelSftp chSftp = channel.getChannel(sftpDetails, 60000);
//        	List<String> list = new ArrayList<String>(FileUtil.loc_file.keySet());
//        	
//        	for(String f : list){
//        		if("IS_SYNC_FTP".equals(f) || "APP_ROOT".equals(f)){
//        			continue;
//        		}
//        		chSftp.cd("..");
//    			channel.openAndMakeDir(FileUtil.loc_file.get("APP_ROOT")+FileUtil.loc_file.get(f));
//        	}
//        	chSftp.quit();
//        	channel.closeChannel();
//        	
//		} catch (Exception e) {
//			log.error("ssftp upload file failed,", e);
//		}finally{
//			try {
//				channel.closeChannel();
//			} catch (Exception e) {
//				log.error("channel.closeChannel failed,",e);
//			}
//		}
//    }
    
    public static void main(String[] args) throws Exception {
    	Map<String, String> sftpDetails = (Map<String, String>) FileUtil.fileConfgs.get("sftp");
    	//createCd(sftpDetails);
    	sftpDetails.put("host", "192.168.8.222");
    	//createCd(sftpDetails);
    	
    }
        
        
}