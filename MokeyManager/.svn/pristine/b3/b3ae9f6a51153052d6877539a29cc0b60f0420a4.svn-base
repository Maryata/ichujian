package com.org.mokey.basedata.sysinfo.dao.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.org.mokey.basedata.sysinfo.dao.SupplierDao;
import com.org.mokey.basedata.sysinfo.entiy.Supplier;
import com.org.mokey.basedata.sysinfo.util.ISupplierConst;
import com.org.mokey.basedata.sysinfo.util.ProcessUtil;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.common.util.JdbcTemplateUtils;
import com.org.mokey.util.StrUtils;

public class SupplierDaoImpl implements SupplierDao {
	private static final Logger log = Logger.getLogger( SupplierDaoImpl.class );
	private JdbcTemplate jdbcTemplate;

	@Override
	public Map<String, Object> checkCodeandName(String code, String name) { // save
		// check
		// TODO Auto-generated method stub
		Map<String, Object> retMap = new HashMap<String, Object>();
		String sql = "";
		List<Object> args = new ArrayList<Object>();
		if ( StrUtils.isNotEmpty( code ) ) {
			sql = "select * from T_BASE_SUPPLIER t where t.C_SUPPLIER_CODE=?";
			args.add( code );
		}
		if ( StrUtils.isNotEmpty( name ) ) {
			sql = "select * from T_BASE_SUPPLIER t where t.C_SUPPLIER_NAME=?";
			args.add( name );
		}
		List<Map<String,Object>> count = jdbcTemplate.queryForList( sql, args.toArray() );
		if ( count.size() > 0 ) {
			retMap.put( "isExits", 1 );
		} else {
			retMap.put( "isExits", 0 );
		}
		return retMap;
	}

	@Override
	public Map<String, Object> checkCodeandNameBymodify(String code,
			String name, String id) { // save modify
		// TODO Auto-generated method stub

		Map<String, Object> retMap = new HashMap<String, Object>();
		List<Object> args = new ArrayList<Object>();
		String sql = "";
		if ( StrUtils.isNotEmpty( code ) ) {
			sql = "select count(*) from T_BASE_SUPPLIER where C_SUPPLIER_CODE=? and c_id<>?";
			args.add( code );
			args.add( id );
		}
		if ( StrUtils.isNotEmpty( name ) ) {
			sql = "select count(*) from T_BASE_SUPPLIER where C_SUPPLIER_NAME=? and c_id<>?";
			args.add( name );
			args.add( id );
		}
		int count = jdbcTemplate.queryForObject( sql, args.toArray(),Integer.class );
		if ( count > 0 ) {
			retMap.put( "isExits", 1 );
		} else {
			retMap.put( "isExits", 0 );
		}
		return retMap;
	}

	@Override
	public void deleteSupplier(String id) {
		// TODO Auto-generated method stub
		String sql = "delete from  T_BASE_SUPPLIER where C_ID=?";
		jdbcTemplate.update( sql, new Object[] { id } );
	}

	@Override
	public Map<String, Object> getSupplierList(String code, String name,
			int start, int limit) {
		// TODO Auto-generated method stub
		Map<String, Object> ret = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer(
				"select count(*) from T_BASE_SUPPLIER t where 1=1" );
		List<String> argsList = new ArrayList<String>();
		if ( StrUtils.isNotEmpty( code ) ) {
			sql.append( " and t.C_SUPPLIER_CODE = ?" );
			argsList.add( code );
		}
		if ( StrUtils.isNotEmpty( name ) ) {
			sql.append( " and t.C_SUPPLIER_NAME like ?" );
			argsList.add( "%" + name + "%" );
		}
		int count = jdbcTemplate.queryForObject( sql.toString(), argsList
				.toArray(),Integer.class );
		sql.append( " order by t.c_supplier_code asc  " );
		String sql1 = DaoUtil.addfy_oracle( sql, start, limit, argsList )
				.toString().replace( "count(*)", "*" );
		List<Map<String,Object>> list = jdbcTemplate.queryForList( sql1, argsList.toArray() );
		ret.put( "count", count );
		ret.put( "list", list );
		return ret;
	}

