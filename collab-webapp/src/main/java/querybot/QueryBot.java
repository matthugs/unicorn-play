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
		String reqtype = (String)params.get("filter-by");//what they're narrowing the search down by
		String reqcrit = (String)params.get("filter-value");//the text being searched for
		String rettype = (String)params.get("listing-type");//the return type
		Map<String, Object> replyParams = null;
		/*if(reqtype.equals("Artist")){
			replyParams = qre.searchArtist(reqcrit);
		} else if(reqtype.equals("Album")){
			replyParams = qre.searchAlbum(reqcrit);
		} else if(reqtype.equals("Song")){
			replyParams = qre.searchSong(reqcrit);
		} else if(reqtype.equals("Any")){
			replyParams = qre.searchAny(reqcrit);
		} else{
			replyParams = qre.listAll();
		}*/
		if(rettype != null){
			if(rettype.equals("Artist")){
				replyParams = query.searchforArtists("");
			}
			else if(rettype.equals("Album")){
				replyParams = query.searchArtistAlbums("");
			} else if(rettype.equals("Track")){
				if(reqtype != null && reqcrit != null){
					if(reqtype.equals("Artist")){
						replyParams = query.searchArtist(reqcrit);
					} else if(reqtype.equals("Album")){
						replyParams = query.searchAlbum(reqcrit);
					} else if(reqtype.equals("Song")){
						replyParams = query.searchSong(reqcrit);
					} else if(reqtype.equals("Any")){
						replyParams = query.searchAny(reqcrit);
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
