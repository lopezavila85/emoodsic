package net.unir.emoodsic.webapp.json;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import net.unir.emoodsic.common.entities.QbmPlaylist;
import net.unir.emoodsic.common.entities.QbmPlaylistInfo;
import net.unir.emoodsic.dbaccess.interfaces.RecSysService;
import net.unir.emoodsic.webapp.definitions.WebappConstants;

public class LoadQbmPlaylistAction  extends ActionSupport implements
SessionAware {

	/**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(LoadQbmPlaylistAction.class);
    
    /**
    *
    */
    protected transient Map<String, Object> session;

    private String sidx;
    
    private String sord;
    
    private List<QbmPlaylistInfo> qbmPlaylistSongs;

	public List<QbmPlaylistInfo> getQbmPlaylistSongs() {
		return qbmPlaylistSongs;
	}

	public void setQbmPlaylistSongs(List<QbmPlaylistInfo> qbmPlaylistSongs) {
		this.qbmPlaylistSongs = qbmPlaylistSongs;
	}

	@Autowired
    private transient RecSysService recSysService;
	
    /**
     *
     */
    public LoadQbmPlaylistAction() {
    	super();
    }
    
    @Override
    public String execute() {
    	return Action.SUCCESS;
    }
    
    public String getJSON() {
    	
    	Integer idUser = (Integer)this.session.get(WebappConstants.SESSION_KEY_IDUSER);
      	if (idUser == null
      		|| idUser <= 0) {
      		addActionError(getText(WebappConstants.ERROR_USER_NOT_FOUND_KEY));
			return Action.SUCCESS;
      	}
      	
		//Recuperar la última playlist y mostrarla
		QbmPlaylist qp = this.recSysService.getMostRecentQbmPlaylist(idUser);
		if (qp == null) {
			addActionError(getText(WebappConstants.ERROR_MOST_RECENT_QBM_PLAYLIST_KEY));
			return Action.SUCCESS;
		}
		
		this.qbmPlaylistSongs = this.recSysService.getQbmPlaylistInfo(qp.getIdQbmPlaylist());
		if (this.qbmPlaylistSongs == null) {
			addActionError(getText(WebappConstants.ERROR_MOST_RECENT_QBM_SONGS_KEY));			
		}
    	
    	return Action.SUCCESS;
    }

	@Override
	public void setSession(Map<String, Object> arg0) {
		this.session = arg0;		
	}

	/**
	 * @return the sidx
	 */
	public String getSidx() {
		return sidx;
	}

	/**
	 * @param sidx the sidx to set
	 */
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	/**
	 * @return the sord
	 */
	public String getSord() {
		return sord;
	}

	/**
	 * @param sord the sord to set
	 */
	public void setSord(String sord) {
		this.sord = sord;
	}
}
