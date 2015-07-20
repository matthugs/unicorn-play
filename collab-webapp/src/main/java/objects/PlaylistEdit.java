package objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class PlaylistEdit extends JSONWrapper {
	private static final Logger logger = LogManager.getLogger();
	
	//The primary difference from playlist item will be the desired features.
	//To be valid, this requires the type, location, and at least hashID of the edit.
	private boolean valid;
	private String topic;
	private String type; //insert, delete, update, null
	private Integer site;
	private PlaylistItem value;//it's a JSON object.
	//need to know more to know exactly what 'value' should have, but chances are it'll have the hash
	private Integer position;
	
	
	public PlaylistEdit(Map<String, Object> map) {
		super(map);
		valid = true;
		try{
			topic = (String)map.get("topic");
		} catch (ClassCastException e){
			topic = null;
			valid = false;
			logger.warn("Bad topic");
		}
		try{
			type = (String)map.get("type");
		} catch (ClassCastException e){
			type = null;
			valid = false;
			logger.warn("Bad type");
		}
		try{
			site = (Integer) map.get("site");
		} catch (ClassCastException e){
			site = null;
			valid = false;
			logger.warn("Bad site");
		}
		try{
			value = new PlaylistItem((Map<String, Object>) map.get("value"));
		} catch (ClassCastException e){
			value = null;
			valid = false;
			logger.warn("Bad value");
		}
		try{
			position = (Integer) map.get("position");
		} catch (ClassCastException e){
			position = null;
			valid = false;
			logger.warn("Bad position");
		}
		if(!value.isValid()){
			valid = false;
		}
		if(!(type.equals("insert") || type.equals("delete") || type.equals("update") || type.equals("null"))){
			valid = false;
			logger.warn("Invalid type");
		}
	}

	public boolean isValid(){
		return valid;
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