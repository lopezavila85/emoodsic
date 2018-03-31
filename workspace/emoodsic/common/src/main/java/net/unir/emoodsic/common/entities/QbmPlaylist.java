package net.unir.emoodsic.common.entities;

import java.io.Serializable;

public class QbmPlaylist implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -348421832613802141L;

	private int idQbmPlaylist;
	
	private int idUser;
	
	private long tsCreation;
	
	private int idInitialMoodCat;
	
	private int idFinalMoodCat;
	
	private int idSeedMood;
	
	private String gidSeedFavArtist;
	
	/**
	 * FK to Style table
	 */
	private Integer idSeedFavStyle;
	
	private int idSeedMusicPrefDimStyle;
	
	private Double tasteWeight;
	
	private Double personalityWeight;
	
	private int tasteSongsNum;
	
	private int personalitySongsNum;
	
	private int idSortOrder;
	
	public QbmPlaylist() {
		super();
		this.idQbmPlaylist = 0;
		this.idSeedFavStyle = null;
		this.gidSeedFavArtist = null;
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
	 * @return the idUser
	 */
	public int getIdUser() {
		return idUser;
	}

	/**
	 * @param idUser the idUser to set
	 */
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	/**
	 * @return the tsCreation
	 */
	public long getTsCreation() {
		return tsCreation;
	}

	/**
	 * @param tsCreation the tsCreation to set
	 */
	public void setTsCreation(long tsCreation) {
		this.tsCreation = tsCreation;
	}

	/**
	 * @return the idInitialMoodCat
	 */
	public int getIdInitialMoodCat() {
		return idInitialMoodCat;
	}

	/**
	 * @param idInitialMoodCat the idInitialMoodCat to set
	 */
	public void setIdInitialMoodCat(int idInitialMoodCat) {
		this.idInitialMoodCat = idInitialMoodCat;
	}

	/**
	 * @return the idFinalMoodCat
	 */
	public int getIdFinalMoodCat() {
		return idFinalMoodCat;
	}

	/**
	 * @param idFinalMoodCat the idFinalMoodCat to set
	 */
	public void setIdFinalMoodCat(int idFinalMoodCat) {
		this.idFinalMoodCat = idFinalMoodCat;
	}

	/**
	 * @return the idSeedMood
	 */
	public int getIdSeedMood() {
		return idSeedMood;
	}

	/**
	 * @param idSeedMood the idSeedMood to set
	 */
	public void setIdSeedMood(int idSeedMood) {
		this.idSeedMood = idSeedMood;
	}

	/**
	 * @return the gidSeedFavArtist
	 */
	public String getGidSeedFavArtist() {
		return gidSeedFavArtist;
	}

	/**
	 * @param gidSeedFavArtist the gidSeedFavArtist to set
	 */
	public void setGidSeedFavArtist(String gidSeedFavArtist) {
		this.gidSeedFavArtist = gidSeedFavArtist;
	}

	/**
	 * @return the idSeedFavStyle
	 */
	public Integer getIdSeedFavStyle() {
		return idSeedFavStyle;
	}

	/**
	 * @param idSeedFavStyle the idSeedFavStyle to set
	 */
	public void setIdSeedFavStyle(Integer idSeedFavStyle) {
		this.idSeedFavStyle = idSeedFavStyle;
	}

	/**
	 * @return the tasteWeight
	 */
	public Double getTasteWeight() {
		return tasteWeight;
	}

	/**
	 * @param tasteWeight the tasteWeight to set
	 */
	public void setTasteWeight(Double tasteWeight) {
		this.tasteWeight = tasteWeight;
	}

	/**
	 * @return the personalityWeight
	 */
	public Double getPersonalityWeight() {
		return personalityWeight;
	}

	/**
	 * @param personalityWeight the personalityWeight to set
	 */
	public void setPersonalityWeight(Double personalityWeight) {
		this.personalityWeight = personalityWeight;
	}

	/**
	 * @return the idSeedMusicPrefDimStyle
	 */
	public int getIdSeedMusicPrefDimStyle() {
		return idSeedMusicPrefDimStyle;
	}

	/**
	 * @param idSeedMusicPrefDimStyle the idSeedMusicPrefDimStyle to set
	 */
	public void setIdSeedMusicPrefDimStyle(int idSeedMusicPrefDimStyle) {
		this.idSeedMusicPrefDimStyle = idSeedMusicPrefDimStyle;
	}

	/**
	 * @return the tasteSongsNum
	 */
	public int getTasteSongsNum() {
		return tasteSongsNum;
	}

	/**
	 * @param tasteSongsNum the tasteSongsNum to set
	 */
	public void setTasteSongsNum(int tasteSongsNum) {
		this.tasteSongsNum = tasteSongsNum;
	}

	/**
	 * @return the personalitySongsNum
	 */
	public int getPersonalitySongsNum() {
		return personalitySongsNum;
	}

	/**
	 * @param personalitySongsNum the personalitySongsNum to set
	 */
	public void setPersonalitySongsNum(int personalitySongsNum) {
		this.personalitySongsNum = personalitySongsNum;
	}

	/**
	 * @return the idSortOrder
	 */
	public int getIdSortOrder() {
		return idSortOrder;
	}

	/**
	 * @param idSortOrder the idSortOrder to set
	 */
	public void setIdSortOrder(int idSortOrder) {
		this.idSortOrder = idSortOrder;
	}


}
