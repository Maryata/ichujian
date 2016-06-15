package com.org.mokey.basedata.sysinfo.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.basedata.sysinfo.dao.H5GameCollectionDao;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.util.StrUtils;

/**
 * 获取H5游戏合集
 * @author Maryn
 *
 */
public class H5GameCollectionDaoImpl implements H5GameCollectionDao {

private static final Logger LOGGER = Logger.getLogger(H5GameCollectionDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	// 获取H5游戏合集列表
	public List<Map<String, Object>> h5GameCol() {
		try {
			String sql = "SELECT * FROM T_GAME_H5_COLLECTION_INFO ORDER BY C_ID";
			return this.jdbcTemplate.queryForList(sql);
		} catch (Exception e) {
			LOGGER.error("H5GameCollectionDaoImpl.h5GameCol failed, e : " + e);
		}
		return null;
	}

	@Override
	// 添加游戏合集
	public void h5AddCol(String name) {
		try {
			if(StrUtils.isNotEmpty(name)){
				String sql = "INSERT INTO T_GAME_H5_COLLECTION_INFO (C_ID,C_NAME,C_ISLIVE) " +
						"VALUES(SEQ_GAME_H5_COLLECTION_INFO.NEXTVAL,?,1)";
				Object[] obj = {name};
				this.jdbcTemplate.update(sql,obj);
			}
		} catch (Exception e) {
			LOGGER.error("H5GameCollectionDaoImpl.h5AddCol failed, e : " + e);
		}
	}

	@Override
	// 删除合集游戏中间表数据
	public void h5DeleteColGame(String cid) {
		try {
			String sql = "DELETE FROM T_GAME_COLLECTION_H5_GAME T WHERE T.C_CID = ?";
			Object[] obj = {cid};
			this.jdbcTemplate.update(sql, obj);
		} catch (Exception e) {
			LOGGER.error("H5GameCollectionDaoImpl.h5DeleteColGame failed, e : " + e);
		}
	}
	
	@Override
	// 删除合集
	public void h5DeleteCol(String cid) {
		try {
			String sql = "DELETE FROM T_GAME_H5_COLLECTION_INFO T WHERE T.C_ID = ?";
			Object[] obj = {cid};
			this.jdbcTemplate.update(sql,obj);
		} catch (Exception e) {
			LOGGER.error("H5GameCollectionDaoImpl.h5DeleteCol failed, e : " + e);
		}
	}

	@Override
	// 使合集不可用/可用
	public void h5IsValid(String cid, String islive) {
		try {
			if(StrUtils.isNotEmpty(cid) && StrUtils.isNotEmpty(islive)){
				String sql = "UPDATE T_GAME_H5_COLLECTION_INFO T SET T.C_ISLIVE = ? WHERE T.C_ID = ?";
				Object[] obj = {islive,cid};
				this.jdbcTemplate.update(sql,obj);
			}
		} catch (DataAccessException e) {
			LOGGER.error("H5GameCollectionDaoImpl.h5DeleteCol failed, e : " + e);
		}
	}

	@Override
	// 修改合集名称
	public void h5ModifyCol(String cid, String cname, String islive) {
		try {
			if(StrUtils.isNotEmpty(cid)){
				String sql = "UPDATE T_GAME_H5_COLLECTION_INFO T SET T.C_NAME = ?, T.C_ISLIVE = ? WHERE T.C_ID = ?";
				Object[] obj = {cname,islive,cid};
				this.jdbcTemplate.update(sql,obj);
			}
		} catch (DataAccessException e) {
			LOGGER.error("H5GameCollectionDaoImpl.h5DeleteCol failed, e : " + e);
		}
	}

	@Override
	// 查询不属于当前合集的游戏总条数
	public Integer getTotal(String cid) {
		try {
			List<Object> args = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer("SELECT COUNT(C_ID) FROM (SELECT T.C_ID FROM" +
					" T_GAME_H5_INFO T WHERE T.C_ISLIVE = 1 AND NOT EXISTS (SELECT C.C_GID" +
					" FROM T_GAME_COLLECTION_H5_GAME C WHERE T.C_ID = C.C_GID");
			// 如果是合集4，则查询不属于当前合集的所有游戏
			if("4".equals(cid)){
				sql.append(" AND C.C_CID = ? ))");
				args.add(cid);// 合集id
				return this.jdbcTemplate.queryForObject(sql.toString(),args.toArray(),Integer.class);
			// 如果是合集1/2/3，则查询不属于合集1、2、3的游戏
			}else if("1".equals(cid) || "2".equals(cid) || "3".equals(cid)){
				sql.append(" AND C.C_CID IN(1, 2, 3)))");
				return this.jdbcTemplate.queryForObject(sql.toString(),Integer.class);
			}
		} catch (DataAccessException e) {
			LOGGER.error("H5GameCollectionDaoImpl.getTotal failed, e : " + e);
		}
		return null;
	}

	@Override
	// 查询不属于当前合集的游戏
	public List<Map<String, Object>> getGameList(int page, String cid) {
		try {
			if(StrUtils.isNotEmpty(page)){
				List<Object> args = new ArrayList<Object>();
				StringBuffer sql = new StringBuffer("SELECT T.C_ID,T.C_NAME FROM" +
						" T_GAME_H5_INFO T WHERE T.C_ISLIVE = 1 AND NOT EXISTS" +
						" (SELECT C.C_GID FROM T_GAME_COLLECTION_H5_GAME C WHERE" +
						" C.C_ISLIVE = 1 AND T.C_ID = C.C_GID ");
				// 如果是合集4，则查询不属于当前合集的所有游戏
				if("4".equals(cid)){
					sql.append(" AND C.C_CID = ? )");
					args.add(cid);// 合集id
				// 如果是合集1/2/3，则查询不属于合集1、2、3的游戏
				}else if("1".equals(cid) || "2".equals(cid) || "3".equals(cid)){
					sql.append(" AND C.C_CID IN(1, 2, 3))");
				}
				// 组织分页语句
				DaoUtil.addfy_oracle(sql, (page-1)*5, 5, args);// 默认每页5条数据
				// 查询
				List<Map<String,Object>> list = this.jdbcTemplate.queryForList(sql.toString(),args.toArray());
				return list;
			}
		} catch (DataAccessException e) {
			LOGGER.error("H5GameCollectionDaoImpl.getGameList failed, e : " + e);
		}
		return null;
	}

	@Override
	// 根据合集id查询合集对应的游戏总数
	public Integer getTotalCol(String cid) {
		try {
			if(StrUtils.isNotEmpty(cid)){
				StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM T_GAME_H5_INFO T," +
						" T_GAME_COLLECTION_H5_GAME C WHERE T.C_ISLIVE = 1 AND" +
						" C.C_ISLIVE = 1 AND T.C_ID = C.C_GID AND C.C_CID = ?");
				Object[] args = {cid};
				return this.jdbcTemplate.queryForObject(sql.toString(), args, Integer.class);
			}
		} catch (Exception e) {
			LOGGER.error("H5GameCollectionDaoImpl.getTotalCol failed, e : " + e);
		}
		return null;
	}
	
