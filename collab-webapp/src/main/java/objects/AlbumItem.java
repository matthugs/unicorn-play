package objects;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is server version of MPDAlbum.
 * @author emily
 *
 */
public class AlbumItem extends JSONWrapper {

	String artist;
	String album;
		
	boolean valid;
	
	/**
	 * Constructs an album item from a JSON message
	 * @param map
	 */
	public AlbumItem(Map<String, Object> map) {
		super(map);
		valid = true;
		try{
			artist = (String)map.get("singer");
			album = (String) map.get("album");
		} catch (ClassCastException e){
			artist = null;
			album = null;
			valid = false;
		}
	}
	
	/**
	 * Constructs an album item from the artist name and album name
	 * @param artist
	 * @param album
	 */
	public AlbumItem(String artist, String album) {
		super(new HashMap<String, Object>());
		valid = true;
		this.album = album;
		this.artist = artist;
		map.put("singer", artist);
		map.put("album", album);
	}
	
	/* (non-Javadoc)
	 * @see objects.JSONWrapper#isValid()
	 */
	@Override
	public boolean isValid() {
		return valid;
	}

	/* (non-Javadoc)
	 * @see objects.JSONWrapper#getMap()
	 */
	@Override
	public	Map<String, Object> getMap() {
		return map;
	}

}