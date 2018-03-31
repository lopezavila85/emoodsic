/**
 * 
 */
package net.unir.emoodsic.dbaccess.mappers.emoodsic;

import java.util.List;

import net.unir.emoodsic.common.entities.FavStyle;

/**
 * @author Álvaro
 *
 */
public interface FavStyleMapper {

	List<FavStyle> getListByIdUser(int idUser);
	
	int insertList(List<FavStyle> favStyles);
}
