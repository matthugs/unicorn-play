package hello;

import java.util.HashMap;
import java.util.Map;

public class PlaylistItem extends JSONWrapper {
	//A hash string should be one of the things in here, something that can be passed
	//to the hashtable to get an MPDSong object
	private boolean valid;
	private String song;
	private String singer;
	private String hash;

	public PlaylistItem(Map<String, Object> map) {
		super(map);
		valid = true;
		try{
			song = (String)map.get("song");
		} catch (Exception ClassCastException){
			song = null;
			valid = false;
		}
		try{
			singer = (String)map.get("singer");
		} catch (Exception ClassCastException){
			singer = null;
			valid = false;
		}
		try{
			hash = (String)map.get("hash");
		} catch (Exception ClassCastException){
			hash = null;
			valid = false;
		}
	}
	
	public PlaylistItem(String song, String hash, String singer) {
		super(new HashMap<String, Object>());
		valid = true;
		this.song = song;
		this.singer = singer;
		this.hash = hash;
		map.put("song", song); map.put("singer", singer); map.put("hash", hash);
	}

	public boolean isValid(){
		return valid;
	}

	@Override
	public Map<String, Object> getMap() {
		return map;
	}
	
	public String getSong() {
		return song;
	}
	public String getSinger() {
		return singer;
	}
	public String getHash() {
		return hash;
	}
}