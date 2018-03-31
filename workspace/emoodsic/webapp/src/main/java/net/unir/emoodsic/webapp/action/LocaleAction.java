/**
 * 
 */
package net.unir.emoodsic.webapp.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import net.unir.emoodsic.webapp.definitions.WebappConstants;

/**
 * @author Álvaro
 * @see http://www.mkyong.com/struts2/struts-2-i18n-or-localization-example/ *
 */
public class LocaleAction extends ActionSupport implements ServletRequestAware {

	private static final String FALL_BACK_ACTION = "logout";

	private HttpServletRequest request;
	private String forwardAction = null;
	
	@Override
	public String execute() throws Exception {
	    String referer = request.getHeader("referer");
	    String action = FALL_BACK_ACTION;

	    if (referer!=null) {        
	        // WeberT: first cut off the protocol, server and port...
	        referer = referer.substring(referer.lastIndexOf("/") + 1, referer.length());

	        // WeberT: then cut of the .action part if available
	        int dotPos = referer.indexOf(".");

	        if (dotPos>0) {
	            int qMarkPos = referer.indexOf("?");
	            if (qMarkPos> 0 && dotPos < qMarkPos) {         
	                action = referer.substring(0, dotPos) + referer.substring(qMarkPos, referer.length());
	            } else {
	                action = referer.substring(0, dotPos);                  
	            }               
	        }
	        forwardAction = action;
	    }
	    return SUCCESS;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
	    this.request = request;     
	}

	/**
	 * @return the forwardAction
	 */
	public String getForwardAction() {
	    return forwardAction;
	}

}
