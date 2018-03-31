/**
 * 
 */
package net.unir.emoodsic.dbaccess.mappers.emoodsic;

import java.util.List;

import net.unir.emoodsic.common.entities.FavArtist;

/**
 * @author Álvaro
 *
 */
public interface FavArtistMapper {

	List<FavArtist> getListByIdUser(int idUser);
	
	int insertList(List<FavArtist> favArtists);
}
