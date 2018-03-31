/**
 * 
 */
package net.unir.emoodsic.dbaccess.services.transactional;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import net.unir.emoodsic.common.classes.CommonUtils;
import net.unir.emoodsic.common.classes.EmoodsicException;
import net.unir.emoodsic.common.definitions.CommonDefs;
import net.unir.emoodsic.common.entities.QbmPlaylist;
import net.unir.emoodsic.dbaccess.interfaces.EmoodsicTx;
import net.unir.emoodsic.dbaccess.mappers.emoodsic.QbmPlaylistMapper;

/**
 * @author Álvaro
 *
 */
@Service("echoNestServiceDbTx")
public class EchoNestServiceDbTx {

	/**
	 * 
	 */
	public EchoNestServiceDbTx() {
		super();
	}
	
	@EmoodsicTx
    public void updateQbmSortOrder(QbmPlaylistMapper qpMapper, QbmPlaylist qp) throws EmoodsicException {
        int updated = -1;
    	try {
    		updated = qpMapper.updateIdSortOrder(qp);

        } catch (DataAccessException dae) {
            throw new EmoodsicException(String.format(CommonDefs.DATA_ACCESS_EX, CommonUtils.getMethodName()), dae);
        }
        if (updated != 1) {
            throw new EmoodsicException(String.format(CommonDefs.TRANSACTION_RESULT_ERROR, CommonUtils.getMethodName()));
        }
	}
}
