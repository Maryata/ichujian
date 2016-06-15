package com.org.mokey.system.entiy.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;

import com.org.mokey.system.entiy.TSysFunction;
import com.org.mokey.util.JSONUtil;

public class MenuModel implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7113923854935827L;
	private static final Logger log = Logger.getLogger(MenuModel.class);
	/**
	 * key是父节点的ID，可以使一个菜单或者一个功能 value是子功能的列表
	 */
	private Map<String, Map<String, TSysFunction>> parentSubListMap;
	/**
	 * key是功能的ID列表 value为功能
	 */
	private Map<String, TSysFunction> functionIdMap;

	/**
	 * 用户看到的菜单
	 */
	private MenuTree menuTree;

	/**
	 * key是模组的ID， value是当前模组,
	 */
	//private Map<String, TSysModule> moduleIdMap;

	/**
	 * ROOT下的主要模块使用json格式
	 */
	private String menuJSONString;
	private List autoLoadList;
	private Map autoLoadMap;

	private Map subTreeMap = new TreeMap();
	private boolean userIsSA;
	private MenuTree linkMenuTree;

	/**
	 * key是功能的ID列表 value为空
	 */
	public Map<String, TSysFunction> getFunctionIdMap() {
		return functionIdMap;
	}

	public void setFunctionIdMap(Map<String, TSysFunction> functionIdMap) {
		this.functionIdMap = functionIdMap;
	}

	/**
	 * key是父节点的ID，可以使一个菜单或者一个功能 value是子功能的列表
	 */
	public Map<String, Map<String, TSysFunction>> getParentSubListMap() {
		return parentSubListMap;
	}

	public void setParentSubListMap(
			Map<String, Map<String, TSysFunction>> parentSubListMap) {
		this.parentSubListMap = parentSubListMap;
	}

	/**
	 * 菜单树的对象
	 */
	public MenuTree getMenuTree() {
		return menuTree;
	}

	public void setMenuTree(MenuTree menuTree) {
		this.menuTree = menuTree;
	}

	/**
	 * 菜单树的json显示格式字符串
	 * 
	 * @return
	 */
	public String getMenuJSONString() {
		//log.debug("start getMenuJSONString...");
		try {
			List<MenuTree> list = getMenuTree().getChildren();
			if (list != null) {
				JSONArray jsonArray = JSONArray.fromObject(list);
				//log.debug("transfer ok");
				menuJSONString = jsonArray.toString();
			}
		} catch (RuntimeException e) {
			log.error(" Json error", e);
			throw e;
		}

		return menuJSONString;
	}

	/**
	 * 菜单树的json显示格式字符串
	 * 
	 * @return
	 */
	public static String getMenuJSONString(MenuTree menuTree) {
		//log.debug("start getMenuJSONString...");
		String menuJSONString = "";
		try {
			List<MenuTree> list = menuTree.getChildren();
			if (list != null) {
				JSONArray jsonArray = JSONArray.fromObject(list);
				//log.debug("transfer ok");
				menuJSONString = jsonArray.toString();
			}
		} catch (RuntimeException e) {
			log.error(" Json error", e);
			throw e;
		}
		return menuJSONString;
	}

	/**
	 * 菜单树的json显示格式字符串
	 * 
	 * @param menuTree
	 *            树对象
	 * @param strArray
	 *            去掉不需要转换的字符串数组
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getMenuJSONString(MenuTree menuTree, String[] strArray) {
		//log.debug("start getMenuJSONString...");
		String menuJSONString = "";

		try {
			List<MenuTree> list = menuTree.getChildren();
			if (list != null) {
				JsonConfig config = JSONUtil.kickProperty(strArray,MenuTree.class); // 去掉不需要转换的字符串
				JSONArray jsonArray = JSONArray.fromObject(list, config);
				//log.debug("transfer ok");
				menuJSONString = jsonArray.toString();
			}
		} catch (RuntimeException e) {
			log.error(" Json error", e);
			throw e;
		}

		return menuJSONString;
	}

	/**
	 * 菜单树的json显示格式字符串(,List列表中.同一字段选择性部分过滤)
	 * 
	 * @param menuTree
	 *            树对象
	 * @param strArray
	 *            去掉不需要转换的字符串数组
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getMenuJSONStringByMode(MenuTree menuTree,
			String[] strArray) {
		//log.debug("start getMenuJSONString...");
		String menuJSONString = "";
		try {
			List<MenuTree> list = menuTree.getChildren();
			if (list != null) {
				JsonConfig config = JSONUtil.kickPropertyByMode(strArray,MenuTree.class); // 去掉不需要转换的字符串
				JSONArray jsonArray = JSONArray.fromObject(list, config);
				//log.debug("transfer ok");
				menuJSONString = jsonArray.toString();
			}
		} catch (RuntimeException e) {
			log.error(" Json error", e);
			throw e;
		}
		return menuJSONString;
	}

	/**
	 * 菜单树的json显示格式字符串(,List列表中.同一字段选择性部分过滤)
	 * 
	 * @param menuTree
	 *            树对象
	 * @param strArray
	 *            去掉不需要转换的字符串数组
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getMenuJSONStringByIsNeedCheck(MenuTree menuTree,
			String[] strArray) {
		//log.debug("start getMenuJSONString...");
		String menuJSONString = "";
		try {
			List<MenuTree> list = menuTree.getChildren();
			if (list != null) {
				JsonConfig config = JSONUtil.kickPropertyByMode(strArray,MenuTree.class.getName()); // 去掉不需要转换的字符串
				// for(MenuTree m: list){
				// System.out.println("临时测试"+m+"list长度"+list.size());
				// }
				JSONArray jsonArray = JSONArray.fromObject(list, config);
				//log.debug("transfer ok");
				menuJSONString = jsonArray.toString();
			}
		} catch (RuntimeException e) {
			log.error(" Json error", e);
			throw e;
		}
		return menuJSONString;
	}

	public void setMenuJSONString(String menuJSONString) {
		this.menuJSONString = menuJSONString;
	}

	public void setAutoLoadList(List autoLoadList) {
		this.autoLoadList = autoLoadList;

	}

	public void setAutoLoadMap(Map autoLoadList) {
		this.autoLoadMap = autoLoadList;

	}

	/**
	 * 菜单树的json显示格式字符串
	 * 
	 * @return
	 */
	public String getAutoJSONString() {
		//log.debug("start getAutoJSONString...");
		try {
			List<MenuTree> list = this.autoLoadList;
			if (list != null) {
				JSONArray jsonArray = JSONArray.fromObject(list);
				//log.debug("transfer ok");
				menuJSONString = jsonArray.toString();
			}
		} catch (RuntimeException e) {
			log.error(" Json error", e);
			throw e;
		}

		return menuJSONString;
	}

	/**
	 * 菜单树的json map显示格式字符串
	 * 
	 * @return
	 */
	public String getAutoMapJsonString() {
		//log.debug("start getAutoJSONString...");
		try {
			if (autoLoadMap != null) {
				JSONObject jsonArray = JSONObject.fromObject(autoLoadMap);
				//log.debug("transfer ok");
				menuJSONString = jsonArray.toString();
			}
		} catch (RuntimeException e) {
			log.error(" Json error", e);
			throw e;
		}

		return menuJSONString;
	}

	public Map getAutoLoadMap() {
		return autoLoadMap;
	}

	/**
	 * 仅仅取root下的2层出来
	 * 
	 * @return
	 */
	public String getTopMenuJsonString() {
		List<MenuTree> list = getMenuTree().getChildren();
		// log.debug(" getMenuTree() == " +getMenuTree());

		if (list == null || list.size() == 0) {
			throw new IllegalArgumentException("sub Menu is Empty");
		}
		// ROOT下第一层
		List topList = new ArrayList();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {

			MenuTree menu = (MenuTree) iterator.next();
			TopMenu top = deeplyCopyMenu(menu);

			// ROOT下第二层
			List subList = new ArrayList();
			top.setChildren(subList);
			topList.add(top);
			if (menu.getChildren() == null || menu.getChildren().size() == 0) {
				continue;
			}
			for (Iterator iterator2 = menu.getChildren().iterator(); iterator2
					.hasNext();) {
				MenuTree name = (MenuTree) iterator2.next();
				TopMenu subTop = deeplyCopyMenu(name);

				// 添加到子树列表
				if (name.getChildren() != null
						&& name.getChildren().size() >= 0) {
					subTreeMap.put(name.getId(), name.getChildren());
				}

				subList.add(subTop);
			}

		}
		JSONArray jsonArray = JSONArray.fromObject(topList);
		//log.debug("transfer ok");
		return jsonArray.toString();

	}

	/**
	 * @param name
	 * @return
	 */
	private TopMenu deeplyCopyMenu(MenuTree name) {
		TopMenu subTop = new TopMenu();
		subTop.setId(name.getId());
		subTop.setPid(name.getPid());
		subTop.setText(name.getText());
		subTop.setPcls(name.getPcls());
		subTop.setPurl(name.getPurl());
		subTop.setLocation(name.getLocation());
		subTop.setActivation(name.isActivation());
		subTop.setTitle(name.getTitle());
		subTop.setPicture(name.getPicture());
		return subTop;
	}

	public Map getSubTreeMap() {
		if (subTreeMap == null || subTreeMap.size()<=0) {
			getTopMenuJsonString();// 重新计算
		}
		return subTreeMap;
	}

	public void setSubTreeMap(Map subTreeMap) {
		this.subTreeMap = subTreeMap;
	}


	public void setUserIsSA(boolean b) {
		userIsSA = b;

	}

	public boolean getUserIsSA() {
		return userIsSA;
	}


	public void setLinkMenuTree(MenuTree menuTree) {
		linkMenuTree = menuTree;

	}

	public MenuTree getLinkMenuTree() {
		return linkMenuTree;
	}

}
