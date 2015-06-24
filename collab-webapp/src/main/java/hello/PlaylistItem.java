package hello;

import java.util.Map;

public class PlaylistItem extends JSONWrapper {
	//A hash string should be one of the things in here, something that can be passed
	//to the hashtable to get an MPDSong object

	public PlaylistItem(Map<String, Object> map) {
		super(map);
	}

	public boolean isValid(){
		return true;
	}

	@Override
	public Map<String, Object> getMap() {
		return null;
	}
}