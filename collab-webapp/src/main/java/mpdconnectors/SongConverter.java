/**
 * 
 */
package mpdconnectors;

import objects.PlaylistItem;
import java.util.Collection;
import java.util.Hashtable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bff.javampd.Database;
import org.bff.javampd.exception.MPDDatabaseException;
import org.bff.javampd.objects.MPDSong;

/**
 * @author emily
 * This class is a singleton so that we avoid copying this giant hashtable 100 times.
 * 
 * It is responsible for converting PlaylistItems to MPDSongs, MPDSongs to PlaylistItems, and performing hash lookups.
 *
 */
public final class SongConverter {
	private static final Logger logger = LogManager.getLogger();
	
	private static SongConverter converter = null;
	private Hashtable<String, MPDSong> hashTable;
	
	/**
	 * Constructs the SongCoverter object by building a hashtable on all the songs in the MPD library
	 */
	private SongConverter() {
		
		//setup the hashtable for wrapper -> mpdsong conversion 
		
		hashTable = new Hashtable<String, MPDSong>();
		
		Collection<MPDSong> allSongList = null;
		try{
			allSongList = MPDWrapper.getMPD().getDatabase().listAllSongs();
		}
		catch (MPDDatabaseException e) {
			logger.warn(e.getMessage());
		}
		for(MPDSong song : allSongList) {
			hashTable.put(hash(song), song);
		}
	}
	
	/**
	 * @return The singleton instance of the SongConverter object.
	 */
	public static SongConverter getConverter(){
		if(converter == null) {
			converter = new SongConverter();
		}
		return converter;
	}
	

	/**
	 * Converts the PlaylistItem to an MPDSong
	 * @param wrapper
	 * @return
	 */
	public MPDSong playlistItemToMPDSong(PlaylistItem wrapper){
		return hashToMPDSong(wrapper.getHash());
	}
	
	/**
	 * Returns the MPDSong associated with the hash
	 * @param hash
	 * @return an MPDSong if successful, null else.
	 */
	public MPDSong hashToMPDSong(String hash){
		return hashTable.get(hash);
	}
	
	//This method can be called as results are served up from database searches
	/**
	 * Converts MPDSongs to PlaylistItems
	 * @param song
	 * @return
	 */
	public PlaylistItem mpdSongToPlaylistItem(MPDSong song){
		return new PlaylistItem(song.getName(), hash(song), song.getArtistName());
	}
	
	//Good heavens this method is a runtime monstrosity.
	/**
	 * Returns the PlaylistItem associated with the hash
	 * @param hash
	 * @return
	 */
	public PlaylistItem hashToPlaylistItem(String hash) {
		return mpdSongToPlaylistItem(hashToMPDSong(hash));
	}
	
	/**
	 * Creates the hash for an MPDSong
	 * @param song
	 * @return
	 */
	private String hash(MPDSong song){
		return (song.getArtistName() + song.getTitle());
	}
}
