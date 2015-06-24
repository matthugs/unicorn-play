package hello;

import java.util.ArrayList;

public class PlaylistStateManager {

	public Arraylist<PlaylistItem> playlist;

	public PlaylistStateManager(){
		playlist = new Arraylist<PlaylistItem>();
	}

	//this extends Arraylist<PlaylistItems>
	public boolean addTrack(PlaylistItem song){
		return true;
	}

	public boolean addTrack(PlaylistItem item, int position) {
		return true;
	}

	public boolean removeTrack(PlaylistItem song) {
		return true;
	}

	public boolean removeTrack(int position) {
		return true;
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
}