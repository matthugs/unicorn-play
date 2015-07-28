package querybot;//subject to change

import mpdconnectors.MPDQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.coweb.bots.VanillaBot;
import org.coweb.bots.Proxy; //Used to send messages back to the session - don't need this right now

import java.util.Map;
import java.io.*;
/**
 * Fields asychronous requests for data by clients for information on available songs.
 * 
 * @author William Baldwin
 * @version 1.0
 *
 */
public class QueryBot extends VanillaBot {
	private static final Logger logger = LogManager.getLogger();
	
	private Proxy proxy = null;
	IQuery query = null;
	
	/**
	 * Sets the proxy object this bot should use to reply to subscribes, sync, requests, etc.
	 * 
	 * @param proxy	the proxy to be used.
	 */
	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}
	
	/**
	 * Called when the bot service is to shutdown. Allows the bot to do any cleanup that it needs to.
	 */
    @Override
	public void onShutdown() {
    	query = null;
	}

    /**
     * The bot Proxy will call this method when a new session has been created and a user has subscribed to this bot's service.
     */
    @Override
	public synchronized void init() {
    	System.out.println("Initialized (Not necessarily properly)!");
    	query = new MPDQuery();
	}
	
    /**
     * Called when a user makes a private request to this bot.
     * <p>
     * In our case, this takes in the form of request and relays to MPDQuery to acquire the song info it will send back to the user.
     * 
     * @param params	key value pairs of parameters sent by the user; gives the type of query.
     * @param replyToken	token associated with this request. The bot must pass this token back to the proxy when replying to this request.
     * @param username	The username of the client making this request. (irrelevant for our purposes)
     */
	public synchronized void onRequest(Map<String, Object> params, String replyToken, String username) {
		//parse the request
		Map<String, Object> parameters = (Map<String, Object>)params.get("query");
		String filterBy = (String)parameters.get("filter-by");//what they're narrowing the search down by
		String searchTerm = (String)parameters.get("filter-value");//the text being searched for
		String returnType = (String)parameters.get("listing-type");//the return type
		Map<String, Object> replyParams = null;

		if(returnType != null){
			if(returnType.equals("Artist")){
				replyParams = query.searchforArtists("");
			}

			else if(returnType.equals("Album")){
				
				if(filterBy != null && filterBy.equals("Artist")) {
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