	@Override
	// 根据合集id查询游戏
	public List<Map<String, Object>> getGamePageByColId(String cid, int page) { 
		try {
			StringBuffer sb = new StringBuffer("SELECT A.C_ID C_ID,A.C_NAME C_NAME," +
					"C.C_ORDER C_ORDER FROM T_GAME_COLLECTION_H5_GAME C,T_GAME_H5_INFO A " +
					"WHERE C.C_ISLIVE = 1 AND A.C_ISLIVE = 1 AND C.C_CID = ? AND C.C_GID = A.C_ID ");
			List<Object> args = new ArrayList<Object>();
			args.add(cid);
			// 组织分页语句
			StringBuffer sql = DaoUtil.addfy_oracle(sb, " C.C_ORDER, A.C_ID ", (page-1)*5, 5, args);
			// 查询
			return this.jdbcTemplate.queryForList(sql.toString(),args.toArray());
		} catch (Exception e) {
			LOGGER.error("H5GameCollectionDaoImpl.getGamePageByColId failed, e : " + e);
		}
		return null;
	}
	
	@Override
	// 条件查询游戏总数
	public Integer getTotalCondition(String cid, String name) {
		try {
			List<Object> args = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer("SELECT COUNT(C_ID) FROM " +
					"(SELECT T.C_ID FROM T_GAME_H5_INFO T WHERE T.C_ISLIVE = 1");
			if(StrUtils.isNotEmpty(name)){// 模糊查询名称
				sql.append(" AND T.C_NAME LIKE ? ");
				args.add("%" + name + "%");
			}
			sql.append(" AND NOT EXISTS (SELECT C.C_GID FROM T_GAME_COLLECTION_H5_GAME " +
					"C WHERE T.C_ID = C.C_GID");
			/**
			 *  合集1：不可以存3合集游戏
			 *  合集2：不可以存2、4合集游戏
			 *  合集3：不可以存3合集游戏
			 *  合集4：可以存所有游戏
			 */
			// 如果是合集1，则查询不属于1、2合集的游戏
			if("1".equals(cid)){
				sql.append(" AND C.C_CID IN(1, 2)))");
			// 如果是合集2，则查询不属于合集1、2、3的所有游戏
			}else if("2".equals(cid)){
				sql.append(" AND C.C_CID IN(1, 2, 3)))");
			// 如果是合集3，则查询不属于合集2、3的所有游戏
			}else if("3".equals(cid)){
				sql.append(" AND C.C_CID IN(2, 3)))");
			// 如果是合集3，则查询不属于当前合集的所有游戏
			}else if("4".equals(cid)){
				sql.append(" AND C.C_CID = ? ))");
				args.add(cid);// 合集id
			}
			if(StrUtils.isNotEmpty(args)){
				return this.jdbcTemplate.queryForObject(sql.toString(), args.toArray(), Integer.class);
			}else{
				return this.jdbcTemplate.queryForObject(sql.toString(), Integer.class);
			}
		} catch (DataAccessException e) {
			LOGGER.error("H5GameCollectionDaoImpl.getTotalCondition failed, e : " + e);
		}
		return null;
	}
	
