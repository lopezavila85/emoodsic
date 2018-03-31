/**
 * 
 */
package net.unir.emoodsic.dbaccess.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import net.unir.emoodsic.common.classes.CommonUtils;
import net.unir.emoodsic.common.classes.LogUtils;
import net.unir.emoodsic.common.entities.QbmPlaylistInfo;
import net.unir.emoodsic.dbaccess.interfaces.MusicBrainzService;
import net.unir.emoodsic.dbaccess.mappers.musicbrainzdb.ArtistMapper;
import net.unir.emoodsic.dbaccess.mappers.musicbrainzdb.RecordingMapper;

/**
 * @author Álvaro
 *
 */
@Service("musicBrainzServiceDb")
public class MusicBrainzServiceDb implements MusicBrainzService {

    /**
     * Class logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(MusicBrainzServiceDb.class);
	
    @Autowired
    private transient ArtistMapper artistMapper;
    
    @Autowired
    private transient RecordingMapper recordingMapper;

    public MusicBrainzServiceDb() {
    	super();
    }
    
	@Override
	public void setRecordingGid(List<QbmPlaylistInfo> qpiList) {
		
		for (QbmPlaylistInfo qpi : qpiList) {
	        try {
	        	
	        	Integer artistCredit = this.artistMapper.getIdByGid(qpi.getGidArtistMb());
	        	if (artistCredit != null) {	        		
	        		String sGidRecording =
	        			this.recordingMapper.getGidByArtistCreditAndName(artistCredit, qpi.getSong());
	        		if (sGidRecording != null
	        			&& !sGidRecording.isEmpty()) {
	        			qpi.setGidRecordingMb(sGidRecording);
	        		} else {
		        		LOG.warn(String.format("setRecordingGid[2]: NO Recording gid for: %s - %s",
		        		    qpi.getArtist(), qpi.getSong()));
	        		}
	        	} else {
	        		LOG.warn(String.format("setRecordingGid[1]: NO Recording gid for: %s - %s",
	        		    qpi.getArtist(), qpi.getSong()));
	        	}
	        } catch (DataAccessException dae) {
	            LogUtils.logDataAccessException(LOG, CommonUtils.getMethodName(), dae.getMessage(),
	                String.format(" artist %s song %s", qpi.getArtist(), qpi.getSong()));           
	        }
		}		
	}
	
    /*
	@Override
	public Artist getArtistData(String name) {
        try {

        	if (name.length() == DbAccessDefs.MUSICBRAINZ_GID_LENGTH
        	    && name.contains((CharSequence)("-"))) {
        		
        		//Ej: 302bd7b9-d012-4360-897a-93b00c855680
        		return this.artistMapper.getByGid(name);
        	}        	
        	return this.artistMapper.getByName(name);

        } catch (DataAccessException dae) {
            LogUtils.logDataAccessException(LOG, CommonUtils.getMethodName(), dae.getMessage(),
                String.format(" name %s", name));           
        }
        return null;
	}
	*/
}
