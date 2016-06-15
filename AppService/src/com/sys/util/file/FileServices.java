package com.sys.util.file;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.sys.util.StrUtils;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.Map;

public class FileServices {

    /**
     * Logger available to subclasses
     */
    private static Logger log = (Logger.getLogger(FileServices.class));

    /**
     * 获取上传文件对应地址
     *
     * @param type
     * @return
     */
    public static String getAppType (Object type) {
        String ret = "";
        if (StrUtils.isNotEmpty(type)) {
            FileUtils.init();
            ret = (String) ((Map) FileUtils.fileConfgs.get("appType")).get(String.valueOf(type + ""));
        }
        if (StrUtils.isEmpty(ret)) {
            ret = "news";
        }
        return ret;
    }

    /**
     * 头像文件上传
     *
     * @param file     文件
     * @param filePath 文件路径（不含域名，如：activity/image/userHead/1.png）
     */
    public static String saveHeadFile (InputStream file, String filePath) {
        FileUtils.init();
        String httpPath = "";
        /*
		 * if(StrUtils.isEmpty(FileUtils.user_head.get(fileType))){
		 * log.error("loc_file not find key "+fileType); return ""; }
		 */
        httpPath = FileUtils.user_head.get("HTTP_ROOT") + filePath;

        String namepath = FileUtils.user_head.get("DIR_ROOT") + filePath;

        // File f = new File(path);
        // if(!f.exists()){
        // f.mkdirs();
        // }
//		StreamUtil.fileSave(file,"e:\\a11.png");

        // 同步FTP
        if ("Y".equals(FileUtils.user_head.get("IS_SYNC_FTP"))) {
            // 设置主机ip，端口，用户名，密码
            @SuppressWarnings("unchecked")
            Map<String, String> sftpDetails = (Map<String, String>) FileUtils.fileConfgs
                    .get("sftp");
            SFTPChannel channel = new SFTPChannel();
            try {
                ChannelSftp chSftp = channel.getChannel(sftpDetails, 60000);
                chSftp.put(file, namepath, ChannelSftp.OVERWRITE);
                chSftp.quit();
                log.debug("sftp upload file:" + namepath + " success!");
            } catch (Exception e) {
                log.error("ssftp upload file:" + namepath + " failed,", e);
            } finally {
                try {
                    channel.closeChannel();
                } catch (Exception e) {
                    log.error("channel.closeChannel failed,", e);
                }
            }
            //
            // 设置主机ip，端口，用户名，密码
            // sftpDetails = (Map<String, String>)
            // FileUtils.fileConfgs.get("sftp2");
            // //SFTPChannel channel = new SFTPChannel();
            // try {
            // ChannelSftp chSftp = channel.getChannel(sftpDetails, 60000);
            // chSftp.put(file1, path+fileName, ChannelSftp.OVERWRITE);
            // chSftp.quit();
            // log.debug("sftp upload file:"+path+fileName+" success!");
            // } catch (Exception e) {
            // log.error("ssftp upload file:"+path+fileName+" failed,", e);
            // }finally{
            // try {
            // channel.closeChannel();
            // } catch (Exception e) {
            // log.error("channel.closeChannel failed,",e);
            // }
            // }
        }
        log.debug("save end : " + namepath);
        return httpPath;
    }

    public  static String saveFile(InputStream file, String filePath) {
        FileUtils.init();
        String httpPath = "";

        httpPath = FileUtils.user_head.get("HTTP_ROOT") + filePath;

        String namepath = FileUtils.user_head.get("DIR_ROOT") + filePath;

        // 同步FTP
        if ("Y".equals(FileUtils.user_head.get("IS_SYNC_FTP"))) {
            // 设置主机ip，端口，用户名，密码
            @SuppressWarnings("unchecked")
            Map<String, String> sftpDetails = (Map<String, String>) FileUtils.fileConfgs
                    .get("sftp");
            SFTPChannel channel = new SFTPChannel();

            try {
                ChannelSftp chSftp = channel.getChannel(sftpDetails, 600000);

                log.info("pwd : " + chSftp.pwd());

                String[] folders = namepath.split( "/" );
                folders[folders.length - 1] = null;

                chSftp.cd("/");

                for ( String folder : folders ) {
                    if ( folder != null && folder.length() > 0 ) {
                        try {
                            chSftp.cd( folder );
                        }
                        catch ( SftpException e ) {
                            try {
                                chSftp.mkdir( folder );
                                chSftp.cd( folder );
                            } catch (SftpException e1) {
                                log.error(e1);
                            }
                        }
                    }
                }

                chSftp.put(file, namepath, ChannelSftp.OVERWRITE);
                chSftp.quit();
                log.info("sftp upload file:" + namepath + " success!");
            } catch (Exception e) {
                log.error("ssftp upload file:" + namepath + " failed,", e);
            } finally {
                try {
                    channel.closeChannel();
                } catch (Exception e) {
                    log.error("channel.closeChannel failed,", e);
                }
            }

        }
        log.debug("save end : " + namepath);

        return httpPath;
    }
}
