/**
 * 
 */
package net.unir.emoodsic.webapp.initialization;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;

import net.unir.emoodsic.common.entities.MusicPrefDimProb;
import net.unir.emoodsic.common.entities.QbmPlaylist;
import net.unir.emoodsic.common.entities.QbmPlaylistInfo;
import net.unir.emoodsic.common.entities.RecommendationType;
import net.unir.emoodsic.dbaccess.classes.EmoodsicDbUtils;
import net.unir.emoodsic.dbaccess.interfaces.EmoodsicService;
import net.unir.emoodsic.dbaccess.interfaces.MusicPrefDimService;
import net.unir.emoodsic.dbaccess.interfaces.RecSysService;
import net.unir.emoodsic.dbaccess.interfaces.UserService;

/**
 * @author Álvaro
 *
 */
public final class Initializer implements javax.servlet.ServletContextListener {

    /**
     * Get logback handler.
     */
    private static final Logger LOG = LoggerFactory.getLogger(Initializer.class);

    @Autowired
    private transient EmoodsicService emoodsicService;
    
    @Autowired
    private transient MusicPrefDimService mpdService;
    
    @Autowired
    private transient UserService userService;
        
    @Autowired
    private transient RecSysService rsService;
   

    /**
     * Default constructor.
     */
    public Initializer() {
        super();
    }

    @Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
		LOG.warn("Emoodsic Webapp stopped OK");
	}

	@Override
	public void contextInitialized(final ServletContextEvent sce) {

    	LOG.warn("Emoodsic Webapp initializing and autowiring...");
    	
        WebApplicationContextUtils
        	.getRequiredWebApplicationContext(sce.getServletContext())
        	.getAutowireCapableBeanFactory().autowireBean(this);
        
    	LOG.warn("Emoodsic Webapp loading database information...");

//    	List<QbmPlaylistInfo> playlist = new ArrayList<QbmPlaylistInfo>();
//    	QbmPlaylistInfo qpi = new QbmPlaylistInfo();
//    	qpi.setArtist("David Guetta");
//    	qpi.setSong("Sun Goes Down");
//    	playlist.add(qpi);    	
//    	testYoutube.searchVideos(playlist);
    	
    	//TODO DB management tasks
    	//Clean authentication tokens table
    	this.userService.deleteAuthTokens();
    	
    	loadEmoodsicDbInfo();
  	
    	//Nuevos usuarios
    	List<MusicPrefDimProb> probList = this.userService.calculateBfiUsers();
    	if (probList != null
    		&& !probList.isEmpty()) {
    		//If there are any initial probabilities for users (i.e. 1 in the most correlated dimension, 0 in the rest),
    		//insert them in the table
    		this.mpdService.setMusicPrefDimProbs(probList);
    	}
    	
    	//TODO
    	this.mpdService.classifyUsersInMusicPrefDimensions(true);    	
    	
    	this.mpdService.setKNearestNeighbors();
    	
    	/*
    	//Ejemplo de generacion de playlist
    	int idUser = 10;
    	int idInitialMoodCat = 5;
    	int idFinalMoodCat = 10;
    	this.rsService.queryByMood(idUser, idInitialMoodCat, idFinalMoodCat);    	
    	*/
    	
    	/*
    	//Manual rating
    	int idQbmPlaylist = 76;
    	List<QbmPlaylistInfo> qpiList = this.rsService.getQbmPlaylistInfo(idQbmPlaylist);
    	List<Integer> tasteRatings = new ArrayList<Integer>();
    	List<Integer> personalityRatings = new ArrayList<Integer>();
    	    	
    	for (QbmPlaylistInfo qpi: qpiList) {
    		
//    		if (qpi.getIdQbmPlaylistInfo() == (10 * (idQbmPlaylist - 1)) + 1) {
//    			qpi.setLikertScore(1);    			
//    			
//    		} else if (qpi.getIdQbmPlaylistInfo() == (10 * (idQbmPlaylist - 1)) + 2) {
//    			qpi.setLikertScore(2);
//
//    		} else if (qpi.getIdQbmPlaylistInfo() == (10 * (idQbmPlaylist - 1)) + 3) {
//    			qpi.setLikertScore(2);
//
//    		} else if (qpi.getIdQbmPlaylistInfo() == (10 * (idQbmPlaylist - 1)) + 4) {
//    			qpi.setLikertScore(3);
//
//    		} else if (qpi.getIdQbmPlaylistInfo() == (10 * (idQbmPlaylist - 1)) + 5) {
//    			qpi.setLikertScore(3);
//
//    		} else if (qpi.getIdQbmPlaylistInfo() == (10 * (idQbmPlaylist - 1)) + 6) {
//    			qpi.setLikertScore(4);
//
//    		} else if (qpi.getIdQbmPlaylistInfo() == (10 * (idQbmPlaylist - 1)) + 7) {
//    			qpi.setLikertScore(1);
//
//    		} else if (qpi.getIdQbmPlaylistInfo() == (10 * (idQbmPlaylist - 1)) + 8) {
//    			qpi.setLikertScore(3);
//
//    		} else if (qpi.getIdQbmPlaylistInfo() == (10 * (idQbmPlaylist - 1)) + 9) {
//    			qpi.setLikertScore(3);
//
//    		} else if (qpi.getIdQbmPlaylistInfo() == (10 * (idQbmPlaylist - 1)) + 10) {
//    			qpi.setLikertScore(2);
//    		}
    		    		
    		if (qpi.getIdQbmPlaylistInfo() == 741) {
    			qpi.setLikertScore(2);    			
    			
    		} else if (qpi.getIdQbmPlaylistInfo() == 742) {
    			qpi.setLikertScore(1);

    		} else if (qpi.getIdQbmPlaylistInfo() == 743) {
    			qpi.setLikertScore(4);

    		} else if (qpi.getIdQbmPlaylistInfo() == 744) {
    			qpi.setLikertScore(1);

    		} else if (qpi.getIdQbmPlaylistInfo() == 745) {
    			qpi.setLikertScore(3);

    		} else if (qpi.getIdQbmPlaylistInfo() == 746) {
    			qpi.setLikertScore(4);

    		} else if (qpi.getIdQbmPlaylistInfo() == 747) {
    			qpi.setLikertScore(5);

    		} else if (qpi.getIdQbmPlaylistInfo() == 748) {
    			qpi.setLikertScore(4);

    		} else if (qpi.getIdQbmPlaylistInfo() == 749) {
    			qpi.setLikertScore(4);

    		} else if (qpi.getIdQbmPlaylistInfo() == 750) {
    			qpi.setLikertScore(3);
    		}
    		
    		
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
    	
    		if (this.rsService.addQbmLikertScores(qpiList)) {
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
        		
        		QbmPlaylist qp = new QbmPlaylist();
        		qp.setIdQbmPlaylist(idQbmPlaylist);
        		qp.setTasteWeight(tasteWeight);
        		qp.setPersonalityWeight(personalityWeight);
        		
        		this.rsService.addQbmWeights(qp);
        	}
    	}
    	//On the contrary, request the user to rate more items
    	*/
    	LOG.warn("Emoodsic Webapp initialized OK");	
	}
	
	/**
	 * Loads short static lists from database
	 */
	private void loadEmoodsicDbInfo() {
		//TODO Crear un metodo para recargar todas las listas con las que trabaja el sistema
		EmoodsicDbUtils.loadEmoodsicDbLists(this.emoodsicService);
	}
}
