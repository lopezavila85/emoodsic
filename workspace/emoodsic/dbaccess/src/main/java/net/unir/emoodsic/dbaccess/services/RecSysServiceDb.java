package net.unir.emoodsic.dbaccess.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import net.unir.emoodsic.common.classes.CommonUtils;
import net.unir.emoodsic.common.classes.EmoodsicException;
import net.unir.emoodsic.common.classes.LogUtils;
import net.unir.emoodsic.common.classes.QbmPlaylistUtils;
import net.unir.emoodsic.common.classes.RandomUtils;
import net.unir.emoodsic.common.entities.FavArtist;
import net.unir.emoodsic.common.entities.FavStyle;
import net.unir.emoodsic.common.entities.Mood;
import net.unir.emoodsic.common.entities.MoodCategory;
import net.unir.emoodsic.common.entities.MusicPrefDimProb;
import net.unir.emoodsic.common.entities.MusicPrefDimProbItem;
import net.unir.emoodsic.common.entities.MusicPrefDimension;
import net.unir.emoodsic.common.entities.QbmPlaylist;
import net.unir.emoodsic.common.entities.QbmPlaylistInfo;
import net.unir.emoodsic.common.entities.RecommendationType;
import net.unir.emoodsic.common.entities.User;
import net.unir.emoodsic.dbaccess.classes.EmoodsicDbUtils;
import net.unir.emoodsic.dbaccess.interfaces.RecSysService;
import net.unir.emoodsic.dbaccess.mappers.emoodsic.FavArtistMapper;
import net.unir.emoodsic.dbaccess.mappers.emoodsic.FavStyleMapper;
import net.unir.emoodsic.dbaccess.mappers.emoodsic.MusicPrefDimProbMapper;
import net.unir.emoodsic.dbaccess.mappers.emoodsic.QbmPlaylistInfoMapper;
import net.unir.emoodsic.dbaccess.mappers.emoodsic.QbmPlaylistMapper;
import net.unir.emoodsic.dbaccess.mappers.emoodsic.UserMapper;
import net.unir.emoodsic.dbaccess.services.transactional.RecSysServiceDbTx;


@Service("recSysServiceDb")
public class RecSysServiceDb implements RecSysService {

    /**
     * Class logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(RecSysServiceDb.class);
    
    @Autowired
    private transient EchoNestServiceDb echoNestMng;
    
    @Autowired
    private transient FavArtistMapper favArtistMapper;
    
    @Autowired
    private transient FavStyleMapper favStyleMapper;
    
    @Autowired
    private transient MusicPrefDimProbMapper mpdProbMapper;
    
    @Autowired
    private transient RecSysServiceDbTx recSysServiceDbTx;
    
    @Autowired
    private transient QbmPlaylistMapper qbmPlaylistMapper;
    
    @Autowired
    private transient QbmPlaylistInfoMapper qbmPlInfoMapper;
    
    @Autowired
    private transient UserMapper userMapper;
    
    /**
     * The maximum number of last songs recommended to a user, in order not
     * to display duplicates.
     */
    private int qbmLastNSongsRetention;
    
    /**
     * Maximum days of retention to calculate tastes and personality weights for 
     * a QBM playlist. This value is injected by Spring context on startup.
     */
    private int qbmMaxDaysRetention;
    
    /**
     * The minimum percentage for each recommendation type in a QBM playlist.
     */
    private double qbmMinRecommendationTypePercent;
    
    /**
     * Determines the minimum number of playlists in order not to include an additional 0.5 weight for each
     * category of QBM recommendations.
     */
    private int qbmMinPlaylistsAvgWeights;
  

	public RecSysServiceDb() {
    	super();
    	this.qbmLastNSongsRetention = 20;
    	this.qbmMaxDaysRetention = 30;
    	this.qbmMinRecommendationTypePercent = 10;
    	this.qbmMinPlaylistsAvgWeights = 10;    	
    }

