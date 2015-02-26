package json;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

// Creates a sort of "stack" of tokens
// that can be parsed out of an input
// stream.

// DISCLAIMER: This is not an actual
//             stack, it just emulates
//             one.
public class JSONTokenizer {
	private InputStream input;
	private JSONToken lastToken;
	private Object lastValue;
	
	public JSONTokenizer (String json) throws IOException {
		this(new ByteArrayInputStream(json.getBytes()));
	}
	
	public JSONTokenizer (InputStream input) throws IOException {
		this.input = input;
	}
	
	private int hexdigit (char c) {
		if (c >= '0' && c <= '9') return c-'0';
		else if (c >= 'a' && c <= 'f') return c-'a'+10;
		else if (c >= 'A' && c <= 'F') return c-'A'+10;
		else throw new JSONTokenException(c);
	}
	
	public void read () throws IOException {
		boolean number = false;
		boolean negative = false;
		int dpt = 0;
		boolean string = false;
		boolean escape = false;
		int unicode = 0;
		int unicodeNumber = 0;
		boolean reserved = false;
		while (true) {
			char c = (char) input.read(); // Get the next character from the input stream.
			
			if (number && !(c >= '0' && c <= '9') && !(c == '.' && dpt <= 0)) { // Break out of parsing a number.
				input.reset();
				number = false;
				lastToken = JSONToken.NUMBER;
				if (negative) lastValue = -((Double)lastValue);
				negative = false;
				dpt = 0;
				return;				
			}
			
			if (reserved && !((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) { // Break out of parsing either true, false, or null.
				input.reset();
				reserved = false;
				if (lastValue.equals("true")) lastToken = JSONToken.TRUE;
				else if (lastValue.equals("false")) lastToken = JSONToken.FALSE;
				else if (lastValue.equals("null")) lastToken = JSONToken.NULL;
				else throw new JSONTokenException((String)lastValue);
				return;				
			}
			
			if (string) { // If we're currently parsing a string.
				input.mark(1); // Allow us to backtrack once we parse the entire string.
				if (unicode > 0) { // if we're currently parsing a \\u, which should be followed by 4 hex digits
					unicodeNumber = unicodeNumber*16 + hexdigit(c);
					if (unicode++ == 4) {
						lastValue = ((String)lastValue)+((char)unicodeNumber); // append the appropriate unicode character
						unicode = 0;
						unicodeNumber = 0;
					}
					continue;
				}
				if (escape) { // If we parsed a \\ character previously.
					escape = false;
					switch (c) {
					case '"':
						lastValue = ((String)lastValue)+'"';
						break;
					case '\\':
						lastValue = ((String)lastValue)+'\\';
						break;
					case '/':
						lastValue = ((String)lastValue)+'/';
						break;
					case 'b':
						lastValue = ((String)lastValue)+'\b';
						break;
					case 'f':
						lastValue = ((String)lastValue)+'\f';
						break;
					case 'n':
						lastValue = ((String)lastValue)+'\n';
						break;
					case 'r':
						lastValue = ((String)lastValue)+'\r';
						break;
					case 't':
						lastValue = ((String)lastValue)+'\t';
						break;
					case 'u':
						unicode = 1;
						break;
					default:
						lastValue = ((String)lastValue)+'\\'+c;
					}
					continue;
				}
				if (c == '\\') escape = true;
				else if (c == '\n') throw new JSONTokenException('\n'); // No line breaks in the middle of strings.
				else if (c == '"') { // Stop parsing the string.
					if (unicode > 0) throw new JSONTokenException('"');
					string = false;
					lastToken = JSONToken.STRING;
					input.reset();
					return;
				} else lastValue = ((String)lastValue)+c; // In the normal case, append the current character.
				continue;
			}
			if (c == '"') { // Start parsing a string.
				input.mark(1);
				string = true;
				lastValue = "";
				continue;
			}
			
			if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) { // Start parsing either an identifier string.
				input.mark(1);
				if (!reserved) {
					reserved = true;
					lastValue = "";
				}
				lastValue = ((String)lastValue)+c;
				continue;
			}
			
			if (c == '-') { // We're about to parse a negative number.
				input.mark(1);
				negative = !negative;
				continue;
			}
			if (c >= '0' && c <= '9') { // Start parsing a number.
				input.mark(1);
				if (!number) {
					number = true;
					lastValue = new Double(0);
				}
				if (dpt > 0) { // We've already seen a decimal point.
					lastValue = ((Double)lastValue)+(((double)(c-'0'))/Math.pow(10, dpt));
					dpt++;
				} else lastValue = ((Double)lastValue)*10+(c-'0');
				continue;
			}
			if (c == '.' && number && dpt <= 0) { // If we're seeing a decimal point for the first time.
				input.mark(1);
				dpt++;
				continue;
			}
			
			// Miscellaneous single-character tokens.
			lastValue = null;
			switch (c) {
			case '{':
				lastToken = JSONToken.LCURLY;
				return;
			case '}':
				lastToken = JSONToken.RCURLY;
				return;
			case '[':
				lastToken = JSONToken.LSQUARE;
				return;
			case ']':
				lastToken = JSONToken.RSQUARE;
				return;
			case ',':
				lastToken = JSONToken.COMMA;
				return;
			case ':':
				lastToken = JSONToken.COLON;
				return;
			case ' ':
			case '\n':
			case '\t':
			case '\r':
				break;
			default:
				throw new JSONTokenException(c);
			}
		}
	}
	
	public JSONToken peek () throws IOException {
		if (lastToken == null) read();
		return lastToken;
	}
	
	public Object peekValue () throws IOException {
		if (lastToken == null) read();
		return lastValue;
	}
	
	public JSONToken pop () throws IOException {
		JSONToken t = peek();
		lastToken = null;
		lastValue = null;
		return t;
	}

}