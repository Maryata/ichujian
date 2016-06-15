package com.org.mokey.common.util.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

/**
 * FTP客户端工具类
 * @author jason.wu
 * @version 4.0
 * @since 4.0
 */
public class FtpClientUtil {
	 
	private static final Logger log = Logger.getLogger(FtpClientUtil.class);
	/**
	 * ftp服务器的地址
	 */
	private String ftpServer; 
	/**
	 * 端口
	 */
    private String ftpPort;  
    /**
     * 登录用户名
     */
    private String ftpUserName;  
    /**
     * 登录用户密码
     */
    private String ftpPassword;  
    /**
     * FTPClient对象
     */
    private FTPClient ftpClient; 
    /**
     * 判断是否登录或者登录成功
     */
    private boolean isLogin ; 
   
     
   /**
    * FtpClientUtil构造函数,登录ftp服务器
    * @param ftpServer ftp服务器地址
    * @param ftpPort ftp服务器端口号
    * @param ftpUserName ftp登录用户名
    * @param ftpPassword ftp登录密码
    */
    public FtpClientUtil(String ftpServer, String ftpPort, String ftpUserName,
            String ftpPassword)  {//从外面传过来的
    	log.debug("enter into FtpClientUtil");
    	this.ftpServer = ftpServer;
        if(ftpPort.trim().equals("")){
        	this.ftpPort="21";
        }else{
        	this.ftpPort =ftpPort;
        }
        if(ftpUserName.trim().equals("")){
        	this.ftpUserName ="Anonymous";
        }else{
        	this.ftpUserName = ftpUserName;
        }	
        this.ftpPassword = ftpPassword;
        log.debug("ftpServer:"+this.ftpServer+"---ftpPort:"+this.ftpPort+"---ftpUserName:" +
        		""+this.ftpUserName+"---ftpPassword:"+this.ftpPassword+"");
        try{
        	 isLogin = true;
        	 ftpClient = new FTPClient();  //创建ftpclient对象
        	 int port = 21;
        	 try{
        		 port = Integer.parseInt(this.ftpPort);
        	 }catch(Exception e){
        		 log.error("error in FtpClientUtil port", e);
        	 }
        	 ftpClient.setControlEncoding("utf-8");
        	 ftpClient.setConnectTimeout(2000);
	         ftpClient.setDataTimeout(2000); 
	         ftpClient.connect(this.ftpServer,port); 
        	 if(FTPReply.isPositiveCompletion(ftpClient.getReplyCode())){   
                 if(ftpClient.login(this.ftpUserName, this.ftpPassword)){   
                	 log.debug("Login success");
                	 isLogin = true;
                 }   
             } 
        	
        }catch(Exception e){
        	isLogin = false;
        	 conClose();
        	log.error("error in FtpClientUtil Login Failure", e);
        }
    }
    
    /**
     * 关闭ftp服务器
     */
    public void conClose() {
  	  try {
	  	   if(ftpClient!=null){
	  		   if(isLogin){
	  			 ftpClient.logout();
	  		   }
		  		 
	  	    }
  	  } catch (Exception e) {
  		 log.error("error in close ftpClient Failure", e);
  	  } 
  	  finally{
  		if(ftpClient.isConnected()){   
            try {
				ftpClient.disconnect();
			} catch (IOException e) {
				log.error("error in close ftpClient Failure", e);
			}   
        }
  	  }
  	}
    
