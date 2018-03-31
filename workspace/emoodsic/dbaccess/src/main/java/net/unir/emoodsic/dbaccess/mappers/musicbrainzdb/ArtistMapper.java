/**
 * 
 */
package net.unir.emoodsic.dbaccess.mappers.musicbrainzdb;

/**
 * @author Álvaro
 *
 */
public interface ArtistMapper {

	//Artist getByGid(String gid);	
	//Artist getById(int id);	
	//Artist getByName(String name);
	
	Integer getIdByGid(String gid);	
}
