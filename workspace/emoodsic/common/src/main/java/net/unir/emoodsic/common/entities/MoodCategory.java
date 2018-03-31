/**
 * 
 */
package net.unir.emoodsic.common.entities;

import java.io.Serializable;

/**
 * @author Álvaro
 *
 */
public class MoodCategory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3779858076368179539L;

	private int idMoodCategory;
	
	private String name;
	
	private double minValence;
	
	private double maxValence;
	
	private double minArousal;
	
	private double maxArousal;
	
	public MoodCategory() {
		super();
	}

	/**
	 * @return the idMoodCategory
	 */
	public int getIdMoodCategory() {
		return idMoodCategory;
	}

	/**
	 * @param idMoodCategory the idMoodCategory to set
	 */
	public void setIdMoodCategory(int idMoodCategory) {
		this.idMoodCategory = idMoodCategory;
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

	/**
	 * @return the minValence
	 */
	public double getMinValence() {
		return minValence;
	}

	/**
	 * @param minValence the minValence to set
	 */
	public void setMinValence(double minValence) {
		this.minValence = minValence;
	}

	/**
	 * @return the maxValence
	 */
	public double getMaxValence() {
		return maxValence;
	}

	/**
	 * @param maxValence the maxValence to set
	 */
	public void setMaxValence(double maxValence) {
		this.maxValence = maxValence;
	}

	/**
	 * @return the minArousal
	 */
	public double getMinArousal() {
		return minArousal;
	}

	/**
	 * @param minArousal the minArousal to set
	 */
	public void setMinArousal(double minArousal) {
		this.minArousal = minArousal;
	}

	/**
	 * @return the maxArousal
	 */
	public double getMaxArousal() {
		return maxArousal;
	}

	/**
	 * @param maxArousal the maxArousal to set
	 */
	public void setMaxArousal(double maxArousal) {
		this.maxArousal = maxArousal;
	}
	
}
