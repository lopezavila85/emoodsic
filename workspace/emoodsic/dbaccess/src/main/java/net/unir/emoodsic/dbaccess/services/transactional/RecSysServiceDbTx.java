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
import net.unir.emoodsic.common.entities.QbmPlaylist;
import net.unir.emoodsic.common.entities.QbmPlaylistInfo;
import net.unir.emoodsic.dbaccess.interfaces.EmoodsicTx;
import net.unir.emoodsic.dbaccess.mappers.emoodsic.QbmPlaylistInfoMapper;
import net.unir.emoodsic.dbaccess.mappers.emoodsic.QbmPlaylistMapper;

/**
 *  Helper class for nested transactional methods in RecSysServiceDb.
 *  
 * @author Álvaro
 *
 */
@Service("recSysServiceDbTx")
public class RecSysServiceDbTx {

	/**
	 * 
	 */
	public RecSysServiceDbTx() {
		super();
	}
		
	@EmoodsicTx
    public void insertQbmPlaylist(QbmPlaylistMapper qplMapper, QbmPlaylist qbm) throws EmoodsicException {
        int inserted = -1;
    	try {
            inserted = qplMapper.insert(qbm);

        } catch (DataAccessException dae) {
            throw new EmoodsicException(String.format(CommonDefs.DATA_ACCESS_EX, CommonUtils.getMethodName()), dae);
        }
        if (inserted != 1) {
            throw new EmoodsicException(String.format(CommonDefs.TRANSACTION_RESULT_ERROR, CommonUtils.getMethodName()));
        }
	}
	
	@EmoodsicTx
	public void insertPlaylist (QbmPlaylistInfoMapper qpiMapper, List<QbmPlaylistInfo> qpiList) throws EmoodsicException {
        int rowsAffected = -1;
        try {
            rowsAffected = qpiMapper.insertList(qpiList);

        } catch (DataAccessException dae) {
        	throw new EmoodsicException(String.format(CommonDefs.DATA_ACCESS_EX, CommonUtils.getMethodName()), dae);
        }

        if (rowsAffected != qpiList.size()) {
        	throw new EmoodsicException(String.format(CommonDefs.TRANSACTION_RESULT_ERROR, CommonUtils.getMethodName()));
        }
	}
	
	@EmoodsicTx
    public void updateQbmLikertScore(QbmPlaylistInfoMapper qpiMapper, QbmPlaylistInfo qpi) throws EmoodsicException {
		
        int updated = -1;
    	try {
    		updated = qpiMapper.updateLikertScores(qpi);

        } catch (DataAccessException dae) {
            throw new EmoodsicException(String.format(CommonDefs.DATA_ACCESS_EX, CommonUtils.getMethodName()), dae);
        }
        if (updated != 1) {
            throw new EmoodsicException(String.format(CommonDefs.TRANSACTION_RESULT_ERROR, CommonUtils.getMethodName()));
        }
	}
	
	@EmoodsicTx
    public void updateQbmLikertScores(QbmPlaylistInfoMapper qpiMapper, List<QbmPlaylistInfo> qpiList) throws EmoodsicException {
		
		int totalUpdated = 0;
		int updated = 0;

    	try {    		
    		for (QbmPlaylistInfo qpi: qpiList) {
    			updated = qpiMapper.updateLikertScores(qpi);
    			totalUpdated += updated;
    		}
        } catch (DataAccessException dae) {
            throw new EmoodsicException(String.format(CommonDefs.DATA_ACCESS_EX, CommonUtils.getMethodName()), dae);
        }	

        if (totalUpdated != qpiList.size()) {
            throw new EmoodsicException(String.format(CommonDefs.TRANSACTION_RESULT_ERROR, CommonUtils.getMethodName()));
        }
	}

	@EmoodsicTx
    public void updateQbmWeights(QbmPlaylistMapper qpMapper, QbmPlaylist qp) throws EmoodsicException {
        int updated = -1;
    	try {
    		updated = qpMapper.updateWeights(qp);

        } catch (DataAccessException dae) {
            throw new EmoodsicException(String.format(CommonDefs.DATA_ACCESS_EX, CommonUtils.getMethodName()), dae);
        }
        if (updated != 1) {
            throw new EmoodsicException(String.format(CommonDefs.TRANSACTION_RESULT_ERROR, CommonUtils.getMethodName()));
        }
	}

	@EmoodsicTx
	public void updateYoutubeVideoId(QbmPlaylistInfoMapper qbmPlInfoMapper, QbmPlaylistInfo qpi) throws EmoodsicException {
        int rowsMatched = -1;

        try {
            rowsMatched = qbmPlInfoMapper.updateYoutubeVideoId(qpi);
            
        } catch (DataAccessException dae) {
            throw new EmoodsicException(String.format(CommonDefs.DATA_ACCESS_EX, CommonUtils.getMethodName()), dae);
        }

        if (rowsMatched != 1) {
            throw new EmoodsicException(String.format(CommonDefs.TRANSACTION_RESULT_ERROR, CommonUtils.getMethodName()));
        }		
	}
}
