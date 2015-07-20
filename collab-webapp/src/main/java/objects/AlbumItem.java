package objects;

import java.util.HashMap;
import java.util.Map;

public class AlbumItem extends JSONWrapper {

	String artist;
	String album;
		
	boolean valid;
	
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
	
	public AlbumItem(String artist, String album) {
		super(new HashMap<String, Object>());
		valid = true;
		this.album = album;
		this.artist = artist;
		map.put("singer", artist);
		map.put("album", album);
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