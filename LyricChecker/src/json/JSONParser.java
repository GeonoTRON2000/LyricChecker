package json;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class JSONParser {
	protected JSONTokenizer tokenizer;
	
	public JSONParser (String json) throws IOException {
		this(new ByteArrayInputStream(json.getBytes()));
	}
	
	public JSONParser (InputStream input) throws IOException {
		tokenizer = new JSONTokenizer(input);
	}
	
	public JSONObject object () throws IOException {
		// Parse the following syntax:
		// {
		//   "string" : value,
		//   ...
		// }
		if (tokenizer.pop() != JSONToken.LCURLY) throw new JSONParseException("That's not an object.");
		JSONObject object = new JSONObject();
		while (tokenizer.peek() != JSONToken.RCURLY) {
			JSONString key = string();
			if (tokenizer.pop() != JSONToken.COLON) throw new JSONParseException("Expected ':' here.");
			JSONValue value = value();
			object.set(key.getValue(), value);
			if (tokenizer.peek() == JSONToken.RCURLY) break;
			if (tokenizer.pop() != JSONToken.COMMA) throw new JSONParseException("Expected ',' here.");
		}
		tokenizer.pop();
		return object;
	}
	
	public JSONArray array () throws IOException {
		// Parse the following syntax:
		// [value1, value2, ...]
		if (tokenizer.pop() != JSONToken.LSQUARE) throw new JSONParseException("That's not an array.");
		JSONArray array = new JSONArray();
		while (tokenizer.peek() != JSONToken.RSQUARE) {
			array.add(value());
			if (tokenizer.peek() == JSONToken.RSQUARE) break;
			if (tokenizer.pop() != JSONToken.COMMA) throw new JSONParseException("Expected ',' here.");
		}
		tokenizer.pop();
		return array;
	}
	
	public JSONString string() throws IOException {
		// Parse a string (which we've already parsed in the tokenizer.)
		if (tokenizer.peek() != JSONToken.STRING) throw new JSONParseException("That's not a string.");
		JSONString string = new JSONString((String)tokenizer.peekValue());
		tokenizer.pop();
		return string;
	}
	
	public JSONNumber number () throws IOException {
		// Parse a double (which we've already parsed in the tokenizer.)
		if (tokenizer.peek() != JSONToken.NUMBER) throw new JSONParseException("That's not a number.");
		JSONNumber number = new JSONNumber((Double)tokenizer.peekValue());
		tokenizer.pop();
		return number;
	}
	
	public JSONBoolean bool () throws IOException {
		// Parse a boolean, which was already done in the tokenizer.
		JSONToken t = tokenizer.pop();
		if (t == JSONToken.TRUE) return new JSONBoolean(true);
		else if (t == JSONToken.FALSE) return new JSONBoolean(false);
		else throw new JSONParseException("That's not a boolean.");
	}
	
	public JSONValue value () throws IOException {
		JSONToken t = tokenizer.peek();
		if (t == JSONToken.LCURLY) return object(); // If it starts with {, parse an object.
		else if (t == JSONToken.LSQUARE) return array(); // If it starts with [, parse an array.
		else if (t == JSONToken.STRING) return string(); // If it's a string, parse a string.
		else if (t == JSONToken.NUMBER) return number(); // If it's a double, parse a double.
		else if (t == JSONToken.TRUE || t == JSONToken.FALSE) return bool(); // If it's true or false, return a boolean.
		else if (t == JSONToken.NULL) return null; // If null, return null.
		else throw new JSONParseException("Expected a value.");
	}

}
