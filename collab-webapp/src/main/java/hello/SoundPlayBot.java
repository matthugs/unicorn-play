package hello;//subject to change
import org.bff.javampd.*;
import java.util.List;
import java.util.Scanner;
import org.coweb.bots.VanillaBot;
import org.coweb.bots.Proxy; //Used to send messages back to the session - don't need this right now
import java.util.Map;
import java.util.HashMap;
import java.io.*;

public class SoundPlayBot extends VanillaBot {
	private Proxy proxy = null;
	MPDBox boomBox = null;
	
	/*
	 * They say this is necessary for all bots
	 * but besides 'in the interface' not much communication is needed here.
	 */
	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}
	
    @Override
	public void onShutdown() {
    	try{
	    	if(boomBox != null){
	    		boomBox.stop();
	    	}
    	}
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("Abject failure!");
        }
    	boomBox = null;
	}

    @Override
	public void init() {
    	System.out.println("Initialized (Not necessarily properly)!");
    	this.boomBox = new BoomBox(6001); //note: ACTUALLY GET A BOOMBOX FROM SOMEWHERE.
	}//may be good enough for this; pretty much just means 'given a working boombox'.
	
	/*
	 * The request here is for a song to play.
	 * For purposes of this, a lot of these parameters aren't relevant.
	 */
	public synchronized void onRequest(Map<String, Object> params, String replyToken, String username) {
		//get the relevant info from params
		//String hash = (String)params.get("player");//obviously need to figure out exactly what I'm getting.
		//this is defined by Ting so this is sufficient for now.

		String req = (String)params.get("player");
		try{
	        //boomBox.playFromHash(hash);
	        if(req.equals("play") ){
	            boomBox.play();
	        }
		}
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("Abject failure!");
        }
        //seems like standard issue for an answer. Not gonna touch this (though I could).
        Map<String, Object> reply = new HashMap<String, Object>();
        reply.put("reply", "acknowledged");
        this.proxy.reply(this, replyToken, reply);
	}
	
}