	@Override
	// 条件查询游戏APP
	public List<Map<String, Object>> queryGameCondition(String cid, String name, int page) {
		try {
			List<Object> args = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer("SELECT T.C_ID, T.C_NAME FROM" +
					" T_GAME_H5_INFO T WHERE T.C_ISLIVE = 1 ");
			if(StrUtils.isNotEmpty(name)){// 模糊查询名称
				sql.append(" AND T.C_NAME LIKE ? ");
				args.add("%" + name + "%");
			}
			sql.append(" AND NOT EXISTS (SELECT 1 FROM T_GAME_COLLECTION_H5_GAME C" +
					" WHERE C.C_ISLIVE = 1 AND T.C_ID = C.C_GID");
			/**
			 *  合集1：不可以存3合集游戏
			 *  合集2：不可以存2、4合集游戏
			 *  合集3：不可以存3合集游戏
			 *  合集4：可以存所有游戏
			 */
			// 如果是合集1，则查询不属于1、2合集的游戏
			if("1".equals(cid)){
				sql.append(" AND C.C_CID IN(1, 2))");
			// 如果是合集2，则查询不属于合集1、2、3的所有游戏
			}else if("2".equals(cid)){
				sql.append(" AND C.C_CID IN(1, 2, 3))");
			// 如果是合集3，则查询不属于合集2、3的所有游戏
			}else if("3".equals(cid)){
				sql.append(" AND C.C_CID IN(2, 3))");
			// 如果是合集3，则查询不属于当前合集的所有游戏
			}else if("4".equals(cid)){
				sql.append(" AND C.C_CID = ? )");
				args.add(cid);// 合集id
			}
			if(StrUtils.isNotEmpty(args)){
				DaoUtil.addfy_oracle(sql, (page-1)*5, 5, args);
				return this.jdbcTemplate.queryForList(sql.toString(), args.toArray());
			}else{
				DaoUtil.addfy_oracle(sql, (page-1)*5, 5);
				return this.jdbcTemplate.queryForList(sql.toString());
			}
		} catch (Exception e) {
			LOGGER.error("H5GameCollectionDaoImpl.queryGameCondition failed, e : " + e);
		}
		return null;
	}
	