	@Override
	public Boolean addQbmLikertScore(int idQbmPlaylistInfo, int likertScore) {
		try {
			QbmPlaylistInfo qpi = new QbmPlaylistInfo();
			qpi.setIdQbmPlaylistInfo(idQbmPlaylistInfo);
			qpi.setLikertScore(likertScore);
			this.recSysServiceDbTx.updateQbmLikertScore(this.qbmPlInfoMapper, qpi);
			return true;

        } catch (EmoodsicException dex) {
            LogUtils.logEmoodsicException(LOG, dex);
        }
        return false;	
	}
	
    @Override
    public Boolean addQbmLikertScores(List<QbmPlaylistInfo> qpiList) {
		try {
			this.recSysServiceDbTx.updateQbmLikertScores(this.qbmPlInfoMapper, qpiList);
			return true;

        } catch (EmoodsicException dex) {
            LogUtils.logEmoodsicException(LOG, dex);
        }
        return false;	
	}
    
    @Override
    public Boolean addQbmWeights(QbmPlaylist qp) {
		try {
			this.recSysServiceDbTx.updateQbmWeights(this.qbmPlaylistMapper, qp);
			return true;

        } catch (EmoodsicException dex) {
            LogUtils.logEmoodsicException(LOG, dex);
        }
        return false;
    }

