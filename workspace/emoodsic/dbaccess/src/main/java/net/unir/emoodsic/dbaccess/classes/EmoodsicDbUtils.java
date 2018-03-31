/**
 * 
 */
package net.unir.emoodsic.dbaccess.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.unir.emoodsic.common.classes.RandomUtils;
import net.unir.emoodsic.common.entities.Education;
import net.unir.emoodsic.common.entities.Mood;
import net.unir.emoodsic.common.entities.MoodCategory;
import net.unir.emoodsic.common.entities.MusicKnowledge;
import net.unir.emoodsic.common.entities.MusicPrefDimStyle;
import net.unir.emoodsic.common.entities.MusicPrefDimension;
import net.unir.emoodsic.common.entities.RecommendationType;
import net.unir.emoodsic.common.entities.Style;
import net.unir.emoodsic.dbaccess.interfaces.EmoodsicService;
import net.unir.emoodsic.dbaccess.services.RecSysServiceDb;

/**
 * @author Álvaro
 *
 */
public final class EmoodsicDbUtils {

    /**
     * Class logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(EmoodsicDbUtils.class);
    
    private static List<Education> educationList = new ArrayList<Education>();
    
	private static List<Mood> moodList = new ArrayList<Mood>();
	
	private static List<MoodCategory> moodCategoryList = new ArrayList<MoodCategory>();
	
	private static List<MusicKnowledge> musicKnowledgeList = new ArrayList<MusicKnowledge>();
	
	private static List<MusicPrefDimension> musicPrefDimensionList = new ArrayList<MusicPrefDimension>();
	
	private static List<MusicPrefDimStyle> musicPrefDimStyleList = new ArrayList<MusicPrefDimStyle>();
	
	private static List<RecommendationType> recommendationTypeList = new ArrayList<RecommendationType>();
	
	private static List<Style> styleList = new ArrayList<Style>();
	
	/**
	 * A list containing just the styles assigned to Music Preference Dimensions.
	 */
	private static List<Style> musicDimStyleList = new ArrayList<Style>();


	private EmoodsicDbUtils() {
		super();
	}

	
	public static List<Style> getMusicDimStyleList() {
		return musicDimStyleList;
	}

	public static void setMusicDimStyleList(List<Style> musicDimStyleList) {
		EmoodsicDbUtils.musicDimStyleList = musicDimStyleList;
	}
	
	/**
	 * @return the moodCategoryList
	 */
	public static List<MoodCategory> getMoodCategoryList() {
		return moodCategoryList;
	}
	
	public static void loadEmoodsicDbLists(EmoodsicService srvc) {
		List<Education> el = srvc.getEducationList();
		if (el == null) {
			LOG.error("Education list is empty !!!");
		} else {
			setEducationList(el);
		}
		
		List<Mood> ml = srvc.getMoodList();
		if (ml == null) {
			LOG.error("Mood list is empty !!!");
		} else {
			setMoodList(ml);
		}
		
		List<MoodCategory> mcl = srvc.getMoodCategoryList();
		if (mcl == null) {			
			LOG.error("MoodCategory list is empty !!!");
		} else {
			setMoodCategoryList(mcl);
		}
		
		List<MusicKnowledge> mkl = srvc.getMusicKnowledgeList();
		if (mkl == null) {			
			LOG.error("MusicKnowledge list is empty !!!");
		} else {
			setMusicKnowledgeList(mkl);
		}
		
		List<MusicPrefDimension> mpdl = srvc.getMusicPrefDimensionList();
		if (mpdl == null) {			
			LOG.error("MusicPrefDimension list is empty !!!");
		} else {
			setMusicPrefDimensionList(mpdl);
		}
		
		List<MusicPrefDimStyle> mpdsl = srvc.getMusicPrefDimStyleList();
		if (mpdl == null) {			
			LOG.error("MusicPrefDimStyle list is empty !!!");
		} else {
			setMusicPrefDimStyleList(mpdsl);
		}
		
		List<RecommendationType> rtl = srvc.getRecommendationTypeList();
		if (rtl == null) {			
			LOG.error("RecommendationType list is empty !!!");
		} else {
			setRecommendationTypeList(rtl);
		}
		
		List<Style> sl = srvc.getStyleList();
		if (sl == null) {			
			LOG.error("Style list is empty !!!");
		} else {
			setStyleList(sl);
			
			for (MusicPrefDimStyle mpds: musicPrefDimStyleList) {
				Style s = getStyleById(mpds.getIdStyle());
				musicDimStyleList.add(s);
			}
		}
	}

