package net.unir.emoodsic.common.entities;

import java.io.Serializable;

public class QbmPlaylistInfo implements Serializable, Comparable<QbmPlaylistInfo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1717086974556707681L;

	/**
	 *  Echo Nest values for valence and energy are in a [0,1] scale.
	 *	The maximum distance goes from (0,0) to (1,1).
	 *	This value will be used in case of unkown values of arousal and valence for the song.
	 */
	public static final double MAX_PLAYLIST_DISTANCE = Math.sqrt(2);
	
	public static final int MIN_LIKERT_SCORE = 0;
	
	public static final int MAX_LIKERT_SCORE = 5;
	
	private int idQbmPlaylistInfo;
	
	private int idQbmPlaylist;
	
	private int idRecommendationType;
	
	private String gidArtistMb;
	
	private String artist;
	
	private String gidRecordingMb;
	
	private String song;
	
	private int likertScore;
	
	/**
	 * The song is displayed through a Youtube video.
	 */
	private String youtubeVideoId;

	private double latitude;
	
	private double longitude;
	
	private String country;
	
	private double valence;
		
	/**
	 * If available, corresponds to energy level in The Echo Nest.
	 */
	private double arousal;
	
	/**
	 * The distance to the final objective in the playlist
	 */
	private double playlistDistance;
	
	
	public QbmPlaylistInfo() {
		super();		
		this.playlistDistance = MAX_PLAYLIST_DISTANCE;
		this.latitude = 0;
		this.longitude = 0;
		this.likertScore = 0;
	}

	/**
	 * @return the idQbmPlaylistInfo
	 */
	public int getIdQbmPlaylistInfo() {
		return idQbmPlaylistInfo;
	}

	/**
	 * @param idQbmPlaylistInfo the idQbmPlaylistInfo to set
	 */
	public void setIdQbmPlaylistInfo(int idQbmPlaylistInfo) {
		this.idQbmPlaylistInfo = idQbmPlaylistInfo;
	}

	/**
	 * @return the idQbmPlaylist
	 */
	public int getIdQbmPlaylist() {
		return idQbmPlaylist;
	}

	/**
	 * @param idQbmPlaylist the idQbmPlaylist to set
	 */
	public void setIdQbmPlaylist(int idQbmPlaylist) {
		this.idQbmPlaylist = idQbmPlaylist;
	}

	/**
	 * @return the idRecommendationType
	 */
	public int getIdRecommendationType() {
		return idRecommendationType;
	}

	/**
	 * @param idRecommendationType the idRecommendationType to set
	 */
	public void setIdRecommendationType(int idRecommendationType) {
		this.idRecommendationType = idRecommendationType;
	}

	/**
	 * @return the gidArtistMb
	 */
	public String getGidArtistMb() {
		return gidArtistMb;
	}

	/**
	 * @param gidArtistMb the gidArtistMb to set
	 */
	public void setGidArtistMb(String gidArtistMb) {
		this.gidArtistMb = gidArtistMb;
	}

	/**
	 * @return the artist
	 */
	public String getArtist() {
		return artist;
	}

	/**
	 * @param artist the artist to set
	 */
	public void setArtist(String artist) {
		this.artist = artist;
	}

	/**
	 * @return the gidRecordingMb
	 */
	public String getGidRecordingMb() {
		return gidRecordingMb;
	}

	/**
	 * @param gidRecordingMb the gidRecordingMb to set
	 */
	public void setGidRecordingMb(String gidRecordingMb) {
		this.gidRecordingMb = gidRecordingMb;
	}

	/**
	 * @return the song
	 */
	public String getSong() {
		return song;
	}

	/**
	 * @param song the song to set
	 */
	public void setSong(String song) {
		this.song = song;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the likertScore
	 */
	public int getLikertScore() {
		return likertScore;
	}

	/**
	 * @param likertScore the likertScore to set
	 */
	public void setLikertScore(int likertScore) {
		this.likertScore = likertScore;
	}

	/**
	 * @return the youtubeVideoId
	 */
	public String getYoutubeVideoId() {
		return youtubeVideoId;
	}

	/**
	 * @param youtubeVideoId the youtubeVideoId to set
	 */
	public void setYoutubeVideoId(String youtubeVideoId) {
		this.youtubeVideoId = youtubeVideoId;
	}

	/**
	 * @return the valence
	 */
	public double getValence() {
		return valence;
	}

	/**
	 * @param valence the valence to set
	 */
	public void setValence(double valence) {
		this.valence = valence;
	}

	/**
	 * @return the energy
	 */
	public double getArousal() {
		return arousal;
	}

	/**
	 * @param energy the energy to set
	 */
	public void setArousal(double energy) {
		this.arousal = energy;
	}
	
	public double getPlaylistDistance() {
		return playlistDistance;
	}

	public void setPlaylistDistance(double playlistDistance) {
		this.playlistDistance = playlistDistance;
	}
	
	@Override
	public int compareTo(QbmPlaylistInfo o) {
		//From highest to lowest playlist distance.
		double diff = this.playlistDistance - o.playlistDistance;
		//Reverse order
		if (diff > 0) {			
			return -1;
		} else if (diff < 0) {
			return 1;
		}
		return 0;
	}
}
