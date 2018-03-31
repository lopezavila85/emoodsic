/**
 * 
 */
package net.unir.emoodsic.dbaccess.interfaces;

import java.util.List;

import net.unir.emoodsic.common.entities.Artist;
import net.unir.emoodsic.common.entities.QbmPlaylistInfo;

/**
 * @author Álvaro
 *
 */
public interface MusicBrainzService {

	/*
	 * Retrieves the artist data. In case name is a gid, it searches by gid instead of by name.
	 * 
	 * @param name	the artist name or gid
	 * @return		artist data if OK, null otherwise.
	 *
	Artist getArtistData(final String name);
	*/
	
	/**
	 * Sets the Gid of the songs contained in a QbmPlaylistInfo list, if available.
	 * @param qpiList	a QbmPlaylistInfo list containing songs.
	 */
	void setRecordingGid(List<QbmPlaylistInfo> qpiList);
}