	@Override
	// 查询游戏是否已经存在于当前合集中
	public Integer isExist(String cid, String gid) {
		try {
			String sql = "SELECT COUNT(*) FROM T_GAME_COLLECTION_H5_GAME T " +
					"WHERE T.C_ISLIVE = 1 AND T.C_CID = ? AND T.C_GID = ?";
			Object[] obj = {cid, gid};
			return this.jdbcTemplate.queryForObject(sql, obj, Integer.class);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	// 对指定id的游戏进行合集分类
	public void handleCol(String cid, String gid, String order) {
		try {
			if(StrUtils.isNotEmpty(cid) && StrUtils.isNotEmpty(gid)){
				String sql = "INSERT INTO T_GAME_COLLECTION_H5_GAME T" +
						"(T.C_ID,T.C_GID,T.C_CID,T.C_ISLIVE,T.C_ORDER) " +
						"VALUES (SEQ_GAME_COLLECTION_H5_GAME.NEXTVAL,?,?,1,?)";
				Object[] obj = {gid,cid,order};
				this.jdbcTemplate.update(sql,obj);
			}
		} catch (DataAccessException e) {
			LOGGER.error("H5GameCollectionDaoImpl.handleCol failed, e : " + e);
		}
	}

	@Override
	// 更新当前合集中的游戏排序
	public void updateOrder(String cid, String gid, String order) {
		try {
			String sql = "UPDATE T_GAME_COLLECTION_H5_GAME T SET T.C_ORDER = ? " +
					"WHERE T.C_GID = ? AND T.C_CID = ? AND T.C_ISLIVE = 1";
			Object[] obj = {order,gid,cid};
			this.jdbcTemplate.update(sql, obj);
		} catch (Exception e) {
			LOGGER.error("H5GameCollectionDaoImpl.updateOrder failed, e : " + e);
		}
	}

	@Override
	// 从指定合集中移除指定游戏
	public void removeGame(String cid, String gid) {
		try {
			String sql = "DELETE FROM T_GAME_COLLECTION_H5_GAME T WHERE T.C_CID = ? " +
					"AND T.C_GID = ? AND T.C_ISLIVE = 1";
			Object[] obj = {cid,gid};
			this.jdbcTemplate.update(sql, obj);
		} catch (Exception e) {
			LOGGER.error("H5GameCollectionDaoImpl.removeGame failed, e : " + e);
		}
	}

	@Override
	// 根据合集id查询合集游戏（用于生成html）
	public List<Map<String, Object>> getGamePageByColIdHtml(String cid, int page) {
		try {
			StringBuffer sb = new StringBuffer("SELECT G.C_ID,G.C_NAME,G.C_LOGOURL,G.C_APPURL," +
					"G.C_TITLE,G.C_JARNAME FROM T_GAME_COLLECTION_H5_GAME C, T_GAME_H5_INFO G " +
					"WHERE C.C_CID = ? AND C.C_ISLIVE = 1 AND G.C_ISLIVE = 1 AND G.C_ID = C.C_GID");
			List<Object> args = new ArrayList<Object>();
			args.add(cid);
			// 组织分页语句
			StringBuffer sql = DaoUtil.addfy_oracle(sb, " C.C_ORDER, C.C_CNTRORDER, G.C_ID ", (page-1)*10, 10, args);
			// 查询
			return this.jdbcTemplate.queryForList(sql.toString(),args.toArray());
		} catch (Exception e) {
			LOGGER.error("H5GameCollectionDaoImpl.getGamePageByColIdHtml failed, e : " + e);
		}
		return null;
	}

	@Override
	// 查询指定H5游戏被启动的次数
	public Integer startingCountH5(String gid) {
		try {
			String sql = "SELECT COUNT(*) FROM T_GAME_MEMBER_ACTION T WHERE " +
					"T.C_GID = ? AND T.C_SOURCE = 1 AND T.C_TYPE = 4";
			// 查询
			return this.jdbcTemplate.queryForObject(sql, new Object[]{gid}, Integer.class);
		} catch (Exception e) {
			LOGGER.error("H5GameCollectionDaoImpl.startingCountH5 failed, e : " + e);
		}
		return null;
	}

	@Override
	// 条件查询合集中游戏总数
	public Integer getColTotalCondition(String cid, String name) {
		try {
			List<Object> list = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM T_GAME_COLLECTION_H5_GAME C," +
					" T_GAME_H5_INFO T WHERE C.C_ISLIVE = 1 AND T.C_ISLIVE = 1 AND T.C_ID = C.C_GID" +
					" AND C.C_CID = ?");
			list.add(cid);
			if(StrUtils.isNotEmpty(name)){
				sql.append(" AND T.C_NAME LIKE ?");
				list.add("%" + name + "%");
			}
			return this.jdbcTemplate.queryForObject(sql.toString(), list.toArray(), Integer.class);
		} catch (DataAccessException e) {
			LOGGER.error("H5GameCollectionDaoImpl.getColTotalCondition failed, e : " + e);
		}
		return null;
	}

	@Override
	// 条件查询合集中游戏
	public List<Map<String, Object>> queryColGameCondition(String cid,
			String name, int page) {
		try {
			List<Object> args = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer("SELECT T.C_ID, T.C_NAME, C.C_ORDER" +
					" FROM T_GAME_H5_INFO T, T_GAME_COLLECTION_H5_GAME C WHERE T.C_ISLIVE = 1 " +
					"AND C.C_ISLIVE = 1 AND T.C_ID = C.C_GID AND C.C_CID = ?");
			args.add(cid);
			if(StrUtils.isNotEmpty(name)){
				sql.append(" AND T.C_NAME LIKE ?");
				args.add("%" + name + "%");
			}
			DaoUtil.addfy_oracle(sql, " C.C_ORDER ", (page-1)*5, 5, args);
			return this.jdbcTemplate.queryForList(sql.toString(), args.toArray());
		} catch (Exception e) {
			LOGGER.error("H5GameCollectionDaoImpl.queryColGameCondition failed, e : " + e);
		}
		return null;
	}
}
