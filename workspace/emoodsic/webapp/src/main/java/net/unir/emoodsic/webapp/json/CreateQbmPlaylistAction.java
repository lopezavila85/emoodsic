package net.unir.emoodsic.webapp.json;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import net.unir.emoodsic.common.entities.QbmPlaylistInfo;
import net.unir.emoodsic.dbaccess.interfaces.RecSysService;
import net.unir.emoodsic.webapp.action.AuthenticationAction;
import net.unir.emoodsic.webapp.definitions.WebappConstants;

public class CreateQbmPlaylistAction extends ActionSupport implements
SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8445526412075178974L;

	/**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(CreateQbmPlaylistAction.class);
    
	/**
    *
    */
    protected transient Map<String, Object> session;
    
    private String initialMoodCat;
    
    private String finalMoodCat;
    
    private boolean newQbmPlaylistCreated;
    
    @Autowired
    private transient RecSysService rsService;
    
	public CreateQbmPlaylistAction() {
		super();
		this.setNewQbmPlaylistCreated(false);
	}
	
    
    public String getJSON() {
    	
    	try {
    		Integer idInitialMoodCat = Integer.parseInt(this.initialMoodCat);
    		Integer idFinalMoodCat = Integer.parseInt(this.finalMoodCat);
    		Integer idUser = (Integer)session.get(WebappConstants.SESSION_KEY_IDUSER);
    		
    		if (idInitialMoodCat == null
    			|| idFinalMoodCat == null
    			|| idUser == null) {
    			LOG.error(String.format("Error parsing: idUser [%s] - [%s][%s]",
    				idUser, this.initialMoodCat, this.finalMoodCat));
    			return Action.ERROR;
    		}
    		
    		List<QbmPlaylistInfo> qpiList =
    			this.rsService.queryByMood(idUser, idInitialMoodCat, idFinalMoodCat);  
    		if (qpiList == null) {
    			LOG.error(String.format("Error creating playlist: idUser [%s] - [%s][%s]",
    				idUser, this.initialMoodCat, this.finalMoodCat));    			
    		} else {
    			this.setNewQbmPlaylistCreated(true);
    		}    	
    	} catch (NumberFormatException nfe) {
    		return Action.INPUT;
    	}
    	
    	return Action.SUCCESS;
    }
    
    @Override
    public String execute() {
    	return Action.SUCCESS;
    }
    
	@Override
	public void setSession(Map<String, Object> arg0) {
		this.session = arg0;		
	}


	/**
	 * @return the initialMoodCat
	 */
	public String getInitialMoodCat() {
		return initialMoodCat;
	}


	/**
	 * @param initialMoodCat the initialMoodCat to set
	 */
	public void setInitialMoodCat(String initialMoodCat) {
		this.initialMoodCat = initialMoodCat;
	}


	/**
	 * @return the finalMoodCat
	 */
	public String getFinalMoodCat() {
		return finalMoodCat;
	}


	/**
	 * @param finalMoodCat the finalMoodCat to set
	 */
	public void setFinalMoodCat(String finalMoodCat) {
		this.finalMoodCat = finalMoodCat;
	}


	/**
	 * @return the newQbmPlaylistCreated
	 */
	public boolean isNewQbmPlaylistCreated() {
		return newQbmPlaylistCreated;
	}


	/**
	 * @param newQbmPlaylistCreated the newQbmPlaylistCreated to set
	 */
	public void setNewQbmPlaylistCreated(boolean newQbmPlaylistCreated) {
		this.newQbmPlaylistCreated = newQbmPlaylistCreated;
	}
}