	@Override
	public void saveSupplier(Map<String, Object> map) {
		Object _name = map.get( "C_SUPPLIER_NAME" );
		if(null == _name || _name.toString().isEmpty()) map.remove( "C_SUPPLIER_NAME" );
			
		// TODO Auto-generated method stub
		Object id_old = map.get( "C_ID" );
		if ( null != id_old && !id_old.toString().isEmpty() ) {
			String sql = "select count(1) from T_BASE_SUPPLIER t where t.c_id="
					+ id_old;
			List<Map<String, Object>> cn = jdbcTemplate.queryForList( sql );
			map.put( "c_modity_time", new Date() );
			if ( cn.size() > 0 ) {
				Map<String, Object> wMap = new HashMap<String, Object>();
				wMap.put( "C_ID", id_old );
				JdbcTemplateUtils.updateDataByMap( jdbcTemplate, map, wMap,
						"T_BASE_SUPPLIER" );
			} else {
				String sqlid = JdbcTemplateUtils.getSeqId( jdbcTemplate,
						"SEQ_SYS_SUPPLIER" );
				map.put( "C_ID", sqlid );
				map.put( "c_modity_time", new Date() );
				JdbcTemplateUtils.saveDataByMap( jdbcTemplate, map,
						"T_BASE_SUPPLIER" );
			}
		} else {
			String sqlid = JdbcTemplateUtils.getSeqId( jdbcTemplate,
					"SEQ_SYS_SUPPLIER" );
			map.put( "C_ID", sqlid );
			map.put( "c_modity_time", new Date() );
			JdbcTemplateUtils.saveDataByMap( jdbcTemplate, map,
					"T_BASE_SUPPLIER" );
		}
	}

	/**
	 * /ichujian/public/app/
	 */
	private final String APK_BASE_PATH = "/ichujian/public/app/";
	/**
	 * source/ichujian_android/
	 */
	private final String SOURCE_PATH = APK_BASE_PATH
			+ "source/ichujian_android/";

	public void decodingAndBuildingApk(String code) throws IOException,
			InterruptedException, DocumentException {
		log.info( "begin........................" );
		

		Supplier supplier = null;
		if ( null != code ) { // 定制生成单个代理商
			supplier = _find(
					"SELECT C_SUPPLIER_CODE, C_COMPANY,C_CONTACTS,C_EMAIL,C_ID,C_LEVE,C_LOCATION,C_LOGO_URI,C_MODITY_TIME,C_PHONE,C_SYSDATE,C_TYPE,C_URL,C_SUPPLIER_NAME,C_COLOR,C_ABOUT_LOGO_URI,C_MAIN_COMMON_SLOGAN,C_MAIN_MAIN_LOGO_URI,C_ABOUT_INFO,C_MAIN_MAIN_BUY,C_SHOPPING_URI,C_COMMON_COPYRIGHT,C_HELPANDFEEDBACK,C_WEBSITE,C_BACKGROUND_LANCH,C_BACKGROUND_HOME,C_FLOATWINDOW_MIUIV4V5,C_FLOATWINDOW_MIUIV6,C_FLOATWINDOW_EMUI3,C_HOST_WEBSITE_WETHER_SHOW,C_HUAWEI_EMUI,C_AID_ONE,C_ISEXCHANGE FROM T_BASE_SUPPLIER WHERE C_SUPPLIER_CODE = ? AND C_ISLIVE='1'",
					new Object[] { code } );
			// 反编译
			_decoding();
			_process( supplier );
			// 处理代理商资源替换
		} else {
			// 定制生成所有代理商
			String sql = "SELECT C_SUPPLIER_CODE, C_COMPANY,C_CONTACTS,C_EMAIL,C_ID,C_LEVE,C_LOCATION,C_LOGO_URI,C_MODITY_TIME,C_PHONE,C_SYSDATE,C_TYPE,C_URL,C_SUPPLIER_NAME,C_COLOR,C_ABOUT_LOGO_URI,C_MAIN_COMMON_SLOGAN,C_MAIN_MAIN_LOGO_URI,C_ABOUT_INFO,C_MAIN_MAIN_BUY,C_SHOPPING_URI,C_COMMON_COPYRIGHT,C_HELPANDFEEDBACK,C_WEBSITE,C_BACKGROUND_LANCH,C_BACKGROUND_HOME,C_FLOATWINDOW_MIUIV4V5,C_FLOATWINDOW_MIUIV6,C_FLOATWINDOW_EMUI3,C_HOST_WEBSITE_WETHER_SHOW,C_HUAWEI_EMUI,C_AID_ONE,C_ISEXCHANGE FROM T_BASE_SUPPLIER WHERE C_ISLIVE='1'";
			String countSql = "SELECT COUNT(1) FROM T_BASE_SUPPLIER WHERE C_ISLIVE='1'";
			int count = getJdbcTemplate().queryForObject( countSql, null,
					Integer.class );
			int start = 0, limit = 1000; // 默认一次处理1000条代理商数据
			while ( count - start >= 1 ) {
				StringBuffer sb = DaoUtil.addfy_oracle(
						new StringBuffer( sql ), start, limit );

				List<Supplier> list = _query( sb.toString(), null );
				for ( int i = 0; i < list.size(); ++i ) {
					// 反编译
					_decoding(); 
					_process( list.get( i ) ); // 处理代理商资源替换
				}

				start += limit;
			}
		}
	}

