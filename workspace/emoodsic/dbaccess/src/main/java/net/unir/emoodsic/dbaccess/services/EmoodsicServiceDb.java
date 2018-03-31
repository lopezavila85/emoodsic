/**
 * 
 */
package net.unir.emoodsic.dbaccess.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import net.unir.emoodsic.common.classes.CommonUtils;
import net.unir.emoodsic.common.entities.Education;
import net.unir.emoodsic.common.entities.Mood;
import net.unir.emoodsic.common.entities.MoodCategory;
import net.unir.emoodsic.common.entities.MusicKnowledge;
import net.unir.emoodsic.common.entities.MusicPrefDimStyle;
import net.unir.emoodsic.common.entities.MusicPrefDimension;
import net.unir.emoodsic.common.entities.RecommendationType;
import net.unir.emoodsic.common.entities.Style;
import net.unir.emoodsic.common.classes.LogUtils;
import net.unir.emoodsic.dbaccess.interfaces.EmoodsicService;
import net.unir.emoodsic.dbaccess.mappers.emoodsic.EducationMapper;
import net.unir.emoodsic.dbaccess.mappers.emoodsic.MoodCategoryMapper;
import net.unir.emoodsic.dbaccess.mappers.emoodsic.MoodMapper;
import net.unir.emoodsic.dbaccess.mappers.emoodsic.MusicKnowledgeMapper;
import net.unir.emoodsic.dbaccess.mappers.emoodsic.MusicPrefDimStyleMapper;
import net.unir.emoodsic.dbaccess.mappers.emoodsic.MusicPrefDimensionMapper;
import net.unir.emoodsic.dbaccess.mappers.emoodsic.RecommendationTypeMapper;
import net.unir.emoodsic.dbaccess.mappers.emoodsic.StyleMapper;


/**
 * @author Álvaro
 *
 */
@Service("emoodsicServiceDb")
public class EmoodsicServiceDb implements EmoodsicService {

    /**
     * Class logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(EmoodsicServiceDb.class);
    
    @Autowired
    private transient EducationMapper educationMapper;
    
    @Autowired
    private transient MoodCategoryMapper moodCategoryMapper;
    
    @Autowired
    private transient MoodMapper moodMapper;
    
    @Autowired
    private transient MusicKnowledgeMapper musicKnowledgeMapper;
    
    @Autowired
    private transient MusicPrefDimensionMapper musicPrefDimMapper;
    
    @Autowired
    private transient MusicPrefDimStyleMapper musicPrefDimStyleMapper;
    
    @Autowired
    private transient RecommendationTypeMapper recTypeMapper;
    
    @Autowired
    private transient StyleMapper styleMapper;
    
    public EmoodsicServiceDb() {
    	super();
    }
    
	@Override
	public List<Education> getEducationList() {
        try {        	
        	return this.educationMapper.getList();

        } catch (DataAccessException dae) {
            LogUtils.logDataAccessException(LOG, CommonUtils.getMethodName(), dae.getMessage(),
                null);           
        }
        return null;
	}
	
	@Override
	public List<Mood> getMoodList() {
        try {        	
        	return this.moodMapper.getList();

        } catch (DataAccessException dae) {
            LogUtils.logDataAccessException(LOG, CommonUtils.getMethodName(), dae.getMessage(),
                null);           
        }
        return null;
	}

	@Override
	public List<MoodCategory> getMoodCategoryList() {
        try {        	
        	return this.moodCategoryMapper.getList();

        } catch (DataAccessException dae) {
            LogUtils.logDataAccessException(LOG, CommonUtils.getMethodName(), dae.getMessage(),
                null);           
        }
        return null;
	}
	
	@Override
	public List<MusicKnowledge> getMusicKnowledgeList() {
        try {        	
        	return this.musicKnowledgeMapper.getList();

        } catch (DataAccessException dae) {
            LogUtils.logDataAccessException(LOG, CommonUtils.getMethodName(), dae.getMessage(),
                null);           
        }
        return null;
	}

	@Override
	public List<Style> getStyleList() {
        try {        	
        	return this.styleMapper.getList();

        } catch (DataAccessException dae) {
            LogUtils.logDataAccessException(LOG, CommonUtils.getMethodName(), dae.getMessage(),
                null);           
        }
        return null;
	}

	@Override
	public List<MusicPrefDimStyle> getMusicPrefDimStyleList() {
        try {        	
        	return this.musicPrefDimStyleMapper.getList();

        } catch (DataAccessException dae) {
            LogUtils.logDataAccessException(LOG, CommonUtils.getMethodName(), dae.getMessage(),
                null);           
        }
        return null;
	}
	
	@Override
	public List<RecommendationType> getRecommendationTypeList() {
        try {        	
        	return this.recTypeMapper.getList();

        } catch (DataAccessException dae) {
            LogUtils.logDataAccessException(LOG, CommonUtils.getMethodName(), dae.getMessage(),
                null);           
        }
        return null;
	}
	
	@Override
	public List<MusicPrefDimension> getMusicPrefDimensionList() {
        try {        	
        	return this.musicPrefDimMapper.getList();

        } catch (DataAccessException dae) {
            LogUtils.logDataAccessException(LOG, CommonUtils.getMethodName(), dae.getMessage(),
                null);           
        }
        return null;
	}
}
