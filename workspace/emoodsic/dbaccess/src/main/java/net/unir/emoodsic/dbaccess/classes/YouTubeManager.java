/**
 * 
 */
package net.unir.emoodsic.dbaccess.classes;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.SearchResultSnippet;

import net.unir.emoodsic.common.entities.QbmPlaylistInfo;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

/**
 * @author Álvaro
 *
 */
@Component("youTubeManager")
public class YouTubeManager {

    /**
     * Class logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(YouTubeManager.class);
	
    private static final long NUMBER_OF_VIDEOS_RETURNED = 3;
    
    /**
     * Define a global instance of the HTTP transport.
     * @see Auth.java in google samples.
     */
    public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    /**
     * Define a global instance of the JSON factory.
     * @see Auth.java in google samples
     */
    public static final JsonFactory JSON_FACTORY = new JacksonFactory();
    
    /**
     * Define a global instance of a Youtube object, which will be used
     * to make YouTube Data API requests.
     */
    private YouTube youtube;
    
	private String apiKey;
	
	public YouTubeManager() {
		super();
		this.initializeApi();		
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
	
	private void initializeApi() {
		
		if (this.youtube == null) {
	        // This object is used to make YouTube Data API requests. The last
	        // argument is required, but since we don't need anything
	        // initialized when the HttpRequest is initialized, we override
	        // the interface and provide a no-op function.
	        youtube = new YouTube.Builder(YouTubeManager.HTTP_TRANSPORT, YouTubeManager.JSON_FACTORY,
	        	new HttpRequestInitializer() {
	            	public void initialize(HttpRequest request) throws IOException {
	            	}
	        }).setApplicationName("Emoodsic").build();
		}
	}
	
	/**
	 * 
	 * @param playlist
	 * @see https://developers.google.com/youtube/v3/docs/search
	 * @see https://developers.google.com/youtube/v3/docs/search/list
	 */
	public void searchVideos(List<QbmPlaylistInfo> playlist) {
		String queryTerm = "";		
		this.initializeApi();

		for (QbmPlaylistInfo song: playlist) {			
			try {
				queryTerm = new StringBuilder(
				    song.getArtist() + " - " + song.getSong()).toString();
				
				// Define the API request for retrieving search results.
	            YouTube.Search.List search = youtube.search().list("id,snippet");
				
				 // Set your developer key from the {{ Google Cloud Console }} for
	            // non-authenticated requests. See:
	            // {{ https://cloud.google.com/console }}
				search.setKey(this.apiKey);
				
				search.setQ(queryTerm);
				
	            // Restrict the search results to only include videos. See:
	            // https://developers.google.com/youtube/v3/docs/search/list#type
	            search.setType("video");
	            
	            // To increase efficiency, only retrieve the fields that the
	            // application uses.       
	          //search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
	            search.setFields("items(id/kind,id/videoId,snippet/title)");
	            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
	            search.setOrder("relevance");

	            // Call the API and print results.
	            SearchListResponse searchResponse = search.execute();
	            List<SearchResult> searchResultList = searchResponse.getItems();
	            if (searchResultList == null
	                || searchResultList.isEmpty()) {
	            	LOG.warn(String.format("No results searching %s", queryTerm));
	            	continue;
	            }
	            
	            this.searchVideoId(searchResultList, queryTerm, song);

			} catch (GoogleJsonResponseException e) {
	            LOG.error(String.format("YouTube service error %d searching [%s]: %s",
	            	e.getDetails().getCode(), queryTerm, e.getDetails().getMessage()));
	        } catch (IOException e) {
	        	LOG.error("YouTube IO error ["  + queryTerm + "]: " + e.getCause() + " : " + e.getMessage());
	        } catch (Throwable t) {
	            t.printStackTrace();
	        }
		} //for		
	}
	
	private void searchVideoId(List<SearchResult> searchResultList, String queryTerm, QbmPlaylistInfo song) {
        
        Iterator<SearchResult> iteratorSearchResults = searchResultList.iterator();	            
        if (!iteratorSearchResults.hasNext()) {
        	LOG.warn(String.format("No results for the query %s", queryTerm));
        	return;
        }
		
		//Iterate the results in order to obtain the most proper video Id.
        String preferredVideoId = null;
        int iPreferredVideoRanking = 0;
        
        while (iteratorSearchResults.hasNext()) {

            SearchResult singleVideo = iteratorSearchResults.next();
            ResourceId rId = singleVideo.getId();
            SearchResultSnippet snippet = singleVideo.getSnippet();
            
            // Confirm that the result represents a video. Otherwise, the
            // item will not contain a video ID.
            if (rId.getKind().equals("youtube#video")) {
            	
            	String title = snippet.getTitle();	                	
            	if (title != null
            	    && !title.isEmpty()) {
            		
            		if (title.contains(song.getArtist())
            		    && title.contains(song.getSong())
            		    && iPreferredVideoRanking < 4) {
            			
            			//The most important result
            			preferredVideoId = rId.getVideoId();
            			iPreferredVideoRanking = 4;
            			break;
            			
            		} else if (title.contains(song.getArtist())
            			&& iPreferredVideoRanking < 2) {
            			
            			//Just contain the artist name
            			preferredVideoId = rId.getVideoId();
            			iPreferredVideoRanking = 2;
            			
                    } else if (title.contains(song.getSong())
                    	&& iPreferredVideoRanking < 1) {
            			
                    	//Just contain the artist name
            			preferredVideoId = rId.getVideoId();
            			iPreferredVideoRanking = 1;
            		}
            	}
            }
        }
        
        if (preferredVideoId != null
            && !preferredVideoId.isEmpty()) {        	
        	song.setYoutubeVideoId(preferredVideoId);
        	
        } else {
        	//Assign the most relevant result instead
        	song.setYoutubeVideoId(searchResultList.get(0).getId().getVideoId());        	
        }
	}
}
