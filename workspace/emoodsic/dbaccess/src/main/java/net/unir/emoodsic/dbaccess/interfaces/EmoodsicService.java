/**
 * 
 */
package net.unir.emoodsic.dbaccess.interfaces;

import java.util.List;

import net.unir.emoodsic.common.entities.Education;
import net.unir.emoodsic.common.entities.Mood;
import net.unir.emoodsic.common.entities.MoodCategory;
import net.unir.emoodsic.common.entities.MusicKnowledge;
import net.unir.emoodsic.common.entities.MusicPrefDimStyle;
import net.unir.emoodsic.common.entities.MusicPrefDimension;
import net.unir.emoodsic.common.entities.RecommendationType;
import net.unir.emoodsic.common.entities.Style;

/**
 * @author Álvaro
 * Service to read generic data from the database (getters).
 */
public interface EmoodsicService {

	public List<Education> getEducationList();
	
	public List<Mood> getMoodList();
	
	public List<MoodCategory> getMoodCategoryList();
	
	public List<MusicKnowledge> getMusicKnowledgeList();
	
	public List<MusicPrefDimension> getMusicPrefDimensionList();
	
	public List<MusicPrefDimStyle> getMusicPrefDimStyleList();
	
	public List<RecommendationType> getRecommendationTypeList();
	
	public List<Style> getStyleList();
}
