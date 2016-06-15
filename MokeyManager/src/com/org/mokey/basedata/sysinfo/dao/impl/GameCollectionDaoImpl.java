package com.org.mokey.basedata.sysinfo.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.org.mokey.basedata.sysinfo.dao.GameCollectionDao;
import com.org.mokey.common.util.DaoUtil;
import com.org.mokey.util.PathUtil;
import com.org.mokey.util.StrUtils;

/**
 * 	游戏帮：游戏合集
 */
public class GameCollectionDaoImpl implements GameCollectionDao {
	
	private static final Logger LOGGER = Logger.getLogger(GameCollectionDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	// 获取游戏合集
	public List<Map<String, Object>> gameCol(String cid) {
		try {
			StringBuffer sb = new StringBuffer();
			List<Object> list = new ArrayList<Object>();
			String sql = "SELECT * FROM T_GAME_COLLECTION_BASE T WHERE 1 = 1";
			sb.append(sql);
			if(StrUtils.isNotEmpty(cid)){
				sb.append(" AND T.C_ID = ?");
				list.add(cid);
			}
			sb.append(" ORDER BY T.C_TYPE");
			return this.jdbcTemplate.queryForList(sb.toString(),list.toArray());
		} catch (DataAccessException e) {
			LOGGER.error("GameCollectionDaoImpl.gameCol failed, e : " + e);
		}
		return null;
	}

	@Override
	// 添加游戏合集
	public void addCol(String name, String type) {
		try {
			if(StrUtils.isNotEmpty(name) && StrUtils.isNotEmpty(type)){
				String tbName = PathUtil.getConfig("tableName", "tbName.".concat(type));
				String sql = "INSERT INTO T_GAME_COLLECTION_BASE " +
						"(C_ID,C_NAME,C_TNAME,C_TYPE,C_ISLIVE) " +
						"VALUES(SEQ_GAME_COLLECTION_BASE.NEXTVAL,?,?,?,1)";
				Object[] obj = {name, tbName, type};
				this.jdbcTemplate.update(sql,obj);
			}
		} catch (DataAccessException e) {
			LOGGER.error("GameCollectionDaoImpl.addCol failed, e : " + e);
		}
	}

	@Override
	// 删除合集游戏中间表数据
	public void deleteColGame(String cid) {
		try {
			String sql = "DELETE FROM T_GAME_COLLECTION_DETAIL T WHERE T.C_CID = ?";
			Object[] obj = {cid};
			this.jdbcTemplate.update(sql, obj);
		} catch (Exception e) {
			LOGGER.error("GameCollectionDaoImpl.deleteColGame failed, e : " + e);
		}
	}
	
	@Override
	// 删除游戏合集
	public void deleteCol(String cid) {
		try {
			if(StrUtils.isNotEmpty(cid)){
				String sql = "DELETE FROM T_GAME_COLLECTION_BASE T WHERE T.C_ID = ?";
				Object[] obj = {cid};
				this.jdbcTemplate.update(sql,obj);
			}
		} catch (DataAccessException e) {
			LOGGER.error("GameCollectionDaoImpl.deleteCol failed, e : " + e);
		}
	}

	@Override
	// 修改合集名称
	public void modifyCol(String cid, String cname, String islive) {
		try {
			if(StrUtils.isNotEmpty(cid)){
				String sql = "UPDATE T_GAME_COLLECTION_BASE T SET T.C_NAME = ?, T.C_ISLIVE = ? WHERE T.C_ID = ?";
				Object[] obj = {cname,islive,cid};
				this.jdbcTemplate.update(sql,obj);
			}
		} catch (DataAccessException e) {
			LOGGER.error("GameCollectionDaoImpl.modifyCol failed, e : " + e);
		}
	}

	@Override
	// 使合集不可用/可用
	public void isvalid(String cid, String islive) {
		try {
			if(StrUtils.isNotEmpty(cid) && StrUtils.isNotEmpty(islive)){
				String sql = "UPDATE T_GAME_COLLECTION_BASE T SET T.C_ISLIVE = ? WHERE T.C_ID = ?";
				Object[] obj = {islive,cid};
				this.jdbcTemplate.update(sql,obj);
			}
		} catch (DataAccessException e) {
			LOGGER.error("GameCollectionDaoImpl.isvalid failed, e : " + e);
		}
	}

	@Override
	// 查询不属于当前合集的游戏
	public List<Map<String, Object>> getGameList(int page, String cid, String type) {
		try {
			if(StrUtils.isNotEmpty(page)){
				String tbName = PathUtil.getConfig("tableName", "tbName.".concat(type));
				List<Object> args = new ArrayList<Object>();
				StringBuffer sql = new StringBuffer("SELECT C_ID, C_NAME FROM " +
						" (SELECT temp.*, ROWNUM RM FROM (SELECT T.C_ID, T.C_NAME " +
						" FROM "+ tbName +" T WHERE T.C_ISLIVE = 1 ");
				if("1".equals(type)){
					sql.append(" AND T.C_TYPE <> 3 ");
				}
				if("2".equals(type)){
					sql.append(" AND T.C_TYPE = 3 ");
				}
				sql.append("AND NOT EXISTS ( SELECT 1 FROM T_GAME_COLLECTION_DETAIL" +
						" C WHERE C.C_ISLIVE = 1 AND T.C_ID = C.C_GID  AND C.C_CID = ? )" +
						" ORDER BY C_NAME ) TEMP WHERE ROWNUM <= (" + page
						+ ") * 5) WHERE RM > (" + page + " - 1) * 5");
				
				args.add(cid);// 合集id
				return this.jdbcTemplate.queryForList(sql.toString(), args.toArray());
//				StringBuffer sql = new StringBuffer("SELECT C_ID, C_NAME, ROUND(C_SIZE/1048576,2) C_SIZE FROM " +
//						" (SELECT temp.*, ROWNUM RM FROM (SELECT T.C_ID, T.C_NAME,T.C_SIZE" +
//						" FROM T_GAME_APP_INFO T WHERE T.C_ISLIVE = 1  AND NOT EXISTS ( " +
//						" SELECT 1 FROM T_GAME_COLLECTION_DETAIL C WHERE C.C_ISLIVE = 1 AND" +
//						" T.C_ID = C.C_GID");
				// 如果是合集1或5，则查询不属于当前合集的所有游戏
//				if("1".equals(cid) || "5".equals(cid)){
//					sql.append(" AND C.C_CID = ? )");
//					args.add(cid);// 合集id
//				// 如果是合集2/3/4，则查询不属于合集2、3、4的所有游戏
//				}else if("2".equals(cid) || "3".equals(cid) || "4".equals(cid)){
//					sql.append(" AND C.C_CID IN(2, 3, 4) )");
//				}
//				sql.append(" ORDER BY C_NAME ) TEMP WHERE ROWNUM <= (" + page + ") * 5) WHERE RM > (" + page + " - 1) * 5");
				// 查询
//				if(StrUtils.isNotEmpty(args)){
//					return this.jdbcTemplate.queryForList(sql.toString(),args.toArray());
//				}else{
//					return this.jdbcTemplate.queryForList(sql.toString());
//				}
			}
		} catch (DataAccessException e) {
			LOGGER.error("GameCollectionDaoImpl.getGameList failed, e : " + e);
		}
		return null;
	}

	@Override
	// 查询不属于当前合集的游戏总条数
	public Integer getTotal(String cid, String type) {
		try {
			String tbName = PathUtil.getConfig("tableName", "tbName.".concat(type));
			List<Object> args = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer("SELECT COUNT(C_ID) FROM (SELECT T.C_ID FROM " + tbName +
					" T WHERE T.C_ISLIVE = 1 AND NOT EXISTS (SELECT C.C_GID FROM " +
					" T_GAME_COLLECTION_DETAIL C WHERE T.C_ID = C.C_GID ");
			if("1".equals(type)){
				sql.append(" AND T.C_TYPE <> 3 ");
			}
			if("2".equals(type)){
				sql.append(" AND T.C_TYPE = 3 ");
			}
			sql.append(" AND C.C_CID = ?))");
			
			args.add(cid);// 合集id
			return this.jdbcTemplate.queryForObject(sql.toString(), args.toArray(), Integer.class);
			
//			StringBuffer sql = new StringBuffer("SELECT COUNT(C_ID) FROM (SELECT T.C_ID FROM T_GAME_APP_INFO" +
//					" T WHERE T.C_ISLIVE = 1 AND NOT EXISTS (SELECT C.C_GID FROM" +
//					" T_GAME_COLLECTION_DETAIL C WHERE T.C_ID = C.C_GID");
			// 如果是合集1或5，则查询不属于当前合集的所有游戏
//			if("1".equals(cid) || "5".equals(cid)){
//				sql.append(" AND C.C_CID = ?))");
//				args.add(cid);// 合集id
//				return this.jdbcTemplate.queryForObject(sql.toString(), args.toArray(), Integer.class);
//			// 如果是合集2/3/4，则查询不属于合集2、3、4的所有游戏
//			}else if("2".equals(cid) || "3".equals(cid) || "4".equals(cid)){
//				sql.append(" AND C.C_CID IN(2, 3, 4)))");
//				return this.jdbcTemplate.queryForObject(sql.toString(), Integer.class);
//			}
		} catch (DataAccessException e) {
			LOGGER.error("GameCollectionDaoImpl.getTotal failed, e : " + e);
		}
		return null;
	}

	
	@Override
	// 对指定id的游戏进行合集分类
	public void handleCol(String cid, String gid, String order) {
		try {
			if(StrUtils.isNotEmpty(cid) && StrUtils.isNotEmpty(gid)){
				String sql = "INSERT INTO T_GAME_COLLECTION_DETAIL" +
						" T(T.C_ID,T.C_GID,T.C_CID,T.C_ISLIVE,T.C_ORDER) " +
						"VALUES (SEQ_GAME_COLLECTION_DETAIL.NEXTVAL,?,?,1,?)";
				Object[] obj = {gid,cid,order};
				this.jdbcTemplate.update(sql,obj);
			}
		} catch (DataAccessException e) {
			LOGGER.error("GameCollectionDaoImpl.handleCol failed, e : " + e);
		}
	}

	@Override
	// 查询游戏是否已经存在于当前合集中
	public Integer isExist(String cid, String gid) {
		try {
			String sql = "SELECT COUNT(*) FROM T_GAME_COLLECTION_DETAIL T WHERE T.C_ISLIVE = 1 AND T.C_CID = ? AND T.C_GID = ?";
			Object[] obj = {cid,gid};
			return this.jdbcTemplate.queryForObject(sql,obj,Integer.class);
		} catch (DataAccessException e) {
			LOGGER.error("GameCollectionDaoImpl.isExist failed, e : " + e);
		}
		return null;
	}

	@Override
	// 条件查询游戏APP
	public List<Map<String, Object>> queryGameCondition(String cid,
			String name, String type, String minSize, String maxSize, int page) {
		try {
			String tbName = PathUtil.getConfig("tableName", "tbName.".concat(type));
			List<Object> list = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer("SELECT T.C_ID, T.C_NAME" +
					" FROM "+tbName+" T WHERE T.C_ISLIVE = 1 ");
			if("1".equals(type)){
				sql.append(" AND T.C_TYPE <> 3 ");
			}
			if("2".equals(type)){
				sql.append(" AND T.C_TYPE = 3 ");
			}
			if(StrUtils.isNotEmpty(name)){// 模糊查询名称
				sql.append(" AND T.C_NAME LIKE ? ");
				list.add("%"+name+"%");
			}
//			if(StrUtils.isNotEmpty(minSize)){// 游戏大小
//				sql.append(" AND T.C_SIZE > ? ");
//				list.add(minSize);
//			}
//			if(StrUtils.isNotEmpty(maxSize)){// 游戏大小
//				sql.append(" AND T.C_SIZE <= ? ");
//				list.add(maxSize);
//			}
			sql.append(" AND NOT EXISTS (SELECT 1 FROM T_GAME_COLLECTION_DETAIL C" +
					" WHERE C.C_ISLIVE = 1 AND T.C_ID = C.C_GID AND C.C_CID = ? )");
			list.add(cid);// 合集id
			/**
			 *  合集1：可以存所有游戏
			 *  合集2：不可以存3合集游戏
			 *  合集3：不可以存2、4合集游戏
			 *  合集4：不可以存3合集游戏
			 *  合集4：可以存所有游戏
			 */
			// 如果是合集1或5，则查询不属于当前合集的所有游戏
//			if("1".equals(cid) || "5".equals(cid)){
//				sql.append(" AND C.C_CID = ? )");
//				list.add(cid);// 合集id
//			// 如果是合集2，则查询不属于合集2、3的所有游戏
//			}else if("2".equals(cid)){
//				sql.append(" AND C.C_CID IN(2, 3))");
//			// 如果是合集4，则查询不属于合集3、4的所有游戏
//			}else if("4".equals(cid)){
//				sql.append(" AND C.C_CID IN(3, 4))");
//			// 如果是合集3，则查询不属于合集2、3、4的所有游戏
//			}else if("3".equals(cid)){
//				sql.append(" AND C.C_CID IN(2, 3, 4))");
//			}
			if(StrUtils.isNotEmpty(list)){
				DaoUtil.addfy_oracle(sql, (page-1)*5, 5, list);
				return this.jdbcTemplate.queryForList(sql.toString(), list.toArray());
			}else{
				DaoUtil.addfy_oracle(sql, (page-1)*5, 5);
				return this.jdbcTemplate.queryForList(sql.toString());
			}
		} catch (Exception e) {
			LOGGER.error("GameCollectionDaoImpl.queryGameCondition failed, e : " + e);
		}
		return null;
	}

	@Override
	// 条件查询游戏总数
	public Integer getTotalCondition(String cid, String name,
			String type, String minSize, String maxSize) {
		try {
			String tbName = PathUtil.getConfig("tableName", "tbName.".concat(type));
			List<Object> list = new ArrayList<Object>();
			
			StringBuffer sql = new StringBuffer("SELECT COUNT(C_ID) FROM " +
					"(SELECT T.C_ID FROM "+tbName+" T WHERE T.C_ISLIVE = 1");
			if("1".equals(type)){
				sql.append(" AND T.C_TYPE <> 3 ");
			}
			if("2".equals(type)){
				sql.append(" AND T.C_TYPE = 3 ");
			}
			if(StrUtils.isNotEmpty(name)){// 模糊查询名称
				sql.append( " AND T.C_NAME LIKE ? ");
				list.add("%"+name+"%");
			}
//			if(StrUtils.isNotEmpty(minSize)){// 游戏大小
//				sql.append( " AND T.C_SIZE > ? ");
//				list.add(minSize);
//			}
//			if(StrUtils.isNotEmpty(maxSize)){// 游戏大小
//				sql.append( " AND T.C_SIZE <= ? ");
//				list.add(maxSize);
//			}
			sql.append(" AND NOT EXISTS (SELECT C.C_GID FROM T_GAME_COLLECTION_DETAIL " +
					"C WHERE T.C_ID = C.C_GID AND C.C_CID = ? ))");
			list.add(cid);// 合集id
			
			// CNT1：所有游戏数量。CNT2：（cid=1/5）1|5合集游戏数量；（cid=2/3/4）2&3&4合集游戏数量
			/**
			 *  合集1：可以存所有游戏
			 *  合集2：不可以存3合集游戏
			 *  合集3：不可以存2、4合集游戏
			 *  合集4：不可以存3合集游戏
			 *  合集4：可以存所有游戏
			 */
			// 如果是合集1或5，则查询不属于当前合集的所有游戏
//			if("1".equals(cid) || "5".equals(cid)){
//				sql.append(" AND C.C_CID = ? ))");
//				list.add(cid);// 合集id
//			// 如果是合集2，则查询不属于合集2、3的所有游戏
//			}else if("2".equals(cid)){
//				sql.append(" AND C.C_CID IN(2, 3)))");
//			// 如果是合集4，则查询不属于合集3、4的所有游戏
//			}else if("4".equals(cid)){
//				sql.append(" AND C.C_CID IN(3, 4)))");
//			// 如果是合集3，则查询不属于合集2、3、4的所有游戏
//			}else if("3".equals(cid)){
//				sql.append(" AND C.C_CID IN(2, 3, 4)))");
//			}
			if(StrUtils.isNotEmpty(list)){
				return this.jdbcTemplate.queryForObject(sql.toString(), list.toArray(), Integer.class);
			}else{
				return this.jdbcTemplate.queryForObject(sql.toString(), Integer.class);
			}
		} catch (DataAccessException e) {
			LOGGER.error("GameCollectionDaoImpl.getTotalCondition failed, e : " + e);
		}
		return null;
	}

	@Override
	// 根据合集id查询游戏
	public List<Map<String, Object>> getGamePageByColId(String cid, int page, String type) { 
		try {
			String tbName = PathUtil.getConfig("tableName", "tbName.".concat(type));
			StringBuffer sb = new StringBuffer("SELECT A.C_ID C_ID," +
					" A.C_NAME C_NAME, C.C_ORDER C_ORDER FROM T_GAME_COLLECTION_DETAIL" +
					" C, "+ tbName +" A WHERE C.C_ISLIVE = 1 AND A.C_ISLIVE = 1 AND" +
					" C.C_CID = ? AND C.C_GID = A.C_ID ");
			List<Object> args = new ArrayList<Object>();
			args.add(cid);
			// 组织分页语句
			StringBuffer sql = DaoUtil.addfy_oracle(sb, " C.C_ORDER, A.C_ID ", (page-1)*5, 5, args);
			// 查询
			return this.jdbcTemplate.queryForList(sql.toString(),args.toArray());
		} catch (Exception e) {
			LOGGER.error("GameCollectionDaoImpl.getGamePageByColId failed, e : " + e);
		}
		return null;
	}

	@Override
	// 根据合集id查询合集对应的游戏总数
	public Integer getTotalCol(String cid, String type) {
		try {
			if(StrUtils.isNotEmpty(cid)){
				String tbName = PathUtil.getConfig("tableName", "tbName.".concat(type));
				StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM "+ tbName +" T," +
						" T_GAME_COLLECTION_DETAIL C WHERE T.C_ISLIVE = 1 AND" +
						" C.C_ISLIVE = 1 AND T.C_ID = C.C_GID AND C.C_CID = ?");;
				Object[] args = {cid};
				return this.jdbcTemplate.queryForObject(sql.toString(), args, Integer.class);
			}
		} catch (Exception e) {
			LOGGER.error("GameCollectionDaoImpl.getTotalCol failed, e : " + e);
		}
		return null;
	}

