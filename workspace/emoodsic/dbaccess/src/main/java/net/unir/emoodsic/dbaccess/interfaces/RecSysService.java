/**
 * 
 */
package net.unir.emoodsic.dbaccess.interfaces;

import java.util.List;

import net.unir.emoodsic.common.entities.QbmPlaylist;
import net.unir.emoodsic.common.entities.QbmPlaylistInfo;

/**
 * @author Álvaro
 *
 */
public interface RecSysService {

	public Boolean addQbmLikertScore(int idQbmPlaylistInfo, int likertScore);
	
	public Boolean addQbmLikertScores(List<QbmPlaylistInfo> qpiList);
	
	public Boolean addQbmWeights(QbmPlaylist qp);
	
	/**
	 * Creates a new Query By Mood Playlist.
	 * 
	 * @param idUser	the id of a user.
	 * @param idInitialMoodCategory		the initial mood category.
	 * @param idFinalMoodCategory		the final mood category.
	 * @return							the created QbmPlaylist if OK, null otherwise.
	 */
	public QbmPlaylist createQbmPlaylist(int idUser, int idInitialMoodCategory, int idFinalMoodCategory);
	
	/**
	 * 
	 * @param idUser
	 * @return		the most recent QbmPlaylist if exists, null otherwise
	 */	
	public QbmPlaylist getMostRecentQbmPlaylist(int idUser);
	
	/**
	 * Gets the songs contained in a QBM playlist.
	 * @param idQbmPlaylist		the ID of the QBM playlist.
	 * @return					a list with the songs of the requested playlist if OK, null otherwise.
	 */
	public List<QbmPlaylistInfo> getQbmPlaylistInfo(int idQbmPlaylist);
	
	/**
	 * 
	 * @param idUser
	 * @return		true if the most recent is complete or there isn't any playlist, 
	 * 				false if the songs for the playlist exist but are not rated,
	 * 				null if no songs have been generated or an error occurred.
	 */
	public Boolean isMostRecentQbmPlaylistComplete(int idUser);
	
	/**
	 * Searches for songs to fill QbmPlaylist
	 * @param qbm	the QbmPlaylist containing information to create the playlist
	 * @return		a QbmPlaylistInfo list containing the recommended songs if OK, null otherwise.
	 */
	public List<QbmPlaylistInfo> createQbmPlaylistInfo(QbmPlaylist qbm);
	
	/**
	 * Main method for querying by mood
	 * 
	 * @param idUser			the user id.
	 * @param idInitialMoodCat	the initial mood category id.
	 * @param idFinalMoodCat	the final mood category id.
	 * @return					a playlist created from 'Query By Mood' if OK, null otherwise.
	 */
	List<QbmPlaylistInfo> queryByMood(int idUser, int idInitialMoodCat, int idFinalMoodCat);
	
	/*
	 * Udates QbmPlaylist info table with Youtube video Ids.
	 * @param playlist	a playlist containing songs whose youtubeVideoId must be updated.
	 *
	public void setYoutubeVideoId(List<QbmPlaylistInfo> playlist);
	*/
}
