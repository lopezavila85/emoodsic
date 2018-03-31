/**
 * 
 */
package net.unir.emoodsic.common.entities;

import java.io.Serializable;

/**
 * @author Álvaro
 *
 */
public class SortOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2632068049078694712L;

	private int idSortOrder;
	
	private String name;
	
	public SortOrder() {
		super();
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
