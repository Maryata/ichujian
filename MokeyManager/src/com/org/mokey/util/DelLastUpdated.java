package com.org.mokey.util;

import java.io.File;
import java.io.FilenameFilter;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DelLastUpdated {
    private static final String MAVEN_REPO_PATH = "E:\\works\\maven\\repository"; 
    private static final String FILE_SUFFIX = "lastUpdated"; 
    private static final Log _log = LogFactory.getLog(DelLastUpdated.class); 
 
    /** 
     * @param args 
     */ 
    public static void main(String[] args) { 
        File mavenRep = new File(MAVEN_REPO_PATH); 
        if (!mavenRep.exists()) { 
            _log.warn("Maven repos is not exist."); 
            return; 
        } 
        File[] files = mavenRep.listFiles((FilenameFilter) FileFilterUtils.directoryFileFilter()); 
        delFileRecr(files,null); 
        _log.info("Clean lastUpdated files finished."); 
    } 
 
    private static void delFileRecr(File[] dirs, File[] files) { 
        if (dirs != null && dirs.length > 0) { 
            for(File dir: dirs){ 
                File[] childDir = dir.listFiles((FilenameFilter) FileFilterUtils 
                .directoryFileFilter()); 
                File[] childFiles = dir.listFiles((FilenameFilter) FileFilterUtils .suffixFileFilter(FILE_SUFFIX)); 
                delFileRecr(childDir,childFiles); 
            } 
        } 
        if(files!=null&&files.length>0){ 
            for(File file: files){ 
                if(file.delete()){ 
                    _log.info("File: ["+file.getName()+"] has been deleted."); 
                } 
            } 
        } 
    } 
 
} 