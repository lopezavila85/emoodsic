/**
 * 
 */
package net.unir.emoodsic.dbaccess.mappers.emoodsic;

import java.util.List;

import net.unir.emoodsic.common.entities.Neighbor;

/**
 * @author Álvaro
 *
 */
public interface NeighborMapper {

	List<Integer> getIdList();
	
	String getNeighbors(int idUser);
	
	int insert(Neighbor neighbor);
	
	int update(Neighbor neighbor);
}
