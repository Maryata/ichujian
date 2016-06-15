package com.sys.util;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * 视频处理 --> jpg
 * @author giles
 *
 */
public class VideoProcess {
	
	/** Logger available to subclasses */
	private Logger log = (Logger.getLogger(getClass()));
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long stime = System.currentTimeMillis();		
		VideoProcess vp = new VideoProcess();
		
		String oldVPath,newVPath,imgPath;
		
		oldVPath="D:\\baiduyundownload\\11度青春之老男孩.rmvb";
		newVPath = "D:\\baiduyundownload\\度青春之老男孩.mp4";
		imgPath = "D:\\baiduyundownload\\out-img.jpg";
		
		oldVPath="D:\\baiduyundownload\\早安美国.flv";
		newVPath = "D:\\baiduyundownload\\早安美国.mp4";
		imgPath = "D:\\baiduyundownload\\out-zaosan.jpg";
		
		try {
			vp.executeCodecs(oldVPath, newVPath, imgPath,"100","200*200");
		} catch (Exception e) {
			e.printStackTrace();
		}
		//vp.processToImg(oldVPath, imgPath,"100","500*380");
		
		long etime = System.currentTimeMillis();
        System.out.println("time:"+ (etime - stime));
	}
	
	/**
     * 视频转码
     * @param ffmpegPath    转码工具的存放路径
     * @param upFilePath    用于指定要转换格式的文件,要截图的视频源文件
     * @param codcFilePath    格式转换后的的文件保存路径
     * @param mediaPicPath    截图保存路径
     * @return
     * @throws Exception
     */
    public boolean executeCodecs(String upFilePath, String codcFilePath,
            String mediaPicPath ,String second_offset,String msize) throws Exception {
    	String ffmpegPath = StreamUtil.getRootPath() + "dll/ffmpeg/ffmpeg.exe";
        // 创建一个List集合来保存转换视频文件为flv格式的命令
        List<String> convert = new ArrayList<String>();
        convert.add(ffmpegPath); // 添加转换工具路径
        convert.add("-i"); // 添加参数＂-i＂，该参数指定要转换的文件
        convert.add(upFilePath); // 添加要转换格式的视频文件的路径
        convert.add("-qscale");     //指定转换的质量
        convert.add("6");
        convert.add("-ab");        //设置音频码率
        convert.add("64");
        convert.add("-ac");        //设置声道数
        convert.add("2");
        convert.add("-ar");        //设置声音的采样频率
        convert.add("22050");
        convert.add("-r");        //设置帧频
        convert.add("24");
        convert.add("-y"); // 添加参数＂-y＂，该参数指定将覆盖已存在的文件
        convert.add(codcFilePath);

        // 创建一个List集合来保存从视频中截取图片的命令
        List<String> cutpic = new ArrayList<String>();
        cutpic.add(ffmpegPath);
        cutpic.add("-i");
        cutpic.add(upFilePath); // 同上（指定的文件即可以是转换为flv格式之前的文件，也可以是转换的flv文件）
        cutpic.add("-y");
        cutpic.add("-f");
        cutpic.add("image2");
        cutpic.add("-ss"); // 添加参数＂-ss＂，该参数指定截取的起始时间
        cutpic.add((second_offset==null ||second_offset.length()==0) ? "17" : second_offset);//25
        cutpic.add("-t"); // 添加参数＂-t＂，该参数指定持续时间
        cutpic.add("0.001"); // 添加持续时间为1毫秒
        cutpic.add("-s"); // 添加参数＂-s＂，该参数指定截取的图片大小
        cutpic.add("800*280"); // 添加截取的图片大小为350*240
        cutpic.add(mediaPicPath); // 添加截取的图片的保存路径

        boolean mark = true;
        log.debug("convert start");
        //ProcessClass.execBuilder(convert);
        log.debug("convert end");
        log.debug("cutpic start");
        ProcessClass.execBuilder(cutpic);
        log.debug("cutpic end");
        
       /* ProcessBuilder builder = new ProcessBuilder();
        try {
            builder.command(convert);
            builder.redirectErrorStream(true);
            builder.start();
            
            builder.command(cutpic);
            builder.redirectErrorStream(true);
            // 如果此属性为 true，则任何由通过此对象的 start() 方法启动的后续子进程生成的错误输出都将与标准输出合并，
            //因此两者均可使用 Process.getInputStream() 方法读取。这使得关联错误消息和相应的输出变得更容易
            builder.start();
        } catch (Exception e) {
            mark = false;
            System.out.println(e);
            e.printStackTrace();
        }*/
        return mark;
    }
	
	
	/**
	 * 视频截取图片
	 * @param videoRealPath 视频文件
	 * @param imageRealPath 转换后的image文件 必须是 <font size="12">jpg<font> 格式
	 * @param second_offset 截取第几秒  defout : 8
	 * @param  msize 设置帧大小 defout : 350*240
	 * @return
	 */
	public boolean processToImg(String videoRealPath,String imageRealPath,String second_offset,String msize){
		try{
			//init cmdExec
			//strBuf = execFilePath+" -i "+videoRealPath+" -y -ss 30 -t 0.001 -s 350*240 -f image2 "+imageRealPath+".jpg";
			String execFilePath = StreamUtil.getRootPath() + "dll/ffmpeg/ffmpeg.exe";
			//log.debug("execFilePath:"+execFilePath);
			//命令
			List<String> commend = new java.util.ArrayList<String>();
			commend.add(execFilePath);
			commend.add("-i");
			commend.add(videoRealPath);
			commend.add("-y");
			commend.add("-ss");//-ss position 搜索到指定的时间 [-]hh:mm:ss[.xxx]的格式也支持
			commend.add((second_offset==null ||second_offset.length()==0) ? "8" : second_offset);//25
			//-t 00:00:05
			//commend.add("-t");commend.add("00:00:05");
			//commend.add("-s"); //设置帧大小
			//commend.add((msize==null ||msize.length()==0) ? "350*240" : msize );//msize "350*240"
			commend.add("-f");
			commend.add("image2");
			commend.add(imageRealPath);
			ProcessClass.execBuilder(commend);
			return true;
		}catch(Exception e){
			log.error("processToImg faile,",e);
			return false;
		}finally{
		}
	}
	
