/**
 * 
 */
package net.unir.emoodsic.webapp.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Álvaro
 *
 */
public class LogoutAction extends ActionSupport implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4112547447478101891L;

	/**
    *
    */
    protected transient Map<String, Object> session;
    
	public LogoutAction() {
		super();
	}
	
    @Override
    public String execute() {

        this.session.clear();
        return SUCCESS;
    }

	@Override
	public void setSession(Map<String, Object> arg0) {
		this.session = arg0;		
	}
}
