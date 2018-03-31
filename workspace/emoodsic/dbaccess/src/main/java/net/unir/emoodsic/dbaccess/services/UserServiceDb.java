/**
 * 
 */
package net.unir.emoodsic.dbaccess.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import net.unir.emoodsic.common.classes.CommonUtils;
import net.unir.emoodsic.common.classes.EmoodsicException;
import net.unir.emoodsic.common.entities.AuthToken;
import net.unir.emoodsic.common.entities.BigFiveInventory;
import net.unir.emoodsic.common.entities.FavArtist;
import net.unir.emoodsic.common.entities.FavStyle;
import net.unir.emoodsic.common.entities.MusicPrefDimProb;
import net.unir.emoodsic.common.entities.MusicPrefDimension;
import net.unir.emoodsic.common.entities.User;
import net.unir.emoodsic.common.classes.LogUtils;
import net.unir.emoodsic.common.classes.PersonalityUtils;
import net.unir.emoodsic.dbaccess.classes.ConfigComponent;
import net.unir.emoodsic.dbaccess.classes.EmoodsicDbUtils;
import net.unir.emoodsic.dbaccess.interfaces.UserService;
import net.unir.emoodsic.dbaccess.mappers.emoodsic.AuthTokenMapper;
import net.unir.emoodsic.dbaccess.mappers.emoodsic.BigFiveInventoryMapper;
import net.unir.emoodsic.dbaccess.mappers.emoodsic.FavArtistMapper;
import net.unir.emoodsic.dbaccess.mappers.emoodsic.FavStyleMapper;
import net.unir.emoodsic.dbaccess.mappers.emoodsic.UserMapper;
import net.unir.emoodsic.dbaccess.services.transactional.UserServiceDbTx;

/**
 * @author Álvaro
 *
 */
@Service("userServiceDb")
public class UserServiceDb implements UserService {

    /**
     * Class logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceDb.class);

    @Autowired
    private transient AuthTokenMapper authTokenMapper;
    
    @Autowired
    private transient BigFiveInventoryMapper bfiMapper;

    @Autowired
    private transient FavArtistMapper favArtistMapper;
    
    @Autowired
    private transient FavStyleMapper favStyleMapper;
    
    @Autowired
    private transient UserMapper userMapper;
    
    /**
     * Helper class for nested transactional methods.
     */
    @Autowired
    private transient UserServiceDbTx userServiceDbTx;
    
    @Autowired
    private transient ConfigComponent configComponent;
    
	public UserServiceDb() {
		super();
	}
	
	@Override
	public Boolean addPersonalityTest(BigFiveInventory bfi) {

		try {			
			this.userServiceDbTx.insertBfi(this.bfiMapper, bfi);
			return true;

        } catch (EmoodsicException dex) {
            LogUtils.logEmoodsicException(LOG, dex);
        }
        return false;
	}
	
	@Override
	public Boolean addUser(User user) {
		try {
			this.userServiceDbTx.insertUser(this.userMapper, user);
			return true;

        } catch (EmoodsicException dex) {
            LogUtils.logEmoodsicException(LOG, dex);
        }
        return false;	
	}

	@Override
	public Boolean addFavoriteArtists(final List<FavArtist> favArtistList) {
		try {			
			if (favArtistList != null
			    && !favArtistList.isEmpty()) {
				this.userServiceDbTx.insertFavArtists(this.favArtistMapper, favArtistList);
				return true;
			} else {
				LogUtils.logDataAccessException(LOG, CommonUtils.getMethodName(), "favArtistList null or empty !!!");
			}
        } catch (EmoodsicException dex) {
            LogUtils.logEmoodsicException(LOG, dex);
        }
        return false;
	}
	
	@Override
	public Boolean addFavoriteStyles(final List<FavStyle> favStyleList) {
		try {			
			if (favStyleList != null
			    && !favStyleList.isEmpty()) {
				this.userServiceDbTx.insertFavStyles(this.favStyleMapper, favStyleList);
				return true;
			} else {
				LogUtils.logDataAccessException(LOG, CommonUtils.getMethodName(), "favStyleList null or empty !!!");
			}
        } catch (EmoodsicException dex) {
            LogUtils.logEmoodsicException(LOG, dex);
        }
        return false;	
	}
	
