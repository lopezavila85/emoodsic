/**
 * 
 */
package net.unir.emoodsic.dbaccess.mappers.emoodsic;

import java.util.List;

import net.unir.emoodsic.common.entities.User;

/**
 * @author Álvaro
 *
 */
public interface UserMapper {

	User getAuthenticationInfo(String email);
	
	User getById(int idUser);
	
	List<Integer> getNoPersonalityId();
	
	int getCount();
	
	List<Integer> getIdList();
	
	List<User> getPersonalityList();
	
	/**
	 * Retrieves the personality traits of the users with ids contained in idUserList.
	 * 
	 * @param idUserList
	 * @return
	 */
	List<User> getPersonalityByUserList(List<Integer> idUserList);
	
	int insert(User user);
	
	int updateMusicPref(User user);
	
	int updatePersonality(User user);
}
