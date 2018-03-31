/**
 * 
 */
package net.unir.emoodsic.common.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Álvaro
 * @note Rentfrow, P. J., Gosling, S. D. (2003). 
 * The do re mi’s of everyday life: the structure and personality correlates of music preferences.
 * Journal of Personality and Social Psychology, 2003, Vol, 84, No. 6, pp 1236-1256.
 */
public class MusicPrefDimension implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 144748954828284659L;

	public static final String REFLECTIVE_COMPLEX = "Reflective-Complex";
	
	public static final int REFLECTIVE_COMPLEX_ID = 1;
	
	public static final String INTENSE_REBELLIOUS = "Intense-Rebellious";
	
	public static final int INTENSE_REBELLIOUS_ID = 2;
	
	public static final String UPBEAT_CONVENTIONAL = "Upbeat-Conventional";
	
	public static final int UPBEAT_CONVENTIONAL_ID = 3;
	
	public static final String ENERGETIC_RHYTHMIC = "Energetic-Rhythmic";
	
	public static final int ENERGETIC_RHYTHMIC_ID = 4;
	
	public static final int NUM_DIM = 4;
	
	private int idMusicPrefDimension;
	
	/**
	 * The name of the current dimension.
	 */
	private String name;
	
	private double persTraitO;
	
	private double persTraitC;
	
	private double persTraitE;
	
	private double persTraitA;
	
	private double persTraitN;
	
	/**
	 * The list of music styles related to the dimension
	 */
	private transient List<MusicPrefDimStyle> musicPrefDimStyles;
	
	public MusicPrefDimension() {
		super();
		
		this.setMusicPrefDimStyles(new ArrayList<MusicPrefDimStyle>());
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
	 * @return the persTraitO
	 */
	public double getPersTraitO() {
		return persTraitO;
	}

	/**
	 * @param persTraitO the persTraitO to set
	 */
	public void setPersTraitO(double persTraitO) {
		this.persTraitO = persTraitO;
	}

	/**
	 * @return the persTraitC
	 */
	public double getPersTraitC() {
		return persTraitC;
	}

	/**
	 * @param persTraitC the persTraitC to set
	 */
	public void setPersTraitC(double persTraitC) {
		this.persTraitC = persTraitC;
	}

	/**
	 * @return the persTraitE
	 */
	public double getPersTraitE() {
		return persTraitE;
	}

	/**
	 * @param persTraitE the persTraitE to set
	 */
	public void setPersTraitE(double persTraitE) {
		this.persTraitE = persTraitE;
	}

	/**
	 * @return the persTraitA
	 */
	public double getPersTraitA() {
		return persTraitA;
	}

	/**
	 * @param persTraitA the persTraitA to set
	 */
	public void setPersTraitA(double persTraitA) {
		this.persTraitA = persTraitA;
	}

	/**
	 * @return the persTraitN
	 */
	public double getPersTraitN() {
		return persTraitN;
	}

	/**
	 * @param persTraitN the persTraitN to set
	 */
	public void setPersTraitN(double persTraitN) {
		this.persTraitN = persTraitN;
	}


	/**
	 * @return the musicPrefDimStyles
	 */
	public List<MusicPrefDimStyle> getMusicPrefDimStyles() {
		return musicPrefDimStyles;
	}


	/**
	 * @param musicPrefDimStyles the musicPrefDimStyles to set
	 */
	public void setMusicPrefDimStyles(List<MusicPrefDimStyle> musicPrefDimStyles) {
		this.musicPrefDimStyles = musicPrefDimStyles;
	}
}
