/**
 * 
 */
package net.unir.emoodsic.dbaccess.mappers.emoodsic;

import net.unir.emoodsic.common.entities.AuthToken;

/**
 * @author Álvaro
 *
 */
public interface AuthTokenMapper {

    /**
     * Deletes the content of the emoodsic.authToken.
     * @return  the number of rows deleted.
     */
    int cleanAuthTokens();
    
    /**
     * @param   idUser  User id to search for an authentication token
     * @return          Stored user´s authentication token or null if not found
     */
	String getAuthTokenValue(int idUser);

    /**
     * @param  authenticationToken  Authentication token containing user´s id and token string
     * @return                      Number of rows inserted
     */
    int insert(AuthToken authenticationToken);

    /**
     * @param  authenticationToken  Authentication token containing user´s id and token string
     * @return                      Number of rows updated
     */
    int update(AuthToken authenticationToken);
}
