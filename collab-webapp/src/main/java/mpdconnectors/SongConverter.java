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
 */
public final class SongConverter {
	private static final Logger logger = LogManager.getLogger();
	
	private static SongConverter converter = null;
	private Hashtable<String, MPDSong> hashTable;
	
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
	
	public static SongConverter getConverter(){
		if(converter == null) {
			converter = new SongConverter();
		}
		return converter;
	}
	
	// Designed to quickly retrieve an MPDSong and put it in the playlist
	public MPDSong playlistItemToMPDSong(PlaylistItem wrapper){
		return hashToMPDSong(wrapper.getHash());
	}
	
	public MPDSong hashToMPDSong(String hash){
		return hashTable.get(hash);
	}
	
	//This method can be called as results are served up from database searches
	public PlaylistItem mpdSongToPlaylistItem(MPDSong song){
		return new PlaylistItem(song.getName(), hash(song), song.getArtistName());
	}
	
	//Good heavens this method is a monstrosity.
	public PlaylistItem hashToPlaylistItem(String hash) {
		return mpdSongToPlaylistItem(hashToMPDSong(hash));
	}
	
	private String hash(MPDSong song){
		return (song.getArtistName() + song.getTitle());
	}
}
