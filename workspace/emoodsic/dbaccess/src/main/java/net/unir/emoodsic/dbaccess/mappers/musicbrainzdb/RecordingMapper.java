/**
 * 
 */
package net.unir.emoodsic.dbaccess.mappers.musicbrainzdb;

import org.apache.ibatis.annotations.Param;

/**
 * @author Álvaro
 *
 */
public interface RecordingMapper {

	String getGidByArtistCreditAndName(@Param("artistCredit") int artistCredit, @Param("name") String name);
}
