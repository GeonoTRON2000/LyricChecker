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
	
	public static void main (String[] args) {
		SpotifyGUI gui = new SpotifyGUI(new ArrayList<String>(0), new ArrayList<String>(0));
		gui.setVisible(true);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
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
		
		
	    JLabel idLabel = new JLabel("Spotify ID");
	    idLabel.setFont(idLabel.getFont().deriveFont(14.0f));
	    idLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    pane.add(idLabel);
	    
	    input = new JTextField(30);
	    input.setMaximumSize(input.getPreferredSize());
	    input.addKeyListener(this);
	    pane.add(input);
	    
	    pane.add(Box.createRigidArea(new Dimension(0,10)));
	    
	    JLabel info = new JLabel("Paste Spotify ID(s) and press enter.");
	    info.setAlignmentX(Component.CENTER_ALIGNMENT);
	    pane.add(info);
	    
	    pane.add(Box.createRigidArea(new Dimension(0,3))); 
	    
	    output = new JTextArea();
	    output.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	    output.setLineWrap(true);
	    output.setWrapStyleWord(true);
	    output.setEditable(false);
	    pane.add(new JScrollPane(output));
	    
	    
		add(pane);
		setTitle("Search By Spotify ID");
		setSize(400, 225);
		setLocationRelativeTo(null);
	}

	public void keyPressed (KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			output.setText("");
			String[] ids = input.getText().split(" ");
			for (String id : ids) {
				output.append(lookupId(id));
			}
		}
	}
	
	private String lookupId (String id) {
		String lcid = id.toLowerCase();
		if (lcid.startsWith("spotify:track:")) return lookupTrack(id);
		else if (lcid.startsWith("spotify:album:")) return lookupAlbum(id);
		else return "Invalid ID\r\n";
	}
	
	private String lookupTrack (String id) {
		try {
			String out = "";
			Track t = SpotifyConnector.getTrack(id);
			out += t.getName();
			if (t.isExplicit()) {
				LyricChecker lc = new LyricChecker(t.getName(), t.getArtist(), bw, qw);
				lc.checkLyrics();
				if (lc.hasBadWords()) out += " [Bad Words]";
				else if (lc.hasQWords()) out += " [Questionable]";
				else out += " [Clean]";
			} else {
				out += " [Clean]";
			}
			out += "\r\n";
			return out;
		} catch (IOException e) {
			return id+" [Failed]\r\n";
		}
	}
	
	private String lookupAlbum (String id) {
		try {
			String out = "";
			Playlist pl = SpotifyConnector.getAlbum(id);
			for (Track t : pl) {
				out += t.getName();
				if (t.isExplicit()) {
					LyricChecker lc = new LyricChecker(t.getName(), t.getArtist(), bw, qw);
					lc.checkLyrics();
					if (lc.hasBadWords()) out += " [Bad Words]";
					else if (lc.hasQWords()) out += " [Questionable]";
					else out += " [Clean]";
				} else {
					out += " [Clean]";
				}
				out += "\r\n";				
			}
			return out;
		} catch (IOException e) {
			return id+" [Failed]\r\n";
		}
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
}
