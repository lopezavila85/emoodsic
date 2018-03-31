package net.unir.emoodsic.webapp.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import net.unir.emoodsic.common.entities.MoodCategory;
import net.unir.emoodsic.dbaccess.classes.EmoodsicDbUtils;

public class MoodCategoryAction extends ActionSupport {

	//TODO BORRAR
	/**
	 * 
	 */
	private static final long serialVersionUID = 842546472888941103L;

	/**
     * List of geofencing areas stored in the database.
     */
    private Map<Integer,String> moodCategoryMap;
    
    public Map<Integer, String> getMoodCategoryMap() {
		return moodCategoryMap;
	}

	public void setMoodCategoryMap(Map<Integer, String> moodCategoryMap) {
		this.moodCategoryMap = moodCategoryMap;
	}

	/**
     * Class constructor.
     */
    public MoodCategoryAction() {
        super();
    }

    public String getJSON() {
    	List<MoodCategory> mcl = EmoodsicDbUtils.getMoodCategoryList();
    	this.moodCategoryMap =  new TreeMap<Integer, String>();
    	
    	for (MoodCategory mc: mcl) {
    		if (mc.getIdMoodCategory() > 0
    		    && mc.getIdMoodCategory() < 12) {
    			
        		//Do not include unknown category
        		//TODO remove unknown category
        		this.moodCategoryMap.put(mc.getIdMoodCategory(), mc.getName());
    		}
    	}
    	    	
    	return Action.SUCCESS;
    } 
    
    @Override
    public String execute() {
    	return Action.SUCCESS;
    }

}
