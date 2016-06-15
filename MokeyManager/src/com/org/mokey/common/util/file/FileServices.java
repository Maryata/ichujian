package com.org.mokey.common.util.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.util.Map;

import org.apache.log4j.Logger;

import com.org.mokey.util.StrUtils;
import com.org.mokey.util.StreamUtil;

public class FileServices {
	
	/** Logger available to subclasses */
	private static Logger log = (Logger.getLogger(FileServices.class));
	
	/**
	 * 获取上传文件对应地址
	 * @param type
	 * @param key
	 * @param fileName
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getAppType(Object type){
		String ret = "";
		if(StrUtils.isNotEmpty(type)){
			FileUtil.inits();
			ret = (String)((Map)FileUtil.fileConfgs.get("appType")).get(String.valueOf(type+""));
		}
		if(StrUtils.isEmpty(ret)){
			ret = "news";
		}
		return ret;
	}
	
	public static String getSaveRoot(){
		FileUtil.inits();
		return FileUtil.filePath.get("SAVE_ROOT");
	}
	
	public static String getHttpRoot(){
		FileUtil.inits();
		return FileUtil.filePath.get("HTTP_ROOT");
	}
	
	public static String getHttpRoot(String fileName){
		return getHttpRoot()+fileName;
	}
	
	public static String saveFile(File file,String fileName){
		//---------
		String httpPath = getHttpRoot(fileName);
		
		String savePath = getSaveRoot()+fileName;
		StreamUtil.fileSave(file,savePath);
		log.debug("saveFile.savePath:"+savePath);
		log.debug("saveFile.httpPath:"+httpPath);
		return httpPath;
	}
	
	public static String saveFile(BufferedInputStream in, String fileName) {
		String httpPath = getHttpRoot(fileName);
		String savePath = getSaveRoot()+fileName;
		StreamUtil.fileSave(in,savePath);
		log.debug("saveFile.savePath:"+savePath);
		log.debug("saveFile.httpPath:"+httpPath);
		return httpPath;
	}
	
//	/**
//	 * app文件上传
//	 * @param file
//	 * @param fileName
//	 * @param fileType
//	 */
//	public static String saveLocFile(File file,String fileName,String fileType){
//		FileUtil.init();
//		if(StrUtils.isEmpty(FileUtil.loc_file.get(fileType))){
//			log.error("loc_file not find key "+fileType);
//			return "";
//		}
//		
//		String retHttpPath = FileUtil.http_file.get("APP_ROOT")+FileUtil.http_file.get(fileType)+fileName;
//		
//		String path = FileUtil.loc_file.get("APP_ROOT")+FileUtil.loc_file.get(fileType);
//		File f = new File(path);
//		if(!f.exists()){
//			f.mkdirs();
//		}
//		StreamUtil.fileSave(file,path+fileName);
//		//------
//		//同步FTP
//		if("Y".equals(FileUtil.loc_file.get("IS_SYNC_FTP"))){
//			// 设置主机ip，端口，用户名，密码
//			@SuppressWarnings("unchecked")
//			Map<String, String> sftpDetails = (Map<String, String>) FileUtil.fileConfgs.get("sftp");
//	        //String src = "E:\\aa.rar"; // 本地文件名
//	        //String dst = "/mokey/log/aa.rar"; // 目标文件名
//	        SFTPChannel channel = new SFTPChannel();
//	        try {
//	        	//String src = "d:"+path+fileName;
//				ChannelSftp chSftp = channel.getChannel(sftpDetails, 60000);
//				//channel.cdDirectory(path, true);
//				//log.debug("cd "+path +" succcess!"+src);
//				chSftp.put(new FileInputStream(file), path+fileName,  ChannelSftp.OVERWRITE);
//				chSftp.quit();
//				log.debug("sftp upload file:"+path+fileName+" success!");
//			} catch (Exception e) {
//				log.error("ssftp upload file:"+path+fileName+" failed,", e);
//			}finally{
//				try {
//					channel.closeChannel();
//				} catch (Exception e) {
//					log.error("channel.closeChannel failed,",e);
//				}
//			}
//		}
//		log.debug("save end : "+path+fileName);
//		return retHttpPath;
//	}
//	
	
//	/**
//	 * 活动帮文件上传
//	 * @param file
//	 * @param fileName
//	 * @param fileType
//	 */
//	public static String saveActFile(File file,String fileName,String fileType){
//		FileUtil.init();
//		if(StrUtils.isEmpty(FileUtil.loc_file.get(fileType))){
//			log.error("loc_file not find key "+fileType);
//			return "";
//		}
//		fileType = FileUtil.loc_file.get(fileType);
//		
//		String extPath = "";
//		String[] dirs = fileType.split("/");
//		for (int i = 0; i < dirs.length; i++) {
//			String thisdir = dirs[i];
//			if(StrUtils.isEmpty(thisdir)){
//				continue;
//			}
//			if(thisdir.startsWith("@date@")){
//				String date = thisdir.replace("@date@", "");
//				if("".equals(date)){
//					date = ApDateTime.getNowDateTime("yyyyMMdd");
//				}
//				thisdir=thisdir.replace("@date@", "");
//				try {
//					thisdir = ApDateTime.getNowDateTime(thisdir);
//				} catch (Exception e) {
//					log.error("date replace err,date:"+date+",replace:"+thisdir);
//				}
//				//log.debug("thisdir:"+thisdir);
//			}
//			extPath += thisdir+"/";
//		}
//		String retHttpPath = FileUtil.loc_file.get("ACT_HTTP")+extPath+fileName;
//		log.debug("retHttpPath:"+retHttpPath);
//		String path = FileUtil.loc_file.get("ACT_ROOT")+extPath;
//		File f = new File(path);
//		if(!f.exists()){
//			f.mkdirs();
//		}
//		StreamUtil.fileSave(file,path+fileName);
//		//------
//		//同步FTP
//		if("Y".equals(FileUtil.loc_file.get("IS_SYNC_FTP"))){
//			// 设置主机ip，端口，用户名，密码
//			@SuppressWarnings("unchecked")
//			Map<String, String> sftpDetails = (Map<String, String>) FileUtil.fileConfgs.get("sftp");
//	        //String src = "E:\\aa.rar"; // 本地文件名
//	        //String dst = "/mokey/log/aa.rar"; // 目标文件名
//	        SFTPChannel channel = new SFTPChannel();
//	        try {
//	        	//String src = "d:"+path+fileName;
//				ChannelSftp chSftp = channel.getChannel(sftpDetails, 60000);
//				chSftp.cd("..");
//				channel.openDir(FileUtil.loc_file.get("ACT_ROOT"));
//				channel.openAndMakeDir(extPath);
//				//log.debug("cd "+path +" succcess!"+src);
//				chSftp.put(new FileInputStream(file), path+fileName,  ChannelSftp.OVERWRITE);
//				chSftp.quit();
//				log.debug("sftp upload file:"+path+fileName+" success!");
//			} catch (Exception e) {
//				log.error("ssftp upload file:"+path+fileName+" failed,", e);
//			}finally{
//				try {
//					channel.closeChannel();
//				} catch (Exception e) {
//					log.error("channel.closeChannel failed,",e);
//				}
//			}
//			
//			// 设置主机ip，端口，用户名，密码
//			sftpDetails = (Map<String, String>) FileUtil.fileConfgs.get("sftp2");
//	        //SFTPChannel channel = new SFTPChannel();
//	        try {
//				ChannelSftp chSftp = channel.getChannel(sftpDetails, 60000);
//				chSftp.cd("..");
//				channel.openDir(FileUtil.loc_file.get("ACT_ROOT"));
//				channel.openAndMakeDir(extPath);
//				chSftp.put(new FileInputStream(file), path+fileName,  ChannelSftp.OVERWRITE);
//				chSftp.quit();
//				log.debug("sftp upload file:"+path+fileName+" success!");
//			} catch (Exception e) {
//				log.error("ssftp upload file:"+path+fileName+" failed,", e);
//			}finally{
//				try {
//					channel.closeChannel();
//				} catch (Exception e) {
//					log.error("channel.closeChannel failed,",e);
//				}
//			}
//		}
//		log.debug("save end : "+path+fileName);
//		return retHttpPath;
//	}
//	
//	
//	/**
//	 * 报表上传
//	 * @param file
//	 * @param fileName
//	 * @param fileType
//	 */
//	public static void saveReportLocFile(File file,String fileName,String fileType){
//		FileUtil.init();
//		if(StrUtils.isEmpty(FileUtil.report_file.get(fileType))){
//			log.error("loc_file not find key "+fileType);
//			return;
//		}
//		String path = FileUtil.report_file.get(fileType);
//		StreamUtil.fileSave(file,path+fileName);
//		log.debug("save report end : "+path+fileName);
//	}

}
