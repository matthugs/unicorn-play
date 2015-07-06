package moderator;

import java.util.ArrayList;
import org.bff.javampd.objects.*;

import java.util.HashMap;

public class PlaylistStateManager {
	public ArrayList<PlaylistItem> playlist;
	private PlaylistModerator par;//so it can tell the moderator if there's a change from the MPD end
	private MPDPlaylistRepresentation rep;
	public PlaylistStateManager(PlaylistModerator ins){
		playlist = new ArrayList<PlaylistItem>();
		par = ins;
		rep = new MPDPlaylistRepresentation(this);
	}
	//This should specify if it needs to add to the start or end, but right now it adds to the end.
	public boolean addTrack(PlaylistItem song){
		return playlist.add(song);
	}

	public boolean addTrack(PlaylistItem item, int position) {
		playlist.add(position, item);
		if(playlist.get(position).equals(item)){
			if(position == 0){//start of the list
				//notify the playlistrepresentation in some way
			} else if (position == 1){
				
			}
		}
		return (playlist.get(position).equals(item));
	}

	public boolean removeTrack(PlaylistItem song) {
		return playlist.remove(song);
	}

	public boolean removeTrack(int position) {
		PlaylistItem check = playlist.remove(position);
		if(check != null){
			if(position == 0){
				
			} else if(position == 1){
				
			}
		}
		return check != null;
	}
	
	//If the song is not in the list, it just adds the song. Otherwise, it promotes it the specified number of positions.
	public boolean promoteTrack(PlaylistItem song, int positions){
		return true;
	}

	public boolean pushToTop(PlaylistItem item) {
		return true;
	}

	//for onNewJoin or whatever that method is in the moderator
	public boolean getMap(){ //kinda crappy method name
		//for every item
		//get the Map<String, Object> and package them together
		return true;
	}

	protected boolean pushNextToMPD(){
		return true;
	}

	//remove the song that just finished playing
	protected boolean popTop(){
		return true;
	}

	protected boolean updateTheMPDPlaylist(){
		return true;
	}

	protected boolean updateCurrentlyPlaying(MPDSong song){
		try{
			//FIXME to use a playlistItem and not a song
			//playlist.set(0, song);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	protected PlaylistItem getNextSong() {
		//TODO
		return new PlaylistItem(new HashMap<String, Object>());
	}
}