	private void _decoding() throws IOException, InterruptedException {
		StringBuffer buffer = new StringBuffer();
		buffer.append( "apktool d -f " ).append( APK_BASE_PATH ).append(
				"/ichujian_android.apk  -o " ).append( SOURCE_PATH ); // cmd.exe /c // -o
		ProcessUtil pu = new ProcessUtil( null, null, null );
		pu.process( buffer.toString() );
		log.info( "Decoding........................" + buffer.toString() );
	}

	private void _building(String code) throws Exception {
		StringBuffer buffer = new StringBuffer();
		///ichujian/public/apk_base/source/ichujian_android
		buffer.append( "apktool b " ).append( SOURCE_PATH ).append( " -o " ).append( // -o
				APK_BASE_PATH ).append( code ).append( "/ichujian_android_temp.apk" ); // cmd.exe
		// /c

		ProcessUtil pu = new ProcessUtil( null, null, null );
		pu.process( buffer.toString() );
		
		sign(APK_BASE_PATH + code + "/ichujian_android_temp.apk", APK_BASE_PATH + code + "/ichujian_android.apk","/ichujian/public/app/");
		
		log.info( "Building........................" + buffer.toString() );
	}
	
	/**密钥 名称*/  
    private static final String SECRET_KEY_NAME = "mokey_android.android";
    /**密钥 密码*/  
    private static final String SECRET_KEY_PASS = "mokey_android";
    /**密钥 别名*/  
    private static final String SECRET_KEY_ALIAS = "cn";
	private void sign(String targetPath, String resultPath, String keystorePath) throws Exception {
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
        	log.error("zipFile failed...  "+buffer);
        }
        
