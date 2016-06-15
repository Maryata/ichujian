/**
 * 
 */
package com.qujian;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.qujian.po.Supplier;
import com.qujian.service.ISupplierService;

/**
 * @author vpc
 */
public class Index extends HttpServlet {
	private static final long serialVersionUID = 2281529587306429730L;

	//private  Logger log = (Logger.getLogger(getClass()));
	/**
	 * Constructor of the object.
	 */
	public Index() {
		super();
		//		
		// ServletContext servletContext = servletConfig.getServletContext();
		// WebApplicationContext webApplicationContext =
		// WebApplicationContextUtils.getWebApplicationContext(servletContext);
		// AutowireCapableBeanFactory autowireCapableBeanFactory =
		// webApplicationContext.getAutowireCapableBeanFactory();
		// autowireCapableBeanFactory.configureBean(this, BEAN_NAME);
		//	    
		//	    
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final String defaultCode = "001001";

		ISupplierService supplierService = WebApplicationContextUtils
				.getRequiredWebApplicationContext( getServletContext() ).getBean( ISupplierService.class );
		String lc = request.getParameter( "lc" );
		
		//if ( lc.matches( "" ) ) { // regex lc的正则匹配规则
		if(null != lc && lc.length() >= 11) { // lc长度是21，6-11是供应商代码，lc长度大等于11这里认为正确
			String code = lc.substring( 5,11 ); // lc.substring( start,end ); 代表供应商代码的位置(起始、结束)
			Supplier supplier = supplierService.find( code.toUpperCase() ); // 获取供应商信息

			if(null == supplier) { // 不定制
				//supplier = new Supplier(); //supplierService.find( "" ) 寻找触键信息
				supplier = supplierService.find( defaultCode ); //寻找触键信息
				//supplier.setName( "触键" );
				//..
			}
			
			request.setAttribute( "supplier", supplier ); 
		} else {
			Supplier supplier = supplierService.find( defaultCode ); //寻找触键信息
			request.setAttribute( "supplier", supplier );
		}
		
		doPost( request, response );
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute( "title", "标题测试" );

		RequestDispatcher reqDispatcher = request
				.getRequestDispatcher( "/index.jsp" );
		reqDispatcher.forward( request, response );
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
