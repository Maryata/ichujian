package com.qujian;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.qujian.po.Supplier;
import com.qujian.service.ISupplierService;
import com.qujian.util.FileUtil;

@Component
public class ZipApkLinux implements ZipApk {
	
	protected  Logger log = (Logger.getLogger(getClass()));
	
	/**签名目录*/  
    private static final String SIGN_PATH_NAME = "META-INF";
    /**修改文件目录*/  
    private static final String UPDATE_PATH_NAME = "res/raw/license.txt";
    
    /**密钥 名称*/  
    private static final String SECRET_KEY_NAME = "mokey_android.android";
    /**密钥 密码*/  
    private static final String SECRET_KEY_PASS = "mokey_android";
    /**密钥 别名*/  
    private static final String SECRET_KEY_ALIAS = "cn";
    
    private static ISupplierService ss;

	@Resource
	private ISupplierService supplierService;

	@PostConstruct
	public void init() {
		ss = supplierService;
	}
    
	@Override
	public String exec(String licenseCn) {
		//System.out.println("------------------- start");
		long ts =System.currentTimeMillis();
		String resultBase = "";
		String resultPath = "";
		String code = null;
		Supplier supplier = null;
		if(licenseCn.length() >= 11) {
			code = licenseCn.substring( 5,11 ); // 通过licenseCn截取
			supplier = ss.find( code.toUpperCase() );
		}
		
		if(null == supplier) {
			code = "";
			//licenseCn = "CN";
		}
		
		//ss.saveActionDownload(licenseCn,code);
		
		String apkBasePath = "/ichujian/public/app/";
		String apkPath = apkBasePath + code;
		
		String target = apkPath + "/target/";
		String result = apkPath;
		String keystorePath = "/ichujian/public/app/";
		try{
			
			
			//原始文件
			File srcFile = new File(apkPath + "/ichujian_android.apk");
			if(null == supplier || !srcFile.exists()) {
				srcFile = new File(apkBasePath + "ichujian_android.apk");
			} 
			log.info( srcFile.getAbsolutePath() );
			/*File srcFile = new File(apkPath + "/ichujian_android.apk");
			if(!srcFile.exists()) {
				srcFile = new File(apkBasePath + "ichujian_android.apk");
			}*/
			//激活码
			
			String tempPath = licenseCn;// Base64.encode(licenseCn);
			
			//重新打包的文件
			String targetPath = target + "/ichujian_android_"+tempPath+".apk";
			
			//重新打包的文件
			resultBase = tempPath+"/ichujian_android.apk";
			resultPath = result + "/" + resultBase;
			
			if(FileUtil.isHasFile(resultPath)){
				log.debug("resultBase is exists: "+resultBase);
				return  resultBase; 
			}
			
			File targetFile = new File(targetPath);
			
			//检测文件夹
			log.info( _mkdir( apkPath ) );
			log.info( _mkdir(target) );
			log.info( _mkdir(result + "/" + tempPath + "/") );
			//FileUtil.checkDir(target);
			//FileUtil.checkDir(result +"/"+tempPath + "/");
			log.info( target );
			log.info( result + "/" + tempPath );
			//重新打包
			if(licenseCn.length() != 11) {
				uppdateCompress(srcFile, targetFile, licenseCn);
			}
			
			//签名
			sign(targetPath , resultPath, keystorePath);
			
			try{
				if(targetFile.exists()){
					targetFile.delete();
				}
			}catch(Exception e){
				log.error("delete target faild", e);
			}
			
	        long t1 =System.currentTimeMillis();
	        log.debug("time_count: "+(t1-ts)/1000.0 +" retName:"+resultBase);
		} catch (Exception e) {
			log.error("exec faild,", e);
		}
		return  code + "/" + resultBase;
	}
	
	private boolean _mkdir(String path) {
		File file = new File(path);
		if(!file.exists()) {
			return file.mkdir();
		}
		
		return false;
	}

