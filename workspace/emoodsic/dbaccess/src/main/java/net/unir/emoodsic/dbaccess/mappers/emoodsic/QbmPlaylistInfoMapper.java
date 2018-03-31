/**
 * 
 */
package net.unir.emoodsic.dbaccess.mappers.emoodsic;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.unir.emoodsic.common.entities.QbmPlaylist;
import net.unir.emoodsic.common.entities.QbmPlaylistInfo;

/**
 * @author Álvaro
 *
 */
public interface QbmPlaylistInfoMapper {

	/**
	 * Retrieves the information necessary for the collaborative filtering process.
	 * @param idQbmPlaylist
	 * @param cfRecTypes
	 * @return	a list containing QbmPlaylistInfo which have been rated previously.
	 */
	List<QbmPlaylistInfo> getCfInfo(@Param("idQbmPlaylist")int idQbmPlaylist,
	    @Param("cfRecTypes") List<Integer> cfRecTypes);
	
	List<QbmPlaylistInfo> getPlaylist(int idQbmPlaylist);
	
	List<QbmPlaylistInfo> getSimpleInfo(int idQbmPlaylist);
	
	int insertList(List<QbmPlaylistInfo> qpiList);
	
	int updateLikertScores(QbmPlaylistInfo qpiList);
	
	int updateYoutubeVideoId(QbmPlaylistInfo qpi);	
}
