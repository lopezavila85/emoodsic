/**
 * 
 */
package net.unir.emoodsic.common.entities;

import java.io.Serializable;

/**
 * @author Álvaro
 * Represents a Muscbrainz artist.
 *
 */
public class Artist implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8849888296811911545L;

	private int id;
	
	private String gid;
	
	private String name;
	
	public Artist() {
		super();
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

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
}
