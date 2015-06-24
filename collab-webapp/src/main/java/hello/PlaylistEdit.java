package hello;

import java.util.Map;

public class PlaylistEdit extends JSONWrapper {
	//The primary difference from playlist item will be the desired features.
	//To be valid, this requires the type, location, and at least hashID of the edit.
	private String topic;
	private String type;
	private Integer site;
	private PlaylistItem value;//it's a JSON object.
	//need to know more to know exactly what 'value' should have, but chances are it'll have the hash
	private Integer position;
	public PlaylistEdit(Map<String, Object> map) {
		super(map);
		try{
			topic = (String)map.get("topic");
		} catch (Exception ClassCastException){
			topic = null;
		}
		try{
			type = (String)map.get("type");
		} catch (Exception ClassCastException){
			type = null;
		}
		try{
			site = (Integer) map.get("site");
		} catch (Exception ClassCastException){
			site = null;
		}
		try{
			value = new PlaylistItem((Map<String, Object>) map.get("value"));
		} catch (Exception ClassCastException){
			value = null;
		}
		try{
			position = (Integer) map.get("position");
		} catch (Exception ClassCastException){
			position = null;
		}
	}

	public boolean isValid(){
		if (topic != null && type != null && site != null && value != null && position != null){
			//if ALL of that is valid...
			if(type.equals("insert") || type.equals("delete") || type.equals("update") || type.equals("null")){
				return value.isValid();
				//it's a properly formatted edit.
			}
		}
		return false;
	}

	@Override
	Map<String, Object> getMap() {
		return map;
	}
	public String getTopic() {
		return topic;
	}
	public String getType() {
		return type;
	}
	public PlaylistItem getValue(){
		return value;
	}
	public int getSite() {
		return site;
		//I'd make a check for the change from Integer to int given the possibility of null
		//But honestly, you shouldn't use this if you haven't checked isValid first.
	}
	public int getPosition() {
		return position;
	}
}