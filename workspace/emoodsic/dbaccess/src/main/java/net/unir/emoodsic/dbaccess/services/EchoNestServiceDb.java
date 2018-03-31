/**
 * 
 */
package net.unir.emoodsic.dbaccess.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.echonest.api.v4.EchoNestAPI;
import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Location;
import com.echonest.api.v4.Playlist;
import com.echonest.api.v4.PlaylistParams;
import com.echonest.api.v4.Song;
import com.echonest.api.v4.Song.SongType;

import net.unir.emoodsic.common.classes.CommonUtils;
import net.unir.emoodsic.common.classes.EmoodsicException;
import net.unir.emoodsic.common.classes.LogUtils;
import net.unir.emoodsic.common.classes.QbmPlaylistUtils;
import net.unir.emoodsic.common.classes.WekaUtils;
import net.unir.emoodsic.common.entities.FavArtist;
import net.unir.emoodsic.common.entities.Mood;
import net.unir.emoodsic.common.entities.MoodCategory;
import net.unir.emoodsic.common.entities.QbmPersonalityCfInfo;
import net.unir.emoodsic.common.entities.QbmPlaylist;
import net.unir.emoodsic.common.entities.QbmPlaylistInfo;
import net.unir.emoodsic.common.entities.RecommendationType;
import net.unir.emoodsic.common.entities.Style;
import net.unir.emoodsic.dbaccess.classes.ConfigComponent;
import net.unir.emoodsic.dbaccess.classes.EmoodsicDbUtils;
import net.unir.emoodsic.dbaccess.classes.GoogleMapsManager;
import net.unir.emoodsic.dbaccess.classes.YouTubeManager;
import net.unir.emoodsic.dbaccess.interfaces.EchoNestService;
import net.unir.emoodsic.dbaccess.interfaces.MusicBrainzService;
import net.unir.emoodsic.dbaccess.mappers.emoodsic.FavArtistMapper;
import net.unir.emoodsic.dbaccess.mappers.emoodsic.NeighborMapper;
import net.unir.emoodsic.dbaccess.mappers.emoodsic.QbmPlaylistInfoMapper;
import net.unir.emoodsic.dbaccess.mappers.emoodsic.QbmPlaylistMapper;
import net.unir.emoodsic.dbaccess.services.transactional.EchoNestServiceDbTx;


/**
 * @author Álvaro
 * 
 * Manager to handle The Echo Nest API methods
 */
@Service("echoNestServiceDb")
public class EchoNestServiceDb implements EchoNestService {

    /**
     * Class logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(EchoNestServiceDb.class);
    
    private static final String MUSICBRAINZ_ARTIST = "musicbrainz:artist:";
    
    private static final String MUSICBRAINZ_SPACE_ID = "id:musicbrainz";
    
    private static final String LIMITED_INTERACTIVITY = "limited_interactivity";
    
    private static final String MIN_VALENCE = "min_valence";
    
    private static final String MAX_VALENCE = "max_valence";
    
    private static final String SONG_PATH_ARTIST_FOREIGN_ID = "artist_foreign_ids[0].foreign_id";
    
    private static final String SONG_PATH_VALENCE = "audio_summary.valence";
    
	private EchoNestAPI en;
	
	private String apiKey;
	
	@Autowired
	private transient GoogleMapsManager mapsMng;
	
	@Autowired
	private transient MusicBrainzService musicBrainzService;
	
	@Autowired
	private transient YouTubeManager youtubeMng;
	
	@Autowired
	private transient NeighborMapper neighborMapper;
	
	@Autowired
	private transient QbmPlaylistMapper qbmPlaylistMapper;
	
	@Autowired
	private transient QbmPlaylistInfoMapper qbmPlaylistInfoMapper;
	
	//@Autowired
	//private transient ArtistMapper artistMapper;
	
	@Autowired
    private transient EchoNestServiceDbTx echoNestServiceDbTx;
    
    @Autowired
    private transient ConfigComponent configComponent;
    
    @Autowired
    private transient FavArtistMapper favArtistMapper;
    
    /**
     * Maximum days of retention to calculate tastes and personality weights for 
     * a QBM playlist. This value is injected by Spring context on startup.
     */
    private int qbmMaxDaysRetention;
    
    /**
     * Maximum percent of songs recommended by collaborative filtering of nearest neighbors.
     */
    private double qbmMaxCfNeighborsPercent;
    
	/**
	 * Class constructor
	 */
	public EchoNestServiceDb() {
		super();
		this.qbmMaxDaysRetention = 30;
		this.qbmMaxCfNeighborsPercent = 33;
		this.initializeApi();		
	}
	
	public String getApiKey() {
		return this.apiKey;
	}
	
	public void setApiKey(String str) {
		this.apiKey = str;
	}
	
	private void initializeApi() {
		
		if (en == null 
		    && this.apiKey != null) {
			en = new EchoNestAPI(this.apiKey);
			en.setTraceRecvs(true);
			en.setTraceSends(true);
		}
	}
	
	/**
	 * @return the qbmMaxCfNeighborsPercent
	 */
	public double getQbmMaxCfNeighborsPercent() {
		return qbmMaxCfNeighborsPercent;
	}

