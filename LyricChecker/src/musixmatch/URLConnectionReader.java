package musixmatch;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

// credit: Joseph Weissman http://stackoverflow.com/questions/4328711/read-url-to-string-in-few-lines-of-java-code
// Read text directly from a URL.

public class URLConnectionReader {
  
  public static String getText(String url) throws IOException {
    URL website = new URL(url);
    URLConnection connection = website.openConnection();
    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    StringBuilder response = new StringBuilder();
    String inputLine;
    while ((inputLine = in.readLine()) != null) response.append(inputLine);
    in.close();
    return response.toString();
  }
}