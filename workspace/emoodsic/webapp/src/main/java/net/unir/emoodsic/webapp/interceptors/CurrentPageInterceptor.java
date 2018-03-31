package net.unir.emoodsic.webapp.interceptors;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import net.unir.emoodsic.webapp.definitions.WebappConstants;

public class CurrentPageInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 407382070371733592L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
	    HttpServletRequest request = ServletActionContext.getRequest();
	    Map<String, Object> session = invocation.getInvocationContext().getSession();
	    
	    String queryString = request.getQueryString();
	    String savedUrl = request.getRequestURI() + (queryString==null?"":("?"+queryString));
	    String currentPage = (String) session.get(WebappConstants.SESSION_KEY_SAVEDURL);
	    if (currentPage == null) {
	    	currentPage = savedUrl;
	    }
	    session.put(WebappConstants.SESSION_KEY_CURRENTPAGE, currentPage); 
	    session.put(WebappConstants.SESSION_KEY_SAVEDURL, savedUrl);
	    
	    return invocation.invoke();
	}

}