        long t1 =System.currentTimeMillis();
		log.debug("sign.time: "+(t1-t0)/1000.0);
	}

	private boolean isEmpty(String str) {
		return null == str || str.isEmpty();
	}

	@SuppressWarnings("unused")
	private void _inputStreamToOutputStream(InputStream in, OutputStream out)
			throws IOException {
		int len;
		byte[] data = new byte[4096];
		while ( (len = in.read( data )) > 0 ) {
			out.write( data, 0, len );
		}
		in.close();
		out.flush();
		out.close();
	}

	private void _process(Supplier supplier) throws IOException,
			InterruptedException, DocumentException {
		if ( supplier != null ) {
			log.info( "process supplier : " + supplier.getCode() );
			// 修改logo
			if ( !isEmpty( supplier.getLogouri() ) ) {
				File outFile = new File(
						SOURCE_PATH + ISupplierConst.LOGO_URI );
				if(outFile.exists()) {
					FileOutputStream out = new FileOutputStream( outFile );
					URL url = new URL( supplier.getLogouri() );
					InputStream in = url.openStream();
					ImageIO.write( ImageIO.read( in ), "png", out );
				}
				//_inputStreamToOutputStream( in, out );
			}
			// 修改导航栏图片
			/*if ( !isEmpty( supplier.getColor() ) ) {
				File outFile = new File(
						SOURCE_PATH + ISupplierConst.COLOR_URI );
				if(outFile.exists()) {
					URL url = new URL( supplier.getColor() );
					InputStream in = url.openStream();
					FileOutputStream out = new FileOutputStream( outFile );
					_inputStreamToOutputStream( in, out );
				}
				
			}
			*/
			if(!isEmpty( supplier.getColor() )) {
				File outFile = new File(
						SOURCE_PATH + ISupplierConst.COLOR_KEY );
				if(outFile.exists()) {
					URL url = new URL( supplier.getColor() );
					InputStream in = url.openStream();
					FileOutputStream out = new FileOutputStream( outFile );
					ImageIO.write( ImageIO.read( in ), "png", out );
					//_inputStreamToOutputStream( in, out );
				}
			}
			if ( !isEmpty( supplier.getAboutLogouri() ) ) {
				File outFile = new File(
						SOURCE_PATH + ISupplierConst.ABOUT_APP_LOGO );
				if(outFile.exists()) {
					URL url = new URL( supplier.getAboutLogouri() );
					InputStream in = url.openStream();
					FileOutputStream out = new FileOutputStream( outFile );
					ImageIO.write( ImageIO.read( in ), "png", out );
					//_inputStreamToOutputStream( in, out );
				}
			}
			if ( !isEmpty( supplier.getMainLogouri() ) ) {
				File outFile = new File(
						SOURCE_PATH + ISupplierConst.MAIN_LOGO );
				if ( outFile.exists() ) {
					URL url = new URL( supplier.getMainLogouri() );
					InputStream in = url.openStream();
					FileOutputStream out = new FileOutputStream( outFile );
					ImageIO.write( ImageIO.read( in ), "png", out );
					//_inputStreamToOutputStream( in, out );
				}
			}
			if ( !isEmpty( supplier.getSlogan() ) ) {
				File outFile = new File(
						SOURCE_PATH + ISupplierConst.SLOGAN_URI );
				if(outFile.exists()) {
					URL url = new URL( supplier.getSlogan() );
					InputStream in = url.openStream();
					FileOutputStream out = new FileOutputStream( outFile );
					ImageIO.write( ImageIO.read( in ), "png", out );
					//_inputStreamToOutputStream( in, out );
				}
			}
			if ( !isEmpty( supplier.getShoppingUri() ) ) {
				File outFile = new File(
						SOURCE_PATH + ISupplierConst.SHOPPING_URI );
				if(outFile.exists()) {
					URL url = new URL( supplier.getShoppingUri() );
					InputStream in = url.openStream();
					FileOutputStream out = new FileOutputStream( outFile );
					ImageIO.write( ImageIO.read( in ), "png", out );
					//_inputStreamToOutputStream( in, out );
				}
			}
			if ( !isEmpty( supplier.getBackgroundLanch() ) ) {
				File outFile = new File(
						SOURCE_PATH + ISupplierConst.BACKGROUND_LANCH );
				if(outFile.exists()) {
					URL url = new URL( supplier.getBackgroundLanch() );
					InputStream in = url.openStream();
					FileOutputStream out = new FileOutputStream( outFile );
					ImageIO.write( ImageIO.read( in ), "png", out );
				}
			}
			if ( !isEmpty( supplier.getBackgroundHome() ) ) {
				File outFile = new File(
						SOURCE_PATH + ISupplierConst.BACKGROUND_HOME );
				if(outFile.exists()) {
					URL url = new URL( supplier.getBackgroundHome() );
					InputStream in = url.openStream();
					FileOutputStream out = new FileOutputStream( outFile );
					ImageIO.write( ImageIO.read( in ), "png", out );
				}
			}
			if ( !isEmpty( supplier.getFloatwindowMiuiv4v5() ) ) {
				File outFile = new File(
						SOURCE_PATH + ISupplierConst.FLOATWINDOW_MIUIV4V5 );
				if(outFile.exists()) {
					URL url = new URL( supplier.getFloatwindowMiuiv4v5() );
					InputStream in = url.openStream();
					FileOutputStream out = new FileOutputStream( outFile );
					ImageIO.write( ImageIO.read( in ), "png", out );
				}
			}
			if ( !isEmpty( supplier.getFloatwindowMiuiv6() ) ) {
				File outFile = new File(
						SOURCE_PATH + ISupplierConst.FLOATWINDOW_MIUIV6 );
				if(outFile.exists()) {
					URL url = new URL( supplier.getFloatwindowMiuiv6() );
					InputStream in = url.openStream();
					FileOutputStream out = new FileOutputStream( outFile );
					ImageIO.write( ImageIO.read( in ), "png", out );
				}
			}
			if ( !isEmpty( supplier.getFloatwindowEmui3() ) ) {
				File outFile = new File(
						SOURCE_PATH + ISupplierConst.FLOATWINDOW_EMUI3 );
				if(outFile.exists()) {
					URL url = new URL( supplier.getFloatwindowEmui3() );
					InputStream in = url.openStream();
					FileOutputStream out = new FileOutputStream( outFile );
					ImageIO.write( ImageIO.read( in ), "png", out );
				}
			}
			
			
			if ( !isEmpty( supplier.getHuaweiEmui() ) ) {
				File outFile = new File(
						SOURCE_PATH + ISupplierConst.HUAWEI_EMUI );
				if(outFile.exists()) {
					URL url = new URL( supplier.getHuaweiEmui() );
					InputStream in = url.openStream();
					FileOutputStream out = new FileOutputStream( outFile );
					ImageIO.write( ImageIO.read( in ), "png", out );
				}
			}
			if ( !isEmpty( supplier.getAidOne() ) ) {
				File outFile = new File(
						SOURCE_PATH + ISupplierConst.AID_ONE );
				if(outFile.exists()) {
					URL url = new URL( supplier.getAidOne() );
					InputStream in = url.openStream();
					FileOutputStream out = new FileOutputStream( outFile );
					ImageIO.write( ImageIO.read( in ), "png", out );
				}
			}
			
			
			File stringsXml = new File( SOURCE_PATH + ISupplierConst.STRINGS );
			FileInputStream in = new FileInputStream( stringsXml );
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			int i;
			// 转化为字节数组流
			while ( (i = in.read()) != -1 ) {
				byteArrayOutputStream.write( i );
			}
			in.close();
			// 把文件存在一个字节数组中
			byte[] filea = byteArrayOutputStream.toByteArray();
			byteArrayOutputStream.close();
			String encoding = "UTF-8";
			String fileaString = new String( filea, encoding );
			Document doc = DocumentHelper.parseText( fileaString );
			Element root = doc.getRootElement();

			for ( Iterator<Element> it = root.elementIterator(); it.hasNext(); ) {
				Element e = it.next();
				String nameValue = e.attributeValue( "name" );
				if ( ISupplierConst.COMMON_APP_NAME.equals( nameValue ) ) {
					if ( !isEmpty( supplier.getName() ) ) {
						e.setText( supplier.getName() );
					}
					continue;
				}
				if ( ISupplierConst.ABOUT_KEY.equals( nameValue ) ) { // 关于信息
					if ( !isEmpty( supplier.getAboutInfo() ) ) {
						e.setText( supplier.getAboutInfo() );
					}
					continue;
				}
				if ( ISupplierConst.WEBSITE_KEY.equals( nameValue ) ) { // 官网链接
					if ( !isEmpty( supplier.getWebSite() ) ) {
						e.setText( supplier.getWebSite() );
					}
					continue;
				}
				if ( ISupplierConst.BUY_KEY.equals( nameValue ) ) { // 购买信息
					if ( !isEmpty( supplier.getBuy() ) ) {
						e.setText( supplier.getBuy() );
					}
					continue;
				}
				if ( ISupplierConst.HELPANDFEEDBACK_KEY.equals( nameValue ) ) { // 帮助和反馈
					if ( !isEmpty( supplier.getHelpAndfeedback() ) ) {
						e.setText( supplier.getHelpAndfeedback() );
					}
					continue;
				}
				if ( ISupplierConst.ICHUJIAN_ISSHOW_HELP.equals( nameValue ) ) { // 是否显示帮助
					e.setText( "1" );
					continue;
				}
				if ( ISupplierConst.COPYRIGHT_KEY.equals( nameValue ) ) { // 版权
					e.setText( "" );
					//if ( !isEmpty( supplier.getCopyright() ) ) {
					//	e.setText( supplier.getCopyright() );
					//}
					continue;
				}
				if ( ISupplierConst.CUSTOM_URL.equals( nameValue ) ) { // 一键购膜url
					if ( !isEmpty( supplier.getUrl() ) ) {
						e.setText( supplier.getUrl() );
					}
					continue;
				}
				if ( ISupplierConst.HOST_WEBSITE_WETHER_SHOW.equals( nameValue ) ) { // 是否显示站点
					if ( !isEmpty( supplier.getHostWebsiteWetherShow() ) ) {
						e.setText( supplier.getHostWebsiteWetherShow() );
					}
					continue;
				}
				if(ISupplierConst.MAIN_FEEDBACK_DESCRIBE.equals( nameValue )) { // 帮助反馈描述
					e.setText( "" );
					continue;
				}
				if(ISupplierConst.BUY_TYPE.equals( nameValue )) {
					if(!isEmpty( supplier.getType() )) {
						e.setText( supplier.getType() );
					}
				}
				if(ISupplierConst.EXCHANGE.equals( nameValue )) {
					if(!isEmpty( supplier.getExchange() )) {
						e.setText( supplier.getExchange() );
					}
				}
			}

			FileOutputStream out = new FileOutputStream( stringsXml );
			out.write( doc.asXML().getBytes() );
			out.flush();
			out.close();
			log.info( "write........................" );

			// 编译
			try {
				_building( supplier.getCode() );
			} catch ( Exception e ) {
				log.error( e );
			}
		}
	}

	class SupplierRowMapper implements RowMapper<Supplier> {
		// 实现ResultSet到Supplier实体的转换
		public Supplier mapRow(ResultSet rs, int rowNum) throws SQLException {
			Supplier m = new Supplier();
			m.setCode( rs.getNString( 1 ) );
			m.setCompany( rs.getNString( 2 ) );
			m.setContacts( rs.getNString( 3 ) );
			m.setEmail( rs.getNString( 4 ) );
			m.setId( rs.getNString( 5 ) );
			m.setLeve( rs.getNString( 6 ) );
			m.setLocation( rs.getNString( 7 ) );
			m.setLogouri( rs.getNString( 8 ) );
			m.setModityTime( rs.getDate( 9 ) );
			m.setPhone( rs.getNString( 10 ) );
			m.setSysdate( rs.getDate( 11 ) );
			m.setType( rs.getNString( 12 ) );
			m.setUrl( rs.getNString( 13 ) );
			m.setName( rs.getNString( 14 ) );

			m.setColor( rs.getNString( 15 ) );
			m.setAboutLogouri( rs.getNString( 16 ) );
			m.setSlogan( rs.getNString( 17 ) );
			m.setMainLogouri( rs.getNString( 18 ) );
			m.setAboutInfo( rs.getNString( 19 ) );
			m.setBuy( rs.getNString( 20 ) );
			m.setShoppingUri( rs.getNString( 21 ) );
			m.setCopyright( rs.getNString( 22 ) );
			m.setHelpAndfeedback( rs.getNString( 23 ) );
			m.setWebSite( rs.getNString( 24 ) );

			m.setBackgroundLanch( rs.getNString( 25 ) );
			m.setBackgroundHome( rs.getNString( 26 ) );
			m.setFloatwindowMiuiv4v5( rs.getNString( 27 ) );
			m.setFloatwindowMiuiv6( rs.getNString( 28 ) );
			m.setFloatwindowEmui3( rs.getNString( 29 ) );
			m.setHostWebsiteWetherShow( rs.getNString( 30 ) );
			
			m.setHuaweiEmui( rs.getNString( 31 ) );
			m.setAidOne( rs.getNString( 32 ) );

			m.setExchange( rs.getNString( 33 ));

			return m;
		}
	};

	private List<Supplier> _query(String sql, Object[] args) {
		try {
			return getJdbcTemplate().query( sql, args, new SupplierRowMapper() );
		} catch ( Exception e ) {
			e.printStackTrace();
		}

		return null;
	}

	private Supplier _find(String sql, Object[] args) {
		List<Supplier> suppliers = _query(
				"SELECT C_SUPPLIER_CODE, C_COMPANY,C_CONTACTS,C_EMAIL,C_ID,C_LEVE,C_LOCATION,C_LOGO_URI,C_MODITY_TIME,C_PHONE,C_SYSDATE,C_TYPE,C_URL,C_SUPPLIER_NAME,C_COLOR,C_ABOUT_LOGO_URI,C_MAIN_COMMON_SLOGAN,C_MAIN_MAIN_LOGO_URI,C_ABOUT_INFO,C_MAIN_MAIN_BUY,C_SHOPPING_URI,C_COMMON_COPYRIGHT,C_HELPANDFEEDBACK,C_WEBSITE,C_BACKGROUND_LANCH,C_BACKGROUND_HOME,C_FLOATWINDOW_MIUIV4V5,C_FLOATWINDOW_MIUIV6,C_FLOATWINDOW_EMUI3,C_HOST_WEBSITE_WETHER_SHOW,C_HUAWEI_EMUI,C_AID_ONE,C_ISEXCHANGE FROM T_BASE_SUPPLIER WHERE C_SUPPLIER_CODE = ? AND C_ISLIVE='1'",
				args );
		if ( null != suppliers && suppliers.size() >= 1 )
			return suppliers.get( 0 );

		return null;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Map<String,Object>> getSuppliers() {
		String sql = "select C_ID,C_SUPPLIER_CODE,C_COMPANY as C_SUPPLIER_NAME from T_BASE_SUPPLIER ";
		return jdbcTemplate.queryForList( sql );
	}

}
