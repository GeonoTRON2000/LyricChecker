package json;
import java.util.ArrayList;
import java.util.Iterator;

// Holds an ArrayList of JSONValues, and
// is a JSONValue in itself. Can get any
// subtype of JSONValue by index.
public class JSONArray extends JSONValue implements Iterable<JSONValue> {
	private ArrayList<JSONValue> values;
	
	public JSONArray () {
		values = new ArrayList<JSONValue>();
	}
	
	public void add (JSONValue value) {
		values.add(value);
	}
	
	public JSONValue get (int i) {
		return values.get(i);
	}
	
	public JSONObject getObject (int i) {
		JSONValue fetched = values.get(i);
		if (!(fetched instanceof JSONObject)) throw new JSONException("'"+i+"' is not an object.");
		return (JSONObject) fetched;
	}
	
	public JSONArray getArray (int i) {
		JSONValue fetched = values.get(i);
		if (!(fetched instanceof JSONArray)) throw new JSONException("'"+i+"' is not an array.");
		return (JSONArray) fetched;
	}
	
	public JSONString getString (int i) {
		JSONValue fetched = values.get(i);
		if (!(fetched instanceof JSONString)) throw new JSONException("'"+i+"' is not a string.");
		return (JSONString) fetched;
	}
	
	public JSONNumber getNumber (int i) {
		JSONValue fetched = values.get(i);
		if (!(fetched instanceof JSONNumber)) throw new JSONException("'"+i+"' is not a number.");
		return (JSONNumber) fetched;
	}
	
	public JSONBoolean getBoolean (int i) {
		JSONValue fetched = values.get(i);
		if (!(fetched instanceof JSONBoolean)) throw new JSONException("'"+i+"' is not a boolean.");
		return (JSONBoolean) fetched;
	}
	
	public int size () {
		return values.size();
	}
	
	public String toString () {
		return values.toString();
	}
	
	public Iterator<JSONValue> iterator () {
		return values.iterator();
	}
	
}
