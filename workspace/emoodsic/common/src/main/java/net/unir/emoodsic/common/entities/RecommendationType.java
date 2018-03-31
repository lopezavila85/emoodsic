/**
 * 
 */
package net.unir.emoodsic.common.entities;

import java.io.Serializable;

/**
 * @author Álvaro
 *
 */
public class RecommendationType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3551041169130857234L;

	/**
	 * Favorite artist recommendation
	 */
	public static String ARTIST = "artist";
	
	/**
	 * Favorite style recommendation
	 */
	public static String STYLE = "style";
	
	/**
	 * Personality-based recommendation
	 */
	public static String PERSONALITY = "personality";
	
	/**
	 * Collaborative filtering Personality-based recommendation
	 */
	public static String CF_PERSONALITY = "cfPersonality";
	
	/**
	 * Collaborative filtering recommended by a nearest neighbor
	 */
	public static String CF_NEIGHBOR = "cfNeighbor";
	
	
	private int idRecommendationType;
	
	private String name;
	
	public RecommendationType() {
		super();
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
}
