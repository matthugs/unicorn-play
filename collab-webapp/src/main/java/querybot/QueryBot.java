package querybot;//subject to change

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.coweb.bots.VanillaBot;
import org.coweb.bots.Proxy; //Used to send messages back to the session - don't need this right now

import java.util.Map;
import java.util.HashMap;
import java.io.*;

public class QueryBot extends VanillaBot {
	private static final Logger logger = LogManager.getLogger();
	
	private Proxy proxy = null;
	Query qre = null;
	
	/*
	 * They say this is necessary for all bots
	 * but besides 'in the interface' not much communication is needed here.
	 */
	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}
	
    @Override
	public void onShutdown() {
    	qre = null;
	}

    @Override
	public void init() {
    	System.out.println("Initialized (Not necessarily properly)!");
	}
	
	/*
	 * The request here is for a song to play.
	 * For purposes of this, a lot of these parameters aren't relevant.
	 */
	public synchronized void onRequest(Map<String, Object> params, String replyToken, String username) {
		//send this data back to the user (so the front end can hopefully use it)
    	this.qre = new MPDQuery();
        Map<String, Object> replyParams = qre.listAll();
        this.proxy.reply(this, replyToken, replyParams);
        //actually surprisingly easy to extend if the front end can use the contents properly.
	}
	
}
