package mpdconnectors;

import moderator.PlaylistStateManager;
import moderator.IPlaylistUpdate;
import objects.PlaylistItem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bff.javampd.*;
import org.bff.javampd.objects.*;

import java.util.Timer;
import java.util.TimerTask;

public class MPDPlaylistRepresentation{
	private static final Logger logger = LogManager.getLogger();
	
	SongConverter converter = SongConverter.getConverter();

	MPDSong current;
	MPDSong next;
	MPD mpd;
	Playlist playlist;
	IPlaylistUpdate playlistStateManager;
	Timer timer;

	public MPDPlaylistRepresentation(PlaylistStateManager playlistStateManager) {
		this.playlistStateManager = playlistStateManager;
		this.timer = new Timer();
		mpd = MPDWrapper.getMPD();
		try{
			this.playlist = mpd.getPlaylist();
			mpd.getAdmin().updateDatabase();
		}
		catch (Exception e){
			logger.error(e.getMessage());
		}
		current = null;
		next = null;

		//don't play yet!
	}
	
	public boolean setCurrent(PlaylistItem item){
		MPDSong song = SongConverter.getConverter().playlistItemToMPDSong(item);
		return setCurrent(song);
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
				logger.warn("Song not found");
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
				logger.warn("Song not found");
				ret = false;
			}
		}
		return ret;
	}

	public boolean setNext(PlaylistItem item) {
		MPDSong song = SongConverter.getConverter().playlistItemToMPDSong(item);
		return setNext(song);
	}
	
	public boolean setNext(MPDSong song){
		next = song;
		try {
			playlist.swap(next, 1);
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
					setCurrent(next);
					setNext(converter.playlistItemToMPDSong(playlistStateManager.getNext()));

					playlistStateManager.popCurrentlyPlaying();
					if(playlistStateManager.verifyCurrentlyPlaying(converter.mpdSongToPlaylistItem(current)) == false) {
						playlistStateManager.forceCurrentlyPlaying(converter.mpdSongToPlaylistItem(current));
					}
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