package hello;

import org.bff.javampd.*;
import org.bff.javampd.objects.*;
import java.util.Timer;
import java.util.TimerTask;

public class MPDPlaylistRepresentation{
	MPDSong current;
	MPDSong next;
	MPD mpd;
	Playlist playlist;
	PlaylistStateManager playlistStateManager;
	Timer timer;

	public MPDPlaylistRepresentation(PlaylistStateManager playlistStateManager) {
		this.playlistStateManager = playlistStateManager;
		this.timer = new Timer();
		//timer.schedule(new ChangePoller(), (current.getLength()*1000 - 4000), 500);
		mpd = MPDWrapper.getMPD();
		try{
			this.playlist = mpd.getPlaylist();
			mpd.getAdmin().updateDatabase();
		}
		catch (Exception e){

		}
		current = null;
		next = null;

		//don't play yet!
	}

	public boolean setCurrent(MPDSong song) {
		boolean ret = true;
		if(current == null) {
			current = song;
			try{
				//FIX THIS TO USE PLAyLIST ITEM
				//playlist.add(song);
				mpd.getPlayer().play();
			}
			catch(Exception e) {
				System.out.println("i really should figure out loggin");
				ret = false;
			}
		}
		else {
			try{
				//if nothing is on the playlist
				if(playlist.getSongList().isEmpty()) {
					playlist.addSong(song);
					mpd.getPlayer().play();
				}
				else{ //something is on the playlist, 
					  //we're swapping out the track that's currently playing
					playlist.swap(song, 0);
				}
			}
			catch(Exception e){
				System.out.println("i really should figure out loggin");
				ret = false;
			}
		}
		return ret;
	}

	public boolean setNext(MPDSong song) {
		next = song;
		try {
			playlist.swap(song, 1);
		}
		catch (Exception e) {
			System.out.println("i really should figure out loggin");
		}
		return true;
	}

	////////////////////////////////////////////////////////////////////////

	class ChangePoller extends TimerTask {
		long lastTime = 0;
		long thisTime = 0;

		public void run() {
			try{
				//System.out.println("inside the try block");
				thisTime = (long) mpd.getPlayer().getElapsedTime();

				if(thisTime < lastTime){
					lastTime = thisTime;
					playlistStateManager.updateCurrentlyPlaying(mpd.getPlayer().getCurrentSong());
					//next = playlistStateManager.getNextSong();
				}
				else {
					//System.out.println("do I have a logical error?? Look:" + thisTime);
				}
			}
			catch (Exception e) {
				//System.out.println("it didn't work");
			}
		}
	}
	
	private void timerUpdate(int milliseconds) {
		timer.cancel();
		//timer.purge();
		timer.schedule(new ChangePoller(), (current.getLength()*1000 - 4000), 500);
	}
}