	@Override
	// 从指定合集中移除游戏
	public void removeGame(String cid, String gid) {
		try {
			String sql = "DELETE FROM T_GAME_COLLECTION_DETAIL T WHERE T.C_CID = ? AND T.C_GID = ? AND T.C_ISLIVE = 1";
			Object[] obj = {cid,gid};
			this.jdbcTemplate.update(sql, obj);
		} catch (Exception e) {
			LOGGER.error("GameCollectionDaoImpl.removeGame failed, e : " + e);
		}
	}

	@Override
	// 更新当前合集中的游戏排序
	public void updateOrder(String cid, String gid, String order) {
		try {
			String sql = "UPDATE T_GAME_COLLECTION_DETAIL T SET T.C_ORDER = ? " +
					"WHERE T.C_GID = ? AND T.C_CID = ? AND T.C_ISLIVE = 1";
			Object[] obj = {order,gid,cid};
			this.jdbcTemplate.update(sql, obj);
		} catch (Exception e) {
			LOGGER.error("GameCollectionDaoImpl.updateOrder failed, e : " + e);
		}
	}

	@Override
	// 条件查询合集中游戏总数
	public Integer getColTotalCondition(String cid, String name, String type) {
		try {
			String tbName = PathUtil.getConfig("tableName", "tbName.".concat(type));
			List<Object> list = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer("SELECT COUNT(1) FROM" +
					" T_GAME_COLLECTION_DETAIL C, "+tbName+" T WHERE C.C_ISLIVE = 1" +
							" AND T.C_ISLIVE = 1 AND T.C_ID = C.C_GID AND C.C_CID = ?");
			list.add(cid);
			if(StrUtils.isNotEmpty(name)){
				sql.append(" AND T.C_NAME LIKE ?");
				list.add("%" + name + "%");
			}
			return this.jdbcTemplate.queryForObject(sql.toString(), list.toArray(), Integer.class);
		} catch (DataAccessException e) {
			LOGGER.error("GameCollectionDaoImpl.getColTotalCondition failed, e : " + e);
		}
		return null;
	}