/*	
	
	public static boolean processTransToImg2(String oldfilepath,String newfilename,String newimg){
		System.out.println(oldfilepath+"->"+newfilename+"->"+newimg);
		List<String> commendF = new ArrayList<String>();
		List<String> commendI=new java.util.ArrayList<String>();
		commendF.add("F://mencoder//summer//mencoder");
		commendF.add(oldfilepath);
		commendF.add("-o");
		commendF.add(newfilename);
		commendF.add("-of");
		commendF.add("lavf");
		commendF.add("-oac");
		commendF.add("mp3lame");
		commendF.add("-lameopts");
		commendF.add("abr:br=32:vol=1");
		commendF.add("-srate");
		commendF.add("22050");
		commendF.add("-ovc");
		commendF.add("lavc");
		commendF.add("-lavcopts");
		commendF.add("vcodec=flv:vbitrate=480:mbd=2:v4mv:turbo:vb_strategy=1:last_pred=2:trell");
		commendF.add("-vf");
		commendF.add("scale=400:226");
		
		commendI.add("E://work//mencoder//ffmpeg.exe");
		commendI.add("-i");
		commendI.add(oldfilepath);
		commendI.add("-y");
		commendI.add("-f");
		commendI.add("image2");
		commendI.add("-ss");
		commendI.add("8");
		commendI.add("-t");
		commendI.add("0.001");
		commendI.add("-s");
		commendI.add("100x100");//350*240
		
		commendI.add(newimg);
		Process process = null; 
		try {
			process = ProcessClass.exec(commendF);
			//int pro = process.waitFor();
			//process.destroy();
			//ProcessClass.exec(commendI);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void main(String[] args){
		String oldfilepath = "E://work//mencoder//shanghaitan.rm";
		String newfilename = "E://work//mencoder//zaoan.flv";
		String newimg = "E://work//mencoder//zaoan.jpg";
		System.out.println("--------------");
		processTransToImg2(oldfilepath,newfilename,newimg);
		System.out.println("over");
	}*/

}
