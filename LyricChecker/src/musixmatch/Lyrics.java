package musixmatch;

// Hold the lyrics returned by
// MusixMatch and whether or not
// they are explicit.
public class Lyrics {
	private String lyrics;
	private boolean explicit;
	
	public Lyrics (String lyrics, boolean explicit) {
		this.lyrics = lyrics;
		this.explicit = explicit;
	}
	
	public String getLyrics () {
		return lyrics;
	}
	
	public boolean isExplicit () {
		return explicit;
	}

}
