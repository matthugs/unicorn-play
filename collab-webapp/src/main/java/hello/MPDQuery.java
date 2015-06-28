package hello;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bff.javampd.*;
import org.bff.javampd.objects.*;
import org.bff.javampd.exception.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class MPDQuery implements Query{

	private Database database;
	private Hashtable<String, MPDSong> hashTable;
	private static final Logger logger = LogManager.getLogger();
	
	public MPDQuery() {
		//this is just a stub to satisfy the compiler temporarily
		try {
			MPD mpd = new MPD.Builder()
			.port(6001)
			.password("password")
			.build();
			
			database = mpd.getDatabase();
			mpd.getAdmin().updateDatabase();
			
		} catch (Exception e){
			e.printStackTrace();
		}
		
		
	}

	public MPDQuery(Database database) {
		this.database = database;
		hashTable = new Hashtable<String, MPDSong>();
		Collection<MPDSong> allSongList = null;
		try{
			allSongList = database.listAllSongs();
		}
		catch (MPDDatabaseException e) {
			//Logger.error("sttuf");
		}
		for(MPDSong song : allSongList) {
			hashTable.put(hash(song), song);
		}
	}

	public Map<String, Object> searchAny(String criteria){
		Map<String, Object> ret = null;
		try{
			ret = songListToMap(database.findAny(criteria));
		}
		catch(Exception e) {

		}
		return ret;
	}

	public Map<String, Object> searchArtist(String artist){
		Map<String, Object> ret = null;
		try{
			ret = songListToMap(database.findArtist(artist));
		}
		catch(Exception e) {

		}
		return ret;
	}

	public Map<String, Object> searchGenre(String genre){
		Map<String, Object> ret = null;
		try{
			ret = songListToMap(database.findGenre(genre));
		}
		catch(Exception e) {

		}
		return ret;
	}

	public Map<String, Object> searchAlbum(String album){
		Map<String, Object> ret = null;
		try{
			ret = songListToMap(database.findAlbum(album));
		}
		catch(Exception e) {

		}
		return ret;
	}

	public Map<String, Object> searchSong(String song){
		Map<String, Object> ret = null;
		try{
			ret = songListToMap(database.findTitle(song));
		}
		catch(Exception e) {

		}
		return ret;
	}
	
	public Map<String, Object> listAll() {
		Map<String, Object> map = null;
		try{
			map = songListToMap(database.listAllSongs());
		}
		catch(Exception e) {

		}
		return map;
	}

	private Map<String, Object> mpdSongToJSON(MPDSong mpdsong){
		return new PlaylistItem(mpdsong.getName(), hash(mpdsong), mpdsong.getArtistName()).getMap();
	}

	private Map<String, Object> songListToMap(Collection<MPDSong> songList) {
		List<Map<String, Object>> set = new ArrayList<Map<String, Object>>();//list of playlist objects (maps)
		for(MPDSong song : songList) {
			set.add(mpdSongToJSON(song));
		}
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("list", set);//Only actual parameter for this
		/*Map<String, Object> ret = new HashMap<String, Object>();
		for(MPDSong song : songList) {
			//the value should be a JSON object with the same data as the frontend list.
			ret.put(song.getName(), mpdSongToMap(song));
			//may need to figure out a different key.
		}*/
		return ret;
	}

	public String hash(MPDSong song){
		return (song.getArtistName() + song.getTitle());
	}
	
	public MPDSong getSong(String hash){
		return hashTable.get(hash);
	}
}