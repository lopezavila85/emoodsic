/**
 * 
 */
package net.unir.emoodsic.common.entities;

import java.io.Serializable;

/**
 * @author Álvaro
 *
 */
public class MusicPrefDimStyle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7290665851024468528L;

	private int idMusicPrefDimStyle;
	
	private int idMusicPrefDimension;
	
	private int idStyle;
	
	public MusicPrefDimStyle() {
		super();
	}

	/**
	 * @return the idMusicPrefDimStyle
	 */
	public int getIdMusicPrefDimStyle() {
		return idMusicPrefDimStyle;
	}

	/**
	 * @param idMusicPrefDimStyle the idMusicPrefDimStyle to set
	 */
	public void setIdMusicPrefDimStyle(int idMusicPrefDimStyle) {
		this.idMusicPrefDimStyle = idMusicPrefDimStyle;
	}

	/**
	 * @return the idMusicPrefDimension
	 */
	public int getIdMusicPrefDimension() {
		return idMusicPrefDimension;
	}

	/**
	 * @param idMusicPrefDimension the idMusicPrefDimension to set
	 */
	public void setIdMusicPrefDimension(int idMusicPrefDimension) {
		this.idMusicPrefDimension = idMusicPrefDimension;
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
