/**
 * 
 */
package net.unir.emoodsic.dbaccess.classes;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.AddressType;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
//import com.google.maps.model.LocationType;

import net.unir.emoodsic.common.entities.QbmPlaylistInfo;

/**
 * @author Álvaro
 *
 */
@Component("googleMapsManager")
public class GoogleMapsManager {

    /**
     * Class logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(GoogleMapsManager.class);
    
    private String apiKey;
    
    private GeoApiContext context;
    
    public GoogleMapsManager() {
    	super();
    	this.initializeContext();
    }   
    
	/**
	 * @return the apiKey
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * @param apiKey the apiKey to set
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
    //String countryName = get("http://maps.googleapis.com/maps/api/geocode/json?latlng={lat},{long}&sensor=false", latitude, longitude).
    //jsonPath().getString("results.address_components.flatten().find { it.types.flatten().contains('country') }?.long_name");

    private void initializeContext() {

    	if (this.context == null
    		&& this.apiKey != null
    		&& !this.apiKey.isEmpty()) {    		
	        
    		this.context = new GeoApiContext()
	        	.setApiKey(apiKey)
		        //.setQueryRateLimit(3)
		        .setConnectTimeout(1, TimeUnit.SECONDS)
		        .setReadTimeout(1, TimeUnit.SECONDS)
		        .setWriteTimeout(1, TimeUnit.SECONDS);
    	}    	
    }
    
    /**
     * 
     * @param playlist
     * @see https://github.com/googlemaps/google-maps-services-java
     * @see http://www.jayway.com/2013/06/09/finding-country-name-from-geolocation/
     */
    public void searchLocation(List<QbmPlaylistInfo> playlist) {
    	
    	this.initializeContext();
    	
    	for (QbmPlaylistInfo song: playlist) {
    		if (song.getLatitude() != 0 && song.getLongitude() != 0) {
    			//Retrieve the country in English language to perform visualizations
    			try {
					GeocodingResult[] results = GeocodingApi.newRequest(this.context)
						.language("en")
						.latlng(new LatLng(song.getLatitude(), song.getLongitude()))
						.resultType(AddressType.COUNTRY)
						.await();
					
					//E.g.  { "long_name" : "Suecia", "short_name" : "SE", "types" : [ "country", "political" ] }
					boolean bSet = false;
					for (int i = 0; i < results.length; i++) {						
						if (results[i] != null) {						
							AddressType[] adtyp = results[i].types;
							for (int j = 0; j < adtyp.length; j++) {
								if (adtyp[j] == AddressType.COUNTRY) {
									song.setCountry(results[i].addressComponents[0].longName);
									bSet = true;
									break;
								}
							}
						}	
						if (bSet) {
							break;
						}
					}
				} catch (Exception e) {
					LOG.error(String.format("Error retrieving country for [%s - %s]: %s",
						song.getArtist(), song.getSong(), e.getMessage()));
				}
    		}
    		//.locationType(LocationType.ROOFTOP)
    	}
    }
}