    /**
     * 下载单个文件 
     * @param remoteFileName 服务器上的文件名(含全路径)
     * @param localFileName 本地文件名(全路径名)
     * @return boolean true下载成功 false 表示下载失败
     */
    public boolean downloadFile(String remoteFileName,String localFileName){
    	boolean downloadFileSuccess = false;
    	try{
    		//设置被动模式   
    		remoteFileName = remoteFileName.replace("\\", "/");
    		log.debug("remoteFileName:"+remoteFileName+"---localFileName:"+localFileName);
            ftpClient.enterLocalPassiveMode();   
            //设置以二进制方式传输   
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);   
            //检查远程文件是否存在   
            FTPFile[] files = ftpClient.listFiles(new String(remoteFileName));   
            //log.debug("files.length:"+files.length);
            log.debug("files.length:"+files.length);   
            if(files.length != 1){   
            //	log.debug("远程文件不存在");
            	log.debug("file is not exits");   
                return downloadFileSuccess;
            }   
            long lRemoteSize = files[0].getSize();   
            File f = new File(localFileName);   
            //本地存在文件，进行断点下载   
            if(f.exists() && f.isFile()){   
            	
                long localSize = f.length();   
                //判断本地文件大小是否大于远程文件大小   
                if(localSize >= lRemoteSize){   
                   log.debug("本地文件大于等于远程文件，下载中止");   
                    downloadFileSuccess = true;
                    return  downloadFileSuccess;
                }   
                   
                //进行断点续传，并记录状态  
                try{
	                FileOutputStream out = new FileOutputStream(f,true);   
	                ftpClient.setRestartOffset(localSize);   
	                InputStream in = ftpClient.retrieveFileStream(new String(remoteFileName));   
	                byte[] bytes = new byte[1024];   
	                long step = lRemoteSize /100;   
	                long process=localSize /step;   
	                int c;   
	                while((c = in.read(bytes))!= -1){   
	                    out.write(bytes,0,c);   
	                    localSize+=c;   
	                    long nowProcess = localSize /step;   
	                    if(nowProcess > process){   
	                        process = nowProcess;   
	                        if(process % 10 == 0)   
	                            log.debug("下载进度："+process);   
	                        //TODO 更新文件下载进度,值存放在process变量中   
	                    } 
	                     
	                }   
	                in.close();   
	                out.close();   
	                boolean isDo = ftpClient.completePendingCommand();   
	                if(isDo){   
	                	downloadFileSuccess = true;
	                	//ftpClient.deleteFile(remoteFileName);
	                }else {   
	                	downloadFileSuccess = false;  
	                }  
                }catch(Exception e){
                	log.debug("error in ---");
                }
            }else { 
            	try{
            		
            		 OutputStream out = new FileOutputStream(f);   
                     InputStream in= ftpClient.retrieveFileStream(remoteFileName);   
                     byte[] bytes = new byte[1024];   
                     long step = lRemoteSize /100; 
                     if(step<=0){
                    	 step = 1;
                     }
                     long process=0;    
                     long localSize = 0L;   
                     int c;   
                     while((c = in.read(bytes))!= -1){   
                     	//log.debug("--------ftputil");
                         out.write(bytes, 0, c);   
                         localSize+=c;   
                        
                         long nowProcess = localSize /step;   
                         if(nowProcess > process){   
                             process = nowProcess;   
                             if(process % 10 == 0)   
                                 log.debug("下载进度："+process);     
                             //TODO 更新文件下载进度,值存放在process变量中   
                         } 
                     }   
                     System.out.println("------ttttttt");
                     in.close();   
                     out.close();   
                     boolean upNewStatus = ftpClient.completePendingCommand();   
                     if(upNewStatus){   
                     	downloadFileSuccess = true; 
                     	//ftpClient.deleteFile(remoteFileName);
                     }else {   
                     	downloadFileSuccess = false;    
                     } 
            	}catch(Exception e){
            		log.debug("error ------");
            	}
                 
            }   
            
    	}catch(Exception e){
    		log.error("error in downloadFile",e);
    		downloadFileSuccess = false;
    	}
    	System.out.println("---"+downloadFileSuccess);
    	return downloadFileSuccess;
    }
    
    /**
     * 取得某一个目录下的文件下载路径
     * @param remoteFolderName
     * @return
     */
    public List<String> listFileName(String remoteFolderName){
		log.debug("remoteFileName:"+remoteFolderName);
		List<String> rs = new ArrayList<String>();
		try{
			 ftpClient.enterLocalPassiveMode();   
		        //设置以二进制方式传输   
		        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);   
		        //检查远程文件是否存在   
		        FTPFile[] files = ftpClient.listFiles(new String(remoteFolderName));  
		        log.debug("files:"+files.length);
		        if(files.length>0){
		        	for(int i=0;i<files.length;i++){
	        			try{
	        				 if(!files[i].isDirectory()){
	        					 rs.add(remoteFolderName+(remoteFolderName.endsWith("/")||files[i].getName().startsWith("/")?"":"/")+files[i].getName());
	        				 }
	                   	}catch(Exception e){
	                   		log.debug("error ------",e);
	                   	}
        			}
		        }
		        //log.debug("files.length:"+files.length);
		        log.debug("files.length:"+files.length); 
		}catch(Exception e){
			log.error("error in downloadFolder", e);
		}
		return rs;
    }
    
    /**
     * 递归下载文件夹(文件下面的子文件夹以及文件) 
     * @param remoteFolderName 服务器上需要下载的文件加的名字
     * @param localFolderName 本地存放的文件夹
     * @return   boolean true下载成功 false 表示下载失败
     */
    public boolean downloadFolder(String remoteFolderName,String localFolderName){
    	remoteFolderName = remoteFolderName.replace("\\", "/");
    	localFolderName = localFolderName.replace("\\", "/");
		log.debug("remoteFileName:"+remoteFolderName+"---localFileName:"+localFolderName);
		try{
			 ftpClient.enterLocalPassiveMode();   
		        //设置以二进制方式传输   
		        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);   
		        //检查远程文件是否存在   
		        FTPFile[] files = ftpClient.listFiles(new String(remoteFolderName));  
		        log.debug("files:"+files.length);
		        if(files.length>0){
		        	File f = new File(localFolderName);  
		        	log.debug("f======:"+f.exists());
		        	if(!f.exists()){
		        		//创建文件夹
		        		f.mkdirs();
		        		
		        	}
		        	for(int i=0;i<files.length;i++){
	        			try{
	        				 if(files[i].isDirectory()){
	        					 this.downloadFolder(remoteFolderName+"/"+files[i].getName(), localFolderName+"/"+files[i].getName());
	        				 }else{
	        					 
	        					 OutputStream out = new FileOutputStream(localFolderName+"/"+files[i].getName());   
		                         InputStream in= ftpClient.retrieveFileStream(remoteFolderName+"/"+files[i].getName());   
		                         byte[] bytes = new byte[1024];   
		                         int c;   
		                         while((c = in.read(bytes))!= -1){   
		                         	//log.debug("--------ftputil");
		                             out.write(bytes, 0, c);   
		                         }   
		                         in.close();   
		                         out.close();   
		                         boolean upNewStatus = ftpClient.completePendingCommand();  
	        				 }
	        				 
	                         
	                   	}catch(Exception e){
	                   		log.debug("error ------",e);
	                   	}
        			}
		        	
		        	
		        }
		        //log.debug("files.length:"+files.length);
		        log.debug("files.length:"+files.length); 
		}catch(Exception e){
			log.error("error in downloadFolder", e);
		}
         
        return true;
    }
    /**
     * 上传单个文件 uploadFile(String clientFileName, String ftpPath)
     * @param clientFileName 本地要上传的文件的全路径
     * @param remote FTP上对于根目录的路径
     * @throws IOException
    */
    public void uploadFile(String clientFileName, String remote) throws
            Exception {
    	log.debug("clientFileName : remote" + clientFileName + ":"+remote);
          try {
        	    boolean result = false;
                ftpClient.enterLocalPassiveMode();   
                //ftpClient.setConnectTimeout(1000);
               
                //设置以二进制流的方式传输   
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);  
//                ftpClient.setControlEncoding("utf-8");
                //对远程目录的处理   
                remote = remote.replace("\\", "/");
                String remoteFileName = remote;   
                if(remote.contains("/")){   
                	System.out.println("-----------------"+remote);
                    remoteFileName = remote.substring(remote.lastIndexOf("/")+1);  
                    log.debug("-----------------"+remoteFileName);
                    //创建服务器远程目录结构，创建失败直接返回   
                    if(CreateDirectroy(remote, ftpClient)){   
                    	log.debug("创建文件夹成功");
                   } else{
                	   log.debug("创建文件夹失败");
                   	return;
                   }
                  
                }  
                
                log.debug("-----------------local:"+clientFileName);
                log.debug("-----------------remoteFileName:"+remoteFileName);
                //检查远程是否存在文件   
                FTPFile[] files = ftpClient.listFiles(new String(remoteFileName));   
                log.debug("-----------------"+files.length);
                if(files.length == 1){   
                    long remoteSize = files[0].getSize();   
                    File f = new File(clientFileName);
                    long localSize = f.length();   
                    if(remoteSize>=localSize){   
                        return ;
                    }  
                    //尝试移动文件内读取指针,实现断点续传   
                   result = uploadFile(remoteFileName, f, ftpClient, remoteSize);   
                    //如果断点续传没有成功，则删除服务器上文件，重新上传   
                    if(!result ){   
                        if(!ftpClient.deleteFile(remoteFileName)){   
                           
                        }   
                        result = uploadFile(remoteFileName, f, ftpClient, 0);   
                    }  
                	
                }else {   
                    result = uploadFile(remoteFileName, new File(clientFileName), ftpClient, 0);   
                }   
                
            } catch (Exception ex) {
            	System.out.println("aaa-------------");
                ex.printStackTrace();
            }
        
    }
    /**
     * 将FTP上面的文件移动到目标文件下面去
	 * @param fileName 需要移动的文件路径 （相对于FTP上面的文件路径）
	 * @param remote 移动的目标路径 （相对于FTP上面的文件路径）
     */
    public void rename(String fileName,String  remote){
    	
    	try {
    		
    		 FTPFile[] files = ftpClient.listFiles(new String(remote));   
             log.debug("-----------------"+files.length);
             if(files.length == 1){  
             	ftpClient.deleteFile(remote);
             } 
            log.debug("fileName:"+fileName+"=====remote:"+remote);
			ftpClient.rename(fileName, remote);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
       
    }
    
    /**
     * 上传单个文件 uploadFile(String clientFileName, String ftpPath)
     * @param clientFileName 本地要上传的文件的全路径
     * @param remote FTP上对于根目录的路径
     * @throws IOException
    */
    public void uploadNewFile(String clientFileName, String remote) throws
            Exception {
    	log.debug("clientFileName : remote" + clientFileName + ":"+remote);
          try {
        	    boolean result = false;
                ftpClient.enterLocalPassiveMode();   
                //ftpClient.setConnectTimeout(1000);
               
                //设置以二进制流的方式传输   
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);  
//                ftpClient.setControlEncoding("utf-8");
                //对远程目录的处理   
                remote = remote.replace("\\", "/");
                String remoteFileName = remote;   
                if(remote.contains("/")){   
                	System.out.println("-----------------"+remote);
                    remoteFileName = remote.substring(remote.lastIndexOf("/")+1);  
                    log.debug("-----------------"+remoteFileName);
                    //创建服务器远程目录结构，创建失败直接返回   
                    if(CreateDirectroy(remote, ftpClient)){   
                    	log.debug("创建文件夹成功");
                   } else{
                	   log.debug("创建文件夹失败");
                   	return;
                   }
                  
                }  
                
                log.debug("-----------------local:"+clientFileName);
                log.debug("-----------------remoteFileName:"+remoteFileName);
                //检查远程是否存在文件   
                FTPFile[] files = ftpClient.listFiles(new String(remoteFileName));   
                log.debug("-----------------"+files.length);
                if(files.length == 1){  
                	ftpClient.deleteFile(remoteFileName);
                } 
                result = uploadFile(remoteFileName, new File(clientFileName), ftpClient, 0);   
                  
                
            } catch (Exception ex) {
            	System.out.println("aaa-------------");
                ex.printStackTrace();
            }
        
    }
    
    /**  
     * 上传文件到服务器,新上传和断点续传  
     * @param remoteFile 远程文件名，在上传之前已经将服务器工作目录做了改变  
     * @param localFile 本地文件File句柄，绝对路径  
     * @param processStep 需要显示的处理进度步进值  
     * @param ftpClient FTPClient引用  
     * @return   boolean true上传成功 false表示上传失败
     * @throws IOException  
     */  
    public boolean uploadFile(String remoteFile,File localFile,FTPClient ftpClient,long remoteSize) throws IOException{   
    	boolean uploadFileSuccess = false; 
    	
    	log.debug("remoteFile-localFile:"+remoteFile+"=="+localFile.getAbsolutePath()+"==="+localFile.getName());
    	
        //显示进度的上传   
        long step = localFile.length() / 100;  
        
        long process = 0;   
        long localreadbytes = 0L;   
        RandomAccessFile raf = new RandomAccessFile(localFile,"r");   
        log.debug("remoteFile:"+remoteFile);
        OutputStream out = ftpClient.appendFileStream(new String(remoteFile));   
        //断点续传   
        if(remoteSize>0){   
            ftpClient.setRestartOffset(remoteSize); 
            if(step>0){
            	 process = remoteSize /step;   
            }
           
            raf.seek(remoteSize);   
            localreadbytes = remoteSize;   
        }   
        byte[] bytes = new byte[1024];   
        int c;   
        while((c = raf.read(bytes))!= -1){   
            out.write(bytes,0,c);   
            localreadbytes+=c;   
            if(step>0){
            	if(localreadbytes / step != process){   
                    process = localreadbytes / step;   
                    
                    //TODO 汇报上传状态   
                }  
            }else{
            	process = 100;
            }
//            log.debug("上传进度:" + process);  
        }   
        out.flush();   
        raf.close();   
        out.close();   
        boolean result =ftpClient.completePendingCommand();
        System.out.println("result : " + result);
        if(result){   
        	uploadFileSuccess = true;
        }else {   
        	uploadFileSuccess = false;
        }   
        return uploadFileSuccess;   
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
    public static void main(String[] args) {   
        FtpClientUtil myFtp = new FtpClientUtil("192.168.90.40","21","wya","123456789");   
      //  System.out.println(80/60);
        try {
        	myFtp.uploadFile("D:\\新建 文本文档.txt", "新建 文本文档123123.txt");
        	//myFtp.downloadFolder("userphoto", "F:\\upload\\userphoto");
        	//myFtp.rename("kkk.xls", "ty/kkk.xls");
        	//File file = new File("D:\\a\\b\\c.txt");
        	//file.mkdirs();
        	//System.out.print(file.exists()+"=======:"+file.isFile()+"==="+file.isDirectory());
        	//File file = new File("D:\\mmusic.rar");
//        	System.out.println(file.length());
         //  boolean flag = myFtp.connect("192.168.90.38", 21, "wya", "123456789");   
        // System.out.println("==="+myFtp.isLogin);
        // myFtp.downloadFile("TM20110711嘿嘿.zip","D:\\TM2011嘿嘿.zip");
        	
       // myFtp.uploadFile("E:\\JASON\\apache-tomcat-6.0.26\\work\\Catalina\\localhost\\evoice3.5.1\\upload_73c4e480_13561cddcff__8000_00000009.tmp","2012-02-09/adjunct/%5C%5Cfakepath%5C%5CmeetingRecordTemp%E5%9C%A8%E5%9C%A8西安.xls");
        // myFtp.uploadFile("F:\\test\\TM20110711成.zip","TM201107110功.zip");
       //  myFtp.conClose();  
        
 
//        // System.out.println(myFtp.upload("","",5));   
//           System.out.println(myFtp.upload("D:\\test\\aaa.txt","/abcdefg"));   
//           System.out.println(flag);   
//            //System.out.println(myFtp.download("abcdefg\\123.txt", "D:\\test\\"));   
//            myFtp.disconnect();   
        } catch (Exception e) {   
        	e.printStackTrace();
           // System.out.println("连接FTP出错："+e.getMessage());   
        }   
    }
	/**
	 * 返回是否登录ftp成功
	 * @return boolean true表示登录成功 false表示登录失败
	 */
    public boolean isLogin() {
		return isLogin;
	}
    /**
     * 设置登录的状态
     * @param isLogin Y/N 
     */
	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}  
}







