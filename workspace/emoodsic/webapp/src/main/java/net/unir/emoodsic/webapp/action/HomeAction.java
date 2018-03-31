/**
 * 
 */
package net.unir.emoodsic.webapp.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import net.unir.emoodsic.webapp.definitions.WebappConstants;

/**
 * @author Álvaro
 *
 */
public class HomeAction extends ActionSupport implements SessionAware {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5914063472754049025L;

	/**
    *
    */
    protected transient Map<String, Object> session;
    
    private String greetings;
    
	public HomeAction() {
		super();
	}
	
    @Override
    public String execute() {

    	if (session.containsKey(WebappConstants.SESSION_KEY_GREETINGS)) {
			this.greetings =
			    String.format("%s %s", getText(WebappConstants.GREETINGS_KEY),
			        (String)session.get(WebappConstants.SESSION_KEY_GREETINGS));
    	}
        return Action.SUCCESS;
    }

	@Override
	public void setSession(Map<String, Object> arg0) {
		this.session = arg0;		
	}

	/**
	 * @return the greetings
	 */
	public String getGreetings() {
		return greetings;
	}

	/**
	 * @param greetings the greetings to set
	 */
	public void setGreetings(String greetings) {
		this.greetings = greetings;
	}
}