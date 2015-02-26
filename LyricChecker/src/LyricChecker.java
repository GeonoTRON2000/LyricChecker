import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.ArrayList;
import musixmatch.URLConnectionReader;

public class LyricChecker
{
  String webText;
  Scanner scan;
  private String songLyrics;
  private String artistText;
  private String songText;
  private String badWordsOutput;
  private boolean hasBadWords;
  private boolean hasQWords;
  private ArrayList<String> badWords;
  private ArrayList<String> qWords;
  //private boolean urlInvalid;
  
  //input artist and song; get song lyrics; 
  //get list of swear words; return swear words and maybe number of occurences; 
  //deal with apostrophes and stuff;
  public LyricChecker(String artist, String song, ArrayList<String> bWords, ArrayList<String> questionWords)  //takes in human input artist and song
  { 
    badWords = bWords;
    qWords = questionWords;
    useMetro(artist, song);
    System.out.println(songLyrics);
    badWordsOutput = findBadWords(songLyrics);
  }
  public static void main(String[] args)
  {
  }
  
  public void useAZ(String artist, String song)
  {
    try
    {
      String webText;
      Scanner scan;
      artist  = processAZ(artist);
      if(artist.substring(0, 3).equals("the"))
        artist = artist.substring(3);
      song = processAZ(song);
      
      webText = URLConnectionReader.getText("http://www.azlyrics.com/lyrics/" + artist +"/" + song + ".html");
      scan = new Scanner(webText);
      
      scan.useDelimiter("artist: \"");
      scan.next();
      scan.useDelimiter("\"");
      scan.next();
      artistText = scan.next();
      
      scan.useDelimiter("song: \"");
      scan.next();
      scan.useDelimiter("\"");
      scan.next();
      songText = scan.next();
      
      scan.useDelimiter("<!-- start of lyrics -->");
      scan.next();
      scan.useDelimiter("<!-- end of lyrics -->");
      songLyrics = scan.next().substring(23);
      System.out.print(songLyrics);
      scan.close();
    }
    catch(Exception ex)
    {
      return;
    }
  }
  
  public void useMetro(String artist, String song)
  {
    String webText;
    artist  = processMetro(artist);
    song  = processMetro(song);
    try
    {
      webText = URLConnectionReader.getText("http://www.metrolyrics.com/printlyric/"+song+"-lyrics-"+artist+".html");
      //Scanner scan = new Scanner(webText);
      scan.useDelimiter("<h1>");
      scan.next();
      scan.useDelimiter(" Lyrics</h1>");
      songText = scan.next().substring(4);
      
      scan.useDelimiter("<h2>by ");
      scan.next();
      scan.useDelimiter("</h2>");
      artistText = scan.next().substring(7);
      
      scan.useDelimiter("<p class=\"lyrics-body\"><p class='verse'>");
      scan.next();
      scan.useDelimiter("</div>");
      songLyrics = scan.next();
      System.out.println(songLyrics);
      scan.close();
    }
    catch(Exception ex)
    {}
  }
  
  public String findBadWords(String lyrics)
  {         
    hasBadWords = true;
    hasQWords = true;
    String bad = "Bad words: ";
    String q = "Questionable words: ";
    for(int i = 0; i < badWords.size(); i++)
    {
      String currBWord = badWords.get(i);
      if(lyrics == null)
        System.out.println("LyricsWTF");
      if(currBWord == null)
        System.out.println("currBWord WTF");
      if(lyrics.indexOf(currBWord) != -1)
      {
        bad = bad + currBWord.substring(0,1) + currBWord.substring(1,currBWord.length()-1).replaceAll(".", "*") + currBWord.substring(currBWord.length()-1) + "; ";
      }
    }
    for(int i = 0; i < qWords.size(); i++)
    {
      String currQWord = qWords.get(i);
      if(lyrics.indexOf(currQWord) != -1)
      {
        if(currQWord.equals(" ass "))
          currQWord = "ass";
        q = q + currQWord + "; ";
      }
    }
    if(bad.equals("Bad words: "))
    {
      bad = bad + "none found. ";
      hasBadWords = false;
    }
    if(q.equals("Questionable words: "))
    {
      q = q + "none found. ";
      hasQWords = false;
    }
    bad = bad.substring(0, bad.length()-2) + ". ";
    q = "\n" + q.substring(0, q.length()-2) + ". ";
    return bad + q;
  }
  public static String processAZ(String songOrArtist)
  {
    songOrArtist = songOrArtist.replaceAll("\\s","");  
    songOrArtist = songOrArtist.replaceAll("\"","");
    songOrArtist = songOrArtist.replaceAll("\\.","");
    songOrArtist = songOrArtist.replaceAll("\'","");
    songOrArtist = songOrArtist.replaceAll(",","");   
    songOrArtist = songOrArtist.replaceAll("/",""); 
    songOrArtist = songOrArtist.replaceAll("-","");
    songOrArtist = songOrArtist.replaceAll("!",""); 
    songOrArtist = songOrArtist.replace("?","");
    songOrArtist = songOrArtist.replaceAll("#","");
    songOrArtist = songOrArtist.replaceAll("&","");
    songOrArtist = songOrArtist.replace("(","");
    songOrArtist = songOrArtist.replace(")","");
    songOrArtist = songOrArtist.toLowerCase();
    return songOrArtist;
  }
  public static String processMetro(String songOrArtist)
  {
    songOrArtist = songOrArtist.replaceAll("\'","");
    songOrArtist = songOrArtist.replace("?","");
    songOrArtist = songOrArtist.replaceAll("&","and");
    songOrArtist = songOrArtist.replaceAll("\\.","");
    songOrArtist = songOrArtist.replaceAll("!","");
    songOrArtist = songOrArtist.replaceAll("#","");
    songOrArtist = songOrArtist.replace("(","");
    songOrArtist = songOrArtist.replace(")","");
    songOrArtist = songOrArtist.replaceAll(" - ","-");
    songOrArtist = songOrArtist.toLowerCase();
    songOrArtist = songOrArtist.replaceAll("\\s","-");
    System.out.println(songOrArtist);
    return songOrArtist;
  }
  
  public void addBadWord(String s)
  {
    badWords.add(s);
  }
  
  public void removeBadWord(String s)
  {
    badWords.remove(s);
  }
  
  public void addQWord(String s)
  {
    qWords.add(s);
  }
  
  public void removeQWord(String s)
  {
    qWords.remove(s);
  }
  
  public String getLyrics()
  { return songLyrics; }
  public String getArtist()
  { return artistText; }
  public String getSong()
  { return songText; }
  public String getBadWordsOutput()
  { return badWordsOutput; }
  public boolean hasBadWords()
  { return hasBadWords; }
  public boolean hasQWords()
  { return hasQWords; }
  public ArrayList<String> getBadWords()
  { return badWords; }
  public ArrayList<String> getQWords()
  { return qWords; }
  //public boolean isURLInvalid()
  //{ return urlInvalid; }
  //for album processing
  //scan to "album\">", then scan to "<b>", then scan to " <\b>" for album name
  //scan to "blank\">", then scan to "</a> for song name (repeat for all songs, have user input number of songs in album)
}
