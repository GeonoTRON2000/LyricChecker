import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import json.JSONObject;
import json.JSONArray;
import json.JSONParser;
import json.JSONString;
import musixmatch.MusixMatch;
import musixmatch.Playlist;
import musixmatch.Track;
import musixmatch.URLConnectionReader;

public class SpotifyConnector {

	private static String buildAPIUrl (String url) throws UnsupportedEncodingException {
		return "http://ws.spotify.com/lookup/1/.json?uri="+URLEncoder.encode(url, "UTF-8");
	}
	
	private JSONObject apiLookup (String url) throws IOException {
		String json = URLConnectionReader.getText(buildAPIUrl(url));
		JSONParser p = new JSONParser(json);
		return p.object();
	}
	
	public Track getTrack (String url) throws IOException {
		JSONObject result = apiLookup(url);
		JSONString type = result.getObject("info").getString("type");
		if (!type.getValue().equals("track")) return null;
		JSONObject tdata = result.getObject("track");
		JSONArray artists = tdata.getArray("artists");
		if (artists.size() <= 0) return null;
		JSONObject artist = artists.getObject(0);
		return MusixMatch.trackSearch(tdata.getString("name").getValue(), artist.getString("name").getValue());
	}
	
	public Playlist getAlbum (String url) throws IOException {
		JSONObject result = apiLookup(url);
		JSONString type = result.getObject("info").getString("album");
		if (!type.getValue().equals("track")) return null;
		JSONObject data = result.getObject("album");
		return MusixMatch.albumSearch(data.getString("name").getValue(), data.getString("artist").getValue());
	}
	
}