	/**
	 * @param moodCategoryList the moodCategoryList to set
	 */
	public static void setMoodCategoryList(List<MoodCategory> moodCategoryList) {
		EmoodsicDbUtils.moodCategoryList = moodCategoryList;
	}
	
	//http://www.leveluplunch.com/java/examples/find-element-in-list/
	//http://www.leveluplunch.com/java/tutorials/006-how-to-filter-arraylist-stream-java8/
	public static MoodCategory searchMoodCategory(int id) {
		
		/*for (MoodCategory mc: moodCategoryList) {
			if (mc.getIdMoodCategory() == id) {
				return mc;
			}
		}
		return null;*/
		return moodCategoryList.stream()
			.filter(x -> x.getIdMoodCategory() == id).findFirst().get();
	}
	
	public static List<Mood> searchMoods(int idCat) {
	    
		//Predicate<Mood> nonNullPredicate = Objects::nonNull;
		//Predicate <Mood> moodCatPredicate = ;	    
		//Predicate<BBTeam> nameNotNull = p -> p.teamName != null;
	    //Predicate<BBTeam> teamWIPredicate = p -> p.teamName.equals("Wisconsin");
	    //Predicate<BBTeam> fullPredicate = nonNullPredicate.and(nameNotNull).and(teamWIPredicate);

	    return moodList.stream().filter(p -> p.getIdMoodCategory() == idCat)
	        .collect(Collectors.toList());
	}
	
	public static int getRandomMoodId(List<Mood> ml) {		
		
		if (ml.size() <= 0) {
			return -1;
		}
		
		List<Integer> il = new ArrayList<Integer>();		
		for (Mood m: ml) {
			il.add(m.getIdMood());
		}	
		return RandomUtils.getRandomInt(il);
	}
	
	/**
	 *
	 * @return "artist" or "style" in order to recommend favorite songs in Query By Mood.
	 */
	public static String getQbmRandomArtistOrStyle() {
		
		List<String> sl = new ArrayList<String>();
		
		//Fill the list
		for (RecommendationType rt: EmoodsicDbUtils.recommendationTypeList) {

			if (rt.getName().equals(RecommendationType.ARTIST)
			    || rt.getName().equals(RecommendationType.STYLE)) {
				sl.add(rt.getName());
			}
		}		
		return RandomUtils.getRandomString(sl);
	}
	
	public static int getIdRecommendationType(String name) {
		return recommendationTypeList.stream()
			.filter(x -> x.getName().equals(name)).findFirst().get().getIdRecommendationType();
	}
	
	public static String getNameRecommendationType(int idRecommendationType) {
		return recommendationTypeList.stream()
			.filter(x -> x.getIdRecommendationType() == idRecommendationType).findFirst().get().getName();
	}
	
	public static int getMusicPrefDimensionId(final String name) {
		
		MusicPrefDimension dim = musicPrefDimensionList.stream()
			.filter(x -> x.getName().equals(name)).findFirst().get();
		
		if (dim == null) {
			LOG.error(String.format("Error retrieving MusicPrefDimension Id for %s", name));
			return -1;
		}
		return dim.getIdMusicPrefDimension();		
	}
	
	public static String getMusicPrefDimensionName(final int id) {
		MusicPrefDimension dim = musicPrefDimensionList.stream()
			.filter(x -> x.getIdMusicPrefDimension() == id).findFirst().get();
		
		if (dim == null) {
			LOG.error(String.format("Error retrieving MusicPrefDimension Id for %d", id));
			return "";
		} 
		return dim.getName();
	}
	
