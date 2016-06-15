package com.sys.game.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sys.commons.AbstractAction;
import com.sys.util.StrUtils;

public class GameBaseAction extends AbstractAction{
	
	private static final long serialVersionUID = -772162987046240079L;
	
	protected String out;
	
	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}
	
	protected boolean isEmpty(Object o) {
		return StrUtils.isEmpty( o );
	}
	
	protected void defaultCount(int grade, List<Map<String, Object>> tempList) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put( "C_COUNT", 0 );
		m.put( "C_GRADE", grade );
		tempList.add( m );
	}
}
