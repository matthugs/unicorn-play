package objects;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is server version of MPDArtist.
 * @author emily
 *
 */

public class ArtistItem extends JSONWrapper {
	
	String artist;
	boolean valid;
	
	/**
	 * @param map Constructs the wrapper from a JSON map
	 */
	public ArtistItem(Map<String, Object> map) {
		super(map);
		valid = true;
		try{
			artist = (String)map.get("singer");
		} catch (ClassCastException e){
			artist = null;
			valid = false;
		}
	}
	
	/**
	 * @param artist Constructs the wrapper from the artist name
	 */
	public ArtistItem(String artist) {
		super(new HashMap<String, Object>());
		valid = true;
		this.artist = artist;
		map.put("singer", artist);
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
