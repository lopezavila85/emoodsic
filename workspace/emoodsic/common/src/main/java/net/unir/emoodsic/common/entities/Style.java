package net.unir.emoodsic.common.entities;

import java.io.Serializable;

public class Style implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 766282291762533829L;

	private int idStyle;
	
	private String name;
	
	public Style() {
		super();
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
