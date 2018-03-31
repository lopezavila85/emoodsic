/**
 * 
 */
package net.unir.emoodsic.common.entities;

import java.io.Serializable;

/**
 * @author Álvaro
 *
 */
public class MusicPrefDimProb implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7466257876018781219L;

	private int idUser;
	
	private int idMusicPrefDimension;
	
	private double probRC;
	
	private double probIR;
	
	private double probUC;
	
	private double probER;
	
	public MusicPrefDimProb() {
		super();
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
	 * @return the probRC
	 */
	public double getProbRC() {
		return probRC;
	}

	/**
	 * @param probRC the probRC to set
	 */
	public void setProbRC(double probRC) {
		this.probRC = probRC;
	}

	/**
	 * @return the probIR
	 */
	public double getProbIR() {
		return probIR;
	}

	/**
	 * @param probIR the probIR to set
	 */
	public void setProbIR(double probIR) {
		this.probIR = probIR;
	}

	/**
	 * @return the probUC
	 */
	public double getProbUC() {
		return probUC;
	}

	/**
	 * @param probUC the probUC to set
	 */
	public void setProbUC(double probUC) {
		this.probUC = probUC;
	}

	/**
	 * @return the probER
	 */
	public double getProbER() {
		return probER;
	}

	/**
	 * @param probER the probER to set
	 */
	public void setProbER(double probER) {
		this.probER = probER;
	}
}