	/**
	 * 
	 * @param idMusicDimension	the Music Preference Dimension assigned to a playlist
	 * @return					the id of a style, based on personality, in order to create
	 * @note The original styles in Rentfrow's study do not contain the word "spanish" within their names.
		As this is a system adapted to Spanish people, the corresponding "spanish"
		styles will be also considered to determine a personality style.
		The MusicPrefDimStyle table will contain these additional styles, so no further operations
		are required.
	 */
	public static int getRandomMusicPrefDimStyle(int idMusicDimension) {
		
		List<MusicPrefDimStyle> mpdsl = musicPrefDimStyleList.stream()
		    .filter(p -> p.getIdMusicPrefDimension() == idMusicDimension)
		    .collect(Collectors.toList());
		if (mpdsl == null || mpdsl.isEmpty()) {
			return -1;
		}
		
		List<Integer> styleIds = new ArrayList<Integer>();
		for (MusicPrefDimStyle s : mpdsl) {
			styleIds.add(s.getIdStyle());
		}
		
		return RandomUtils.getRandomInt(styleIds);
	}
	
	public static MoodCategory getMoodCategoryById(int idMoodCategory) {
		
		return moodCategoryList.stream()
			.filter(x -> x.getIdMoodCategory() == idMoodCategory).findFirst().get();
	}
	
	public static Mood getMoodById(int idMood) {
		
		return moodList.stream()
			.filter(x -> x.getIdMood() == idMood).findFirst().get();
	}
	
	public static Style getStyleById(int idStyle) {
		
		return styleList.stream()
			.filter(x -> x.getIdStyle() == idStyle).findFirst().get();
	}
	

	/**
	 * @return the educationList
	 */
	public static List<Education> getEducationList() {
		return educationList;
	}

	/**
	 * @param educationList the educationList to set
	 */
	public static void setEducationList(List<Education> educationList) {
		EmoodsicDbUtils.educationList = educationList;
	}

	/**
	 * @return the musicKnowledgeList
	 */
	public static List<MusicKnowledge> getMusicKnowledgeList() {
		return musicKnowledgeList;
	}

	/**
	 * @param musicKnowledgeList the musicKnowledgeList to set
	 */
	public static void setMusicKnowledgeList(List<MusicKnowledge> musicKnowledgeList) {
		EmoodsicDbUtils.musicKnowledgeList = musicKnowledgeList;
	}

	/**
	 * @return the musicPrefDimStyleList
	 */
	public static List<MusicPrefDimStyle> getMusicPrefDimStyleList() {
		return musicPrefDimStyleList;
	}

	/**
	 * @param musicPrefDimStyleList the musicPrefDimStyleList to set
	 */
	public static void setMusicPrefDimStyleList(List<MusicPrefDimStyle> musicPrefDimStyleList) {
		EmoodsicDbUtils.musicPrefDimStyleList = musicPrefDimStyleList;
	}
	
	/**
	 * @return the moodList
	 */
	public static List<Mood> getMoodList() {
		return moodList;
	}

	/**
	 * @param moodList the moodList to set
	 */
	public static void setMoodList(List<Mood> moodList) {
		EmoodsicDbUtils.moodList = moodList;
	}

	/**
	 * @return the recommendationTypeList
	 */
	public static List<RecommendationType> getRecommendationTypeList() {
		return recommendationTypeList;
	}

	/**
	 * @param recommendationTypeList the recommendationTypeList to set
	 */
	public static void setRecommendationTypeList(List<RecommendationType> recommendationTypeList) {
		EmoodsicDbUtils.recommendationTypeList = recommendationTypeList;
	}

	/**
	 * @return the musicPrefDimensionList
	 */
	public static List<MusicPrefDimension> getMusicPrefDimensionList() {
		return musicPrefDimensionList;
	}

	/**
	 * @param musicPrefDimensionList the musicPrefDimensionList to set
	 */
	public static void setMusicPrefDimensionList(List<MusicPrefDimension> musicPrefDimensionList) {
		EmoodsicDbUtils.musicPrefDimensionList = musicPrefDimensionList;
	}

	/**
	 * @return the styleList
	 */
	public static List<Style> getStyleList() {
		return styleList;
	}

	/**
	 * @param styleList the styleList to set
	 */
	public static void setStyleList(List<Style> styleList) {
		EmoodsicDbUtils.styleList = styleList;
	}
}
