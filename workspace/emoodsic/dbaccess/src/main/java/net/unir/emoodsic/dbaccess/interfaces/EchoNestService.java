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
public interface EchoNestService {

	
	public Boolean addQbmSortOrder(QbmPlaylist qp);

	/**
	 * Obtains the songs of a QBM playlist.
	 * @param qp					the QbmPlaylist objetc containing seed data.
	 * @param qpiLastSongs			the last N songs recommended to a certain user.
	 * @return						a list containing the songs (QbmPlaylistInfo).
	 */
	public List<QbmPlaylistInfo> createQbmPlaylist(final QbmPlaylist qp, final List<QbmPlaylistInfo> qpiLastSongs);
}
