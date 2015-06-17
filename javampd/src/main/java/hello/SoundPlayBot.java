package hello;//subject to change
import org.bff.javampd.*;
import java.util.List;
import java.util.Scanner;
import org.coweb.bots.VanillaBot;
import org.coweb.bots.Proxy; //Used to send messages back to the session - don't need this right now
import java.io.*;

public class SoundPlayBot extends VanillaBot {
	private Proxy proxy = null;
	BoomBox boomBox = null;
	
	/*
	 * They say this is necessary for all bots
	 * but besides 'in the interface' not much communication is needed here.
	 */
	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}
	
    @Override
	public void onShutdown() {
    	if(boomBox != null){
    		boomBox.stop();
    	}
    	boomBox = null;
	}

    @Override
	public void init() {
    	this.boomBox = new BoomBox(6001); //note: ACTUALLY GET A BOOMBOX FROM SOMEWHERE.
	}//may be good enough for this; pretty much just means 'given a working boombox'.
	
	/*
	 * The request here is for a song to play.
	 * For purposes of this, a lot of these parameters aren't relevant.
	 */
	public synchronized void onRequest(Map<String, Object> params, String replyToken, String username) {
		//get the relevant info from params
		String req = (String)params.get("player");//obviously need to figure out exactly what I'm getting.
		//this is defined by Ting so this is sufficient for now.
        if(req.equals("play") ){
            boomBox.play();
        }
        else if(req.equals("stop")) {
            boomBox.stop();
        }
        else if(req.equals("pause")) {
            boomBox.stop();
        }
        
        //seems like standard issue for an answer. Not gonna touch this (though I could).
        Map<String, Object> reply = new HashMap<String, Object>();
        reply.put("reply", "acknowledged");
        this.proxy.reply(this, replyToken, reply);
	}
	
}