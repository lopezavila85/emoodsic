/**
 * 
 */
package net.unir.emoodsic.dbaccess.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import net.unir.emoodsic.common.classes.CommonUtils;
import net.unir.emoodsic.common.classes.EmoodsicException;
import net.unir.emoodsic.common.classes.LogUtils;
import net.unir.emoodsic.common.classes.PersonalityUtils;
import net.unir.emoodsic.common.classes.WekaUtils;
import net.unir.emoodsic.common.entities.MusicPrefDimProb;
import net.unir.emoodsic.common.entities.MusicPrefDimension;
import net.unir.emoodsic.common.entities.Neighbor;
import net.unir.emoodsic.common.entities.User;
import net.unir.emoodsic.dbaccess.classes.ConfigComponent;
import net.unir.emoodsic.dbaccess.classes.EmoodsicDbUtils;
import net.unir.emoodsic.dbaccess.interfaces.MusicPrefDimService;
import net.unir.emoodsic.dbaccess.mappers.emoodsic.MusicPrefDimProbMapper;
import net.unir.emoodsic.dbaccess.mappers.emoodsic.NeighborMapper;
import net.unir.emoodsic.dbaccess.mappers.emoodsic.UserMapper;
import net.unir.emoodsic.dbaccess.services.transactional.MusicPrefDimServiceDbTx;

/**
 * @author Álvaro
 *
 */
@Service("musicPrefDimServiceDb")
public class MusicPrefDimServiceDb implements MusicPrefDimService {

    /**
     * Class logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(MusicPrefDimServiceDb.class);

    @Autowired
    private transient MusicPrefDimProbMapper mpdProbMapper;
    
    @Autowired
    private transient NeighborMapper neighborMapper;
    
    @Autowired
    private transient UserMapper userMapper;
    
    /**
     * Helper class for nested transactional methods.
     */
    @Autowired
    private transient MusicPrefDimServiceDbTx mpdsTx;

    @Autowired
    private transient ConfigComponent configComponent;
    
    public MusicPrefDimServiceDb() {
    	super();
    }

