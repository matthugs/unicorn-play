public abstract class JSONWrapper {
	Map<String, Object> map;
	public JSONWrapper(Map<String, Object> map) {
		this.map = map;
	}

	abstract boolean isValid() {

	}

	getMap() {
		return map;
	}
}