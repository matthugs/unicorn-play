package hello;

import java.util.Map;

import org.cometd.bayeux.Message;
import org.cometd.bayeux.server.ServerSession;
import org.coweb.DefaultSessionModerator;
import org.coweb.SessionModerator;

public class PlaylistModerator extends DefaultSessionModerator {
	public PlaylistStateManager manager;//maybe private? we may need public so MPD can talk to it.
	private CollabInterface collab;
	boolean isReady = false;
	
	public PlaylistModerator(){
	}
	
	@Override
	public void onSessionReady() {
		this.collab = this.initCollab("playlist");
		this.isReady = true;
		manager = new PlaylistStateManager();
	}
	
    @Override
    public void onSessionEnd() {
        this.collab = null;
        this.isReady = false;
    }
    @Override
    public void onSync(String clientId, Map<String, Object> data) {
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
}
