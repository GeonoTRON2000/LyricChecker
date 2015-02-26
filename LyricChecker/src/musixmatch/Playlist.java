package musixmatch;
import java.util.Iterator;

// An iterable list of tracks with
// a name, AKA an album or a playlist.
public class Playlist implements Iterable<Track>, Iterator<Track> {
	private String name;
	private Track[] tracks;
	private int i;
	
	public Playlist (String name, Track[] tracks) {
		this.name = name;
		this.tracks = tracks;
		this.i = 0;
	}
	
	public Track getTrack (int i) {
		return tracks[i];
	}
	
	public String getName () {
		return name;
	}
		
	public int size () {
		return tracks.length;
	}

	public boolean hasNext () {
		return i < size();
	}

	public Track next () {
		return tracks[i++];
	}

	public void remove() {}
	
	public String toString () {
		String data = name+":\r\n";
		for (Track t : tracks) {
			data += "  "+t+"\r\n";
		}
		return data;
	}
	
	public Iterator<Track> iterator () {
		return this;
	}
	
}
