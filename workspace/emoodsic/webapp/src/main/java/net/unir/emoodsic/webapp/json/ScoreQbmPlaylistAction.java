package net.unir.emoodsic.webapp.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import net.unir.emoodsic.common.entities.QbmPlaylist;
import net.unir.emoodsic.common.entities.QbmPlaylistInfo;
import net.unir.emoodsic.common.entities.RecommendationType;
import net.unir.emoodsic.dbaccess.classes.EmoodsicDbUtils;
import net.unir.emoodsic.dbaccess.interfaces.RecSysService;
import net.unir.emoodsic.webapp.definitions.WebappConstants;

public class ScoreQbmPlaylistAction extends ActionSupport implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3979429515756489428L;

	/**
    *
    */
    protected transient Map<String, Object> session;
    
    public static final String ERROR_SCORE_NOT_VALID_KEY = "qbm.score.notValid";
    
    private boolean playlistScoredOk = false;
    
    @Autowired
    private transient RecSysService recSysService;
    
    public ScoreQbmPlaylistAction() {
    	super();
    }
    
    @Override
    public String execute() {
    	return Action.SUCCESS;
    }
    
    public String getJSON() {
                
    	this.playlistScoredOk = false;
    	Integer idUser = (Integer)this.session.get(WebappConstants.SESSION_KEY_IDUSER);
      	if (idUser == null
      		|| idUser <= 0) {
      		addActionError(getText(WebappConstants.ERROR_USER_NOT_FOUND_KEY));
			return Action.SUCCESS;
      	}
    	
		//Retrieve the latest playlist, which must correspond to the one being displayed
		final QbmPlaylist qp = this.recSysService.getMostRecentQbmPlaylist(idUser);
		if (qp == null) {
			addActionError(getText(WebappConstants.ERROR_MOST_RECENT_QBM_PLAYLIST_KEY));
			return Action.SUCCESS;
		}
		
		final List<QbmPlaylistInfo> qpiList = this.recSysService.getQbmPlaylistInfo(qp.getIdQbmPlaylist());
		if (qpiList == null) {
			addActionError(getText(WebappConstants.ERROR_MOST_RECENT_QBM_SONGS_KEY));
			return Action.SUCCESS;
		}
        
    	final List<Integer> tasteRatings = new ArrayList<Integer>();
    	final List<Integer> personalityRatings = new ArrayList<Integer>();
    	
    	for (QbmPlaylistInfo qpi: qpiList) {
    		if (qpi.getLikertScore() > 0) {
    			//The item has been rated. Taste or personality?
    			String name = EmoodsicDbUtils.getNameRecommendationType(qpi.getIdRecommendationType());
    			if (name == null) {
    				//Should not enter here
    				continue;
    			}
    			
    			if (name.equals(RecommendationType.ARTIST)
    			    || name.equals(RecommendationType.STYLE)) {
    				tasteRatings.add(qpi.getLikertScore());    				
    			} else {
    				personalityRatings.add(qpi.getLikertScore());
    			}
    		}
    	}
    	
    	//The condition to calculate weights is that both lists contain ratings
    	if (tasteRatings.size() > 0
    		&& personalityRatings.size() > 0) {
    	
    		if (this.recSysService.addQbmLikertScores(qpiList)) {
        		//Total for taste
        		int totalTasteRating = 0;
        		int totalPersonalityRating = 0;
        		
        		for (Integer t: tasteRatings) {
        			totalTasteRating += t;
        		}
        		for (Integer p: personalityRatings) {
        			totalPersonalityRating += p;
        		}
        		
        		int totalRating = totalTasteRating + totalPersonalityRating;
        		double tasteWeight = (double) (totalTasteRating / (double)totalRating);
        		double personalityWeight = 1 - tasteWeight;

        		qp.setTasteWeight(tasteWeight);
        		qp.setPersonalityWeight(personalityWeight);
        		
        		if (this.recSysService.addQbmWeights(qp)) {
        			this.playlistScoredOk = true;
        			return Action.SUCCESS;
        		}
        	}
    	}
    	
    	addActionError(getText(ERROR_SCORE_NOT_VALID_KEY));
    	return Action.ERROR;
    	
    }
    
	@Override
	public void setSession(Map<String, Object> arg0) {
		this.session = arg0;		
	}

	/**
	 * @return the playlistScoredOk
	 */
	public boolean isPlaylistScoredOk() {
		return playlistScoredOk;
	}

	/**
	 * @param playlistScoredOk the playlistScoredOk to set
	 */
	public void setPlaylistScoredOk(boolean playlistScoredOk) {
		this.playlistScoredOk = playlistScoredOk;
	}

}
