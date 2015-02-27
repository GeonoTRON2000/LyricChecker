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

public class SpotifyConnector 
{

 private static String buildAPIUrl (String url) throws UnsupportedEncodingException 
 {
  return "http://ws.spotify.com/lookup/1/.json?uri="+URLEncoder.encode(url, "UTF-8");
 }

 // Parse API response into a JSONObject.
 private static JSONObject apiLookup (String url) throws IOException 
 {
  String json = URLConnectionReader.getText(buildAPIUrl(url));
  JSONParser p = new JSONParser(json);
  return p.object();
 }
 
 // Find the track at a given Spotify URL,
 // then take the name and artist and search
 // them in MusixMatch.
 public static Track getTrack (String url) throws IOException {
  JSONObject result = apiLookup(url);
  JSONString type = result.getObject("info").getString("type");
  if (!type.getValue().equals("track")) return null;
  JSONObject tdata = result.getObject("track");
  JSONArray artists = tdata.getArray("artists");
  String tn = tdata.getString("name").getValue();
  Track t = null;
  for (int i = 0; i < artists.size(); i++) 
  {
   t = MusixMatch.trackSearch(tn, artists.getObject(i).getString("name").getValue());
   if (t != null) break;
  }
  return t;
 }
 
 // Do the same as above with an album.
 public static Playlist getAlbum (String url) throws IOException 
 {
  JSONObject result = apiLookup(url);
  JSONString type = result.getObject("info").getString("type");
  if (!type.getValue().equals("album")) return null;
  JSONObject data = result.getObject("album");
  return MusixMatch.albumSearch(data.getString("name").getValue(), data.getString("artist").getValue());
 }
 
}
