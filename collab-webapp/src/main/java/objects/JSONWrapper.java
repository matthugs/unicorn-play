package objects;

import java.util.Map;

public abstract class JSONWrapper {
	Map<String, Object> map;
	public JSONWrapper(Map<String, Object> map) {
		this.map = map;
	}

	abstract boolean isValid();

	abstract Map<String, Object> getMap();
}