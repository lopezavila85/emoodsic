/**
 * 
 */
package net.unir.emoodsic.common.entities;

import java.io.Serializable;

/**
 * @author Álvaro
 *
 */
public class Education implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3836941442526963212L;

	private int idEducation;
	
	private String name;
	
	public Education() {
		super();
	}

	/**
	 * @return the idEducation
	 */
	public int getIdEducation() {
		return idEducation;
	}

	/**
	 * @param idEducation the idEducation to set
	 */
	public void setIdEducation(int idEducation) {
		this.idEducation = idEducation;
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
