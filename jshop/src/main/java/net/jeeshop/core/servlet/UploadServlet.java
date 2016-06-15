package net.jeeshop.core.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.jeeshop.core.front.SystemManager;
import net.jeeshop.core.util.PinYinUtil;
import net.jeeshop.core.util.StrUtils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.slf4j.LoggerFactory;

/**
 * jQuery文件上传
 * @author Administrator
 *
 */
public class UploadServlet extends HttpServlet{
	private static final long serialVersionUID = 1L; 
	
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UploadServlet.class);
	  
    /** 
     * 实现多文件的同时上传 
     */   
    public void doGet(HttpServletRequest request,  
            HttpServletResponse response) throws ServletException, IOException {  
        //设置接收的编码格式  
        request.setCharacterEncoding("UTF-8");  
        Date date = new Date();//获取当前时间  
        //SimpleDateFormat sdfFileName = new SimpleDateFormat("yyyyMMddHHmmss");  
        SimpleDateFormat sdfFolder = new SimpleDateFormat("yyMMdd");  
        //String fileRealPath = "";//文件存放真实地址  
        
        String folder = request.getParameter("folder");//上传文件存放的目录;
        String nameModel = request.getParameter("nameModel");//文件命名模式; 
        String saveModel = request.getParameter("saveModel");//文件上传模式; loc:本地保存 ; server:服务器模式,上传到服务器
        //名称  界面编码 必须 和request 保存一致..否则乱码  
        String name = request.getParameter("name");  
        String id = request.getParameter("id");  //内容的ID,必须先添加内容，然后才能上传图片
        //check//
        if("new".equals(nameModel) && StrUtils.isEmpty(name)){
        	this.msg(response, false, null,"nameModel check error");
        	return;
        }else{
        	if(StrUtils.isEmpty(id)){
        		id = sdfFolder.format(date);
        	}
        }
        /*if(StrUtils.isEmpty(id)){
        	this.msg(response, false, null,"id check error");
        	return;
        }*/
        
        logger.info("id:{} ,name:{} ,nameModel:{} ,saveModel:{} ,folder:{}",id,name,nameModel,saveModel,folder);
        //文件保存的根目录;
        String serverModel = SystemManager.getInstance().getProperty("file.server.Model");
        String savePath = SystemManager.getInstance().getProperty("file."+serverModel+".savePath");
        String httpRoot = SystemManager.getInstance().getProperty("file."+serverModel+".httpPath");
        
    	//httpRoot = "upload/"+id+"/";
    	//savePath = this.getServletConfig().getServletContext().getRealPath("/") + "upload\\" + id +"\\";
        if(StrUtils.isNotEmpty(folder)){
        	httpRoot += folder+"/";
            savePath += folder+"/";
        }
        httpRoot += id+"/";
        savePath += id+"/";
        
    	File file = new File(savePath);  
        if (!file.isDirectory()) {
            file.mkdirs();  
        }
        //---------------------------------
           
        String fileRealPath="";//磁盘全路径
        String fileRealResistPath = "";//虚拟路径
        // 获得容器中上传文件夹所在的物理路径  
        try {
        	//创建磁盘文件工厂  
            DiskFileItemFactory fac = new DiskFileItemFactory(); 
            //创建servlet文件上传组件  
            ServletFileUpload upload = new ServletFileUpload(fac);  
            upload.setHeaderEncoding("UTF-8");
            //文件列表  
            List<FileItem> fileList = upload.parseRequest(request);
            
            //保存后的文件名  
            String fileName = null;  
            // 遍历上传文件写入磁盘  
            Iterator<FileItem> it = fileList.iterator();  
            while (it.hasNext()) {
            	 FileItem item =  it.next();     
                 //如果不是普通表单域，当做文件域来处理  
                 if(!item.isFormField()){
                	 fileName = item.getName().substring(item.getName().lastIndexOf("\\")+1).replace(" ", "");
                	 String fileExt = fileName.substring(fileName.lastIndexOf("."));//获取文件后缀名  
                	 if("new".equals(nameModel)){
                		 fileName = name +fileExt;
                	 }else{
                		 // use old
                		 fileName = PinYinUtil.getPingYin(fileName);
                	 }
                    //
                    fileRealPath = savePath + fileName;//+ formatName;//文件存放真实地址  
                    
                    logger.info("save File: {}",fileName);
                      
                    BufferedInputStream in = new BufferedInputStream(item.getInputStream());// 获得文件输入流  
                    BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(new File(fileRealPath)));// 获得文件输出流  
                    Streams.copy(in, outStream, true);// 开始把文件写到你指定的上传文件夹  
                    //上传成功，则插入数据库  
                    if (new File(fileRealPath).exists()) {  
                        //虚拟路径赋值  
                        fileRealResistPath = httpRoot+fileName;
                        logger.info(" fileRealResistPath: {}",fileRealResistPath);
                        //保存到数据库  
                        //System.out.println("虚拟路径:"+fileRealResistPath);  
	                }
            	}
            }   
        } catch (org.apache.commons.fileupload.FileUploadException ex) {
        	logger.error(" update faild,", ex);
           this.msg(response, false, null,"没有上传文件");
           return;  
		}
        this.msg(response, true, fileRealResistPath,null);
    }
    
    private void msg(HttpServletResponse response,boolean status,String filePath,String msg){
    	String json ="";
    	if(status){
    		json = "{\"status\":1,\"filePath\":\""+filePath+"\"}";
    	}else{
    		json = "{\"status\":0,\"msg\":\""+msg+"\"}";
    	}
    	response.setContentType("text/html;charset=utf-8");
    	try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
   
    public void doPost(HttpServletRequest req, HttpServletResponse resp)  
            throws ServletException, IOException {  
        doGet(req, resp);  
    }  
}
