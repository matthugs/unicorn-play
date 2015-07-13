package moderator;

import objects.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import org.cometd.bayeux.Message;
import org.cometd.bayeux.server.ServerSession;
import org.coweb.DefaultSessionModerator;
import org.coweb.SessionModerator;

public class PlaylistModerator extends DefaultSessionModerator {
	private static final Logger logger = LogManager.getLogger();

	public PlaylistStateManager manager;//maybe private? we may need public so MPD can talk to it.
	private CollabInterface collab;
	boolean isReady = false;
	
	protected PlaylistModerator(){
	}
	
	@Override
	public void onSessionReady() {
		this.collab = this.initCollab("playlist");
		this.isReady = true;
		manager = new PlaylistStateManager(this);
	}
	
    @Override
    public void onSessionEnd() {
        this.collab = null;
        this.isReady = false;
    }
    @Override
    synchronized public void onSync(String clientId, Map<String, Object> data) {
    	PlaylistEdit edit = new PlaylistEdit(data);
    	if (edit.isValid()){
			if(edit.getType().equals("insert")){
				manager.addTrack(edit.getValue(), edit.getPosition());
			}
			else if(edit.getType().equals("delete")){
				manager.removeTrack(edit.getPosition());
			}
			else if(edit.getType().equals("update")){
				//I don't know if this is actually relevant, same for null
				//It may be relevant for voting (alters the list value in a way that isn't removal)
			}
    	}
    }

	public void removeTop() {
		collab.sendSync("listChange", null, "delete", 0);
	}

	public void addTop(PlaylistItem current) {
		collab.sendSync("listChange", current.getMap(), "insert", 0);
	}
}
