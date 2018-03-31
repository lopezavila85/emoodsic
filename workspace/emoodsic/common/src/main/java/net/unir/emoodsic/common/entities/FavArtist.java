/**
 * 
 */
package net.unir.emoodsic.common.entities;

import java.io.Serializable;

/**
 * @author Álvaro
 *
 */
public class FavArtist implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8491344400373609770L;

	private int idFavArtist;
	
	private int idUser;
	
	private String gid;
	
	/**
	 * The name of the artist, in order to create a playlist faster.
	 */
	private String name;
	
	public FavArtist() {
		super();
	}

	/**
	 * @return the idFavArtist
	 */
	public int getIdFavArtist() {
		return idFavArtist;
	}

	/**
	 * @param idFavArtist the idFavArtist to set
	 */
	public void setIdFavArtist(int idFavArtist) {
		this.idFavArtist = idFavArtist;
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
	 * @return the gid
	 */
	public String getGid() {
		return gid;
	}

	/**
	 * @param gid the gid to set
	 */
	public void setGid(String gid) {
		this.gid = gid;
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
