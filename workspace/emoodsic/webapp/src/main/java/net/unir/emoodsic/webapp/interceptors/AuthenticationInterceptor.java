/**
 * 
 */
package net.unir.emoodsic.webapp.interceptors;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import net.unir.emoodsic.common.classes.Validators;
import net.unir.emoodsic.common.entities.User;
import net.unir.emoodsic.dbaccess.interfaces.UserService;
import net.unir.emoodsic.webapp.definitions.WebappConstants;

/**
 * @author Álvaro
 *
 */
public class AuthenticationInterceptor extends AbstractInterceptor {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6353801707308302566L;

	/**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationInterceptor.class);
    
    @Autowired
    private transient UserService userService;
    
	public AuthenticationInterceptor() {
		super();
	}

	@Override
	public String intercept(final ActionInvocation invocation) throws Exception {
		
		// Retrieve current session
        final Map<String, Object> session = ActionContext.getContext().getSession();
        
        if (!session.containsKey(WebappConstants.SESSION_KEY_EMAIL)) {
        	LOG.warn(String.format("Session does not contain SESSION_KEY_AUTHENTICATIONTOKEN for %s",
                (String)session.get(WebappConstants.SESSION_KEY_EMAIL)));
        	return WebappConstants.NOT_LOGGED;
        }
        
        String sessionEmail = (String)session.get(WebappConstants.SESSION_KEY_EMAIL);
        User u = null;
        if (sessionEmail != null
        	&& !sessionEmail.isEmpty()) {
	        u = this.userService.getAuthData(sessionEmail);
	        if (u == null) {
	        	LOG.warn(String.format("DB does not contain email %s", sessionEmail));
	        	return WebappConstants.NOT_LOGGED;
	        }
        }

        // Check auth token
        if (session.containsKey(WebappConstants.SESSION_KEY_AUTHENTICATIONTOKEN)) {
            
        	final String token = this.userService.getAuthToken(u.getIdUser());
            final String sessionToken = (String) session.get(WebappConstants.SESSION_KEY_AUTHENTICATIONTOKEN);
            
        	if (token == null
            	|| !Validators.validateToken(token)
                || !Validators.validateToken(sessionToken)) {
            	
            	LOG.error(String.format("Error in authToken for email %s", sessionEmail));
            	
            } else {
                if (sessionToken.equals(token)) { 
                	String interceptResult = null;
                    try {
                        interceptResult = invocation.invoke();
                    } catch (Exception e) {
                        LOG.error(String.format("Error in invoke: %s", e.getMessage()));
                        interceptResult = Action.ERROR;
                    }
                    return interceptResult;
                //} else {
                	//Token Incoherence
                }
            }
        } else {
        	LOG.warn(String.format("Session does not contain SESSION_KEY_AUTHENTICATIONTOKEN for %s",
        		(String)session.get(WebappConstants.SESSION_KEY_EMAIL)));        	
        }
        return WebappConstants.NOT_LOGGED;
	}

}
