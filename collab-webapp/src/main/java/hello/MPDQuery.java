package hello;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.bff.javampd.*;
import org.bff.javampd.objects.*;
import org.bff.javampd.exception.*;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class MPDQuery implements Query{
	private static final Logger logger = LogManager.getLogger();
	private Database database;
	private Hashtable<String, MPDSong> hashTable;


	public MPDQuery(Database database) {
		this.database = database;
		hashTable = new Hashtable<String, MPDSong>();
		try{
		Collection<MPDSong> allSongList = database.listAllSongs();
		}
		catch (MPDDatabaseException e) {
			Logger.error("sttuf");
		}
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
		try{
		HashMap<String, Object> ret = songListToJSONList(database.findGenre(genre));
		}
		catch(Exception e) {

		}
	}

	public Map<String, Object> searchAlbum(String album){
		return songListToJSONList(database.findAlbum(album));
	}

	public Map<String, Object> searchSong(String song){
		return songListToJSONList(database.findTitle(song));
	}

	private Map<String, Object> mpdSongToJSON(MPDSong mpdsong){
		//TODO: FIX ME!!!!!!
		//Should include the hash for this song: method is below.
		return new HashMap<String, Object>();
	}

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

	public String hash(MPDSong song){
		return (song.getArtistName() + song.getTitle());
	}
	
	public MPDSong getSong(String hash){
		return hashTable.get(hash);
	}

	public Map<String, Object> listAll() {
		return songListToJSONList(database.listAllSongs());
	}
}