package net.jeeshop.web.action.front.orders;

import javax.annotation.PostConstruct;

import net.jeeshop.core.front.SystemManager;
import net.jeeshop.core.util.StrUtils;
import net.jeeshop.services.front.account.bean.Account;
import net.jeeshop.services.front.order.bean.Order;
import net.jeeshop.web.util.LoginUserHolder;
import net.jeeshop.web.util.RequestHolder;

/**
 * 购物车工具类
 * @author giles
 */
public class CartInfoHelp {
	
	private volatile static CartInfoHelp instance;
	private CartModel model = CartModel.cache;//定义使用的模式;
	private String myCart = "myCart";//用户购物车key ;切换为其他模式
	private String confirm_OrderId = "confirm_OrderId";//返回修改的订单Id
	
	public int maxProductNum = 10000000;//每个单品最大数量

	/**
	 * 类型:session 或者 cache模式; 
	 */
	public enum CartModel {
        session, cache;
    }

	@PostConstruct 
    public void afterPropertiesSet(){
        instance = this;
    }
	public static CartInfoHelp getInstance(){
		if(instance==null){
			synchronized (CartInfoHelp.class){
				if(instance==null){
					instance = new CartInfoHelp();
				}
			}
		}
		return instance;
	}
	
	/**
	 * 获取购物车
	 * @param uid
	 * @return
	 */
	private CartInfo getMyCart(String uid){
		if(model==CartModel.cache){
			return SystemManager.getInstance().getMyCart(uid);
		}else{
			return (CartInfo) RequestHolder.getSession().getAttribute(myCart);
		}
	}
	
	/**
	 * 设置购物车
	 * @param cartInfo
	 * @param uid
	 */
	public void setMyCart(CartInfo cartInfo,String uid){
		if(model==CartModel.cache){
			SystemManager.getInstance().setMyCart(cartInfo, uid);
		}else{
			RequestHolder.getSession().setAttribute(myCart, cartInfo);
		}
	}
	
	/**
	 * 清空购物车
	 * @param uid
	 */
	private void clearMyCart(String uid){
		if(model==CartModel.cache){
			CartInfo cartInfo = new CartInfo();
			SystemManager.getInstance().setMyCart(cartInfo, uid);
		}else{
			CartInfo cartInfo = this.getMyCart(uid);
			if(cartInfo!=null){
				cartInfo.clear();
				cartInfo = null;
			}
			RequestHolder.getSession().setAttribute(myCart, null);
		}
	}
	
	/**
	 * 登陆用户的账号
	 * @return
	 */
	private String getUid(){
		Account acc = LoginUserHolder.getLoginAccount();
		if(acc!=null){
			return acc.getId();//.getAccount()
		}
		return null;
	}
	
	/**
	 * 获取购物车
	 * @return
	 */
	public CartInfo getMyCart(){
		CartInfo ci = this.getMyCart(getUid());
		if(ci==null){
			ci = new CartInfo();
		}
		return ci;
	}
	
	/**
	 * 设置购物车
	 * @param cartInfo
	 */
	public void setMyCart(CartInfo cartInfo){
		this.setMyCart(cartInfo,getUid());
	}
	
	/**
	 * 清空购物车
	 */
	public void clearMyCart(){
		this.clearMyCart(getUid());
	}
	
	/**
	 * 获取当前购物车商品数量
	 * @return
	 */
	public int getMyQuantity(){
		CartInfo ci = this.getMyCart();
		if(ci!=null){
			return ci.getQuantity();
		}
		return 0;
	}
	
	public String getConfirmOrderId(){
		Object id = RequestHolder.getSession().getAttribute(confirm_OrderId);
		if(StrUtils.isEmpty(id)){
			return null;
		}
		return String.valueOf(id);
	}
	
	public void setConfirmOrderId(String orderId){
		RequestHolder.getSession().setAttribute(confirm_OrderId,orderId);
	}
	
	public void clearConfirmOrderId(){
		RequestHolder.getSession().removeAttribute(confirm_OrderId);
	}
	
	/**
	 * 将待确认的订单信息存入缓存
	 * @param preOrder
	 */
	public void setPreOrder(Order preOrder) {
		String uid = getUid();
		SystemManager.getInstance().setPreOrder(uid, preOrder);
	}
	/**
	 * 获取待确认的订单信息
	 * @param preOrder
	 */
	//public Order getPreOrder() {
	//	return (Order) SystemManager.getInstance().getPreOrder(getUid());
	//}
	public Order getPreOrder() {
		Order o = (Order) SystemManager.getInstance().getPreOrder(getUid());
		if(null==o.getAmount() ){
			return null;
		}
		return o;
		
	}

}
