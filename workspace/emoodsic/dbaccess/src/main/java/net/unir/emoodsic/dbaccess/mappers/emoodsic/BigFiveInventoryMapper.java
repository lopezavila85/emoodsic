package net.unir.emoodsic.dbaccess.mappers.emoodsic;

import net.unir.emoodsic.common.entities.BigFiveInventory;

public interface BigFiveInventoryMapper {

	/**
	 * Retrieves the BFI values answered in the test.
	 * @param idUser	the id of the user.
	 * @return			the BigFiveInventory of a certain user.
	 */
	BigFiveInventory getByIdUser(int idUser);
	
	/**
	 * 
	 * @param bfi	the big five inventory to insert.
	 * @return		the number of rows inserted.
	 */
	int insert(BigFiveInventory bfi);
}
