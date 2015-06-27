package hello;
//import org.bff.javampd.events.*;
import org.bff.javampd.*;
import org.bff.javampd.objects.*;
/*
 * This class replaces Boombox, roughly. 
 */

public final int portNumber = 6001;
public final String password = "password";

public class HookerUpper {
	private MPD mpd = null;
	// private Player player;
	// private Database database;
	// private Admin admin;
	// private Playlist mpdPlaylist;
	//private Timer timer;
	//private ICollabPlaylist playlist;
	//private Query query;

	public HookerUpper(){
		query = new MPDQuery();
		playlist = new CollabPlaylist();

		try {
            mpd = new MPD.Builder()
                        .port(portNumber)
                        .password(password);
                        .build();
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
    }
}