/**
 * 
 */
package com.qujian.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.qujian.dao.ISupplierDao;
import com.qujian.po.Supplier;

/**
 * @author vpc
 *
 */
@Repository("supplierDao")
public class SupplierDaoImpl extends JdbcDaoSupport implements ISupplierDao {

	@Resource
    private JdbcTemplate jdbcTemplate;    
    
    @PostConstruct    
    public void initSqlMapClient() {    
         super.setJdbcTemplate(jdbcTemplate);  
    }   
	
	class SupplierRowMapper implements RowMapper<Supplier> {
        //实现ResultSet到Supplier实体的转换
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
            
            return m;
        }
    }

    public List<Supplier> query(String sql, Object[] args) {
        try {
			return getJdbcTemplate().query(sql, args, new SupplierRowMapper());
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
		return null;
    }

	@Override
	public List<Map<String, Object>> list(String sql, Object... args) {
		return getJdbcTemplate().queryForList( sql, args );
	}

    @Override
	public void saveActionDownload(String licenseCn, String code) {
		String sql = " insert into t_action_active_download (c_id, c_activecode, c_supplier_code) values (SEQ_ACTION_ACTIVE_DOWNLOAD.nextval,?,?)";
		getJdbcTemplate().update( sql, licenseCn,code);
	}

}
