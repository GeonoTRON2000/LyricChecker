package json;

// Holds a double and is a JSONValue.
public class JSONNumber extends JSONValue {
	private double d;
	
	public JSONNumber (double d) {
		this.d = d;
	}
	
	public double getValue () {
		return d;
	}
	
	public String toString () {
		return Double.toString(d);
	}
	
}