	@Override
	public QbmPlaylist createQbmPlaylist(int idUser, int idInitialMoodCategory, int idFinalMoodCategory) {
		
    	QbmPlaylist qbm = new QbmPlaylist();
        try {
        	
        	User currentUser = this.userMapper.getById(idUser);
        	if (currentUser == null) {
        		LOG.error(String.format("createQbmPlaylist: ERROR searching the current user: idUser %d", idUser));
        		return null;
        	}
        	
        	List<FavArtist> fal = this.favArtistMapper.getListByIdUser(idUser);
        	if (fal == null
        	    || fal.isEmpty()) {
        		LOG.error(String.format("createQbmPlaylist: ERROR searching the user's favorite artists: idUser %d", idUser));
        		return null;
        	}
        	currentUser.setFavArtists(fal);
        	
        	List<FavStyle> fsl = this.favStyleMapper.getListByIdUser(idUser);
        	if (fsl == null
        	    || fsl.isEmpty()) {
        		LOG.error(String.format("createQbmPlaylist: ERROR searching the user's favorite styles: idUser %d", idUser));
        		return null;
        	}
        	currentUser.setFavStyles(fsl);
        	
        	////////////////////////////////////////////////////////////////////////////////////////////
        	//Generate a playlist in the database
        	qbm.setIdUser(idUser);
        	qbm.setIdInitialMoodCat(idInitialMoodCategory);
        	qbm.setIdFinalMoodCat(idFinalMoodCategory);
        	
        	MoodCategory mcInit = EmoodsicDbUtils.searchMoodCategory(idInitialMoodCategory);
        	MoodCategory mcFinal = EmoodsicDbUtils.searchMoodCategory(idFinalMoodCategory);
        	
        	if (mcInit == null || mcFinal == null) {
        		LOG.error(String.format(
        			"createQbmPlaylist: ERROR Mood categories not found: idUser %d idInitialMoodCategory %d  idFinalMoodCategory %d",
                	idUser, idInitialMoodCategory, idFinalMoodCategory));
        		return null;
        	}
        	
        	List<Mood> moods = EmoodsicDbUtils.searchMoods(idFinalMoodCategory);
        	if (moods == null || moods.size() == 0) {
        		LOG.error(String.format("createQbmPlaylist: ERROR Moods not found: idUser %d idFinalMoodCategory %d",
                   	idUser, idFinalMoodCategory));
        		return null;
        	}
        	
			//Seed mood to generate the playlist
        	int idSeedMood = EmoodsicDbUtils.getRandomMoodId(moods);
        	if (idSeedMood <= 0) {
        		LOG.error(String.format("createQbmPlaylist: ERROR searching the seed mood: moods %d", moods.size()));
        		return null;
        	}        	        	
        	qbm.setIdSeedMood(idSeedMood);
        	//////////////////////////////////////////////////////////////////////////////////////////////
			
        	//Retrieve the most recent playlist
        	QbmPlaylist lastQbmp = this.qbmPlaylistMapper.getMostRecent(idUser);
        	String qbmFavType = "";
        	
        	if (lastQbmp == null) {
        		qbmFavType = EmoodsicDbUtils.getQbmRandomArtistOrStyle();        	
        	} else {
        		if (lastQbmp.getIdSeedFavStyle() == null) {
        			qbmFavType = RecommendationType.STYLE;
        		} else {
        			//The most recent recommendation to that user for her favorite tastes
        			//was based on the favorite music style. Now it's turn to the favorite artist.
        			qbmFavType = RecommendationType.ARTIST;
        		}
        	}
        	
        	if (qbmFavType.equals(RecommendationType.ARTIST)) {
        		//In this way, it is possible to filter in the next recommendations, as shown in the previous lines.
        		//idSeedFavStyle is initialized to null
        		
        		String randGidFavArtist = RandomUtils.getRandomString(currentUser.getGidsFavArtists());
        		if (randGidFavArtist == null
        		    || randGidFavArtist.isEmpty()) {
            		LOG.error(String.format("createQbmPlaylist: ERROR obtaining randGidFavArtist: idUser %d", idUser));
            		return null;
        		}
        		qbm.setGidSeedFavArtist(randGidFavArtist);
        		
        	} else {
        		//STYLE
        		//idSeedFavArtist initialized to null
        		
        		int randIdFavStlye = RandomUtils.getRandomInt(currentUser.getIdsFavStyles());
        		if (randIdFavStlye <= 0) {
        			LOG.error(String.format("createQbmPlaylist: ERROR obtaining randIdFavStlye: idUser %d", idUser));
            		return null;
        		}
        		qbm.setIdSeedFavStyle(randIdFavStlye);        		    		
        	}
        	
        	//////////////////////////////////////////////////////////////////////////////////////////////
        	//Seed personality style
        	MusicPrefDimProb userProb = this.mpdProbMapper.getByIdUser(idUser);
        	if (userProb == null) {
        		userProb = new MusicPrefDimProb();
        		//Equiprobability       		
        		userProb.setProbRC(0.25);
        		userProb.setProbIR(0.25);
        		userProb.setProbUC(0.25);
        		userProb.setProbER(0.25);
        		
        		userProb.setIdUser(idUser);
        		userProb.setIdMusicPrefDimension(currentUser.getIdMusicPrefDimension());
        	}
        	
        	int idSeedMusicPrefDimStyle = this.searchPersonalityStyle(idUser, userProb);
        	if (idSeedMusicPrefDimStyle <= 0) {
        		LOG.error(String.format("createQbmPlaylist: ERROR obtaining idSeedMusicPrefDimStyle (idUser %d)", idUser));
        		return null;      		
        	}        	
        	qbm.setIdSeedMusicPrefDimStyle(idSeedMusicPrefDimStyle);
        	
        	//////////////////////////////////////////////////////////////////////////////////////////////        	
        	//Number of taste and personality songs
        	int tasteSongsNum = getTasteSongsNextQbmPlaylist(currentUser);
        	int persSongsNum = currentUser.getNumberSongsPlaylist() - tasteSongsNum;
        	
        	qbm.setTasteSongsNum(tasteSongsNum);
        	qbm.setPersonalitySongsNum(persSongsNum);
        	
        	this.recSysServiceDbTx.insertQbmPlaylist(this.qbmPlaylistMapper, qbm);
        	
        	LOG.info(String.format("*** createQbmPlaylist: idUser [%d] [%d -> %d] [taste %d - pers %d]",
            	idUser, idInitialMoodCategory, idFinalMoodCategory, tasteSongsNum, persSongsNum));
        
        } catch (DataAccessException dae) {
        	LogUtils.logDataAccessException(LOG, CommonUtils.getMethodName(), dae.getMessage(),
        		String.format("createQbmPlaylist: ERROR idUser %d idInitialMoodCategory %d idFinalMoodCategory %d",
        		idUser, idInitialMoodCategory, idFinalMoodCategory));           
        	qbm = null;
        
        } catch (EmoodsicException dex) {
            LogUtils.logEmoodsicException(LOG, dex);
            qbm = null;
            
        } catch (Exception ex) {
        	LOG.error(String.format("createQbmPlaylist: %s", ex.getMessage()));
        	qbm = null;
        }
        return qbm;
	}	
	
