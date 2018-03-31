/**
 * 
 */
package net.unir.emoodsic.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import net.unir.emoodsic.common.entities.MoodCategory;
import net.unir.emoodsic.common.entities.QbmPlaylist;
import net.unir.emoodsic.common.entities.QbmPlaylistInfo;
import net.unir.emoodsic.common.entities.User;
import net.unir.emoodsic.dbaccess.classes.EmoodsicDbUtils;
import net.unir.emoodsic.dbaccess.interfaces.RecSysService;
import net.unir.emoodsic.dbaccess.interfaces.UserService;
import net.unir.emoodsic.webapp.definitions.WebappConstants;

/**
 * @author Álvaro
 *
 */
public class QueryByMoodAction extends ActionSupport implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1153550628502962914L;
    /**
     * Class logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(QueryByMoodAction.class);

    /**
    *
    */
    protected transient Map<String, Object> session;
    
    private String greetings;
    
    private boolean qbmPlaylistReady;
    
    private String initialMoodCat;
    
    private String finalMoodCat;
   
    /**
     * The initial mood of a certain QbmPlaylist
     */
    private String initialMoodName;
    /**
     * The final mood of a certain QbmPlaylist
     */
    private String finalMoodName;
    
	/**
     * List of mood categories.
     */
    private List<MoodCategory> moodCategoryList;
    
	public List<MoodCategory> getMoodCategoryList() {
		return moodCategoryList;
	}

	public void setMoodCategoryList(List<MoodCategory> moodCategoryList) {
		this.moodCategoryList = moodCategoryList;
	}

	@Autowired
    private transient RecSysService recSysService;

    /**
    *
    */
    @Autowired
    private transient UserService userDao;
    
    /**
     * Class constructor.
     */
	public QueryByMoodAction() {
		super();
	}
	
    @Override
    public String execute() {

    	if (session.containsKey(WebappConstants.SESSION_KEY_GREETINGS)) {
			this.greetings =
			    String.format("%s %s", getText(WebappConstants.GREETINGS_KEY),
			        (String)session.get(WebappConstants.SESSION_KEY_GREETINGS));           
    	}
    	
    	this.loadMoodCategories();
    	
    	//Check if the user has any pending playlist to rate. If not, display the comboboxes
    	//for mood category selection.
    	
    	Integer idUser = (Integer)session.get(WebappConstants.SESSION_KEY_IDUSER);
    	Boolean isPlaylistComplete = this.recSysService.isMostRecentQbmPlaylistComplete(idUser);
    	if (isPlaylistComplete == null
    		|| isPlaylistComplete == true) {
    		this.qbmPlaylistReady = true;    		
    		//Display the main form to select the moods
    		
    	} else {
    		//Display the list of songs
    		this.qbmPlaylistReady = false;
    		
    		QbmPlaylist qp = this.recSysService.getMostRecentQbmPlaylist(idUser);
    		if (qp == null) {
    			this.initialMoodName = "";
    			this.finalMoodName = "";
    		
    		} else {
    			MoodCategory mc = EmoodsicDbUtils.getMoodCategoryById(qp.getIdInitialMoodCat());
    			this.initialMoodName = getText(mc.getName());
    			
    			mc = EmoodsicDbUtils.getMoodCategoryById(qp.getIdFinalMoodCat());
    			this.finalMoodName = getText(mc.getName());
    		}
    	}

        return Action.SUCCESS;
    }
    
    
    private void loadMoodCategories() {
    	List<MoodCategory> mcl = EmoodsicDbUtils.getMoodCategoryList();
    	this.moodCategoryList = new ArrayList<MoodCategory>();
    	
    	for (MoodCategory mc: mcl) {
    		if (mc.getIdMoodCategory() > 0
    		    && mc.getIdMoodCategory() < 12) {
    			
        		//Do not include unknown category
        		//TODO remove unknown category
        		this.moodCategoryList.add(mc);
    		}
    	}
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
	
    public boolean isQbmPlaylistReady() {
		return qbmPlaylistReady;
	}

	public void setQbmPlaylistReady(boolean qbmPlaylistReady) {
		this.qbmPlaylistReady = qbmPlaylistReady;
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
	 * @return the initialMoodName
	 */
	public String getInitialMoodName() {
		return initialMoodName;
	}

	/**
	 * @param initialMoodName the initialMoodName to set
	 */
	public void setInitialMoodName(String initialMoodName) {
		this.initialMoodName = initialMoodName;
	}

	/**
	 * @return the finalMoodName
	 */
	public String getFinalMoodName() {
		return finalMoodName;
	}

	/**
	 * @param finalMoodName the finalMoodName to set
	 */
	public void setFinalMoodName(String finalMoodName) {
		this.finalMoodName = finalMoodName;
	}
}