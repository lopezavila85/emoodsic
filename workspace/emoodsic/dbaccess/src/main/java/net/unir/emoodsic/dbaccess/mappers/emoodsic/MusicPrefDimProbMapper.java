/**
 * 
 */
package net.unir.emoodsic.dbaccess.mappers.emoodsic;

import java.util.List;

import net.unir.emoodsic.common.entities.MusicPrefDimProb;

/**
 * @author Álvaro
 *
 */
public interface MusicPrefDimProbMapper {

	MusicPrefDimProb getByIdUser(int idUser);
	
	List<Integer> getIdList();
	
	List<Integer> getUserListByDimension(int idMusicPrefDimension);
	
	/**
	 * @return all users and dimensions included in the table.
	 */
	List<MusicPrefDimProb> getUserProbabilityList();
	
	int insert(MusicPrefDimProb mpdp);
	
	int update(MusicPrefDimProb mpdp);
}