	@Override
	public void sign(String targetPath, String resultPath, String keystorePath) throws Exception {
		long t0 =System.currentTimeMillis();
		//File jdkFile =new File(JDK_BIN_PATH);
		//
		StringBuffer buffer = new StringBuffer();  
		//组合签名命令  
        buffer.append("jarsigner") //cmd.exe /c 
        .append(" -sigalg SHA1withRSA -digestalg SHA1")
        .append(" -keystore ").append(keystorePath).append(SECRET_KEY_NAME) //秘钥目录及名称
        .append(" -storepass ").append(SECRET_KEY_PASS) //密码
        .append(" -signedjar ").append(resultPath).append(" ") //签名保存路径应用名称  
        .append(targetPath).append(" ") //需要签名的应用名称
        .append(SECRET_KEY_ALIAS); //秘钥别名
        log.debug("cmd: "+buffer);
        
        Process process = Runtime.getRuntime().exec(buffer.toString());  //,null,jdkFile
        if(process.waitFor()!=0){
        	log.error("zipFile faild...  "+buffer);
        	String result = FileUtil.toString(process.getInputStream(),System.getProperty("sun.jnu.encoding"));
        	log.error("info: "+result);
        }
        
        long t1 =System.currentTimeMillis();
		log.debug("sign.time: "+(t1-t0)/1000.0);
	}

	@Override
	public void uppdateCompress(File srcFile, File newFile, String licenseCn)
			throws Exception {
		long t0 =System.currentTimeMillis();
		byte[] buf = new byte[2048];
		ZipInputStream zin = new ZipInputStream(new FileInputStream(srcFile));
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(newFile));
		//int i=0;
		ZipEntry entry = zin.getNextEntry();
		
