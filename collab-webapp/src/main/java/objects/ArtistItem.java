package objects;

import java.util.HashMap;
import java.util.Map;

public class ArtistItem extends JSONWrapper {
	
	String artist;
	boolean valid;
	
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
	
	public ArtistItem(String artist) {
		super(new HashMap<String, Object>());
		valid = true;
		this.artist = artist;
		map.put("singer", artist);
	}
	
	@Override
	public boolean isValid() {
		return valid;
	}

	@Override
	public	Map<String, Object> getMap() {
		return map;
	}

}
