package com.sys.util.jdbc;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.sys.util.PathUtil;

public class JdbcUtil{

	private static Logger log = Logger.getLogger(JdbcUtil.class);
	
	static String url;
	
	public static Connection getConn(){    //获取连接
		Connection conn=null;
    	//连接SQLite的JDBC
        try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection(dataBase());
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} 
    	return conn;
    }
	
    public static String dataBase(){
    	if(url==null){
    		Properties p = new Properties();
            String path = new PathUtil().getWebInfPath();
            InputStream in;
    		try {                               
    			in = new FileInputStream(path+"/WEB-INF/conf/jdbc/DB_jdbc.properties");
    			p.load(in);
    			url = p.getProperty("dataSource.jdbcUrl");
    			log.debug("url:"+url);
    		} catch (Exception e) {
    			log.debug(e.getMessage(),e);
    		}
    	}
       return url;
   }  
	
}
