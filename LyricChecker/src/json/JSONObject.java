package json;
import java.util.HashMap;

// Stores a HashMap of string keys mapped
// to JSONValue values. Can get any subtype
// of JSONValue by string key.
public class JSONObject extends JSONValue {
	private HashMap<String, JSONValue> data;
	
	public JSONObject () {
		data = new HashMap<String, JSONValue>();
	}
	
	public void set (String key, JSONValue value) {
		data.put(key, value);
	}
	
	public JSONValue get (String key) {
		return data.get(key);
	}
	
	public JSONObject getObject (String key) {
		JSONValue fetched = data.get(key);
		if (!(fetched instanceof JSONObject)) throw new JSONException("'"+key+"' is not an object.");
		return (JSONObject) fetched;
	}
	
	public JSONArray getArray (String key) {
		JSONValue fetched = data.get(key);
		if (!(fetched instanceof JSONArray)) throw new JSONException("'"+key+"' is not an array.");
		return (JSONArray) fetched;
	}
	
	public JSONString getString (String key) {
		JSONValue fetched = data.get(key);
		if (!(fetched instanceof JSONString)) throw new JSONException("'"+key+"' is not a string.");
		return (JSONString) fetched;
	}
	
	public JSONNumber getNumber (String key) {
		JSONValue fetched = data.get(key);
		if (!(fetched instanceof JSONNumber)) throw new JSONException("'"+key+"' is not a number.");
		return (JSONNumber) fetched;
	}
	
	public JSONBoolean getBoolean (String key) {
		JSONValue fetched = data.get(key);
		if (!(fetched instanceof JSONBoolean)) throw new JSONException("'"+key+"' is not a boolean.");
		return (JSONBoolean) fetched;
	}
	
	public String[] keys () {
		return data.keySet().toArray(new String[data.size()]);
	}
	
	public String toString () {
		String str = "{\n";
		String[] keys = keys();
		for (String key : keys) {
			str += "\""+key+"\": "+data.get(key)+",\n";
		}
		str += "}";
		return str;
	}
	
}