	private int getTasteSongsNextQbmPlaylist(User currentUser) {
	
		//Taste weight and personality weight
    	//A time window is considered to obtain the average weights for that moment.
    	long minDateWeightCalTime = new Date().getTime()
    		- TimeUnit.MILLISECONDS.convert((long) this.qbmMaxDaysRetention, TimeUnit.DAYS);
    	
    	List<Double> qbmTasteWeights = this.qbmPlaylistMapper.getTasteWeights(currentUser.getIdUser(), minDateWeightCalTime);
    	double tasteWeight = 0;
    	//double persWeight = 0;
    	
    	if (qbmTasteWeights == null
    	    || qbmTasteWeights.isEmpty()) {
    		//It is assumed that no data is available, so the same percentage is given to each category.
    		tasteWeight = 0.5;
    		//persWeight = 0.5;
    	
    	} else {
			//If the list contains few elements, consider also the first recommendation weight
			//to calculate the average weight for user taste category.    		
    		if (qbmTasteWeights.size() < 10) {
    			qbmTasteWeights.add(0.5);
    		}
    		
    		double avgTasteWeight = QbmPlaylistUtils.getAvgTasteWeight(qbmTasteWeights);
    		double qbmMinRecTypeWeight = this.qbmMinRecommendationTypePercent / 100;
    		
    		if (avgTasteWeight < qbmMinRecTypeWeight) {
    			//Average taste weight is small
        		tasteWeight = qbmMinRecTypeWeight;
        		        	
    		} else if (avgTasteWeight > (1 - qbmMinRecTypeWeight)) {
    			//Average taste weight is big
    			tasteWeight = 1 - qbmMinRecTypeWeight;
        	
    		} else {
    			//Normal average weight
    			tasteWeight = avgTasteWeight;
        		//persWeight = 1 - this.qbmMinRecommendationTypePercent;
        	}
    	}    	
    	return (int) Math.round((double)(tasteWeight * currentUser.getNumberSongsPlaylist()));
	}
	
	
	
	private int searchPersonalityStyle(int idUser, MusicPrefDimProb userProb) {
			
		List<MusicPrefDimProbItem> mpdProbItems =
            new ArrayList<MusicPrefDimProbItem>(MusicPrefDimension.NUM_DIM);
        	
    	MusicPrefDimProbItem item1 = new MusicPrefDimProbItem();
    	item1.setIdMusicPrefDimension(MusicPrefDimension.REFLECTIVE_COMPLEX_ID);
    	item1.setProbability(userProb.getProbRC());
    	mpdProbItems.add(item1);
    	
    	MusicPrefDimProbItem item2 = new MusicPrefDimProbItem();
    	item2.setIdMusicPrefDimension(MusicPrefDimension.INTENSE_REBELLIOUS_ID);
    	item2.setProbability(userProb.getProbIR());
    	mpdProbItems.add(item2);
    	
    	MusicPrefDimProbItem item3 = new MusicPrefDimProbItem();
    	item3.setIdMusicPrefDimension(MusicPrefDimension.UPBEAT_CONVENTIONAL_ID);
    	item3.setProbability(userProb.getProbUC());
    	mpdProbItems.add(item3);
    	
    	MusicPrefDimProbItem item4 = new MusicPrefDimProbItem();
    	item4.setIdMusicPrefDimension(MusicPrefDimension.ENERGETIC_RHYTHMIC_ID);
    	item4.setProbability(userProb.getProbER());
    	mpdProbItems.add(item4);
    	
    	//Highest to lowest probability
    	Collections.sort(mpdProbItems);
    	
    	MusicPrefDimProbItem selectedItem = RandomUtils.chooseItemOnProbability(mpdProbItems);
    	int idMusicDimension = -1;
    	if (selectedItem == null) {
    		//Return the element with highest probability
    		idMusicDimension = mpdProbItems.get(0).getIdMusicPrefDimension();
    	} else {
    		idMusicDimension = selectedItem.getIdMusicPrefDimension();
    	}
    	
    	//With the id of the most probable music dimension, it's time to select one
    	//of the music styles assigned to the dimension.
    	return EmoodsicDbUtils.getRandomMusicPrefDimStyle(idMusicDimension);
	}

