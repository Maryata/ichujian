package com.sys.util;

import java.io.File;
import java.util.Timer;

import org.apache.log4j.Logger;

public class MyFileTask extends java.util.TimerTask{
	private Logger log = Logger.getLogger(getClass());
	private String from;
	private String to;
	// 1: copy file ; 2: convert video
	private int fun;
	private Timer timer;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}
	
	public MyFileTask(String from, String to,int fun) {
		this.from = from;
		this.to = to;
		this.fun = fun;
		//log.debug("set from :"+from);
	}
	
	public MyFileTask(String from, String to,int fun,Timer timer) {
		this.from = from;
		this.to = to;
		this.fun = fun;
		this.timer = timer;
		//log.debug("set from :"+from);
	}
	
	public void run() {
		if(fun == 1){
			//log.debug("init copy");
			//FileUtil.cpoyForTransfer(from, to);
			File f = new File(from);
			for(int i=0;i<100;i++){
				if(!f.exists()){
					try {
						//log.debug("sleep "+i);
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
				}else{
					log.debug("copy "+i);
					StreamUtil.fileSave(f,to);
					break;
				}
			}
			//this.timer.cancel();
			//this.timer = null;
			log.debug("end copy");
		}
		
	}

}
