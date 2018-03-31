/**
 * 
 */
package net.unir.emoodsic.webapp.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import net.unir.emoodsic.common.classes.CommonUtils;
import net.unir.emoodsic.common.classes.CryptoUtils;
import net.unir.emoodsic.common.entities.AuthToken;
import net.unir.emoodsic.common.entities.User;
import net.unir.emoodsic.dbaccess.interfaces.UserService;
import net.unir.emoodsic.webapp.definitions.WebappConstants;

/**
 * @author Álvaro
 *
 */
public class AuthenticationAction extends ActionSupport implements SessionAware {


	/**
	 * 
	 */
	private static final long serialVersionUID = -1510128638177756061L;
	/**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationAction.class);
    /**
    *
    */
   private static final String AUTH_INVALID_CREDENTIALS_KEY = "auth.invalid.credentials";
   
   private static final String AUTH_EMAIL_REQUIRED_KEY = "auth.email.required";
   
   private static final String AUTH_PASSWORD_REQUIRED_KEY = "auth.password.required";
   
   /**
    * 
    */
   private static final String LOGIN_EMAIL = "loginEmail";
   /**
    * 
    */
   private static final String LOGIN_PASSWORD = "loginPassword";
   /**
    *
    */
   private static final String WHOLE_FORM = "wholeForm";
   /**
    *
    */
   private static final int AUTH_FAIL_WAIT_MS = 500;   
   /**
    *
    */
    protected transient Map<String, Object> session;
    
    /**
    *
    */
    @Autowired
    private transient UserService userDao;
    
    /**
    *
    */
    private String greetings;    
    /**
     * 
     */
    private String loginEmail;
    /**
     * 
     */
    private String loginPassword;
     
    
    
	public AuthenticationAction() {
		super();
	}
	
    @Override
    public void setSession(final Map<String, Object> pSession) {
        this.session = pSession;
    }
    
    /**
     * @return the greetings.
     */
    public String getGreetings() {
        return this.greetings;
    }

    /**
     * @param   pGreetings  the greetings.
     */
    public void setGreetings(final String pGreetings) {
        this.greetings = pGreetings;
    }
    
    @Override
    public void validate() {
    	
    	if (!session.containsKey(WebappConstants.SESSION_KEY_EMAIL)) {
    		//In case of error, the result is INPUT.    	
        	if (this.loginEmail == null
        		|| this.loginEmail.isEmpty()) {
        		addFieldError(LOGIN_EMAIL, getText(AUTH_EMAIL_REQUIRED_KEY));
        		return;
        	}
        	
        	if (this.loginPassword == null
        		|| this.loginPassword.isEmpty()) {    		
        		addFieldError(LOGIN_PASSWORD, getText(AUTH_PASSWORD_REQUIRED_KEY));
        		return;
        	}
        }    	
    }
    
    @Override
    public String execute() {

    	if (session.containsKey(WebappConstants.SESSION_KEY_GREETINGS)) {
			this.greetings =
			    String.format("%s %s", getText(WebappConstants.GREETINGS_KEY),
			        (String)session.get(WebappConstants.SESSION_KEY_GREETINGS));
			return Action.SUCCESS;
    	}
    	this.session.clear();

        String actionResult = Action.ERROR;
        boolean waitToReturn = true;

        User user = this.userDao.getAuthData(this.loginEmail);
        if (user == null) {
            addFieldError(WHOLE_FORM, getText(AUTH_INVALID_CREDENTIALS_KEY));
            addActionError(getText(AUTH_INVALID_CREDENTIALS_KEY));           

        } else {
            // Check the password
            if (this.checkPassword(user, this.loginPassword)) {
                generateSession(user);                
                actionResult = Action.SUCCESS;
                waitToReturn = false;
            } else {            
            	//	TODO Manage bad login counter.
            	 addFieldError(WHOLE_FORM, getText(AUTH_INVALID_CREDENTIALS_KEY));
                 addActionError(getText(AUTH_INVALID_CREDENTIALS_KEY));  
            }
        }
        
        if (waitToReturn) {
            try {
                Thread.sleep(AUTH_FAIL_WAIT_MS);
            } catch (InterruptedException e) {
                LOG.error(String.format("AuthenticationAction error while waiting to return: %s", e.getMessage()));
            }
        }
        return actionResult;
    }

    /**
     * Checks the password of a certain user.
     *
     * @param   user        an Emoodsic user to retrieve data from the database.
     * @param   rawPassword	the raw password to obtain the password hash.
     * @return              true if the computed password hash is equal to the one stored in the database,
     *                      false otherwise.
     */
    private Boolean checkPassword(final User user, final String rawPassword) {
        
    	// Generate password hash
        final String passwordHash = CryptoUtils.getHashMd5(rawPassword);
        if (passwordHash != null
        	&& passwordHash.equals(user.getEncryptedPassword())) {            
            return true;
        }

        //TODO Bad login counter management in both cases.
        return false;
    }

    /**
     * Generates the session token for a certain user.
     *
     * @param   id  the user's id to keep the token in the session stored.
     */
    private void generateSession(final User u) {
        // Create authentication token
        final String token = AuthToken.createNewToken();

        // Write the auth. token to the DB
        if (!this.userDao.setAuthToken(u.getIdUser(), token)) {
            LOG.warn(String.format("Error in %s. Error creating auth token %s for user %d",
                CommonUtils.getStackTrace(), token, u.getIdUser()));
        }
        // Write to the session
        this.session.put(WebappConstants.SESSION_KEY_IDUSER, u.getIdUser());
        this.session.put(WebappConstants.SESSION_KEY_EMAIL, u.getEmail());
        this.session.put(WebappConstants.SESSION_KEY_AUTHENTICATIONTOKEN, token);
        
        this.greetings =
            String.format("%s %s %s", getText(WebappConstants.GREETINGS_KEY), u.getFirstName(), u.getLastName());
        
        //i18n
        final String userGreetings =
        	String.format("%s %s", u.getFirstName(), u.getLastName());
        
        this.session.put(WebappConstants.SESSION_KEY_GREETINGS, userGreetings);
    }
    
    

	/**
	 * @return the loginEmail
	 */
	public String getLoginEmail() {
		return loginEmail;
	}

	/**
	 * @param loginEmail the loginEmail to set
	 */
	public void setLoginEmail(String loginEmail) {
		this.loginEmail = loginEmail;
	}

	/**
	 * @return the loginPassword
	 */
	public String getLoginPassword() {
		return loginPassword;
	}

	/**
	 * @param loginPassword the loginPassword to set
	 */
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
}
