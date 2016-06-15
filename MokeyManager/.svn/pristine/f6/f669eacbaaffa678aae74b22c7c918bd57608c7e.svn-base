package com.org.mokey.basedata.sysinfo.service.impl;

import java.util.Map;

import com.org.mokey.basedata.sysinfo.dao.H5GameDao;
import com.org.mokey.basedata.sysinfo.service.H5GameService;

public class H5GameServiceImpl implements H5GameService {
	//private static final Logger log = Logger.getLogger( H5GameServiceImpl.class );
	private H5GameDao h5GameDao;
	/* (non-Javadoc)
	 * @see com.org.mokey.basedata.sysinfo.service.H5GameService#delete(java.lang.String)
	 */
	@Override
	public void delete(String id) {
		if( id == null || id.isEmpty() ) return;
		
		h5GameDao.delete( id );
	}
	/* (non-Javadoc)
	 * @see com.org.mokey.basedata.sysinfo.service.H5GameService#list(java.lang.String, int, int)
	 */
	@Override
	public Map<String, Object> list(String name, int start, int limit) {
		return h5GameDao.list( name, start, limit );
	}
	/* (non-Javadoc)
	 * @see com.org.mokey.basedata.sysinfo.service.H5GameService#save(java.util.Map)
	 */
	@Override
	public void save(Map<String, Object> map) {
		h5GameDao.save( map );
	}
	/* (non-Javadoc)
	 * @see com.org.mokey.basedata.sysinfo.service.H5GameService#checkJarname(java.lang.String)
	 */
	@Override
	public Map<String, Object> checkJarname(String jarname) {
		return h5GameDao.checkJarname( jarname );
	}
	/* (non-Javadoc)
	 * @see com.org.mokey.basedata.sysinfo.service.H5GameService#checkJarname(java.lang.String, java.lang.String)
	 */
	@Override
	public Map<String, Object> checkJarname(String jarname, String id) {
		return h5GameDao.checkJarname( jarname, id );
	}
	
	public H5GameDao getH5GameDao() {
		return h5GameDao;
	}
	public void setH5GameDao(H5GameDao h5GameDao) {
		this.h5GameDao = h5GameDao;
	}
}