	@Override
	// 条件查询合集中游戏
	public List<Map<String, Object>> queryColGameCondition(String cid,
			String name, int page, String type) {
		try {
			String tbName = PathUtil.getConfig("tableName", "tbName.".concat(type));
			List<Object> args = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer("SELECT C_ID, C_NAME " +
					" ,C_ORDER FROM (SELECT TEMP.*, ROWNUM RM FROM (SELECT T.C_ID, T.C_NAME," +
					" C.C_ORDER FROM "+tbName+" T, T_GAME_COLLECTION_DETAIL C  WHERE T.C_ISLIVE = 1 " +
					"AND C.C_ISLIVE = 1 AND T.C_ID = C.C_GID AND C.C_CID = ?");
			args.add(cid);
			if(StrUtils.isNotEmpty(name)){
				sql.append(" AND T.C_NAME LIKE ?");
				args.add("%" + name + "%");
			}
			sql.append(" ORDER BY C_ORDER) TEMP WHERE ROWNUM <= " + page + " * 5) WHERE RM > (" +
					page + " - 1) * 5");
			return this.jdbcTemplate.queryForList(sql.toString(), args.toArray());
		} catch (Exception e) {
			LOGGER.error("GameCollectionDaoImpl.queryColGameCondition failed, e : " + e);
		}
		return null;
	}
}