	/**
	 * @param qbmMaxCfNeighborsPercent the qbmMaxCfNeighborsPercent to set
	 */
	public void setQbmMaxCfNeighborsPercent(double qbmMaxCfNeighborsPercent) {
		this.qbmMaxCfNeighborsPercent = qbmMaxCfNeighborsPercent;
	}	
	
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

	
	
	
	@Override
	public List<QbmPlaylistInfo> createQbmPlaylist(final QbmPlaylist qp, final List<QbmPlaylistInfo> qpiLastSongs) {
		
		this.initializeApi();
		if (this.en == null) {
			LOG.error("EchoNestAPI is null !!!");
			return null;
		}		
		
		//Initial mood category (the one pointed by the user).
		MoodCategory initialMoodCat = EmoodsicDbUtils.getMoodCategoryById(qp.getIdInitialMoodCat());
				
		//Final mood category (the one desired by the user).
		MoodCategory finalMoodCat = EmoodsicDbUtils.getMoodCategoryById(qp.getIdFinalMoodCat());
		
		if (initialMoodCat == null || finalMoodCat == null) {
			LOG.error("Error retrieving mood categories !!!");
			return null;
		}
		
		//Mood
		Mood seedMood = EmoodsicDbUtils.getMoodById(qp.getIdSeedMood());
		
		//The total playlist
		List<QbmPlaylistInfo> qpiTotal =
			new ArrayList<QbmPlaylistInfo>(qp.getTasteSongsNum() + qp.getPersonalitySongsNum());

		//Query By Mood - Favorite tastes (artits or style)
		List<QbmPlaylistInfo> qpiTaste = null;
		
		final List<FavArtist> faList = this.favArtistMapper.getListByIdUser(qp.getIdUser());
		
		if (qp.getGidSeedFavArtist() != null
		    && !qp.getGidSeedFavArtist().isEmpty()) {
			//Favorite artist
			qpiTaste = this.createArtistPlaylist(qp, qpiLastSongs, initialMoodCat, finalMoodCat, seedMood, faList);
			
		} else if (qp.getIdSeedFavStyle() != null
			&& qp.getIdSeedFavStyle() > 0) {
			//Favorite style
			qpiTaste = this.createStylePlaylist(qp, qpiLastSongs, initialMoodCat, finalMoodCat, seedMood, faList);

		} else {
			LOG.error("ERROR in Artist GID or style id !!!");
			return null;
		}
		
		if (qpiTaste == null || qpiTaste.isEmpty()) {
			LOG.error(String.format("Error creating taste playlist: idQbmPlaylist %d idUser %d",
			    qp.getIdQbmPlaylist(), qp.getIdUser()));
			return null;
		} else {
			qpiTotal.addAll(qpiTaste);
		}
		
		List<QbmPlaylistInfo> qpiPersonality = this.createPersonalityPlaylist(qp, qpiTaste, qpiLastSongs,
			initialMoodCat, finalMoodCat, seedMood, faList);
		
		if (qpiPersonality == null || qpiPersonality.isEmpty()) {
			LOG.error(String.format("Error creating personality playlist: idQbmPlaylist %d idUser %d",
		        qp.getIdQbmPlaylist(), qp.getIdUser()));
			return null;
		} else {
			qpiTotal.addAll(qpiPersonality);
		}		
		
		//1. Obtain Musicbrainz Gids for recordings based on their song name
		this.musicBrainzService.setRecordingGid(qpiTotal);
		
		//2. Obtain youtube video id in order to be listened by the user
		this.youtubeMng.searchVideos(qpiTotal);

		//3. Obtain geolocation for given song's latitude and longitude
		this.mapsMng.searchLocation(qpiTotal);
		
		//4. Finally, order the songs regarding projection along the minimum and maximum values of 
		//arousal and valence.		
		QbmPlaylistUtils.sortSongs(qp, qpiTotal, initialMoodCat, finalMoodCat);
		//[150916] Update sort order
		this.addQbmSortOrder(qp);
		
		return qpiTotal;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
    
	
	private List<QbmPlaylistInfo> createArtistPlaylist(QbmPlaylist qp, List<QbmPlaylistInfo> qpiLastSongs,
		MoodCategory initialMoodCat, MoodCategory finalMoodCat, Mood seedMood, final List<FavArtist> faList) {
		
		PlaylistParams params = new PlaylistParams();
		
		//Artist
		params.setType(PlaylistParams.PlaylistType.ARTIST_RADIO);
		params.addArtistID(String.format("%s%s", MUSICBRAINZ_ARTIST, qp.getGidSeedFavArtist()));
		
		//Mood
		params.addMood(seedMood.getName());
		
		//Final Mood category - Thayer's model Arousal-Valence values for that category
		params.set(MIN_VALENCE, (float) Math.min(initialMoodCat.getMinValence(), finalMoodCat.getMinValence()));
		params.set(MAX_VALENCE, (float) Math.max(initialMoodCat.getMaxValence(), finalMoodCat.getMaxValence()));
		params.setMinEnergy((float) Math.min(initialMoodCat.getMinArousal(), finalMoodCat.getMinArousal()));
		params.setMaxEnergy((float) Math.max(initialMoodCat.getMaxArousal(), finalMoodCat.getMaxArousal()));
		
		//bucket
		//https://github.com/echonest/jEN/blob/master/src/com/echonest/api/v4/examples/RosettaPlaylistExample.java
		params.addIDSpace(MUSICBRAINZ_SPACE_ID);
		
		//artist_location
		params.includeArtistLocation();
		
		//audio_summary
		params.includeAudioSummary();

		//Double the number of taste songs to have enough at a time, so it is possible to search easily
		//for duplicates
		params.setResults(2 * qp.getTasteSongsNum());
		
		//Select only studio songs
		params.addSongType(SongType.studio, Song.SongTypeFlag.True);
		
		//[150830] limited_interactivity
		params.add(LIMITED_INTERACTIVITY, true);
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		List<QbmPlaylistInfo> artistPlaylist = new ArrayList<QbmPlaylistInfo>();
		List<QbmPlaylistInfo> dupOrNonRelevList = new ArrayList<QbmPlaylistInfo>();

		try {			
			Playlist playlist = en.createStaticPlaylist(params);
			
			//Song to QbmPlaylistInfo conversion
			for (Song song : playlist.getSongs()) {
	            QbmPlaylistInfo qpi = this.parseSongInfo(song, qp.getIdQbmPlaylist(), finalMoodCat);
            
	            if (qpi != null) {
	            	qpi.setIdRecommendationType(EmoodsicDbUtils.getIdRecommendationType(RecommendationType.ARTIST));
	            	
	            	if (this.isDuplicateOrNonRelevantSong(qpi, qpiLastSongs, faList)) {
	            		dupOrNonRelevList.add(qpi);
	            	} else {		            	
		            	artistPlaylist.add(qpi);
	            		//[150902] Do not add the same song twice.
	            		qpiLastSongs.add(qpi);
		            	if (artistPlaylist.size() == qp.getTasteSongsNum()) {
		            		break;
		            	}
	            	}
	            }
	        }
			
			//If the list is not filled, complete although duplicates are required
			if (artistPlaylist.size() != qp.getTasteSongsNum()) {
				for (QbmPlaylistInfo qpi : dupOrNonRelevList) {
					LOG.warn(String.format("*** createArtistPlaylist Duplicated or Non Relevant %s - %s",
						qpi.getArtist(), qpi.getSong()));
	            	artistPlaylist.add(qpi);
	            	if (artistPlaylist.size() == qp.getTasteSongsNum()) {
	            		break;
	            	}					
				}		
			}	
		} catch (EchoNestException e) {
			LOG.error(e.getMessage());
			return null;
		}
        return artistPlaylist;
	}
	
	
	/**
	 * Creates a Favorite Style playlist.
	 * @param qp
	 * @param qpiLastSongs
	 * @param initialMoodCat
	 * @param finalMoodCat
	 * @param seedMood
	 * @return
	 */
    private List<QbmPlaylistInfo> createStylePlaylist(QbmPlaylist qp, List<QbmPlaylistInfo> qpiLastSongs, MoodCategory initialMoodCat,
		MoodCategory finalMoodCat, Mood seedMood, final List<FavArtist> faList) { 
		
    	PlaylistParams params = new PlaylistParams();
		
		//Style -> artist-description
		params.setType(PlaylistParams.PlaylistType.ARTIST_DESCRIPTION);
		
		Style favStyle = EmoodsicDbUtils.getStyleById(qp.getIdSeedFavStyle());
		if (favStyle == null) {
			LOG.error("Error retrieving user's favorite Style !!!");
			return null;
		}
		params.addStyle(favStyle.getName());
		
		//Mood
		params.addMood(seedMood.getName());
		
		//Final Mood category - Thayer's model Arousal-Valence values for that category
		params.set(MIN_VALENCE, (float) Math.min(initialMoodCat.getMinValence(), finalMoodCat.getMinValence()));
		params.set(MAX_VALENCE, (float) Math.max(initialMoodCat.getMaxValence(), finalMoodCat.getMaxValence()));
		params.setMinEnergy((float) Math.min(initialMoodCat.getMinArousal(), finalMoodCat.getMinArousal()));
		params.setMaxEnergy((float) Math.max(initialMoodCat.getMaxArousal(), finalMoodCat.getMaxArousal()));
		
		//bucket
		//https://github.com/echonest/jEN/blob/master/src/com/echonest/api/v4/examples/RosettaPlaylistExample.java
		params.addIDSpace(MUSICBRAINZ_SPACE_ID);
		
		//artist_location
		params.includeArtistLocation();
		
		//audio_summary
		params.includeAudioSummary();

		//Double the number of taste songs to have enough at a time, so it is possible to search easily
		//for duplicates
		params.setResults(2 * qp.getTasteSongsNum());
		
		//Select only studio songs
		params.addSongType(SongType.studio, Song.SongTypeFlag.True);
		
		//[150830] limited_interactivity
		params.add(LIMITED_INTERACTIVITY, true);
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		List<QbmPlaylistInfo> stylePlaylist = new ArrayList<QbmPlaylistInfo>();
		List<QbmPlaylistInfo> dupOrNonRelevList = new ArrayList<QbmPlaylistInfo>();
		
		try {			
			Playlist playlist = en.createStaticPlaylist(params);
			
			//Song to QbmPlaylistInfo conversion
			for (Song song : playlist.getSongs()) {
	            QbmPlaylistInfo qpi = this.parseSongInfo(song, qp.getIdQbmPlaylist(), finalMoodCat);
	            
	            if (qpi != null) {
	            	qpi.setIdRecommendationType(EmoodsicDbUtils.getIdRecommendationType(RecommendationType.STYLE));
	            	
	            	if (this.isDuplicateOrNonRelevantSong(qpi, qpiLastSongs, faList)) {
	            		dupOrNonRelevList.add(qpi);
	            	} else {		            	
	            		stylePlaylist.add(qpi);
	            		//[150902] Do not add the same song twice.
	            		qpiLastSongs.add(qpi);
		            	if (stylePlaylist.size() == qp.getTasteSongsNum()) {
		            		break;
		            	}
	            	}
	            }
	        }
			
			//If the list is not filled, complete although duplicates are required
			if (stylePlaylist.size() != qp.getTasteSongsNum()) {
				for (QbmPlaylistInfo qpi : dupOrNonRelevList) {
					LOG.warn(String.format("*** createStylePlaylist Duplicated or Non Relevant %s - %s",
						qpi.getArtist(), qpi.getSong()));
					stylePlaylist.add(qpi);
	            	if (stylePlaylist.size() == qp.getTasteSongsNum()) {
	            		break;
	            	}					
				}		
			}
		} catch (EchoNestException e) {
			LOG.error(e.getMessage());
			return null;
		}
        return stylePlaylist;
	}
	
    
	
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private List<QbmPlaylistInfo> createPersonalityPlaylist(QbmPlaylist qp, List<QbmPlaylistInfo> qpiTaste,
		List<QbmPlaylistInfo> qpiLastSongs, MoodCategory initialMoodCat, MoodCategory finalMoodCat,
		Mood seedMood, final List<FavArtist> faList) { 
		
		PlaylistParams params = new PlaylistParams();
		
		//Personality = style -> artist-description
		params.setType(PlaylistParams.PlaylistType.ARTIST_DESCRIPTION);
		
		Style favStyle = EmoodsicDbUtils.getStyleById(qp.getIdSeedMusicPrefDimStyle());
		if (favStyle == null) {
			LOG.error("Error retrieving personality Style !!!");
			return null;
		}
		params.addStyle(favStyle.getName());
		
		//Mood
		params.addMood(seedMood.getName());
		
		//Final Mood category - Thayer's model Arousal-Valence values for that category
		params.set(MIN_VALENCE, (float) Math.min(initialMoodCat.getMinValence(), finalMoodCat.getMinValence()));
		params.set(MAX_VALENCE, (float) Math.max(initialMoodCat.getMaxValence(), finalMoodCat.getMaxValence()));
		params.setMinEnergy((float) Math.min(initialMoodCat.getMinArousal(), finalMoodCat.getMinArousal()));
		params.setMaxEnergy((float) Math.max(initialMoodCat.getMaxArousal(), finalMoodCat.getMaxArousal()));
		
		//bucket
		//https://github.com/echonest/jEN/blob/master/src/com/echonest/api/v4/examples/RosettaPlaylistExample.java
		params.addIDSpace(MUSICBRAINZ_SPACE_ID);
		
		//artist_location
		params.includeArtistLocation();
		
		//audio_summary
		params.includeAudioSummary();

		//Double the number of taste songs to have enough at a time, so it is possible to search easily
		//for duplicates
		params.setResults(2 * qp.getPersonalitySongsNum());
		
		//Select only studio songs
		params.addSongType(SongType.studio, Song.SongTypeFlag.True);
		
		//[150830] limited_interactivity
		params.add(LIMITED_INTERACTIVITY, true);
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		List<QbmPlaylistInfo> personalityPlaylist = new ArrayList<QbmPlaylistInfo>();
		List<QbmPlaylistInfo> dupOrNonRelevList = new ArrayList<QbmPlaylistInfo>();
		
		//Search CF Info from neighbors
		List<QbmPlaylistInfo> neighborsRelevantSongs = new ArrayList<QbmPlaylistInfo>();
		List<QbmPersonalityCfInfo> cfInfo = new ArrayList<QbmPersonalityCfInfo>();		
		
		try {			
			Playlist playlist = en.createStaticPlaylist(params);
			
			///////////////////////////////////////////////////////////////////////////////////////////////////////////
			//Collaborative Filter
			//TODO
			this.searchQbmPersonalityCfInfo(qp, neighborsRelevantSongs, cfInfo);
			
			List<Song> pendingSongs = this.cfFilterSongs(playlist, personalityPlaylist, dupOrNonRelevList,
			    neighborsRelevantSongs, cfInfo, qp, finalMoodCat, qpiLastSongs, faList);

			if (pendingSongs != null
				&& !pendingSongs.isEmpty()
			    && personalityPlaylist.size() < qp.getPersonalitySongsNum()) {
				
				//Add songs to the playlist which have not been filtered by the CF process.
				//The number
				this.addPersonalitySongs(personalityPlaylist, pendingSongs, dupOrNonRelevList, qpiLastSongs,
					qp, finalMoodCat, faList);
			}
			
			////////////////////////////////////////////////////////////////////////////////////////////////////////			
			//Finally, if the list is not filled, complete although duplicates are required
			if (personalityPlaylist.size() < qp.getPersonalitySongsNum()) {
				for (QbmPlaylistInfo qpi : dupOrNonRelevList) {
					LOG.warn(String.format("*** createPersonalityPlaylist Duplicated or Non Relevant %s - %s",
						qpi.getArtist(), qpi.getSong()));
					personalityPlaylist.add(qpi);
	            	if (personalityPlaylist.size() == qp.getPersonalitySongsNum()) {
	            		break;
	            	}
				}		
			}
			////////////////////////////////////////////////////////////////////////////////////////////////////////			
			
		} catch (EchoNestException e) {
			LOG.error(e.getMessage());
			return null;
		}
        return personalityPlaylist;
	}
	

	/**
	 * 
	 * @param playlist					the playlist recently received by The Echo Nest
	 * @param personalityPlaylist		the list to fill
	 * @param duplicatedList			a list containing duplicated songs respect from the last N recommendations
	 * @param neighborsRelevantSongs	a list containing songs which are relevant for the nearest neighbors.
	 * @param cfInfo					a list containing information for the CF process (Naive Bayes).
	 * @param qp						the current QbmPlaylist.
	 * @param finalMoodCat				the final mood category.
	 * @param qpiLastSongs				the last N songs recommended to a certain user.
	 * @return
	 */
	private List<Song> cfFilterSongs(Playlist playlist, List<QbmPlaylistInfo> personalityPlaylist,
		List<QbmPlaylistInfo> dupOrNonRelevList, List<QbmPlaylistInfo> neighborsRelevantSongs,
	    List<QbmPersonalityCfInfo> cfInfo, QbmPlaylist qp, MoodCategory finalMoodCat,
	    List<QbmPlaylistInfo> qpiLastSongs, final List<FavArtist> faList) {
		
		
		//return playlist.getSongs();
		
		//This array will contain the songs which have not been marked as relevant by the classifier
		List<Song> pendingSongs = new ArrayList<Song>();
		
		///////////////////////////////////////////////////////////////////////////////////////////////////
		//Collaborative Filtering stage
		if (cfInfo != null && !cfInfo.isEmpty()
			&& !playlist.getSongs().isEmpty()) {
			
			//Evaluation set
			List<QbmPersonalityCfInfo> songsEvalInfo = new ArrayList<QbmPersonalityCfInfo>();
			
			for (Song song : playlist.getSongs()) {
				QbmPersonalityCfInfo si = new QbmPersonalityCfInfo();
				
				//Naive Bayes with Artist ID
				//String str = song.getString(SONG_PATH_ARTIST_FOREIGN_ID);
//				if (str != null
//				    && str.startsWith(MUSICBRAINZ_ARTIST)) {				
//					String gidArtistMb = str.substring(MUSICBRAINZ_ARTIST.length(), str.length());
//					Integer artistCredit = this.artistMapper.getIdByGid(gidArtistMb);
//					if (artistCredit == null || artistCredit <= 0) {
//						pendingSongs.add(song);
//						continue;
//					} else {
//						si.setArtistCredit(artistCredit);
//					}
//				} else {
//					pendingSongs.add(song);
//					continue;
//				}
				
				si.setArtist(song.getArtistName());		
				si.setSong(song.getTitle());
				si.setFinalMoodCat(finalMoodCat.getName());
				//Mark as non-relevant
				si.setLikertScore(QbmPersonalityCfInfo.MIN_RELEVANT_SCORE - 1);
				try {
					si.setArousal(song.getEnergy());
					//song.fetchBucket("audio_summary");
					si.setValence(song.getDouble(SONG_PATH_VALENCE));
					si.setStyle(EmoodsicDbUtils.getStyleById(qp.getIdSeedMusicPrefDimStyle()).getName());
					
				} catch (EchoNestException e) {
					LOG.error(String.format("*** Error obtaining energy and valence for %s - %s: %s",
						song.getArtistName(), song.getTitle(), e.getMessage()));
					
					//By default, assign the minimum values of the target mood category
					si.setArousal(finalMoodCat.getMinArousal());
					si.setValence(finalMoodCat.getMinValence());
				} catch (Exception e) {
					//Not valid
					continue;
				}
				songsEvalInfo.add(si);
			}
			
			//Classify all received songs
			
			
			WekaUtils.classifyQbmPersonalitySongs(cfInfo, songsEvalInfo,
				EmoodsicDbUtils.getMoodCategoryList(), EmoodsicDbUtils.getMusicDimStyleList(),
				this.configComponent.getNbMinPctCorrect());
			
			for (Song song : playlist.getSongs()) {
				
				if (isSongCfRelevant(song, songsEvalInfo)) {
					
					QbmPlaylistInfo qpi = this.parseSongInfo(song, qp.getIdQbmPlaylist(), finalMoodCat);
		            if (qpi != null) {
		            	qpi.setIdRecommendationType(
		            		EmoodsicDbUtils.getIdRecommendationType(RecommendationType.CF_PERSONALITY));
		            	
		            	if (this.isDuplicateOrNonRelevantSong(qpi, qpiLastSongs, faList)) {
		            		dupOrNonRelevList.add(qpi);
		            	} else {            	
		            		personalityPlaylist.add(qpi);
		            		//In order not to add the same song twice.
		            		qpiLastSongs.add(qpi);
			            	if (personalityPlaylist.size() == qp.getPersonalitySongsNum()) {
			            		break;
			            	}
		            	}
		            }
				} else {
					//The song has benn marked as non relevant.
					pendingSongs.add(song);
				}
			}			
		} else {
			//No CF stage. Pending songs to review are all.
			pendingSongs = playlist.getSongs();				
		}
		
		/////////////////////////////////////////////////////////////////////////////////////////////////
		//Furthermore, the neighbors may give some recommendations up to a certain limit (configurable)
		if (personalityPlaylist.size() < qp.getPersonalitySongsNum()
			&& neighborsRelevantSongs != null && !neighborsRelevantSongs.isEmpty()) {
			
			//Check the maximum number of songs allowed to be recommended this way
			int iMaxCfNeighbors = (int)Math.round(qp.getPersonalitySongsNum() * this.qbmMaxCfNeighborsPercent / 100);			
			
			if (iMaxCfNeighbors > 0) {				
				int iCfNeighbors = 0;				
				for (QbmPlaylistInfo qpi : neighborsRelevantSongs) {
				
					qpi.setIdQbmPlaylistInfo(-1);
					qpi.setIdQbmPlaylist(qp.getIdQbmPlaylist());
					qpi.setIdRecommendationType(
						EmoodsicDbUtils.getIdRecommendationType(RecommendationType.CF_NEIGHBOR));
					//The rest of properties have been retrieved from DB previously
					
	            	if (this.isDuplicateOrNonRelevantSong(qpi, qpiLastSongs, faList)) {
	            		dupOrNonRelevList.add(qpi);
	            	} else {		            	
	            		personalityPlaylist.add(qpi);
	            		//In order not to add the same song twice.
	            		qpiLastSongs.add(qpi);
	            		iCfNeighbors += 1;
	            		
		            	if (personalityPlaylist.size() == qp.getPersonalitySongsNum()
		            		|| iCfNeighbors >= iMaxCfNeighbors) {
		            		//If the playlist is complete, or the maximum number of songs allowed to be
		            		//recommended by neighbors is enough, break
		            		break;
		            	}
	            	}
				}				
			}
		}
		
		/////////////////////////////////////////////////////////////////////////////////////////////////
		//Return the rest of the songs not processed in CF		
		return pendingSongs;

	}
	
	
	
	private boolean isSongCfRelevant(Song tenSong, List<QbmPersonalityCfInfo> songsEvalInfo){
		
		boolean isRelevant = false;
		
		for (QbmPersonalityCfInfo qpci: songsEvalInfo) {		
			if (qpci.getArtist().equals(tenSong.getArtistName())
				&& qpci.getSong().equals(tenSong.getTitle())
				&& qpci.getLikertScore() >= QbmPersonalityCfInfo.MIN_RELEVANT_SCORE) {
				
				isRelevant = true;
				break;
			}			
		}	
		return isRelevant;
	}
	
	private void addPersonalitySongs(List<QbmPlaylistInfo> personalityPlaylist, List<Song> pendingSongs,
		List<QbmPlaylistInfo> dupOrNonRelList, List<QbmPlaylistInfo> qpiLastSongs, QbmPlaylist qp,
		MoodCategory finalMoodCat, final List<FavArtist> faList) {
		
		for (Song song : pendingSongs) {
            QbmPlaylistInfo qpi = this.parseSongInfo(song, qp.getIdQbmPlaylist(), finalMoodCat);
            
            if (qpi != null) {
            	qpi.setIdRecommendationType(EmoodsicDbUtils.getIdRecommendationType(RecommendationType.PERSONALITY));
            	
            	if (this.isDuplicateOrNonRelevantSong(qpi, qpiLastSongs, faList)) {
            		dupOrNonRelList.add(qpi);
            	} else {		            	
            		personalityPlaylist.add(qpi);
            		//[150902] In order not to add the same song twice.
            		qpiLastSongs.add(qpi);
	            	if (personalityPlaylist.size() == qp.getPersonalitySongsNum()) {
	            		break;
	            	}
            	}
            }
        }
	}
	
	/**
	 * Parses the information received by The Echo Nest into a QbmPlaylistinfo object.
	 * @param song
	 * @param idQbmPlaylist
	 * @param moodCat
	 * @return
	 */
	private QbmPlaylistInfo parseSongInfo(Song song, int idQbmPlaylist, MoodCategory moodCat) {
		QbmPlaylistInfo qpi = new QbmPlaylistInfo();
		
		//idQbmPlaylist when the object is inserted
		qpi.setIdQbmPlaylist(idQbmPlaylist);	
		
		//Artist
		// "foreign_id": "musicbrainz:artist:b185451a-9edd-46df-8046-1db7e9594f5a"
		String str = song.getString(SONG_PATH_ARTIST_FOREIGN_ID);
		if (str != null
		    && str.startsWith(MUSICBRAINZ_ARTIST)) {			
			qpi.setGidArtistMb(str.substring(MUSICBRAINZ_ARTIST.length(), str.length()));
		}
		qpi.setArtist(song.getArtistName());
		
		//Song GID afterwards, when searched for all songs
		qpi.setSong(song.getTitle());
		
		//Youtube Video ID afterwards.
		
		try {
			Location loc = song.getArtistLocation();
			if (loc != null) {
				qpi.setLatitude(loc.getLatitude());
				qpi.setLongitude(loc.getLongitude());
			}
		} catch (EchoNestException e) {
			LOG.error(String.format("Error obtaining artist location for %s - %s: %s",
				song.getArtistName(), song.getTitle(), e.getMessage()));
		}		
		
		try {
			qpi.setArousal(song.getEnergy());
			//song.fetchBucket("audio_summary");
			qpi.setValence(song.getDouble(SONG_PATH_VALENCE));
			
		} catch (EchoNestException e) {
			LOG.error(String.format("Error obtaining energy and valence for %s - %s: %s",
				song.getArtistName(), song.getTitle(), e.getMessage()));
			
			//By default, assign the minimum values of the target mood category
			qpi.setArousal(moodCat.getMinArousal());
			qpi.setValence(moodCat.getMinValence());
		}
	
		//[150907] Not rated
		qpi.setLikertScore(0);
		
		return qpi;
	}
	
	private boolean isDuplicateOrNonRelevantSong(QbmPlaylistInfo qpi, List<QbmPlaylistInfo> qpiLastSongs,
		final List<FavArtist> faList) {
		
		if (qpiLastSongs == null
		    || qpiLastSongs.isEmpty()) {
			//There aren't any previous songs
			return false;
		}
		
		boolean bCheckRelevance = false;
		
		//Check for duplicates
		for (QbmPlaylistInfo lastNSong : qpiLastSongs) {

			try {
//v1
//				if (lastNSong.getGidArtistMb() != null
//					&& lastNSong.getGidArtistMb().equals(qpi.getGidArtistMb())) {
//					bCheckRelevance = true;
//					if (lastNSong.getSong().equals(qpi.getSong())) {
//						//Duplicate
//						return true;
//					}	
//				} else if (lastNSong.getArtist().equals(qpi.getArtist())
//				    && lastNSong.getSong().equals(qpi.getSong())) {
//					//Duplicate
//					return true;				
//				}
				if (lastNSong.getGidArtistMb() != null
					&& lastNSong.getGidArtistMb().equals(qpi.getGidArtistMb())) {
					
					if (lastNSong.getSong().equals(qpi.getSong())) {
						//Duplicate: artist and song
						return true;
					} else {
						bCheckRelevance = true;
					}	
				} else if (lastNSong.getArtist() != null
					&& lastNSong.getArtist().equals(qpi.getArtist())) {
				   
					if(lastNSong.getSong() != null
						&& lastNSong.getSong().equals(qpi.getSong())) {
				    	//Duplicate: artist and song
				    	return true;
				    } else {
				    	bCheckRelevance = true;
				    }
				}
				//TODO suprimir canciones con caracteres en ruso o chino??
			} catch (NullPointerException npe) {
				//[150902] Any field could be null
				LOG.error(String.format("isDuplicateOrNonRelevantSong: %s", npe.getMessage()));
				continue;
			}
		}
		
		//The song is not a duplicate, is the artist relevant for this user?
		boolean bDuplicateOrNonRelevant = false;
		if (bCheckRelevance) {
			int iTotal = 0;
			int iCount = 0;
			
			for (QbmPlaylistInfo lastNSong : qpiLastSongs) {
				
				try {
					//In the last N songs there may be more than one song from the current artist
					if (lastNSong.getArtist() != null
						&& lastNSong.getArtist().equals(qpi.getArtist())) {
						
						if (lastNSong.getLikertScore() == 0) {
							//Consider there isn't enough data
							//Non-relevant
							bDuplicateOrNonRelevant = true;
							break;
						} else if (0 < lastNSong.getLikertScore()) {
							
							//If the artist is one of the favorites do not consider...
							for (FavArtist fa: faList) {
								if (fa.getGid() != null
									&& fa.getGid().equals(lastNSong.getGidArtistMb())) {
									return false;
								}
							}
							iTotal += lastNSong.getLikertScore();
							iCount += 1;
						}
					}
				} catch (NullPointerException npe) {
					//[150912] In case gid is null
					continue;
				}
			}
			
			if (iCount > 0) {
				//Check if the artist is relevant or not
				double avgScore = (double) (iTotal/(double) iCount);				
				if ((int)Math.round(avgScore) < QbmPersonalityCfInfo.MIN_RELEVANT_SCORE) {
					//Non-relevant
					bDuplicateOrNonRelevant = true;
				}
			}
		}

		//Not duplicate and may be relevant
		return bDuplicateOrNonRelevant;
	}
	
	/**
	 * Searches songs for collaborative filtering processing in personality songs.
	 *
	 * @param qp	the current user's QbmPlaylist containing seed info.
	 * @return
	 */
	private void searchQbmPersonalityCfInfo(QbmPlaylist qp, List<QbmPlaylistInfo> neighborsRelevantSongs,
		List<QbmPersonalityCfInfo> cfInfo) {	
		
		try {
			String idNeighbors = this.neighborMapper.getNeighbors(qp.getIdUser());
			if (idNeighbors == null
				|| idNeighbors.isEmpty()) {
				return;
			}

			//Obtain neighbor ids to searh for their playlists
			String[] strArray = idNeighbors.split(",");
			int[] intArray = new int[strArray.length];
			for(int i = 0; i < strArray.length; i++) {
			    intArray[i] = Integer.parseInt(strArray[i]);
			}
			
			//For each user, obtain the ids of the ranked playlist in the last time window
			for (int idUser: intArray) {
				
		    	//A time window is considered to obtain the playlists.
		    	long minDatePlaylists = new Date().getTime()
		    		- TimeUnit.MILLISECONDS.convert((long) this.qbmMaxDaysRetention, TimeUnit.DAYS);
		    	
				List<QbmPlaylist> scoredPlaylists = this.qbmPlaylistMapper.getCfInfo(idUser, minDatePlaylists);
				if (scoredPlaylists == null
					|| scoredPlaylists.isEmpty()) {
					//No valid playlists for this users
					continue;
				}
			
				List<Integer> cfRecommendationTypes = new ArrayList<Integer>();
				int rt = EmoodsicDbUtils.getIdRecommendationType(RecommendationType.PERSONALITY);
				cfRecommendationTypes.add(rt);
				rt = EmoodsicDbUtils.getIdRecommendationType(RecommendationType.CF_PERSONALITY);
				cfRecommendationTypes.add(rt);
				rt = EmoodsicDbUtils.getIdRecommendationType(RecommendationType.CF_NEIGHBOR);
				cfRecommendationTypes.add(rt);
				
				for(QbmPlaylist sp: scoredPlaylists) {
					
					List<QbmPlaylistInfo> scoredSongs =
						this.qbmPlaylistInfoMapper.getCfInfo(sp.getIdQbmPlaylist(), cfRecommendationTypes);
					if (scoredSongs == null
						|| scoredSongs.isEmpty()) {
						continue;
					}
					
					for (QbmPlaylistInfo ss: scoredSongs) {
						
						QbmPersonalityCfInfo qpci = new QbmPersonalityCfInfo();
						qpci.setIdQbmPlaylistInfo(ss.getIdQbmPlaylistInfo());
						qpci.setArtist(ss.getArtist());
						MoodCategory finalMoodCat = EmoodsicDbUtils.getMoodCategoryById(sp.getIdFinalMoodCat());
						qpci.setFinalMoodCat(finalMoodCat.getName());
						qpci.setLikertScore(ss.getLikertScore());
						qpci.setValence(ss.getValence());
						qpci.setArousal(ss.getArousal());
						try {
							qpci.setStyle(EmoodsicDbUtils.getStyleById(sp.getIdSeedMusicPrefDimStyle()).getName());
						} catch (Exception ex) {
							//Not valid
							continue;
						}
						//Add the object which will be used in the Naive Bayes classifier
						cfInfo.add(qpci);
						
						//////////////////////////////////////////////////////////////////////////////////
						//If the song has been relevant for the user, add to the relevant set
						//The final mood category and the music preference dimension style must be the same
						//in order to maintain certain comprehension of the recommendation done
						//TODO check if the dimension style should be the same
						if (qp.getIdFinalMoodCat() == sp.getIdFinalMoodCat()
							&& qp.getIdSeedMusicPrefDimStyle() == sp.getIdSeedMusicPrefDimStyle()
							&& ss.getLikertScore() >= QbmPersonalityCfInfo.MIN_RELEVANT_SCORE) {
							neighborsRelevantSongs.add(ss);
						}
						//////////////////////////////////////////////////////////////////////////////////					
//						Integer artistCredit = this.artistMapper.getIdByGid(rs.getGidArtistMb());
//						if (artistCredit != null && artistCredit > 0) {
//							qpci.setArtistCredit(artistCredit);
//						}
					}
				}
			}		
		} catch (DataAccessException dae) {
            LogUtils.logDataAccessException(LOG, CommonUtils.getMethodName(), dae.getMessage());           
        }
	}
	
    
    @Override
    public Boolean addQbmSortOrder(QbmPlaylist qp) {
		try {
			this.echoNestServiceDbTx.updateQbmSortOrder(this.qbmPlaylistMapper, qp);
			return true;

        } catch (EmoodsicException dex) {
            LogUtils.logEmoodsicException(LOG, dex);
        }
        return false;
    }
}
