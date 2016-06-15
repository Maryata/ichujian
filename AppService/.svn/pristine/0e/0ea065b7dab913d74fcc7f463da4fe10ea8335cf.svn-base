package com.sys.util.file;

import com.sys.util.StrUtils;
import com.sys.util.StreamUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class FileUtils {
	
	private static String upload_root;
	
	private static String http_root;
	
	/** Logger available to subclasses */
	private static Logger log = (Logger.getLogger(FileUtils.class));
	
	//static Map<String , String> fileTypes = null;
	
	public static Map<String , Object> fileConfgs = null;
	public static Map<String , String> user_head = null;
 
	
	public static void init (){
		/*if(fileTypes==null){
			fileTypes = new HashMap<String,String>();
			fileTypes.put("PPT", ".ppt,.pptx,.pps");
			fileTypes.put("WORD", ".doc,.docx");
			fileTypes.put("IMAGE", ".jpg,.jpeg,.png,.bpm");
			fileTypes.put("PDF", ".pdf");
			fileTypes.put("VIDEO", ".flv,.rmvb,.mov,.mp4");
			fileTypes.put("AUDIO", ".mp3");
		}*/
		if(fileConfgs!=null){
			return;
		}
		
		String path = StreamUtil.getRootPath() + "WEB-INF/conf/uploadFile.json";
//		String path = StreamUtil.getRootPath() + "WEB-INF/conf/uploadFile.bak.test.json";
		
		String str;
		try {
			str = StreamUtil.readFile(path);
			
	        Map<String,Object> classMap = new HashMap<String,Object>();
	        classMap.put("sftp", Map.class);
	        //classMap.put("sftp2", Map.class);
	        classMap.put("userhead", Map.class);
 
	        //使用暗示，直接将json解析为指定自定义对象，其中List完全解析,Map没有完全解析
	        fileConfgs = (Map) JSONObject.toBean(JSONObject.fromObject(str),Map.class , classMap);
	        user_head = (Map<String, String>) fileConfgs.get("userhead");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		init ();
		//deleteRs("d:/JHResource/360downloads");
		//cpoyForTransfer(new File("D:\\JHResource\\dynamicPic\\039ec9d8-5ca1-411a-8cd3-c04f0b94acdf.jpg")
		//,new File("d:\\JHResource\\files\\互动\\039ec9d8-5ca1-411a-8cd3-c04f0b94acdf.jpg"));
	}
	/**
	 * 获取文件时否是当前类别
	 * @param filename
	 * @param key
	 * @return
	 */
	public static boolean getIsKeyFileType(String filename,String key){
		init ();
		/*String type = filename.substring(filename.lastIndexOf("."),filename.length());
		//log.debug("type:"+type);
		//log.debug("type:"+type);
		String keys = fileTypes.get(key);
		//log.debug("keys:"+keys);
		if(keys!=null){
			String [] keysArr = keys.split(",");
			for(String k : keysArr){
				//log.debug("k:"+k);
				if (type.equalsIgnoreCase(k)) {
					return true;
				}
			}
		}*/
		return false;
	}
	
	/***
	 * 图像目录
	 * @return
	 */
	public static String getUploadFacePath(){
		String path = getRootpath()+"userHead/";
		File f = new File(path);
		if(!f.exists()){
			f.mkdirs();
		}
		return path;
	}
	
	/***
	 * 图像目录 http
	 * @return
	 */
	public static String getHttpFacePath(HttpServletRequest request){
		return getHttpRootPath(request)+"/userHead/";
	}
	
	/***
	 * 文件目录
	 * @return
	 */
	public static String getUploadPath(String extPath){
		String path = getRootpath()+"files/"+(StrUtils.isNotEmpty(extPath) ?extPath+"/":"" );
		File f = new File(path);
		if(!f.exists()){
			f.mkdirs();
		}
		return path;
	}
	
	public static String getHttpUploadPath(String extPath){
		return getHttpRootpath()+"/files/"+extPath+"/";
	}
	
	/***
	 * 文件目录 http
	 * @return
	 */
	public static String getHttpUploadPath(HttpServletRequest request){
		return getHttpRootPath(request)+"/files/";
	}
	
	/***
	 * 动态缩略图
	 * @return
	 */
	public static String getDynamicPicPath(){
		String path = getRootpath()+"dynamicPic/";
		File f = new File(path);
		if(!f.exists()){
			f.mkdirs();
		}
		return path;
	}
	
	/***
	 * 动态缩略图 http
	 * @return
	 */
	public static String getHttpDynamicPic(HttpServletRequest request){
		return getHttpRootPath(request)+"/dynamicPic/";
	}
	
	
	public static String getHttpRootPath(HttpServletRequest request){
		return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ "/Resource";
	}
	
	public static String getRootpath() {
		if(upload_root==null){
			ResourceBundle loadMedium = ResourceBundle.getBundle("SysParams");
			upload_root = loadMedium.getString("UPLOAD_ROOT");
			http_root = loadMedium.getString("DOWNLOAD_ROOT");//"d:/JHResource/";//StreamUtil.getRootPath();
		}
		return upload_root;
	}
	
	public static String getHttpRootpath() {
		if(http_root==null){
			ResourceBundle loadMedium = ResourceBundle.getBundle("SysParams");
			upload_root = loadMedium.getString("UPLOAD_ROOT");
			http_root = loadMedium.getString("DOWNLOAD_ROOT");//"d:/JHResource/";//StreamUtil.getRootPath();
		}
		return http_root;
	}
	
	/**  
     * 删除文件，可以是单个文件或文件夹  
     * @param   fileName    待删除的文件名  
     * @return 文件删除成功返回true,否则返回false  
     */  
    public static boolean delete(String fileName){   
        File file = new File(fileName);
        if(file.isDirectory()){
        	return deleteDirectory(fileName);
        }else{
        	
        }
        if(!file.exists()){   
            log.debug("删除文件失败："+fileName+"文件不存在");   
            return false;   
        }else{   
            if(file.isFile()){   
                return deleteFile(fileName);   
            }else{   
                return deleteDirectory(fileName); 
            }   
        }   
    }   
       
    /**  
     * 删除单个文件  
     * @param   fileName    被删除文件的文件名  
     * @return 单个文件删除成功返回true,否则返回false  
     */  
    public static boolean deleteFile(String fileName){   
        File file = new File(fileName);   
        if(file.isFile() && file.exists()){   
            boolean result =  file.delete();
            if(!result)
             {
              System.gc();
              file.delete();
             }
            //log.debug("删除单个文件"+fileName+"成功！");   
            return true;   
        }else{   
            log.debug("删除单个文件"+fileName+"失败！");   
            return false;   
        }   
    }   
       
    /**  
     * 删除目录（文件夹）以及目录下的文件  
     * @param   dir 被删除目录的文件路径  
     * @return  目录删除成功返回true,否则返回false  
     */  
    public static boolean deleteDirectory(String dir){   
        //如果dir不以文件分隔符结尾，自动添加文件分隔符   
        if(!dir.endsWith(File.separator)){   
            dir = dir+File.separator;   
        }   
        File dirFile = new File(dir);   
        //如果dir对应的文件不存在，或者不是一个目录，则退出   
        if(!dirFile.exists() || !dirFile.isDirectory()){   
            log.debug("删除目录失败"+dir+"目录不存在！");   
            return false;   
        }   
        boolean flag = true;   
        //删除文件夹下的所有文件(包括子目录)   
        File[] files = dirFile.listFiles();   
        for(int i=0;i<files.length;i++){   
            //删除子文件   
            if(files[i].isFile()){   
                flag = deleteFile(files[i].getAbsolutePath());   
                if(!flag){   
                    break;   
                }   
            }   
            //删除子目录   
            else{   
                flag = deleteDirectory(files[i].getAbsolutePath());   
                if(!flag){   
                    break;   
                }   
            }   
        }   
        if(!flag){   
            log.debug("删除目录失败");   
            return false;   
        }   
        //删除当前目录   
        if(dirFile.delete()){   
            log.debug("删除目录"+dir+"成功！");   
            return true;   
        }else{   
            log.debug("删除目录"+dir+"失败！");   
            return false;   
        }   
    }
    /**  
     * 删除目录(文件夹)下的文件  
     * @param   dir 被删除目录的文件路径  
     * @return  目录删除成功返回true,否则返回false  
     */  
    public static boolean deleteDirectoryFile(String dir){   
        //如果dir不以文件分隔符结尾，自动添加文件分隔符   
        if(!dir.endsWith(File.separator)){   
            dir = dir+File.separator;   
        }   
        File dirFile = new File(dir);   
        //如果dir对应的文件不存在，或者不是一个目录，则退出   
        if(!dirFile.exists() || !dirFile.isDirectory()){   
            log.debug("删除目录失败"+dir+"目录不存在！");   
            return false;   
        }   
        boolean flag = true;   
        //删除文件夹下的所有文件(包括子目录)   
        File[] files = dirFile.listFiles();   
        for(int i=0;i<files.length;i++){   
            //删除子文件   
            if(files[i].isFile()){   
                flag = deleteFile(files[i].getAbsolutePath());   
                if(!flag){   
                    break;   
                }   
            }   
            //删除子目录   
            else{   
                flag = deleteDirectory(files[i].getAbsolutePath());   
                if(!flag){   
                    break;   
                }   
            }   
        }   
        if(!flag){   
            log.debug("删除文件失败");   
            return false;   
        }   
        return flag;
    }
    /**
     * 强制删除目录
     * @param rs
     * @return
     */
    public static boolean deleteRs(String rs){
    	try{
    		if(rs==null || "".equals(rs.trim())){
    			return false;
    		}
    		if(rs.endsWith("/")){
    			rs = rs.substring(0,rs.length()-1);
    		}
    		
    		rs = rs.replace("/", "\\");
	    	String cmdline="cmd.exe /c rd /q /s "+rs;
	    	log.debug(""+cmdline);
	    	Runtime.getRuntime().exec(cmdline);//Process p = 
	    	log.debug("删除目录["+rs+"]成功");
	    	return true;
    	}catch(Exception e){
    		//log.error("",e);
    		log.debug("删除目录["+rs+"]失败");
    		return false;
    	}
    }
    
    public static void cpoyForTransfer(File from,File to){
    	FileInputStream in = null;
    	FileOutputStream out = null;
    	FileChannel inC = null;
    	FileChannel outC = null;
    	try{
    		log.debug("from:["+from+"],to:["+to+"]");
    		if(!from.exists()){
    			log.debug("from not find:"+from);
    			return;
    		}
	        //long time=new Date().getTime();
	        int length=2097152;
	        in=new FileInputStream(from);
	        out=new FileOutputStream(to);
	        inC=in.getChannel();
	        outC=out.getChannel();
	        int i=0;
	        while(true){
	            if(inC.position()==inC.size()){
	                inC.close();
	                outC.close();
	                //return new Date().getTime()-time;
	                return;
	            }
	            if((inC.size()-inC.position())<20971520)
	                length=(int)(inC.size()-inC.position());
	            else
	                length=20971520;
	            inC.transferTo(inC.position(),length,outC);
	            inC.position(inC.position()+length);
	            i++;
	        }
    	}catch(Exception e){
    		log.error("cpoy.forTransfer failed",e);
    	}finally{
    		try{ if(in!=null){in.close();} }catch(Exception e){log.debug("close failed");}
    		try{ if(out!=null){out.close();} }catch(Exception e){log.debug("close failed");}
    		try{ if(inC!=null){inC.close();} }catch(Exception e){log.debug("close failed");}
    		try{ if(outC!=null){outC.close();} }catch(Exception e){log.debug("close failed");}
    	}
    }
    
    public static void cpoyForTransfer(String fromName,String toName){
    	FileInputStream in = null;
    	FileOutputStream out = null;
    	FileChannel inC = null;
    	FileChannel outC = null;
    	try{
    		log.debug("from:["+fromName+"],to:["+toName+"]");
    		File from = new File(fromName);
    		if(!from.exists()){
    			log.debug("from not find:"+from);
    			return;
    		}
    		File to = new File(toName);
	        //long time=new Date().getTime();
	        int length=2097152;
	        in=new FileInputStream(from);
	        out=new FileOutputStream(to);
	        inC=in.getChannel();
	        outC=out.getChannel();
	        int i=0;
	        while(true){
	            if(inC.position()==inC.size()){
	                inC.close();
	                outC.close();
	                //return new Date().getTime()-time;
	                return;
	            }
	            if((inC.size()-inC.position())<20971520)
	                length=(int)(inC.size()-inC.position());
	            else
	                length=20971520;
	            inC.transferTo(inC.position(),length,outC);
	            inC.position(inC.position()+length);
	            i++;
	        }
    	}catch(Exception e){
    		log.error("cpoy.forTransfer failed",e);
    	}finally{
    		try{ if(in!=null){in.close();} }catch(Exception e){log.debug("close failed");}
    		try{ if(out!=null){out.close();} }catch(Exception e){log.debug("close failed");}
    		try{ if(inC!=null){inC.close();} }catch(Exception e){log.debug("close failed");}
    		try{ if(outC!=null){outC.close();} }catch(Exception e){log.debug("close failed");}
    	}
    }

	public static String saveFile(byte[] bytes, String dest) throws IOException {
		File file = createNewFile(dest);
		BufferedOutputStream stream =
				new BufferedOutputStream(new FileOutputStream(file));
		stream.write(bytes);
		stream.close();

		return dest;
	}

	private static File createNewFile(String filename) throws IOException {
		File file = new File(filename);
		File parentFile = file.getParentFile();
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}

		file.createNewFile();

		return file;
	}
}
