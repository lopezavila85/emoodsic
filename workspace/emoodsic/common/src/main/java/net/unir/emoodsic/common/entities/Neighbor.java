/**
 * 
 */
package net.unir.emoodsic.common.entities;

import java.io.Serializable;

/**
 * @author Álvaro
 *
 */
public class Neighbor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1509738201245194350L;

	public static String NEIGHBOR_SPLIT = ",";
	
	private int idUser;
	
	/**
	 * List containing the ids of the neighbors, separated with commas.
	 */
	private String idNeighbors;
	
	public Neighbor() {
		super();
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
	 * @return the idNeighbors
	 */
	public String getIdNeighbors() {
		return idNeighbors;
	}

	/**
	 * @param idNeighbors the idNeighbors to set
	 */
	public void setIdNeighbors(String idNeighbors) {
		this.idNeighbors = idNeighbors;
	}
}
