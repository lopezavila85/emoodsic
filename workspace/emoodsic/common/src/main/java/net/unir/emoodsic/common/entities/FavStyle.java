/**
 * 
 */
package net.unir.emoodsic.common.entities;

import java.io.Serializable;

/**
 * @author Álvaro
 *
 */
public class FavStyle implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9041964886248185134L;

	private int idFavStyle;
	
	private int idUser;
	
	private int idStyle;
	
	public FavStyle() {
		super();
	}

	/**
	 * @return the idFavStyle
	 */
	public int getIdFavStyle() {
		return idFavStyle;
	}

	/**
	 * @param idFavStyle the idFavStyle to set
	 */
	public void setIdFavStyle(int idFavStyle) {
		this.idFavStyle = idFavStyle;
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
	 * @return the idStyle
	 */
	public int getIdStyle() {
		return idStyle;
	}

	/**
	 * @param idStyle the idStyle to set
	 */
	public void setIdStyle(int idStyle) {
		this.idStyle = idStyle;
	}

}
