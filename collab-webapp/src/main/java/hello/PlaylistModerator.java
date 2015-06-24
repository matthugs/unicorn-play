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
    		//it is possible for it to be valid but have type be null (I need to check WHY, though).
    		if(edit.getType() != null){
    			if(edit.getType().equals("insert")){
    				
    			}
    			else if(edit.getType().equals("delete")){
    				
    			}
    			else if(edit.getType().equals("update")){
    				
    			}
    		}
    	}
    }
}
