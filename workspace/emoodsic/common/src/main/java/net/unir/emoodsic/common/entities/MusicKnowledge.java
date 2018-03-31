/**
 * 
 */
package net.unir.emoodsic.common.entities;

import java.io.Serializable;

/**
 * @author Álvaro
 *
 */
public class MusicKnowledge implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2544570419595436757L;

	private int idMusicKnowledge;
	
	private String name;
	
	public MusicKnowledge() {
		super();
	}

	/**
	 * @return the idMusicKnowledge
	 */
	public int getIdMusicKnowledge() {
		return idMusicKnowledge;
	}

	/**
	 * @param idMusicKnowledge the idMusicKnowledge to set
	 */
	public void setIdMusicKnowledge(int idMusicKnowledge) {
		this.idMusicKnowledge = idMusicKnowledge;
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
