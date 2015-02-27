package musixmatch;
import java.io.IOException;
import java.net.URLEncoder;
import json.JSONArray;
import json.JSONNumber;
import json.JSONObject;
import json.JSONParser;

public class MusixMatch {
 private static final String api_base = "http://api.musixmatch.com/ws/1.1/";
 private static final String api_key = "7e4f8d8e45c998aae965023755ffafd3";
 
 // Fetch and parse the body of the request,
 // then return it as JSONObject.
 private static JSONObject apiFetch (String url) throws IOException {
  String feedback = URLConnectionReader.getText(api_base+url);
  JSONParser p = new JSONParser(feedback);
  JSONObject message = p.object().getObject("message");
  System.out.println(message);
  JSONNumber status = message.getObject("header").getNumber("status_code");
  if (status.getValue() != 200) throw new MusixMatchException("API lookup did not return successfully: "+(int)status.getValue()+".");
  return message.getObject("body");
 }
 
 // Look up the lyrics given a track id, then
 // interpret the results as a Lyrics object.
 private static Lyrics lyrics (int tid) throws IOException {
  JSONObject data = apiFetch("track.lyrics.get?apikey="+api_key+"&track_id="+tid+"&format=json").getObject("lyrics");
  JSONNumber expl = data.getNumber("explicit");
  boolean explicit = false;
  if (expl != null) explicit = expl.getValue() > 0;
  return new Lyrics(data.getString("lyrics_body").getValue(), explicit);
 }
 
 // Take the first search result by track and
 // artist name, build a Track object from there.
 public static Track trackSearch (String song, String artist) throws IOException {
  JSONObject data = apiFetch("track.search?apikey="+api_key+"&q_track="+URLEncoder.encode(song, "UTF-8")+"&q_artist="+URLEncoder.encode(artist, "UTF-8")+"&page_size=1&format=json");
  JSONArray tracklist = data.getArray("track_list");
  if (tracklist.size() <= 0) return null;
  JSONObject first = tracklist.getObject(0).getObject("track");
  int trackid = (int)first.getNumber("track_id").getValue();
  return new Track(first.getString("track_name").getValue(), first.getString("artist_name").getValue(), trackid, lyrics(trackid));
 }
 
 // Extract the album ID from an object.
 private static int albumId (JSONObject album) {
  return (int)album.getObject("album").getNumber("album_id").getValue();
 }
 
 // Extract the album name from an object.
 private static String albumName (JSONObject album) {
  return album.getObject("album").getString("album_name").getValue();
 }
 
 // Take the first artist result from
 // the API, comb through their list of
 // albums. Find take the first one that
 // matches the name passed, then build
 // Track objects out of every track listed.
 // Return a Playlist object composed of all
 // the Track objects we built.
 public static Playlist albumSearch (String album, String artist) throws IOException {
  JSONObject artistSearch = apiFetch("artist.search?apikey="+api_key+"&q_artist="+URLEncoder.encode(artist, "UTF-8")+"&format=json");
  JSONArray artistList = artistSearch.getArray("artist_list");
  if (artistList.size() <= 0) return null;
  JSONNumber artistIDNum = artistList.getObject(0).getObject("artist").getNumber("artist_id");
  int artistId = (int)artistIDNum.getValue();
  JSONObject albumsData = apiFetch("artist.albums.get?apikey="+api_key+"&artist_id="+artistId+"&page_size="+Integer.MAX_VALUE+"&format=json");
  JSONArray albumList = albumsData.getArray("album_list");
  int albumId = 0;
  String albumName = null;
  for (int i = 0; i < albumList.size(); i++) {
   if ((albumName = albumName(albumList.getObject(i))).equalsIgnoreCase(album)) {
    albumId = albumId(albumList.getObject(i));
    break;
   }
  }
  if (albumId <= 0) return null;
  JSONObject tracksSearch = apiFetch("album.tracks.get?apikey="+api_key+"&album_id="+albumId+"&page_size="+Integer.MAX_VALUE+"&format=json");
  JSONArray tracksList = tracksSearch.getArray("track_list");
  Track[] tracks = new Track[tracksList.size()];
  for (int i = 0; i < tracksList.size(); i++) {
   JSONObject trackObject = tracksList.getObject(i).getObject("track");
   int trackid = (int)trackObject.getNumber("track_id").getValue();
   tracks[i] = new Track(trackObject.getString("track_name").getValue(), trackObject.getString("artist_name").getValue(), trackid, lyrics(trackid));
  }
  return new Playlist(albumName, tracks);
 }
 
}