package collaboration;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import objects.PlaylistItem;
import mpdconnectors.SongConverter;

/**
 * Suggest and Demand collaboration was deemed to not be a priority for the MVP. This class is unfinished.
 * @author emily
 *
 */
public class SuggestDemand implements ICollabToPlaylist {
	Queue<PlaylistItem> demandQueue;
	ArrayList<PlaylistItem> suggestionBox; // Is an arraylist really the best structure to use here?
	SongConverter converter = SongConverter.getConverter();
	
	
	public SuggestDemand() {
		demandQueue = new LinkedList<PlaylistItem>();
		suggestionBox = new ArrayList<PlaylistItem>();
	}
	
	public void suggest(String hash) {
		suggestionBox.add(converter.hashToPlaylistItem(hash));
	}
	
	public void demand(String hash) {
		demandQueue.add(converter.hashToPlaylistItem(hash));
		//TODO: rate limit the suggester?? Should that happen here?
	}
	
	
	@Override
	public PlaylistItem getTrack() {
		//pop the top of the queue first
		//if it's null, look in the suggestion box and remove the highest voted thing
		PlaylistItem ret = demandQueue.remove();
		if(ret == null) {
			ret = suggestionBox.remove(0);
		}
		return ret;
	}

}
