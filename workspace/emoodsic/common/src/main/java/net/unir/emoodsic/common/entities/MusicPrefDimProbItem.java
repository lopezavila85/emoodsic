/**
 * 
 */
package net.unir.emoodsic.common.entities;

/**
 * @author Álvaro
 *
 */
public class MusicPrefDimProbItem implements Comparable<MusicPrefDimProbItem> {

	private int idMusicPrefDimension;
	
	private double probability;
	
	public MusicPrefDimProbItem() {
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
	 * @return the probability
	 */
	public double getProbability() {
		return probability;
	}

	/**
	 * @param probability the probability to set
	 */
	public void setProbability(double probability) {
		this.probability = probability;
	}

	@Override
	public int compareTo(MusicPrefDimProbItem o) {
		double diff = o.getProbability() - this.getProbability();
		if (diff > 0) {
			return 1;
		} else if (diff == 0) {
			return 0;			
		} 
		return -1;
	}
}
