import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import musixmatch.URLConnectionReader;

public class LyricChecker {
  private String name;
  private String artist;
  private ArrayList<String> bwc;  // bad words found in this song
  private ArrayList<String> qwc;  // questionable words found in this song
  private ArrayList<String> bw;  // list of bad words 
  private ArrayList<String> qw;  // list of questionable words
  private boolean lookupFailed;  // is true when metrolyrics could not find the song specified
  
  public LyricChecker (String n, String a, ArrayList<String> bWords, ArrayList<String> qWords) //inititalize fields
  { 
    name = n;
    artist = a;
    bw = bWords;
    qw = qWords;
    bwc = new ArrayList<String>();
    qwc = new ArrayList<String>();
    lookupFailed = false;
  }
  
  public void checkLyrics () //checks the lyrics and adds the bad/questionable words found to bwc/qwc
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
        if(cqw.equals(" ass "))  //remove the spaces in ass
          cqw = "ass";
        qwc.add(cqw);
      }
    }
  }
  
  public static String metroLookup (String song, String artist) //looks up and returns the lyrics from metrolyrics as a string
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
  
  private static String processMetro (String str) //formats the user inputted song/artist to be put into the URL
  {  
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
  
  public ArrayList<String> foundBadWords () 
  {
    return bwc;
  }
  
  public ArrayList<String> foundQWords () 
  {
    return qwc;
  }
  
  public boolean hasBadWords() //returns whether bad words are present or not
  {
    return bwc.size() > 0;
  }
  
  public boolean hasQWords() //returns whether questionable words are present or not
  {
    return qwc.size() > 0;
  }
  
  public boolean lookupFailed() //returns true if metrolyrics did not have the song specified
  {
    return lookupFailed;
  }
  
}