	@Override
	public QbmPlaylist getMostRecentQbmPlaylist(int idUser) {

		try {        	
			return this.qbmPlaylistMapper.getMostRecent(idUser);

        } catch (DataAccessException dae) {
            LogUtils.logDataAccessException(LOG, CommonUtils.getMethodName(), dae.getMessage(),
                String.format(" idUser %d", idUser));           
        }		
        return null;
	}
	
	@Override
	public List<QbmPlaylistInfo> getQbmPlaylistInfo(int idQbmPlaylist) {
        try {        	
        	return this.qbmPlInfoMapper.getPlaylist(idQbmPlaylist);

        } catch (DataAccessException dae) {
            LogUtils.logDataAccessException(LOG, CommonUtils.getMethodName(), dae.getMessage(),
                String.format(" idQbmPlaylist %d", idQbmPlaylist));           
        }
        return null;
	}
	
	@Override
	public Boolean isMostRecentQbmPlaylistComplete(int idUser) {
		
		try {        	
			QbmPlaylist qp = this.qbmPlaylistMapper.getMostRecent(idUser);
			if (qp == null) {
				//No most recent playlist, so it is complete to create another one
				return true;				
			} else {
				if (qp.getTasteWeight() != null
					&& qp.getTasteWeight() > 0) {
					//The relevance feedback for this playlist has been received
					return true;
				} else {
					//Check if the songs for the playlist where generated
					List<QbmPlaylistInfo> qpiList = this.qbmPlInfoMapper.getSimpleInfo(qp.getIdQbmPlaylist());
					if (qpiList != null
					    && !qpiList.isEmpty()) {
						//No relevance feedback but the songs are stored.
						return false;
					}
				}
			}
        } catch (DataAccessException dae) {
            LogUtils.logDataAccessException(LOG, CommonUtils.getMethodName(), dae.getMessage(),
                String.format(" idUser %d", idUser));           
        }
		// null indicates that it is required to generate the songs for the playlist.
        return null;
	}
	
	/*
	private void setYoutubeVideoId(List<QbmPlaylistInfo> playlist) {
		
		for (QbmPlaylistInfo qpi : playlist) {
			try {			
				this.recSysServiceDbTx.updateYoutubeVideoId(this.qbmPlInfoMapper, qpi);
				
	        } catch (EmoodsicException dex) {
	            LogUtils.logEmoodsicException(LOG, dex);
	        }	        
		}
	}
	*/

	/**
	 * @return the qbmMaxDaysRetention
	 */
	public int getQbmMaxDaysRetention() {
		return qbmMaxDaysRetention;
	}

	/**
	 * @param qbmMaxDaysRetention the qbmMaxDaysRetention to set
	 */
	public void setQbmMaxDaysRetention(int qbmMaxDaysRetention) {
		this.qbmMaxDaysRetention = qbmMaxDaysRetention;
	}

	/**
	 * @return the qbmMinRecommendationTypePercent
	 */
	public double getQbmMinRecommendationTypePercent() {
		return qbmMinRecommendationTypePercent;
	}

	/**
	 * @param qbmMinRecommendationTypePercent the qbmMinRecommendationTypePercent to set
	 */
	public void setQbmMinRecommendationTypePercent(double qbmMinRecommendationTypePercent) {
		this.qbmMinRecommendationTypePercent = qbmMinRecommendationTypePercent;
	}

	/**
	 * @return the qbmLastNSongsRetention
	 */
	public int getQbmLastNSongsRetention() {
		return qbmLastNSongsRetention;
	}

