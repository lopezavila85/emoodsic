/**
 * 
 */
package net.unir.emoodsic.webapp.json;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import net.unir.emoodsic.common.entities.QbmPlaylistInfo;
import net.unir.emoodsic.dbaccess.interfaces.RecSysService;
import net.unir.emoodsic.webapp.definitions.WebappConstants;

/**
 * @author Álvaro
 *
 */
public class ScoreQbmPlaylistSongAction extends ActionSupport implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7455360613365594353L;
    
	/**
    *
    */
    protected transient Map<String, Object> session;
    
    /**
    *
    */
    private String id;
    
    private int likertScore;
    
    /**
    * Edit operation
    */
    private String oper;
    
    @Autowired
    private transient RecSysService rsService;
    
	public ScoreQbmPlaylistSongAction() {
		super();
	}
	
    public int getLikertScore() {
		return likertScore;
	}

	public void setLikertScore(int likertScore) {
		this.likertScore = likertScore;
	}

    @Override
    public String execute() {
    	return Action.SUCCESS;
    }
    
    public String getJSON() {

        if (!WebappConstants.OPER_EDIT.equalsIgnoreCase(this.oper)) {
           return Action.SUCCESS;
        }
        
        int idQbmPlaylistInfo = -1;
        try {
        	idQbmPlaylistInfo = Integer.parseInt(this.id);
        } catch( NumberFormatException nex) {        	
        }
        
        if (idQbmPlaylistInfo <= 0
            || this.likertScore < QbmPlaylistInfo.MIN_LIKERT_SCORE
            || this.likertScore > QbmPlaylistInfo.MAX_LIKERT_SCORE) {
        	return Action.INPUT;
        }

        if (this.rsService.addQbmLikertScore(idQbmPlaylistInfo, this.likertScore)) { 	       
        	return Action.SUCCESS;
        }
        
       	return Action.ERROR;
    }
    
	@Override
	public void setSession(Map<String, Object> arg0) {
		this.session = arg0;		
	}

	/**
	 * @return the oper
	 */
	public String getOper() {
		return oper;
	}

	/**
	 * @param oper the oper to set
	 */
	public void setOper(String oper) {
		this.oper = oper;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
}
