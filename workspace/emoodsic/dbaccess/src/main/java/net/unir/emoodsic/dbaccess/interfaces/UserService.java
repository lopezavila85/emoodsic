/**
 * 
 */
package net.unir.emoodsic.dbaccess.interfaces;

import java.util.List;

import net.unir.emoodsic.common.entities.BigFiveInventory;
import net.unir.emoodsic.common.entities.FavArtist;
import net.unir.emoodsic.common.entities.FavStyle;
import net.unir.emoodsic.common.entities.MusicPrefDimProb;
import net.unir.emoodsic.common.entities.User;

/**
 * @author Álvaro
 *
 */
public interface UserService {

	public Boolean addUser(final User user);
	
	public Boolean addFavoriteArtists(final List<FavArtist> favArtistList);
	
	public Boolean addFavoriteStyles(final List<FavStyle> favStyleList);
	
	/**
	 * Adds a personaliy test to the database
	 *
	 * @param bft
	 * @return	true if ok, false otherwise
	 */
	public Boolean addPersonalityTest(final BigFiveInventory bft);
	
	/**
	 * Calculates personality traits for not initialized users
	 * 
	 * @return 	a list containing probabilities of pertaining to each dimension
	 */
	public List<MusicPrefDimProb> calculateBfiUsers();
	
	public boolean deleteAuthTokens();
	
	public User getAuthData(final String email);
	
	public String getAuthToken(final int idUser);
	
	public BigFiveInventory getBfi(int idUser);
	
	public User getData(final int idUser);
	
	public int getUserCount();
	
	public List<User> getUserPersonality();
	
	public List<Integer> getUsersWithoutPersonality();
	
	public boolean setAuthToken(final int idUser, final String token);
	
	public Boolean setMusicPreferenceDimension(final User user);
	
	public Boolean setPersonality(final User user);
}
