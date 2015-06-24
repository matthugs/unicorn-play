package hello;

import java.util.Map;

public class PlaylistEdit extends JSONWrapper {
	//The primary difference from playlist item will be the desired features.
	//To be valid, this requires the type, location, and at least hashID of the edit.
	private String topic;
	private String type;
	private int site;
	//need to know more to know exactly what 'value' should have, but chances are it'll have the hash
	private int position;
	public PlaylistEdit(Map<String, Object> map) {
		super(map);
		topic = (String)map.get("topic");
		type = (String)map.get("type");
		//should probably have an instanceof call here or two but I dunno if that would apply here
		site = (Integer) map.get("site");
		position = (Integer) map.get("position");
	}

	public boolean isValid(){
		return true;
	}

	@Override
	Map<String, Object> getMap() {
		return null;
	}
	public String getTopic() {
		return topic;
	}
	public String getType() {
		return type;
	}
	public int getSite() {
		return site;
	}
	public int getPosition() {
		return position;
	}
}