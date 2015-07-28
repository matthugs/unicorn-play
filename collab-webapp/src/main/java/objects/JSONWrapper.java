package objects;

import java.util.Map;

/**
 * The abstract class for all Javascript to useful object conversion. OpenCoWeb hands javascript messages to the moderators and bots as Map<String, Object> types.
 * The purpose of these classes is to interpret those objects into something useful and also provide verification methods.
 * @author William
 *
 */
public abstract class JSONWrapper {
	Map<String, Object> map;
	public JSONWrapper(Map<String, Object> map) {
		this.map = map;
	}

	/**
	 * @return returns true if valid, false else.
	 */
	abstract boolean isValid();

	/**
	 * @returns the Map<String, Object> JSON form of this object.
	 */
	abstract Map<String, Object> getMap();
}