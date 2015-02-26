package json;

import java.io.IOException;

public class TokenTester {
	public static void main (String[] args) {
		try {
			JSONParser p = new JSONParser(System.in);
			//while (true) {
//				System.out.println(p.value());
				JSONObject track = p.object().getObject("track");
				JSONArray artists = track.getArray("artists");
				System.out.print("Artists: ");
				for (int i = 0; i < artists.size(); i++) {
					JSONString artist = artists.getObject(i).getString("name");
					System.out.print(artist.getValue()+" ");
				}
				System.out.println();
				JSONString album = track.getObject("album").getString("name");
				System.out.println("Album: "+album.getValue());
				JSONString name = track.getString("name");
				System.out.println("Track: "+name.getValue());
		//	}	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
