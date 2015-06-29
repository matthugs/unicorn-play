/*package hello;
//import org.bff.javampd.events.*;
import org.bff.javampd.*;
import org.bff.javampd.objects.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BoomBox implements MPDBox{
	
	private MPD mpd = null;
	private Player player;
	private Database database;
	private Admin admin;
	private Playlist mpdPlaylist;
	private Timer timer;
	private PlaylistStateManager playlist;
	private Query query;

	public BoomBox(int portNumber) {
		
		playlist = new PlaylistStateManager(null);

		try {
            mpd = new MPD.Builder()
                        .port(portNumber)
                        .password("password")
                        .build();


	        player = mpd.getPlayer();
	        database = mpd.getDatabase();
			admin = mpd.getAdmin();
	        mpdPlaylist = mpd.getPlaylist();
	        query = new MPDQuery(database);
	        //Make sure MPD scans the appropriate folders and has all available tracks
	        //in its database
	        admin.updateDatabase();

	        //setup the elapsed time timer
			timer = new Timer();
			timer.schedule(new TickTask(), 0, 1000);


			//This should change. it's just for easy testing now:
	        addAllSongsToPlaylist();
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
        
	}


	class TickTask extends TimerTask {
		long lastTime = 0;
		long thisTime = 0;

		public void run() {
			System.out.println("the run method got called");
			try{
				System.out.println("inside the try block");
				thisTime = player.getElapsedTime();

				if(thisTime != lastTime){
					lastTime = thisTime;
					System.out.println(thisTime+" / "+player.getTotalTime());
				}
				else {
					System.out.println("do I have a logical error?? Look:" + thisTime);
				}
			}
			catch (Exception e) {
				System.out.println("it didn't work");
			}
		}
	}

	// playerChanged(PlayerChangeEvent event) {
	// 	switch(event) {
	// 		case PLAYER_STARTED {
	// 			player.play();
	// 		}

	// 		case PLAYER_PAUSED {

	// 		}

	// 		case PLAYER_UNPAUSED {

	// 		}

	// 		case 

	// 	}
	// }

	public void addAllSongsToPlaylist() throws Exception{
		mpdPlaylist.addSongs((List)database.listAllSongs());
	}

	public void addSongToPlaylist(MPDSong song) throws Exception{
		mpdPlaylist.addSong(song);
	}

	public void pause() throws Exception {
		player.pause();
	}

	public void play() throws Exception{
		player.play();
	}

	public void playSong(MPDSong song) throws Exception{
		mpdPlaylist.addSong(song);
		player.play();
	}

	public void stop() throws Exception{
		player.stop();
	}

	public void next() throws Exception{
		player.playNext();
	}

	public void previous() throws Exception{
		player.playPrev();
	}

	public void playFromHash(String hash) throws Exception{
		MPDSong song = query.getSong(hash);
		mpdPlaylist.clearPlaylist();
		playSong(song);
	}
}*/