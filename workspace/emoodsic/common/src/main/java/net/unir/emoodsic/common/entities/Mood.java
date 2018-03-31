/**
 * 
 */
package net.unir.emoodsic.common.entities;

import java.io.Serializable;

/**
 * @author Álvaro
 *
 */
public class Mood implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2096936935226026459L;

	private int idMood;
	
	private int idMoodCategory;
	
	private String name;
	
	public Mood() {
		super();
	}

	/**
	 * @return the idMood
	 */
	public int getIdMood() {
		return idMood;
	}

	/**
	 * @param idMood the idMood to set
	 */
	public void setIdMood(int idMood) {
		this.idMood = idMood;
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
}
