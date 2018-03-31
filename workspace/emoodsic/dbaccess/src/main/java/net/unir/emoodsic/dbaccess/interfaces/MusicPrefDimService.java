/**
 * 
 */
package net.unir.emoodsic.dbaccess.interfaces;

import java.util.List;

import net.unir.emoodsic.common.entities.MusicPrefDimProb;

/**
 * @author Álvaro
 * 
 * Service to manage music preference dimension tasks.
 */
public interface MusicPrefDimService {

	/**
	 * Using a neural network classify the users in one of the four music preference dimensions.
	 * Calculate, for each user the probability of pertaining to one or another dimension.
	 * If there are enough users, the values in MusicPrefDimProb table will be updated.
	 * 
	 * @param bCheckUsers	checks whether the user exists in both user and musicPrefDimProb tables.
	 */
	void classifyUsersInMusicPrefDimensions(boolean bCheckUsers);
	
	
	List<MusicPrefDimProb> getMPDimProbUserList();

	/**
	 * Locate for each user, her K nearest neighbours for collaborative filtering.
	 * The dimension where she is classified is considered.
	 *
	 * 	
	 * @return	true if ok, false otherwise.
	 */
	Boolean setKNearestNeighbors();

	/**
	 * Merges the probabilities list into MusicPrefDimProbTable
	 * @param probList	the user probabilities list of pertaining to each cluster
	 * @return			true if ok, false otherwise.
	 */
	Boolean setMusicPrefDimProbs(List<MusicPrefDimProb> probList);
}
