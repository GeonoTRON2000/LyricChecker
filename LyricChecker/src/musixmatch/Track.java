package musixmatch;

// Hold the name, MusixMatch
// ID, and Lyrics object of
// a given track.
public class Track {
	private String name;
	private String artist;
	private int id;
	private Lyrics lyrics;
	
	public Track (String name, String artist, int id, Lyrics lyrics) {
		this.name = name;
		this.artist = artist;
		this.id = id;
		this.lyrics = lyrics;
	}
	
	public String getName () {
		return name;
	}
	
	public String getArtist () {
		return artist;
	}
	
	public int getId () {
		return id;
	}
	
	public boolean isExplicit () {
		return lyrics.isExplicit();
	}
	
	public String getLyrics () {
		return lyrics.getLyrics();
	}
		
	public String toString () {
		return getName();
	}
		
}
