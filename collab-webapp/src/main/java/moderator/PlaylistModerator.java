package moderator;

import objects.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import org.cometd.bayeux.Message;
import org.cometd.bayeux.server.ServerSession;
import org.coweb.DefaultSessionModerator;
import org.coweb.SessionModerator;
/**
 * Provides a SessionModerator to communicate with the front-end and create and manage the PlaylistStateManager.
 * <p>
 * Upon creation of the session, the front-end javascript creates this object to handle communication.
 * This object subsequently handles creating the other back-end architecture and forming connections between it.
 * The PlaylistModerator fields sync requests to maintain the playlist on the backend when it is changed by the front.
 * 
 * @author William Baldwin
 * @version 1.0
 *
 */
public class PlaylistModerator extends DefaultSessionModerator {
	private static final Logger logger = LogManager.getLogger();

	public PlaylistStateManager manager;//maybe private? we may need public so MPD can talk to it.
	private CollabInterface collab;
	boolean isReady = false;
	
	public PlaylistModerator(){
	}
	/**
	 * Callback when the session is created. See coweb documentation for more details.
	 * <p>
	 * In our case, this also instantiates the PlaylistStateManager.
	 */
	@Override
	public void onSessionReady() {
		this.collab = this.initCollab("playlist");
		this.isReady = true;
		manager = new PlaylistStateManager(this);
	}
	
	/*@Override
	public Map<String,Object> getLateJoinState(){
		System.out.println("YES THIS WAS CALLED");
		Map<String, Object> replyParams = null;
		replyParams = manager.getMap();
		return replyParams;
	}*/
	
	/**
	 * Called whenever a session is over (i.e. all clients have left). See coweb documentation for more details.
	 */
    @Override
    public void onSessionEnd() {
        this.collab = null;
        this.isReady = false;
    }
    
    /**
     * Called when the operation engine detects a sync event. See coweb documentation for more details.
     * <p>
     * In our case, this forwards edits to the PlaylistStateManager if they are of a valid format.
     * 
     * @param	clientId	string identifier of client
     * @param	data	Map with sync data
     */
    @Override
    synchronized public void onSync(String clientId, Map<String, Object> data) {
    	PlaylistEdit edit = new PlaylistEdit(data);
    	if (edit.isValid()){
			if(edit.getType().equals("insert")){
				manager.addTrack(edit.getValue(), edit.getPosition());
			}
			else if(edit.getType().equals("delete")){
				manager.removeTrack(edit.getPosition());
			} else {
				logger.warn("PlaylistEdit Type not insert or delete");
			}
    	} else {
    		logger.warn("Invalid PlaylistEdit");
    	}
    }

    /**
     * Called by the PlaylistStateManager to inform the Moderator to propagate the change when a song ends.
     * <p>
     * Currently not being used.
     */
	public void removeTop() {
		collab.sendSync("listChange", null, "delete", 0);
	}
	
    /**
     * Called by the PlaylistStateManager to force a song to the top of the list.
     * <p>
     * Currently not being used.
     * 
     * @param	current	The PlaylistItem to be moved to the top of the list.
     */
	public void addTop(PlaylistItem current) {
		collab.sendSync("listChange", current.getMap(), "insert", 0);
	}
}
