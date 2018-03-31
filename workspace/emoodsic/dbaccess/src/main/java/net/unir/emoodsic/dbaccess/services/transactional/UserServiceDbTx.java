/**
 * 
 */
package net.unir.emoodsic.dbaccess.services.transactional;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import net.unir.emoodsic.common.classes.CommonUtils;
import net.unir.emoodsic.common.classes.EmoodsicException;
import net.unir.emoodsic.common.definitions.CommonDefs;
import net.unir.emoodsic.common.entities.AuthToken;
import net.unir.emoodsic.common.entities.BigFiveInventory;
import net.unir.emoodsic.common.entities.FavArtist;
import net.unir.emoodsic.common.entities.FavStyle;
import net.unir.emoodsic.common.entities.User;
import net.unir.emoodsic.dbaccess.interfaces.EmoodsicTx;
import net.unir.emoodsic.dbaccess.mappers.emoodsic.AuthTokenMapper;
import net.unir.emoodsic.dbaccess.mappers.emoodsic.BigFiveInventoryMapper;
import net.unir.emoodsic.dbaccess.mappers.emoodsic.FavArtistMapper;
import net.unir.emoodsic.dbaccess.mappers.emoodsic.FavStyleMapper;
import net.unir.emoodsic.dbaccess.mappers.emoodsic.UserMapper;

/**
 * Helper class for nested transactional methods in UserServiceDb.
 * 
 * @author Álvaro
 *
 */
@Service("userServiceDbTx")
public class UserServiceDbTx {

    /**
     * Class constructor.
     */
    public UserServiceDbTx() {
        super();
    }
    
    /**
    *
    * @param   authenticationMapper    MyBatis mapper for dodfw.authentication_token related queries.
    * @throws  DoDFWException          error while deleting.
    */
    @EmoodsicTx
   public void deleteAuthTokenTable(final AuthTokenMapper authMapper) throws EmoodsicException {
       int rowsDeleted = -1;

       try {
           rowsDeleted = authMapper.cleanAuthTokens();

       } catch (DataAccessException dae) {
           throw new EmoodsicException(String.format(CommonDefs.DATA_ACCESS_EX, CommonUtils.getMethodName()), dae);
       }
       if (rowsDeleted < 0) {
    	   throw new EmoodsicException(String.format(CommonDefs.TRANSACTION_RESULT_ERROR, CommonUtils.getMethodName()));
       }
   }
   
    @EmoodsicTx
    public void insertBfi(final BigFiveInventoryMapper bfiMapper, final BigFiveInventory bfi) throws EmoodsicException {
        int inserted = -1;
    	try {
            inserted = bfiMapper.insert(bfi);

        } catch (DataAccessException dae) {
            throw new EmoodsicException(String.format(CommonDefs.DATA_ACCESS_EX, CommonUtils.getMethodName()), dae);
        }
        if (inserted != 1) {
            throw new EmoodsicException(String.format(CommonDefs.TRANSACTION_RESULT_ERROR, CommonUtils.getMethodName()));
        }
    }
    
    @EmoodsicTx
    public void insertFavArtists(final FavArtistMapper favArtistMapper, final List<FavArtist> favArtistList)
        throws EmoodsicException {

        int rowsAffected = -1;
        try {
            rowsAffected = favArtistMapper.insertList(favArtistList);

        } catch (DataAccessException dae) {
            throw new EmoodsicException(String.format(CommonDefs.DATA_ACCESS_EX, CommonUtils.getMethodName()), dae);
        }

        if (rowsAffected != favArtistList.size()) {
            throw new EmoodsicException(String.format(CommonDefs.TRANSACTION_RESULT_ERROR, CommonUtils.getMethodName()));
        }
    }
    
    @EmoodsicTx
    public void insertFavStyles(final FavStyleMapper favStyleMapper, final List<FavStyle> favStyleList)
        throws EmoodsicException {

        int rowsAffected = -1;
        try {
            rowsAffected = favStyleMapper.insertList(favStyleList);

        } catch (DataAccessException dae) {
            throw new EmoodsicException(String.format(CommonDefs.DATA_ACCESS_EX, CommonUtils.getMethodName()), dae);
        }

        if (rowsAffected != favStyleList.size()) {
            throw new EmoodsicException(String.format(CommonDefs.TRANSACTION_RESULT_ERROR, CommonUtils.getMethodName()));
        }
    }
    
    @EmoodsicTx
    public void insertUser(final UserMapper userMapper, final User user) throws EmoodsicException {
        int inserted = -1;
    	try {
            inserted = userMapper.insert(user);

        } catch (DataAccessException dae) {
            throw new EmoodsicException(String.format(CommonDefs.DATA_ACCESS_EX, CommonUtils.getMethodName()), dae);
        }
        if (inserted != 1) {
            throw new EmoodsicException(String.format(CommonDefs.TRANSACTION_RESULT_ERROR, CommonUtils.getMethodName()));
        }
    }
    
	@EmoodsicTx
    public void mergeAuthToken(AuthTokenMapper authTokenMapper, AuthToken authToken) throws EmoodsicException {
        int mergedRows = -1;

        try {
            mergedRows = authTokenMapper.update(authToken);
            if (mergedRows == 0) {
                mergedRows = authTokenMapper.insert(authToken);
            }
        } catch (DataAccessException dae) {
            throw new EmoodsicException(String.format(CommonDefs.DATA_ACCESS_EX_MESSAGE, CommonUtils.getMethodName(), dae.toString()), dae);
        }

        if (mergedRows != 1) {
            throw new EmoodsicException(String.format(CommonDefs.TRANSACTION_RESULT_ERROR, CommonUtils.getMethodName()));
        }
	}
	
    @EmoodsicTx
    public void updateFiveFactorModel(final UserMapper userMapper, final User user)
            throws EmoodsicException {
        int rowsMatched = -1;

        try {
            rowsMatched = userMapper.updatePersonality(user);
            
        } catch (DataAccessException dae) {
            throw new EmoodsicException(String.format(CommonDefs.DATA_ACCESS_EX, CommonUtils.getMethodName()), dae);
        }

        if (rowsMatched != 1) {
            throw new EmoodsicException(String.format(CommonDefs.TRANSACTION_RESULT_ERROR, CommonUtils.getMethodName()));
        }
    }
    
    @EmoodsicTx
    public void updateMusicPreferenceDimension(final UserMapper userMapper, final User user)
            throws EmoodsicException {
        int rowsMatched = -1;

        try {
            rowsMatched = userMapper.updateMusicPref(user);
            
        } catch (DataAccessException dae) {
            throw new EmoodsicException(String.format(CommonDefs.DATA_ACCESS_EX, CommonUtils.getMethodName()), dae);
        }

        if (rowsMatched != 1) {
            throw new EmoodsicException(String.format(CommonDefs.TRANSACTION_RESULT_ERROR, CommonUtils.getMethodName()));
        }
    }
}