	@Override
	public List<MusicPrefDimProb> calculateBfiUsers() {
		List<Integer> uNoBfiList = this.getUsersWithoutPersonality();
		List<MusicPrefDimProb> probList = new ArrayList<MusicPrefDimProb>();		
		int iNumUsers = this.getUserCount();
		
		if (uNoBfiList == null
		    || uNoBfiList.isEmpty()) {
			LOG.warn("*** No users without personality calculated.");
			return null;
		}
		
		List<MusicPrefDimension> mpdl = EmoodsicDbUtils.getMusicPrefDimensionList();
		
		for (int idUser : uNoBfiList) {
			//Calculate personality and music preference dimension values for each user.
			User u = new User();
			u.setIdUser(idUser);
			
			final BigFiveInventory bfi = this.getBfi(idUser);
			if (bfi == null) {
				LOG.warn(String.format("*** No BFI for user %d.", idUser));
				continue;
			}
			
			//Calculate Big Five and Music Preference Dimension correlation			
			PersonalityUtils.calculatePersonality(u, bfi);
			PersonalityUtils.calculateMusicPrefDimension(u, mpdl);
			
			//Update the database
			this.setPersonality(u);
			this.setMusicPreferenceDimension(u);
			
			if (iNumUsers <= this.configComponent.getMlpMinUsers()) {
				//If there aren't enough users to evaluate the MLP, calculate initial probabilities
				MusicPrefDimProb mpdp = new MusicPrefDimProb();
				PersonalityUtils.calculateInitialMusicPrefDimProb(u, mpdp);
				probList.add(mpdp);
			}
			//In other case, the user will be classified via the MLP.
		}	
		
		return probList;
	}
	
	@Override
	public boolean deleteAuthTokens() {
		try {			
			this.userServiceDbTx.deleteAuthTokenTable(this.authTokenMapper);
			return true;
			
        } catch (EmoodsicException dex) {
            LogUtils.logEmoodsicException(LOG, dex);
        }
        return false;	
	}
	
	@Override
	public User getAuthData(final String email) {
        try {        	
        	return this.userMapper.getAuthenticationInfo(email);

        } catch (DataAccessException dae) {
            LogUtils.logDataAccessException(LOG, CommonUtils.getMethodName(), dae.getMessage(),
                String.format(" email %s", email));           
        }
        return null;
	}
	
	@Override
	public String getAuthToken(final int idUser) {
        try {        	
        	return this.authTokenMapper.getAuthTokenValue(idUser);

        } catch (DataAccessException dae) {
            LogUtils.logDataAccessException(LOG, CommonUtils.getMethodName(), dae.getMessage(),
                String.format(" idUser %d", idUser));           
        }
        return null;
	}
	
	@Override
	public BigFiveInventory getBfi(int idUser) {
        try {        	
        	return this.bfiMapper.getByIdUser(idUser);

        } catch (DataAccessException dae) {
            LogUtils.logDataAccessException(LOG, CommonUtils.getMethodName(), dae.getMessage(),
                String.format(" idUser %d", idUser));           
        }
        return null;
	}

	@Override
	public User getData(int idUser) {
       
		User u = null;
        try {        	
        	u = this.userMapper.getById(idUser);
        	if (u != null) {
        		//Fill with favourite artists and styles
        		List<FavArtist> fa = this.favArtistMapper.getListByIdUser(idUser);
        		if (fa != null
        			&& !fa.isEmpty()) {
        			u.setFavArtists(fa);
        		}
        		
        		List<FavStyle> fs = this.favStyleMapper.getListByIdUser(idUser);
        		if (fs != null
        			&& !fs.isEmpty()) {
        			u.setFavStyles(fs);
        		}
        	}
        	return u;
        	
        } catch (DataAccessException dae) {
            LogUtils.logDataAccessException(LOG, CommonUtils.getMethodName(), dae.getMessage(),
                String.format(" idUser %d", idUser));           
        }
        return null;
	}
	
	@Override
	public int getUserCount() {
		
        try {        	
        	return this.userMapper.getCount();

        } catch (DataAccessException dae) {
            LogUtils.logDataAccessException(LOG, CommonUtils.getMethodName(), dae.getMessage());           
        }
        return -1;
	}
	
	@Override
	public List<User> getUserPersonality() {
        try {        	
        	return this.userMapper.getPersonalityList();

        } catch (DataAccessException dae) {
            LogUtils.logDataAccessException(LOG, CommonUtils.getMethodName(), dae.getMessage());           
        }
        return null;
	}

	@Override
	public List<Integer> getUsersWithoutPersonality() {
        try {        	
        	return this.userMapper.getNoPersonalityId();

        } catch (DataAccessException dae) {
            LogUtils.logDataAccessException(LOG, CommonUtils.getMethodName(), dae.getMessage());           
        }
        return null;
	}

	@Override
	public boolean setAuthToken(final int idUser, final String token) {
		try {			
			final AuthToken authToken = new AuthToken();
			authToken.setIdUser(idUser);
			authToken.setAuthTokenValue(token);
			this.userServiceDbTx.mergeAuthToken(this.authTokenMapper, authToken);
			return true;
			
        } catch (EmoodsicException dex) {
            LogUtils.logEmoodsicException(LOG, dex);
        }
        return false;	
	}
	
	@Override
	public Boolean setMusicPreferenceDimension(final User user) {
		try {			
			this.userServiceDbTx.updateMusicPreferenceDimension(this.userMapper, user);
			return true;
			
        } catch (EmoodsicException dex) {
            LogUtils.logEmoodsicException(LOG, dex);
        }
        return false;
	}	
	
	@Override
	public Boolean setPersonality(final User user) {
		try {			
			this.userServiceDbTx.updateFiveFactorModel(this.userMapper, user);
			return true;

        } catch (EmoodsicException dex) {
            LogUtils.logEmoodsicException(LOG, dex);
        }
        return false;
	}

}