    @Override
    public void classifyUsersInMusicPrefDimensions(boolean bCheckUsers) {
		//Original music preference dimensions. The unique elements which are classified.

    	
    	//TODO false forces the calculation of MusicPrefDimProb
    	//TODO true to work as in normal mode
    	if (bCheckUsers) {
	    	try {  
	    		
	    		List<Integer> userTableIds = this.userMapper.getIdList();
	    		if (userTableIds == null
	    			|| userTableIds.isEmpty()) {
	    			LOG.error("classifyUsersInMusicPrefDimensions: ERROR retrieving IDs from user table");
	    			return;
	    		}
	    		
				List<Integer> musicPrefDimProbIds = this.mpdProbMapper.getIdList();
	    		if (musicPrefDimProbIds != null
	    			&& !musicPrefDimProbIds.isEmpty()) {
	    		
	    			//Check if both lists contain the same elements (any new user).
	    			userTableIds.removeAll(musicPrefDimProbIds);
	    			if (userTableIds.isEmpty()) {
	    				//Identical tables
	    				LOG.warn("classifyUsersInMusicPrefDimensions: Not necessary to calculate");
	    				return;
	    			}    			
	    		}   		
	    		
	    		//TODO Why these lines were here?
	    		//Calculate kNN in each dimension
	    		//int k = this.configComponent.getKdTreeKnn();
	    		//this.setKNNDimension(k, MusicPrefDimension.REFLECTIVE_COMPLEX_ID);
	    		//this.setKNNDimension(k, MusicPrefDimension.INTENSE_REBELLIOUS_ID);
	    		//this.setKNNDimension(k, MusicPrefDimension.UPBEAT_CONVENTIONAL_ID);
	    		//this.setKNNDimension(k, MusicPrefDimension.ENERGETIC_RHYTHMIC_ID);
	    		
	        } catch (DataAccessException dae) {
	            LogUtils.logDataAccessException(LOG, CommonUtils.getMethodName(), dae.getMessage(),
	                null);           
	        } 
    	}		
		
		//Retrieve the list of previously classified elements (ids of users and dimensions).
		List<MusicPrefDimProb> mpdprobl = this.getMPDimProbUserList();	
		if (mpdprobl == null) {
			mpdprobl = new ArrayList<MusicPrefDimProb>();
		}
		
		List<User> userl = null;
		
		try {
			userl = this.userMapper.getPersonalityList();
        } catch (DataAccessException dae) {
            LogUtils.logDataAccessException(LOG, CommonUtils.getMethodName(), dae.getMessage()); 
            userl = null;
        }
		
		if (userl == null) {
			userl = new ArrayList<User>();
		}
		
		List<MusicPrefDimension> train = new ArrayList<MusicPrefDimension>();
		
		List<MusicPrefDimension> evaluate = new ArrayList<MusicPrefDimension>();
		
		//Previously classified users in MusicPrefDimProb table
		if (!userl.isEmpty()) {
			for (MusicPrefDimProb mpdp : mpdprobl) {
				User u = null;
				try {				
					u = userl.stream()
					    .filter(x -> x.getIdUser() == mpdp.getIdUser()).findFirst().get();
					if (u == null) {
						continue;
					}
				} catch (NullPointerException nre) {
					continue;
				} catch (NoSuchElementException nse) {
					continue;
				}		
				//The class MusicPrefDimension is recycled for classification.
				MusicPrefDimension m = new MusicPrefDimension();
				//NOTE: The id of the user will be used to update the probabilities of pertaining to a class
				m.setIdMusicPrefDimension(u.getIdUser());
				//Get its name as it corresponds to the class
				String mName = EmoodsicDbUtils.getMusicPrefDimensionName(mpdp.getIdMusicPrefDimension());
				m.setName(mName);
				
				//And the personality traits of the user
				m.setPersTraitO(u.getPersTraitO());
				m.setPersTraitC(u.getPersTraitC());
				m.setPersTraitE(u.getPersTraitE());
				m.setPersTraitA(u.getPersTraitA());
				m.setPersTraitN(u.getPersTraitN());
				
				train.add(m);		
			}
		}
		
		//New users to evaluate
		int iNumUsers = -1;
		try {
			iNumUsers = this.userMapper.getCount();
        } catch (DataAccessException dae) {
            LogUtils.logDataAccessException(LOG, CommonUtils.getMethodName(), dae.getMessage()); 
            iNumUsers = -1;
        }
		
		List<MusicPrefDimProb> initialProbList = new ArrayList<MusicPrefDimProb>();
		
		for (User u : userl) {
			try {
				if (!mpdprobl.isEmpty()) {
					//There are users with probabilities assigned to each dimension
					MusicPrefDimProb mpd = mpdprobl.stream()
					    .filter(x -> x.getIdUser() == u.getIdUser()).findFirst().get();
					if (mpd != null) {
						//The user is already in train set
						continue;
					}
				}
			} catch (NullPointerException nre) {
				//Element not found
			} catch (NoSuchElementException nse) {
				//Element not found
			}
			
			//Fill an element for the evaluation set
			
			//The class MusicPrefDimension is recycled for classification.
			MusicPrefDimension m = new MusicPrefDimension();
			//NOTE: The id of the user will be used to update the probabilities of pertaining to a class
			m.setIdMusicPrefDimension(u.getIdUser());
			//Not classified but use the one previously calculated MPu = W.PSu
			String mName = EmoodsicDbUtils.getMusicPrefDimensionName(u.getIdMusicPrefDimension());
			m.setName(mName);
			
			//And the personality traits of the user
			m.setPersTraitO(u.getPersTraitO());
			m.setPersTraitC(u.getPersTraitC());
			m.setPersTraitE(u.getPersTraitE());
			m.setPersTraitA(u.getPersTraitA());
			m.setPersTraitN(u.getPersTraitN());
			
			evaluate.add(m);
			
			if (iNumUsers <= this.configComponent.getMlpMinUsers()) {
				//If there are not enough users, the update will be based just in the calculation
				//with W matrix to obtain the Music-Preference dimension correlation (Hu & Pu).
				MusicPrefDimProb mpdp = new MusicPrefDimProb();
				PersonalityUtils.calculateInitialMusicPrefDimProb(u, mpdp);
				initialProbList.add(mpdp);				
			}
		}
		
		//Create the Music Preference Dimension clusters, classify instances and update probabilities
		//List<MusicPrefDimProb> probList = WekaUtils.createMusicPrefDimClusters(train, evaluate);

		List<MusicPrefDimProb> mlpProbList = WekaUtils.createMusicPrefDimMLP(train, evaluate,
			this.configComponent.getMlpLearningRate(), this.configComponent.getMlpMomentum(),
			this.configComponent.getMlpHiddenLayers());		
		
		if (initialProbList.isEmpty()) {
			if(iNumUsers >= this.configComponent.getMlpMinUsers()
				&& mlpProbList != null
				&& !mlpProbList.isEmpty()) {
				//Update the table with new probabilities and assignments for everyone
				this.setMusicPrefDimProbs(mlpProbList);
			}
			//If there are few users, do not update probabilities
		} else {
			//Update the new users as there are not enough to consider the MLP results
			this.setMusicPrefDimProbs(initialProbList);
		}
    }

