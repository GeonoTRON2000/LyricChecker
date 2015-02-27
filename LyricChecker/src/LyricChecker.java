import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import musixmatch.URLConnectionReader;

public class LyricChecker {
 private String name;
 private String artist;
 private ArrayList<String> bwc;
 private ArrayList<String> qwc;
 private ArrayList<String> bw;
 private ArrayList<String> qw;
 private boolean lookupFailed;
 
 public LyricChecker (String n, String a, ArrayList<String> bWords, ArrayList<String> qWords) {
  name = n;
  artist = a;
  bw = bWords;
  qw = qWords;
  bwc = new ArrayList<String>();
  qwc = new ArrayList<String>();
  lookupFailed = false;
 }

 public void checkLyrics () 
 {
  String lyrics = metroLookup(name, artist);
  if (lyrics == null) {
    lookupFailed = true;
    return;
  }
  for(int i = 0; i < bw.size(); i++) {
   String cbw = bw.get(i);
   if (lyrics.indexOf(cbw) > -1) bwc.add(cbw);
  }
  for (int i = 0; i < qw.size(); i++) {
   String cqw = qw.get(i);
   if (lyrics.indexOf(cqw) > -1) 
   {
     if(cqw.equals(" ass "))
       cqw = "ass";
     qwc.add(cqw);
   }
  }
 }
 
 public static String metroLookup (String song, String artist) 
 {
   try {
     String html = URLConnectionReader.getText("http://www.metrolyrics.com/printlyric/"+processMetro(song)+"-lyrics-"+processMetro(artist)+".html");
     Scanner scan = new Scanner(html);
     scan.useDelimiter("<p class='verse'>");
     scan.next();
     scan.useDelimiter("</div>");
     String lookedup = scan.next();
     scan.close();
     return lookedup;
   } catch (IOException e) {
     return null;
   }
 }
 
 private static String processMetro (String str) {
  str = str.replaceAll("\'","");
  str = str.replaceAll(",","");
  str = str.replace("?","");
  str = str.replaceAll(" &","");  //to "" or "and"??
  str = str.replaceAll("\\.","");
  str = str.replaceAll("!","");
  str = str.replace("[","");
  str = str.replace("]","");
  str = str.replaceAll("#","");
  str = str.replace("(","");
  str = str.replace(")","");
  str = str.replaceAll(" - ","-");
  str = str.toLowerCase();
  str = str.replaceAll("\\s","-");
  int feat = str.indexOf("-feat\\S*");
  if(feat > 0)
    str = str.substring(0, feat);
  return str;
 }
 
 public ArrayList<String> foundBadWords () {
  return bwc;
 }
 
 public ArrayList<String> foundQWords () {
  return qwc;
 }
 
 public boolean hasBadWords() {
  return bwc.size() > 0;
 }
 
 public boolean hasQWords() {
  return qwc.size() > 0;
 }
 
 public boolean lookupFailed(){
   return lookupFailed;
 }

}