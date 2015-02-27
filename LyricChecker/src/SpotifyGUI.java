import java.awt.Font;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import musixmatch.Playlist;
import musixmatch.Track;

public class SpotifyGUI extends JFrame implements KeyListener {
	private static final long serialVersionUID = -7484625783866853171L;
	private JTextField input;
	private JTextArea output;
	private ArrayList<String> bw;
	private ArrayList<String> qw;

	public SpotifyGUI (ArrayList<String> bw, ArrayList<String> qw) {
		super();
		this.bw = bw;
		this.qw = qw;
		buildFrame();
	}

	private void buildFrame () {
		JPanel pane = new JPanel();
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		pane.setBorder(BorderFactory.createEmptyBorder(6, 10, 10, 10));


		JLabel idLabel = new JLabel("Spotify URI(s)");
		idLabel.setFont(idLabel.getFont().deriveFont(14.0f));
		idLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		pane.add(idLabel);

		input = new JTextField(30);
		input.setMaximumSize(input.getPreferredSize());
		input.addKeyListener(this);
		pane.add(input);

		pane.add(Box.createRigidArea(new Dimension(0,10)));

		JLabel info = new JLabel("Paste Spotify URI(s) and press enter.");
		info.setAlignmentX(Component.CENTER_ALIGNMENT);
		pane.add(info);

		pane.add(Box.createRigidArea(new Dimension(0,3))); 

		output = new JTextArea();
		output.setFont(new Font("monospaced", Font.PLAIN, 14));
		output.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		output.setLineWrap(true);
		output.setWrapStyleWord(true);
		output.setEditable(false);
		pane.add(new JScrollPane(output));


		add(pane);
		setTitle("Search By Spotify URI");
		setSize(520, 300);
		setLocationRelativeTo(null);
	}

	public void keyPressed (KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			output.setText("");
			String[] ids = input.getText().split(" ");
			for (String id : ids) output.append(lookupId(id));
			input.requestFocusInWindow();
			input.selectAll();
		}
	}

	private String lookupId (String id) {
		String lcid = id.toLowerCase();
		if (lcid.startsWith("spotify:track:")) return lookupTrack(id);
		else if (lcid.startsWith("spotify:album:")) return lookupAlbum(id);
		else return "Unsupported ID\r\n";
	}

	private String lookupTrack (String id) {
		try {
			String out = "";
			Track t = SpotifyConnector.getTrack(id);
			if (t == null) return MenuGUI.makeLevelLeft(id)+MenuGUI.makeLevelRight("[Not Found]")+"\r\n"; 
			out += MenuGUI.makeLevelLeft(t.getName());
			LyricChecker lc = new LyricChecker(t.getName(), t.getArtist(), bw, qw);
			lc.checkLyrics();
			if (lc.hasBadWords()) out += MenuGUI.makeLevelRight("[Explicit]");
			else if (lc.hasQWords()) out += MenuGUI.makeLevelRight("[Questionable]");
			else if (lc.lookupFailed()) out += MenuGUI.makeLevelRight(t.isExplicit() ? "[Explicit]" : "[Clean]");
			else out += MenuGUI.makeLevelRight("[Clean]");
			out += "\r\n";
			return out;
		} catch (IOException e) {
			return MenuGUI.makeLevelLeft(id)+MenuGUI.makeLevelRight("[IO Failed]")+"\r\n";
		}
	}

	private String lookupAlbum (String id) {
		try {
			String out = "";
			Playlist pl = SpotifyConnector.getAlbum(id);
			if (pl == null) return MenuGUI.makeLevelLeft(id)+MenuGUI.makeLevelRight("[Failed]")+"\r\n"; 
			for (Track t : pl) {
				out += MenuGUI.makeLevelLeft(t.getName());
				LyricChecker lc = new LyricChecker(t.getName(), t.getArtist(), bw, qw);
				lc.checkLyrics();
				if (lc.hasBadWords()) out += MenuGUI.makeLevelRight("[Explicit]");
				else if (lc.hasQWords()) out += MenuGUI.makeLevelRight("[Questionable]");
				else if (lc.lookupFailed()) out += MenuGUI.makeLevelRight(t.isExplicit() ? "[Explicit]" : "[Clean]");
				else out += MenuGUI.makeLevelRight("[Clean]");
				out += "\r\n";
			}
			return out;
		} catch (IOException e) {
			return MenuGUI.makeLevelLeft(id)+MenuGUI.makeLevelRight("[Failed]")+"\r\n";
		}
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
}
