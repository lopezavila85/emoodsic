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
import net.unir.emoodsic.common.entities.MusicPrefDimProb;
import net.unir.emoodsic.common.entities.Neighbor;
import net.unir.emoodsic.common.entities.QbmPlaylistInfo;
import net.unir.emoodsic.dbaccess.interfaces.EmoodsicTx;
import net.unir.emoodsic.dbaccess.mappers.emoodsic.MusicPrefDimProbMapper;
import net.unir.emoodsic.dbaccess.mappers.emoodsic.NeighborMapper;

/**
 * Helper class for nested transactional methods in MusicPrefDimServiceDb.
 * 
 * @author Álvaro
 *
 */
@Service("musicPrefDimServiceDbTx")
public class MusicPrefDimServiceDbTx {

	/**
	 * 
	 */
	public MusicPrefDimServiceDbTx() {
		super();
	}
	
	@EmoodsicTx
    public void mergeNeighbors(NeighborMapper neighborMapper, List<Neighbor> neighborList) throws EmoodsicException {
		
		int totalUpdated = 0;
		int mergedRows = 0;

    	try {    		
    		for (Neighbor nn: neighborList) {
                mergedRows = neighborMapper.update(nn);
                if (mergedRows == 0) {
                    mergedRows = neighborMapper.insert(nn);
                }
                if (mergedRows == 1) {
                	totalUpdated += 1;
                } else {
                	//Wrong result
                	break;
                }
    		}
        } catch (DataAccessException dae) {
            throw new EmoodsicException(String.format(CommonDefs.DATA_ACCESS_EX, CommonUtils.getMethodName()), dae);
        }	

        if (totalUpdated != neighborList.size()) {
            throw new EmoodsicException(String.format(CommonDefs.TRANSACTION_RESULT_ERROR, CommonUtils.getMethodName()));
        }
	}
	
	@EmoodsicTx
    public void mergeProb(MusicPrefDimProbMapper mpdProbMapper, MusicPrefDimProb prob) throws EmoodsicException {
        int mergedRows = -1;

        try {
            mergedRows = mpdProbMapper.update(prob);
            if (mergedRows == 0) {
                mergedRows = mpdProbMapper.insert(prob);
            }
        } catch (DataAccessException dae) {
            throw new EmoodsicException(String.format(CommonDefs.DATA_ACCESS_EX_MESSAGE, CommonUtils.getMethodName(), dae.toString()), dae);
        }

        if (mergedRows != 1) {
            throw new EmoodsicException(String.format(CommonDefs.TRANSACTION_RESULT_ERROR, CommonUtils.getMethodName()));
        }
	}
}
