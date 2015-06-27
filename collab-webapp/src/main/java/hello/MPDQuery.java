package hello;

import org.bff.javampd.*;
import org.bff.javampd.objects.*;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class MPDQuery implements Query{

	private MPDDatabase database;
	private Hashtable<String, MPDSong> hashTable;

	public MPDQuery(MPDDatabase database) {
		this.database = database;
		hashTable = new Hashtable<String, MPDSong>();
		ArrayList<MPDSong> allSongList = database.listAllSongs();
		for(MPDSong song : allSongList) {
			hashTable.put(hash(song), song);
		}
	}

	public Map<String, Object> searchAny(String criteria){
		return songListToJSONList(database.findAny(criteria));
	}

	public Map<String, Object> searchArtist(String artist){
		return songListToJSONList(database.findArtist(artist));
	}

	public Map<String, Object> searchGenre(String genre){
		return songListToJSONList(database.findGenre(genre));
	}

	public Map<String, Object> searchAlbum(String album){
		return songListToJSONList(database.findAlbum(album));
	}

	public Map<String, Object> searchSong(String song){
		return songListToJSONList(database.findTitle(song));
	}

	// private Map<String, Object> mpdSongToJSON(MPDSong mpdsong){
	// 	//TODO: FIX ME!!!!!!
	// 	//Should include the hash for this song: method is below.
	// 	return "";
	// }

	private Map<String, Object> songListToJSONList(Collection<MPDSong> songList) {
		/*List<String> ret = new ArrayList<String>();
		for(MPDSong song : songList) {
			ret.add(mpdSongToJSON(song));
		}
		return ret;*/
		Map<String, Object> ret = new HashMap<String, Object>();
		for(MPDSong song : songList) {
			//the value should be a JSON object with the same data as the frontend list.
			ret.put(song.getName(), mpdSongToJSON(song));
			//may need to figure out a different key.
		}
		return ret;
	}

	protected String hash(MPDSong song){
		return (song.getArtistName() + song.getTitle());
	}
	
	public MPDSong getSong(String hash){
		return hashTable.get(hash);
	}

}