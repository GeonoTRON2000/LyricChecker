package json;

// The various things that can be popped
// off of the token "stack".
public enum JSONToken {
	STRING,
	NUMBER,
	LCURLY,
	RCURLY,
	LSQUARE,
	RSQUARE,
	COMMA,
	COLON,
	TRUE,
	FALSE,
	NULL
}
