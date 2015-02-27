import java.util.ArrayList;
import java.util.Arrays;

public class BadWords {
	private static ArrayList<String> badWords = new ArrayList<String>(Arrays.asList("asshole","cocksucker","cunt","dick","fuck","nigga","nigger","piss","pussy","shit"));
	private static ArrayList<String> qWords = new ArrayList<String>(Arrays.asList(" ass ","bitch","bastard","damn","hell","sex"));
	
	public static ArrayList<String> getBadWords () {
		return badWords;
	}
	
	public static ArrayList<String> getQWords () {
		return qWords;
	}
	
}
