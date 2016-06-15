package net.jeeshop.core.freemarker.fn;

import java.util.List;

import net.jeeshop.web.action.front.orders.CartInfoHelp;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * 获取购物车
 * @author dylan
 */
public class ShoppingCartGetter implements TemplateMethodModelEx {
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        return CartInfoHelp.getInstance().getMyCart();
    }
}
