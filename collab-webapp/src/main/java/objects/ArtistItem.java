package objects;

import java.util.HashMap;
import java.util.Map;

/**
 * @author emily
 *
 */

public class ArtistItem extends JSONWrapper {
	
	String artist;
	boolean valid;
	
	/**
	 * @param map
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
	 * @param artist
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