	@Override
	public List<MusicPrefDimProb> getMPDimProbUserList() {
        try {        	
        	return this.mpdProbMapper.getUserProbabilityList();

        } catch (DataAccessException dae) {
            LogUtils.logDataAccessException(LOG, CommonUtils.getMethodName(), dae.getMessage(),
                null);           
        }
        return null;
	}


    @Override
    public Boolean setKNearestNeighbors() {
    	
    	try {  
    		
    		List<Integer> userTableIds = this.userMapper.getIdList();
    		if (userTableIds == null
    			|| userTableIds.isEmpty()) {
    			LOG.error("setKNearestNeighbors: ERROR retrieving IDs from user table");
    			return false;
    		}
    		
    		List<Integer> neighborTableIds = this.neighborMapper.getIdList();
    		if (neighborTableIds != null
    			&& !neighborTableIds.isEmpty()) {
    		
    			//Check if both lists contain the same elements. In that case it won't be 
    			//necessary to calculate the KNN
    			userTableIds.removeAll(neighborTableIds);
    			if (userTableIds.isEmpty()) {
    				//Identical tables -> KNN not necessary
    				LOG.warn("setKNearestNeighbors: Not necessary to calculate");
    				return true;
    			}    			
    		}

    		//Calculate kNN in each dimension
    		int k = this.configComponent.getKdTreeKnn();
    		this.setKNNDimension(k, MusicPrefDimension.REFLECTIVE_COMPLEX_ID);
    		this.setKNNDimension(k, MusicPrefDimension.INTENSE_REBELLIOUS_ID);
    		this.setKNNDimension(k, MusicPrefDimension.UPBEAT_CONVENTIONAL_ID);
    		this.setKNNDimension(k, MusicPrefDimension.ENERGETIC_RHYTHMIC_ID);
    		
        } catch (DataAccessException dae) {
            LogUtils.logDataAccessException(LOG, CommonUtils.getMethodName(), dae.getMessage(),
                null);           
        }
    	
    	return false;
    }
    
    private void setKNNDimension(final int k, final int idMusicPrefDimension) {
    	
    	try {
        	//Get all users from the current Music Preference Dimension probability table
        	List<Integer> idUserList = this.mpdProbMapper.getUserListByDimension(idMusicPrefDimension);
        	if (idUserList == null
        	    || idUserList.isEmpty()) {
        		LOG.warn(String.format("No users in %s dimension",
        			EmoodsicDbUtils.getMusicPrefDimensionName(idMusicPrefDimension)));
        		return;
        	}        	

        	//Retrieve the personality traits values for each user of the current dimension
        	List<User> userList = this.userMapper.getPersonalityByUserList(idUserList);
        	if (userList == null
        	    || userList.isEmpty()) {
        		LOG.error("searchKNN: ERROR retrieving userList");
        		return;
        	}
        	
        	List<Neighbor> neighborList = WekaUtils.searchKnn(k, userList);
        	if (neighborList == null
        	    || neighborList.isEmpty()) {
        		LOG.error("searchKNN: ERROR retrieving userList");
        		return;
        	} else {
        		this.mpdsTx.mergeNeighbors(this.neighborMapper, neighborList);
        	}
        } catch (DataAccessException dae) {
            LogUtils.logDataAccessException(LOG, CommonUtils.getMethodName(), dae.getMessage(),
                null);           
        } catch (EmoodsicException dex) {
            LogUtils.logEmoodsicException(LOG, dex);            
        }
    }
    
	@Override
	public Boolean setMusicPrefDimProbs(List<MusicPrefDimProb> probList) {

		Boolean bAllOk = true;
		for (MusicPrefDimProb mpdp: probList) {
			try {			
				this.mpdsTx.mergeProb(this.mpdProbMapper, mpdp);
				
	        } catch (EmoodsicException dex) {
	            LogUtils.logEmoodsicException(LOG, dex);
	            bAllOk = false;
	        }	       
		}
		return bAllOk;
	}	
}