	/**
	 * @param qbmLastNSongsRetention the qbmLastNSongsRetention to set
	 */
	public void setQbmLastNSongsRetention(int qbmLastNSongsRetention) {
		this.qbmLastNSongsRetention = qbmLastNSongsRetention;
	}
	
	
	@Override
	public List<QbmPlaylistInfo> createQbmPlaylistInfo(QbmPlaylist qp) {
		
		//Get the latest N songs recommended to that user.
		// RecommendationType is not filtered
		List<QbmPlaylistInfo> lastSongs = this.searchForLastNSongs(qp);
		
		//The initial list of songs
		List<QbmPlaylistInfo> qpiList =
			this.echoNestMng.createQbmPlaylist(qp, lastSongs);

		if (qpiList != null) {
			try {			
				this.recSysServiceDbTx.insertPlaylist(this.qbmPlInfoMapper, qpiList);
				
				//In the insert is not possible to retrieve the songs Ids, so it is required
				//to select them from the table
				return this.qbmPlInfoMapper.getPlaylist(qp.getIdQbmPlaylist());
				
	        } catch (EmoodsicException dex) {
	            LogUtils.logEmoodsicException(LOG, dex);
	            
	        } catch (DataAccessException dae) {
	            LogUtils.logDataAccessException(LOG, CommonUtils.getMethodName(), dae.getMessage(),
	                String.format(" idQbmPlaylist %d", qp.getIdQbmPlaylist()));           
	        }
		}		
		return null;
	}
	
	
	private List<QbmPlaylistInfo> searchForLastNSongs(QbmPlaylist qp) {
		List<QbmPlaylistInfo> lastSongs = new ArrayList<QbmPlaylistInfo>();
		
    	//A time window is considered to obtain the latest playlist.
    	long minDateWeightCalTime = new Date().getTime()
    		- TimeUnit.MILLISECONDS.convert((long) this.qbmMaxDaysRetention, TimeUnit.DAYS);
    	
		List<Integer> idQbmPlaylistList =
			this.qbmPlaylistMapper.getMostRecentIds(qp.getIdUser(), minDateWeightCalTime);
		
		if (idQbmPlaylistList != null
		    && !idQbmPlaylistList.isEmpty()) {
			
			//Fill the list up to qbmLastNSongsRetention.
			for (int i = 0; i < idQbmPlaylistList.size(); i++) {
				
				List<QbmPlaylistInfo> auxSongs =
				   this.qbmPlInfoMapper.getSimpleInfo(idQbmPlaylistList.get(i));
				
				if (lastSongs.size() + auxSongs.size() <= this.qbmLastNSongsRetention) {
					lastSongs.addAll(auxSongs);
				
				} else {
					for (int j = 0; j < auxSongs.size(); j++) {
						if (lastSongs.size() < this.qbmLastNSongsRetention) {
							lastSongs.add(auxSongs.get(j));
						} else {
							break;
						}
					}
				}				
				if (lastSongs.size() == this.qbmLastNSongsRetention) {
					break;
				}			
			}
		}
		return lastSongs;
	}
	
    public int getQbmMinPlaylistsAvgWeights() {
		return qbmMinPlaylistsAvgWeights;
	}

	public void setQbmMinPlaylistsAvgWeights(int qbmMinPlaylistsAvgWeights) {
		this.qbmMinPlaylistsAvgWeights = qbmMinPlaylistsAvgWeights;
	}
	
	@Override
	public List<QbmPlaylistInfo> queryByMood(final int idUser, final int idInitialMoodCat, final int idFinalMoodCat) {
		List<QbmPlaylistInfo> qpiList = null;
		
		Boolean b = this.isMostRecentQbmPlaylistComplete(idUser);
    	if (b == null) {    		
    		//Generate songs for existing QbmPlaylist
    		QbmPlaylist qp = this.getMostRecentQbmPlaylist(idUser);
    		if (qp != null) {
    			qpiList = this.createQbmPlaylistInfo(qp);
    		}
    	} else if (b == true) {
    		//New playlist    		
        	QbmPlaylist qp = this.createQbmPlaylist(idUser, idInitialMoodCat, idFinalMoodCat);
        	if (qp != null) {
        		qpiList = this.createQbmPlaylistInfo(qp);
        	}        	
    	} else {
    		//The relevance feedback is missing. Display the songs again to the user to be scored.
    		QbmPlaylist qp = this.getMostRecentQbmPlaylist(idUser);
    		if (qp != null) {
    			//Obtain songs and display
    			qpiList = this.getQbmPlaylistInfo(qp.getIdQbmPlaylist());
    		}
    	}
    	
    	return qpiList;	
	}
}