		while (entry != null) {
			/*i++;
			if(i%10 ==0){
				System.out.println("--");
			}*/
			String name = entry.getName();
			//System.out.print(name+"\t");
			//去除签名文件
			if(name.startsWith(SIGN_PATH_NAME)){
				entry = zin.getNextEntry();
				continue;
			}
			//修改license
			if(name.equals(UPDATE_PATH_NAME)){
				//System.out.println("\n----------------------------"+name);
				// Add ZIP entry to output stream.
				out.putNextEntry(new ZipEntry(name));
				
				byte[] srtbyte = licenseCn.getBytes();
				out.write(srtbyte,0,srtbyte.length);  
				
				entry = zin.getNextEntry();
				continue;
			}
			
			/******************************************/
			/*if ( supplier != null ) {
				// 修改logo
				if ( ISupplierConst.LOGO_URI.equals( name ) ) {
					if ( !isEmpty( supplier.getLogouri() ) ) {
						out.putNextEntry( new ZipEntry( name ) );
						URL url = new URL( supplier.getLogouri() );
						InputStream logo = url.openStream();
						int len;
						while ( (len = logo.read( buf )) > 0 ) {
							out.write( buf, 0, len );
						}

						entry = zin.getNextEntry();
						continue;
					}
				}
				// 修改导航栏图片
				if ( ISupplierConst.COLOR_URI.equals( name ) ) {
					if ( !isEmpty( supplier.getColor() ) ) {
						out.putNextEntry( new ZipEntry( name ) );
						URL url = new URL( supplier.getColor() );
						InputStream logo = url.openStream();
						int len;
						while ( (len = logo.read( buf )) > 0 ) {
							out.write( buf, 0, len );
						}

						entry = zin.getNextEntry();
						continue;
					}
				}

				if ( ISupplierConst.STRINGS.equals( name ) ) {
					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
			        int i;  
			        //转化为字节数组流  
			        while ((i = zin.read()) != -1) {  
			            byteArrayOutputStream.write(i);  
			        }  
			        // 把文件存在一个字节数组中  
			        byte[] filea = byteArrayOutputStream.toByteArray();  
			        byteArrayOutputStream.close();  
			        String encoding = "UTF-8";  
			        String fileaString = new String(filea, encoding);  
					
			        JSONObject json = new JSONObject( fileaString );
			        
			        if(!isEmpty( supplier.getAboutInfo() )) { // 关于信息 
			        	json.put( ISupplierConst.ABOUT_KEY, supplier.getAboutInfo() );
					}
			        if(!isEmpty( supplier.getWebSite() )) { // 官网链接 {
			        	json.put( ISupplierConst.WEBSITE_KEY, supplier.getWebSite() );
					}
			        if(!isEmpty( supplier.getBuy() )) { // 购买信息
			        	json.put( ISupplierConst.BUY_KEY, supplier.getBuy() );
			        }
			        if(!isEmpty( supplier.getHelpAndfeedback() )) { // 帮助和反馈
			        	json.put( ISupplierConst.HELPANDFEEDBACK_KEY, supplier.getHelpAndfeedback() );
			        }
			        if(!isEmpty( supplier.getCopyright() )) {
			        	json.put( ISupplierConst.COPYRIGHT_KEY, supplier.getCopyright() );
			        }
			        if(!isEmpty( supplier.getUrl() )) {
			        	json.put( ISupplierConst.CUSTOM_URL, supplier.getUrl() );
			        }
			        
			        json.put( ISupplierConst.ICHUJIAN_ISSHOW_HELP, "1" );
					
					out.putNextEntry( new ZipEntry( name ) );
					byte[] bytes = json.toString().getBytes(encoding);
					InputStream inputStream = new ByteInputStream(bytes, bytes.length);
					int len;
					while ( (len = inputStream.read( buf )) > 0 ) {
						out.write( buf, 0, len );
					}

					entry = zin.getNextEntry();
					continue;
					
				}
				if ( ISupplierConst.ABOUT_APP_LOGO.equals( name ) ) {
					if ( !isEmpty( supplier.getAboutLogouri() ) ) {
						out.putNextEntry( new ZipEntry( name ) );
						URL url = new URL( supplier.getAboutLogouri() );
						InputStream logo = url.openStream();
						int len;
						while ( (len = logo.read( buf )) > 0 ) {
							out.write( buf, 0, len );
						}

						entry = zin.getNextEntry();
						continue;
					}
				}
				if ( ISupplierConst.MAIN_LOGO.equals( name ) ) {
					if ( !isEmpty( supplier.getMainLogouri() ) ) {
						out.putNextEntry( new ZipEntry( name ) );
						URL url = new URL( supplier.getMainLogouri() );
						InputStream logo = url.openStream();
						int len;
						while ( (len = logo.read( buf )) > 0 ) {
							out.write( buf, 0, len );
						}

						entry = zin.getNextEntry();
						continue;
					}
				}
				if ( ISupplierConst.SLOGAN_URI.equals( name ) ) {
					if ( !isEmpty( supplier.getSlogan() ) ) {
						out.putNextEntry( new ZipEntry( name ) );
						URL url = new URL( supplier.getSlogan() );
						InputStream logo = url.openStream();
						int len;
						while ( (len = logo.read( buf )) > 0 ) {
							out.write( buf, 0, len );
						}

						entry = zin.getNextEntry();
						continue;
					}
				}
				if ( ISupplierConst.SHOPPING_URI.equals( name ) ) {
					if ( !isEmpty( supplier.getShoppingUri() ) ) {
						out.putNextEntry( new ZipEntry( name ) );
						URL url = new URL( supplier.getShoppingUri() );
						InputStream logo = url.openStream();
						int len;
						while ( (len = logo.read( buf )) > 0 ) {
							out.write( buf, 0, len );
						}

						entry = zin.getNextEntry();
						continue;
					}
				}
			}*/
			/******************************************/
			
			// Add ZIP entry to output stream.
			out.putNextEntry(new ZipEntry(name));
			// Transfer bytes from the ZIP file to the output file
			int len;
			while ((len = zin.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			entry = zin.getNextEntry();
		}
		// Close the streams
		zin.close();
		out.close();
		//tempFile.delete();
		long t1 =System.currentTimeMillis();
		log.debug("zip.time: "+(t1-t0)/1000.0);
	}

	/* (non-Javadoc)
	 * @see com.qujian.ZipApk#sign(java.lang.String, java.lang.String)
	 */
	@Override
	public void sign(String targetPath, String resultPath) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
