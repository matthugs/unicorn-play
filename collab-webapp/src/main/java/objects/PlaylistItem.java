package objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * PlaylistItem is the fundamental unit of data for the server. It can be constructed from information pulled from MPDSongs.
 * @author William
 *
 */
public class PlaylistItem extends JSONWrapper {
	private static final Logger logger = LogManager.getLogger();

	//A hash string should be one of the things in here, something that can be passed
	//to the hashtable to get an MPDSong object
	private boolean valid;
	private String song;
	private String singer;
	private String hash;

	/**
	 * @param map
	 */
	public PlaylistItem(Map<String, Object> map) {
		super(map);
		valid = true;
		try{
			song = (String)map.get("song");
		} catch (ClassCastException e){
			song = null;
			valid = false;
		}
		try{
			singer = (String)map.get("singer");
		} catch (ClassCastException e){
			singer = null;
			valid = false;
		}
		try{
			hash = (String)map.get("hash");
		} catch (ClassCastException e){
			hash = null;
			valid = false;
		}
	}
	
	/**
	 * Constructs a PlaylistItem given these three parameters.
	 * @param song the song title
	 * @param hash the string hash
	 * @param singer the artist name
	 */
	public PlaylistItem(String song, String hash, String singer) {
		super(new HashMap<String, Object>());
		valid = true;
		this.song = song;
		this.singer = singer;
		this.hash = hash;
		map.put("song", song); map.put("singer", singer); map.put("hash", hash);
	}

	/* (non-Javadoc)
	 * @see objects.JSONWrapper#isValid()
	 */
	public boolean isValid(){
		return valid;
	}

	/* (non-Javadoc)
	 * @see objects.JSONWrapper#getMap()
	 */
	@Override
	public Map<String, Object> getMap() {
		return map;
	}
	
	/**
	 * @return the song title
	 */
	public String getSong() {
		return song;
	}
	
	/**
	 * @return the artist name
	 */
	public String getSinger() {
		return singer;
	}
	
	/**
	 * @return the hash string
	 */
	public String getHash() {
		return hash;
	}
}