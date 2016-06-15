package net.jeeshop.core;

import java.util.List;

import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.core.front.SystemManager;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class ServersManager<E extends PagerModel, DAO extends  DaoManager<E>> implements Services<E>{
	
	protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected SystemManager systemManager;
	protected DAO dao;

	public DAO getDao() {
		return dao;
	}

	public abstract void setDao(DAO dao);

	/**
	 * 添加
	 * 
	 * @param e
	 * @return
	 */
	public int insert(E e) {
		if(e==null){
			throw new NullPointerException();
		}
		return dao.insert(e);
	}

	/**
	 * 批量添加
	 * 
	 * @param e
	 * @return
	 */
//	public void insertList(List<E> list) {
//		if(list==null)
//			throw new NullPointerException();
//		
//		for(int i=0;i<list.size();i++){
//			dao.insert(list.get(i));
//		}
//	}

	/**
	 * 删除
	 * 
	 * @param e
	 * @return
	 */
	public int delete(E e) {
		if(e==null){
			throw new NullPointerException();
		}
		return dao.delete(e);
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	public int deletes(String[] ids) {
		if (ids == null || ids.length == 0) {
			throw new NullPointerException("id不能全为空！");
		}
		
		for (int i = 0; i < ids.length; i++) {
			if(StringUtils.isBlank(ids[i])){
				throw new NullPointerException("id不能为空！");
			}
			dao.deleteById(Integer.parseInt(ids[i]));
		}
		return 0;
	}

	/**
	 * 修改
	 * 
	 * @param e
	 * @return
	 */
	public int update(E e) {
		if(e==null){
			throw new NullPointerException();
		}
		return dao.update(e);
	}

	/**
	 * 查询一条记录
	 * 
	 * @param e
	 * @return
	 */
	public E selectOne(E e) {
		return dao.selectOne(e);
	}

	/**
	 * 分页查询
	 * 
	 * @param e
	 * @return
	 */
	public PagerModel selectPageList(E e) {
		return dao.selectPageList(e);
	}
	
	public List<E> selectList(E e) {
		return dao.selectList(e);
	}

	@Override
	public E selectById(String id) {
		return dao.selectById(id);
	}
}