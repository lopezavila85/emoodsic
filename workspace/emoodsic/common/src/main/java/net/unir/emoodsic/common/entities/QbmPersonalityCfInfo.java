/**
 * 
 */
package net.unir.emoodsic.common.entities;

/**
 * @author Álvaro
 *
 */
public class QbmPersonalityCfInfo {

	public static String RELEVANT_CF = "yes";
	
	public static String NON_RELEVANT_CF = "no";
	
	public static int MIN_RELEVANT_SCORE = 4;
	
	private int idQbmPlaylistInfo;
	
	private String artist;
	
	//private int artistCredit;
	
	/**
	 * The song name received by The Echo Nest
	 */
	private String song;
	
	private String finalMoodCat;
	
	private int likertScore;
	
	private double valence;
	
	private double arousal;
	
	private String style;
	
	public QbmPersonalityCfInfo() {
		super();
	}

	/*
	 * @return the artist
	 *
	public String getArtist() {
		return artist;
	}

	
	 * @param artist the artist to set
	 *
	public void setArtist(String artist) {
		this.artist = artist;
	}
	*/

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
	 * @return the arousal
	 */
	public double getArousal() {
		return arousal;
	}

	/**
	 * @param arousal the arousal to set
	 */
	public void setArousal(double arousal) {
		this.arousal = arousal;
	}

	/**
	 * @return the style
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * @param style the style to set
	 */
	public void setStyle(String style) {
		this.style = style;
	}
}
