package net.jeeshop.core.freemarker.fn;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.jeeshop.core.front.SystemManager;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * Created by dylan on 15-1-26.
 */
public class SysDicGetter implements TemplateMethodModelEx {
    @SuppressWarnings("rawtypes")
	@Override
    public Object exec(List arguments) throws TemplateModelException {
    	//System.out.println("xxxx"+arguments);
    	Map<String,String> dics = SystemManager.getInstance().getSystemDicts(arguments.get(0).toString());
    	//System.out.println("dics"+dics);
    	if(dics==null){
    		dics = new LinkedHashMap<String,String>();
    	}
        return dics ;
    }
}
