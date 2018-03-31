/**
 * 
 */
package net.unir.emoodsic.dbaccess.mappers.emoodsic;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.unir.emoodsic.common.entities.QbmPlaylist;

/**
 * @author Álvaro
 *
 */
public interface QbmPlaylistMapper {

	/**
	 * Retrieves the information necessary for the collaborative filtering process.
	 * @param idUser
	 * @return	a list containing information of QbmPlaylist which have been rated previously.
	 */
	List<QbmPlaylist> getCfInfo(@Param("idUser") int idUser, @Param("minDatePlaylists") long minDatePlaylists);
	
	/**
	 * Retrieves the weights for favorite and personality recommendations.
	 *
	 * @param minDateWeightCalculation	the minimum date to search for Qbm playlists.
	 * @return							a QbmPlaylist containing the average weights of previous recommendation types.
	 */
	List<Double> getTasteWeights(@Param("idUser") int idUser, @Param("minDateWeightCalTime") long minDateWeightCalTime);
	
	/**
	 * Searches for the most recent QBM playlist.
	 * 
	 * @param idUser	the id of a user.
	 * @return the most recent playlist.
	 */
	QbmPlaylist getMostRecent(int idUser);
	
	/**
	 * Searches for the IDs of the most recent playlists
	 *
	 * @param idUser
	 * @param minDateWeightCalTime
	 * @return
	 */
	List<Integer> getMostRecentIds(@Param("idUser") int idUser, @Param("minDateWeightCalTime") long minDateWeightCalTime);
	
	/**
	 * Inserts a new QbmPlaylist.
	 * @param qbm	the QbmPlaylist to be inserted.
	 * @return		the number of rows inserted (1 if OK, 0 otherwise).
	 */
	int insert(QbmPlaylist qbm);
	
	/**
	 *
	 * @param qbm
	 * @return
	 */
	int updateIdSortOrder(QbmPlaylist qbm);
	
	/**
	 * 
	 * @param qbm
	 * @return
	 */
	int updateWeights(QbmPlaylist qbm);
}
