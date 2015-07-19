package querybot;//subject to change

import mpdconnectors.MPDQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.coweb.bots.VanillaBot;
import org.coweb.bots.Proxy; //Used to send messages back to the session - don't need this right now

import java.util.Map;
import java.io.*;

public class QueryBot extends VanillaBot {
	private static final Logger logger = LogManager.getLogger();
	
	private Proxy proxy = null;
	IQuery query = null;
	
	/*
	 * They say this is necessary for all bots
	 * but besides 'in the interface' not much communication is needed here.
	 */
	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}
	
    @Override
	public void onShutdown() {
    	query = null;
	}

    @Override
	public synchronized void init() {
    	System.out.println("Initialized (Not necessarily properly)!");
    	query = new MPDQuery();
	}
	
	/*
	 * The request here is for a song to play.
	 * For purposes of this, a lot of these parameters aren't relevant.
	 */
	public synchronized void onRequest(Map<String, Object> params, String replyToken, String username) {
		//parse the request
		String filterBy = (String)params.get("filter-by");//what they're narrowing the search down by
		String searchTerm = (String)params.get("filter-value");//the text being searched for
		String returnType = (String)params.get("listing-type");//the return type
		Map<String, Object> replyParams = null;

		if(returnType != null){
			if(returnType.equals("Artist")){
				replyParams = query.searchforArtists("");
			}

			else if(returnType.equals("Album")){
				
				if(filterBy.equals("Artist")) {
					replyParams = query.listAllAlbumsByArtist(searchTerm);
				}
				else{
					replyParams = query.listAllAlbumsByArtist("");
				}
			} 
			
			else if(returnType.equals("Track")){
				if(filterBy != null && searchTerm != null){
					if(filterBy.equals("Artist")){
						replyParams = query.searchArtist(searchTerm);
					} else if(filterBy.equals("Album")){
						replyParams = query.searchAlbum(searchTerm);
					} else if(filterBy.equals("Song")){
						replyParams = query.searchSong(searchTerm);
					} else if(filterBy.equals("Any")){
						replyParams = query.searchAny(searchTerm);
					}
				}
			}
		}
		if(replyParams == null){
			replyParams = query.listAll();
		}
		//send this data back to the user (so the front end can hopefully use it)
        this.proxy.reply(this, replyToken, replyParams);
        //actually surprisingly easy to extend if the front end can use the contents properly.
	}
	
}
