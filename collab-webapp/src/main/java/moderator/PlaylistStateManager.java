package moderator;

import mpdconnectors.MPDPlaylistRepresentation;
import objects.PlaylistItem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bff.javampd.objects.*;

import java.util.HashMap;

/**
 * Maintains an internal playlist representation and communicates between the interface to MPD and the moderator.
 * <p>
 * Functions mainly include adding and removing tracks when told to by the moderator or MPDPlaylistRepresentation.
 * In either case, it notifies the other object of the changes to maintain playlist consistency.
 * 
 * @author William Baldwin
 * @version 1.0
 *
 */
public class PlaylistStateManager implements IPlaylistUpdate{
	private static final Logger logger = LogManager.getLogger();

	public ArrayList<PlaylistItem> playlist;
	private PlaylistModerator par;//so it can tell the moderator if there's a change from the MPD end
	private MPDPlaylistRepresentation updater;
	
	public PlaylistStateManager(PlaylistModerator ins){
		logger.info("PLAYLIST STATE MANAGER INSTANTIATED");
		playlist = new ArrayList<PlaylistItem>();
		par = ins;
		updater = new MPDPlaylistRepresentation(this);
	}
	
	//Appends to the end of the list
	/**
	 * Relays the adding of a track to MPDPlaylistRepresentation.
	 * 
	 * @param song	the track to be added
	 * @return true if successful, false else
	 */
	public boolean addTrack(PlaylistItem song){
		logger.debug("addTrack(song) called");
		// this is deprecated for the moment because we're not optimzing yet

		// if(playlist.isEmpty()) {
		// 	return addTrack(song, 0);
		// }
		// else if (playlist.size() == 1){
		// 	return addTrack(song, 1);
		// }
		// else {
		// 	return playlist.add(song);
		// }

		updater.addSong(song);
		return true;
	}
	/**
	 * Relays the adding of a track to MPDPlaylistRepresentation.
	 * <p>
	 * Currently not fully functional, hence the relay to the non-positional version of addTrack.
	 * @param item	the track to be added
	 * @param position	where in the list to add the track
	 * @return true if successful, false else
	 */
	public boolean addTrack(PlaylistItem item, int position) {
		logger.debug("addTrack(song, "+position+") called");
		playlist.add(position, item);
		// if(playlist.get(position).equals(item)){
		// 	if(position == 0){//start of the list
		// 		updater.setCurrent(item);//notify the playlistrepresentation in some way
		// 	} else if (position == 1){
		// 		updater.setNext(item);
		// 	}
		// }
		// logger.debug("Track Added: " + item.getSinger() + " - " + item.getSong() + "\n");
		// logger.debug("PLAYLIST STATE: \n");
		// logger.debug(toString());
		// return (playlist.get(position).equals(item));
		return addTrack(item);
	}
	
	public String toString() {
		String ret = "stuff\n";
		for(int i = 0; i < playlist.size(); i++) {
			ret += (i + ": " + playlist.get(i).getSinger() + " - " + playlist.get(i).getSong() + "\n");
		}
		
		return ret;
	}
	
	/**
	 * Relays the removal of a specific track to MPDPlaylistRepresentation.
	 * 
	 * @param song	the track to be removed
	 * @return true if successful, false else
	 */
	public boolean removeTrack(PlaylistItem song) {
		logger.debug("removeTrack(song) called");
		return playlist.remove(song);
	}

	public boolean removeTrack(int position) {
		logger.debug("removeTrack("+ position +") called");
		PlaylistItem check = playlist.remove(position);
		if(check != null){
			if(position == 0){
				
			} else if(position == 1){
				
			}
		}
		return check != null;
	}
	
	//for onNewJoin or whatever that method is in the moderator
	public Map<String, Object> getMap(){ //kinda crappy method name
		logger.debug("getMap() called");
		//for every item
		//get the Map<String, Object> and package them together
		List<Map<String, Object>> set = new ArrayList<Map<String, Object>>();
		for(PlaylistItem item : playlist) {
			System.out.println(item.getHash());
			set.add(item.getMap());
		}
		HashMap<String, Object> ret = new HashMap<String,Object>();
		ret.put("list", set);
		return ret;
	}
    //these need to be mirrored by the Moderator.
	
	/**
	 * Relays the removal of the track at the top of the list to PlaylistModerator.
	 */
	@Override
	public void popCurrentlyPlaying() {
		logger.debug("popCurrentlyPlaying()");
		playlist.remove(0);//depending on how sendsync works this may be double-removing
		
		par.removeTop();
		//Should pull next from collaboration model
	}
	
	/**
	 * Verifies that the top song on MPD's playlist and the top song on this playlist match.
	 * 
	 * @return whether the currently playing song matches the song on PlaylistStateManager's list
	 */
	@Override
	public boolean verifyCurrentlyPlaying(PlaylistItem whatMPDIsCurrentlyPlaying) {
		logger.debug("verifyCurrentlyPlaying() called");
		logger.debug("PLAYLIST STATE at verifyCurrentlyPlaying(): \n");
		logger.debug(toString());
		
		boolean ret = false;
		
		if (playlist.get(0).equals(whatMPDIsCurrentlyPlaying)) {
			ret = true;
		}
		
		return ret;
	}
	
	@Override
	public void forceCurrentlyPlaying(PlaylistItem whatMPDIsCurrentlyPlaying) {
		logger.debug("forceCurrentlyPlaying() called");
		playlist.add(0, whatMPDIsCurrentlyPlaying);//depending on how sendsync works this may be double-adding
		par.addTop(whatMPDIsCurrentlyPlaying);
	}

	/**
	 * Called by MPDPlaylistRepresentation to grab the next song when needed (typically when one finishes playing)
	 * 
	 * @return the next song on the playlist
	 */
	@Override
	public PlaylistItem getNext() {
		logger.debug("getNext() called");
		return playlist.get(1);
	}
}